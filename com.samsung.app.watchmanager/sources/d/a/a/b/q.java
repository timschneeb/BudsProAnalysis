package d.a.a.b;

import d.a.a.a;
import d.a.a.b.a;
import d.a.a.c.f;
import d.a.a.c.n;
import d.a.a.d;
import d.a.a.g;
import java.util.concurrent.ConcurrentHashMap;

public final class q extends a {
    private static final q M = new q(p.Z());
    private static final ConcurrentHashMap<g, q> N = new ConcurrentHashMap<>();

    static {
        N.put(g.f2149a, M);
    }

    private q(a aVar) {
        super(aVar, null);
    }

    public static q N() {
        return b(g.b());
    }

    public static q O() {
        return M;
    }

    public static q b(g gVar) {
        if (gVar == null) {
            gVar = g.b();
        }
        q qVar = N.get(gVar);
        if (qVar != null) {
            return qVar;
        }
        q qVar2 = new q(s.a(M, gVar));
        q putIfAbsent = N.putIfAbsent(gVar, qVar2);
        return putIfAbsent != null ? putIfAbsent : qVar2;
    }

    @Override // d.a.a.a
    public a G() {
        return M;
    }

    @Override // d.a.a.a
    public a a(g gVar) {
        if (gVar == null) {
            gVar = g.b();
        }
        return gVar == k() ? this : b(gVar);
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.b.a
    public void a(a.C0040a aVar) {
        if (L().k() == g.f2149a) {
            aVar.H = new f(r.f2032c, d.a(), 100);
            aVar.k = aVar.H.a();
            aVar.G = new n((f) aVar.H, d.x());
            aVar.C = new n((f) aVar.H, aVar.h, d.v());
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof q) {
            return k().equals(((q) obj).k());
        }
        return false;
    }

    public int hashCode() {
        return ("ISO".hashCode() * 11) + k().hashCode();
    }

    public String toString() {
        g k = k();
        if (k == null) {
            return "ISOChronology";
        }
        return "ISOChronology" + '[' + k.c() + ']';
    }
}
