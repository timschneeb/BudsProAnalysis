package com.samsung.android.app.twatchmanager.uiitems;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;

public class ProgressCircle extends ProgressBar {
    public ProgressCircle(Context context) {
        super(context);
    }

    public ProgressCircle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public ProgressCircle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attributeSet) {
        setColor("#" + Integer.toHexString(context.getResources().getColor(R.color.setup_progress_tint)));
    }

    public void setColor(String str) {
        if (Build.VERSION.SDK_INT >= 21) {
            UIUtils.setColorFilter(str, getIndeterminateDrawable());
        }
    }
}
