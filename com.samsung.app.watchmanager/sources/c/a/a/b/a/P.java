package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.c.a;
import c.a.a.p;

/* access modifiers changed from: package-private */
public class P implements K {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Class f1548a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Class f1549b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ J f1550c;

    P(Class cls, Class cls2, J j) {
        this.f1548a = cls;
        this.f1549b = cls2;
        this.f1550c = j;
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, a<T> aVar) {
        Class<? super T> a2 = aVar.a();
        if (a2 == this.f1548a || a2 == this.f1549b) {
            return this.f1550c;
        }
        return null;
    }

    public String toString() {
        return "Factory[type=" + this.f1549b.getName() + "+" + this.f1548a.getName() + ",adapter=" + this.f1550c + "]";
    }
}
