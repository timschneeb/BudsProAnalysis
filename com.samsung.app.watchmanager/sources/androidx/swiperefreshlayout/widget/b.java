package androidx.swiperefreshlayout.widget;

import android.animation.ValueAnimator;
import androidx.swiperefreshlayout.widget.d;

/* access modifiers changed from: package-private */
public class b implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ d.a f1150a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ d f1151b;

    b(d dVar, d.a aVar) {
        this.f1151b = dVar;
        this.f1150a = aVar;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.f1151b.a(floatValue, this.f1150a);
        this.f1151b.a(floatValue, this.f1150a, false);
        this.f1151b.invalidateSelf();
    }
}
