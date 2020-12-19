package androidx.picker.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
import androidx.core.content.res.ResourcesCompat;
import androidx.picker.R;
import androidx.picker.widget.SeslDatePickerSpinnerLayout;
import androidx.picker.widget.SeslSimpleMonthView;
import androidx.reflect.feature.SeslCscFeatureReflector;
import androidx.reflect.feature.SeslFloatingFeatureReflector;
import androidx.reflect.lunarcalendar.SeslFeatureReflector;
import androidx.reflect.lunarcalendar.SeslSolarLunarTablesReflector;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslHoverPopupWindowReflector;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import dalvik.system.PathClassLoader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

public class SeslDatePicker extends LinearLayout implements SeslSimpleMonthView.OnDayClickListener, View.OnClickListener, View.OnLongClickListener, SeslSimpleMonthView.OnDeactivatedDayClickListener {
    public static final int DATE_MODE_END = 2;
    public static final int DATE_MODE_NONE = 0;
    public static final int DATE_MODE_START = 1;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final int DEFAULT_MONTH_PER_YEAR = 12;
    private static final int DEFAULT_START_YEAR = 1902;
    private static final int LEAP_MONTH = 1;
    private static final float MAX_FONT_SCALE = 1.2f;
    private static final int MESSAGE_CALENDAR_HEADER_MONTH_BUTTON_SET = 1001;
    private static final int MESSAGE_CALENDAR_HEADER_TEXT_VALUE_SET = 1000;
    private static final int NOT_LEAP_MONTH = 0;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final String TAG = "SeslDatePicker";
    private static final String TAG_CSCFEATURE_CALENDAR_SETCOLOROFDAYS = "CscFeature_Calendar_SetColorOfDays";
    private static final int USE_LOCALE = 0;
    public static final int VIEW_TYPE_CALENDAR = 0;
    public static final int VIEW_TYPE_SPINNER = 1;
    private static PackageManager mPackageManager;
    private ViewAnimator mAnimator;
    private int mBackgroundBorderlessResId;
    private final View.OnFocusChangeListener mBtnFocusChangeListener;
    private RelativeLayout mCalendarHeader;
    private View.OnClickListener mCalendarHeaderClickListener;
    private RelativeLayout mCalendarHeaderLayout;
    private int mCalendarHeaderLayoutHeight;
    private TextView mCalendarHeaderText;
    private CalendarPagerAdapter mCalendarPagerAdapter;
    private int mCalendarViewMargin;
    private ViewPager mCalendarViewPager;
    private int mCalendarViewPagerHeight;
    private int mCalendarViewPagerWidth;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private FrameLayout mContentFrame;
    private Context mContext;
    private Calendar mCurrentDate;
    private Locale mCurrentLocale;
    private int mCurrentPosition;
    private int mCurrentViewType;
    private RelativeLayout mCustomButtonLayout;
    private View mCustomButtonView;
    private int mDatePickerHeight;
    private LinearLayout mDatePickerLayout;
    private SimpleDateFormat mDayFormatter;
    private LinearLayout mDayOfTheWeekLayout;
    private int mDayOfTheWeekLayoutHeight;
    private int mDayOfTheWeekLayoutWidth;
    private DayOfTheWeekView mDayOfTheWeekView;
    private int mDialogPaddingVertical;
    private Window mDialogWindow;
    private Calendar mEndDate;
    private View mFirstBlankSpace;
    private int mFirstBlankSpaceHeight;
    private int mFirstDayOfWeek;
    private Handler mHandler;
    private boolean mIsConfigurationChanged;
    private boolean mIsCustomButtonSeparate;
    private boolean mIsEnabled;
    private boolean mIsFarsiLanguage;
    private boolean mIsFirstMeasure;
    private boolean mIsFromSetLunar;
    private boolean mIsInDialog;
    private int mIsLeapEndMonth;
    private boolean mIsLeapMonth;
    private int mIsLeapStartMonth;
    private boolean mIsLunar;
    private boolean mIsLunarSupported;
    private boolean mIsRTL;
    private boolean mIsSimplifiedChinese;
    private boolean mLunarChanged;
    private int mLunarCurrentDay;
    private int mLunarCurrentMonth;
    private int mLunarCurrentYear;
    private int mLunarEndDay;
    private int mLunarEndMonth;
    private int mLunarEndYear;
    private int mLunarStartDay;
    private int mLunarStartMonth;
    private int mLunarStartYear;
    private Calendar mMaxDate;
    private int mMeasureSpecHeight;
    private Calendar mMinDate;
    private int mMode;
    private View.OnKeyListener mMonthBtnKeyListener;
    private View.OnTouchListener mMonthBtnTouchListener;
    private ImageButton mNextButton;
    private int mNumDays;
    private int mOldCalendarViewPagerWidth;
    private int mOldSelectedDay;
    private OnDateChangedListener mOnDateChangedListener;
    private OnViewTypeChangedListener mOnViewTypeChangedListener;
    private int mPadding;
    PathClassLoader mPathClassLoader;
    private int mPositionCount;
    private ImageButton mPrevButton;
    private View mSecondBlankSpace;
    private int mSecondBlankSpaceHeight;
    private Object mSolarLunarTables;
    private SeslDatePickerSpinnerLayout mSpinnerLayout;
    private Calendar mStartDate;
    private Calendar mTempDate;
    private Calendar mTempMinMaxDate;
    private int[] mTotalMonthCountWithLeap;
    private ValidationCallback mValidationCallback;
    private int mWeekStart;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DateMode {
    }

    public interface OnDateChangedListener {
        void onDateChanged(SeslDatePicker seslDatePicker, int i, int i2, int i3);
    }

    public interface OnEditTextModeChangedListener {
        void onEditTextModeChanged(SeslDatePicker seslDatePicker, boolean z);
    }

    public interface OnViewTypeChangedListener {
        void onViewTypeChanged(SeslDatePicker seslDatePicker);
    }

