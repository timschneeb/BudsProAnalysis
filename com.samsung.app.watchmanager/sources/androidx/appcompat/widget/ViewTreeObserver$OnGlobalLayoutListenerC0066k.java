package androidx.appcompat.widget;

import android.view.ViewTreeObserver;
import b.e.g.AbstractC0112b;

/* renamed from: androidx.appcompat.widget.k  reason: case insensitive filesystem */
class ViewTreeObserver$OnGlobalLayoutListenerC0066k implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ActivityChooserView f479a;

    ViewTreeObserver$OnGlobalLayoutListenerC0066k(ActivityChooserView activityChooserView) {
        this.f479a = activityChooserView;
    }

    public void onGlobalLayout() {
        if (!this.f479a.b()) {
            return;
        }
        if (!this.f479a.isShown()) {
            this.f479a.getListPopupWindow().dismiss();
            return;
        }
        this.f479a.getListPopupWindow().c();
        AbstractC0112b bVar = this.f479a.j;
        if (bVar != null) {
            bVar.a(true);
        }
    }
}
