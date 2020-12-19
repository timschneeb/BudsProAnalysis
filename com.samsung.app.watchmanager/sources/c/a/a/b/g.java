package c.a.a.b;

import c.a.a.r;
import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public class g implements x<T> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ r f1615a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Type f1616b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ o f1617c;

    g(o oVar, r rVar, Type type) {
        this.f1617c = oVar;
        this.f1615a = rVar;
        this.f1616b = type;
    }

    @Override // c.a.a.b.x
    public T a() {
        return (T) this.f1615a.a(this.f1616b);
    }
}
