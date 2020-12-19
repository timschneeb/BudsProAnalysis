package c.a.a.b;

import c.a.a.J;
import c.a.a.c.a;
import c.a.a.d.b;
import c.a.a.d.d;

class p extends J<T> {

    /* renamed from: a  reason: collision with root package name */
    private J<T> f1628a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ boolean f1629b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ boolean f1630c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ c.a.a.p f1631d;
    final /* synthetic */ a e;
    final /* synthetic */ q f;

    p(q qVar, boolean z, boolean z2, c.a.a.p pVar, a aVar) {
        this.f = qVar;
        this.f1629b = z;
        this.f1630c = z2;
        this.f1631d = pVar;
        this.e = aVar;
    }

    private J<T> a() {
        J<T> j = this.f1628a;
        if (j != null) {
            return j;
        }
        J<T> a2 = this.f1631d.a(this.f, this.e);
        this.f1628a = a2;
        return a2;
    }

    @Override // c.a.a.J
    public T a(b bVar) {
        if (!this.f1629b) {
            return (T) a().a(bVar);
        }
        bVar.p();
        return null;
    }

    @Override // c.a.a.J
    public void a(d dVar, T t) {
        if (this.f1630c) {
            dVar.h();
        } else {
            a().a(dVar, t);
        }
    }
}
