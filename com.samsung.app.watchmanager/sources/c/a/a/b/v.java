package c.a.a.b;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class v<K, V> extends AbstractMap<K, V> implements Serializable {

    /* renamed from: a  reason: collision with root package name */
    private static final Comparator<Comparable> f1638a = new t();

    /* renamed from: b  reason: collision with root package name */
    Comparator<? super K> f1639b;

    /* renamed from: c  reason: collision with root package name */
    d<K, V> f1640c;

    /* renamed from: d  reason: collision with root package name */
    int f1641d;
    int e;
    final d<K, V> f;
    private v<K, V>.a g;
    private v<K, V>.b h;

    /* access modifiers changed from: package-private */
    public class a extends AbstractSet<Map.Entry<K, V>> {
        a() {
        }

        public void clear() {
            v.this.clear();
        }

        public boolean contains(Object obj) {
            return (obj instanceof Map.Entry) && v.this.a((Map.Entry) obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new u(this);
        }

        public boolean remove(Object obj) {
            d<K, V> a2;
            if (!(obj instanceof Map.Entry) || (a2 = v.this.a((Map.Entry) obj)) == null) {
                return false;
            }
            v.this.a((d) a2, true);
            return true;
        }

        public int size() {
            return v.this.f1641d;
        }
    }

    final class b extends AbstractSet<K> {
        b() {
        }

        public void clear() {
            v.this.clear();
        }

        public boolean contains(Object obj) {
            return v.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set, java.lang.Iterable
        public Iterator<K> iterator() {
            return new w(this);
        }

        public boolean remove(Object obj) {
            return v.this.b(obj) != null;
        }

        public int size() {
            return v.this.f1641d;
        }
    }

    /* access modifiers changed from: private */
    public abstract class c<T> implements Iterator<T> {

        /* renamed from: a  reason: collision with root package name */
        d<K, V> f1644a;

        /* renamed from: b  reason: collision with root package name */
        d<K, V> f1645b;

        /* renamed from: c  reason: collision with root package name */
        int f1646c;

        private c() {
            v vVar = v.this;
            this.f1644a = vVar.f.f1651d;
            this.f1645b = null;
            this.f1646c = vVar.e;
        }

        /* synthetic */ c(v vVar, t tVar) {
            this();
        }

        /* access modifiers changed from: package-private */
        public final d<K, V> a() {
            d<K, V> dVar = this.f1644a;
            v vVar = v.this;
            if (dVar == vVar.f) {
                throw new NoSuchElementException();
            } else if (vVar.e == this.f1646c) {
                this.f1644a = dVar.f1651d;
                this.f1645b = dVar;
                return dVar;
            } else {
                throw new ConcurrentModificationException();
            }
        }

        public final boolean hasNext() {
            return this.f1644a != v.this.f;
        }

        public final void remove() {
            d<K, V> dVar = this.f1645b;
            if (dVar != null) {
                v.this.a((d) dVar, true);
                this.f1645b = null;
                this.f1646c = v.this.e;
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: package-private */
    public static final class d<K, V> implements Map.Entry<K, V> {

        /* renamed from: a  reason: collision with root package name */
        d<K, V> f1648a;

        /* renamed from: b  reason: collision with root package name */
        d<K, V> f1649b;

        /* renamed from: c  reason: collision with root package name */
        d<K, V> f1650c;

        /* renamed from: d  reason: collision with root package name */
        d<K, V> f1651d;
        d<K, V> e;
        final K f;
        V g;
        int h;

        d() {
            this.f = null;
            this.e = this;
            this.f1651d = this;
        }

        d(d<K, V> dVar, K k, d<K, V> dVar2, d<K, V> dVar3) {
            this.f1648a = dVar;
            this.f = k;
            this.h = 1;
            this.f1651d = dVar2;
            this.e = dVar3;
            dVar3.f1651d = this;
            dVar2.e = this;
        }

        public d<K, V> a() {
            d<K, V> dVar = this;
            for (d<K, V> dVar2 = this.f1649b; dVar2 != null; dVar2 = dVar2.f1649b) {
                dVar = dVar2;
            }
            return dVar;
        }

        public d<K, V> b() {
            d<K, V> dVar = this;
            for (d<K, V> dVar2 = this.f1650c; dVar2 != null; dVar2 = dVar2.f1650c) {
                dVar = dVar2;
            }
            return dVar;
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0031 A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean equals(java.lang.Object r4) {
            /*
                r3 = this;
                boolean r0 = r4 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 == 0) goto L_0x0032
                java.util.Map$Entry r4 = (java.util.Map.Entry) r4
                K r0 = r3.f
                if (r0 != 0) goto L_0x0012
                java.lang.Object r0 = r4.getKey()
                if (r0 != 0) goto L_0x0032
                goto L_0x001c
            L_0x0012:
                java.lang.Object r2 = r4.getKey()
                boolean r0 = r0.equals(r2)
                if (r0 == 0) goto L_0x0032
            L_0x001c:
                V r0 = r3.g
                if (r0 != 0) goto L_0x0027
                java.lang.Object r4 = r4.getValue()
                if (r4 != 0) goto L_0x0032
                goto L_0x0031
            L_0x0027:
                java.lang.Object r4 = r4.getValue()
                boolean r4 = r0.equals(r4)
                if (r4 == 0) goto L_0x0032
            L_0x0031:
                r1 = 1
            L_0x0032:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: c.a.a.b.v.d.equals(java.lang.Object):boolean");
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.f;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.g;
        }

        public int hashCode() {
            K k = this.f;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.g;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = this.g;
            this.g = v;
            return v2;
        }

        public String toString() {
            return ((Object) this.f) + "=" + ((Object) this.g);
        }
    }

    public v() {
        this(f1638a);
    }

    public v(Comparator<? super K> comparator) {
        this.f1641d = 0;
        this.e = 0;
        this.f = new d<>();
        this.f1639b = comparator == null ? f1638a : comparator;
    }

    private void a(d<K, V> dVar) {
        d<K, V> dVar2 = dVar.f1649b;
        d<K, V> dVar3 = dVar.f1650c;
        d<K, V> dVar4 = dVar3.f1649b;
        d<K, V> dVar5 = dVar3.f1650c;
        dVar.f1650c = dVar4;
        if (dVar4 != null) {
            dVar4.f1648a = dVar;
        }
        a((d) dVar, (d) dVar3);
        dVar3.f1649b = dVar;
        dVar.f1648a = dVar3;
        int i = 0;
        dVar.h = Math.max(dVar2 != null ? dVar2.h : 0, dVar4 != null ? dVar4.h : 0) + 1;
        int i2 = dVar.h;
        if (dVar5 != null) {
            i = dVar5.h;
        }
        dVar3.h = Math.max(i2, i) + 1;
    }

    private void a(d<K, V> dVar, d<K, V> dVar2) {
        d<K, V> dVar3 = dVar.f1648a;
        dVar.f1648a = null;
        if (dVar2 != null) {
            dVar2.f1648a = dVar3;
        }
        if (dVar3 == null) {
            this.f1640c = dVar2;
        } else if (dVar3.f1649b == dVar) {
            dVar3.f1649b = dVar2;
        } else {
            dVar3.f1650c = dVar2;
        }
    }

    private boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private void b(d<K, V> dVar) {
        d<K, V> dVar2 = dVar.f1649b;
        d<K, V> dVar3 = dVar.f1650c;
        d<K, V> dVar4 = dVar2.f1649b;
        d<K, V> dVar5 = dVar2.f1650c;
        dVar.f1649b = dVar5;
        if (dVar5 != null) {
            dVar5.f1648a = dVar;
        }
        a((d) dVar, (d) dVar2);
        dVar2.f1650c = dVar;
        dVar.f1648a = dVar2;
        int i = 0;
        dVar.h = Math.max(dVar3 != null ? dVar3.h : 0, dVar5 != null ? dVar5.h : 0) + 1;
        int i2 = dVar.h;
        if (dVar4 != null) {
            i = dVar4.h;
        }
        dVar2.h = Math.max(i2, i) + 1;
    }

    private void b(d<K, V> dVar, boolean z) {
        while (dVar != null) {
            d<K, V> dVar2 = dVar.f1649b;
            d<K, V> dVar3 = dVar.f1650c;
            int i = 0;
            int i2 = dVar2 != null ? dVar2.h : 0;
            int i3 = dVar3 != null ? dVar3.h : 0;
            int i4 = i2 - i3;
            if (i4 == -2) {
                d<K, V> dVar4 = dVar3.f1649b;
                d<K, V> dVar5 = dVar3.f1650c;
                int i5 = dVar5 != null ? dVar5.h : 0;
                if (dVar4 != null) {
                    i = dVar4.h;
                }
                int i6 = i - i5;
                if (i6 != -1 && (i6 != 0 || z)) {
                    b((d) dVar3);
                }
                a((d) dVar);
                if (z) {
                    return;
                }
            } else if (i4 == 2) {
                d<K, V> dVar6 = dVar2.f1649b;
                d<K, V> dVar7 = dVar2.f1650c;
                int i7 = dVar7 != null ? dVar7.h : 0;
                if (dVar6 != null) {
                    i = dVar6.h;
                }
                int i8 = i - i7;
                if (i8 != 1 && (i8 != 0 || z)) {
                    a((d) dVar2);
                }
                b((d) dVar);
                if (z) {
                    return;
                }
            } else if (i4 == 0) {
                dVar.h = i2 + 1;
                if (z) {
                    return;
                }
            } else {
                dVar.h = Math.max(i2, i3) + 1;
                if (!z) {
                    return;
                }
            }
            dVar = dVar.f1648a;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: package-private */
    public d<K, V> a(Object obj) {
        if (obj == 0) {
            return null;
        }
        try {
            return a(obj, false);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public d<K, V> a(K k, boolean z) {
        int i;
        d<K, V> dVar;
        Comparator<? super K> comparator = this.f1639b;
        d<K, V> dVar2 = this.f1640c;
        if (dVar2 != null) {
            K k2 = comparator == f1638a ? k : null;
            while (true) {
                i = k2 != null ? k2.compareTo(dVar2.f) : comparator.compare(k, dVar2.f);
                if (i == 0) {
                    return dVar2;
                }
                d<K, V> dVar3 = i < 0 ? dVar2.f1649b : dVar2.f1650c;
                if (dVar3 == null) {
                    break;
                }
                dVar2 = dVar3;
            }
        } else {
            i = 0;
        }
        if (!z) {
            return null;
        }
        d<K, V> dVar4 = this.f;
        if (dVar2 != null) {
            dVar = new d<>(dVar2, k, dVar4, dVar4.e);
            if (i < 0) {
                dVar2.f1649b = dVar;
            } else {
                dVar2.f1650c = dVar;
            }
            b(dVar2, true);
        } else if (comparator != f1638a || (k instanceof Comparable)) {
            dVar = new d<>(dVar2, k, dVar4, dVar4.e);
            this.f1640c = dVar;
        } else {
            throw new ClassCastException(k.getClass().getName() + " is not Comparable");
        }
        this.f1641d++;
        this.e++;
        return dVar;
    }

    /* access modifiers changed from: package-private */
    public d<K, V> a(Map.Entry<?, ?> entry) {
        d<K, V> a2 = a(entry.getKey());
        if (a2 != null && a(a2.g, entry.getValue())) {
            return a2;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void a(d<K, V> dVar, boolean z) {
        int i;
        if (z) {
            d<K, V> dVar2 = dVar.e;
            dVar2.f1651d = dVar.f1651d;
            dVar.f1651d.e = dVar2;
        }
        d<K, V> dVar3 = dVar.f1649b;
        d<K, V> dVar4 = dVar.f1650c;
        d<K, V> dVar5 = dVar.f1648a;
        int i2 = 0;
        if (dVar3 == null || dVar4 == null) {
            if (dVar3 != null) {
                a((d) dVar, (d) dVar3);
                dVar.f1649b = null;
            } else if (dVar4 != null) {
                a((d) dVar, (d) dVar4);
                dVar.f1650c = null;
            } else {
                a((d) dVar, (d) null);
            }
            b(dVar5, false);
            this.f1641d--;
            this.e++;
            return;
        }
        d<K, V> b2 = dVar3.h > dVar4.h ? dVar3.b() : dVar4.a();
        a((d) b2, false);
        d<K, V> dVar6 = dVar.f1649b;
        if (dVar6 != null) {
            i = dVar6.h;
            b2.f1649b = dVar6;
            dVar6.f1648a = b2;
            dVar.f1649b = null;
        } else {
            i = 0;
        }
        d<K, V> dVar7 = dVar.f1650c;
        if (dVar7 != null) {
            i2 = dVar7.h;
            b2.f1650c = dVar7;
            dVar7.f1648a = b2;
            dVar.f1650c = null;
        }
        b2.h = Math.max(i, i2) + 1;
        a((d) dVar, (d) b2);
    }

    /* access modifiers changed from: package-private */
    public d<K, V> b(Object obj) {
        d<K, V> a2 = a(obj);
        if (a2 != null) {
            a((d) a2, true);
        }
        return a2;
    }

    public void clear() {
        this.f1640c = null;
        this.f1641d = 0;
        this.e++;
        d<K, V> dVar = this.f;
        dVar.e = dVar;
        dVar.f1651d = dVar;
    }

    public boolean containsKey(Object obj) {
        return a(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        v<K, V>.a aVar = this.g;
        if (aVar != null) {
            return aVar;
        }
        v<K, V>.a aVar2 = new a();
        this.g = aVar2;
        return aVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        d<K, V> a2 = a(obj);
        if (a2 != null) {
            return a2.g;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        v<K, V>.b bVar = this.h;
        if (bVar != null) {
            return bVar;
        }
        v<K, V>.b bVar2 = new b();
        this.h = bVar2;
        return bVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        if (k != null) {
            d<K, V> a2 = a((Object) k, true);
            V v2 = a2.g;
            a2.g = v;
            return v2;
        }
        throw new NullPointerException("key == null");
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        d<K, V> b2 = b(obj);
        if (b2 != null) {
            return b2.g;
        }
        return null;
    }

    public int size() {
        return this.f1641d;
    }
}
