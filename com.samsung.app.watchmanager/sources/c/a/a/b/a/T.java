package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.c.a;
import c.a.a.p;

/* access modifiers changed from: package-private */
public class T implements K {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Class f1554a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ J f1555b;

    T(Class cls, J j) {
        this.f1554a = cls;
        this.f1555b = j;
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, a<T> aVar) {
        if (this.f1554a.isAssignableFrom(aVar.a())) {
            return this.f1555b;
        }
        return null;
    }

    public String toString() {
        return "Factory[typeHierarchy=" + this.f1554a.getName() + ",adapter=" + this.f1555b + "]";
    }
}
