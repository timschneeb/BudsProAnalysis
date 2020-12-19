package c.a.a.b;

import java.lang.reflect.Method;

/* access modifiers changed from: package-private */
public class C extends F {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Method f1541a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f1542b;

    C(Method method, int i) {
        this.f1541a = method;
        this.f1542b = i;
    }

    @Override // c.a.a.b.F
    public <T> T a(Class<T> cls) {
        return (T) this.f1541a.invoke(null, cls, Integer.valueOf(this.f1542b));
    }
}
