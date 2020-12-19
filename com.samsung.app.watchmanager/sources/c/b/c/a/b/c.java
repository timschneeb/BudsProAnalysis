package c.b.c.a.b;

import android.animation.ValueAnimator;

/* access modifiers changed from: package-private */
public class c implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ e f1840a;

    c(e eVar) {
        this.f1840a = eVar;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        e eVar = this.f1840a;
        this.f1840a.a(eVar.i.a(floatValue, 0.0f, 1.0f, 1.0f, eVar.h));
    }
}
