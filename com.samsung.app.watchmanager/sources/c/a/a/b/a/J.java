package c.a.a.b.a;

import c.a.a.K;
import c.a.a.c.a;
import c.a.a.p;
import java.sql.Timestamp;
import java.util.Date;

class J implements K {
    J() {
    }

    @Override // c.a.a.K
    public <T> c.a.a.J<T> a(p pVar, a<T> aVar) {
        if (aVar.a() != Timestamp.class) {
            return null;
        }
        return new I(this, pVar.a((Class) Date.class));
    }
}
