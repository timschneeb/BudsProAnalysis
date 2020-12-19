package c.b.c.b.a.a;

import c.b.c.a.b.i;

public abstract class c {

    /* renamed from: a  reason: collision with root package name */
    public i f1875a;

    /* renamed from: b  reason: collision with root package name */
    public i f1876b;

    /* renamed from: c  reason: collision with root package name */
    public float f1877c = -1.0f;

    /* renamed from: d  reason: collision with root package name */
    public i f1878d;
    public float e;
    public float f;
    public float g;
    public float h;
    public boolean i;
    public char j;
    public char k;
    b l;

    public c(b bVar) {
        this.l = bVar;
        bVar.d().add(this);
        b(1.0f);
        this.f1875a = new i();
        this.f1876b = new i();
        this.f1878d = new i();
        this.i = true;
        this.e = 10.0f;
        d(0.1f);
        this.k = 0;
        a((char) 1);
    }

    public float a() {
        return this.f;
    }

    public c a(char c2) {
        this.j = c2;
        return this;
    }

    public c a(float f2) {
        this.f1877c += f2;
        return this;
    }

    public c a(float f2, float f3) {
        this.f = 1.0f / (f2 * f3);
        return this;
    }

    public c a(i iVar) {
        this.f1878d.b(iVar);
        return this;
    }

    public i b() {
        return this.f1878d;
    }

    public c b(float f2) {
        a(f2, 60.0f);
        return this;
    }

    public c b(float f2, float f3) {
        float f4 = 1.0f / (f2 * f3);
        this.g = f4;
        this.f = f4;
        this.h = f4;
        return this;
    }

    public c b(i iVar) {
        this.f1875a.b(iVar);
        return this;
    }

    public float c() {
        return this.f1877c;
    }

    public c c(float f2) {
        this.f1877c = f2;
        return this;
    }

    public int d() {
        return this.j;
    }

    public c d(float f2) {
        this.g = f2;
        this.f = f2;
        this.h = f2;
        return this;
    }

    public i e() {
        return this.f1875a;
    }

    public c e(float f2) {
        b(f2, 60.0f);
        return this;
    }

    public float f() {
        return this.e;
    }

    public c f(float f2) {
        this.e = f2;
        return this;
    }

    public i g() {
        return this.f1876b;
    }

    public b h() {
        return this.l;
    }

    public c i() {
        this.l.d().remove(this);
        return this;
    }
}
