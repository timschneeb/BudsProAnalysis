package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.net.InetAddress;

class G extends J<InetAddress> {
    G() {
    }

    @Override // c.a.a.J
    public InetAddress a(b bVar) {
        if (bVar.o() != c.NULL) {
            return InetAddress.getByName(bVar.n());
        }
        bVar.m();
        return null;
    }

    public void a(d dVar, InetAddress inetAddress) {
        dVar.c(inetAddress == null ? null : inetAddress.getHostAddress());
    }
}
