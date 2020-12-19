package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class f {

    /* renamed from: a  reason: collision with root package name */
    public static f f1986a = new f();

    public static String a() {
        return Build.VERSION.SDK_INT >= 26 ? "" : Build.SERIAL;
    }

    public static String b() {
        String a2 = a();
        if ("".equals(a2)) {
            return "";
        }
        return "TWID:" + a2;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle a(android.content.Context r4) {
        /*
            r3 = this;
            java.lang.String r0 = "deviceInfo"
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            android.os.Bundle r2 = new android.os.Bundle     // Catch:{ Exception -> 0x000f }
            r2.<init>()     // Catch:{ Exception -> 0x000f }
            r1.putBundle(r0, r2)     // Catch:{ Exception -> 0x000f }
        L_0x000f:
            android.os.Bundle r0 = r1.getBundle(r0)     // Catch:{ Exception -> 0x001c }
            java.lang.String r2 = "serviceClientVer"
            java.lang.String r4 = r3.c(r4)     // Catch:{ Exception -> 0x001c }
            r0.putString(r2, r4)     // Catch:{ Exception -> 0x001c }
        L_0x001c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.f.a(android.content.Context):android.os.Bundle");
    }

    public String a(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return "unknown";
        }
        try {
            return packageManager.getPackageInfo(str, 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            return "unknown";
        }
    }

    public String b(Context context) {
        return context.getPackageName();
    }

    public String c(Context context) {
        return a(context, b(context));
    }
}
