package androidx.picker.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.reflect.feature.SeslCscFeatureReflector;
import androidx.reflect.lunarcalendar.SeslFeatureReflector;
import androidx.reflect.lunarcalendar.SeslLunarDateUtilsReflector;
import androidx.reflect.lunarcalendar.SeslSolarLunarConverterReflector;
import androidx.reflect.view.SeslViewReflector;
import dalvik.system.PathClassLoader;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/* access modifiers changed from: package-private */
public class SeslSimpleMonthView extends View {
    private static final int DEFAULT_MONTH_LINE = 6;
    private static final int DEFAULT_NUM_DAYS = 7;
    private static final String DEFAULT_WEEK_DAY_STRING_FEATURE = "XXXXXXR";
    private static final int DEFAULT_WEEK_START = 1;
    private static final float DIVISOR_FOR_CIRCLE_POSITION_Y = 2.7f;
    private static final int LEAP_MONTH = 1;
    private static final float LEAP_MONTH_WEIGHT = 0.5f;
    private static final int MAX_MONTH_VIEW_ID = 42;
    private static final int MIN_HEIGHT = 10;
    private static final int MONTH_WEIGHT = 100;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final String TAG = "SeslSimpleMonthView";
    private static final String TAG_CSCFEATURE_CALENDAR_SETCOLOROFDAYS = "CscFeature_Calendar_SetColorOfDays";
    private static final int YEAR_WEIGHT = 10000;
    private Paint mAbnormalSelectedDayPaint;
    private final int mAbnormalStartEndDateBackgroundAlpha;
    private final Calendar mCalendar;
    private int mCalendarWidth;
    private Context mContext;
    private int[] mDayColorSet;
    private int mDayNumberDisabledAlpha;
    private Paint mDayNumberPaint;
    private Paint mDayNumberSelectedPaint;
    private int mDayOfWeekStart;
    private int mDaySelectedCircleSize;
    private int mDaySelectedCircleStroke;
    private int mEnabledDayEnd;
    private int mEnabledDayStart;
    private int mEndDay;
    private int mEndMonth;
    private int mEndYear;
    private Paint mHcfEnabledDayNumberPaint;
    private boolean mIsFirstMonth;
    private boolean mIsHcfEnabled;
    private boolean mIsLastMonth;
    private int mIsLeapEndMonth;
    private boolean mIsLeapMonth;
    private int mIsLeapStartMonth;
    private boolean mIsLunar;
    private boolean mIsNextMonthLeap;
    private boolean mIsPrevMonthLeap;
    private boolean mIsRTL;
    private boolean mLockAccessibilityDelegate;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private int mMiniDayNumberTextSize;
    private int mMode;
    private int mMonth;
    private int mNormalTextColor;
    private int mNumCells;
    private int mNumDays;
    private OnDayClickListener mOnDayClickListener;
    private OnDeactivatedDayClickListener mOnDeactivatedDayClickListener;
    private int mPadding;
    private PathClassLoader mPathClassLoader;
    private final int mPrevNextMonthDayNumberAlpha;
    private int mSaturdayTextColor;
    private int mSelectedDay;
    private int mSelectedDayColor;
    private int mSelectedDayNumberTextColor;
    private Object mSolarLunarConverter;
    private int mStartDay;
    private int mStartMonth;
    private int mStartYear;
    private int mSundayTextColor;
    private Calendar mTempDate;
    private final MonthViewTouchHelper mTouchHelper;
    private int mWeekHeight;
    private int mWeekStart;
    private int mYear;

    /* access modifiers changed from: package-private */
    public interface OnDayClickListener {
        void onDayClick(SeslSimpleMonthView seslSimpleMonthView, int i, int i2, int i3);
    }

    /* access modifiers changed from: package-private */
    public interface OnDeactivatedDayClickListener {
        void onDeactivatedDayClick(SeslSimpleMonthView seslSimpleMonthView, int i, int i2, int i3, boolean z, boolean z2);
    }

    private static boolean isValidDayOfWeek(int i) {
        return i >= 1 && i <= 7;
    }

    private static boolean isValidMonth(int i) {
        return i >= 0 && i <= 11;
    }

    SeslSimpleMonthView(Context context) {
        this(context, null);
    }

