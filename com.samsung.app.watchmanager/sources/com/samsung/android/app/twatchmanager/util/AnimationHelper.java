package com.samsung.android.app.twatchmanager.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import com.samsung.android.app.watchmanager.R;

public class AnimationHelper {
    private static final String TAG = "AnimationHelper";
    int animationType;
    View arrowA;
    View arrowB;
    private int connectCase = 1;
    Context context;
    boolean initialized;
    private boolean mIsSlidingUp;
    private int mParallaxOffset = -1;
    private float mRatio;
    float prevOffset = 0.0f;
    View source;
    float sourceX;
    float sourceY;
    View target;
    float targetX;
    float targetY;

    public AnimationHelper(Context context2) {
        this.context = context2;
    }

    public AnimationHelper(Context context2, View view, int i, int i2) {
        this.context = context2;
        this.source = view;
        this.mRatio = ((float) i2) / ((float) i);
        this.animationType = 2;
        String str = TAG;
        Log.d(str, "AnimationHelper() slideValue : " + i2 + " ratio : " + this.mRatio);
    }

    public AnimationHelper(Context context2, View view, View view2, int i) {
        this.context = context2;
        this.source = view;
        this.target = view2;
        this.animationType = i;
    }

    private void animateAlpha(float f) {
        int applyDimension = (int) TypedValue.applyDimension(1, 20.0f, this.context.getResources().getDisplayMetrics());
        double d2 = (double) f;
        if (d2 <= 0.4d) {
            float f2 = 1.0f - (2.5f * f);
            if (((double) f2) < 0.05d) {
                this.source.setAlpha(0.05f);
            } else {
                this.source.setAlpha(f2);
            }
            this.target.setAlpha(0.0f);
            float f3 = (float) applyDimension;
            this.target.setY(this.targetY + f3);
            this.source.setTranslationY(((-f3) * f) / 0.4f);
        } else if (d2 > 0.4d) {
            this.source.setAlpha(0.0f);
            View view = this.target;
            Double.isNaN(d2);
            double d3 = d2 - 0.4d;
            view.setAlpha((float) (1.6666666666666667d * d3));
            View view2 = this.target;
            double d4 = (double) (this.targetY + ((float) applyDimension));
            double d5 = (double) applyDimension;
            Double.isNaN(d5);
            Double.isNaN(d4);
            view2.setY((float) (d4 - ((d5 * d3) / 0.6d)));
        }
    }

    private void animateSubText(float f) {
        View view;
        int i;
        this.source.setAlpha(1.0f - f);
        if (((double) this.source.getAlpha()) <= 0.3d) {
            view = this.source;
            i = 8;
        } else {
            view = this.source;
            i = 0;
        }
        view.setVisibility(i);
    }

    private void animateXYScale(float f) {
        this.source.setTranslationX((this.targetX - this.sourceX) * f);
        this.source.setTranslationY((this.targetY - this.sourceY) * f);
        this.source.setScaleY(1.0f - ((1.0f - (((float) this.target.getHeight()) / ((float) this.source.getHeight()))) * f));
        this.source.setScaleX(1.0f - ((1.0f - (((float) this.target.getWidth()) / ((float) this.source.getWidth()))) * f));
    }

    private void animateY(float f) {
        if (this.mParallaxOffset < 0) {
            int height = (int) (((float) this.source.getHeight()) * this.mRatio);
            String str = TAG;
            Log.d(str, "animateY() init mParallaxOffset ... source.getHeight() : " + this.source.getHeight() + " result : " + height);
            this.mParallaxOffset = height;
        }
        if (this.mParallaxOffset > 0) {
            this.source.setTranslationY((float) getCurrentParallaxOffset(f));
        }
    }

    @TargetApi(11)
    private void init() {
        if (!this.initialized) {
            int i = this.animationType;
            if (i == 1 || !(i == 2 || i == 3 || i != 4)) {
                this.sourceX = this.source.getX();
                this.sourceY = this.source.getY();
                this.targetX = this.target.getX();
                this.targetY = this.target.getY();
                this.source.setPivotX(0.0f);
                this.source.setPivotY(0.0f);
            }
            this.initialized = true;
        }
    }

