package c.a.a;

import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

/* access modifiers changed from: package-private */
public class m extends J<Number> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ p f1676a;

    m(p pVar) {
        this.f1676a = pVar;
    }

    /* Return type fixed from 'java.lang.Double' to match base method */
    @Override // c.a.a.J
    public Number a(b bVar) {
        if (bVar.o() != c.NULL) {
            return Double.valueOf(bVar.i());
        }
        bVar.m();
        return null;
    }

    public void a(d dVar, Number number) {
        if (number == null) {
            dVar.h();
            return;
        }
        this.f1676a.a((p) number.doubleValue());
        dVar.a(number);
    }
}
