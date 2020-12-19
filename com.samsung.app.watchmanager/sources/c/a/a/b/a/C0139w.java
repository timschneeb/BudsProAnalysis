package c.a.a.b.a;

import c.a.a.D;
import c.a.a.J;
import c.a.a.b.s;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

/* renamed from: c.a.a.b.a.w  reason: case insensitive filesystem */
class C0139w extends J<Number> {
    C0139w() {
    }

    @Override // c.a.a.J
    public Number a(b bVar) {
        c o = bVar.o();
        int i = U.f1556a[o.ordinal()];
        if (i == 1) {
            return new s(bVar.n());
        }
        if (i == 4) {
            bVar.m();
            return null;
        }
        throw new D("Expecting number, got: " + o);
    }

    public void a(d dVar, Number number) {
        dVar.a(number);
    }
}
