package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import com.sec.android.diagmonagent.log.provider.a.b;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class d {

    /* renamed from: a  reason: collision with root package name */
    private Context f1974a;

    /* renamed from: b  reason: collision with root package name */
    private String f1975b = "";

    /* renamed from: c  reason: collision with root package name */
    private String f1976c = "";

    /* renamed from: d  reason: collision with root package name */
    private String f1977d = "";
    private boolean e;
    public boolean f;
    private String g = "";
    private String h = "";
    private String i = "";
    private JSONObject j;
    private String k = "";
    private a l;

    /* access modifiers changed from: package-private */
    public class a {

        /* renamed from: a  reason: collision with root package name */
        private Context f1978a;

        /* renamed from: b  reason: collision with root package name */
        private boolean f1979b = true;

        /* renamed from: c  reason: collision with root package name */
        private boolean f1980c = true;

        /* renamed from: d  reason: collision with root package name */
        private String f1981d = "";
        private String e = "";
        private String f = "";
        private String g = "";
        private String h = "";

        public a(Context context) {
            this.f1978a = context;
        }

        public String a() {
            return this.f;
        }

        public void a(String str) {
            this.f1981d = str;
        }

        public void a(boolean z) {
            this.f1980c = z;
        }

        public String b() {
            return this.e;
        }

        public String c() {
            return this.g;
        }

        public String d() {
            return this.h;
        }

        public String e() {
            return this.f1981d;
        }

        public boolean f() {
            return this.f1980c;
        }
    }

    public d(Context context) {
        this.f1974a = context;
        this.e = true;
        this.f = false;
        if (com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1) {
            this.l = new a(context);
        }
    }

    public static long a() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
    }

    private JSONObject m() {
        long nativeHeapFreeSize = Debug.getNativeHeapFreeSize() >> 20;
        long nativeHeapSize = Debug.getNativeHeapSize() >> 20;
        long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize() >> 20;
        String str = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
        Log.d(str, "[NativeHeap] nativeHeapSize : " + nativeHeapSize + " nativeHeapFree : " + nativeHeapFreeSize + " nativeHeapAllocatedSize : " + nativeHeapAllocatedSize);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("HEAP_SIZE", nativeHeapSize);
            jSONObject.put("HEAP_FREE", nativeHeapFreeSize);
            jSONObject.put("HEAD_ALLOC", nativeHeapAllocatedSize);
        } catch (JSONException e2) {
            Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, e2.getMessage());
        }
        return jSONObject;
    }

    private static long n() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
    }

    private JSONObject o() {
        Runtime runtime = Runtime.getRuntime();
        long j2 = runtime.totalMemory() >> 20;
        long freeMemory = runtime.freeMemory() >> 20;
        long maxMemory = runtime.maxMemory() >> 20;
        String str = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
        Log.d(str, "[VM] TotalMemory : " + j2 + " FreeMemory : " + freeMemory + " maxMemory : " + maxMemory);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("TOTAL", j2);
            jSONObject.put("FREE", freeMemory);
            jSONObject.put("MAX", maxMemory);
        } catch (JSONException e2) {
            Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, e2.getMessage());
        }
        return jSONObject;
    }

    private boolean p() {
        return c.d() == null;
    }

    public d a(boolean z) {
        if (p()) {
            return this;
        }
        this.f = true;
        this.e = z;
        if (com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1) {
            this.l.a(z);
        }
        return this;
    }

    public List<String> a(String str) {
        ArrayList arrayList = new ArrayList();
        File[] listFiles = new File(str).listFiles();
        for (File file : listFiles) {
            arrayList.add(file.getPath());
            Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "found file : " + file.getPath());
        }
        return arrayList;
    }

    public d b(String str) {
        if (p()) {
            return this;
        }
        this.f1977d = str;
        if (com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1) {
            this.l.a(str);
        }
        return this;
    }

    public String b() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1 ? this.l.a() : this.g;
    }

    public d c(String str) {
        try {
            if (p()) {
                return this;
            }
            this.f1975b = str;
            if (com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1 && this.f1975b != null) {
                if (!b.a(this.f1975b)) {
                    c.d().f().a(a(this.f1975b));
                    c.e().a(c.d());
                }
            }
            return this;
        } catch (Exception | NullPointerException unused) {
        }
    }

    public String c() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1 ? this.l.e() : this.f1977d;
    }

    public String d() {
        JSONObject jSONObject = this.j;
        return jSONObject == null ? "" : jSONObject.toString();
    }

    public void d(String str) {
        this.k = str;
    }

    public JSONObject e() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("TOTAL", n() >> 20);
            jSONObject.put("FREE", a() >> 20);
        } catch (JSONException e2) {
            Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, e2.getMessage());
        }
        return jSONObject;
    }

    public String f() {
        return this.f1975b;
    }

    public JSONObject g() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("VM", o());
            jSONObject.put("NATIVE", m());
            Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, jSONObject.toString());
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public boolean h() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1 ? this.l.f() : this.e;
    }

    public String i() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1 ? this.l.c() : this.i;
    }

    public String j() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1 ? this.l.d() : this.h;
    }

    public String k() {
        return com.sec.android.diagmonagent.log.provider.a.a.a(this.f1974a) == 1 ? this.l.b() : this.f1976c;
    }

    public String l() {
        return this.k;
    }
}
