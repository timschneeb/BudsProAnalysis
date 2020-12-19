package androidx.picker.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.picker.R;
import androidx.picker.widget.SeslDatePicker;
import androidx.picker.widget.SeslNumberPicker;
import androidx.reflect.lunarcalendar.SeslFeatureReflector;
import androidx.reflect.lunarcalendar.SeslSolarLunarTablesReflector;
import dalvik.system.PathClassLoader;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/* access modifiers changed from: package-private */
public class SeslDatePickerSpinnerLayout extends LinearLayout {
    private static final int FORMAT_DDMMYYYY = 1;
    private static final int FORMAT_MMDDYYYY = 0;
    private static final int FORMAT_YYYYDDMM = 3;
    private static final int FORMAT_YYYYMMDD = 2;
    private static final int HUNGARIAN_MONTH_TEXT_SIZE_DIFF = 4;
    private static final int PICKER_DAY = 0;
    private static final int PICKER_MONTH = 1;
    private static final int PICKER_YEAR = 2;
    private static final boolean SESL_DEBUG = false;
    private static final String TAG = SeslDatePickerSpinnerLayout.class.getSimpleName();
    private Context mContext;
    private Calendar mCurrentDate;
    private Locale mCurrentLocale;
    private SeslDatePicker mDatePicker;
    private final SeslNumberPicker mDaySpinner;
    private final EditText mDaySpinnerInput;
    private TextView.OnEditorActionListener mEditorActionListener;
    private boolean mIsEditTextMode;
    private boolean mIsLeapMonth;
    private boolean mIsLunar;
    private int mLunarCurrentDay;
    private int mLunarCurrentMonth;
    private int mLunarCurrentYear;
    private int mLunarTempDay;
    private int mLunarTempMonth;
    private int mLunarTempYear;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private SeslNumberPicker.OnEditTextModeChangedListener mModeChangeListener;
    private final SeslNumberPicker mMonthSpinner;
    private final EditText mMonthSpinnerInput;
    private int mNumberOfMonths;
    private SeslDatePicker.OnEditTextModeChangedListener mOnEditTextModeChangedListener;
    private OnSpinnerDateChangedListener mOnSpinnerDateChangedListener;
    PathClassLoader mPathClassLoader;
    private EditText[] mPickerTexts;
    private final View mPrimaryEmptyView;
    private final View mSecondaryEmptyView;
    private String[] mShortMonths;
    private Object mSolarLunarTables;
    private final LinearLayout mSpinners;
    private Calendar mTempDate;
    private Toast mToast;
    private String mToastText;
    private final SeslNumberPicker mYearSpinner;
    private final EditText mYearSpinnerInput;

    public interface OnSpinnerDateChangedListener {
        void onDateChanged(SeslDatePickerSpinnerLayout seslDatePickerSpinnerLayout, int i, int i2, int i3);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void seslLog(String str) {
    }

    public SeslDatePickerSpinnerLayout(Context context) {
        this(context, null);
    }

    public SeslDatePickerSpinnerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public SeslDatePickerSpinnerLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslDatePickerSpinnerLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsLunar = false;
        this.mIsLeapMonth = false;
        this.mPathClassLoader = null;
        this.mModeChangeListener = new SeslNumberPicker.OnEditTextModeChangedListener() {
            /* class androidx.picker.widget.SeslDatePickerSpinnerLayout.AnonymousClass1 */

            @Override // androidx.picker.widget.SeslNumberPicker.OnEditTextModeChangedListener
            public void onEditTextModeChanged(SeslNumberPicker seslNumberPicker, boolean z) {
                SeslDatePickerSpinnerLayout.this.setEditTextMode(z);
                SeslDatePickerSpinnerLayout.this.updateModeState(z);
            }
        };
        this.mPickerTexts = new EditText[3];
        this.mEditorActionListener = new TextView.OnEditorActionListener() {
            /* class androidx.picker.widget.SeslDatePickerSpinnerLayout.AnonymousClass3 */

            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6) {
                    SeslDatePickerSpinnerLayout.this.updateInputState();
                    SeslDatePickerSpinnerLayout.this.setEditTextMode(false);
                }
                return false;
            }
        };
        this.mContext = context;
        LayoutInflater.from(this.mContext).inflate(R.layout.sesl_date_picker_spinner, (ViewGroup) this, true);
        this.mCurrentLocale = Locale.getDefault();
        setCurrentLocale(this.mCurrentLocale);
        AnonymousClass2 r8 = new SeslNumberPicker.OnValueChangeListener() {
            /* class androidx.picker.widget.SeslDatePickerSpinnerLayout.AnonymousClass2 */

            /* JADX WARNING: Removed duplicated region for block: B:42:0x0132  */
            /* JADX WARNING: Removed duplicated region for block: B:50:0x01a0  */
            @Override // androidx.picker.widget.SeslNumberPicker.OnValueChangeListener
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onValueChange(androidx.picker.widget.SeslNumberPicker r9, int r10, int r11) {
                /*
                // Method dump skipped, instructions count: 460
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.picker.widget.SeslDatePickerSpinnerLayout.AnonymousClass2.onValueChange(androidx.picker.widget.SeslNumberPicker, int, int):void");
            }
        };
        this.mSpinners = (LinearLayout) findViewById(R.id.sesl_date_picker_pickers);
        this.mPrimaryEmptyView = findViewById(R.id.sesl_date_picker_primary_empty);
        this.mSecondaryEmptyView = findViewById(R.id.sesl_date_picker_secondary_empty);
        this.mDaySpinner = (SeslNumberPicker) findViewById(R.id.sesl_date_picker_spinner_day);
        this.mDaySpinnerInput = (EditText) this.mDaySpinner.findViewById(R.id.numberpicker_input);
        this.mDaySpinner.setFormatter(SeslNumberPicker.getTwoDigitFormatter());
        this.mDaySpinner.setOnValueChangedListener(r8);
        this.mDaySpinner.setOnEditTextModeChangedListener(this.mModeChangeListener);
        this.mDaySpinner.setMaxInputLength(2);
        this.mDaySpinner.setYearDateTimeInputMode();
        this.mMonthSpinner = (SeslNumberPicker) findViewById(R.id.sesl_date_picker_spinner_month);
        this.mMonthSpinnerInput = (EditText) this.mMonthSpinner.findViewById(R.id.numberpicker_input);
        if (usingNumericMonths()) {
            this.mMonthSpinner.setMinValue(1);
            this.mMonthSpinner.setMaxValue(12);
            this.mMonthSpinner.setYearDateTimeInputMode();
            this.mMonthSpinner.setMaxInputLength(2);
        } else {
            this.mMonthSpinner.setMinValue(0);
            this.mMonthSpinner.setMaxValue(this.mNumberOfMonths - 1);
            this.mMonthSpinner.setFormatter(null);
            this.mMonthSpinner.setDisplayedValues(this.mShortMonths);
            this.mMonthSpinnerInput.setInputType(1);
            this.mMonthSpinner.setMonthInputMode();
        }
        this.mMonthSpinner.setOnValueChangedListener(r8);
        this.mMonthSpinner.setOnEditTextModeChangedListener(this.mModeChangeListener);
        this.mYearSpinner = (SeslNumberPicker) findViewById(R.id.sesl_date_picker_spinner_year);
        this.mYearSpinnerInput = (EditText) this.mYearSpinner.findViewById(R.id.numberpicker_input);
        this.mYearSpinner.setOnValueChangedListener(r8);
        this.mYearSpinner.setOnEditTextModeChangedListener(this.mModeChangeListener);
        this.mYearSpinner.setMaxInputLength(4);
        this.mYearSpinner.setYearDateTimeInputMode();
        Typeface create = Typeface.create("sec-roboto-light", 0);
        this.mDaySpinner.setTextTypeface(create);
        this.mMonthSpinner.setTextTypeface(create);
        this.mYearSpinner.setTextTypeface(create);
        Resources resources = context.getResources();
        int integer = resources.getInteger(R.integer.sesl_date_picker_spinner_number_text_size);
        int integer2 = resources.getInteger(R.integer.sesl_date_picker_spinner_number_text_size_small);
        this.mToastText = resources.getString(R.string.sesl_number_picker_invalid_value_entered);
        float f = (float) integer;
        this.mDaySpinner.setTextSize(f);
        this.mYearSpinner.setTextSize((float) integer2);
        String language = this.mCurrentLocale.getLanguage();
        if ("my".equals(language) || "ml".equals(language) || "bn".equals(language) || "ar".equals(language) || "fa".equals(language)) {
            integer2 = resources.getInteger(R.integer.sesl_date_picker_spinner_long_month_text_size);
        } else if ("ga".equals(language)) {
            integer2 = resources.getInteger(R.integer.sesl_date_picker_spinner_long_month_text_size) - 1;
        } else if ("hu".equals(language)) {
            integer2 -= 4;
        }
        if (usingNumericMonths()) {
            this.mMonthSpinner.setTextSize(f);
        } else {
            this.mMonthSpinner.setTextSize((float) integer2);
        }
        this.mDaySpinner.setPickerContentDescription(context.getResources().getString(R.string.sesl_date_picker_day));
        this.mMonthSpinner.setPickerContentDescription(context.getResources().getString(R.string.sesl_date_picker_month));
        this.mYearSpinner.setPickerContentDescription(context.getResources().getString(R.string.sesl_date_picker_year));
        this.mCurrentDate.setTimeInMillis(System.currentTimeMillis());
        init(this.mCurrentDate.get(1), this.mCurrentDate.get(2), this.mCurrentDate.get(5));
        reorderSpinners();
    }

    /* access modifiers changed from: package-private */
    public void init(int i, int i2, int i3) {
        setDate(i, i2, i3);
        updateSpinners(true, true, true, true);
    }

    /* access modifiers changed from: package-private */
    public void updateDate(int i, int i2, int i3) {
        if (isNewDate(i, i2, i3)) {
            setDate(i, i2, i3);
            updateSpinners(true, true, true, true);
        }
    }

    /* access modifiers changed from: package-private */
    public int getYear() {
        if (this.mIsLunar) {
            return this.mLunarCurrentYear;
        }
        return this.mCurrentDate.get(1);
    }

    /* access modifiers changed from: package-private */
    public int getMonth() {
        if (this.mIsLunar) {
            return this.mLunarCurrentMonth;
        }
        return this.mCurrentDate.get(2);
    }

    /* access modifiers changed from: package-private */
    public int getDayOfMonth() {
        if (this.mIsLunar) {
            return this.mLunarCurrentDay;
        }
        return this.mCurrentDate.get(5);
    }

    /* access modifiers changed from: package-private */
    public void setMinDate(long j) {
        this.mMinDate.setTimeInMillis(j);
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        }
        updateSpinners(true, true, true, true);
    }

    /* access modifiers changed from: package-private */
    public Calendar getMinDate() {
        return this.mMinDate;
    }

    /* access modifiers changed from: package-private */
    public void setMaxDate(long j) {
        this.mMaxDate.setTimeInMillis(j);
        if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
        updateSpinners(true, true, true, true);
    }

    /* access modifiers changed from: package-private */
    public Calendar getMaxDate() {
        return this.mMaxDate;
    }

    public void setEnabled(boolean z) {
        this.mDaySpinner.setEnabled(z);
        this.mMonthSpinner.setEnabled(z);
        this.mYearSpinner.setEnabled(z);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setCurrentLocale(configuration.locale);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.getText().add(DateUtils.formatDateTime(this.mContext, this.mCurrentDate.getTimeInMillis(), 20));
    }

    /* access modifiers changed from: protected */
    public void setCurrentLocale(Locale locale) {
        this.mTempDate = getCalendarForLocale(this.mTempDate, locale);
        this.mMinDate = getCalendarForLocale(this.mMinDate, locale);
        this.mMaxDate = getCalendarForLocale(this.mMaxDate, locale);
        this.mCurrentDate = getCalendarForLocale(this.mCurrentDate, locale);
        this.mNumberOfMonths = this.mTempDate.getActualMaximum(2) + 1;
        this.mShortMonths = new DateFormatSymbols().getShortMonths();
        int i = 0;
        while (true) {
            String[] strArr = this.mShortMonths;
            if (i >= strArr.length) {
                break;
            }
            strArr[i] = strArr[i].toUpperCase();
            i++;
        }
        if (usingNumericMonths()) {
            this.mShortMonths = new String[this.mNumberOfMonths];
            int i2 = 0;
            while (i2 < this.mNumberOfMonths) {
                int i3 = i2 + 1;
                this.mShortMonths[i2] = String.format("%d", Integer.valueOf(i3));
                i2 = i3;
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean usingNumericMonths() {
        return Character.isDigit(this.mShortMonths[0].charAt(0));
    }

    private Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        if (calendar == null) {
            return Calendar.getInstance(locale);
        }
        long timeInMillis = calendar.getTimeInMillis();
        Calendar instance = Calendar.getInstance(locale);
        instance.setTimeInMillis(timeInMillis);
        return instance;
    }

    private void reorderSpinners() {
        this.mSpinners.removeAllViews();
        char[] dateFormatOrder = DateFormat.getDateFormatOrder(this.mContext);
        int length = dateFormatOrder.length;
        for (int i = 0; i < length; i++) {
            char c = dateFormatOrder[i];
            if (c == 'M') {
                this.mSpinners.addView(this.mMonthSpinner);
                setImeOptions(this.mMonthSpinner, length, i);
            } else if (c == 'd') {
                this.mSpinners.addView(this.mDaySpinner);
                setImeOptions(this.mDaySpinner, length, i);
            } else if (c == 'y') {
                this.mSpinners.addView(this.mYearSpinner);
                setImeOptions(this.mYearSpinner, length, i);
            } else {
                throw new IllegalArgumentException(Arrays.toString(dateFormatOrder));
            }
            if (i == 0) {
                this.mSpinners.addView(this.mPrimaryEmptyView);
            } else if (i == 1) {
                this.mSpinners.addView(this.mSecondaryEmptyView);
            }
        }
        char c2 = dateFormatOrder[0];
        char c3 = dateFormatOrder[1];
        if (c2 == 'M') {
            setTextWatcher(0);
        } else if (c2 == 'd') {
            setTextWatcher(1);
        } else if (c2 == 'y') {
            if (c3 == 'd') {
                setTextWatcher(3);
            } else {
                setTextWatcher(2);
            }
        }
    }

    private boolean isNewDate(int i, int i2, int i3) {
        if (this.mCurrentDate.get(1) == i && this.mCurrentDate.get(2) == i2 && this.mCurrentDate.get(5) == i3) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setDate(int i, int i2, int i3) {
        this.mCurrentDate.set(i, i2, i3);
        if (this.mIsLunar) {
            this.mLunarCurrentYear = i;
            this.mLunarCurrentMonth = i2;
            this.mLunarCurrentDay = i3;
        }
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        } else if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateSpinners(boolean z, boolean z2, boolean z3, boolean z4) {
        EditText[] editTextArr;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        if (z2) {
            this.mYearSpinner.setMinValue(this.mMinDate.get(1));
            this.mYearSpinner.setMaxValue(this.mMaxDate.get(1));
            this.mYearSpinner.setWrapSelectorWheel(false);
        }
        if (z3) {
            int i6 = 11;
            if (this.mMaxDate.get(1) - this.mMinDate.get(1) == 0) {
                i4 = this.mMinDate.get(2);
                i5 = this.mMaxDate.get(2);
            } else {
                int i7 = this.mCurrentDate.get(1);
                if (this.mIsLunar) {
                    i7 = this.mLunarCurrentYear;
                }
                if (i7 == this.mMinDate.get(1)) {
                    i5 = 11;
                    i4 = this.mMinDate.get(2);
                } else {
                    if (i7 == this.mMaxDate.get(1)) {
                        i6 = this.mMaxDate.get(2);
                    }
                    i5 = i6;
                    i4 = 0;
                }
            }
            if (usingNumericMonths()) {
                i4++;
                i5++;
            }
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(i4);
            this.mMonthSpinner.setMaxValue(i5);
            if (!usingNumericMonths()) {
                this.mMonthSpinner.setDisplayedValues((String[]) Arrays.copyOfRange(this.mShortMonths, this.mMonthSpinner.getMinValue(), this.mMonthSpinner.getMaxValue() + 1));
            }
        }
        if (z4) {
            int i8 = this.mMaxDate.get(1) - this.mMinDate.get(1);
            int i9 = this.mMaxDate.get(2) - this.mMinDate.get(2);
            if (i8 == 0 && i9 == 0) {
                int i10 = this.mMinDate.get(5);
                i2 = this.mMaxDate.get(5);
                i = i10;
            } else {
                int i11 = this.mCurrentDate.get(1);
                int i12 = this.mCurrentDate.get(2);
                if (this.mIsLunar) {
                    i11 = this.mLunarCurrentYear;
                    i12 = this.mLunarCurrentMonth;
                }
                if (i11 == this.mMinDate.get(1) && i12 == this.mMinDate.get(2)) {
                    i = this.mMinDate.get(5);
                    i2 = this.mIsLunar ? getLunarMaxDayOfMonth(i11, i12, this.mIsLeapMonth) : this.mCurrentDate.getActualMaximum(5);
                } else {
                    if (i11 == this.mMaxDate.get(1) && i12 == this.mMaxDate.get(2)) {
                        i3 = this.mMaxDate.get(5);
                        if (this.mIsLunar) {
                            i2 = Math.min(i3, getLunarMaxDayOfMonth(i11, i12, this.mIsLeapMonth));
                            i = 1;
                        }
                    } else {
                        i3 = this.mCurrentDate.getActualMaximum(5);
                        if (this.mIsLunar) {
                            i2 = getLunarMaxDayOfMonth(i11, i12, this.mIsLeapMonth);
                            i = 1;
                        }
                    }
                    i2 = i3;
                    i = 1;
                }
            }
            this.mDaySpinner.setMinValue(i);
            this.mDaySpinner.setMaxValue(i2);
        }
        if (z) {
            this.mYearSpinner.setValue(this.mCurrentDate.get(1));
            int i13 = this.mCurrentDate.get(2);
            if (this.mIsLunar) {
                i13 = this.mLunarCurrentMonth;
            }
            if (usingNumericMonths()) {
                this.mMonthSpinner.setValue(i13 + 1);
            } else {
                this.mMonthSpinner.setValue(i13);
            }
            int i14 = this.mCurrentDate.get(5);
            if (this.mIsLunar) {
                i14 = this.mLunarCurrentDay;
            }
            this.mDaySpinner.setValue(i14);
            if (usingNumericMonths()) {
                this.mMonthSpinnerInput.setRawInputType(2);
            }
            if (this.mIsEditTextMode && (editTextArr = this.mPickerTexts) != null) {
                for (EditText editText : editTextArr) {
                    if (editText.hasFocus()) {
                        editText.setSelection(0, 0);
                        editText.selectAll();
                        return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyDateChanged() {
        sendAccessibilityEvent(4);
        OnSpinnerDateChangedListener onSpinnerDateChangedListener = this.mOnSpinnerDateChangedListener;
        if (onSpinnerDateChangedListener != null) {
            onSpinnerDateChangedListener.onDateChanged(this, getYear(), getMonth(), getDayOfMonth());
        }
    }

    private void setImeOptions(SeslNumberPicker seslNumberPicker, int i, int i2) {
        ((TextView) seslNumberPicker.findViewById(R.id.numberpicker_input)).setImeOptions(i2 < i + -1 ? 33554437 : 33554438);
    }

    /* access modifiers changed from: package-private */
    public void updateInputState() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        if (inputMethodManager.isActive(this.mYearSpinnerInput)) {
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            this.mYearSpinnerInput.clearFocus();
        } else if (inputMethodManager.isActive(this.mMonthSpinnerInput)) {
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            this.mMonthSpinnerInput.clearFocus();
        } else if (inputMethodManager.isActive(this.mDaySpinnerInput)) {
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            this.mDaySpinnerInput.clearFocus();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0085  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setTextWatcher(int r15) {
        /*
        // Method dump skipped, instructions count: 225
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.picker.widget.SeslDatePickerSpinnerLayout.setTextWatcher(int):void");
    }

    /* access modifiers changed from: private */
    public class SeslKeyListener implements View.OnKeyListener {
        public SeslKeyListener() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            SeslDatePickerSpinnerLayout.this.seslLog(keyEvent.toString());
            if (keyEvent.getAction() != 1) {
                return false;
            }
            if (i != 23) {
                if (i != 61) {
                    if (i != 66 && i != 160) {
                        return false;
                    }
                    if (SeslDatePickerSpinnerLayout.this.isEditTextMode()) {
                        EditText editText = (EditText) view;
                        if ((editText.getImeOptions() & 5) == 5) {
                            View findNextFocus = FocusFinder.getInstance().findNextFocus(SeslDatePickerSpinnerLayout.this.mDatePicker, view, 2);
                            if (findNextFocus == null) {
                                return true;
                            }
                            findNextFocus.requestFocus();
                        } else if ((editText.getImeOptions() & 6) == 6) {
                            SeslDatePickerSpinnerLayout.this.updateInputState();
                            SeslDatePickerSpinnerLayout.this.setEditTextMode(false);
                        }
                    }
                }
                return true;
            }
            if (SeslDatePickerSpinnerLayout.this.getResources().getConfiguration().keyboard == 3) {
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public class SeslTextWatcher implements TextWatcher {
        private final int INVALID_POSITION_ID;
        private final int LAST_POSITION_ID;
        private int mChangedLen;
        private int mCheck;
        private int mId;
        private boolean mIsMonth;
        private int mMaxLen;
        private int mNext;
        private String mPrevText;

        private SeslTextWatcher(int i, int i2, boolean z) {
            int i3 = -1;
            this.INVALID_POSITION_ID = -1;
            this.LAST_POSITION_ID = 2;
            this.mChangedLen = 0;
            this.mMaxLen = i;
            this.mId = i2;
            this.mIsMonth = z;
            this.mCheck = this.mId - 1;
            if (this.mCheck < 0) {
                this.mCheck = 2;
            }
            int i4 = this.mId;
            this.mNext = i4 + 1 <= 2 ? i4 + 1 : i3;
        }

        public void afterTextChanged(Editable editable) {
            SeslDatePickerSpinnerLayout seslDatePickerSpinnerLayout = SeslDatePickerSpinnerLayout.this;
            seslDatePickerSpinnerLayout.seslLog("[" + this.mId + "] " + "afterTextChanged: " + editable.toString());
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            SeslDatePickerSpinnerLayout seslDatePickerSpinnerLayout = SeslDatePickerSpinnerLayout.this;
            seslDatePickerSpinnerLayout.seslLog("[" + this.mId + "] " + "beforeTextChanged: " + ((Object) charSequence) + ", " + i + ", " + i2 + ", " + i3);
            this.mPrevText = charSequence.toString();
            this.mChangedLen = i3;
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            int i4;
            int i5;
            SeslDatePickerSpinnerLayout.this.seslLog("[" + this.mId + "] " + "onTextChanged: " + this.mPrevText + " -> " + ((Object) charSequence));
            int length = charSequence.length();
            String charSequence2 = charSequence.toString();
            String str = (String) SeslDatePickerSpinnerLayout.this.mPickerTexts[this.mId].getTag();
            if (str != null && ("onClick".equals(str) || "onLongClick".equals(str))) {
                SeslDatePickerSpinnerLayout.this.seslLog("[" + this.mId + "] " + "TAG exists: " + str);
            } else if (SeslDatePickerSpinnerLayout.this.mPickerTexts[this.mId].isFocused()) {
                String str2 = "";
                if (this.mIsMonth) {
                    if (SeslDatePickerSpinnerLayout.this.usingNumericMonths() && this.mChangedLen == 1) {
                        SeslDatePickerSpinnerLayout.this.seslLog("[" + this.mId + "] " + "Samsung Keypad Num Month");
                        int minValue = SeslDatePickerSpinnerLayout.this.mMonthSpinner.getMinValue();
                        int parseInt = Integer.parseInt(charSequence2);
                        if (length == this.mMaxLen) {
                            if (parseInt >= minValue) {
                                changeFocus();
                            } else if (Character.getNumericValue(charSequence2.charAt(0)) < 2) {
                                showInvalidValueEnteredToast(Character.toString(charSequence2.charAt(0)), 1);
                            } else {
                                showInvalidValueEnteredToast(str2, 0);
                            }
                        } else if (length <= 0) {
                        } else {
                            if (minValue >= 10 && "0".equals(charSequence2)) {
                                showInvalidValueEnteredToast(str2, 0);
                            } else if (!"1".equals(charSequence2) && !"0".equals(charSequence2)) {
                                if (parseInt < minValue) {
                                    showInvalidValueEnteredToast(str2, 0);
                                } else {
                                    changeFocus();
                                }
                            }
                        }
                    } else if (isNumericStr(this.mPrevText)) {
                    } else {
                        if (length >= this.mMaxLen) {
                            if (!isMeaLanguage()) {
                                changeFocus();
                            } else if (TextUtils.isEmpty(this.mPrevText) && isMonthStr(charSequence2)) {
                                changeFocus();
                            }
                        } else if ((isSwaLanguage() || isFarsiLanguage()) && length > 0 && !isNumericStr(charSequence2)) {
                            changeFocus();
                        }
                    }
                } else if (this.mChangedLen != 1) {
                } else {
                    if (this.mMaxLen < 3) {
                        int minValue2 = SeslDatePickerSpinnerLayout.this.mDaySpinner.getMinValue();
                        int parseInt2 = Integer.parseInt(charSequence2);
                        if (this.mPrevText.length() >= length || length != this.mMaxLen) {
                            if ((minValue2 < 10 || parseInt2 != 0) && ((minValue2 < 20 || !(parseInt2 == 0 || parseInt2 == 1)) && (minValue2 < 30 || !(parseInt2 == 0 || parseInt2 == 1 || parseInt2 == 2)))) {
                                if (parseInt2 > 3) {
                                    if (parseInt2 < minValue2) {
                                        showInvalidValueEnteredToast(str2, 0);
                                        return;
                                    }
                                    changeFocus();
                                }
                                if (SeslDatePickerSpinnerLayout.this.usingNumericMonths()) {
                                    i5 = SeslDatePickerSpinnerLayout.this.mMonthSpinner.getValue() - 1;
                                } else {
                                    i5 = SeslDatePickerSpinnerLayout.this.mMonthSpinner.getValue();
                                }
                                if (SeslDatePickerSpinnerLayout.this.mIsLunar || i5 != 1 || parseInt2 != 3) {
                                    return;
                                }
                                if (parseInt2 < minValue2) {
                                    showInvalidValueEnteredToast(str2, 0);
                                } else {
                                    changeFocus();
                                }
                            } else {
                                showInvalidValueEnteredToast(str2, 0);
                            }
                        } else if (parseInt2 >= minValue2) {
                            changeFocus();
                        } else if (Character.getNumericValue(charSequence2.charAt(0)) < 4) {
                            showInvalidValueEnteredToast(Character.toString(charSequence2.charAt(0)), 1);
                        } else {
                            showInvalidValueEnteredToast(str2, 0);
                        }
                    } else {
                        int minValue3 = SeslDatePickerSpinnerLayout.this.mYearSpinner.getMinValue();
                        int maxValue = SeslDatePickerSpinnerLayout.this.mYearSpinner.getMaxValue();
                        int parseInt3 = Integer.parseInt(charSequence2);
                        if (this.mPrevText.length() >= length || length != this.mMaxLen) {
                            int i6 = length - 1;
                            int pow = (int) (1000.0d / Math.pow(10.0d, (double) i6));
                            if (length != 1) {
                                str2 = charSequence2.substring(0, i6);
                            }
                            if (parseInt3 < minValue3 / pow || parseInt3 > maxValue / pow) {
                                showInvalidValueEnteredToast(str2, i6);
                            }
                        } else if (parseInt3 < minValue3 || parseInt3 > maxValue) {
                            showInvalidValueEnteredToast(charSequence2.substring(0, 3), 3);
                        } else {
                            if (SeslDatePickerSpinnerLayout.this.usingNumericMonths()) {
                                i4 = SeslDatePickerSpinnerLayout.this.mMonthSpinner.getValue() - 1;
                            } else {
                                i4 = SeslDatePickerSpinnerLayout.this.mMonthSpinner.getValue();
                            }
                            SeslDatePickerSpinnerLayout.this.mTempDate.clear();
                            SeslDatePickerSpinnerLayout.this.mTempDate.set(parseInt3, i4, SeslDatePickerSpinnerLayout.this.mDaySpinner.getValue());
                            Calendar instance = Calendar.getInstance();
                            instance.clear();
                            instance.set(SeslDatePickerSpinnerLayout.this.mMinDate.get(1), SeslDatePickerSpinnerLayout.this.mMinDate.get(2), SeslDatePickerSpinnerLayout.this.mMinDate.get(5));
                            if (SeslDatePickerSpinnerLayout.this.mTempDate.before(instance) || SeslDatePickerSpinnerLayout.this.mTempDate.after(SeslDatePickerSpinnerLayout.this.mMaxDate)) {
                                showInvalidValueEnteredToast(charSequence2.substring(0, 3), 3);
                            } else {
                                changeFocus();
                            }
                        }
                    }
                }
            }
        }

        private void showInvalidValueEnteredToast(String str, int i) {
            SeslDatePickerSpinnerLayout.this.mPickerTexts[this.mId].setText(str);
            if (i != 0) {
                SeslDatePickerSpinnerLayout.this.mPickerTexts[this.mId].setSelection(i);
            }
            if (SeslDatePickerSpinnerLayout.this.mToast == null) {
                SeslDatePickerSpinnerLayout seslDatePickerSpinnerLayout = SeslDatePickerSpinnerLayout.this;
                seslDatePickerSpinnerLayout.mToast = Toast.makeText(seslDatePickerSpinnerLayout.mContext, SeslDatePickerSpinnerLayout.this.mToastText, 0);
            }
            SeslDatePickerSpinnerLayout.this.mToast.show();
        }

        private boolean isSwaLanguage() {
            String language = SeslDatePickerSpinnerLayout.this.mCurrentLocale.getLanguage();
            return "hi".equals(language) || "ta".equals(language) || "ml".equals(language) || "te".equals(language) || "or".equals(language) || "ne".equals(language) || "as".equals(language) || "bn".equals(language) || "gu".equals(language) || "si".equals(language) || "pa".equals(language) || "kn".equals(language) || "mr".equals(language);
        }

        private boolean isMeaLanguage() {
            String language = SeslDatePickerSpinnerLayout.this.mCurrentLocale.getLanguage();
            return "ar".equals(language) || "fa".equals(language) || "ur".equals(language);
        }

        private boolean isFarsiLanguage() {
            return "fa".equals(SeslDatePickerSpinnerLayout.this.mCurrentLocale.getLanguage());
        }

        private boolean isNumericStr(String str) {
            return !TextUtils.isEmpty(str) && Character.isDigit(str.charAt(0));
        }

        private boolean isMonthStr(String str) {
            for (int i = 0; i < SeslDatePickerSpinnerLayout.this.mNumberOfMonths; i++) {
                if (str.equals(SeslDatePickerSpinnerLayout.this.mShortMonths[i])) {
                    return true;
                }
            }
            return false;
        }

        private void changeFocus() {
            AccessibilityManager accessibilityManager = (AccessibilityManager) SeslDatePickerSpinnerLayout.this.mContext.getSystemService("accessibility");
            if (accessibilityManager == null || !accessibilityManager.isTouchExplorationEnabled()) {
                SeslDatePickerSpinnerLayout seslDatePickerSpinnerLayout = SeslDatePickerSpinnerLayout.this;
                seslDatePickerSpinnerLayout.seslLog("[" + this.mId + "] " + "changeFocus() mNext : " + this.mNext + ", mCheck : " + this.mCheck);
                if (this.mNext >= 0) {
                    if (!SeslDatePickerSpinnerLayout.this.mPickerTexts[this.mCheck].isFocused()) {
                        SeslDatePickerSpinnerLayout.this.mPickerTexts[this.mNext].requestFocus();
                    }
                    if (SeslDatePickerSpinnerLayout.this.mPickerTexts[this.mId].isFocused()) {
                        SeslDatePickerSpinnerLayout.this.mPickerTexts[this.mId].clearFocus();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnSpinnerDateChangedListener(SeslDatePicker seslDatePicker, OnSpinnerDateChangedListener onSpinnerDateChangedListener) {
        if (this.mDatePicker == null) {
            this.mDatePicker = seslDatePicker;
        }
        this.mOnSpinnerDateChangedListener = onSpinnerDateChangedListener;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateModeState(boolean z) {
        if (this.mIsEditTextMode != z && !z) {
            if (this.mDaySpinner.isEditTextMode()) {
                this.mDaySpinner.setEditTextMode(false);
            }
            if (this.mMonthSpinner.isEditTextMode()) {
                this.mMonthSpinner.setEditTextMode(false);
            }
            if (this.mYearSpinner.isEditTextMode()) {
                this.mYearSpinner.setEditTextMode(false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setEditTextMode(boolean z) {
        if (this.mIsEditTextMode != z) {
            this.mIsEditTextMode = z;
            InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
            this.mDaySpinner.setEditTextMode(z);
            this.mMonthSpinner.setEditTextMode(z);
            this.mYearSpinner.setEditTextMode(z);
            if (inputMethodManager != null) {
                if (!this.mIsEditTextMode) {
                    inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
                } else {
                    inputMethodManager.showSoftInput(this.mDaySpinner, 0);
                }
            }
            SeslDatePicker.OnEditTextModeChangedListener onEditTextModeChangedListener = this.mOnEditTextModeChangedListener;
            if (onEditTextModeChangedListener != null) {
                onEditTextModeChangedListener.onEditTextModeChanged(this.mDatePicker, z);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isEditTextMode() {
        return this.mIsEditTextMode;
    }

    /* access modifiers changed from: package-private */
    public void setOnEditTextModeChangedListener(SeslDatePicker seslDatePicker, SeslDatePicker.OnEditTextModeChangedListener onEditTextModeChangedListener) {
        if (this.mDatePicker == null) {
            this.mDatePicker = seslDatePicker;
        }
        this.mOnEditTextModeChangedListener = onEditTextModeChangedListener;
    }

    /* access modifiers changed from: package-private */
    public EditText getEditText(int i) {
        if (i == 0) {
            return this.mDaySpinner.getEditText();
        }
        if (i == 1) {
            return this.mMonthSpinner.getEditText();
        }
        if (i != 2) {
            return this.mDaySpinner.getEditText();
        }
        return this.mYearSpinner.getEditText();
    }

    /* access modifiers changed from: package-private */
    public SeslNumberPicker getNumberPicker(int i) {
        if (i == 0) {
            return this.mDaySpinner;
        }
        if (i == 1) {
            return this.mMonthSpinner;
        }
        if (i != 2) {
            return this.mDaySpinner;
        }
        return this.mYearSpinner;
    }

    /* access modifiers changed from: package-private */
    public void setLunar(boolean z, boolean z2, PathClassLoader pathClassLoader) {
        this.mIsLunar = z;
        this.mIsLeapMonth = z2;
        if (this.mIsLunar && this.mPathClassLoader == null) {
            this.mPathClassLoader = pathClassLoader;
            this.mSolarLunarTables = SeslFeatureReflector.getSolarLunarTables(this.mPathClassLoader);
        }
        updateSpinners(false, true, true, true);
    }

    /* access modifiers changed from: package-private */
    public void setIsLeapMonth(boolean z) {
        this.mIsLeapMonth = z;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getLunarMaxDayOfMonth(int i, int i2, boolean z) {
        Object obj = this.mSolarLunarTables;
        if (obj == null) {
            return 0;
        }
        return SeslSolarLunarTablesReflector.getDayLengthOf(this.mPathClassLoader, obj, i, i2, z);
    }

    public void requestLayout() {
        super.requestLayout();
        SeslNumberPicker seslNumberPicker = this.mDaySpinner;
        if (seslNumberPicker != null) {
            seslNumberPicker.requestLayout();
        }
        SeslNumberPicker seslNumberPicker2 = this.mYearSpinner;
        if (seslNumberPicker2 != null) {
            seslNumberPicker2.requestLayout();
        }
        SeslNumberPicker seslNumberPicker3 = this.mMonthSpinner;
        if (seslNumberPicker3 != null) {
            seslNumberPicker3.requestLayout();
        }
    }
}
