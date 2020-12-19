package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.samsung.accessory.hearablemgr.R;
import java.util.Locale;
import seccompat.android.util.Log;

public class DottedLineView extends View {
    private static final int DEFAULT_DOT_COLOR = -16777216;
    private static final float DEFAULT_DOT_SIZE = 1.5f;
    private static final float DEFAULT_DOT_SPACE = 3.0f;
    private static final String TAG = "Attic_DottedLineView";
    private int mColor;
    private Paint mPaint = new Paint(1);
    private float mSize;
    private float mSpace;

    public DottedLineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DottedLineView);
        try {
            this.mSize = obtainStyledAttributes.getDimension(1, TypedValue.applyDimension(1, DEFAULT_DOT_SIZE, context.getResources().getDisplayMetrics()));
            this.mSpace = obtainStyledAttributes.getDimension(2, TypedValue.applyDimension(1, DEFAULT_DOT_SPACE, context.getResources().getDisplayMetrics()));
            this.mColor = obtainStyledAttributes.getColor(0, -16777216);
            this.mPaint.setColor(this.mColor);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int paddingLeft = (int) (((float) getPaddingLeft()) + this.mSize + ((float) getPaddingRight()));
        int paddingTop = (int) (((float) getPaddingTop()) + this.mSize + ((float) getPaddingBottom()));
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (!(mode == Integer.MIN_VALUE || mode == 0 || mode == 1073741824)) {
            size = paddingLeft;
        }
        if (mode2 == Integer.MIN_VALUE) {
            paddingTop = Math.min(size2, paddingTop);
        } else if (mode2 == 1073741824) {
            paddingTop = size2;
        }
        Log.d(TAG, "setMeasuredDimension(" + size + ", " + paddingTop + ")");
        setMeasuredDimension(size, paddingTop);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw(" + getWidth() + ", " + getHeight() + ") " + String.format(Locale.US, "0x%x", Integer.valueOf(this.mColor)));
        float f = this.mSize / 2.0f;
        float paddingTop = ((float) getPaddingTop()) + f;
        float width = ((float) (getWidth() - getPaddingRight())) - f;
        float paddingLeft = ((float) getPaddingLeft()) + f;
        while (paddingLeft < width) {
            canvas.drawCircle(paddingLeft, paddingTop, f, this.mPaint);
            paddingLeft += this.mSpace + f + f;
        }
    }

    public void setDotColor(int i) {
        this.mColor = i;
        this.mPaint.setColor(this.mColor);
        invalidate();
    }

    public float getDotColor() {
        return (float) this.mColor;
    }

    public void setDotSize(float f) {
        this.mSize = f;
        invalidate();
    }

    public float getDotSize() {
        return this.mSize;
    }

    public void setDotSpace(float f) {
        this.mSpace = f;
        invalidate();
    }

    public float getDotSpace() {
        return this.mSpace;
    }
}
