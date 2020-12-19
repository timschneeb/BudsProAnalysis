package c.a.a.b.a;

import c.a.a.D;
import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

/* renamed from: c.a.a.b.a.x  reason: case insensitive filesystem */
class C0140x extends J<Character> {
    C0140x() {
    }

    @Override // c.a.a.J
    public Character a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        String n = bVar.n();
        if (n.length() == 1) {
            return Character.valueOf(n.charAt(0));
        }
        throw new D("Expecting character, got: " + n);
    }

    public void a(d dVar, Character ch) {
        dVar.c(ch == null ? null : String.valueOf(ch));
    }
}
