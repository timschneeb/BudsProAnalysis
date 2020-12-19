package c.b.b.a.a;

import android.text.TextUtils;
import c.b.b.a.a.a.d.d;
import c.b.b.a.a.a.i.b;
import c.b.b.a.a.a.i.e;
import c.b.b.a.a.f;
import java.util.HashMap;
import java.util.Map;

public abstract class f<T extends f> {

    /* renamed from: a  reason: collision with root package name */
    protected Map<String, String> f1817a = new HashMap();

    protected f() {
    }

    public T a(String str) {
        if (TextUtils.isEmpty(str)) {
            e.b("Failure to build logs [PropertyBuilder] : Key cannot be null.");
        } else {
            a("pn", str);
        }
        return b();
    }

    public final T a(String str, String str2) {
        if (str != null) {
            this.f1817a.put(str, str2);
        }
        return b();
    }

    public T a(Map<String, String> map) {
        a("cd", new b().a(d.a(map), b.a.TWO_DEPTH));
        return b();
    }

    public Map<String, String> a() {
        a("ts", String.valueOf(c()));
        return this.f1817a;
    }

    /* access modifiers changed from: protected */
    public abstract T b();

    public long c() {
        return System.currentTimeMillis();
    }
}
