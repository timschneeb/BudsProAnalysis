package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.TextViewCompat;
import androidx.reflect.os.SeslBuildReflector;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;

public class CollapsingToolbarLayout extends FrameLayout {
    private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 600;
    private static final float LAND_HEIGHT_PERCENT = 0.3f;
    private static final float MAX_FONT_SCALE = 1.1f;
    protected static final String TAG = "Sesl_CTL";
    final CollapsingTextHelper collapsingTextHelper;
    private boolean collapsingTitleEnabled;
    private Drawable contentScrim;
    int currentOffset;
    private boolean drawCollapsingTitle;
    private View dummyView;
    private int expandedMarginBottom;
    private int expandedMarginEnd;
    private int expandedMarginStart;
    private int expandedMarginTop;
    WindowInsetsCompat lastInsets;
    private Context mContext;
    private View mCustomSubTitleView;
    private float mDefaultHeight;
    private int mExtendSubTitleAppearance;
    private int mExtendTitleAppearance;
    private TextView mExtendedSubTitle;
    private TextView mExtendedTitle;
    private float mHeightProportion;
    private boolean mIsCollapsingToolbarTitleCustom;
    private boolean mIsCustomAccessibility;
    private boolean mSubTitleEnabled;
    private boolean mTitleEnabled;
    private LinearLayout mTitleLayout;
    private LinearLayout mTitleLayoutParent;
    private ViewStubCompat mViewStubCompat;
    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
    private boolean refreshToolbar;
    private int scrimAlpha;
    private long scrimAnimationDuration;
    private ValueAnimator scrimAnimator;
    private int scrimVisibleHeightTrigger;
    private boolean scrimsAreShown;
    Drawable statusBarScrim;
    private final Rect tmpRect;
    private Toolbar toolbar;
    private View toolbarDirectChild;
    private int toolbarId;

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.refreshToolbar = true;
        this.tmpRect = new Rect();
        this.scrimVisibleHeightTrigger = -1;
        this.mIsCustomAccessibility = false;
        this.mCustomSubTitleView = null;
        this.mContext = context;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.CollapsingToolbarLayout, i, R.style.Widget_Design_CollapsingToolbar, new int[0]);
        this.collapsingTitleEnabled = obtainStyledAttributes.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, false);
        this.mTitleEnabled = obtainStyledAttributes.getBoolean(R.styleable.CollapsingToolbarLayout_extendedTitleEnabled, true);
        boolean z = this.collapsingTitleEnabled;
        if (z == this.mTitleEnabled && z) {
            this.collapsingTitleEnabled = false;
        }
        if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper = new CollapsingTextHelper(this);
            this.collapsingTextHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
            this.collapsingTextHelper.setExpandedTextGravity(obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, BadgeDrawable.BOTTOM_START));
            this.collapsingTextHelper.setCollapsedTextGravity(obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
            this.expandedMarginBottom = dimensionPixelSize;
            this.expandedMarginEnd = dimensionPixelSize;
            this.expandedMarginTop = dimensionPixelSize;
            this.expandedMarginStart = dimensionPixelSize;
        } else {
            this.collapsingTextHelper = null;
        }
        this.mExtendTitleAppearance = obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_extendedTitleTextAppearance, 0);
        this.mExtendSubTitleAppearance = obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_extendedSubtitleTextAppearance, 0);
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.mExtendTitleAppearance = obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0);
        }
        CharSequence text = obtainStyledAttributes.getText(R.styleable.CollapsingToolbarLayout_subtitle);
        this.mSubTitleEnabled = this.mTitleEnabled && !TextUtils.isEmpty(text);
        this.mTitleLayoutParent = new LinearLayout(context, attributeSet, i);
        this.mTitleLayoutParent.setId(R.id.collapsing_appbar_title_layout_parent);
        this.mTitleLayoutParent.setBackgroundColor(0);
        if (this.mTitleLayoutParent != null) {
            addView(this.mTitleLayoutParent, new FrameLayout.LayoutParams(-1, -1, 17));
        }
        this.mTitleLayout = new LinearLayout(context, attributeSet, i);
        this.mTitleLayout.setId(R.id.collapsing_appbar_title_layout);
        this.mTitleLayout.setBackgroundColor(0);
        LinearLayout linearLayout = this.mTitleLayout;
        if (linearLayout != null) {
            linearLayout.setOrientation(1);
            int statusbarHeight = getStatusbarHeight();
            if (statusbarHeight > 0) {
                this.mTitleLayout.setPadding(0, 0, 0, statusbarHeight / 2);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2, 16.0f);
            layoutParams.gravity = 16;
            this.mTitleLayoutParent.addView(this.mTitleLayout, layoutParams);
        }
        if (this.mTitleEnabled) {
            this.mExtendedTitle = new TextView(context);
            this.mExtendedTitle.setId(R.id.collapsing_appbar_extended_title);
            if (Build.VERSION.SDK_INT >= 29) {
                this.mExtendedTitle.setHyphenationFrequency(1);
            }
            this.mTitleLayout.addView(this.mExtendedTitle);
            this.mExtendedTitle.setEllipsize(TextUtils.TruncateAt.END);
            this.mExtendedTitle.setGravity(17);
            this.mExtendedTitle.setTextAppearance(getContext(), this.mExtendTitleAppearance);
            int dimension = (int) getResources().getDimension(R.dimen.sesl_appbar_extended_title_padding);
            this.mExtendedTitle.setPadding(dimension, 0, dimension, 0);
        }
        if (this.mSubTitleEnabled) {
            seslSetSubtitle(text);
        }
        updateDefaultHeight();
        updateTitleLayout();
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.expandedMarginStart = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.expandedMarginEnd = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.expandedMarginTop = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.expandedMarginBottom = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        setTitle(obtainStyledAttributes.getText(R.styleable.CollapsingToolbarLayout_title));
        if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setExpandedTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
            this.collapsingTextHelper.setCollapsedTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
            if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
                this.collapsingTextHelper.setExpandedTextAppearance(obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
                this.collapsingTextHelper.setCollapsedTextAppearance(obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
            }
        }
        this.scrimVisibleHeightTrigger = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        this.scrimAnimationDuration = (long) obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
        setContentScrim(obtainStyledAttributes.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
        setStatusBarScrim(obtainStyledAttributes.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
        this.toolbarId = obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (!obtainStyledAttributes2.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            LayoutInflater.from(context).inflate(R.layout.sesl_material_action_mode_view_stub, (ViewGroup) this, true);
            this.mViewStubCompat = (ViewStubCompat) findViewById(R.id.action_mode_bar_stub);
        }
        obtainStyledAttributes2.recycle();
        setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
            /* class com.google.android.material.appbar.CollapsingToolbarLayout.AnonymousClass1 */

            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return CollapsingToolbarLayout.this.onWindowInsetChanged(windowInsetsCompat);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View) parent));
            if (this.onOffsetChangedListener == null) {
                this.onOffsetChangedListener = new OffsetUpdateListener();
            }
            ((AppBarLayout) parent).addOnOffsetChangedListener(this.onOffsetChangedListener);
            ViewCompat.requestApplyInsets(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        ViewParent parent = getParent();
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener2 = this.onOffsetChangedListener;
        if (onOffsetChangedListener2 != null && (parent instanceof AppBarLayout)) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(onOffsetChangedListener2);
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: package-private */
    public WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows(this) ? windowInsetsCompat : null;
        if (!ObjectsCompat.equals(this.lastInsets, windowInsetsCompat2)) {
            this.lastInsets = windowInsetsCompat2;
            requestLayout();
        }
        return windowInsetsCompat.consumeSystemWindowInsets();
    }

    public void draw(Canvas canvas) {
        Drawable drawable;
        super.draw(canvas);
        ensureToolbar();
        if (this.toolbar == null && (drawable = this.contentScrim) != null && this.scrimAlpha > 0) {
            drawable.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
        }
        if (this.collapsingTitleEnabled && this.drawCollapsingTitle) {
            this.collapsingTextHelper.draw(canvas);
        }
        if (this.statusBarScrim != null && this.scrimAlpha > 0) {
            WindowInsetsCompat windowInsetsCompat = this.lastInsets;
            int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
            if (systemWindowInsetTop > 0) {
                this.statusBarScrim.setBounds(0, -this.currentOffset, getWidth(), systemWindowInsetTop - this.currentOffset);
                this.statusBarScrim.mutate().setAlpha(this.scrimAlpha);
                this.statusBarScrim.draw(canvas);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        if (this.contentScrim == null || this.scrimAlpha <= 0 || !isToolbarChild(view)) {
            z = false;
        } else {
            this.contentScrim.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
            z = true;
        }
        return super.drawChild(canvas, view, j) || z;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Drawable drawable = this.contentScrim;
        if (drawable != null) {
            drawable.setBounds(0, 0, i, i2);
        }
    }

    private void ensureToolbar() {
        if (this.refreshToolbar) {
            Toolbar toolbar2 = null;
            this.toolbar = null;
            this.toolbarDirectChild = null;
            int i = this.toolbarId;
            if (i != -1) {
                this.toolbar = (Toolbar) findViewById(i);
                Toolbar toolbar3 = this.toolbar;
                if (toolbar3 != null) {
                    this.toolbarDirectChild = findDirectChild(toolbar3);
                }
            }
            if (this.toolbar == null) {
                int childCount = getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    View childAt = getChildAt(i2);
                    if (childAt instanceof Toolbar) {
                        toolbar2 = (Toolbar) childAt;
                        break;
                    }
                    i2++;
                }
                this.toolbar = toolbar2;
                ViewStubCompat viewStubCompat = this.mViewStubCompat;
                if (viewStubCompat != null) {
                    viewStubCompat.bringToFront();
                    this.mViewStubCompat.invalidate();
                }
            }
            updateDummyView();
            this.refreshToolbar = false;
        }
    }

    private boolean isToolbarChild(View view) {
        View view2 = this.toolbarDirectChild;
        if (view2 == null || view2 == this) {
            if (view == this.toolbar) {
                return true;
            }
        } else if (view == view2) {
            return true;
        }
        return false;
    }

    private View findDirectChild(View view) {
        ViewParent parent = view.getParent();
        while (parent != this && parent != null) {
            if (parent instanceof View) {
                view = (View) parent;
            }
            parent = parent.getParent();
        }
        return view;
    }

    private void updateDummyView() {
        View view;
        if (!this.collapsingTitleEnabled && (view = this.dummyView) != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.dummyView);
            }
        }
        if (this.collapsingTitleEnabled && this.toolbar != null) {
            if (this.dummyView == null) {
                this.dummyView = new View(getContext());
            }
            if (this.dummyView.getParent() == null) {
                this.toolbar.addView(this.dummyView, -1, -1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        ensureToolbar();
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if (mode == 0 && systemWindowInsetTop > 0) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + systemWindowInsetTop, 1073741824));
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        final int i5;
        View view;
        super.onLayout(z, i, i2, i3, i4);
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
            int childCount = getChildCount();
            for (int i6 = 0; i6 < childCount; i6++) {
                View childAt = getChildAt(i6);
                if (!ViewCompat.getFitsSystemWindows(childAt) && childAt.getTop() < systemWindowInsetTop) {
                    ViewCompat.offsetTopAndBottom(childAt, systemWindowInsetTop);
                }
            }
        }
        int childCount2 = getChildCount();
        for (int i7 = 0; i7 < childCount2; i7++) {
            getViewOffsetHelper(getChildAt(i7)).onViewLayout();
        }
        if (this.collapsingTitleEnabled && (view = this.dummyView) != null) {
            boolean z2 = true;
            this.drawCollapsingTitle = ViewCompat.isAttachedToWindow(view) && this.dummyView.getVisibility() == 0;
            if (this.drawCollapsingTitle) {
                if (ViewCompat.getLayoutDirection(this) != 1) {
                    z2 = false;
                }
                View view2 = this.toolbarDirectChild;
                if (view2 == null) {
                    view2 = this.toolbar;
                }
                int maxOffsetForPinChild = getMaxOffsetForPinChild(view2);
                DescendantOffsetUtils.getDescendantRect(this, this.dummyView, this.tmpRect);
                this.collapsingTextHelper.setCollapsedBounds(this.tmpRect.left + (z2 ? this.toolbar.getTitleMarginEnd() : this.toolbar.getTitleMarginStart()), this.tmpRect.top + maxOffsetForPinChild + this.toolbar.getTitleMarginTop(), this.tmpRect.right + (z2 ? this.toolbar.getTitleMarginStart() : this.toolbar.getTitleMarginEnd()), (this.tmpRect.bottom + maxOffsetForPinChild) - this.toolbar.getTitleMarginBottom());
                this.collapsingTextHelper.setExpandedBounds(z2 ? this.expandedMarginEnd : this.expandedMarginStart, this.tmpRect.top + this.expandedMarginTop, (i3 - i) - (z2 ? this.expandedMarginStart : this.expandedMarginEnd), (i4 - i2) - this.expandedMarginBottom);
                this.collapsingTextHelper.recalculate();
            }
        }
        if (this.toolbar != null) {
            if (this.collapsingTitleEnabled && TextUtils.isEmpty(this.collapsingTextHelper.getText())) {
                setTitle(this.toolbar.getTitle());
            }
            View view3 = this.toolbarDirectChild;
            if (view3 == null || view3 == this) {
                i5 = getHeightWithMargins(this.toolbar);
            } else {
                i5 = getHeightWithMargins(view3);
            }
            if (ViewCompat.getMinimumHeight(this) < i5) {
                post(new Runnable() {
                    /* class com.google.android.material.appbar.CollapsingToolbarLayout.AnonymousClass2 */

                    public void run() {
                        CollapsingToolbarLayout.this.setMinimumHeight(i5);
                    }
                });
            }
        }
        updateScrimVisibility();
        int childCount3 = getChildCount();
        for (int i8 = 0; i8 < childCount3; i8++) {
            getViewOffsetHelper(getChildAt(i8)).applyOffsets();
        }
    }

    private static int getHeightWithMargins(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return view.getHeight();
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return view.getHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    static ViewOffsetHelper getViewOffsetHelper(View view) {
        ViewOffsetHelper viewOffsetHelper = (ViewOffsetHelper) view.getTag(R.id.view_offset_helper);
        if (viewOffsetHelper != null) {
            return viewOffsetHelper;
        }
        ViewOffsetHelper viewOffsetHelper2 = new ViewOffsetHelper(view);
        view.setTag(R.id.view_offset_helper, viewOffsetHelper2);
        return viewOffsetHelper2;
    }

    public void setTitle(CharSequence charSequence) {
        if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setText(charSequence);
            updateContentDescriptionFromTitle();
        } else {
            TextView textView = this.mExtendedTitle;
            if (textView != null) {
                textView.setText(charSequence);
            }
        }
        updateTitleLayout();
    }

    public CharSequence getTitle() {
        if (this.collapsingTitleEnabled) {
            return this.collapsingTextHelper.getText();
        }
        return this.mExtendedTitle.getText();
    }

    public void setTitleEnabled(boolean z) {
        TextView textView;
        if (!z) {
            this.mTitleEnabled = false;
            this.collapsingTitleEnabled = false;
        } else if (this.mExtendedTitle != null) {
            this.mTitleEnabled = true;
            this.mTitleEnabled = false;
        } else if (this.collapsingTextHelper != null) {
            this.mTitleEnabled = true;
            this.mTitleEnabled = false;
        } else {
            this.mTitleEnabled = false;
            this.mTitleEnabled = false;
        }
        if (!z && !this.mTitleEnabled && (textView = this.mExtendedTitle) != null) {
            textView.setVisibility(4);
        }
        if (z && this.collapsingTitleEnabled) {
            updateDummyView();
            requestLayout();
        }
    }

    public boolean isTitleEnabled() {
        return this.mTitleEnabled;
    }

    public void setScrimsShown(boolean z) {
        setScrimsShown(z, ViewCompat.isLaidOut(this) && !isInEditMode());
    }

    public void setScrimsShown(boolean z, boolean z2) {
        if (this.scrimsAreShown != z) {
            int i = 255;
            if (z2) {
                if (!z) {
                    i = 0;
                }
                animateScrim(i);
            } else {
                if (!z) {
                    i = 0;
                }
                setScrimAlpha(i);
            }
            this.scrimsAreShown = z;
        }
    }

    private void animateScrim(int i) {
        ensureToolbar();
        ValueAnimator valueAnimator = this.scrimAnimator;
        if (valueAnimator == null) {
            this.scrimAnimator = new ValueAnimator();
            this.scrimAnimator.setDuration(this.scrimAnimationDuration);
            this.scrimAnimator.setInterpolator(i > this.scrimAlpha ? AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR : AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
            this.scrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.google.android.material.appbar.CollapsingToolbarLayout.AnonymousClass3 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CollapsingToolbarLayout.this.setScrimAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
        } else if (valueAnimator.isRunning()) {
            this.scrimAnimator.cancel();
        }
        this.scrimAnimator.setIntValues(this.scrimAlpha, i);
        this.scrimAnimator.start();
    }

    /* access modifiers changed from: package-private */
    public void setScrimAlpha(int i) {
        Toolbar toolbar2;
        if (i != this.scrimAlpha) {
            if (!(this.contentScrim == null || (toolbar2 = this.toolbar) == null)) {
                ViewCompat.postInvalidateOnAnimation(toolbar2);
            }
            this.scrimAlpha = i;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: package-private */
    public int getScrimAlpha() {
        return this.scrimAlpha;
    }

    public void setContentScrim(Drawable drawable) {
        Drawable drawable2 = this.contentScrim;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.contentScrim = drawable3;
            Drawable drawable4 = this.contentScrim;
            if (drawable4 != null) {
                drawable4.setBounds(0, 0, getWidth(), getHeight());
                this.contentScrim.setCallback(this);
                this.contentScrim.setAlpha(this.scrimAlpha);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setContentScrimColor(int i) {
        setContentScrim(new ColorDrawable(i));
    }

    public void setContentScrimResource(int i) {
        setContentScrim(ContextCompat.getDrawable(getContext(), i));
    }

    public Drawable getContentScrim() {
        return this.contentScrim;
    }

    public void setStatusBarScrim(Drawable drawable) {
        Drawable drawable2 = this.statusBarScrim;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.statusBarScrim = drawable3;
            Drawable drawable4 = this.statusBarScrim;
            if (drawable4 != null) {
                if (drawable4.isStateful()) {
                    this.statusBarScrim.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.statusBarScrim, ViewCompat.getLayoutDirection(this));
                this.statusBarScrim.setVisible(getVisibility() == 0, false);
                this.statusBarScrim.setCallback(this);
                this.statusBarScrim.setAlpha(this.scrimAlpha);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.statusBarScrim;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.contentScrim;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
        if (collapsingTextHelper2 != null) {
            z |= collapsingTextHelper2.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.contentScrim || drawable == this.statusBarScrim;
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.statusBarScrim;
        if (!(drawable == null || drawable.isVisible() == z)) {
            this.statusBarScrim.setVisible(z, false);
        }
        Drawable drawable2 = this.contentScrim;
        if (drawable2 != null && drawable2.isVisible() != z) {
            this.contentScrim.setVisible(z, false);
        }
    }

    public void setStatusBarScrimColor(int i) {
        setStatusBarScrim(new ColorDrawable(i));
    }

    public void setStatusBarScrimResource(int i) {
        setStatusBarScrim(ContextCompat.getDrawable(getContext(), i));
    }

    public Drawable getStatusBarScrim() {
        return this.statusBarScrim;
    }

    public void setCollapsedTitleTextAppearance(int i) {
        if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setCollapsedTextAppearance(i);
        }
    }

    public void setCollapsedTitleTextColor(int i) {
        setCollapsedTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setCollapsedTitleTextColor(ColorStateList colorStateList) {
        if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
    }

    public void setCollapsedTitleGravity(int i) {
        if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setCollapsedTextGravity(i);
        }
    }

    public int getCollapsedTitleGravity() {
        if (this.collapsingTitleEnabled) {
            return this.collapsingTextHelper.getCollapsedTextGravity();
        }
        return 0;
    }

    public void setExpandedTitleTextAppearance(int i) {
        if (this.mTitleEnabled) {
            this.mExtendedTitle.setTextAppearance(getContext(), i);
        } else if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setExpandedTextAppearance(i);
        }
    }

    public void setExpandedTitleColor(int i) {
        setExpandedTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setExpandedTitleTextColor(ColorStateList colorStateList) {
        if (this.mTitleEnabled) {
            this.mExtendedTitle.setTextColor(colorStateList);
        } else if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setExpandedTextColor(colorStateList);
        }
    }

    public void setExpandedTitleGravity(int i) {
        if (this.mTitleEnabled) {
            this.mExtendedTitle.setGravity(i);
        } else if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setExpandedTextGravity(i);
        }
    }

    public int getExpandedTitleGravity() {
        if (this.mTitleEnabled) {
            return this.mExtendedTitle.getGravity();
        }
        if (this.collapsingTitleEnabled) {
            return this.collapsingTextHelper.getExpandedTextGravity();
        }
        return 0;
    }

    public void setCollapsedTitleTypeface(Typeface typeface) {
        if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setCollapsedTypeface(typeface);
        }
    }

    public Typeface getCollapsedTitleTypeface() {
        if (this.collapsingTitleEnabled) {
            return this.collapsingTextHelper.getCollapsedTypeface();
        }
        return Typeface.DEFAULT;
    }

    public void setExpandedTitleTypeface(Typeface typeface) {
        if (this.mTitleEnabled) {
            this.mExtendedTitle.setTypeface(typeface);
        } else if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.setExpandedTypeface(typeface);
        }
    }

    public Typeface getExpandedTitleTypeface() {
        if (this.mTitleEnabled) {
            return this.mExtendedTitle.getTypeface();
        }
        if (this.collapsingTitleEnabled) {
            return this.collapsingTextHelper.getExpandedTypeface();
        }
        return Typeface.DEFAULT;
    }

    public void setExpandedTitleMargin(int i, int i2, int i3, int i4) {
        this.expandedMarginStart = i;
        this.expandedMarginTop = i2;
        this.expandedMarginEnd = i3;
        this.expandedMarginBottom = i4;
        requestLayout();
    }

    public int getExpandedTitleMarginStart() {
        return this.expandedMarginStart;
    }

    public void setExpandedTitleMarginStart(int i) {
        this.expandedMarginStart = i;
        requestLayout();
    }

    public int getExpandedTitleMarginTop() {
        return this.expandedMarginTop;
    }

    public void setExpandedTitleMarginTop(int i) {
        this.expandedMarginTop = i;
        requestLayout();
    }

    public int getExpandedTitleMarginEnd() {
        return this.expandedMarginEnd;
    }

    public void setExpandedTitleMarginEnd(int i) {
        this.expandedMarginEnd = i;
        requestLayout();
    }

    public int getExpandedTitleMarginBottom() {
        return this.expandedMarginBottom;
    }

    public void setExpandedTitleMarginBottom(int i) {
        this.expandedMarginBottom = i;
        requestLayout();
    }

    public void setScrimVisibleHeightTrigger(int i) {
        if (this.scrimVisibleHeightTrigger != i) {
            this.scrimVisibleHeightTrigger = i;
            updateScrimVisibility();
        }
    }

    public int getScrimVisibleHeightTrigger() {
        int i = this.scrimVisibleHeightTrigger;
        if (i >= 0) {
            return i;
        }
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        if (minimumHeight > 0) {
            return Math.min((minimumHeight * 2) + systemWindowInsetTop, getHeight());
        }
        return getHeight() / 3;
    }

    public void setScrimAnimationDuration(long j) {
        this.scrimAnimationDuration = j;
    }

    public long getScrimAnimationDuration() {
        return this.scrimAnimationDuration;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.widget.FrameLayout
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.widget.FrameLayout, android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public static final int COLLAPSE_MODE_OFF = 0;
        public static final int COLLAPSE_MODE_PARALLAX = 2;
        public static final int COLLAPSE_MODE_PIN = 1;
        private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5f;
        int collapseMode = 0;
        private boolean isTitleCustom;
        float parallaxMult = DEFAULT_PARALLAX_MULTIPLIER;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout_Layout);
            this.collapseMode = obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            setParallaxMultiplier(obtainStyledAttributes.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, DEFAULT_PARALLAX_MULTIPLIER));
            this.isTitleCustom = obtainStyledAttributes.getBoolean(R.styleable.CollapsingToolbarLayout_Layout_isCustomTitle, false);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2, i3);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(FrameLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public void setCollapseMode(int i) {
            this.collapseMode = i;
        }

        public int getCollapseMode() {
            return this.collapseMode;
        }

        public void setParallaxMultiplier(float f) {
            this.parallaxMult = f;
        }

        public float getParallaxMultiplier() {
            return this.parallaxMult;
        }

        public void seslSetIsTitleCustom(Boolean bool) {
            this.isTitleCustom = bool.booleanValue();
        }

        public boolean seslIsTitleCustom() {
            return this.isTitleCustom;
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateScrimVisibility() {
        if (this.contentScrim != null || this.statusBarScrim != null) {
            setScrimsShown(getHeight() + this.currentOffset < getScrimVisibleHeightTrigger());
        }
    }

    /* access modifiers changed from: package-private */
    public final int getMaxOffsetForPinChild(View view) {
        return ((getHeight() - getViewOffsetHelper(view).getLayoutTop()) - view.getHeight()) - ((LayoutParams) view.getLayoutParams()).bottomMargin;
    }

    private void updateContentDescriptionFromTitle() {
        setContentDescription(getTitle());
    }

    @Override // android.view.ViewGroup
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2;
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        super.addView(view, layoutParams);
        if (this.mTitleEnabled && (layoutParams2 = (LayoutParams) view.getLayoutParams()) != null) {
            this.mIsCollapsingToolbarTitleCustom = layoutParams2.seslIsTitleCustom();
            if (this.mIsCollapsingToolbarTitleCustom) {
                TextView textView = this.mExtendedTitle;
                if (textView != null && textView.getParent() == (linearLayout2 = this.mTitleLayout)) {
                    linearLayout2.removeView(this.mExtendedTitle);
                }
                TextView textView2 = this.mExtendedSubTitle;
                if (textView2 != null && textView2.getParent() == (linearLayout = this.mTitleLayout)) {
                    linearLayout.removeView(this.mExtendedSubTitle);
                }
                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                this.mTitleLayout.addView(view, layoutParams);
            }
        }
    }

    public void seslSetCustomTitleView(View view, LayoutParams layoutParams) {
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        this.mIsCollapsingToolbarTitleCustom = layoutParams.seslIsTitleCustom();
        if (this.mIsCollapsingToolbarTitleCustom) {
            TextView textView = this.mExtendedTitle;
            if (textView != null && textView.getParent() == (linearLayout2 = this.mTitleLayout)) {
                linearLayout2.removeView(this.mExtendedTitle);
            }
            TextView textView2 = this.mExtendedSubTitle;
            if (textView2 != null && textView2.getParent() == (linearLayout = this.mTitleLayout)) {
                linearLayout.removeView(this.mExtendedSubTitle);
            }
            this.mTitleLayout.addView(view, layoutParams);
            return;
        }
        super.addView(view, layoutParams);
    }

    private void updateTitleLayout() {
        Resources resources = getResources();
        this.mHeightProportion = ResourcesCompat.getFloat(resources, R.dimen.sesl_appbar_height_proportion);
        if (this.mTitleEnabled) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(this.mExtendTitleAppearance, R.styleable.TextAppearance);
            TypedValue peekValue = obtainStyledAttributes.peekValue(R.styleable.TextAppearance_android_textSize);
            if (peekValue == null) {
                Log.i(TAG, "ExtendTitleAppearance value is null");
                return;
            }
            float complexToFloat = TypedValue.complexToFloat(peekValue.data);
            float min = Math.min(resources.getConfiguration().fontScale, (float) MAX_FONT_SCALE);
            Log.i(TAG, "updateTitleLayout : context : " + getContext() + ", testSize : " + complexToFloat + ", fontScale : " + min + ", mSubTitleEnabled : " + this.mSubTitleEnabled);
            if (!this.mSubTitleEnabled) {
                this.mExtendedTitle.setTextSize(1, complexToFloat * min);
            } else {
                this.mExtendedTitle.setTextSize(0, (float) resources.getDimensionPixelSize(R.dimen.sesl_appbar_extended_title_text_size_with_subtitle));
                this.mExtendedSubTitle.setTextSize(0, (float) resources.getDimensionPixelSize(R.dimen.sesl_toolbar_subtitle_text_size));
            }
            if (SeslBuildReflector.SeslVersionReflector.getField_SEM_PLATFORM_INT() >= 120000) {
                try {
                    this.mExtendedTitle.setLayoutParams(new LinearLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.sesl_appbar_extended_title_text_view_height)));
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(this.mExtendedTitle, 100);
                    TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(this.mExtendedTitle, new int[]{38, 32, 26}, 1);
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
            if (this.mHeightProportion != LAND_HEIGHT_PERCENT) {
                this.mExtendedTitle.setSingleLine(false);
                this.mExtendedTitle.setMaxLines(2);
            } else if (this.mSubTitleEnabled) {
                this.mExtendedTitle.setSingleLine(true);
                this.mExtendedTitle.setMaxLines(1);
            } else {
                this.mExtendedTitle.setSingleLine(false);
                this.mExtendedTitle.setMaxLines(2);
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateDefaultHeight() {
        if (getParent() instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) getParent();
            if (appBarLayout.useCollapsedHeight()) {
                this.mDefaultHeight = appBarLayout.seslGetCollapsedHeight();
            } else if (appBarLayout.getPaddingBottom() > 0) {
                this.mDefaultHeight = (float) getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding);
            } else {
                this.mDefaultHeight = (float) getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_default_height);
            }
        } else {
            this.mDefaultHeight = (float) getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding);
        }
    }

    public void seslSetSubtitle(int i) {
        seslSetSubtitle(getContext().getText(i));
    }

    public void seslSetSubtitle(CharSequence charSequence) {
        if (!this.mTitleEnabled || TextUtils.isEmpty(charSequence)) {
            this.mSubTitleEnabled = false;
            TextView textView = this.mExtendedSubTitle;
            if (textView != null) {
                ((ViewGroup) textView.getParent()).removeView(this.mExtendedSubTitle);
                this.mExtendedSubTitle = null;
            }
        } else {
            this.mSubTitleEnabled = true;
            TextView textView2 = this.mExtendedSubTitle;
            if (textView2 == null) {
                this.mExtendedSubTitle = new TextView(getContext());
                this.mExtendedSubTitle.setId(R.id.collapsing_appbar_extended_subtitle);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                this.mExtendedSubTitle.setText(charSequence);
                layoutParams.gravity = 1;
                this.mTitleLayout.addView(this.mExtendedSubTitle, layoutParams);
                this.mExtendedSubTitle.setSingleLine(false);
                this.mExtendedSubTitle.setMaxLines(1);
                this.mExtendedSubTitle.setGravity(1);
                this.mExtendedSubTitle.setTextAppearance(getContext(), this.mExtendSubTitleAppearance);
            } else {
                textView2.setText(charSequence);
            }
            TextView textView3 = this.mExtendedTitle;
            if (textView3 != null) {
                textView3.setTextSize(0, (float) getContext().getResources().getDimensionPixelSize(R.dimen.sesl_appbar_extended_title_text_size_with_subtitle));
            }
        }
        updateTitleLayout();
        requestLayout();
    }

    public void seslSetCustomSubtitle(View view) {
        if (view != null) {
            this.mCustomSubTitleView = view;
            if (this.mTitleEnabled) {
                this.mTitleLayout.addView(this.mCustomSubTitleView);
            }
        } else {
            View view2 = this.mCustomSubTitleView;
            if (view2 != null) {
                ((ViewGroup) view2.getParent()).removeView(this.mCustomSubTitleView);
                this.mCustomSubTitleView = null;
            }
        }
        updateTitleLayout();
        requestLayout();
    }

    public View seslGetCustomSubtitle() {
        return this.mCustomSubTitleView;
    }

    public CharSequence getSubTitle() {
        TextView textView = this.mExtendedSubTitle;
        if (textView != null) {
            return textView.getText();
        }
        return null;
    }

    private int getStatusbarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return getResources().getDimensionPixelOffset(identifier);
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mHeightProportion = ResourcesCompat.getFloat(getResources(), R.dimen.sesl_appbar_height_proportion);
        updateDefaultHeight();
        updateTitleLayout();
    }

    public void seslSetUseCustomAccessibility(boolean z) {
        this.mIsCustomAccessibility = z;
    }

    public boolean seslIsCustomAccessibility() {
        return this.mIsCustomAccessibility;
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
            CollapsingToolbarLayout.this.updateDefaultHeight();
        }

        /* JADX WARNING: Removed duplicated region for block: B:67:0x0192  */
        /* JADX WARNING: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
        @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onOffsetChanged(com.google.android.material.appbar.AppBarLayout r11, int r12) {
            /*
            // Method dump skipped, instructions count: 463
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.CollapsingToolbarLayout.OffsetUpdateListener.onOffsetChanged(com.google.android.material.appbar.AppBarLayout, int):void");
        }
    }
}
