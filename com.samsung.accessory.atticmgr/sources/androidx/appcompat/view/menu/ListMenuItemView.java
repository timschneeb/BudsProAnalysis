package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.SeslDropDownItemTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import java.text.NumberFormat;
import java.util.Locale;

public class ListMenuItemView extends LinearLayout implements MenuView.ItemView, AbsListView.SelectionBoundsAdjuster {
    private static final int BADGE_LIMIT_NUMBER = 99;
    private static final String TAG = "ListMenuItemView";
    private Drawable mBackground;
    private TextView mBadgeView;
    private CheckBox mCheckBox;
    private LinearLayout mContent;
    private SeslDropDownItemTextView mDropDownItemTextView;
    private boolean mForceShowIcon;
    private ImageView mGroupDivider;
    private boolean mHasListDivider;
    private ImageView mIconView;
    private LayoutInflater mInflater;
    private boolean mIsSubMenu;
    private MenuItemImpl mItemData;
    private int mMenuType;
    private NumberFormat mNumberFormat;
    private boolean mPreserveIconSpacing;
    private RadioButton mRadioButton;
    private TextView mShortcutView;
    private Drawable mSubMenuArrow;
    private ImageView mSubMenuArrowView;
    private int mTextAppearance;
    private Context mTextAppearanceContext;
    private TextView mTitleView;

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.mIsSubMenu = false;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R.styleable.MenuView, i, 0);
        this.mBackground = obtainStyledAttributes.getDrawable(R.styleable.MenuView_android_itemBackground);
        this.mTextAppearance = obtainStyledAttributes.getResourceId(R.styleable.MenuView_android_itemTextAppearance, -1);
        this.mPreserveIconSpacing = obtainStyledAttributes.getBoolean(R.styleable.MenuView_preserveIconSpacing, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = obtainStyledAttributes.getDrawable(R.styleable.MenuView_subMenuArrow);
        TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(null, new int[]{16843049}, R.attr.dropDownListViewStyle, 0);
        this.mHasListDivider = obtainStyledAttributes2.hasValue(0);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes2.recycle();
        this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        ViewCompat.setBackground(this, this.mBackground);
        this.mDropDownItemTextView = (SeslDropDownItemTextView) findViewById(R.id.sub_menu_title);
        this.mIsSubMenu = this.mDropDownItemTextView != null;
        if (!this.mIsSubMenu) {
            this.mTitleView = (TextView) findViewById(R.id.title);
            int i = this.mTextAppearance;
            if (i != -1) {
                this.mTitleView.setTextAppearance(this.mTextAppearanceContext, i);
            }
            TextView textView = this.mTitleView;
            if (textView != null) {
                textView.setSingleLine(false);
                this.mTitleView.setMaxLines(2);
            }
            this.mShortcutView = (TextView) findViewById(R.id.shortcut);
            this.mSubMenuArrowView = (ImageView) findViewById(R.id.submenuarrow);
            ImageView imageView = this.mSubMenuArrowView;
            if (imageView != null) {
                imageView.setImageDrawable(this.mSubMenuArrow);
            }
            this.mGroupDivider = (ImageView) findViewById(R.id.group_divider);
            this.mContent = (LinearLayout) findViewById(R.id.content);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl menuItemImpl, int i) {
        this.mItemData = menuItemImpl;
        this.mMenuType = i;
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        setTitle(menuItemImpl.getTitleForItemView(this));
        setCheckable(menuItemImpl.isCheckable());
        setShortcut(menuItemImpl.shouldShowShortcut(), menuItemImpl.getShortcut());
        setIcon(menuItemImpl.getIcon());
        setEnabled(menuItemImpl.isEnabled());
        setSubMenuArrowVisible(menuItemImpl.hasSubMenu());
        setContentDescription(menuItemImpl.getContentDescription());
        setBadgeText(menuItemImpl.getBadgeText());
    }

    private void addContentView(View view) {
        addContentView(view, -1);
    }

    private void addContentView(View view, int i) {
        LinearLayout linearLayout = this.mContent;
        if (linearLayout != null) {
            linearLayout.addView(view, i);
        } else {
            addView(view, i);
        }
    }

    public void setForceShowIcon(boolean z) {
        this.mForceShowIcon = z;
        this.mPreserveIconSpacing = z;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setTitle(CharSequence charSequence) {
        if (this.mIsSubMenu) {
            if (charSequence != null) {
                this.mDropDownItemTextView.setText(charSequence);
                if (this.mDropDownItemTextView.getVisibility() != 0) {
                    this.mDropDownItemTextView.setVisibility(0);
                }
            } else if (this.mDropDownItemTextView.getVisibility() != 8) {
                this.mDropDownItemTextView.setVisibility(8);
            }
        } else if (charSequence != null) {
            this.mTitleView.setText(charSequence);
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0);
            }
        } else if (this.mTitleView.getVisibility() != 8) {
            this.mTitleView.setVisibility(8);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        if (z || this.mRadioButton != null || this.mCheckBox != null) {
            if (!this.mIsSubMenu) {
                if (this.mItemData.isExclusiveCheckable()) {
                    if (this.mRadioButton == null) {
                        insertRadioButton();
                    }
                    compoundButton2 = this.mRadioButton;
                    compoundButton = this.mCheckBox;
                } else {
                    if (this.mCheckBox == null) {
                        insertCheckBox();
                    }
                    compoundButton2 = this.mCheckBox;
                    compoundButton = this.mRadioButton;
                }
                if (z) {
                    compoundButton2.setChecked(this.mItemData.isChecked());
                    int i = z ? 0 : 8;
                    if (compoundButton2.getVisibility() != i) {
                        compoundButton2.setVisibility(i);
                    }
                    if (compoundButton != null && compoundButton.getVisibility() != 8) {
                        compoundButton.setVisibility(8);
                        return;
                    }
                    return;
                }
                CheckBox checkBox = this.mCheckBox;
                if (checkBox != null) {
                    checkBox.setVisibility(8);
                }
                RadioButton radioButton = this.mRadioButton;
                if (radioButton != null) {
                    radioButton.setVisibility(8);
                }
            } else if (z) {
                this.mDropDownItemTextView.setChecked(this.mItemData.isChecked());
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
        CompoundButton compoundButton;
        if (this.mIsSubMenu) {
            this.mDropDownItemTextView.setChecked(z);
            return;
        }
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            compoundButton = this.mRadioButton;
        } else {
            if (this.mCheckBox == null) {
                insertCheckBox();
            }
            compoundButton = this.mCheckBox;
        }
        compoundButton.setChecked(z);
    }

    private void setSubMenuArrowVisible(boolean z) {
        ImageView imageView = this.mSubMenuArrowView;
        if (imageView != null && !this.mIsSubMenu) {
            imageView.setVisibility(z ? 0 : 8);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c) {
        if (!this.mIsSubMenu) {
            int i = (!z || !this.mItemData.shouldShowShortcut()) ? 8 : 0;
            if (i == 0) {
                this.mShortcutView.setText(this.mItemData.getShortcutLabel());
            }
            if (this.mShortcutView.getVisibility() != i) {
                this.mShortcutView.setVisibility(i);
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setIcon(Drawable drawable) {
        if (!this.mIsSubMenu) {
            boolean z = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
            if (!z && !this.mPreserveIconSpacing) {
                return;
            }
            if (this.mIconView != null || drawable != null || this.mPreserveIconSpacing) {
                if (this.mIconView == null) {
                    insertIconView();
                }
                if (drawable != null || this.mPreserveIconSpacing) {
                    ImageView imageView = this.mIconView;
                    if (!z) {
                        drawable = null;
                    }
                    imageView.setImageDrawable(drawable);
                    if (this.mIconView.getVisibility() != 0) {
                        this.mIconView.setVisibility(0);
                        return;
                    }
                    return;
                }
                this.mIconView.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mIconView != null && this.mPreserveIconSpacing && !this.mIsSubMenu) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height;
            }
        }
        super.onMeasure(i, i2);
    }

    private void insertIconView() {
        if (!this.mIsSubMenu) {
            this.mIconView = (ImageView) getInflater().inflate(R.layout.abc_list_menu_item_icon, (ViewGroup) this, false);
            addContentView(this.mIconView, 0);
        }
    }

    private void insertRadioButton() {
        this.mRadioButton = (RadioButton) getInflater().inflate(R.layout.sesl_list_menu_item_radio, (ViewGroup) this, false);
        addContentView(this.mRadioButton);
    }

    private void insertCheckBox() {
        this.mCheckBox = (CheckBox) getInflater().inflate(R.layout.sesl_list_menu_item_checkbox, (ViewGroup) this, false);
        addContentView(this.mCheckBox);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return this.mForceShowIcon;
    }

    private LayoutInflater getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getContext());
        }
        return this.mInflater;
    }

    public void setGroupDividerEnabled(boolean z) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null) {
            imageView.setVisibility((this.mHasListDivider || !z) ? 8 : 0);
        }
    }

    public void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null && imageView.getVisibility() == 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGroupDivider.getLayoutParams();
            rect.top += this.mGroupDivider.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
        }
    }

    private void setBadgeText(String str) {
        if (this.mBadgeView == null) {
            insertBadge();
        }
        if (isNumericValue(str)) {
            String format = this.mNumberFormat.format((long) Math.min(Integer.parseInt(str), 99));
            this.mBadgeView.setText(format);
            Resources resources = getResources();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBadgeView.getLayoutParams();
            layoutParams.width = (int) (resources.getDimension(R.dimen.sesl_badge_default_width) + (((float) format.length()) * resources.getDimension(R.dimen.sesl_badge_additional_width)));
            this.mBadgeView.setLayoutParams(layoutParams);
        } else {
            this.mBadgeView.setText(str);
        }
        this.mBadgeView.setVisibility(str != null ? 0 : 8);
    }

    private boolean isNumericValue(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private void insertBadge() {
        this.mBadgeView = (TextView) getInflater().inflate(R.layout.sesl_list_menu_item_badge, (ViewGroup) this, false);
        addView(this.mBadgeView);
    }
}
