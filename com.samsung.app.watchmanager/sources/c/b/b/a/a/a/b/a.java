package c.b.b.a.a.a.b;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class a {

    /* renamed from: a  reason: collision with root package name */
    private String f1718a = "";

    /* renamed from: b  reason: collision with root package name */
    private String f1719b = "";

    /* renamed from: c  reason: collision with root package name */
    private String f1720c = "";

    /* renamed from: d  reason: collision with root package name */
    private String f1721d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private String j = "";

    public a(Context context) {
        String simOperator;
        Locale locale = context.getResources().getConfiguration().locale;
        this.f1718a = locale.getDisplayCountry();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (!(telephonyManager == null || (simOperator = telephonyManager.getSimOperator()) == null || simOperator.length() < 3)) {
            this.g = simOperator.substring(0, 3);
            this.h = simOperator.substring(3);
        }
        this.f1719b = locale.getLanguage();
        this.f1720c = Build.VERSION.RELEASE;
        this.f1721d = Build.BRAND;
        this.e = Build.MODEL;
        this.j = Build.VERSION.INCREMENTAL;
        this.i = String.valueOf(TimeUnit.MILLISECONDS.toMinutes((long) TimeZone.getDefault().getRawOffset()));
        try {
            this.f = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            c.b.b.a.a.a.i.a.a(a.class, e2);
        }
    }

    public String a() {
        return this.f1720c;
    }

    public String b() {
        return this.f;
    }

    public String c() {
        return this.e;
    }

    public String d() {
        return this.j;
    }

    public String e() {
        return this.f1719b;
    }

    public String f() {
        return this.g;
    }

    public String g() {
        return this.h;
    }

    public String h() {
        return this.i;
    }
}
