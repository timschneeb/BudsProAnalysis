package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.d;
import java.sql.Timestamp;
import java.util.Date;

class I extends J<Timestamp> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ J f1544a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ J f1545b;

    I(J j, J j2) {
        this.f1545b = j;
        this.f1544a = j2;
    }

    @Override // c.a.a.J
    public Timestamp a(b bVar) {
        Date date = (Date) this.f1544a.a(bVar);
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    public void a(d dVar, Timestamp timestamp) {
        this.f1544a.a(dVar, timestamp);
    }
}
