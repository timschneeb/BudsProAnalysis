package androidx.appcompat.app;

import android.view.View;
import b.e.g.B;

/* access modifiers changed from: package-private */
public class E extends B {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ G f156a;

    E(G g) {
        this.f156a = g;
    }

    @Override // b.e.g.A
    public void b(View view) {
        G g = this.f156a;
        g.B = null;
        g.h.requestLayout();
    }
}
