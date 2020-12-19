package c.a.a;

import c.a.a.b.A;
import c.a.a.b.a.C0119b;
import c.a.a.b.a.C0120c;
import c.a.a.b.a.C0122e;
import c.a.a.b.a.C0123f;
import c.a.a.b.a.C0128k;
import c.a.a.b.a.C0131n;
import c.a.a.b.a.C0133p;
import c.a.a.b.a.C0136t;
import c.a.a.b.a.ca;
import c.a.a.b.a.r;
import c.a.a.b.o;
import c.a.a.b.q;
import c.a.a.b.y;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import c.a.a.d.e;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class p {

    /* renamed from: a  reason: collision with root package name */
    private final ThreadLocal<Map<c.a.a.c.a<?>, a<?>>> f1679a = new ThreadLocal<>();

    /* renamed from: b  reason: collision with root package name */
    private final Map<c.a.a.c.a<?>, J<?>> f1680b = Collections.synchronizedMap(new HashMap());

    /* renamed from: c  reason: collision with root package name */
    private final List<K> f1681c;

    /* renamed from: d  reason: collision with root package name */
    private final o f1682d;
    private final boolean e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    final t i = new C0156k(this);
    final B j = new l(this);

    /* access modifiers changed from: package-private */
    public static class a<T> extends J<T> {

        /* renamed from: a  reason: collision with root package name */
        private J<T> f1683a;

        a() {
        }

        @Override // c.a.a.J
        public T a(b bVar) {
            J<T> j = this.f1683a;
            if (j != null) {
                return j.a(bVar);
            }
            throw new IllegalStateException();
        }

        public void a(J<T> j) {
            if (this.f1683a == null) {
                this.f1683a = j;
                return;
            }
            throw new AssertionError();
        }

        @Override // c.a.a.J
        public void a(d dVar, T t) {
            J<T> j = this.f1683a;
            if (j != null) {
                j.a(dVar, t);
                return;
            }
            throw new IllegalStateException();
        }
    }

    p(q qVar, AbstractC0155j jVar, Map<Type, r<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, G g2, List<K> list) {
        this.f1682d = new o(map);
        this.e = z;
        this.g = z3;
        this.f = z4;
        this.h = z5;
        ArrayList arrayList = new ArrayList();
        arrayList.add(ca.Q);
        arrayList.add(C0131n.f1581a);
        arrayList.add(qVar);
        arrayList.addAll(list);
        arrayList.add(ca.x);
        arrayList.add(ca.m);
        arrayList.add(ca.g);
        arrayList.add(ca.i);
        arrayList.add(ca.k);
        arrayList.add(ca.a(Long.TYPE, Long.class, a(g2)));
        arrayList.add(ca.a(Double.TYPE, Double.class, a(z6)));
        arrayList.add(ca.a(Float.TYPE, Float.class, b(z6)));
        arrayList.add(ca.r);
        arrayList.add(ca.t);
        arrayList.add(ca.z);
        arrayList.add(ca.B);
        arrayList.add(ca.a(BigDecimal.class, ca.v));
        arrayList.add(ca.a(BigInteger.class, ca.w));
        arrayList.add(ca.D);
        arrayList.add(ca.F);
        arrayList.add(ca.J);
        arrayList.add(ca.O);
        arrayList.add(ca.H);
        arrayList.add(ca.f1566d);
        arrayList.add(C0122e.f1569a);
        arrayList.add(ca.M);
        arrayList.add(C0136t.f1594a);
        arrayList.add(r.f1592a);
        arrayList.add(ca.K);
        arrayList.add(C0119b.f1557a);
        arrayList.add(ca.f1564b);
        arrayList.add(new C0120c(this.f1682d));
        arrayList.add(new C0128k(this.f1682d, z2));
        arrayList.add(new C0123f(this.f1682d));
        arrayList.add(ca.R);
        arrayList.add(new C0133p(this.f1682d, jVar, qVar));
        this.f1681c = Collections.unmodifiableList(arrayList);
    }

    private J<Number> a(G g2) {
        return g2 == G.DEFAULT ? ca.n : new o(this);
    }

    private J<Number> a(boolean z) {
        return z ? ca.p : new m(this);
    }

    private d a(Writer writer) {
        if (this.g) {
            writer.write(")]}'\n");
        }
        d dVar = new d(writer);
        if (this.h) {
            dVar.b("  ");
        }
        dVar.c(this.e);
        return dVar;
    }

    /* access modifiers changed from: private */
    public void a(double d2) {
        if (Double.isNaN(d2) || Double.isInfinite(d2)) {
            throw new IllegalArgumentException(d2 + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static void a(Object obj, b bVar) {
        if (obj != null) {
            try {
                if (bVar.o() != c.END_DOCUMENT) {
                    throw new w("JSON document was not fully consumed.");
                }
            } catch (e e2) {
                throw new D(e2);
            } catch (IOException e3) {
                throw new w(e3);
            }
        }
    }

    private J<Number> b(boolean z) {
        return z ? ca.o : new n(this);
    }

    public <T> J<T> a(K k, c.a.a.c.a<T> aVar) {
        boolean z = !this.f1681c.contains(k);
        for (K k2 : this.f1681c) {
            if (z) {
                J<T> a2 = k2.a(this, aVar);
                if (a2 != null) {
                    return a2;
                }
            } else if (k2 == k) {
                z = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + aVar);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v5, resolved type: java.util.Map<c.a.a.c.a<?>, c.a.a.J<?>> */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX DEBUG: Type inference failed for r4v4. Raw type applied. Possible types: c.a.a.J<T>, c.a.a.J<?> */
    public <T> J<T> a(c.a.a.c.a<T> aVar) {
        J<T> j2 = (J<T>) this.f1680b.get(aVar);
        if (j2 != null) {
            return j2;
        }
        Map<c.a.a.c.a<?>, a<?>> map = this.f1679a.get();
        boolean z = false;
        if (map == null) {
            map = new HashMap<>();
            this.f1679a.set(map);
            z = true;
        }
        a<?> aVar2 = map.get(aVar);
        if (aVar2 != null) {
            return aVar2;
        }
        try {
            a<?> aVar3 = new a<>();
            map.put(aVar, aVar3);
            for (K k : this.f1681c) {
                J j3 = (J<T>) k.a(this, aVar);
                if (j3 != null) {
                    aVar3.a((J<?>) j3);
                    this.f1680b.put(aVar, j3);
                    return j3;
                }
            }
            throw new IllegalArgumentException("GSON cannot handle " + aVar);
        } finally {
            map.remove(aVar);
            if (z) {
                this.f1679a.remove();
            }
        }
    }

    public <T> J<T> a(Class<T> cls) {
        return a((c.a.a.c.a) c.a.a.c.a.a((Class) cls));
    }

    public <T> T a(b bVar, Type type) {
        boolean g2 = bVar.g();
        boolean z = true;
        bVar.a(true);
        try {
            bVar.o();
            z = false;
            T a2 = a((c.a.a.c.a) c.a.a.c.a.a(type)).a(bVar);
            bVar.a(g2);
            return a2;
        } catch (EOFException e2) {
            if (z) {
                bVar.a(g2);
                return null;
            }
            throw new D(e2);
        } catch (IllegalStateException e3) {
            throw new D(e3);
        } catch (IOException e4) {
            throw new D(e4);
        } catch (Throwable th) {
            bVar.a(g2);
            throw th;
        }
    }

    public <T> T a(Reader reader, Type type) {
        b bVar = new b(reader);
        T t = (T) a(bVar, type);
        a(t, bVar);
        return t;
    }

    public <T> T a(String str, Class<T> cls) {
        return (T) y.a((Class) cls).cast(a(str, (Type) cls));
    }

    public <T> T a(String str, Type type) {
        if (str == null) {
            return null;
        }
        return (T) a((Reader) new StringReader(str), type);
    }

    public String a(v vVar) {
        StringWriter stringWriter = new StringWriter();
        a(vVar, stringWriter);
        return stringWriter.toString();
    }

    public String a(Object obj) {
        return obj == null ? a((v) x.f1689a) : a(obj, obj.getClass());
    }

    public String a(Object obj, Type type) {
        StringWriter stringWriter = new StringWriter();
        a(obj, type, stringWriter);
        return stringWriter.toString();
    }

    public void a(v vVar, d dVar) {
        boolean g2 = dVar.g();
        dVar.b(true);
        boolean f2 = dVar.f();
        dVar.a(this.f);
        boolean e2 = dVar.e();
        dVar.c(this.e);
        try {
            A.a(vVar, dVar);
            dVar.b(g2);
            dVar.a(f2);
            dVar.c(e2);
        } catch (IOException e3) {
            throw new w(e3);
        } catch (Throwable th) {
            dVar.b(g2);
            dVar.a(f2);
            dVar.c(e2);
            throw th;
        }
    }

    public void a(v vVar, Appendable appendable) {
        try {
            a(vVar, a(A.a(appendable)));
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void a(Object obj, Type type, d dVar) {
        J a2 = a((c.a.a.c.a) c.a.a.c.a.a(type));
        boolean g2 = dVar.g();
        dVar.b(true);
        boolean f2 = dVar.f();
        dVar.a(this.f);
        boolean e2 = dVar.e();
        dVar.c(this.e);
        try {
            a2.a(dVar, obj);
            dVar.b(g2);
            dVar.a(f2);
            dVar.c(e2);
        } catch (IOException e3) {
            throw new w(e3);
        } catch (Throwable th) {
            dVar.b(g2);
            dVar.a(f2);
            dVar.c(e2);
            throw th;
        }
    }

    public void a(Object obj, Type type, Appendable appendable) {
        try {
            a(obj, type, a(A.a(appendable)));
        } catch (IOException e2) {
            throw new w(e2);
        }
    }

    public String toString() {
        return "{serializeNulls:" + this.e + "factories:" + this.f1681c + ",instanceCreators:" + this.f1682d + "}";
    }
}
