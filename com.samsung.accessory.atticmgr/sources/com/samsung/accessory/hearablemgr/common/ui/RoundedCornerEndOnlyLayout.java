package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

public class RoundedCornerEndOnlyLayout extends FrameLayout {
    private static final float CORNER_SIZE = 26.0f;
    private Path mPath;
    private float[] mRadii;
    private RectF mRectF;

    public RoundedCornerEndOnlyLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRectF = new RectF();
        this.mPath = new Path();
        this.mRadii = new float[8];
    }

    public RoundedCornerEndOnlyLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRectF = new RectF();
        this.mPath = new Path();
        this.mRadii = new float[8];
    }

    public RoundedCornerEndOnlyLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRectF = new RectF();
        this.mPath = new Path();
        this.mRadii = new float[8];
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        float applyDimension = TypedValue.applyDimension(1, CORNER_SIZE, getContext().getResources().getDisplayMetrics());
        this.mPath.reset();
        this.mRectF.set(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
        if (UiUtil.isLayoutRtl(this)) {
            float[] fArr = this.mRadii;
            fArr[0] = applyDimension;
            fArr[1] = applyDimension;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = applyDimension;
            fArr[7] = applyDimension;
        } else {
            float[] fArr2 = this.mRadii;
            fArr2[0] = 0.0f;
            fArr2[1] = 0.0f;
            fArr2[2] = applyDimension;
            fArr2[3] = applyDimension;
            fArr2[4] = applyDimension;
            fArr2[5] = applyDimension;
            fArr2[6] = 0.0f;
            fArr2[7] = 0.0f;
        }
        this.mPath.addRoundRect(this.mRectF, this.mRadii, Path.Direction.CW);
        canvas.clipPath(this.mPath);
        super.dispatchDraw(canvas);
    }
}
