package androidx.appcompat.app;

import android.view.View;
import b.e.g.A;
import b.e.g.B;

class s extends B {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ t f197a;

    s(t tVar) {
        this.f197a = tVar;
    }

    @Override // b.e.g.A
    public void b(View view) {
        this.f197a.f198a.q.setAlpha(1.0f);
        this.f197a.f198a.t.a((A) null);
        this.f197a.f198a.t = null;
    }

    @Override // b.e.g.B, b.e.g.A
    public void c(View view) {
        this.f197a.f198a.q.setVisibility(0);
    }
}
