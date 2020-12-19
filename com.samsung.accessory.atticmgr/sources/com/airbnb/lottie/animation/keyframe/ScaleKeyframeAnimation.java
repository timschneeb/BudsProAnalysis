package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

public class ScaleKeyframeAnimation extends KeyframeAnimation<ScaleXY> {
    private final ScaleXY scaleXY = new ScaleXY();

    public ScaleKeyframeAnimation(List<Keyframe<ScaleXY>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public ScaleXY getValue(Keyframe<ScaleXY> keyframe, float f) {
        ScaleXY scaleXY2;
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        T t = keyframe.startValue;
        T t2 = keyframe.endValue;
        if (this.valueCallback != null && (scaleXY2 = (ScaleXY) this.valueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), t, t2, f, getLinearCurrentKeyframeProgress(), getProgress())) != null) {
            return scaleXY2;
        }
        this.scaleXY.set(MiscUtils.lerp(t.getScaleX(), t2.getScaleX(), f), MiscUtils.lerp(t.getScaleY(), t2.getScaleY(), f));
        return this.scaleXY;
    }
}
