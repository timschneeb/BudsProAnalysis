package androidx.picker.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeProvider;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.picker.util.SeslAnimationListener;
import androidx.picker.widget.SeslSpinningDatePicker;
import androidx.reflect.view.SeslViewReflector;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

/* access modifiers changed from: package-private */
public class SeslSpinningDatePickerSpinner extends LinearLayout {
    private static final DateFormatter mDateFormatter = new DateFormatter();
    private DatePickerDelegate mDelegate;
    private boolean mIsLeapMonth;
    private boolean mIsLunar;

    /* access modifiers changed from: package-private */
    public interface DatePickerDelegate {
        void computeScroll();

        int computeVerticalScrollExtent();

        int computeVerticalScrollOffset();

        int computeVerticalScrollRange();

        Calendar convertLunarToSolar(Calendar calendar);

        Calendar convertSolarToLunar(Calendar calendar, SeslSpinningDatePicker.LunarDate lunarDate);

        boolean dispatchHoverEvent(MotionEvent motionEvent);

        boolean dispatchKeyEvent(KeyEvent keyEvent);

        boolean dispatchKeyEventPreIme(KeyEvent keyEvent);

        boolean dispatchTouchEvent(MotionEvent motionEvent);

        void dispatchTrackballEvent(MotionEvent motionEvent);

        AccessibilityNodeProvider getAccessibilityNodeProvider();

        int getMaxHeight();

        Calendar getMaxValue();

        int getMaxWidth();

        int getMinHeight();

        Calendar getMinValue();

        int getMinWidth();

        OnSpinnerDateClickListener getOnSpinnerDateClickListener();

        int getPaintFlags();

        Calendar getValue();

        boolean getWrapSelectorWheel();

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

        void setEnabled(boolean z);

        void setFormatter(Formatter formatter);

        void setLunar(boolean z, boolean z2);

        void setMaxValue(Calendar calendar);

        void setMinValue(Calendar calendar);

        void setOnLongPressUpdateInterval(long j);

        void setOnScrollListener(OnScrollListener onScrollListener);

        void setOnSpinnerDateClickListener(OnSpinnerDateClickListener onSpinnerDateClickListener);

        void setOnValueChangedListener(OnValueChangeListener onValueChangeListener);

        void setPaintFlags(int i);

        void setPickerContentDescription(String str);

        void setSkipValuesOnLongPressEnabled(boolean z);

        void setSubTextSize(float f);

        void setTextSize(float f);

        void setTextTypeface(Typeface typeface);

        void setValue(Calendar calendar);

        void setWrapSelectorWheel(boolean z);

        void startAnimation(int i, SeslAnimationListener seslAnimationListener);
    }

    interface Formatter {
        String format(Calendar calendar);

        Calendar parse(String str);
    }

    interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ScrollState {
        }

