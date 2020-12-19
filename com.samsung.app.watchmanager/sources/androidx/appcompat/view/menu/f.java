package androidx.appcompat.view.menu;

import android.view.View;
import android.view.ViewTreeObserver;

/* access modifiers changed from: package-private */
public class f implements View.OnAttachStateChangeListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ i f231a;

    f(i iVar) {
        this.f231a = iVar;
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        ViewTreeObserver viewTreeObserver = this.f231a.z;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.f231a.z = view.getViewTreeObserver();
            }
            i iVar = this.f231a;
            iVar.z.removeGlobalOnLayoutListener(iVar.k);
        }
        view.removeOnAttachStateChangeListener(this);
    }
}
