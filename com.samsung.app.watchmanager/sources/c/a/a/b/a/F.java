package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

class F extends J<Class> {
    F() {
    }

    @Override // c.a.a.J
    public Class a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
    }

    public void a(d dVar, Class cls) {
        if (cls == null) {
            dVar.h();
            return;
        }
        throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + cls.getName() + ". Forgot to register a type adapter?");
    }
}
