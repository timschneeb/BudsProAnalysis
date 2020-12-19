package c.b.b.a.a.a.f.b;

import c.b.b.a.a.a.c.a;
import c.b.b.a.a.a.f.c;

class b extends a {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ int f1762a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f1763b;

    b(c cVar, int i) {
        this.f1763b = cVar;
        this.f1762a = i;
    }

    @Override // c.b.b.a.a.a.c.a
    public void a(int i, String str, String str2, String str3) {
        this.f1763b.e.a(Long.valueOf(str).longValue(), str2, str3.equals(c.DEVICE.b()) ? c.DEVICE : c.UIX);
        c.b.b.a.a.a.d.b.b(this.f1763b.f1743a, this.f1762a, str2.getBytes().length * -1);
    }

    @Override // c.b.b.a.a.a.c.a
    public void b(int i, String str, String str2, String str3) {
    }
}
