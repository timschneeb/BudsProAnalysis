package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.a.c;
import c.a.a.d.b;
import c.a.a.d.d;
import c.a.a.v;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.BitSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public final class ca {
    public static final J<StringBuffer> A = new C();
    public static final K B = a(StringBuffer.class, A);
    public static final J<URL> C = new D();
    public static final K D = a(URL.class, C);
    public static final J<URI> E = new E();
    public static final K F = a(URI.class, E);
    public static final J<InetAddress> G = new G();
    public static final K H = b(InetAddress.class, G);
    public static final J<UUID> I = new H();
    public static final K J = a(UUID.class, I);
    public static final K K = new J();
    public static final J<Calendar> L = new K();
    public static final K M = b(Calendar.class, GregorianCalendar.class, L);
    public static final J<Locale> N = new L();
    public static final K O = a(Locale.class, N);
    public static final J<v> P = new M();
    public static final K Q = b(v.class, P);
    public static final K R = new N();

    /* renamed from: a  reason: collision with root package name */
    public static final J<Class> f1563a = new F();

    /* renamed from: b  reason: collision with root package name */
    public static final K f1564b = a(Class.class, f1563a);

    /* renamed from: c  reason: collision with root package name */
    public static final J<BitSet> f1565c = new Q();

    /* renamed from: d  reason: collision with root package name */
    public static final K f1566d = a(BitSet.class, f1565c);
    public static final J<Boolean> e = new V();
    public static final J<Boolean> f = new W();
    public static final K g = a(Boolean.TYPE, Boolean.class, e);
    public static final J<Number> h = new X();
    public static final K i = a(Byte.TYPE, Byte.class, h);
    public static final J<Number> j = new Y();
    public static final K k = a(Short.TYPE, Short.class, j);
    public static final J<Number> l = new Z();
    public static final K m = a(Integer.TYPE, Integer.class, l);
    public static final J<Number> n = new aa();
    public static final J<Number> o = new ba();
    public static final J<Number> p = new C0138v();
    public static final J<Number> q = new C0139w();
    public static final K r = a(Number.class, q);
    public static final J<Character> s = new C0140x();
    public static final K t = a(Character.TYPE, Character.class, s);
    public static final J<String> u = new C0141y();
    public static final J<BigDecimal> v = new C0142z();
    public static final J<BigInteger> w = new A();
    public static final K x = a(String.class, u);
    public static final J<StringBuilder> y = new B();
    public static final K z = a(StringBuilder.class, y);

    /* access modifiers changed from: private */
    public static final class a<T extends Enum<T>> extends J<T> {

        /* renamed from: a  reason: collision with root package name */
        private final Map<String, T> f1567a = new HashMap();

        /* renamed from: b  reason: collision with root package name */
        private final Map<T, String> f1568b = new HashMap();

        public a(Class<T> cls) {
            try {
                T[] enumConstants = cls.getEnumConstants();
                for (T t : enumConstants) {
                    String name = t.name();
                    c cVar = (c) cls.getField(name).getAnnotation(c.class);
                    name = cVar != null ? cVar.value() : name;
                    this.f1567a.put(name, t);
                    this.f1568b.put(t, name);
                }
            } catch (NoSuchFieldException unused) {
                throw new AssertionError();
            }
        }

        @Override // c.a.a.J
        public T a(b bVar) {
            if (bVar.o() != c.a.a.d.c.NULL) {
                return this.f1567a.get(bVar.n());
            }
            bVar.m();
            return null;
        }

        public void a(d dVar, T t) {
            dVar.c(t == null ? null : this.f1568b.get(t));
        }
    }

    public static <TT> K a(Class<TT> cls, J<TT> j2) {
        return new O(cls, j2);
    }

    public static <TT> K a(Class<TT> cls, Class<TT> cls2, J<? super TT> j2) {
        return new P(cls, cls2, j2);
    }

    public static <TT> K b(Class<TT> cls, J<TT> j2) {
        return new T(cls, j2);
    }

    public static <TT> K b(Class<TT> cls, Class<? extends TT> cls2, J<? super TT> j2) {
        return new S(cls, cls2, j2);
    }
}
