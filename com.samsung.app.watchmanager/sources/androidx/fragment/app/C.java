package androidx.fragment.app;

import android.graphics.Rect;
import android.transition.Transition;

class C extends Transition.EpicenterCallback {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Rect f699a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ G f700b;

    C(G g, Rect rect) {
        this.f700b = g;
        this.f699a = rect;
    }

    public Rect onGetEpicenter(Transition transition) {
        return this.f699a;
    }
}
