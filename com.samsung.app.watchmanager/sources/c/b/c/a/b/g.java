package c.b.c.a.b;

public class g {

    /* renamed from: a  reason: collision with root package name */
    a f1846a;

    /* renamed from: b  reason: collision with root package name */
    i f1847b;

    /* renamed from: c  reason: collision with root package name */
    i f1848c;

    /* renamed from: d  reason: collision with root package name */
    i f1849d;
    float e;
    i f;

    public g() {
        f();
        g();
    }

    public float a() {
        return this.e;
    }

    public g a(i iVar, i iVar2, i iVar3, float f2) {
        this.f1847b.b(iVar);
        this.f1848c.d(iVar2);
        this.f1849d.b(iVar3);
        this.e = f2;
        h();
        return this;
    }

    public i b() {
        return this.f1849d;
    }

    public a c() {
        return this.f1846a;
    }

    public i d() {
        return this.f1847b;
    }

    public i e() {
        return this.f1848c;
    }

    /* access modifiers changed from: package-private */
    public void f() {
        this.f = new i();
        this.f1847b = new i();
        this.f1848c = new i();
        this.f1849d = new i();
        this.f1846a = new a();
    }

    public g g() {
        throw null;
    }

    public g h() {
        a aVar = new a();
        a aVar2 = this.f1846a;
        i iVar = this.f1848c;
        aVar2.a(iVar.f1850a, iVar.f1851b, iVar.f1852c);
        aVar.a(this.f1849d, this.e);
        this.f1846a.b(aVar);
        aVar.a(this.f1847b);
        this.f1846a.b(aVar);
        return this;
    }
}