    SeslSimpleMonthView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    SeslSimpleMonthView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.mDayColorSet = new int[7];
        this.mMode = 0;
        this.mDayOfWeekStart = 0;
        this.mPadding = 0;
        this.mSelectedDay = -1;
        this.mWeekStart = 1;
        this.mNumDays = 7;
        this.mNumCells = this.mNumDays;
        this.mEnabledDayStart = 1;
        this.mEnabledDayEnd = 31;
        this.mIsHcfEnabled = false;
        this.mCalendar = Calendar.getInstance();
        this.mMinDate = Calendar.getInstance();
        this.mMaxDate = Calendar.getInstance();
        this.mTempDate = Calendar.getInstance();
        this.mIsLunar = false;
        this.mIsLeapMonth = false;
        this.mPathClassLoader = null;
        this.mIsFirstMonth = false;
        this.mIsLastMonth = false;
        this.mIsPrevMonthLeap = false;
        this.mIsNextMonthLeap = false;
        this.mContext = context;
        this.mIsRTL = isRTL();
        Resources resources = context.getResources();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        if (typedValue.resourceId != 0) {
            this.mSelectedDayColor = resources.getColor(typedValue.resourceId);
        } else {
            this.mSelectedDayColor = typedValue.data;
        }
        this.mSundayTextColor = resources.getColor(androidx.picker.R.color.sesl_date_picker_sunday_number_text_color);
        this.mSaturdayTextColor = resources.getColor(androidx.picker.R.color.sesl_date_picker_saturday_text_color);
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, androidx.picker.R.styleable.DatePicker, i, 0);
        this.mNormalTextColor = obtainStyledAttributes.getColor(androidx.picker.R.styleable.DatePicker_dayNumberTextColor, resources.getColor(androidx.picker.R.color.sesl_date_picker_normal_day_number_text_color_light));
        this.mSelectedDayNumberTextColor = obtainStyledAttributes.getColor(androidx.picker.R.styleable.DatePicker_selectedDayNumberTextColor, resources.getColor(androidx.picker.R.color.sesl_date_picker_selected_day_number_text_color_light));
        this.mDayNumberDisabledAlpha = obtainStyledAttributes.getInteger(androidx.picker.R.styleable.DatePicker_dayNumberDisabledAlpha, resources.getInteger(androidx.picker.R.integer.sesl_day_number_disabled_alpha_light));
        obtainStyledAttributes.recycle();
        this.mWeekHeight = resources.getDimensionPixelOffset(androidx.picker.R.dimen.sesl_date_picker_calendar_week_height);
        this.mDaySelectedCircleSize = resources.getDimensionPixelSize(androidx.picker.R.dimen.sesl_date_picker_selected_day_circle_radius);
        this.mDaySelectedCircleStroke = resources.getDimensionPixelSize(androidx.picker.R.dimen.sesl_date_picker_selected_day_circle_stroke);
        this.mMiniDayNumberTextSize = resources.getDimensionPixelSize(androidx.picker.R.dimen.sesl_date_picker_day_number_text_size);
        this.mCalendarWidth = resources.getDimensionPixelOffset(androidx.picker.R.dimen.sesl_date_picker_calendar_view_width);
        this.mPadding = resources.getDimensionPixelOffset(androidx.picker.R.dimen.sesl_date_picker_calendar_view_padding);
        this.mTouchHelper = new MonthViewTouchHelper(this);
        ViewCompat.setAccessibilityDelegate(this, this.mTouchHelper);
        setImportantForAccessibility(1);
        this.mLockAccessibilityDelegate = true;
        if (Settings.System.getString(this.mContext.getContentResolver(), "current_sec_active_themepackage") != null) {
            this.mDayNumberDisabledAlpha = resources.getInteger(androidx.picker.R.integer.sesl_day_number_theme_disabled_alpha);
        }
        this.mPrevNextMonthDayNumberAlpha = resources.getInteger(androidx.picker.R.integer.sesl_day_number_theme_disabled_alpha);
        this.mAbnormalStartEndDateBackgroundAlpha = resources.getInteger(androidx.picker.R.integer.sesl_date_picker_abnormal_start_end_date_background_alpha);
        initView();
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIsRTL = isRTL();
        this.mTouchHelper.invalidateRoot();
        Resources resources = this.mContext.getResources();
        this.mWeekHeight = resources.getDimensionPixelOffset(androidx.picker.R.dimen.sesl_date_picker_calendar_week_height);
        this.mDaySelectedCircleSize = resources.getDimensionPixelSize(androidx.picker.R.dimen.sesl_date_picker_selected_day_circle_radius);
        this.mMiniDayNumberTextSize = resources.getDimensionPixelSize(androidx.picker.R.dimen.sesl_date_picker_day_number_text_size);
        initView();
    }

    /* access modifiers changed from: package-private */
    public void setTextColor() {
        String string = SeslCscFeatureReflector.getString(TAG_CSCFEATURE_CALENDAR_SETCOLOROFDAYS, DEFAULT_WEEK_DAY_STRING_FEATURE);
        for (int i = 0; i < this.mNumDays; i++) {
            char charAt = string.charAt(i);
            int i2 = (i + 2) % this.mNumDays;
            if (charAt == 'R') {
                this.mDayColorSet[i2] = this.mSundayTextColor;
            } else if (charAt == 'B') {
                this.mDayColorSet[i2] = this.mSaturdayTextColor;
            } else {
                this.mDayColorSet[i2] = this.mNormalTextColor;
            }
        }
    }

    public void setAccessibilityDelegate(View.AccessibilityDelegate accessibilityDelegate) {
        if (!this.mLockAccessibilityDelegate) {
            super.setAccessibilityDelegate(accessibilityDelegate);
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.mOnDayClickListener = onDayClickListener;
    }

    /* access modifiers changed from: package-private */
    public void setOnDeactivatedDayClickListener(OnDeactivatedDayClickListener onDeactivatedDayClickListener) {
        this.mOnDeactivatedDayClickListener = onDeactivatedDayClickListener;
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            int dayFromLocation = getDayFromLocation(motionEvent.getX(), motionEvent.getY());
            if ((this.mIsFirstMonth && dayFromLocation < this.mEnabledDayStart) || (this.mIsLastMonth && dayFromLocation > this.mEnabledDayEnd)) {
                return true;
            }
            int i = 11;
            if (dayFromLocation <= 0) {
                if (this.mIsLunar) {
                    int i2 = this.mYear;
                    int i3 = this.mMonth - (!this.mIsLeapMonth ? 1 : 0);
                    if (i3 < 0) {
                        i2--;
                    } else {
                        i = i3;
                    }
                    onDeactivatedDayClick(i2, i, getDaysInMonthLunar(i, i2, this.mIsPrevMonthLeap) + dayFromLocation, true);
                } else {
                    Calendar instance = Calendar.getInstance();
                    instance.clear();
                    instance.set(this.mYear, this.mMonth, 1);
                    instance.add(5, dayFromLocation - 1);
                    onDeactivatedDayClick(instance.get(1), instance.get(2), instance.get(5), true);
                }
            } else if (dayFromLocation <= this.mNumCells) {
                onDayClick(this.mYear, this.mMonth, dayFromLocation);
            } else if (this.mIsLunar) {
                int i4 = this.mYear;
                int i5 = this.mMonth + (!this.mIsNextMonthLeap ? 1 : 0);
                if (i5 > 11) {
                    i4++;
                    i5 = 0;
                }
                onDeactivatedDayClick(i4, i5, dayFromLocation - this.mNumCells, false);
            } else {
                Calendar instance2 = Calendar.getInstance();
                instance2.clear();
                instance2.set(this.mYear, this.mMonth, this.mNumCells);
                instance2.add(5, dayFromLocation - this.mNumCells);
                onDeactivatedDayClick(instance2.get(1), instance2.get(2), instance2.get(5), false);
            }
        }
        return true;
    }

    private void initView() {
        this.mDayNumberSelectedPaint = new Paint();
        this.mDayNumberSelectedPaint.setAntiAlias(true);
        this.mDayNumberSelectedPaint.setColor(this.mSelectedDayColor);
        this.mDayNumberSelectedPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayNumberSelectedPaint.setStrokeWidth((float) this.mDaySelectedCircleStroke);
        this.mDayNumberSelectedPaint.setFakeBoldText(true);
        this.mDayNumberSelectedPaint.setStyle(Paint.Style.FILL);
        this.mAbnormalSelectedDayPaint = new Paint(this.mDayNumberSelectedPaint);
        this.mAbnormalSelectedDayPaint.setColor(this.mNormalTextColor);
        this.mAbnormalSelectedDayPaint.setAlpha(this.mAbnormalStartEndDateBackgroundAlpha);
        this.mDayNumberPaint = new Paint();
        this.mDayNumberPaint.setAntiAlias(true);
        this.mDayNumberPaint.setTextSize((float) this.mMiniDayNumberTextSize);
        this.mDayNumberPaint.setTypeface(Typeface.create("sec-roboto-light", 0));
        this.mDayNumberPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayNumberPaint.setStyle(Paint.Style.FILL);
        this.mDayNumberPaint.setFakeBoldText(false);
        this.mHcfEnabledDayNumberPaint = new Paint(this.mDayNumberPaint);
        this.mHcfEnabledDayNumberPaint.setTypeface(Typeface.create("sec-roboto-light", 1));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawDays(canvas);
    }

    /* access modifiers changed from: package-private */
    public void setMonthParams(int i, int i2, int i3, int i4, int i5, int i6, Calendar calendar, Calendar calendar2, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15) {
        Object obj;
        this.mMode = i15;
        if (this.mWeekHeight < 10) {
            this.mWeekHeight = 10;
        }
        this.mSelectedDay = i;
        if (isValidMonth(i2)) {
            this.mMonth = i2;
        }
        this.mYear = i3;
        this.mCalendar.clear();
        this.mCalendar.set(2, this.mMonth);
        this.mCalendar.set(1, this.mYear);
        this.mCalendar.set(5, 1);
        this.mMinDate = calendar;
        this.mMaxDate = calendar2;
        if (!this.mIsLunar || (obj = this.mSolarLunarConverter) == null) {
            this.mDayOfWeekStart = this.mCalendar.get(7);
            this.mNumCells = getDaysInMonth(this.mMonth, this.mYear);
        } else {
            SeslSolarLunarConverterReflector.convertLunarToSolar(this.mPathClassLoader, obj, this.mYear, this.mMonth, 1, this.mIsLeapMonth);
            this.mDayOfWeekStart = SeslSolarLunarConverterReflector.getWeekday(this.mPathClassLoader, this.mSolarLunarConverter, SeslSolarLunarConverterReflector.getYear(this.mPathClassLoader, this.mSolarLunarConverter), SeslSolarLunarConverterReflector.getMonth(this.mPathClassLoader, this.mSolarLunarConverter), SeslSolarLunarConverterReflector.getDay(this.mPathClassLoader, this.mSolarLunarConverter)) + 1;
            this.mNumCells = getDaysInMonthLunar(this.mMonth, this.mYear, this.mIsLeapMonth);
        }
        if (isValidDayOfWeek(i4)) {
            this.mWeekStart = i4;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        int i16 = (this.mMonth == calendar.get(2) && this.mYear == calendar.get(1)) ? calendar.get(5) : i5;
        int i17 = (this.mMonth == calendar2.get(2) && this.mYear == calendar2.get(1)) ? calendar2.get(5) : i6;
        if (i16 > 0 && i17 < 32) {
            this.mEnabledDayStart = i16;
        }
        if (i17 > 0 && i17 < 32 && i17 >= i16) {
            this.mEnabledDayEnd = i17;
        }
        this.mTouchHelper.invalidateRoot();
        this.mStartYear = i7;
        this.mStartMonth = i8;
        this.mStartDay = i9;
        this.mIsLeapStartMonth = i10;
        this.mEndYear = i11;
        this.mEndMonth = i12;
        this.mEndDay = i13;
        this.mIsLeapEndMonth = i14;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getDaysInMonthLunar(int i, int i2, boolean z) {
        int daysInMonth = getDaysInMonth(i, i2);
        Object obj = this.mSolarLunarConverter;
        if (obj != null) {
            return SeslSolarLunarConverterReflector.getDayLengthOf(this.mPathClassLoader, obj, i2, i, z);
        }
        Log.e(TAG, "getDaysInMonthLunar, mSolarLunarConverter is null");
        return daysInMonth;
    }

    private static int getDaysInMonth(int i, int i2) {
        switch (i) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                if (i2 % 4 != 0) {
                    return 28;
                }
                if (i2 % 100 != 0 || i2 % 400 == 0) {
                    return 29;
                }
                return 28;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(makeMeasureSpec(i, this.mCalendarWidth), i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            this.mTouchHelper.invalidateRoot();
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    private int makeMeasureSpec(int i, int i2) {
        if (i2 == -1) {
            return i;
        }
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE) {
            this.mCalendarWidth = Math.min(size, i2);
            return View.MeasureSpec.makeMeasureSpec(this.mCalendarWidth, 1073741824);
        } else if (mode == 0) {
            return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
        } else {
            if (mode == 1073741824) {
                this.mCalendarWidth = size;
                return i;
            }
            throw new IllegalArgumentException("Unknown measure mode: " + mode);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mTouchHelper.invalidateRoot();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:93:0x017b, code lost:
        if (r34.mMode == 1) goto L_0x017d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x025e  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x02e0  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x03a1  */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x03ca  */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x0418  */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x0420  */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x0434  */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x051f  */
    /* JADX WARNING: Removed duplicated region for block: B:224:0x0567  */
    /* JADX WARNING: Removed duplicated region for block: B:250:? A[ADDED_TO_REGION, ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00cb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawDays(android.graphics.Canvas r35) {
        /*
        // Method dump skipped, instructions count: 1465
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.picker.widget.SeslSimpleMonthView.drawDays(android.graphics.Canvas):void");
    }

    private boolean isPrevMonthStartMonth() {
        if (!this.mIsLunar) {
            return (this.mYear == this.mStartYear && this.mMonth == this.mStartMonth + 1) || (this.mYear == this.mStartYear + 1 && this.mMonth == 0 && this.mStartMonth == 11);
        }
        float f = (float) this.mMonth;
        float f2 = (float) this.mStartMonth;
        if (this.mIsLeapMonth) {
            f += LEAP_MONTH_WEIGHT;
        }
        if (this.mIsLeapStartMonth == 1) {
            f2 += LEAP_MONTH_WEIGHT;
        }
        float f3 = f - f2;
        if (this.mYear != this.mStartYear || (f3 >= 1.0f && (f3 != 1.0f || this.mIsPrevMonthLeap))) {
            if (this.mYear != this.mStartYear + 1) {
                return false;
            }
            float f4 = f3 + 12.0f;
            if (f4 >= 1.0f && (f4 != 1.0f || this.mIsPrevMonthLeap)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNextMonthEndMonth() {
        if (!this.mIsLunar) {
            return (this.mYear == this.mEndYear && this.mMonth == this.mEndMonth - 1) || (this.mYear == this.mEndYear - 1 && this.mMonth == 11 && this.mEndMonth == 0);
        }
        float f = (float) this.mMonth;
        float f2 = (float) this.mEndMonth;
        if (this.mIsLeapMonth) {
            f += LEAP_MONTH_WEIGHT;
        }
        if (this.mIsLeapEndMonth == 1) {
            f2 += LEAP_MONTH_WEIGHT;
        }
        float f3 = f2 - f;
        if (this.mYear != this.mEndYear || (f3 >= 1.0f && (f3 != 1.0f || this.mIsNextMonthLeap))) {
            if (this.mYear != this.mEndYear - 1) {
                return false;
            }
            float f4 = f3 + 12.0f;
            if (f4 >= 1.0f && (f4 != 1.0f || this.mIsNextMonthLeap)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int findDayOffset() {
        int i = this.mDayOfWeekStart;
        if (i < this.mWeekStart) {
            i += this.mNumDays;
        }
        return i - this.mWeekStart;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getDayFromLocation(float f, float f2) {
        int i = this.mPadding;
        if (this.mIsRTL) {
            f = ((float) this.mCalendarWidth) - f;
        }
        float f3 = (float) i;
        if (f < f3) {
            return -1;
        }
        int i2 = this.mCalendarWidth;
        if (f > ((float) (this.mPadding + i2))) {
            return -1;
        }
        return (((int) (((f - f3) * ((float) this.mNumDays)) / ((float) i2))) - findDayOffset()) + 1 + ((((int) f2) / this.mWeekHeight) * this.mNumDays);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onDayClick(int i, int i2, int i3) {
        if (this.mOnDayClickListener != null) {
            playSoundEffect(0);
            this.mOnDayClickListener.onDayClick(this, i, i2, i3);
        }
        this.mTouchHelper.sendEventForVirtualView(i3, 1);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onDeactivatedDayClick(int i, int i2, int i3, boolean z) {
        if (!this.mIsLunar) {
            this.mTempDate.clear();
            this.mTempDate.set(i, i2, i3);
            if (z) {
                Calendar instance = Calendar.getInstance();
                instance.clear();
                instance.set(this.mMinDate.get(1), this.mMinDate.get(2), this.mMinDate.get(5));
                if (this.mTempDate.before(instance)) {
                    return;
                }
            } else if (this.mTempDate.after(this.mMaxDate)) {
                return;
            }
        }
        if (this.mOnDeactivatedDayClickListener != null) {
            playSoundEffect(0);
            this.mOnDeactivatedDayClickListener.onDeactivatedDayClick(this, i, i2, i3, this.mIsLeapMonth, z);
        }
        this.mTouchHelper.sendEventForVirtualView(i3, 1);
    }

    /* access modifiers changed from: package-private */
    public void clearAccessibilityFocus() {
        this.mTouchHelper.clearFocusedVirtualView();
    }

    /* access modifiers changed from: private */
    public class MonthViewTouchHelper extends ExploreByTouchHelper {
        private final Calendar mTempCalendar = Calendar.getInstance();
        private final Rect mTempRect = new Rect();

        public MonthViewTouchHelper(View view) {
            super(view);
        }

        public void setFocusedVirtualView(int i) {
            getAccessibilityNodeProvider(SeslSimpleMonthView.this).performAction(i, 64, null);
        }

        public void clearFocusedVirtualView() {
            int focusedVirtualView = getFocusedVirtualView();
            if (focusedVirtualView != Integer.MIN_VALUE) {
                getAccessibilityNodeProvider(SeslSimpleMonthView.this).performAction(focusedVirtualView, 128, null);
            }
        }

        /* access modifiers changed from: protected */
        @Override // androidx.customview.widget.ExploreByTouchHelper
        public int getVirtualViewAt(float f, float f2) {
            int dayFromLocation = SeslSimpleMonthView.this.getDayFromLocation(f, f2);
            if (SeslSimpleMonthView.this.mIsFirstMonth && dayFromLocation < SeslSimpleMonthView.this.mEnabledDayStart) {
                return Integer.MIN_VALUE;
            }
            if (!SeslSimpleMonthView.this.mIsLastMonth || dayFromLocation <= SeslSimpleMonthView.this.mEnabledDayEnd) {
                return dayFromLocation + SeslSimpleMonthView.this.findDayOffset();
            }
            return Integer.MIN_VALUE;
        }

        /* access modifiers changed from: protected */
        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void getVisibleVirtualViews(List<Integer> list) {
            int findDayOffset = SeslSimpleMonthView.this.findDayOffset();
            for (int i = 1; i <= 42; i++) {
                int i2 = i - findDayOffset;
                if ((!SeslSimpleMonthView.this.mIsFirstMonth || i2 >= SeslSimpleMonthView.this.mEnabledDayStart) && (!SeslSimpleMonthView.this.mIsLastMonth || i2 <= SeslSimpleMonthView.this.mEnabledDayEnd)) {
                    list.add(Integer.valueOf(i));
                }
            }
        }

        /* access modifiers changed from: protected */
        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getItemDescription(i - SeslSimpleMonthView.this.findDayOffset()));
        }

        /* access modifiers changed from: protected */
        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int findDayOffset = i - SeslSimpleMonthView.this.findDayOffset();
            getItemBounds(findDayOffset, this.mTempRect);
            accessibilityNodeInfoCompat.setContentDescription(getItemDescription(findDayOffset));
            accessibilityNodeInfoCompat.setBoundsInParent(this.mTempRect);
            accessibilityNodeInfoCompat.addAction(16);
            if (SeslSimpleMonthView.this.mSelectedDay != -1 && findDayOffset == SeslSimpleMonthView.this.mSelectedDay) {
                accessibilityNodeInfoCompat.addAction(4);
                accessibilityNodeInfoCompat.setClickable(true);
                accessibilityNodeInfoCompat.setCheckable(true);
                accessibilityNodeInfoCompat.setChecked(true);
            }
        }

        /* access modifiers changed from: protected */
        @Override // androidx.customview.widget.ExploreByTouchHelper
        public boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            int findDayOffset = i - SeslSimpleMonthView.this.findDayOffset();
            if ((SeslSimpleMonthView.this.mIsFirstMonth && findDayOffset < SeslSimpleMonthView.this.mEnabledDayStart) || (SeslSimpleMonthView.this.mIsLastMonth && findDayOffset > SeslSimpleMonthView.this.mEnabledDayEnd)) {
                return true;
            }
            if (findDayOffset <= 0) {
                if (SeslSimpleMonthView.this.mIsLunar) {
                    int i3 = SeslSimpleMonthView.this.mMonth - (!SeslSimpleMonthView.this.mIsLeapMonth ? 1 : 0);
                    if (i3 < 0) {
                        SeslSimpleMonthView seslSimpleMonthView = SeslSimpleMonthView.this;
                        int daysInMonthLunar = seslSimpleMonthView.getDaysInMonthLunar(11, seslSimpleMonthView.mYear - 1, SeslSimpleMonthView.this.mIsLeapMonth);
                        SeslSimpleMonthView seslSimpleMonthView2 = SeslSimpleMonthView.this;
                        seslSimpleMonthView2.onDeactivatedDayClick(seslSimpleMonthView2.mYear - 1, i3, daysInMonthLunar + findDayOffset, true);
                    } else {
                        SeslSimpleMonthView seslSimpleMonthView3 = SeslSimpleMonthView.this;
                        int daysInMonthLunar2 = seslSimpleMonthView3.getDaysInMonthLunar(i3, seslSimpleMonthView3.mYear, SeslSimpleMonthView.this.mIsLeapMonth);
                        SeslSimpleMonthView seslSimpleMonthView4 = SeslSimpleMonthView.this;
                        seslSimpleMonthView4.onDeactivatedDayClick(seslSimpleMonthView4.mYear, i3, daysInMonthLunar2 + findDayOffset, true);
                    }
                } else {
                    Calendar instance = Calendar.getInstance();
                    instance.clear();
                    instance.set(SeslSimpleMonthView.this.mYear, SeslSimpleMonthView.this.mMonth, 1);
                    instance.add(5, findDayOffset - 1);
                    SeslSimpleMonthView.this.onDeactivatedDayClick(instance.get(1), instance.get(2), instance.get(5), true);
                }
            } else if (findDayOffset <= SeslSimpleMonthView.this.mNumCells) {
                SeslSimpleMonthView seslSimpleMonthView5 = SeslSimpleMonthView.this;
                seslSimpleMonthView5.onDayClick(seslSimpleMonthView5.mYear, SeslSimpleMonthView.this.mMonth, findDayOffset);
            } else if (SeslSimpleMonthView.this.mIsLunar) {
                int i4 = SeslSimpleMonthView.this.mMonth + 1;
                if (i4 > 11) {
                    SeslSimpleMonthView seslSimpleMonthView6 = SeslSimpleMonthView.this;
                    seslSimpleMonthView6.onDeactivatedDayClick(seslSimpleMonthView6.mYear + 1, 0, findDayOffset - SeslSimpleMonthView.this.mNumCells, false);
                } else {
                    SeslSimpleMonthView seslSimpleMonthView7 = SeslSimpleMonthView.this;
                    seslSimpleMonthView7.onDeactivatedDayClick(seslSimpleMonthView7.mYear, i4, findDayOffset - SeslSimpleMonthView.this.mNumCells, false);
                }
            } else {
                Calendar instance2 = Calendar.getInstance();
                instance2.clear();
                instance2.set(SeslSimpleMonthView.this.mYear, SeslSimpleMonthView.this.mMonth, SeslSimpleMonthView.this.mNumCells);
                instance2.add(5, findDayOffset - SeslSimpleMonthView.this.mNumCells);
                SeslSimpleMonthView.this.onDeactivatedDayClick(instance2.get(1), instance2.get(2), instance2.get(5), false);
            }
            return true;
        }

        private void getItemBounds(int i, Rect rect) {
            int i2 = SeslSimpleMonthView.this.mPadding;
            int i3 = SeslSimpleMonthView.this.mWeekHeight;
            int i4 = SeslSimpleMonthView.this.mCalendarWidth / SeslSimpleMonthView.this.mNumDays;
            int findDayOffset = (i - 1) + SeslSimpleMonthView.this.findDayOffset();
            int i5 = findDayOffset / SeslSimpleMonthView.this.mNumDays;
            int i6 = i2 + ((findDayOffset % SeslSimpleMonthView.this.mNumDays) * i4);
            int i7 = ((int) (SeslSimpleMonthView.this.mContext.getResources().getDisplayMetrics().density * -1.0f)) + (i5 * i3);
            rect.set(i6, i7, i4 + i6, i3 + i7);
        }

        private CharSequence getItemDescription(int i) {
            this.mTempCalendar.set(SeslSimpleMonthView.this.mYear, SeslSimpleMonthView.this.mMonth, i);
            String formatDateTime = DateUtils.formatDateTime(SeslSimpleMonthView.this.mContext, this.mTempCalendar.getTimeInMillis(), 22);
            if (!SeslSimpleMonthView.this.mIsLunar || SeslSimpleMonthView.this.mPathClassLoader == null) {
                return formatDateTime;
            }
            int i2 = SeslSimpleMonthView.this.mYear;
            int i3 = SeslSimpleMonthView.this.mMonth;
            boolean z = SeslSimpleMonthView.this.mIsLeapMonth;
            if (i <= 0) {
                i3 = SeslSimpleMonthView.this.mMonth - (!SeslSimpleMonthView.this.mIsLeapMonth ? 1 : 0);
                z = SeslSimpleMonthView.this.mIsPrevMonthLeap;
                if (i3 < 0) {
                    i2--;
                    i3 = 11;
                }
                i += SeslSimpleMonthView.this.getDaysInMonthLunar(i3, i2, z);
            } else if (i > SeslSimpleMonthView.this.mNumCells) {
                i3 = SeslSimpleMonthView.this.mMonth + (!SeslSimpleMonthView.this.mIsNextMonthLeap ? 1 : 0);
                z = SeslSimpleMonthView.this.mIsNextMonthLeap;
                if (i3 > 11) {
                    i2++;
                    i3 = 0;
                }
                i -= SeslSimpleMonthView.this.mNumCells;
            }
            SeslSolarLunarConverterReflector.convertLunarToSolar(SeslSimpleMonthView.this.mPathClassLoader, SeslSimpleMonthView.this.mSolarLunarConverter, i2, i3, i, z);
            int year = SeslSolarLunarConverterReflector.getYear(SeslSimpleMonthView.this.mPathClassLoader, SeslSimpleMonthView.this.mSolarLunarConverter);
            int month = SeslSolarLunarConverterReflector.getMonth(SeslSimpleMonthView.this.mPathClassLoader, SeslSimpleMonthView.this.mSolarLunarConverter);
            int day = SeslSolarLunarConverterReflector.getDay(SeslSimpleMonthView.this.mPathClassLoader, SeslSimpleMonthView.this.mSolarLunarConverter);
            Calendar instance = Calendar.getInstance();
            instance.set(year, month, day);
            return SeslLunarDateUtilsReflector.buildLunarDateString(SeslSimpleMonthView.this.mPathClassLoader, instance, SeslSimpleMonthView.this.getContext());
        }
    }

    /* access modifiers changed from: package-private */
    public int getWeekStart() {
        return this.mWeekStart;
    }

    /* access modifiers changed from: package-private */
    public int getNumDays() {
        return this.mNumDays;
    }

    /* access modifiers changed from: package-private */
    public void setStartDate(Calendar calendar, int i) {
        this.mStartYear = calendar.get(1);
        this.mStartMonth = calendar.get(2);
        this.mStartDay = calendar.get(5);
        this.mIsLeapStartMonth = i;
    }

    /* access modifiers changed from: package-private */
    public void setEndDate(Calendar calendar, int i) {
        this.mEndYear = calendar.get(1);
        this.mEndMonth = calendar.get(2);
        this.mEndDay = calendar.get(5);
        this.mIsLeapEndMonth = i;
    }

    private boolean isRTL() {
        Locale locale = Locale.getDefault();
        if ("ur".equals(locale.getLanguage())) {
            return false;
        }
        byte directionality = Character.getDirectionality(locale.getDisplayName(locale).charAt(0));
        if (directionality == 1 || directionality == 2) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setLunar(boolean z, boolean z2, PathClassLoader pathClassLoader) {
        this.mIsLunar = z;
        this.mIsLeapMonth = z2;
        if (this.mIsLunar && this.mSolarLunarConverter == null) {
            this.mPathClassLoader = pathClassLoader;
            this.mSolarLunarConverter = SeslFeatureReflector.getSolarLunarConverter(this.mPathClassLoader);
        }
    }

    /* access modifiers changed from: package-private */
    public void setFirstMonth() {
        this.mIsFirstMonth = true;
    }

    /* access modifiers changed from: package-private */
    public void setLastMonth() {
        this.mIsLastMonth = true;
    }

    /* access modifiers changed from: package-private */
    public void setPrevMonthLeap() {
        this.mIsPrevMonthLeap = true;
    }

    /* access modifiers changed from: package-private */
    public void setNextMonthLeap() {
        this.mIsNextMonthLeap = true;
    }

    private boolean isHighContrastFontEnabled() {
        return SeslViewReflector.isHighContrastTextEnabled(this);
    }
}
