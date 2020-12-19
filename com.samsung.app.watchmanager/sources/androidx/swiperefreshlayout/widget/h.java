package androidx.swiperefreshlayout.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

class h extends Animation {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ int f1165a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f1166b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ SwipeRefreshLayout f1167c;

    h(SwipeRefreshLayout swipeRefreshLayout, int i, int i2) {
        this.f1167c = swipeRefreshLayout;
        this.f1165a = i;
        this.f1166b = i2;
    }

    public void applyTransformation(float f, Transformation transformation) {
        d dVar = this.f1167c.D;
        int i = this.f1165a;
        dVar.setAlpha((int) (((float) i) + (((float) (this.f1166b - i)) * f)));
    }
}
