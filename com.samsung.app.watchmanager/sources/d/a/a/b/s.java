package d.a.a.b;

import d.a.a.b.a;
import d.a.a.c;
import d.a.a.g;
import d.a.a.h;
import d.a.a.j;
import d.a.a.k;
import java.util.HashMap;
import java.util.Locale;

public final class s extends a {

    /* access modifiers changed from: package-private */
    public static final class a extends d.a.a.c.b {

        /* renamed from: b  reason: collision with root package name */
        final c f2033b;

        /* renamed from: c  reason: collision with root package name */
        final g f2034c;

        /* renamed from: d  reason: collision with root package name */
        final h f2035d;
        final boolean e;
        final h f;
        final h g;

        a(c cVar, g gVar, h hVar, h hVar2, h hVar3) {
            super(cVar.g());
            if (cVar.i()) {
                this.f2033b = cVar;
                this.f2034c = gVar;
                this.f2035d = hVar;
                this.e = s.a(hVar);
                this.f = hVar2;
                this.g = hVar3;
                return;
            }
            throw new IllegalArgumentException();
        }

        private int j(long j) {
            int c2 = this.f2034c.c(j);
            long j2 = (long) c2;
            if (((j + j2) ^ j) >= 0 || (j ^ j2) < 0) {
                return c2;
            }
            throw new ArithmeticException("Adding time zone offset caused overflow");
        }

        @Override // d.a.a.c
        public int a(long j) {
            return this.f2033b.a(this.f2034c.a(j));
        }

        @Override // d.a.a.c, d.a.a.c.b
        public int a(Locale locale) {
            return this.f2033b.a(locale);
        }

        @Override // d.a.a.c, d.a.a.c.b
        public long a(long j, int i) {
            if (this.e) {
                long j2 = (long) j(j);
                return this.f2033b.a(j + j2, i) - j2;
            }
            return this.f2034c.a(this.f2033b.a(this.f2034c.a(j), i), false, j);
        }

        @Override // d.a.a.c, d.a.a.c.b
        public long a(long j, String str, Locale locale) {
            return this.f2034c.a(this.f2033b.a(this.f2034c.a(j), str, locale), false, j);
        }

        @Override // d.a.a.c
        public final h a() {
            return this.f2035d;
        }

        @Override // d.a.a.c, d.a.a.c.b
        public String a(int i, Locale locale) {
            return this.f2033b.a(i, locale);
        }

        @Override // d.a.a.c, d.a.a.c.b
        public String a(long j, Locale locale) {
            return this.f2033b.a(this.f2034c.a(j), locale);
        }

        @Override // d.a.a.c
        public long b(long j, int i) {
            long b2 = this.f2033b.b(this.f2034c.a(j), i);
            long a2 = this.f2034c.a(b2, false, j);
            if (a(a2) == i) {
                return a2;
            }
            k kVar = new k(b2, this.f2034c.c());
            j jVar = new j(this.f2033b.g(), Integer.valueOf(i), kVar.getMessage());
            jVar.initCause(kVar);
            throw jVar;
        }

        @Override // d.a.a.c, d.a.a.c.b
        public final h b() {
            return this.g;
        }

        @Override // d.a.a.c, d.a.a.c.b
        public String b(int i, Locale locale) {
            return this.f2033b.b(i, locale);
        }

        @Override // d.a.a.c, d.a.a.c.b
        public String b(long j, Locale locale) {
            return this.f2033b.b(this.f2034c.a(j), locale);
        }

        @Override // d.a.a.c, d.a.a.c.b
        public boolean b(long j) {
            return this.f2033b.b(this.f2034c.a(j));
        }

        @Override // d.a.a.c
        public int c() {
            return this.f2033b.c();
        }

        @Override // d.a.a.c, d.a.a.c.b
        public long c(long j) {
            return this.f2033b.c(this.f2034c.a(j));
        }

        @Override // d.a.a.c
        public int d() {
            return this.f2033b.d();
        }

