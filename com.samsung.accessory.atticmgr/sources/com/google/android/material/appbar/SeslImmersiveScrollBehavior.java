package com.google.android.material.appbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsController;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.EditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.reflect.content.res.SeslConfigurationReflector;
import androidx.reflect.view.SeslDecorViewReflector;
import com.google.android.material.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.ContextUtils;
import java.util.List;

public final class SeslImmersiveScrollBehavior extends AppBarLayout.Behavior {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MSG_APPEAR_ANIMATION = 100;
    private static final String TAG = SeslImmersiveScrollBehavior.class.getSimpleName();
    private boolean isRoundedCornerHide = false;
    private boolean isSetCustomAnimationCallback = false;
    private WindowInsetsAnimationController mAnimationController;
    private Handler mAnimationHandler = new Handler(Looper.getMainLooper()) {
        /* class com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (message.what == 100) {
                SeslImmersiveScrollBehavior.this.startRestoreAnimation();
            }
        }
    };
    private AppBarLayout mAppBarLayout;
    private View mBottomArea;
    private boolean mCanImmersiveScroll;
    private CancellationSignal mCancellationSignal;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private View mContentView;
    private Context mContext;
    private CoordinatorLayout mCoordinatorLayout;
    private float mCurOffset = 0.0f;
    private WindowInsetsAnimation.Callback mCustomWindowInsetsAnimation = null;
    private View mDecorView;
    private WindowInsets mDecorViewInset;
    private float mHeightProportion;
    private boolean mIsDeskTopMode;
    private boolean mIsFullScreen = false;
    private boolean mIsMultiWindow;
    private View mNavigationBarBg;
    private int mNavigationBarHeight;
    private boolean mNeedRestoreAnim = true;
    private ValueAnimator mOffsetAnimator;
    private AppBarLayout.OnOffsetChangedListener mOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
        /* class com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass8 */

        @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            float f;
            int i2;
            float f2;
            if (!SeslImmersiveScrollBehavior.this.isSetCustomAnimationCallback) {
                float f3 = 0.0f;
                if (SeslImmersiveScrollBehavior.this.mCanImmersiveScroll) {
                    int height = SeslImmersiveScrollBehavior.this.mBottomArea != null ? SeslImmersiveScrollBehavior.this.mBottomArea.getHeight() : 0;
                    float seslGetCollapsedHeight = appBarLayout.seslGetCollapsedHeight();
                    int i3 = (seslGetCollapsedHeight > 0.0f ? 1 : (seslGetCollapsedHeight == 0.0f ? 0 : -1));
                    float f4 = ((float) (SeslImmersiveScrollBehavior.this.mNavigationBarHeight + height)) / (i3 == 0 ? 1.0f : seslGetCollapsedHeight);
                    float totalScrollRange = ((float) ((appBarLayout.getTotalScrollRange() - AppBarLayout.seslGetTCScrollRange()) + i)) - seslGetCollapsedHeight;
                    float f5 = ((float) SeslImmersiveScrollBehavior.this.mStatusBarHeight) + totalScrollRange;
                    float f6 = (f4 + 1.0f) * totalScrollRange;
                    float min = Math.min((float) SeslImmersiveScrollBehavior.this.mStatusBarHeight, ((float) SeslImmersiveScrollBehavior.this.mStatusBarHeight) + totalScrollRange);
                    float max = Math.max(Math.min((float) SeslImmersiveScrollBehavior.this.mNavigationBarHeight, ((float) SeslImmersiveScrollBehavior.this.mNavigationBarHeight) + f6), 0.0f);
                    float f7 = (((float) SeslImmersiveScrollBehavior.this.mNavigationBarHeight) - max) / ((float) (SeslImmersiveScrollBehavior.this.mNavigationBarHeight != 0 ? SeslImmersiveScrollBehavior.this.mNavigationBarHeight : 1));
                    if (((float) appBarLayout.getBottom()) > seslGetCollapsedHeight) {
                        f = (float) (SeslImmersiveScrollBehavior.this.mAppBarLayout.getTotalScrollRange() + i);
                        if (SeslImmersiveScrollBehavior.this.mIsMultiWindow && SeslImmersiveScrollBehavior.this.mBottomArea != null) {
                            f += (float) SeslImmersiveScrollBehavior.this.mBottomArea.getHeight();
                        }
                        if (!SeslImmersiveScrollBehavior.this.isLandscape() && !SeslImmersiveScrollBehavior.this.mIsMultiWindow && SeslImmersiveScrollBehavior.this.mBottomArea != null && SeslImmersiveScrollBehavior.this.mDecorViewInset != null) {
                            SeslImmersiveScrollBehavior.this.mBottomArea.setTranslationY((float) (-SeslImmersiveScrollBehavior.this.mNavigationBarHeight));
                            f = f + ((float) SeslImmersiveScrollBehavior.this.mBottomArea.getHeight()) + ((float) SeslImmersiveScrollBehavior.this.mNavigationBarHeight);
                        }
                    } else if (SeslImmersiveScrollBehavior.this.dispatchImmersiveScrollEnable()) {
                        if (SeslImmersiveScrollBehavior.this.mBottomArea != null) {
                            float f8 = (float) height;
                            float min2 = Math.min(f6 + f8, max);
                            SeslImmersiveScrollBehavior.this.mBottomArea.setTranslationY(-min2);
                            f2 = Math.max(f8 + min2, 0.0f);
                            i2 = appBarLayout.getTotalScrollRange();
                        } else {
                            f2 = Math.max(max, 0.0f);
                            i2 = appBarLayout.getTotalScrollRange();
                        }
                        float f9 = f2 + ((float) i2) + ((float) i);
                        if (SeslImmersiveScrollBehavior.this.mNavigationBarBg != null) {
                            SeslImmersiveScrollBehavior.this.mNavigationBarBg.setTranslationY(-Math.min(0.0f, f6));
                        } else if (SeslImmersiveScrollBehavior.this.mNavigationBarHeight != 0) {
                            SeslImmersiveScrollBehavior.this.findSystemBarsBackground();
                            if (SeslImmersiveScrollBehavior.this.mNavigationBarBg != null) {
                                SeslImmersiveScrollBehavior.this.mNavigationBarBg.setTranslationY(0.0f);
                            }
                        }
                        if (SeslImmersiveScrollBehavior.this.mStatusBarBg != null) {
                            SeslImmersiveScrollBehavior.this.mStatusBarBg.setTranslationY(Math.min(0.0f, totalScrollRange));
                        }
                        if (SeslImmersiveScrollBehavior.this.mCurOffset != f5) {
                            SeslImmersiveScrollBehavior.this.mCurOffset = f5;
                            if (SeslImmersiveScrollBehavior.this.mAnimationController != null) {
                                int i4 = (int) max;
                                SeslImmersiveScrollBehavior.this.forceHideRoundedCorner(i4);
                                SeslImmersiveScrollBehavior.this.mAnimationController.setInsetsAndAlpha(Insets.of(0, (int) min, 0, i4), 1.0f, f7);
                            }
                        }
                        f3 = f9;
                    } else {
                        if (SeslImmersiveScrollBehavior.this.mStatusBarBg != null) {
                            SeslImmersiveScrollBehavior.this.mStatusBarBg.setTranslationY(0.0f);
                        }
                        if (SeslImmersiveScrollBehavior.this.mNavigationBarBg != null) {
                            SeslImmersiveScrollBehavior.this.mNavigationBarBg.setTranslationY(0.0f);
                        }
                        f = (float) (SeslImmersiveScrollBehavior.this.mAppBarLayout.getTotalScrollRange() + i);
                        if (SeslImmersiveScrollBehavior.this.mBottomArea != null) {
                            float f10 = (float) height;
                            if (i3 == 0) {
                                seslGetCollapsedHeight = 1.0f;
                            }
                            float bottom = f10 - (((float) SeslImmersiveScrollBehavior.this.mAppBarLayout.getBottom()) * (f10 / seslGetCollapsedHeight));
                            SeslImmersiveScrollBehavior.this.mBottomArea.setTranslationY(Math.max(bottom, 0.0f));
                            f = (float) ((int) ((f + ((float) SeslImmersiveScrollBehavior.this.mBottomArea.getHeight())) - Math.max(bottom, 0.0f)));
                        }
                        SeslImmersiveScrollBehavior.this.finishWindowInsetsAnimationController();
                    }
                    f3 = f;
                } else {
                    if (SeslImmersiveScrollBehavior.this.mStatusBarBg != null) {
                        SeslImmersiveScrollBehavior.this.mStatusBarBg.setTranslationY(0.0f);
                    }
                    if (SeslImmersiveScrollBehavior.this.mNavigationBarBg != null) {
                        SeslImmersiveScrollBehavior.this.mNavigationBarBg.setTranslationY(0.0f);
                    }
                    if (SeslImmersiveScrollBehavior.this.mBottomArea != null) {
                        SeslImmersiveScrollBehavior.this.mBottomArea.setTranslationY(0.0f);
                    }
                }
                if (SeslImmersiveScrollBehavior.this.mAppBarLayout != null) {
                    SeslImmersiveScrollBehavior.this.mAppBarLayout.onImmOffsetChanged((int) f3);
                }
            }
        }
    };
    private WindowInsetsAnimationController mPendingRequestOnReady;
    private int mPrevOffset;
    private int mPrevOrientation;
    private boolean mShownAtDown;
    private View mStatusBarBg;
    private int mStatusBarHeight;
    private View mTargetView;
    private boolean mToolIsMouse;
    private final WindowInsetsAnimation.Callback mWindowAnimationCallback = new WindowInsetsAnimation.Callback(1) {
        /* class com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass5 */

        public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
            return windowInsets;
        }

        public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
            SeslImmersiveScrollBehavior.super.onEnd(windowInsetsAnimation);
            if (SeslImmersiveScrollBehavior.this.mContentView != null) {
                SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                seslImmersiveScrollBehavior.mDecorViewInset = seslImmersiveScrollBehavior.mContentView.getRootWindowInsets();
                SeslImmersiveScrollBehavior.this.mContentView.dispatchApplyWindowInsets(SeslImmersiveScrollBehavior.this.mDecorViewInset);
            }
        }
    };
    private WindowInsetsAnimationControlListener mWindowInsetsAnimationControlListener = new WindowInsetsAnimationControlListener() {
        /* class com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass4 */

        public void onReady(WindowInsetsAnimationController windowInsetsAnimationController, int i) {
            if (SeslImmersiveScrollBehavior.this.mDecorView != null) {
                SeslImmersiveScrollBehavior.this.mCancellationSignal = null;
                SeslImmersiveScrollBehavior.this.mAnimationController = windowInsetsAnimationController;
                SeslImmersiveScrollBehavior.this.mPendingRequestOnReady = null;
            }
        }

        public void onFinished(WindowInsetsAnimationController windowInsetsAnimationController) {
            SeslImmersiveScrollBehavior.this.resetWindowInsetsAnimationController();
        }

        public void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController) {
            SeslImmersiveScrollBehavior.this.cancelWindowInsetsAnimationController();
        }
    };
    private WindowInsetsController mWindowInsetsController = null;

    public SeslImmersiveScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        updateSystemBarsHeight();
        updateAppBarHeightProportion();
    }

    private boolean isDexEnabled() {
        Context context = this.mContext;
        if (context == null) {
            return false;
        }
        return SeslConfigurationReflector.isDexEnabled(context.getResources().getConfiguration());
    }

    private boolean getCurrentNavbarCanMoveState() {
        try {
            return this.mContext.getApplicationContext().getResources().getBoolean(Resources.getSystem().getIdentifier("config_navBarCanMove", "bool", "android"));
        } catch (Exception e) {
            String str = TAG;
            Log.e(str, "ERROR, e : " + e.getMessage());
            return true;
        }
    }

    private boolean isAccessibilityEnable() {
        Context context = this.mContext;
        if (context == null) {
            return false;
        }
        return ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled();
    }

    private boolean canImmersiveScroll() {
        AppBarLayout appBarLayout;
        if (this.mAppBarLayout != null && Build.VERSION.SDK_INT >= 30 && !isDexEnabled() && !this.isSetCustomAnimationCallback) {
            if (this.mAppBarLayout.getIsMouse()) {
                switchImmersiveScroll(false, false);
                return false;
            } else if (isAccessibilityEnable()) {
                updateOrientationState();
                switchImmersiveScroll(false, true);
                return false;
            } else {
                View view = this.mDecorView;
                if (view != null) {
                    this.mDecorViewInset = view.getRootWindowInsets();
                    WindowInsets windowInsets = this.mDecorViewInset;
                    if (windowInsets != null) {
                        boolean isVisible = windowInsets.isVisible(WindowInsets.Type.ime());
                        updateOrientationState();
                        if (isVisible || (this.mDecorView.findFocus() instanceof EditText)) {
                            switchImmersiveScroll(false, true);
                            return false;
                        }
                    }
                }
                if (this.mAppBarLayout.seslGetImmersiveScroll()) {
                    switchImmersiveScroll(true, false);
                    boolean updateOrientationState = getCurrentNavbarCanMoveState() ? updateOrientationState() : true;
                    Context context = this.mContext;
                    if (context == null) {
                        return updateOrientationState;
                    }
                    Activity activity = ContextUtils.getActivity(context);
                    if (activity == null && (appBarLayout = this.mAppBarLayout) != null) {
                        this.mContext = appBarLayout.getContext();
                        activity = ContextUtils.getActivity(this.mAppBarLayout.getContext());
                    }
                    if (activity == null) {
                        return updateOrientationState;
                    }
                    boolean isMultiWindow = isMultiWindow(activity);
                    if (this.mIsMultiWindow != isMultiWindow) {
                        forceRestoreWindowInset(true);
                        cancelWindowInsetsAnimationController();
                    }
                    this.mIsMultiWindow = isMultiWindow;
                    if (this.mIsMultiWindow) {
                        return false;
                    }
                    return updateOrientationState;
                }
                switchImmersiveScroll(false, false);
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean dispatchImmersiveScrollEnable() {
        boolean canImmersiveScroll = canImmersiveScroll();
        setFullScreen(Boolean.valueOf(canImmersiveScroll));
        updateAppBarHeightProportion();
        updateSystemBarsHeight();
        return canImmersiveScroll;
    }

    private void switchImmersiveScroll(boolean z, boolean z2) {
        if (this.mCanImmersiveScroll != z) {
            this.mCanImmersiveScroll = z;
            forceRestoreWindowInset(z2);
            setFullScreen(Boolean.valueOf(z));
            setAppBarScrolling(z);
        }
    }

    private boolean isMultiWindow(Activity activity) {
        return activity.isInMultiWindowMode();
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.AppBarLayout.Behavior
    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
        dispatchImmersiveScrollEnable();
        return super.onMeasureChild(coordinatorLayout, appBarLayout, i, i2, i3, i4);
    }

    private boolean updateOrientationState() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout == null) {
            return false;
        }
        int currentOrientation = appBarLayout.getCurrentOrientation();
        if (this.mPrevOrientation != currentOrientation) {
            this.mPrevOrientation = currentOrientation;
            forceRestoreWindowInset(true);
        }
        if (currentOrientation == 1) {
            return true;
        }
        if (currentOrientation == 2) {
            return false;
        }
        Log.e(TAG, "ERROR, e : AppbarLayout Configuration is wrong");
        return false;
    }

    private boolean IsFullScreen() {
        return this.mIsFullScreen;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isLandscape() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout != null && appBarLayout.getCurrentOrientation() == 2) {
            return true;
        }
        return false;
    }

    private void setFullScreen(Boolean bool) {
        AppBarLayout appBarLayout;
        WindowInsets windowInsets;
        AppBarLayout appBarLayout2;
        if (this.mDecorView != null && (appBarLayout = this.mAppBarLayout) != null && !this.isSetCustomAnimationCallback) {
            if (this.mContext == null) {
                this.mContext = appBarLayout.getContext();
                if (this.mContext == null) {
                    return;
                }
            }
            this.mIsFullScreen = bool.booleanValue();
            Activity activity = ContextUtils.getActivity(this.mContext);
            if (activity == null && (appBarLayout2 = this.mAppBarLayout) != null) {
                this.mContext = appBarLayout2.getContext();
                activity = ContextUtils.getActivity(this.mAppBarLayout.getContext());
            }
            if (activity != null) {
                Window window = activity.getWindow();
                if (bool.booleanValue()) {
                    this.mAppBarLayout.setImmersiveTopInset(this.mStatusBarHeight);
                    window.setDecorFitsSystemWindows(false);
                    return;
                }
                this.mAppBarLayout.setImmersiveTopInset(0);
                boolean z = true;
                window.setDecorFitsSystemWindows(true);
                if (this.mAppBarLayout.getImmHideStatusBarForLandscape() && isLandscape()) {
                    if (this.mWindowInsetsController == null) {
                        startAnimationControlRequest();
                    }
                    this.mDecorViewInset = this.mDecorView.getRootWindowInsets();
                    if (this.mWindowInsetsController != null && (windowInsets = this.mDecorViewInset) != null) {
                        if (windowInsets.getInsets(WindowInsets.Type.statusBars()).top == 0) {
                            z = false;
                        }
                        if (z) {
                            this.mWindowInsetsController.hide(WindowInsets.Type.statusBars());
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void layoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
        super.layoutChild(coordinatorLayout, (View) appBarLayout, i);
        WindowInsetsController windowInsetsController = this.mWindowInsetsController;
        if (windowInsetsController != null) {
            windowInsetsController.addOnControllableInsetsChangedListener(new WindowInsetsController.OnControllableInsetsChangedListener() {
                /* class com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass2 */

                public void onControllableInsetsChanged(WindowInsetsController windowInsetsController, int i) {
                    if (i == 8) {
                        SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                        seslImmersiveScrollBehavior.mDecorViewInset = seslImmersiveScrollBehavior.mDecorView.getRootWindowInsets();
                        if (SeslImmersiveScrollBehavior.this.mDecorViewInset != null && SeslImmersiveScrollBehavior.this.mDecorViewInset.isVisible(WindowInsets.Type.statusBars()) && SeslImmersiveScrollBehavior.this.isAppBarHide()) {
                            SeslImmersiveScrollBehavior.this.seslRestoreTopAndBottom();
                        }
                    }
                }
            });
        }
        AppBarLayout appBarLayout2 = this.mAppBarLayout;
        if (appBarLayout2 == null || appBarLayout != appBarLayout2) {
            initImmViews(coordinatorLayout, appBarLayout);
        }
    }

    /* access modifiers changed from: package-private */
    public void initImmViews(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
        this.mAppBarLayout = appBarLayout;
        this.mCoordinatorLayout = coordinatorLayout;
        this.mAppBarLayout.addOnOffsetChangedListener(this.mOffsetChangedListener);
        int i = 0;
        if (!this.mAppBarLayout.getImmersiveByUser()) {
            this.mAppBarLayout.setImmersiveScroll(true, false);
        }
        this.mDecorView = this.mAppBarLayout.getRootView();
        this.mContentView = this.mDecorView.findViewById(16908290);
        if (this.isSetCustomAnimationCallback) {
            this.mContentView.setWindowInsetsAnimationCallback(this.mCustomWindowInsetsAnimation);
        } else {
            this.mContentView.setWindowInsetsAnimationCallback(this.mWindowAnimationCallback);
        }
        findSystemBarsBackground();
        dispatchImmersiveScrollEnable();
        while (true) {
            if (i >= appBarLayout.getChildCount()) {
                break;
            }
            View childAt = appBarLayout.getChildAt(i);
            if (this.mCollapsingToolbarLayout != null) {
                break;
            } else if (childAt instanceof CollapsingToolbarLayout) {
                this.mCollapsingToolbarLayout = (CollapsingToolbarLayout) childAt;
                break;
            } else {
                i++;
            }
        }
        View findViewById = coordinatorLayout.findViewById(R.id.bottom_bar_overlay);
        if (this.mBottomArea == null || findViewById != null) {
            this.mBottomArea = findViewById;
        }
    }

    /* access modifiers changed from: package-private */
    public void setWindowInsetsAnimationCallback(AppBarLayout appBarLayout, WindowInsetsAnimation.Callback callback) {
        if (this.mContentView == null) {
            this.mDecorView = appBarLayout.getRootView();
            this.mContentView = this.mDecorView.findViewById(16908290);
        }
        if (callback == null) {
            this.isSetCustomAnimationCallback = false;
        } else {
            this.mCustomWindowInsetsAnimation = callback;
            this.isSetCustomAnimationCallback = true;
        }
        if (this.isSetCustomAnimationCallback) {
            this.mContentView.setWindowInsetsAnimationCallback(this.mCustomWindowInsetsAnimation);
            switchImmersiveScroll(false, false);
            View view = this.mBottomArea;
            if (view != null) {
                view.setTranslationY(0.0f);
                return;
            }
            return;
        }
        this.mContentView.setPadding(0, 0, 0, 0);
        this.mContentView.setWindowInsetsAnimationCallback(this.mWindowAnimationCallback);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void findSystemBarsBackground() {
        View view = this.mDecorView;
        if (view != null && this.mContext != null) {
            this.mDecorViewInset = view.getRootWindowInsets();
            this.mDecorView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                /* class com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass3 */

                public boolean onPreDraw() {
                    SeslImmersiveScrollBehavior.this.mDecorView.getViewTreeObserver().removeOnPreDrawListener(this);
                    SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                    seslImmersiveScrollBehavior.mStatusBarBg = seslImmersiveScrollBehavior.mDecorView.findViewById(16908335);
                    SeslImmersiveScrollBehavior seslImmersiveScrollBehavior2 = SeslImmersiveScrollBehavior.this;
                    seslImmersiveScrollBehavior2.mNavigationBarBg = seslImmersiveScrollBehavior2.mDecorView.findViewById(16908336);
                    return false;
                }
            });
            updateSystemBarsHeight();
        }
    }

    private void updateSystemBarsHeight() {
        Context context = this.mContext;
        if (context != null) {
            Resources resources = context.getResources();
            int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                this.mStatusBarHeight = resources.getDimensionPixelSize(identifier);
            }
            int identifier2 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (identifier2 > 0) {
                this.mNavigationBarHeight = resources.getDimensionPixelSize(identifier2);
            }
        }
    }

    private void updateAppBarHeightProportion() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout != null) {
            if (this.mContext == null) {
                this.mContext = appBarLayout.getContext();
                if (this.mContext == null) {
                    return;
                }
            }
            Resources resources = this.mContext.getResources();
            this.mHeightProportion = ResourcesCompat.getFloat(resources, R.dimen.sesl_appbar_height_proportion);
            float f = this.mHeightProportion;
            float f2 = 0.0f;
            if (f != 0.0f) {
                f2 = f + getDifferImmHeightRatio(resources);
            }
            if (IsFullScreen()) {
                this.mAppBarLayout.internalProportion(f2);
            } else {
                this.mAppBarLayout.internalProportion(this.mHeightProportion);
            }
        }
    }

    private float getDifferImmHeightRatio(Resources resources) {
        return ((float) this.mStatusBarHeight) / ((float) resources.getDisplayMetrics().heightPixels);
    }

    private void setAppBarScrolling(boolean z) {
        if (z != this.mAppBarLayout.getCanScroll()) {
            this.mAppBarLayout.setCanScroll(z);
        }
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.AppBarLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
        this.mTargetView = view2;
        if (dispatchImmersiveScrollEnable()) {
            startAnimationControlRequest();
        }
        return super.onStartNestedScroll(coordinatorLayout, appBarLayout, view, view2, i, i2);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.AppBarLayout.Behavior
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        this.mTargetView = view;
        super.onNestedScroll(coordinatorLayout, appBarLayout, view, i, i2, i3, i4, i5, iArr);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.AppBarLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
        this.mTargetView = view;
        if (this.mCancellationSignal != null) {
            iArr[0] = i;
            iArr[1] = i2;
            return;
        }
        super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, i, i2, iArr, i3);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.AppBarLayout.Behavior
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
        this.mTargetView = view;
        super.onStopNestedScroll(coordinatorLayout, appBarLayout, view, i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void cancelWindowInsetsAnimationController() {
        View view = this.mDecorView;
        if (view != null) {
            this.mDecorViewInset = view.getRootWindowInsets();
            WindowInsets windowInsets = this.mDecorViewInset;
            if (windowInsets != null) {
                this.mShownAtDown = windowInsets.isVisible(WindowInsets.Type.statusBars()) | this.mDecorViewInset.isVisible(WindowInsets.Type.navigationBars());
            }
        }
        WindowInsetsAnimationController windowInsetsAnimationController = this.mAnimationController;
        if (windowInsetsAnimationController != null) {
            windowInsetsAnimationController.finish(this.mShownAtDown);
        }
        CancellationSignal cancellationSignal = this.mCancellationSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
        resetWindowInsetsAnimationController();
    }

    /* access modifiers changed from: package-private */
    public void forceRestoreWindowInset(boolean z) {
        WindowInsets windowInsets;
        if (this.mWindowInsetsController != null) {
            this.mDecorViewInset = this.mDecorView.getRootWindowInsets();
            AppBarLayout appBarLayout = this.mAppBarLayout;
            if ((appBarLayout != null && appBarLayout.getImmHideStatusBarForLandscape() && isLandscape()) || (windowInsets = this.mDecorViewInset) == null) {
                return;
            }
            if ((!windowInsets.isVisible(WindowInsets.Type.statusBars()) || !this.mDecorViewInset.isVisible(WindowInsets.Type.navigationBars())) || isAppBarHide() || z) {
                this.mWindowInsetsController.show(WindowInsets.Type.systemBars());
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void finishWindowInsetsAnimationController() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout != null) {
            WindowInsetsAnimationController windowInsetsAnimationController = this.mAnimationController;
            if (this.mContentView == null) {
                this.mDecorView = appBarLayout.getRootView();
                this.mContentView = this.mDecorView.findViewById(16908290);
            }
            if (windowInsetsAnimationController == null) {
                CancellationSignal cancellationSignal = this.mCancellationSignal;
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    return;
                }
                return;
            }
            int i = windowInsetsAnimationController.getCurrentInsets().bottom;
            int i2 = windowInsetsAnimationController.getShownStateInsets().bottom;
            int i3 = windowInsetsAnimationController.getHiddenStateInsets().bottom;
            if (i == i2) {
                windowInsetsAnimationController.finish(true);
            } else if (i == i3) {
                windowInsetsAnimationController.finish(false);
            }
        }
    }

    private void startAnimationControlRequest() {
        if (this.mDecorView != null && this.mAnimationController == null) {
            if (this.mCancellationSignal == null) {
                this.mCancellationSignal = new CancellationSignal();
            }
            if (this.mWindowInsetsController == null) {
                this.mWindowInsetsController = this.mDecorView.getWindowInsetsController();
            }
            this.mWindowInsetsController.hide(WindowInsets.Type.systemBars());
            this.mWindowInsetsController.setSystemBarsBehavior(2);
            this.mWindowInsetsController.controlWindowInsetsAnimation(WindowInsets.Type.systemBars(), -1, (Interpolator) null, this.mCancellationSignal, this.mWindowInsetsAnimationControlListener);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetWindowInsetsAnimationController() {
        this.mAnimationController = null;
        this.mCancellationSignal = null;
        this.mShownAtDown = false;
        this.mPendingRequestOnReady = null;
    }

    private boolean isMouseEvent(MotionEvent motionEvent) {
        return motionEvent.getToolType(0) == 3;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, MotionEvent motionEvent) {
        boolean isMouseEvent = isMouseEvent(motionEvent);
        if (this.mToolIsMouse != isMouseEvent) {
            this.mToolIsMouse = isMouseEvent;
            appBarLayout.seslSetIsMouse(isMouseEvent);
        }
        if (motionEvent.getAction() == 0 && dispatchImmersiveScrollEnable() && this.mAnimationController == null) {
            startAnimationControlRequest();
        }
        return super.onInterceptTouchEvent(coordinatorLayout, (View) appBarLayout, motionEvent);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        boolean isMouseEvent = isMouseEvent(motionEvent);
        if (this.mToolIsMouse != isMouseEvent) {
            this.mToolIsMouse = isMouseEvent;
            AppBarLayout appBarLayout = this.mAppBarLayout;
            if (appBarLayout != null) {
                appBarLayout.seslSetIsMouse(isMouseEvent);
                dispatchImmersiveScrollEnable();
            }
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    /* access modifiers changed from: package-private */
    public boolean isAppBarHide() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        return appBarLayout != null && ((float) (appBarLayout.getBottom() + this.mAppBarLayout.getPaddingBottom())) < this.mAppBarLayout.seslGetCollapsedHeight();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean startRestoreAnimation() {
        if (!isAppBarHide()) {
            return false;
        }
        animateRestoreTopAndBottom(this.mCoordinatorLayout, this.mAppBarLayout, -this.mAppBarLayout.getUpNestedPreScrollRange());
        return true;
    }

    /* access modifiers changed from: package-private */
    public void seslRestoreTopAndBottom() {
        seslRestoreTopAndBottom(true);
    }

    /* access modifiers changed from: package-private */
    public void seslRestoreTopAndBottom(boolean z) {
        String str = TAG;
        Log.i(str, " Restore top and bottom areas [Animate] " + z);
        this.mNeedRestoreAnim = z;
        restoreTopAndBottomInternal();
    }

    /* access modifiers changed from: package-private */
    public void seslSetBottomView(View view) {
        this.mBottomArea = view;
    }

    private void restoreTopAndBottomInternal() {
        if (this.mAppBarLayout != null && isAppBarHide()) {
            if (this.mAnimationHandler.hasMessages(100)) {
                this.mAnimationHandler.removeMessages(100);
            }
            this.mAnimationHandler.sendEmptyMessageDelayed(100, 100);
        }
    }

    private void animateRestoreTopAndBottom(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
        animateOffsetWithDuration(coordinatorLayout, appBarLayout, i);
    }

    private void animateOffsetWithDuration(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int i) {
        this.mPrevOffset = i;
        PathInterpolator pathInterpolator = new PathInterpolator(0.17f, 0.17f, 0.2f, 1.0f);
        float seslGetCollapsedHeight = this.mAppBarLayout.seslGetCollapsedHeight();
        float f = ((float) (-this.mAppBarLayout.getHeight())) + seslGetCollapsedHeight;
        if (this.mIsMultiWindow || isLandscape()) {
            if (this.mHeightProportion == 0.0f) {
                f = 0.0f;
            } else {
                f = ((float) (-this.mAppBarLayout.getHeight())) + seslGetCollapsedHeight;
            }
        }
        final int[] iArr = {0};
        ValueAnimator valueAnimator = this.mOffsetAnimator;
        if (valueAnimator == null) {
            this.mOffsetAnimator = new ValueAnimator();
            this.mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass6 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (SeslImmersiveScrollBehavior.this.mTargetView == null) {
                        Log.e(SeslImmersiveScrollBehavior.TAG, "mTargetView is null");
                        return;
                    }
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    iArr[0] = SeslImmersiveScrollBehavior.this.mPrevOffset - intValue;
                    SeslImmersiveScrollBehavior.this.mTargetView.scrollBy(0, -iArr[0]);
                    SeslImmersiveScrollBehavior.this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, intValue);
                    SeslImmersiveScrollBehavior.this.mPrevOffset = intValue;
                }
            });
        } else {
            valueAnimator.cancel();
        }
        this.mOffsetAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass7 */

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (SeslImmersiveScrollBehavior.this.mNavigationBarBg != null) {
                    SeslImmersiveScrollBehavior.this.mNavigationBarBg.setTranslationY(0.0f);
                }
                if (SeslImmersiveScrollBehavior.this.mAnimationController != null) {
                    SeslImmersiveScrollBehavior.this.mAnimationController.finish(true);
                }
            }
        });
        this.mOffsetAnimator.setDuration(150L);
        this.mOffsetAnimator.setInterpolator(pathInterpolator);
        this.mOffsetAnimator.setStartDelay(0);
        ValueAnimator valueAnimator2 = this.mOffsetAnimator;
        int[] iArr2 = new int[2];
        iArr2[0] = this.mNeedRestoreAnim ? -this.mAppBarLayout.getHeight() : (int) f;
        iArr2[1] = (int) f;
        valueAnimator2.setIntValues(iArr2);
        this.mOffsetAnimator.start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void forceHideRoundedCorner(int i) {
        WindowInsetsAnimationController windowInsetsAnimationController = this.mAnimationController;
        if (windowInsetsAnimationController != null && this.mDecorView != null) {
            boolean z = i != windowInsetsAnimationController.getShownStateInsets().bottom;
            if (z != this.isRoundedCornerHide) {
                this.isRoundedCornerHide = z;
                SeslDecorViewReflector.semSetForceHideRoundedCorner(this.mDecorView, z);
            }
        }
    }
}
