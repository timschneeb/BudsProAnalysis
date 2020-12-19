package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.b.a.ca;
import c.a.a.c.a;
import c.a.a.p;

class N implements K {
    N() {
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, a<T> aVar) {
        Class<? super Object> a2 = aVar.a();
        if (!Enum.class.isAssignableFrom(a2) || a2 == Enum.class) {
            return null;
        }
        if (!a2.isEnum()) {
            a2 = a2.getSuperclass();
        }
        return new ca.a(a2);
    }
}
