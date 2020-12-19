package androidx.appcompat.app;

import android.view.View;
import b.e.g.A;
import b.e.g.B;
import b.e.g.t;

class u extends B {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatDelegateImpl f199a;

    u(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f199a = appCompatDelegateImpl;
    }

    @Override // b.e.g.A
    public void b(View view) {
        this.f199a.q.setAlpha(1.0f);
        this.f199a.t.a((A) null);
        this.f199a.t = null;
    }

    @Override // b.e.g.B, b.e.g.A
    public void c(View view) {
        this.f199a.q.setVisibility(0);
        this.f199a.q.sendAccessibilityEvent(32);
        if (this.f199a.q.getParent() instanceof View) {
            t.z((View) this.f199a.q.getParent());
        }
    }
}
