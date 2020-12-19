package d.a.a.c;

import d.a.a.c;
import d.a.a.d;
import d.a.a.h;

public final class r extends d {
    public r(c cVar, d dVar) {
        super(cVar, dVar);
        if (cVar.d() != 0) {
            throw new IllegalArgumentException("Wrapped field's minumum value must be zero");
        }
    }

    @Override // d.a.a.c, d.a.a.c.d
    public int a(long j) {
        int a2 = j().a(j);
        return a2 == 0 ? c() : a2;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, int i) {
        return j().a(j, i);
    }

    @Override // d.a.a.c, d.a.a.c.d
    public long b(long j, int i) {
        int c2 = c();
        g.a(this, i, 1, c2);
        if (i == c2) {
            i = 0;
        }
        return j().b(j, i);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public h b() {
        return j().b();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public boolean b(long j) {
        return j().b(j);
    }

    @Override // d.a.a.c
    public int c() {
        return j().c() + 1;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long c(long j) {
        return j().c(j);
    }

    @Override // d.a.a.c
    public int d() {
        return 1;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long d(long j) {
        return j().d(j);
    }

    @Override // d.a.a.c
    public long e(long j) {
        return j().e(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long f(long j) {
        return j().f(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long g(long j) {
        return j().g(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long h(long j) {
        return j().h(j);
    }
}
