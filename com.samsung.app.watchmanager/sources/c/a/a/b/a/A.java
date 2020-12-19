package c.a.a.b.a;

import c.a.a.D;
import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.math.BigInteger;

class A extends J<BigInteger> {
    A() {
    }

    @Override // c.a.a.J
    public BigInteger a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        try {
            return new BigInteger(bVar.n());
        } catch (NumberFormatException e) {
            throw new D(e);
        }
    }

    public void a(d dVar, BigInteger bigInteger) {
        dVar.a(bigInteger);
    }
}
