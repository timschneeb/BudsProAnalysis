package com.accessorydm.adapter;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.agent.XDMDebug;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.ui.XUIAdapter;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.GeneralUtil;
import com.samsung.android.fotaprovider.util.NetworkUtil;
import java.util.List;

public class XDMCommonUtils implements XDMDefInterface {
    private static final String XDM_DM_DEBUG_BOOLEAN_KEY = "runing";
    private static final String XDM_DM_DEBUG_INT_KEY = "index";
    private static final String XDM_DM_DEBUG_SHARED_PREF = "flag";
    private static final String XDM_DM_SETTING_ROAMING_KEY = "RoamingValue";
    private static final String XDM_DM_SETTING_SHARED_PREF = "PrefDmSetting";
    private static final String XDM_DM_SETTING_SSL_KEY = "SSLCheckValue";
    private static final String XDM_DM_SETTING_VALIDATION_KEY = "ValidationValue";

    public static String xdmGetUsingBearer() {
        String str;
        try {
            if (!NetworkUtil.isWiFiNetworkConnected(FotaProviderInitializer.getContext())) {
                int xdmGetNetworkType = xdmGetNetworkType();
                Log.I("xdmGetUsingBearer : " + xdmGetNetworkType);
                switch (xdmGetNetworkType) {
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        str = "3G";
                        break;
                    case 4:
                    case 7:
                    case 11:
                    default:
                        str = "";
                        break;
                    case 13:
                        str = XDMDefInterface.XDM_NETWORK_CONNECT_TYPE_LTE;
                        break;
                }
            } else {
                str = XDMDefInterface.XDM_NETWORK_CONNECT_TYPE_WIFI;
            }
            Log.H("szBearer : " + str);
            return str;
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    public static void xdmSetRoamingPrefValue(int i) {
        SharedPreferences.Editor edit = FotaProviderInitializer.getContext().getSharedPreferences(XDM_DM_SETTING_SHARED_PREF, 0).edit();
        edit.putInt(XDM_DM_SETTING_ROAMING_KEY, i);
        edit.apply();
        Log.I("" + i);
    }

    public static int xdmGetRoamingPrefValue() {
        int i = FotaProviderInitializer.getContext().getSharedPreferences(XDM_DM_SETTING_SHARED_PREF, 0).getInt(XDM_DM_SETTING_ROAMING_KEY, 0);
        Log.I("" + i);
        return i;
    }

    public static void xdmSetSSLPrefValue(int i) {
        SharedPreferences.Editor edit = FotaProviderInitializer.getContext().getSharedPreferences(XDM_DM_SETTING_SHARED_PREF, 0).edit();
        edit.putInt(XDM_DM_SETTING_SSL_KEY, i);
        edit.apply();
        Log.I("" + i);
    }

    public static int xdmGetSSLPrefValue() {
        int i = FotaProviderInitializer.getContext().getSharedPreferences(XDM_DM_SETTING_SHARED_PREF, 0).getInt(XDM_DM_SETTING_SSL_KEY, 0);
        Log.I("" + i);
        return i;
    }

    public static void xdmSetValidationPrefValue(int i) {
        SharedPreferences.Editor edit = FotaProviderInitializer.getContext().getSharedPreferences(XDM_DM_SETTING_SHARED_PREF, 0).edit();
        edit.putInt(XDM_DM_SETTING_VALIDATION_KEY, i);
        edit.apply();
        Log.I("" + i);
    }

    public static int xdmGetValidationPrefValue() {
        int i = FotaProviderInitializer.getContext().getSharedPreferences(XDM_DM_SETTING_SHARED_PREF, 0).getInt(XDM_DM_SETTING_VALIDATION_KEY, 0);
        Log.I("" + i);
        return i;
    }

    public static void xdmSavelogflag() {
        SharedPreferences.Editor edit = FotaProviderInitializer.getContext().getSharedPreferences(XDM_DM_DEBUG_SHARED_PREF, 0).edit();
        edit.putInt(XDM_DM_DEBUG_INT_KEY, XDMDebug.curFileIndex);
        edit.putBoolean(XDM_DM_DEBUG_BOOLEAN_KEY, XDMDebug.bSessionRuning);
        edit.apply();
    }

    public static void xdmLoadlogflag() {
        SharedPreferences sharedPreferences = FotaProviderInitializer.getContext().getSharedPreferences(XDM_DM_DEBUG_SHARED_PREF, 0);
        XDMDebug.curFileIndex = sharedPreferences.getInt(XDM_DM_DEBUG_INT_KEY, 1);
        XDMDebug.bSessionRuning = sharedPreferences.getBoolean(XDM_DM_DEBUG_BOOLEAN_KEY, false);
    }

    private static int xdmGetNetworkType() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) XDMDmUtils.getInstance().xdmGetServiceManager(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE);
            if (telephonyManager != null) {
                return telephonyManager.getNetworkType();
            }
            return 0;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static String xdmGetTopActivityName() {
        String str = "NoActivity";
        try {
            ActivityManager activityManager = (ActivityManager) XDMDmUtils.getInstance().xdmGetServiceManager("activity");
            if (activityManager == null) {
                Log.E("activityManager is null!!");
                return str;
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks == null) {
                Log.E("runningTasks is null!!");
                return str;
            }
            ComponentName componentName = runningTasks.get(0).topActivity;
            if (componentName == null) {
                Log.E("componentName is null!!");
                return str;
            }
            str = componentName.getClassName();
            Log.I("TopActivity : " + str);
            return str;
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static boolean xdmServiceRunningCheck() {
        ActivityManager activityManager = (ActivityManager) XDMDmUtils.getInstance().xdmGetServiceManager("activity");
        if (activityManager == null) {
            Log.I("activityManager is null!!");
            return false;
        }
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);
            Log.I("xdmServiceRunningCheck size : " + runningServices.size());
            if (runningServices.size() <= 0) {
                return false;
            }
            for (int i = 0; i < runningServices.size(); i++) {
                if (!"com.accessorydm.service.XDMService".equals(runningServices.get(i).service.getClassName())) {
                    if (!"com.accessorydm.service.XDMJobService".equals(runningServices.get(i).service.getClassName())) {
                    }
                }
                Log.I("Service is Running");
                return true;
            }
            Log.I("Service not yet");
            return false;
        } catch (Exception e) {
            Log.E("Exception : " + e.toString());
            return false;
        }
    }

    public static boolean xdmCheckIdleScreen() {
        if (XUIAdapter.xuiAdpIsUserClick()) {
            return true;
        }
        return GeneralUtil.isIdleScreen();
    }
}
