package d.a.a.b;

import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import d.a.a.b.a;
import d.a.a.c.f;
import d.a.a.c.g;
import d.a.a.c.i;
import d.a.a.c.k;
import d.a.a.c.m;
import d.a.a.c.n;
import d.a.a.c.r;
import d.a.a.d;
import d.a.a.h;
import d.a.a.j;
import java.util.Locale;

/* access modifiers changed from: package-private */
public abstract class c extends a {
    private static final h M = i.f2048a;
    private static final h N = new m(d.a.a.i.j(), 1000);
    private static final h O = new m(d.a.a.i.h(), 60000);
    private static final h P = new m(d.a.a.i.f(), 3600000);
    private static final h Q = new m(d.a.a.i.e(), 43200000);
    private static final h R = new m(d.a.a.i.b(), 86400000);
    private static final h S = new m(d.a.a.i.k(), 604800000);
    private static final d.a.a.c T = new k(d.n(), M, N);
    private static final d.a.a.c U = new k(d.m(), M, R);
    private static final d.a.a.c V = new k(d.s(), N, O);
    private static final d.a.a.c W = new k(d.r(), N, R);
    private static final d.a.a.c X = new k(d.p(), O, P);
    private static final d.a.a.c Y = new k(d.o(), O, R);
    private static final d.a.a.c Z = new k(d.k(), P, R);
    private static final d.a.a.c aa = new k(d.l(), P, Q);
    private static final d.a.a.c ba = new r(Z, d.b());
    private static final d.a.a.c ca = new r(aa, d.c());
    private static final d.a.a.c da = new a();
    private final transient b[] ea = new b[1024];
    private final int fa;

    private static class a extends k {
        a() {
            super(d.j(), c.Q, c.R);
        }

        @Override // d.a.a.c, d.a.a.c.b
        public int a(Locale locale) {
            return m.a(locale).c();
        }

        @Override // d.a.a.c, d.a.a.c.b
        public long a(long j, String str, Locale locale) {
            return b(j, m.a(locale).c(str));
        }

        @Override // d.a.a.c, d.a.a.c.b
        public String b(int i, Locale locale) {
            return m.a(locale).d(i);
        }
    }

    /* access modifiers changed from: private */
    public static class b {

        /* renamed from: a  reason: collision with root package name */
        public final int f2017a;

        /* renamed from: b  reason: collision with root package name */
        public final long f2018b;

        b(int i, long j) {
            this.f2017a = i;
            this.f2018b = j;
        }
    }

