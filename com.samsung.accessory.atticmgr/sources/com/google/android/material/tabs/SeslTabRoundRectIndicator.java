package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.util.SeslMisc;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;

public class SeslTabRoundRectIndicator extends SeslAbsIndicatorView {
    private static final int DURATION_PRESS = 50;
    private static final int DURATION_RELEASE = 350;
    private static final float SCALE_MINOR = 0.95f;
    private AnimationSet mPressAnimationSet;

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setClick() {
        super.setClick();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setHide() {
        super.setHide();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setPressed() {
        super.setPressed();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setReleased() {
        super.setReleased();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setSelectedIndicatorColor(int i) {
        super.setSelectedIndicatorColor(i);
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public /* bridge */ /* synthetic */ void setShow() {
        super.setShow();
    }

    public SeslTabRoundRectIndicator(Context context) {
        this(context, null);
    }

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ViewCompat.setBackground(this, ContextCompat.getDrawable(context, SeslMisc.isLightTheme(context) ? R.drawable.sesl_tablayout_subtab_indicator_background_light : R.drawable.sesl_tablayout_subtab_indicator_background_dark));
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i != 0 && !isSelected()) {
            onHide();
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void onHide() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        startAnimation(alphaAnimation);
        setAlpha(0.0f);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void onShow() {
        setAlpha(1.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        startAnimation(alphaAnimation);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void startPressEffect() {
        setAlpha(1.0f);
        this.mPressAnimationSet = new AnimationSet(false);
        this.mPressAnimationSet.setStartOffset(50);
        this.mPressAnimationSet.setFillAfter(true);
        this.mPressAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            /* class com.google.android.material.tabs.SeslTabRoundRectIndicator.AnonymousClass1 */

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                SeslTabRoundRectIndicator.this.mPressAnimationSet = null;
            }
        });
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, SCALE_MINOR, 1.0f, SCALE_MINOR, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(50);
        scaleAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_80);
        scaleAnimation.setFillAfter(true);
        this.mPressAnimationSet.addAnimation(scaleAnimation);
        if (!isSelected()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(50);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_80);
            this.mPressAnimationSet.addAnimation(alphaAnimation);
        }
        startAnimation(this.mPressAnimationSet);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void startReleaseEffect() {
        setAlpha(1.0f);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(SCALE_MINOR, 1.0f, SCALE_MINOR, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(350);
        scaleAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_80);
        scaleAnimation.setFillAfter(true);
        animationSet.addAnimation(scaleAnimation);
        startAnimation(animationSet);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void startPressAndReleaseEffect() {
        setAlpha(1.0f);
        this.mPressAnimationSet = new AnimationSet(false);
        this.mPressAnimationSet.setStartOffset(50);
        this.mPressAnimationSet.setFillAfter(true);
        this.mPressAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            /* class com.google.android.material.tabs.SeslTabRoundRectIndicator.AnonymousClass2 */

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                SeslTabRoundRectIndicator.this.mPressAnimationSet = null;
            }
        });
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, SCALE_MINOR, 1.0f, SCALE_MINOR, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(50);
        scaleAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_80);
        scaleAnimation.setFillAfter(true);
        this.mPressAnimationSet.addAnimation(scaleAnimation);
        if (!isSelected()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(50);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_80);
            this.mPressAnimationSet.addAnimation(alphaAnimation);
        }
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(SCALE_MINOR, 1.0f, SCALE_MINOR, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation2.setStartOffset(50);
        scaleAnimation2.setDuration(350);
        scaleAnimation2.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_80);
        scaleAnimation2.setFillAfter(true);
        this.mPressAnimationSet.addAnimation(scaleAnimation);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public void onSetSelectedIndicatorColor(int i) {
        if (!(getBackground() instanceof NinePatchDrawable)) {
            if (Build.VERSION.SDK_INT >= 22) {
                getBackground().setTint(i);
            } else {
                getBackground().setColorFilter(i, PorterDuff.Mode.SRC_IN);
            }
            if (!isSelected()) {
                setHide();
            }
        }
    }
}
