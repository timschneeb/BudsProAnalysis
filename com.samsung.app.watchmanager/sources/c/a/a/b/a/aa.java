package c.a.a.b.a;

import c.a.a.D;
import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

class aa extends J<Number> {
    aa() {
    }

    @Override // c.a.a.J
    public Number a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        try {
            return Long.valueOf(bVar.k());
        } catch (NumberFormatException e) {
            throw new D(e);
        }
    }

    public void a(d dVar, Number number) {
        dVar.a(number);
    }
}
