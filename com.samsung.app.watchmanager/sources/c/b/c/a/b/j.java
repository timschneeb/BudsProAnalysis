package c.b.c.a.b;

import android.animation.ValueAnimator;

/* access modifiers changed from: package-private */
public class j implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ l f1853a;

    j(l lVar) {
        this.f1853a = lVar;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        l lVar = this.f1853a;
        this.f1853a.a(lVar.f1857c.a(floatValue, 0.0f, 1.0f, 1.0f, lVar.f1856b));
    }
}
