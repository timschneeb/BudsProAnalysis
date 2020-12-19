package d.a.a.b;

import d.a.a.c.l;
import d.a.a.d;
import d.a.a.h;

final class e extends l {

    /* renamed from: d  reason: collision with root package name */
    private final c f2020d;

    e(c cVar, h hVar) {
        super(d.f(), hVar);
        this.f2020d = cVar;
    }

    @Override // d.a.a.c
    public int a(long j) {
        return this.f2020d.c(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public boolean b(long j) {
        return this.f2020d.j(j);
    }

    @Override // d.a.a.c
    public int c() {
        return this.f2020d.U();
    }

    @Override // d.a.a.c, d.a.a.c.l
    public int d() {
        return 1;
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.c.l
    public int d(long j, int i) {
        int U = this.f2020d.U() - 1;
        return (i > U || i < 1) ? i(j) : U;
    }

    @Override // d.a.a.c
    public h f() {
        return this.f2020d.K();
    }

    @Override // d.a.a.c.b
    public int i(long j) {
        return this.f2020d.b(this.f2020d.i(j));
    }
}
