package d.a.a.c;

import d.a.a.h;
import java.io.Serializable;

public final class i extends h implements Serializable {

    /* renamed from: a  reason: collision with root package name */
    public static final h f2048a = new i();

    private i() {
    }

    /* renamed from: a */
    public int compareTo(h hVar) {
        long c2 = hVar.c();
        long c3 = c();
        if (c3 == c2) {
            return 0;
        }
        return c3 < c2 ? -1 : 1;
    }

    @Override // d.a.a.h
    public long a(long j, int i) {
        return g.a(j, (long) i);
    }

    @Override // d.a.a.h
    public long a(long j, long j2) {
        return g.a(j, j2);
    }

    @Override // d.a.a.h
    public d.a.a.i b() {
        return d.a.a.i.g();
    }

    @Override // d.a.a.h
    public final long c() {
        return 1;
    }

    @Override // d.a.a.h
    public final boolean d() {
        return true;
    }

    @Override // d.a.a.h
    public boolean e() {
        return true;
    }

    public boolean equals(Object obj) {
        return (obj instanceof i) && c() == ((i) obj).c();
    }

    public int hashCode() {
        return (int) c();
    }

    public String toString() {
        return "DurationField[millis]";
    }
}
