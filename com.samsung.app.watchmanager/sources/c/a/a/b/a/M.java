package c.a.a.b.a;

import c.a.a.A;
import c.a.a.J;
import c.a.a.b.s;
import c.a.a.d.b;
import c.a.a.d.d;
import c.a.a.v;
import c.a.a.x;
import c.a.a.y;
import java.util.Iterator;
import java.util.Map;

class M extends J<v> {
    M() {
    }

    @Override // c.a.a.J
    public v a(b bVar) {
        switch (U.f1556a[bVar.o().ordinal()]) {
            case 1:
                return new A(new s(bVar.n()));
            case 2:
                return new A(Boolean.valueOf(bVar.h()));
            case 3:
                return new A(bVar.n());
            case 4:
                bVar.m();
                return x.f1689a;
            case 5:
                c.a.a.s sVar = new c.a.a.s();
                bVar.a();
                while (bVar.f()) {
                    sVar.a(a(bVar));
                }
                bVar.c();
                return sVar;
            case 6:
                y yVar = new y();
                bVar.b();
                while (bVar.f()) {
                    yVar.a(bVar.l(), a(bVar));
                }
                bVar.d();
                return yVar;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void a(d dVar, v vVar) {
        if (vVar == null || vVar.f()) {
            dVar.h();
        } else if (vVar.h()) {
            A c2 = vVar.c();
            if (c2.p()) {
                dVar.a(c2.n());
            } else if (c2.o()) {
                dVar.d(c2.i());
            } else {
                dVar.c(c2.d());
            }
        } else if (vVar.e()) {
            dVar.a();
            Iterator<v> it = vVar.a().iterator();
            while (it.hasNext()) {
                a(dVar, it.next());
            }
            dVar.c();
        } else if (vVar.g()) {
            dVar.b();
            for (Map.Entry<String, v> entry : vVar.b().i()) {
                dVar.a(entry.getKey());
                a(dVar, entry.getValue());
            }
            dVar.d();
        } else {
            throw new IllegalArgumentException("Couldn't write " + vVar.getClass());
        }
    }
}
