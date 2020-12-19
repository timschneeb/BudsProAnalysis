package com.samsung.sht.log;

import android.util.Log;

public class ShtLog {
    private static final int DEFAULT_LOG_LEVEL = 4;
    private static final String DEFAULT_TAG = "ShtCore";
    public static final int LOG_LEVEL_DEBUG = 3;
    public static final int LOG_LEVEL_ERROR = 0;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_VERBOSE = 4;
    public static final int LOG_LEVEL_WARN = 1;
    private static final String VERSION = "0.30";
    private static final String VERSION_PREFIX = "(0.30)";
    private static int curLogLevel = 4;

    public static void setLogLevel(int i) {
        curLogLevel = i;
    }

    public static void e(String str, String str2) {
        if (curLogLevel >= 0) {
            Log.e(str, VERSION_PREFIX + str2);
        }
    }

    public static void e(String str) {
        e(DEFAULT_TAG, str);
    }

    public static void w(String str, String str2) {
        if (curLogLevel >= 1) {
            Log.w(str, VERSION_PREFIX + str2);
        }
    }

    public static void w(String str) {
        w(DEFAULT_TAG, str);
    }

    public static void i(String str, String str2) {
        if (curLogLevel >= 2) {
            Log.i(str, VERSION_PREFIX + str2);
        }
    }

    public static void i(String str) {
        i(DEFAULT_TAG, str);
    }

    public static void d(String str, String str2) {
        if (curLogLevel >= 3) {
            Log.d(str, VERSION_PREFIX + str2);
        }
    }

    public static void d(String str) {
        d(DEFAULT_TAG, str);
    }

    public static void v(String str, String str2) {
        if (curLogLevel >= 4) {
            Log.v(str, VERSION_PREFIX + str2);
        }
    }

    public static void v(String str) {
        v(DEFAULT_TAG, str);
    }
}
