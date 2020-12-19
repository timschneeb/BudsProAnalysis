package androidx.fragment.app;

import android.transition.Transition;
import android.view.View;
import java.util.ArrayList;

class D implements Transition.TransitionListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ View f701a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ArrayList f702b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ G f703c;

    D(G g, View view, ArrayList arrayList) {
        this.f703c = g;
        this.f701a = view;
        this.f702b = arrayList;
    }

    public void onTransitionCancel(Transition transition) {
    }

    public void onTransitionEnd(Transition transition) {
        transition.removeListener(this);
        this.f701a.setVisibility(8);
        int size = this.f702b.size();
        for (int i = 0; i < size; i++) {
            ((View) this.f702b.get(i)).setVisibility(0);
        }
    }

    public void onTransitionPause(Transition transition) {
    }

    public void onTransitionResume(Transition transition) {
    }

    public void onTransitionStart(Transition transition) {
    }
}
