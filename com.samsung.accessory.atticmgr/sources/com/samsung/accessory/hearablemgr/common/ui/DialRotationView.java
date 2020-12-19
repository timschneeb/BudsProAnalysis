package com.samsung.accessory.hearablemgr.common.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.samsung.accessory.hearablemgr.Application;
import java.util.TreeMap;
import seccompat.android.util.Log;

public class DialRotationView extends View {
    private static final String TAG = (Application.TAG_ + DialRotationView.class.getSimpleName());
    private float DIAL_ENABLE_TOUCH_ANGLE = 30.0f;
    private Float[] ROTATION_ANGLE = {Float.valueOf(146.0f), Float.valueOf(-170.0f), Float.valueOf(-127.0f), Float.valueOf(-54.0f), Float.valueOf(-12.0f), Float.valueOf(35.0f)};
    private TreeMap<Float, Integer> mAngleMap = new TreeMap<>();
    private int mCurrentIndex;
    private int mDataSize;
    private float mDialAngle;
    private DialEventListener mDialEventListener;
    private AppCompatImageView mDialView;
    private float mEndAngle;
    private TreeMap<Integer, Float> mIndexMap = new TreeMap<>();
    private float mStartAngle;
    private int mViewHeight;
    private int mViewWidth;
    private RotationThread rotationThread = new RotationThread();
    private ValueAnimator valueAnimator;

    public interface DialEventListener {
        void onDialChanged(int i);
    }

    private float convertPositiveAngle(float f) {
        return f < 0.0f ? f + 360.0f : f;
    }

    public DialRotationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Float[] fArr = this.ROTATION_ANGLE;
        this.mDataSize = fArr.length;
        this.mStartAngle = convertPositiveAngle(fArr[0].floatValue());
        this.mEndAngle = convertPositiveAngle(this.ROTATION_ANGLE[this.mDataSize - 1].floatValue() - this.mStartAngle);
        setAngleFromStartAngle();
        setRTLConfiguration(false);
    }

    private void setAngleFromStartAngle() {
        for (int i = 0; i < this.mDataSize; i++) {
            Float[] fArr = this.ROTATION_ANGLE;
            fArr[i] = Float.valueOf(convertPositiveAngle(fArr[i].floatValue() - this.mStartAngle));
        }
    }

    public void setRTLConfiguration(boolean z) {
        this.mIndexMap.clear();
        this.mAngleMap.clear();
        for (int i = 0; i < this.mDataSize; i++) {
            float floatValue = this.ROTATION_ANGLE[i].floatValue();
            if (z) {
                this.mIndexMap.put(Integer.valueOf((this.mDataSize - 1) - i), Float.valueOf(floatValue));
                this.mAngleMap.put(Float.valueOf(floatValue), Integer.valueOf((this.mDataSize - 1) - i));
            } else {
                this.mIndexMap.put(Integer.valueOf(i), Float.valueOf(floatValue));
                this.mAngleMap.put(Float.valueOf(floatValue), Integer.valueOf(i));
            }
        }
        this.mDialAngle = this.mIndexMap.get(0).floatValue();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mViewWidth = i3 - i;
        this.mViewHeight = i4 - i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b0, code lost:
        if (r12 != 3) goto L_0x011d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r12) {
        /*
        // Method dump skipped, instructions count: 287
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.common.ui.DialRotationView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean isOutOfCircleRange(float f, float f2, float f3) {
        return Math.pow((double) f, 2.0d) + Math.pow((double) f2, 2.0d) > Math.pow((double) f3, 2.0d);
    }

    private boolean isInsideCircleRange(float f, float f2, float f3) {
        return Math.pow((double) f, 2.0d) + Math.pow((double) f2, 2.0d) < Math.pow((double) f3, 2.0d);
    }

    private boolean isInsideTouchAngle(float f) {
        float f2 = this.mDialAngle;
        float f3 = this.DIAL_ENABLE_TOUCH_ANGLE;
        return f < f2 + f3 && f > f2 - f3;
    }

    public void syncDialView(AppCompatImageView appCompatImageView) {
        this.mDialView = appCompatImageView;
        rotate(this.mStartAngle);
    }

    public void rotate(float f) {
        try {
            this.mDialView.setRotation(this.mStartAngle + f);
            this.mDialAngle = f;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void smoothRotate(int i) {
        smoothRotate(this.mIndexMap.get(Integer.valueOf(i)).floatValue());
        this.mCurrentIndex = i;
    }

    public void smoothRotate(float f) {
        float f2 = this.mStartAngle;
        this.valueAnimator = ValueAnimator.ofFloat(this.mDialAngle + f2, f2 + f);
        this.valueAnimator.setDuration(200L);
        this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.samsung.accessory.hearablemgr.common.ui.DialRotationView.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DialRotationView.this.mDialView.setRotation(((Float) valueAnimator.getAnimatedValue()).floatValue());
                DialRotationView.this.mDialAngle = ((Float) valueAnimator.getAnimatedValue()).floatValue() - DialRotationView.this.mStartAngle;
            }
        });
        this.valueAnimator.start();
    }

    public void setPosition(int i) {
        rotate(this.mIndexMap.get(Integer.valueOf(i)).floatValue());
        this.mCurrentIndex = i;
    }

    public void setDialEventListener(DialEventListener dialEventListener) {
        this.mDialEventListener = dialEventListener;
    }

    private class RotationThread {
        private Handler handler;
        private Runnable rotate;
        private boolean stop;
        private float targetAngle;

        private RotationThread() {
            this.stop = false;
            this.handler = new Handler();
            this.rotate = new Runnable() {
                /* class com.samsung.accessory.hearablemgr.common.ui.DialRotationView.RotationThread.AnonymousClass1 */
                final float tick = 20.0f;

                public void run() {
                    DialRotationView.this.rotate(DialRotationView.this.mDialAngle);
                    if (Math.abs(DialRotationView.this.mDialAngle - RotationThread.this.targetAngle) < 20.0f) {
                        DialRotationView.this.mDialAngle = RotationThread.this.targetAngle;
                    } else if (DialRotationView.this.mDialAngle - RotationThread.this.targetAngle < 0.0f) {
                        DialRotationView.this.mDialAngle += 20.0f;
                    } else {
                        DialRotationView.this.mDialAngle -= 20.0f;
                    }
                    if (!RotationThread.this.stop) {
                        RotationThread.this.handler.post(RotationThread.this.rotate);
                    }
                }
            };
        }

        /* access modifiers changed from: package-private */
        public void stop() {
            Log.d(DialRotationView.TAG, "RotationThread stop()");
            this.stop = true;
        }

        /* access modifiers changed from: package-private */
        public void start() {
            Log.d(DialRotationView.TAG, "RotationThread start()");
            this.stop = false;
            this.handler.post(this.rotate);
        }

        /* access modifiers changed from: package-private */
        public void setTargetAngle(float f) {
            this.targetAngle = f;
        }
    }
}
