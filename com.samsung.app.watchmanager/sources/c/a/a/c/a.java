package c.a.a.c;

import c.a.a.b.C0117a;
import c.a.a.b.C0143b;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class a<T> {

    /* renamed from: a  reason: collision with root package name */
    final Class<? super T> f1655a;

    /* renamed from: b  reason: collision with root package name */
    final Type f1656b;

    /* renamed from: c  reason: collision with root package name */
    final int f1657c;

    protected a() {
        this.f1656b = b(a.class);
        this.f1655a = (Class<? super T>) C0143b.e(this.f1656b);
        this.f1657c = this.f1656b.hashCode();
    }

    a(Type type) {
        C0117a.a(type);
        this.f1656b = C0143b.c(type);
        this.f1655a = (Class<? super T>) C0143b.e(this.f1656b);
        this.f1657c = this.f1656b.hashCode();
    }

    public static <T> a<T> a(Class<T> cls) {
        return new a<>(cls);
    }

    public static a<?> a(Type type) {
        return new a<>(type);
    }

    static Type b(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof Class)) {
            return C0143b.c(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    public final Class<? super T> a() {
        return this.f1655a;
    }

    public final Type b() {
        return this.f1656b;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof a) && C0143b.a(this.f1656b, ((a) obj).f1656b);
    }

    public final int hashCode() {
        return this.f1657c;
    }

    public final String toString() {
        return C0143b.h(this.f1656b);
    }
}
