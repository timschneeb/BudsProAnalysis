package d.a.a.c;

import d.a.a.h;
import d.a.a.i;

public class o extends e {

    /* renamed from: c  reason: collision with root package name */
    private final int f2057c;

    public o(h hVar, i iVar, int i) {
        super(hVar, iVar);
        if (i == 0 || i == 1) {
            throw new IllegalArgumentException("The scalar must not be 0 or 1");
        }
        this.f2057c = i;
    }

    @Override // d.a.a.h
    public long a(long j, int i) {
        return g().a(j, ((long) i) * ((long) this.f2057c));
    }

    @Override // d.a.a.h
    public long a(long j, long j2) {
        return g().a(j, g.a(j2, this.f2057c));
    }

    @Override // d.a.a.h
    public long c() {
        return g().c() * ((long) this.f2057c);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof o)) {
            return false;
        }
        o oVar = (o) obj;
        return g().equals(oVar.g()) && b() == oVar.b() && this.f2057c == oVar.f2057c;
    }

    public int hashCode() {
        long j = (long) this.f2057c;
        return ((int) (j ^ (j >>> 32))) + b().hashCode() + g().hashCode();
    }
}
