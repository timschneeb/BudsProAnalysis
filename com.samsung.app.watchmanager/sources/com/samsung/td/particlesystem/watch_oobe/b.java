package com.samsung.td.particlesystem.watch_oobe;

import android.animation.ValueAnimator;

/* access modifiers changed from: package-private */
public class b implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_Base f1930a;

    b(ParticleView_Watch_OOBE_Base particleView_Watch_OOBE_Base) {
        this.f1930a = particleView_Watch_OOBE_Base;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f1930a.invalidate();
    }
}
