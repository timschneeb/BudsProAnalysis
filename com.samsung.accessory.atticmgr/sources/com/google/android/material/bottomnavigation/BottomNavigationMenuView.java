package com.google.android.material.bottomnavigation;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.util.Pools;
import androidx.core.view.ViewCompat;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.TextScale;
import java.util.HashSet;

public class BottomNavigationMenuView extends ViewGroup implements MenuView {
    private static final long ACTIVE_ANIMATION_DURATION_MS = 0;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private static final int ITEM_POOL_SIZE = 5;
    private static final String TAG = BottomNavigationMenuView.class.getSimpleName();
    private int activeItemMaxWidth;
    private final int activeItemMinWidth;
    private SparseArray<BadgeDrawable> badgeDrawables;
    private final int inactiveItemMaxWidth;
    private final int inactiveItemMinWidth;
    private Drawable itemBackground;
    private int itemBackgroundRes;
    private int itemHeight;
    private boolean itemHorizontalTranslationEnabled;
    private int itemIconSize;
    private ColorStateList itemIconTint;
    private final Pools.Pool<BottomNavigationItemView> itemPool;
    private int itemTextAppearanceActive;
    private int itemTextAppearanceInactive;
    private final ColorStateList itemTextColorDefault;
    private ColorStateList itemTextColorFromUser;
    private int labelVisibilityMode;
    private BottomNavigationMenuView mBottomNavigationMenuView;
    private BottomNavigationItemView[] mButtons;
    private ContentResolver mContentResolver;
    MenuBuilder mDummyMenu;
    private boolean mHasIcon;
    private boolean mHasOverflowMenu;
    private InternalBtnInfo mInvisibleBtns;
    BottomNavigationItemView mOverflowButton;
    private MenuBuilder mOverflowMenu;
    private ColorDrawable mSBBTextColorDrawable;
    private MenuBuilder.Callback mSelectedCallback;
    private InternalBtnInfo mVisibleBtns;
    private int mVisibleItemCount;
    private float mWidthPercent;
    private MenuBuilder menu;
    private final View.OnClickListener onClickListener;
    private BottomNavigationPresenter presenter;
    private int selectedItemId;
    private int selectedItemPosition;
    private final TransitionSet set;
    private int[] tempChildWidths;

    private boolean isShifting(int i, int i2) {
        return i == 0;
    }

