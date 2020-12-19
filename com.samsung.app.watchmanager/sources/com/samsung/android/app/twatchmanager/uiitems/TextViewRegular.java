package com.samsung.android.app.twatchmanager.uiitems;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;

public class TextViewRegular extends TextView {
    private static final String TAG = ("tUHM:" + TextViewRegular.class.getSimpleName());

    public TextViewRegular(Context context) {
        super(context);
    }

    public TextViewRegular(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public TextViewRegular(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attributeSet) {
        setTypeface(Typeface.create(HostManagerUtils.isSamsungDevice() ? "Roboto-Regular" : "sans-serif-light", 0));
    }
}
