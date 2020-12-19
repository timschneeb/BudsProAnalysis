package c.b.b.a.a.a.d;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import c.b.b.a.a.a.i.a;
import c.b.b.a.a.a.i.c;
import c.b.b.a.a.a.i.e;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class b {

    /* renamed from: a  reason: collision with root package name */
    private static int f1735a = -1;

    public static int a(Context context, int i) {
        int i2;
        String str;
        SharedPreferences a2 = c.a(context);
        int i3 = 0;
        if (i == 1) {
            i2 = a2.getInt("dq-w", 0);
            str = "wifi_used";
        } else if (i == 0) {
            i2 = a2.getInt("dq-3g", 0);
            str = "data_used";
        } else {
            i2 = 0;
            return i2 - i3;
        }
        i3 = a2.getInt(str, 0);
        return i2 - i3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0060  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(android.content.Context r4, int r5, int r6) {
        /*
        // Method dump skipped, instructions count: 114
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.b.a.a.a.d.b.a(android.content.Context, int, int):int");
    }

    public static int a(Context context, c.b.b.a.a.c cVar) {
        if (f1735a == -1) {
            try {
                int i = 0;
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
                a.a("Validation", "dma pkg:" + packageInfo.versionCode);
                if (packageInfo.versionCode < 540000000) {
                    if (!cVar.k()) {
                        i = 1;
                    }
                    f1735a = i;
                } else if (packageInfo.versionCode >= 600000000) {
                    f1735a = 3;
                } else {
                    f1735a = 2;
                }
            } catch (Exception e) {
                f1735a = !cVar.k();
                a.a("DMA not found" + e.getMessage());
            }
        }
        return f1735a;
    }

    public static a a(Context context, c.b.b.a.a.c cVar, c.b.b.a.a.a.b.a aVar, c.b.b.a.a.a.a aVar2) {
        a aVar3 = new a(c.b.b.a.a.a.a.a.GET_POLICY, a(context, aVar, cVar), c.a(context), aVar2);
        a.c("trid: " + cVar.e().substring(0, 7) + ", uv: " + cVar.g());
        return aVar3;
    }

    public static String a() {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, "ro.csc.sales_code");
        } catch (Exception unused) {
            return null;
        }
    }

    public static Map<String, String> a(Context context, c.b.b.a.a.a.b.a aVar, c.b.b.a.a.c cVar) {
        HashMap hashMap = new HashMap();
        hashMap.put("pkn", context.getPackageName());
        hashMap.put("dm", aVar.c());
        if (!TextUtils.isEmpty(aVar.f())) {
            hashMap.put("mcc", aVar.f());
        }
        if (!TextUtils.isEmpty(aVar.g())) {
            hashMap.put("mnc", aVar.g());
        }
        hashMap.put("uv", cVar.g());
        hashMap.put("sv", c.b.b.a.a.b.f1812b);
        hashMap.put("did", cVar.d());
        hashMap.put("tid", cVar.e());
        String format = SimpleDateFormat.getTimeInstance(2, Locale.US).format(new Date());
        hashMap.put("ts", format);
        hashMap.put("hc", d.a(cVar.e() + format + d.f1738a));
        String a2 = a();
        if (!TextUtils.isEmpty(a2)) {
            hashMap.put("csc", a2);
        }
        return hashMap;
    }

    public static void a(Context context, c.b.b.a.a.c cVar, c.b.b.a.a.a.c.c cVar2, c.b.b.a.a.a.b.a aVar) {
        cVar2.a(a(context, cVar, aVar, (c.b.b.a.a.a.a) null));
    }

    public static void a(Context context, c.b.b.a.a.c cVar, c.b.b.a.a.a.c.c cVar2, c.b.b.a.a.a.b.a aVar, c.b.b.a.a.a.a aVar2) {
        cVar2.a(a(context, cVar, aVar, aVar2));
    }

    public static void a(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().putLong("quota_reset_date", System.currentTimeMillis()).putInt("data_used", 0).putInt("wifi_used", 0).apply();
    }

    public static boolean a(Context context) {
        SharedPreferences a2 = c.a(context);
        if (e.a(1, Long.valueOf(a2.getLong("quota_reset_date", 0)))) {
            a(a2);
        }
        return e.a(a2.getInt("rint", 1), Long.valueOf(a2.getLong("policy_received_date", 0)));
    }

    public static int b() {
        return f1735a;
    }

    public static void b(Context context, int i, int i2) {
        SharedPreferences.Editor putInt;
        SharedPreferences a2 = c.a(context);
        if (i == 1) {
            putInt = a2.edit().putInt("wifi_used", a2.getInt("wifi_used", 0) + i2);
        } else if (i == 0) {
            putInt = a2.edit().putInt("data_used", c.a(context).getInt("data_used", 0) + i2);
        } else {
            return;
        }
        putInt.apply();
    }
}
