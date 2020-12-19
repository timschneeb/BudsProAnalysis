package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import androidx.appcompat.R;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.util.SeslShowButtonShapesHelper;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.ForwardingListener;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

public class ActionMenuItemView extends AppCompatTextView implements MenuView.ItemView, View.OnClickListener, ActionMenuView.ActionMenuChildView, View.OnLongClickListener {
    private static final float MAX_FONT_SCALE = 1.2f;
    private static final int MAX_ICON_SIZE = 32;
    private static final String TAG = "ActionMenuItemView";
    private boolean mAllowTextWithIcon;
    private float mDefaultTextSize;
    private boolean mExpandedFormat;
    private ForwardingListener mForwardingListener;
    private Drawable mIcon;
    private boolean mIsChangedRelativePadding;
    private boolean mIsLastItem;
    MenuItemImpl mItemData;
    MenuBuilder.ItemInvoker mItemInvoker;
    private int mMaxIconSize;
    private int mMinWidth;
    PopupCallback mPopupCallback;
    private SeslShowButtonShapesHelper mSBSHelper;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    public static abstract class PopupCallback {
        public abstract ShowableListMenu getPopup();
    }

    public boolean onLongClick(View view) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c) {
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsChangedRelativePadding = false;
        this.mDefaultTextSize = 0.0f;
        this.mIsLastItem = false;
        Resources resources = context.getResources();
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionMenuItemView, i, 0);
        this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionMenuItemView_android_minWidth, 0);
        obtainStyledAttributes.recycle();
        this.mMaxIconSize = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        setOnLongClickListener(this);
        this.mSavedPaddingLeft = -1;
        setSaveEnabled(false);
        TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(null, R.styleable.AppCompatTheme, 0, 0);
        int resourceId = obtainStyledAttributes2.getResourceId(R.styleable.AppCompatTheme_actionMenuTextAppearance, 0);
        obtainStyledAttributes2.recycle();
        TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(resourceId, R.styleable.TextAppearance);
        TypedValue peekValue = obtainStyledAttributes3.peekValue(R.styleable.TextAppearance_android_textSize);
        obtainStyledAttributes3.recycle();
        if (peekValue != null) {
            this.mDefaultTextSize = TypedValue.complexToFloat(peekValue.data);
        }
        if (Build.VERSION.SDK_INT > 27) {
            seslSetButtonShapeEnabled(true);
        } else {
            this.mSBSHelper = new SeslShowButtonShapesHelper(this, ResourcesCompat.getDrawable(getResources(), R.drawable.sesl_action_text_button_show_button_shapes_background, null), getBackground());
        }
    }

    public void setBackground(Drawable drawable) {
        super.setBackground(drawable);
        SeslShowButtonShapesHelper seslShowButtonShapesHelper = this.mSBSHelper;
        if (seslShowButtonShapesHelper != null) {
            seslShowButtonShapesHelper.setBackgroundOff(drawable);
        }
    }

    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        this.mSavedPaddingLeft = i;
        this.mIsChangedRelativePadding = true;
        super.setPaddingRelative(i, i2, i3, i4);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        updateTextButtonVisibility();
    }

    private boolean shouldAllowTextWithIcon() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return i >= 480 || (i >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.mSavedPaddingLeft = i;
        super.setPadding(i, i2, i3, i4);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl menuItemImpl, int i) {
        this.mItemData = menuItemImpl;
        setIcon(menuItemImpl.getIcon());
        setTitle(menuItemImpl.getTitleForItemView(this));
        setId(menuItemImpl.getItemId());
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        setEnabled(menuItemImpl.isEnabled());
        if (menuItemImpl.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ForwardingListener forwardingListener;
        if (!this.mItemData.hasSubMenu() || (forwardingListener = this.mForwardingListener) == null || !forwardingListener.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void onClick(View view) {
        MenuBuilder.ItemInvoker itemInvoker = this.mItemInvoker;
        if (itemInvoker != null) {
            itemInvoker.invokeItem(this.mItemData);
        }
    }

    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    public void setPopupCallback(PopupCallback popupCallback) {
        this.mPopupCallback = popupCallback;
    }

    public void setExpandedFormat(boolean z) {
        if (this.mExpandedFormat != z) {
            this.mExpandedFormat = z;
            MenuItemImpl menuItemImpl = this.mItemData;
            if (menuItemImpl != null) {
                menuItemImpl.actionFormatChanged();
            }
        }
    }

    private void updateTextButtonVisibility() {
        CharSequence charSequence;
        CharSequence charSequence2;
        boolean z = (!TextUtils.isEmpty(this.mTitle)) & (this.mIcon == null || (this.mItemData.showsTextAsAction() && (this.mAllowTextWithIcon || this.mExpandedFormat)));
        CharSequence charSequence3 = null;
        setText(z ? this.mTitle : null);
        if (z) {
            setBackgroundResource(SeslMisc.isLightTheme(getContext()) ? R.drawable.sesl_action_bar_item_text_background_light : R.drawable.sesl_action_bar_item_text_background_dark);
        }
        CharSequence contentDescription = this.mItemData.getContentDescription();
        if (TextUtils.isEmpty(contentDescription)) {
            if (z) {
                charSequence2 = null;
            } else {
                charSequence2 = this.mItemData.getTitle();
            }
            setContentDescription(charSequence2);
        } else {
            setContentDescription(contentDescription);
        }
        CharSequence tooltipText = this.mItemData.getTooltipText();
        if (TextUtils.isEmpty(tooltipText)) {
            if (z) {
                charSequence = null;
            } else {
                charSequence = this.mItemData.getTitle();
            }
            TooltipCompat.setTooltipText(this, charSequence);
        } else {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
        if (this.mDefaultTextSize > 0.0f) {
            setTextSize(1, this.mDefaultTextSize * Math.min(getResources().getConfiguration().fontScale, (float) MAX_FONT_SCALE));
        }
        if (z) {
            charSequence3 = this.mTitle;
        }
        setText(charSequence3);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i = this.mMaxIconSize;
            if (intrinsicWidth > i) {
                intrinsicHeight = (int) (((float) intrinsicHeight) * (((float) i) / ((float) intrinsicWidth)));
                intrinsicWidth = i;
            }
            int i2 = this.mMaxIconSize;
            if (intrinsicHeight > i2) {
                intrinsicWidth = (int) (((float) intrinsicWidth) * (((float) i2) / ((float) intrinsicHeight)));
                intrinsicHeight = i2;
            }
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        }
        setCompoundDrawables(drawable, null, null, null);
        if (!hasText() || ViewCompat.getLayoutDirection(this) != 1) {
            setCompoundDrawables(drawable, null, null, null);
        } else {
            setCompoundDrawables(null, null, drawable, null);
        }
        updateTextButtonVisibility();
    }

    public void setIsLastItem(boolean z) {
        this.mIsLastItem = z;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence contentDescription = getContentDescription();
        if (!TextUtils.isEmpty(contentDescription)) {
            accessibilityEvent.getText().add(contentDescription);
        }
    }

    /* access modifiers changed from: protected */
    public boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (!this.mIsChangedRelativePadding) {
            return frame;
        }
        Drawable background = getBackground();
        if (this.mIcon != null && background != null) {
            int width = getWidth();
            int height = getHeight();
            int paddingLeft = (getPaddingLeft() - getPaddingRight()) / 2;
            DrawableCompat.setHotspotBounds(background, paddingLeft, 0, width + paddingLeft, height);
        } else if (background != null) {
            DrawableCompat.setHotspotBounds(background, 0, 0, getWidth(), getHeight());
        }
        return frame;
    }

    public void onHoverChanged(boolean z) {
        TooltipCompat.seslSetTooltipForceActionBarPosX(true);
        TooltipCompat.seslSetTooltipForceBelow(true);
        super.onHoverChanged(z);
    }

    public boolean performLongClick() {
        if (this.mIcon == null) {
            TooltipCompat.setTooltipNull(true);
            return true;
        }
        TooltipCompat.seslSetTooltipForceActionBarPosX(true);
        TooltipCompat.seslSetTooltipForceBelow(true);
        return super.performLongClick();
    }

    public boolean hasText() {
        return !TextUtils.isEmpty(getText());
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        setContentDescription(this.mTitle);
        updateTextButtonVisibility();
    }

    @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
    public boolean needsDividerBefore() {
        return hasText() && this.mItemData.getIcon() == null;
    }

    @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
    public boolean needsDividerAfter() {
        return hasText();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatTextView
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        boolean hasText = hasText();
        if (hasText && (i4 = this.mSavedPaddingLeft) >= 0) {
            super.setPadding(i4, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        if (this.mSBSHelper != null) {
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            if (hasText()) {
                i3 = R.drawable.sesl_action_text_button_show_button_shapes_background;
            } else if (this.mIsLastItem) {
                i3 = R.drawable.sesl_more_button_show_button_shapes_background;
            } else {
                i3 = R.drawable.sesl_action_icon_button_show_button_shapes_background;
            }
            this.mSBSHelper.setBackgroundOn(ContextCompat.getDrawable(getContext(), i3));
            this.mSBSHelper.updateButtonBackground();
            setPadding(paddingLeft, getPaddingTop(), paddingRight, getPaddingBottom());
        }
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int measuredWidth = getMeasuredWidth();
        int min = mode == Integer.MIN_VALUE ? Math.min(size, this.mMinWidth) : this.mMinWidth;
        if (mode != 1073741824 && this.mMinWidth > 0 && measuredWidth < min) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(min, 1073741824), i2);
        }
        if (!hasText && this.mIcon != null) {
            int measuredWidth2 = getMeasuredWidth();
            int width = this.mIcon.getBounds().width();
            if (!this.mIsChangedRelativePadding) {
                super.setPadding((measuredWidth2 - width) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            }
        }
    }

    private class ActionMenuItemForwardingListener extends ForwardingListener {
        public ActionMenuItemForwardingListener() {
            super(ActionMenuItemView.this);
        }

        @Override // androidx.appcompat.widget.ForwardingListener
        public ShowableListMenu getPopup() {
            if (ActionMenuItemView.this.mPopupCallback != null) {
                return ActionMenuItemView.this.mPopupCallback.getPopup();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        @Override // androidx.appcompat.widget.ForwardingListener
        public boolean onForwardingStarted() {
            ShowableListMenu popup;
            if (ActionMenuItemView.this.mItemInvoker == null || !ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData) || (popup = getPopup()) == null || !popup.isShowing()) {
                return false;
            }
            return true;
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }
}
