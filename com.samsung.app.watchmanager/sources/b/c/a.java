package b.c;

import java.util.Map;

/* access modifiers changed from: package-private */
public class a extends h<K, V> {

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ b f1285d;

    a(b bVar) {
        this.f1285d = bVar;
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public int a(Object obj) {
        return this.f1285d.a(obj);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public Object a(int i, int i2) {
        return this.f1285d.f[(i << 1) + i2];
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public V a(int i, V v) {
        return (V) this.f1285d.a(i, v);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public void a() {
        this.f1285d.clear();
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public void a(int i) {
        this.f1285d.c(i);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public void a(K k, V v) {
        this.f1285d.put(k, v);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public int b(Object obj) {
        return this.f1285d.b(obj);
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public Map<K, V> b() {
        return this.f1285d;
    }

    /* access modifiers changed from: protected */
    @Override // b.c.h
    public int c() {
        return this.f1285d.g;
    }
}
