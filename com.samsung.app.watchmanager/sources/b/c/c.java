package b.c;

import java.util.Map;

/* access modifiers changed from: package-private */
public class c extends h<E, E> {

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ d f1286d;

    c(d dVar) {
        this.f1286d = dVar;
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public int a(Object obj) {
        return this.f1286d.indexOf(obj);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public Object a(int i, int i2) {
        return this.f1286d.h[i];
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public E a(int i, E e) {
        throw new UnsupportedOperationException("not a map");
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public void a() {
        this.f1286d.clear();
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public void a(int i) {
        this.f1286d.b(i);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public void a(E e, E e2) {
        this.f1286d.add(e);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public int b(Object obj) {
        return this.f1286d.indexOf(obj);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public Map<E, E> b() {
        throw new UnsupportedOperationException("not a map");
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public int c() {
        return this.f1286d.i;
    }
}
