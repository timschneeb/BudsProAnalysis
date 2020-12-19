package androidx.appcompat.view.menu;

import android.widget.PopupWindow;

/* access modifiers changed from: package-private */
public class t implements PopupWindow.OnDismissListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ u f271a;

    t(u uVar) {
        this.f271a = uVar;
    }

    public void onDismiss() {
        this.f271a.d();
    }
}
