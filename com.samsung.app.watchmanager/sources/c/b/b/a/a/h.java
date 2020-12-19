package c.b.b.a.a;

import android.app.Application;
import c.b.b.a.a.a.d.d;
import c.b.b.a.a.a.e;
import java.util.Map;

public class h {

    /* renamed from: a  reason: collision with root package name */
    private static h f1818a;

    /* renamed from: b  reason: collision with root package name */
    private e f1819b = null;

    private h(Application application, c cVar) {
        if (!d.a(application, cVar)) {
            return;
        }
        if (cVar.k() || d.a(application)) {
            this.f1819b = new e(application, cVar);
        }
    }

    public static h a() {
        if (f1818a == null) {
            c.b.b.a.a.a.i.e.b("call after setConfiguration() method");
            if (!c.b.b.a.a.a.i.e.a()) {
                return b(null, null);
            }
        }
        return f1818a;
    }

    public static void a(Application application, c cVar) {
        b(application, cVar);
    }

    private static h b(Application application, c cVar) {
        h hVar = f1818a;
        if (hVar == null || hVar.f1819b == null) {
            synchronized (h.class) {
                f1818a = new h(application, cVar);
            }
        }
        return f1818a;
    }

    public int a(Map<String, String> map) {
        try {
            return this.f1819b.a(map);
        } catch (NullPointerException unused) {
            return -100;
        }
    }
}
