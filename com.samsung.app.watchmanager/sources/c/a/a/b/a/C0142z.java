package c.a.a.b.a;

import c.a.a.D;
import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.math.BigDecimal;

/* renamed from: c.a.a.b.a.z  reason: case insensitive filesystem */
class C0142z extends J<BigDecimal> {
    C0142z() {
    }

    @Override // c.a.a.J
    public BigDecimal a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        try {
            return new BigDecimal(bVar.n());
        } catch (NumberFormatException e) {
            throw new D(e);
        }
    }

    public void a(d dVar, BigDecimal bigDecimal) {
        dVar.a(bigDecimal);
    }
}
