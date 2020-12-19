package d.a.a.a;

import d.a.a.c.g;
import d.a.a.d.j;
import d.a.a.e;
import d.a.a.q;

public abstract class b implements q {
    protected b() {
    }

    /* renamed from: a */
    public int compareTo(q qVar) {
        if (this == qVar) {
            return 0;
        }
        long a2 = qVar.a();
        long a3 = a();
        if (a3 == a2) {
            return 0;
        }
        return a3 < a2 ? -1 : 1;
    }

    public String a(d.a.a.d.b bVar) {
        return bVar == null ? toString() : bVar.a(this);
    }

    public boolean a(long j) {
        return a() > j;
    }

    public boolean b() {
        return a(e.a());
    }

    public boolean b(long j) {
        return a() < j;
    }

    public boolean c() {
        return b(e.a());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof q)) {
            return false;
        }
        q qVar = (q) obj;
        return a() == qVar.a() && g.a(getChronology(), qVar.getChronology());
    }

    public int hashCode() {
        return ((int) (a() ^ (a() >>> 32))) + getChronology().hashCode();
    }

    public String toString() {
        return j.b().a(this);
    }
}
