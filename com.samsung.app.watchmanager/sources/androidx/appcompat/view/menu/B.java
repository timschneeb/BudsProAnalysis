package androidx.appcompat.view.menu;

import android.view.View;
import android.view.ViewTreeObserver;

/* access modifiers changed from: package-private */
public class B implements View.OnAttachStateChangeListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ C f207a;

    B(C c2) {
        this.f207a = c2;
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        ViewTreeObserver viewTreeObserver = this.f207a.q;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.f207a.q = view.getViewTreeObserver();
            }
            C c2 = this.f207a;
            c2.q.removeGlobalOnLayoutListener(c2.k);
        }
        view.removeOnAttachStateChangeListener(this);
    }
}
