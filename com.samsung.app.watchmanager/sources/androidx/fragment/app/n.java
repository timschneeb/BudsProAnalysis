package androidx.fragment.app;

import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.fragment.app.r;

/* access modifiers changed from: package-private */
public class n extends r.b {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ViewGroup f773b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Fragment f774c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ r f775d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    n(r rVar, Animation.AnimationListener animationListener, ViewGroup viewGroup, Fragment fragment) {
        super(animationListener);
        this.f775d = rVar;
        this.f773b = viewGroup;
        this.f774c = fragment;
    }

    @Override // androidx.fragment.app.r.b
    public void onAnimationEnd(Animation animation) {
        super.onAnimationEnd(animation);
        this.f773b.post(new RunnableC0093m(this));
    }
}
