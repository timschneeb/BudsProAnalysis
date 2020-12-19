package androidx.appcompat.app;

import android.view.View;
import b.e.g.C;

/* access modifiers changed from: package-private */
public class F implements C {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ G f157a;

    F(G g) {
        this.f157a = g;
    }

    @Override // b.e.g.C
    public void a(View view) {
        ((View) this.f157a.h.getParent()).invalidate();
    }
}
