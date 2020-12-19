package d.a.a.c;

import d.a.a.c;
import d.a.a.d;
import d.a.a.h;

public class n extends d {

    /* renamed from: c  reason: collision with root package name */
    final int f2055c;

    /* renamed from: d  reason: collision with root package name */
    final h f2056d;
    final h e;

    public n(f fVar) {
        this(fVar, fVar.g());
    }

    public n(f fVar, d dVar) {
        this(fVar, fVar.j().a(), dVar);
    }

    public n(f fVar, h hVar, d dVar) {
        super(fVar.j(), dVar);
        this.f2055c = fVar.f2043c;
        this.f2056d = hVar;
        this.e = fVar.f2044d;
    }

    public n(c cVar, h hVar, d dVar, int i) {
        super(cVar, dVar);
        if (i >= 2) {
            this.e = hVar;
            this.f2056d = cVar.a();
            this.f2055c = i;
            return;
        }
        throw new IllegalArgumentException("The divisor must be at least 2");
    }

    private int a(int i) {
        return i >= 0 ? i / this.f2055c : ((i + 1) / this.f2055c) - 1;
    }

    @Override // d.a.a.c, d.a.a.c.d
    public int a(long j) {
        int a2 = j().a(j);
        if (a2 >= 0) {
            return a2 % this.f2055c;
        }
        int i = this.f2055c;
        return (i - 1) + ((a2 + 1) % i);
    }

    @Override // d.a.a.c, d.a.a.c.d
    public h a() {
        return this.f2056d;
    }

    @Override // d.a.a.c, d.a.a.c.d
    public long b(long j, int i) {
        g.a(this, i, 0, this.f2055c - 1);
        return j().b(j, (a(j().a(j)) * this.f2055c) + i);
    }

    @Override // d.a.a.c
    public int c() {
        return this.f2055c - 1;
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

    @Override // d.a.a.c, d.a.a.c.b
    public long f(long j) {
        return j().f(j);
    }

    @Override // d.a.a.c, d.a.a.c.d
    public h f() {
        return this.e;
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
