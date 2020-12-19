package c.b.b.a.a.a;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import c.b.b.a.a.a.c.f;
import c.b.b.a.a.a.d.b;
import c.b.b.a.a.a.f.d;
import c.b.b.a.a.a.i.a;
import c.b.b.a.a.c;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

public class e {

    /* renamed from: a  reason: collision with root package name */
    private Application f1739a;

    /* renamed from: b  reason: collision with root package name */
    private c f1740b;

    public e(Application application, c cVar) {
        String str;
        this.f1739a = application;
        this.f1740b = cVar;
        Context applicationContext = application.getApplicationContext();
        if (!TextUtils.isEmpty(cVar.d())) {
            this.f1740b.a(2);
        } else if (!f() && cVar.i() && (cVar.k() || b.b() == 1)) {
            a(b(), 1);
        }
        if (b.b() == 0) {
            c();
        }
        if (!cVar.k()) {
            this.f1740b.a(new b(this, applicationContext));
        }
        if (e()) {
            if (cVar.j()) {
                d.a(application, b.b(), cVar);
            }
            if (b.b() == 3) {
                SharedPreferences a2 = c.b.b.a.a.a.i.c.a(applicationContext);
                try {
                    str = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    a.a(e.class, e);
                    str = "";
                }
                str = str == null ? "None" : str;
                boolean z = a2.getBoolean("sendCommonSuccess", false);
                String string = a2.getString("appVersion", "None");
                Long valueOf = Long.valueOf(a2.getLong("sendCommonTime", 0));
                a.a("AppVersion = " + str + ", prefAppVerison = " + string + ", beforeSendCommonTime = " + valueOf + ", success = " + z);
                if (!str.equals(string) || ((z && c.b.b.a.a.a.i.e.a(7, valueOf)) || (!z && c.b.b.a.a.a.i.e.b(6, valueOf)))) {
                    a.a("send Common!!");
                    a2.edit().putString("appVersion", str).putLong("sendCommonTime", System.currentTimeMillis()).apply();
                    ((c.b.b.a.a.a.f.c.d) d.a(application, 3, cVar)).a();
                }
            }
        }
        c.b.b.a.a.a.i.e.b(applicationContext, cVar);
        g();
        a.a("Tracker", "Tracker start:6.05.026 , senderType : " + b.b());
    }

    private void a(String str, int i) {
        c.b.b.a.a.a.i.c.a(this.f1739a.getApplicationContext()).edit().putString("deviceId", str).putInt("auidType", i).apply();
        this.f1740b.a(i);
        this.f1740b.a(str);
    }

    private boolean a() {
        if (b.b() >= 2 || !TextUtils.isEmpty(this.f1740b.d())) {
            return true;
        }
        a.a("did is empty");
        return false;
    }

    private String b() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bArr = new byte[16];
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            secureRandom.nextBytes(bArr);
            try {
                sb.append("0123456789abcdefghijklmjopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (Math.abs(new BigInteger(bArr).longValue()) % ((long) 62))));
            } catch (Exception e) {
                a.a(e.class, e);
                return null;
            }
        }
        return sb.toString();
    }

    private void c() {
        SharedPreferences a2 = c.b.b.a.a.a.i.c.a(this.f1739a);
        c.b.b.a.a.a.a.c.f1710c.a(a2.getString("dom", ""));
        c.b.b.a.a.a.a.b.DLS_DIR.a(a2.getString("uri", ""));
        c.b.b.a.a.a.a.b.DLS_DIR_BAT.a(a2.getString("bat-uri", ""));
        if (b.a(this.f1739a.getApplicationContext())) {
            b.a(this.f1739a, this.f1740b, f.a(), new c.b.b.a.a.a.b.a(this.f1739a), new c(this));
        }
    }

    private boolean d() {
        if (!c.b.b.a.a.a.i.e.a(1, Long.valueOf(c.b.b.a.a.a.i.c.a(this.f1739a).getLong("property_sent_date", 0)))) {
            a.a("do not send property < 1day");
            return false;
        }
        c.b.b.a.a.a.i.c.a(this.f1739a).edit().putLong("property_sent_date", System.currentTimeMillis()).apply();
        return true;
    }

    private boolean e() {
        return this.f1740b.f().a();
    }

    private boolean f() {
        SharedPreferences a2 = c.b.b.a.a.a.i.c.a(this.f1739a);
        String string = a2.getString("deviceId", "");
        int i = a2.getInt("auidType", -1);
        if (TextUtils.isEmpty(string) || string.length() != 32 || i == -1) {
            return false;
        }
        this.f1740b.a(i);
        this.f1740b.a(string);
        return true;
    }

    private void g() {
        SharedPreferences sharedPreferences = this.f1739a.getSharedPreferences("SATerms", 0);
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            f.a().a(new c.b.b.a.a.a.h.a(this.f1740b.e(), key, ((Long) entry.getValue()).longValue(), new d(this, sharedPreferences, key)));
        }
    }

    public int a(Map<String, String> map) {
        if (!e()) {
            a.a("user do not agree");
            return -2;
        } else if (map == null || map.isEmpty()) {
            a.a("Failure to send Logs : No data");
            return -3;
        } else if (!a()) {
            return -5;
        } else {
            if (!map.get("t").equals("pp") || d()) {
                return d.a(this.f1739a, b.b(), this.f1740b).a(map);
            }
            return -9;
        }
    }
}
