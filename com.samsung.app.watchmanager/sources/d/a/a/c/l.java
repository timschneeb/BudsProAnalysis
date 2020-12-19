package d.a.a.c;

import d.a.a.d;
import d.a.a.h;

public abstract class l extends b {

    /* renamed from: b  reason: collision with root package name */
    final long f2052b;

    /* renamed from: c  reason: collision with root package name */
    private final h f2053c;

    public l(d dVar, h hVar) {
        super(dVar);
        if (hVar.d()) {
            this.f2052b = hVar.c();
            if (this.f2052b >= 1) {
                this.f2053c = hVar;
                return;
            }
            throw new IllegalArgumentException("The unit milliseconds must be at least 1");
        }
        throw new IllegalArgumentException("Unit duration field must be precise");
    }

    @Override // d.a.a.c
    public h a() {
        return this.f2053c;
    }

    @Override // d.a.a.c
    public long b(long j, int i) {
        g.a(this, i, d(), d(j, i));
        return j + (((long) (i - a(j))) * this.f2052b);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long c(long j) {
        if (j >= 0) {
            return j % this.f2052b;
        }
        long j2 = this.f2052b;
        return (((j + 1) % j2) + j2) - 1;
    }

    @Override // d.a.a.c
    public int d() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int d(long j, int i) {
        return i(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long d(long j) {
        if (j <= 0) {
            return j - (j % this.f2052b);
        }
        long j2 = j - 1;
        long j3 = this.f2052b;
        return (j2 - (j2 % j3)) + j3;
    }

    @Override // d.a.a.c
    public long e(long j) {
        long j2;
        if (j >= 0) {
            j2 = j % this.f2052b;
        } else {
            long j3 = j + 1;
            j2 = this.f2052b;
            j = j3 - (j3 % j2);
        }
        return j - j2;
    }

    @Override // d.a.a.c
    public boolean h() {
        return false;
    }

    public final long j() {
        return this.f2052b;
    }
}
