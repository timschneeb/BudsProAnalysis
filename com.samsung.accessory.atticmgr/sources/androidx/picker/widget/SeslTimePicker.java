package androidx.picker.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.core.math.MathUtils;
import androidx.picker.util.SeslAnimationListener;
import java.util.Locale;

public class SeslTimePicker extends FrameLayout {
    public static final int PICKER_AMPM = 2;
    public static final int PICKER_DIVIDER = 3;
    public static final int PICKER_HOUR = 0;
    public static final int PICKER_MINUTE = 1;
    private TimePickerDelegate mDelegate;

    public interface OnEditTextModeChangedListener {
        void onEditTextModeChanged(SeslTimePicker seslTimePicker, boolean z);
    }

    public interface OnTimeChangedListener {
        void onTimeChanged(SeslTimePicker seslTimePicker, int i, int i2);
    }

    /* access modifiers changed from: private */
    public interface TimePickerDelegate {
        boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        int getBaseline();

        int getDefaultHeight();

        int getDefaultWidth();

        EditText getEditText(int i);

        int getHour();

        int getMinute();

        SeslNumberPicker getNumberPicker(int i);

        boolean is24Hour();

        boolean isEditTextMode();

        boolean isEnabled();

        void onConfigurationChanged(Configuration configuration);

        void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo);

        void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        void onRestoreInstanceState(Parcelable parcelable);

        Parcelable onSaveInstanceState(Parcelable parcelable);

        void requestLayout();

        void set5MinuteInterval(boolean z);

        void setCurrentLocale(Locale locale);

        void setEditTextMode(boolean z);

        void setEnabled(boolean z);

        void setHour(int i);

        void setIs24Hour(boolean z);

        void setMinute(int i);

        void setNumberPickerTextSize(int i, float f);

        void setNumberPickerTextTypeface(int i, Typeface typeface);

        void setOnEditTextModeChangedListener(OnEditTextModeChangedListener onEditTextModeChangedListener);

        void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener);

        void showMarginLeft(boolean z);

        void startAnimation(int i, SeslAnimationListener seslAnimationListener);
    }

    public SeslTimePicker(Context context) {
        this(context, null);
    }

    public SeslTimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843933);
    }

    public SeslTimePicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslTimePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDelegate = new SeslTimePickerSpinnerDelegate(this, context, attributeSet, i, i2);
    }

    public void setOnEditTextModeChangedListener(OnEditTextModeChangedListener onEditTextModeChangedListener) {
        this.mDelegate.setOnEditTextModeChangedListener(onEditTextModeChangedListener);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            i = View.MeasureSpec.makeMeasureSpec(this.mDelegate.getDefaultWidth(), 1073741824);
        }
        if (mode2 == Integer.MIN_VALUE) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.mDelegate.getDefaultHeight(), 1073741824);
        }
        super.onMeasure(i, i2);
    }

    public void setEditTextMode(boolean z) {
        this.mDelegate.setEditTextMode(z);
    }

    public boolean isEditTextMode() {
        return this.mDelegate.isEditTextMode();
    }

    public void setHour(int i) {
        this.mDelegate.setHour(MathUtils.constrain(i, 0, 23));
    }

    public int getHour() {
        return this.mDelegate.getHour();
    }

    public void setMinute(int i) {
        this.mDelegate.setMinute(MathUtils.constrain(i, 0, 59));
    }

    public int getMinute() {
        return this.mDelegate.getMinute();
    }

    public void setIs24HourView(Boolean bool) {
        if (bool != null) {
            this.mDelegate.setIs24Hour(bool.booleanValue());
        }
    }

    public void showMarginLeft(Boolean bool) {
        this.mDelegate.showMarginLeft(bool.booleanValue());
    }

    public boolean is24HourView() {
        return this.mDelegate.is24Hour();
    }

    public void set5MinuteInterval() {
        set5MinuteInterval(true);
    }

    public void set5MinuteInterval(boolean z) {
        this.mDelegate.set5MinuteInterval(z);
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.mDelegate.setOnTimeChangedListener(onTimeChangedListener);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mDelegate.setEnabled(z);
    }

    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    public int getBaseline() {
        return this.mDelegate.getBaseline();
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View, android.view.ViewGroup
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        return this.mDelegate.onSaveInstanceState(super.onSaveInstanceState());
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        this.mDelegate.onRestoreInstanceState(baseSavedState);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.mDelegate.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        this.mDelegate.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        this.mDelegate.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        this.mDelegate.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    public void setLocale(Locale locale) {
        this.mDelegate.setCurrentLocale(locale);
    }

    public void startAnimation(int i, SeslAnimationListener seslAnimationListener) {
        this.mDelegate.startAnimation(i, seslAnimationListener);
    }

    public EditText getEditText(int i) {
        return this.mDelegate.getEditText(i);
    }

    public SeslNumberPicker getNumberPicker(int i) {
        return this.mDelegate.getNumberPicker(i);
    }

    public void setNumberPickerTextSize(int i, float f) {
        this.mDelegate.setNumberPickerTextSize(i, f);
    }

    public void setNumberPickerTextTypeface(int i, Typeface typeface) {
        this.mDelegate.setNumberPickerTextTypeface(i, typeface);
    }

    public void requestLayout() {
        super.requestLayout();
        TimePickerDelegate timePickerDelegate = this.mDelegate;
        if (timePickerDelegate != null) {
            timePickerDelegate.requestLayout();
        }
    }

    static abstract class AbsTimePickerDelegate implements TimePickerDelegate {
        protected Context mContext;
        Locale mCurrentLocale;
        SeslTimePicker mDelegator;
        OnEditTextModeChangedListener mOnEditTextModeChangedListener;
        OnTimeChangedListener mOnTimeChangedListener;

        AbsTimePickerDelegate(SeslTimePicker seslTimePicker, Context context) {
            this.mDelegator = seslTimePicker;
            this.mContext = context;
            setCurrentLocale(Locale.getDefault());
        }

        @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
        public void setCurrentLocale(Locale locale) {
            if (!locale.equals(this.mCurrentLocale)) {
                this.mCurrentLocale = locale;
            }
        }
    }
}
