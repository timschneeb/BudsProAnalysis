package com.samsung.accessory.hearablemgr.core.notification;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.core.app.NotificationManagerCompat;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.permission.PermissionManager;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.lang.reflect.Method;
import java.util.List;
import seccompat.android.provider.Settings;
import seccompat.android.util.Log;
import seccompat.com.samsung.android.widget.LockPatternUtils;

public class NotificationUtil extends Util {
    public static final int DISABLE_INBANDRING = 0;
    public static final int ENABLE_INBANDRING = 1;
    private static final int IMPORTANCE_DEFAULT = 3;
    private static final String INBAND_RINGTONE = "bluetooth_hfp_ibr";
    private static final String NOTIFICATION_LISTENER = "com.samsung.accessory.atticmgr/com.samsung.accessory.atticmgr.core.notification.NotificationListener";
    private static final String TAG = "Attic_NotificationUtil";

    public static String getAppNotificationDetails(String str) {
        return Preferences.getString(NotificationConstants.PREFERENCE_VN_APP_DETAIL + str, NotificationConstants.NOTIFICATION_TYPE_SUMMARY, UhmFwUtil.getLastLaunchDeviceId());
    }

    public static void setAppNotificationDetails(String str, String str2) {
        Log.d(TAG, "setAppNotificationDetails");
        Preferences.putString(NotificationConstants.PREFERENCE_VN_APP_DETAIL + str, str2, UhmFwUtil.getLastLaunchDeviceId());
    }

