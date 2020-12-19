package com.samsung.android.fotaprovider.customize.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.core.widget.NestedScrollView;
import com.sec.android.fotaprovider.R;

public class RoundedNestedScrollView extends NestedScrollView {
    private SeslRoundedCorner seslRoundedCorner;

    public RoundedNestedScrollView(Context context) {
        super(context);
    }

    public RoundedNestedScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RoundedNestedScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.seslRoundedCorner = new SeslRoundedCorner(getContext(), false);
        this.seslRoundedCorner.setRoundedCorners(15);
        this.seslRoundedCorner.setRoundedCornerColor(15, getResources().getColor(R.color.activity_base_rounded_corner_background));
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        SeslRoundedCorner seslRoundedCorner2 = this.seslRoundedCorner;
        if (seslRoundedCorner2 != null) {
            seslRoundedCorner2.drawRoundedCorner(canvas);
        }
    }
}
