package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.util.SeslShowButtonShapesHelper;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ActionProvider;
import androidx.core.view.GravityCompat;
import androidx.core.widget.TextViewCompat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/* access modifiers changed from: package-private */
public class ActionMenuPresenter extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {
    private static final String TAG = "ActionMenuPresenter";
    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private boolean mExpandedActionViewsExclusive;
    private boolean mHasNavigationBar = false;
    private boolean mIsLightTheme = false;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    private NumberFormat mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
    int mOpenSubMenuId;
    OverflowMenuButton mOverflowButton;
    OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private boolean mStrictWidthLimit;
    private CharSequence mTooltipText;
    private boolean mUseTextItemMode;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    public ActionMenuPresenter(Context context) {
        super(context, R.layout.sesl_action_menu_layout, R.layout.sesl_action_menu_item_layout);
        this.mUseTextItemMode = context.getResources().getBoolean(R.bool.sesl_action_bar_text_item_mode);
        this.mHasNavigationBar = ActionBarPolicy.get(context).hasNavigationBar();
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        Resources resources = context.getResources();
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = actionBarPolicy.showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = actionBarPolicy.getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = actionBarPolicy.getMaxActionButtons();
        }
        int i = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
                this.mOverflowButton.setId(R.id.sesl_action_bar_overflow_button);
                if (this.mPendingOverflowIconSet) {
                    if (this.mUseTextItemMode) {
                        ((AppCompatImageView) this.mOverflowButton.getInnerView()).setImageDrawable(this.mPendingOverflowIcon);
                    }
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.mOverflowButton.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = i;
        this.mMinCellSize = (int) (resources.getDisplayMetrics().density * 56.0f);
    }

