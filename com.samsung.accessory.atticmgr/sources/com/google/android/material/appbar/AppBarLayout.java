package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsAnimation;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.util.SeslMisc;
import androidx.coordinatorlayout.widget.AppBarLayoutBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(Behavior.class)
public class AppBarLayout extends LinearLayout implements AppBarLayoutBehavior {
    private static final float DEFAULT_HEIGHT_RATIO_TO_SCREEN = 0.39f;
    private static final int INVALID_SCROLL_RANGE = -1;
    static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    static final int PENDING_ACTION_COLLAPSED = 2;
    static final int PENDING_ACTION_COLLAPSED_IMM = 512;
    static final int PENDING_ACTION_EXPANDED = 1;
    static final int PENDING_ACTION_FORCE = 8;
    static final int PENDING_ACTION_NONE = 0;
    public static final int SESL_STATE_COLLAPSED = 0;
    public static final int SESL_STATE_EXPANDED = 1;
    public static final int SESL_STATE_HIDE = 2;
    public static final int SESL_STATE_IDLE = 3;
    private static final String TAG = AppBarLayout.class.getSimpleName();
    private static int sSeslTCScrollRange = 0;
    private int currentOffset;
    private int downPreScrollRange;
    private int downScrollRange;
    private ValueAnimator elevationOverlayAnimator;
    private boolean haveChildWithInterpolator;
    private boolean isMouse;
    private WindowInsetsCompat lastInsets;
    private boolean liftOnScroll;
    private WeakReference<View> liftOnScrollTargetView;
    private int liftOnScrollTargetViewId;
    private boolean liftable;
    private boolean liftableOverride;
    protected boolean lifted;
    private List<BaseOnOffsetChangedListener> listeners;
    private SeslAppbarState mAppbarState;
    private Drawable mBackground;
    private int mBottomPadding;
    private float mCollapsedHeight;
    private int mCurrentOrientation;
    private int mCurrentScreenHeight;
    private int mCustomHeight;
    private float mCustomHeightProportion;
    private float mHeightProportion;
    private boolean mImmHideStatusBar;
    private List<SeslBaseOnImmOffsetChangedListener> mImmOffsetListener;
    private int mImmersiveTopInset;
    private boolean mIsCanScroll;
    private boolean mIsImmersiveScroll;
    private boolean mIsSetByUser;
    private Resources mResources;
    private boolean mRestoreAnim;
    private boolean mSetCustomHeight;
    private boolean mSetCustomProportion;
    private boolean mUseCollapsedHeight;
    private boolean mUseCustomHeight;
    private boolean mUseCustomPadding;
    private int pendingAction;
    private Drawable statusBarForeground;
    private int[] tmpStatesArray;
    private int totalScrollRange;

    public interface BaseOnOffsetChangedListener<T extends AppBarLayout> {
        void onOffsetChanged(T t, int i);
    }

    public interface OnOffsetChangedListener extends BaseOnOffsetChangedListener<AppBarLayout> {
        @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    public interface SeslBaseOnImmOffsetChangedListener<T extends AppBarLayout> {
        void onOffsetChanged(T t, int i);
    }

