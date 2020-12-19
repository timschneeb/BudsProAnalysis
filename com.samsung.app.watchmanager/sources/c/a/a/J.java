package c.a.a;

import c.a.a.b.a.C0127j;
import c.a.a.d.b;
import c.a.a.d.d;
import java.io.IOException;

public abstract class J<T> {
    public final v a(T t) {
        try {
            C0127j jVar = new C0127j();
            a(jVar, t);
            return jVar.i();
        } catch (IOException e) {
            throw new w(e);
        }
    }

    public abstract T a(b bVar);

    public abstract void a(d dVar, T t);
}
