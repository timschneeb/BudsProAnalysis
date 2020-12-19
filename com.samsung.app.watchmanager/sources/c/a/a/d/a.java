package c.a.a.d;

import c.a.a.b.a.C0125h;
import c.a.a.b.r;

class a extends r {
    a() {
    }

    @Override // c.a.a.b.r
    public void a(b bVar) {
        int i;
        if (bVar instanceof C0125h) {
            ((C0125h) bVar).q();
            return;
        }
        int i2 = bVar.i;
        if (i2 == 0) {
            i2 = bVar.s();
        }
        if (i2 == 13) {
            i = 9;
        } else if (i2 == 12) {
            i = 8;
        } else if (i2 == 14) {
            i = 10;
        } else {
            throw new IllegalStateException("Expected a name but was " + bVar.o() + " " + " at line " + bVar.u() + " column " + bVar.t() + " path " + bVar.e());
        }
        bVar.i = i;
    }
}