    c(d.a.a.a aVar, Object obj, int i) {
        super(aVar, obj);
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Invalid min days in first week: " + i);
        }
        this.fa = i;
    }

    private long a(int i, int i2, int i3, int i4) {
        long a2 = a(i, i2, i3);
        if (a2 == Long.MIN_VALUE) {
            a2 = a(i, i2, i3 + 1);
            i4 -= 86400000;
        }
        long j = ((long) i4) + a2;
        if (j < 0 && a2 > 0) {
            return Long.MAX_VALUE;
        }
        if (j <= 0 || a2 >= 0) {
            return j;
        }
        return Long.MIN_VALUE;
    }

    private b h(int i) {
        int i2 = i & 1023;
        b bVar = this.ea[i2];
        if (bVar != null && bVar.f2017a == i) {
            return bVar;
        }
        b bVar2 = new b(i, a(i));
        this.ea[i2] = bVar2;
        return bVar2;
    }

    /* access modifiers changed from: package-private */
    public abstract long P();

    /* access modifiers changed from: package-private */
    public abstract long Q();

    /* access modifiers changed from: package-private */
    public abstract long R();

    /* access modifiers changed from: package-private */
    public abstract long S();

    /* access modifiers changed from: package-private */
    public int T() {
        return 31;
    }

    /* access modifiers changed from: package-private */
    public int U() {
        return 366;
    }

    /* access modifiers changed from: package-private */
    public int V() {
        return 12;
    }

    /* access modifiers changed from: package-private */
    public abstract int W();

    /* access modifiers changed from: package-private */
    public abstract int X();

    public int Y() {
        return this.fa;
    }

    /* access modifiers changed from: package-private */
    public abstract int a(int i, int i2);

    /* access modifiers changed from: package-private */
    public int a(long j) {
        int i = i(j);
        return a(j, i, d(j, i));
    }

    /* access modifiers changed from: package-private */
    public int a(long j, int i) {
        return a(j, i, d(j, i));
    }

    /* access modifiers changed from: package-private */
    public int a(long j, int i, int i2) {
        return ((int) ((j - (f(i) + b(i, i2))) / 86400000)) + 1;
    }

    /* access modifiers changed from: package-private */
    public abstract long a(int i);

    /* access modifiers changed from: package-private */
    public long a(int i, int i2, int i3) {
        g.a(d.w(), i, X() - 1, W() + 1);
        g.a(d.q(), i2, 1, d(i));
        int a2 = a(i, i2);
        if (i3 < 1 || i3 > a2) {
            d d2 = d.d();
            Integer valueOf = Integer.valueOf(i3);
            Integer valueOf2 = Integer.valueOf(a2);
            throw new j(d2, valueOf, 1, valueOf2, "year: " + i + " month: " + i2);
        }
        long b2 = b(i, i2, i3);
        if (b2 < 0 && i == W() + 1) {
            return Long.MAX_VALUE;
        }
        if (b2 <= 0 || i != X() - 1) {
            return b2;
        }
        return Long.MIN_VALUE;
    }

    @Override // d.a.a.a, d.a.a.b.b, d.a.a.b.a
    public long a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        d.a.a.a L = L();
        if (L != null) {
            return L.a(i, i2, i3, i4, i5, i6, i7);
        }
        g.a(d.k(), i4, 0, 23);
        g.a(d.p(), i5, 0, 59);
        g.a(d.s(), i6, 0, 59);
        g.a(d.n(), i7, 0, 999);
        return a(i, i2, i3, (int) ((long) ((i4 * 3600000) + (i5 * 60000) + (i6 * SAGUIDHelper.GUID_REQUEST_ID) + i7)));
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.b.a
    public void a(a.C0040a aVar) {
        aVar.f2013a = M;
        aVar.f2014b = N;
        aVar.f2015c = O;
        aVar.f2016d = P;
        aVar.e = Q;
        aVar.f = R;
        aVar.g = S;
        aVar.m = T;
        aVar.n = U;
        aVar.o = V;
        aVar.p = W;
        aVar.q = X;
        aVar.r = Y;
        aVar.s = Z;
        aVar.u = aa;
        aVar.t = ba;
        aVar.v = ca;
        aVar.w = da;
        aVar.E = new j(this);
        aVar.F = new o(aVar.E, this);
        aVar.H = new f(new d.a.a.c.j(aVar.F, 99), d.a(), 100);
        aVar.k = aVar.H.a();
        aVar.G = new d.a.a.c.j(new n((f) aVar.H), d.x(), 1);
        aVar.I = new l(this);
        aVar.x = new k(this, aVar.f);
        aVar.y = new d(this, aVar.f);
        aVar.z = new e(this, aVar.f);
        aVar.D = new n(this);
        aVar.B = new i(this);
        aVar.A = new h(this, aVar.g);
        aVar.C = new d.a.a.c.j(new n(aVar.B, aVar.k, d.v(), 100), d.v(), 1);
        aVar.j = aVar.E.a();
        aVar.i = aVar.D.a();
        aVar.h = aVar.B.a();
    }

    /* access modifiers changed from: package-private */
    public int b(int i) {
        return g(i) ? 366 : 365;
    }

    /* access modifiers changed from: package-private */
    public int b(long j) {
        long j2;
        if (j >= 0) {
            j2 = j / 86400000;
        } else {
            j2 = (j - 86399999) / 86400000;
            if (j2 < -3) {
                return ((int) ((j2 + 4) % 7)) + 7;
            }
        }
        return ((int) ((j2 + 3) % 7)) + 1;
    }

    /* access modifiers changed from: package-private */
    public int b(long j, int i) {
        return ((int) ((j - f(i)) / 86400000)) + 1;
    }

    /* access modifiers changed from: package-private */
    public abstract long b(int i, int i2);

    /* access modifiers changed from: package-private */
    public long b(int i, int i2, int i3) {
        return f(i) + b(i, i2) + (((long) (i3 - 1)) * 86400000);
    }

    /* access modifiers changed from: package-private */
    public int c(long j) {
        return b(j, i(j));
    }

    /* access modifiers changed from: package-private */
    public int c(long j, int i) {
        return d(j);
    }

    /* access modifiers changed from: package-private */
    public long c(int i) {
        long f = f(i);
        int b2 = b(f);
        return b2 > 8 - this.fa ? f + (((long) (8 - b2)) * 86400000) : f - (((long) (b2 - 1)) * 86400000);
    }

    /* access modifiers changed from: package-private */
    public long c(int i, int i2) {
        return f(i) + b(i, i2);
    }

    /* access modifiers changed from: package-private */
    public int d(int i) {
        return V();
    }

    /* access modifiers changed from: package-private */
    public int d(long j) {
        int i = i(j);
        return a(i, d(j, i));
    }

    /* access modifiers changed from: package-private */
    public abstract int d(long j, int i);

    /* access modifiers changed from: package-private */
    public int e(int i) {
        return (int) ((c(i + 1) - c(i)) / 604800000);
    }

    /* access modifiers changed from: package-private */
    public int e(long j) {
        return j >= 0 ? (int) (j % 86400000) : ((int) ((j + 1) % 86400000)) + 86399999;
    }

    /* access modifiers changed from: package-private */
    public int e(long j, int i) {
        long c2 = c(i);
        if (j < c2) {
            return e(i - 1);
        }
        if (j >= c(i + 1)) {
            return 1;
        }
        return ((int) ((j - c2) / 604800000)) + 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        c cVar = (c) obj;
        return Y() == cVar.Y() && k().equals(cVar.k());
    }

    /* access modifiers changed from: package-private */
    public int f(long j) {
        return d(j, i(j));
    }

    /* access modifiers changed from: package-private */
    public long f(int i) {
        return h(i).f2018b;
    }

    /* access modifiers changed from: package-private */
    public abstract long f(long j, int i);

    /* access modifiers changed from: package-private */
    public int g(long j) {
        return e(j, i(j));
    }

    /* access modifiers changed from: package-private */
    public abstract boolean g(int i);

    /* access modifiers changed from: package-private */
    public int h(long j) {
        long j2;
        int i = i(j);
        int e = e(j, i);
        if (e == 1) {
            j2 = j + 604800000;
        } else if (e <= 51) {
            return i;
        } else {
            j2 = j - 1209600000;
        }
        return i(j2);
    }

    public int hashCode() {
        return (getClass().getName().hashCode() * 11) + k().hashCode() + Y();
    }

    /* access modifiers changed from: package-private */
    public int i(long j) {
        long S2 = S();
        long P2 = (j >> 1) + P();
        if (P2 < 0) {
            P2 = (P2 - S2) + 1;
        }
        int i = (int) (P2 / S2);
        long f = f(i);
        long j2 = j - f;
        if (j2 < 0) {
            return i - 1;
        }
        long j3 = 31536000000L;
        if (j2 < 31536000000L) {
            return i;
        }
        if (g(i)) {
            j3 = 31622400000L;
        }
        return f + j3 <= j ? i + 1 : i;
    }

    /* access modifiers changed from: package-private */
    public boolean j(long j) {
        return false;
    }

    @Override // d.a.a.a, d.a.a.b.a
    public d.a.a.g k() {
        d.a.a.a L = L();
        return L != null ? L.k() : d.a.a.g.f2149a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        String name = getClass().getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            name = name.substring(lastIndexOf + 1);
        }
        sb.append(name);
        sb.append('[');
        d.a.a.g k = k();
        if (k != null) {
            sb.append(k.c());
        }
        if (Y() != 4) {
            sb.append(",mdfw=");
            sb.append(Y());
        }
        sb.append(']');
        return sb.toString();
    }
}
