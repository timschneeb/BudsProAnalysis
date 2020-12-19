package d.a.a.c;

import d.a.a.h;
import d.a.a.i;
import java.io.Serializable;

public abstract class c extends h implements Serializable {

    /* renamed from: a  reason: collision with root package name */
    private final i f2040a;

    protected c(i iVar) {
        if (iVar != null) {
            this.f2040a = iVar;
            return;
        }
        throw new IllegalArgumentException("The type must not be null");
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
    public final i b() {
        return this.f2040a;
    }

    @Override // d.a.a.h
    public final boolean e() {
        return true;
    }

    public final String f() {
        return this.f2040a.d();
    }

    public String toString() {
        return "DurationField[" + f() + ']';
    }
}
