package d.a.a.c;

import d.a.a.d;
import d.a.a.h;

public class k extends l {

    /* renamed from: d  reason: collision with root package name */
    private final int f2051d;
    private final h e;

    public k(d dVar, h hVar, h hVar2) {
        super(dVar, hVar);
        if (hVar2.d()) {
            this.f2051d = (int) (hVar2.c() / j());
            if (this.f2051d >= 2) {
                this.e = hVar2;
                return;
            }
            throw new IllegalArgumentException("The effective range must be at least 2");
        }
        throw new IllegalArgumentException("Range duration field must be precise");
    }

    @Override // d.a.a.c
    public int a(long j) {
        return j >= 0 ? (int) ((j / j()) % ((long) this.f2051d)) : (this.f2051d - 1) + ((int) (((j + 1) / j()) % ((long) this.f2051d)));
    }

    @Override // d.a.a.c, d.a.a.c.l
    public long b(long j, int i) {
        g.a(this, i, d(), c());
        return j + (((long) (i - a(j))) * this.f2052b);
    }

    @Override // d.a.a.c
    public int c() {
        return this.f2051d - 1;
    }

    @Override // d.a.a.c
    public h f() {
        return this.e;
    }
}
