package d.a.a;

import d.a.a.a.c;
import java.io.Serializable;

public final class b extends c implements p, Serializable {
    public b() {
    }

    public b(long j, a aVar) {
        super(j, aVar);
    }

    public static b h() {
        return new b();
    }

    public b a(int i) {
        return i == 0 ? this : d(getChronology().h().a(a(), i));
    }

    public b a(int i, int i2, int i3, int i4) {
        a chronology = getChronology();
        return d(chronology.k().a(chronology.G().a(g(), e(), d(), i, i2, i3, i4), false, a()));
    }

    public b a(g gVar) {
        return b(getChronology().a(gVar));
    }

    public b b(int i) {
        return i == 0 ? this : d(getChronology().q().a(a(), i));
    }

    public b b(a aVar) {
        a a2 = e.a(aVar);
        return a2 == getChronology() ? this : new b(a(), a2);
    }

    public b c(int i) {
        return i == 0 ? this : d(getChronology().v().a(a(), i));
    }

    public b d(long j) {
        return j == a() ? this : new b(j, getChronology());
    }
}
