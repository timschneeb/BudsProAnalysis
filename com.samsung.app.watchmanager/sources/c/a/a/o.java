package c.a.a;

import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

/* access modifiers changed from: package-private */
public class o extends J<Number> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ p f1678a;

    o(p pVar) {
        this.f1678a = pVar;
    }

    @Override // c.a.a.J
    public Number a(b bVar) {
        if (bVar.o() != c.NULL) {
            return Long.valueOf(bVar.k());
        }
        bVar.m();
        return null;
    }

    public void a(d dVar, Number number) {
        if (number == null) {
            dVar.h();
        } else {
            dVar.c(number.toString());
        }
    }
}
