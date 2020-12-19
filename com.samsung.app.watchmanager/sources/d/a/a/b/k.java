package d.a.a.b;

import d.a.a.c.l;
import d.a.a.d;
import d.a.a.h;
import java.util.Locale;

final class k extends l {

    /* renamed from: d  reason: collision with root package name */
    private final c f2025d;

    k(c cVar, h hVar) {
        super(d.e(), hVar);
        this.f2025d = cVar;
    }

    @Override // d.a.a.c
    public int a(long j) {
        return this.f2025d.b(j);
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.c.b
    public int a(String str, Locale locale) {
        return m.a(locale).a(str);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public int a(Locale locale) {
        return m.a(locale).a();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public String a(int i, Locale locale) {
        return m.a(locale).a(i);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public String b(int i, Locale locale) {
        return m.a(locale).b(i);
    }

    @Override // d.a.a.c
    public int c() {
        return 7;
    }

    @Override // d.a.a.c, d.a.a.c.l
    public int d() {
        return 1;
    }

    @Override // d.a.a.c
    public h f() {
        return this.f2025d.C();
    }
}
