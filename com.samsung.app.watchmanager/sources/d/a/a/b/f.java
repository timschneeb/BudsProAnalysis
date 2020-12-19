package d.a.a.b;

import d.a.a.a;

abstract class f extends c {
    private static final int[] ga = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] ha = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final long[] ia = new long[12];
    private static final long[] ja = new long[12];

    static {
        long j = 0;
        int i = 0;
        long j2 = 0;
        while (i < 11) {
            j += ((long) ga[i]) * 86400000;
            int i2 = i + 1;
            ia[i2] = j;
            j2 += ((long) ha[i]) * 86400000;
            ja[i2] = j2;
            i = i2;
        }
    }

    f(a aVar, Object obj, int i) {
        super(aVar, obj, i);
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public int a(int i, int i2) {
        return g(i) ? ha[i2 - 1] : ga[i2 - 1];
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public long b(int i, int i2) {
        return g(i) ? ja[i2 - 1] : ia[i2 - 1];
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public int c(long j, int i) {
        if (i > 28 || i < 1) {
            return d(j);
        }
        return 28;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0072 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0080 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0082 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0095 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0098 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a8 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00ab A[ORIG_RETURN, RETURN, SYNTHETIC] */
    @Override // d.a.a.b.c
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int d(long r13, int r15) {
        /*
        // Method dump skipped, instructions count: 174
        */
        throw new UnsupportedOperationException("Method not decompiled: d.a.a.b.f.d(long, int):int");
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public long f(long j, int i) {
        int i2 = i(j);
        int b2 = b(j, i2);
        int e = e(j);
        if (b2 > 59) {
            if (g(i2)) {
                if (!g(i)) {
                    b2--;
                }
            } else if (g(i)) {
                b2++;
            }
        }
        return b(i, 1, b2) + ((long) e);
    }

    /* access modifiers changed from: package-private */
    @Override // d.a.a.b.c
    public boolean j(long j) {
        return e().a(j) == 29 && w().b(j);
    }
}
