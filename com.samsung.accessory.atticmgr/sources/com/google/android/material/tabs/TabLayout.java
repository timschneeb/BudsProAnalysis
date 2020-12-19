package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Pools;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
public class TabLayout extends HorizontalScrollView {
    private static final int ANIMATION_DURATION = 300;
    private static final int ANIM_HIDE_DURATION = 400;
    private static final float ANIM_RIPPLE_MINOR_SCALE = 0.95f;
    private static final int ANIM_SHOW_DURATION = 350;
    private static final int BADGE_N_TEXT_SIZE = 11;
    private static final int BADGE_TYPE_DOT = 2;
    private static final int BADGE_TYPE_N = 1;
    private static final int BADGE_TYPE_UNKNOWN = -1;
    static final int DEFAULT_GAP_TEXT_ICON = 8;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
    private static final int DEPTH_TYPE_MAIN = 1;
    private static final int DEPTH_TYPE_SUB = 2;
    static final int FIXED_WRAP_GUTTER_MIN = 16;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    public static final int INDICATOR_GRAVITY_BOTTOM = 0;
    public static final int INDICATOR_GRAVITY_CENTER = 1;
    public static final int INDICATOR_GRAVITY_STRETCH = 3;
    public static final int INDICATOR_GRAVITY_TOP = 2;
    private static final int INVALID_WIDTH = -1;
    private static final int MIN_INDICATOR_WIDTH = 24;
    public static final int MODE_AUTO = 2;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    public static final int TAB_LABEL_VISIBILITY_LABELED = 1;
    public static final int TAB_LABEL_VISIBILITY_UNLABELED = 0;
    private static final int TAB_MIN_WIDTH_MARGIN = 56;
    private static final Pools.Pool<Tab> tabPool = new Pools.SynchronizedPool(16);
    private AdapterChangeListener adapterChangeListener;
    private int contentInsetStart;
    private BaseOnTabSelectedListener currentVpSelectedListener;
    boolean inlineLabel;
    private int mBadgeColor;
    private int mBadgeTextColor;
    private Typeface mBoldTypeface;
    private int mDepthStyle;
    private int mIconTextGap;
    private boolean mIsScaledTextSizeType;
    private Typeface mNormalTypeface;
    private int mRequestedTabWidth;
    private int mSubTabIndicatorHeight;
    private int mSubTabSelectedIndicatorColor;
    private int mTabSelectedIndicatorColor;
    @ViewDebug.ExportedProperty(category = "tablayout")
    int mode;
    private TabLayoutOnPageChangeListener pageChangeListener;
    private PagerAdapter pagerAdapter;
    private DataSetObserver pagerAdapterObserver;
    private final int requestedTabMaxWidth;
    private final int requestedTabMinWidth;
    private ValueAnimator scrollAnimator;
    private final int scrollableTabMinWidth;
    private BaseOnTabSelectedListener selectedListener;
    private final ArrayList<BaseOnTabSelectedListener> selectedListeners;
    private Tab selectedTab;
    private boolean setupViewPagerImplicitly;
    private final SlidingTabIndicator slidingTabIndicator;
    final int tabBackgroundResId;
    int tabGravity;
    ColorStateList tabIconTint;
    PorterDuff.Mode tabIconTintMode;
    int tabIndicatorAnimationDuration;
    boolean tabIndicatorFullWidth;
    int tabIndicatorGravity;
    int tabMaxWidth;
    int tabPaddingBottom;
    int tabPaddingEnd;
    int tabPaddingStart;
    int tabPaddingTop;
    ColorStateList tabRippleColorStateList;
    Drawable tabSelectedIndicator;
    int tabTextAppearance;
    ColorStateList tabTextColors;
    float tabTextMultiLineSize;
    float tabTextSize;
    private final RectF tabViewContentBounds;
    private final Pools.Pool<TabView> tabViewPool;
    private final ArrayList<Tab> tabs;
    boolean unboundedRipple;
    ViewPager viewPager;

    @Deprecated
    public interface BaseOnTabSelectedListener<T extends Tab> {
        void onTabReselected(T t);

        void onTabSelected(T t);

        void onTabUnselected(T t);
    }

