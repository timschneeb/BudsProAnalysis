package d.a.a.c;

import androidx.recyclerview.widget.LinearLayoutManager;
import d.a.a.c;
import d.a.a.d;
import d.a.a.h;

public class j extends d {

    /* renamed from: c  reason: collision with root package name */
    private final int f2049c;

    /* renamed from: d  reason: collision with root package name */
    private final int f2050d;
    private final int e;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public j(c cVar, int i) {
        this(cVar, cVar == null ? null : cVar.g(), i, LinearLayoutManager.INVALID_OFFSET, Integer.MAX_VALUE);
    }

    public j(c cVar, d dVar, int i) {
        this(cVar, dVar, i, LinearLayoutManager.INVALID_OFFSET, Integer.MAX_VALUE);
    }

    public j(c cVar, d dVar, int i, int i2, int i3) {
        super(cVar, dVar);
        if (i != 0) {
            this.f2049c = i;
            if (i2 < cVar.d() + i) {
                this.f2050d = cVar.d() + i;
            } else {
                this.f2050d = i2;
            }
            if (i3 > cVar.c() + i) {
                this.e = cVar.c() + i;
            } else {
                this.e = i3;
            }
        } else {
            throw new IllegalArgumentException("The offset cannot be zero");
        }
    }

    @Override // d.a.a.c, d.a.a.c.d
    public int a(long j) {
        return super.a(j) + this.f2049c;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long a(long j, int i) {
        long a2 = super.a(j, i);
        g.a(this, a(a2), this.f2050d, this.e);
        return a2;
    }

    @Override // d.a.a.c, d.a.a.c.d
    public long b(long j, int i) {
        g.a(this, i, this.f2050d, this.e);
        return super.b(j, i - this.f2049c);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public h b() {
        return j().b();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public boolean b(long j) {
        return j().b(j);
    }

    @Override // d.a.a.c
    public int c() {
        return this.e;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long c(long j) {
        return j().c(j);
    }

    @Override // d.a.a.c
    public int d() {
        return this.f2050d;
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long d(long j) {
        return j().d(j);
    }

    @Override // d.a.a.c
    public long e(long j) {
        return j().e(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long f(long j) {
        return j().f(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long g(long j) {
        return j().g(j);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public long h(long j) {
        return j().h(j);
    }
}