    @TargetApi(21)
    private void setPathInterpolator(ValueAnimator valueAnimator) {
        if (Build.VERSION.SDK_INT >= 21) {
            valueAnimator.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f));
        }
    }

    public void animate(float f) {
        init();
        if (this.connectCase != 1 || ((double) Math.abs(this.prevOffset - f)) <= 0.5d) {
            if (this.prevOffset < f) {
                this.mIsSlidingUp = true;
            }
            this.prevOffset = f;
            int i = this.animationType;
            if (i == 1) {
                animateXYScale(f);
            } else if (i == 2) {
                animateY(f);
            } else if (i == 3) {
                animateSubText(f);
            } else if (i == 4) {
                animateAlpha(f);
            }
        }
    }

    public void animate(float f, int i) {
        this.connectCase = i;
        animate(f);
    }

    public int getCurrentParallaxOffset(float f) {
        int max = (int) (((float) this.mParallaxOffset) * Math.max(f, 0.0f));
        return this.mIsSlidingUp ? -max : max;
    }

    public void startAlphaAnimation(View view, int i, final boolean z) {
        final AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.setDuration((long) i);
        animatorSet.play(ofFloat);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            /* class com.samsung.android.app.twatchmanager.util.AnimationHelper.AnonymousClass5 */

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                Log.d(AnimationHelper.TAG, "onAnimationEnd> ");
                if (z) {
                    animatorSet.start();
                    animatorSet.removeAllListeners();
                }
            }
        });
    }

    public void startArrowAnimation(View view) {
        this.arrowA = view.findViewById(R.id.arrow_A);
        this.arrowB = view.findViewById(R.id.arrow_B);
        final AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.arrowB, View.ALPHA, 0.0f, 1.0f);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.setDuration(333L);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.arrowB, "translationY", (float) (-UIUtils.convertDpToPx(this.context, 10)));
        ofFloat2.setDuration(650L);
        setPathInterpolator(ofFloat2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.arrowB, "alpha", 1.0f, 0.0f);
        ofFloat3.setInterpolator(new LinearInterpolator());
        ofFloat3.setDuration(650L);
        ofFloat3.setStartDelay(200);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.arrowA, "alpha", 0.0f, 1.0f);
        ofFloat4.setInterpolator(new LinearInterpolator());
        ofFloat4.setDuration(333L);
        ofFloat4.setStartDelay(200);
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.arrowA, "translationY", (float) (-UIUtils.convertDpToPx(this.context, 10)));
        ofFloat5.setDuration(650L);
        setPathInterpolator(ofFloat5);
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(this.arrowA, "alpha", 1.0f, 0.0f);
        ofFloat6.setInterpolator(new LinearInterpolator());
        ofFloat6.setDuration(650L);
        ofFloat6.setStartDelay(200);
        animatorSet.play(ofFloat).with(ofFloat2).with(ofFloat4).with(ofFloat5);
        animatorSet.play(ofFloat3).after(ofFloat2);
        animatorSet.play(ofFloat6).after(ofFloat5);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            /* class com.samsung.android.app.twatchmanager.util.AnimationHelper.AnonymousClass3 */

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                Log.d(AnimationHelper.TAG, "onAnimationEnd> ");
                animatorSet.start();
                animatorSet.removeAllListeners();
            }
        });
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.android.app.twatchmanager.util.AnimationHelper.AnonymousClass4 */

            public void run() {
                AnimationHelper.this.arrowA.setVisibility(0);
                AnimationHelper.this.arrowB.setVisibility(0);
            }
        }, 200);
    }

    public void startFlickeringTextAnimation(View view, final AnimatorSet animatorSet, int i) {
        final AnimatorSet animatorSet2 = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 0.85f);
        ofFloat.setInterpolator(new LinearInterpolator());
        long j = (long) i;
        ofFloat.setDuration(j);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, View.ALPHA, 0.85f, 0.2f);
        ofFloat2.setInterpolator(new LinearInterpolator());
        ofFloat2.setDuration(j);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, View.ALPHA, 0.2f, 0.85f);
        ofFloat3.setInterpolator(new LinearInterpolator());
        ofFloat3.setDuration(j);
        ofFloat3.setStartDelay(j);
        animatorSet2.play(ofFloat);
        animatorSet2.start();
        animatorSet2.addListener(new AnimatorListenerAdapter() {
            /* class com.samsung.android.app.twatchmanager.util.AnimationHelper.AnonymousClass1 */

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                Log.d(AnimationHelper.TAG, "onAnimationEnd> ");
                animatorSet.start();
                animatorSet2.removeAllListeners();
                animatorSet2.end();
                animatorSet2.cancel();
            }
        });
        animatorSet.play(ofFloat2).with(ofFloat3);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            /* class com.samsung.android.app.twatchmanager.util.AnimationHelper.AnonymousClass2 */

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                Log.d(AnimationHelper.TAG, "onAnimationEnd> ");
                animatorSet.start();
            }
        });
    }
}
