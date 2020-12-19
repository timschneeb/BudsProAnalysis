package c.a.a;

import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;

/* access modifiers changed from: package-private */
public class n extends J<Number> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ p f1677a;

    n(p pVar) {
        this.f1677a = pVar;
    }

    /* Return type fixed from 'java.lang.Float' to match base method */
    @Override // c.a.a.J
    public Number a(b bVar) {
        if (bVar.o() != c.NULL) {
            return Float.valueOf((float) bVar.i());
        }
        bVar.m();
        return null;
    }

    public void a(d dVar, Number number) {
        if (number == null) {
            dVar.h();
            return;
        }
        this.f1677a.a((p) ((double) number.floatValue()));
        dVar.a(number);
    }
}
