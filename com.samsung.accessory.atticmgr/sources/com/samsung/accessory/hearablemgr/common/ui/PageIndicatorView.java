package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.samsung.accessory.hearablemgr.R;
import seccompat.android.util.Log;

public class PageIndicatorView extends View {
    private static final int STROKE_PIXEL = 5;
    private static final String TAG = "Attic_PageIndicatorView";
    private int mPageMax = 1;
    private int mPageSelect = 0;
    private Paint mPaintSelect = new Paint(1);
    private Paint mPaintUnselect = new Paint(1);
    private float mSize;

    public PageIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        loadAttributeSet(context, attributeSet);
        init();
    }

    private void loadAttributeSet(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PageIndicatorView);
        try {
            this.mPageMax = obtainStyledAttributes.getInteger(0, 1);
            this.mPageSelect = obtainStyledAttributes.getInteger(1, 0);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private void init() {
        int primaryColor = getPrimaryColor();
        this.mPaintSelect.setColor(primaryColor);
        this.mPaintUnselect.setColor(primaryColor);
        this.mPaintUnselect.setAlpha(128);
        this.mPaintUnselect.setStrokeWidth(5.0f);
        this.mPaintUnselect.setStyle(Paint.Style.STROKE);
        this.mSize = UiUtil.DP_TO_PX(8.0f);
        Log.d(TAG, "init() : " + this.mSize);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        float f = this.mSize;
        int i3 = this.mPageMax;
        int resolveSizeAndState = resolveSizeAndState((int) (((float) getPaddingLeft()) + (((float) i3) * f) + (f * ((float) (i3 - 1))) + ((float) getPaddingRight())), i, 0);
        int resolveSizeAndState2 = resolveSizeAndState((int) (((float) getPaddingTop()) + this.mSize + ((float) getPaddingBottom())), i2, 0);
        Log.d(TAG, "setMeasuredDimension(" + resolveSizeAndState + ", " + resolveSizeAndState2 + ")");
        setMeasuredDimension(resolveSizeAndState, resolveSizeAndState2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw(" + getWidth() + ", " + getHeight() + ")");
        float f = this.mSize;
        int i = this.mPageMax;
        float width = (((float) getWidth()) - ((float) ((int) ((((float) i) * f) + (((float) (i + -1)) * f))))) / 2.0f;
        float height = (((float) getHeight()) - ((float) ((int) f))) / 2.0f;
        float f2 = this.mSize / 2.0f;
        for (int i2 = 0; i2 < this.mPageMax; i2++) {
            if (i2 == this.mPageSelect) {
                canvas.drawCircle((((float) i2) * this.mSize * 2.0f) + width + f2, height + f2, f2, this.mPaintSelect);
            } else {
                canvas.drawCircle((((float) i2) * this.mSize * 2.0f) + width + f2, height + f2, f2 - 2.5f, this.mPaintUnselect);
            }
        }
    }

    private int getPrimaryColor() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(com.samsung.accessory.atticmgr.R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public void setPageMax(int i) {
        this.mPageMax = i;
        invalidate();
    }

    public int getPageMax() {
        return this.mPageMax;
    }

    public void setPageSelect(int i) {
        this.mPageSelect = i;
        invalidate();
    }

    public int getPageSelect() {
        return this.mPageSelect;
    }
}
