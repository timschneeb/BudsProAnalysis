package d.a.a.b;

import d.a.a.c;
import d.a.a.c.d;
import d.a.a.c.g;
import d.a.a.h;

class r extends d {

    /* renamed from: c  reason: collision with root package name */
    static final c f2032c = new r();

    private r() {
        super(p.Z().H(), d.a.a.d.y());
    }

    @Override // d.a.a.c, d.a.a.c.d
    public int a(long j) {
        int a2 = j().a(j);
        return a2 < 0 ? -a2 : a2;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, int i) {
        return j().a(j, i);
    }

    @Override // d.a.a.c, d.a.a.c.d
    public long b(long j, int i) {
        g.a(this, i, 0, c());
        if (j().a(j) < 0) {
            i = -i;
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
        return 0;
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
        return p.Z().j();
    }
}
