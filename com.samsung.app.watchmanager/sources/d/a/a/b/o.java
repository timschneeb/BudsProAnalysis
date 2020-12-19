package d.a.a.b;

import d.a.a.c;
import d.a.a.c.d;
import d.a.a.c.g;
import d.a.a.h;

final class o extends d {

    /* renamed from: c  reason: collision with root package name */
    private final c f2031c;

    o(c cVar, c cVar2) {
        super(cVar, d.a.a.d.y());
        this.f2031c = cVar2;
    }

    @Override // d.a.a.c, d.a.a.c.d
    public int a(long j) {
        int a2 = j().a(j);
        return a2 <= 0 ? 1 - a2 : a2;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, int i) {
        return j().a(j, i);
    }

    @Override // d.a.a.c, d.a.a.c.d
    public long b(long j, int i) {
        g.a(this, i, 1, c());
        if (this.f2031c.i(j) <= 0) {
            i = 1 - i;
        }
        return super.b(j, i);
    }

    @Override // d.a.a.c
    public int c() {
        return j().c();
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

    @Override // d.a.a.c, d.a.a.c.d
    public h f() {
        return this.f2031c.j();
    }
}