    public interface ValidationCallback {
        void onValidationChanged(boolean z);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void debugLog(String str) {
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setPrevButtonProperties(float f, boolean z) {
        this.mPrevButton.setAlpha(f);
        if (z) {
            this.mPrevButton.setBackgroundResource(this.mBackgroundBorderlessResId);
            this.mPrevButton.setEnabled(true);
            this.mPrevButton.setFocusable(true);
            return;
        }
        this.mPrevButton.setBackground(null);
        this.mPrevButton.setEnabled(false);
        this.mPrevButton.setFocusable(false);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setNextButtonProperties(float f, boolean z) {
        this.mNextButton.setAlpha(f);
        if (z) {
            this.mNextButton.setBackgroundResource(this.mBackgroundBorderlessResId);
            this.mNextButton.setEnabled(true);
            this.mNextButton.setFocusable(true);
            return;
        }
        this.mNextButton.setBackground(null);
        this.mNextButton.setEnabled(false);
        this.mNextButton.setFocusable(false);
    }

    public SeslDatePicker(Context context) {
        this(context, null);
    }

    public SeslDatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public SeslDatePicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslDatePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        boolean z = false;
        this.mIsConfigurationChanged = false;
        this.mIsFirstMeasure = true;
        this.mIsEnabled = true;
        this.mCurrentViewType = -1;
        this.mOldSelectedDay = -1;
        this.mPadding = 0;
        this.mBackgroundBorderlessResId = -1;
        this.mMode = 0;
        this.mFirstDayOfWeek = 0;
        this.mIsLunarSupported = false;
        this.mIsLunar = false;
        this.mIsLeapMonth = false;
        this.mIsFromSetLunar = false;
        this.mLunarChanged = false;
        this.mIsCustomButtonSeparate = false;
        this.mPathClassLoader = null;
        this.mBtnFocusChangeListener = new View.OnFocusChangeListener() {
            /* class androidx.picker.widget.SeslDatePicker.AnonymousClass1 */

            public void onFocusChange(View view, boolean z) {
                if (!z) {
                    SeslDatePicker.this.removeAllCallbacks();
                }
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper()) {
            /* class androidx.picker.widget.SeslDatePicker.AnonymousClass2 */

            public void handleMessage(Message message) {
                super.handleMessage(message);
                int i = message.what;
                if (i != 1000) {
                    if (i == 1001) {
                        if (SeslDatePicker.this.mCurrentViewType == 1) {
                            SeslDatePicker.this.setPrevButtonProperties(0.0f, false);
                            SeslDatePicker.this.setNextButtonProperties(0.0f, false);
                            int field_TYPE_NONE = SeslHoverPopupWindowReflector.getField_TYPE_NONE();
                            if (field_TYPE_NONE != -1) {
                                SeslViewReflector.semSetHoverPopupType(SeslDatePicker.this.mPrevButton, field_TYPE_NONE);
                                SeslViewReflector.semSetHoverPopupType(SeslDatePicker.this.mNextButton, field_TYPE_NONE);
                                return;
                            }
                            return;
                        }
                        int field_TYPE_TOOLTIP = SeslHoverPopupWindowReflector.getField_TYPE_TOOLTIP();
                        if (field_TYPE_TOOLTIP != -1) {
                            SeslViewReflector.semSetHoverPopupType(SeslDatePicker.this.mPrevButton, field_TYPE_TOOLTIP);
                            SeslViewReflector.semSetHoverPopupType(SeslDatePicker.this.mNextButton, field_TYPE_TOOLTIP);
                        }
                        if (SeslDatePicker.this.mCurrentPosition > 0 && SeslDatePicker.this.mCurrentPosition < SeslDatePicker.this.mPositionCount - 1) {
                            SeslDatePicker.this.setPrevButtonProperties(1.0f, true);
                            SeslDatePicker.this.setNextButtonProperties(1.0f, true);
                        } else if (SeslDatePicker.this.mPositionCount == 1) {
                            SeslDatePicker.this.setPrevButtonProperties(0.4f, false);
                            SeslDatePicker.this.setNextButtonProperties(0.4f, false);
                            SeslDatePicker.this.removeAllCallbacks();
                        } else if (SeslDatePicker.this.mCurrentPosition == 0) {
                            SeslDatePicker.this.setPrevButtonProperties(0.4f, false);
                            SeslDatePicker.this.setNextButtonProperties(1.0f, true);
                            SeslDatePicker.this.removeAllCallbacks();
                        } else if (SeslDatePicker.this.mCurrentPosition == SeslDatePicker.this.mPositionCount - 1) {
                            SeslDatePicker.this.setPrevButtonProperties(1.0f, true);
                            SeslDatePicker.this.setNextButtonProperties(0.4f, false);
                            SeslDatePicker.this.removeAllCallbacks();
                        }
                    }
                } else if (SeslDatePicker.this.mTempDate.get(1) <= SeslDatePicker.this.getMaxYear() && SeslDatePicker.this.mTempDate.get(1) >= SeslDatePicker.this.getMinYear()) {
                    SeslDatePicker seslDatePicker = SeslDatePicker.this;
                    String monthAndYearString = seslDatePicker.getMonthAndYearString(seslDatePicker.mTempDate);
                    SeslDatePicker.this.mCalendarHeaderText.setText(monthAndYearString);
                    String string = SeslDatePicker.this.mContext.getString(SeslDatePicker.this.mCurrentViewType == 0 ? R.string.sesl_date_picker_switch_to_wheel_description : R.string.sesl_date_picker_switch_to_calendar_description);
                    TextView textView = SeslDatePicker.this.mCalendarHeaderText;
                    textView.setContentDescription(monthAndYearString + ", " + string);
                }
            }
        };
        this.mMonthBtnTouchListener = new View.OnTouchListener() {
            /* class androidx.picker.widget.SeslDatePicker.AnonymousClass3 */

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1 && motionEvent.getAction() != 3) {
                    return false;
                }
                SeslDatePicker.this.removeAllCallbacks();
                return false;
            }
        };
        this.mMonthBtnKeyListener = new View.OnKeyListener() {
            /* class androidx.picker.widget.SeslDatePicker.AnonymousClass4 */

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (SeslDatePicker.this.mIsRTL) {
                    SeslDatePicker.this.mIsConfigurationChanged = false;
                }
                if (keyEvent.getAction() == 1 || keyEvent.getAction() == 3) {
                    SeslDatePicker.this.removeAllCallbacks();
                }
                return false;
            }
        };
        this.mCalendarHeaderClickListener = new View.OnClickListener() {
            /* class androidx.picker.widget.SeslDatePicker.AnonymousClass5 */

            public void onClick(View view) {
                SeslDatePicker seslDatePicker = SeslDatePicker.this;
                seslDatePicker.setCurrentViewType((seslDatePicker.mCurrentViewType + 1) % 2);
            }
        };
        this.mContext = context;
        this.mCurrentLocale = Locale.getDefault();
        this.mIsRTL = isRTL();
        this.mIsFarsiLanguage = isFarsiLanguage();
        this.mIsSimplifiedChinese = isSimplifiedChinese();
        if (this.mIsSimplifiedChinese) {
            this.mDayFormatter = new SimpleDateFormat("EEEEE", this.mCurrentLocale);
        } else {
            this.mDayFormatter = new SimpleDateFormat("EEE", this.mCurrentLocale);
        }
        this.mMinDate = getCalendarForLocale(this.mMinDate, this.mCurrentLocale);
        this.mMaxDate = getCalendarForLocale(this.mMaxDate, this.mCurrentLocale);
        this.mTempMinMaxDate = getCalendarForLocale(this.mMaxDate, this.mCurrentLocale);
        this.mCurrentDate = getCalendarForLocale(this.mCurrentDate, this.mCurrentLocale);
        this.mTempDate = getCalendarForLocale(this.mCurrentDate, this.mCurrentLocale);
        Resources resources = getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DatePicker, i, i2);
        this.mMinDate.set(obtainStyledAttributes.getInt(R.styleable.DatePicker_android_startYear, DEFAULT_START_YEAR), 0, 1);
        this.mMaxDate.set(obtainStyledAttributes.getInt(R.styleable.DatePicker_android_endYear, DEFAULT_END_YEAR), 11, 31);
        ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(Build.VERSION.SDK_INT >= 23 ? R.layout.sesl_date_picker : R.layout.sesl_date_picker_legacy, (ViewGroup) this, true);
        int i3 = obtainStyledAttributes.getInt(R.styleable.DatePicker_android_firstDayOfWeek, 0);
        if (i3 != 0) {
            setFirstDayOfWeek(i3);
        }
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.DatePicker, i, i2);
        this.mDayOfTheWeekView = new DayOfTheWeekView(this.mContext, obtainStyledAttributes2);
        int color = obtainStyledAttributes2.getColor(R.styleable.DatePicker_dayNumberTextColor, resources.getColor(R.color.sesl_date_picker_normal_day_number_text_color_light));
        int color2 = obtainStyledAttributes2.getColor(R.styleable.DatePicker_buttonTintColor, resources.getColor(R.color.sesl_date_picker_button_tint_color_light));
        obtainStyledAttributes2.recycle();
        this.mCalendarPagerAdapter = new CalendarPagerAdapter();
        this.mCalendarViewPager = (ViewPager) findViewById(R.id.sesl_date_picker_calendar);
        this.mCalendarViewPager.setAdapter(this.mCalendarPagerAdapter);
        this.mCalendarViewPager.setOnPageChangeListener(new CalendarPageChangeListener());
        this.mCalendarViewPager.seslSetSupportedMouseWheelEvent(true);
        this.mCalendarViewPager.canSupportLayoutDirectionForDatePicker(true);
        this.mPadding = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_view_padding);
        this.mCalendarHeader = (RelativeLayout) findViewById(R.id.sesl_date_picker_calendar_header);
        this.mCalendarHeaderText = (TextView) findViewById(R.id.sesl_date_picker_calendar_header_text);
        this.mCalendarHeaderText.setTextColor(color);
        this.mStartDate = getCalendarForLocale(this.mCurrentDate, this.mCurrentLocale);
        this.mEndDate = getCalendarForLocale(this.mCurrentDate, this.mCurrentLocale);
        this.mAnimator = (ViewAnimator) findViewById(R.id.sesl_date_picker_view_animator);
        this.mSpinnerLayout = (SeslDatePickerSpinnerLayout) findViewById(R.id.sesl_date_picker_spinner_view);
        this.mSpinnerLayout.setOnSpinnerDateChangedListener(this, new SeslDatePickerSpinnerLayout.OnSpinnerDateChangedListener() {
            /* class androidx.picker.widget.SeslDatePicker.AnonymousClass6 */

            @Override // androidx.picker.widget.SeslDatePickerSpinnerLayout.OnSpinnerDateChangedListener
            public void onDateChanged(SeslDatePickerSpinnerLayout seslDatePickerSpinnerLayout, int i, int i2, int i3) {
                SeslDatePicker.this.mCurrentDate.set(1, i);
                SeslDatePicker.this.mCurrentDate.set(2, i2);
                SeslDatePicker.this.mCurrentDate.set(5, i3);
                if (SeslDatePicker.this.mIsLunar) {
                    SeslDatePicker.this.mLunarCurrentYear = i;
                    SeslDatePicker.this.mLunarCurrentMonth = i2;
                    SeslDatePicker.this.mLunarCurrentDay = i3;
                }
                int i4 = SeslDatePicker.this.mMode;
                if (i4 == 1) {
                    SeslDatePicker seslDatePicker = SeslDatePicker.this;
                    seslDatePicker.clearCalendar(seslDatePicker.mStartDate, i, i2, i3);
                    if (SeslDatePicker.this.mIsLunar) {
                        SeslDatePicker.this.mLunarStartYear = i;
                        SeslDatePicker.this.mLunarStartMonth = i2;
                        SeslDatePicker.this.mLunarStartDay = i3;
                        SeslDatePicker.this.mIsLeapStartMonth = 0;
                    }
                } else if (i4 != 2) {
                    SeslDatePicker seslDatePicker2 = SeslDatePicker.this;
                    seslDatePicker2.clearCalendar(seslDatePicker2.mStartDate, i, i2, i3);
                    SeslDatePicker seslDatePicker3 = SeslDatePicker.this;
                    seslDatePicker3.clearCalendar(seslDatePicker3.mEndDate, i, i2, i3);
                    if (SeslDatePicker.this.mIsLunar) {
                        SeslDatePicker.this.mLunarStartYear = i;
                        SeslDatePicker.this.mLunarStartMonth = i2;
                        SeslDatePicker.this.mLunarStartDay = i3;
                        SeslDatePicker.this.mIsLeapStartMonth = 0;
                        SeslDatePicker.this.mLunarEndYear = i;
                        SeslDatePicker.this.mLunarEndMonth = i2;
                        SeslDatePicker.this.mLunarEndDay = i3;
                        SeslDatePicker.this.mIsLeapEndMonth = 0;
                        SeslDatePicker.this.mIsLeapMonth = false;
                    }
                } else {
                    SeslDatePicker seslDatePicker4 = SeslDatePicker.this;
                    seslDatePicker4.clearCalendar(seslDatePicker4.mEndDate, i, i2, i3);
                    if (SeslDatePicker.this.mIsLunar) {
                        SeslDatePicker.this.mLunarEndYear = i;
                        SeslDatePicker.this.mLunarEndMonth = i2;
                        SeslDatePicker.this.mLunarEndDay = i3;
                        SeslDatePicker.this.mIsLeapEndMonth = 0;
                    }
                }
                SeslDatePicker seslDatePicker5 = SeslDatePicker.this;
                seslDatePicker5.onValidationChanged(!seslDatePicker5.mStartDate.after(SeslDatePicker.this.mEndDate));
                SeslDatePicker.this.updateSimpleMonthView(false);
                SeslDatePicker.this.onDateChanged();
            }
        });
        this.mCurrentViewType = 0;
        this.mCalendarHeaderText.setOnClickListener(this.mCalendarHeaderClickListener);
        this.mCalendarHeaderText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /* class androidx.picker.widget.SeslDatePicker.AnonymousClass7 */

            public void onFocusChange(View view, boolean z) {
                if (z && SeslDatePicker.this.mCurrentViewType == 1) {
                    SeslDatePicker.this.setEditTextMode(false);
                }
            }
        });
        this.mDayOfTheWeekLayoutHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_day_height);
        checkMaxFontSize();
        this.mCalendarViewPagerWidth = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_view_width);
        this.mCalendarViewMargin = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_view_margin);
        this.mDayOfTheWeekLayoutWidth = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_view_width);
        this.mDayOfTheWeekLayout = (LinearLayout) findViewById(R.id.sesl_date_picker_day_of_the_week);
        this.mDayOfTheWeekLayout.addView(this.mDayOfTheWeekView);
        this.mDatePickerLayout = (LinearLayout) findViewById(R.id.sesl_date_picker_layout);
        this.mCalendarHeaderLayout = (RelativeLayout) findViewById(R.id.sesl_date_picker_calendar_header_layout);
        if (this.mIsRTL) {
            this.mPrevButton = (ImageButton) findViewById(R.id.sesl_date_picker_calendar_header_next_button);
            this.mNextButton = (ImageButton) findViewById(R.id.sesl_date_picker_calendar_header_prev_button);
            this.mPrevButton.setContentDescription(this.mContext.getString(R.string.sesl_date_picker_decrement_month));
            this.mNextButton.setContentDescription(this.mContext.getString(R.string.sesl_date_picker_increment_month));
        } else {
            this.mPrevButton = (ImageButton) findViewById(R.id.sesl_date_picker_calendar_header_prev_button);
            this.mNextButton = (ImageButton) findViewById(R.id.sesl_date_picker_calendar_header_next_button);
        }
        this.mPrevButton.setOnClickListener(this);
        this.mNextButton.setOnClickListener(this);
        this.mPrevButton.setOnLongClickListener(this);
        this.mNextButton.setOnLongClickListener(this);
        this.mPrevButton.setOnTouchListener(this.mMonthBtnTouchListener);
        this.mNextButton.setOnTouchListener(this.mMonthBtnTouchListener);
        this.mPrevButton.setOnKeyListener(this.mMonthBtnKeyListener);
        this.mNextButton.setOnKeyListener(this.mMonthBtnKeyListener);
        this.mPrevButton.setOnFocusChangeListener(this.mBtnFocusChangeListener);
        this.mNextButton.setOnFocusChangeListener(this.mBtnFocusChangeListener);
        this.mPrevButton.setColorFilter(color2);
        this.mNextButton.setColorFilter(color2);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843868, typedValue, true);
        this.mBackgroundBorderlessResId = typedValue.resourceId;
        this.mCalendarHeaderLayoutHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_header_height);
        this.mCalendarViewPagerHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_view_height);
        this.mOldCalendarViewPagerWidth = this.mCalendarViewPagerWidth;
        this.mCalendarHeaderText.setFocusable(true);
        this.mPrevButton.setNextFocusRightId(R.id.sesl_date_picker_calendar_header_text);
        this.mNextButton.setNextFocusLeftId(R.id.sesl_date_picker_calendar_header_text);
        this.mCalendarHeaderText.setNextFocusRightId(R.id.sesl_date_picker_calendar_header_next_button);
        this.mCalendarHeaderText.setNextFocusLeftId(R.id.sesl_date_picker_calendar_header_prev_button);
        this.mFirstBlankSpace = findViewById(R.id.sesl_date_picker_between_header_and_weekend);
        this.mFirstBlankSpaceHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_gap_between_header_and_weekend);
        this.mSecondBlankSpace = findViewById(R.id.sesl_date_picker_between_weekend_and_calender);
        this.mSecondBlankSpaceHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_gap_between_weekend_and_calender);
        this.mDatePickerHeight = this.mCalendarHeaderLayoutHeight + this.mFirstBlankSpaceHeight + this.mDayOfTheWeekLayoutHeight + this.mSecondBlankSpaceHeight + this.mCalendarViewPagerHeight;
        updateSimpleMonthView(true);
        TypedValue typedValue2 = new TypedValue();
        this.mContext.getTheme().resolveAttribute(16842839, typedValue2, true);
        this.mIsInDialog = typedValue2.data != 0 ? true : z;
        Activity scanForActivity = scanForActivity(this.mContext);
        if (scanForActivity != null && !this.mIsInDialog) {
            this.mContentFrame = (FrameLayout) scanForActivity.getWindow().getDecorView().findViewById(16908290);
        } else if (scanForActivity == null) {
            Log.e(TAG, "Cannot get window of this context. context:" + this.mContext);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    public void setValidationCallback(ValidationCallback validationCallback) {
        this.mValidationCallback = validationCallback;
    }

    /* access modifiers changed from: protected */
    public void onValidationChanged(boolean z) {
        ValidationCallback validationCallback = this.mValidationCallback;
        if (validationCallback != null) {
            validationCallback.onValidationChanged(z);
        }
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

    public void setOnViewTypeChangedListener(OnViewTypeChangedListener onViewTypeChangedListener) {
        this.mOnViewTypeChangedListener = onViewTypeChangedListener;
    }

    public void init(int i, int i2, int i3, OnDateChangedListener onDateChangedListener) {
        this.mCurrentDate.set(1, i);
        this.mCurrentDate.set(2, i2);
        this.mCurrentDate.set(5, i3);
        if (this.mIsLunar) {
            this.mLunarCurrentYear = i;
            this.mLunarCurrentMonth = i2;
            this.mLunarCurrentDay = i3;
        }
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate = getCalendarForLocale(this.mMinDate, this.mCurrentLocale);
        }
        if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate = getCalendarForLocale(this.mMaxDate, this.mCurrentLocale);
        }
        this.mOnDateChangedListener = onDateChangedListener;
        updateSimpleMonthView(true);
        onDateChanged();
        this.mSpinnerLayout.setMinDate(this.mMinDate.getTimeInMillis());
        this.mSpinnerLayout.setMaxDate(this.mMaxDate.getTimeInMillis());
        if (this.mCurrentViewType == 0) {
            this.mSpinnerLayout.setVisibility(4);
            this.mSpinnerLayout.setEnabled(false);
        }
        clearCalendar(this.mStartDate, i, i2, i3);
        clearCalendar(this.mEndDate, i, i2, i3);
        if (this.mIsLunar) {
            this.mLunarStartYear = i;
            this.mLunarStartMonth = i2;
            this.mLunarStartDay = i3;
            this.mLunarEndYear = i;
            this.mLunarEndMonth = i2;
            this.mLunarEndDay = i3;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clearCalendar(Calendar calendar, int i, int i2, int i3) {
        calendar.clear();
        calendar.set(1, i);
        calendar.set(2, i2);
        calendar.set(5, i3);
    }

    public void updateDate(int i, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        SeslSimpleMonthView seslSimpleMonthView;
        SeslSimpleMonthView seslSimpleMonthView2;
        this.mTempDate.set(1, i);
        this.mTempDate.set(2, i2);
        this.mTempDate.set(5, i3);
        this.mCurrentDate = getCalendarForLocale(this.mTempDate, this.mCurrentLocale);
        if (this.mIsLunar) {
            this.mLunarCurrentYear = i;
            this.mLunarCurrentMonth = i2;
            this.mLunarCurrentDay = i3;
        }
        int i10 = this.mMode;
        if (i10 == 1) {
            clearCalendar(this.mStartDate, i, i2, i3);
            if (this.mIsLunar) {
                this.mLunarStartYear = i;
                this.mLunarStartMonth = i2;
                this.mLunarStartDay = i3;
            }
        } else if (i10 != 2) {
            clearCalendar(this.mStartDate, i, i2, i3);
            clearCalendar(this.mEndDate, i, i2, i3);
            if (this.mIsLunar) {
                this.mLunarStartYear = i;
                this.mLunarStartMonth = i2;
                this.mLunarStartDay = i3;
                this.mLunarEndYear = i;
                this.mLunarEndMonth = i2;
                this.mLunarEndDay = i3;
            }
        } else {
            clearCalendar(this.mEndDate, i, i2, i3);
            if (this.mIsLunar) {
                this.mLunarEndYear = i;
                this.mLunarEndMonth = i2;
                this.mLunarEndDay = i3;
            }
        }
        updateSimpleMonthView(true);
        onDateChanged();
        SparseArray<SeslSimpleMonthView> sparseArray = this.mCalendarPagerAdapter.views;
        SeslSimpleMonthView seslSimpleMonthView3 = sparseArray.get(this.mCurrentPosition);
        if (seslSimpleMonthView3 != null) {
            int minDay = (getMinMonth() == i2 && getMinYear() == i) ? getMinDay() : 1;
            int maxDay = (getMaxMonth() == i2 && getMaxYear() == i) ? getMaxDay() : 31;
            if (this.mIsLunarSupported) {
                seslSimpleMonthView3.setLunar(this.mIsLunar, this.mIsLeapMonth, this.mPathClassLoader);
            }
            int i11 = this.mStartDate.get(1);
            int i12 = this.mStartDate.get(2);
            int i13 = this.mStartDate.get(5);
            int i14 = this.mEndDate.get(1);
            int i15 = this.mEndDate.get(2);
            int i16 = this.mEndDate.get(5);
            if (this.mIsLunar) {
                i9 = this.mLunarStartYear;
                i8 = this.mLunarStartMonth;
                i7 = this.mLunarStartDay;
                i6 = this.mLunarEndYear;
                i5 = this.mLunarEndMonth;
                i4 = this.mLunarEndDay;
            } else {
                i9 = i11;
                i5 = i15;
                i4 = i16;
                i8 = i12;
                i7 = i13;
                i6 = i14;
            }
            seslSimpleMonthView3.setMonthParams(i3, i2, i, getFirstDayOfWeek(), minDay, maxDay, this.mMinDate, this.mMaxDate, i9, i8, i7, this.mIsLeapStartMonth, i6, i5, i4, this.mIsLeapEndMonth, this.mMode);
            seslSimpleMonthView3.invalidate();
            if (!this.mIsLunar) {
                int i17 = this.mCurrentPosition - 1;
                if (i17 >= 0 && (seslSimpleMonthView2 = sparseArray.get(i17)) != null) {
                    seslSimpleMonthView2.setStartDate(this.mStartDate, this.mIsLeapStartMonth);
                    seslSimpleMonthView2.setEndDate(this.mEndDate, this.mIsLeapEndMonth);
                }
                int i18 = this.mCurrentPosition + 1;
                if (i18 < this.mPositionCount && (seslSimpleMonthView = sparseArray.get(i18)) != null) {
                    seslSimpleMonthView.setStartDate(this.mStartDate, this.mIsLeapStartMonth);
                    seslSimpleMonthView.setEndDate(this.mEndDate, this.mIsLeapEndMonth);
                }
            }
        }
        SeslDatePickerSpinnerLayout seslDatePickerSpinnerLayout = this.mSpinnerLayout;
        if (seslDatePickerSpinnerLayout != null) {
            seslDatePickerSpinnerLayout.updateDate(i, i2, i3);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onDateChanged() {
        if (this.mOnDateChangedListener != null) {
            int i = this.mCurrentDate.get(1);
            int i2 = this.mCurrentDate.get(2);
            int i3 = this.mCurrentDate.get(5);
            if (this.mIsLunar) {
                i = this.mLunarCurrentYear;
                i2 = this.mLunarCurrentMonth;
                i3 = this.mLunarCurrentDay;
            }
            this.mOnDateChangedListener.onDateChanged(this, i, i2, i3);
        }
    }

    public int getYear() {
        if (this.mIsLunar) {
            return this.mLunarCurrentYear;
        }
        return this.mCurrentDate.get(1);
    }

    public int getMonth() {
        if (this.mIsLunar) {
            return this.mLunarCurrentMonth;
        }
        return this.mCurrentDate.get(2);
    }

    public int getDayOfMonth() {
        if (this.mIsLunar) {
            return this.mLunarCurrentDay;
        }
        return this.mCurrentDate.get(5);
    }

    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    public void setMinDate(long j) {
        this.mTempMinMaxDate.setTimeInMillis(j);
        if (this.mTempMinMaxDate.get(1) != this.mMinDate.get(1) || this.mTempMinMaxDate.get(6) == this.mMinDate.get(6)) {
            if (this.mIsLunar) {
                setTotalMonthCountWithLeap();
            }
            if (this.mCurrentDate.before(this.mTempMinMaxDate)) {
                this.mCurrentDate.setTimeInMillis(j);
                onDateChanged();
            }
            this.mMinDate.setTimeInMillis(j);
            this.mSpinnerLayout.setMinDate(this.mMinDate.getTimeInMillis());
            this.mCalendarPagerAdapter.notifyDataSetChanged();
            updateSimpleMonthView(false);
        }
    }

    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    public void setMaxDate(long j) {
        this.mTempMinMaxDate.setTimeInMillis(j);
        if (this.mTempMinMaxDate.get(1) != this.mMaxDate.get(1) || this.mTempMinMaxDate.get(6) == this.mMaxDate.get(6)) {
            if (this.mIsLunar) {
                setTotalMonthCountWithLeap();
            }
            if (this.mCurrentDate.after(this.mTempMinMaxDate)) {
                this.mCurrentDate.setTimeInMillis(j);
                onDateChanged();
            }
            this.mMaxDate.setTimeInMillis(j);
            this.mSpinnerLayout.setMaxDate(this.mMaxDate.getTimeInMillis());
            this.mCalendarPagerAdapter.notifyDataSetChanged();
            updateSimpleMonthView(false);
        }
    }

    /* access modifiers changed from: package-private */
    public int getMinYear() {
        return this.mMinDate.get(1);
    }

    /* access modifiers changed from: package-private */
    public int getMaxYear() {
        return this.mMaxDate.get(1);
    }

    /* access modifiers changed from: package-private */
    public int getMinMonth() {
        return this.mMinDate.get(2);
    }

    /* access modifiers changed from: package-private */
    public int getMaxMonth() {
        return this.mMaxDate.get(2);
    }

    /* access modifiers changed from: package-private */
    public int getMinDay() {
        return this.mMinDate.get(5);
    }

    /* access modifiers changed from: package-private */
    public int getMaxDay() {
        return this.mMaxDate.get(5);
    }

    public void setEnabled(boolean z) {
        if (isEnabled() != z) {
            super.setEnabled(z);
            this.mIsEnabled = z;
        }
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.getText().add(getFormattedCurrentDate());
        return true;
    }

    private String getFormattedCurrentDate() {
        return DateUtils.formatDateTime(this.mContext, this.mCurrentDate.getTimeInMillis(), 20);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        Locale locale;
        super.onConfigurationChanged(configuration);
        this.mIsRTL = isRTL();
        this.mIsFarsiLanguage = isFarsiLanguage();
        if (Build.VERSION.SDK_INT >= 24) {
            locale = configuration.getLocales().get(0);
        } else {
            locale = configuration.locale;
        }
        if (!this.mCurrentLocale.equals(locale)) {
            this.mCurrentLocale = locale;
            this.mIsSimplifiedChinese = isSimplifiedChinese();
            if (this.mIsSimplifiedChinese) {
                this.mDayFormatter = new SimpleDateFormat("EEEEE", locale);
            } else {
                this.mDayFormatter = new SimpleDateFormat("EEE", locale);
            }
        }
        Resources resources = this.mContext.getResources();
        this.mDatePickerLayout.setGravity(1);
        this.mIsFirstMeasure = true;
        this.mCalendarHeaderLayoutHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_header_height);
        this.mCalendarViewPagerHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_view_height);
        this.mDayOfTheWeekLayoutHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_day_height);
        this.mFirstBlankSpaceHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_gap_between_header_and_weekend);
        this.mSecondBlankSpaceHeight = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_gap_between_weekend_and_calender);
        this.mDatePickerHeight = this.mCalendarHeaderLayoutHeight + this.mFirstBlankSpaceHeight + this.mDayOfTheWeekLayoutHeight + this.mSecondBlankSpaceHeight + this.mCalendarViewPagerHeight;
        if (this.mIsRTL) {
            this.mIsConfigurationChanged = true;
        }
        checkMaxFontSize();
    }

    public void setFirstDayOfWeek(int i) {
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("firstDayOfWeek must be between 1 and 7");
        }
        this.mFirstDayOfWeek = i;
    }

    public int getFirstDayOfWeek() {
        int i = this.mFirstDayOfWeek;
        if (i != 0) {
            return i;
        }
        return this.mCurrentDate.getFirstDayOfWeek();
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup, android.view.View
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        int i = this.mCurrentDate.get(1);
        int i2 = this.mCurrentDate.get(2);
        int i3 = this.mCurrentDate.get(5);
        if (this.mIsLunar) {
            i = this.mLunarCurrentYear;
            i2 = this.mLunarCurrentMonth;
            i3 = this.mLunarCurrentDay;
        }
        return new SavedState(onSaveInstanceState, i, i2, i3, this.mMinDate.getTimeInMillis(), this.mMaxDate.getTimeInMillis());
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(((View.BaseSavedState) parcelable).getSuperState());
        SavedState savedState = (SavedState) parcelable;
        this.mCurrentDate.set(savedState.getSelectedYear(), savedState.getSelectedMonth(), savedState.getSelectedDay());
        if (this.mIsLunar) {
            this.mLunarCurrentYear = savedState.getSelectedYear();
            this.mLunarCurrentMonth = savedState.getSelectedMonth();
            this.mLunarCurrentDay = savedState.getSelectedDay();
        }
        this.mMinDate.setTimeInMillis(savedState.getMinDate());
        this.mMaxDate.setTimeInMillis(savedState.getMaxDate());
    }

    /* JADX WARN: Type inference failed for: r0v14, types: [int, boolean] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDayOfMonthSelected(int r5, int r6, int r7) {
        /*
        // Method dump skipped, instructions count: 141
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.picker.widget.SeslDatePicker.onDayOfMonthSelected(int, int, int):void");
    }

    public Calendar getStartDate() {
        return this.mStartDate;
    }

    public Calendar getEndDate() {
        return this.mEndDate;
    }

    /* access modifiers changed from: private */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class androidx.picker.widget.SeslDatePicker.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final long mMaxDate;
        private final long mMinDate;
        private final int mSelectedDay;
        private final int mSelectedMonth;
        private final int mSelectedYear;

        private SavedState(Parcelable parcelable, int i, int i2, int i3, long j, long j2) {
            super(parcelable);
            this.mSelectedYear = i;
            this.mSelectedMonth = i2;
            this.mSelectedDay = i3;
            this.mMinDate = j;
            this.mMaxDate = j2;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mSelectedYear = parcel.readInt();
            this.mSelectedMonth = parcel.readInt();
            this.mSelectedDay = parcel.readInt();
            this.mMinDate = parcel.readLong();
            this.mMaxDate = parcel.readLong();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mSelectedYear);
            parcel.writeInt(this.mSelectedMonth);
            parcel.writeInt(this.mSelectedDay);
            parcel.writeLong(this.mMinDate);
            parcel.writeLong(this.mMaxDate);
        }

        /* access modifiers changed from: package-private */
        public int getSelectedDay() {
            return this.mSelectedDay;
        }

        /* access modifiers changed from: package-private */
        public int getSelectedMonth() {
            return this.mSelectedMonth;
        }

        /* access modifiers changed from: package-private */
        public int getSelectedYear() {
            return this.mSelectedYear;
        }

        /* access modifiers changed from: package-private */
        public long getMinDate() {
            return this.mMinDate;
        }

        /* access modifiers changed from: package-private */
        public long getMaxDate() {
            return this.mMaxDate;
        }
    }

    private boolean isRTL() {
        if ("ur".equals(this.mCurrentLocale.getLanguage())) {
            return false;
        }
        Locale locale = this.mCurrentLocale;
        byte directionality = Character.getDirectionality(locale.getDisplayName(locale).charAt(0));
        if (directionality == 1 || directionality == 2) {
            return true;
        }
        return false;
    }

    @Override // androidx.picker.widget.SeslSimpleMonthView.OnDayClickListener
    public void onDayClick(SeslSimpleMonthView seslSimpleMonthView, int i, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        debugLog("onDayClick day : " + i3);
        int i8 = this.mCurrentDate.get(1);
        int i9 = this.mCurrentDate.get(2);
        if (this.mIsLunar) {
            i8 = this.mLunarCurrentYear;
            i9 = this.mLunarCurrentMonth;
        }
        onDayOfMonthSelected(i, i2, i3);
        boolean z = this.mCurrentPosition != (i2 - getMinMonth()) + ((i - getMinYear()) * 12);
        if (!(i == i8 && i2 == i9 && i3 == this.mOldSelectedDay && !this.mIsLunar && !z)) {
            this.mOldSelectedDay = i3;
            this.mCalendarPagerAdapter.notifyDataSetChanged();
        }
        int minDay = (getMinMonth() == i2 && getMinYear() == i) ? getMinDay() : 1;
        int maxDay = (getMaxMonth() == i2 && getMaxYear() == i) ? getMaxDay() : 31;
        if (this.mIsLunarSupported) {
            seslSimpleMonthView.setLunar(this.mIsLunar, this.mIsLeapMonth, this.mPathClassLoader);
        }
        int i10 = this.mStartDate.get(1);
        int i11 = this.mStartDate.get(2);
        int i12 = this.mStartDate.get(5);
        int i13 = this.mEndDate.get(1);
        int i14 = this.mEndDate.get(2);
        int i15 = this.mEndDate.get(5);
        if (this.mIsLunar) {
            i10 = this.mLunarStartYear;
            int i16 = this.mLunarStartMonth;
            int i17 = this.mLunarStartDay;
            int i18 = this.mLunarEndYear;
            int i19 = this.mLunarEndMonth;
            i15 = this.mLunarEndDay;
            i7 = i16;
            i6 = i17;
            i5 = i18;
            i4 = i19;
        } else {
            i5 = i13;
            i4 = i14;
            i7 = i11;
            i6 = i12;
        }
        seslSimpleMonthView.setMonthParams(i3, i2, i, getFirstDayOfWeek(), minDay, maxDay, this.mMinDate, this.mMaxDate, i10, i7, i6, this.mIsLeapStartMonth, i5, i4, i15, this.mIsLeapEndMonth, this.mMode);
        seslSimpleMonthView.invalidate();
    }

    @Override // androidx.picker.widget.SeslSimpleMonthView.OnDeactivatedDayClickListener
    public void onDeactivatedDayClick(SeslSimpleMonthView seslSimpleMonthView, int i, int i2, int i3, boolean z, boolean z2) {
        if (this.mIsLunar) {
            int i4 = this.mCurrentPosition;
            LunarDate lunarDateByPosition = getLunarDateByPosition(z2 ? i4 - 1 : i4 + 1);
            int i5 = lunarDateByPosition.year;
            int i6 = lunarDateByPosition.month;
            this.mIsLeapMonth = lunarDateByPosition.isLeapMonth;
            int i7 = this.mCurrentPosition;
            this.mCurrentPosition = z2 ? i7 - 1 : i7 + 1;
            this.mCalendarViewPager.setCurrentItem(this.mCurrentPosition);
            onDayClick(seslSimpleMonthView, i5, i6, i3);
            return;
        }
        onDayClick(seslSimpleMonthView, i, i2, i3);
        updateSimpleMonthView(true);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateSimpleMonthView(boolean z) {
        int i = this.mCurrentDate.get(2);
        int i2 = this.mCurrentDate.get(1);
        if (this.mIsLunar) {
            i2 = this.mLunarCurrentYear;
            i = this.mLunarCurrentMonth;
        }
        if (this.mLunarChanged) {
            i = this.mTempDate.get(2);
            i2 = this.mTempDate.get(1);
        }
        int minYear = ((i2 - getMinYear()) * 12) + (i - getMinMonth());
        if (this.mIsLunar) {
            minYear = (i < getIndexOfleapMonthOfYear(i2) ? i : i + 1) + (i2 == getMinYear() ? -getMinMonth() : getTotalMonthCountWithLeap(i2 - 1));
            if ((this.mMode == 1 && i == this.mLunarStartMonth && this.mIsLeapStartMonth == 1) || ((this.mMode == 2 && i == this.mLunarEndMonth && this.mIsLeapEndMonth == 1) || (this.mMode == 0 && this.mIsLeapMonth))) {
                minYear++;
            }
        }
        this.mCurrentPosition = minYear;
        this.mCalendarViewPager.setCurrentItem(minYear, z);
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 1000;
        obtainMessage.obj = true;
        this.mHandler.sendMessage(obtainMessage);
        Message obtainMessage2 = this.mHandler.obtainMessage();
        obtainMessage2.what = 1001;
        this.mHandler.sendMessage(obtainMessage2);
    }

    /* access modifiers changed from: private */
    public class CalendarPagerAdapter extends PagerAdapter {
        SparseArray<SeslSimpleMonthView> views = new SparseArray<>();

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Parcelable saveState() {
            return null;
        }

        public CalendarPagerAdapter() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            int maxYear = SeslDatePicker.this.getMaxYear() - SeslDatePicker.this.getMinYear();
            SeslDatePicker seslDatePicker = SeslDatePicker.this;
            seslDatePicker.mPositionCount = (seslDatePicker.getMaxMonth() - SeslDatePicker.this.getMinMonth()) + 1 + (maxYear * 12);
            if (SeslDatePicker.this.mIsLunar) {
                SeslDatePicker seslDatePicker2 = SeslDatePicker.this;
                seslDatePicker2.mPositionCount = seslDatePicker2.getTotalMonthCountWithLeap(seslDatePicker2.getMaxYear());
            }
            return SeslDatePicker.this.mPositionCount;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(View view, int i) {
            int i2;
            int i3;
            boolean z;
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            int i9;
            SeslSimpleMonthView seslSimpleMonthView = new SeslSimpleMonthView(SeslDatePicker.this.mContext);
            SeslDatePicker.this.debugLog("instantiateItem : " + i);
            seslSimpleMonthView.setClickable(true);
            seslSimpleMonthView.setOnDayClickListener(SeslDatePicker.this);
            seslSimpleMonthView.setOnDeactivatedDayClickListener(SeslDatePicker.this);
            seslSimpleMonthView.setTextColor();
            int minMonth = SeslDatePicker.this.getMinMonth() + i;
            int minYear = (minMonth / 12) + SeslDatePicker.this.getMinYear();
            int i10 = minMonth % 12;
            if (SeslDatePicker.this.mIsLunar) {
                LunarDate lunarDateByPosition = SeslDatePicker.this.getLunarDateByPosition(i);
                int i11 = lunarDateByPosition.year;
                i3 = lunarDateByPosition.month;
                z = lunarDateByPosition.isLeapMonth;
                i2 = i11;
            } else {
                i3 = i10;
                i2 = minYear;
                z = false;
            }
            int i12 = (SeslDatePicker.this.mCurrentDate.get(1) == i2 && SeslDatePicker.this.mCurrentDate.get(2) == i3) ? SeslDatePicker.this.mCurrentDate.get(5) : -1;
            if (SeslDatePicker.this.mIsLunar) {
                i12 = (SeslDatePicker.this.mLunarCurrentYear == i2 && SeslDatePicker.this.mLunarCurrentMonth == i3) ? SeslDatePicker.this.mLunarCurrentDay : -1;
            }
            if (SeslDatePicker.this.mIsLunarSupported) {
                seslSimpleMonthView.setLunar(SeslDatePicker.this.mIsLunar, z, SeslDatePicker.this.mPathClassLoader);
            }
            int i13 = SeslDatePicker.this.mStartDate.get(1);
            int i14 = SeslDatePicker.this.mStartDate.get(2);
            int i15 = SeslDatePicker.this.mStartDate.get(5);
            int i16 = SeslDatePicker.this.mEndDate.get(1);
            int i17 = SeslDatePicker.this.mEndDate.get(2);
            int i18 = SeslDatePicker.this.mEndDate.get(5);
            if (SeslDatePicker.this.mIsLunar) {
                int i19 = SeslDatePicker.this.mLunarStartYear;
                i9 = i19;
                i8 = SeslDatePicker.this.mLunarStartMonth;
                i7 = SeslDatePicker.this.mLunarStartDay;
                i6 = SeslDatePicker.this.mLunarEndYear;
                i5 = SeslDatePicker.this.mLunarEndMonth;
                i4 = SeslDatePicker.this.mLunarEndDay;
            } else {
                i9 = i13;
                i8 = i14;
                i4 = i18;
                i5 = i17;
                i7 = i15;
                i6 = i16;
            }
            seslSimpleMonthView.setMonthParams(i12, i3, i2, SeslDatePicker.this.getFirstDayOfWeek(), 1, 31, SeslDatePicker.this.mMinDate, SeslDatePicker.this.mMaxDate, i9, i8, i7, SeslDatePicker.this.mIsLeapStartMonth, i6, i5, i4, SeslDatePicker.this.mIsLeapEndMonth, SeslDatePicker.this.mMode);
            if (i == 0) {
                seslSimpleMonthView.setFirstMonth();
            }
            if (i == SeslDatePicker.this.mPositionCount - 1) {
                seslSimpleMonthView.setLastMonth();
            }
            if (SeslDatePicker.this.mIsLunar) {
                if (i != 0 && SeslDatePicker.this.getLunarDateByPosition(i - 1).isLeapMonth) {
                    seslSimpleMonthView.setPrevMonthLeap();
                }
                if (i != SeslDatePicker.this.mPositionCount - 1 && SeslDatePicker.this.getLunarDateByPosition(i + 1).isLeapMonth) {
                    seslSimpleMonthView.setNextMonthLeap();
                }
            }
            SeslDatePicker.this.mNumDays = seslSimpleMonthView.getNumDays();
            SeslDatePicker.this.mWeekStart = seslSimpleMonthView.getWeekStart();
            ((ViewPager) view).addView(seslSimpleMonthView, 0);
            this.views.put(i, seslSimpleMonthView);
            return seslSimpleMonthView;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(View view, int i, Object obj) {
            SeslDatePicker seslDatePicker = SeslDatePicker.this;
            seslDatePicker.debugLog("destroyItem : " + i);
            ((ViewPager) view).removeView((View) obj);
            this.views.remove(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view != null && view.equals(obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void startUpdate(View view) {
            SeslDatePicker.this.debugLog("startUpdate");
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void finishUpdate(View view) {
            SeslDatePicker.this.debugLog("finishUpdate");
        }
    }

    private class CalendarPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        private CalendarPageChangeListener() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            boolean z = false;
            if (SeslDatePicker.this.mIsRTL) {
                SeslDatePicker.this.mIsConfigurationChanged = false;
            }
            if (SeslDatePicker.this.mIsFromSetLunar) {
                SeslDatePicker.this.mIsFromSetLunar = false;
                return;
            }
            SeslDatePicker.this.mCurrentPosition = i;
            int minMonth = SeslDatePicker.this.getMinMonth() + i;
            int minYear = (minMonth / 12) + SeslDatePicker.this.getMinYear();
            int i2 = minMonth % 12;
            int i3 = SeslDatePicker.this.mCurrentDate.get(5);
            if (SeslDatePicker.this.mIsLunar) {
                LunarDate lunarDateByPosition = SeslDatePicker.this.getLunarDateByPosition(i);
                minYear = lunarDateByPosition.year;
                int i4 = lunarDateByPosition.month;
                int i5 = SeslDatePicker.this.mLunarCurrentDay;
                SeslDatePicker.this.mIsLeapMonth = lunarDateByPosition.isLeapMonth;
                i2 = i4;
                i3 = i5;
            }
            if (minYear != SeslDatePicker.this.mTempDate.get(1)) {
                z = true;
            }
            SeslDatePicker.this.mTempDate.set(1, minYear);
            SeslDatePicker.this.mTempDate.set(2, i2);
            SeslDatePicker.this.mTempDate.set(5, 1);
            if (i3 > SeslDatePicker.this.mTempDate.getActualMaximum(5)) {
                i3 = SeslDatePicker.this.mTempDate.getActualMaximum(5);
            }
            SeslDatePicker.this.mTempDate.set(5, i3);
            Message obtainMessage = SeslDatePicker.this.mHandler.obtainMessage();
            obtainMessage.what = 1000;
            obtainMessage.obj = Boolean.valueOf(z);
            SeslDatePicker.this.mHandler.sendMessage(obtainMessage);
            Message obtainMessage2 = SeslDatePicker.this.mHandler.obtainMessage();
            obtainMessage2.what = 1001;
            SeslDatePicker.this.mHandler.sendMessage(obtainMessage2);
            SparseArray<SeslSimpleMonthView> sparseArray = SeslDatePicker.this.mCalendarPagerAdapter.views;
            if (sparseArray.get(i) != null) {
                sparseArray.get(i).clearAccessibilityFocus();
                sparseArray.get(i).setImportantForAccessibility(1);
            }
            if (i != 0) {
                int i6 = i - 1;
                if (sparseArray.get(i6) != null) {
                    sparseArray.get(i6).clearAccessibilityFocus();
                    sparseArray.get(i6).setImportantForAccessibility(2);
                }
            }
            if (i != SeslDatePicker.this.mPositionCount - 1) {
                int i7 = i + 1;
                if (sparseArray.get(i7) != null) {
                    sparseArray.get(i7).clearAccessibilityFocus();
                    sparseArray.get(i7).setImportantForAccessibility(2);
                }
            }
        }
    }

    private static Activity scanForActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.mMeasureSpecHeight = View.MeasureSpec.getSize(i2);
        int makeMeasureSpec = makeMeasureSpec(i, this.mCalendarViewPagerWidth);
        if (this.mIsFirstMeasure || this.mOldCalendarViewPagerWidth != this.mCalendarViewPagerWidth) {
            this.mIsFirstMeasure = false;
            this.mOldCalendarViewPagerWidth = this.mCalendarViewPagerWidth;
            RelativeLayout relativeLayout = this.mCustomButtonLayout;
            if (relativeLayout != null) {
                relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, this.mCalendarHeaderLayoutHeight));
            }
            this.mCalendarHeaderLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, this.mCalendarHeaderLayoutHeight));
            this.mDayOfTheWeekLayout.setLayoutParams(new LinearLayout.LayoutParams(this.mDayOfTheWeekLayoutWidth, this.mDayOfTheWeekLayoutHeight));
            this.mDayOfTheWeekView.setLayoutParams(new LinearLayout.LayoutParams(this.mDayOfTheWeekLayoutWidth, this.mDayOfTheWeekLayoutHeight));
            this.mCalendarViewPager.setLayoutParams(new LinearLayout.LayoutParams(this.mCalendarViewPagerWidth, this.mCalendarViewPagerHeight));
            if (this.mIsRTL && this.mIsConfigurationChanged) {
                this.mCalendarViewPager.seslSetConfigurationChanged(true);
            }
            this.mFirstBlankSpace.setLayoutParams(new LinearLayout.LayoutParams(-1, this.mFirstBlankSpaceHeight));
            this.mSecondBlankSpace.setLayoutParams(new LinearLayout.LayoutParams(-1, this.mSecondBlankSpaceHeight));
            super.onMeasure(makeMeasureSpec, i2);
            return;
        }
        super.onMeasure(makeMeasureSpec, i2);
    }

    private int makeMeasureSpec(int i, int i2) {
        int i3;
        if (i2 == -1) {
            return i;
        }
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE) {
            int i4 = getResources().getConfiguration().smallestScreenWidthDp;
            if (i4 >= 600) {
                i3 = getResources().getDimensionPixelSize(R.dimen.sesl_date_picker_dialog_min_width);
            } else {
                i3 = (int) (TypedValue.applyDimension(1, (float) i4, getResources().getDisplayMetrics()) + 0.5f);
            }
        } else {
            i3 = View.MeasureSpec.getSize(i);
        }
        if (mode == Integer.MIN_VALUE) {
            int i5 = this.mCalendarViewMargin;
            this.mCalendarViewPagerWidth = i3 - (i5 * 2);
            this.mDayOfTheWeekLayoutWidth = i3 - (i5 * 2);
            return View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        } else if (mode == 0) {
            return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
        } else {
            if (mode == 1073741824) {
                int i6 = this.mCalendarViewMargin;
                this.mCalendarViewPagerWidth = i3 - (i6 * 2);
                this.mDayOfTheWeekLayoutWidth = i3 - (i6 * 2);
                return i;
            }
            throw new IllegalArgumentException("Unknown measure mode: " + mode);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        calculateContentHeight();
    }

    private void calculateContentHeight() {
        Window window;
        if (getLayoutParams().height == -2 || getMeasuredHeight() <= this.mDatePickerHeight) {
            if (this.mContentFrame == null && (window = this.mDialogWindow) != null) {
                this.mContentFrame = (FrameLayout) window.findViewById(R.id.customPanel);
            }
            int i = this.mMeasureSpecHeight;
            FrameLayout frameLayout = this.mContentFrame;
            if (frameLayout != null) {
                i = frameLayout.getMeasuredHeight();
                if (this.mDialogWindow != null) {
                    i -= this.mDialogPaddingVertical;
                }
            }
            updateViewType(i);
        }
    }

    private void updateViewType(int i) {
        Activity scanForActivity;
        if (Build.VERSION.SDK_INT >= 24 && (scanForActivity = scanForActivity(this.mContext)) != null && scanForActivity.isInMultiWindowMode()) {
            if (i < this.mDatePickerHeight) {
                setCurrentViewType(1);
                this.mCalendarHeaderText.setOnClickListener(null);
                this.mCalendarHeaderText.setClickable(false);
            } else if (!this.mCalendarHeaderText.hasOnClickListeners()) {
                this.mCalendarHeaderText.setOnClickListener(this.mCalendarHeaderClickListener);
                this.mCalendarHeaderText.setClickable(true);
            }
        }
    }

    public void setDialogWindow(Window window) {
        if (window != null) {
            this.mDialogWindow = window;
        }
    }

    public void setDialogPaddingVertical(int i) {
        this.mDialogPaddingVertical = i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getMonthAndYearString(Calendar calendar) {
        if (this.mIsFarsiLanguage) {
            return new SimpleDateFormat("LLLL y", this.mCurrentLocale).format(calendar.getTime());
        }
        StringBuilder sb = new StringBuilder(50);
        Formatter formatter = new Formatter(sb, this.mCurrentLocale);
        sb.setLength(0);
        long timeInMillis = calendar.getTimeInMillis();
        return DateUtils.formatDateRange(getContext(), formatter, timeInMillis, timeInMillis, 36, Time.getCurrentTimezone()).toString();
    }

    private class DayOfTheWeekView extends View {
        private int[] mDayColorSet = new int[7];
        private Calendar mDayLabelCalendar = Calendar.getInstance();
        private String mDefaultWeekdayFeatureString = "XXXXXXR";
        private Paint mMonthDayLabelPaint;
        private int mNormalDayTextColor;
        private int mSaturdayTextColor;
        private int mSundayTextColor;
        private String mWeekdayFeatureString;

        public DayOfTheWeekView(Context context, TypedArray typedArray) {
            super(context);
            Resources resources = context.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sesl_date_picker_month_day_label_text_size);
            this.mNormalDayTextColor = typedArray.getColor(R.styleable.DatePicker_dayTextColor, resources.getColor(R.color.sesl_date_picker_normal_text_color_light));
            this.mSundayTextColor = typedArray.getColor(R.styleable.DatePicker_sundayTextColor, resources.getColor(R.color.sesl_date_picker_sunday_text_color_light));
            this.mSaturdayTextColor = ResourcesCompat.getColor(resources, R.color.sesl_date_picker_saturday_week_text_color, null);
            this.mWeekdayFeatureString = SeslCscFeatureReflector.getString(SeslDatePicker.TAG_CSCFEATURE_CALENDAR_SETCOLOROFDAYS, this.mDefaultWeekdayFeatureString);
            this.mMonthDayLabelPaint = new Paint();
            this.mMonthDayLabelPaint.setAntiAlias(true);
            this.mMonthDayLabelPaint.setColor(this.mNormalDayTextColor);
            this.mMonthDayLabelPaint.setTextSize((float) dimensionPixelSize);
            this.mMonthDayLabelPaint.setTypeface(Typeface.create("sec-roboto-light", 0));
            this.mMonthDayLabelPaint.setTextAlign(Paint.Align.CENTER);
            this.mMonthDayLabelPaint.setStyle(Paint.Style.FILL);
            this.mMonthDayLabelPaint.setFakeBoldText(false);
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            int i;
            int i2;
            super.onDraw(canvas);
            if (SeslDatePicker.this.mNumDays != 0) {
                int i3 = (SeslDatePicker.this.mDayOfTheWeekLayoutHeight * 2) / 3;
                int i4 = SeslDatePicker.this.mDayOfTheWeekLayoutWidth / (SeslDatePicker.this.mNumDays * 2);
                for (int i5 = 0; i5 < SeslDatePicker.this.mNumDays; i5++) {
                    char charAt = this.mWeekdayFeatureString.charAt(i5);
                    int i6 = (i5 + 2) % SeslDatePicker.this.mNumDays;
                    if (charAt == 'B') {
                        this.mDayColorSet[i6] = this.mSaturdayTextColor;
                    } else if (charAt != 'R') {
                        this.mDayColorSet[i6] = this.mNormalDayTextColor;
                    } else {
                        this.mDayColorSet[i6] = this.mSundayTextColor;
                    }
                }
                for (int i7 = 0; i7 < SeslDatePicker.this.mNumDays; i7++) {
                    int i8 = (SeslDatePicker.this.mWeekStart + i7) % SeslDatePicker.this.mNumDays;
                    this.mDayLabelCalendar.set(7, i8);
                    String upperCase = SeslDatePicker.this.mDayFormatter.format(this.mDayLabelCalendar.getTime()).toUpperCase();
                    if (SeslDatePicker.this.mIsRTL) {
                        i2 = ((((SeslDatePicker.this.mNumDays - 1) - i7) * 2) + 1) * i4;
                        i = SeslDatePicker.this.mPadding;
                    } else {
                        i2 = ((i7 * 2) + 1) * i4;
                        i = SeslDatePicker.this.mPadding;
                    }
                    this.mMonthDayLabelPaint.setColor(this.mDayColorSet[i8]);
                    canvas.drawText(upperCase, (float) (i2 + i), (float) i3, this.mMonthDayLabelPaint);
                }
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.sesl_date_picker_calendar_header_prev_button) {
            if (this.mIsRTL) {
                int i = this.mCurrentPosition;
                if (i != this.mPositionCount - 1) {
                    this.mCalendarViewPager.setCurrentItem(i + 1);
                    return;
                }
                return;
            }
            int i2 = this.mCurrentPosition;
            if (i2 != 0) {
                this.mCalendarViewPager.setCurrentItem(i2 - 1);
            }
        } else if (id != R.id.sesl_date_picker_calendar_header_next_button) {
        } else {
            if (this.mIsRTL) {
                int i3 = this.mCurrentPosition;
                if (i3 != 0) {
                    this.mCalendarViewPager.setCurrentItem(i3 - 1);
                    return;
                }
                return;
            }
            int i4 = this.mCurrentPosition;
            if (i4 != this.mPositionCount - 1) {
                this.mCalendarViewPager.setCurrentItem(i4 + 1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeAllCallbacks();
        super.onDetachedFromWindow();
    }

    public boolean onLongClick(View view) {
        int id = view.getId();
        if (id == R.id.sesl_date_picker_calendar_header_prev_button && this.mCurrentPosition != 0) {
            postChangeCurrentByOneFromLongPress(false, (long) ViewConfiguration.getLongPressTimeout());
        } else if (id == R.id.sesl_date_picker_calendar_header_next_button && this.mCurrentPosition != this.mPositionCount - 1) {
            postChangeCurrentByOneFromLongPress(true, (long) ViewConfiguration.getLongPressTimeout());
        }
        return false;
    }

    private void postChangeCurrentByOneFromLongPress(boolean z, long j) {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(z);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, j);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void removeAllCallbacks() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
            new Handler().postDelayed(new Runnable() {
                /* class androidx.picker.widget.SeslDatePicker.AnonymousClass8 */

                public void run() {
                    SeslDatePicker.this.mCalendarViewPager.setCurrentItem(SeslDatePicker.this.mCurrentPosition, false);
                }
            }, 200);
        }
    }

    /* access modifiers changed from: private */
    public class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        private ChangeCurrentByOneFromLongPressCommand() {
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void setStep(boolean z) {
            this.mIncrement = z;
        }

        public void run() {
            if (this.mIncrement) {
                SeslDatePicker.this.mCalendarViewPager.setCurrentItem(SeslDatePicker.this.mCurrentPosition + 1);
            } else {
                SeslDatePicker.this.mCalendarViewPager.setCurrentItem(SeslDatePicker.this.mCurrentPosition - 1);
            }
            SeslDatePicker.this.postDelayed(this, SeslDatePicker.DEFAULT_LONG_PRESS_UPDATE_INTERVAL);
        }
    }

    public void setDateMode(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        this.mMode = i;
        int i17 = this.mMode;
        if (i17 == 1) {
            if (this.mIsLunar) {
                i13 = this.mLunarStartYear;
                i12 = this.mLunarStartMonth;
                i11 = this.mLunarStartDay;
            } else {
                i13 = this.mStartDate.get(1);
                i12 = this.mStartDate.get(2);
                i11 = this.mStartDate.get(5);
            }
            this.mSpinnerLayout.updateDate(i13, i12, i11);
        } else if (i17 == 2) {
            if (this.mIsLunar) {
                i16 = this.mLunarEndYear;
                i15 = this.mLunarEndMonth;
                i14 = this.mLunarEndDay;
            } else {
                i16 = this.mEndDate.get(1);
                i15 = this.mEndDate.get(2);
                i14 = this.mEndDate.get(5);
            }
            this.mSpinnerLayout.updateDate(i16, i15, i14);
        }
        if (this.mCurrentViewType == 1) {
            this.mSpinnerLayout.setVisibility(0);
            this.mSpinnerLayout.setEnabled(true);
        }
        SeslSimpleMonthView seslSimpleMonthView = this.mCalendarPagerAdapter.views.get(this.mCurrentPosition);
        if (seslSimpleMonthView != null) {
            if (this.mIsLunar) {
                i4 = this.mLunarCurrentYear;
                i3 = this.mLunarCurrentMonth;
                i2 = this.mLunarCurrentDay;
            } else {
                i4 = this.mCurrentDate.get(1);
                i3 = this.mCurrentDate.get(2);
                i2 = this.mCurrentDate.get(5);
            }
            int minDay = (getMinMonth() == i3 && getMinYear() == i4) ? getMinDay() : 1;
            int maxDay = (getMaxMonth() == i3 && getMaxYear() == i4) ? getMaxDay() : 31;
            if (this.mIsLunar) {
                int i18 = this.mLunarStartYear;
                int i19 = this.mLunarStartMonth;
                i10 = i18;
                i9 = i19;
                i8 = this.mLunarStartDay;
                i7 = this.mLunarEndYear;
                i6 = this.mLunarEndMonth;
                i5 = this.mLunarEndDay;
            } else {
                int i20 = this.mStartDate.get(1);
                int i21 = this.mStartDate.get(2);
                int i22 = this.mStartDate.get(5);
                int i23 = this.mEndDate.get(1);
                int i24 = this.mEndDate.get(2);
                i5 = this.mEndDate.get(5);
                i6 = i24;
                i7 = i23;
                i10 = i20;
                i9 = i21;
                i8 = i22;
            }
            seslSimpleMonthView.setMonthParams(i2, i3, i4, getFirstDayOfWeek(), minDay, maxDay, this.mMinDate, this.mMaxDate, i10, i9, i8, this.mIsLeapStartMonth, i7, i6, i5, this.mIsLeapEndMonth, this.mMode);
            seslSimpleMonthView.invalidate();
        }
        if (this.mIsLunar) {
            updateSimpleMonthView(false);
        }
        this.mCalendarPagerAdapter.notifyDataSetChanged();
    }

    public int getDateMode() {
        return this.mMode;
    }

    private void checkMaxFontSize() {
        float f = this.mContext.getResources().getConfiguration().fontScale;
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_header_month_text_size);
        if (f > MAX_FONT_SCALE) {
            this.mCalendarHeaderText.setTextSize(0, (float) Math.floor(Math.ceil((double) (((float) dimensionPixelOffset) / f)) * 1.2000000476837158d));
        }
    }

    public void setCurrentViewType(int i) {
        OnViewTypeChangedListener onViewTypeChangedListener;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z = true;
        if (i != 0) {
            if (i == 1) {
                if (this.mCurrentViewType != i) {
                    int i7 = this.mMode;
                    if (i7 != 1) {
                        if (i7 != 2) {
                            if (this.mIsLunar) {
                                i4 = this.mLunarCurrentYear;
                                i3 = this.mLunarCurrentMonth;
                                i2 = this.mLunarCurrentDay;
                                this.mSpinnerLayout.updateDate(i4, i3, i2);
                                this.mAnimator.setDisplayedChild(1);
                                this.mSpinnerLayout.setEnabled(true);
                                this.mCurrentViewType = i;
                                Message obtainMessage = this.mHandler.obtainMessage();
                                obtainMessage.what = 1000;
                                this.mHandler.sendMessage(obtainMessage);
                                onViewTypeChangedListener = this.mOnViewTypeChangedListener;
                                if (onViewTypeChangedListener != null && z) {
                                    onViewTypeChangedListener.onViewTypeChanged(this);
                                }
                                Message obtainMessage2 = this.mHandler.obtainMessage();
                                obtainMessage2.what = 1001;
                                this.mHandler.sendMessage(obtainMessage2);
                            }
                            i4 = this.mCurrentDate.get(1);
                            i5 = this.mCurrentDate.get(2);
                            i6 = this.mCurrentDate.get(5);
                        } else if (this.mIsLunar) {
                            i4 = this.mLunarEndYear;
                            i3 = this.mLunarEndMonth;
                            i2 = this.mLunarEndDay;
                            this.mSpinnerLayout.updateDate(i4, i3, i2);
                            this.mAnimator.setDisplayedChild(1);
                            this.mSpinnerLayout.setEnabled(true);
                            this.mCurrentViewType = i;
                            Message obtainMessage3 = this.mHandler.obtainMessage();
                            obtainMessage3.what = 1000;
                            this.mHandler.sendMessage(obtainMessage3);
                            onViewTypeChangedListener = this.mOnViewTypeChangedListener;
                            onViewTypeChangedListener.onViewTypeChanged(this);
                            Message obtainMessage22 = this.mHandler.obtainMessage();
                            obtainMessage22.what = 1001;
                            this.mHandler.sendMessage(obtainMessage22);
                        } else {
                            i4 = this.mEndDate.get(1);
                            i5 = this.mEndDate.get(2);
                            i6 = this.mEndDate.get(5);
                        }
                    } else if (this.mIsLunar) {
                        i4 = this.mLunarStartYear;
                        i3 = this.mLunarStartMonth;
                        i2 = this.mLunarStartDay;
                        this.mSpinnerLayout.updateDate(i4, i3, i2);
                        this.mAnimator.setDisplayedChild(1);
                        this.mSpinnerLayout.setEnabled(true);
                        this.mCurrentViewType = i;
                        Message obtainMessage32 = this.mHandler.obtainMessage();
                        obtainMessage32.what = 1000;
                        this.mHandler.sendMessage(obtainMessage32);
                        onViewTypeChangedListener = this.mOnViewTypeChangedListener;
                        onViewTypeChangedListener.onViewTypeChanged(this);
                        Message obtainMessage222 = this.mHandler.obtainMessage();
                        obtainMessage222.what = 1001;
                        this.mHandler.sendMessage(obtainMessage222);
                    } else {
                        i4 = this.mStartDate.get(1);
                        i5 = this.mStartDate.get(2);
                        i6 = this.mStartDate.get(5);
                    }
                    i2 = i6;
                    i3 = i5;
                    this.mSpinnerLayout.updateDate(i4, i3, i2);
                    this.mAnimator.setDisplayedChild(1);
                    this.mSpinnerLayout.setEnabled(true);
                    this.mCurrentViewType = i;
                    Message obtainMessage322 = this.mHandler.obtainMessage();
                    obtainMessage322.what = 1000;
                    this.mHandler.sendMessage(obtainMessage322);
                    onViewTypeChangedListener = this.mOnViewTypeChangedListener;
                    onViewTypeChangedListener.onViewTypeChanged(this);
                    Message obtainMessage2222 = this.mHandler.obtainMessage();
                    obtainMessage2222.what = 1001;
                    this.mHandler.sendMessage(obtainMessage2222);
                }
            } else {
                return;
            }
        } else if (this.mCurrentViewType != i) {
            this.mSpinnerLayout.updateInputState();
            this.mSpinnerLayout.setEditTextMode(false);
            this.mAnimator.setDisplayedChild(0);
            this.mSpinnerLayout.setVisibility(4);
            this.mSpinnerLayout.setEnabled(false);
            this.mCurrentViewType = i;
            Message obtainMessage4 = this.mHandler.obtainMessage();
            obtainMessage4.what = 1000;
            this.mHandler.sendMessage(obtainMessage4);
            this.mCalendarPagerAdapter.notifyDataSetChanged();
            onViewTypeChangedListener = this.mOnViewTypeChangedListener;
            onViewTypeChangedListener.onViewTypeChanged(this);
            Message obtainMessage22222 = this.mHandler.obtainMessage();
            obtainMessage22222.what = 1001;
            this.mHandler.sendMessage(obtainMessage22222);
        }
        z = false;
        onViewTypeChangedListener = this.mOnViewTypeChangedListener;
        onViewTypeChangedListener.onViewTypeChanged(this);
        Message obtainMessage222222 = this.mHandler.obtainMessage();
        obtainMessage222222.what = 1001;
        this.mHandler.sendMessage(obtainMessage222222);
    }

    public int getCurrentViewType() {
        return this.mCurrentViewType;
    }

    public void setEditTextMode(boolean z) {
        if (this.mCurrentViewType != 0) {
            this.mSpinnerLayout.setEditTextMode(z);
        }
    }

    public boolean isEditTextMode() {
        return this.mCurrentViewType != 0 && this.mSpinnerLayout.isEditTextMode();
    }

    public void setOnEditTextModeChangedListener(OnEditTextModeChangedListener onEditTextModeChangedListener) {
        this.mSpinnerLayout.setOnEditTextModeChangedListener(this, onEditTextModeChangedListener);
    }

    public EditText getEditText(int i) {
        return this.mSpinnerLayout.getEditText(i);
    }

    public SeslNumberPicker getNumberPicker(int i) {
        return this.mSpinnerLayout.getNumberPicker(i);
    }

    public void setLunarSupported(boolean z, View view) {
        RelativeLayout.LayoutParams layoutParams;
        RelativeLayout.LayoutParams layoutParams2;
        this.mIsLunarSupported = z;
        if (!this.mIsLunarSupported) {
            this.mIsLunar = false;
            this.mIsLeapMonth = false;
            this.mCustomButtonView = null;
        } else {
            removeCustomViewFromParent();
            this.mCustomButtonView = view;
            if (this.mCustomButtonView != null) {
                removeCustomViewFromParent();
                this.mCustomButtonView.setId(16908331);
                ViewGroup.LayoutParams layoutParams3 = this.mCustomButtonView.getLayoutParams();
                if (layoutParams3 instanceof RelativeLayout.LayoutParams) {
                    layoutParams = (RelativeLayout.LayoutParams) layoutParams3;
                } else {
                    if (layoutParams3 instanceof ViewGroup.MarginLayoutParams) {
                        layoutParams2 = new RelativeLayout.LayoutParams((ViewGroup.MarginLayoutParams) layoutParams3);
                    } else if (layoutParams3 != null) {
                        layoutParams2 = new RelativeLayout.LayoutParams(layoutParams3);
                    } else {
                        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                    }
                    layoutParams = layoutParams2;
                }
                layoutParams.addRule(15);
                layoutParams.addRule(21);
                addCustomButtonInHeader();
            }
        }
        if (this.mIsLunarSupported && this.mPathClassLoader == null) {
            mPackageManager = this.mContext.getApplicationContext().getPackageManager();
            this.mPathClassLoader = LunarUtils.getPathClassLoader(getContext());
            PathClassLoader pathClassLoader = this.mPathClassLoader;
            if (pathClassLoader != null) {
                this.mSolarLunarTables = SeslFeatureReflector.getSolarLunarTables(pathClassLoader);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    public void setLunar(boolean z, boolean z2) {
        if (this.mIsLunarSupported) {
            this.mIsLunar = z;
            this.mIsLeapMonth = z2;
            this.mSpinnerLayout.setLunar(z, z2, this.mPathClassLoader);
            if (z) {
                setTotalMonthCountWithLeap();
                if (this.mMode == 0) {
                    this.mIsLeapStartMonth = z2 ? 1 : 0;
                    this.mIsLeapEndMonth = z2;
                }
            }
            this.mIsFromSetLunar = true;
            this.mCalendarPagerAdapter.notifyDataSetChanged();
            this.mLunarChanged = true;
            updateSimpleMonthView(true);
            this.mLunarChanged = false;
        }
    }

    public boolean isLunar() {
        return this.mIsLunar;
    }

    public boolean isLeapMonth() {
        return this.mIsLeapMonth;
    }

    public void setSeparateLunarButton(boolean z) {
        if (this.mIsCustomButtonSeparate != z) {
            if (z) {
                removeCustomButtonInHeader();
                addCustomButtonSeparateLayout();
            } else {
                removeCustomButtonSeparateLayout();
                addCustomButtonInHeader();
            }
            this.mIsCustomButtonSeparate = z;
        }
    }

    private void removeCustomViewFromParent() {
        View view = this.mCustomButtonView;
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mCustomButtonView);
            }
        }
    }

    private void addCustomButtonInHeader() {
        if (this.mCustomButtonView != null) {
            removeCustomViewFromParent();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mCalendarHeader.getLayoutParams();
            layoutParams.addRule(16, this.mCustomButtonView.getId());
            layoutParams.leftMargin = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sesl_date_picker_lunar_calendar_header_margin);
            ((RelativeLayout.LayoutParams) this.mPrevButton.getLayoutParams()).leftMargin = 0;
            ((RelativeLayout.LayoutParams) this.mNextButton.getLayoutParams()).rightMargin = 0;
            this.mCalendarHeaderLayout.addView(this.mCustomButtonView);
        }
    }

    private void removeCustomButtonInHeader() {
        Resources resources = this.mContext.getResources();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mCalendarHeader.getLayoutParams();
        layoutParams.removeRule(16);
        layoutParams.leftMargin = 0;
        ((RelativeLayout.LayoutParams) this.mPrevButton.getLayoutParams()).leftMargin = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_view_margin);
        ((RelativeLayout.LayoutParams) this.mNextButton.getLayoutParams()).rightMargin = resources.getDimensionPixelOffset(R.dimen.sesl_date_picker_calendar_view_margin);
        removeCustomViewFromParent();
    }

    private void addCustomButtonSeparateLayout() {
        if (this.mCustomButtonView != null) {
            RelativeLayout relativeLayout = this.mCustomButtonLayout;
            if (relativeLayout == null) {
                this.mCustomButtonLayout = new RelativeLayout(this.mContext);
                this.mCustomButtonLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, this.mCalendarHeaderLayoutHeight));
            } else {
                ((LinearLayout.LayoutParams) relativeLayout.getLayoutParams()).height = this.mCalendarHeaderLayoutHeight;
            }
            removeCustomViewFromParent();
            this.mCustomButtonLayout.addView(this.mCustomButtonView);
            this.mDatePickerLayout.addView(this.mCustomButtonLayout, 0);
            this.mDatePickerHeight += this.mCalendarHeaderLayoutHeight;
        }
    }

    private void removeCustomButtonSeparateLayout() {
        removeCustomViewFromParent();
        this.mDatePickerLayout.removeView(this.mCustomButtonLayout);
        this.mDatePickerHeight -= this.mCalendarHeaderLayoutHeight;
    }

    private void setTotalMonthCountWithLeap() {
        if (!(this.mSolarLunarTables == null || this.mPathClassLoader == null)) {
            int i = 0;
            this.mTotalMonthCountWithLeap = new int[((getMaxYear() - getMinYear()) + 1)];
            for (int minYear = getMinYear(); minYear <= getMaxYear(); minYear++) {
                int i2 = 12;
                if (minYear == getMinYear()) {
                    int minMonth = getMinMonth() + 1;
                    int indexOfleapMonthOfYear = getIndexOfleapMonthOfYear(minYear);
                    i2 = (indexOfleapMonthOfYear > 12 || indexOfleapMonthOfYear < minMonth) ? (12 - minMonth) + 1 : (13 - minMonth) + 1;
                } else if (minYear == getMaxYear()) {
                    int maxMonth = getMaxMonth() + 1;
                    int indexOfleapMonthOfYear2 = getIndexOfleapMonthOfYear(minYear);
                    if (indexOfleapMonthOfYear2 <= 12 && maxMonth >= indexOfleapMonthOfYear2) {
                        maxMonth++;
                    }
                    i2 = maxMonth;
                } else if (getIndexOfleapMonthOfYear(minYear) <= 12) {
                    i2 = 13;
                }
                i += i2;
                this.mTotalMonthCountWithLeap[minYear - getMinYear()] = i;
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getTotalMonthCountWithLeap(int i) {
        if (this.mTotalMonthCountWithLeap == null || i < getMinYear()) {
            return 0;
        }
        return this.mTotalMonthCountWithLeap[i - getMinYear()];
    }

    /* JADX DEBUG: Multi-variable search result rejected for r9v1, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r9v2, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r9v4, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private LunarDate getLunarDateByPosition(int i) {
        int i2;
        boolean z;
        int i3;
        LunarDate lunarDate = new LunarDate();
        int minYear = getMinYear();
        int minYear2 = getMinYear();
        while (true) {
            i2 = 0;
            if (minYear2 > getMaxYear()) {
                z = false;
                break;
            } else if (i < getTotalMonthCountWithLeap(minYear2)) {
                if (minYear2 == getMinYear()) {
                    i3 = -getMinMonth();
                } else {
                    i3 = getTotalMonthCountWithLeap(minYear2 - 1);
                }
                int i4 = i - i3;
                int indexOfleapMonthOfYear = getIndexOfleapMonthOfYear(minYear2);
                char c = '\f';
                if (indexOfleapMonthOfYear <= 12) {
                    c = '\r';
                }
                int i5 = i4 < indexOfleapMonthOfYear ? i4 : i4 - 1;
                if (c == '\r' && indexOfleapMonthOfYear == i4) {
                    i2 = 1;
                }
                minYear = minYear2;
                z = i2;
                i2 = i5;
            } else {
                minYear2++;
            }
        }
        lunarDate.set(minYear, i2, 1, z);
        return lunarDate;
    }

    private int getIndexOfleapMonthOfYear(int i) {
        Object obj = this.mSolarLunarTables;
        if (obj == null) {
            return 127;
        }
        int field_START_OF_LUNAR_YEAR = SeslSolarLunarTablesReflector.getField_START_OF_LUNAR_YEAR(this.mPathClassLoader, obj);
        int field_WIDTH_PER_YEAR = SeslSolarLunarTablesReflector.getField_WIDTH_PER_YEAR(this.mPathClassLoader, this.mSolarLunarTables);
        return SeslSolarLunarTablesReflector.getLunar(this.mPathClassLoader, this.mSolarLunarTables, ((i - field_START_OF_LUNAR_YEAR) * field_WIDTH_PER_YEAR) + SeslSolarLunarTablesReflector.getField_INDEX_OF_LEAP_MONTH(this.mPathClassLoader, this.mSolarLunarTables));
    }

    /* access modifiers changed from: private */
    public static class LunarDate {
        public int day;
        boolean isLeapMonth;
        public int month;
        public int year;

        LunarDate() {
            this.year = 1900;
            this.month = 1;
            this.day = 1;
            this.isLeapMonth = false;
        }

        LunarDate(int i, int i2, int i3, boolean z) {
            this.year = i;
            this.month = i2;
            this.day = i3;
            this.isLeapMonth = z;
        }

        public void set(int i, int i2, int i3, boolean z) {
            this.year = i;
            this.month = i2;
            this.day = i3;
            this.isLeapMonth = z;
        }
    }

    private static class LunarUtils {
        private static PathClassLoader mClassLoader;

        private LunarUtils() {
        }

        public static PathClassLoader getPathClassLoader(Context context) {
            if (mClassLoader == null) {
                try {
                    ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(SeslDatePicker.getCalendarPackageName(), 128);
                    if (applicationInfo == null) {
                        Log.e(SeslDatePicker.TAG, "getPathClassLoader, appInfo is null");
                        return null;
                    }
                    String str = applicationInfo.sourceDir;
                    if (str == null || TextUtils.isEmpty(str)) {
                        Log.e(SeslDatePicker.TAG, "getPathClassLoader, calendar package source directory is null or empty");
                        return null;
                    }
                    mClassLoader = new PathClassLoader(str, ClassLoader.getSystemClassLoader());
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e(SeslDatePicker.TAG, "getPathClassLoader, calendar package name not found");
                    return null;
                }
            }
            return mClassLoader;
        }
    }

    /* access modifiers changed from: private */
    public static String getCalendarPackageName() {
        String string = SeslFloatingFeatureReflector.getString("SEC_FLOATING_FEATURE_CALENDAR_CONFIG_PACKAGE_NAME", "com.android.calendar");
        if (!"com.android.calendar".equals(string)) {
            try {
                mPackageManager.getPackageInfo(string, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                return "com.android.calendar";
            }
        }
        return string;
    }

    public void setLunarStartDate(int i, int i2, int i3, boolean z) {
        this.mLunarStartYear = i;
        this.mLunarStartMonth = i2;
        this.mLunarStartDay = i3;
        this.mIsLeapStartMonth = z ? 1 : 0;
    }

    public int[] getLunarStartDate() {
        return new int[]{this.mLunarStartYear, this.mLunarStartMonth, this.mLunarStartDay, this.mIsLeapStartMonth};
    }

    public void setLunarEndDate(int i, int i2, int i3, boolean z) {
        this.mLunarEndYear = i;
        this.mLunarEndMonth = i2;
        this.mLunarEndDay = i3;
        this.mIsLeapEndMonth = z ? 1 : 0;
    }

    public int[] getLunarEndDate() {
        return new int[]{this.mLunarEndYear, this.mLunarEndMonth, this.mLunarEndDay, this.mIsLeapEndMonth};
    }

    private boolean isFarsiLanguage() {
        return "fa".equals(this.mCurrentLocale.getLanguage());
    }

    private boolean isSimplifiedChinese() {
        return this.mCurrentLocale.getLanguage().equals(Locale.SIMPLIFIED_CHINESE.getLanguage()) && this.mCurrentLocale.getCountry().equals(Locale.SIMPLIFIED_CHINESE.getCountry());
    }

    public void requestLayout() {
        super.requestLayout();
        SeslDatePickerSpinnerLayout seslDatePickerSpinnerLayout = this.mSpinnerLayout;
        if (seslDatePickerSpinnerLayout != null && seslDatePickerSpinnerLayout.getVisibility() == 0) {
            this.mSpinnerLayout.requestLayout();
        }
    }
}
