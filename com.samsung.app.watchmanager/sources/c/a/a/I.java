package c.a.a;

import c.a.a.b.A;
import c.a.a.b.C0117a;
import c.a.a.d.b;
import c.a.a.d.d;

final class I<T> extends J<T> {

    /* renamed from: a  reason: collision with root package name */
    private final C<T> f1525a;

    /* renamed from: b  reason: collision with root package name */
    private final u<T> f1526b;

    /* renamed from: c  reason: collision with root package name */
    private final p f1527c;

    /* renamed from: d  reason: collision with root package name */
    private final c.a.a.c.a<T> f1528d;
    private final K e;
    private J<T> f;

    private static class a implements K {

        /* renamed from: a  reason: collision with root package name */
        private final c.a.a.c.a<?> f1529a;

        /* renamed from: b  reason: collision with root package name */
        private final boolean f1530b;

        /* renamed from: c  reason: collision with root package name */
        private final Class<?> f1531c;

        /* renamed from: d  reason: collision with root package name */
        private final C<?> f1532d;
        private final u<?> e;

        private a(Object obj, c.a.a.c.a<?> aVar, boolean z, Class<?> cls) {
            u<?> uVar = null;
            this.f1532d = obj instanceof C ? (C) obj : null;
            this.e = obj instanceof u ? (u) obj : uVar;
            C0117a.a((this.f1532d == null && this.e == null) ? false : true);
            this.f1529a = aVar;
            this.f1530b = z;
            this.f1531c = cls;
        }

        @Override // c.a.a.K
        public <T> J<T> a(p pVar, c.a.a.c.a<T> aVar) {
            c.a.a.c.a<?> aVar2 = this.f1529a;
            if (aVar2 != null ? aVar2.equals(aVar) || (this.f1530b && this.f1529a.b() == aVar.a()) : this.f1531c.isAssignableFrom(aVar.a())) {
                return new I(this.f1532d, this.e, pVar, aVar, this);
            }
            return null;
        }
    }

    private I(C<T> c2, u<T> uVar, p pVar, c.a.a.c.a<T> aVar, K k) {
        this.f1525a = c2;
        this.f1526b = uVar;
        this.f1527c = pVar;
        this.f1528d = aVar;
        this.e = k;
    }

    private J<T> a() {
        J<T> j = this.f;
        if (j != null) {
            return j;
        }
        J<T> a2 = this.f1527c.a(this.e, this.f1528d);
        this.f = a2;
        return a2;
    }

    public static K a(c.a.a.c.a<?> aVar, Object obj) {
        return new a(obj, aVar, false, null);
    }

    @Override // c.a.a.J
    public T a(b bVar) {
        if (this.f1526b == null) {
            return a().a(bVar);
        }
        v a2 = A.a(bVar);
        if (a2.f()) {
            return null;
        }
        return this.f1526b.a(a2, this.f1528d.b(), this.f1527c.i);
    }

    @Override // c.a.a.J
    public void a(d dVar, T t) {
        C<T> c2 = this.f1525a;
        if (c2 == null) {
            a().a(dVar, t);
        } else if (t == null) {
            dVar.h();
        } else {
            A.a(c2.a(t, this.f1528d.b(), this.f1527c.j), dVar);
        }
    }
}
