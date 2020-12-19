package d.a.a.b;

import d.a.a.c.b;
import d.a.a.c.g;
import d.a.a.c.q;
import d.a.a.d;
import d.a.a.h;
import d.a.a.i;
import java.util.Locale;

final class l extends b {

    /* renamed from: b  reason: collision with root package name */
    private final c f2026b;

    l(c cVar) {
        super(d.g());
        this.f2026b = cVar;
    }

    @Override // d.a.a.c
    public int a(long j) {
        return this.f2026b.i(j) <= 0 ? 0 : 1;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public int a(Locale locale) {
        return m.a(locale).b();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, String str, Locale locale) {
        return b(j, m.a(locale).b(str));
    }

    @Override // d.a.a.c
    public h a() {
        return q.a(i.c());
    }

    @Override // d.a.a.c
    public long b(long j, int i) {
        g.a(this, i, 0, 1);
        if (a(j) == i) {
            return j;
        }
        return this.f2026b.f(j, -this.f2026b.i(j));
    }

    @Override // d.a.a.c, d.a.a.c.b
    public String b(int i, Locale locale) {
        return m.a(locale).c(i);
    }

    @Override // d.a.a.c
    public int c() {
        return 1;
    }

    @Override // d.a.a.c
    public int d() {
        return 0;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long d(long j) {
        if (a(j) == 0) {
            return this.f2026b.f(0, 1);
        }
        return Long.MAX_VALUE;
    }

    @Override // d.a.a.c
    public long e(long j) {
        if (a(j) == 1) {
            return this.f2026b.f(0, 1);
        }
        return Long.MIN_VALUE;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long f(long j) {
        return e(j);
    }

    @Override // d.a.a.c
    public h f() {
        return null;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long g(long j) {
        return e(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long h(long j) {
        return e(j);
    }

    @Override // d.a.a.c
    public boolean h() {
        return false;
    }
}
