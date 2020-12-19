package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.sec.android.diagmonagent.log.provider.a.a;
import com.sec.android.diagmonagent.log.provider.a.b;
import java.util.concurrent.TimeUnit;

/* access modifiers changed from: package-private */
public class e implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    private static String f1982a = "diagmon_pref";

    /* renamed from: b  reason: collision with root package name */
    private static String f1983b = "diagmon_timestamp";

    /* renamed from: c  reason: collision with root package name */
    private static long f1984c = TimeUnit.HOURS.toMillis(6);

    /* renamed from: d  reason: collision with root package name */
    private a f1985d;
    private DiagMonProvider e;
    private Bundle f;

    public e(a aVar) {
        this.f1985d = aVar;
    }

    private static long a(Context context) {
        return context.getSharedPreferences(f1982a, 0).getLong(f1983b, 0);
    }

    private static void a(Context context, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences(f1982a, 0).edit();
        edit.putLong(f1983b, j);
        edit.apply();
    }

    private static boolean a(Context context, String str) {
        try {
            if (a.a(context) != 2) {
                return true;
            }
            try {
                Bundle bundle = new Bundle();
                bundle.putString("serviceId", str);
                context.getContentResolver().call(Uri.parse("content://com.sec.android.log.diagmonagent/"), "request_deviceid", "request_deviceid", bundle);
                return true;
            } catch (Exception | IllegalArgumentException unused) {
                return false;
            }
        } catch (Exception | NullPointerException unused2) {
            return false;
        }
    }

    public void run() {
        String str;
        String str2;
        c.a(true);
        int a2 = a.a(this.f1985d.c());
        if (a2 == 0) {
            str = a.f1964a;
            str2 = "Not installed DMA";
        } else if (a2 != 1) {
            if (a2 != 2) {
                str = a.f1964a;
                str2 = "Exceptional case";
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis <= a(this.f1985d.c()) + f1984c) {
                    Log.d(a.f1964a, "it is not time to request for SR");
                    return;
                } else if (a(this.f1985d.c(), this.f1985d.g())) {
                    a(this.f1985d.c(), currentTimeMillis);
                    this.f = new Bundle();
                    this.f = a.a(this.f1985d);
                    c.c(this.f);
                    c.b(this.f);
                    return;
                } else {
                    Log.w(a.f1964a, "Authority check got failed");
                    return;
                }
            }
        } else if (b.a(this.f1985d)) {
            Log.w(a.f1964a, "Invalid DiagMonConfiguration");
            Log.w(a.f1964a, "SetConfiguration is aborted");
            this.f1985d = null;
            return;
        } else {
            this.e = new DiagMonProvider();
            this.e.a(this.f1985d);
            Log.i(a.f1964a, "Valid DiagMonConfiguration");
            return;
        }
        Log.w(str, str2);
        Log.w(a.f1964a, "SetConfiguration is aborted");
    }
}
