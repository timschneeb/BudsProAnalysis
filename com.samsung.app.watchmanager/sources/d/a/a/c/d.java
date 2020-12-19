package d.a.a.c;

import d.a.a.c;
import d.a.a.h;

public abstract class d extends b {

    /* renamed from: b  reason: collision with root package name */
    private final c f2041b;

    protected d(c cVar, d.a.a.d dVar) {
        super(dVar);
        if (cVar == null) {
            throw new IllegalArgumentException("The field must not be null");
        } else if (cVar.i()) {
            this.f2041b = cVar;
        } else {
            throw new IllegalArgumentException("The field must be supported");
        }
    }

    @Override // d.a.a.c
    public int a(long j) {
        return this.f2041b.a(j);
    }

    @Override // d.a.a.c
    public h a() {
        return this.f2041b.a();
    }

    @Override // d.a.a.c
    public long b(long j, int i) {
        return this.f2041b.b(j, i);
    }

    @Override // d.a.a.c
    public h f() {
        return this.f2041b.f();
    }

    @Override // d.a.a.c
    public boolean h() {
        return this.f2041b.h();
    }

    public final c j() {
        return this.f2041b;
    }
}