        @Override // d.a.a.c, d.a.a.c.b
        public long d(long j) {
            if (this.e) {
                long j2 = (long) j(j);
                return this.f2033b.d(j + j2) - j2;
            }
            return this.f2034c.a(this.f2033b.d(this.f2034c.a(j)), false, j);
        }

        @Override // d.a.a.c
        public long e(long j) {
            if (this.e) {
                long j2 = (long) j(j);
                return this.f2033b.e(j + j2) - j2;
            }
            return this.f2034c.a(this.f2033b.e(this.f2034c.a(j)), false, j);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.f2033b.equals(aVar.f2033b) && this.f2034c.equals(aVar.f2034c) && this.f2035d.equals(aVar.f2035d) && this.f.equals(aVar.f);
        }

        @Override // d.a.a.c
        public final h f() {
            return this.f;
        }

        @Override // d.a.a.c
        public boolean h() {
            return this.f2033b.h();
        }

        public int hashCode() {
            return this.f2033b.hashCode() ^ this.f2034c.hashCode();
        }
    }

    /* access modifiers changed from: package-private */
    public static class b extends d.a.a.c.c {

        /* renamed from: b  reason: collision with root package name */
        final h f2036b;

        /* renamed from: c  reason: collision with root package name */
        final boolean f2037c;

        /* renamed from: d  reason: collision with root package name */
        final g f2038d;

        b(h hVar, g gVar) {
            super(hVar.b());
            if (hVar.e()) {
                this.f2036b = hVar;
                this.f2037c = s.a(hVar);
                this.f2038d = gVar;
                return;
            }
            throw new IllegalArgumentException();
        }

        private int a(long j) {
            int d2 = this.f2038d.d(j);
            long j2 = (long) d2;
            if (((j - j2) ^ j) >= 0 || (j ^ j2) >= 0) {
                return d2;
            }
            throw new ArithmeticException("Subtracting time zone offset caused overflow");
        }

        private int b(long j) {
            int c2 = this.f2038d.c(j);
            long j2 = (long) c2;
            if (((j + j2) ^ j) >= 0 || (j ^ j2) < 0) {
                return c2;
            }
            throw new ArithmeticException("Adding time zone offset caused overflow");
        }

        @Override // d.a.a.h
        public long a(long j, int i) {
            int b2 = b(j);
            long a2 = this.f2036b.a(j + ((long) b2), i);
            if (!this.f2037c) {
                b2 = a(a2);
            }
            return a2 - ((long) b2);
        }

        @Override // d.a.a.h
        public long a(long j, long j2) {
            int b2 = b(j);
            long a2 = this.f2036b.a(j + ((long) b2), j2);
            if (!this.f2037c) {
                b2 = a(a2);
            }
            return a2 - ((long) b2);
        }

        @Override // d.a.a.h
        public long c() {
            return this.f2036b.c();
        }

        @Override // d.a.a.h
        public boolean d() {
            return this.f2037c ? this.f2036b.d() : this.f2036b.d() && this.f2038d.f();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return this.f2036b.equals(bVar.f2036b) && this.f2038d.equals(bVar.f2038d);
        }

        public int hashCode() {
            return this.f2036b.hashCode() ^ this.f2038d.hashCode();
        }
    }

    private s(d.a.a.a aVar, g gVar) {
        super(aVar, gVar);
    }

