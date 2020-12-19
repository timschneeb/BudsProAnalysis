package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.b.v;
import c.a.a.d.b;
import c.a.a.d.d;
import c.a.a.p;
import java.util.ArrayList;

/* renamed from: c.a.a.b.a.n  reason: case insensitive filesystem */
public final class C0131n extends J<Object> {

    /* renamed from: a  reason: collision with root package name */
    public static final K f1581a = new C0129l();

    /* renamed from: b  reason: collision with root package name */
    private final p f1582b;

    private C0131n(p pVar) {
        this.f1582b = pVar;
    }

    /* synthetic */ C0131n(p pVar, C0129l lVar) {
        this(pVar);
    }

    @Override // c.a.a.J
    public Object a(b bVar) {
        switch (C0130m.f1580a[bVar.o().ordinal()]) {
            case 1:
                ArrayList arrayList = new ArrayList();
                bVar.a();
                while (bVar.f()) {
                    arrayList.add(a(bVar));
                }
                bVar.c();
                return arrayList;
            case 2:
                v vVar = new v();
                bVar.b();
                while (bVar.f()) {
                    vVar.put(bVar.l(), a(bVar));
                }
                bVar.d();
                return vVar;
            case 3:
                return bVar.n();
            case 4:
                return Double.valueOf(bVar.i());
            case 5:
                return Boolean.valueOf(bVar.h());
            case 6:
                bVar.m();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    @Override // c.a.a.J
    public void a(d dVar, Object obj) {
        if (obj == null) {
            dVar.h();
            return;
        }
        J a2 = this.f1582b.a((Class) obj.getClass());
        if (a2 instanceof C0131n) {
            dVar.b();
            dVar.d();
            return;
        }
        a2.a(dVar, obj);
    }
}
