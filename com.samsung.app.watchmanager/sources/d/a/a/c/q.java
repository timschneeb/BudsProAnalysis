package d.a.a.c;

import d.a.a.h;
import d.a.a.i;
import java.io.Serializable;
import java.util.HashMap;

public final class q extends h implements Serializable {

    /* renamed from: a  reason: collision with root package name */
    private static HashMap<i, q> f2061a;

    /* renamed from: b  reason: collision with root package name */
    private final i f2062b;

    private q(i iVar) {
        this.f2062b = iVar;
    }

    public static synchronized q a(i iVar) {
        q qVar;
        synchronized (q.class) {
            if (f2061a == null) {
                f2061a = new HashMap<>(7);
                qVar = null;
            } else {
                qVar = f2061a.get(iVar);
            }
            if (qVar == null) {
                qVar = new q(iVar);
                f2061a.put(iVar, qVar);
            }
        }
        return qVar;
    }

    private UnsupportedOperationException g() {
        return new UnsupportedOperationException(this.f2062b + " field is unsupported");
    }

    /* renamed from: a */
    public int compareTo(h hVar) {
        return 0;
    }

    @Override // d.a.a.h
    public long a(long j, int i) {
        throw g();
    }

    @Override // d.a.a.h
    public long a(long j, long j2) {
        throw g();
    }

    @Override // d.a.a.h
    public final i b() {
        return this.f2062b;
    }

    @Override // d.a.a.h
    public long c() {
        return 0;
    }

    @Override // d.a.a.h
    public boolean d() {
        return true;
    }

    @Override // d.a.a.h
    public boolean e() {
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof q)) {
            return false;
        }
        q qVar = (q) obj;
        return qVar.f() == null ? f() == null : qVar.f().equals(f());
    }

    public String f() {
        return this.f2062b.d();
    }

    public int hashCode() {
        return f().hashCode();
    }

    public String toString() {
        return "UnsupportedDurationField[" + f() + ']';
    }
}
