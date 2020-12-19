package d.a.a.c;

import d.a.a.c;
import d.a.a.d;
import d.a.a.h;

public class f extends d {

    /* renamed from: c  reason: collision with root package name */
    final int f2043c;

    /* renamed from: d  reason: collision with root package name */
    final h f2044d;
    final h e;
    private final int f;
    private final int g;

    public f(c cVar, d dVar, int i) {
        this(cVar, cVar.f(), dVar, i);
    }

    public f(c cVar, h hVar, d dVar, int i) {
        super(cVar, dVar);
        if (i >= 2) {
            h a2 = cVar.a();
            if (a2 == null) {
                this.f2044d = null;
            } else {
                this.f2044d = new o(a2, dVar.h(), i);
            }
            this.e = hVar;
            this.f2043c = i;
            int d2 = cVar.d();
            int i2 = d2 >= 0 ? d2 / i : ((d2 + 1) / i) - 1;
            int c2 = cVar.c();
            int i3 = c2 >= 0 ? c2 / i : ((c2 + 1) / i) - 1;
            this.f = i2;
            this.g = i3;
            return;
        }
        throw new IllegalArgumentException("The divisor must be at least 2");
    }

    private int a(int i) {
        if (i >= 0) {
            return i % this.f2043c;
        }
        int i2 = this.f2043c;
        return (i2 - 1) + ((i + 1) % i2);
    }

    @Override // d.a.a.c, d.a.a.c.d
    public int a(long j) {
        int a2 = j().a(j);
        return a2 >= 0 ? a2 / this.f2043c : ((a2 + 1) / this.f2043c) - 1;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, int i) {
        return j().a(j, i * this.f2043c);
    }

    @Override // d.a.a.c, d.a.a.c.d
    public h a() {
        return this.f2044d;
    }

    @Override // d.a.a.c, d.a.a.c.d
    public long b(long j, int i) {
        g.a(this, i, this.f, this.g);
        return j().b(j, (i * this.f2043c) + a(j().a(j)));
    }

    @Override // d.a.a.c
    public int c() {
        return this.g;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long c(long j) {
        return b(j, a(j().c(j)));
    }

    @Override // d.a.a.c
    public int d() {
        return this.f;
    }

    @Override // d.a.a.c
    public long e(long j) {
        c j2 = j();
        return j2.e(j2.b(j, a(j) * this.f2043c));
    }

    @Override // d.a.a.c, d.a.a.c.d
    public h f() {
        h hVar = this.e;
        return hVar != null ? hVar : super.f();
    }
}
