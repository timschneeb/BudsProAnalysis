package d.a.a.b;

import java.util.Locale;

final class n extends g {
    n(c cVar) {
        super(cVar, 2);
    }

    /* access modifiers changed from: protected */
    @Override // d.a.a.c.b
    public int a(String str, Locale locale) {
        return m.a(locale).d(str);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public int a(Locale locale) {
        return m.a(locale).d();
    }

    @Override // d.a.a.c, d.a.a.c.b
    public String a(int i, Locale locale) {
        return m.a(locale).e(i);
    }

    @Override // d.a.a.c, d.a.a.c.b
    public String b(int i, Locale locale) {
        return m.a(locale).f(i);
    }
}
