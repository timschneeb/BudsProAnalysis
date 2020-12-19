package c.a.a.b;

import c.a.a.c.a;
import c.a.a.r;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public final class o {

    /* renamed from: a  reason: collision with root package name */
    private final Map<Type, r<?>> f1627a;

    public o(Map<Type, r<?>> map) {
        this.f1627a = map;
    }

    private <T> x<T> a(Class<? super T> cls) {
        try {
            Constructor<? super T> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new h(this, declaredConstructor);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    private <T> x<T> a(Type type, Class<? super T> cls) {
        if (Collection.class.isAssignableFrom(cls)) {
            return SortedSet.class.isAssignableFrom(cls) ? new i(this) : EnumSet.class.isAssignableFrom(cls) ? new j(this, type) : Set.class.isAssignableFrom(cls) ? new k(this) : Queue.class.isAssignableFrom(cls) ? new l(this) : new m(this);
        }
        if (Map.class.isAssignableFrom(cls)) {
            return SortedMap.class.isAssignableFrom(cls) ? new n(this) : (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(a.a(((ParameterizedType) type).getActualTypeArguments()[0]).a())) ? new C0145d(this) : new C0144c(this);
        }
        return null;
    }

    private <T> x<T> b(Type type, Class<? super T> cls) {
        return new C0146e(this, cls, type);
    }

    public <T> x<T> a(a<T> aVar) {
        Type b2 = aVar.b();
        Class<? super T> a2 = aVar.a();
        r<?> rVar = this.f1627a.get(b2);
        if (rVar != null) {
            return new C0147f(this, rVar, b2);
        }
        r<?> rVar2 = this.f1627a.get(a2);
        if (rVar2 != null) {
            return new g(this, rVar2, b2);
        }
        x<T> a3 = a(a2);
        if (a3 != null) {
            return a3;
        }
        x<T> a4 = a(b2, a2);
        return a4 != null ? a4 : b(b2, a2);
    }

    public String toString() {
        return this.f1627a.toString();
    }
}
