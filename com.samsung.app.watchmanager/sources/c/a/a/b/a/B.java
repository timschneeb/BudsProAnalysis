package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

class B extends J<StringBuilder> {
    B() {
    }

    @Override // c.a.a.J
    public StringBuilder a(b bVar) {
        if (bVar.o() != c.NULL) {
            return new StringBuilder(bVar.n());
        }
        bVar.m();
        return null;
    }

    public void a(d dVar, StringBuilder sb) {
        dVar.c(sb == null ? null : sb.toString());
    }
}
