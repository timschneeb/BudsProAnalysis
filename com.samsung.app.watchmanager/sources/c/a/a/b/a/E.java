package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import c.a.a.w;
import java.net.URI;
import java.net.URISyntaxException;

class E extends J<URI> {
    E() {
    }

    @Override // c.a.a.J
    public URI a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        try {
            String n = bVar.n();
            if ("null".equals(n)) {
                return null;
            }
            return new URI(n);
        } catch (URISyntaxException e) {
            throw new w(e);
        }
    }

    public void a(d dVar, URI uri) {
        dVar.c(uri == null ? null : uri.toASCIIString());
    }
}
