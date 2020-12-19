package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.widget.NestedScrollView;

public class CanAwakeNestedScrollView extends NestedScrollView {
    public CanAwakeNestedScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void awakenScrollBar() {
        super.awakenScrollBars();
    }
}
