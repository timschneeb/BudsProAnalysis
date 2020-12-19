package seccompat.android.util;

import android.os.Build;

public class Log {
    public static int v(String str, String str2) {
        if (isSamsungROSOrHigher()) {
            return android.util.Log.i(str, str2);
        }
        return android.util.Log.v(str, str2);
    }

    public static int d(String str, String str2) {
        if (isSamsungROSOrHigher()) {
            return android.util.Log.i(str, str2);
        }
        return android.util.Log.d(str, str2);
    }

    public static int i(String str, String str2) {
        return android.util.Log.i(str, str2);
    }

    public static int w(String str, String str2) {
        return android.util.Log.w(str, str2);
    }

    public static int w(String str, String str2, Throwable th) {
        return android.util.Log.w(str, str2, th);
    }

    public static int e(String str, String str2) {
        return android.util.Log.e(str, str2);
    }

    private static boolean isSamsungROSOrHigher() {
        return "SAMSUNG".equalsIgnoreCase(Build.MANUFACTURER) && Build.VERSION.SDK_INT >= 30;
    }
}
