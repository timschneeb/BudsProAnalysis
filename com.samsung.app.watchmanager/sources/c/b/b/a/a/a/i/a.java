package c.b.b.a.a.a.i;

import android.util.Log;

public class a {
    public static void a(Class cls, Exception exc) {
        if (exc != null) {
            Log.w("SamsungAnalytics605026", "[" + cls.getSimpleName() + "] " + exc.getClass().getSimpleName() + " " + exc.getMessage());
        }
    }

    public static void a(String str) {
        Log.d("SamsungAnalytics605026", str);
    }

    public static void a(String str, String str2) {
        a("[" + str + "] " + str2);
    }

    public static void b(String str) {
        Log.e("SamsungAnalytics605026", str);
    }

    public static void c(String str) {
        if (e.a()) {
            Log.d("SamsungAnalytics605026", "[ENG ONLY] " + str);
        }
    }
}
