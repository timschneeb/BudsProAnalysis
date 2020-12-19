package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.c.a;
import c.a.a.p;

/* access modifiers changed from: package-private */
public class S implements K {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Class f1551a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Class f1552b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ J f1553c;

    S(Class cls, Class cls2, J j) {
        this.f1551a = cls;
        this.f1552b = cls2;
        this.f1553c = j;
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, a<T> aVar) {
        Class<? super T> a2 = aVar.a();
        if (a2 == this.f1551a || a2 == this.f1552b) {
            return this.f1553c;
        }
        return null;
    }

    public String toString() {
        return "Factory[type=" + this.f1551a.getName() + "+" + this.f1552b.getName() + ",adapter=" + this.f1553c + "]";
    }
}
