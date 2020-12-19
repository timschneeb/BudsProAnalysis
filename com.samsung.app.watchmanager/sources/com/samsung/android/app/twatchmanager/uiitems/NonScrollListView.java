package com.samsung.android.app.twatchmanager.uiitems;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class NonScrollListView extends ListView {
    public NonScrollListView(Context context) {
        super(context);
    }

    public NonScrollListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NonScrollListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(536870911, LinearLayoutManager.INVALID_OFFSET));
        getLayoutParams().height = getMeasuredHeight();
    }
}
