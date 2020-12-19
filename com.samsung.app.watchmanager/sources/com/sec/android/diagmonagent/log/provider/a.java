package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class a {

    /* renamed from: a  reason: collision with root package name */
    private Context f1956a;

    /* renamed from: b  reason: collision with root package name */
    private String f1957b = "";

    /* renamed from: c  reason: collision with root package name */
    private String f1958c = "";

    /* renamed from: d  reason: collision with root package name */
    private String f1959d = "";
    private boolean e;
    private String f = "";
    private String g = "";
    public boolean h;
    public boolean i;
    private boolean j = false;
    private C0039a k;

    /* access modifiers changed from: package-private */
    /* renamed from: com.sec.android.diagmonagent.log.provider.a$a  reason: collision with other inner class name */
    public class C0039a {

        /* renamed from: a  reason: collision with root package name */
        private Context f1960a;

        /* renamed from: b  reason: collision with root package name */
        private boolean f1961b = false;

        /* renamed from: c  reason: collision with root package name */
        private String f1962c = "";

        /* renamed from: d  reason: collision with root package name */
        private String f1963d = "";
        private String e = "Samsung Software";
        private String f = "";
        private String g = "";
        private List<String> h;
        private List<String> i;

        public C0039a(Context context) {
            this.f1960a = context;
            this.h = new ArrayList();
            this.i = new ArrayList();
        }

        public void a(String str) {
            this.f1962c = str;
            if (this.f1962c.equals("S")) {
                this.f1962c = "Y";
            }
            if (this.f1962c.isEmpty()) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Empty agreement");
            } else if (this.f1962c.equals("Y") || this.f1962c.equals("D")) {
                this.f1961b = true;
                return;
            } else {
                String str2 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
                Log.w(str2, "Wrong agreement : " + str);
            }
            this.f1961b = false;
        }

        public void a(List<String> list) {
            this.i = list;
        }

        public boolean a() {
            return this.f1961b;
        }

        public String b() {
            return this.f1962c;
        }

        public void b(String str) {
            List<String> list = this.h;
            list.add("com.sec.android.log." + str);
        }

        public List<String> c() {
            return this.h;
        }

        public void c(String str) {
            this.f1963d = str;
            b(str);
        }

        public String d() {
            return this.f;
        }

        public List<String> e() {
            return this.i;
        }

        public String f() {
            return this.f1963d;
        }

        public String g() {
            return this.e;
        }

        public String h() {
            return this.g;
        }
    }

    public a(Context context) {
        this.f1956a = context;
        this.e = false;
        this.h = true;
        this.i = false;
        try {
            this.f1958c = this.f1956a.getPackageManager().getPackageInfo(this.f1956a.getPackageName(), 0).versionName.toString();
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        if (com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1) {
            this.k = new C0039a(context);
        }
    }

    public a a(String str) {
        this.f1959d = str;
        if (this.f1959d == null) {
            Log.e(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "You can't use agreement as null");
            return this;
        }
        if (com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1) {
            this.k.a(this.f1959d);
        } else if (!this.f1959d.equals("S") && !this.f1959d.equals("D")) {
            this.e = false;
        } else {
            this.e = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void a(boolean z) {
        this.j = z;
    }

    public boolean a() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1 ? this.k.a() : this.e;
    }

    public a b(String str) {
        this.f1957b = str;
        if (com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1) {
            this.k.c(str);
            this.k.b(str);
        }
        return this;
    }

    public String b() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1 ? this.k.b() : this.f1959d;
    }

    public Context c() {
        return this.f1956a;
    }

    public boolean d() {
        return this.h;
    }

    public String e() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1 ? this.k.d() : this.f;
    }

    public C0039a f() {
        if (com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1) {
            return this.k;
        }
        return null;
    }

    public String g() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1 ? this.k.f() : this.f1957b;
    }

    public String h() {
        return this.f1958c;
    }

    public String i() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1956a) == 1 ? this.k.h() : this.g;
    }

    /* access modifiers changed from: protected */
    public boolean j() {
        return this.j;
    }

    public boolean k() {
        return this.i;
    }
}