    public void onConfigurationChanged(Configuration configuration) {
        OverflowMenuButton overflowMenuButton;
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.mContext);
        if (!this.mMaxItemsSet) {
            this.mMaxItems = actionBarPolicy.getMaxActionButtons();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = actionBarPolicy.getEmbeddedMenuWidthLimit();
        }
        if (!this.mReserveOverflow || (overflowMenuButton = this.mOverflowButton) == null) {
            this.mActionItemWidthLimit = this.mWidthLimit;
        } else {
            this.mActionItemWidthLimit = this.mWidthLimit - overflowMenuButton.getMeasuredWidth();
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    public void setWidthLimit(int i, boolean z) {
        this.mWidthLimit = i;
        this.mStrictWidthLimit = z;
        this.mWidthLimitSet = true;
    }

    public void setReserveOverflow(boolean z) {
        this.mReserveOverflow = z;
        this.mReserveOverflowSet = true;
    }

    public void setItemLimit(int i) {
        this.mMaxItems = i;
        this.mMaxItemsSet = true;
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.mExpandedActionViewsExclusive = z;
    }

    public void setOverflowIcon(Drawable drawable) {
        if (!this.mUseTextItemMode) {
            OverflowMenuButton overflowMenuButton = this.mOverflowButton;
            if (overflowMenuButton != null) {
                ((AppCompatImageView) overflowMenuButton.getInnerView()).setImageDrawable(drawable);
                return;
            }
            this.mPendingOverflowIconSet = true;
            this.mPendingOverflowIcon = drawable;
        }
    }

    public Drawable getOverflowIcon() {
        if (this.mUseTextItemMode) {
            return null;
        }
        OverflowMenuButton overflowMenuButton = this.mOverflowButton;
        if (overflowMenuButton != null) {
            return ((AppCompatImageView) overflowMenuButton.getInnerView()).getDrawable();
        }
        if (this.mPendingOverflowIconSet) {
            return this.mPendingOverflowIcon;
        }
        return null;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        MenuView menuView = this.mMenuView;
        MenuView menuView2 = super.getMenuView(viewGroup);
        if (menuView != menuView2) {
            ((ActionMenuView) menuView2).setPresenter(this);
        }
        return menuView2;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        View actionView = menuItemImpl.getActionView();
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            actionView = super.getItemView(menuItemImpl, view, viewGroup);
        }
        actionView.setVisibility(menuItemImpl.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
        itemView.initialize(menuItemImpl, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) itemView;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.mMenuView);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        actionMenuItemView.setPopupCallback(this.mPopupCallback);
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public boolean shouldIncludeItem(int i, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        super.updateMenuView(z);
        if (this.mMenuView != null) {
            ((View) this.mMenuView).requestLayout();
        }
        boolean z2 = false;
        if (this.mMenu != null) {
            ArrayList<MenuItemImpl> actionItems = this.mMenu.getActionItems();
            int size = actionItems.size();
            for (int i = 0; i < size; i++) {
                ActionProvider supportActionProvider = actionItems.get(i).getSupportActionProvider();
                if (supportActionProvider != null) {
                    supportActionProvider.setSubUiVisibilityListener(this);
                }
            }
        }
        ArrayList<MenuItemImpl> nonActionItems = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        if (this.mReserveOverflow && nonActionItems != null) {
            int size2 = nonActionItems.size();
            if (size2 == 1) {
                z2 = !nonActionItems.get(0).isActionViewExpanded();
            } else if (size2 > 0) {
                z2 = true;
            }
        }
        if (z2) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
                this.mOverflowButton.setId(R.id.sesl_action_bar_overflow_button);
            }
            ViewGroup viewGroup = (ViewGroup) this.mOverflowButton.getParent();
            if (viewGroup != this.mMenuView) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.mOverflowButton);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.mMenuView;
                if (actionMenuView != null) {
                    actionMenuView.addView(this.mOverflowButton, actionMenuView.generateOverflowButtonLayoutParams());
                }
            }
        } else {
            OverflowMenuButton overflowMenuButton = this.mOverflowButton;
            if (overflowMenuButton != null && overflowMenuButton.getParent() == this.mMenuView) {
                if (this.mMenuView != null) {
                    ((ViewGroup) this.mMenuView).removeView(this.mOverflowButton);
                }
                if (isOverflowMenuShowing()) {
                    hideOverflowMenu();
                }
            }
        }
        if (!(this.mOverflowButton == null || this.mMenuView == null)) {
            ActionMenuView actionMenuView2 = (ActionMenuView) this.mMenuView;
            this.mOverflowButton.setBadgeText(actionMenuView2.getOverflowBadgeText(), actionMenuView2.getSumOfDigitsInBadges());
        }
        OverflowMenuButton overflowMenuButton2 = this.mOverflowButton;
        if ((overflowMenuButton2 == null || overflowMenuButton2.getVisibility() != 0) && isOverflowMenuShowing()) {
            hideOverflowMenu();
        }
        if (this.mMenuView != null) {
            ((ActionMenuView) this.mMenuView).setOverflowReserved(this.mReserveOverflow);
        }
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public boolean filterLeftoverView(ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) == this.mOverflowButton) {
            return false;
        }
        return super.filterLeftoverView(viewGroup, i);
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        boolean z = false;
        if (subMenuBuilder == null || !subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        while (subMenuBuilder2.getParentMenu() != this.mMenu) {
            subMenuBuilder2 = (SubMenuBuilder) subMenuBuilder2.getParentMenu();
        }
        View findViewForItem = findViewForItem(subMenuBuilder2.getItem());
        if (findViewForItem == null) {
            return false;
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        int size = subMenuBuilder.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            MenuItem item = subMenuBuilder.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                z = true;
                break;
            }
            i++;
        }
        this.mActionButtonPopup = new ActionButtonSubmenu(this.mContext, subMenuBuilder, findViewForItem);
        this.mActionButtonPopup.setForceShowIcon(z);
        this.mActionButtonPopup.show();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    private View findViewForItem(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof MenuView.ItemView) && ((MenuView.ItemView) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    public boolean showOverflowMenu() {
        if (!this.mReserveOverflow || isOverflowMenuShowing() || this.mMenu == null || this.mMenuView == null || this.mPostedOpenRunnable != null || this.mMenu.getNonActionItems().isEmpty()) {
            return false;
        }
        this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true));
        ((View) this.mMenuView).post(this.mPostedOpenRunnable);
        super.onSubMenuSelected(null);
        return true;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable == null || this.mMenuView == null) {
            OverflowPopup overflowPopup = this.mOverflowPopup;
            if (overflowPopup == null) {
                return false;
            }
            overflowPopup.dismiss();
            return true;
        }
        ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
        this.mPostedOpenRunnable = null;
        return true;
    }

    public boolean dismissPopupMenus() {
        return hideOverflowMenu() | hideSubMenus();
    }

    public boolean hideSubMenus() {
        ActionButtonSubmenu actionButtonSubmenu = this.mActionButtonPopup;
        if (actionButtonSubmenu == null) {
            return false;
        }
        actionButtonSubmenu.dismiss();
        return true;
    }

    public boolean isOverflowMenuShowing() {
        OverflowPopup overflowPopup = this.mOverflowPopup;
        return overflowPopup != null && overflowPopup.isShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPostedOpenRunnable != null || isOverflowMenuShowing();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        int i;
        ArrayList<MenuItemImpl> arrayList;
        int i2;
        int i3;
        int i4;
        boolean z;
        ActionMenuPresenter actionMenuPresenter = this;
        View view = null;
        boolean z2 = false;
        if (actionMenuPresenter.mMenu != null) {
            arrayList = actionMenuPresenter.mMenu.getVisibleItems();
            i = arrayList.size();
        } else {
            arrayList = null;
            i = 0;
        }
        int i5 = actionMenuPresenter.mMaxItems;
        int i6 = actionMenuPresenter.mActionItemWidthLimit;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        if (actionMenuPresenter.mMenuView == null) {
            Log.d(TAG, "mMenuView is null, maybe Menu has not been initialized.");
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) actionMenuPresenter.mMenuView;
        boolean z3 = false;
        int i7 = 0;
        int i8 = 0;
        int i9 = i5;
        for (int i10 = 0; i10 < i; i10++) {
            MenuItemImpl menuItemImpl = arrayList.get(i10);
            if (menuItemImpl.requiresActionButton()) {
                i7++;
            } else if (menuItemImpl.requestsActionButton()) {
                i8++;
            } else {
                z3 = true;
            }
            if (actionMenuPresenter.mExpandedActionViewsExclusive && menuItemImpl.isActionViewExpanded()) {
                i9 = 0;
            }
        }
        if (actionMenuPresenter.mReserveOverflow && (z3 || i8 + i7 > i9)) {
            i9--;
        }
        int i11 = i9 - i7;
        SparseBooleanArray sparseBooleanArray = actionMenuPresenter.mActionButtonGroups;
        sparseBooleanArray.clear();
        if (actionMenuPresenter.mStrictWidthLimit) {
            int i12 = actionMenuPresenter.mMinCellSize;
            i2 = i6 / i12;
            i3 = i12 + ((i6 % i12) / i2);
        } else {
            i3 = 0;
            i2 = 0;
        }
        int i13 = 0;
        int i14 = i6;
        int i15 = 0;
        while (i15 < i) {
            MenuItemImpl menuItemImpl2 = arrayList.get(i15);
            if (menuItemImpl2.requiresActionButton()) {
                View itemView = actionMenuPresenter.getItemView(menuItemImpl2, view, viewGroup);
                if (actionMenuPresenter.mStrictWidthLimit) {
                    int i16 = z2 ? 1 : 0;
                    int i17 = z2 ? 1 : 0;
                    int i18 = z2 ? 1 : 0;
                    i2 -= ActionMenuView.measureChildForCells(itemView, i3, i2, makeMeasureSpec, i16);
                } else {
                    itemView.measure(makeMeasureSpec, makeMeasureSpec);
                }
                int measuredWidth = itemView.getMeasuredWidth();
                i14 -= measuredWidth;
                if (i13 != 0) {
                    measuredWidth = i13;
                }
                int groupId = menuItemImpl2.getGroupId();
                if (groupId != 0) {
                    sparseBooleanArray.put(groupId, true);
                }
                menuItemImpl2.setIsActionButton(true);
                i13 = measuredWidth;
                z = z2;
                i4 = i;
            } else if (menuItemImpl2.requestsActionButton()) {
                int groupId2 = menuItemImpl2.getGroupId();
                boolean z4 = sparseBooleanArray.get(groupId2);
                boolean z5 = (i11 > 0 || z4) && i14 > 0 && (!actionMenuPresenter.mStrictWidthLimit || i2 > 0);
                boolean z6 = z5;
                i4 = i;
                if (z5) {
                    View itemView2 = actionMenuPresenter.getItemView(menuItemImpl2, null, viewGroup);
                    if (actionMenuPresenter.mStrictWidthLimit) {
                        int measureChildForCells = ActionMenuView.measureChildForCells(itemView2, i3, i2, makeMeasureSpec, 0);
                        i2 -= measureChildForCells;
                        z6 = measureChildForCells == 0 ? false : z6;
                    } else {
                        itemView2.measure(makeMeasureSpec, makeMeasureSpec);
                    }
                    int measuredWidth2 = itemView2.getMeasuredWidth();
                    i14 -= measuredWidth2;
                    if (i13 == 0) {
                        i13 = measuredWidth2;
                    }
                    z5 = z6 & (i14 >= 0);
                }
                if (z5 && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                } else if (z4) {
                    sparseBooleanArray.put(groupId2, false);
                    for (int i19 = 0; i19 < i15; i19++) {
                        MenuItemImpl menuItemImpl3 = arrayList.get(i19);
                        if (menuItemImpl3.getGroupId() == groupId2) {
                            if (menuItemImpl3.isActionButton()) {
                                i11++;
                            }
                            menuItemImpl3.setIsActionButton(false);
                        }
                    }
                }
                if (z5) {
                    i11--;
                }
                menuItemImpl2.setIsActionButton(z5);
                z = false;
            } else {
                boolean z7 = z2 ? 1 : 0;
                Object[] objArr = z2 ? 1 : 0;
                Object[] objArr2 = z2 ? 1 : 0;
                z = z7;
                i4 = i;
                menuItemImpl2.setIsActionButton(z);
            }
            i15++;
            view = null;
            z2 = z;
            i = i4;
            actionMenuPresenter = this;
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        dismissPopupMenus();
        super.onCloseMenu(menuBuilder, z);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem findItem;
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            if (savedState.openSubMenuId > 0 && this.mMenu != null && (findItem = this.mMenu.findItem(savedState.openSubMenuId)) != null) {
                onSubMenuSelected((SubMenuBuilder) findItem.getSubMenu());
            }
        }
    }

    @Override // androidx.core.view.ActionProvider.SubUiVisibilityListener
    public void onSubUiVisibilityChanged(boolean z) {
        if (z) {
            super.onSubMenuSelected(null);
        } else if (this.mMenu != null) {
            this.mMenu.close(false);
        }
    }

    public void setMenuView(ActionMenuView actionMenuView) {
        this.mMenuView = actionMenuView;
        actionMenuView.initialize(this.mMenu);
    }

    /* access modifiers changed from: private */
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class androidx.appcompat.widget.ActionMenuPresenter.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int openSubMenuId;

        public int describeContents() {
            return 0;
        }

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.openSubMenuId);
        }
    }

    /* access modifiers changed from: package-private */
    public class OverflowMenuButton extends FrameLayout implements ActionMenuView.ActionMenuChildView {
        private static final int BADGE_LIMIT_NUMBER = 99;
        private ViewGroup mBadgeBackground;
        private CharSequence mBadgeContentDescription;
        private TextView mBadgeText;
        private CharSequence mContentDescription;
        private View mInnerView;

        @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerAfter() {
            return false;
        }

        @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerBefore() {
            return false;
        }

        public OverflowMenuButton(Context context) {
            super(context);
            this.mInnerView = ActionMenuPresenter.this.mUseTextItemMode ? new OverflowTextView(context) : new OverflowImageView(context);
            addView(this.mInnerView, new FrameLayout.LayoutParams(-2, -2));
            Resources resources = getResources();
            View view = this.mInnerView;
            if (view instanceof OverflowImageView) {
                this.mContentDescription = view.getContentDescription();
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_action_menu_overflow_badge_description);
            }
            if (TextUtils.isEmpty(this.mContentDescription)) {
                this.mContentDescription = resources.getString(R.string.sesl_action_menu_overflow_description);
                View view2 = this.mInnerView;
                if (view2 != null) {
                    view2.setContentDescription(this.mContentDescription);
                }
            }
            this.mBadgeBackground = (ViewGroup) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sesl_action_menu_item_badge, (ViewGroup) this, false);
            this.mBadgeText = (TextView) this.mBadgeBackground.getChildAt(0);
            addView(this.mBadgeBackground);
        }

        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            Resources resources = getResources();
            this.mBadgeText.setTextSize(0, (float) ((int) resources.getDimension(R.dimen.sesl_menu_item_badge_text_size)));
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBadgeBackground.getLayoutParams();
            marginLayoutParams.width = (int) (resources.getDimension(R.dimen.sesl_badge_default_width) + (((float) this.mBadgeText.getText().length()) * resources.getDimension(R.dimen.sesl_badge_additional_width)));
            marginLayoutParams.height = (int) resources.getDimension(R.dimen.sesl_menu_item_badge_size);
            if (Build.VERSION.SDK_INT >= 17) {
                marginLayoutParams.setMarginEnd((int) resources.getDimension(R.dimen.sesl_menu_item_badge_end_margin));
            }
            this.mBadgeBackground.setLayoutParams(marginLayoutParams);
            if (this.mInnerView instanceof OverflowImageView) {
                this.mContentDescription = getContentDescription();
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_action_menu_overflow_badge_description);
            }
            if (TextUtils.isEmpty(this.mContentDescription)) {
                this.mContentDescription = resources.getString(R.string.sesl_action_menu_overflow_description);
                this.mBadgeContentDescription = ((Object) this.mContentDescription) + " , " + resources.getString(R.string.sesl_action_menu_overflow_badge_description);
            }
            if (this.mBadgeBackground.getVisibility() == 0) {
                View view = this.mInnerView;
                if (view instanceof OverflowImageView) {
                    view.setContentDescription(this.mBadgeContentDescription);
                    return;
                }
                return;
            }
            View view2 = this.mInnerView;
            if (view2 instanceof OverflowImageView) {
                view2.setContentDescription(this.mContentDescription);
            }
        }

        public View getInnerView() {
            return this.mInnerView;
        }

        public void setBadgeText(String str, int i) {
            if (i > 99) {
                i = 99;
            }
            if (str == null && !str.equals("")) {
                str = ActionMenuPresenter.this.mNumberFormat.format((long) i);
            }
            this.mBadgeText.setText(str);
            int dimension = (int) (getResources().getDimension(R.dimen.sesl_badge_default_width) + (((float) str.length()) * getResources().getDimension(R.dimen.sesl_badge_additional_width)));
            ViewGroup.LayoutParams layoutParams = this.mBadgeBackground.getLayoutParams();
            layoutParams.width = dimension;
            this.mBadgeBackground.setLayoutParams(layoutParams);
            this.mBadgeBackground.setVisibility(i > 0 ? 0 : 8);
            if (this.mBadgeBackground.getVisibility() == 0) {
                View view = this.mInnerView;
                if (view instanceof OverflowImageView) {
                    view.setContentDescription(this.mBadgeContentDescription);
                    return;
                }
                return;
            }
            View view2 = this.mInnerView;
            if (view2 instanceof OverflowImageView) {
                view2.setContentDescription(this.mContentDescription);
            }
        }
    }

    /* access modifiers changed from: private */
    public class OverflowImageView extends AppCompatImageView {
        private SeslShowButtonShapesHelper mSBSHelper;

        public OverflowImageView(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setLongClickable(true);
            Resources resources = getResources();
            ActionMenuPresenter.this.mTooltipText = resources.getString(R.string.sesl_action_menu_overflow_description);
            TooltipCompat.setTooltipText(this, ActionMenuPresenter.this.mTooltipText);
            if (Build.VERSION.SDK_INT <= 27) {
                this.mSBSHelper = new SeslShowButtonShapesHelper(this, ResourcesCompat.getDrawable(resources, R.drawable.sesl_more_button_show_button_shapes_background, null), getBackground());
            }
        }

        /* access modifiers changed from: protected */
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            Context context = getContext();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.View, R.attr.actionOverflowButtonStyle, 0);
            setMinimumHeight(obtainStyledAttributes.getDimensionPixelSize(R.styleable.View_android_minHeight, 0));
            ActionMenuPresenter.this.mTooltipText = context.getResources().getString(R.string.sesl_action_menu_overflow_description);
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(null, R.styleable.AppCompatImageView, R.attr.actionOverflowButtonStyle, 0);
            Drawable drawable = ContextCompat.getDrawable(context, obtainStyledAttributes2.getResourceId(R.styleable.AppCompatImageView_android_src, -1));
            if (drawable != null) {
                setImageDrawable(drawable);
            }
            obtainStyledAttributes2.recycle();
            SeslShowButtonShapesHelper seslShowButtonShapesHelper = this.mSBSHelper;
            if (seslShowButtonShapesHelper != null) {
                seslShowButtonShapesHelper.updateOverflowButtonBackground(ContextCompat.getDrawable(context, R.drawable.sesl_more_button_show_button_shapes_background));
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            SeslShowButtonShapesHelper seslShowButtonShapesHelper = this.mSBSHelper;
            if (seslShowButtonShapesHelper != null) {
                seslShowButtonShapesHelper.updateButtonBackground();
            }
        }

        public boolean performLongClick() {
            TooltipCompat.seslSetTooltipForceActionBarPosX(true);
            TooltipCompat.seslSetTooltipForceBelow(true);
            return super.performLongClick();
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            if (ActionMenuPresenter.this.showOverflowMenu() && isHovered()) {
                TooltipCompat.setTooltipNull(true);
            }
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (!(drawable == null || background == null)) {
                int width = getWidth();
                int height = getHeight();
                int paddingLeft = (getPaddingLeft() - getPaddingRight()) / 2;
                DrawableCompat.setHotspotBounds(background, paddingLeft, 0, width + paddingLeft, height);
            }
            return frame;
        }
    }

    private class OverflowTextView extends AppCompatTextView {
        private SeslShowButtonShapesHelper mSBBHelper;

        public OverflowTextView(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R.styleable.AppCompatTheme, 0, 0);
            TextViewCompat.setTextAppearance(this, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_actionMenuTextAppearance, 0));
            obtainStyledAttributes.recycle();
            Resources resources = getResources();
            setText(resources.getString(R.string.sesl_more_item_label));
            ActionMenuPresenter.this.mIsLightTheme = SeslMisc.isLightTheme(context);
            if (ActionMenuPresenter.this.mIsLightTheme) {
                setBackgroundResource(R.drawable.sesl_action_bar_item_text_background_light);
            } else {
                setBackgroundResource(R.drawable.sesl_action_bar_item_text_background_dark);
            }
            if (Build.VERSION.SDK_INT > 27) {
                seslSetButtonShapeEnabled(true);
            } else {
                this.mSBBHelper = new SeslShowButtonShapesHelper(this, ResourcesCompat.getDrawable(resources, R.drawable.sesl_action_text_button_show_button_shapes_background, null), getBackground());
            }
        }

        /* access modifiers changed from: protected */
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            SeslShowButtonShapesHelper seslShowButtonShapesHelper = this.mSBBHelper;
            if (seslShowButtonShapesHelper != null) {
                seslShowButtonShapesHelper.updateButtonBackground();
            }
        }

        /* access modifiers changed from: protected */
        @Override // androidx.appcompat.widget.AppCompatTextView
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            SeslShowButtonShapesHelper seslShowButtonShapesHelper = this.mSBBHelper;
            if (seslShowButtonShapesHelper != null) {
                seslShowButtonShapesHelper.updateButtonBackground();
            }
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }
    }

    /* access modifiers changed from: private */
    public class OverflowPopup extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean z) {
            super(context, menuBuilder, view, z, R.attr.actionOverflowMenuStyle);
            setGravity(GravityCompat.END);
            setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        /* access modifiers changed from: protected */
        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public void onDismiss() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.this.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    /* access modifiers changed from: private */
    public class ActionButtonSubmenu extends MenuPopupHelper {
        public ActionButtonSubmenu(Context context, SubMenuBuilder subMenuBuilder, View view) {
            super(context, subMenuBuilder, view, false, R.attr.actionOverflowMenuStyle);
            if (!((MenuItemImpl) subMenuBuilder.getItem()).isActionButton()) {
                setAnchorView(ActionMenuPresenter.this.mOverflowButton == null ? (View) ActionMenuPresenter.this.mMenuView : ActionMenuPresenter.this.mOverflowButton);
            }
            setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        /* access modifiers changed from: protected */
        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public void onDismiss() {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            actionMenuPresenter.mActionButtonPopup = null;
            actionMenuPresenter.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    private class PopupPresenterCallback implements MenuPresenter.Callback {
        PopupPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) menuBuilder).getItem().getItemId();
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                return callback.onOpenSubMenu(menuBuilder);
            }
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }
    }

    /* access modifiers changed from: private */
    public class OpenOverflowRunnable implements Runnable {
        private OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        public void run() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.changeMenuMode();
            }
            View view = (View) ActionMenuPresenter.this.mMenuView;
            if (!(view == null || view.getWindowToken() == null || !this.mPopup.tryShow(0, 0))) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    private class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
        ActionMenuPopupCallback() {
        }

        @Override // androidx.appcompat.view.menu.ActionMenuItemView.PopupCallback
        public ShowableListMenu getPopup() {
            if (ActionMenuPresenter.this.mActionButtonPopup != null) {
                return ActionMenuPresenter.this.mActionButtonPopup.getPopup();
            }
            return null;
        }
    }
}
