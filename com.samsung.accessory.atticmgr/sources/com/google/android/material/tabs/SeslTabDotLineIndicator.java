package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class SeslTabDotLineIndicator extends SeslAbsIndicatorView {
    private static final float CIRCLE_INTERVAL = 2.5f;
    private static final float DIAMETER_SIZE = 2.5f;
    private static final int SCALE_DIFF = 5;
    private final int mDiameter;
    private final int mInterval;
    private Paint mPaint;
    private float mScaleFrom;
    private final float mScaleFromDiff;
    private int mWidth;

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setClick() {
        super.setClick();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setHide() {
        super.setHide();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setPressed() {
        super.setPressed();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setReleased() {
        super.setReleased();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setSelectedIndicatorColor(int i) {
        super.setSelectedIndicatorColor(i);
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setShow() {
        super.setShow();
    }

    public SeslTabDotLineIndicator(Context context) {
        this(context, null);
    }

    public SeslTabDotLineIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslTabDotLineIndicator(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslTabDotLineIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDiameter = (int) TypedValue.applyDimension(1, 2.5f, displayMetrics);
        this.mInterval = (int) TypedValue.applyDimension(1, 2.5f, displayMetrics);
        this.mScaleFromDiff = TypedValue.applyDimension(1, 5.0f, displayMetrics);
        this.mPaint = new Paint();
        this.mPaint.setFlags(1);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void onHide() {
        setAlpha(0.0f);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void onShow() {
        startReleaseEffect();
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void startPressEffect() {
        setAlpha(1.0f);
        invalidate();
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void startReleaseEffect() {
        setAlpha(1.0f);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void startPressAndReleaseEffect() {
        setAlpha(1.0f);
        invalidate();
    }

    private void updateDotLineScaleFrom() {
        if (this.mWidth != getWidth() || this.mWidth == 0) {
            this.mWidth = getWidth();
            int i = this.mWidth;
            if (i <= 0) {
                this.mScaleFrom = 0.9f;
            } else {
                this.mScaleFrom = (((float) i) - this.mScaleFromDiff) / ((float) i);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void onSetSelectedIndicatorColor(int i) {
        this.mPaint.setColor(i);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updateDotLineScaleFrom();
        if ((isPressed() || isSelected()) && (getBackground() instanceof ColorDrawable)) {
            int width = (getWidth() - getPaddingStart()) - getPaddingEnd();
            float height = ((float) getHeight()) / 2.0f;
            int i = this.mDiameter;
            float f = ((float) i) / 2.0f;
            canvas.drawRoundRect(0.0f, height - f, (float) width, height + f, (float) i, (float) i, this.mPaint);
        }
    }
}
