package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

public class RoundedCornerLayout extends FrameLayout {
    private static final float CORNER_SIZE = 26.0f;
    private Path mPath = new Path();
    private RectF mRectF = new RectF();

    public RoundedCornerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RoundedCornerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public RoundedCornerLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        float applyDimension = TypedValue.applyDimension(1, CORNER_SIZE, getContext().getResources().getDisplayMetrics());
        this.mPath.reset();
        this.mRectF.set(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
        this.mPath.addRoundRect(this.mRectF, applyDimension, applyDimension, Path.Direction.CW);
        canvas.clipPath(this.mPath);
        super.dispatchDraw(canvas);
    }
}
