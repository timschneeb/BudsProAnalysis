package androidx.lifecycle;

import androidx.lifecycle.f;

public class CompositeGeneratedAdaptersObserver implements e {

    /* renamed from: a  reason: collision with root package name */
    private final d[] f818a;

    CompositeGeneratedAdaptersObserver(d[] dVarArr) {
        this.f818a = dVarArr;
    }

    @Override // androidx.lifecycle.e
    public void a(h hVar, f.a aVar) {
        n nVar = new n();
        for (d dVar : this.f818a) {
            dVar.a(hVar, aVar, false, nVar);
        }
        for (d dVar2 : this.f818a) {
            dVar2.a(hVar, aVar, true, nVar);
        }
    }
}