    private boolean isValidId(int i) {
        return i != -1;
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public int getWindowAnimations() {
        return 0;
    }

    public BottomNavigationMenuView(Context context) {
        this(context, null);
    }

    public BottomNavigationMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.itemPool = new Pools.SynchronizedPool(5);
        this.selectedItemId = 0;
        this.selectedItemPosition = 0;
        this.badgeDrawables = new SparseArray<>(5);
        this.mVisibleBtns = null;
        this.mInvisibleBtns = null;
        this.mOverflowButton = null;
        this.mHasOverflowMenu = false;
        this.mOverflowMenu = null;
        this.mVisibleItemCount = 0;
        Resources resources = getResources();
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.sesl_bottom_navigation_width_proportion, typedValue, true);
        this.mWidthPercent = typedValue.getFloat();
        this.inactiveItemMaxWidth = resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_item_max_width);
        this.inactiveItemMinWidth = resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_item_min_width);
        this.activeItemMaxWidth = (int) (((float) getResources().getDisplayMetrics().widthPixels) * this.mWidthPercent);
        this.activeItemMinWidth = resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_active_item_min_width);
        this.itemHeight = resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_mode_height);
        this.itemTextColorDefault = createDefaultColorStateList(16842808);
        this.set = new AutoTransition();
        this.set.setOrdering(0);
        this.set.setDuration(0L);
        this.set.addTransition(new TextScale());
        this.onClickListener = new View.OnClickListener() {
            /* class com.google.android.material.bottomnavigation.BottomNavigationMenuView.AnonymousClass1 */

            public void onClick(View view) {
                MenuItemImpl itemData = ((BottomNavigationItemView) view).getItemData();
                if (!BottomNavigationMenuView.this.menu.performItemAction(itemData, BottomNavigationMenuView.this.presenter, 0)) {
                    itemData.setChecked(true);
                }
            }
        };
        this.tempChildWidths = new int[5];
        this.mContentResolver = context.getContentResolver();
        this.mBottomNavigationMenuView = this;
    }

    /* access modifiers changed from: package-private */
    public void setHasIcon(boolean z) {
        this.mHasIcon = z;
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public void initialize(MenuBuilder menuBuilder) {
        this.menu = menuBuilder;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = (int) (((float) View.MeasureSpec.getSize(i)) * this.mWidthPercent);
        int i3 = this.mVisibleItemCount;
        int childCount = getChildCount();
        if (childCount > 5 || i3 > 5) {
            i3 = Math.min(5, Math.min(i3, childCount));
        }
        this.itemHeight = getResources().getDimensionPixelSize(this.mHasIcon ? R.dimen.sesl_bottom_navigation_icon_mode_height : R.dimen.sesl_bottom_navigation_text_mode_height);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.itemHeight, 1073741824);
        if (!isShifting(this.labelVisibilityMode, i3) || !this.itemHorizontalTranslationEnabled) {
            int i4 = size / (i3 == 0 ? 1 : i3);
            if (i3 != 2) {
                i4 = Math.min(i4, this.activeItemMaxWidth);
            }
            int i5 = size - (i4 * i3);
            for (int i6 = 0; i6 < i3; i6++) {
                View childAt = getChildAt(i6);
                if (childAt != null) {
                    if (childAt.getVisibility() != 8) {
                        int[] iArr = this.tempChildWidths;
                        iArr[i6] = i4;
                        if (i5 > 0) {
                            iArr[i6] = iArr[i6] + 1;
                            i5--;
                        }
                    } else {
                        this.tempChildWidths[i6] = 0;
                    }
                }
            }
        } else {
            View childAt2 = getChildAt(this.selectedItemPosition);
            int i7 = this.activeItemMinWidth;
            if (childAt2.getVisibility() != 8) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(this.activeItemMaxWidth, Integer.MIN_VALUE), makeMeasureSpec);
                i7 = Math.max(i7, childAt2.getMeasuredWidth());
            }
            int i8 = i3 - (childAt2.getVisibility() != 8 ? 1 : 0);
            int min = Math.min(size - (this.inactiveItemMinWidth * i8), Math.min(i7, this.activeItemMaxWidth));
            int i9 = size - min;
            int min2 = Math.min(i9 / (i8 == 0 ? 1 : i8), this.inactiveItemMaxWidth);
            int i10 = i9 - (i8 * min2);
            int i11 = 0;
            while (i11 < i3) {
                if (getChildAt(i11).getVisibility() != 8) {
                    this.tempChildWidths[i11] = i11 == this.selectedItemPosition ? min : min2;
                    if (i10 > 0) {
                        int[] iArr2 = this.tempChildWidths;
                        iArr2[i11] = iArr2[i11] + 1;
                        i10--;
                    }
                } else {
                    this.tempChildWidths[i11] = 0;
                }
                i11++;
            }
        }
        int i12 = 0;
        for (int i13 = 0; i13 < i3; i13++) {
            View childAt3 = getChildAt(i13);
            if (!(childAt3 == null || childAt3.getVisibility() == 8)) {
                childAt3.measure(View.MeasureSpec.makeMeasureSpec(this.tempChildWidths[i13], 1073741824), makeMeasureSpec);
                childAt3.getLayoutParams().width = childAt3.getMeasuredWidth();
                i12 += childAt3.getMeasuredWidth();
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(i12, View.MeasureSpec.makeMeasureSpec(i12, 1073741824), 0), View.resolveSizeAndState(this.itemHeight, makeMeasureSpec, 0));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int dimensionPixelSize = this.mHasIcon ? getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_mode_padding_horizontal) : 0;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection(this) == 1) {
                    int i9 = i5 - i7;
                    childAt.layout((i9 - childAt.getMeasuredWidth()) - dimensionPixelSize, 0, i9 + dimensionPixelSize, i6);
                } else {
                    childAt.layout(i7 + dimensionPixelSize, 0, (childAt.getMeasuredWidth() + i7) - dimensionPixelSize, i6);
                }
                i7 += childAt.getMeasuredWidth();
            }
        }
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.itemIconTint = colorStateList;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView == null) {
                    break;
                }
                bottomNavigationItemView.setIconTintList(colorStateList);
            }
        }
        BottomNavigationItemView bottomNavigationItemView2 = this.mOverflowButton;
        if (bottomNavigationItemView2 != null) {
            bottomNavigationItemView2.setIconTintList(colorStateList);
        }
    }

    public ColorStateList getIconTintList() {
        return this.itemIconTint;
    }

    public void setItemIconSize(int i) {
        this.itemIconSize = i;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView == null) {
                    break;
                }
                bottomNavigationItemView.setIconSize(i);
            }
        }
        BottomNavigationItemView bottomNavigationItemView2 = this.mOverflowButton;
        if (bottomNavigationItemView2 != null) {
            bottomNavigationItemView2.setIconSize(i);
        }
    }

    public int getItemIconSize() {
        return this.itemIconSize;
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.itemTextColorFromUser = colorStateList;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView == null) {
                    break;
                }
                bottomNavigationItemView.setTextColor(colorStateList);
            }
        }
        BottomNavigationItemView bottomNavigationItemView2 = this.mOverflowButton;
        if (bottomNavigationItemView2 != null) {
            bottomNavigationItemView2.setTextColor(colorStateList);
            setOverflowSpanColor(0, true);
        }
    }

    private void setOverflowSpanColor(int i, boolean z) {
        SpannableStringBuilder labelImageSpan;
        BottomNavigationItemView bottomNavigationItemView = this.mOverflowButton;
        if (!(bottomNavigationItemView == null || (labelImageSpan = bottomNavigationItemView.getLabelImageSpan()) == null)) {
            Drawable drawable = getContext().getDrawable(R.drawable.sesl_ic_menu_overflow_dark);
            ImageSpan[] imageSpanArr = (ImageSpan[]) labelImageSpan.getSpans(0, labelImageSpan.length(), ImageSpan.class);
            if (imageSpanArr != null) {
                for (ImageSpan imageSpan : imageSpanArr) {
                    labelImageSpan.removeSpan(imageSpan);
                }
            }
            ImageSpan imageSpan2 = new ImageSpan(drawable);
            drawable.setState(new int[]{16842910, -16842910});
            if (z) {
                drawable.setTintList(this.itemTextColorFromUser);
            } else {
                drawable.setTint(i);
            }
            drawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_size), getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_size));
            labelImageSpan.setSpan(imageSpan2, 0, 1, 18);
            this.mOverflowButton.setLabelImageSpan(labelImageSpan);
        }
    }

    private void initOverflowSpan(BottomNavigationItemView bottomNavigationItemView) {
        Drawable drawable = getContext().getDrawable(R.drawable.sesl_ic_menu_overflow_dark);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(" ");
        ImageSpan imageSpan = new ImageSpan(drawable);
        drawable.setState(new int[]{16842910, -16842910});
        drawable.setTintList(this.itemTextColorFromUser);
        drawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_size), getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_size));
        spannableStringBuilder.setSpan(imageSpan, 0, 1, 18);
        bottomNavigationItemView.setLabelImageSpan(spannableStringBuilder);
    }

    public ColorStateList getItemTextColor() {
        return this.itemTextColorFromUser;
    }

    public void setItemTextAppearanceInactive(int i) {
        this.itemTextAppearanceInactive = i;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView == null) {
                    break;
                }
                bottomNavigationItemView.setTextAppearanceInactive(i);
                ColorStateList colorStateList = this.itemTextColorFromUser;
                if (colorStateList != null) {
                    bottomNavigationItemView.setTextColor(colorStateList);
                }
            }
        }
        BottomNavigationItemView bottomNavigationItemView2 = this.mOverflowButton;
        if (bottomNavigationItemView2 != null) {
            bottomNavigationItemView2.setTextAppearanceInactive(i);
            ColorStateList colorStateList2 = this.itemTextColorFromUser;
            if (colorStateList2 != null) {
                this.mOverflowButton.setTextColor(colorStateList2);
            }
        }
    }

    public int getItemTextAppearanceInactive() {
        return this.itemTextAppearanceInactive;
    }

    public void setItemTextAppearanceActive(int i) {
        this.itemTextAppearanceActive = i;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView == null) {
                    break;
                }
                bottomNavigationItemView.setTextAppearanceActive(i);
                ColorStateList colorStateList = this.itemTextColorFromUser;
                if (colorStateList != null) {
                    bottomNavigationItemView.setTextColor(colorStateList);
                }
            }
        }
        BottomNavigationItemView bottomNavigationItemView2 = this.mOverflowButton;
        if (!(bottomNavigationItemView2 == null || this.itemTextColorFromUser == null)) {
            bottomNavigationItemView2.setTextAppearanceActive(i);
            this.mOverflowButton.setTextColor(this.itemTextColorFromUser);
        }
    }

    public int getItemTextAppearanceActive() {
        return this.itemTextAppearanceActive;
    }

    public void setItemBackgroundRes(int i) {
        this.itemBackgroundRes = i;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView == null) {
                    break;
                }
                bottomNavigationItemView.setItemBackground(i);
            }
        }
        BottomNavigationItemView bottomNavigationItemView2 = this.mOverflowButton;
        if (bottomNavigationItemView2 != null) {
            bottomNavigationItemView2.setItemBackground(i);
        }
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.itemBackgroundRes;
    }

    public void setItemBackground(Drawable drawable) {
        this.itemBackground = drawable;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView == null) {
                    break;
                }
                bottomNavigationItemView.setItemBackground(drawable);
            }
        }
        BottomNavigationItemView bottomNavigationItemView2 = this.mOverflowButton;
        if (bottomNavigationItemView2 != null) {
            bottomNavigationItemView2.setItemBackground(drawable);
        }
    }

    public Drawable getItemBackground() {
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr == null || bottomNavigationItemViewArr.length <= 0) {
            return this.itemBackground;
        }
        return bottomNavigationItemViewArr[0].getBackground();
    }

    public void setLabelVisibilityMode(int i) {
        this.labelVisibilityMode = i;
    }

    public int getLabelVisibilityMode() {
        return this.labelVisibilityMode;
    }

    public void setItemHorizontalTranslationEnabled(boolean z) {
        this.itemHorizontalTranslationEnabled = z;
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return this.itemHorizontalTranslationEnabled;
    }

    public ColorStateList createDefaultColorStateList(int i) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i2 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(DISABLED_STATE_SET, defaultColor), i2, defaultColor});
    }

    public void setPresenter(BottomNavigationPresenter bottomNavigationPresenter) {
        this.presenter = bottomNavigationPresenter;
    }

    /* access modifiers changed from: package-private */
    public class InternalBtnInfo {
        int cnt = 0;
        int[] originPos;

        InternalBtnInfo(int i) {
            this.originPos = new int[i];
        }
    }

    public void buildMenuView() {
        CharSequence charSequence;
        removeAllViews();
        TransitionManager.beginDelayedTransition(this, this.set);
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView == null) {
                    break;
                }
                this.itemPool.release(bottomNavigationItemView);
                bottomNavigationItemView.removeBadge();
            }
        }
        int size = this.menu.size();
        if (size == 0) {
            this.selectedItemId = 0;
            this.selectedItemPosition = 0;
            this.mVisibleItemCount = 0;
            this.mButtons = null;
            this.mOverflowButton = null;
            this.mOverflowMenu = null;
            this.mVisibleBtns = null;
            this.mInvisibleBtns = null;
            return;
        }
        removeUnusedBadges();
        boolean isShifting = isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());
        this.mButtons = new BottomNavigationItemView[5];
        this.mVisibleBtns = new InternalBtnInfo(size);
        this.mInvisibleBtns = new InternalBtnInfo(size);
        this.mOverflowMenu = new MenuBuilder(getContext());
        this.mVisibleBtns.cnt = 0;
        this.mInvisibleBtns.cnt = 0;
        for (int i = 0; i < size; i++) {
            this.presenter.setUpdateSuspended(true);
            this.menu.getItem(i).setCheckable(true);
            this.presenter.setUpdateSuspended(false);
            if (((MenuItemImpl) this.menu.getItem(i)).requiresOverflow()) {
                int[] iArr = this.mInvisibleBtns.originPos;
                InternalBtnInfo internalBtnInfo = this.mInvisibleBtns;
                int i2 = internalBtnInfo.cnt;
                internalBtnInfo.cnt = i2 + 1;
                iArr[i2] = i;
            } else {
                int[] iArr2 = this.mVisibleBtns.originPos;
                InternalBtnInfo internalBtnInfo2 = this.mVisibleBtns;
                int i3 = internalBtnInfo2.cnt;
                internalBtnInfo2.cnt = i3 + 1;
                iArr2[i3] = i;
            }
        }
        this.mHasOverflowMenu = this.mInvisibleBtns.cnt > 0;
        if (this.mVisibleBtns.cnt + (this.mHasOverflowMenu ? 1 : 0) > 5) {
            int i4 = this.mVisibleBtns.cnt;
            for (int i5 = 4; i5 < i4; i5++) {
                int[] iArr3 = this.mInvisibleBtns.originPos;
                InternalBtnInfo internalBtnInfo3 = this.mInvisibleBtns;
                int i6 = internalBtnInfo3.cnt;
                internalBtnInfo3.cnt = i6 + 1;
                iArr3[i6] = this.mVisibleBtns.originPos[i5];
                this.mVisibleBtns.cnt--;
            }
        }
        this.mVisibleItemCount = 0;
        for (int i7 = 0; i7 < this.mVisibleBtns.cnt; i7++) {
            buildInternalMenu(isShifting, this.mVisibleBtns.originPos[i7]);
        }
        if (this.mInvisibleBtns.cnt > 0) {
            int i8 = 0;
            for (int i9 = 0; i9 < this.mInvisibleBtns.cnt; i9++) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) this.menu.getItem(this.mInvisibleBtns.originPos[i9]);
                if (menuItemImpl != null) {
                    charSequence = menuItemImpl.getTitle();
                } else {
                    charSequence = menuItemImpl.getContentDescription();
                }
                this.mOverflowMenu.add(menuItemImpl.getGroupId(), menuItemImpl.getItemId(), menuItemImpl.getOrder(), charSequence).setVisible(menuItemImpl.isVisible()).setEnabled(menuItemImpl.isEnabled());
                if (!menuItemImpl.isVisible()) {
                    i8++;
                }
            }
            if (this.mInvisibleBtns.cnt - i8 > 0) {
                this.mOverflowButton = ensureOverflowButton(isShifting);
                this.mButtons[this.mVisibleBtns.cnt] = this.mOverflowButton;
                this.mVisibleItemCount++;
            }
        }
        if (this.mVisibleItemCount > 5) {
            Log.i(TAG, "Maximum number of visible items supported by BottomNavigationView is 5. Current visible count is " + this.mVisibleItemCount);
            this.mVisibleItemCount = 5;
        }
        for (int i10 = 0; i10 < this.mVisibleItemCount; i10++) {
            setShowButtonShape(this.mButtons[i10]);
        }
        this.selectedItemPosition = Math.min(4, this.selectedItemPosition);
        this.menu.getItem(this.selectedItemPosition).setChecked(true);
    }

    private void setShowButtonShape(BottomNavigationItemView bottomNavigationItemView) {
        int i;
        MenuItemImpl itemData;
        ColorStateList itemTextColor = getItemTextColor();
        if (!isShowButtonShapesEnabled()) {
            return;
        }
        if (Build.VERSION.SDK_INT > 26) {
            ColorDrawable colorDrawable = this.mSBBTextColorDrawable;
            if (colorDrawable != null) {
                i = colorDrawable.getColor();
            } else {
                i = getResources().getColor(SeslMisc.isLightTheme(getContext()) ? R.color.sesl_bottom_navigation_background_light : R.color.sesl_bottom_navigation_background_dark, null);
            }
            bottomNavigationItemView.setShowButtonShape(i, itemTextColor);
            if (this.mOverflowButton != null && (itemData = bottomNavigationItemView.getItemData()) != null && this.mDummyMenu != null && itemData.getItemId() == this.mDummyMenu.getItem(0).getItemId()) {
                setOverflowSpanColor(i, false);
                return;
            }
            return;
        }
        bottomNavigationItemView.setShowButtonShape(0, itemTextColor);
    }

    /* access modifiers changed from: protected */
    public void setOverflowSelectedCallback(MenuBuilder.Callback callback) {
        this.mSelectedCallback = callback;
    }

    /* access modifiers changed from: package-private */
    public boolean hasOverflowButton() {
        return this.mHasOverflowMenu;
    }

    /* access modifiers changed from: package-private */
    public MenuBuilder getOverflowMenu() {
        return this.mOverflowMenu;
    }

    /* access modifiers changed from: package-private */
    public void showOverflowMenu() {
        BottomNavigationPresenter bottomNavigationPresenter;
        if (hasOverflowButton() && (bottomNavigationPresenter = this.presenter) != null) {
            bottomNavigationPresenter.showOverflowMenu(this.mOverflowMenu);
        }
    }

    /* access modifiers changed from: package-private */
    public void hideOverflowMenu() {
        BottomNavigationPresenter bottomNavigationPresenter;
        if (hasOverflowButton() && (bottomNavigationPresenter = this.presenter) != null && bottomNavigationPresenter.isOverflowMenuShowing()) {
            this.presenter.hideOverflowMenu();
        }
    }

    private void buildInternalMenu(boolean z, int i) {
        if (this.mButtons != null) {
            BottomNavigationItemView newItem = getNewItem(this.mHasIcon);
            this.mButtons[this.mVisibleItemCount] = newItem;
            newItem.setVisibility(0);
            newItem.setIconTintList(this.itemIconTint);
            newItem.setIconSize(this.itemIconSize);
            newItem.setTextColor(this.itemTextColorDefault);
            newItem.setTextAppearanceInactive(this.itemTextAppearanceInactive);
            newItem.setTextAppearanceActive(this.itemTextAppearanceActive);
            newItem.setTextColor(this.itemTextColorFromUser);
            Drawable drawable = this.itemBackground;
            if (drawable != null) {
                newItem.setItemBackground(drawable);
            } else {
                newItem.setItemBackground(this.itemBackgroundRes);
            }
            newItem.setShifting(z);
            newItem.setLabelVisibilityMode(this.labelVisibilityMode);
            newItem.initialize((MenuItemImpl) this.menu.getItem(i), 0);
            newItem.setItemPosition(this.mVisibleItemCount);
            newItem.setOnClickListener(this.onClickListener);
            if (this.selectedItemId != 0 && this.menu.getItem(i).getItemId() == this.selectedItemId) {
                this.selectedItemPosition = this.mVisibleItemCount;
            }
            setBadgeIfNeeded(newItem);
            if (newItem.getParent() instanceof ViewGroup) {
                ((ViewGroup) newItem.getParent()).removeView(newItem);
            }
            addView(newItem);
            this.mVisibleItemCount++;
        }
    }

    private BottomNavigationItemView ensureOverflowButton(boolean z) {
        this.mHasOverflowMenu = true;
        this.mDummyMenu = new MenuBuilder(getContext());
        new MenuInflater(getContext()).inflate(R.menu.bnv_dummy_overflow_menu_icon, this.mDummyMenu);
        if (this.mDummyMenu.getItem(0) instanceof MenuItemImpl) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mDummyMenu.getItem(0);
            if (this.mHasIcon) {
                menuItemImpl.setTooltipText((CharSequence) null);
            } else {
                menuItemImpl.setTooltipText((CharSequence) getResources().getString(R.string.sesl_more_item_label));
            }
        }
        BottomNavigationItemView newItem = getNewItem(this.mHasIcon);
        newItem.setIconTintList(this.itemIconTint);
        newItem.setIconSize(this.itemIconSize);
        newItem.setTextColor(this.itemTextColorDefault);
        newItem.setTextAppearanceInactive(this.itemTextAppearanceInactive);
        newItem.setTextAppearanceActive(this.itemTextAppearanceActive);
        newItem.setTextColor(this.itemTextColorFromUser);
        Drawable drawable = this.itemBackground;
        if (drawable != null) {
            newItem.setItemBackground(drawable);
        } else {
            newItem.setItemBackground(this.itemBackgroundRes);
        }
        newItem.setShifting(z);
        newItem.setLabelVisibilityMode(this.labelVisibilityMode);
        newItem.initialize((MenuItemImpl) this.mDummyMenu.getItem(0), 0);
        newItem.setItemPosition(this.mVisibleItemCount);
        newItem.setOnClickListener(new View.OnClickListener() {
            /* class com.google.android.material.bottomnavigation.BottomNavigationMenuView.AnonymousClass2 */

            public void onClick(View view) {
                BottomNavigationMenuView.this.mOverflowMenu.setCallback(BottomNavigationMenuView.this.mSelectedCallback);
                BottomNavigationMenuView.this.presenter.showOverflowMenu(BottomNavigationMenuView.this.mOverflowMenu);
            }
        });
        if (!this.mHasIcon) {
            initOverflowSpan(newItem);
        }
        if (newItem.getParent() instanceof ViewGroup) {
            ((ViewGroup) newItem.getParent()).removeView(newItem);
        }
        addView(newItem);
        return newItem;
    }

    public void updateMenuView() {
        MenuBuilder menuBuilder = this.menu;
        if (!(menuBuilder == null || this.mButtons == null || this.mVisibleBtns == null || this.mInvisibleBtns == null)) {
            int size = menuBuilder.size();
            hideOverflowMenu();
            if (size != this.mVisibleBtns.cnt + this.mInvisibleBtns.cnt) {
                buildMenuView();
                return;
            }
            int i = this.selectedItemId;
            for (int i2 = 0; i2 < this.mVisibleBtns.cnt; i2++) {
                MenuItem item = this.menu.getItem(this.mVisibleBtns.originPos[i2]);
                if (item.isChecked()) {
                    this.selectedItemId = item.getItemId();
                    this.selectedItemPosition = i2;
                }
            }
            if (i != this.selectedItemId) {
                TransitionManager.beginDelayedTransition(this, this.set);
            }
            boolean isShifting = isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());
            for (int i3 = 0; i3 < this.mVisibleBtns.cnt; i3++) {
                this.presenter.setUpdateSuspended(true);
                this.mButtons[i3].setLabelVisibilityMode(this.labelVisibilityMode);
                this.mButtons[i3].setShifting(isShifting);
                this.mButtons[i3].initialize((MenuItemImpl) this.menu.getItem(this.mVisibleBtns.originPos[i3]), 0);
                this.presenter.setUpdateSuspended(false);
            }
        }
    }

    private BottomNavigationItemView getNewItem(boolean z) {
        BottomNavigationItemView acquire = this.itemPool.acquire();
        return acquire == null ? new BottomNavigationItemView(getContext(), z) : acquire;
    }

    public int getSelectedItemId() {
        return this.selectedItemId;
    }

    /* access modifiers changed from: package-private */
    public void tryRestoreSelectedItemId(int i) {
        MenuBuilder menuBuilder = this.menu;
        if (!(menuBuilder == null || this.mVisibleBtns == null || menuBuilder.size() == 0)) {
            for (int i2 = 0; i2 < this.mVisibleBtns.cnt; i2++) {
                MenuItem item = this.menu.getItem(this.mVisibleBtns.originPos[i2]);
                if (i == item.getItemId()) {
                    this.selectedItemId = i;
                    this.selectedItemPosition = i2;
                    item.setChecked(true);
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public SparseArray<BadgeDrawable> getBadgeDrawables() {
        return this.badgeDrawables;
    }

    /* access modifiers changed from: package-private */
    public void setBadgeDrawables(SparseArray<BadgeDrawable> sparseArray) {
        this.badgeDrawables = sparseArray;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView != null) {
                    bottomNavigationItemView.setBadge(sparseArray.get(bottomNavigationItemView.getId()));
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public BadgeDrawable getBadge(int i) {
        return this.badgeDrawables.get(i);
    }

    /* access modifiers changed from: package-private */
    public BadgeDrawable getOrCreateBadge(int i) {
        validateMenuItemId(i);
        BadgeDrawable badgeDrawable = this.badgeDrawables.get(i);
        if (badgeDrawable == null) {
            badgeDrawable = BadgeDrawable.create(getContext());
            this.badgeDrawables.put(i, badgeDrawable);
        }
        BottomNavigationItemView findItemView = findItemView(i);
        if (findItemView != null) {
            findItemView.setBadge(badgeDrawable);
        }
        return badgeDrawable;
    }

    /* access modifiers changed from: package-private */
    public void removeBadge(int i) {
        validateMenuItemId(i);
        BadgeDrawable badgeDrawable = this.badgeDrawables.get(i);
        BottomNavigationItemView findItemView = findItemView(i);
        if (findItemView != null) {
            findItemView.removeBadge();
        }
        if (badgeDrawable != null) {
            this.badgeDrawables.remove(i);
        }
    }

    private void setBadgeIfNeeded(BottomNavigationItemView bottomNavigationItemView) {
        BadgeDrawable badgeDrawable;
        int id = bottomNavigationItemView.getId();
        if (isValidId(id) && (badgeDrawable = this.badgeDrawables.get(id)) != null) {
            bottomNavigationItemView.setBadge(badgeDrawable);
        }
    }

    private void removeUnusedBadges() {
        if (this.menu != null) {
            HashSet hashSet = new HashSet();
            for (int i = 0; i < this.menu.size(); i++) {
                hashSet.add(Integer.valueOf(this.menu.getItem(i).getItemId()));
            }
            for (int i2 = 0; i2 < this.badgeDrawables.size(); i2++) {
                int keyAt = this.badgeDrawables.keyAt(i2);
                if (!hashSet.contains(Integer.valueOf(keyAt))) {
                    this.badgeDrawables.delete(keyAt);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public BottomNavigationItemView findItemView(int i) {
        validateMenuItemId(i);
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
        if (bottomNavigationItemViewArr == null) {
            return null;
        }
        for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
            if (bottomNavigationItemView == null) {
                return null;
            }
            if (bottomNavigationItemView.getId() == i) {
                return bottomNavigationItemView;
            }
        }
        return null;
    }

    private void validateMenuItemId(int i) {
        if (!isValidId(i)) {
            throw new IllegalArgumentException(i + " is not a valid view id");
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.activeItemMaxWidth = (int) (((float) getResources().getDisplayMetrics().widthPixels) * this.mWidthPercent);
        if (this.mHasIcon) {
            setItemIconSize(getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_size));
            BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
            if (bottomNavigationItemViewArr != null) {
                for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                    if (bottomNavigationItemView == null) {
                        break;
                    }
                    bottomNavigationItemView.updateBaseLineTopMargin(getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_size));
                }
            }
        }
        hideOverflowMenu();
    }

    private boolean isShowButtonShapesEnabled() {
        return Settings.System.getInt(this.mContentResolver, "show_button_background", 0) == 1;
    }

    public void setBackgroundColorDrawable(ColorDrawable colorDrawable) {
        this.mSBBTextColorDrawable = colorDrawable;
    }

    public ColorDrawable getBackgroundColorDrawable() {
        return this.mSBBTextColorDrawable;
    }
}
