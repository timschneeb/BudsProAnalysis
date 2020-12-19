package androidx.picker.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.picker.R;
import androidx.picker.util.SeslAnimationListener;
import androidx.picker.widget.SeslNumberPicker;
import androidx.picker.widget.SeslTimePicker;
import androidx.reflect.icu.SeslLocaleDataReflector;
import java.io.File;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/* access modifiers changed from: package-private */
public class SeslTimePickerSpinnerDelegate extends SeslTimePicker.AbsTimePickerDelegate {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int DEFAULT_MINUTE_INTERVAL = 1;
    private static final char[] DIGIT_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 1632, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1776, 1777, 1778, 1779, 1780, 1781, 1782, 1783, 1784, 1785, 2406, 2407, 2408, 2409, 2410, 2411, 2412, 2413, 2414, 2415, 2534, 2535, 2536, 2537, 2538, 2539, 2540, 2541, 2542, 2543, 3302, 3303, 3304, 3305, 3306, 3307, 3308, 3309, 3310, 3311, 4160, 4161, 4162, 4163, 4164, 4165, 4166, 4167, 4168, 4169};
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final int LAST_HOUR_IN_DAY = 23;
    private static final int LAYOUT_MODE_DEFAULT = 0;
    private static final int LAYOUT_MODE_MULTIPANE = 2;
    private static final int LAYOUT_MODE_PHONE = 1;
    private static final char QUOTE = '\'';
    private static final String TAG = "SeslTimePickerSpinner";
    private final View mAmPmMarginInside;
    private final View mAmPmMarginOutside;
    private final SeslNumberPicker mAmPmSpinner;
    private final EditText mAmPmSpinnerInput;
    private final String[] mAmPmStrings;
    private final int mDefaultHeight;
    private final int mDefaultWidth;
    private final TextView mDivider;
    private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        /* class androidx.picker.widget.SeslTimePickerSpinnerDelegate.AnonymousClass5 */

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 6) {
                if (!SeslTimePickerSpinnerDelegate.this.mSkipToChangeInterval && !SeslTimePickerSpinnerDelegate.this.mMinuteSpinner.isChangedDefaultInterval() && SeslTimePickerSpinnerDelegate.this.mMinuteSpinner.getValue() % 5 != 0) {
                    SeslTimePickerSpinnerDelegate.this.mMinuteSpinner.applyWheelCustomInterval(false);
                }
                SeslTimePickerSpinnerDelegate.this.updateInputState();
                SeslTimePickerSpinnerDelegate.this.setEditTextMode(false);
            }
            return false;
        }
    };
    private final View mEmpty1;
    private final View mEmpty2;
    private char mHourFormat;
    private final SeslNumberPicker mHourSpinner;
    private final EditText mHourSpinnerInput;
    private boolean mHourWithTwoDigit;
    private boolean mIs24HourView;
    private boolean mIsAm;
    private boolean mIsAmPmAutoFlipped = false;
    private boolean mIsDateTimeMode;
    private boolean mIsEditTextMode;
    private boolean mIsEnabled = true;
    private boolean mIsInvalidMinute = false;
    private boolean mIsMarginLeftShown;
    private int mLayoutMode;
    private final View mMarginLeft;
    private final View mMarginRight;
    private int mMinuteInterval = 1;
    private final SeslNumberPicker mMinuteSpinner;
    private final EditText mMinuteSpinnerInput;
    private SeslNumberPicker.OnEditTextModeChangedListener mModeChangeListener = new SeslNumberPicker.OnEditTextModeChangedListener() {
        /* class androidx.picker.widget.SeslTimePickerSpinnerDelegate.AnonymousClass4 */

        @Override // androidx.picker.widget.SeslNumberPicker.OnEditTextModeChangedListener
        public void onEditTextModeChanged(SeslNumberPicker seslNumberPicker, boolean z) {
            SeslTimePickerSpinnerDelegate.this.setEditTextMode(z);
            SeslTimePickerSpinnerDelegate.this.updateModeState(z);
        }
    };
    private EditText[] mPickerTexts = new EditText[3];
    private boolean mSkipToChangeInterval = false;
    private Calendar mTempCalendar;

    SeslTimePickerSpinnerDelegate(SeslTimePicker seslTimePicker, Context context, AttributeSet attributeSet, int i, int i2) {
        super(seslTimePicker, context);
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TimePicker, i, i2);
        this.mIsDateTimeMode = obtainStyledAttributes.getBoolean(R.styleable.TimePicker_dateTimeMode, false);
        this.mLayoutMode = obtainStyledAttributes.getInt(R.styleable.TimePicker_timeLayoutMode, 0);
        obtainStyledAttributes.recycle();
        LayoutInflater from = LayoutInflater.from(this.mContext);
        if (isDateTimeMode()) {
            int i3 = this.mLayoutMode;
            if (i3 == 1) {
                from.inflate(R.layout.sesl_spinning_datepicker_time_picker_spinner_phone, (ViewGroup) this.mDelegator, true);
            } else if (i3 == 2) {
                from.inflate(R.layout.sesl_spinning_datepicker_time_picker_spinner_multipane, (ViewGroup) this.mDelegator, true);
            } else {
                from.inflate(R.layout.sesl_spinning_datepicker_time_picker_spinner, (ViewGroup) this.mDelegator, true);
            }
        } else {
            from.inflate(R.layout.sesl_time_picker_spinner, (ViewGroup) this.mDelegator, true);
        }
        this.mHourSpinner = (SeslNumberPicker) seslTimePicker.findViewById(R.id.hour);
        this.mHourSpinner.setPickerContentDescription(context.getResources().getString(R.string.sesl_time_picker_hour));
        this.mHourSpinner.setOnEditTextModeChangedListener(this.mModeChangeListener);
        this.mHourSpinner.setOnValueChangedListener(new SeslNumberPicker.OnValueChangeListener() {
            /* class androidx.picker.widget.SeslTimePickerSpinnerDelegate.AnonymousClass1 */

            @Override // androidx.picker.widget.SeslNumberPicker.OnValueChangeListener
            public void onValueChange(SeslNumberPicker seslNumberPicker, int i, int i2) {
                if (!SeslTimePickerSpinnerDelegate.this.is24Hour() && !SeslTimePickerSpinnerDelegate.this.mIsEditTextMode) {
                    int i3 = 12;
                    if (SeslTimePickerSpinnerDelegate.this.mHourFormat == 'K') {
                        i3 = 0;
                    }
                    if ((i == 11 && i2 == i3) || (i == i3 && i2 == 11)) {
                        SeslTimePickerSpinnerDelegate seslTimePickerSpinnerDelegate = SeslTimePickerSpinnerDelegate.this;
                        seslTimePickerSpinnerDelegate.mIsAm = seslTimePickerSpinnerDelegate.mAmPmSpinner.getValue() != 0;
                        SeslTimePickerSpinnerDelegate.this.mAmPmSpinner.performClick(false);
                        SeslTimePickerSpinnerDelegate.this.mIsAmPmAutoFlipped = true;
                        SeslTimePickerSpinnerDelegate.this.mAmPmSpinner.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {
                            /* class androidx.picker.widget.SeslTimePickerSpinnerDelegate.AnonymousClass1.AnonymousClass1 */

                            public void run() {
                                SeslTimePickerSpinnerDelegate.this.mIsAmPmAutoFlipped = false;
                                if (SeslTimePickerSpinnerDelegate.this.mAmPmSpinner != null) {
                                    SeslTimePickerSpinnerDelegate.this.mAmPmSpinner.setEnabled(true);
                                }
                            }
                        }, 500);
                    }
                }
                SeslTimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        this.mHourSpinnerInput = (EditText) this.mHourSpinner.findViewById(R.id.numberpicker_input);
        this.mHourSpinner.setYearDateTimeInputMode();
        this.mHourSpinnerInput.setImeOptions(33554437);
        this.mHourSpinner.setMaxInputLength(2);
        this.mHourSpinnerInput.setOnEditorActionListener(this.mEditorActionListener);
        this.mDivider = (TextView) this.mDelegator.findViewById(R.id.divider);
        if (this.mDivider != null) {
            setDividerText();
        }
        Resources resources = this.mDelegator.getResources();
        int i4 = resources.getConfiguration().smallestScreenWidthDp;
        if (i4 >= 600) {
            this.mDefaultWidth = resources.getDimensionPixelSize(R.dimen.sesl_time_picker_dialog_min_width);
        } else {
            this.mDefaultWidth = (int) (TypedValue.applyDimension(1, (float) i4, resources.getDisplayMetrics()) + 0.5f);
        }
        this.mDefaultHeight = resources.getDimensionPixelSize(R.dimen.sesl_time_picker_spinner_height);
        this.mMinuteSpinner = (SeslNumberPicker) this.mDelegator.findViewById(R.id.minute);
        this.mMinuteSpinner.setYearDateTimeInputMode();
        this.mMinuteSpinner.setMinValue(0);
        this.mMinuteSpinner.setMaxValue(59);
        this.mMinuteSpinner.setOnLongPressUpdateInterval(100);
        this.mMinuteSpinner.setSkipValuesOnLongPressEnabled(true);
        this.mMinuteSpinner.setFormatter(SeslNumberPicker.getTwoDigitFormatter());
        this.mMinuteSpinner.setPickerContentDescription(context.getResources().getString(R.string.sesl_time_picker_minute));
        this.mMinuteSpinner.setOnEditTextModeChangedListener(this.mModeChangeListener);
        this.mMinuteSpinner.setOnValueChangedListener(new SeslNumberPicker.OnValueChangeListener() {
            /* class androidx.picker.widget.SeslTimePickerSpinnerDelegate.AnonymousClass2 */

            @Override // androidx.picker.widget.SeslNumberPicker.OnValueChangeListener
            public void onValueChange(SeslNumberPicker seslNumberPicker, int i, int i2) {
                SeslTimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        this.mMinuteSpinnerInput = (EditText) this.mMinuteSpinner.findViewById(R.id.numberpicker_input);
        this.mMinuteSpinnerInput.setImeOptions(33554438);
        this.mMinuteSpinner.setMaxInputLength(2);
        this.mMinuteSpinnerInput.setOnEditorActionListener(this.mEditorActionListener);
        this.mAmPmStrings = getAmPmStrings(context);
        View findViewById = this.mDelegator.findViewById(R.id.amPm);
        this.mEmpty1 = this.mDelegator.findViewById(R.id.sesl_timepicker_empty_1);
        this.mEmpty2 = this.mDelegator.findViewById(R.id.sesl_timepicker_empty_2);
        this.mMarginRight = this.mDelegator.findViewById(R.id.sesl_timepicker_margin_right);
        this.mMarginLeft = this.mDelegator.findViewById(R.id.sesl_timepicker_margin_left);
        this.mIsMarginLeftShown = false;
        this.mAmPmMarginInside = this.mDelegator.findViewById(R.id.sesl_timepicker_ampm_picker_margin_left);
        this.mAmPmMarginOutside = this.mDelegator.findViewById(R.id.sesl_timepicker_ampm_picker_margin_right);
        this.mAmPmSpinner = (SeslNumberPicker) findViewById;
        this.mAmPmSpinner.setAmPm();
        this.mAmPmSpinner.setMinValue(0);
        this.mAmPmSpinner.setMaxValue(1);
        this.mAmPmSpinner.setDisplayedValues(this.mAmPmStrings);
        this.mAmPmSpinner.setOnValueChangedListener(new SeslNumberPicker.OnValueChangeListener() {
            /* class androidx.picker.widget.SeslTimePickerSpinnerDelegate.AnonymousClass3 */

            @Override // androidx.picker.widget.SeslNumberPicker.OnValueChangeListener
            public void onValueChange(SeslNumberPicker seslNumberPicker, int i, int i2) {
                boolean z = true;
                if (!SeslTimePickerSpinnerDelegate.this.mAmPmSpinner.isEnabled()) {
                    SeslTimePickerSpinnerDelegate.this.mAmPmSpinner.setEnabled(true);
                }
                if (SeslTimePickerSpinnerDelegate.this.mIsAmPmAutoFlipped) {
                    SeslTimePickerSpinnerDelegate.this.mIsAmPmAutoFlipped = false;
                } else if (SeslTimePickerSpinnerDelegate.this.mIsAm && i2 == 0) {
                } else {
                    if (SeslTimePickerSpinnerDelegate.this.mIsAm || i2 != 1) {
                        SeslTimePickerSpinnerDelegate seslTimePickerSpinnerDelegate = SeslTimePickerSpinnerDelegate.this;
                        if (i2 != 0) {
                            z = false;
                        }
                        seslTimePickerSpinnerDelegate.mIsAm = z;
                        SeslTimePickerSpinnerDelegate.this.updateAmPmControl();
                        SeslTimePickerSpinnerDelegate.this.onTimeChanged();
                        SeslTimePickerSpinnerDelegate.this.validCheck();
                    }
                }
            }
        });
        this.mAmPmSpinnerInput = (EditText) this.mAmPmSpinner.findViewById(R.id.numberpicker_input);
        this.mAmPmSpinnerInput.setInputType(0);
        this.mAmPmSpinnerInput.setCursorVisible(false);
        this.mAmPmSpinnerInput.setFocusable(false);
        this.mAmPmSpinnerInput.setFocusableInTouchMode(false);
        byte directionality = Character.getDirectionality(this.mAmPmStrings[0].charAt(0));
        boolean z = directionality == 1 || directionality == 2;
        byte directionality2 = Character.getDirectionality(this.mCurrentLocale.getDisplayName(this.mCurrentLocale).charAt(0));
        boolean z2 = directionality2 == 1 || directionality2 == 2;
        boolean isAmPmAtStart = isAmPmAtStart();
        if ((isAmPmAtStart && z2 == z) || (!isAmPmAtStart && z2 != z)) {
            changeAmPmView();
        }
        getHourFormatData();
        updateHourControl();
        updateAmPmControl();
        setHour(this.mTempCalendar.get(11));
        setMinute(this.mTempCalendar.get(12));
        if (!isEnabled()) {
            setEnabled(false);
        }
        if (this.mDelegator.getImportantForAccessibility() == 0) {
            this.mDelegator.setImportantForAccessibility(1);
        }
        setTextWatcher();
        if (isDateTimeMode()) {
            float dimensionPixelSize = (((float) resources.getDimensionPixelSize(R.dimen.sesl_spinning_date_picker_date_spinner_text_size)) * 160.0f) / ((float) resources.getDisplayMetrics().densityDpi);
            setNumberPickerTextSize(0, dimensionPixelSize);
            setNumberPickerTextSize(1, dimensionPixelSize);
            setNumberPickerTextSize(3, dimensionPixelSize);
            setNumberPickerTextSize(2, dimensionPixelSize);
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void requestLayout() {
        SeslNumberPicker seslNumberPicker = this.mAmPmSpinner;
        if (seslNumberPicker != null) {
            seslNumberPicker.requestLayout();
        }
        SeslNumberPicker seslNumberPicker2 = this.mHourSpinner;
        if (seslNumberPicker2 != null) {
            seslNumberPicker2.requestLayout();
        }
        SeslNumberPicker seslNumberPicker3 = this.mMinuteSpinner;
        if (seslNumberPicker3 != null) {
            seslNumberPicker3.requestLayout();
        }
    }

    private void changeAmPmView() {
        ViewGroup viewGroup = (ViewGroup) this.mDelegator.findViewById(R.id.sesl_timepicker_layout);
        viewGroup.removeView(this.mAmPmMarginInside);
        viewGroup.removeView(this.mAmPmSpinner);
        viewGroup.removeView(this.mAmPmMarginOutside);
        int indexOfChild = isDateTimeMode() ? viewGroup.indexOfChild(this.mMarginLeft) + 1 : 0;
        viewGroup.addView(this.mAmPmMarginInside, indexOfChild);
        viewGroup.addView(this.mAmPmSpinner, indexOfChild);
        viewGroup.addView(this.mAmPmMarginOutside, indexOfChild);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateModeState(boolean z) {
        if (this.mIsEditTextMode != z && !z) {
            if (this.mHourSpinner.isEditTextMode()) {
                this.mHourSpinner.setEditTextMode(false);
            }
            if (this.mMinuteSpinner.isEditTextMode()) {
                this.mMinuteSpinner.setEditTextMode(false);
            }
        }
    }

    private void getHourFormatData() {
        String bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mCurrentLocale, this.mIs24HourView ? "Hm" : "hm");
        int length = bestDateTimePattern.length();
        this.mHourWithTwoDigit = false;
        for (int i = 0; i < length; i++) {
            char charAt = bestDateTimePattern.charAt(i);
            if (charAt == 'H' || charAt == 'h' || charAt == 'K' || charAt == 'k') {
                this.mHourFormat = charAt;
                int i2 = i + 1;
                if (i2 < length && charAt == bestDateTimePattern.charAt(i2)) {
                    this.mHourWithTwoDigit = true;
                    return;
                }
                return;
            }
        }
    }

    private boolean isAmPmAtStart() {
        return DateFormat.getBestDateTimePattern(this.mCurrentLocale, "hm").startsWith("a");
    }

    private void setDividerText() {
        this.mDivider.setText(getHourMinSeparatorFromPattern(DateFormat.getBestDateTimePattern(this.mCurrentLocale, this.mIs24HourView ? "Hm" : "hm")));
        Typeface defaultFromStyle = Typeface.defaultFromStyle(0);
        Typeface create = Typeface.create("sec-roboto-condensed-light", 0);
        Typeface create2 = Typeface.create("sec-roboto-light", 0);
        if (!defaultFromStyle.equals(create2)) {
            create = create2;
        } else if (create.equals(create2)) {
            create = Typeface.create("sans-serif-thin", 0);
        }
        String string = Settings.System.getString(this.mContext.getContentResolver(), "theme_font_clock");
        if (string != null && !string.equals("")) {
            this.mDivider.setTypeface(getFontTypeface(string));
        }
        this.mDivider.setTypeface(create);
    }

    private static String getHourMinSeparatorFromPattern(String str) {
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt != ' ') {
                if (charAt != '\'') {
                    if (charAt == 'H' || charAt == 'K' || charAt == 'h' || charAt == 'k') {
                        z = true;
                    } else if (z) {
                        return Character.toString(str.charAt(i));
                    }
                } else if (z) {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str.substring(i));
                    return spannableStringBuilder.subSequence(0, appendQuotedText(spannableStringBuilder, 0)).toString();
                }
            }
        }
        return ":";
    }

    private static int appendQuotedText(SpannableStringBuilder spannableStringBuilder, int i) {
        int i2;
        int length = spannableStringBuilder.length();
        int i3 = i + 1;
        if (i3 >= length || spannableStringBuilder.charAt(i3) != '\'') {
            int i4 = 0;
            spannableStringBuilder.delete(i, i3);
            int i5 = length - 1;
            while (true) {
                if (i >= i5) {
                    break;
                } else if (spannableStringBuilder.charAt(i) == '\'') {
                    i2 = i + 1;
                    if (i2 >= i5 || spannableStringBuilder.charAt(i2) != '\'') {
                        spannableStringBuilder.delete(i, i2);
                    } else {
                        spannableStringBuilder.delete(i, i2);
                        i5--;
                        i4++;
                        i = i2;
                    }
                } else {
                    i++;
                    i4++;
                }
            }
            spannableStringBuilder.delete(i, i2);
            return i4;
        }
        spannableStringBuilder.delete(i, i3);
        return 1;
    }

    private static Typeface getFontTypeface(String str) {
        if (!new File(str).exists()) {
            return null;
        }
        try {
            return Typeface.createFromFile(str);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setHour(int i) {
        setCurrentHour(i, true);
    }

    private void setCurrentHour(int i, boolean z) {
        if (i != getHour()) {
            if (!is24Hour()) {
                if (i >= 12) {
                    this.mIsAm = false;
                    if (i > 12) {
                        i -= 12;
                    }
                } else {
                    this.mIsAm = true;
                    if (i == 0) {
                        i = 12;
                    }
                }
                updateAmPmControl();
            }
            this.mHourSpinner.setValue(i);
            if (z) {
                onTimeChanged();
            }
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public int getHour() {
        int value = this.mHourSpinner.getValue();
        if (is24Hour()) {
            return value;
        }
        if (this.mIsAm) {
            return value % 12;
        }
        return (value % 12) + 12;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setMinute(int i) {
        int minuteInterval = getMinuteInterval();
        if (minuteInterval != 1) {
            if (this.mIsEditTextMode) {
                this.mMinuteSpinner.setValue(i);
            } else {
                if (i % minuteInterval == 0) {
                    this.mMinuteSpinner.applyWheelCustomInterval(true);
                } else {
                    this.mMinuteSpinner.applyWheelCustomInterval(false);
                }
                this.mMinuteSpinner.setValue(i);
            }
        } else if (i != getMinute()) {
            this.mMinuteSpinner.setValue(i);
        } else if (isCharacterNumberLanguage()) {
            this.mMinuteSpinner.setValue(i);
            return;
        } else {
            return;
        }
        onTimeChanged();
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public int getMinute() {
        return this.mMinuteSpinner.getValue();
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setIs24Hour(boolean z) {
        if (this.mIs24HourView != z) {
            int hour = getHour();
            this.mIs24HourView = z;
            getHourFormatData();
            updateHourControl();
            setCurrentHour(hour, false);
            updateAmPmControl();
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void showMarginLeft(boolean z) {
        this.mIsMarginLeftShown = z;
        if (isDateTimeMode()) {
            updateAmPmControl();
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public boolean is24Hour() {
        return this.mIs24HourView;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void set5MinuteInterval(boolean z) {
        if (z) {
            if (getMinute() >= 58) {
                int hour = getHour();
                setCurrentHour(hour == 23 ? 0 : hour + 1, false);
            }
            this.mMinuteSpinner.setCustomIntervalValue(5);
            this.mMinuteSpinner.applyWheelCustomInterval(true);
            setMinuteInterval(5);
            return;
        }
        this.mMinuteSpinner.setCustomIntervalValue(5);
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setOnTimeChangedListener(SeslTimePicker.OnTimeChangedListener onTimeChangedListener) {
        this.mOnTimeChangedListener = onTimeChangedListener;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setEnabled(boolean z) {
        this.mMinuteSpinner.setEnabled(z);
        TextView textView = this.mDivider;
        if (textView != null) {
            textView.setEnabled(z);
        }
        this.mHourSpinner.setEnabled(z);
        this.mAmPmSpinner.setEnabled(z);
        this.mIsEnabled = z;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public int getBaseline() {
        return this.mHourSpinner.getBaseline();
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void onConfigurationChanged(Configuration configuration) {
        setCurrentLocale(configuration.locale);
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new SavedState(parcelable, getHour(), getMinute());
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        setHour(savedState.getHour());
        setMinute(savedState.getMinute());
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int i = this.mIs24HourView ? 129 : 65;
        this.mTempCalendar.set(11, getHour());
        this.mTempCalendar.set(12, getMinute());
        accessibilityEvent.getText().add(DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), i));
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(TimePicker.class.getName());
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        accessibilityNodeInfo.setClassName(TimePicker.class.getName());
    }

    private int getMinuteInterval() {
        return this.mMinuteInterval;
    }

    private void setMinuteInterval(int i) {
        this.mMinuteInterval = i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateInputState() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        if (inputMethodManager.isActive(this.mHourSpinnerInput)) {
            inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            EditText editText = this.mHourSpinnerInput;
            if (editText != null) {
                editText.clearFocus();
            }
        } else if (inputMethodManager.isActive(this.mMinuteSpinnerInput)) {
            inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            EditText editText2 = this.mMinuteSpinnerInput;
            if (editText2 != null) {
                editText2.clearFocus();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateAmPmControl() {
        if (is24Hour()) {
            this.mAmPmSpinner.setVisibility(8);
            this.mAmPmMarginInside.setVisibility(8);
            this.mAmPmMarginOutside.setVisibility(8);
            if (!isDateTimeMode() || this.mIsMarginLeftShown) {
                this.mEmpty1.setVisibility(0);
            }
            this.mEmpty2.setVisibility(0);
            if (isDateTimeMode()) {
                this.mMarginRight.setVisibility(8);
                this.mMarginLeft.setVisibility(8);
            }
        } else {
            this.mAmPmSpinner.setValue(!this.mIsAm ? 1 : 0);
            this.mAmPmSpinner.setVisibility(0);
            this.mAmPmMarginInside.setVisibility(0);
            this.mAmPmMarginOutside.setVisibility(0);
            this.mEmpty1.setVisibility(8);
            this.mEmpty2.setVisibility(8);
            if (isDateTimeMode()) {
                this.mMarginRight.setVisibility(0);
                if (this.mIsMarginLeftShown) {
                    this.mMarginLeft.setVisibility(0);
                }
            }
        }
        this.mDelegator.sendAccessibilityEvent(4);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void validCheck() {
        if (this.mIsEditTextMode) {
            EditText editText = this.mHourSpinnerInput;
            if (editText != null && editText.hasFocus()) {
                if (!TextUtils.isEmpty(this.mHourSpinnerInput.getText())) {
                    int parseInt = Integer.parseInt(String.valueOf(this.mHourSpinnerInput.getText()));
                    if (!is24Hour()) {
                        if (!this.mIsAm && parseInt != 12) {
                            parseInt += 12;
                        } else if (this.mIsAm && parseInt == 12) {
                            parseInt = 0;
                        }
                    }
                    setHour(parseInt);
                    this.mHourSpinnerInput.selectAll();
                } else {
                    return;
                }
            }
            EditText editText2 = this.mMinuteSpinnerInput;
            if (editText2 != null && editText2.hasFocus() && !TextUtils.isEmpty(this.mMinuteSpinnerInput.getText())) {
                setMinute(Integer.parseInt(String.valueOf(this.mMinuteSpinnerInput.getText())));
                this.mMinuteSpinnerInput.selectAll();
            }
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate, androidx.picker.widget.SeslTimePicker.AbsTimePickerDelegate
    public void setCurrentLocale(Locale locale) {
        super.setCurrentLocale(locale);
        this.mTempCalendar = Calendar.getInstance(locale);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
    }

    private void updateHourControl() {
        if (is24Hour()) {
            if (this.mHourFormat == 'k') {
                this.mHourSpinner.setMinValue(1);
                this.mHourSpinner.setMaxValue(24);
            } else {
                this.mHourSpinner.setMinValue(0);
                this.mHourSpinner.setMaxValue(23);
            }
        } else if (this.mHourFormat == 'K') {
            this.mHourSpinner.setMinValue(0);
            this.mHourSpinner.setMaxValue(11);
        } else {
            this.mHourSpinner.setMinValue(1);
            this.mHourSpinner.setMaxValue(12);
        }
        this.mHourSpinner.setFormatter(this.mHourWithTwoDigit ? SeslNumberPicker.getTwoDigitFormatter() : null);
    }

    /* access modifiers changed from: private */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class androidx.picker.widget.SeslTimePickerSpinnerDelegate.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final int mHour;
        private final int mMinute;

        private SavedState(Parcelable parcelable, int i, int i2) {
            super(parcelable);
            this.mHour = i;
            this.mMinute = i2;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mHour = parcel.readInt();
            this.mMinute = parcel.readInt();
        }

        public int getHour() {
            return this.mHour;
        }

        public int getMinute() {
            return this.mMinute;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mHour);
            parcel.writeInt(this.mMinute);
        }
    }

    public static String[] getAmPmStrings(Context context) {
        String[] strArr = new String[2];
        Object obj = SeslLocaleDataReflector.get(context.getResources().getConfiguration().locale);
        if (obj != null) {
            String[] field_amPm = SeslLocaleDataReflector.getField_amPm(obj);
            String field_narrowAm = SeslLocaleDataReflector.getField_narrowAm(obj);
            String field_narrowPm = SeslLocaleDataReflector.getField_narrowPm(obj);
            String str = field_amPm[0];
            String str2 = field_amPm[1];
            if (isMeaLanguage()) {
                strArr[0] = str;
                strArr[1] = str2;
                return strArr;
            }
            if (str.length() <= 4) {
                field_narrowAm = str;
            }
            strArr[0] = field_narrowAm;
            if (str2.length() <= 4) {
                field_narrowPm = str2;
            }
            strArr[1] = field_narrowPm;
            return strArr;
        }
        Log.e(TAG, "LocaleData failed. Use DateFormatSymbols for ampm");
        return new DateFormatSymbols().getAmPmStrings();
    }

    private boolean isDateTimeMode() {
        return this.mIsDateTimeMode;
    }

    private static boolean isMeaLanguage() {
        String language = Locale.getDefault().getLanguage();
        return "lo".equals(language) || "ar".equals(language) || "fa".equals(language) || "ur".equals(language);
    }

    private static boolean isCharacterNumberLanguage() {
        String language = Locale.getDefault().getLanguage();
        return "lo".equals(language) || "ar".equals(language) || "fa".equals(language) || "ur".equals(language) || "my".equals(language);
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setOnEditTextModeChangedListener(SeslTimePicker.OnEditTextModeChangedListener onEditTextModeChangedListener) {
        this.mOnEditTextModeChangedListener = onEditTextModeChangedListener;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setEditTextMode(boolean z) {
        if (this.mIsEditTextMode != z) {
            this.mIsEditTextMode = z;
            InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
            this.mHourSpinner.setEditTextMode(z);
            this.mMinuteSpinner.setEditTextMode(z);
            if (inputMethodManager != null) {
                if (!this.mIsEditTextMode) {
                    inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
                } else {
                    if (!inputMethodManager.showSoftInput(this.mMinuteSpinnerInput.hasFocus() ? this.mMinuteSpinnerInput : this.mHourSpinnerInput, 0)) {
                        this.mDelegator.postDelayed(new Runnable() {
                            /* class androidx.picker.widget.SeslTimePickerSpinnerDelegate.AnonymousClass6 */

                            public void run() {
                                InputMethodManager inputMethodManager = (InputMethodManager) SeslTimePickerSpinnerDelegate.this.mContext.getSystemService("input_method");
                                if (SeslTimePickerSpinnerDelegate.this.mIsEditTextMode && inputMethodManager != null) {
                                    inputMethodManager.showSoftInput(SeslTimePickerSpinnerDelegate.this.mMinuteSpinnerInput.hasFocus() ? SeslTimePickerSpinnerDelegate.this.mMinuteSpinnerInput : SeslTimePickerSpinnerDelegate.this.mHourSpinnerInput, 0);
                                }
                            }
                        }, 20);
                    }
                }
            }
            if (this.mOnEditTextModeChangedListener != null) {
                this.mOnEditTextModeChangedListener.onEditTextModeChanged(this.mDelegator, z);
            }
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public boolean isEditTextMode() {
        return this.mIsEditTextMode;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void startAnimation(int i, SeslAnimationListener seslAnimationListener) {
        if (isAmPmAtStart()) {
            this.mAmPmSpinner.startAnimation(i, null);
            this.mHourSpinner.startAnimation(i + 55, null);
            this.mMinuteSpinner.startAnimation(i + 110, seslAnimationListener);
            return;
        }
        this.mHourSpinner.startAnimation(i, null);
        this.mMinuteSpinner.startAnimation(i + 55, seslAnimationListener);
        this.mAmPmSpinner.startAnimation(i + 110, null);
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public EditText getEditText(int i) {
        if (i == 0) {
            return this.mHourSpinner.getEditText();
        }
        if (i != 2) {
            return this.mMinuteSpinner.getEditText();
        }
        return this.mAmPmSpinner.getEditText();
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public SeslNumberPicker getNumberPicker(int i) {
        if (i == 0) {
            return this.mHourSpinner;
        }
        if (i != 2) {
            return this.mMinuteSpinner;
        }
        return this.mAmPmSpinner;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setNumberPickerTextSize(int i, float f) {
        if (i == 0) {
            this.mHourSpinner.setTextSize(f);
        } else if (i == 1) {
            this.mMinuteSpinner.setTextSize(f);
        } else if (i == 2) {
            this.mAmPmSpinner.setTextSize(f);
        } else if (i != 3) {
            this.mMinuteSpinner.setTextSize(f);
        } else {
            this.mDivider.setTextSize(1, f);
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public void setNumberPickerTextTypeface(int i, Typeface typeface) {
        if (i == 0) {
            this.mHourSpinner.setTextTypeface(typeface);
        } else if (i == 1) {
            this.mMinuteSpinner.setTextTypeface(typeface);
        } else if (i == 2) {
            this.mAmPmSpinner.setTextTypeface(typeface);
        } else if (i != 3) {
            this.mMinuteSpinner.setTextTypeface(typeface);
        } else {
            this.mDivider.setTypeface(typeface);
        }
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public int getDefaultWidth() {
        return this.mDefaultWidth;
    }

    @Override // androidx.picker.widget.SeslTimePicker.TimePickerDelegate
    public int getDefaultHeight() {
        return this.mDefaultHeight;
    }

    private void setTextWatcher() {
        this.mPickerTexts[0] = this.mHourSpinner.getEditText();
        this.mPickerTexts[1] = this.mMinuteSpinner.getEditText();
        this.mPickerTexts[0].addTextChangedListener(new SeslTextWatcher(2, 0));
        this.mPickerTexts[1].addTextChangedListener(new SeslTextWatcher(2, 1));
        this.mPickerTexts[0].setOnKeyListener(new SeslKeyListener());
        this.mPickerTexts[1].setOnKeyListener(new SeslKeyListener());
    }

    /* access modifiers changed from: private */
    public class SeslKeyListener implements View.OnKeyListener {
        public SeslKeyListener() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() != 1) {
                return false;
            }
            if (i == 23) {
                return SeslTimePickerSpinnerDelegate.this.mDelegator.getResources().getConfiguration().keyboard != 3;
            }
            if (i != 61) {
                if (i != 66 && i != 160) {
                    return false;
                }
                if (SeslTimePickerSpinnerDelegate.this.isEditTextMode()) {
                    EditText editText = (EditText) view;
                    if ((editText.getImeOptions() & 5) == 5) {
                        View findNextFocus = FocusFinder.getInstance().findNextFocus(SeslTimePickerSpinnerDelegate.this.mDelegator, view, 2);
                        if (findNextFocus == null) {
                            return true;
                        }
                        findNextFocus.requestFocus();
                    } else if ((editText.getImeOptions() & 6) == 6) {
                        SeslTimePickerSpinnerDelegate.this.updateInputState();
                        SeslTimePickerSpinnerDelegate.this.setEditTextMode(false);
                    }
                }
            }
            return true;
        }
    }

    public class SeslTextWatcher implements TextWatcher {
        private int changedLen = 0;
        private int mId;
        private int mMaxLen;
        private int mNext;
        private String prevText;

        public void afterTextChanged(Editable editable) {
        }

        public SeslTextWatcher(int i, int i2) {
            this.mMaxLen = i;
            this.mId = i2;
            int i3 = this.mId;
            this.mNext = i3 + 1 >= 2 ? -1 : i3 + 1;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            this.prevText = charSequence.toString();
            this.changedLen = i3;
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String str = (String) SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].getTag();
            if (str == null || (!str.equals("onClick") && !str.equals("onLongClick"))) {
                int i4 = this.mId;
                if (i4 != 0) {
                    if (i4 != 1) {
                        if (this.prevText.length() < charSequence.length() && charSequence.length() == this.mMaxLen && SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].isFocused()) {
                            changeFocus();
                        }
                    } else if (this.changedLen != 1) {
                    } else {
                        if (charSequence.length() == this.mMaxLen) {
                            if (SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].isFocused()) {
                                changeFocus();
                            }
                        } else if (charSequence.length() > 0) {
                            int convertDigitCharacterToNumber = convertDigitCharacterToNumber(charSequence.toString());
                            if (convertDigitCharacterToNumber < 6 || convertDigitCharacterToNumber > 9) {
                                if (!SeslTimePickerSpinnerDelegate.this.mIsInvalidMinute || !(convertDigitCharacterToNumber == 5 || convertDigitCharacterToNumber == 0)) {
                                    SeslTimePickerSpinnerDelegate.this.mIsInvalidMinute = false;
                                    SeslTimePickerSpinnerDelegate.this.mSkipToChangeInterval = false;
                                    return;
                                }
                                SeslTimePickerSpinnerDelegate.this.mIsInvalidMinute = false;
                                SeslTimePickerSpinnerDelegate.this.mSkipToChangeInterval = true;
                            } else if (SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].isFocused()) {
                                SeslTimePickerSpinnerDelegate.this.mIsInvalidMinute = true;
                                changeFocus();
                            }
                        }
                    }
                } else if (this.changedLen != 1) {
                } else {
                    if (charSequence.length() == this.mMaxLen) {
                        if (SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].isFocused()) {
                            changeFocus();
                        }
                    } else if (charSequence.length() > 0) {
                        int convertDigitCharacterToNumber2 = convertDigitCharacterToNumber(charSequence.toString());
                        if ((convertDigitCharacterToNumber2 > 2 || (convertDigitCharacterToNumber2 > 1 && !SeslTimePickerSpinnerDelegate.this.is24Hour())) && SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].isFocused()) {
                            changeFocus();
                        }
                    }
                }
            } else {
                SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].setTag("");
            }
        }

        private void changeFocus() {
            if (((AccessibilityManager) SeslTimePickerSpinnerDelegate.this.mContext.getSystemService("accessibility")).isTouchExplorationEnabled()) {
                int i = this.mId;
                if (i == 0) {
                    SeslTimePickerSpinnerDelegate seslTimePickerSpinnerDelegate = SeslTimePickerSpinnerDelegate.this;
                    seslTimePickerSpinnerDelegate.setHour(Integer.parseInt(String.valueOf(seslTimePickerSpinnerDelegate.mPickerTexts[this.mId].getText())));
                    SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].selectAll();
                } else if (i == 1) {
                    SeslTimePickerSpinnerDelegate seslTimePickerSpinnerDelegate2 = SeslTimePickerSpinnerDelegate.this;
                    seslTimePickerSpinnerDelegate2.setMinute(Integer.parseInt(String.valueOf(seslTimePickerSpinnerDelegate2.mPickerTexts[this.mId].getText())));
                    SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].selectAll();
                }
            } else if (this.mNext >= 0) {
                SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mNext].requestFocus();
                if (SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].isFocused()) {
                    SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].clearFocus();
                }
            } else if (this.mId == 1) {
                SeslTimePickerSpinnerDelegate seslTimePickerSpinnerDelegate3 = SeslTimePickerSpinnerDelegate.this;
                seslTimePickerSpinnerDelegate3.setMinute(Integer.parseInt(String.valueOf(seslTimePickerSpinnerDelegate3.mPickerTexts[this.mId].getText())));
                SeslTimePickerSpinnerDelegate.this.mPickerTexts[this.mId].selectAll();
            }
        }

        private int convertDigitCharacterToNumber(String str) {
            int i = 0;
            for (char c : SeslTimePickerSpinnerDelegate.DIGIT_CHARACTERS) {
                if (str.equals(Character.toString(c))) {
                    return i % 10;
                }
                i++;
            }
            return -1;
        }
    }
}
