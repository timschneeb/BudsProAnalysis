package c.a.a.b.a;

import c.a.a.J;
import c.a.a.b.a.C0133p;
import c.a.a.c.a;
import c.a.a.d.b;
import c.a.a.d.d;
import c.a.a.p;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* access modifiers changed from: package-private */
/* renamed from: c.a.a.b.a.u  reason: case insensitive filesystem */
public final class C0137u<T> extends J<T> {

    /* renamed from: a  reason: collision with root package name */
    private final p f1596a;

    /* renamed from: b  reason: collision with root package name */
    private final J<T> f1597b;

    /* renamed from: c  reason: collision with root package name */
    private final Type f1598c;

    C0137u(p pVar, J<T> j, Type type) {
        this.f1596a = pVar;
        this.f1597b = j;
        this.f1598c = type;
    }

    private Type a(Type type, Object obj) {
        return obj != null ? (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type : type;
    }

    @Override // c.a.a.J
    public T a(b bVar) {
        return this.f1597b.a(bVar);
    }

    @Override // c.a.a.J
    public void a(d dVar, T t) {
        J<T> j = this.f1597b;
        Type a2 = a(this.f1598c, t);
        if (a2 != this.f1598c) {
            j = this.f1596a.a((a) a.a(a2));
            if (j instanceof C0133p.a) {
                J<T> j2 = this.f1597b;
                if (!(j2 instanceof C0133p.a)) {
                    j = j2;
                }
            }
        }
        j.a(dVar, t);
    }
}