    public @interface LabelVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public interface OnTabSelectedListener extends BaseOnTabSelectedListener<Tab> {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabGravity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabIndicatorGravity {
    }

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.tabStyle);
    }

    /* JADX INFO: finally extract failed */
    public TabLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.tabs = new ArrayList<>();
        this.tabViewContentBounds = new RectF();
        this.tabMaxWidth = Integer.MAX_VALUE;
        this.selectedListeners = new ArrayList<>();
        this.tabViewPool = new Pools.SimplePool(12);
        this.mIconTextGap = -1;
        this.mDepthStyle = 1;
        this.mBadgeColor = -1;
        this.mBadgeTextColor = -1;
        this.mRequestedTabWidth = -1;
        this.mSubTabSelectedIndicatorColor = -1;
        this.mSubTabIndicatorHeight = 1;
        this.mIsScaledTextSizeType = false;
        setHorizontalScrollBarEnabled(false);
        this.slidingTabIndicator = new SlidingTabIndicator(context);
        super.addView(this.slidingTabIndicator, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TabLayout, i, SeslMisc.isLightTheme(context) ? R.style.Widget_Design_TabLayout_Light : R.style.Widget_Design_TabLayout);
        if (getBackground() instanceof ColorDrawable) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) getBackground()).getColor()));
            materialShapeDrawable.initializeElevationOverlay(context);
            materialShapeDrawable.setElevation(ViewCompat.getElevation(this));
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        this.slidingTabIndicator.setSelectedIndicatorHeight(obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, -1));
        this.mTabSelectedIndicatorColor = obtainStyledAttributes.getColor(R.styleable.TabLayout_tabIndicatorColor, 0);
        this.slidingTabIndicator.setSelectedIndicatorColor(this.mTabSelectedIndicatorColor);
        this.slidingTabIndicator.setSelectedIndicatorColor(this.mTabSelectedIndicatorColor);
        setSelectedTabIndicator(MaterialResources.getDrawable(context, obtainStyledAttributes, R.styleable.TabLayout_tabIndicator));
        setSelectedTabIndicatorGravity(obtainStyledAttributes.getInt(R.styleable.TabLayout_tabIndicatorGravity, 0));
        setTabIndicatorFullWidth(obtainStyledAttributes.getBoolean(R.styleable.TabLayout_tabIndicatorFullWidth, true));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
        this.tabPaddingBottom = dimensionPixelSize;
        this.tabPaddingEnd = dimensionPixelSize;
        this.tabPaddingTop = dimensionPixelSize;
        this.tabPaddingStart = dimensionPixelSize;
        this.tabPaddingStart = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.tabPaddingStart);
        this.tabPaddingTop = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.tabPaddingTop);
        this.tabPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.tabPaddingEnd);
        this.tabPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.tabPaddingBottom);
        this.tabTextAppearance = obtainStyledAttributes.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(this.tabTextAppearance, androidx.appcompat.R.styleable.TextAppearance);
        try {
            this.tabTextSize = (float) obtainStyledAttributes2.getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, 0);
            this.mIsScaledTextSizeType = obtainStyledAttributes2.getText(androidx.appcompat.R.styleable.TextAppearance_android_textSize).toString().contains("sp");
            this.tabTextColors = MaterialResources.getColorStateList(context, obtainStyledAttributes2, androidx.appcompat.R.styleable.TextAppearance_android_textColor);
            obtainStyledAttributes2.recycle();
            Resources resources = getResources();
            String string = resources.getString(androidx.appcompat.R.string.sesl_font_family_regular);
            this.mBoldTypeface = Typeface.create(string, 1);
            this.mNormalTypeface = Typeface.create(string, 0);
            this.mSubTabIndicatorHeight = resources.getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_indicator_height);
            if (obtainStyledAttributes.hasValue(R.styleable.TabLayout_tabTextColor)) {
                this.tabTextColors = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.TabLayout_tabTextColor);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
                this.tabTextColors = createColorStateList(this.tabTextColors.getDefaultColor(), obtainStyledAttributes.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0));
            }
            this.tabIconTint = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.TabLayout_tabIconTint);
            this.tabIconTintMode = ViewUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.TabLayout_tabIconTintMode, -1), null);
            this.tabRippleColorStateList = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.TabLayout_tabRippleColor);
            this.tabIndicatorAnimationDuration = obtainStyledAttributes.getInt(R.styleable.TabLayout_tabIndicatorAnimationDuration, ANIMATION_DURATION);
            this.requestedTabMinWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
            this.requestedTabMaxWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
            this.tabBackgroundResId = obtainStyledAttributes.getResourceId(R.styleable.TabLayout_tabBackground, 0);
            this.contentInsetStart = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
            this.mode = obtainStyledAttributes.getInt(R.styleable.TabLayout_tabMode, 1);
            this.tabGravity = obtainStyledAttributes.getInt(R.styleable.TabLayout_tabGravity, 0);
            this.inlineLabel = obtainStyledAttributes.getBoolean(R.styleable.TabLayout_tabInlineLabel, false);
            this.unboundedRipple = obtainStyledAttributes.getBoolean(R.styleable.TabLayout_tabUnboundedRipple, false);
            obtainStyledAttributes.recycle();
            this.tabTextMultiLineSize = (float) resources.getDimensionPixelSize(R.dimen.sesl_tab_text_size_2line);
            this.scrollableTabMinWidth = resources.getDimensionPixelSize(R.dimen.sesl_tab_scrollable_min_width);
            applyModeAndGravity();
        } catch (Throwable th) {
            obtainStyledAttributes2.recycle();
            throw th;
        }
    }

    public void setSelectedTabIndicatorColor(int i) {
        int i2;
        this.mTabSelectedIndicatorColor = i;
        Iterator<Tab> it = this.tabs.iterator();
        while (it.hasNext()) {
            SeslAbsIndicatorView seslAbsIndicatorView = it.next().view.mIndicatorView;
            if (seslAbsIndicatorView != null) {
                if (this.mDepthStyle != 2 || (i2 = this.mSubTabSelectedIndicatorColor) == -1) {
                    seslAbsIndicatorView.setSelectedIndicatorColor(i);
                } else {
                    seslAbsIndicatorView.setSelectedIndicatorColor(i2);
                }
                seslAbsIndicatorView.invalidate();
            }
        }
    }

    @Deprecated
    public void setSelectedTabIndicatorHeight(int i) {
        this.slidingTabIndicator.setSelectedIndicatorHeight(i);
    }

    public void setScrollPosition(int i, float f, boolean z) {
        setScrollPosition(i, f, z, true);
    }

    public void setScrollPosition(int i, float f, boolean z, boolean z2) {
        int round = Math.round(((float) i) + f);
        if (round >= 0 && round < this.slidingTabIndicator.getChildCount()) {
            if (z2) {
                this.slidingTabIndicator.setIndicatorPositionFromTabPosition(i, f);
            }
            ValueAnimator valueAnimator = this.scrollAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.scrollAnimator.cancel();
            }
            scrollTo(calculateScrollXForTab(i, f), 0);
            if (z) {
                setSelectedTabView(round, true);
            }
        }
    }

    public void addTab(Tab tab) {
        addTab(tab, this.tabs.isEmpty());
    }

    public void addTab(Tab tab, int i) {
        addTab(tab, i, this.tabs.isEmpty());
    }

    public void addTab(Tab tab, boolean z) {
        addTab(tab, this.tabs.size(), z);
    }

    public void addTab(Tab tab, int i, boolean z) {
        if (tab.parent == this) {
            configureTab(tab, i);
            addTabView(tab);
            if (z) {
                tab.select();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    private void addTabFromItemView(TabItem tabItem) {
        Tab newTab = newTab();
        if (tabItem.text != null) {
            newTab.setText(tabItem.text);
        }
        if (tabItem.icon != null) {
            newTab.setIcon(tabItem.icon);
        }
        if (tabItem.customLayout != 0) {
            newTab.setCustomView(tabItem.customLayout);
        }
        if (!TextUtils.isEmpty(tabItem.getContentDescription())) {
            newTab.setContentDescription(tabItem.getContentDescription());
        }
        addTab(newTab);
    }

    @Deprecated
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        setOnTabSelectedListener((BaseOnTabSelectedListener) onTabSelectedListener);
    }

    @Deprecated
    public void setOnTabSelectedListener(BaseOnTabSelectedListener baseOnTabSelectedListener) {
        BaseOnTabSelectedListener baseOnTabSelectedListener2 = this.selectedListener;
        if (baseOnTabSelectedListener2 != null) {
            removeOnTabSelectedListener(baseOnTabSelectedListener2);
        }
        this.selectedListener = baseOnTabSelectedListener;
        if (baseOnTabSelectedListener != null) {
            addOnTabSelectedListener(baseOnTabSelectedListener);
        }
    }

    public void addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        addOnTabSelectedListener((BaseOnTabSelectedListener) onTabSelectedListener);
    }

    @Deprecated
    public void addOnTabSelectedListener(BaseOnTabSelectedListener baseOnTabSelectedListener) {
        if (!this.selectedListeners.contains(baseOnTabSelectedListener)) {
            this.selectedListeners.add(baseOnTabSelectedListener);
        }
    }

    public void removeOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        removeOnTabSelectedListener((BaseOnTabSelectedListener) onTabSelectedListener);
    }

    @Deprecated
    public void removeOnTabSelectedListener(BaseOnTabSelectedListener baseOnTabSelectedListener) {
        this.selectedListeners.remove(baseOnTabSelectedListener);
    }

    public void clearOnTabSelectedListeners() {
        this.selectedListeners.clear();
    }

    public Tab newTab() {
        Tab createTabFromPool = createTabFromPool();
        createTabFromPool.parent = this;
        createTabFromPool.view = createTabView(createTabFromPool);
        return createTabFromPool;
    }

    /* access modifiers changed from: protected */
    public Tab createTabFromPool() {
        Tab acquire = tabPool.acquire();
        return acquire == null ? new Tab() : acquire;
    }

    /* access modifiers changed from: protected */
    public boolean releaseFromTabPool(Tab tab) {
        return tabPool.release(tab);
    }

    public int getTabCount() {
        return this.tabs.size();
    }

    public Tab getTabAt(int i) {
        if (i < 0 || i >= getTabCount()) {
            return null;
        }
        return this.tabs.get(i);
    }

    public int getSelectedTabPosition() {
        Tab tab = this.selectedTab;
        if (tab != null) {
            return tab.getPosition();
        }
        return -1;
    }

    public void removeTab(Tab tab) {
        if (tab.parent == this) {
            removeTabAt(tab.getPosition());
            return;
        }
        throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
    }

    public void removeTabAt(int i) {
        Tab tab = this.selectedTab;
        int position = tab != null ? tab.getPosition() : 0;
        removeTabViewAt(i);
        Tab remove = this.tabs.remove(i);
        if (remove != null) {
            remove.reset();
            releaseFromTabPool(remove);
        }
        int size = this.tabs.size();
        for (int i2 = i; i2 < size; i2++) {
            this.tabs.get(i2).setPosition(i2);
        }
        if (position == i) {
            selectTab(this.tabs.isEmpty() ? null : this.tabs.get(Math.max(0, i - 1)));
        }
    }

    public void removeAllTabs() {
        for (int childCount = this.slidingTabIndicator.getChildCount() - 1; childCount >= 0; childCount--) {
            removeTabViewAt(childCount);
        }
        Iterator<Tab> it = this.tabs.iterator();
        while (it.hasNext()) {
            Tab next = it.next();
            it.remove();
            next.reset();
            releaseFromTabPool(next);
        }
        this.selectedTab = null;
    }

    public void setTabMode(int i) {
        if (i != this.mode) {
            this.mode = i;
            applyModeAndGravity();
            updateBadgePosition();
        }
    }

    public int getTabMode() {
        return this.mode;
    }

    public void setTabGravity(int i) {
        if (this.tabGravity != i) {
            this.tabGravity = i;
            applyModeAndGravity();
        }
    }

    public int getTabGravity() {
        return this.tabGravity;
    }

    public void setSelectedTabIndicatorGravity(int i) {
        if (this.tabIndicatorGravity != i) {
            this.tabIndicatorGravity = i;
            ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
        }
    }

    public int getTabIndicatorGravity() {
        return this.tabIndicatorGravity;
    }

    public void setTabIndicatorFullWidth(boolean z) {
        this.tabIndicatorFullWidth = z;
        ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
    }

    public boolean isTabIndicatorFullWidth() {
        return this.tabIndicatorFullWidth;
    }

    public void setInlineLabel(boolean z) {
        if (this.inlineLabel != z) {
            this.inlineLabel = z;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                View childAt = this.slidingTabIndicator.getChildAt(i);
                if (childAt instanceof TabView) {
                    ((TabView) childAt).updateOrientation();
                }
            }
            applyModeAndGravity();
        }
    }

    public void setInlineLabelResource(int i) {
        setInlineLabel(getResources().getBoolean(i));
    }

    public boolean isInlineLabel() {
        return this.inlineLabel;
    }

    public void setUnboundedRipple(boolean z) {
        if (this.unboundedRipple != z) {
            this.unboundedRipple = z;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                View childAt = this.slidingTabIndicator.getChildAt(i);
                if (childAt instanceof TabView) {
                    ((TabView) childAt).updateBackgroundDrawable(getContext());
                }
            }
        }
    }

    public void setUnboundedRippleResource(int i) {
        setUnboundedRipple(getResources().getBoolean(i));
    }

    public boolean hasUnboundedRipple() {
        return this.unboundedRipple;
    }

    public void setTabTextColors(ColorStateList colorStateList) {
        if (this.tabTextColors != colorStateList) {
            this.tabTextColors = colorStateList;
            updateAllTabs();
        }
    }

    public ColorStateList getTabTextColors() {
        return this.tabTextColors;
    }

    public void setTabTextColors(int i, int i2) {
        setTabTextColors(createColorStateList(i, i2));
    }

    public void setTabIconTint(ColorStateList colorStateList) {
        if (this.tabIconTint != colorStateList) {
            this.tabIconTint = colorStateList;
            updateAllTabs();
        }
    }

    public void setTabIconTintResource(int i) {
        setTabIconTint(AppCompatResources.getColorStateList(getContext(), i));
    }

    public ColorStateList getTabIconTint() {
        return this.tabIconTint;
    }

    public ColorStateList getTabRippleColor() {
        return this.tabRippleColorStateList;
    }

    public void setTabRippleColor(ColorStateList colorStateList) {
        if (this.tabRippleColorStateList != colorStateList) {
            this.tabRippleColorStateList = colorStateList;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                View childAt = this.slidingTabIndicator.getChildAt(i);
                if (childAt instanceof TabView) {
                    ((TabView) childAt).updateBackgroundDrawable(getContext());
                }
            }
        }
    }

    public void setTabRippleColorResource(int i) {
        setTabRippleColor(AppCompatResources.getColorStateList(getContext(), i));
    }

    public Drawable getTabSelectedIndicator() {
        return this.tabSelectedIndicator;
    }

    public void setSelectedTabIndicator(Drawable drawable) {
        if (this.tabSelectedIndicator != drawable) {
            this.tabSelectedIndicator = drawable;
            ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
        }
    }

    public void setSelectedTabIndicator(int i) {
        if (i != 0) {
            setSelectedTabIndicator(AppCompatResources.getDrawable(getContext(), i));
        } else {
            setSelectedTabIndicator((Drawable) null);
        }
    }

    public void setupWithViewPager(ViewPager viewPager2) {
        setupWithViewPager(viewPager2, true);
    }

    public void setupWithViewPager(ViewPager viewPager2, boolean z) {
        setupWithViewPager(viewPager2, z, false);
    }

    private void setupWithViewPager(ViewPager viewPager2, boolean z, boolean z2) {
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 != null) {
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = this.pageChangeListener;
            if (tabLayoutOnPageChangeListener != null) {
                viewPager3.removeOnPageChangeListener(tabLayoutOnPageChangeListener);
            }
            AdapterChangeListener adapterChangeListener2 = this.adapterChangeListener;
            if (adapterChangeListener2 != null) {
                this.viewPager.removeOnAdapterChangeListener(adapterChangeListener2);
            }
        }
        BaseOnTabSelectedListener baseOnTabSelectedListener = this.currentVpSelectedListener;
        if (baseOnTabSelectedListener != null) {
            removeOnTabSelectedListener(baseOnTabSelectedListener);
            this.currentVpSelectedListener = null;
        }
        if (viewPager2 != null) {
            this.viewPager = viewPager2;
            if (this.pageChangeListener == null) {
                this.pageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            this.pageChangeListener.reset();
            viewPager2.addOnPageChangeListener(this.pageChangeListener);
            this.currentVpSelectedListener = new ViewPagerOnTabSelectedListener(viewPager2);
            addOnTabSelectedListener(this.currentVpSelectedListener);
            PagerAdapter adapter = viewPager2.getAdapter();
            if (adapter != null) {
                setPagerAdapter(adapter, z);
            }
            if (this.adapterChangeListener == null) {
                this.adapterChangeListener = new AdapterChangeListener();
            }
            this.adapterChangeListener.setAutoRefresh(z);
            viewPager2.addOnAdapterChangeListener(this.adapterChangeListener);
            setScrollPosition(viewPager2.getCurrentItem(), 0.0f, true);
        } else {
            this.viewPager = null;
            setPagerAdapter(null, false);
        }
        this.setupViewPagerImplicitly = z2;
    }

    @Deprecated
    public void setTabsFromPagerAdapter(PagerAdapter pagerAdapter2) {
        setPagerAdapter(pagerAdapter2, false);
    }

    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (int i = 0; i < getTabCount(); i++) {
            Tab tabAt = getTabAt(i);
            if (!(tabAt == null || tabAt.view == null)) {
                if (tabAt.view.mMainTabTouchBackground != null) {
                    tabAt.view.mMainTabTouchBackground.setAlpha(0.0f);
                }
                if (tabAt.view.mIndicatorView != null) {
                    if (getSelectedTabPosition() == i) {
                        tabAt.view.mIndicatorView.setShow();
                    } else {
                        tabAt.view.mIndicatorView.setHide();
                    }
                }
            }
        }
        MaterialShapeUtils.setParentAbsoluteElevation(this);
        if (this.viewPager == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                setupWithViewPager((ViewPager) parent, true, true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.setupViewPagerImplicitly) {
            setupWithViewPager(null);
            this.setupViewPagerImplicitly = false;
        }
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.slidingTabIndicator.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    /* access modifiers changed from: package-private */
    public void setPagerAdapter(PagerAdapter pagerAdapter2, boolean z) {
        DataSetObserver dataSetObserver;
        PagerAdapter pagerAdapter3 = this.pagerAdapter;
        if (!(pagerAdapter3 == null || (dataSetObserver = this.pagerAdapterObserver) == null)) {
            pagerAdapter3.unregisterDataSetObserver(dataSetObserver);
        }
        this.pagerAdapter = pagerAdapter2;
        if (z && pagerAdapter2 != null) {
            if (this.pagerAdapterObserver == null) {
                this.pagerAdapterObserver = new PagerAdapterObserver();
            }
            pagerAdapter2.registerDataSetObserver(this.pagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    /* access modifiers changed from: package-private */
    public void populateFromPagerAdapter() {
        int currentItem;
        removeAllTabs();
        PagerAdapter pagerAdapter2 = this.pagerAdapter;
        if (pagerAdapter2 != null) {
            int count = pagerAdapter2.getCount();
            for (int i = 0; i < count; i++) {
                addTab(newTab().setText(this.pagerAdapter.getPageTitle(i)), false);
            }
            ViewPager viewPager2 = this.viewPager;
            if (viewPager2 != null && count > 0 && (currentItem = viewPager2.getCurrentItem()) != getSelectedTabPosition() && currentItem < getTabCount()) {
                selectTab(getTabAt(currentItem), true, true);
            }
        }
    }

    private void updateAllTabs() {
        int size = this.tabs.size();
        for (int i = 0; i < size; i++) {
            this.tabs.get(i).updateView();
        }
    }

    private TabView createTabView(Tab tab) {
        Pools.Pool<TabView> pool = this.tabViewPool;
        TabView acquire = pool != null ? pool.acquire() : null;
        if (acquire == null) {
            acquire = new TabView(getContext());
        }
        if (acquire.mMainTabTouchBackground != null) {
            acquire.mMainTabTouchBackground.setAlpha(0.0f);
        }
        acquire.setTab(tab);
        acquire.setFocusable(true);
        acquire.setMinimumWidth(getTabMinWidth());
        if (TextUtils.isEmpty(tab.contentDesc)) {
            acquire.setContentDescription(tab.text);
        } else {
            acquire.setContentDescription(tab.contentDesc);
        }
        return acquire;
    }

    private void configureTab(Tab tab, int i) {
        tab.setPosition(i);
        this.tabs.add(i, tab);
        int size = this.tabs.size();
        while (true) {
            i++;
            if (i < size) {
                this.tabs.get(i).setPosition(i);
            } else {
                return;
            }
        }
    }

    private void addTabView(Tab tab) {
        TabView tabView = tab.view;
        tabView.setSelected(false);
        tabView.setActivated(false);
        this.slidingTabIndicator.addView(tabView, tab.getPosition(), createLayoutParamsForTabs());
    }

    public void addView(View view) {
        addViewInternal(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view, int i) {
        addViewInternal(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    private void addViewInternal(View view) {
        if (view instanceof TabItem) {
            addTabFromItemView((TabItem) view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private LinearLayout.LayoutParams createLayoutParamsForTabs() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        updateTabViewLayoutParams(layoutParams);
        return layoutParams;
    }

    private void updateTabViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (this.mode == 1 && this.tabGravity == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
    }

    public void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View childAt = this.slidingTabIndicator.getChildAt(i);
            if (childAt instanceof TabView) {
                ((TabView) childAt).drawBackground(canvas);
            }
        }
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0070, code lost:
        if (r0 != 2) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007b, code lost:
        if (r7.getMeasuredWidth() != getMeasuredWidth()) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007d, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0087, code lost:
        if (r7.getMeasuredWidth() < getMeasuredWidth()) goto L_0x007d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r7, int r8) {
        /*
        // Method dump skipped, instructions count: 171
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.onMeasure(int, int):void");
    }

    private void removeTabViewAt(int i) {
        TabView tabView = (TabView) this.slidingTabIndicator.getChildAt(i);
        this.slidingTabIndicator.removeViewAt(i);
        if (tabView != null) {
            tabView.reset();
            this.tabViewPool.release(tabView);
        }
        requestLayout();
    }

    private void animateToTab(int i) {
        if (i != -1) {
            if (getWindowToken() == null || !ViewCompat.isLaidOut(this) || this.slidingTabIndicator.childrenNeedLayout()) {
                setScrollPosition(i, 0.0f, true);
                return;
            }
            int scrollX = getScrollX();
            int calculateScrollXForTab = calculateScrollXForTab(i, 0.0f);
            if (scrollX != calculateScrollXForTab) {
                ensureScrollAnimator();
                this.scrollAnimator.setIntValues(scrollX, calculateScrollXForTab);
                this.scrollAnimator.start();
            }
            this.slidingTabIndicator.animateIndicatorToPosition(i, this.tabIndicatorAnimationDuration);
        }
    }

    private void ensureScrollAnimator() {
        if (this.scrollAnimator == null) {
            this.scrollAnimator = new ValueAnimator();
            this.scrollAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.scrollAnimator.setDuration((long) this.tabIndicatorAnimationDuration);
            this.scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.google.android.material.tabs.TabLayout.AnonymousClass1 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TabLayout.this.scrollTo(((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void setScrollAnimatorListener(Animator.AnimatorListener animatorListener) {
        ensureScrollAnimator();
        this.scrollAnimator.addListener(animatorListener);
    }

    private void setSelectedTabView(int i, boolean z) {
        int childCount = this.slidingTabIndicator.getChildCount();
        if (i < childCount) {
            int i2 = 0;
            while (true) {
                boolean z2 = true;
                if (i2 >= childCount) {
                    break;
                }
                View childAt = this.slidingTabIndicator.getChildAt(i2);
                childAt.setSelected(i2 == i);
                if (i2 != i) {
                    z2 = false;
                }
                childAt.setActivated(z2);
                i2++;
            }
            this.tabs.get(i).view.setSelected(true);
            for (int i3 = 0; i3 < getTabCount(); i3++) {
                TabView tabView = this.tabs.get(i3).view;
                if (i3 == i) {
                    if (tabView.textView != null) {
                        startTextColorChangeAnimation(tabView.textView, getSelectedTabTextColor());
                        tabView.textView.setTypeface(this.mBoldTypeface);
                        tabView.textView.setSelected(true);
                    }
                    if (tabView.mIndicatorView != null) {
                        if (!z) {
                            this.tabs.get(i3).view.mIndicatorView.setReleased();
                        } else if (tabView.mIndicatorView.getAlpha() != 1.0f) {
                            tabView.mIndicatorView.setShow();
                        }
                    }
                } else {
                    if (tabView.mIndicatorView != null) {
                        tabView.mIndicatorView.setHide();
                    }
                    if (tabView.textView != null) {
                        tabView.textView.setTypeface(this.mNormalTypeface);
                        startTextColorChangeAnimation(tabView.textView, this.tabTextColors.getDefaultColor());
                        tabView.textView.setSelected(false);
                    }
                }
            }
        }
    }

    public void selectTab(Tab tab) {
        selectTab(tab, true);
    }

    public void selectTab(Tab tab, boolean z) {
        selectTab(tab, z, true);
    }

    private void selectTab(Tab tab, boolean z, boolean z2) {
        ViewPager viewPager2;
        if (tab == null || tab.view.isEnabled() || (viewPager2 = this.viewPager) == null) {
            Tab tab2 = this.selectedTab;
            if (tab2 != tab) {
                int position = tab != null ? tab.getPosition() : -1;
                if (z) {
                    if ((tab2 == null || tab2.getPosition() == -1) && position != -1) {
                        setScrollPosition(position, 0.0f, true);
                    } else {
                        animateToTab(position);
                    }
                    if (position != -1) {
                        setSelectedTabView(position, z2);
                    }
                }
                this.selectedTab = tab;
                if (tab2 != null) {
                    dispatchTabUnselected(tab2);
                }
                if (tab != null) {
                    dispatchTabSelected(tab);
                }
            } else if (tab2 != null) {
                dispatchTabReselected(tab);
                animateToTab(tab.getPosition());
            }
        } else {
            viewPager2.setCurrentItem(getSelectedTabPosition());
        }
    }

    private void dispatchTabSelected(Tab tab) {
        for (int size = this.selectedListeners.size() - 1; size >= 0; size--) {
            this.selectedListeners.get(size).onTabSelected(tab);
        }
    }

    private void dispatchTabUnselected(Tab tab) {
        for (int size = this.selectedListeners.size() - 1; size >= 0; size--) {
            this.selectedListeners.get(size).onTabUnselected(tab);
        }
    }

    private void dispatchTabReselected(Tab tab) {
        for (int size = this.selectedListeners.size() - 1; size >= 0; size--) {
            this.selectedListeners.get(size).onTabReselected(tab);
        }
    }

    private int calculateScrollXForTab(int i, float f) {
        int i2 = this.mode;
        int i3 = 0;
        if (i2 != 0 && i2 != 2) {
            return 0;
        }
        View childAt = this.slidingTabIndicator.getChildAt(i);
        int i4 = i + 1;
        View childAt2 = i4 < this.slidingTabIndicator.getChildCount() ? this.slidingTabIndicator.getChildAt(i4) : null;
        int width = childAt != null ? childAt.getWidth() : 0;
        if (childAt2 != null) {
            i3 = childAt2.getWidth();
        }
        int left = (childAt.getLeft() + (width / 2)) - (getWidth() / 2);
        int i5 = (int) (((float) (width + i3)) * 0.5f * f);
        return ViewCompat.getLayoutDirection(this) == 0 ? left + i5 : left - i5;
    }

    private void applyModeAndGravity() {
        ViewCompat.setPaddingRelative(this.slidingTabIndicator, 0, 0, 0, 0);
        int i = this.mode;
        if (i == 0) {
            this.slidingTabIndicator.setGravity(GravityCompat.START);
        } else if (i == 1 || i == 2) {
            this.slidingTabIndicator.setGravity(1);
        }
        updateTabViews(true);
    }

    /* access modifiers changed from: package-private */
    public void updateTabViews(boolean z) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View childAt = this.slidingTabIndicator.getChildAt(i);
            childAt.setMinimumWidth(getTabMinWidth());
            updateTabViewLayoutParams((LinearLayout.LayoutParams) childAt.getLayoutParams());
            if (z) {
                childAt.requestLayout();
            }
        }
        updateBadgePosition();
    }

    public static class Tab {
        public static final int INVALID_POSITION = -1;
        private CharSequence contentDesc;
        private View customView;
        private Drawable icon;
        private int labelVisibilityMode = 1;
        public TabLayout parent;
        private int position = -1;
        private Object tag;
        private CharSequence text;
        public TabView view;

        public Object getTag() {
            return this.tag;
        }

        public Tab setTag(Object obj) {
            this.tag = obj;
            return this;
        }

        public View getCustomView() {
            return this.customView;
        }

        public Tab setCustomView(View view2) {
            if (this.view.textView != null) {
                this.view.removeAllViews();
            }
            this.customView = view2;
            updateView();
            return this;
        }

        public Tab setCustomView(int i) {
            return setCustomView(LayoutInflater.from(this.view.getContext()).inflate(i, (ViewGroup) this.view, false));
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public int getPosition() {
            return this.position;
        }

        /* access modifiers changed from: package-private */
        public void setPosition(int i) {
            this.position = i;
        }

        public CharSequence getText() {
            return this.text;
        }

        public Tab setIcon(Drawable drawable) {
            this.icon = drawable;
            if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
                this.parent.updateTabViews(true);
            }
            updateView();
            if (BadgeUtils.USE_COMPAT_PARENT && this.view.hasBadgeDrawable() && this.view.badgeDrawable.isVisible()) {
                this.view.invalidate();
            }
            return this;
        }

        public Tab setIcon(int i) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setIcon(AppCompatResources.getDrawable(tabLayout.getContext(), i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setText(CharSequence charSequence) {
            if (TextUtils.isEmpty(this.contentDesc) && !TextUtils.isEmpty(charSequence)) {
                this.view.setContentDescription(charSequence);
            }
            this.text = charSequence;
            updateView();
            return this;
        }

        public Tab setText(int i) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setText(tabLayout.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public BadgeDrawable getOrCreateBadge() {
            return this.view.getOrCreateBadge();
        }

        public void removeBadge() {
            this.view.removeBadge();
        }

        public BadgeDrawable getBadge() {
            return this.view.getBadge();
        }

        public Tab setTabLabelVisibility(int i) {
            this.labelVisibilityMode = i;
            if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
                this.parent.updateTabViews(true);
            }
            updateView();
            if (BadgeUtils.USE_COMPAT_PARENT && this.view.hasBadgeDrawable() && this.view.badgeDrawable.isVisible()) {
                this.view.invalidate();
            }
            return this;
        }

        public int getTabLabelVisibility() {
            return this.labelVisibilityMode;
        }

        public void select() {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                tabLayout.selectTab(this);
                return;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public boolean isSelected() {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return tabLayout.getSelectedTabPosition() == this.position;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setContentDescription(int i) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setContentDescription(tabLayout.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setContentDescription(CharSequence charSequence) {
            this.contentDesc = charSequence;
            updateView();
            return this;
        }

        public CharSequence getContentDescription() {
            TabView tabView = this.view;
            if (tabView == null) {
                return null;
            }
            return tabView.getContentDescription();
        }

        /* access modifiers changed from: package-private */
        public void updateView() {
            TabView tabView = this.view;
            if (tabView != null) {
                tabView.update();
            }
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.parent = null;
            this.view = null;
            this.tag = null;
            this.icon = null;
            this.text = null;
            this.contentDesc = null;
            this.position = -1;
            this.customView = null;
        }

        public TextView seslGetTextView() {
            TabView tabView;
            if (this.customView != null || (tabView = this.view) == null) {
                return null;
            }
            return tabView.textView;
        }
    }

    public final class TabView extends LinearLayout {
        private View badgeAnchorView;
        private BadgeDrawable badgeDrawable;
        private Drawable baseBackgroundDrawable;
        private ImageView customIconView;
        private TextView customTextView;
        private View customView;
        private int defaultMaxLines = 2;
        private ImageView iconView;
        private TextView mDotBadgeView;
        private int mIconSize;
        private SeslAbsIndicatorView mIndicatorView;
        private boolean mIsCallPerformClick;
        private View mMainTabTouchBackground;
        private TextView mNBadgeView;
        View.OnKeyListener mTabViewKeyListener = new View.OnKeyListener() {
            /* class com.google.android.material.tabs.TabLayout.TabView.AnonymousClass1 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        };
        private RelativeLayout mTextParentView;
        private Tab tab;
        private TextView textView;

        public TabView(Context context) {
            super(context);
            updateBackgroundDrawable(context);
            setGravity(17);
            setOrientation(!TabLayout.this.inlineLabel ? 1 : 0);
            setClickable(true);
            ViewCompat.setAccessibilityDelegate(this, null);
            setOnKeyListener(this.mTabViewKeyListener);
            if (TabLayout.this.mDepthStyle == 1) {
                ViewCompat.setPaddingRelative(this, 0, TabLayout.this.tabPaddingTop, 0, TabLayout.this.tabPaddingBottom);
            }
            this.mIconSize = getResources().getDimensionPixelOffset(R.dimen.sesl_tab_icon_size);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void updateBackgroundDrawable(Context context) {
            if (TabLayout.this.tabBackgroundResId == 0 || TabLayout.this.mDepthStyle == 2) {
                this.baseBackgroundDrawable = null;
                return;
            }
            this.baseBackgroundDrawable = AppCompatResources.getDrawable(context, TabLayout.this.tabBackgroundResId);
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable != null && drawable.isStateful()) {
                this.baseBackgroundDrawable.setState(getDrawableState());
            }
            ViewCompat.setBackground(this, this.baseBackgroundDrawable);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void drawBackground(Canvas canvas) {
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable != null) {
                drawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
                this.baseBackgroundDrawable.draw(canvas);
            }
        }

        /* access modifiers changed from: protected */
        public void drawableStateChanged() {
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable != null && drawable.isStateful()) {
                this.baseBackgroundDrawable.setState(drawableState);
            }
        }

        public boolean performClick() {
            if (this.mIsCallPerformClick) {
                this.mIsCallPerformClick = false;
                return true;
            }
            boolean performClick = super.performClick();
            if (this.tab == null) {
                return performClick;
            }
            if (!performClick) {
                playSoundEffect(0);
            }
            this.tab.select();
            return true;
        }

        public void setSelected(boolean z) {
            if (isEnabled()) {
                boolean z2 = isSelected() != z;
                super.setSelected(z);
                if (z2 && z && Build.VERSION.SDK_INT < 16) {
                    sendAccessibilityEvent(4);
                }
                TextView textView2 = this.textView;
                if (textView2 != null) {
                    textView2.setSelected(z);
                }
                ImageView imageView = this.iconView;
                if (imageView != null) {
                    imageView.setSelected(z);
                }
                View view = this.customView;
                if (view != null) {
                    view.setSelected(z);
                }
                SeslAbsIndicatorView seslAbsIndicatorView = this.mIndicatorView;
                if (seslAbsIndicatorView != null) {
                    seslAbsIndicatorView.setSelected(z);
                }
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(ActionBar.Tab.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(ActionBar.Tab.class.getName());
            BadgeDrawable badgeDrawable2 = this.badgeDrawable;
            if (badgeDrawable2 != null && badgeDrawable2.isVisible()) {
                CharSequence contentDescription = getContentDescription();
                accessibilityNodeInfo.setContentDescription(((Object) contentDescription) + ", " + ((Object) this.badgeDrawable.getContentDescription()));
            }
            TextView textView2 = this.mNBadgeView;
            if (textView2 != null && textView2.getVisibility() == 0 && this.mNBadgeView.getContentDescription() != null) {
                accessibilityNodeInfo.setContentDescription(((Object) getContentDescription()) + ", " + ((Object) this.mNBadgeView.getContentDescription()));
            }
        }

        public void onMeasure(int i, int i2) {
            Layout layout;
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            int tabMaxWidth = TabLayout.this.getTabMaxWidth();
            if (TabLayout.this.mRequestedTabWidth != -1) {
                i = View.MeasureSpec.makeMeasureSpec(TabLayout.this.mRequestedTabWidth, 1073741824);
            } else if (tabMaxWidth > 0 && (mode == 0 || size > tabMaxWidth)) {
                i = View.MeasureSpec.makeMeasureSpec(TabLayout.this.tabMaxWidth, Integer.MIN_VALUE);
            }
            super.onMeasure(i, i2);
            if (this.textView != null && this.customView == null) {
                float f = TabLayout.this.tabTextSize;
                TabLayout.this.checkMaxFontScale(this.textView, (int) f);
                int i3 = this.defaultMaxLines;
                ImageView imageView = this.iconView;
                boolean z = true;
                if (imageView == null || imageView.getVisibility() != 0) {
                    TextView textView2 = this.textView;
                    if (textView2 != null && textView2.getLineCount() > 1) {
                        f = TabLayout.this.tabTextMultiLineSize;
                    }
                } else {
                    i3 = 1;
                }
                float textSize = this.textView.getTextSize();
                int lineCount = this.textView.getLineCount();
                int maxLines = TextViewCompat.getMaxLines(this.textView);
                int i4 = (f > textSize ? 1 : (f == textSize ? 0 : -1));
                if (i4 != 0 || (maxLines >= 0 && i3 != maxLines)) {
                    if (TabLayout.this.mode == 1 && i4 > 0 && lineCount == 1 && ((layout = this.textView.getLayout()) == null || approximateLineWidth(layout, 0, f) > ((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())))) {
                        z = false;
                    }
                    if (z) {
                        this.textView.setTextSize(0, f);
                        TabLayout.this.checkMaxFontScale(this.textView, (int) f);
                        this.textView.setMaxLines(i3);
                        super.onMeasure(i, i2);
                    }
                }
            }
            if (this.customTextView == null && this.mTextParentView != null && this.textView != null && this.tab != null && TabLayout.this.mode == 0 && TabLayout.this.mDepthStyle == 2) {
                if (tabMaxWidth > 0) {
                    this.textView.measure(tabMaxWidth, 0);
                } else {
                    this.textView.measure(0, 0);
                }
                int measuredWidth = this.textView.getMeasuredWidth();
                ViewGroup.LayoutParams layoutParams = this.mTextParentView.getLayoutParams();
                layoutParams.width = measuredWidth + (getContext().getResources().getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_side_space) * 2);
                this.mTextParentView.setLayoutParams(layoutParams);
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(layoutParams.width, Integer.MIN_VALUE), i2);
            }
        }

        /* access modifiers changed from: package-private */
        public void setTab(Tab tab2) {
            if (tab2 != this.tab) {
                this.tab = tab2;
                update();
            }
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            setTab(null);
            setSelected(false);
        }

        /* access modifiers changed from: package-private */
        public final void update() {
            RelativeLayout relativeLayout;
            int i;
            RelativeLayout relativeLayout2;
            Tab tab2 = this.tab;
            Drawable drawable = null;
            View customView2 = tab2 != null ? tab2.getCustomView() : null;
            if (customView2 != null) {
                ViewParent parent = customView2.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView2);
                    }
                    addView(customView2);
                }
                this.customView = customView2;
                TextView textView2 = this.textView;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                }
                ImageView imageView = this.iconView;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.iconView.setImageDrawable(null);
                }
                this.customTextView = (TextView) customView2.findViewById(16908308);
                TextView textView3 = this.customTextView;
                if (textView3 != null) {
                    this.defaultMaxLines = TextViewCompat.getMaxLines(textView3);
                }
                this.customIconView = (ImageView) customView2.findViewById(16908294);
            } else {
                View view = this.customView;
                if (view != null) {
                    removeView(view);
                    this.customView = null;
                }
                this.customTextView = null;
                this.customIconView = null;
            }
            boolean z = false;
            if (this.customView == null) {
                if (this.textView == null) {
                    Context context = getContext();
                    int i2 = -2;
                    if (TabLayout.this.mDepthStyle == 2) {
                        relativeLayout2 = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.sesl_tabs_sub_tab_layout, (ViewGroup) this, false);
                        if (TabLayout.this.mode != 0) {
                            i2 = -1;
                        }
                        i = TabLayout.this.mSubTabIndicatorHeight;
                        this.mIndicatorView = (SeslAbsIndicatorView) relativeLayout2.findViewById(R.id.indicator);
                        if (!(this.mIndicatorView == null || TabLayout.this.mSubTabSelectedIndicatorColor == -1)) {
                            this.mIndicatorView.setSelectedIndicatorColor(TabLayout.this.mSubTabSelectedIndicatorColor);
                        }
                    } else {
                        RelativeLayout relativeLayout3 = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.sesl_tabs_main_tab_layout, (ViewGroup) this, false);
                        if (this.tab.icon == null) {
                            i2 = -1;
                        }
                        this.mIndicatorView = (SeslAbsIndicatorView) relativeLayout3.findViewById(R.id.indicator);
                        SeslAbsIndicatorView seslAbsIndicatorView = this.mIndicatorView;
                        if (seslAbsIndicatorView != null) {
                            seslAbsIndicatorView.setSelectedIndicatorColor(TabLayout.this.mTabSelectedIndicatorColor);
                        }
                        this.mMainTabTouchBackground = relativeLayout3.findViewById(R.id.main_tab_touch_background);
                        if (this.mMainTabTouchBackground != null && this.tab.icon == null) {
                            ViewCompat.setBackground(this.mMainTabTouchBackground, ContextCompat.getDrawable(context, SeslMisc.isLightTheme(context) ? R.drawable.sesl_tablayout_maintab_touch_background_light : R.drawable.sesl_tablayout_maintab_touch_background_dark));
                            this.mMainTabTouchBackground.setAlpha(0.0f);
                        }
                        relativeLayout2 = relativeLayout3;
                        i = -1;
                    }
                    relativeLayout2.getLayoutParams().width = i2;
                    addView(relativeLayout2, i2, i);
                    this.textView = (TextView) relativeLayout2.findViewById(R.id.title);
                    this.mTextParentView = relativeLayout2;
                    this.defaultMaxLines = TextViewCompat.getMaxLines(this.textView);
                }
                if (this.iconView == null && (relativeLayout = this.mTextParentView) != null) {
                    this.iconView = (ImageView) relativeLayout.findViewById(R.id.icon);
                }
                if (!(tab2 == null || tab2.getIcon() == null)) {
                    drawable = DrawableCompat.wrap(tab2.getIcon()).mutate();
                }
                if (drawable != null) {
                    DrawableCompat.setTintList(drawable, TabLayout.this.tabIconTint);
                    if (TabLayout.this.tabIconTintMode != null) {
                        DrawableCompat.setTintMode(drawable, TabLayout.this.tabIconTintMode);
                    }
                }
                TextViewCompat.setTextAppearance(this.textView, TabLayout.this.tabTextAppearance);
                TabLayout tabLayout = TabLayout.this;
                tabLayout.checkMaxFontScale(this.textView, (int) tabLayout.tabTextSize);
                if (TabLayout.this.tabTextColors != null) {
                    this.textView.setTextColor(TabLayout.this.tabTextColors);
                }
                updateTextAndIcon(this.textView, this.iconView);
                tryUpdateBadgeAnchor();
                addOnLayoutChangeListener(this.iconView);
                addOnLayoutChangeListener(this.textView);
            } else if (!(this.customTextView == null && this.customIconView == null)) {
                updateTextAndIcon(this.customTextView, this.customIconView);
            }
            if (tab2 != null && !TextUtils.isEmpty(tab2.contentDesc)) {
                setContentDescription(tab2.contentDesc);
            }
            if (tab2 != null && tab2.isSelected()) {
                z = true;
            }
            setSelected(z);
        }

        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.google.android.material.tabs.TabLayout$TabView */
        /* JADX WARN: Multi-variable type inference failed */
        private void inflateAndAddDefaultIconView() {
            FrameLayout frameLayout;
            if (BadgeUtils.USE_COMPAT_PARENT) {
                frameLayout = createPreApi18BadgeAnchorRoot();
                addView(frameLayout, 0);
            } else {
                frameLayout = this;
            }
            this.iconView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.sesl_layout_tab_icon, (ViewGroup) frameLayout, false);
            frameLayout.addView(this.iconView, 0);
        }

        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.google.android.material.tabs.TabLayout$TabView */
        /* JADX WARN: Multi-variable type inference failed */
        private void inflateAndAddDefaultTextView() {
            FrameLayout frameLayout;
            if (BadgeUtils.USE_COMPAT_PARENT) {
                frameLayout = createPreApi18BadgeAnchorRoot();
                addView(frameLayout);
            } else {
                frameLayout = this;
            }
            this.textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.sesl_layout_tab_text, (ViewGroup) frameLayout, false);
            frameLayout.addView(this.textView);
        }

        private FrameLayout createPreApi18BadgeAnchorRoot() {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            return frameLayout;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private BadgeDrawable getOrCreateBadge() {
            if (this.badgeDrawable == null) {
                this.badgeDrawable = BadgeDrawable.create(getContext());
            }
            tryUpdateBadgeAnchor();
            BadgeDrawable badgeDrawable2 = this.badgeDrawable;
            if (badgeDrawable2 != null) {
                return badgeDrawable2;
            }
            throw new IllegalStateException("Unable to create badge");
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private BadgeDrawable getBadge() {
            return this.badgeDrawable;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void removeBadge() {
            if (this.badgeAnchorView != null) {
                tryRemoveBadgeFromAnchor();
            }
            this.badgeDrawable = null;
        }

        private void addOnLayoutChangeListener(final View view) {
            if (view != null) {
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    /* class com.google.android.material.tabs.TabLayout.TabView.AnonymousClass2 */

                    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        if (view.getVisibility() == 0) {
                            TabView.this.tryUpdateBadgeDrawableBounds(view);
                        }
                    }
                });
            }
        }

        private void tryUpdateBadgeAnchor() {
            Tab tab2;
            Tab tab3;
            if (hasBadgeDrawable()) {
                if (this.customView != null) {
                    tryRemoveBadgeFromAnchor();
                } else if (this.iconView != null && (tab3 = this.tab) != null && tab3.getIcon() != null) {
                    View view = this.badgeAnchorView;
                    ImageView imageView = this.iconView;
                    if (view != imageView) {
                        tryRemoveBadgeFromAnchor();
                        tryAttachBadgeToAnchor(this.iconView);
                        return;
                    }
                    tryUpdateBadgeDrawableBounds(imageView);
                } else if (this.textView == null || (tab2 = this.tab) == null || tab2.getTabLabelVisibility() != 1) {
                    tryRemoveBadgeFromAnchor();
                } else {
                    View view2 = this.badgeAnchorView;
                    TextView textView2 = this.textView;
                    if (view2 != textView2) {
                        tryRemoveBadgeFromAnchor();
                        tryAttachBadgeToAnchor(this.textView);
                        return;
                    }
                    tryUpdateBadgeDrawableBounds(textView2);
                }
            }
        }

        private void tryAttachBadgeToAnchor(View view) {
            if (hasBadgeDrawable() && view != null) {
                setClipChildren(false);
                setClipToPadding(false);
                BadgeUtils.attachBadgeDrawable(this.badgeDrawable, view, getCustomParentForBadge(view));
                this.badgeAnchorView = view;
            }
        }

        private void tryRemoveBadgeFromAnchor() {
            if (hasBadgeDrawable() && this.badgeAnchorView != null) {
                setClipChildren(true);
                setClipToPadding(true);
                BadgeDrawable badgeDrawable2 = this.badgeDrawable;
                View view = this.badgeAnchorView;
                BadgeUtils.detachBadgeDrawable(badgeDrawable2, view, getCustomParentForBadge(view));
                this.badgeAnchorView = null;
            }
        }

        /* access modifiers changed from: package-private */
        public final void updateOrientation() {
            setOrientation(!TabLayout.this.inlineLabel ? 1 : 0);
            if (this.customTextView == null && this.customIconView == null) {
                updateTextAndIcon(this.textView, this.iconView);
            } else {
                updateTextAndIcon(this.customTextView, this.customIconView);
            }
        }

        private void updateTextAndIcon(TextView textView2, ImageView imageView) {
            int i;
            Tab tab2 = this.tab;
            Drawable mutate = (tab2 == null || tab2.getIcon() == null) ? null : DrawableCompat.wrap(this.tab.getIcon()).mutate();
            Tab tab3 = this.tab;
            CharSequence text = tab3 != null ? tab3.getText() : null;
            if (imageView != null) {
                if (mutate != null) {
                    imageView.setImageDrawable(mutate);
                    imageView.setVisibility(0);
                    setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable(null);
                }
            }
            boolean z = !TextUtils.isEmpty(text);
            if (textView2 != null) {
                if (z) {
                    textView2.setText(text);
                    if (this.tab.labelVisibilityMode == 1) {
                        textView2.setVisibility(0);
                    } else {
                        textView2.setVisibility(8);
                    }
                    setVisibility(0);
                } else {
                    textView2.setVisibility(8);
                    textView2.setText((CharSequence) null);
                }
            }
            if (imageView != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                if (!z || imageView.getVisibility() != 0) {
                    i = 0;
                } else {
                    i = TabLayout.this.mIconTextGap != -1 ? TabLayout.this.mIconTextGap : (int) ViewUtils.dpToPx(getContext(), 8);
                }
                if (i != MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams)) {
                    MarginLayoutParamsCompat.setMarginEnd(marginLayoutParams, i);
                    marginLayoutParams.bottomMargin = 0;
                    imageView.setLayoutParams(marginLayoutParams);
                    imageView.requestLayout();
                    if (textView2 != null) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView2.getLayoutParams();
                        layoutParams.addRule(13, 0);
                        layoutParams.addRule(15, 1);
                        layoutParams.addRule(17, R.id.icon);
                        textView2.setLayoutParams(layoutParams);
                    }
                }
            }
            Tab tab4 = this.tab;
            CharSequence charSequence = tab4 != null ? tab4.contentDesc : null;
            if (z) {
                charSequence = null;
            }
            TooltipCompat.setTooltipText(this, charSequence);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void tryUpdateBadgeDrawableBounds(View view) {
            if (hasBadgeDrawable() && view == this.badgeAnchorView) {
                BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, view, getCustomParentForBadge(view));
            }
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private boolean hasBadgeDrawable() {
            return this.badgeDrawable != null;
        }

        private FrameLayout getCustomParentForBadge(View view) {
            if ((view == this.iconView || view == this.textView) && BadgeUtils.USE_COMPAT_PARENT) {
                return (FrameLayout) view.getParent();
            }
            return null;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private int getContentWidth() {
            View[] viewArr = {this.textView, this.iconView, this.customView};
            int i = 0;
            int i2 = 0;
            boolean z = false;
            for (View view : viewArr) {
                if (view != null && view.getVisibility() == 0) {
                    i2 = z ? Math.min(i2, view.getLeft()) : view.getLeft();
                    i = z ? Math.max(i, view.getRight()) : view.getRight();
                    z = true;
                }
            }
            return i - i2;
        }

        public Tab getTab() {
            return this.tab;
        }

        private float approximateLineWidth(Layout layout, int i, float f) {
            return layout.getLineWidth(i) * (f / layout.getPaint().getTextSize());
        }

        /* access modifiers changed from: protected */
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            this.mIconSize = getResources().getDimensionPixelOffset(R.dimen.sesl_tab_icon_size);
        }

        public void setEnabled(boolean z) {
            super.setEnabled(z);
            View view = this.mMainTabTouchBackground;
            if (view != null) {
                view.setVisibility(z ? 0 : 8);
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            TextView textView2;
            super.onLayout(z, i, i2, i3, i4);
            View view = this.mMainTabTouchBackground;
            if (view != null) {
                view.setLeft(0);
                View view2 = this.mMainTabTouchBackground;
                RelativeLayout relativeLayout = this.mTextParentView;
                view2.setRight(relativeLayout != null ? relativeLayout.getWidth() : i3 - i);
                if (this.mMainTabTouchBackground.getAnimation() != null && this.mMainTabTouchBackground.getAnimation().hasEnded()) {
                    this.mMainTabTouchBackground.setAlpha(0.0f);
                }
            }
            if (this.iconView != null && this.tab.icon != null && (textView2 = this.textView) != null && this.mIndicatorView != null && this.mTextParentView != null) {
                int measuredWidth = this.mIconSize + textView2.getMeasuredWidth();
                if (TabLayout.this.mIconTextGap != -1) {
                    measuredWidth += TabLayout.this.mIconTextGap;
                }
                int abs = Math.abs((getWidth() - measuredWidth) / 2);
                if (ViewUtils.isLayoutRtl(this)) {
                    int i5 = -abs;
                    if (this.iconView.getRight() == this.mTextParentView.getRight()) {
                        this.textView.offsetLeftAndRight(i5);
                        this.iconView.offsetLeftAndRight(i5);
                        this.mIndicatorView.offsetLeftAndRight(i5);
                    }
                } else if (this.iconView.getLeft() == this.mTextParentView.getLeft()) {
                    this.textView.offsetLeftAndRight(abs);
                    this.iconView.offsetLeftAndRight(abs);
                    this.mIndicatorView.offsetLeftAndRight(abs);
                }
            }
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (!isEnabled()) {
                return super.onTouchEvent(motionEvent);
            }
            if (this.tab.getCustomView() != null) {
                return super.onTouchEvent(motionEvent);
            }
            return startTabTouchAnimation(motionEvent, null);
        }

        private boolean startTabTouchAnimation(MotionEvent motionEvent, KeyEvent keyEvent) {
            SeslAbsIndicatorView seslAbsIndicatorView;
            TextView textView2;
            if (motionEvent == null || this.tab.getCustomView() != null || this.textView == null || ((motionEvent == null && keyEvent == null) || (motionEvent != null && keyEvent != null))) {
                return false;
            }
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this.mIsCallPerformClick = false;
                if (this.tab.position != TabLayout.this.getSelectedTabPosition() && (textView2 = this.textView) != null) {
                    textView2.setTypeface(TabLayout.this.mBoldTypeface);
                    TabLayout tabLayout = TabLayout.this;
                    tabLayout.startTextColorChangeAnimation(this.textView, tabLayout.getSelectedTabTextColor());
                    SeslAbsIndicatorView seslAbsIndicatorView2 = this.mIndicatorView;
                    if (seslAbsIndicatorView2 != null) {
                        seslAbsIndicatorView2.setPressed();
                    }
                    TabLayout tabLayout2 = TabLayout.this;
                    Tab tabAt = tabLayout2.getTabAt(tabLayout2.getSelectedTabPosition());
                    if (tabAt != null) {
                        if (tabAt.view.textView != null) {
                            tabAt.view.textView.setTypeface(TabLayout.this.mNormalTypeface);
                            TabLayout.this.startTextColorChangeAnimation(tabAt.view.textView, TabLayout.this.tabTextColors.getDefaultColor());
                        }
                        if (tabAt.view.mIndicatorView != null) {
                            tabAt.view.mIndicatorView.setHide();
                        }
                    }
                } else if (this.tab.position == TabLayout.this.getSelectedTabPosition() && (seslAbsIndicatorView = this.mIndicatorView) != null) {
                    seslAbsIndicatorView.setPressed();
                }
                showMainTabTouchBackground(0);
            } else if (action == 1) {
                showMainTabTouchBackground(1);
                SeslAbsIndicatorView seslAbsIndicatorView3 = this.mIndicatorView;
                if (seslAbsIndicatorView3 != null) {
                    seslAbsIndicatorView3.setReleased();
                    this.mIndicatorView.onTouchEvent(motionEvent);
                }
                performClick();
                this.mIsCallPerformClick = true;
            } else if (action == 3) {
                this.textView.setTypeface(TabLayout.this.mNormalTypeface);
                TabLayout tabLayout3 = TabLayout.this;
                tabLayout3.startTextColorChangeAnimation(this.textView, tabLayout3.tabTextColors.getDefaultColor());
                SeslAbsIndicatorView seslAbsIndicatorView4 = this.mIndicatorView;
                if (seslAbsIndicatorView4 != null && !seslAbsIndicatorView4.isSelected()) {
                    this.mIndicatorView.setHide();
                }
                TabLayout tabLayout4 = TabLayout.this;
                Tab tabAt2 = tabLayout4.getTabAt(tabLayout4.getSelectedTabPosition());
                if (tabAt2 != null) {
                    if (tabAt2.view.textView != null) {
                        tabAt2.view.textView.setTypeface(TabLayout.this.mBoldTypeface);
                        TabLayout.this.startTextColorChangeAnimation(tabAt2.view.textView, TabLayout.this.getSelectedTabTextColor());
                    }
                    if (tabAt2.view.mIndicatorView != null) {
                        tabAt2.view.mIndicatorView.setShow();
                    }
                }
                if (TabLayout.this.mDepthStyle == 1) {
                    showMainTabTouchBackground(3);
                } else {
                    SeslAbsIndicatorView seslAbsIndicatorView5 = this.mIndicatorView;
                    if (seslAbsIndicatorView5 != null && seslAbsIndicatorView5.isSelected()) {
                        this.mIndicatorView.setReleased();
                    }
                }
            }
            return super.onTouchEvent(motionEvent);
        }

        private void showMainTabTouchBackground(int i) {
            if (this.mMainTabTouchBackground != null && TabLayout.this.mDepthStyle == 1 && TabLayout.this.tabBackgroundResId == 0) {
                this.mMainTabTouchBackground.setAlpha(1.0f);
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.setFillAfter(true);
                if (i == 0) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(100);
                    alphaAnimation.setFillAfter(true);
                    animationSet.addAnimation(alphaAnimation);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(TabLayout.ANIM_RIPPLE_MINOR_SCALE, 1.0f, TabLayout.ANIM_RIPPLE_MINOR_SCALE, 1.0f, 1, 0.5f, 1, 0.5f);
                    scaleAnimation.setDuration(350);
                    scaleAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_80);
                    scaleAnimation.setFillAfter(true);
                    animationSet.addAnimation(scaleAnimation);
                    this.mMainTabTouchBackground.startAnimation(animationSet);
                } else if ((i != 1 && i != 3) || this.mMainTabTouchBackground.getAnimation() == null) {
                } else {
                    if (this.mMainTabTouchBackground.getAnimation().hasEnded()) {
                        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
                        alphaAnimation2.setDuration(400);
                        alphaAnimation2.setFillAfter(true);
                        animationSet.addAnimation(alphaAnimation2);
                        this.mMainTabTouchBackground.startAnimation(animationSet);
                        return;
                    }
                    this.mMainTabTouchBackground.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                        /* class com.google.android.material.tabs.TabLayout.TabView.AnonymousClass3 */

                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationStart(Animation animation) {
                        }

                        public void onAnimationEnd(Animation animation) {
                            AnimationSet animationSet = new AnimationSet(true);
                            animationSet.setFillAfter(true);
                            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                            alphaAnimation.setDuration(400);
                            alphaAnimation.setFillAfter(true);
                            animationSet.addAnimation(alphaAnimation);
                            TabView.this.mMainTabTouchBackground.startAnimation(alphaAnimation);
                        }
                    });
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public class SlidingTabIndicator extends LinearLayout {
        private final GradientDrawable defaultSelectionIndicator;
        private ValueAnimator indicatorAnimator;
        private int indicatorLeft = -1;
        private int indicatorRight = -1;
        private int layoutDirection = -1;
        private int selectedIndicatorHeight;
        private final Paint selectedIndicatorPaint;
        int selectedPosition = -1;
        float selectionOffset;

        private void updateIndicatorPosition() {
        }

        /* access modifiers changed from: package-private */
        public void animateIndicatorToPosition(int i, int i2) {
        }

        SlidingTabIndicator(Context context) {
            super(context);
            setWillNotDraw(false);
            this.selectedIndicatorPaint = new Paint();
            this.defaultSelectionIndicator = new GradientDrawable();
        }

        /* access modifiers changed from: package-private */
        public void setSelectedIndicatorColor(int i) {
            if (this.selectedIndicatorPaint.getColor() != i) {
                this.selectedIndicatorPaint.setColor(i);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void setSelectedIndicatorHeight(int i) {
            if (this.selectedIndicatorHeight != i) {
                this.selectedIndicatorHeight = i;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean childrenNeedLayout() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (getChildAt(i).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void setIndicatorPositionFromTabPosition(int i, float f) {
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.indicatorAnimator.cancel();
            }
            this.selectedPosition = i;
            this.selectionOffset = f;
            updateIndicatorPosition();
        }

        /* access modifiers changed from: package-private */
        public float getIndicatorPosition() {
            return ((float) this.selectedPosition) + this.selectionOffset;
        }

        public void onRtlPropertiesChanged(int i) {
            super.onRtlPropertiesChanged(i);
            if (Build.VERSION.SDK_INT < 23 && this.layoutDirection != i) {
                requestLayout();
                this.layoutDirection = i;
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            boolean z;
            super.onMeasure(i, i2);
            if (View.MeasureSpec.getMode(i) == 1073741824) {
                if (TabLayout.this.tabGravity == 1 || TabLayout.this.mode == 2) {
                    int childCount = getChildCount();
                    int i3 = 0;
                    for (int i4 = 0; i4 < childCount; i4++) {
                        View childAt = getChildAt(i4);
                        if (childAt.getVisibility() == 0) {
                            i3 = Math.max(i3, childAt.getMeasuredWidth());
                        }
                    }
                    if (i3 > 0) {
                        if (i3 * childCount <= getMeasuredWidth() - (((int) ViewUtils.dpToPx(getContext(), 16)) * 2)) {
                            z = false;
                            for (int i5 = 0; i5 < childCount; i5++) {
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildAt(i5).getLayoutParams();
                                if (layoutParams.width != i3 || layoutParams.weight != 0.0f) {
                                    layoutParams.width = i3;
                                    layoutParams.weight = 0.0f;
                                    z = true;
                                }
                            }
                        } else {
                            TabLayout tabLayout = TabLayout.this;
                            tabLayout.tabGravity = 0;
                            tabLayout.updateTabViews(false);
                            z = true;
                        }
                        if (z) {
                            super.onMeasure(i, i2);
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                updateIndicatorPosition();
                return;
            }
            this.indicatorAnimator.cancel();
            animateIndicatorToPosition(this.selectedPosition, Math.round((1.0f - this.indicatorAnimator.getAnimatedFraction()) * ((float) this.indicatorAnimator.getDuration())));
        }

        /* access modifiers changed from: package-private */
        public void setIndicatorPosition(int i, int i2) {
            if (i != this.indicatorLeft || i2 != this.indicatorRight) {
                this.indicatorLeft = i;
                this.indicatorRight = i2;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        private void calculateTabViewContentBounds(TabView tabView, RectF rectF) {
            int contentWidth = tabView.getContentWidth();
            int dpToPx = (int) ViewUtils.dpToPx(getContext(), 24);
            if (contentWidth < dpToPx) {
                contentWidth = dpToPx;
            }
            int left = (tabView.getLeft() + tabView.getRight()) / 2;
            int i = contentWidth / 2;
            rectF.set((float) (left - i), 0.0f, (float) (left + i), 0.0f);
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
        }
    }

    private static ColorStateList createColorStateList(int i, int i2) {
        return new ColorStateList(new int[][]{SELECTED_STATE_SET, EMPTY_STATE_SET}, new int[]{i2, i});
    }

    private int getDefaultHeight() {
        int size = this.tabs.size();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i < size) {
                Tab tab = this.tabs.get(i);
                if (tab != null && tab.getIcon() != null && !TextUtils.isEmpty(tab.getText())) {
                    z = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return (!z || this.inlineLabel) ? 48 : 72;
    }

    private int getTabMinWidth() {
        int i = this.requestedTabMinWidth;
        if (i != -1) {
            return i;
        }
        return 0;
    }

    @Override // android.widget.FrameLayout, android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: package-private */
    public int getTabMaxWidth() {
        return this.tabMaxWidth;
    }

    public void seslSetSubTabStyle() {
        if (this.mDepthStyle == 1) {
            this.mDepthStyle = 2;
            this.tabTextColors = getResources().getColorStateList(SeslMisc.isLightTheme(getContext()) ? R.color.sesl_tablayout_subtab_text_color_light : R.color.sesl_tablayout_subtab_text_color_dark);
            if (this.tabs.size() > 0) {
                int selectedTabPosition = getSelectedTabPosition();
                ArrayList arrayList = new ArrayList(this.tabs.size());
                for (int i = 0; i < this.tabs.size(); i++) {
                    Tab newTab = newTab();
                    newTab.text = this.tabs.get(i).text;
                    newTab.icon = this.tabs.get(i).icon;
                    newTab.customView = this.tabs.get(i).customView;
                    if (i == selectedTabPosition) {
                        newTab.select();
                    }
                    newTab.view.update();
                    arrayList.add(newTab);
                }
                removeAllTabs();
                int i2 = 0;
                while (i2 < arrayList.size()) {
                    addTab((Tab) arrayList.get(i2), i2 == selectedTabPosition);
                    if (this.tabs.get(i2) != null) {
                        this.tabs.get(i2).view.update();
                    }
                    i2++;
                }
                arrayList.clear();
            }
        }
    }

    private void updateBadgePosition() {
        int i;
        int i2;
        int i3;
        ArrayList<Tab> arrayList = this.tabs;
        if (!(arrayList == null || arrayList.size() == 0)) {
            for (int i4 = 0; i4 < this.tabs.size(); i4++) {
                Tab tab = this.tabs.get(i4);
                TabView tabView = this.tabs.get(i4).view;
                if (!(tab == null || tabView == null)) {
                    TextView textView = tabView.textView;
                    if (tabView.getWidth() > 0 && textView != null && textView.getWidth() > 0) {
                        TextView textView2 = null;
                        char c = 65535;
                        if (tabView.mNBadgeView != null && tabView.mNBadgeView.getVisibility() == 0) {
                            textView2 = tabView.mNBadgeView;
                            int marginStart = ((RelativeLayout.LayoutParams) textView2.getLayoutParams()).getMarginStart();
                            i = getContext().getResources().getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_n_badge_xoffset);
                            i2 = marginStart;
                            c = 1;
                        } else if (tabView.mDotBadgeView == null || tabView.mDotBadgeView.getVisibility() != 0) {
                            i = getContext().getResources().getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_n_badge_xoffset);
                            i2 = 0;
                        } else {
                            textView2 = tabView.mDotBadgeView;
                            int marginStart2 = ((RelativeLayout.LayoutParams) textView2.getLayoutParams()).getMarginStart();
                            i = getContext().getResources().getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_dot_badge_offset_x);
                            i2 = marginStart2;
                            c = 2;
                        }
                        if (textView2 != null && textView2.getVisibility() == 0) {
                            textView2.measure(0, 0);
                            if (c == 1) {
                                i3 = textView2.getMeasuredWidth();
                            } else {
                                i3 = getResources().getDimensionPixelSize(R.dimen.sesl_tab_badge_dot_size);
                            }
                            int width = tabView.getWidth();
                            if (i2 == 0 || i2 < textView.getRight()) {
                                i2 = textView.getRight() + i;
                            }
                            if (i2 > width) {
                                i2 = width - i3;
                            } else {
                                int i5 = i2 + i3;
                                if (i5 > width) {
                                    i2 -= i5 - width;
                                } else if (i2 > textView.getRight() + i) {
                                    i2 = textView.getRight() + i;
                                }
                            }
                            int max = Math.max(0, i2);
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView2.getLayoutParams();
                            int i6 = layoutParams.width;
                            if (layoutParams.getMarginStart() != max || i6 != i3) {
                                layoutParams.setMarginStart(max);
                                layoutParams.width = i3;
                                textView2.setLayoutParams(layoutParams);
                            }
                        }
                    }
                }
            }
        }
    }

    public void seslSetSubTabSelectedIndicatorColor(int i) {
        this.mSubTabSelectedIndicatorColor = i;
        setSelectedTabIndicatorColor(i);
    }

    public void seslSetTabTextColor(ColorStateList colorStateList, boolean z) {
        if (this.tabTextColors != colorStateList) {
            this.tabTextColors = colorStateList;
            if (z) {
                updateAllTabs();
            } else if (this.tabs != null) {
                for (int i = 0; i < this.tabs.size(); i++) {
                    TabView tabView = this.tabs.get(i).view;
                    if (!(tabView == null || tabView.textView == null)) {
                        tabView.textView.setTextColor(this.tabTextColors);
                    }
                }
            }
        }
    }

    public void seslSetBadgeColor(int i) {
        this.mBadgeColor = i;
    }

    public void seslSetBadgeTextColor(int i) {
        this.mBadgeTextColor = i;
    }

    public void seslSetTabWidth(int i) {
        this.mRequestedTabWidth = i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getSelectedTabTextColor() {
        ColorStateList colorStateList = this.tabTextColors;
        if (colorStateList != null) {
            return colorStateList.getColorForState(new int[]{16842913, 16842910}, colorStateList.getDefaultColor());
        }
        return -1;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startTextColorChangeAnimation(TextView textView, int i) {
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkMaxFontScale(TextView textView, int i) {
        float f = getResources().getConfiguration().fontScale;
        if (textView != null && this.mIsScaledTextSizeType && f > 1.3f) {
            textView.setTextSize(0, (((float) i) / f) * 1.3f);
        }
    }

    private void createAddBadge(int i, TabView tabView) {
        if (tabView != null && tabView.mTextParentView != null) {
            TextView textView = new TextView(getContext());
            Resources resources = getResources();
            if (i == 2) {
                if (tabView.mDotBadgeView == null) {
                    textView.setVisibility(8);
                    ViewCompat.setBackground(textView, resources.getDrawable(R.drawable.sesl_dot_badge));
                    textView.setId(R.id.sesl_badge_dot);
                    int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sesl_tab_badge_dot_size);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
                    layoutParams.addRule(6, R.id.title);
                    textView.setMinHeight(dimensionPixelSize);
                    textView.setMinWidth(dimensionPixelSize);
                    tabView.mTextParentView.addView(textView, layoutParams);
                    tabView.mDotBadgeView = textView;
                }
            } else if (tabView.mNBadgeView == null) {
                textView.setVisibility(8);
                textView.setMinWidth(resources.getDimensionPixelSize(R.dimen.sesl_tab_badge_number_min_width));
                textView.setTextSize(1, 11.0f);
                textView.setGravity(17);
                textView.setTextColor(resources.getColor(R.color.sesl_badge_text_color));
                ViewCompat.setBackground(textView, resources.getDrawable(R.drawable.sesl_tab_n_badge));
                textView.setId(R.id.sesl_badge_n);
                textView.setMaxLines(1);
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, resources.getDimensionPixelSize(R.dimen.sesl_tab_badge_number_height));
                layoutParams2.addRule(6, R.id.title);
                layoutParams2.setMargins(0, -resources.getDimensionPixelSize(R.dimen.sesl_tab_badge_offset_y), 0, 0);
                tabView.mTextParentView.addView(textView, layoutParams2);
                tabView.mNBadgeView = textView;
            }
        }
    }

    public void seslShowDotBadge(int i, boolean z) {
        if (this.tabs.get(i) != null && this.tabs.get(i).view != null) {
            TabView tabView = this.tabs.get(i).view;
            if (tabView.mDotBadgeView == null) {
                createAddBadge(2, tabView);
            }
            if (tabView.mDotBadgeView != null) {
                TextView textView = tabView.mDotBadgeView;
                if (z) {
                    textView.setVisibility(0);
                    if (this.mBadgeColor != -1) {
                        DrawableCompat.setTint(textView.getBackground(), this.mBadgeColor);
                    }
                    updateBadgePosition();
                    return;
                }
                textView.setVisibility(8);
            }
        }
    }

    public void seslShowBadge(int i, boolean z, String str) {
        seslShowBadge(i, z, str, null);
    }

    public void seslShowBadge(int i, boolean z, String str, String str2) {
        if (this.mDepthStyle != 2 && this.tabs.get(i) != null && this.tabs.get(i).view != null) {
            TabView tabView = this.tabs.get(i).view;
            if (tabView.mNBadgeView == null) {
                createAddBadge(1, tabView);
            }
            if (tabView.mNBadgeView != null) {
                TextView textView = tabView.mNBadgeView;
                textView.setText(str);
                if (z) {
                    textView.setVisibility(0);
                    if (this.mBadgeColor != -1) {
                        DrawableCompat.setTint(textView.getBackground(), this.mBadgeColor);
                    }
                    int i2 = this.mBadgeTextColor;
                    if (i2 != -1) {
                        textView.setTextColor(i2);
                    }
                    if (str2 != null) {
                        textView.setContentDescription(str2);
                    }
                    updateBadgePosition();
                    textView.requestLayout();
                    return;
                }
                textView.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateBadgePosition();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        for (int i2 = 0; i2 < getTabCount(); i2++) {
            Tab tabAt = getTabAt(i2);
            if (!(tabAt == null || tabAt.view == null || tabAt.view.mMainTabTouchBackground == null)) {
                tabAt.view.mMainTabTouchBackground.setAlpha(0.0f);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        for (int i = 0; i < getTabCount(); i++) {
            Tab tabAt = getTabAt(i);
            if (!(tabAt == null || tabAt.view == null || tabAt.view.mMainTabTouchBackground == null)) {
                tabAt.view.mMainTabTouchBackground.setAlpha(0.0f);
            }
        }
        updateBadgePosition();
    }

    public void seslSetSubTabIndicatorHeight(int i) {
        this.mSubTabIndicatorHeight = i;
    }

    public void seslSetIconTextGap(int i) {
        this.mIconTextGap = i;
        updateAllTabs();
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int previousScrollState;
        private int scrollState;
        private final WeakReference<TabLayout> tabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.tabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            this.previousScrollState = this.scrollState;
            this.scrollState = i;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            TabLayout tabLayout = this.tabLayoutRef.get();
            if (tabLayout != null) {
                boolean z = false;
                boolean z2 = this.scrollState != 2 || this.previousScrollState == 1;
                if (!(this.scrollState == 2 && this.previousScrollState == 0)) {
                    z = true;
                }
                tabLayout.setScrollPosition(i, f, z2, z);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            TabLayout tabLayout = this.tabLayoutRef.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != i && i < tabLayout.getTabCount()) {
                int i2 = this.scrollState;
                tabLayout.selectTab(tabLayout.getTabAt(i), i2 == 0 || (i2 == 2 && this.previousScrollState == 0));
            }
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.scrollState = 0;
            this.previousScrollState = 0;
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager viewPager;

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabReselected(Tab tab) {
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabUnselected(Tab tab) {
        }

        public ViewPagerOnTabSelectedListener(ViewPager viewPager2) {
            this.viewPager = viewPager2;
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabSelected(Tab tab) {
            this.viewPager.setCurrentItem(tab.getPosition());
        }
    }

    /* access modifiers changed from: private */
    public class PagerAdapterObserver extends DataSetObserver {
        PagerAdapterObserver() {
        }

        public void onChanged() {
            TabLayout.this.populateFromPagerAdapter();
        }

        public void onInvalidated() {
            TabLayout.this.populateFromPagerAdapter();
        }
    }

    /* access modifiers changed from: private */
    public class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
        private boolean autoRefresh;

        AdapterChangeListener() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            if (TabLayout.this.viewPager == viewPager) {
                TabLayout.this.setPagerAdapter(pagerAdapter2, this.autoRefresh);
            }
        }

        /* access modifiers changed from: package-private */
        public void setAutoRefresh(boolean z) {
            this.autoRefresh = z;
        }
    }
}