    private long a(long j) {
        if (j == Long.MAX_VALUE) {
            return Long.MAX_VALUE;
        }
        if (j == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        g k = k();
        int d2 = k.d(j);
        long j2 = j - ((long) d2);
        if (j > 604800000 && j2 < 0) {
            return Long.MAX_VALUE;
        }
        if (j < -604800000 && j2 > 0) {
            return Long.MIN_VALUE;
        }
        if (d2 == k.c(j2)) {
            return j2;
        }
        throw new k(j, k.c());
    }

    public static s a(d.a.a.a aVar, g gVar) {
        if (aVar != null) {
            d.a.a.a G = aVar.G();
            if (G == null) {
                throw new IllegalArgumentException("UTC chronology must not be null");
            } else if (gVar != null) {
                return new s(G, gVar);
            } else {
                throw new IllegalArgumentException("DateTimeZone must not be null");
            }
        } else {
            throw new IllegalArgumentException("Must supply a chronology");
        }
    }

    private c a(c cVar, HashMap<Object, Object> hashMap) {
        if (cVar == null || !cVar.i()) {
            return cVar;
        }
        if (hashMap.containsKey(cVar)) {
            return (c) hashMap.get(cVar);
        }
        a aVar = new a(cVar, k(), a(cVar.a(), hashMap), a(cVar.f(), hashMap), a(cVar.b(), hashMap));
        hashMap.put(cVar, aVar);
        return aVar;
    }

    private h a(h hVar, HashMap<Object, Object> hashMap) {
        if (hVar == null || !hVar.e()) {
            return hVar;
        }
        if (hashMap.containsKey(hVar)) {
            return (h) hashMap.get(hVar);
        }
        b bVar = new b(hVar, k());
        hashMap.put(hVar, bVar);
        return bVar;
    }

    static boolean a(h hVar) {
        return hVar != null && hVar.c() < 43200000;
    }

    @Override // d.a.a.a
    public d.a.a.a G() {
        return L();
    }

    @Override // d.a.a.a, d.a.a.b.b, d.a.a.b.a
    public long a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(L().a(i, i2, i3, i4, i5, i6, i7));
    }

    @Override // d.a.a.a
    public d.a.a.a a(g gVar) {
        if (gVar == null) {
            gVar = g.b();
        }
        return gVar == M() ? this : gVar == g.f2149a ? L() : new s(L(), gVar);
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.b.a
    public void a(a.C0040a aVar) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        aVar.l = a(aVar.l, hashMap);
        aVar.k = a(aVar.k, hashMap);
        aVar.j = a(aVar.j, hashMap);
        aVar.i = a(aVar.i, hashMap);
        aVar.h = a(aVar.h, hashMap);
        aVar.g = a(aVar.g, hashMap);
        aVar.f = a(aVar.f, hashMap);
        aVar.e = a(aVar.e, hashMap);
        aVar.f2016d = a(aVar.f2016d, hashMap);
        aVar.f2015c = a(aVar.f2015c, hashMap);
        aVar.f2014b = a(aVar.f2014b, hashMap);
        aVar.f2013a = a(aVar.f2013a, hashMap);
        aVar.E = a(aVar.E, hashMap);
        aVar.F = a(aVar.F, hashMap);
        aVar.G = a(aVar.G, hashMap);
        aVar.H = a(aVar.H, hashMap);
        aVar.I = a(aVar.I, hashMap);
        aVar.x = a(aVar.x, hashMap);
        aVar.y = a(aVar.y, hashMap);
        aVar.z = a(aVar.z, hashMap);
        aVar.D = a(aVar.D, hashMap);
        aVar.A = a(aVar.A, hashMap);
        aVar.B = a(aVar.B, hashMap);
        aVar.C = a(aVar.C, hashMap);
        aVar.m = a(aVar.m, hashMap);
        aVar.n = a(aVar.n, hashMap);
        aVar.o = a(aVar.o, hashMap);
        aVar.p = a(aVar.p, hashMap);
        aVar.q = a(aVar.q, hashMap);
        aVar.r = a(aVar.r, hashMap);
        aVar.s = a(aVar.s, hashMap);
        aVar.u = a(aVar.u, hashMap);
        aVar.t = a(aVar.t, hashMap);
        aVar.v = a(aVar.v, hashMap);
        aVar.w = a(aVar.w, hashMap);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        return L().equals(sVar.L()) && k().equals(sVar.k());
    }

    public int hashCode() {
        return (k().hashCode() * 11) + 326565 + (L().hashCode() * 7);
    }

    @Override // d.a.a.a, d.a.a.b.a
    public g k() {
        return (g) M();
    }

    public String toString() {
        return "ZonedChronology[" + L() + ", " + k().c() + ']';
    }
}
