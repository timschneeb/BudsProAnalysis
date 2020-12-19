package b.e.g;

import android.animation.ValueAnimator;
import android.view.View;

/* access modifiers changed from: package-private */
public class y implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ C f1439a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f1440b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ z f1441c;

    y(z zVar, C c2, View view) {
        this.f1441c = zVar;
        this.f1439a = c2;
        this.f1440b = view;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f1439a.a(this.f1440b);
    }
}
