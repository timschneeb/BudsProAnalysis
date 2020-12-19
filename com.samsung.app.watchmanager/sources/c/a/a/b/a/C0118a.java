package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.b.C0143b;
import c.a.a.c.a;
import c.a.a.p;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/* renamed from: c.a.a.b.a.a  reason: case insensitive filesystem */
class C0118a implements K {
    C0118a() {
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, a<T> aVar) {
        Type b2 = aVar.b();
        if (!(b2 instanceof GenericArrayType) && (!(b2 instanceof Class) || !((Class) b2).isArray())) {
            return null;
        }
        Type d2 = C0143b.d(b2);
        return new C0119b(pVar, pVar.a((a) a.a(d2)), C0143b.e(d2));
    }
}
