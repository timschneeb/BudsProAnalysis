package c.a.a.b.a;

import c.a.a.D;
import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

class Z extends J<Number> {
    Z() {
    }

    @Override // c.a.a.J
    public Number a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        try {
            return Integer.valueOf(bVar.j());
        } catch (NumberFormatException e) {
            throw new D(e);
        }
    }

    public void a(d dVar, Number number) {
        dVar.a(number);
    }
}
