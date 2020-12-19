package b.e.f;

public class d<F, S> {

    /* renamed from: a  reason: collision with root package name */
    public final F f1393a;

    /* renamed from: b  reason: collision with root package name */
    public final S f1394b;

    public d(F f, S s) {
        this.f1393a = f;
        this.f1394b = s;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return c.a(dVar.f1393a, this.f1393a) && c.a(dVar.f1394b, this.f1394b);
    }

    public int hashCode() {
        F f = this.f1393a;
        int i = 0;
        int hashCode = f == null ? 0 : f.hashCode();
        S s = this.f1394b;
        if (s != null) {
            i = s.hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.f1393a) + " " + String.valueOf(this.f1394b) + "}";
    }
}