    public static boolean isAccessibilityON() {
        boolean z;
        Context context = Application.getContext();
        try {
            ComponentName componentName = new ComponentName(context.getPackageName(), "com.samsung.accessory.atticmgr.core.notification.NotificationListener");
            if (Build.VERSION.SDK_INT >= 27) {
                z = ((NotificationManager) context.getSystemService("notification")).isNotificationListenerAccessGranted(componentName);
                Log.w(TAG, "isAccessibilityON,  NotificationManager - " + z);
            } else {
                String string = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_NOTIFICATION_LISTENERS);
                Log.d(TAG, "NOTIFICATION_LISTENER : com.samsung.accessory.atticmgr/com.samsung.accessory.atticmgr.core.notification.NotificationListener");
                Log.d(TAG, "ENABLED_NOTIFICATION_LISTENERS : " + string);
                z = string != null && string.contains(NOTIFICATION_LISTENER);
            }
            Log.w(TAG, "isAccessibilityON - NotificationManager : " + z);
            return z;
        } catch (Exception e) {
            Log.e(TAG, "isAccessibilityON" + e.toString());
            boolean contains = NotificationManagerCompat.getEnabledListenerPackages(Application.getContext()).contains(Application.getContext().getPackageName());
            Log.d(TAG, "isEnabledListenerPackage : " + contains);
            return contains;
        }
    }

    public static void enableNotificationService(boolean z) {
        Log.d(TAG, "enableNotificationService:: NLS/ACCESSIBILITY");
        if (Build.VERSION.SDK_INT < 27 && Build.VERSION.SDK_INT >= 21) {
            setNotificationListenerService(z);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setNotificationListenerService(boolean r4) {
        /*
        // Method dump skipped, instructions count: 117
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.notification.NotificationUtil.setNotificationListenerService(boolean):void");
    }

    public static boolean isKnoxUserId(int i) {
        for (int i2 = 0; i2 < NotificationConstants.KNOX_USERID.length; i2++) {
            if (NotificationConstants.KNOX_USERID[i2].intValue() == i) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0152 A[Catch:{ Exception -> 0x01bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01a7 A[Catch:{ Exception -> 0x01bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01ba  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isAlarmNeed(android.app.Notification r12, java.lang.String r13, android.service.notification.NotificationListenerService.Ranking r14) {
        /*
        // Method dump skipped, instructions count: 496
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.notification.NotificationUtil.isAlarmNeed(android.app.Notification, java.lang.String, android.service.notification.NotificationListenerService$Ranking):boolean");
    }

    public static boolean isExcludeApp(String str) {
        for (String str2 : NotificationConstants.excludeApps) {
            if (str.equalsIgnoreCase(str2)) {
                Log.d(TAG, "applicationInfo:pkgName() = " + str + " is ExcludeApp | skip");
                return true;
            }
        }
        return false;
    }

    public static boolean checkAllStatus(String str) {
        Log.d(TAG, "checkAllOptions");
        Context context = Application.getContext();
        if (!Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true) || ((Preferences.getBoolean(PreferenceKey.NOTIFICATION_IGNORE_SETTING, true) && isDeviceActive(context, str)) || !isAppNotificationEnabled(str) || !PermissionManager.isBasicPermissionGranted(context, PermissionManager.ALL_PERMISSION_LIST))) {
            return false;
        }
        Log.d(TAG, "this notification need to be relayed");
        return true;
    }

    public static boolean isDeviceActive(Context context, String str) {
        if (isCarMode(context)) {
            return true;
        }
        if (!NotificationConstants.ALARM_PACKAGENAME.equals(str) && !NotificationConstants.INCOMING_CALL_PACKAGENAME.equals(str)) {
            boolean isCoverOpen = Application.getNotificationCoreService().getIsCoverOpen();
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            Log.d(TAG, "isDeviceActive()-isKeyguardLocked:" + keyguardManager.isKeyguardLocked() + "_isScreenOn:" + powerManager.isInteractive() + "_isCoverOpen:" + isCoverOpen);
            if (!isCoverOpen || !powerManager.isInteractive() || keyguardManager.isKeyguardLocked()) {
                Log.d(TAG, "INACTIVE");
            } else {
                Log.d(TAG, "DEVICE IS AWAKE AND UNLOCKED AND UNCOVER :: ACTIVE");
                return true;
            }
        }
        return false;
    }

    public static boolean isCarMode(Context context) {
        int i;
        int i2;
        if (Build.VERSION.SDK_INT < 23) {
            i2 = Settings.System.getInt(context.getContentResolver(), "drive_link_setting", 0);
            i = Settings.System.getInt(context.getContentResolver(), "drive_link_multi_setting", 0);
        } else {
            i2 = Settings.Secure.getInt(context.getContentResolver(), "drive_link_setting", 0);
            i = Settings.Secure.getInt(context.getContentResolver(), "drive_link_multi_setting", 0);
        }
        Log.d(TAG, "isCarMode = " + i2);
        Log.d(TAG, "misCarMode = " + i);
        if (!isRunningProcess(context, "com.sec.android.automotive.drivelink")) {
            return false;
        }
        Log.d(TAG, "PACKAGE_DRIVELINK_KK is running!");
        if (i2 != 1 && i != 1) {
            return false;
        }
        Log.d(TAG, "is Car MODE!");
        return true;
    }

    public static boolean isRunningProcess(Context context, String str) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setNotiEnabledApplication(String str, String str2) {
        Log.d(TAG, "setEnabledApplication::" + str + "  value = " + str2);
        StringBuilder sb = new StringBuilder();
        sb.append(NotificationConstants.PREFERENCE_VN_APP_ENABLE);
        sb.append(str);
        Preferences.putString(sb.toString(), str2, UhmFwUtil.getLastLaunchDeviceId());
    }

    public static boolean isAppNotificationEnabled(String str) {
        boolean equals = Preferences.getString(NotificationConstants.PREFERENCE_VN_APP_ENABLE + str, NotificationConstants.NOTIFICATION_TYPE_OFF, UhmFwUtil.getLastLaunchDeviceId()).equals(NotificationConstants.NOTIFICATION_TYPE_ON);
        if (!isSupportSpeakCallerName() || !str.equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
            Log.d(TAG, "isAppNotificationEnabled:: " + str + " is " + equals);
            return equals;
        }
        boolean speakCallerName = getSpeakCallerName();
        Log.d(TAG, "isAppNotificationEnabled:: " + str + " is " + speakCallerName);
        return speakCallerName;
    }

    public static boolean isSupportSpeakCallerName() {
        int i = Settings.Global.getInt(Application.getContext().getContentResolver(), "call_read_caller_id", -1);
        boolean z = true;
        if ((Build.VERSION.SDK_INT < 28 || i != 0) && i != 1) {
            z = false;
        }
        Log.d(TAG, "isSupportSpeakCallerName : " + z);
        return z;
    }

    public static boolean getSpeakCallerName() {
        return Settings.Global.getInt(Application.getContext().getContentResolver(), "call_read_caller_id", -1) == 1;
    }

    public static void setSpeakCallerName(int i) {
        if (isSupportSpeakCallerName()) {
            Settings.Global.putInt(Application.getContext().getContentResolver(), "call_read_caller_id", i);
        }
    }

    public static boolean isInstalledPackage(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 128);
            Log.d(TAG, "installed : " + str);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d(TAG, "not installed : " + str);
            return false;
        }
    }

    public static boolean suppressAlertingDueToGrouping(Notification notification) {
        if (Build.VERSION.SDK_INT < 26 || notification == null) {
            return false;
        }
        if (notification.getGroup() != null && (notification.flags & 512) != 0 && notification.getGroupAlertBehavior() == 2) {
            return true;
        }
        if (notification.getGroup() != null && (notification.flags & 512) == 0 && notification.getGroupAlertBehavior() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isSupportInbandringtone() {
        Log.d(TAG, "isSupportInbandringtone()");
        if (!Util.isSamsungDevice()) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            BluetoothHeadset headsetProxy = Application.getBluetoothManager().getHeadsetProxy();
            if (headsetProxy == null || seccompat.android.bluetooth.BluetoothHeadset.proxyGetFeatureSettings(headsetProxy, seccompat.android.bluetooth.BluetoothHeadset.proxyGetFeatureIdSupportIBR()) != 1) {
                return false;
            }
            return true;
        } else if (Settings.System.getInt(Application.getContext().getContentResolver(), INBAND_RINGTONE, 0) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean getInBandRingtone() {
        Log.d(TAG, "getInBandRingtone()");
        if (!isSupportInbandringtone()) {
            Log.d(TAG, "INBAND_RINGTONE not supported");
            return false;
        } else if (Build.VERSION.SDK_INT >= 23) {
            BluetoothHeadset headsetProxy = Application.getBluetoothManager().getHeadsetProxy();
            BluetoothDevice bondedDevice = BluetoothUtil.getBondedDevice(UhmFwUtil.getLastLaunchDeviceId());
            if (headsetProxy == null || bondedDevice == null || 1 != seccompat.android.bluetooth.BluetoothHeadset.proxyGetHeadsetSettings(headsetProxy, bondedDevice, seccompat.android.bluetooth.BluetoothHeadset.proxyGetSettingIdApplyedIBR())) {
                return false;
            }
            return true;
        } else {
            String lastLaunchDeviceId = UhmFwUtil.getLastLaunchDeviceId();
            if (lastLaunchDeviceId == null || lastLaunchDeviceId.equals("")) {
                Log.d(TAG, "INBAND_RINGTONE not supported - no bt addr");
                return false;
            }
            ContentResolver contentResolver = Application.getContext().getContentResolver();
            if (1 == Settings.System.getInt(contentResolver, INBAND_RINGTONE + lastLaunchDeviceId, 0)) {
                return true;
            }
            return false;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    public static void setInBandRingtone(boolean z) {
        Log.d(TAG, "setInBandRingtone() : " + z);
        if (!isSupportInbandringtone()) {
            Log.d(TAG, "INBAND_RINGTONE not supported");
        } else if (Build.VERSION.SDK_INT >= 23) {
            BluetoothHeadset headsetProxy = Application.getBluetoothManager().getHeadsetProxy();
            BluetoothDevice bondedDevice = BluetoothUtil.getBondedDevice(UhmFwUtil.getLastLaunchDeviceId());
            if (headsetProxy != null && bondedDevice != null) {
                seccompat.android.bluetooth.BluetoothHeadset.proxySetHeadsetSettings(headsetProxy, bondedDevice, seccompat.android.bluetooth.BluetoothHeadset.proxyGetSettingIdApplyedIBR(), z);
            }
        } else {
            String lastLaunchDeviceId = UhmFwUtil.getLastLaunchDeviceId();
            if (lastLaunchDeviceId == null || lastLaunchDeviceId.equals("")) {
                Log.d(TAG, "INBAND_RINGTONE not supported - no bt addr");
                return;
            }
            ContentResolver contentResolver = Application.getContext().getContentResolver();
            Settings.System.putInt(contentResolver, INBAND_RINGTONE + lastLaunchDeviceId, z ? 1 : 0);
        }
    }

    public static boolean isActiveDevice() {
        BluetoothDevice proxySemGetActiveStreamDevice;
        BluetoothA2dp a2dpProxy = Application.getBluetoothManager().getA2dpProxy();
        BluetoothDevice connectedDevice = Application.getCoreService().getConnectedDevice();
        if (Application.getBluetoothManager().getA2dpState(connectedDevice) == 0) {
            return false;
        }
        if (a2dpProxy == null || connectedDevice == null || (proxySemGetActiveStreamDevice = seccompat.android.bluetooth.BluetoothA2dp.proxySemGetActiveStreamDevice(a2dpProxy)) == null) {
            return true;
        }
        return proxySemGetActiveStreamDevice.equals(connectedDevice);
    }

    public static void initSettingDefaultApps() {
        Context context = Application.getContext();
        if (isSupportInbandringtone()) {
            setInBandRingtone(true);
        }
        if (!isInstalledPackage(context, "com.sec.android.app.clockpackage") && isInstalledPackage(context, "com.sec.android.app.clockpackagechina")) {
            NotificationConstants.ALARM_PACKAGENAME = "com.sec.android.app.clockpackagechina";
            NotificationConstants.mAlertApps[2] = "com.sec.android.app.clockpackagechina";
        }
        if (isInstalledPackage(context, "com.samsung.android.calendar")) {
            NotificationConstants.CALENDAR_PACKAGENAME = "com.samsung.android.calendar";
            NotificationConstants.mAlertApps[3] = "com.samsung.android.calendar";
        }
        if (isInstalledPackage(context, "com.samsung.android.messaging")) {
            NotificationConstants.MESSAGE_PACKAGENAME = "com.samsung.android.messaging";
            NotificationConstants.mAlertApps[4] = "com.samsung.android.messaging";
        }
        if (isInstalledPackage(context, "com.samsung.android.email.provider")) {
            NotificationConstants.EMAIL_PACKAGENAME = "com.samsung.android.email.provider";
            NotificationConstants.mAlertApps[5] = "com.samsung.android.email.provider";
        }
        if (isInstalledPackage(context, "com.samsung.android.email.ui")) {
            NotificationConstants.EMAIL_PACKAGENAME = "com.samsung.android.email.ui";
            NotificationConstants.mAlertApps[5] = "com.samsung.android.email.ui";
        }
        for (int i = 0; i < NotificationConstants.mAlertApps.length; i++) {
            if (i == 0) {
                if (Preferences.getString(NotificationConstants.PREFERENCE_VN_APP_DETAIL + NotificationConstants.mAlertApps[i], null, UhmFwUtil.getLastLaunchDeviceId()) == null) {
                    setAppNotificationDetails(NotificationConstants.mAlertApps[i], NotificationConstants.NOTIFICATION_TYPE_DETAIL);
                }
            }
            if (Preferences.getString(NotificationConstants.PREFERENCE_VN_APP_ENABLE + NotificationConstants.mAlertApps[i], null, UhmFwUtil.getLastLaunchDeviceId()) == null) {
                setNotiEnabledApplication(NotificationConstants.mAlertApps[i], NotificationConstants.NOTIFICATION_TYPE_ON);
            }
        }
        if (isLockNone()) {
            Preferences.putBoolean(PreferenceKey.NOTIFICATION_IGNORE_SETTING, false);
        }
        Preferences.putString(PreferenceKey.NOTIFICATION_LOCALE, context.getResources().getConfiguration().locale.toString());
    }

    public static boolean semAreNotificationsEnabledForPackage(String str, boolean z, int i) {
        int i2;
        boolean z2 = true;
        if (!str.equals(NotificationConstants.MISSED_CALL_PACKAGENAME) && !str.equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
            Context context = Application.getContext();
            try {
                if (Util.isSamsungDevice() && Build.VERSION.SDK_INT > 28) {
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                    Method method = Class.forName(notificationManager.getClass().getName()).getMethod("semAreNotificationsEnabledForPackage", String.class, Integer.TYPE);
                    if (z) {
                        str = str.substring(0, str.indexOf(NotificationConstants.DUAL));
                        i2 = getUidForOtherUser(str, i);
                    } else {
                        i2 = context.getPackageManager().getPackageUid(str, 0);
                    }
                    z2 = ((Boolean) method.invoke(notificationManager, str, Integer.valueOf(i2))).booleanValue();
                }
            } catch (Exception e) {
                Log.e(TAG, "semAreNotificationsEnabledForPackage " + e.getMessage());
                e.printStackTrace();
            }
            Log.d(TAG, "semAreNotificationsEnabledForPackage : " + z2);
        }
        return z2;
    }

    public static int getUidForOtherUser(String str, int i) {
        if (!Util.isSamsungDevice() || Build.VERSION.SDK_INT < 21) {
            return 0;
        }
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        List<ResolveInfo> proxyQueryIntentActivitiesAsUser = seccompat.android.content.pm.PackageManager.proxyQueryIntentActivitiesAsUser(Application.getContext().getPackageManager(), intent, 0, i);
        if (proxyQueryIntentActivitiesAsUser == null || proxyQueryIntentActivitiesAsUser.size() <= 0) {
            return 0;
        }
        return proxyQueryIntentActivitiesAsUser.get(0).activityInfo.applicationInfo.uid;
    }

    public static boolean isLockNone() {
        boolean isLockScreenDisabled = Util.isSamsungDevice() ? LockPatternUtils.isLockScreenDisabled() : false;
        Log.d(TAG, "isLockNone : " + isLockScreenDisabled);
        return isLockScreenDisabled;
    }

    public static void setPreIncomingCallStatus(boolean z) {
        if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true) != z) {
            Log.d(TAG, "setPreIncomingCallStatus : " + z);
            if (!z) {
                Preferences.putBoolean(PreferenceKey.NOTIFICATION_PRE_INCOMUNGCALL_STATUS, Boolean.valueOf(isAppNotificationEnabled(NotificationConstants.INCOMING_CALL_PACKAGENAME)));
                setSpeakCallerName(0);
            } else if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_PRE_INCOMUNGCALL_STATUS, true)) {
                setSpeakCallerName(1);
                NotificationManager.getInstance(Application.getContext()).setCheckIncomingCallStatus(true);
            }
        }
    }
}
