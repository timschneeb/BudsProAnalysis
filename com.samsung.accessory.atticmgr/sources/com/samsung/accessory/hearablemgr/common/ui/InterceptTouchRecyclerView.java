package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

public class InterceptTouchRecyclerView extends AutoScrollBarsRecyclerView {
    private static final String TAG = (Application.TAG_ + InterceptTouchRecyclerView.class.getSimpleName());
    private boolean mChildTouchEvent = false;

    public InterceptTouchRecyclerView(Context context) {
        super(context);
    }

    public InterceptTouchRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public InterceptTouchRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            View findViewById = findViewById(R.id.dial_rotation_view);
            View findViewById2 = findViewById(R.id.view_disable_touch);
            View findViewById3 = findViewById(R.id.text_preset_2);
            View findViewById4 = findViewById(R.id.text_preset_3);
            if (findViewById != null) {
                boolean isInViewBounds = isInViewBounds(findViewById, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                boolean z = isInViewBounds(findViewById2, (int) motionEvent.getRawX(), (int) motionEvent.getRawY()) || isInViewBounds(findViewById3, (int) motionEvent.getRawX(), (int) motionEvent.getRawY()) || isInViewBounds(findViewById4, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                if (isInViewBounds && !z) {
                    Log.d(TAG, "DialView was touched");
                    this.mChildTouchEvent = true;
                    return false;
                }
            }
        } else if (action == 1 && this.mChildTouchEvent) {
            this.mChildTouchEvent = false;
            return false;
        } else if (this.mChildTouchEvent) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    private static boolean isInViewBounds(View view, int i, int i2) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return new Rect(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight()).contains(i, i2);
    }
}
