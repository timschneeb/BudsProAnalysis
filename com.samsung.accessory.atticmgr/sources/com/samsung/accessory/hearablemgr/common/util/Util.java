package com.samsung.accessory.hearablemgr.common.util;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import com.accessorydm.interfaces.XDMAccessoryInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.context.sdk.samsunganalytics.LogBuilders;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import seccompat.SecCompatUtil;
import seccompat.android.os.SystemProperties;
import seccompat.android.util.Log;
import seccompat.com.samsung.android.feature.FloatingFeature;

public class Util {
    public static final String BIXBY = "com.samsung.android.bixby.agent";
    public static final String MCC_OF_JAPAN = "440";
    public static final String SEND_PUI_EVENT = "com.samsung.accessory.earbuds.action.SEND_PUI_EVENT";
    public static final String SPOTIFY = "com.spotify.music";
    public static final String SYSTEM_PROPERTY_COUNTRY_ISO_CODE = "ro.csc.countryiso_code";
    private static final String TAG = "Attic_Util";
    public static String sBuildCharacteristics;
    private static KeyguardManager sKeyguardManager;
    private static PowerManager sPowerManager;

    public static boolean equalsIgnoreCase(String str, String str2) {
        if ((str == null && str2 == null) || str == null) {
            return false;
        }
        return str.equalsIgnoreCase(str2);
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isSamsungDevice() {
        if (isSamsungGEDModel()) {
            return false;
        }
        return Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG");
    }

    public static boolean isSamsungGEDModel() {
        String str = Build.MODEL;
        if (str == null) {
            return false;
        }
        if (!str.contains("SM-G900FG") && !str.contains("GT-I9505G")) {
            return false;
        }
        Log.d(TAG, "SamsungGEDModel : " + str);
        return true;
    }

    public static int getSDKVer() {
        try {
            return Build.VERSION.SDK_INT;
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public static String getSalesCode() {
        return SystemProperties.get("ro.csc.sales_code");
    }

    public static boolean isChinaModel() {
        String salesCode = getSalesCode();
        Log.d(TAG, "Sales code = " + salesCode);
        return salesCode.equalsIgnoreCase("CHN") || salesCode.equalsIgnoreCase("CHM") || salesCode.equalsIgnoreCase("CHU") || salesCode.equalsIgnoreCase("CTC") || salesCode.equalsIgnoreCase("CHC") || salesCode.equalsIgnoreCase("CHZ");
    }

    public static boolean isJapanModel() {
        String salesCode = getSalesCode();
        Log.d(TAG, "Sales code = " + salesCode);
        return MCC_OF_JAPAN.equals(getMcc()) || salesCode.equalsIgnoreCase("DCM") || salesCode.equalsIgnoreCase("KDI") || salesCode.equalsIgnoreCase("KDDI") || salesCode.equalsIgnoreCase("SBM") || salesCode.equalsIgnoreCase("UQM") || salesCode.equalsIgnoreCase("JCO") || salesCode.equalsIgnoreCase("RKT") || salesCode.equalsIgnoreCase("XJP");
    }

    public static boolean isChina() {
        return "460".equals(getMcc());
    }

    public static String getMcc() {
        String simOperator = ((TelephonyManager) Application.getContext().getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE)).getSimOperator();
        return (simOperator == null || simOperator.length() <= 3) ? "" : simOperator.substring(0, 3);
    }

    public static String getMnc() {
        String simOperator = ((TelephonyManager) Application.getContext().getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE)).getSimOperator();
        return (simOperator == null || simOperator.length() <= 3) ? "" : simOperator.substring(3);
    }

    public static void sendPermissionBroadcast(Context context, Intent intent) {
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent, "com.samsung.accessory.atticmgr.permission.SIGNATURE");
    }

    public static boolean isTalkBackEnabled() {
        String string = Settings.Secure.getString(Application.getContext().getContentResolver(), "enabled_accessibility_services");
        boolean z = false;
        if (string != null && (string.matches("(?i).*com.samsung.android.app.talkback.TalkBackService.*") || string.matches("(?i).*com.google.android.marvin.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.android.marvin.talkback.TalkBackService.*"))) {
            z = true;
        }
        Log.d(TAG, "isTalkBackEnabled: " + z);
        return z;
    }

