package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.net.URL;

class D extends J<URL> {
    D() {
    }

    @Override // c.a.a.J
    public URL a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        String n = bVar.n();
        if ("null".equals(n)) {
            return null;
        }
        return new URL(n);
    }

    public void a(d dVar, URL url) {
        dVar.c(url == null ? null : url.toExternalForm());
    }
}
