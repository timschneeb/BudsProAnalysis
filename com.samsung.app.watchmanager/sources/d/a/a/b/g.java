package d.a.a.b;

import d.a.a.c.h;
import d.a.a.d;

/* access modifiers changed from: package-private */
public class g extends h {

    /* renamed from: d  reason: collision with root package name */
    private final c f2021d;
    private final int e = this.f2021d.V();
    private final int f;

    g(c cVar, int i) {
        super(d.q(), cVar.Q());
        this.f2021d = cVar;
        this.f = i;
    }

    @Override // d.a.a.c
    public int a(long j) {
        return this.f2021d.f(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (i == 0) {
            return j;
        }
        long e2 = (long) this.f2021d.e(j);
        int i7 = this.f2021d.i(j);
        int d2 = this.f2021d.d(j, i7);
        int i8 = d2 - 1;
        int i9 = i8 + i;
        if (d2 <= 0 || i9 >= 0) {
            i2 = i9;
            i3 = i7;
        } else {
            if (Math.signum((float) (this.e + i)) == Math.signum((float) i)) {
                i3 = i7 - 1;
                i6 = i + this.e;
            } else {
                i3 = i7 + 1;
                i6 = i - this.e;
            }
            i2 = i6 + i8;
        }
        if (i2 >= 0) {
            int i10 = this.e;
            i5 = i3 + (i2 / i10);
            i4 = (i2 % i10) + 1;
        } else {
            i5 = (i3 + (i2 / this.e)) - 1;
            int abs = Math.abs(i2);
            int i11 = this.e;
            int i12 = abs % i11;
            if (i12 == 0) {
                i12 = i11;
            }
            i4 = (this.e - i12) + 1;
            if (i4 == 1) {
                i5++;
            }
        }
        int a2 = this.f2021d.a(j, i7, d2);
        int a3 = this.f2021d.a(i5, i4);
        if (a2 > a3) {
            a2 = a3;
        }
        return this.f2021d.b(i5, i4, a2) + e2;
    }

    @Override // d.a.a.c.h
    public long a(long j, long j2) {
        long j3;
        long j4;
        int i = (int) j2;
        if (((long) i) == j2) {
            return a(j, i);
        }
        long e2 = (long) this.f2021d.e(j);
        int i2 = this.f2021d.i(j);
        int d2 = this.f2021d.d(j, i2);
        long j5 = ((long) (d2 - 1)) + j2;
        if (j5 >= 0) {
            int i3 = this.e;
            j3 = ((long) i2) + (j5 / ((long) i3));
            j4 = (j5 % ((long) i3)) + 1;
        } else {
            j3 = (((long) i2) + (j5 / ((long) this.e))) - 1;
            long abs = Math.abs(j5);
            int i4 = this.e;
            int i5 = (int) (abs % ((long) i4));
            if (i5 != 0) {
                i4 = i5;
            }
            j4 = (long) ((this.e - i4) + 1);
            if (j4 == 1) {
                j3++;
            }
        }
        if (j3 < ((long) this.f2021d.X()) || j3 > ((long) this.f2021d.W())) {
            throw new IllegalArgumentException("Magnitude of add amount is too large: " + j2);
        }
        int i6 = (int) j3;
        int i7 = (int) j4;
        int a2 = this.f2021d.a(j, i2, d2);
        int a3 = this.f2021d.a(i6, i7);
        if (a2 > a3) {
            a2 = a3;
        }
        return this.f2021d.b(i6, i7, a2) + e2;
    }

    @Override // d.a.a.c
    public long b(long j, int i) {
        d.a.a.c.g.a(this, i, 1, this.e);
        int i2 = this.f2021d.i(j);
        int a2 = this.f2021d.a(j, i2);
        int a3 = this.f2021d.a(i2, i);
        if (a2 > a3) {
            a2 = a3;
        }
        return this.f2021d.b(i2, i, a2) + ((long) this.f2021d.e(j));
    }

    @Override // d.a.a.c, d.a.a.c.b
    public d.a.a.h b() {
        return this.f2021d.h();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public boolean b(long j) {
        int i = this.f2021d.i(j);
        return this.f2021d.g(i) && this.f2021d.d(j, i) == this.f;
    }

    @Override // d.a.a.c
    public int c() {
        return this.e;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long c(long j) {
        return j - e(j);
    }

    @Override // d.a.a.c
    public int d() {
        return 1;
    }

    @Override // d.a.a.c
    public long e(long j) {
        int i = this.f2021d.i(j);
        return this.f2021d.c(i, this.f2021d.d(j, i));
    }

    @Override // d.a.a.c
    public d.a.a.h f() {
        return this.f2021d.K();
    }

    @Override // d.a.a.c
    public boolean h() {
        return false;
    }
}