    public static int getActiveNetworkInfo() {
        int type;
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) Application.getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || (type = activeNetworkInfo.getType()) < 0 || type > 1) {
            Log.d(TAG, "getActiveNetworkInfo :: none");
            return -1;
        }
        Log.d(TAG, "getActiveNetworkInfo :: " + type);
        return type;
    }

    public static boolean isAppOnForeground() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) Application.getContext().getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        String packageName = Application.getContext().getPackageName();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSystemLayoutDirectionRtl() {
        return Application.getContext().getResources().getConfiguration().getLayoutDirection() == 1;
    }

    public static boolean isEmulator() {
        Log.d(TAG, "isEmulator() : " + Application.EMULATOR_MODE);
        return Application.EMULATOR_MODE;
    }

    public static boolean isCalling() {
        return isCPCall() || isScoConnected();
    }

    public static boolean isCPCall() {
        return ((TelephonyManager) Application.getContext().getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE)).getCallState() != 0;
    }

    public static boolean isScoConnected() {
        return isScoConnected(UhmFwUtil.getLastLaunchDeviceId());
    }

    public static boolean isScoConnected(String str) {
        BluetoothHeadset headsetProxy = Application.getBluetoothManager().getHeadsetProxy();
        BluetoothDevice bondedDevice = BluetoothUtil.getBondedDevice(str);
        return (headsetProxy == null || bondedDevice == null || !headsetProxy.isAudioConnected(bondedDevice)) ? false : true;
    }

    public static String objectToString(Object obj) {
        String property = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(obj.getClass().getSimpleName());
        sb.append(" {");
        sb.append(property);
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            sb.append("    ");
            try {
                sb.append(field.getName());
                sb.append(": ");
                sb.append(BluetoothUtil.autoPrivateAddress(field.get(obj)));
            } catch (IllegalAccessException e) {
                Log.w(TAG, "objectToString() : IllegalAccessException e : " + e);
            }
            sb.append(property);
        }
        sb.append("}");
        return sb.toString();
    }

    public static boolean isTablet() {
        if (sBuildCharacteristics == null) {
            sBuildCharacteristics = SystemProperties.get("ro.build.characteristics");
        }
        String str = sBuildCharacteristics;
        boolean z = str != null && str.contains("tablet");
        Log.d(TAG, "isTablet() : " + z);
        return z;
    }

    public static boolean isInstalledPackage(String str) {
        try {
            Application.getContext().getPackageManager().getPackageInfo(str, 128);
            Log.i(TAG, "installed : " + str);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.i(TAG, "not installed : " + str);
            return false;
        }
    }

    public static boolean isFloatingFeatureForPowerSharing() {
        if (!isSamsungDevice() || !FloatingFeature.getInstance().getEnableStatus("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_TX")) {
            Log.d(TAG, "POWER SHARING Feature is not exist");
            return false;
        }
        Log.d(TAG, "POWER SHARING Feature is exist");
        return true;
    }

    public static boolean isInKeyguard() {
        if (sKeyguardManager == null) {
            sKeyguardManager = (KeyguardManager) Application.getContext().getSystemService("keyguard");
        }
        boolean inKeyguardRestrictedInputMode = sKeyguardManager.inKeyguardRestrictedInputMode();
        Log.d(TAG, "isInKeyguard() : " + inKeyguardRestrictedInputMode);
        return inKeyguardRestrictedInputMode;
    }

    public static boolean isScreenOn() {
        if (sPowerManager == null) {
            sPowerManager = (PowerManager) Application.getContext().getSystemService("power");
        }
        boolean isInteractive = sPowerManager.isInteractive();
        Log.d(TAG, "isScreenOn() : " + isInteractive);
        return isInteractive;
    }

    public static boolean isInUserUse() {
        boolean z = isScreenOn() && !isInKeyguard();
        Log.d(TAG, "isInUserUse() : " + z);
        return z;
    }

    public static boolean hasEmoji(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            char charAt = charSequence.charAt(i);
            if (!((charAt >= 55296 && charAt <= 56319) || charAt == 9728 || charAt == 9729 || charAt == 9748 || charAt == 9749 || charAt == 9829 || charAt == 9830 || charAt == 9888 || charAt == 9889 || charAt == 9898 || charAt == 9899 || charAt == 9917 || charAt == 9918)) {
                switch (charAt) {
                    case 8265:
                    case 8505:
                    case 8618:
                    case 8987:
                    case 9200:
                    case 9203:
                    case 9410:
                    case 9643:
                    case 9654:
                    case 9664:
                    case 9745:
                    case 9757:
                    case 9786:
                    case 9800:
                    case 9801:
                    case 9802:
                    case 9803:
                    case 9804:
                    case 9805:
                    case 9806:
                    case 9807:
                    case 9808:
                    case 9809:
                    case 9810:
                    case 9811:
                    case 9824:
                    case 9827:
                    case 9832:
                    case 9851:
                    case 9855:
                    case 9924:
                    case 9925:
                    case 9934:
                    case 9940:
                    case 9971:
                    case 9973:
                    case 9978:
                    case 9981:
                    case 9986:
                    case 9989:
                    case 9992:
                    case 9994:
                    case 9995:
                    case 9996:
                    case 10002:
                    case 10004:
                    case 10006:
                    case 10024:
                    case 10035:
                    case 10036:
                    case 10052:
                    case 10055:
                    case 10060:
                    case 10062:
                    case 10067:
                    case 10068:
                    case 10069:
                    case 10071:
                    case 10084:
                    case 10133:
                    case 10134:
                    case 10135:
                    case 10160:
                    case 10175:
                    case 10548:
                    case 10549:
                    case 11013:
                    case 11014:
                    case 11015:
                    case 11035:
                    case 11036:
                    case 11088:
                    case 11093:
                    case 12336:
                    case 12349:
                    case 12951:
                    case 12953:
                        break;
                    default:
                        switch (charAt) {
                            case 8596:
                            case 8597:
                            case 8598:
                            case 8599:
                            case 8600:
                            case 8601:
                            case 8602:
                                break;
                            default:
                                switch (charAt) {
                                    case 9193:
                                    case 9194:
                                    case 9195:
                                    case 9196:
                                        break;
                                    default:
                                        switch (charAt) {
                                            case 9723:
                                            case 9724:
                                            case 9725:
                                            case 9726:
                                                break;
                                            default:
                                        }
                                }
                        }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isBixbyDefaultVoiceCommandAgent() {
        boolean z = false;
        ResolveInfo resolveActivity = Application.getContext().getPackageManager().resolveActivity(new Intent("android.intent.action.VOICE_COMMAND"), 0);
        if (resolveActivity != null && resolveActivity.activityInfo.packageName.equals("com.samsung.android.bixby.agent")) {
            z = true;
        }
        Log.d(TAG, "isBixbyDefaultVoiceCommandAgent() : " + z);
        return z;
    }

    public static void removeTaskHistory(String str) {
        Log.d(TAG, "removeTaskHistory() : " + str);
        try {
            if (!TextUtils.isEmpty(str)) {
                ActivityManager activityManager = (ActivityManager) Application.getContext().getSystemService("activity");
                if (activityManager != null) {
                    List<ActivityManager.RecentTaskInfo> recentTasks = activityManager.getRecentTasks(Integer.MAX_VALUE, 2);
                    if (recentTasks != null) {
                        for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
                            if (str.equals(recentTaskInfo.baseIntent.getComponent().getPackageName())) {
                                Log.d(TAG, "proxyRemoveTask() : " + str);
                                seccompat.android.app.ActivityManager.proxyRemoveTask(activityManager, recentTaskInfo.persistentId, 0);
                                return;
                            }
                        }
                        return;
                    }
                    throw new Exception("tasks == null");
                }
                throw new Exception("activityManager == null");
            }
            throw new Exception("pkgName is empty");
        } catch (Error | Exception e) {
            Log.e(TAG, "removeTaskHistory() : Exception : " + e);
        }
    }

    public static String getAction(Intent intent) {
        if (intent == null) {
            return "null";
        }
        String action = intent.getAction();
        return action != null ? action : "null";
    }

    public static String toSimpleString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
    }

    public static String getSimCountryIso() {
        String simCountryIso = ((TelephonyManager) Application.getContext().getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE)).getSimCountryIso();
        Log.d(TAG, "getSimCountryIso() : " + simCountryIso);
        return simCountryIso;
    }

    public static String getNetworkCountryIso() {
        String networkCountryIso = ((TelephonyManager) Application.getContext().getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE)).getNetworkCountryIso();
        Log.d(TAG, "getNetworkCountryIso() : " + networkCountryIso);
        return networkCountryIso;
    }

    public static String getCscCountryIso() {
        String str = SystemProperties.get(SYSTEM_PROPERTY_COUNTRY_ISO_CODE);
        Log.d(TAG, "getCscCountryIso() : " + str);
        return str;
    }

    public static String getCountryIso() {
        String simCountryIso = getSimCountryIso();
        if (TextUtils.isEmpty(simCountryIso)) {
            simCountryIso = getNetworkCountryIso();
        }
        if (TextUtils.isEmpty(simCountryIso)) {
            simCountryIso = getCscCountryIso();
        }
        if (TextUtils.isEmpty(simCountryIso)) {
            simCountryIso = getLocale().getCountry();
        }
        Log.d(TAG, "getCountryIso() : " + simCountryIso);
        return simCountryIso;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isGDPRCountry(String str) {
        char c;
        boolean z = true;
        if (str != null) {
            String lowerCase = str.toLowerCase();
            switch (lowerCase.hashCode()) {
                case 3115:
                    if (lowerCase.equals(LogBuilders.Property.APP_LANGUAGE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3123:
                    if (lowerCase.equals("at")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3139:
                    if (lowerCase.equals("be")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3141:
                    if (lowerCase.equals("bg")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 3173:
                    if (lowerCase.equals("ch")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 3191:
                    if (lowerCase.equals("cz")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 3201:
                    if (lowerCase.equals("de")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 3207:
                    if (lowerCase.equals("dk")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 3232:
                    if (lowerCase.equals("ee")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 3246:
                    if (lowerCase.equals("es")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 3267:
                    if (lowerCase.equals("fi")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 3276:
                    if (lowerCase.equals("fr")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 3291:
                    if (lowerCase.equals(XDMAccessoryInterface.XDM_ACCESSORY_INFO_COUNTRY_DEFAULT)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 3307:
                    if (lowerCase.equals("gr")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 3338:
                    if (lowerCase.equals("hr")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 3341:
                    if (lowerCase.equals("hu")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 3356:
                    if (lowerCase.equals("ie")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 3371:
                    if (lowerCase.equals("it")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 3464:
                    if (lowerCase.equals("lt")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 3465:
                    if (lowerCase.equals("lu")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 3466:
                    if (lowerCase.equals("lv")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 3495:
                    if (lowerCase.equals("mt")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 3518:
                    if (lowerCase.equals("nl")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 3521:
                    if (lowerCase.equals("no")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 3580:
                    if (lowerCase.equals("pl")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 3588:
                    if (lowerCase.equals("pt")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 3645:
                    if (lowerCase.equals("ro")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case 3666:
                    if (lowerCase.equals("se")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 3670:
                    if (lowerCase.equals("si")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case 3672:
                    if (lowerCase.equals("sk")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
            }
            Log.d(TAG, "isGDPRCountry() : " + z + " (" + str + ")");
            return z;
        }
        z = false;
        Log.d(TAG, "isGDPRCountry() : " + z + " (" + str + ")");
        return z;
    }

    public static boolean isLGPDCountry(String str) {
        boolean z = false;
        if (str != null) {
            String lowerCase = str.toLowerCase();
            char c = 65535;
            if (lowerCase.hashCode() == 3152 && lowerCase.equals("br")) {
                c = 0;
            }
            if (c == 0) {
                z = true;
            }
        }
        Log.d(TAG, "isLGPDCountry() : " + z + " (" + str + ")");
        return z;
    }

    public static boolean isUSA(String str) {
        boolean equalsIgnoreCase = "us".equalsIgnoreCase(str);
        Log.d(TAG, "isUSA() : " + equalsIgnoreCase);
        return equalsIgnoreCase;
    }

    public static Locale getLocale() {
        Configuration configuration = Application.getContext().getResources().getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? configuration.getLocales().get(0) : configuration.locale;
    }

    public static void performHapticFeedback(View view, int i) {
        if (SecCompatUtil.isSEPDevice()) {
            try {
                if (Build.VERSION.SEM_INT >= 2801) {
                    view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(i));
                } else {
                    view.performHapticFeedback(50025);
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }
}
