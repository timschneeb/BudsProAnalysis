package androidx.recyclerview.widget;

import android.view.animation.Interpolator;

class A implements Interpolator {
    A() {
    }

    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }
}
