package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslTextViewReflector;

public class SeslCheckedTextView extends TextView implements Checkable {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private int mBasePadding;
    private Drawable mCheckMarkDrawable;
    private int mCheckMarkGravity;
    private int mCheckMarkResource;
    private ColorStateList mCheckMarkTintList;
    private PorterDuff.Mode mCheckMarkTintMode;
    private int mCheckMarkWidth;
    private boolean mChecked;
    private int mDrawablePadding;
    private boolean mHasCheckMarkTint;
    private boolean mHasCheckMarkTintMode;
    private boolean mNeedRequestlayout;

    public SeslCheckedTextView(Context context) {
        this(context, null);
    }

    public SeslCheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkedTextViewStyle);
    }

    public SeslCheckedTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslCheckedTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCheckMarkTintList = null;
        this.mCheckMarkTintMode = null;
        this.mHasCheckMarkTint = false;
        this.mHasCheckMarkTintMode = false;
        this.mCheckMarkGravity = GravityCompat.START;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CheckedTextView, i, i2);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, R.styleable.CheckedTextView, attributeSet, obtainStyledAttributes, i, i2);
        }
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.CheckedTextView_android_checkMark);
        if (drawable != null) {
            setCheckMarkDrawable(drawable);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CheckedTextView_android_checkMarkTintMode)) {
            this.mCheckMarkTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.CheckedTextView_android_checkMarkTintMode, -1), this.mCheckMarkTintMode);
            this.mHasCheckMarkTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CheckedTextView_android_checkMarkTint)) {
            this.mCheckMarkTintList = obtainStyledAttributes.getColorStateList(R.styleable.CheckedTextView_android_checkMarkTint);
            this.mHasCheckMarkTint = true;
        }
        this.mCheckMarkGravity = obtainStyledAttributes.getInt(R.styleable.CheckedTextView_checkMarkGravity, GravityCompat.START);
        setChecked(obtainStyledAttributes.getBoolean(R.styleable.CheckedTextView_android_checked, false));
        this.mDrawablePadding = context.getResources().getDimensionPixelSize(R.dimen.sesl_checked_text_padding);
        obtainStyledAttributes.recycle();
        applyCheckMarkTint();
    }

    public void toggle() {
        setChecked(!this.mChecked);
    }

    @ViewDebug.ExportedProperty
    public boolean isChecked() {
        return this.mChecked;
    }

    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mChecked = z;
            refreshDrawableState();
            SeslViewReflector.notifyViewAccessibilityStateChangedIfNeeded(this, 0);
        }
    }

    public void setCheckMarkDrawable(int i) {
        if (i == 0 || i != this.mCheckMarkResource) {
            setCheckMarkDrawableInternal(i != 0 ? ContextCompat.getDrawable(getContext(), i) : null, i);
        }
    }

    public void setCheckMarkDrawable(Drawable drawable) {
        setCheckMarkDrawableInternal(drawable, 0);
    }

    private void setCheckMarkDrawableInternal(Drawable drawable, int i) {
        Drawable drawable2 = this.mCheckMarkDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mCheckMarkDrawable);
        }
        boolean z = true;
        this.mNeedRequestlayout = drawable != this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (getVisibility() != 0) {
                z = false;
            }
            drawable.setVisible(z, false);
            drawable.setState(CHECKED_STATE_SET);
            setMinHeight(drawable.getIntrinsicHeight());
            this.mCheckMarkWidth = drawable.getIntrinsicWidth();
            drawable.setState(getDrawableState());
        } else {
            this.mCheckMarkWidth = 0;
        }
        this.mCheckMarkDrawable = drawable;
        this.mCheckMarkResource = i;
        applyCheckMarkTint();
        SeslViewReflector.resolvePadding(this);
        setBasePadding(isCheckMarkAtStart());
    }

    public void setCheckMarkTintList(ColorStateList colorStateList) {
        this.mCheckMarkTintList = colorStateList;
        this.mHasCheckMarkTint = true;
        applyCheckMarkTint();
    }

    public ColorStateList getCheckMarkTintList() {
        return this.mCheckMarkTintList;
    }

    public void setCheckMarkTintMode(PorterDuff.Mode mode) {
        this.mCheckMarkTintMode = mode;
        this.mHasCheckMarkTintMode = true;
        applyCheckMarkTint();
    }

    public PorterDuff.Mode getCheckMarkTintMode() {
        return this.mCheckMarkTintMode;
    }

    private void applyCheckMarkTint() {
        if (this.mCheckMarkDrawable == null) {
            return;
        }
        if (this.mHasCheckMarkTint || this.mHasCheckMarkTintMode) {
            this.mCheckMarkDrawable = this.mCheckMarkDrawable.mutate();
            if (this.mHasCheckMarkTint) {
                this.mCheckMarkDrawable.setTintList(this.mCheckMarkTintList);
            }
            if (this.mHasCheckMarkTintMode) {
                this.mCheckMarkDrawable.setTintMode(this.mCheckMarkTintMode);
            }
            if (this.mCheckMarkDrawable.isStateful()) {
                this.mCheckMarkDrawable.setState(getDrawableState());
            }
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.setVisible(i == 0, false);
        }
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mCheckMarkDrawable || super.verifyDrawable(drawable);
    }

    public Drawable getCheckMarkDrawable() {
        return this.mCheckMarkDrawable;
    }

    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updatePadding();
    }

    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (verifyDrawable(drawable)) {
            Rect bounds = drawable.getBounds();
            if (ViewUtils.isLayoutRtl(this) && SeslTextViewReflector.getField_mSingleLine(this)) {
                invalidate(bounds.left, bounds.top, bounds.right, bounds.bottom);
            }
        }
    }

    private void updatePadding() {
        SeslViewReflector.resetPaddingToInitialValues(this);
        int i = this.mCheckMarkDrawable != null ? this.mCheckMarkWidth + this.mBasePadding + this.mDrawablePadding : this.mBasePadding;
        boolean z = true;
        if (isCheckMarkAtStart()) {
            boolean z2 = this.mNeedRequestlayout;
            if (SeslViewReflector.getField_mPaddingLeft(this) == i) {
                z = false;
            }
            this.mNeedRequestlayout = z2 | z;
            SeslViewReflector.setField_mPaddingLeft(this, i);
        } else {
            boolean z3 = this.mNeedRequestlayout;
            if (SeslViewReflector.getField_mPaddingRight(this) == i) {
                z = false;
            }
            this.mNeedRequestlayout = z3 | z;
            SeslViewReflector.setField_mPaddingRight(this, i);
        }
        if (this.mNeedRequestlayout) {
            requestLayout();
            this.mNeedRequestlayout = false;
        }
    }

    private void setBasePadding(boolean z) {
        if (z) {
            this.mBasePadding = getPaddingLeft();
        } else {
            this.mBasePadding = getPaddingRight();
        }
    }

    private boolean isCheckMarkAtStart() {
        return (Gravity.getAbsoluteGravity(this.mCheckMarkGravity, ViewCompat.getLayoutDirection(this)) & 7) == 3;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        int i2;
        super.onDraw(canvas);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            int gravity = getGravity() & 112;
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i3 = 0;
            if (gravity == 16) {
                i3 = (getHeight() - intrinsicHeight) / 2;
            } else if (gravity == 80) {
                i3 = getHeight() - intrinsicHeight;
            }
            boolean isCheckMarkAtStart = isCheckMarkAtStart();
            int width = getWidth();
            int i4 = intrinsicHeight + i3;
            if (isCheckMarkAtStart) {
                i2 = this.mBasePadding;
                i = this.mCheckMarkWidth + i2;
            } else {
                i = width - this.mBasePadding;
                i2 = i - this.mCheckMarkWidth;
            }
            int scrollX = getScrollX();
            if (ViewUtils.isLayoutRtl(this)) {
                drawable.setBounds(scrollX + i2, i3, scrollX + i, i4);
            } else {
                drawable.setBounds(i2, i3, i, i4);
            }
            drawable.draw(canvas);
            Drawable background = getBackground();
            if (background != null) {
                DrawableCompat.setHotspotBounds(background, i2 + scrollX, i3, scrollX + i, i4);
            }
        }
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, f, f2);
        }
    }

    public CharSequence getAccessibilityClassName() {
        return CheckedTextView.class.getName();
    }

    /* access modifiers changed from: package-private */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class androidx.appcompat.widget.SeslCheckedTextView.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean checked;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.checked = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.checked));
        }

        public String toString() {
            return "SeslCheckedTextView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " checked=" + this.checked + "}";
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = isChecked();
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
        requestLayout();
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setChecked(this.mChecked);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(this.mChecked);
    }
}
