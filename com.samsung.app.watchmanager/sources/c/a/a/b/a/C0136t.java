package c.a.a.b.a;

import c.a.a.D;
import c.a.a.J;
import c.a.a.K;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* renamed from: c.a.a.b.a.t  reason: case insensitive filesystem */
public final class C0136t extends J<Time> {

    /* renamed from: a  reason: collision with root package name */
    public static final K f1594a = new C0135s();

    /* renamed from: b  reason: collision with root package name */
    private final DateFormat f1595b = new SimpleDateFormat("hh:mm:ss a");

    @Override // c.a.a.J
    public synchronized Time a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        try {
            return new Time(this.f1595b.parse(bVar.n()).getTime());
        } catch (ParseException e) {
            throw new D(e);
        }
    }

    public synchronized void a(d dVar, Time time) {
        dVar.c(time == null ? null : this.f1595b.format(time));
    }
}
