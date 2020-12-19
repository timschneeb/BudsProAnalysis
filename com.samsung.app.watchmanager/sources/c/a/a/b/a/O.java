package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.c.a;
import c.a.a.p;

/* access modifiers changed from: package-private */
public class O implements K {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Class f1546a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ J f1547b;

    O(Class cls, J j) {
        this.f1546a = cls;
        this.f1547b = j;
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, a<T> aVar) {
        if (aVar.a() == this.f1546a) {
            return this.f1547b;
        }
        return null;
    }

    public String toString() {
        return "Factory[type=" + this.f1546a.getName() + ",adapter=" + this.f1547b + "]";
    }
}
