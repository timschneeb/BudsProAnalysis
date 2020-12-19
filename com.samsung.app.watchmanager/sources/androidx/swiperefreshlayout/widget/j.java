package androidx.swiperefreshlayout.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

class j extends Animation {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SwipeRefreshLayout f1169a;

    j(SwipeRefreshLayout swipeRefreshLayout) {
        this.f1169a = swipeRefreshLayout;
    }

    public void applyTransformation(float f, Transformation transformation) {
        SwipeRefreshLayout swipeRefreshLayout = this.f1169a;
        int abs = !swipeRefreshLayout.L ? swipeRefreshLayout.B - Math.abs(swipeRefreshLayout.A) : swipeRefreshLayout.B;
        SwipeRefreshLayout swipeRefreshLayout2 = this.f1169a;
        int i = swipeRefreshLayout2.y;
        this.f1169a.setTargetOffsetTopAndBottom((i + ((int) (((float) (abs - i)) * f))) - swipeRefreshLayout2.w.getTop());
        this.f1169a.D.a(1.0f - f);
    }
}
