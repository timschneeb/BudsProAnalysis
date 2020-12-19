package b.b.a.b;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class b<K, V> implements Iterable<Map.Entry<K, V>> {

    /* renamed from: a  reason: collision with root package name */
    c<K, V> f1272a;

    /* renamed from: b  reason: collision with root package name */
    private c<K, V> f1273b;

    /* renamed from: c  reason: collision with root package name */
    private WeakHashMap<f<K, V>, Boolean> f1274c = new WeakHashMap<>();

    /* renamed from: d  reason: collision with root package name */
    private int f1275d = 0;

    /* access modifiers changed from: package-private */
    public static class a<K, V> extends e<K, V> {
        a(c<K, V> cVar, c<K, V> cVar2) {
            super(cVar, cVar2);
        }

        /* access modifiers changed from: package-private */
        @Override // b.b.a.b.b.e
        public c<K, V> b(c<K, V> cVar) {
            return cVar.f1279d;
        }

        /* access modifiers changed from: package-private */
        @Override // b.b.a.b.b.e
        public c<K, V> c(c<K, V> cVar) {
            return cVar.f1278c;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b.b.a.b.b$b  reason: collision with other inner class name */
    public static class C0020b<K, V> extends e<K, V> {
        C0020b(c<K, V> cVar, c<K, V> cVar2) {
            super(cVar, cVar2);
        }

        /* access modifiers changed from: package-private */
        @Override // b.b.a.b.b.e
        public c<K, V> b(c<K, V> cVar) {
            return cVar.f1278c;
        }

        /* access modifiers changed from: package-private */
        @Override // b.b.a.b.b.e
        public c<K, V> c(c<K, V> cVar) {
            return cVar.f1279d;
        }
    }

    /* access modifiers changed from: package-private */
    public static class c<K, V> implements Map.Entry<K, V> {

        /* renamed from: a  reason: collision with root package name */
        final K f1276a;

        /* renamed from: b  reason: collision with root package name */
        final V f1277b;

        /* renamed from: c  reason: collision with root package name */
        c<K, V> f1278c;

        /* renamed from: d  reason: collision with root package name */
        c<K, V> f1279d;

        c(K k, V v) {
            this.f1276a = k;
            this.f1277b = v;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return this.f1276a.equals(cVar.f1276a) && this.f1277b.equals(cVar.f1277b);
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.f1276a;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.f1277b;
        }

        public int hashCode() {
            return this.f1276a.hashCode() ^ this.f1277b.hashCode();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            return ((Object) this.f1276a) + "=" + ((Object) this.f1277b);
        }
    }

    /* access modifiers changed from: private */
    public class d implements Iterator<Map.Entry<K, V>>, f<K, V> {

        /* renamed from: a  reason: collision with root package name */
        private c<K, V> f1280a;

        /* renamed from: b  reason: collision with root package name */
        private boolean f1281b = true;

        d() {
        }

        @Override // b.b.a.b.b.f
        public void a(c<K, V> cVar) {
            c<K, V> cVar2 = this.f1280a;
            if (cVar == cVar2) {
                this.f1280a = cVar2.f1279d;
                this.f1281b = this.f1280a == null;
            }
        }

        public boolean hasNext() {
            if (this.f1281b) {
                return b.this.f1272a != null;
            }
            c<K, V> cVar = this.f1280a;
            return (cVar == null || cVar.f1278c == null) ? false : true;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            c<K, V> cVar;
            if (this.f1281b) {
                this.f1281b = false;
                cVar = b.this.f1272a;
            } else {
                c<K, V> cVar2 = this.f1280a;
                cVar = cVar2 != null ? cVar2.f1278c : null;
            }
            this.f1280a = cVar;
            return this.f1280a;
        }
    }

    private static abstract class e<K, V> implements Iterator<Map.Entry<K, V>>, f<K, V> {

        /* renamed from: a  reason: collision with root package name */
        c<K, V> f1283a;

        /* renamed from: b  reason: collision with root package name */
        c<K, V> f1284b;

        e(c<K, V> cVar, c<K, V> cVar2) {
            this.f1283a = cVar2;
            this.f1284b = cVar;
        }

        private c<K, V> a() {
            c<K, V> cVar = this.f1284b;
            c<K, V> cVar2 = this.f1283a;
            if (cVar == cVar2 || cVar2 == null) {
                return null;
            }
            return c(cVar);
        }

        @Override // b.b.a.b.b.f
        public void a(c<K, V> cVar) {
            if (this.f1283a == cVar && cVar == this.f1284b) {
                this.f1284b = null;
                this.f1283a = null;
            }
            c<K, V> cVar2 = this.f1283a;
            if (cVar2 == cVar) {
                this.f1283a = b(cVar2);
            }
            if (this.f1284b == cVar) {
                this.f1284b = a();
            }
        }

        /* access modifiers changed from: package-private */
        public abstract c<K, V> b(c<K, V> cVar);

        /* access modifiers changed from: package-private */
        public abstract c<K, V> c(c<K, V> cVar);

        public boolean hasNext() {
            return this.f1284b != null;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            c<K, V> cVar = this.f1284b;
            this.f1284b = a();
            return cVar;
        }
    }

    /* access modifiers changed from: package-private */
    public interface f<K, V> {
        void a(c<K, V> cVar);
    }

    /* access modifiers changed from: protected */
    public c<K, V> a(K k) {
        c<K, V> cVar = this.f1272a;
        while (cVar != null && !cVar.f1276a.equals(k)) {
            cVar = cVar.f1278c;
        }
        return cVar;
    }

    /* access modifiers changed from: protected */
    public c<K, V> a(K k, V v) {
        c<K, V> cVar = new c<>(k, v);
        this.f1275d++;
        c<K, V> cVar2 = this.f1273b;
        if (cVar2 == null) {
            this.f1272a = cVar;
            this.f1273b = this.f1272a;
            return cVar;
        }
        cVar2.f1278c = cVar;
        cVar.f1279d = cVar2;
        this.f1273b = cVar;
        return cVar;
    }

    public Map.Entry<K, V> a() {
        return this.f1272a;
    }

    public b<K, V>.d b() {
        b<K, V>.d dVar = new d();
        this.f1274c.put(dVar, false);
        return dVar;
    }

    public V b(K k, V v) {
        c<K, V> a2 = a(k);
        if (a2 != null) {
            return a2.f1277b;
        }
        a(k, v);
        return null;
    }

    public Map.Entry<K, V> c() {
        return this.f1273b;
    }

    public Iterator<Map.Entry<K, V>> descendingIterator() {
        C0020b bVar = new C0020b(this.f1273b, this.f1272a);
        this.f1274c.put(bVar, false);
        return bVar;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        if (size() != bVar.size()) {
            return false;
        }
        Iterator<Map.Entry<K, V>> it = iterator();
        Iterator<Map.Entry<K, V>> it2 = bVar.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry<K, V> next = it.next();
            Map.Entry<K, V> next2 = it2.next();
            if ((next == null && next2 != null) || (next != null && !next.equals(next2))) {
                return false;
            }
        }
        return !it.hasNext() && !it2.hasNext();
    }

    public int hashCode() {
        Iterator<Map.Entry<K, V>> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().hashCode();
        }
        return i;
    }

    @Override // java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        a aVar = new a(this.f1272a, this.f1273b);
        this.f1274c.put(aVar, false);
        return aVar;
    }

    public V remove(K k) {
        c<K, V> a2 = a(k);
        if (a2 == null) {
            return null;
        }
        this.f1275d--;
        if (!this.f1274c.isEmpty()) {
            for (f<K, V> fVar : this.f1274c.keySet()) {
                fVar.a(a2);
            }
        }
        c<K, V> cVar = a2.f1279d;
        if (cVar != null) {
            cVar.f1278c = a2.f1278c;
        } else {
            this.f1272a = a2.f1278c;
        }
        c<K, V> cVar2 = a2.f1278c;
        if (cVar2 != null) {
            cVar2.f1279d = a2.f1279d;
        } else {
            this.f1273b = a2.f1279d;
        }
        a2.f1278c = null;
        a2.f1279d = null;
        return a2.f1277b;
    }

    public int size() {
        return this.f1275d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
