package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.executor.Executor;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PolicyUtils {
    private static final int ALL_NEW_DMA_VERSION = 600000000;
    public static final int DLC = 1;
    public static final int DLS = 0;
    public static final int DMA_B = 2;
    public static final int DMA_P = 3;
    private static final int DMA_SUPPORT_VERSION = 540000000;
    public static final int NONE = -1;
    private static int senderType = -1;

    public static int getRemainingQuota(Context context, int i) {
        int i2;
        SharedPreferences preferences = Preferences.getPreferences(context);
        int i3 = 0;
        if (i == 1) {
            i2 = preferences.getInt(Constants.KEY_WIFI_QUOTA, 0);
            i3 = preferences.getInt(Constants.KEY_WIFI_USED, 0);
        } else if (i == 0) {
            i2 = preferences.getInt(Constants.KEY_DATA_QUOTA, 0);
            i3 = preferences.getInt(Constants.KEY_DATA_USED, 0);
        } else {
            i2 = 0;
        }
        return i2 - i3;
    }

    public static int hasQuota(Context context, int i, int i2) {
        int i3;
        int i4;
        int i5;
        SharedPreferences preferences = Preferences.getPreferences(context);
        if (i == 1) {
            i3 = preferences.getInt(Constants.KEY_WIFI_QUOTA, 0);
            i5 = preferences.getInt(Constants.KEY_WIFI_USED, 0);
            i4 = preferences.getInt(Constants.KEY_WIFI_ONCE_QUOTA, 0);
        } else if (i == 0) {
            i3 = preferences.getInt(Constants.KEY_DATA_QUOTA, 0);
            i5 = preferences.getInt(Constants.KEY_DATA_USED, 0);
            i4 = preferences.getInt(Constants.KEY_DATA_ONCE_QUOTA, 0);
        } else {
            i4 = 0;
            i3 = 0;
            i5 = 0;
        }
        Debug.LogENG("Quota : " + i3 + "/ Uploaded : " + i5 + "/ limit : " + i4 + "/ size : " + i2);
        if (i3 < i5 + i2) {
            Debug.LogD("DLS Sender", "send result fail : Over daily quota");
            return -1;
        } else if (i4 >= i2) {
            return 0;
        } else {
            Debug.LogD("DLS Sender", "send result fail : Over once quota");
            return -11;
        }
    }

    public static boolean isPolicyExpired(Context context) {
        SharedPreferences preferences = Preferences.getPreferences(context);
        if (Utils.compareDays(1, Long.valueOf(preferences.getLong(Constants.LAST_QUOTA_RESET_DATE, 0)))) {
            resetQuota(preferences);
        }
        return Utils.compareDays(preferences.getInt(Constants.POLICY_RINT, 1), Long.valueOf(preferences.getLong(Constants.POLICY_RECEIVED_DATE, 0)));
    }

    public static void resetQuota(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().putLong(Constants.LAST_QUOTA_RESET_DATE, System.currentTimeMillis()).putInt(Constants.KEY_DATA_USED, 0).putInt(Constants.KEY_WIFI_USED, 0).apply();
    }

    public static Map<String, String> makePolicyParam(Context context, DeviceInfo deviceInfo, Configuration configuration) {
        HashMap hashMap = new HashMap();
        hashMap.put("pkn", context.getPackageName());
        hashMap.put("dm", deviceInfo.getDeviceModel());
        if (!TextUtils.isEmpty(deviceInfo.getMcc())) {
            hashMap.put(XDBInterface.XDM_SQL_ACCESSORY_MCC, deviceInfo.getMcc());
        }
        if (!TextUtils.isEmpty(deviceInfo.getMnc())) {
            hashMap.put("mnc", deviceInfo.getMnc());
        }
        hashMap.put("uv", configuration.getVersion());
        hashMap.put("sv", "6.05.033");
        hashMap.put("did", configuration.getDeviceId());
        hashMap.put("tid", configuration.getTrackingId());
        String format = SimpleDateFormat.getTimeInstance(2, Locale.US).format(new Date());
        hashMap.put("ts", format);
        hashMap.put("hc", Validation.sha256(configuration.getTrackingId() + format + Validation.SALT));
        String csc = getCSC();
        if (!TextUtils.isEmpty(csc)) {
            hashMap.put("csc", csc);
        }
        return hashMap;
    }

    public static void getPolicy(Context context, Configuration configuration, Executor executor, DeviceInfo deviceInfo, Callback callback) {
        executor.execute(makeGetPolicyClient(context, configuration, deviceInfo, callback));
    }

    public static void getPolicy(Context context, Configuration configuration, Executor executor, DeviceInfo deviceInfo) {
        executor.execute(makeGetPolicyClient(context, configuration, deviceInfo, null));
    }

    public static GetPolicyClient makeGetPolicyClient(Context context, Configuration configuration, DeviceInfo deviceInfo, Callback callback) {
        GetPolicyClient getPolicyClient = new GetPolicyClient(API.GET_POLICY, makePolicyParam(context, deviceInfo, configuration), Preferences.getPreferences(context), callback);
        Debug.LogENG("trid: " + configuration.getTrackingId().substring(0, 7) + ", uv: " + configuration.getVersion());
        return getPolicyClient;
    }

    public static void useQuota(Context context, int i, int i2) {
        SharedPreferences preferences = Preferences.getPreferences(context);
        if (i == 1) {
            preferences.edit().putInt(Constants.KEY_WIFI_USED, preferences.getInt(Constants.KEY_WIFI_USED, 0) + i2).apply();
        } else if (i == 0) {
            preferences.edit().putInt(Constants.KEY_DATA_USED, Preferences.getPreferences(context).getInt(Constants.KEY_DATA_USED, 0) + i2).apply();
        }
    }

    public static int setSenderType(Context context, Configuration configuration) {
        int i = -1;
        if (senderType == -1) {
            int dMAversion = Utils.getDMAversion(context);
            if (dMAversion < DMA_SUPPORT_VERSION) {
                if (configuration.isEnableUseInAppLogging()) {
                    i = 0;
                }
                senderType = i;
            } else if (dMAversion >= ALL_NEW_DMA_VERSION) {
                senderType = 3;
            } else {
                senderType = 2;
            }
        }
        return senderType;
    }

    public static int getSenderType() {
        return senderType;
    }

    public static String getCSC() {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, "ro.csc.sales_code");
        } catch (Exception unused) {
            return null;
        }
    }
}
