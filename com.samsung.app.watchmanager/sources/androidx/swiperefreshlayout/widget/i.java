package androidx.swiperefreshlayout.widget;

import android.view.animation.Animation;

class i implements Animation.AnimationListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SwipeRefreshLayout f1168a;

    i(SwipeRefreshLayout swipeRefreshLayout) {
        this.f1168a = swipeRefreshLayout;
    }

    public void onAnimationEnd(Animation animation) {
        SwipeRefreshLayout swipeRefreshLayout = this.f1168a;
        if (!swipeRefreshLayout.t) {
            swipeRefreshLayout.a((Animation.AnimationListener) null);
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
