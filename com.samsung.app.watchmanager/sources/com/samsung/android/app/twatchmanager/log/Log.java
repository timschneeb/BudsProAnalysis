package com.samsung.android.app.twatchmanager.log;

import android.text.TextUtils;
import java.util.regex.Pattern;

public final class Log {
    private static final boolean DEV_MODE = false;
    private static final boolean FILE_WRITE = true;
    private static Pattern btAddressPattern = Pattern.compile("([0-9A-Fa-f]{2}[:-]){4}");
    private static String mVersion = "";

    public static final void d(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            str2 = btAddressPattern.matcher(str2).replaceAll("##:##:##:##:");
        }
        android.util.Log.i(str, str2);
        MLogger.info(mVersion + str + "." + str2);
    }

    public static final void e(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            str2 = btAddressPattern.matcher(str2).replaceAll("##:##:##:##:");
        }
        android.util.Log.e(str, str2);
        MLogger.info(mVersion + str + "." + str2);
    }

    public static final void i(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            str2 = btAddressPattern.matcher(str2).replaceAll("##:##:##:##:");
        }
        android.util.Log.i(str, str2);
        MLogger.info(mVersion + str + "." + str2);
    }

    public static final void saveLog() {
        android.util.Log.i("tUHM:saveLog", "starts saving log");
        MLogger.dumpLog();
        MLogger.copyToSdcard();
        android.util.Log.i("tUHM:saveLog", "ends saving log");
    }

    public static void setVersion(String str) {
        mVersion = "v" + str;
    }

    public static final void v(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            str2 = btAddressPattern.matcher(str2).replaceAll("##:##:##:##:");
        }
        android.util.Log.i(str, str2);
        MLogger.info(mVersion + str + "." + str2);
    }

    public static final void w(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            str2 = btAddressPattern.matcher(str2).replaceAll("##:##:##:##:");
        }
        android.util.Log.w(str, str2);
        MLogger.info(mVersion + str + "." + str2);
    }

    public static final void w(String str, String str2, Error error) {
        if (!TextUtils.isEmpty(str2)) {
            str2 = btAddressPattern.matcher(str2).replaceAll("##:##:##:##:");
        }
        android.util.Log.w(str, str2, error);
        MLogger.info(mVersion + str + "." + str2 + ":" + error.getMessage());
    }

    public static final void w(String str, String str2, Exception exc) {
        if (!TextUtils.isEmpty(str2)) {
            str2 = btAddressPattern.matcher(str2).replaceAll("##:##:##:##:");
        }
        android.util.Log.w(str, str2, exc);
        MLogger.info(mVersion + str + "." + str2 + ":" + exc.getMessage());
    }
}
