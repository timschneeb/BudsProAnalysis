package d.a.a.b;

import d.a.a.c.g;
import d.a.a.c.h;
import d.a.a.d;

final class i extends h {

    /* renamed from: d  reason: collision with root package name */
    private final c f2023d;

    i(c cVar) {
        super(d.u(), cVar.R());
        this.f2023d = cVar;
    }

    @Override // d.a.a.c
    public int a(long j) {
        return this.f2023d.h(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, int i) {
        return i == 0 ? j : b(j, a(j) + i);
    }

    @Override // d.a.a.c.h
    public long a(long j, long j2) {
        return a(j, g.a(j2));
    }

    @Override // d.a.a.c
    public long b(long j, int i) {
        g.a(this, Math.abs(i), this.f2023d.X(), this.f2023d.W());
        int a2 = a(j);
        if (a2 == i) {
            return j;
        }
        int b2 = this.f2023d.b(j);
        int e = this.f2023d.e(a2);
        int e2 = this.f2023d.e(i);
        if (e2 < e) {
            e = e2;
        }
        int g = this.f2023d.g(j);
        if (g <= e) {
            e = g;
        }
        long f = this.f2023d.f(j, i);
        int a3 = a(f);
        if (a3 < i) {
            f += 604800000;
        } else if (a3 > i) {
            f -= 604800000;
        }
        return this.f2023d.f().b(f + (((long) (e - this.f2023d.g(f))) * 604800000), b2);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public d.a.a.h b() {
        return this.f2023d.C();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public boolean b(long j) {
        c cVar = this.f2023d;
        return cVar.e(cVar.h(j)) > 52;
    }

    @Override // d.a.a.c
    public int c() {
        return this.f2023d.W();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long c(long j) {
        return j - e(j);
    }

    @Override // d.a.a.c
    public int d() {
        return this.f2023d.X();
    }

    @Override // d.a.a.c
    public long e(long j) {
        long e = this.f2023d.B().e(j);
        int g = this.f2023d.g(e);
        return g > 1 ? e - (((long) (g - 1)) * 604800000) : e;
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
