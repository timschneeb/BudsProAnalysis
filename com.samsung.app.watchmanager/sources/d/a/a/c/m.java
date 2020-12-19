package d.a.a.c;

import d.a.a.i;

public class m extends c {

    /* renamed from: b  reason: collision with root package name */
    private final long f2054b;

    public m(i iVar, long j) {
        super(iVar);
        this.f2054b = j;
    }

    @Override // d.a.a.h
    public long a(long j, int i) {
        return g.a(j, ((long) i) * this.f2054b);
    }

    @Override // d.a.a.h
    public long a(long j, long j2) {
        return g.a(j, g.b(j2, this.f2054b));
    }

    @Override // d.a.a.h
    public final long c() {
        return this.f2054b;
    }

    @Override // d.a.a.h
    public final boolean d() {
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof m)) {
            return false;
        }
        m mVar = (m) obj;
        return b() == mVar.b() && this.f2054b == mVar.f2054b;
    }

    public int hashCode() {
        long j = this.f2054b;
        return ((int) (j ^ (j >>> 32))) + b().hashCode();
    }
}
