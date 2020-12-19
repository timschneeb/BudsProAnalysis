package c.b.b.a.a.a.i;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Settings;
import c.b.b.a.a.a.c.f;
import c.b.b.a.a.a.f.c;
import c.b.b.a.a.a.g.a;

public class e {

    /* renamed from: a  reason: collision with root package name */
    private static BroadcastReceiver f1810a;

    public static long a(int i) {
        return Long.valueOf(System.currentTimeMillis()).longValue() - (((long) i) * 86400000);
    }

    public static c a(String str) {
        return "dl".equals(str) ? c.DEVICE : c.UIX;
    }

    public static void a(Context context, c.b.b.a.a.c cVar) {
        a.c("register BR ");
        if (f1810a == null) {
            f1810a = new d(cVar);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            context.registerReceiver(f1810a, intentFilter);
            return;
        }
        a.c("BR is already registered");
    }

    public static boolean a() {
        return Build.TYPE.equals("eng");
    }

    public static boolean a(int i, Long l) {
        return Long.valueOf(System.currentTimeMillis()).longValue() > l.longValue() + (((long) i) * 86400000);
    }

    public static boolean a(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "samsung_errorlog_agree", 0) == 1;
    }

    public static void b(Context context, c.b.b.a.a.c cVar) {
        f.a().a(new a(context, cVar));
    }

    public static void b(String str) {
        if (!a()) {
            a.b(str);
            return;
        }
        throw new c.b.b.a.a.a(str);
    }

    public static boolean b(int i, Long l) {
        return Long.valueOf(System.currentTimeMillis()).longValue() > l.longValue() + (((long) i) * 3600000);
    }
}
