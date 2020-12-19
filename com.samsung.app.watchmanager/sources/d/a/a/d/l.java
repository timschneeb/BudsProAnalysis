package d.a.a.d;

/* access modifiers changed from: package-private */
public class l implements d, k {

    /* renamed from: a  reason: collision with root package name */
    private final k f2125a;

    private l(k kVar) {
        this.f2125a = kVar;
    }

    static d a(k kVar) {
        if (kVar instanceof f) {
            return ((f) kVar).a();
        }
        if (kVar instanceof d) {
            return (d) kVar;
        }
        if (kVar == null) {
            return null;
        }
        return new l(kVar);
    }

    @Override // d.a.a.d.k
    public int a(e eVar, CharSequence charSequence, int i) {
        return this.f2125a.a(eVar, charSequence, i);
    }

    @Override // d.a.a.d.d
    public int a(e eVar, String str, int i) {
        return this.f2125a.a(eVar, str, i);
    }

    @Override // d.a.a.d.k, d.a.a.d.d
    public int b() {
        return this.f2125a.b();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof l) {
            return this.f2125a.equals(((l) obj).f2125a);
        }
        return false;
    }
}
