package com.google.android.material.bottomnavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.GravityCompat;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ParcelableSparseArray;

public class BottomNavigationPresenter extends BaseMenuPresenter implements MenuPresenter {
    private static final int ANIM_UPDATE_DELAY = 180;
    private static final int ANIM_UPDATE_DURATION = 400;
    private static final int MSG_UPDATE_ANIMATION = 100;
    private int id;
    private Handler mAnimationHandler = new Handler(Looper.getMainLooper()) {
        /* class com.google.android.material.bottomnavigation.BottomNavigationPresenter.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (message.what == 100) {
                BottomNavigationPresenter.this.updateMenuViewWithAnimate();
            }
        }
    };
    private Context mContext;
    private OverflowPopup mOverflowPopup;
    private final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    private OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mSetAnim = false;
    private MenuBuilder menu;
    private BottomNavigationMenuView menuView;
    private boolean updateSuspended = false;

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
    }

    BottomNavigationPresenter(Context context) {
        super(context, R.layout.sesl_action_menu_layout, R.layout.sesl_action_menu_item_layout);
    }

    /* access modifiers changed from: package-private */
    public void setBottomNavigationMenuView(BottomNavigationMenuView bottomNavigationMenuView) {
        this.menuView = bottomNavigationMenuView;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.menu = menuBuilder;
        this.menuView.initialize(this.menu);
        this.mContext = context;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        return this.menuView;
    }

    /* access modifiers changed from: package-private */
    public void setAnimationEnable(boolean z) {
        this.mSetAnim = z;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        if (!this.updateSuspended) {
            if (this.mSetAnim) {
                if (z) {
                    if (this.mAnimationHandler.hasMessages(100)) {
                        this.mAnimationHandler.removeMessages(100);
                    }
                    this.mAnimationHandler.sendEmptyMessage(100);
                    return;
                }
                this.menuView.postDelayed(new Runnable() {
                    /* class com.google.android.material.bottomnavigation.BottomNavigationPresenter.AnonymousClass2 */

                    public void run() {
                        BottomNavigationPresenter.this.menuView.updateMenuView();
                    }
                }, 180);
            } else if (z) {
                this.menuView.buildMenuView();
            } else {
                this.menuView.updateMenuView();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateMenuViewWithAnimate() {
        if (this.menuView != null) {
            final PathInterpolator pathInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
            BottomNavigationMenuView bottomNavigationMenuView = this.menuView;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(bottomNavigationMenuView, "y", (float) bottomNavigationMenuView.getHeight());
            ofFloat.setDuration(400L);
            ofFloat.setInterpolator(pathInterpolator);
            ofFloat.start();
            ofFloat.addListener(new AnimatorListenerAdapter() {
                /* class com.google.android.material.bottomnavigation.BottomNavigationPresenter.AnonymousClass3 */

                public void onAnimationEnd(Animator animator) {
                    BottomNavigationPresenter.this.menuView.buildMenuView();
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(BottomNavigationPresenter.this.menuView, "y", 0.0f);
                    ofFloat.setDuration(400L);
                    ofFloat.setInterpolator(pathInterpolator);
                    ofFloat.start();
                    super.onAnimationEnd(animator);
                }
            });
        }
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public void setId(int i) {
        this.id = i;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public int getId() {
        return this.id;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.selectedItemId = this.menuView.getSelectedItemId();
        savedState.badgeSavedStates = BadgeUtils.createParcelableBadgeStates(this.menuView.getBadgeDrawables());
        return savedState;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.menuView.tryRestoreSelectedItemId(savedState.selectedItemId);
            this.menuView.setBadgeDrawables(BadgeUtils.createBadgeDrawablesFromSavedStates(this.menuView.getContext(), savedState.badgeSavedStates));
        }
    }

    public void setUpdateSuspended(boolean z) {
        this.updateSuspended = z;
    }

    /* access modifiers changed from: package-private */
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class com.google.android.material.bottomnavigation.BottomNavigationPresenter.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        ParcelableSparseArray badgeSavedStates;
        int selectedItemId;

        public int describeContents() {
            return 0;
        }

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.selectedItemId = parcel.readInt();
            this.badgeSavedStates = (ParcelableSparseArray) parcel.readParcelable(getClass().getClassLoader());
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.selectedItemId);
            parcel.writeParcelable(this.badgeSavedStates, 0);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean showOverflowMenu(MenuBuilder menuBuilder) {
        if (isOverflowMenuShowing() || menuBuilder == null || this.menuView == null || this.mPostedOpenRunnable != null || menuBuilder.getNonActionItems().isEmpty()) {
            return false;
        }
        OverflowPopup overflowPopup = new OverflowPopup(this.mContext, menuBuilder, this.menuView.mOverflowButton, true);
        this.mOverflowPopup = overflowPopup;
        this.mPostedOpenRunnable = new OpenOverflowRunnable(overflowPopup);
        this.menuView.post(this.mPostedOpenRunnable);
        super.onSubMenuSelected(null);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean isOverflowMenuShowing() {
        OverflowPopup overflowPopup = this.mOverflowPopup;
        return overflowPopup != null && overflowPopup.isShowing();
    }

    /* access modifiers changed from: private */
    public class OverflowPopup extends MenuPopupHelper {
        private OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean z) {
            super(context, menuBuilder, view, z, com.google.android.material.R.attr.actionOverflowMenuStyle);
            setGravity(GravityCompat.END);
            setPresenterCallback(BottomNavigationPresenter.this.mPopupPresenterCallback);
            setAnchorView(view);
        }

        /* access modifiers changed from: protected */
        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public void onDismiss() {
            if (BottomNavigationPresenter.this.menu != null) {
                BottomNavigationPresenter.this.menu.close();
            }
            BottomNavigationPresenter.this.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    /* access modifiers changed from: package-private */
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

    /* access modifiers changed from: private */
    public class PopupPresenterCallback implements MenuPresenter.Callback {
        PopupPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            ((SubMenuBuilder) menuBuilder).getItem().getItemId();
            MenuPresenter.Callback callback = BottomNavigationPresenter.this.getCallback();
            if (callback == null || !callback.onOpenSubMenu(menuBuilder)) {
                return false;
            }
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            MenuPresenter.Callback callback = BottomNavigationPresenter.this.getCallback();
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }
    }

    /* access modifiers changed from: private */
    public class OpenOverflowRunnable implements Runnable {
        private OverflowPopup mPopup;

        private OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        public void run() {
            if (BottomNavigationPresenter.this.menu != null) {
                BottomNavigationPresenter.this.menu.changeMenuMode();
            }
            if (!(BottomNavigationPresenter.this.menuView == null || BottomNavigationPresenter.this.menuView.getWindowToken() == null || !this.mPopup.tryShow(0, 0))) {
                BottomNavigationPresenter.this.mOverflowPopup = this.mPopup;
            }
            BottomNavigationPresenter.this.mPostedOpenRunnable = null;
        }
    }
}
