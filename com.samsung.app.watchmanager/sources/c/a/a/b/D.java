package c.a.a.b;

import java.lang.reflect.Method;

/* access modifiers changed from: package-private */
public class D extends F {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Method f1543a;

    D(Method method) {
        this.f1543a = method;
    }

    @Override // c.a.a.b.F
    public <T> T a(Class<T> cls) {
        return (T) this.f1543a.invoke(null, cls, Object.class);
    }
}
