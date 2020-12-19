package c.a.a.b;

import java.lang.reflect.Method;

/* access modifiers changed from: package-private */
public class B extends F {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Method f1539a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Object f1540b;

    B(Method method, Object obj) {
        this.f1539a = method;
        this.f1540b = obj;
    }

    @Override // c.a.a.b.F
    public <T> T a(Class<T> cls) {
        return (T) this.f1539a.invoke(this.f1540b, cls);
    }
}