    public interface SeslOnImmOffsetChangedListener extends SeslBaseOnImmOffsetChangedListener<AppBarLayout> {
        @Override // com.google.android.material.appbar.AppBarLayout.SeslBaseOnImmOffsetChangedListener
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    public static class SeslAppbarState {
        private int mCurrentState = 3;

        SeslAppbarState() {
        }

        /* access modifiers changed from: package-private */
        public void onStateChanged(int i) {
            this.mCurrentState = i;
        }

        public int getState() {
            return this.mCurrentState;
        }
    }

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.appBarLayoutStyle);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        this.pendingAction = 0;
        this.lifted = false;
        this.mCustomHeight = -1;
        this.mBottomPadding = 0;
        this.mUseCollapsedHeight = false;
        this.isMouse = false;
        this.mIsImmersiveScroll = false;
        this.mIsSetByUser = false;
        this.mIsCanScroll = false;
        this.mRestoreAnim = false;
        this.mImmHideStatusBar = false;
        this.mImmersiveTopInset = 0;
        setOrientation(1);
        if (Build.VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setStateListAnimatorFromAttrs(this, attributeSet, i, R.style.Widget_Design_AppBarLayout);
        }
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.AppBarLayout, i, R.style.Widget_Design_AppBarLayout, new int[0]);
        this.mAppbarState = new SeslAppbarState();
        this.mResources = getResources();
        boolean isLightTheme = SeslMisc.isLightTheme(context);
        if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_background)) {
            this.mBackground = obtainStyledAttributes.getDrawable(R.styleable.AppBarLayout_android_background);
            ViewCompat.setBackground(this, this.mBackground);
        } else {
            this.mBackground = null;
            setBackgroundColor(this.mResources.getColor(isLightTheme ? R.color.sesl_action_bar_background_color_light : R.color.sesl_action_bar_background_color_dark));
        }
        if (getBackground() instanceof ColorDrawable) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) getBackground()).getColor()));
            materialShapeDrawable.initializeElevationOverlay(context);
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_expanded)) {
            setExpanded(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
        }
        if (Build.VERSION.SDK_INT >= 21 && obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_elevation)) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_seslUseCustomHeight)) {
            this.mUseCustomHeight = obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_seslUseCustomHeight, false);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_seslHeightProportion)) {
            this.mSetCustomProportion = true;
            this.mCustomHeightProportion = obtainStyledAttributes.getFloat(R.styleable.AppBarLayout_seslHeightProportion, DEFAULT_HEIGHT_RATIO_TO_SCREEN);
        } else {
            this.mSetCustomProportion = false;
            this.mCustomHeightProportion = DEFAULT_HEIGHT_RATIO_TO_SCREEN;
        }
        this.mHeightProportion = ResourcesCompat.getFloat(this.mResources, R.dimen.sesl_appbar_height_proportion);
        if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_seslUseCustomPadding)) {
            this.mUseCustomPadding = obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_seslUseCustomPadding, false);
        }
        if (this.mUseCustomPadding) {
            this.mBottomPadding = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AppBarLayout_android_paddingBottom, 0);
            setPadding(0, 0, 0, this.mBottomPadding);
            this.mCollapsedHeight = (float) (this.mResources.getDimensionPixelSize(R.dimen.sesl_action_bar_default_height) + this.mBottomPadding);
        } else {
            this.mBottomPadding = this.mResources.getDimensionPixelOffset(R.dimen.sesl_extended_appbar_bottom_padding);
            setPadding(0, 0, 0, this.mBottomPadding);
            this.mCollapsedHeight = (float) this.mResources.getDimensionPixelSize(this.mBottomPadding > 0 ? R.dimen.sesl_action_bar_height_with_padding : R.dimen.sesl_action_bar_default_height);
        }
        seslSetCollapsedHeight(this.mCollapsedHeight, false);
        if (Build.VERSION.SDK_INT >= 21 && obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_elevation)) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                setKeyboardNavigationCluster(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                setTouchscreenBlocksFocus(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        this.liftOnScroll = obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
        this.liftOnScrollTargetViewId = obtainStyledAttributes.getResourceId(R.styleable.AppBarLayout_liftOnScrollTargetViewId, -1);
        setStatusBarForeground(obtainStyledAttributes.getDrawable(R.styleable.AppBarLayout_statusBarForeground));
        obtainStyledAttributes.recycle();
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
            /* class com.google.android.material.appbar.AppBarLayout.AnonymousClass1 */

            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AppBarLayout.this.onWindowInsetChanged(windowInsetsCompat);
            }
        });
        this.mCurrentOrientation = this.mResources.getConfiguration().orientation;
        this.mCurrentScreenHeight = this.mResources.getConfiguration().screenHeightDp;
    }

    /* access modifiers changed from: package-private */
    public void updateInternalCollapsedHeight() {
        if (useCollapsedHeight()) {
            return;
        }
        if (getImmBehavior() == null || !getCanScroll()) {
            float seslGetCollapsedHeight = seslGetCollapsedHeight();
            float height = (float) (getHeight() - getTotalScrollRange());
            if (height != seslGetCollapsedHeight && height > 0.0f) {
                String str = TAG;
                Log.i(str, "Internal collapsedHeight/ oldCollapsedHeight :" + seslGetCollapsedHeight + " newCollapsedHeight :" + height);
                seslSetCollapsedHeight(height, false);
                updateInternalHeight();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateInternalCollapsedHeightOnce() {
        if (useCollapsedHeight()) {
            return;
        }
        if (getImmBehavior() == null || !getCanScroll()) {
            float seslGetCollapsedHeight = seslGetCollapsedHeight();
            String str = TAG;
            Log.i(str, "update InternalCollapsedHeight from updateInternalHeight() : " + seslGetCollapsedHeight);
            seslSetCollapsedHeight(seslGetCollapsedHeight, false);
        }
    }

    public SeslAppbarState seslGetAppBarState() {
        return this.mAppbarState;
    }

    public void seslSetCustomHeightProportion(boolean z, float f) {
        if (f > 1.0f) {
            Log.e(TAG, "Height proportion float range is 0..1");
            return;
        }
        this.mUseCustomHeight = z;
        this.mSetCustomProportion = z;
        this.mSetCustomHeight = false;
        this.mCustomHeightProportion = f;
        updateInternalHeight();
        requestLayout();
    }

    public void seslSetCustomHeight(int i) {
        CoordinatorLayout.LayoutParams layoutParams;
        this.mCustomHeight = i;
        this.mUseCustomHeight = true;
        this.mSetCustomHeight = true;
        this.mSetCustomProportion = false;
        try {
            layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
        } catch (ClassCastException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            layoutParams = null;
        }
        if (layoutParams != null) {
            layoutParams.height = i;
            setLayoutParams(layoutParams);
        }
    }

    public void addOnOffsetChangedListener(BaseOnOffsetChangedListener baseOnOffsetChangedListener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        if (baseOnOffsetChangedListener != null && !this.listeners.contains(baseOnOffsetChangedListener)) {
            this.listeners.add(baseOnOffsetChangedListener);
        }
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        addOnOffsetChangedListener((BaseOnOffsetChangedListener) onOffsetChangedListener);
    }

    public void seslAddOnImmOffsetChangedListener(SeslBaseOnImmOffsetChangedListener seslBaseOnImmOffsetChangedListener) {
        if (this.mImmOffsetListener == null) {
            this.mImmOffsetListener = new ArrayList();
        }
        if (seslBaseOnImmOffsetChangedListener != null && !this.mImmOffsetListener.contains(seslBaseOnImmOffsetChangedListener)) {
            this.mImmOffsetListener.add(seslBaseOnImmOffsetChangedListener);
        }
    }

    public void seslAddOnImmOffsetChangedListener(SeslOnImmOffsetChangedListener seslOnImmOffsetChangedListener) {
        seslAddOnImmOffsetChangedListener((SeslBaseOnImmOffsetChangedListener) seslOnImmOffsetChangedListener);
    }

    public void removeOnOffsetChangedListener(BaseOnOffsetChangedListener baseOnOffsetChangedListener) {
        List<BaseOnOffsetChangedListener> list = this.listeners;
        if (list != null && baseOnOffsetChangedListener != null) {
            list.remove(baseOnOffsetChangedListener);
        }
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        removeOnOffsetChangedListener((BaseOnOffsetChangedListener) onOffsetChangedListener);
    }

    public void seslRemoveOnImmOffsetChangedListener(SeslBaseOnImmOffsetChangedListener seslBaseOnImmOffsetChangedListener) {
        List<SeslBaseOnImmOffsetChangedListener> list = this.mImmOffsetListener;
        if (list != null && seslBaseOnImmOffsetChangedListener != null) {
            list.remove(seslBaseOnImmOffsetChangedListener);
        }
    }

    public void seslRemoveOnImmOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        seslRemoveOnImmOffsetChangedListener((SeslBaseOnImmOffsetChangedListener) onOffsetChangedListener);
    }

    public void setStatusBarForeground(Drawable drawable) {
        Drawable drawable2 = this.statusBarForeground;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.statusBarForeground = drawable3;
            Drawable drawable4 = this.statusBarForeground;
            if (drawable4 != null) {
                if (drawable4.isStateful()) {
                    this.statusBarForeground.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.statusBarForeground, ViewCompat.getLayoutDirection(this));
                this.statusBarForeground.setVisible(getVisibility() == 0, false);
                this.statusBarForeground.setCallback(this);
            }
            updateWillNotDraw();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setStatusBarForegroundColor(int i) {
        setStatusBarForeground(new ColorDrawable(i));
    }

    public void setStatusBarForegroundResource(int i) {
        setStatusBarForeground(AppCompatResources.getDrawable(getContext(), i));
    }

    public Drawable getStatusBarForeground() {
        return this.statusBarForeground;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (shouldDrawStatusBarForeground()) {
            int save = canvas.save();
            canvas.translate(0.0f, (float) (-this.currentOffset));
            this.statusBarForeground.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.statusBarForeground;
        if (drawable != null && drawable.isStateful() && drawable.setState(drawableState)) {
            invalidateDrawable(drawable);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.statusBarForeground;
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        updateInternalHeight();
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824 && ViewCompat.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
            int measuredHeight = getMeasuredHeight();
            if (mode == Integer.MIN_VALUE) {
                measuredHeight = MathUtils.clamp(getMeasuredHeight() + getTopInset(), 0, View.MeasureSpec.getSize(i2));
            } else if (mode == 0) {
                measuredHeight += getTopInset();
            }
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        }
        invalidateScrollRanges();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (ViewCompat.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
            int topInset = getTopInset();
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                ViewCompat.offsetTopAndBottom(getChildAt(childCount), topInset);
            }
        }
        invalidateScrollRanges();
        boolean z2 = false;
        this.haveChildWithInterpolator = false;
        int childCount2 = getChildCount();
        int i5 = 0;
        while (true) {
            if (i5 >= childCount2) {
                break;
            } else if (((LayoutParams) getChildAt(i5).getLayoutParams()).getScrollInterpolator() != null) {
                this.haveChildWithInterpolator = true;
                break;
            } else {
                i5++;
            }
        }
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getTopInset());
        }
        if (!this.liftableOverride) {
            if (this.liftOnScroll || hasCollapsibleChild()) {
                z2 = true;
            }
            setLiftableState(z2);
        }
    }

    private void updateWillNotDraw() {
        setWillNotDraw(!shouldDrawStatusBarForeground());
    }

    private boolean shouldDrawStatusBarForeground() {
        return this.statusBarForeground != null && getTopInset() > 0;
    }

    private boolean hasCollapsibleChild() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((LayoutParams) getChildAt(i).getLayoutParams()).isCollapsible()) {
                return true;
            }
        }
        return false;
    }

    private void invalidateScrollRanges() {
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
    }

    public void setOrientation(int i) {
        if (i == 1) {
            super.setOrientation(i);
            return;
        }
        throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    public void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    public void setExpanded(boolean z) {
        setExpanded(z, ViewCompat.isLaidOut(this));
    }

    public void setExpanded(boolean z, boolean z2) {
        setExpanded(z, z2, true);
    }

    private void setExpanded(boolean z, boolean z2, boolean z3) {
        int i;
        setLifted(!z);
        if (z) {
            i = 1;
        } else {
            i = seslGetImmersiveScroll() ? 512 : 2;
        }
        int i2 = 0;
        int i3 = i | (z2 ? 4 : 0);
        if (z3) {
            i2 = 8;
        }
        this.pendingAction = i3 | i2;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.widget.LinearLayout
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    @Override // android.widget.LinearLayout, android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT >= 19 && (layoutParams instanceof LinearLayout.LayoutParams)) {
            return new LayoutParams((LinearLayout.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearLiftOnScrollTargetView();
    }

    /* access modifiers changed from: package-private */
    public boolean hasChildWithInterpolator() {
        return this.haveChildWithInterpolator;
    }

    public final int getTotalScrollRange() {
        int i = this.totalScrollRange;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i4 = layoutParams.scrollFlags;
            if ((i4 & 1) == 0) {
                break;
            }
            i3 += measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin;
            if (i2 == 0 && ViewCompat.getFitsSystemWindows(childAt)) {
                i3 -= getTopInset();
            }
            if ((i4 & 2) == 0) {
                i2++;
            } else if (getCanScroll()) {
                i3 += getTopInset() + this.mBottomPadding + seslGetTCScrollRange();
            } else {
                i3 -= ViewCompat.getMinimumHeight(childAt);
            }
        }
        int max = Math.max(0, i3);
        this.totalScrollRange = max;
        return max;
    }

    /* access modifiers changed from: package-private */
    public boolean hasScrollableChildren() {
        return getTotalScrollRange() != 0;
    }

    /* access modifiers changed from: package-private */
    public int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    /* access modifiers changed from: package-private */
    public int getDownNestedPreScrollRange() {
        int i;
        int minimumHeight;
        int i2 = this.downPreScrollRange;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount() - 1;
        int i3 = 0;
        while (true) {
            if (childCount < 0) {
                break;
            }
            View childAt = getChildAt(childCount);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i4 = layoutParams.scrollFlags;
            if ((i4 & 5) == 5) {
                int i5 = layoutParams.topMargin + layoutParams.bottomMargin;
                if ((i4 & 8) != 0) {
                    minimumHeight = ViewCompat.getMinimumHeight(childAt);
                } else if ((i4 & 2) != 0) {
                    minimumHeight = measuredHeight - ViewCompat.getMinimumHeight(childAt);
                } else {
                    i = i5 + measuredHeight;
                    if (childCount == 0 && ViewCompat.getFitsSystemWindows(childAt)) {
                        i = Math.min(i, measuredHeight - getTopInset());
                    }
                    i3 += i;
                    childCount--;
                }
                i = i5 + minimumHeight;
                i = Math.min(i, measuredHeight - getTopInset());
                i3 += i;
                childCount--;
            } else if (getCanScroll()) {
                i3 = (int) (((float) i3) + seslGetCollapsedHeight() + ((float) seslGetTCScrollRange()));
            }
        }
        int max = Math.max(0, i3);
        this.downPreScrollRange = max;
        return max;
    }

    /* access modifiers changed from: package-private */
    public int getDownNestedScrollRange() {
        int i = this.downScrollRange;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int i4 = layoutParams.scrollFlags;
            if ((i4 & 1) == 0) {
                break;
            }
            i3 += measuredHeight;
            if ((i4 & 2) != 0) {
                i3 -= ViewCompat.getMinimumHeight(childAt);
                break;
            }
            i2++;
        }
        int max = Math.max(0, i3);
        this.downScrollRange = max;
        return max;
    }

    /* access modifiers changed from: package-private */
    public void onOffsetChanged(int i) {
        this.currentOffset = i;
        int totalScrollRange2 = getTotalScrollRange();
        int height = getHeight() - ((int) seslGetCollapsedHeight());
        if (Math.abs(i) >= totalScrollRange2) {
            if (getCanScroll()) {
                if (this.mAppbarState.getState() != 2) {
                    this.mAppbarState.onStateChanged(2);
                }
            } else if (this.mAppbarState.getState() != 0) {
                this.mAppbarState.onStateChanged(0);
            }
        } else if (Math.abs(i) >= height) {
            if (this.mAppbarState.getState() != 0) {
                this.mAppbarState.onStateChanged(0);
            }
        } else if (Math.abs(i) == 0) {
            if (this.mAppbarState.getState() != 1) {
                this.mAppbarState.onStateChanged(1);
            }
        } else if (this.mAppbarState.getState() != 3) {
            this.mAppbarState.onStateChanged(3);
        }
        if (!willNotDraw()) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        List<BaseOnOffsetChangedListener> list = this.listeners;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                BaseOnOffsetChangedListener baseOnOffsetChangedListener = this.listeners.get(i2);
                if (baseOnOffsetChangedListener != null) {
                    baseOnOffsetChangedListener.onOffsetChanged(this, i);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onImmOffsetChanged(int i) {
        if (!willNotDraw()) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        List<SeslBaseOnImmOffsetChangedListener> list = this.mImmOffsetListener;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                SeslBaseOnImmOffsetChangedListener seslBaseOnImmOffsetChangedListener = this.mImmOffsetListener.get(i2);
                if (seslBaseOnImmOffsetChangedListener != null) {
                    seslBaseOnImmOffsetChangedListener.onOffsetChanged(this, i);
                }
            }
        }
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        if (minimumHeight == 0) {
            int childCount = getChildCount();
            minimumHeight = childCount >= 1 ? ViewCompat.getMinimumHeight(getChildAt(childCount - 1)) : 0;
            if (minimumHeight == 0) {
                return getHeight() / 3;
            }
        }
        return (minimumHeight * 2) + topInset;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }
        int[] iArr = this.tmpStatesArray;
        int[] onCreateDrawableState = super.onCreateDrawableState(i + iArr.length);
        iArr[0] = this.liftable ? R.attr.state_liftable : -R.attr.state_liftable;
        iArr[1] = (!this.liftable || !this.lifted) ? -R.attr.state_lifted : R.attr.state_lifted;
        iArr[2] = this.liftable ? R.attr.state_collapsible : -R.attr.state_collapsible;
        iArr[3] = (!this.liftable || !this.lifted) ? -R.attr.state_collapsed : R.attr.state_collapsed;
        return mergeDrawableStates(onCreateDrawableState, iArr);
    }

    public boolean setLiftable(boolean z) {
        this.liftableOverride = true;
        return setLiftableState(z);
    }

    private boolean setLiftableState(boolean z) {
        if (this.liftable == z) {
            return false;
        }
        this.liftable = z;
        refreshDrawableState();
        return true;
    }

    public boolean setLifted(boolean z) {
        return setLiftedState(z);
    }

    /* access modifiers changed from: package-private */
    public boolean setLiftedState(boolean z) {
        if (this.lifted == z) {
            return false;
        }
        this.lifted = z;
        refreshDrawableState();
        if (!this.liftOnScroll || !(getBackground() instanceof MaterialShapeDrawable)) {
            return true;
        }
        startLiftOnScrollElevationOverlayAnimation((MaterialShapeDrawable) getBackground(), z);
        return true;
    }

    private void startLiftOnScrollElevationOverlayAnimation(final MaterialShapeDrawable materialShapeDrawable, boolean z) {
        float dimension = this.mResources.getDimension(R.dimen.sesl_appbar_elevation);
        float f = z ? 0.0f : dimension;
        if (!z) {
            dimension = 0.0f;
        }
        ValueAnimator valueAnimator = this.elevationOverlayAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.elevationOverlayAnimator = ValueAnimator.ofFloat(f, dimension);
        this.elevationOverlayAnimator.setDuration((long) this.mResources.getInteger(R.integer.app_bar_elevation_anim_duration));
        this.elevationOverlayAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.elevationOverlayAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.google.android.material.appbar.AppBarLayout.AnonymousClass2 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                materialShapeDrawable.setElevation(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.elevationOverlayAnimator.start();
    }

    public void setLiftOnScroll(boolean z) {
        this.liftOnScroll = z;
    }

    public boolean isLiftOnScroll() {
        return this.liftOnScroll;
    }

    public void setLiftOnScrollTargetViewId(int i) {
        this.liftOnScrollTargetViewId = i;
        clearLiftOnScrollTargetView();
    }

    public int getLiftOnScrollTargetViewId() {
        return this.liftOnScrollTargetViewId;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldLift(View view) {
        View findLiftOnScrollTargetView = findLiftOnScrollTargetView(view);
        if (findLiftOnScrollTargetView != null) {
            view = findLiftOnScrollTargetView;
        }
        return view != null && (view.canScrollVertically(-1) || view.getScrollY() > 0);
    }

    private View findLiftOnScrollTargetView(View view) {
        int i;
        if (this.liftOnScrollTargetView == null && (i = this.liftOnScrollTargetViewId) != -1) {
            View findViewById = view != null ? view.findViewById(i) : null;
            if (findViewById == null && (getParent() instanceof ViewGroup)) {
                findViewById = ((ViewGroup) getParent()).findViewById(this.liftOnScrollTargetViewId);
            }
            if (findViewById != null) {
                this.liftOnScrollTargetView = new WeakReference<>(findViewById);
            }
        }
        WeakReference<View> weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private void clearLiftOnScrollTargetView() {
        WeakReference<View> weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.liftOnScrollTargetView = null;
    }

    @Deprecated
    public void setTargetElevation(float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, f);
        }
    }

    /* access modifiers changed from: package-private */
    public int getPendingAction() {
        return this.pendingAction;
    }

    /* access modifiers changed from: package-private */
    public void resetPendingAction() {
        this.pendingAction = 0;
    }

    /* access modifiers changed from: package-private */
    public final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        return 0;
    }

    private boolean shouldOffsetFirstChild() {
        if (getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        if (childAt.getVisibility() == 8 || ViewCompat.getFitsSystemWindows(childAt)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows(this) ? windowInsetsCompat : null;
        if (!ObjectsCompat.equals(this.lastInsets, windowInsetsCompat2)) {
            this.lastInsets = windowInsetsCompat2;
            updateWillNotDraw();
            requestLayout();
        }
        return windowInsetsCompat;
    }

    /* access modifiers changed from: protected */
    public int getCurrentOrientation() {
        return this.mCurrentOrientation;
    }

    @Deprecated
    public void seslRestoreTopAndBottom(View view) {
        seslRestoreTopAndBottom();
    }

    private SeslImmersiveScrollBehavior getImmBehavior() {
        if (Build.VERSION.SDK_INT < 30) {
            return null;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
            if (behavior instanceof SeslImmersiveScrollBehavior) {
                return (SeslImmersiveScrollBehavior) behavior;
            }
        }
        return null;
    }

    public void seslSetWindowInsetsAnimationCallback(Object obj) {
        SeslImmersiveScrollBehavior immBehavior;
        if (Build.VERSION.SDK_INT >= 30 && (immBehavior = getImmBehavior()) != null) {
            if (obj == null) {
                immBehavior.setWindowInsetsAnimationCallback(this, null);
            }
            if (obj instanceof WindowInsetsAnimation.Callback) {
                immBehavior.setWindowInsetsAnimationCallback(this, (WindowInsetsAnimation.Callback) obj);
            }
        }
    }

    public void seslRestoreTopAndBottom() {
        SeslImmersiveScrollBehavior immBehavior = getImmBehavior();
        if (immBehavior != null) {
            immBehavior.seslRestoreTopAndBottom();
        }
    }

    public void seslRestoreTopAndBottom(boolean z) {
        SeslImmersiveScrollBehavior immBehavior = getImmBehavior();
        if (immBehavior != null) {
            immBehavior.seslRestoreTopAndBottom(z);
        }
    }

    public void seslImmHideStatusBarForLandscape(boolean z) {
        this.mImmHideStatusBar = z;
    }

    /* access modifiers changed from: package-private */
    public boolean getImmHideStatusBarForLandscape() {
        return this.mImmHideStatusBar;
    }

    @Deprecated
    public void seslSetBottomView(View view, View view2) {
        seslSetBottomView(view2);
    }

    public void seslSetBottomView(View view) {
        if (view == null) {
            Log.w(TAG, "bottomView is null");
        }
        SeslImmersiveScrollBehavior immBehavior = getImmBehavior();
        if (immBehavior != null) {
            immBehavior.seslSetBottomView(view);
        }
    }

    /* access modifiers changed from: protected */
    public void setImmersiveScroll(boolean z, boolean z2) {
        this.mIsImmersiveScroll = z;
        this.mIsSetByUser = z2;
        SeslImmersiveScrollBehavior immBehavior = getImmBehavior();
        if (immBehavior == null) {
            return;
        }
        if (!z || immBehavior.isAppBarHide()) {
            immBehavior.seslRestoreTopAndBottom(this.mRestoreAnim);
        }
    }

    public void seslSetImmersiveScroll(boolean z, boolean z2) {
        this.mRestoreAnim = z2;
        if (Build.VERSION.SDK_INT >= 30) {
            SeslImmersiveScrollBehavior immBehavior = getImmBehavior();
            if (immBehavior != null ? immBehavior.dispatchImmersiveScrollEnable() : true) {
                setCanScroll(z);
            }
            setImmersiveScroll(z, true);
        }
    }

    public void seslSetImmersiveScroll(boolean z) {
        seslSetImmersiveScroll(z, true);
    }

    public boolean seslGetImmersiveScroll() {
        return this.mIsImmersiveScroll;
    }

    /* access modifiers changed from: protected */
    public boolean getImmersiveByUser() {
        return this.mIsSetByUser;
    }

    /* access modifiers changed from: protected */
    public void setCanScroll(boolean z) {
        if (this.mIsCanScroll != z) {
            this.mIsCanScroll = z;
            invalidateScrollRanges();
            requestLayout();
        }
    }

    public void seslSetTCScrollRange(int i) {
        sSeslTCScrollRange = i;
    }

    static int seslGetTCScrollRange() {
        return sSeslTCScrollRange;
    }

    /* access modifiers changed from: protected */
    public boolean getCanScroll() {
        return this.mIsCanScroll;
    }

    public void seslSetCollapsedHeight(float f) {
        String str = TAG;
        Log.i(str, "seslSetCollapsedHeight, height : " + f);
        seslSetCollapsedHeight(f, true);
    }

    private void seslSetCollapsedHeight(float f, boolean z) {
        this.mUseCollapsedHeight = z;
        this.mCollapsedHeight = f;
    }

    /* access modifiers changed from: package-private */
    public void internalProportion(float f) {
        if (!this.mUseCustomHeight && this.mHeightProportion != f) {
            this.mHeightProportion = f;
            updateInternalHeight();
        }
    }

    /* access modifiers changed from: package-private */
    public void setImmersiveTopInset(int i) {
        this.mImmersiveTopInset = i;
    }

    /* access modifiers changed from: package-private */
    public final int getImmersiveTopInset() {
        if (this.mIsCanScroll) {
            return this.mImmersiveTopInset;
        }
        return 0;
    }

    public float seslGetCollapsedHeight() {
        return this.mCollapsedHeight + ((float) getImmersiveTopInset());
    }

    /* access modifiers changed from: package-private */
    public boolean useCollapsedHeight() {
        return this.mUseCollapsedHeight;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            if (drawable == getBackground()) {
                setBackgroundDrawable(this.mBackground);
            } else {
                setBackgroundDrawable(getBackground());
            }
        } else if (getBackground() != null) {
            this.mBackground = getBackground();
            setBackgroundDrawable(this.mBackground);
        } else {
            this.mBackground = null;
            setBackgroundColor(this.mResources.getColor(SeslMisc.isLightTheme(getContext()) ? R.color.sesl_action_bar_background_color_light : R.color.sesl_action_bar_background_color_dark));
        }
        if (!(this.mCurrentScreenHeight == configuration.screenHeightDp && this.mCurrentOrientation == configuration.orientation) && !this.mUseCustomPadding && !this.mUseCollapsedHeight) {
            Log.i(TAG, "Update bottom padding");
            this.mBottomPadding = this.mResources.getDimensionPixelSize(R.dimen.sesl_extended_appbar_bottom_padding);
            setPadding(0, 0, 0, this.mBottomPadding);
            this.mCollapsedHeight = (float) this.mResources.getDimensionPixelSize(this.mBottomPadding > 0 ? R.dimen.sesl_action_bar_height_with_padding : R.dimen.sesl_action_bar_default_height);
            seslSetCollapsedHeight(this.mCollapsedHeight, false);
        }
        if (!this.mSetCustomProportion) {
            this.mHeightProportion = ResourcesCompat.getFloat(this.mResources, R.dimen.sesl_appbar_height_proportion);
        }
        updateInternalHeight();
        if (this.lifted || (this.mCurrentOrientation == 1 && configuration.orientation == 2)) {
            setExpanded(false, false, true);
        } else {
            setExpanded(true, false, true);
        }
        this.mCurrentOrientation = configuration.orientation;
        this.mCurrentScreenHeight = configuration.screenHeightDp;
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 8) {
            if (this.liftOnScrollTargetView != null) {
                if (motionEvent.getAxisValue(9) < 0.0f) {
                    setExpanded(false);
                } else if (motionEvent.getAxisValue(9) > 0.0f && !canScrollVertically(-1)) {
                    setExpanded(true);
                }
            } else if (motionEvent.getAxisValue(9) < 0.0f) {
                setExpanded(false);
            } else if (motionEvent.getAxisValue(9) > 0.0f) {
                setExpanded(true);
            }
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    private void updateInternalHeight() {
        float f;
        CoordinatorLayout.LayoutParams layoutParams;
        int windowHeight = getWindowHeight();
        if (this.mUseCustomHeight) {
            float f2 = this.mCustomHeightProportion;
            if (f2 != 0.0f) {
                f = f2 + (getCanScroll() ? getDifferImmHeightRatio() : 0.0f);
            } else {
                f = 0.0f;
            }
        } else {
            f = this.mHeightProportion;
        }
        float f3 = ((float) windowHeight) * f;
        if (f3 == 0.0f) {
            updateInternalCollapsedHeightOnce();
            f3 = seslGetCollapsedHeight();
        }
        try {
            layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
        } catch (ClassCastException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            layoutParams = null;
        }
        String str = "[updateInternalHeight] orientation : " + this.mResources.getConfiguration().orientation + ", density : " + this.mResources.getConfiguration().densityDpi + ", windowHeight :" + windowHeight;
        if (this.mUseCustomHeight) {
            if (this.mSetCustomProportion) {
                if (layoutParams != null) {
                    layoutParams.height = (int) f3;
                    setLayoutParams(layoutParams);
                    str = str + ", [1]updateInternalHeight: lp.height : " + layoutParams.height + ", mCustomHeightProportion : " + this.mCustomHeightProportion;
                }
            } else if (this.mSetCustomHeight && layoutParams != null) {
                layoutParams.height = this.mCustomHeight + getImmersiveTopInset();
                setLayoutParams(layoutParams);
                str = str + ", [2]updateInternalHeight: CustomHeight :" + this.mCustomHeight + "lp.height : " + layoutParams.height;
            }
        } else if (layoutParams != null) {
            layoutParams.height = (int) f3;
            setLayoutParams(layoutParams);
            str = str + ", [3]updateInternalHeight: lp.height : " + layoutParams.height + ", mHeightProportion : " + this.mHeightProportion;
        }
        Log.i(TAG, str);
    }

    private float getDifferImmHeightRatio() {
        float windowHeight = (float) getWindowHeight();
        float immersiveTopInset = (float) getImmersiveTopInset();
        if (windowHeight == 0.0f) {
            windowHeight = 1.0f;
        }
        return immersiveTopInset / windowHeight;
    }

    private int getWindowHeight() {
        return this.mResources.getDisplayMetrics().heightPixels;
    }

    @Override // androidx.coordinatorlayout.widget.AppBarLayoutBehavior
    public void seslSetExpanded(boolean z) {
        setExpanded(z);
    }

    @Override // androidx.coordinatorlayout.widget.AppBarLayoutBehavior
    public boolean seslIsCollapsed() {
        return this.lifted;
    }

    @Override // androidx.coordinatorlayout.widget.AppBarLayoutBehavior
    public void seslSetIsMouse(boolean z) {
        this.isMouse = z;
    }

    /* access modifiers changed from: protected */
    public boolean getIsMouse() {
        return this.isMouse;
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        static final int COLLAPSIBLE_FLAGS = 10;
        private static final int FLAG_NO_SCROLL_HOLD = 65536;
        private static final int FLAG_NO_SNAP = 4096;
        static final int FLAG_QUICK_RETURN = 5;
        static final int FLAG_SNAP = 17;
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_NO_SCROLL = 0;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
        public static final int SESL_SCROLL_FLAG_NO_SCROLL_HOLD = 65536;
        public static final int SESL_SCROLL_FLAG_NO_SNAP = 4096;
        int scrollFlags = 1;
        Interpolator scrollInterpolator;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ScrollFlags {
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_Layout);
            this.scrollFlags = obtainStyledAttributes.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, obtainStyledAttributes.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams) layoutParams);
            this.scrollFlags = layoutParams.scrollFlags;
            this.scrollInterpolator = layoutParams.scrollInterpolator;
        }

        public void setScrollFlags(int i) {
            this.scrollFlags = i;
        }

        public int getScrollFlags() {
            return this.scrollFlags;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            this.scrollInterpolator = interpolator;
        }

        public Interpolator getScrollInterpolator() {
            return this.scrollInterpolator;
        }

        /* access modifiers changed from: package-private */
        public boolean isCollapsible() {
            int i = this.scrollFlags;
            return (i & 1) == 1 && (i & 10) != 0;
        }
    }

    public static class Behavior extends BaseBehavior<AppBarLayout> {

        public static abstract class DragCallback extends BaseBehavior.BaseDragCallback<AppBarLayout> {
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public /* bridge */ /* synthetic */ int getLastInterceptTouchEventEvent() {
            return super.getLastInterceptTouchEventEvent();
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public /* bridge */ /* synthetic */ int getLastTouchEventEvent() {
            return super.getLastTouchEventEvent();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean isHorizontalOffsetEnabled() {
            return super.isHorizontalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean isVerticalOffsetEnabled() {
            return super.isVerticalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            return super.onLayoutChild(coordinatorLayout, appBarLayout, i);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, appBarLayout, i, i2, i3, i4);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, float f, float f2) {
            return super.onNestedPreFling(coordinatorLayout, appBarLayout, view, f, f2);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, i, i2, iArr, i3);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            super.onNestedScroll(coordinatorLayout, appBarLayout, view, i, i2, i3, i4, i5, iArr);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            super.onRestoreInstanceState(coordinatorLayout, appBarLayout, parcelable);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            return super.onSaveInstanceState(coordinatorLayout, appBarLayout);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
            return super.onStartNestedScroll(coordinatorLayout, appBarLayout, view, view2, i, i2);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
            super.onStopNestedScroll(coordinatorLayout, appBarLayout, view, i);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onTouchEvent(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, MotionEvent motionEvent) {
            return super.onTouchEvent(coordinatorLayout, appBarLayout, motionEvent);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void setDragCallback(BaseBehavior.BaseDragCallback baseDragCallback) {
            super.setDragCallback(baseDragCallback);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ void setHorizontalOffsetEnabled(boolean z) {
            super.setHorizontalOffsetEnabled(z);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ void setVerticalOffsetEnabled(boolean z) {
            super.setVerticalOffsetEnabled(z);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    /* access modifiers changed from: protected */
    public static class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior<T> {
        private static final int INVALID_POSITION = -1;
        private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
        private WeakReference<View> lastNestedScrollingChildRef;
        private int lastStartedType;
        private float mDiffY_Touch;
        private boolean mDirectTouchAppbar = false;
        private boolean mIsFlingScrollDown = false;
        private boolean mIsFlingScrollUp = false;
        private boolean mIsScrollHold = false;
        private boolean mIsSetStaticDuration = false;
        private float mLastMotionY_Touch;
        private boolean mLifted;
        private boolean mToolisMouse;
        private int mTouchSlop = -1;
        private float mVelocity = 0.0f;
        private ValueAnimator offsetAnimator;
        private int offsetDelta;
        private int offsetToChildIndexOnLayout = -1;
        private boolean offsetToChildIndexOnLayoutIsMinHeight;
        private float offsetToChildIndexOnLayoutPerc;
        private BaseDragCallback onDragCallback;
        private float touchX;
        private float touchY;

        public static abstract class BaseDragCallback<T extends AppBarLayout> {
            public abstract boolean canDrag(T t);
        }

        private static boolean checkFlag(int i, int i2) {
            return (i & i2) == i2;
        }

        /* access modifiers changed from: package-private */
        public void onFlingFinished(CoordinatorLayout coordinatorLayout, T t) {
        }

        public BaseBehavior() {
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, T t, View view, View view2, int i, int i2) {
            ValueAnimator valueAnimator;
            boolean z = (i & 2) != 0 && (t.isLiftOnScroll() || canScrollChildren(coordinatorLayout, t, view));
            if (z && (valueAnimator = this.offsetAnimator) != null) {
                valueAnimator.cancel();
            }
            if (((float) t.getBottom()) <= t.seslGetCollapsedHeight()) {
                this.mLifted = true;
                t.lifted = true;
                this.mDiffY_Touch = 0.0f;
            } else {
                this.mLifted = false;
                t.lifted = false;
            }
            t.updateInternalCollapsedHeight();
            this.lastNestedScrollingChildRef = null;
            this.lastStartedType = i2;
            this.mToolisMouse = t.getIsMouse();
            return z;
        }

        private boolean canScrollChildren(CoordinatorLayout coordinatorLayout, T t, View view) {
            return t.hasScrollableChildren() && coordinatorLayout.getHeight() - view.getHeight() <= t.getHeight();
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, T t, View view, int i, int i2, int[] iArr, int i3) {
            int i4;
            int i5;
            if (i2 != 0) {
                if (i2 < 0) {
                    int i6 = -t.getTotalScrollRange();
                    int downNestedPreScrollRange = t.getDownNestedPreScrollRange() + i6;
                    this.mIsFlingScrollDown = true;
                    this.mIsFlingScrollUp = false;
                    if (((double) t.getBottom()) >= ((double) t.getHeight()) * 0.52d) {
                        this.mIsSetStaticDuration = true;
                    }
                    if (i2 < -30) {
                        this.mIsFlingScrollDown = true;
                    } else {
                        this.mVelocity = 0.0f;
                        this.mIsFlingScrollDown = false;
                    }
                    i5 = i6;
                    i4 = downNestedPreScrollRange;
                } else {
                    int i7 = -t.getUpNestedPreScrollRange();
                    this.mIsFlingScrollDown = false;
                    this.mIsFlingScrollUp = true;
                    if (((double) t.getBottom()) <= ((double) t.getHeight()) * 0.43d) {
                        this.mIsSetStaticDuration = true;
                    }
                    if (i2 > 30) {
                        this.mIsFlingScrollUp = true;
                    } else {
                        this.mVelocity = 0.0f;
                        this.mIsFlingScrollUp = false;
                    }
                    if (getTopAndBottomOffset() == i7) {
                        this.mIsScrollHold = true;
                    }
                    i4 = 0;
                    i5 = i7;
                }
                if (i5 != i4) {
                    iArr[1] = scroll(coordinatorLayout, t, i2, i5, i4);
                }
            }
            if (t.isLiftOnScroll()) {
                t.setLiftedState(t.shouldLift(view));
            }
            stopNestedScrollIfNeeded(i2, t, view, i3);
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, T t, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            if (isScrollHoldMode(t)) {
                if (i4 >= 0 || this.mIsScrollHold) {
                    ViewCompat.stopNestedScroll(view, 1);
                    return;
                }
                iArr[1] = scroll(coordinatorLayout, t, i4, -t.getDownNestedScrollRange(), 0);
                stopNestedScrollIfNeeded(i4, t, view, i5);
            } else if (i4 < 0) {
                iArr[1] = scroll(coordinatorLayout, t, i4, -t.getDownNestedScrollRange(), 0);
                stopNestedScrollIfNeeded(i4, t, view, i5);
            }
        }

        private void stopNestedScrollIfNeeded(int i, T t, View view, int i2) {
            if (i2 == 1) {
                int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
                if ((i < 0 && topBottomOffsetForScrollingSibling == 0) || (i > 0 && topBottomOffsetForScrollingSibling == (-t.getDownNestedScrollRange()))) {
                    ViewCompat.stopNestedScroll(view, 1);
                }
            }
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, T t, View view, int i) {
            if (this.mLastTouchEvent == 3 || this.mLastTouchEvent == 1 || this.mLastInterceptTouchEvent == 3 || this.mLastInterceptTouchEvent == 1) {
                snapToChildIfNeeded(coordinatorLayout, t);
            }
            if (this.lastStartedType == 0 || i == 1) {
                if (t.isLiftOnScroll()) {
                    t.setLiftedState(t.shouldLift(view));
                }
                if (this.mIsScrollHold) {
                    this.mIsScrollHold = false;
                }
            }
            this.lastNestedScrollingChildRef = new WeakReference<>(view);
        }

        public void setDragCallback(BaseDragCallback baseDragCallback) {
            this.onDragCallback = baseDragCallback;
        }

        private void animateOffsetTo(CoordinatorLayout coordinatorLayout, T t, int i, float f) {
            int abs = (Math.abs(this.mVelocity) <= 0.0f || Math.abs(this.mVelocity) > 3000.0f) ? 250 : (int) (((double) (3000.0f - Math.abs(this.mVelocity))) * 0.4d);
            if (abs <= 250) {
                abs = 250;
            }
            if (this.mIsSetStaticDuration) {
                this.mIsSetStaticDuration = false;
                abs = 250;
            }
            if (this.mVelocity < 2000.0f) {
                animateOffsetWithDuration(coordinatorLayout, t, i, abs);
            }
            this.mVelocity = 0.0f;
        }

        private void animateOffsetWithDuration(final CoordinatorLayout coordinatorLayout, final T t, int i, int i2) {
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            if (topBottomOffsetForScrollingSibling == i) {
                ValueAnimator valueAnimator = this.offsetAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.offsetAnimator.cancel();
                    return;
                }
                return;
            }
            ValueAnimator valueAnimator2 = this.offsetAnimator;
            if (valueAnimator2 == null) {
                this.offsetAnimator = new ValueAnimator();
                this.offsetAnimator.setInterpolator(SeslAnimationUtils.SINE_OUT_80);
                this.offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    /* class com.google.android.material.appbar.AppBarLayout.BaseBehavior.AnonymousClass1 */

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        BaseBehavior.this.setHeaderTopBottomOffset(coordinatorLayout, t, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
            } else {
                valueAnimator2.cancel();
            }
            this.offsetAnimator.setDuration((long) Math.min(i2, 600));
            this.offsetAnimator.setIntValues(topBottomOffsetForScrollingSibling, i);
            this.offsetAnimator.start();
        }

        private int getChildIndexOnOffset(T t, int i) {
            int paddingBottom = i + (t.lifted ? t.getPaddingBottom() : 0);
            int childCount = t.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = t.getChildAt(i2);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (checkFlag(layoutParams.getScrollFlags(), 32)) {
                    top -= layoutParams.topMargin;
                    bottom += layoutParams.bottomMargin;
                }
                if (AppBarLayout.seslGetTCScrollRange() != 0) {
                    bottom += AppBarLayout.seslGetTCScrollRange();
                }
                int i3 = -paddingBottom;
                if (top <= i3 && bottom >= i3) {
                    return i2;
                }
            }
            return -1;
        }

        private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, T t) {
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            int childIndexOnOffset = getChildIndexOnOffset(t, topBottomOffsetForScrollingSibling);
            View childAt = coordinatorLayout.getChildAt(1);
            if (childIndexOnOffset >= 0) {
                View childAt2 = t.getChildAt(childIndexOnOffset);
                LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                int scrollFlags = layoutParams.getScrollFlags();
                if ((scrollFlags & 4096) != 4096) {
                    int seslGetTCScrollRange = t.getCanScroll() ? AppBarLayout.seslGetTCScrollRange() : 0;
                    if (((float) t.getBottom()) >= t.seslGetCollapsedHeight()) {
                        int i = -childAt2.getTop();
                        int i2 = -childAt2.getBottom();
                        if (childIndexOnOffset == t.getChildCount() - 1) {
                            i2 += t.getTopInset();
                        }
                        if (checkFlag(scrollFlags, 2)) {
                            if (t.getCanScroll()) {
                                i2 = (int) (((float) i2) + (t.seslGetCollapsedHeight() - ((float) t.getPaddingBottom())));
                            } else {
                                i2 += ViewCompat.getMinimumHeight(childAt2);
                            }
                        } else if (checkFlag(scrollFlags, 5)) {
                            int minimumHeight = ViewCompat.getMinimumHeight(childAt2) + i2;
                            if (topBottomOffsetForScrollingSibling < minimumHeight) {
                                i = minimumHeight;
                            } else {
                                i2 = minimumHeight;
                            }
                        }
                        if (checkFlag(scrollFlags, 32)) {
                            i += layoutParams.topMargin;
                            i2 -= layoutParams.bottomMargin;
                        }
                        int i3 = (!this.mLifted ? ((double) topBottomOffsetForScrollingSibling) >= ((double) (i2 + i)) * 0.43d : ((double) topBottomOffsetForScrollingSibling) >= ((double) (i2 + i)) * 0.52d) ? i : i2;
                        if (childAt == null) {
                            Log.w(AppBarLayout.TAG, "coordinatorLayout.getChildAt(1) is null");
                        } else {
                            if (this.mIsFlingScrollUp) {
                                this.mIsFlingScrollUp = false;
                                this.mIsFlingScrollDown = false;
                                i3 = i2;
                            }
                            if (this.mIsFlingScrollDown && ((float) childAt.getTop()) > t.seslGetCollapsedHeight()) {
                                this.mIsFlingScrollDown = false;
                                i3 = i;
                            }
                        }
                        animateOffsetTo(coordinatorLayout, t, MathUtils.clamp(i3, -t.getTotalScrollRange(), 0), 0.0f);
                    } else if (t.getCanScroll()) {
                        int seslGetCollapsedHeight = (((int) t.seslGetCollapsedHeight()) - t.getTotalScrollRange()) + seslGetTCScrollRange;
                        int i4 = -t.getTotalScrollRange();
                        int i5 = ((double) (t.getBottom() + seslGetTCScrollRange)) >= ((double) t.seslGetCollapsedHeight()) * 0.48d ? seslGetCollapsedHeight : i4;
                        if (!this.mIsFlingScrollUp) {
                            i4 = i5;
                        }
                        if (!this.mIsFlingScrollDown) {
                            seslGetCollapsedHeight = i4;
                        }
                        animateOffsetTo(coordinatorLayout, t, MathUtils.clamp(seslGetCollapsedHeight, -t.getTotalScrollRange(), 0), 0.0f);
                    }
                }
            }
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, T t, int i, int i2, int i3, int i4) {
            if (((CoordinatorLayout.LayoutParams) t.getLayoutParams()).height != -2) {
                return super.onMeasureChild(coordinatorLayout, (View) t, i, i2, i3, i4);
            }
            coordinatorLayout.onMeasureChild(t, i, i2, View.MeasureSpec.makeMeasureSpec(0, 0), i4);
            return true;
        }

        private int getImmPendingActionOffset(AppBarLayout appBarLayout) {
            Behavior behavior = (Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
            if (!appBarLayout.getCanScroll() || !(behavior instanceof SeslImmersiveScrollBehavior)) {
                return 0;
            }
            return ((int) appBarLayout.seslGetCollapsedHeight()) + AppBarLayout.seslGetTCScrollRange();
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, T t, int i) {
            int i2;
            boolean onLayoutChild = super.onLayoutChild(coordinatorLayout, (View) t, i);
            int pendingAction = t.getPendingAction();
            int i3 = this.offsetToChildIndexOnLayout;
            if (i3 >= 0 && (pendingAction & 8) == 0) {
                View childAt = t.getChildAt(i3);
                int i4 = -childAt.getBottom();
                if (this.offsetToChildIndexOnLayoutIsMinHeight) {
                    i2 = ViewCompat.getMinimumHeight(childAt) + t.getTopInset();
                } else {
                    i2 = Math.round(((float) childAt.getHeight()) * this.offsetToChildIndexOnLayoutPerc);
                }
                setHeaderTopBottomOffset(coordinatorLayout, t, i4 + i2);
            } else if (pendingAction != 0) {
                boolean z = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    float immPendingActionOffset = (float) (((-t.getTotalScrollRange()) + getImmPendingActionOffset(t)) - t.getImmersiveTopInset());
                    if (z) {
                        animateOffsetTo(coordinatorLayout, t, (int) immPendingActionOffset, 0.0f);
                    } else {
                        setHeaderTopBottomOffset(coordinatorLayout, t, (int) immPendingActionOffset);
                    }
                } else if ((pendingAction & 512) != 0) {
                    float immPendingActionOffset2 = (float) ((-t.getTotalScrollRange()) + getImmPendingActionOffset(t));
                    if (z) {
                        animateOffsetTo(coordinatorLayout, t, (int) immPendingActionOffset2, 0.0f);
                    } else {
                        setHeaderTopBottomOffset(coordinatorLayout, t, (int) immPendingActionOffset2);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (z) {
                        animateOffsetTo(coordinatorLayout, t, 0, 0.0f);
                    } else {
                        setHeaderTopBottomOffset(coordinatorLayout, t, 0);
                    }
                }
            }
            t.resetPendingAction();
            this.offsetToChildIndexOnLayout = -1;
            setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -t.getTotalScrollRange(), 0));
            updateAppBarLayoutDrawableState(coordinatorLayout, t, getTopAndBottomOffset(), 0, false);
            t.onOffsetChanged(getTopAndBottomOffset());
            return onLayoutChild;
        }

        /* access modifiers changed from: package-private */
        public boolean canDragView(T t) {
            BaseDragCallback baseDragCallback = this.onDragCallback;
            if (baseDragCallback != null) {
                return baseDragCallback.canDrag(t);
            }
            WeakReference<View> weakReference = this.lastNestedScrollingChildRef;
            if (weakReference == null) {
                return true;
            }
            View view = weakReference.get();
            if (view == null || !view.isShown() || view.canScrollVertically(-1)) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public int getMaxDragOffset(T t) {
            return -t.getDownNestedScrollRange();
        }

        /* access modifiers changed from: package-private */
        public int getScrollRangeForDragFling(T t) {
            return t.getTotalScrollRange();
        }

        /* access modifiers changed from: package-private */
        public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, T t, int i, int i2, int i3) {
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            int i4 = 0;
            if (i2 == 0 || topBottomOffsetForScrollingSibling < i2 || topBottomOffsetForScrollingSibling > i3) {
                this.offsetDelta = 0;
            } else {
                int clamp = MathUtils.clamp(i, i2, i3);
                if (topBottomOffsetForScrollingSibling != clamp) {
                    int interpolateOffset = t.hasChildWithInterpolator() ? interpolateOffset(t, clamp) : clamp;
                    boolean topAndBottomOffset = setTopAndBottomOffset(interpolateOffset);
                    i4 = topBottomOffsetForScrollingSibling - clamp;
                    this.offsetDelta = clamp - interpolateOffset;
                    if (!topAndBottomOffset && t.hasChildWithInterpolator()) {
                        coordinatorLayout.dispatchDependentViewsChanged(t);
                    }
                    t.onOffsetChanged(getTopAndBottomOffset());
                    updateAppBarLayoutDrawableState(coordinatorLayout, t, clamp, clamp < topBottomOffsetForScrollingSibling ? -1 : 1, false);
                }
            }
            return i4;
        }

        /* access modifiers changed from: package-private */
        public boolean isOffsetAnimatorRunning() {
            ValueAnimator valueAnimator = this.offsetAnimator;
            return valueAnimator != null && valueAnimator.isRunning();
        }

        /* access modifiers changed from: package-private */
        public int interpolateOffset(T t, int i) {
            int abs = Math.abs(i);
            int childCount = t.getChildCount();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    break;
                }
                View childAt = t.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Interpolator scrollInterpolator = layoutParams.getScrollInterpolator();
                if (abs < childAt.getTop() || abs > childAt.getBottom()) {
                    i3++;
                } else if (scrollInterpolator != null) {
                    int scrollFlags = layoutParams.getScrollFlags();
                    if ((scrollFlags & 1) != 0) {
                        i2 = 0 + childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                        if ((scrollFlags & 2) != 0) {
                            i2 -= ViewCompat.getMinimumHeight(childAt);
                        }
                    }
                    if (ViewCompat.getFitsSystemWindows(childAt)) {
                        i2 -= t.getTopInset();
                    }
                    if (i2 > 0) {
                        float f = (float) i2;
                        return Integer.signum(i) * (childAt.getTop() + Math.round(f * scrollInterpolator.getInterpolation(((float) (abs - childAt.getTop())) / f)));
                    }
                }
            }
            return i;
        }

        /* access modifiers changed from: package-private */
        public void updateAppBarLayoutDrawableState(CoordinatorLayout coordinatorLayout, T t, int i, int i2, boolean z) {
            View appBarChildOnOffset = getAppBarChildOnOffset(t, i);
            if (appBarChildOnOffset != null) {
                int scrollFlags = ((LayoutParams) appBarChildOnOffset.getLayoutParams()).getScrollFlags();
                boolean z2 = false;
                if ((scrollFlags & 1) != 0) {
                    int minimumHeight = ViewCompat.getMinimumHeight(appBarChildOnOffset);
                    if (i2 <= 0 || (scrollFlags & 12) == 0 ? !((scrollFlags & 2) == 0 || (-i) < ((appBarChildOnOffset.getBottom() - minimumHeight) - t.getTopInset()) - t.getImmersiveTopInset()) : (-i) >= ((appBarChildOnOffset.getBottom() - minimumHeight) - t.getTopInset()) - t.getImmersiveTopInset()) {
                        z2 = true;
                    }
                }
                if (t.isLiftOnScroll()) {
                    z2 = t.shouldLift(findFirstScrollingChild(coordinatorLayout));
                }
                boolean liftedState = t.setLiftedState(z2);
                if (z || (liftedState && shouldJumpElevationState(coordinatorLayout, t))) {
                    t.jumpDrawablesToCurrentState();
                }
            }
        }

        private boolean shouldJumpElevationState(CoordinatorLayout coordinatorLayout, T t) {
            List<View> dependents = coordinatorLayout.getDependents(t);
            int size = dependents.size();
            for (int i = 0; i < size; i++) {
                CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependents.get(i).getLayoutParams()).getBehavior();
                if (behavior instanceof ScrollingViewBehavior) {
                    if (((ScrollingViewBehavior) behavior).getOverlayTop() != 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        private static View getAppBarChildOnOffset(AppBarLayout appBarLayout, int i) {
            int abs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                if (abs >= childAt.getTop() && abs <= childAt.getBottom()) {
                    return childAt;
                }
            }
            return null;
        }

        private View findFirstScrollingChild(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if ((childAt instanceof NestedScrollingChild) || (childAt instanceof ListView) || (childAt instanceof ScrollView)) {
                    return childAt;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderBehavior
        public int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.offsetDelta;
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, T t) {
            Parcelable onSaveInstanceState = super.onSaveInstanceState(coordinatorLayout, (View) t);
            int topAndBottomOffset = getTopAndBottomOffset();
            int childCount = t.getChildCount();
            boolean z = false;
            for (int i = 0; i < childCount; i++) {
                View childAt = t.getChildAt(i);
                int bottom = childAt.getBottom() + topAndBottomOffset;
                if (childAt.getTop() + topAndBottomOffset <= 0 && bottom >= 0) {
                    SavedState savedState = new SavedState(onSaveInstanceState);
                    savedState.firstVisibleChildIndex = i;
                    if (bottom == ViewCompat.getMinimumHeight(childAt) + t.getTopInset()) {
                        z = true;
                    }
                    savedState.firstVisibleChildAtMinimumHeight = z;
                    savedState.firstVisibleChildPercentageShown = ((float) bottom) / ((float) childAt.getHeight());
                    return savedState;
                }
            }
            return onSaveInstanceState;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, T t, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                SavedState savedState = (SavedState) parcelable;
                super.onRestoreInstanceState(coordinatorLayout, (View) t, savedState.getSuperState());
                this.offsetToChildIndexOnLayout = savedState.firstVisibleChildIndex;
                this.offsetToChildIndexOnLayoutPerc = savedState.firstVisibleChildPercentageShown;
                this.offsetToChildIndexOnLayoutIsMinHeight = savedState.firstVisibleChildAtMinimumHeight;
                return;
            }
            super.onRestoreInstanceState(coordinatorLayout, (View) t, parcelable);
            this.offsetToChildIndexOnLayout = -1;
        }

        /* access modifiers changed from: protected */
        public static class SavedState extends AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
                /* class com.google.android.material.appbar.AppBarLayout.BaseBehavior.SavedState.AnonymousClass1 */

                @Override // android.os.Parcelable.ClassLoaderCreator
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return new SavedState(parcel, classLoader);
                }

                @Override // android.os.Parcelable.Creator
                public SavedState createFromParcel(Parcel parcel) {
                    return new SavedState(parcel, null);
                }

                @Override // android.os.Parcelable.Creator
                public SavedState[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            boolean firstVisibleChildAtMinimumHeight;
            int firstVisibleChildIndex;
            float firstVisibleChildPercentageShown;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.firstVisibleChildIndex = parcel.readInt();
                this.firstVisibleChildPercentageShown = parcel.readFloat();
                this.firstVisibleChildAtMinimumHeight = parcel.readByte() != 0;
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }

            @Override // androidx.customview.view.AbsSavedState
            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.firstVisibleChildIndex);
                parcel.writeFloat(this.firstVisibleChildPercentageShown);
                parcel.writeByte(this.firstVisibleChildAtMinimumHeight ? (byte) 1 : 0);
            }
        }

        private boolean isScrollHoldMode(T t) {
            if (this.mToolisMouse) {
                return false;
            }
            int childIndexOnOffset = getChildIndexOnOffset(t, getTopBottomOffsetForScrollingSibling());
            return childIndexOnOffset < 0 || (((LayoutParams) t.getChildAt(childIndexOnOffset).getLayoutParams()).getScrollFlags() & 65536) != 65536;
        }

        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, T t, View view, float f, float f2) {
            this.mVelocity = f2;
            if (f2 < -300.0f) {
                this.mIsFlingScrollDown = true;
                this.mIsFlingScrollUp = false;
            } else if (f2 > 300.0f) {
                this.mIsFlingScrollDown = false;
                this.mIsFlingScrollUp = true;
            } else {
                this.mVelocity = 0.0f;
                this.mIsFlingScrollDown = false;
                this.mIsFlingScrollUp = false;
                return true;
            }
            return super.onNestedPreFling(coordinatorLayout, (View) t, view, f, f2);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0026, code lost:
            if (r0 != 3) goto L_0x0094;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout r6, T r7, android.view.MotionEvent r8) {
            /*
            // Method dump skipped, instructions count: 153
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, com.google.android.material.appbar.AppBarLayout, android.view.MotionEvent):boolean");
        }
    }

    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean isHorizontalOffsetEnabled() {
            return super.isHorizontalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean isVerticalOffsetEnabled() {
            return super.isVerticalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            return super.onLayoutChild(coordinatorLayout, view, i);
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, view, i, i2, i3, i4);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ void setHorizontalOffsetEnabled(boolean z) {
            super.setHorizontalOffsetEnabled(z);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ void setVerticalOffsetEnabled(boolean z) {
            super.setVerticalOffsetEnabled(z);
        }

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(obtainStyledAttributes.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            obtainStyledAttributes.recycle();
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            offsetChildAsNeeded(view, view2);
            updateLiftedStateIfNeeded(view, view2);
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            AppBarLayout findFirstDependency = findFirstDependency(coordinatorLayout.getDependencies(view));
            if (findFirstDependency != null) {
                rect.offset(view.getLeft(), view.getTop());
                Rect rect2 = this.tempRect1;
                rect2.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect2.contains(rect)) {
                    findFirstDependency.setExpanded(false, !z);
                    return true;
                }
            }
            return false;
        }

        private void offsetChildAsNeeded(View view, View view2) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view2.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                ViewCompat.offsetTopAndBottom(view, (((view2.getBottom() - view.getTop()) + ((BaseBehavior) behavior).offsetDelta) + getVerticalLayoutGap()) - getOverlapPixelsForOffset(view2));
            }
        }

        /* access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        public float getOverlapRatioForOffset(View view) {
            int i;
            if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
                int appBarLayoutOffset = getAppBarLayoutOffset(appBarLayout);
                if ((downNestedPreScrollRange == 0 || totalScrollRange + appBarLayoutOffset > downNestedPreScrollRange) && (i = totalScrollRange - downNestedPreScrollRange) != 0) {
                    return (((float) appBarLayoutOffset) / ((float) i)) + 1.0f;
                }
            }
            return 0.0f;
        }

        private static int getAppBarLayoutOffset(AppBarLayout appBarLayout) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                return ((BaseBehavior) behavior).getTopBottomOffsetForScrollingSibling();
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        public AppBarLayout findFirstDependency(List<View> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = list.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        public int getScrollRange(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout) view).getTotalScrollRange();
            }
            return super.getScrollRange(view);
        }

        private void updateLiftedStateIfNeeded(View view, View view2) {
            if (view2 instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                if (appBarLayout.isLiftOnScroll()) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
                }
            }
        }
    }
}
