package com.samsung.accessory.hearablemgr.module.noisecontrols;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.SeekBar;
import androidx.core.content.ContextCompat;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.Interpolators;
import java.util.ArrayList;
import java.util.TreeMap;
import seccompat.android.util.Log;

public class NoiseControlSeekBar extends View {
    public static final String TAG = (Application.TAG_ + NoiseControlSeekBar.class.getSimpleName());
    private boolean isAnimatingMoveLine = false;
    private boolean isArrived = false;
    private boolean isCenterArrived = false;
    private boolean isCenterEscaped = false;
    private boolean isDragging;
    private boolean isEnabled;
    private boolean[] isEnabledPosition = {true, true, true};
    private boolean isRTL;
    private int mCurrentIndex;
    private TreeMap<Integer, Float> mIndexMap = new TreeMap<>();
    private int mMax;
    private float mMoveLineEndX;
    private float mMoveLineStartX;
    private Paint mMovedLinePaint = new Paint(1);
    private NoiseControlChangeListener mNoiseControlChangeListener = $$Lambda$NoiseControlSeekBar$AVx5hUgMscgqxJIVRfEsQRo6r4.INSTANCE;
    private NoiseControlMoveLineListener mNoiseControlMoveLineListener = new NoiseControlMoveLineListener() {
        /* class com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.AnonymousClass1 */

        @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlMoveLineListener
        public void onArrivedLineToCenter(int i, int i2) {
        }

        @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlMoveLineListener
        public void onArrivedLineToTarget(int i, int i2) {
        }

        @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlMoveLineListener
        public void onEscapedLineFromCenter(int i, int i2) {
        }

        @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlMoveLineListener
        public void onMoveStart(int i, int i2) {
        }
    };
    private OnClickDisablePositionListener mOnClickDisablePositionListener = $$Lambda$NoiseControlSeekBar$oSUHwP5RhojgcssuV9AIVSCH2pA.INSTANCE;
    private float mPaddingEnd;
    private float mPaddingStart;
    private TreeMap<Float, Integer> mPositionMap = new TreeMap<>();
    private float mScaledTouchSlop;
    private float mSeekBarWidth;
    private Drawable mTickMark;
    private float mTouchDownX;
    private float mTouchDownY;
    private GestureDetector mVerticalScrollDetector = new GestureDetector(getContext(), new VerticalScrollDetector());
    private float mViewHeight;
    private float mViewWidth;
    private ValueAnimator valueAnimator;

    public interface NoiseControlChangeListener {
        void onNoiseControlChanged(int i);
    }

    public interface NoiseControlMoveLineListener {
        void onArrivedLineToCenter(int i, int i2);

        void onArrivedLineToTarget(int i, int i2);

        void onEscapedLineFromCenter(int i, int i2);

        void onMoveStart(int i, int i2);
    }

    public interface OnClickDisablePositionListener {
        void onClick(int i);
    }

    static /* synthetic */ void lambda$new$1(int i) {
    }

    static /* synthetic */ void lambda$new$2(int i) {
    }

