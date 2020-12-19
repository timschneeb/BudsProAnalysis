package com.samsung.accessory.hearablemgr.common.uhm;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import seccompat.android.util.Log;

public class UhmFwUtil {
    public static final int BT_CONNECTED = 2;
    public static final int BT_DISCONNECTED = 1;
    public static final int BT_UNPAIRED = 0;
    public static final String EXTRA_LAUNCH_DATA_BT_ADDRESS = "bt_addr";
    public static final String EXTRA_LAUNCH_DATA_DEVICE_ADDRESS = "device_address";
    public static final String EXTRA_LAUNCH_DATA_DEVICE_ID = "deviceid";
    public static final String EXTRA_LAUNCH_DATA_DEVICE_NAME = "device_name";
    public static final String EXTRA_LAUNCH_DATA_LAUNCH_MODE = "launch_mode";
    public static final String EXTRA_LAUNCH_DATA_OOBE_MODE = "is_oobe";
    public static final String EXTRA_LAUNCH_DATA_PAIRED_BY_TUHM = "pairedByTUHM";
    public static final String EXTRA_LAUNCH_DATA_TARGET_PAGE = "target_page";
    public static final String EXTRA_LAUNCH_DATA_UHM_VERSION = "device_address";
    public static final int LAST_LAUNCHED_DEVICE = 1;
    public static final int LAUNCHED_AFTER_UPDATE = 1007;
    public static final int LAUNCHED_FROM_BIXBY = 1009;
    public static final int LAUNCHED_FROM_BT_SETTING = 1002;
    public static final int LAUNCHED_FROM_DEVICELIST = 1003;
    public static final int LAUNCHED_FROM_DRAWER = 1006;
    public static final int LAUNCHED_FROM_IDLE = 1001;
    public static final int LAUNCHED_FROM_NFC = 1005;
    public static final int LAUNCHED_FROM_QUICK_PANEL = 1004;
    public static final int LAUNCHED_WITH_CLASS = 1008;
    public static final String PACKAGE_NAME_HOST_MANAGER = "com.samsung.android.hostmanager";
    public static final String PERM_ACCESS_UNIFIED_HOST_MANAGER = "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER";
    private static final String TAG = "Attic_UhmFwUtil";
    public static final String UHM_PACKAGE_NAME = "com.samsung.android.app.watchmanager";
    public static final String UHM_PACKAGE_NAME_RIZE = "com.samsung.android.app.watchmanager2";
    public static final String UHM_SETUP_WIZARD_CLASS_NAME = "com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity";
    static String sUhmPackageName;

    public static String getUhmPackageName() {
        if (sUhmPackageName == null) {
            if (isRizeDevice()) {
                sUhmPackageName = UHM_PACKAGE_NAME_RIZE;
            } else {
                sUhmPackageName = UHM_PACKAGE_NAME;
            }
        }
        Log.d(TAG, "getUhmPackageName() : " + sUhmPackageName);
        return sUhmPackageName;
    }

    private static boolean isRizeDevice() {
        boolean hasSystemFeature = Application.getContext().getPackageManager().hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite");
        Log.d(TAG, "isRizeDevice() : " + hasSystemFeature);
        return hasSystemFeature;
    }

    public static void handlePluginLaunch(Activity activity, String str, String str2, String str3) {
        Log.i(TAG, "handlePluginLaunch() : deviceId=" + str2 + ", deviceName=" + str3);
        Intent intent = new Intent();
        intent.setPackage(getUhmPackageName());
        intent.putExtra("isFromPlugin", true);
        intent.putExtra("deviceid", str2);
        intent.putExtra(EXTRA_LAUNCH_DATA_BT_ADDRESS, str2);
        intent.putExtra("prev_deviceid", str);
        intent.putExtra("DEVICE_MODEL", str3);
        intent.putExtra("switching", true);
        intent.putExtra(EXTRA_LAUNCH_DATA_LAUNCH_MODE, 1006);
        intent.setFlags(65536);
        intent.addFlags(268435456);
        try {
            activity.startActivity(intent);
            activity.finishAffinity();
            Util.removeTaskHistory(activity.getPackageName());
            activity.overridePendingTransition(R.anim.activity_right_to_left_in, R.anim.activity_right_to_left_out);
        } catch (ActivityNotFoundException | NullPointerException e) {
            Log.w(TAG, "handlePluginLaunch() : ", e);
        }
    }

    public static void startNewDeviceActivity(Activity activity, boolean z) {
        Log.i(TAG, "startNewDeviceActivity() : " + z);
        Intent intent = new Intent();
        intent.setPackage(getUhmPackageName());
        intent.putExtra("connstatus", z);
        intent.putExtra("isFromPlugin", true);
        intent.addFlags(268435456);
        try {
            activity.startActivity(intent);
            activity.finishAffinity();
            Util.removeTaskHistory(activity.getPackageName());
        } catch (ActivityNotFoundException unused) {
            Log.e(TAG, "can not find activity, intent[" + intent + "]");
        }
    }

    public static void startManageDevices(Activity activity, boolean z) {
        Log.i(TAG, "startManageDevices() : " + z);
        Intent intent = new Intent();
        intent.setPackage(getUhmPackageName());
        intent.putExtra("connstatus", z);
        intent.putExtra("isFromPlugin", true);
        intent.putExtra("isManageDevice", true);
        intent.addFlags(268435456);
        try {
            activity.startActivity(intent);
            activity.finishAffinity();
            Util.removeTaskHistory(activity.getPackageName());
        } catch (ActivityNotFoundException unused) {
            Log.e(TAG, "can not find activity, intent[" + intent + "]");
        }
    }

    public static void startSetupWizardWelcomeActivity(Activity activity) {
        Log.i(TAG, "startNewWelcomeActivity()");
        Intent intent = new Intent();
        intent.setPackage(getUhmPackageName());
        intent.setFlags(65536);
        try {
            activity.startActivity(intent);
            activity.finishAffinity();
            Util.removeTaskHistory(activity.getPackageName());
        } catch (ActivityNotFoundException unused) {
            Log.e(TAG, "can not find activity, intent[" + intent + "]");
        }
    }

    public static void setLastLaunchDeviceId(String str) {
        if (str == null) {
            Log.e(TAG, "setLastLaunchDeviceId() : address == null");
        } else {
            Preferences.putString(PreferenceKey.LAST_LAUNCH_BT_ADDRESS, str, Preferences.MODE_MANAGER);
        }
    }

    public static String getLastLaunchDeviceId() {
        return Preferences.getString(PreferenceKey.LAST_LAUNCH_BT_ADDRESS, null, Preferences.MODE_MANAGER);
    }

    public static String getSimpleBTName(String str) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf("(");
        return indexOf != -1 ? str.substring(0, indexOf).trim() : str;
    }
}
