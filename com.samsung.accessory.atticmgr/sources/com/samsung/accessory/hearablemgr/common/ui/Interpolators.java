package com.samsung.accessory.hearablemgr.common.ui;

import android.view.animation.Interpolator;
import androidx.core.view.animation.PathInterpolatorCompat;

public class Interpolators {
    public static Interpolator SineOut60Interpolator() {
        return PathInterpolatorCompat.create(0.17f, 0.17f, 0.4f, 1.0f);
    }

    public static Interpolator SineInOut80Interpolator() {
        return PathInterpolatorCompat.create(0.33f, 0.0f, 0.2f, 1.0f);
    }

    public static Interpolator SineInOut33Interpolator() {
        return PathInterpolatorCompat.create(0.33f, 0.0f, 0.67f, 1.0f);
    }
}
