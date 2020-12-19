package com.google.android.material.tabs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* access modifiers changed from: package-private */
public abstract class SeslAbsIndicatorView extends View {
    /* access modifiers changed from: package-private */
    public abstract void onHide();

    /* access modifiers changed from: package-private */
    public abstract void onSetSelectedIndicatorColor(int i);

    /* access modifiers changed from: package-private */
    public abstract void onShow();

    /* access modifiers changed from: package-private */
    public abstract void startPressAndReleaseEffect();

    /* access modifiers changed from: package-private */
    public abstract void startPressEffect();

    /* access modifiers changed from: package-private */
    public abstract void startReleaseEffect();

    public SeslAbsIndicatorView(Context context) {
        super(context);
    }

    public SeslAbsIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SeslAbsIndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SeslAbsIndicatorView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setSelectedIndicatorColor(int i) {
        onSetSelectedIndicatorColor(i);
    }

    public void setClick() {
        startPressAndReleaseEffect();
    }

    public void setPressed() {
        startPressEffect();
    }

    public void setReleased() {
        startReleaseEffect();
    }

    public void setHide() {
        onHide();
    }

    public void setShow() {
        onShow();
    }
}
