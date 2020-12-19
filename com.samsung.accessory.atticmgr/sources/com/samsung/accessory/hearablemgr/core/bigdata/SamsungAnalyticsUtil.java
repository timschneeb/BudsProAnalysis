package com.samsung.accessory.hearablemgr.core.bigdata;

import android.content.SharedPreferences;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.LogBuilders;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import com.sec.android.diagmonagent.log.provider.DiagMonSDK;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import seccompat.android.util.Log;

public class SamsungAnalyticsUtil {
    static final String PKG_NAME_FLO = "skplanet.musicmate";
    static final String PKG_NAME_GEINE = "com.ktmusic.geniemusic";
    static final String PKG_NAME_MELON = "com.iloen.melon";
    private static final String TAG = "Attic_SamsungAnalyticsUtil";

    public static String conversationModeEndTimeIndexToDetail(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "null" : "C" : "B" : "A";
    }

    public static String makeTouchControlDetail(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "null" : "a" : "c" : "b";
    }

    public static void createDLCTestFile() {
        File file = new File("/sdcard/DLCTest.cfg");
        if (file.exists()) {
            Log.d(TAG, "createDLCTestFile() : already exist");
            return;
        }
        try {
            boolean createNewFile = file.createNewFile();
            Log.d(TAG, "createDLCTestFile() : createNewFile() : " + createNewFile);
        } catch (IOException unused) {
            Log.e(TAG, "createDLCTestFile() : IOException : createNewFile()");
        }
    }

    public static void init(Application application) {
        if (Application.DEBUG_MODE) {
            createDLCTestFile();
        }
        Configuration configuration = new Configuration();
        configuration.setTrackingId(SA.TRACKING_ID);
        configuration.setVersion(SA.UI_VERSION);
        configuration.enableAutoDeviceId();
        SamsungAnalytics.setConfiguration(application, configuration);
        initUncaughtExceptionLogging();
        registerSettingStatusInfo();
    }

