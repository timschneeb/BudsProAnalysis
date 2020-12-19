package androidx.swiperefreshlayout.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

class l extends Animation {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SwipeRefreshLayout f1171a;

    l(SwipeRefreshLayout swipeRefreshLayout) {
        this.f1171a = swipeRefreshLayout;
    }

    public void applyTransformation(float f, Transformation transformation) {
        SwipeRefreshLayout swipeRefreshLayout = this.f1171a;
        float f2 = swipeRefreshLayout.z;
        swipeRefreshLayout.setAnimationProgress(f2 + ((-f2) * f));
        this.f1171a.a(f);
    }
}
