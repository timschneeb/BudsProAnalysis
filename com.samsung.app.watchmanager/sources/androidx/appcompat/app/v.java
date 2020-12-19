package androidx.appcompat.app;

import android.view.View;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatDelegateImpl;
import b.e.g.A;
import b.e.g.B;
import b.e.g.t;

class v extends B {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatDelegateImpl.b f200a;

    v(AppCompatDelegateImpl.b bVar) {
        this.f200a = bVar;
    }

    @Override // b.e.g.A
    public void b(View view) {
        AppCompatDelegateImpl.this.q.setVisibility(8);
        AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
        PopupWindow popupWindow = appCompatDelegateImpl.r;
        if (popupWindow != null) {
            popupWindow.dismiss();
        } else if (appCompatDelegateImpl.q.getParent() instanceof View) {
            t.z((View) AppCompatDelegateImpl.this.q.getParent());
        }
        AppCompatDelegateImpl.this.q.removeAllViews();
        AppCompatDelegateImpl.this.t.a((A) null);
        AppCompatDelegateImpl.this.t = null;
    }
}