    public NoiseControlSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        String str = TAG;
        Log.d(str, "onCreate " + NoiseControlSeekBar.class.getSimpleName());
        setWillNotDraw(false);
        this.mTickMark = ContextCompat.getDrawable(context, R.drawable.noise_controls_tick_mark);
        this.mMax = 2;
        this.mMovedLinePaint.setStyle(Paint.Style.STROKE);
        this.mMovedLinePaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        this.mMovedLinePaint.setStrokeWidth(dpToPx(6.0f));
        this.mMovedLinePaint.setStrokeCap(Paint.Cap.ROUND);
        this.mScaledTouchSlop = (float) ViewConfiguration.get(context).getScaledTouchSlop();
        this.isRTL = false;
        this.isEnabled = true;
        updateLayout();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mViewWidth = (float) (i3 - i);
        this.mViewHeight = (float) (i4 - i2);
        updateLayout();
    }

    private void updateLayout() {
        this.mPaddingStart = ((float) getPaddingStart()) + getMaxCircleHalfSize();
        this.mPaddingEnd = ((float) getPaddingEnd()) + getMaxCircleHalfSize();
        this.mSeekBarWidth = (this.mViewWidth - this.mPaddingStart) - this.mPaddingEnd;
        clearPositionList();
    }

    public void clearPositionList() {
        ArrayList<Float> arrayList = new ArrayList<>();
        int i = 0;
        while (true) {
            int i2 = this.mMax;
            if (i <= i2) {
                arrayList.add(Float.valueOf(((((float) i) * this.mSeekBarWidth) / ((float) i2)) + getPaddingStartForRTL()));
                i++;
            } else {
                setPositionList(arrayList);
                return;
            }
        }
    }

    public void setPositionList(ArrayList<Float> arrayList) {
        this.mMax = arrayList.size() - 1;
        this.mIndexMap.clear();
        this.mPositionMap.clear();
        int i = 0;
        while (true) {
            int i2 = this.mMax;
            if (i <= i2) {
                if (this.isRTL) {
                    this.mIndexMap.put(Integer.valueOf(i2 - i), arrayList.get(i));
                    this.mPositionMap.put(arrayList.get(i), Integer.valueOf(this.mMax - i));
                } else {
                    this.mIndexMap.put(Integer.valueOf(i), arrayList.get(i));
                    this.mPositionMap.put(arrayList.get(i), Integer.valueOf(i));
                }
                i++;
            } else {
                invalidate();
                return;
            }
        }
    }

    public void setEnabledSeekBar(boolean z) {
        this.isEnabled = z;
    }

    public boolean isEnabledSeekBar() {
        return this.isEnabled;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Float f;
        Float f2;
        if (isEnabled() && this.isEnabled) {
            if (!this.isDragging) {
                this.isDragging = this.mVerticalScrollDetector.onTouchEvent(motionEvent);
            }
            float x = motionEvent.getX();
            if (this.mPositionMap.floorKey(Float.valueOf(x)) == null) {
                f = this.mIndexMap.get(Integer.valueOf(!this.isRTL ? 0 : this.mMax));
            } else {
                f = this.mPositionMap.floorKey(Float.valueOf(x));
            }
            Float f3 = f;
            if (this.mPositionMap.higherKey(Float.valueOf(x)) == null) {
                f2 = this.mIndexMap.get(Integer.valueOf(!this.isRTL ? this.mMax : 0));
            } else {
                f2 = this.mPositionMap.higherKey(Float.valueOf(x));
            }
            Float f4 = f2;
            if (!(f3 == null || f4 == null)) {
                int intValue = (x - f3.floatValue() <= f4.floatValue() - x ? this.mPositionMap.get(f3) : this.mPositionMap.get(f4)).intValue();
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    this.mTouchDownX = motionEvent.getX();
                    this.mTouchDownY = motionEvent.getY();
                } else if (action == 1 || action == 3) {
                    if (isClicked(this.mTouchDownX, motionEvent.getX(), this.mTouchDownY, motionEvent.getY())) {
                        Log.d(TAG, "isClicked!");
                        int i = this.mCurrentIndex;
                        if (intValue != i) {
                            if (!this.isEnabledPosition[intValue]) {
                                this.mOnClickDisablePositionListener.onClick(intValue);
                                return false;
                            }
                            startMoveLineAnimation(i, intValue);
                            this.mCurrentIndex = intValue;
                            this.mNoiseControlChangeListener.onNoiseControlChanged(this.mCurrentIndex);
                            invalidate();
                        }
                    }
                    this.isDragging = false;
                }
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawMoveLine(canvas);
    }

    public void setProgress(int i) {
        if (i > this.mMax) {
            String str = TAG;
            Log.w(str, "setProgress() index : " + i + ", max : " + this.mMax + " >> current index have to lower than max value");
            i = this.mMax;
        }
        this.mCurrentIndex = i;
        invalidate();
    }

    public void setProgressWithAnimation(int i) {
        if (i > this.mMax) {
            String str = TAG;
            Log.w(str, "setProgress() index : " + i + ", max : " + this.mMax + " >> current index have to lower than max value");
            i = this.mMax;
        }
        int i2 = this.mCurrentIndex;
        if (i2 != i) {
            startMoveLineAnimation(i2, i);
            this.mCurrentIndex = i;
            invalidate();
        }
    }

    public int getProgress() {
        return this.mCurrentIndex;
    }

    public void setEnabledPosition(int i, boolean z) {
        this.isEnabledPosition[i] = z;
        invalidate();
    }

    public void setOnNoiseControlMoveLineListener(NoiseControlMoveLineListener noiseControlMoveLineListener) {
        this.mNoiseControlMoveLineListener = noiseControlMoveLineListener;
    }

    public void setOnNoiseControlChangeListener(NoiseControlChangeListener noiseControlChangeListener) {
        this.mNoiseControlChangeListener = noiseControlChangeListener;
    }

    public void setOnClickDisablePositionListener(OnClickDisablePositionListener onClickDisablePositionListener) {
        this.mOnClickDisablePositionListener = onClickDisablePositionListener;
    }

    private float getTickMarkSize() {
        return (float) this.mTickMark.getIntrinsicWidth();
    }

    private float getMaxCircleHalfSize() {
        return getTickMarkSize() / 2.0f;
    }

    private void drawMoveLine(Canvas canvas) {
        if (this.isAnimatingMoveLine) {
            float f = this.mMoveLineStartX;
            float f2 = this.mViewHeight;
            canvas.drawLine(f, f2 / 2.0f, this.mMoveLineEndX, f2 / 2.0f, this.mMovedLinePaint);
        }
    }

    private void startMoveLineAnimation(int i, int i2) {
        float f;
        if (i < i2) {
            try {
                f = getMaxCircleHalfSize();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                return;
            }
        } else {
            f = -getMaxCircleHalfSize();
        }
        float floatValue = this.mIndexMap.get(Integer.valueOf(i)).floatValue() + f;
        float floatValue2 = this.mIndexMap.get(Integer.valueOf(i2)).floatValue() + (-f);
        float abs = Math.abs(floatValue2 - floatValue);
        float f2 = (2.0f * abs) / 3.0f;
        this.isArrived = false;
        this.isCenterArrived = false;
        this.isCenterEscaped = false;
        this.isAnimatingMoveLine = true;
        if (this.valueAnimator != null) {
            this.valueAnimator.cancel();
        }
        this.valueAnimator = ValueAnimator.ofFloat(0.0f, abs + f2);
        this.valueAnimator.setDuration((long) 400);
        this.valueAnimator.setInterpolator(Interpolators.SineInOut33Interpolator());
        this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(floatValue, floatValue2, abs, f2, i, i2) {
            /* class com.samsung.accessory.hearablemgr.module.noisecontrols.$$Lambda$NoiseControlSeekBar$aTaZv9DTxxBhmMN3N0qHC6fkESk */
            private final /* synthetic */ float f$1;
            private final /* synthetic */ float f$2;
            private final /* synthetic */ float f$3;
            private final /* synthetic */ float f$4;
            private final /* synthetic */ int f$5;
            private final /* synthetic */ int f$6;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
                this.f$5 = r6;
                this.f$6 = r7;
            }

            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NoiseControlSeekBar.this.lambda$startMoveLineAnimation$0$NoiseControlSeekBar(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, valueAnimator);
            }
        });
        this.valueAnimator.start();
        this.mNoiseControlMoveLineListener.onMoveStart(i, i2);
    }

    public /* synthetic */ void lambda$startMoveLineAnimation$0$NoiseControlSeekBar(float f, float f2, float f3, float f4, int i, int i2, ValueAnimator valueAnimator2) {
        float f5;
        float f6;
        float f7;
        float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
        if (f < f2) {
            f6 = Math.min(f + floatValue, f2);
            f5 = floatValue < f3 ? Math.min(floatValue, f4) : f4 - (floatValue - f3);
            f7 = f6 - f5;
        } else {
            f7 = Math.max(f - floatValue, f2);
            f5 = floatValue < f3 ? Math.min(f - f7, f4) : f4 - (floatValue - f3);
            f6 = f7 + f5;
        }
        this.mMoveLineStartX = f7;
        this.mMoveLineEndX = f6;
        if (floatValue == f4 + f3) {
            this.isAnimatingMoveLine = false;
        }
        if (floatValue > f3 && !this.isArrived) {
            this.mNoiseControlMoveLineListener.onArrivedLineToTarget(i, i2);
            this.isArrived = true;
        }
        float f8 = f3 / 2.0f;
        if (floatValue > f8 - getMaxCircleHalfSize() && !this.isCenterArrived) {
            this.mNoiseControlMoveLineListener.onArrivedLineToCenter(i, i2);
            this.isCenterArrived = true;
        }
        if (floatValue - f5 > f8 + getMaxCircleHalfSize() && !this.isCenterEscaped && this.isCenterArrived) {
            this.mNoiseControlMoveLineListener.onEscapedLineFromCenter(i, i2);
            this.isCenterEscaped = true;
        }
        invalidate();
    }

    private class VerticalScrollDetector extends GestureDetector.SimpleOnGestureListener {
        private VerticalScrollDetector() {
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return Math.abs(f) > Math.abs(f2) && NoiseControlSeekBar.this.isSloped(f, f2);
        }
    }

    private boolean isClicked(float f, float f2, float f3, float f4) {
        return !isSloped(f - f2, f3 - f4);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isSloped(float f, float f2) {
        return Math.abs(f) > this.mScaledTouchSlop || Math.abs(f2) > this.mScaledTouchSlop;
    }

    private float getPaddingStartForRTL() {
        return this.isRTL ? this.mPaddingEnd : this.mPaddingStart;
    }

    private float getPaddingEndForRTL() {
        return this.isRTL ? this.mPaddingStart : this.mPaddingEnd;
    }

    private float dpToPx(float f) {
        return (f * ((float) getResources().getConfiguration().densityDpi)) / 160.0f;
    }

    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }
}
