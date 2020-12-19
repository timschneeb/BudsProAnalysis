package c.a.a;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class s extends v implements Iterable<v> {

    /* renamed from: a  reason: collision with root package name */
    private final List<v> f1688a = new ArrayList();

    public void a(v vVar) {
        if (vVar == null) {
            vVar = x.f1689a;
        }
        this.f1688a.add(vVar);
    }

    @Override // c.a.a.v
    public String d() {
        if (this.f1688a.size() == 1) {
            return this.f1688a.get(0).d();
        }
        throw new IllegalStateException();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof s) && ((s) obj).f1688a.equals(this.f1688a));
    }

    public int hashCode() {
        return this.f1688a.hashCode();
    }

    @Override // java.lang.Iterable
    public Iterator<v> iterator() {
        return this.f1688a.iterator();
    }
}
