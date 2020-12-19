package b.g.a;

import android.view.animation.Interpolator;

class a implements Interpolator {
    a() {
    }

    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }
}
