package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

class V extends J<Boolean> {
    V() {
    }

    @Override // c.a.a.J
    public Boolean a(b bVar) {
        if (bVar.o() != c.NULL) {
            return bVar.o() == c.STRING ? Boolean.valueOf(Boolean.parseBoolean(bVar.n())) : Boolean.valueOf(bVar.h());
        }
        bVar.m();
        return null;
    }

    public void a(d dVar, Boolean bool) {
        if (bool == null) {
            dVar.h();
        } else {
            dVar.d(bool.booleanValue());
        }
    }
}
