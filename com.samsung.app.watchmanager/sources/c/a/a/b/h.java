package c.a.a.b;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* access modifiers changed from: package-private */
public class h implements x<T> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Constructor f1618a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ o f1619b;

    h(o oVar, Constructor constructor) {
        this.f1619b = oVar;
        this.f1618a = constructor;
    }

    @Override // c.a.a.b.x
    public T a() {
        try {
            return (T) this.f1618a.newInstance(null);
        } catch (InstantiationException e) {
            throw new RuntimeException("Failed to invoke " + this.f1618a + " with no args", e);
        } catch (InvocationTargetException e2) {
            throw new RuntimeException("Failed to invoke " + this.f1618a + " with no args", e2.getTargetException());
        } catch (IllegalAccessException e3) {
            throw new AssertionError(e3);
        }
    }
}
