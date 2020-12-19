package androidx.appcompat.app;

import android.graphics.Rect;
import androidx.appcompat.widget.G;

class q implements G.a {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatDelegateImpl f195a;

    q(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f195a = appCompatDelegateImpl;
    }

    @Override // androidx.appcompat.widget.G.a
    public void a(Rect rect) {
        rect.top = this.f195a.i(rect.top);
    }
}
