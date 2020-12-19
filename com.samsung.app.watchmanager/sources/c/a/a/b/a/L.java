package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.util.Locale;
import java.util.StringTokenizer;

class L extends J<Locale> {
    L() {
    }

    @Override // c.a.a.J
    public Locale a(b bVar) {
        String str = null;
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(bVar.n(), "_");
        String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
        String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
        if (stringTokenizer.hasMoreElements()) {
            str = stringTokenizer.nextToken();
        }
        return (nextToken2 == null && str == null) ? new Locale(nextToken) : str == null ? new Locale(nextToken, nextToken2) : new Locale(nextToken, nextToken2, str);
    }

    public void a(d dVar, Locale locale) {
        dVar.c(locale == null ? null : locale.toString());
    }
}
