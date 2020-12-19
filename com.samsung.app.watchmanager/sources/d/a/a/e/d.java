package d.a.a.e;

import d.a.a.g;

public final class d extends g {
    private final String f;
    private final int g;
    private final int h;

    public d(String str, String str2, int i, int i2) {
        super(str);
        this.f = str2;
        this.g = i;
        this.h = i2;
    }

    @Override // d.a.a.g
    public String b(long j) {
        return this.f;
    }

    @Override // d.a.a.g
    public int c(long j) {
        return this.g;
    }

    @Override // d.a.a.g
    public int d(long j) {
        return this.g;
    }

    @Override // d.a.a.g
    public int e(long j) {
        return this.h;
    }

    @Override // d.a.a.g
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return c().equals(dVar.c()) && this.h == dVar.h && this.g == dVar.g;
    }

    @Override // d.a.a.g
    public boolean f() {
        return true;
    }

    @Override // d.a.a.g
    public long g(long j) {
        return j;
    }

    @Override // d.a.a.g
    public long h(long j) {
        return j;
    }

    @Override // d.a.a.g
    public int hashCode() {
        return c().hashCode() + (this.h * 37) + (this.g * 31);
    }
}
