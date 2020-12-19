package d.a.a.b;

import d.a.a.c.g;
import d.a.a.c.h;
import d.a.a.d;

class j extends h {

    /* renamed from: d  reason: collision with root package name */
    protected final c f2024d;

    j(c cVar) {
        super(d.w(), cVar.R());
        this.f2024d = cVar;
    }

    @Override // d.a.a.c
    public int a(long j) {
        return this.f2024d.i(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, int i) {
        return i == 0 ? j : b(j, g.a(a(j), i));
    }

    @Override // d.a.a.c.h
    public long a(long j, long j2) {
        return a(j, g.a(j2));
    }

    @Override // d.a.a.c
    public long b(long j, int i) {
        g.a(this, i, this.f2024d.X(), this.f2024d.W());
        return this.f2024d.f(j, i);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public d.a.a.h b() {
        return this.f2024d.h();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public boolean b(long j) {
        return this.f2024d.g(a(j));
    }

    @Override // d.a.a.c
    public int c() {
        return this.f2024d.W();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long c(long j) {
        return j - e(j);
    }

    @Override // d.a.a.c
    public long c(long j, int i) {
        g.a(this, i, this.f2024d.X() - 1, this.f2024d.W() + 1);
        return this.f2024d.f(j, i);
    }

    @Override // d.a.a.c
    public int d() {
        return this.f2024d.X();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long d(long j) {
        int a2 = a(j);
        return j != this.f2024d.f(a2) ? this.f2024d.f(a2 + 1) : j;
    }

    @Override // d.a.a.c
    public long e(long j) {
        return this.f2024d.f(a(j));
    }

    @Override // d.a.a.c
    public d.a.a.h f() {
        return null;
    }

    @Override // d.a.a.c
    public boolean h() {
        return false;
    }
}
