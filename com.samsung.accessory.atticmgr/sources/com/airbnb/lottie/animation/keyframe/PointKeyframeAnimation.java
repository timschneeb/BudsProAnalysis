package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public class PointKeyframeAnimation extends KeyframeAnimation<PointF> {
    private final PointF point = new PointF();

    public PointKeyframeAnimation(List<Keyframe<PointF>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public PointF getValue(Keyframe<PointF> keyframe, float f) {
        PointF pointF;
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        T t = keyframe.startValue;
        T t2 = keyframe.endValue;
        if (this.valueCallback != null && (pointF = (PointF) this.valueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), t, t2, f, getLinearCurrentKeyframeProgress(), getProgress())) != null) {
            return pointF;
        }
        this.point.set(((PointF) t).x + ((((PointF) t2).x - ((PointF) t).x) * f), ((PointF) t).y + (f * (((PointF) t2).y - ((PointF) t).y)));
        return this.point;
    }
}
