package com.samsung.android.app.twatchmanager.uiitems;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;

public class TextViewBold extends TextView {
    private static final String TAG = ("tUHM:" + TextViewBold.class.getSimpleName());

    public TextViewBold(Context context) {
        super(context);
    }

    public TextViewBold(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public TextViewBold(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attributeSet) {
        setTypeface(Typeface.create(!HostManagerUtils.isSamsungDevice() ? "sec-roboto-light" : "sans-serif-light", 1));
    }
}