        void onScrollStateChange(SeslSpinningDatePickerSpinner seslSpinningDatePickerSpinner, int i);
    }

    interface OnSpinnerDateClickListener {
        void onSpinnerDateClicked(Calendar calendar);
    }

    interface OnValueChangeListener {
        void onValueChange(SeslSpinningDatePickerSpinner seslSpinningDatePickerSpinner, Calendar calendar, Calendar calendar2, boolean z);
    }

    static class DateFormatter implements Formatter {
        final Object[] mArgs = new Object[1];
        Locale mCurrentLocale;
        SimpleDateFormat mFmt;

        DateFormatter() {
            init(Locale.getDefault());
        }

        private void init(Locale locale) {
            this.mFmt = createFormatter(locale);
            this.mCurrentLocale = locale;
        }

        @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.Formatter
        public String format(Calendar calendar) {
            Locale locale = Locale.getDefault();
            if (!this.mCurrentLocale.equals(locale)) {
                init(locale);
            }
            this.mArgs[0] = calendar;
            return this.mFmt.format(calendar.getTime());
        }

        public String format(Calendar calendar, Context context) {
            this.mArgs[0] = calendar;
            return DateUtils.formatDateTime(context, calendar.getTimeInMillis(), 524314);
        }

        private SimpleDateFormat createFormatter(Locale locale) {
            if (isSimplifiedChinese(locale)) {
                return new SimpleDateFormat("EEEEE, MMM dd", locale);
            }
            if (isRTL(locale)) {
                return new SimpleDateFormat("EEEEE, MMM dd", locale);
            }
            return new SimpleDateFormat("EEE, MMM dd", locale);
        }

        @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.Formatter
        public Calendar parse(String str) {
            Calendar instance = Calendar.getInstance(Locale.getDefault());
            try {
                instance.setTime(this.mFmt.parse(str));
                return instance;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        private boolean isRTL(Locale locale) {
            byte directionality = Character.getDirectionality(locale.getDisplayName(locale).charAt(0));
            return directionality == 1 || directionality == 2;
        }

        private boolean isSimplifiedChinese(Locale locale) {
            return locale.getLanguage().equals(Locale.SIMPLIFIED_CHINESE.getLanguage()) && locale.getCountry().equals(Locale.SIMPLIFIED_CHINESE.getCountry());
        }
    }

    static Formatter getDateFormatter() {
        return mDateFormatter;
    }

    public SeslSpinningDatePickerSpinner(Context context) {
        this(context, null);
    }

    public SeslSpinningDatePickerSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslSpinningDatePickerSpinner(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslSpinningDatePickerSpinner(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDelegate = new SeslSpinningDatePickerSpinnerDelegate(this, context, attributeSet, i, i2);
    }

    /* access modifiers changed from: package-private */
    public void setPickerContentDescription(String str) {
        this.mDelegate.setPickerContentDescription(str);
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
        return this.mDelegate.dispatchHoverEvent(motionEvent);
    }

    public void setSkipValuesOnLongPressEnabled(boolean z) {
        this.mDelegate.setSkipValuesOnLongPressEnabled(z);
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

    public void setOnSpinnerDateClickListener(OnSpinnerDateClickListener onSpinnerDateClickListener) {
        this.mDelegate.setOnSpinnerDateClickListener(onSpinnerDateClickListener);
    }

    public OnSpinnerDateClickListener getOnSpinnerDateClickListener() {
        return this.mDelegate.getOnSpinnerDateClickListener();
    }

    /* access modifiers changed from: package-private */
    public void updateDate(int i, int i2, int i3) {
        Calendar calendar = (Calendar) getValue().clone();
        calendar.set(i, i2, i3);
        if (this.mIsLunar) {
            calendar = this.mDelegate.convertLunarToSolar(calendar);
        }
        setValue(calendar);
    }

    /* access modifiers changed from: package-private */
    public void setFormatter(Formatter formatter) {
        this.mDelegate.setFormatter(formatter);
    }

    /* access modifiers changed from: package-private */
    public void setValue(Calendar calendar) {
        this.mDelegate.setValue(calendar);
    }

    public boolean performClick() {
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

    /* access modifiers changed from: package-private */
    public boolean getWrapSelectorWheel() {
        return this.mDelegate.getWrapSelectorWheel();
    }

    /* access modifiers changed from: package-private */
    public void setWrapSelectorWheel(boolean z) {
        this.mDelegate.setWrapSelectorWheel(z);
    }

    /* access modifiers changed from: package-private */
    public void setOnLongPressUpdateInterval(long j) {
        this.mDelegate.setOnLongPressUpdateInterval(j);
    }

    /* access modifiers changed from: package-private */
    public Calendar getValue() {
        Calendar calendar = (Calendar) this.mDelegate.getValue().clone();
        return this.mIsLunar ? this.mDelegate.convertSolarToLunar(calendar, null) : calendar;
    }

    /* access modifiers changed from: package-private */
    public Calendar getMinValue() {
        return this.mDelegate.getMinValue();
    }

    /* access modifiers changed from: package-private */
    public void setMinValue(Calendar calendar) {
        this.mDelegate.setMinValue(calendar);
    }

    /* access modifiers changed from: package-private */
    public void setLunar(boolean z, boolean z2) {
        this.mIsLunar = z;
        this.mIsLeapMonth = z2;
        this.mDelegate.setLunar(z, z2);
    }

    /* access modifiers changed from: package-private */
    public Calendar getMaxValue() {
        return this.mDelegate.getMaxValue();
    }

    /* access modifiers changed from: package-private */
    public void setMaxValue(Calendar calendar) {
        this.mDelegate.setMaxValue(calendar);
    }

    /* access modifiers changed from: package-private */
    public void setTextSize(float f) {
        this.mDelegate.setTextSize(f);
    }

    /* access modifiers changed from: package-private */
    public void setSubTextSize(float f) {
        this.mDelegate.setSubTextSize(f);
    }

    /* access modifiers changed from: package-private */
    public void setTextTypeface(Typeface typeface) {
        this.mDelegate.setTextTypeface(typeface);
    }

    /* access modifiers changed from: package-private */
    public int getPaintFlags() {
        return this.mDelegate.getPaintFlags();
    }

    /* access modifiers changed from: package-private */
    public void setPaintFlags(int i) {
        this.mDelegate.setPaintFlags(i);
    }

    /* access modifiers changed from: package-private */
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
        this.mDelegate.onDraw(canvas);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        this.mDelegate.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        return this.mDelegate.getAccessibilityNodeProvider();
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

        public CustomEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        /* access modifiers changed from: package-private */
        public void setEditTextPosition(int i) {
            this.mAdjustEditTextPosition = i;
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            canvas.translate(0.0f, (float) this.mAdjustEditTextPosition);
            super.onDraw(canvas);
        }
    }

    static abstract class AbsDatePickerDelegate implements DatePickerDelegate {
        Context mContext;
        SeslSpinningDatePickerSpinner mDelegator;

        AbsDatePickerDelegate(SeslSpinningDatePickerSpinner seslSpinningDatePickerSpinner, Context context) {
            this.mDelegator = seslSpinningDatePickerSpinner;
            this.mContext = context;
        }
    }
}
