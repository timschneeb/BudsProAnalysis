package com.samsung.accessory.hearablemgr.common.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.SeekBar;
import com.accessorydm.interfaces.XFOTAInterface;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.R;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import seccompat.android.util.Log;

public class ResizableSeekBar extends View {
    public static final String TAG = (Application.TAG_ + ResizableSeekBar.class.getSimpleName());
    private boolean isDim;
    private boolean isDragging;
    private boolean isRTL;
    private boolean isSlideAnimation;
    private boolean isUsedTickMark;
    private int mCurrentIndex;
    private DialEventListener mDialEventListener = new DialEventListener() {
        /* class com.samsung.accessory.hearablemgr.common.ui.ResizableSeekBar.AnonymousClass3 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.ResizableSeekBar.DialEventListener
        public void onDialChanged(int i) {
        }
    };
    private Paint mDotPaint = new Paint(1);
    private float mDotPosition;
    private float mDotRadius;
    private TreeMap<Integer, Float> mIndexMap = new TreeMap<>();
    private int mMax;
    private float mPaddingEnd;
    private float mPaddingStart;
    private TreeMap<Float, Integer> mPositionMap = new TreeMap<>();
    private int mPrevIndex;
    private float mScaledTouchSlop;
    private Paint mSeekBarBackgroundDisablePaint = new Paint(1);
    private Paint mSeekBarBackgroundEnablePaint = new Paint(1);
    private float mSeekBarWidth;
    private Drawable mThumb;
    private int mThumbAlpha = 255;
    private Drawable mThumbDim;
    private ArrayList<Drawable> mThumbDimList = new ArrayList<>();
    private ArrayList<Drawable> mThumbList = new ArrayList<>();
    private float mThumbSize;
    private ArrayList<Integer> mThumbSizeList = new ArrayList<>();
    private Drawable mTickMark;
    private ArrayList<Drawable> mTickMarkList = new ArrayList<>();
    private float mTickMarkSize;
    private ArrayList<Integer> mTickMarkSizeList = new ArrayList<>();
    private float mTouchDownX;
    private float mTouchDownY;
    private GestureDetector mVerticalScrollDetector = new GestureDetector(getContext(), new VerticalScrollDetector());
    private float mViewHeight;
    private float mViewWidth;

    public interface DialEventListener {
        void onDialChanged(int i);
    }

    public ResizableSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        String str = TAG;
        Log.d(str, "onCreate " + ResizableSeekBar.class.getSimpleName());
        setWillNotDraw(false);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ResizableSeekBar);
        try {
            setDefaultThumb(obtainStyledAttributes.getDrawable(3));
            setDefaultThumbDim(obtainStyledAttributes.getDrawable(4));
            setDefaultTickMark(obtainStyledAttributes.getDrawable(6));
            this.mMax = obtainStyledAttributes.getInteger(1, 2);
            this.mThumbSize = (float) obtainStyledAttributes.getInteger(5, getDefaultThumb().getIntrinsicWidth());
            this.mTickMarkSize = (float) obtainStyledAttributes.getInteger(7, getDefaultTickMark().getIntrinsicWidth());
            this.mDotRadius = (float) getDefaultThumb().getIntrinsicWidth();
            this.mSeekBarBackgroundEnablePaint.setStyle(Paint.Style.STROKE);
            this.mSeekBarBackgroundEnablePaint.setColor(obtainStyledAttributes.getColor(0, context.getResources().getColor(com.samsung.accessory.atticmgr.R.color.progress_tint_color)));
            this.mSeekBarBackgroundEnablePaint.setStrokeWidth(obtainStyledAttributes.getDimension(2, 20.0f));
            this.mSeekBarBackgroundDisablePaint.setStyle(Paint.Style.STROKE);
            this.mSeekBarBackgroundDisablePaint.setColor(obtainStyledAttributes.getColor(0, context.getResources().getColor(com.samsung.accessory.atticmgr.R.color.progress_tint_color)));
            this.mSeekBarBackgroundDisablePaint.setStrokeWidth(obtainStyledAttributes.getDimension(2, 20.0f));
            this.mDotPaint.setStyle(Paint.Style.FILL);
            this.mDotPaint.setColor(getResources().getColor(com.samsung.accessory.atticmgr.R.color.colorPrimary));
            this.mDotPaint.setAlpha(XFOTAInterface.XDL_STATE_POSTPONE_TO_UPDATE);
            this.mScaledTouchSlop = (float) ViewConfiguration.get(context).getScaledTouchSlop();
            this.isRTL = Util.isSystemLayoutDirectionRtl();
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mViewWidth = (float) (i3 - i);
        this.mViewHeight = (float) (i4 - i2);
        updateLayout();
    }

    private void updateLayout() {
        this.mPaddingStart = ((float) getPaddingStart()) + (getThumbSize(0) / 2.0f);
        this.mPaddingEnd = ((float) getPaddingEnd()) + (getThumbSize(this.mMax) / 2.0f);
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

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00aa, code lost:
        if (r4 != 3) goto L_0x014e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
        // Method dump skipped, instructions count: 336
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.common.ui.ResizableSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawLine(getPaddingStartForRTL(), this.mViewHeight / 2.0f, this.mViewWidth - getPaddingEndForRTL(), this.mViewHeight / 2.0f, isEnabled() ? this.mSeekBarBackgroundEnablePaint : this.mSeekBarBackgroundDisablePaint);
        if (this.isUsedTickMark) {
            drawTickMark(canvas);
        }
        if (this.isDragging) {
            drawThumbSlide(canvas);
            return;
        }
        drawThumb(canvas);
        drawPrevThumb(canvas);
    }

    public void setMax(int i) {
        if (i < this.mCurrentIndex) {
            String str = TAG;
            Log.w(str, "setMax() max : " + i + ", mCurrentIndex : " + this.mCurrentIndex + " >> current index have to lower than max value");
            this.mPrevIndex = i;
            this.mCurrentIndex = i;
        }
        this.mMax = i;
        updateLayout();
    }

    public void setProgress(int i) {
        if (i > this.mMax) {
            String str = TAG;
            Log.w(str, "setProgress() index : " + i + ", max : " + this.mMax + " >> current index have to lower than max value");
            i = this.mMax;
        }
        this.mPrevIndex = i;
        this.mCurrentIndex = i;
        invalidate();
    }

    public void setOnDialEventListener(DialEventListener dialEventListener) {
        this.mDialEventListener = dialEventListener;
    }

    public void setSeekBarEnableColor(int i) {
        this.mSeekBarBackgroundEnablePaint.setColor(i);
    }

    public void setSeekBarDisableColor(int i) {
        this.mSeekBarBackgroundDisablePaint.setColor(i);
    }

    public void setSeekbarWidth(float f) {
        this.mSeekBarBackgroundEnablePaint.setStrokeWidth(dpToPx(f));
        this.mSeekBarBackgroundDisablePaint.setStrokeWidth(dpToPx(f));
    }

    private void setDefaultTickMark(Drawable drawable) {
        if (drawable == null) {
            this.mTickMark = getResources().getDrawable(com.samsung.accessory.atticmgr.R.drawable.level1_booster, null);
            this.isUsedTickMark = false;
            return;
        }
        this.mTickMark = drawable;
        this.isUsedTickMark = true;
    }

    private Drawable getDefaultTickMark() {
        return this.mTickMark;
    }

    public void setTickMark(Drawable drawable) {
        setDefaultTickMark(drawable);
        this.mTickMarkList.clear();
        updateLayout();
    }

    public Drawable getTickMark(int i) {
        try {
            return this.mTickMarkList.get(i);
        } catch (Exception e) {
            Drawable defaultTickMark = getDefaultTickMark();
            e.printStackTrace();
            return defaultTickMark;
        }
    }

    public void setTickMarkList(ArrayList<Integer> arrayList) {
        ArrayList<Drawable> arrayList2 = new ArrayList<>();
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(getResources().getDrawable(it.next().intValue()));
        }
        this.mTickMarkList = arrayList2;
        updateLayout();
    }

    public void setTickMarkSize(int i) {
        this.mTickMarkSize = (float) i;
        updateLayout();
    }

    private float getTickMarkSize(int i) {
        try {
            return (float) (this.mTickMarkSizeList.isEmpty() ? this.mTickMarkList.get(i).getIntrinsicWidth() : this.mTickMarkSizeList.get(i).intValue());
        } catch (Exception e) {
            float f = this.mTickMarkSize;
            e.printStackTrace();
            return f;
        }
    }

    public void setTickMarkSizeList(ArrayList<Integer> arrayList) {
        this.mTickMarkSizeList = arrayList;
        updateLayout();
    }

    private void drawTickMark(Canvas canvas) {
        for (int i = 0; i <= this.mMax; i++) {
            Drawable tickMark = getTickMark(i);
            float tickMarkSize = getTickMarkSize(i) / 2.0f;
            tickMark.setBounds((int) (this.mIndexMap.get(Integer.valueOf(i)).floatValue() - tickMarkSize), (int) ((this.mViewHeight / 2.0f) - tickMarkSize), (int) (this.mIndexMap.get(Integer.valueOf(i)).floatValue() + tickMarkSize), (int) ((this.mViewHeight / 2.0f) + tickMarkSize));
            tickMark.draw(canvas);
        }
    }

    private void setDefaultThumb(Drawable drawable) {
        if (drawable == null) {
            drawable = getResources().getDrawable(com.samsung.accessory.atticmgr.R.drawable.level1_booster_selected, null);
        }
        this.mThumb = drawable;
    }

    private Drawable getDefaultThumb() {
        return this.mThumb;
    }

    public void setThumb(Drawable drawable) {
        setDefaultThumb(drawable);
        updateLayout();
    }

    public Drawable getThumb(int i) {
        try {
            return this.mThumbList.get(i);
        } catch (Exception e) {
            Drawable defaultThumb = getDefaultThumb();
            e.printStackTrace();
            return defaultThumb;
        }
    }

    public void setThumbList(ArrayList<Integer> arrayList) {
        ArrayList<Drawable> arrayList2 = new ArrayList<>();
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(getResources().getDrawable(it.next().intValue()));
        }
        this.mThumbList = arrayList2;
        updateLayout();
    }

    private void setDefaultThumbDim(Drawable drawable) {
        if (drawable == null) {
            drawable = getResources().getDrawable(com.samsung.accessory.atticmgr.R.drawable.level1_booster, null);
        }
        this.mThumbDim = drawable;
    }

    private Drawable getDefaultThumbDim() {
        return this.mThumbDim;
    }

    public void setThumbDim(Drawable drawable) {
        setDefaultThumbDim(drawable);
        updateLayout();
    }

    public Drawable getThumbDim(int i) {
        try {
            return this.mThumbDimList.get(i);
        } catch (Exception e) {
            Drawable defaultThumbDim = getDefaultThumbDim();
            e.printStackTrace();
            return defaultThumbDim;
        }
    }

    private float getThumbDimSize(int i) {
        try {
            return (float) this.mThumbDimList.get(i).getIntrinsicWidth();
        } catch (Exception e) {
            float f = this.mThumbSize;
            e.printStackTrace();
            return f;
        }
    }

    public void setThumbDimList(ArrayList<Integer> arrayList) {
        ArrayList<Drawable> arrayList2 = new ArrayList<>();
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(getResources().getDrawable(it.next().intValue()));
        }
        this.mThumbDimList = arrayList2;
        updateLayout();
    }

    public void setDim(boolean z) {
        this.isDim = z;
        updateLayout();
    }

    public void setThumbSize(int i) {
        this.mThumbSize = (float) i;
        updateLayout();
    }

    private float getThumbSize(int i) {
        try {
            return (float) (this.mThumbSizeList.isEmpty() ? this.mThumbList.get(i).getIntrinsicWidth() : this.mThumbSizeList.get(i).intValue());
        } catch (Exception e) {
            float f = this.mThumbSize;
            e.printStackTrace();
            return f;
        }
    }

    public void setThumbSizeList(ArrayList<Integer> arrayList) {
        this.mThumbSizeList = arrayList;
        updateLayout();
    }

    private void drawThumb(Canvas canvas) {
        Drawable thumbDim = this.isDim ? getThumbDim(this.mCurrentIndex) : getThumb(this.mCurrentIndex);
        float thumbDimSize = (this.isDim ? getThumbDimSize(this.mCurrentIndex) : getThumbSize(this.mCurrentIndex)) / 2.0f;
        thumbDim.setBounds((int) (this.mIndexMap.get(Integer.valueOf(this.mCurrentIndex)).floatValue() - thumbDimSize), (int) ((this.mViewHeight / 2.0f) - thumbDimSize), (int) (this.mIndexMap.get(Integer.valueOf(this.mCurrentIndex)).floatValue() + thumbDimSize), (int) ((this.mViewHeight / 2.0f) + thumbDimSize));
        thumbDim.setAlpha(this.mThumbAlpha);
        thumbDim.draw(canvas);
    }

    private void drawPrevThumb(Canvas canvas) {
        Drawable thumb = getThumb(this.mPrevIndex);
        float thumbSize = getThumbSize(this.mPrevIndex) / 2.0f;
        thumb.setBounds((int) (this.mIndexMap.get(Integer.valueOf(this.mPrevIndex)).floatValue() - thumbSize), (int) ((this.mViewHeight / 2.0f) - thumbSize), (int) (this.mIndexMap.get(Integer.valueOf(this.mPrevIndex)).floatValue() + thumbSize), (int) ((this.mViewHeight / 2.0f) + thumbSize));
        thumb.setAlpha(255 - this.mThumbAlpha);
        thumb.draw(canvas);
    }

    private void drawThumbSlide(Canvas canvas) {
        canvas.drawCircle(this.mDotPosition, this.mViewHeight / 2.0f, this.mDotRadius / 2.0f, this.mDotPaint);
    }

    private void startSlideAnimation(final float f, final float f2, float f3, final float f4) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f3, f4);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(Interpolators.SineOut60Interpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.samsung.accessory.hearablemgr.common.ui.ResizableSeekBar.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ResizableSeekBar.this.mDotPosition = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ResizableSeekBar resizableSeekBar = ResizableSeekBar.this;
                resizableSeekBar.mDotRadius = resizableSeekBar.getThumbSlideSize(f, f2, resizableSeekBar.mDotPosition);
                if (ResizableSeekBar.this.mDotPosition == f4) {
                    ResizableSeekBar.this.isDragging = false;
                    ResizableSeekBar.this.isSlideAnimation = false;
                }
                ResizableSeekBar.this.invalidate();
            }
        });
        this.isSlideAnimation = true;
        ofFloat.start();
    }

    private void startClickAnimation() {
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
        ofInt.setDuration(200L);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.samsung.accessory.hearablemgr.common.ui.ResizableSeekBar.AnonymousClass2 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ResizableSeekBar.this.mThumbAlpha = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                ResizableSeekBar.this.invalidate();
            }
        });
        ofInt.start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getThumbSlideSize(float f, float f2, float f3) {
        float f4 = f2 - f;
        float thumbSize = getThumbSize(this.mPositionMap.get(Float.valueOf(f)).intValue());
        float thumbSize2 = getThumbSize(this.mPositionMap.get(Float.valueOf(f2)).intValue());
        if (f4 == 0.0f) {
            return getThumbSize(this.mPositionMap.get(Float.valueOf(f)).intValue());
        }
        return thumbSize + ((thumbSize2 - thumbSize) * ((f3 - f) / f4));
    }

    private class VerticalScrollDetector extends GestureDetector.SimpleOnGestureListener {
        private VerticalScrollDetector() {
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return Math.abs(f) > Math.abs(f2) && ResizableSeekBar.this.isSloped(f, f2);
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
