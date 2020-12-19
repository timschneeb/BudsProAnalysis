package c.a.a;

import c.a.a.b.v;
import java.util.Map;
import java.util.Set;

public final class y extends v {

    /* renamed from: a  reason: collision with root package name */
    private final v<String, v> f1690a = new v<>();

    public void a(String str, v vVar) {
        if (vVar == null) {
            vVar = x.f1689a;
        }
        this.f1690a.put(str, vVar);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof y) && ((y) obj).f1690a.equals(this.f1690a));
    }

    public int hashCode() {
        return this.f1690a.hashCode();
    }

    public Set<Map.Entry<String, v>> i() {
        return this.f1690a.entrySet();
    }
}
