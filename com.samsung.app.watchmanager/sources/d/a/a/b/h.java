package d.a.a.b;

import d.a.a.c.l;
import d.a.a.d;

final class h extends l {

    /* renamed from: d  reason: collision with root package name */
    private final c f2022d;

    h(c cVar, d.a.a.h hVar) {
        super(d.t(), hVar);
        this.f2022d = cVar;
    }

    @Override // d.a.a.c
    public int a(long j) {
        return this.f2022d.g(j);
    }

    @Override // d.a.a.c
    public int c() {
        return 53;
    }

    @Override // d.a.a.c, d.a.a.c.l, d.a.a.c.b
    public long c(long j) {
        return super.c(j + 259200000);
    }

    @Override // d.a.a.c, d.a.a.c.l
    public int d() {
        return 1;
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.c.l
    public int d(long j, int i) {
        if (i > 52) {
            return i(j);
        }
        return 52;
    }

    @Override // d.a.a.c, d.a.a.c.l, d.a.a.c.b
    public long d(long j) {
        return super.d(j + 259200000) - 259200000;
    }

    @Override // d.a.a.c, d.a.a.c.l
    public long e(long j) {
        return super.e(j + 259200000) - 259200000;
    }

    @Override // d.a.a.c
    public d.a.a.h f() {
        return this.f2022d.F();
    }

    @Override // d.a.a.c.b
    public int i(long j) {
        return this.f2022d.e(this.f2022d.h(j));
    }
}
