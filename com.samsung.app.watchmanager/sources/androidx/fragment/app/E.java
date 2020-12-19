package androidx.fragment.app;

import android.transition.Transition;
import android.view.View;
import java.util.ArrayList;

class E implements Transition.TransitionListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Object f704a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ArrayList f705b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Object f706c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ ArrayList f707d;
    final /* synthetic */ Object e;
    final /* synthetic */ ArrayList f;
    final /* synthetic */ G g;

    E(G g2, Object obj, ArrayList arrayList, Object obj2, ArrayList arrayList2, Object obj3, ArrayList arrayList3) {
        this.g = g2;
        this.f704a = obj;
        this.f705b = arrayList;
        this.f706c = obj2;
        this.f707d = arrayList2;
        this.e = obj3;
        this.f = arrayList3;
    }

    public void onTransitionCancel(Transition transition) {
    }

    public void onTransitionEnd(Transition transition) {
    }

    public void onTransitionPause(Transition transition) {
    }

    public void onTransitionResume(Transition transition) {
    }

    public void onTransitionStart(Transition transition) {
        Object obj = this.f704a;
        if (obj != null) {
            this.g.a(obj, this.f705b, (ArrayList<View>) null);
        }
        Object obj2 = this.f706c;
        if (obj2 != null) {
            this.g.a(obj2, this.f707d, (ArrayList<View>) null);
        }
        Object obj3 = this.e;
        if (obj3 != null) {
            this.g.a(obj3, this.f, (ArrayList<View>) null);
        }
    }
}
