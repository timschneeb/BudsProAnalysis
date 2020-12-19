package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.SwitchCompat;

public class RecycleAnimationSwitchCompat extends SwitchCompat {
    public RecycleAnimationSwitchCompat(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public void setChecked(boolean z) {
        layout(getLeft(), getTop(), getRight(), getBottom());
        super.setChecked(z);
    }
}
