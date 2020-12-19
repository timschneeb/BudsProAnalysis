package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.a.b;
import c.a.a.b.o;
import c.a.a.c.a;
import c.a.a.p;

/* renamed from: c.a.a.b.a.f  reason: case insensitive filesystem */
public final class C0123f implements K {

    /* renamed from: a  reason: collision with root package name */
    private final o f1573a;

    public C0123f(o oVar) {
        this.f1573a = oVar;
    }

    static J<?> a(o oVar, p pVar, a<?> aVar, b bVar) {
        Class<?> value = bVar.value();
        if (J.class.isAssignableFrom(value)) {
            return (J) oVar.a(a.a((Class) value)).a();
        }
        if (K.class.isAssignableFrom(value)) {
            return ((K) oVar.a(a.a((Class) value)).a()).a(pVar, aVar);
        }
        throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, a<T> aVar) {
        b bVar = (b) aVar.a().getAnnotation(b.class);
        if (bVar == null) {
            return null;
        }
        return (J<T>) a(this.f1573a, pVar, aVar, bVar);
    }
}
