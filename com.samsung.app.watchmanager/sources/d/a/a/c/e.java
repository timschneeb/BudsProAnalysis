package d.a.a.c;

import d.a.a.h;
import d.a.a.i;

public class e extends c {

    /* renamed from: b  reason: collision with root package name */
    private final h f2042b;

    public e(h hVar, i iVar) {
        super(iVar);
        if (hVar == null) {
            throw new IllegalArgumentException("The field must not be null");
        } else if (hVar.e()) {
            this.f2042b = hVar;
        } else {
            throw new IllegalArgumentException("The field must be supported");
        }
    }

    @Override // d.a.a.h
    public boolean d() {
        return this.f2042b.d();
    }

    public final h g() {
        return this.f2042b;
    }
}
