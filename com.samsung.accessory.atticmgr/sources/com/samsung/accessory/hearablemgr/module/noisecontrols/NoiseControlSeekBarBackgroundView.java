package com.samsung.accessory.hearablemgr.module.noisecontrols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.samsung.accessory.atticmgr.R;
import java.util.ArrayList;
import java.util.TreeMap;

public class NoiseControlSeekBarBackgroundView extends View {
    private TreeMap<Integer, Float> mIndexMap = new TreeMap<>();
    private int mMax;
    private float mPaddingEnd;
    private float mPaddingStart;
    private TreeMap<Float, Integer> mPositionMap = new TreeMap<>();
    private Paint mSeekBarBackgroundDisablePaint = new Paint(1);
    private Paint mSeekBarBackgroundEnablePaint = new Paint(1);
    private float mSeekBarWidth;
    private Drawable mTickMark;
    private float mViewHeight;
    private float mViewWidth;

    public NoiseControlSeekBarBackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
        this.mTickMark = ContextCompat.getDrawable(context, R.drawable.noise_controls_tick_mark);
        this.mMax = 2;
        this.mSeekBarBackgroundEnablePaint.setStyle(Paint.Style.STROKE);
        this.mSeekBarBackgroundEnablePaint.setColor(ContextCompat.getColor(context, R.color.color_black));
        this.mSeekBarBackgroundEnablePaint.setStrokeWidth(dpToPx(6.0f));
        this.mSeekBarBackgroundDisablePaint.setStyle(Paint.Style.STROKE);
        this.mSeekBarBackgroundDisablePaint.setColor(ContextCompat.getColor(context, R.color.color_black));
        this.mSeekBarBackgroundDisablePaint.setStrokeWidth(dpToPx(6.0f));
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
        this.mPaddingStart = ((float) getPaddingStart()) + (getTickMarkSize() / 2.0f);
        this.mPaddingEnd = ((float) getPaddingEnd()) + (getTickMarkSize() / 2.0f);
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
        for (int i = 0; i <= this.mMax; i++) {
            this.mIndexMap.put(Integer.valueOf(i), arrayList.get(i));
            this.mPositionMap.put(arrayList.get(i), Integer.valueOf(i));
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawLine(getPaddingStartForRTL(), this.mViewHeight / 2.0f, this.mViewWidth - getPaddingEndForRTL(), this.mViewHeight / 2.0f, isEnabled() ? this.mSeekBarBackgroundEnablePaint : this.mSeekBarBackgroundDisablePaint);
        drawTickMark(canvas);
    }

    public Drawable getTickMark() {
        return this.mTickMark;
    }

    private void drawTickMark(Canvas canvas) {
        Drawable tickMark = getTickMark();
        float maxCircleHalfSize = getMaxCircleHalfSize();
        for (int i = 0; i <= this.mMax; i++) {
            tickMark.setBounds((int) (this.mIndexMap.get(Integer.valueOf(i)).floatValue() - maxCircleHalfSize), (int) ((this.mViewHeight / 2.0f) - maxCircleHalfSize), (int) (this.mIndexMap.get(Integer.valueOf(i)).floatValue() + maxCircleHalfSize), (int) ((this.mViewHeight / 2.0f) + maxCircleHalfSize));
            tickMark.draw(canvas);
        }
    }

    private float getTickMarkSize() {
        return (float) this.mTickMark.getIntrinsicWidth();
    }

    private float getMaxCircleHalfSize() {
        return getTickMarkSize() / 2.0f;
    }

    private float getPaddingStartForRTL() {
        return this.mPaddingStart;
    }

    private float getPaddingEndForRTL() {
        return this.mPaddingEnd;
    }

    private float dpToPx(float f) {
        return (f * ((float) getResources().getConfiguration().densityDpi)) / 160.0f;
    }
}
