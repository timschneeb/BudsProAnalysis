package d.a.a.b;

import d.a.a.a;
import d.a.a.b.a;
import d.a.a.g;
import java.util.concurrent.ConcurrentHashMap;

public final class p extends f {
    private static final p ka = b(g.f2149a);
    private static final ConcurrentHashMap<g, p[]> la = new ConcurrentHashMap<>();

    private p(a aVar, Object obj, int i) {
        super(aVar, obj, i);
    }

    public static p Z() {
        return ka;
    }

    public static p a(g gVar, int i) {
        p[] putIfAbsent;
        if (gVar == null) {
            gVar = g.b();
        }
        p[] pVarArr = la.get(gVar);
        if (pVarArr == null && (putIfAbsent = la.putIfAbsent(gVar, (pVarArr = new p[7]))) != null) {
            pVarArr = putIfAbsent;
        }
        int i2 = i - 1;
        try {
            p pVar = pVarArr[i2];
            if (pVar == null) {
                synchronized (pVarArr) {
                    pVar = pVarArr[i2];
                    if (pVar == null) {
                        p pVar2 = gVar == g.f2149a ? new p(null, null, i) : new p(s.a(a(g.f2149a, i), gVar), null, i);
                        pVarArr[i2] = pVar2;
                        pVar = pVar2;
                    }
                }
            }
            return pVar;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new IllegalArgumentException("Invalid min days in first week: " + i);
        }
    }

    public static p b(g gVar) {
        return a(gVar, 4);
    }

    @Override // d.a.a.a
    public a G() {
        return ka;
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public long P() {
        return 31083597720000L;
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public long Q() {
        return 2629746000L;
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public long R() {
        return 31556952000L;
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public long S() {
        return 15778476000L;
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public int W() {
        return 292278993;
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public int X() {
        return -292275054;
    }

    @Override // d.a.a.b.c
    public /* bridge */ /* synthetic */ int Y() {
        return super.Y();
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public long a(int i) {
        int i2;
        int i3 = i / 100;
        if (i < 0) {
            i2 = ((((i + 3) >> 2) - i3) + ((i3 + 3) >> 2)) - 1;
        } else {
            i2 = ((i >> 2) - i3) + (i3 >> 2);
            if (g(i)) {
                i2--;
            }
        }
        return ((((long) i) * 365) + ((long) (i2 - 719527))) * 86400000;
    }

    @Override // d.a.a.a, d.a.a.b.b, d.a.a.b.c, d.a.a.b.a
    public /* bridge */ /* synthetic */ long a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return super.a(i, i2, i3, i4, i5, i6, i7);
    }

    @Override // d.a.a.a
    public a a(g gVar) {
        if (gVar == null) {
            gVar = g.b();
        }
        return gVar == k() ? this : b(gVar);
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.b.c, d.a.a.b.a
    public void a(a.C0040a aVar) {
        if (L() == null) {
            super.a(aVar);
        }
    }

    @Override // d.a.a.b.c
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public boolean g(int i) {
        return (i & 3) == 0 && (i % 100 != 0 || i % 400 == 0);
    }

    @Override // d.a.a.b.c
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // d.a.a.a, d.a.a.b.c, d.a.a.b.a
    public /* bridge */ /* synthetic */ g k() {
        return super.k();
    }

    @Override // d.a.a.b.c
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }
}