    public static void sendPage(String str, Integer num) {
        String str2 = str + ", " + num + " (" + getScreenName(str) + ")";
        if (!Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, false)) {
            Log.w(TAG, "ignored sendPage() : " + str2);
            return;
        }
        Log.d(TAG, "sendPage() : " + str2);
        LogBuilders.ScreenViewBuilder screenViewBuilder = new LogBuilders.ScreenViewBuilder();
        screenViewBuilder.setScreenView(str);
        if (num != null) {
            screenViewBuilder.setScreenValue(num.intValue());
        }
        SamsungAnalytics.getInstance().sendLog(screenViewBuilder.build());
    }

    public static void sendPage(String str) {
        sendPage(str, null);
    }

    public static void sendEvent(String str, String str2, Long l, String str3, Integer num) {
        String str4 = str + ", " + str2 + ", " + l + ", " + str3 + ", " + num + " (" + getEventName(str) + " / " + getScreenName(str2) + ")";
        if (!Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, false)) {
            Log.w(TAG, "ignored sendEvent() : " + str4);
            return;
        }
        Log.d(TAG, "sendEvent() : " + str4);
        LogBuilders.EventBuilder eventBuilder = new LogBuilders.EventBuilder();
        eventBuilder.setEventName(str);
        if (l != null) {
            eventBuilder.setEventValue(l.longValue());
        }
        if (str2 != null) {
            eventBuilder.setScreenView(str2);
        }
        if (str3 != null) {
            HashMap hashMap = new HashMap();
            hashMap.put(LogBuilders.CustomDimension.DETAIL, str3);
            eventBuilder.setDimension(hashMap);
        }
        if (!(num == null || num.intValue() == 0)) {
            eventBuilder.setEventType(num.intValue());
        }
        SamsungAnalytics.getInstance().sendLog(eventBuilder.build());
    }

    public static void sendEvent(String str, String str2, String str3) {
        sendEvent(str, str2, null, str3, null);
    }

    public static void sendEvent(String str, String str2, Integer num) {
        if (num != null) {
            sendEvent(str, str2, Long.valueOf((long) num.intValue()), null, null);
        } else {
            sendEvent(str, str2, null, null, null);
        }
    }

    public static void sendEvent(String str, String str2, Long l) {
        sendEvent(str, str2, l, null, null);
    }

    public static void sendEvent(String str, String str2, Long l, String str3) {
        sendEvent(str, str2, l, str3, null);
    }

    public static void sendEvent(String str, String str2) {
        sendEvent(str, str2, null, null, null);
    }

    public static void sendEvent(String str) {
        sendEvent(str, null, null, null, null);
    }

    public static void registerSettingStatusInfo() {
        if (!Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, false)) {
            Log.w(TAG, "ignored registerSettingStatusInfo() : clear all status info");
            SamsungAnalytics.getInstance().registerSettingPref(new LogBuilders.SettingPrefBuilder().build());
            return;
        }
        LogBuilders.SettingPrefBuilder settingPrefBuilder = new LogBuilders.SettingPrefBuilder();
        Field[] fields = SA.Status.class.getFields();
        for (Field field : fields) {
            try {
                String str = (String) field.get(null);
                if (str != null) {
                    settingPrefBuilder.addKey(SA.STATUS_PREF_NAME, str);
                    if (Application.DEBUG_MODE) {
                        Log.d(TAG, "addKey() : " + str + " (" + field.getName() + ")");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Map<String, Set<String>> build = settingPrefBuilder.build();
        if (!build.isEmpty()) {
            SamsungAnalytics.getInstance().registerSettingPref(build);
        } else {
            Log.e(TAG, "SettingPref is empty !!!");
            throw new RuntimeException("SettingPref is empty !!!");
        }
    }

    public static SharedPreferences.Editor getStatusEditor() {
        return Application.getContext().getSharedPreferences(SA.STATUS_PREF_NAME, 4).edit();
    }

    public static void setStatusInt(String str, int i) {
        Log.d(TAG, "setStatusInt() : " + str + " = " + i + " (" + getStatusName(str) + ")");
        SharedPreferences.Editor statusEditor = getStatusEditor();
        statusEditor.putInt(str, i);
        statusEditor.apply();
    }

    public static void setStatusString(String str, String str2) {
        Log.d(TAG, "setStatusString() : " + str + " = " + str2 + " (" + getStatusName(str) + ")");
        SharedPreferences.Editor statusEditor = getStatusEditor();
        statusEditor.putString(str, str2);
        statusEditor.apply();
    }

    private static String getStatusName(String str) {
        Field[] fields = SA.Status.class.getFields();
        for (Field field : fields) {
            try {
                String str2 = (String) field.get(null);
                if (str2 != null && str2.equals(str)) {
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getScreenName(String str) {
        if (str == null) {
            return null;
        }
        Field[] fields = SA.Screen.class.getFields();
        for (Field field : fields) {
            try {
                String str2 = (String) field.get(null);
                if (str2 != null && str2.equals(str)) {
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getEventName(String str) {
        if (str == null) {
            return null;
        }
        Field[] fields = SA.Event.class.getFields();
        for (Field field : fields) {
            try {
                String str2 = (String) field.get(null);
                if (str2 != null && str2.equals(str)) {
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String touchPadOptionToDetail(int i) {
        switch (i) {
            case 1:
                return Util.isBixbyDefaultVoiceCommandAgent() ? "a" : "c";
            case 2:
                return "d";
            case 3:
                return "b";
            case 4:
                return "e";
            case 5:
                String str = touchPadOptionPkgNameToDetail(Preferences.getString(PreferenceKey.LEFT_OTHER_OPTION_PACKAGE_NAME, null));
                if (str != null) {
                    return str;
                }
                return String.valueOf(i);
            case 6:
                String str2 = touchPadOptionPkgNameToDetail(Preferences.getString(PreferenceKey.RIGHT_OTHER_OPTION_PACKAGE_NAME, null));
                if (str2 != null) {
                    return str2;
                }
                return String.valueOf(i);
            default:
                return String.valueOf(i);
        }
    }

    public static String touchPadOptionPkgNameToDetail(String str) {
        if (str == null) {
            return null;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1201983080) {
            if (hashCode != 346148845) {
                if (hashCode == 729178156 && str.equals(PKG_NAME_FLO)) {
                    c = 2;
                }
            } else if (str.equals(PKG_NAME_MELON)) {
                c = 0;
            }
        } else if (str.equals(PKG_NAME_GEINE)) {
            c = 1;
        }
        if (c == 0) {
            return "f";
        }
        if (c != 1) {
            return c != 2 ? str : "h";
        }
        return "g";
    }

    public static String touchPadTapAndHoldOthersPkgNameToDetail(String str) {
        if (str == null) {
            return null;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1201983080) {
            if (hashCode != 346148845) {
                if (hashCode == 729178156 && str.equals(PKG_NAME_FLO)) {
                    c = 2;
                }
            } else if (str.equals(PKG_NAME_MELON)) {
                c = 0;
            }
        } else if (str.equals(PKG_NAME_GEINE)) {
            c = 1;
        }
        if (c == 0) {
            return "b";
        }
        if (c != 1) {
            return c != 2 ? str : "d";
        }
        return "c";
    }

    public static String equalizerTypeToDetail(int i) {
        if (i == 0) {
            return "f";
        }
        if (i == 1) {
            return "a";
        }
        if (i == 2) {
            return "b";
        }
        if (i == 3) {
            return "c";
        }
        if (i != 4) {
            return i != 5 ? String.valueOf(i) : "e";
        }
        return "d";
    }

    public static String listIndexToDetail(int i) {
        if (i == 0) {
            return "a";
        }
        if (i == 1) {
            return "b";
        }
        if (i == 2) {
            return "c";
        }
        if (i != 3) {
            return i != 4 ? String.valueOf(i) : "e";
        }
        return "d";
    }

    public static void initUncaughtExceptionLogging() {
        boolean z = Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, false);
        Log.w(TAG, "initUncaughtExceptionLogging() : " + z);
        if (z) {
            DiagMonSDK.setDefaultConfiguration(Application.getContext(), SA.SERVICE_ID);
            DiagMonSDK.enableUncaughtExceptionLogging(Application.getContext());
            return;
        }
        DiagMonSDK.disableUncaughtExceptionLogging();
    }

    public static void setReportDiagnosticInfo(boolean z) {
        Log.w(TAG, "setReportDiagnosticInfo() : " + z);
        Preferences.putBoolean(PreferenceKey.SETUP_WIZARD_REPORT_DIAGNOSTIC_INFO, Boolean.valueOf(z));
        initUncaughtExceptionLogging();
        registerSettingStatusInfo();
    }

    public static void setNoiseControlsStatus(int i) {
        if (i == 0) {
            setStatusString("2376", "b");
        } else if (i == 1) {
            setStatusString("2376", "a");
        } else if (i == 2) {
            setStatusString("2376", "c");
        }
    }

    public static String makeSetNoiseControlsDetail(boolean z, boolean z2, boolean z3) {
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append("a");
        }
        if (z2) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("b");
        }
        if (z3) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("c");
        }
        return sb.toString();
    }

    public static String makeSoundBalanceDetail(int i) {
        return (((i - 16) * 100) / 16) + "%%";
    }
}
