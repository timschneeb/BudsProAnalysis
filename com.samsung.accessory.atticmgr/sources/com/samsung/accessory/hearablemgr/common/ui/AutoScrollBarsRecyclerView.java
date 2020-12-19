package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;

public class AutoScrollBarsRecyclerView extends RecyclerView {
    private static final String TAG = "Attic_UpdateRecyclerView";
    private boolean mTouched;

    public AutoScrollBarsRecyclerView(Context context) {
        super(context);
    }

    public AutoScrollBarsRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AutoScrollBarsRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mTouched = true;
        } else if (action == 1) {
            this.mTouched = false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public boolean awakenScrollBars(int i, boolean z) {
        if (this.mTouched) {
            return super.awakenScrollBars(i, z);
        }
        return false;
    }
}
