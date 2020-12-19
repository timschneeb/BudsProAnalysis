package c.b.b.a.a.a.f;

import android.content.Context;
import android.text.TextUtils;
import c.b.b.a.a.a.c.f;
import c.b.b.a.a.a.i.b;
import c.b.b.a.a.a.i.e;
import c.b.b.a.a.c;
import java.util.Map;

public abstract class a implements b {

    /* renamed from: a  reason: collision with root package name */
    protected Context f1743a;

    /* renamed from: b  reason: collision with root package name */
    protected c f1744b;

    /* renamed from: c  reason: collision with root package name */
    protected c.b.b.a.a.a.b.a f1745c;

    /* renamed from: d  reason: collision with root package name */
    protected b<String, String> f1746d;
    protected c.b.b.a.a.a.f.d.a e;
    protected c.b.b.a.a.a.c.c f = f.a();

    public a(Context context, c cVar) {
        this.f1743a = context.getApplicationContext();
        this.f1744b = cVar;
        this.f1745c = new c.b.b.a.a.a.b.a(context);
        this.f1746d = new b<>();
        this.e = c.b.b.a.a.a.f.d.a.a(context, cVar);
    }

    /* access modifiers changed from: protected */
    public c b(Map<String, String> map) {
        return e.a(map.get("t"));
    }

    /* access modifiers changed from: protected */
    public void c(Map<String, String> map) {
        this.e.a(new e(map.get("t"), Long.valueOf(map.get("ts")).longValue(), d(e(map)), b(map)));
    }

    /* access modifiers changed from: protected */
    public String d(Map<String, String> map) {
        return this.f1746d.a(map, b.a.ONE_DEPTH);
    }

    /* access modifiers changed from: protected */
    public Map<String, String> e(Map<String, String> map) {
        if (c.b.b.a.a.a.d.b.b() < 2) {
            map.put("la", this.f1745c.e());
            if (!TextUtils.isEmpty(this.f1745c.f())) {
                map.put("mcc", this.f1745c.f());
            }
            if (!TextUtils.isEmpty(this.f1745c.g())) {
                map.put("mnc", this.f1745c.g());
            }
            map.put("dm", this.f1745c.c());
            map.put("auid", this.f1744b.d());
            map.put("do", this.f1745c.a());
            map.put("av", this.f1745c.b());
            map.put("uv", this.f1744b.g());
            map.put("at", String.valueOf(this.f1744b.b()));
            map.put("fv", this.f1745c.d());
            map.put("tid", this.f1744b.e());
        }
        map.put("tz", this.f1745c.h());
        return map;
    }
}
