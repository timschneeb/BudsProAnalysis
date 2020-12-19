package com.samsung.android.fotaprovider.customize.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.appcompat.util.SeslRoundedCorner;
import com.sec.android.fotaprovider.R;

class FirstLayerRoundedLinearLayout extends LinearLayout {
    private SeslRoundedCorner seslRoundedCorner;

    public FirstLayerRoundedLinearLayout(Context context) {
        super(context);
    }

    public FirstLayerRoundedLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FirstLayerRoundedLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public FirstLayerRoundedLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.seslRoundedCorner = new SeslRoundedCorner(getContext(), false);
        this.seslRoundedCorner.setRoundedCorners(15);
        this.seslRoundedCorner.setRoundedCornerColor(15, getResources().getColor(R.color.middle_content_rounded_corner_background_for_first_layer));
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
