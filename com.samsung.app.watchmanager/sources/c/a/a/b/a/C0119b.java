package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import c.a.a.p;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* renamed from: c.a.a.b.a.b  reason: case insensitive filesystem */
public final class C0119b<E> extends J<Object> {

    /* renamed from: a  reason: collision with root package name */
    public static final K f1557a = new C0118a();

    /* renamed from: b  reason: collision with root package name */
    private final Class<E> f1558b;

    /* renamed from: c  reason: collision with root package name */
    private final J<E> f1559c;

    public C0119b(p pVar, J<E> j, Class<E> cls) {
        this.f1559c = new C0137u(pVar, j, cls);
        this.f1558b = cls;
    }

    @Override // c.a.a.J
    public Object a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        bVar.a();
        while (bVar.f()) {
            arrayList.add(this.f1559c.a(bVar));
        }
        bVar.c();
        Object newInstance = Array.newInstance((Class<?>) this.f1558b, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: c.a.a.J<E> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // c.a.a.J
    public void a(d dVar, Object obj) {
        if (obj == null) {
            dVar.h();
            return;
        }
        dVar.a();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.f1559c.a(dVar, Array.get(obj, i));
        }
        dVar.c();
    }
}
