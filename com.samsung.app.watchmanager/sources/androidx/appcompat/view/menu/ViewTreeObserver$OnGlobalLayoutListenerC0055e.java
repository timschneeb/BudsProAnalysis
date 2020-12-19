package androidx.appcompat.view.menu;

import android.view.View;
import android.view.ViewTreeObserver;
import androidx.appcompat.view.menu.i;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.view.menu.e  reason: case insensitive filesystem */
public class ViewTreeObserver$OnGlobalLayoutListenerC0055e implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ i f230a;

    ViewTreeObserver$OnGlobalLayoutListenerC0055e(i iVar) {
        this.f230a = iVar;
    }

    public void onGlobalLayout() {
        if (this.f230a.b() && this.f230a.j.size() > 0 && !this.f230a.j.get(0).f240a.k()) {
            View view = this.f230a.q;
            if (view == null || !view.isShown()) {
                this.f230a.dismiss();
                return;
            }
            for (i.a aVar : this.f230a.j) {
                aVar.f240a.c();
            }
        }
    }
}
