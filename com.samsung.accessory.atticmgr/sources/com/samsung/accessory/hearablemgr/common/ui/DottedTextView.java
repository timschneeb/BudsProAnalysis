package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class DottedTextView extends TextView {
    public DottedTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setText("• " + ((Object) getText()));
    }
}
