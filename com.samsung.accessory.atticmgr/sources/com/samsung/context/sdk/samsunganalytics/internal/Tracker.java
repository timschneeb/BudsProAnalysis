package com.samsung.context.sdk.samsunganalytics.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.DBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.LogBuilders;
import com.samsung.context.sdk.samsunganalytics.UserAgreement;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Directory;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Domain;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Constants;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.property.PropertyRegisterClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingRegisterClient;
import com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterTask;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Tracker {
    public static final int AUID_TYPE_DMA = 4;
    public static final int AUID_TYPE_FROM_CF = 0;
    public static final int AUID_TYPE_INAPP = 2;
    public static final int AUID_TYPE_MAKE_SDK = 1;
    public static final int AUID_TYPE_UNKNOWN = -1;
    public static final int DEVICE_ID_BIT_NUM = 128;
    private static final int MIN_SDK = 23;
    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;
    private Application application;
    private Configuration configuration;
    private Context mContext;

    public Tracker(Application application2, Configuration configuration2) {
        String str;
        this.application = application2;
        this.configuration = configuration2;
        this.mContext = application2.getApplicationContext();
        if (Build.VERSION.SDK_INT < 23) {
            Debug.LogE("DMASA SDK is required at least version 23");
            return;
        }
        if (!TextUtils.isEmpty(configuration2.getDeviceId())) {
            this.configuration.setAuidType(2);
        } else if (!loadDeviceId() && configuration2.isEnableAutoDeviceId() && configuration2.isEnableUseInAppLogging()) {
            setDeviceId(generateRandomDeviceId(), 1);
        }
        if (PolicyUtils.getSenderType() == 0) {
            getPolicy();
        }
        if (!configuration2.isEnableUseInAppLogging()) {
            this.configuration.setUserAgreement(new UserAgreement() {
                /* class com.samsung.context.sdk.samsunganalytics.internal.Tracker.AnonymousClass1 */

                @Override // com.samsung.context.sdk.samsunganalytics.UserAgreement
                public boolean isAgreement() {
                    return Utils.isDiagnosticAgree(Tracker.this.mContext);
                }
            });
        }
        if ((Utils.isDMADataProvideVersion(application2.getApplicationContext()) || isUserAgreement()) && PolicyUtils.getSenderType() == 3) {
            SharedPreferences preferences = Preferences.getPreferences(this.mContext);
            try {
                str = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                Debug.LogException(getClass(), e);
                str = "";
            }
            str = str == null ? "None" : str;
            boolean z = preferences.getBoolean(Preferences.PREFS_KEY_SEND_COMMON_SUCCESS, false);
            String string = preferences.getString(Preferences.PREFS_KEY_APP_VERSION, "None");
            Long valueOf = Long.valueOf(preferences.getLong(Preferences.PREFS_KEY_SEND_COMMON_TIME, 0));
            Debug.LogD("AppVersion = " + str + ", prefAppVerison = " + string + ", beforeSendCommonTime = " + valueOf + ", success = " + z);
            if (!str.equals(string) || ((z && Utils.compareDays(7, valueOf)) || (!z && Utils.compareHours(6, valueOf)))) {
                Debug.LogD("send Common!!");
                preferences.edit().putString(Preferences.PREFS_KEY_APP_VERSION, str).putLong(Preferences.PREFS_KEY_SEND_COMMON_TIME, System.currentTimeMillis()).apply();
                ((DMALogSender) Sender.get(application2, 3, configuration2)).sendCommon();
            }
        }
        Utils.sendSettings(this.mContext, configuration2);
        sendPreviousUserAgreementState();
        Debug.LogD("Tracker", "Tracker start:6.05.033 , senderType : " + PolicyUtils.getSenderType());
    }

    private void getPolicy() {
        SharedPreferences preferences = Preferences.getPreferences(this.application);
        Domain.DLS.setDomain(preferences.getString(Constants.KEY_DLS_DOMAIN, ""));
        Directory.DLS_DIR.setDirectory(preferences.getString(Constants.KEY_DLS_URI, ""));
        Directory.DLS_DIR_BAT.setDirectory(preferences.getString(Constants.KEY_DLS_URI_BAT, ""));
        if (PolicyUtils.isPolicyExpired(this.mContext)) {
            PolicyUtils.getPolicy(this.application, this.configuration, SingleThreadExecutor.getInstance(), new DeviceInfo(this.application), new Callback<Void, Boolean>() {
                /* class com.samsung.context.sdk.samsunganalytics.internal.Tracker.AnonymousClass2 */

                public Void onResult(Boolean bool) {
                    if (!bool.booleanValue()) {
                        return null;
                    }
                    DBOpenHelper dbOpenHelper = Tracker.this.configuration.getDbOpenHelper();
                    if (dbOpenHelper == null) {
                        Manager.getInstance(Tracker.this.mContext, Tracker.this.configuration).enableDatabaseBuffering(Tracker.this.mContext);
                        return null;
                    }
                    Manager.getInstance(Tracker.this.mContext, Tracker.this.configuration).enableDatabaseBuffering(new DbManager(dbOpenHelper));
                    return null;
                }
            });
        }
    }

    public void enableAutoActivityTracking() {
        if (Build.VERSION.SDK_INT < 23) {
            Debug.LogE("DMASA SDK is required at least version 23");
        } else {
            this.application.registerActivityLifecycleCallbacks(makeActivityLifecycleCallbacks());
        }
    }

    private Application.ActivityLifecycleCallbacks makeActivityLifecycleCallbacks() {
        Application.ActivityLifecycleCallbacks activityLifecycleCallbacks2 = this.activityLifecycleCallbacks;
        if (activityLifecycleCallbacks2 != null) {
            return activityLifecycleCallbacks2;
        }
        this.activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
            /* class com.samsung.context.sdk.samsunganalytics.internal.Tracker.AnonymousClass3 */

            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            public void onActivityDestroyed(Activity activity) {
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivityResumed(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivityStarted(Activity activity) {
                Tracker.this.sendLog(new LogBuilders.ScreenViewBuilder().setScreenView(activity.getComponentName().getShortClassName()).build());
            }
        };
        return this.activityLifecycleCallbacks;
    }

    public int sendLog(Map<String, String> map) {
        String str;
        Map map2;
        if (Build.VERSION.SDK_INT < 23) {
            Debug.LogE("DMASA SDK is required at least version 23");
            return -9;
        }
        if (!Utils.isDMADataProvideVersion(this.application.getApplicationContext())) {
            if (!isUserAgreement()) {
                Debug.LogD("user do not agree");
                return -2;
            }
            if (map.containsKey("pd")) {
                map.remove("pd");
            }
            if (map.containsKey("ps")) {
                map.remove("ps");
            }
        }
        if (map == null || map.isEmpty()) {
            Debug.LogD("Failure to send Logs : No data");
            return -3;
        } else if (!checkDeviceId()) {
            return -5;
        } else {
            if ("pp".equals(map.get("t"))) {
                SingleThreadExecutor.getInstance().execute(new PropertyRegisterClient(this.mContext, map));
                Utils.sendProperties(this.mContext, this.configuration);
                return 0;
            }
            if ("ev".equals(map.get("t")) && (str = map.get("et")) != null && (str.equals(String.valueOf(10)) || str.equals(String.valueOf(11)))) {
                String string = Preferences.getPropertyPreferences(this.mContext).getString("guid", "");
                if (!TextUtils.isEmpty(string)) {
                    String str2 = map.get("cd");
                    if (TextUtils.isEmpty(str2)) {
                        map2 = new HashMap();
                    } else {
                        map2 = Utils.parseDelimiterString(str2, Utils.Depth.TWO_DEPTH);
                    }
                    map2.put("guid", string);
                    map.put("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(map2), Utils.Depth.TWO_DEPTH));
                }
            }
            return Sender.get(this.application, PolicyUtils.getSenderType(), this.configuration).send(map);
        }
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    private void setDeviceId(String str, int i) {
        Preferences.getPreferences(this.mContext).edit().putString("deviceId", str).putInt(Preferences.PREFS_KEY_DID_TYPE, i).apply();
        this.configuration.setAuidType(i);
        this.configuration.setDeviceId(str);
    }

    private boolean loadDeviceId() {
        SharedPreferences preferences = Preferences.getPreferences(this.application);
        String string = preferences.getString("deviceId", "");
        int i = preferences.getInt(Preferences.PREFS_KEY_DID_TYPE, -1);
        if (TextUtils.isEmpty(string) || string.length() != 32 || i == -1) {
            return false;
        }
        this.configuration.setAuidType(i);
        this.configuration.setDeviceId(string);
        return true;
    }

    private String generateRandomDeviceId() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bArr = new byte[16];
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            secureRandom.nextBytes(bArr);
            try {
                sb.append("0123456789abcdefghijklmjopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (Math.abs(new BigInteger(bArr).longValue()) % ((long) 62))));
            } catch (Exception e) {
                Debug.LogException(getClass(), e);
                return null;
            }
        }
        return sb.toString();
    }

    public void registerSettingPref(Map<String, Set<String>> map) {
        if (Build.VERSION.SDK_INT < 23) {
            Debug.LogE("DMASA SDK is required at least version 23");
            return;
        }
        SingleThreadExecutor.getInstance().execute(new SettingRegisterClient(this.mContext, map));
        Utils.sendSettings(this.mContext, this.configuration);
    }

    private boolean isUserAgreement() {
        return this.configuration.getUserAgreement().isAgreement();
    }

    private boolean checkDeviceId() {
        if (PolicyUtils.getSenderType() >= 2 || !TextUtils.isEmpty(this.configuration.getDeviceId())) {
            return true;
        }
        Debug.LogD("did is empty");
        return false;
    }

    public void changeUserAgreementState(boolean z) {
        if (Build.VERSION.SDK_INT < 23) {
            Debug.LogE("DMASA SDK is required at least version 23");
        } else if (!z && this.configuration.isEnableUseInAppLogging()) {
            if (PolicyUtils.getSenderType() >= 2) {
                Intent intent = new Intent();
                intent.setPackage(Utils.DMA_PKG_NAME);
                intent.setAction("com.sec.android.diagmonagent.sa.terms.DELETE_APP_DATA");
                intent.putExtra("tid", this.configuration.getTrackingId());
                intent.putExtra("agree", z);
                this.application.sendBroadcast(intent);
                if (PolicyUtils.getSenderType() == 2) {
                    ((DMALogSender) Sender.get(this.application, 2, this.configuration)).reset();
                }
            }
            sendUserAgreementState();
            if (this.configuration.getAuidType() == 1) {
                setDeviceId(generateRandomDeviceId(), 1);
            }
        }
    }

    private void sendPreviousUserAgreementState() {
        final SharedPreferences sharedPreferences = this.application.getSharedPreferences(Preferences.TERMS_PREF_NAME, 0);
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            final String key = entry.getKey();
            SingleThreadExecutor.getInstance().execute(new RegisterTask(this.configuration.getTrackingId(), key, ((Long) entry.getValue()).longValue(), new AsyncTaskCallback() {
                /* class com.samsung.context.sdk.samsunganalytics.internal.Tracker.AnonymousClass4 */

                @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
                public void onFail(int i, String str, String str2, String str3) {
                }

                @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
                public void onSuccess(int i, String str, String str2, String str3) {
                    sharedPreferences.edit().remove(key).apply();
                }
            }));
        }
    }

    private void sendUserAgreementState() {
        sendPreviousUserAgreementState();
        final long currentTimeMillis = System.currentTimeMillis();
        final String deviceId = this.configuration.getDeviceId();
        SingleThreadExecutor.getInstance().execute(new RegisterTask(this.configuration.getTrackingId(), deviceId, currentTimeMillis, new AsyncTaskCallback() {
            /* class com.samsung.context.sdk.samsunganalytics.internal.Tracker.AnonymousClass5 */

            @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
            public void onSuccess(int i, String str, String str2, String str3) {
            }

            @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
            public void onFail(int i, String str, String str2, String str3) {
                Tracker.this.application.getSharedPreferences(Preferences.TERMS_PREF_NAME, 0).edit().putLong(deviceId, currentTimeMillis).apply();
            }
        }));
    }
}
