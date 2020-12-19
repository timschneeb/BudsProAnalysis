package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* access modifiers changed from: package-private */
public abstract class KeyframeAnimation<T> extends BaseKeyframeAnimation<T, T> {
    KeyframeAnimation(List<? extends Keyframe<T>> list) {
        super(list);
    }
}
