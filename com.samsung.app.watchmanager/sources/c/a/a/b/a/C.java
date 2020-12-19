package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

class C extends J<StringBuffer> {
    C() {
    }

    @Override // c.a.a.J
    public StringBuffer a(b bVar) {
        if (bVar.o() != c.NULL) {
            return new StringBuffer(bVar.n());
        }
        bVar.m();
        return null;
    }

    public void a(d dVar, StringBuffer stringBuffer) {
        dVar.c(stringBuffer == null ? null : stringBuffer.toString());
    }
}
