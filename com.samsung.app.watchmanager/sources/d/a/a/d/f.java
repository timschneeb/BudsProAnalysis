package d.a.a.d;

/* access modifiers changed from: package-private */
public class f implements k {

    /* renamed from: a  reason: collision with root package name */
    private final d f2118a;

    private f(d dVar) {
        this.f2118a = dVar;
    }

    static k a(d dVar) {
        if (dVar instanceof l) {
            return (k) dVar;
        }
        if (dVar == null) {
            return null;
        }
        return new f(dVar);
    }

    @Override // d.a.a.d.k
    public int a(e eVar, CharSequence charSequence, int i) {
        return this.f2118a.a(eVar, charSequence.toString(), i);
    }

    /* access modifiers changed from: package-private */
    public d a() {
        return this.f2118a;
    }

    @Override // d.a.a.d.k
    public int b() {
        return this.f2118a.b();
    }
}
