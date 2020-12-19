package androidx.fragment.app;

import android.graphics.Rect;
import android.transition.Transition;

class F extends Transition.EpicenterCallback {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Rect f708a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ G f709b;

    F(G g, Rect rect) {
        this.f709b = g;
        this.f708a = rect;
    }

    public Rect onGetEpicenter(Transition transition) {
        Rect rect = this.f708a;
        if (rect == null || rect.isEmpty()) {
            return null;
        }
        return this.f708a;
    }
}
