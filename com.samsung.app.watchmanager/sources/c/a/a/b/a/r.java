package c.a.a.b.a;

import c.a.a.D;
import c.a.a.J;
import c.a.a.K;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class r extends J<Date> {

    /* renamed from: a  reason: collision with root package name */
    public static final K f1592a = new C0134q();

    /* renamed from: b  reason: collision with root package name */
    private final DateFormat f1593b = new SimpleDateFormat("MMM d, yyyy");

    @Override // c.a.a.J
    public synchronized Date a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        try {
            return new Date(this.f1593b.parse(bVar.n()).getTime());
        } catch (ParseException e) {
            throw new D(e);
        }
    }

    public synchronized void a(d dVar, Date date) {
        dVar.c(date == null ? null : this.f1593b.format(date));
    }
}
