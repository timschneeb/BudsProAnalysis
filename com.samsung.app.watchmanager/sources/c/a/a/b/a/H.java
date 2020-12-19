package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.util.UUID;

class H extends J<UUID> {
    H() {
    }

    @Override // c.a.a.J
    public UUID a(b bVar) {
        if (bVar.o() != c.NULL) {
            return UUID.fromString(bVar.n());
        }
        bVar.m();
        return null;
    }

    public void a(d dVar, UUID uuid) {
        dVar.c(uuid == null ? null : uuid.toString());
    }
}
