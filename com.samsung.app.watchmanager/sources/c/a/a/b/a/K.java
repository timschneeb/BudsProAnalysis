package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.util.Calendar;
import java.util.GregorianCalendar;

class K extends J<Calendar> {
    K() {
    }

    @Override // c.a.a.J
    public Calendar a(b bVar) {
        if (bVar.o() == c.NULL) {
            bVar.m();
            return null;
        }
        bVar.b();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (bVar.o() != c.END_OBJECT) {
            String l = bVar.l();
            int j = bVar.j();
            if ("year".equals(l)) {
                i = j;
            } else if ("month".equals(l)) {
                i2 = j;
            } else if ("dayOfMonth".equals(l)) {
                i3 = j;
            } else if ("hourOfDay".equals(l)) {
                i4 = j;
            } else if ("minute".equals(l)) {
                i5 = j;
            } else if ("second".equals(l)) {
                i6 = j;
            }
        }
        bVar.d();
        return new GregorianCalendar(i, i2, i3, i4, i5, i6);
    }

    public void a(d dVar, Calendar calendar) {
        if (calendar == null) {
            dVar.h();
            return;
        }
        dVar.b();
        dVar.a("year");
        dVar.a((long) calendar.get(1));
        dVar.a("month");
        dVar.a((long) calendar.get(2));
        dVar.a("dayOfMonth");
        dVar.a((long) calendar.get(5));
        dVar.a("hourOfDay");
        dVar.a((long) calendar.get(11));
        dVar.a("minute");
        dVar.a((long) calendar.get(12));
        dVar.a("second");
        dVar.a((long) calendar.get(13));
        dVar.d();
    }
}
