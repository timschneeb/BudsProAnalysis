package androidx.appcompat.app;

import android.view.View;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import b.e.g.B;
import b.e.g.t;

/* access modifiers changed from: package-private */
public class D extends B {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ G f155a;

    D(G g) {
        this.f155a = g;
    }

    @Override // b.e.g.A
    public void b(View view) {
        View view2;
        G g = this.f155a;
        if (g.w && (view2 = g.k) != null) {
            view2.setTranslationY(0.0f);
            this.f155a.h.setTranslationY(0.0f);
        }
        this.f155a.h.setVisibility(8);
        this.f155a.h.setTransitioning(false);
        G g2 = this.f155a;
        g2.B = null;
        g2.l();
        ActionBarOverlayLayout actionBarOverlayLayout = this.f155a.g;
        if (actionBarOverlayLayout != null) {
            t.z(actionBarOverlayLayout);
        }
    }
}
