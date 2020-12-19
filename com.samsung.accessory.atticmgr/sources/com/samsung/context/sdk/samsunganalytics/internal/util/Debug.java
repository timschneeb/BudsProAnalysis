package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.util.Log;

public class Debug {
    private static final String LOGTAG = "SamsungAnalytics605033";

    public static void LogENG(String str) {
        if (Utils.isEngBin()) {
            Log.d(LOGTAG, "[ENG ONLY] " + str);
        }
    }

    public static void LogV(String str) {
        Log.v(LOGTAG, str);
    }

    public static void LogI(String str) {
        Log.i(LOGTAG, str);
    }

    public static void LogD(String str) {
        Log.d(LOGTAG, str);
    }

    public static void LogD(String str, String str2) {
        LogD("[" + str + "] " + str2);
    }

    public static void LogE(String str) {
        Log.e(LOGTAG, str);
    }

    public static void LogE(String str, String str2) {
        LogE("[" + str + "] " + str2);
    }

    public static void LogException(Class cls, Exception exc) {
        if (exc != null) {
            Log.w(LOGTAG, "[" + cls.getSimpleName() + "] " + exc.getClass().getSimpleName() + " " + exc.getMessage());
        }
    }
}
