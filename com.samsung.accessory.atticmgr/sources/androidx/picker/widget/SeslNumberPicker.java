package androidx.picker.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.picker.util.SeslAnimationListener;
import androidx.reflect.icu.SeslLocaleDataReflector;
import androidx.reflect.view.SeslViewReflector;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class SeslNumberPicker extends LinearLayout {
    private static final TwoDigitFormatter sTwoDigitFormatter = new TwoDigitFormatter();
    private NumberPickerDelegate mDelegate;

    public interface Formatter {
        String format(int i);
    }

    /* access modifiers changed from: package-private */
    public interface NumberPickerDelegate {
        void applyWheelCustomInterval(boolean z);

        void computeScroll();

        int computeVerticalScrollExtent();

        int computeVerticalScrollOffset();

        int computeVerticalScrollRange();

        boolean dispatchHoverEvent(MotionEvent motionEvent);

        boolean dispatchKeyEvent(KeyEvent keyEvent);

        boolean dispatchKeyEventPreIme(KeyEvent keyEvent);

        boolean dispatchTouchEvent(MotionEvent motionEvent);

        void dispatchTrackballEvent(MotionEvent motionEvent);

        AccessibilityNodeProvider getAccessibilityNodeProvider();

        String[] getDisplayedValues();

        EditText getEditText();

        int getMaxHeight();

        int getMaxValue();

        int getMaxWidth();

        int getMinHeight();

        int getMinValue();

        int getMinWidth();

        int getPaintFlags();

        int getValue();

        boolean getWrapSelectorWheel();

        boolean isChangedDefaultInterval();

        boolean isEditTextMode();

        boolean isEditTextModeEnabled();

        boolean isEditTextModeNotAmPm();

        void onAttachedToWindow();

        void onConfigurationChanged(Configuration configuration);

        void onDetachedFromWindow();

        void onDraw(Canvas canvas);

        void onFocusChanged(boolean z, int i, Rect rect);

        boolean onGenericMotionEvent(MotionEvent motionEvent);

        void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        boolean onInterceptTouchEvent(MotionEvent motionEvent);

        void onLayout(boolean z, int i, int i2, int i3, int i4);

        void onMeasure(int i, int i2);

        boolean onTouchEvent(MotionEvent motionEvent);

        void onWindowFocusChanged(boolean z);

        void onWindowVisibilityChanged(int i);

        void performClick();

        void performClick(boolean z);

        void performLongClick();

        void scrollBy(int i, int i2);

        void setAmPm();

        void setCustomIntervalValue(int i);

        void setDisplayedValues(String[] strArr);

        void setEditTextMode(boolean z);

        void setEditTextModeEnabled(boolean z);

        void setEnabled(boolean z);

        void setErrorToastMessage(String str);

        void setFormatter(Formatter formatter);

        void setImeOptions(int i);

        void setMaxInputLength(int i);

        void setMaxValue(int i);

        void setMinValue(int i);

        void setMonthInputMode();

        void setOnEditTextModeChangedListener(OnEditTextModeChangedListener onEditTextModeChangedListener);

        void setOnScrollListener(OnScrollListener onScrollListener);

        void setOnValueChangedListener(OnValueChangeListener onValueChangeListener);

        void setPaintFlags(int i);

        void setPickerContentDescription(String str);

        void setSubTextSize(float f);

        void setTextSize(float f);

        void setTextTypeface(Typeface typeface);

        void setValue(int i);

        void setWrapSelectorWheel(boolean z);

        void setYearDateTimeInputMode();

        void startAnimation(int i, SeslAnimationListener seslAnimationListener);
    }

    public interface OnEditTextModeChangedListener {
        void onEditTextModeChanged(SeslNumberPicker seslNumberPicker, boolean z);
    }

    interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ScrollState {
        }

        void onScrollStateChange(SeslNumberPicker seslNumberPicker, int i);
    }

    public interface OnValueChangeListener {
        void onValueChange(SeslNumberPicker seslNumberPicker, int i, int i2);
    }

    public void setOnLongPressUpdateInterval(long j) {
    }

    public void setSkipValuesOnLongPressEnabled(boolean z) {
    }

    /* access modifiers changed from: private */
    public static class TwoDigitFormatter implements Formatter {
        final Object[] mArgs = new Object[1];
        final StringBuilder mBuilder = new StringBuilder();
        java.util.Formatter mFmt;
        char mZeroDigit;

        TwoDigitFormatter() {
            init(Locale.getDefault());
        }

        private void init(Locale locale) {
            this.mFmt = createFormatter(locale);
            this.mZeroDigit = getZeroDigit(locale);
        }

        @Override // androidx.picker.widget.SeslNumberPicker.Formatter
        public String format(int i) {
            Locale locale = Locale.getDefault();
            if (this.mZeroDigit != getZeroDigit(locale)) {
                init(locale);
            }
            this.mArgs[0] = Integer.valueOf(i);
            synchronized (this.mBuilder) {
                this.mBuilder.delete(0, this.mBuilder.length());
                this.mFmt.format("%02d", this.mArgs);
            }
            return this.mFmt.toString();
        }

        private static char getZeroDigit(Locale locale) {
            Object obj = SeslLocaleDataReflector.get(locale);
            if (obj == null) {
                return '0';
            }
            return SeslLocaleDataReflector.getField_zeroDigit(obj);
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(this.mBuilder, locale);
        }
    }

    static Formatter getTwoDigitFormatter() {
        return sTwoDigitFormatter;
    }

    public SeslNumberPicker(Context context) {
        this(context, null);
    }

    public SeslNumberPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslNumberPicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslNumberPicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDelegate = new SeslNumberPickerSpinnerDelegate(this, context, attributeSet, i, i2);
    }

    public void setPickerContentDescription(String str) {
        this.mDelegate.setPickerContentDescription(str);
    }

    /* access modifiers changed from: package-private */
    public void setAmPm() {
        this.mDelegate.setAmPm();
    }

    public void setEditTextModeEnabled(boolean z) {
        this.mDelegate.setEditTextModeEnabled(z);
    }

    public boolean isEditTextModeEnabled() {
        return this.mDelegate.isEditTextModeEnabled();
    }

    public void setEditTextMode(boolean z) {
        this.mDelegate.setEditTextMode(z);
    }

    public boolean isEditTextMode() {
        return this.mDelegate.isEditTextMode();
    }

    public void setCustomIntervalValue(int i) {
        this.mDelegate.setCustomIntervalValue(i);
    }

    public void applyWheelCustomInterval(boolean z) {
        this.mDelegate.applyWheelCustomInterval(z);
    }

    public boolean isChangedDefaultInterval() {
        return this.mDelegate.isChangedDefaultInterval();
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i) {
        this.mDelegate.onWindowVisibilityChanged(i);
        super.onWindowVisibilityChanged(i);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mDelegate.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: package-private */
    public void superOnMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: package-private */
    public void setMeasuredDimensionWrapper(int i, int i2) {
        setMeasuredDimension(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.mDelegate.onMeasure(i, i2);
    }

    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if (this.mDelegate.dispatchKeyEventPreIme(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEventPreIme(keyEvent);
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mDelegate.onWindowFocusChanged(z);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mDelegate.onInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mDelegate.onTouchEvent(motionEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.mDelegate.dispatchTouchEvent(motionEvent);
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mDelegate.onGenericMotionEvent(motionEvent)) {
            return true;
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        this.mDelegate.onFocusChanged(z, i, rect);
        super.onFocusChanged(z, i, rect);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mDelegate.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        this.mDelegate.dispatchTrackballEvent(motionEvent);
        return super.dispatchTrackballEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.mDelegate.isEditTextModeNotAmPm()) {
            return super.dispatchHoverEvent(motionEvent);
        }
        return this.mDelegate.dispatchHoverEvent(motionEvent);
    }

    public void computeScroll() {
        this.mDelegate.computeScroll();
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mDelegate.setEnabled(z);
    }

    public void scrollBy(int i, int i2) {
        this.mDelegate.scrollBy(i, i2);
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollOffset() {
        return this.mDelegate.computeVerticalScrollOffset();
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollRange() {
        return this.mDelegate.computeVerticalScrollRange();
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollExtent() {
        return this.mDelegate.computeVerticalScrollExtent();
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mDelegate.setOnValueChangedListener(onValueChangeListener);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mDelegate.setOnScrollListener(onScrollListener);
    }

    public void setOnEditTextModeChangedListener(OnEditTextModeChangedListener onEditTextModeChangedListener) {
        this.mDelegate.setOnEditTextModeChangedListener(onEditTextModeChangedListener);
    }

    public void setFormatter(Formatter formatter) {
        this.mDelegate.setFormatter(formatter);
    }

    public void setValue(int i) {
        this.mDelegate.setValue(i);
    }

    public boolean performClick() {
        if (this.mDelegate.isEditTextModeNotAmPm()) {
            return super.performClick();
        }
        if (super.performClick()) {
            return true;
        }
        this.mDelegate.performClick();
        return true;
    }

    /* access modifiers changed from: package-private */
    public void performClick(boolean z) {
        this.mDelegate.performClick(z);
    }

    public boolean performLongClick() {
        if (super.performLongClick()) {
            return true;
        }
        this.mDelegate.performLongClick();
        return true;
    }

    public boolean getWrapSelectorWheel() {
        return this.mDelegate.getWrapSelectorWheel();
    }

    public void setWrapSelectorWheel(boolean z) {
        this.mDelegate.setWrapSelectorWheel(z);
    }

    public int getValue() {
        return this.mDelegate.getValue();
    }

    public int getMinValue() {
        return this.mDelegate.getMinValue();
    }

    public void setMinValue(int i) {
        this.mDelegate.setMinValue(i);
    }

    public int getMaxValue() {
        return this.mDelegate.getMaxValue();
    }

    public void setMaxValue(int i) {
        this.mDelegate.setMaxValue(i);
    }

    public String[] getDisplayedValues() {
        return this.mDelegate.getDisplayedValues();
    }

    public void setDisplayedValues(String[] strArr) {
        this.mDelegate.setDisplayedValues(strArr);
    }

    public void setErrorToastMessage(String str) {
        this.mDelegate.setErrorToastMessage(str);
    }

    public void setTextSize(float f) {
        this.mDelegate.setTextSize(f);
    }

    public void setSubTextSize(float f) {
        this.mDelegate.setSubTextSize(f);
    }

    public void setTextTypeface(Typeface typeface) {
        this.mDelegate.setTextTypeface(typeface);
    }

    public int getPaintFlags() {
        return this.mDelegate.getPaintFlags();
    }

    public void setPaintFlags(int i) {
        this.mDelegate.setPaintFlags(i);
    }

    public void startAnimation(int i, SeslAnimationListener seslAnimationListener) {
        this.mDelegate.startAnimation(i, seslAnimationListener);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDelegate.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mDelegate.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mDelegate.isEditTextModeNotAmPm()) {
            super.onDraw(canvas);
        } else {
            this.mDelegate.onDraw(canvas);
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        this.mDelegate.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (this.mDelegate.isEditTextModeNotAmPm()) {
            return super.getAccessibilityNodeProvider();
        }
        return this.mDelegate.getAccessibilityNodeProvider();
    }

    /* access modifiers changed from: package-private */
    public void setMaxInputLength(int i) {
        this.mDelegate.setMaxInputLength(i);
    }

    public EditText getEditText() {
        return this.mDelegate.getEditText();
    }

    public void setMonthInputMode() {
        this.mDelegate.setMonthInputMode();
    }

    public void setYearDateTimeInputMode() {
        this.mDelegate.setYearDateTimeInputMode();
    }

    /* access modifiers changed from: package-private */
    public int[] getEnableStateSet() {
        return ENABLED_STATE_SET;
    }

    /* access modifiers changed from: package-private */
    public boolean isVisibleToUserWrapper() {
        return SeslViewReflector.isVisibleToUser(this);
    }

    /* access modifiers changed from: package-private */
    public boolean isVisibleToUserWrapper(Rect rect) {
        return SeslViewReflector.isVisibleToUser(this, rect);
    }

    static class CustomEditText extends EditText {
        private int mAdjustEditTextPosition;
        private String mPickerContentDescription = "";

        public CustomEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public void setPickerContentDescription(String str) {
            this.mPickerContentDescription = str;
        }

        public void setEditTextPosition(int i) {
            this.mAdjustEditTextPosition = i;
        }

        public void onEditorAction(int i) {
            super.onEditorAction(i);
            if (i == 6) {
                clearFocus();
            }
        }

        public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            CharSequence charSequence;
            int size = accessibilityEvent.getText().size();
            super.onPopulateAccessibilityEvent(accessibilityEvent);
            int size2 = accessibilityEvent.getText().size();
            if (size2 > size) {
                accessibilityEvent.getText().remove(size2 - 1);
            }
            int eventType = accessibilityEvent.getEventType();
            if (eventType == 16 || eventType == 8192) {
                charSequence = getText();
            } else {
                charSequence = getTextForAccessibility();
            }
            if (!TextUtils.isEmpty(charSequence)) {
                accessibilityEvent.getText().add(charSequence);
            }
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setText(getTextForAccessibility());
        }

        private CharSequence getTextForAccessibility() {
            Editable text = getText();
            if (this.mPickerContentDescription.equals("")) {
                return text;
            }
            if (!TextUtils.isEmpty(text)) {
                return text.toString() + ", " + this.mPickerContentDescription;
            }
            return ", " + this.mPickerContentDescription;
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            canvas.translate(0.0f, (float) this.mAdjustEditTextPosition);
            super.onDraw(canvas);
        }
    }

    static abstract class AbsNumberPickerDelegate implements NumberPickerDelegate {
        protected Context mContext;
        SeslNumberPicker mDelegator;

        AbsNumberPickerDelegate(SeslNumberPicker seslNumberPicker, Context context) {
            this.mDelegator = seslNumberPicker;
            this.mContext = context;
        }
    }
}
