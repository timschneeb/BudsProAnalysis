package c.a.a.b.a;

import c.a.a.A;
import c.a.a.D;
import c.a.a.J;
import c.a.a.K;
import c.a.a.b.C0143b;
import c.a.a.b.o;
import c.a.a.b.r;
import c.a.a.b.x;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import c.a.a.p;
import c.a.a.v;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/* renamed from: c.a.a.b.a.k  reason: case insensitive filesystem */
public final class C0128k implements K {

    /* renamed from: a  reason: collision with root package name */
    private final o f1574a;

    /* renamed from: b  reason: collision with root package name */
    private final boolean f1575b;

    /* renamed from: c.a.a.b.a.k$a */
    private final class a<K, V> extends J<Map<K, V>> {

        /* renamed from: a  reason: collision with root package name */
        private final J<K> f1576a;

        /* renamed from: b  reason: collision with root package name */
        private final J<V> f1577b;

        /* renamed from: c  reason: collision with root package name */
        private final x<? extends Map<K, V>> f1578c;

        public a(p pVar, Type type, J<K> j, Type type2, J<V> j2, x<? extends Map<K, V>> xVar) {
            this.f1576a = new C0137u(pVar, j, type);
            this.f1577b = new C0137u(pVar, j2, type2);
            this.f1578c = xVar;
        }

        private String a(v vVar) {
            if (vVar.h()) {
                A c2 = vVar.c();
                if (c2.p()) {
                    return String.valueOf(c2.n());
                }
                if (c2.o()) {
                    return Boolean.toString(c2.i());
                }
                if (c2.q()) {
                    return c2.d();
                }
                throw new AssertionError();
            } else if (vVar.f()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }

        @Override // c.a.a.J
        public Map<K, V> a(b bVar) {
            c o = bVar.o();
            if (o == c.NULL) {
                bVar.m();
                return null;
            }
            Map<K, V> map = (Map) this.f1578c.a();
            if (o == c.BEGIN_ARRAY) {
                bVar.a();
                while (bVar.f()) {
                    bVar.a();
                    K a2 = this.f1576a.a(bVar);
                    if (map.put(a2, this.f1577b.a(bVar)) == null) {
                        bVar.c();
                    } else {
                        throw new D("duplicate key: " + ((Object) a2));
                    }
                }
                bVar.c();
            } else {
                bVar.b();
                while (bVar.f()) {
                    r.f1636a.a(bVar);
                    K a3 = this.f1576a.a(bVar);
                    if (map.put(a3, this.f1577b.a(bVar)) != null) {
                        throw new D("duplicate key: " + ((Object) a3));
                    }
                }
                bVar.d();
            }
            return map;
        }

        @Override // c.a.a.J
        public /* bridge */ /* synthetic */ void a(d dVar, Object obj) {
            a(dVar, (Map) ((Map) obj));
        }

        /* JADX DEBUG: Multi-variable search result rejected for r9v7, resolved type: c.a.a.J<V> */
        /* JADX DEBUG: Multi-variable search result rejected for r9v11, resolved type: c.a.a.J<V> */
        /* JADX WARN: Multi-variable type inference failed */
        public void a(d dVar, Map<K, V> map) {
            if (map == null) {
                dVar.h();
            } else if (!C0128k.this.f1575b) {
                dVar.b();
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    dVar.a(String.valueOf(entry.getKey()));
                    this.f1577b.a(dVar, entry.getValue());
                }
                dVar.d();
            } else {
                ArrayList arrayList = new ArrayList(map.size());
                ArrayList arrayList2 = new ArrayList(map.size());
                int i = 0;
                boolean z = false;
                for (Map.Entry<K, V> entry2 : map.entrySet()) {
                    v a2 = this.f1576a.a(entry2.getKey());
                    arrayList.add(a2);
                    arrayList2.add(entry2.getValue());
                    z |= a2.e() || a2.g();
                }
                if (z) {
                    dVar.a();
                    while (i < arrayList.size()) {
                        dVar.a();
                        c.a.a.b.A.a((v) arrayList.get(i), dVar);
                        this.f1577b.a(dVar, arrayList2.get(i));
                        dVar.c();
                        i++;
                    }
                    dVar.c();
                    return;
                }
                dVar.b();
                while (i < arrayList.size()) {
                    dVar.a(a((v) arrayList.get(i)));
                    this.f1577b.a(dVar, arrayList2.get(i));
                    i++;
                }
                dVar.d();
            }
        }
    }

    public C0128k(o oVar, boolean z) {
        this.f1574a = oVar;
        this.f1575b = z;
    }

    private J<?> a(p pVar, Type type) {
        return (type == Boolean.TYPE || type == Boolean.class) ? ca.f : pVar.a((c.a.a.c.a) c.a.a.c.a.a(type));
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, c.a.a.c.a<T> aVar) {
        Type b2 = aVar.b();
        if (!Map.class.isAssignableFrom(aVar.a())) {
            return null;
        }
        Type[] b3 = C0143b.b(b2, C0143b.e(b2));
        return new a(pVar, b3[0], a(pVar, b3[0]), b3[1], pVar.a((c.a.a.c.a) c.a.a.c.a.a(b3[1])), this.f1574a.a(aVar));
    }
}
