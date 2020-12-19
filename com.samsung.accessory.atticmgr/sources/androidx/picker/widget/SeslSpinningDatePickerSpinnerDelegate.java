package androidx.picker.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormatSymbols;
import android.icu.text.SimpleDateFormat;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import androidx.appcompat.util.SeslMisc;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.picker.R;
import androidx.picker.util.SeslAnimationListener;
import androidx.picker.widget.SeslSpinningDatePicker;
import androidx.picker.widget.SeslSpinningDatePickerSpinner;
import androidx.reflect.content.res.SeslCompatibilityInfoReflector;
import androidx.reflect.content.res.SeslConfigurationReflector;
import androidx.reflect.graphics.SeslPaintReflector;
import androidx.reflect.lunarcalendar.SeslFeatureReflector;
import androidx.reflect.lunarcalendar.SeslSolarLunarConverterReflector;
import androidx.reflect.media.SeslAudioManagerReflector;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import androidx.reflect.view.SeslViewReflector;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* access modifiers changed from: package-private */
public class SeslSpinningDatePickerSpinnerDelegate extends SeslSpinningDatePickerSpinner.AbsDatePickerDelegate {
    private static final int DECREASE_BUTTON = 1;
    private static final int DEFAULT_CHANGE_VALUE_BY = 1;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final int DEFAULT_START_YEAR = 1902;
    private static final int HCF_UNFOCUSED_TEXT_SIZE_DIFF = 2;
    private static final int INCREASE_BUTTON = 3;
    private static final int INPUT = 2;
    private static final int LONG_PRESSED_SCROLL_COUNT = 10;
    private static final int PICKER_VIBRATE_INDEX = 32;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 300;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 4;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 2;
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 5;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 500;
    private static final int START_ANIMATION_SCROLL_DURATION = 857;
    private static final int START_ANIMATION_SCROLL_DURATION_2016B = 557;
    private static final int TEXT_GAP_COUNT = 3;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private final PathInterpolator ALPHA_PATH_INTERPOLATOR = new PathInterpolator(0.17f, 0.17f, 0.83f, 0.83f);
    private final PathInterpolator SIZE_PATH_INTERPOLATOR = new PathInterpolator(0.5f, 0.0f, 0.4f, 1.0f);
    private AccessibilityManager mAccessibilityManager;
    private AccessibilityNodeProviderImpl mAccessibilityNodeProvider;
    private float mActivatedAlpha = 0.4f;
    private final Scroller mAdjustScroller;
    private float mAlpha = this.mIdleAlpha;
    private SeslAnimationListener mAnimationListener;
    private AudioManager mAudioManager;
    private int mBottomSelectionDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private int mChangeValueBy = 1;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private final Scroller mCustomScroller;
    private boolean mCustomTypefaceSet = false;
    private boolean mDecrementVirtualButtonPressed;
    private final Typeface mDefaultTypeface;
    private ValueAnimator mFadeInAnimator;
    private ValueAnimator mFadeOutAnimator;
    private Scroller mFlingScroller;
    private SeslSpinningDatePickerSpinner.Formatter mFormatter;
    private HapticPreDrawListener mHapticPreDrawListener;
    private Typeface mHcfFocusedTypefaceBold;
    private final int mHcfUnfocusedTextSizeDiff;
    private final float mHeightRatio;
    private float mIdleAlpha = 0.1f;
    private boolean mIgnoreMoveEvents;
    private boolean mIncrementVirtualButtonPressed;
    private int mInitialScrollOffset = Integer.MIN_VALUE;
    private final EditText mInputText;
    private boolean mIsHcfEnabled;
    private boolean mIsLeapMonth;
    private boolean mIsLongClicked = false;
    private boolean mIsLongPressed = false;
    private boolean mIsLunar;
    private boolean mIsStartingAnimation = false;
    private boolean mIsValueChanged = false;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastFocusedChildVirtualViewId;
    private int mLastHoveredChildVirtualViewId;
    private final Typeface mLegacyTypeface;
    private final Scroller mLinearScroller;
    private int mLongPressCount;
    private long mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
    private boolean mLongPressed_FIRST_SCROLL;
    private boolean mLongPressed_SECOND_SCROLL;
    private boolean mLongPressed_THIRD_SCROLL;
    private final int mMaxHeight;
    private Calendar mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private final int mMinHeight;
    private Calendar mMinValue;
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private int mModifiedTxtHeight;
    private SeslSpinningDatePickerSpinner.OnScrollListener mOnScrollListener;
    private SeslSpinningDatePickerSpinner.OnSpinnerDateClickListener mOnSpinnerDateClickListener;
    private SeslSpinningDatePickerSpinner.OnValueChangeListener mOnValueChangeListener;
    private PathClassLoader mPathClassLoader = null;
    private boolean mPerformClickOnTap;
    private String mPickerContentDescription;
    private int mPickerSoundIndex;
    private Typeface mPickerTypeface;
    private int mPickerVibrateIndex;
    private final PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private boolean mReservedStartAnimation = false;
    private int mScrollState = 0;
    private final int mSelectionDividerHeight;
    private int mSelectorElementHeight;
    private final HashMap<Calendar, String> mSelectorIndexToStringCache = new HashMap<>();
    private final Calendar[] mSelectorIndices = new Calendar[5];
    private int mSelectorTextGapHeight;
    private Paint mSelectorWheelPaint;
    private boolean mSkipNumbers;
    private Object mSolarLunarConverter = null;
    private final int mTextColor;
    private int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        /* class androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate.AnonymousClass2 */

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            SeslSpinningDatePickerSpinnerDelegate.this.mAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate();
        }
    };
    private Calendar mValue;
    private int mValueChangeOffset;
    private VelocityTracker mVelocityTracker;
    private final Drawable mVirtualButtonFocusedDrawable;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelPreferred = true;

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        return false;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public int getMaxHeight() {
        return 0;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public int getMaxWidth() {
        return 0;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public int getMinHeight() {
        return 0;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public int getMinWidth() {
        return 0;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onWindowVisibilityChanged(int i) {
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setSubTextSize(float f) {
    }

    public SeslSpinningDatePickerSpinnerDelegate(SeslSpinningDatePickerSpinner seslSpinningDatePickerSpinner, Context context, AttributeSet attributeSet, int i, int i2) {
        super(seslSpinningDatePickerSpinner, context);
        int i3;
        int i4;
        int i5;
        Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sesl_number_picker_spinner_height);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.sesl_number_picker_spinner_width);
        this.mHeightRatio = ((float) resources.getDimensionPixelSize(R.dimen.sesl_number_picker_spinner_edit_text_height)) / ((float) dimensionPixelSize);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NumberPicker, i, i2);
        this.mMinHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_internalMinHeight, -1);
        this.mMaxHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_internalMaxHeight, dimensionPixelSize);
        this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_internalMinWidth, dimensionPixelSize2);
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_internalMaxWidth, -1);
        obtainStyledAttributes.recycle();
        this.mValue = getCalendarForLocale(this.mValue, Locale.getDefault());
        this.mMinValue = getCalendarForLocale(this.mMinValue, Locale.getDefault());
        this.mMaxValue = getCalendarForLocale(this.mMaxValue, Locale.getDefault());
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.DatePicker, i, i2);
        this.mMinValue.set(obtainStyledAttributes2.getInt(R.styleable.DatePicker_android_startYear, DEFAULT_START_YEAR), 0, 1);
        this.mMaxValue.set(obtainStyledAttributes2.getInt(R.styleable.DatePicker_android_endYear, DEFAULT_END_YEAR), 11, 31);
        int i6 = this.mMinHeight;
        if (i6 == -1 || (i5 = this.mMaxHeight) == -1 || i6 <= i5) {
            int i7 = this.mMinWidth;
            if (i7 == -1 || (i4 = this.mMaxWidth) == -1 || i7 <= i4) {
                this.mSelectionDividerHeight = (int) TypedValue.applyDimension(1, 2.0f, resources.getDisplayMetrics());
                this.mComputeMaxWidth = this.mMaxWidth == -1;
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimaryDark, typedValue, true);
                if (typedValue.resourceId != 0) {
                    i3 = ResourcesCompat.getColor(resources, typedValue.resourceId, null);
                } else {
                    i3 = typedValue.data;
                }
                this.mVirtualButtonFocusedDrawable = new ColorDrawable((i3 & ViewCompat.MEASURED_SIZE_MASK) | 855638016);
                if (!SeslMisc.isLightTheme(this.mContext)) {
                    this.mIdleAlpha = 0.2f;
                    this.mAlpha = 0.2f;
                }
                this.mPressedStateHelper = new PressedStateHelper();
                this.mDelegator.setWillNotDraw(false);
                ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.sesl_spinning_date_picker_spinner, (ViewGroup) this.mDelegator, true);
                this.mInputText = (EditText) this.mDelegator.findViewById(R.id.datepicker_input);
                this.mInputText.setIncludeFontPadding(false);
                this.mDefaultTypeface = Typeface.defaultFromStyle(0);
                this.mLegacyTypeface = Typeface.create("sec-roboto-condensed-light", 0);
                this.mPickerTypeface = Typeface.create("sec-roboto-light", 0);
                if (this.mDefaultTypeface.equals(this.mPickerTypeface)) {
                    if (!this.mLegacyTypeface.equals(this.mPickerTypeface)) {
                        this.mPickerTypeface = this.mLegacyTypeface;
                    } else {
                        this.mPickerTypeface = Typeface.create("sans-serif-thin", 0);
                    }
                }
                if (!SeslConfigurationReflector.isDexEnabled(resources.getConfiguration())) {
                    String string = Settings.System.getString(this.mContext.getContentResolver(), "theme_font_clock");
                    if (string != null && !string.isEmpty()) {
                        this.mPickerTypeface = getFontTypeface(string);
                    }
                } else {
                    this.mIdleAlpha = 0.2f;
                    this.mAlpha = 0.2f;
                }
                if (isCharacterNumberLanguage()) {
                    this.mPickerTypeface = this.mDefaultTypeface;
                }
                this.mIsHcfEnabled = isHighContrastFontEnabled();
                this.mHcfFocusedTypefaceBold = Typeface.create(this.mPickerTypeface, 1);
                this.mHcfUnfocusedTextSizeDiff = (int) TypedValue.applyDimension(1, 2.0f, this.mContext.getResources().getDisplayMetrics());
                setInputTextTypeface();
                this.mTextColor = this.mInputText.getTextColors().getColorForState(this.mDelegator.getEnableStateSet(), -1);
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
                this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity() * 2;
                this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity() / 4;
                this.mTextSize = (int) this.mInputText.getTextSize();
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize((float) this.mTextSize);
                paint.setTypeface(this.mPickerTypeface);
                paint.setColor(this.mTextColor);
                this.mSelectorWheelPaint = paint;
                this.mCustomScroller = new Scroller(this.mContext, this.SIZE_PATH_INTERPOLATOR, true);
                this.mLinearScroller = new Scroller(this.mContext, null, true);
                this.mFlingScroller = this.mLinearScroller;
                this.mAdjustScroller = new Scroller(this.mContext, new PathInterpolator(0.4f, 0.0f, 0.3f, 1.0f));
                setFormatter(SeslSpinningDatePickerSpinner.getDateFormatter());
                this.mDelegator.setVerticalScrollBarEnabled(false);
                if (this.mDelegator.getImportantForAccessibility() == 0) {
                    this.mDelegator.setImportantForAccessibility(1);
                }
                this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
                this.mHapticPreDrawListener = new HapticPreDrawListener();
                this.mPickerVibrateIndex = SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(32);
                this.mPickerSoundIndex = SeslAudioManagerReflector.getField_SOUND_TIME_PICKER_SCROLL();
                this.mDelegator.setFocusableInTouchMode(false);
                this.mDelegator.setDescendantFocusability(131072);
                if (Build.VERSION.SDK_INT >= 26) {
                    this.mDelegator.setDefaultFocusHighlightEnabled(false);
                }
                this.mPickerContentDescription = "";
                SeslViewReflector.semSetDirectPenInputEnabled(this.mInputText, false);
                this.mAccessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
                this.mFadeOutAnimator = ValueAnimator.ofFloat(this.mActivatedAlpha, this.mIdleAlpha);
                this.mFadeOutAnimator.setInterpolator(this.ALPHA_PATH_INTERPOLATOR);
                this.mFadeOutAnimator.setDuration(500L);
                this.mFadeOutAnimator.setStartDelay(500);
                this.mFadeOutAnimator.addUpdateListener(this.mUpdateListener);
                this.mFadeInAnimator = ValueAnimator.ofFloat(this.mIdleAlpha, this.mActivatedAlpha);
                this.mFadeInAnimator.setInterpolator(this.ALPHA_PATH_INTERPOLATOR);
                this.mFadeInAnimator.setDuration(100L);
                this.mFadeInAnimator.addUpdateListener(this.mUpdateListener);
                return;
            }
            throw new IllegalArgumentException("minWidth > maxWidth");
        }
        throw new IllegalArgumentException("minHeight > maxHeight");
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setPickerContentDescription(String str) {
        this.mPickerContentDescription = str;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = this.mDelegator.getMeasuredWidth();
        int measuredHeight = this.mDelegator.getMeasuredHeight();
        int measuredWidth2 = this.mInputText.getMeasuredWidth();
        int max = Math.max(this.mInputText.getMeasuredHeight(), (int) Math.floor((double) (((float) measuredHeight) * this.mHeightRatio)));
        this.mModifiedTxtHeight = max;
        int i5 = (measuredWidth - measuredWidth2) / 2;
        int i6 = (measuredHeight - max) / 2;
        int i7 = max + i6;
        this.mInputText.layout(i5, i6, measuredWidth2 + i5, i7);
        if (z) {
            initializeSelectorWheel();
            if (this.mModifiedTxtHeight > this.mSelectorElementHeight) {
                int i8 = this.mValueChangeOffset;
                this.mTopSelectionDividerTop = i8;
                this.mBottomSelectionDividerBottom = i8 * 2;
                return;
            }
            this.mTopSelectionDividerTop = i6;
            this.mBottomSelectionDividerBottom = i7;
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onMeasure(int i, int i2) {
        this.mDelegator.superOnMeasure(makeMeasureSpec(i, this.mMaxWidth), makeMeasureSpec(i2, this.mMaxHeight));
        this.mDelegator.setMeasuredDimensionWrapper(resolveSizeAndStateRespectingMinSize(this.mMinWidth, this.mDelegator.getMeasuredWidth(), i), resolveSizeAndStateRespectingMinSize(this.mMinHeight, this.mDelegator.getMeasuredHeight(), i2));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        int i;
        scroller.forceFinished(true);
        int finalY = scroller.getFinalY() - scroller.getCurrY();
        int i2 = this.mSelectorElementHeight;
        if (i2 == 0 || (i = this.mInitialScrollOffset - (this.mCurrentScrollOffset + finalY)) == 0) {
            return false;
        }
        int i3 = i % i2;
        int abs = Math.abs(i3);
        int i4 = this.mSelectorElementHeight;
        if (abs > i4 / 2) {
            i3 = i3 > 0 ? i3 - i4 : i3 + i4;
        }
        scrollBy(0, finalY + i3);
        return true;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onWindowFocusChanged(boolean z) {
        if (!this.mIsStartingAnimation) {
            if (!this.mFlingScroller.isFinished()) {
                this.mFlingScroller.forceFinished(true);
            }
            if (!this.mAdjustScroller.isFinished()) {
                this.mAdjustScroller.forceFinished(true);
            }
            ensureScrollWheelAdjusted();
        }
        this.mIsHcfEnabled = isHighContrastFontEnabled();
        this.mSelectorWheelPaint.setTextSize((float) this.mTextSize);
        this.mSelectorWheelPaint.setTypeface(this.mPickerTypeface);
        setInputTextTypeface();
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mDelegator.isEnabled() || this.mIsStartingAnimation || motionEvent.getActionMasked() != 0) {
            return false;
        }
        removeAllCallbacks();
        float y = motionEvent.getY();
        this.mLastDownEventY = y;
        this.mLastDownOrMoveEventY = y;
        this.mLastDownEventTime = motionEvent.getEventTime();
        this.mIgnoreMoveEvents = false;
        this.mPerformClickOnTap = false;
        this.mIsValueChanged = false;
        float f = this.mLastDownEventY;
        if (f < ((float) this.mTopSelectionDividerTop)) {
            startFadeAnimation(false);
            if (this.mScrollState == 0) {
                this.mPressedStateHelper.buttonPressDelayed(2);
            }
        } else if (f > ((float) this.mBottomSelectionDividerBottom)) {
            startFadeAnimation(false);
            if (this.mScrollState == 0) {
                this.mPressedStateHelper.buttonPressDelayed(1);
            }
        }
        this.mDelegator.getParent().requestDisallowInterceptTouchEvent(true);
        if (!this.mFlingScroller.isFinished()) {
            this.mFlingScroller.forceFinished(true);
            this.mAdjustScroller.forceFinished(true);
            if (this.mScrollState == 2) {
                this.mFlingScroller.abortAnimation();
                this.mAdjustScroller.abortAnimation();
            }
            onScrollStateChange(0);
        } else if (!this.mAdjustScroller.isFinished()) {
            this.mFlingScroller.forceFinished(true);
            this.mAdjustScroller.forceFinished(true);
        } else {
            float f2 = this.mLastDownEventY;
            if (f2 < ((float) this.mTopSelectionDividerTop)) {
                postChangeCurrentByOneFromLongPress(false, (long) ViewConfiguration.getLongPressTimeout());
            } else if (f2 > ((float) this.mBottomSelectionDividerBottom)) {
                postChangeCurrentByOneFromLongPress(true, (long) ViewConfiguration.getLongPressTimeout());
            } else {
                this.mPerformClickOnTap = true;
            }
        }
        return true;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mDelegator.isEnabled() || this.mIsStartingAnimation) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            removeChangeCurrentByOneFromLongPress();
            this.mPressedStateHelper.cancel();
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
            int yVelocity = (int) velocityTracker.getYVelocity();
            int y = (int) motionEvent.getY();
            int abs = (int) Math.abs(((float) y) - this.mLastDownEventY);
            if (Math.abs(yVelocity) <= this.mMinimumFlingVelocity) {
                long eventTime = motionEvent.getEventTime() - this.mLastDownEventTime;
                if (abs > this.mTouchSlop || eventTime >= ((long) ViewConfiguration.getLongPressTimeout())) {
                    if (this.mIsLongClicked) {
                        this.mIsLongClicked = false;
                    }
                    ensureScrollWheelAdjusted(abs);
                    startFadeAnimation(true);
                } else if (this.mPerformClickOnTap) {
                    this.mPerformClickOnTap = false;
                    performClick();
                } else {
                    if (y > this.mBottomSelectionDividerBottom) {
                        changeValueByOne(true);
                        this.mPressedStateHelper.buttonTapped(1);
                    } else if (y < this.mTopSelectionDividerTop) {
                        changeValueByOne(false);
                        this.mPressedStateHelper.buttonTapped(2);
                    } else {
                        ensureScrollWheelAdjusted(abs);
                    }
                    startFadeAnimation(true);
                }
                this.mIsValueChanged = false;
                onScrollStateChange(0);
            } else if (abs > this.mTouchSlop || !this.mPerformClickOnTap) {
                fling(yVelocity);
                onScrollStateChange(2);
                startFadeAnimation(true);
            } else {
                this.mPerformClickOnTap = false;
                performClick();
                onScrollStateChange(0);
            }
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
                ensureScrollWheelAdjusted();
                startFadeAnimation(true);
                onScrollStateChange(0);
            }
        } else if (!this.mIgnoreMoveEvents) {
            float y2 = motionEvent.getY();
            if (this.mScrollState == 1) {
                scrollBy(0, (int) (y2 - this.mLastDownOrMoveEventY));
                this.mDelegator.invalidate();
            } else if (((int) Math.abs(y2 - this.mLastDownEventY)) > this.mTouchSlop) {
                removeAllCallbacks();
                startFadeAnimation(false);
                onScrollStateChange(1);
            }
            this.mLastDownOrMoveEventY = y2;
        }
        return true;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1 && actionMasked != 3) {
            return false;
        }
        removeAllCallbacks();
        return false;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (this.mDelegator.isEnabled() && !this.mIsStartingAnimation && (motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
            float axisValue = motionEvent.getAxisValue(9);
            if (axisValue != 0.0f) {
                startFadeAnimation(false);
                if (axisValue < 0.0f) {
                    z = true;
                }
                changeValueByOne(z);
                startFadeAnimation(true);
                return true;
            }
        }
        return false;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mCustomTypefaceSet) {
            if (isCharacterNumberLanguage()) {
                this.mInputText.setIncludeFontPadding(true);
                this.mPickerTypeface = this.mDefaultTypeface;
                this.mHcfFocusedTypefaceBold = Typeface.create(this.mPickerTypeface, 1);
                setInputTextTypeface();
                return;
            }
            this.mInputText.setIncludeFontPadding(false);
            setInputTextTypeface();
            tryComputeMaxWidth();
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onFocusChanged(boolean z, int i, Rect rect) {
        AccessibilityNodeProviderImpl accessibilityNodeProviderImpl;
        AccessibilityNodeProviderImpl accessibilityNodeProviderImpl2;
        if (!z) {
            if (this.mAccessibilityManager.isEnabled() && (accessibilityNodeProviderImpl2 = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider()) != null) {
                accessibilityNodeProviderImpl2.performAction(this.mLastFocusedChildVirtualViewId, 128, null);
            }
            this.mLastFocusedChildVirtualViewId = -1;
            this.mLastHoveredChildVirtualViewId = Integer.MIN_VALUE;
        } else {
            InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            }
            this.mLastFocusedChildVirtualViewId = 1;
            if (!this.mWrapSelectorWheel && getValue().equals(getMinValue())) {
                this.mLastFocusedChildVirtualViewId = 2;
            }
            if (this.mAccessibilityManager.isEnabled() && (accessibilityNodeProviderImpl = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider()) != null) {
                accessibilityNodeProviderImpl.performAction(this.mLastFocusedChildVirtualViewId, 64, null);
            }
        }
        this.mDelegator.invalidate();
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int i;
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        if (!(keyCode == 66 || keyCode == 160)) {
            switch (keyCode) {
                case 19:
                case 20:
                    if (action == 0) {
                        if (keyCode == 20) {
                            int i2 = this.mLastFocusedChildVirtualViewId;
                            if (i2 == 1) {
                                this.mLastFocusedChildVirtualViewId = 2;
                                this.mDelegator.invalidate();
                                return true;
                            } else if (i2 != 2) {
                                return i2 != 3 ? false : false;
                            } else {
                                if (!this.mWrapSelectorWheel && getValue().equals(getMaxValue())) {
                                    return false;
                                }
                                this.mLastFocusedChildVirtualViewId = 3;
                                this.mDelegator.invalidate();
                                return true;
                            }
                        } else if (keyCode != 19 || (i = this.mLastFocusedChildVirtualViewId) == 1) {
                            return false;
                        } else {
                            if (i != 2) {
                                if (i == 3) {
                                    this.mLastFocusedChildVirtualViewId = 2;
                                    this.mDelegator.invalidate();
                                    return true;
                                }
                            } else if (!this.mWrapSelectorWheel && getValue().equals(getMinValue())) {
                                return false;
                            } else {
                                this.mLastFocusedChildVirtualViewId = 1;
                                this.mDelegator.invalidate();
                                return true;
                            }
                        }
                    } else if (action == 1 && this.mAccessibilityManager.isEnabled()) {
                        AccessibilityNodeProviderImpl accessibilityNodeProviderImpl = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider();
                        if (accessibilityNodeProviderImpl != null) {
                            accessibilityNodeProviderImpl.performAction(this.mLastFocusedChildVirtualViewId, 64, null);
                        }
                        return true;
                    }
                    break;
                case 21:
                case 22:
                    if (action == 0) {
                        if (keyCode == 21) {
                            View focusSearch = this.mDelegator.focusSearch(17);
                            if (focusSearch != null) {
                                focusSearch.requestFocus(17);
                            }
                            return true;
                        } else if (keyCode == 22) {
                            View focusSearch2 = this.mDelegator.focusSearch(66);
                            if (focusSearch2 != null) {
                                focusSearch2.requestFocus(66);
                            }
                            return true;
                        }
                    }
                    break;
            }
        }
        if (action == 0) {
            if (this.mLastFocusedChildVirtualViewId == 2) {
                performClick();
                removeAllCallbacks();
            } else if (this.mFlingScroller.isFinished()) {
                int i3 = this.mLastFocusedChildVirtualViewId;
                if (i3 == 1) {
                    startFadeAnimation(false);
                    changeValueByOne(false);
                    Calendar calendar = (Calendar) getMinValue().clone();
                    calendar.add(5, 1);
                    if (!this.mWrapSelectorWheel && getValue().equals(calendar)) {
                        this.mLastFocusedChildVirtualViewId = 2;
                    }
                    startFadeAnimation(true);
                } else if (i3 == 3) {
                    startFadeAnimation(false);
                    changeValueByOne(true);
                    Calendar calendar2 = (Calendar) getMaxValue().clone();
                    calendar2.add(5, -1);
                    if (!this.mWrapSelectorWheel && getValue().equals(calendar2)) {
                        this.mLastFocusedChildVirtualViewId = 2;
                    }
                    startFadeAnimation(true);
                }
            }
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void dispatchTrackballEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int i;
        if (!this.mAccessibilityManager.isEnabled()) {
            return false;
        }
        int y = (int) motionEvent.getY();
        if (y <= this.mTopSelectionDividerTop) {
            i = 1;
        } else {
            i = this.mBottomSelectionDividerBottom <= y ? 3 : 2;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 7 || actionMasked == 9) {
            updateHoveredVirtualView(i);
            if (i != Integer.MIN_VALUE) {
                return true;
            }
            return false;
        } else if (actionMasked != 10 || this.mLastHoveredChildVirtualViewId == Integer.MIN_VALUE) {
            return false;
        } else {
            updateHoveredVirtualView(Integer.MIN_VALUE);
            return true;
        }
    }

    private void updateHoveredVirtualView(int i) {
        int i2 = this.mLastHoveredChildVirtualViewId;
        if (i2 != i) {
            this.mLastHoveredChildVirtualViewId = i;
            AccessibilityNodeProviderImpl accessibilityNodeProviderImpl = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider();
            accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i, 128);
            accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i2, 256);
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setSkipValuesOnLongPressEnabled(boolean z) {
        this.mSkipNumbers = z;
    }

    private void playSoundAndHapticFeedback() {
        this.mAudioManager.playSoundEffect(this.mPickerSoundIndex);
        if (!this.mHapticPreDrawListener.mSkipHapticCalls) {
            this.mDelegator.performHapticFeedback(this.mPickerVibrateIndex);
            this.mHapticPreDrawListener.mSkipHapticCalls = true;
        }
    }

    /* access modifiers changed from: private */
    public static class HapticPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        boolean mSkipHapticCalls;

        private HapticPreDrawListener() {
            this.mSkipHapticCalls = false;
        }

        public boolean onPreDraw() {
            this.mSkipHapticCalls = false;
            return true;
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void computeScroll() {
        Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        int currY = scroller.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller.getStartY();
        }
        scrollBy(0, currY - this.mPreviousScrollerY);
        this.mPreviousScrollerY = currY;
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            this.mDelegator.invalidate();
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setEnabled(boolean z) {
        if (!z && this.mScrollState != 0) {
            stopScrollAnimation();
            onScrollStateChange(0);
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void scrollBy(int i, int i2) {
        Calendar[] calendarArr = this.mSelectorIndices;
        if (i2 != 0 && this.mSelectorElementHeight > 0) {
            if (!this.mWrapSelectorWheel && this.mCurrentScrollOffset + i2 > this.mInitialScrollOffset && calendarArr[2].compareTo(this.mMinValue) <= 0) {
                i2 = this.mInitialScrollOffset - this.mCurrentScrollOffset;
            }
            if (!this.mWrapSelectorWheel && this.mCurrentScrollOffset + i2 < this.mInitialScrollOffset && calendarArr[2].compareTo(this.mMaxValue) >= 0) {
                i2 = this.mInitialScrollOffset - this.mCurrentScrollOffset;
            }
            this.mCurrentScrollOffset += i2;
            while (true) {
                int i3 = this.mCurrentScrollOffset;
                if (i3 - this.mInitialScrollOffset < this.mValueChangeOffset) {
                    break;
                }
                this.mCurrentScrollOffset = i3 - this.mSelectorElementHeight;
                decrementSelectorIndices(calendarArr);
                if (!this.mIsStartingAnimation) {
                    setValueInternal(calendarArr[2], true);
                    this.mIsValueChanged = true;
                    int i4 = this.mLongPressCount;
                    if (i4 > 0) {
                        this.mLongPressCount = i4 - 1;
                    } else {
                        playSoundAndHapticFeedback();
                    }
                }
                if (!this.mWrapSelectorWheel && calendarArr[2].compareTo(this.mMinValue) <= 0) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            while (true) {
                int i5 = this.mCurrentScrollOffset;
                if (i5 - this.mInitialScrollOffset <= (-this.mValueChangeOffset)) {
                    this.mCurrentScrollOffset = i5 + this.mSelectorElementHeight;
                    incrementSelectorIndices(calendarArr);
                    if (!this.mIsStartingAnimation) {
                        setValueInternal(calendarArr[2], true);
                        this.mIsValueChanged = true;
                        int i6 = this.mLongPressCount;
                        if (i6 > 0) {
                            this.mLongPressCount = i6 - 1;
                        } else {
                            playSoundAndHapticFeedback();
                        }
                    }
                    if (!this.mWrapSelectorWheel && calendarArr[2].compareTo(this.mMaxValue) >= 0) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                    }
                } else {
                    return;
                }
            }
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public int computeVerticalScrollOffset() {
        return this.mCurrentScrollOffset;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public int computeVerticalScrollRange() {
        return (((int) TimeUnit.MILLISECONDS.toDays(this.mMaxValue.getTimeInMillis() - this.mMinValue.getTimeInMillis())) + 1) * this.mSelectorElementHeight;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public int computeVerticalScrollExtent() {
        return this.mDelegator.getHeight();
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setOnValueChangedListener(SeslSpinningDatePickerSpinner.OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setOnScrollListener(SeslSpinningDatePickerSpinner.OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setOnSpinnerDateClickListener(SeslSpinningDatePickerSpinner.OnSpinnerDateClickListener onSpinnerDateClickListener) {
        this.mOnSpinnerDateClickListener = onSpinnerDateClickListener;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public SeslSpinningDatePickerSpinner.OnSpinnerDateClickListener getOnSpinnerDateClickListener() {
        return this.mOnSpinnerDateClickListener;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setFormatter(SeslSpinningDatePickerSpinner.Formatter formatter) {
        if (formatter != this.mFormatter) {
            this.mFormatter = formatter;
            initializeSelectorWheelIndices();
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setValue(Calendar calendar) {
        if (!this.mFlingScroller.isFinished()) {
            stopScrollAnimation();
        }
        setValueInternal(calendar, false);
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void performClick() {
        SeslSpinningDatePickerSpinner.OnSpinnerDateClickListener onSpinnerDateClickListener = this.mOnSpinnerDateClickListener;
        if (onSpinnerDateClickListener != null) {
            onSpinnerDateClickListener.onSpinnerDateClicked(this.mIsLunar ? convertSolarToLunar(this.mValue, null) : this.mValue);
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void performClick(boolean z) {
        changeValueByOne(z);
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void performLongClick() {
        this.mIgnoreMoveEvents = true;
        this.mIsLongClicked = true;
    }

    private void tryComputeMaxWidth() {
        if (this.mComputeMaxWidth) {
            float f = 0.0f;
            float f2 = 0.0f;
            for (int i = 0; i <= 9; i++) {
                float measureText = this.mSelectorWheelPaint.measureText(formatNumberWithLocale(i));
                if (measureText > f2) {
                    f2 = measureText;
                }
            }
            float f3 = (float) ((int) (((float) 2) * f2));
            float f4 = 0.0f;
            for (String str : new DateFormatSymbols(Locale.getDefault()).getShortWeekdays()) {
                float measureText2 = this.mSelectorWheelPaint.measureText(str);
                if (measureText2 > f4) {
                    f4 = measureText2;
                }
            }
            for (String str2 : new DateFormatSymbols(Locale.getDefault()).getShortMonths()) {
                float measureText3 = this.mSelectorWheelPaint.measureText(str2);
                if (measureText3 > f) {
                    f = measureText3;
                }
            }
            int measureText4 = ((int) (f3 + f4 + f + (this.mSelectorWheelPaint.measureText(" ") * 2.0f) + this.mSelectorWheelPaint.measureText(","))) + this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight();
            if (isHighContrastFontEnabled()) {
                measureText4 += ((int) Math.ceil((double) (SeslPaintReflector.getHCTStrokeWidth(this.mSelectorWheelPaint) / 2.0f))) * 13;
            }
            if (this.mMaxWidth != measureText4) {
                int i2 = this.mMinWidth;
                if (measureText4 > i2) {
                    this.mMaxWidth = measureText4;
                } else {
                    this.mMaxWidth = i2;
                }
                this.mDelegator.invalidate();
            }
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setWrapSelectorWheel(boolean z) {
        this.mWrapSelectorWheelPreferred = z;
        updateWrapSelectorWheel();
    }

    private void updateWrapSelectorWheel() {
        boolean z = true;
        if (!(((int) TimeUnit.MILLISECONDS.toDays(this.mMaxValue.getTimeInMillis() - this.mMinValue.getTimeInMillis())) >= this.mSelectorIndices.length) || !this.mWrapSelectorWheelPreferred) {
            z = false;
        }
        if (this.mWrapSelectorWheel != z) {
            this.mWrapSelectorWheel = z;
            initializeSelectorWheelIndices();
            this.mDelegator.invalidate();
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setOnLongPressUpdateInterval(long j) {
        this.mLongPressUpdateInterval = j;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public Calendar getValue() {
        return this.mValue;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public Calendar getMinValue() {
        return this.mMinValue;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setMinValue(Calendar calendar) {
        if (!this.mMinValue.equals(calendar)) {
            clearCalendar(this.mMinValue, calendar);
            if (this.mMinValue.compareTo(this.mValue) > 0) {
                clearCalendar(this.mValue, this.mMinValue);
            }
            updateWrapSelectorWheel();
            initializeSelectorWheelIndices();
            tryComputeMaxWidth();
            this.mDelegator.invalidate();
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public Calendar getMaxValue() {
        return this.mMaxValue;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setMaxValue(Calendar calendar) {
        if (!this.mMaxValue.equals(calendar)) {
            clearCalendar(this.mMaxValue, calendar);
            if (this.mMaxValue.compareTo(this.mValue) < 0) {
                clearCalendar(this.mValue, this.mMaxValue);
            }
            updateWrapSelectorWheel();
            initializeSelectorWheelIndices();
            tryComputeMaxWidth();
            this.mDelegator.invalidate();
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setTextSize(float f) {
        this.mTextSize = (int) TypedValue.applyDimension(1, f, this.mContext.getResources().getDisplayMetrics());
        this.mSelectorWheelPaint.setTextSize((float) this.mTextSize);
        this.mInputText.setTextSize(0, (float) this.mTextSize);
        tryComputeMaxWidth();
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setTextTypeface(Typeface typeface) {
        this.mCustomTypefaceSet = true;
        this.mPickerTypeface = typeface;
        this.mSelectorWheelPaint.setTypeface(this.mPickerTypeface);
        this.mHcfFocusedTypefaceBold = Typeface.create(this.mPickerTypeface, 1);
        setInputTextTypeface();
        tryComputeMaxWidth();
    }

    private void setInputTextTypeface() {
        if (this.mIsHcfEnabled) {
            this.mInputText.setTypeface(this.mHcfFocusedTypefaceBold);
        } else {
            this.mInputText.setTypeface(this.mPickerTypeface);
        }
    }

    private void setHcfTextAppearance(boolean z) {
        if (this.mIsHcfEnabled) {
            if (z) {
                this.mSelectorWheelPaint.setTypeface(this.mHcfFocusedTypefaceBold);
                this.mSelectorWheelPaint.setTextSize((float) this.mTextSize);
                return;
            }
            this.mSelectorWheelPaint.setTypeface(this.mPickerTypeface);
            this.mSelectorWheelPaint.setTextSize((float) (this.mTextSize - this.mHcfUnfocusedTextSizeDiff));
        }
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

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public int getPaintFlags() {
        return this.mSelectorWheelPaint.getFlags();
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setPaintFlags(int i) {
        if (this.mSelectorWheelPaint.getFlags() != i) {
            this.mSelectorWheelPaint.setFlags(i);
            this.mInputText.setPaintFlags(i);
            tryComputeMaxWidth();
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void startAnimation(final int i, SeslAnimationListener seslAnimationListener) {
        this.mAnimationListener = seslAnimationListener;
        this.mAlpha = this.mActivatedAlpha;
        this.mDelegator.post(new Runnable() {
            /* class androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate.AnonymousClass1 */

            public void run() {
                if (SeslSpinningDatePickerSpinnerDelegate.this.mSelectorElementHeight == 0) {
                    SeslSpinningDatePickerSpinnerDelegate.this.mReservedStartAnimation = true;
                    return;
                }
                SeslSpinningDatePickerSpinnerDelegate.this.mIsStartingAnimation = true;
                SeslSpinningDatePickerSpinnerDelegate seslSpinningDatePickerSpinnerDelegate = SeslSpinningDatePickerSpinnerDelegate.this;
                seslSpinningDatePickerSpinnerDelegate.mFlingScroller = seslSpinningDatePickerSpinnerDelegate.mCustomScroller;
                final int i = (int) (((double) SeslSpinningDatePickerSpinnerDelegate.this.mSelectorElementHeight) * 5.4d);
                SeslSpinningDatePickerSpinnerDelegate.this.scrollBy(0, SeslSpinningDatePickerSpinnerDelegate.this.mSelectorElementHeight * 5);
                SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate();
                new Handler().postDelayed(new Runnable() {
                    /* class androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate.AnonymousClass1.AnonymousClass1 */

                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            /* class androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate.AnonymousClass1.AnonymousClass1.AnonymousClass1 */

                            public void run() {
                                if (!SeslSpinningDatePickerSpinnerDelegate.this.moveToFinalScrollerPosition(SeslSpinningDatePickerSpinnerDelegate.this.mFlingScroller)) {
                                    SeslSpinningDatePickerSpinnerDelegate.this.moveToFinalScrollerPosition(SeslSpinningDatePickerSpinnerDelegate.this.mAdjustScroller);
                                }
                                SeslSpinningDatePickerSpinnerDelegate.this.mPreviousScrollerY = 0;
                                SeslSpinningDatePickerSpinnerDelegate.this.mFlingScroller.startScroll(0, 0, 0, -i, SeslSpinningDatePickerSpinnerDelegate.START_ANIMATION_SCROLL_DURATION_2016B);
                                SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate();
                                new Handler().postDelayed(new Runnable() {
                                    /* class androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate.AnonymousClass1.AnonymousClass1.AnonymousClass1.AnonymousClass1 */

                                    public void run() {
                                        SeslSpinningDatePickerSpinnerDelegate.this.moveToFinalScrollerPosition(SeslSpinningDatePickerSpinnerDelegate.this.mFlingScroller);
                                        SeslSpinningDatePickerSpinnerDelegate.this.mFlingScroller.abortAnimation();
                                        SeslSpinningDatePickerSpinnerDelegate.this.mAdjustScroller.abortAnimation();
                                        SeslSpinningDatePickerSpinnerDelegate.this.ensureScrollWheelAdjusted();
                                        SeslSpinningDatePickerSpinnerDelegate.this.mFlingScroller = SeslSpinningDatePickerSpinnerDelegate.this.mLinearScroller;
                                        SeslSpinningDatePickerSpinnerDelegate.this.mIsStartingAnimation = false;
                                        SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate();
                                        SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(true);
                                        if (SeslSpinningDatePickerSpinnerDelegate.this.mAnimationListener != null) {
                                            SeslSpinningDatePickerSpinnerDelegate.this.mAnimationListener.onAnimationEnd();
                                        }
                                    }
                                }, 857);
                            }
                        }, 100);
                    }
                }, (long) i);
            }
        });
    }

    private void stopScrollAnimation() {
        this.mFlingScroller.abortAnimation();
        this.mAdjustScroller.abortAnimation();
        if (!this.mIsStartingAnimation && !moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        ensureScrollWheelAdjusted();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startFadeAnimation(boolean z) {
        if (z) {
            this.mFadeOutAnimator.setStartDelay((long) (this.mFlingScroller.getDuration() + 500));
            this.mFadeOutAnimator.start();
            return;
        }
        this.mFadeInAnimator.setFloatValues(this.mAlpha, this.mActivatedAlpha);
        this.mFadeOutAnimator.cancel();
        this.mFadeInAnimator.start();
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onDetachedFromWindow() {
        removeAllCallbacks();
        this.mDelegator.getViewTreeObserver().removeOnPreDrawListener(this.mHapticPreDrawListener);
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onAttachedToWindow() {
        this.mDelegator.getViewTreeObserver().addOnPreDrawListener(this.mHapticPreDrawListener);
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onDraw(Canvas canvas) {
        boolean z;
        int right = this.mDelegator.getRight();
        int left = this.mDelegator.getLeft();
        int bottom = this.mDelegator.getBottom();
        float f = 2.0f;
        float f2 = ((float) (right - left)) / 2.0f;
        float f3 = (float) (this.mCurrentScrollOffset - this.mSelectorElementHeight);
        Drawable drawable = this.mVirtualButtonFocusedDrawable;
        if (drawable != null && this.mScrollState == 0) {
            int i = this.mLastFocusedChildVirtualViewId;
            if (i == 1) {
                drawable.setState(this.mDelegator.getDrawableState());
                this.mVirtualButtonFocusedDrawable.setBounds(0, 0, right, this.mTopSelectionDividerTop);
                this.mVirtualButtonFocusedDrawable.draw(canvas);
            } else if (i == 2) {
                drawable.setState(this.mDelegator.getDrawableState());
                this.mVirtualButtonFocusedDrawable.setBounds(0, this.mTopSelectionDividerTop, right, this.mBottomSelectionDividerBottom);
                this.mVirtualButtonFocusedDrawable.draw(canvas);
            } else if (i == 3) {
                drawable.setState(this.mDelegator.getDrawableState());
                this.mVirtualButtonFocusedDrawable.setBounds(0, this.mBottomSelectionDividerBottom, right, bottom);
                this.mVirtualButtonFocusedDrawable.draw(canvas);
            }
        }
        Calendar[] calendarArr = this.mSelectorIndices;
        int length = calendarArr.length;
        float f4 = f3;
        int i2 = 0;
        while (i2 < length) {
            String str = this.mSelectorIndexToStringCache.get(calendarArr[i2]);
            float f5 = this.mAlpha;
            float f6 = this.mIdleAlpha;
            if (f5 >= f6) {
                f6 = f5;
            }
            int descent = (int) ((((this.mSelectorWheelPaint.descent() - this.mSelectorWheelPaint.ascent()) / f) + f4) - this.mSelectorWheelPaint.descent());
            int i3 = this.mTopSelectionDividerTop;
            int i4 = this.mInitialScrollOffset;
            if (f4 >= ((float) (i3 - i4))) {
                int i5 = this.mBottomSelectionDividerBottom;
                if (f4 <= ((float) (i4 + i5))) {
                    if (f4 <= ((float) (i3 + i5)) / f) {
                        canvas.save();
                        canvas.clipRect(0, this.mTopSelectionDividerTop, right, this.mBottomSelectionDividerBottom);
                        this.mSelectorWheelPaint.setColor(this.mTextColor);
                        setHcfTextAppearance(true);
                        float f7 = (float) descent;
                        canvas.drawText(str, f2, f7, this.mSelectorWheelPaint);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0, 0, right, this.mTopSelectionDividerTop);
                        setHcfTextAppearance(false);
                        this.mSelectorWheelPaint.setAlpha((int) (f6 * 255.0f));
                        canvas.drawText(str, f2, f7, this.mSelectorWheelPaint);
                        canvas.restore();
                        z = false;
                    } else {
                        canvas.save();
                        canvas.clipRect(0, this.mTopSelectionDividerTop, right, this.mBottomSelectionDividerBottom);
                        setHcfTextAppearance(true);
                        this.mSelectorWheelPaint.setColor(this.mTextColor);
                        float f8 = (float) descent;
                        canvas.drawText(str, f2, f8, this.mSelectorWheelPaint);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0, this.mBottomSelectionDividerBottom, right, bottom);
                        this.mSelectorWheelPaint.setAlpha((int) (f6 * 255.0f));
                        setHcfTextAppearance(false);
                        canvas.drawText(str, f2, f8, this.mSelectorWheelPaint);
                        canvas.restore();
                        z = false;
                    }
                    f4 += (float) this.mSelectorElementHeight;
                    i2++;
                    f = 2.0f;
                }
            }
            canvas.save();
            this.mSelectorWheelPaint.setAlpha((int) (f6 * 255.0f));
            z = false;
            setHcfTextAppearance(false);
            canvas.drawText(str, f2, (float) descent, this.mSelectorWheelPaint);
            canvas.restore();
            f4 += (float) this.mSelectorElementHeight;
            i2++;
            f = 2.0f;
        }
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(SeslSpinningDatePickerSpinner.class.getName());
        accessibilityEvent.setScrollable(true);
        accessibilityEvent.setScrollY(((int) TimeUnit.MILLISECONDS.toDays(this.mValue.getTimeInMillis() - this.mMinValue.getTimeInMillis())) * this.mSelectorElementHeight);
        accessibilityEvent.setMaxScrollY(((int) TimeUnit.MILLISECONDS.toDays(this.mMaxValue.getTimeInMillis() - this.mMinValue.getTimeInMillis())) * this.mSelectorElementHeight);
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (this.mAccessibilityNodeProvider == null) {
            this.mAccessibilityNodeProvider = new AccessibilityNodeProviderImpl();
        }
        return this.mAccessibilityNodeProvider;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public void setLunar(boolean z, boolean z2) {
        this.mIsLunar = z;
        this.mIsLeapMonth = z2;
        if (!this.mIsLunar) {
            this.mPathClassLoader = null;
            this.mSolarLunarConverter = null;
        } else if (this.mSolarLunarConverter == null) {
            this.mPathClassLoader = SeslSpinningDatePicker.LunarUtils.getPathClassLoader(this.mContext);
            this.mSolarLunarConverter = SeslFeatureReflector.getSolarLunarConverter(this.mPathClassLoader);
        }
    }

    private int makeMeasureSpec(int i, int i2) {
        if (i2 == -1) {
            return i;
        }
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i2), 1073741824);
        }
        if (mode == 0) {
            return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
        }
        if (mode == 1073741824) {
            return i;
        }
        throw new IllegalArgumentException("Unknown measure mode: " + mode);
    }

    private int resolveSizeAndStateRespectingMinSize(int i, int i2, int i3) {
        return i != -1 ? View.resolveSizeAndState(Math.max(i, i2), i3, 0) : i2;
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        Calendar[] calendarArr = this.mSelectorIndices;
        Calendar value = getValue();
        for (int i = 0; i < this.mSelectorIndices.length; i++) {
            Calendar calendar = (Calendar) value.clone();
            calendar.add(5, i - 2);
            if (this.mWrapSelectorWheel) {
                calendar = getWrappedSelectorIndex(calendar);
            }
            calendarArr[i] = calendar;
            ensureCachedScrollSelectorValue(calendarArr[i]);
        }
    }

    private void setValueInternal(Calendar calendar, boolean z) {
        Calendar calendar2;
        if (this.mWrapSelectorWheel) {
            calendar2 = getWrappedSelectorIndex(calendar);
        } else {
            int compareTo = calendar.compareTo(this.mMinValue);
            Calendar calendar3 = calendar;
            if (compareTo < 0) {
                calendar3 = this.mMinValue.clone();
            }
            Calendar calendar4 = calendar3;
            int compareTo2 = calendar4.compareTo(this.mMaxValue);
            Calendar calendar5 = calendar4;
            if (compareTo2 > 0) {
                calendar5 = this.mMaxValue.clone();
            }
            calendar2 = calendar5;
        }
        Calendar calendar6 = (Calendar) this.mValue.clone();
        clearCalendar(this.mValue, calendar2);
        if (z) {
            notifyChange(calendar6);
        }
        initializeSelectorWheelIndices();
        this.mDelegator.invalidate();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void changeValueByOne(boolean z) {
        if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        this.mPreviousScrollerY = 0;
        this.mChangeValueBy = 1;
        if (this.mLongPressed_FIRST_SCROLL) {
            this.mLongPressed_FIRST_SCROLL = false;
            this.mLongPressed_SECOND_SCROLL = true;
        } else if (this.mLongPressed_SECOND_SCROLL) {
            this.mLongPressed_SECOND_SCROLL = false;
            this.mLongPressed_THIRD_SCROLL = true;
            if (getValue().get(5) % 10 == 0) {
                this.mChangeValueBy = 10;
            } else if (z) {
                this.mChangeValueBy = 10 - (getValue().get(5) % 10);
            } else {
                this.mChangeValueBy = getValue().get(5) % 10;
            }
        } else if (this.mLongPressed_THIRD_SCROLL) {
            this.mChangeValueBy = 10;
        }
        int i = 500;
        if (this.mIsLongPressed && this.mSkipNumbers) {
            i = 200;
            this.mLongPressUpdateInterval = 600;
        } else if (this.mIsLongPressed) {
            i = 100;
            this.mChangeValueBy = 1;
            this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        }
        int i2 = this.mChangeValueBy;
        this.mLongPressCount = i2 - 1;
        if (z) {
            this.mFlingScroller.startScroll(0, 0, 0, (-this.mSelectorElementHeight) * i2, i);
        } else {
            this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight * i2, i);
        }
        this.mDelegator.invalidate();
    }

    private void initializeSelectorWheel() {
        if (this.mIsStartingAnimation) {
            if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
                moveToFinalScrollerPosition(this.mAdjustScroller);
            }
            stopScrollAnimation();
        }
        if (!this.mIsStartingAnimation) {
            initializeSelectorWheelIndices();
        }
        this.mSelectorTextGapHeight = (int) ((((float) ((this.mDelegator.getBottom() - this.mDelegator.getTop()) - (this.mTextSize * 3))) / 3.0f) + 0.5f);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        int i = this.mModifiedTxtHeight;
        if (i > this.mSelectorElementHeight) {
            i = this.mDelegator.getHeight() / 3;
        }
        this.mValueChangeOffset = i;
        this.mInitialScrollOffset = (this.mInputText.getTop() + (this.mModifiedTxtHeight / 2)) - this.mSelectorElementHeight;
        this.mCurrentScrollOffset = this.mInitialScrollOffset;
        ((SeslSpinningDatePickerSpinner.CustomEditText) this.mInputText).setEditTextPosition(((int) (((this.mSelectorWheelPaint.descent() - this.mSelectorWheelPaint.ascent()) / 2.0f) - this.mSelectorWheelPaint.descent())) - (this.mInputText.getBaseline() - (this.mModifiedTxtHeight / 2)));
        if (this.mReservedStartAnimation) {
            startAnimation(0, this.mAnimationListener);
            this.mReservedStartAnimation = false;
        }
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            onScrollStateChange(0);
        }
    }

    private void onScrollStateChange(int i) {
        if (this.mScrollState != i) {
            this.mScrollState = i;
            SeslSpinningDatePickerSpinner.OnScrollListener onScrollListener = this.mOnScrollListener;
            if (onScrollListener != null) {
                onScrollListener.onScrollStateChange(this.mDelegator, i);
            }
        }
    }

    private void fling(int i) {
        int i2;
        this.mPreviousScrollerY = 0;
        float scrollFriction = ViewConfiguration.getScrollFriction();
        float abs = ((float) Math.abs(i)) / ((float) this.mMaximumFlingVelocity);
        this.mFlingScroller.setFriction(scrollFriction * abs);
        this.mFlingScroller.fling(0, this.mCurrentScrollOffset, 0, Math.round(((float) i) * abs), 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int round = Math.round(((float) this.mFlingScroller.getFinalY()) / ((float) this.mSelectorElementHeight));
        int i3 = this.mSelectorElementHeight;
        int i4 = this.mInitialScrollOffset;
        int i5 = (round * i3) + i4;
        Scroller scroller = this.mFlingScroller;
        if (i > 0) {
            i2 = Math.max(i5, i3 + i4);
        } else {
            i2 = Math.min(i5, (-i3) + i4);
        }
        scroller.setFinalY(i2);
        this.mDelegator.invalidate();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private Calendar getWrappedSelectorIndex(Calendar calendar) {
        if (calendar.compareTo(this.mMaxValue) > 0) {
            Calendar calendar2 = (Calendar) this.mMinValue.clone();
            calendar2.add(5, ((int) TimeUnit.MILLISECONDS.toDays(calendar.getTimeInMillis() - this.mMinValue.getTimeInMillis())) % (((int) TimeUnit.MILLISECONDS.toDays(this.mMaxValue.getTimeInMillis() - this.mMinValue.getTimeInMillis())) + 1));
            return calendar2;
        } else if (calendar.compareTo(this.mMinValue) >= 0) {
            return calendar;
        } else {
            Calendar calendar3 = (Calendar) this.mMaxValue.clone();
            calendar3.add(5, -(((int) TimeUnit.MILLISECONDS.toDays(this.mMaxValue.getTimeInMillis() - calendar.getTimeInMillis())) % (((int) TimeUnit.MILLISECONDS.toDays(this.mMaxValue.getTimeInMillis() - this.mMinValue.getTimeInMillis())) + 1)));
            return calendar3;
        }
    }

    private void incrementSelectorIndices(Calendar[] calendarArr) {
        System.arraycopy(calendarArr, 1, calendarArr, 0, calendarArr.length - 1);
        Calendar calendar = (Calendar) calendarArr[calendarArr.length - 2].clone();
        calendar.add(5, 1);
        if (this.mWrapSelectorWheel && calendar.compareTo(this.mMaxValue) > 0) {
            clearCalendar(calendar, this.mMinValue);
        }
        calendarArr[calendarArr.length - 1] = calendar;
        ensureCachedScrollSelectorValue(calendar);
    }

    private void decrementSelectorIndices(Calendar[] calendarArr) {
        System.arraycopy(calendarArr, 0, calendarArr, 1, calendarArr.length - 1);
        Calendar calendar = (Calendar) calendarArr[1].clone();
        calendar.add(5, -1);
        if (this.mWrapSelectorWheel && calendar.compareTo(this.mMinValue) < 0) {
            clearCalendar(calendar, this.mMaxValue);
        }
        calendarArr[0] = calendar;
        ensureCachedScrollSelectorValue(calendar);
    }

    private void ensureCachedScrollSelectorValue(Calendar calendar) {
        HashMap<Calendar, String> hashMap = this.mSelectorIndexToStringCache;
        if (hashMap.get(calendar) == null) {
            hashMap.put(calendar, (calendar.compareTo(this.mMinValue) < 0 || calendar.compareTo(this.mMaxValue) > 0) ? "" : this.mIsLunar ? formatDateForLunar(calendar) : formatDate(calendar));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String formatDateForLunar(Calendar calendar) {
        String str;
        Calendar calendar2 = (Calendar) calendar.clone();
        Calendar convertSolarToLunar = convertSolarToLunar(calendar, null);
        SeslSpinningDatePickerSpinner.Formatter formatter = this.mFormatter;
        if (formatter == null) {
            str = formatDateWithLocale(convertSolarToLunar);
        } else if (formatter instanceof SeslSpinningDatePickerSpinner.DateFormatter) {
            str = ((SeslSpinningDatePickerSpinner.DateFormatter) formatter).format(convertSolarToLunar, this.mContext);
        } else {
            str = formatter.format(convertSolarToLunar);
        }
        String formatWeekdayWithLocale = formatWeekdayWithLocale(convertSolarToLunar);
        String formatWeekdayWithLocale2 = formatWeekdayWithLocale(calendar2);
        StringBuilder sb = new StringBuilder(str);
        int lastIndexOf = sb.lastIndexOf(formatWeekdayWithLocale);
        sb.replace(lastIndexOf, formatWeekdayWithLocale.length() + lastIndexOf, formatWeekdayWithLocale2);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String formatDate(Calendar calendar) {
        SeslSpinningDatePickerSpinner.Formatter formatter = this.mFormatter;
        if (formatter == null) {
            return formatDateWithLocale(calendar);
        }
        if (formatter instanceof SeslSpinningDatePickerSpinner.DateFormatter) {
            return ((SeslSpinningDatePickerSpinner.DateFormatter) formatter).format(calendar, this.mContext);
        }
        return formatter.format(calendar);
    }

    private void validateInputTextView(View view) {
        String valueOf = String.valueOf(((TextView) view).getText());
        Calendar selectedPos = getSelectedPos(valueOf);
        if (!TextUtils.isEmpty(valueOf) && !this.mValue.equals(selectedPos)) {
            setValueInternal(selectedPos, true);
        }
    }

    private void notifyChange(Calendar calendar) {
        if (this.mAccessibilityManager.isEnabled() && !this.mIsStartingAnimation) {
            Calendar wrappedSelectorIndex = getWrappedSelectorIndex(this.mValue);
            this.mDelegator.announceForAccessibility(wrappedSelectorIndex.compareTo(this.mMaxValue) <= 0 ? this.mIsLunar ? formatDateForLunar(wrappedSelectorIndex) : formatDate(wrappedSelectorIndex) : null);
        }
        SeslSpinningDatePickerSpinner.OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
        if (onValueChangeListener == null) {
            return;
        }
        if (this.mIsLunar) {
            SeslSpinningDatePicker.LunarDate lunarDate = new SeslSpinningDatePicker.LunarDate();
            this.mOnValueChangeListener.onValueChange(this.mDelegator, convertSolarToLunar(calendar, null), convertSolarToLunar(this.mValue, lunarDate), lunarDate.isLeapMonth);
            return;
        }
        onValueChangeListener.onValueChange(this.mDelegator, calendar, this.mValue, false);
    }

    private void postChangeCurrentByOneFromLongPress(boolean z, long j) {
        if (this.mChangeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            this.mDelegator.removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        this.mIsLongPressed = true;
        this.mLongPressed_FIRST_SCROLL = true;
        this.mChangeCurrentByOneFromLongPressCommand.setStep(z);
        this.mDelegator.postDelayed(this.mChangeCurrentByOneFromLongPressCommand, j);
    }

    private void removeChangeCurrentByOneFromLongPress() {
        if (this.mIsLongPressed) {
            this.mIsLongPressed = false;
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        }
        this.mLongPressed_FIRST_SCROLL = false;
        this.mLongPressed_SECOND_SCROLL = false;
        this.mLongPressed_THIRD_SCROLL = false;
        this.mChangeValueBy = 1;
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            this.mDelegator.removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
    }

    private void removeAllCallbacks() {
        if (this.mIsLongPressed) {
            this.mIsLongPressed = false;
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        }
        this.mLongPressed_FIRST_SCROLL = false;
        this.mLongPressed_SECOND_SCROLL = false;
        this.mLongPressed_THIRD_SCROLL = false;
        this.mChangeValueBy = 1;
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            this.mDelegator.removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    private Calendar getSelectedPos(String str) {
        Calendar parse = SeslSpinningDatePickerSpinner.getDateFormatter().parse(str);
        return parse == null ? (Calendar) this.mMinValue.clone() : parse;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean ensureScrollWheelAdjusted() {
        return ensureScrollWheelAdjusted(0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        if (r0 > 0) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002a, code lost:
        if (r0 > 0) goto L_0x002c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean ensureScrollWheelAdjusted(int r9) {
        /*
            r8 = this;
            int r0 = r8.mInitialScrollOffset
            r1 = 0
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 != r2) goto L_0x0008
            return r1
        L_0x0008:
            int r2 = r8.mCurrentScrollOffset
            int r0 = r0 - r2
            if (r0 == 0) goto L_0x0042
            r8.mPreviousScrollerY = r1
            boolean r2 = r8.mIsValueChanged
            if (r2 != 0) goto L_0x0020
            if (r9 == 0) goto L_0x0020
            int r9 = java.lang.Math.abs(r9)
            int r2 = r8.mSelectorElementHeight
            if (r9 >= r2) goto L_0x0020
            if (r0 <= 0) goto L_0x002d
            goto L_0x002c
        L_0x0020:
            int r9 = java.lang.Math.abs(r0)
            int r2 = r8.mSelectorElementHeight
            int r3 = r2 / 2
            if (r9 <= r3) goto L_0x002e
            if (r0 <= 0) goto L_0x002d
        L_0x002c:
            int r2 = -r2
        L_0x002d:
            int r0 = r0 + r2
        L_0x002e:
            r6 = r0
            android.widget.Scroller r2 = r8.mAdjustScroller
            r3 = 0
            r4 = 0
            r5 = 0
            r7 = 300(0x12c, float:4.2E-43)
            r2.startScroll(r3, r4, r5, r6, r7)
            androidx.picker.widget.SeslSpinningDatePickerSpinner r9 = r8.mDelegator
            r9.invalidate()
            r8.mIsValueChanged = r1
            r9 = 1
            return r9
        L_0x0042:
            r8.mIsValueChanged = r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate.ensureScrollWheelAdjusted(int):boolean");
    }

    /* access modifiers changed from: package-private */
    public class PressedStateHelper implements Runnable {
        static final int BUTTON_DECREMENT = 2;
        static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS = 1;
        private final int MODE_TAPPED = 2;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
        }

        /* access modifiers changed from: package-private */
        public void cancel() {
            int right = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getRight();
            int bottom = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getBottom();
            this.mMode = 0;
            this.mManagedButton = 0;
            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.removeCallbacks(this);
            if (SeslSpinningDatePickerSpinnerDelegate.this.mIncrementVirtualButtonPressed) {
                SeslSpinningDatePickerSpinnerDelegate.this.mIncrementVirtualButtonPressed = false;
                SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
            }
            if (SeslSpinningDatePickerSpinnerDelegate.this.mDecrementVirtualButtonPressed) {
                SeslSpinningDatePickerSpinnerDelegate.this.mDecrementVirtualButtonPressed = false;
                SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop);
            }
        }

        /* access modifiers changed from: package-private */
        public void buttonPressDelayed(int i) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = i;
            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.postDelayed(this, (long) ViewConfiguration.getTapTimeout());
        }

        /* access modifiers changed from: package-private */
        public void buttonTapped(int i) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = i;
            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.post(this);
        }

        public void run() {
            int right = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getRight();
            int bottom = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getBottom();
            int i = this.mMode;
            if (i == 1) {
                int i2 = this.mManagedButton;
                if (i2 == 1) {
                    SeslSpinningDatePickerSpinnerDelegate.this.mIncrementVirtualButtonPressed = true;
                    SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
                } else if (i2 == 2) {
                    SeslSpinningDatePickerSpinnerDelegate.this.mDecrementVirtualButtonPressed = true;
                    SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop);
                }
            } else if (i == 2) {
                int i3 = this.mManagedButton;
                if (i3 == 1) {
                    if (!SeslSpinningDatePickerSpinnerDelegate.this.mIncrementVirtualButtonPressed) {
                        SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                    }
                    SeslSpinningDatePickerSpinnerDelegate seslSpinningDatePickerSpinnerDelegate = SeslSpinningDatePickerSpinnerDelegate.this;
                    seslSpinningDatePickerSpinnerDelegate.mIncrementVirtualButtonPressed = !seslSpinningDatePickerSpinnerDelegate.mIncrementVirtualButtonPressed;
                    SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
                } else if (i3 == 2) {
                    if (!SeslSpinningDatePickerSpinnerDelegate.this.mDecrementVirtualButtonPressed) {
                        SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                    }
                    SeslSpinningDatePickerSpinnerDelegate seslSpinningDatePickerSpinnerDelegate2 = SeslSpinningDatePickerSpinnerDelegate.this;
                    seslSpinningDatePickerSpinnerDelegate2.mDecrementVirtualButtonPressed = !seslSpinningDatePickerSpinnerDelegate2.mDecrementVirtualButtonPressed;
                    SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void setStep(boolean z) {
            this.mIncrement = z;
        }

        public void run() {
            SeslSpinningDatePickerSpinnerDelegate.this.changeValueByOne(this.mIncrement);
            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.postDelayed(this, SeslSpinningDatePickerSpinnerDelegate.this.mLongPressUpdateInterval);
        }
    }

    /* access modifiers changed from: package-private */
    public class AccessibilityNodeProviderImpl extends AccessibilityNodeProvider {
        private static final int UNDEFINED = Integer.MIN_VALUE;
        private static final int VIRTUAL_VIEW_ID_CENTER = 2;
        private static final int VIRTUAL_VIEW_ID_DECREMENT = 1;
        private static final int VIRTUAL_VIEW_ID_INCREMENT = 3;
        private int mAccessibilityFocusedView = Integer.MIN_VALUE;
        private final int[] mTempArray = new int[2];
        private final Rect mTempRect = new Rect();

        AccessibilityNodeProviderImpl() {
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            int left = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getLeft();
            int right = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getRight();
            int top = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getTop();
            int bottom = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getBottom();
            int scrollX = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getScrollX();
            int scrollY = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getScrollY();
            if (!(SeslSpinningDatePickerSpinnerDelegate.this.mLastFocusedChildVirtualViewId == -1 && SeslSpinningDatePickerSpinnerDelegate.this.mLastHoveredChildVirtualViewId == Integer.MIN_VALUE)) {
                if (i == -1) {
                    return createAccessibilityNodeInfoForDatePickerWidget(scrollX, scrollY, (right - left) + scrollX, (bottom - top) + scrollY);
                }
                if (i == 1) {
                    return createAccessibilityNodeInfoForVirtualButton(1, getVirtualDecrementButtonText(), scrollX, scrollY, scrollX + (right - left), SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop + SeslSpinningDatePickerSpinnerDelegate.this.mSelectionDividerHeight);
                }
                if (i == 2) {
                    return createAccessibiltyNodeInfoForCenter(scrollX, SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop + SeslSpinningDatePickerSpinnerDelegate.this.mSelectionDividerHeight, (right - left) + scrollX, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom - SeslSpinningDatePickerSpinnerDelegate.this.mSelectionDividerHeight);
                }
                if (i == 3) {
                    return createAccessibilityNodeInfoForVirtualButton(3, getVirtualIncrementButtonText(), scrollX, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom - SeslSpinningDatePickerSpinnerDelegate.this.mSelectionDividerHeight, scrollX + (right - left), scrollY + (bottom - top));
                }
            }
            AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo(i);
            if (createAccessibilityNodeInfo == null) {
                return AccessibilityNodeInfo.obtain();
            }
            return createAccessibilityNodeInfo;
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str, int i) {
            if (TextUtils.isEmpty(str)) {
                return Collections.emptyList();
            }
            String lowerCase = str.toLowerCase();
            ArrayList arrayList = new ArrayList();
            if (i == -1) {
                findAccessibilityNodeInfosByTextInChild(lowerCase, 1, arrayList);
                findAccessibilityNodeInfosByTextInChild(lowerCase, 2, arrayList);
                findAccessibilityNodeInfosByTextInChild(lowerCase, 3, arrayList);
                return arrayList;
            } else if (i != 1 && i != 2 && i != 3) {
                return super.findAccessibilityNodeInfosByText(str, i);
            } else {
                findAccessibilityNodeInfosByTextInChild(lowerCase, i, arrayList);
                return arrayList;
            }
        }

        public boolean performAction(int i, int i2, Bundle bundle) {
            if (SeslSpinningDatePickerSpinnerDelegate.this.mIsStartingAnimation) {
                return false;
            }
            int right = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getRight();
            int bottom = SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getBottom();
            if (i != -1) {
                if (i != 1) {
                    if (i != 2) {
                        if (i == 3) {
                            if (i2 != 16) {
                                if (i2 != 64) {
                                    if (i2 != 128 || this.mAccessibilityFocusedView != i) {
                                        return false;
                                    }
                                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                                    sendAccessibilityEventForVirtualView(i, 65536);
                                    SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
                                    return true;
                                } else if (this.mAccessibilityFocusedView == i) {
                                    return false;
                                } else {
                                    this.mAccessibilityFocusedView = i;
                                    sendAccessibilityEventForVirtualView(i, 32768);
                                    SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
                                    return true;
                                }
                            } else if (!SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                                return false;
                            } else {
                                SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(false);
                                SeslSpinningDatePickerSpinnerDelegate.this.changeValueByOne(true);
                                sendAccessibilityEventForVirtualView(i, 1);
                                SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(true);
                                return true;
                            }
                        }
                    } else if (i2 != 16) {
                        if (i2 != 64) {
                            if (i2 != 128 || this.mAccessibilityFocusedView != i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            sendAccessibilityEventForVirtualView(i, 65536);
                            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop, right, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom);
                            return true;
                        } else if (this.mAccessibilityFocusedView == i) {
                            return false;
                        } else {
                            this.mAccessibilityFocusedView = i;
                            sendAccessibilityEventForVirtualView(i, 32768);
                            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop, right, SeslSpinningDatePickerSpinnerDelegate.this.mBottomSelectionDividerBottom);
                            return true;
                        }
                    } else if (!SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                        return false;
                    } else {
                        SeslSpinningDatePickerSpinnerDelegate.this.performClick();
                        return true;
                    }
                } else if (i2 != 16) {
                    if (i2 != 64) {
                        if (i2 != 128 || this.mAccessibilityFocusedView != i) {
                            return false;
                        }
                        this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                        sendAccessibilityEventForVirtualView(i, 65536);
                        SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop);
                        return true;
                    } else if (this.mAccessibilityFocusedView == i) {
                        return false;
                    } else {
                        this.mAccessibilityFocusedView = i;
                        sendAccessibilityEventForVirtualView(i, 32768);
                        SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslSpinningDatePickerSpinnerDelegate.this.mTopSelectionDividerTop);
                        return true;
                    }
                } else if (!SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                    return false;
                } else {
                    SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(false);
                    SeslSpinningDatePickerSpinnerDelegate.this.changeValueByOne(false);
                    sendAccessibilityEventForVirtualView(i, 1);
                    SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(true);
                    return true;
                }
            } else if (i2 != 64) {
                if (i2 != 128) {
                    if (i2 != 4096) {
                        if (i2 == 8192) {
                            if (!SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled() || (!SeslSpinningDatePickerSpinnerDelegate.this.getWrapSelectorWheel() && SeslSpinningDatePickerSpinnerDelegate.this.getValue().compareTo(SeslSpinningDatePickerSpinnerDelegate.this.getMinValue()) <= 0)) {
                                return false;
                            }
                            SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(false);
                            SeslSpinningDatePickerSpinnerDelegate.this.changeValueByOne(false);
                            SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(true);
                            return true;
                        }
                    } else if (!SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled() || (!SeslSpinningDatePickerSpinnerDelegate.this.getWrapSelectorWheel() && SeslSpinningDatePickerSpinnerDelegate.this.getValue().compareTo(SeslSpinningDatePickerSpinnerDelegate.this.getMaxValue()) >= 0)) {
                        return false;
                    } else {
                        SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(false);
                        SeslSpinningDatePickerSpinnerDelegate.this.changeValueByOne(true);
                        SeslSpinningDatePickerSpinnerDelegate.this.startFadeAnimation(true);
                        return true;
                    }
                } else if (this.mAccessibilityFocusedView != i) {
                    return false;
                } else {
                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                    SeslViewReflector.clearAccessibilityFocus(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator);
                    return true;
                }
            } else if (this.mAccessibilityFocusedView == i) {
                return false;
            } else {
                this.mAccessibilityFocusedView = i;
                SeslViewReflector.requestAccessibilityFocus(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator);
                return true;
            }
            return super.performAction(i, i2, bundle);
        }

        /* access modifiers changed from: package-private */
        public void sendAccessibilityEventForVirtualView(int i, int i2) {
            if (i != 1) {
                if (i == 2) {
                    sendAccessibilityEventForCenter(i2);
                } else if (i == 3 && hasVirtualIncrementButton()) {
                    sendAccessibilityEventForVirtualButton(i, i2, getVirtualIncrementButtonText());
                }
            } else if (hasVirtualDecrementButton()) {
                sendAccessibilityEventForVirtualButton(i, i2, getVirtualDecrementButtonText());
            }
        }

        private void sendAccessibilityEventForCenter(int i) {
            if (SeslSpinningDatePickerSpinnerDelegate.this.mAccessibilityManager.isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
                obtain.setPackageName(SeslSpinningDatePickerSpinnerDelegate.this.mContext.getPackageName());
                obtain.getText().add(getVirtualCurrentButtonText() + SeslSpinningDatePickerSpinnerDelegate.this.mContext.getString(R.string.sesl_date_picker_switch_to_calendar_description));
                obtain.setEnabled(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled());
                obtain.setSource(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, 2);
                SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.requestSendAccessibilityEvent(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, obtain);
            }
        }

        private void sendAccessibilityEventForVirtualButton(int i, int i2, String str) {
            if (SeslSpinningDatePickerSpinnerDelegate.this.mAccessibilityManager.isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
                obtain.setClassName(Button.class.getName());
                obtain.setPackageName(SeslSpinningDatePickerSpinnerDelegate.this.mContext.getPackageName());
                obtain.getText().add(str);
                obtain.setEnabled(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled());
                obtain.setSource(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, i);
                SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.requestSendAccessibilityEvent(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, obtain);
            }
        }

        private void findAccessibilityNodeInfosByTextInChild(String str, int i, List<AccessibilityNodeInfo> list) {
            if (i == 1) {
                String virtualDecrementButtonText = getVirtualDecrementButtonText();
                if (!TextUtils.isEmpty(virtualDecrementButtonText) && virtualDecrementButtonText.toLowerCase().contains(str)) {
                    list.add(createAccessibilityNodeInfo(1));
                }
            } else if (i == 2) {
                String virtualCurrentButtonText = getVirtualCurrentButtonText();
                if (!TextUtils.isEmpty(virtualCurrentButtonText) && virtualCurrentButtonText.toLowerCase().contains(str)) {
                    list.add(createAccessibilityNodeInfo(2));
                }
            } else if (i == 3) {
                String virtualIncrementButtonText = getVirtualIncrementButtonText();
                if (!TextUtils.isEmpty(virtualIncrementButtonText) && virtualIncrementButtonText.toLowerCase().contains(str)) {
                    list.add(createAccessibilityNodeInfo(3));
                }
            }
        }

        private AccessibilityNodeInfo createAccessibiltyNodeInfoForCenter(int i, int i2, int i3, int i4) {
            AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
            obtain.setPackageName(SeslSpinningDatePickerSpinnerDelegate.this.mContext.getPackageName());
            obtain.setSource(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, 2);
            obtain.setParent(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator);
            obtain.setText(getVirtualCurrentButtonText() + SeslSpinningDatePickerSpinnerDelegate.this.mContext.getString(R.string.sesl_date_picker_switch_to_calendar_description));
            obtain.setClickable(true);
            obtain.setEnabled(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled());
            if (this.mAccessibilityFocusedView != 2) {
                obtain.setAccessibilityFocused(false);
                obtain.addAction(64);
            } else {
                obtain.setAccessibilityFocused(true);
                obtain.addAction(128);
            }
            Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            obtain.setVisibleToUser(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isVisibleToUserWrapper(rect));
            obtain.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            obtain.setBoundsInScreen(rect);
            return obtain;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForVirtualButton(int i, String str, int i2, int i3, int i4, int i5) {
            AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
            obtain.setClassName(Button.class.getName());
            obtain.setPackageName(SeslSpinningDatePickerSpinnerDelegate.this.mContext.getPackageName());
            obtain.setSource(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, i);
            obtain.setParent(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator);
            obtain.setText(str);
            obtain.setClickable(true);
            obtain.setLongClickable(true);
            obtain.setEnabled(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled());
            Rect rect = this.mTempRect;
            rect.set(i2, i3, i4, i5);
            obtain.setVisibleToUser(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isVisibleToUserWrapper(rect));
            obtain.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            obtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != i) {
                obtain.addAction(64);
            } else {
                obtain.addAction(128);
            }
            if (SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                obtain.addAction(16);
            }
            return obtain;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForDatePickerWidget(int i, int i2, int i3, int i4) {
            AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
            obtain.setClassName(SeslSpinningDatePickerSpinner.class.getName());
            obtain.setPackageName(SeslSpinningDatePickerSpinnerDelegate.this.mContext.getPackageName());
            obtain.setSource(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator);
            if (hasVirtualDecrementButton()) {
                obtain.addChild(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, 1);
            }
            obtain.addChild(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, 2);
            if (hasVirtualIncrementButton()) {
                obtain.addChild(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator, 3);
            }
            obtain.setParent((View) SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getParentForAccessibility());
            obtain.setEnabled(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled());
            obtain.setScrollable(true);
            float field_applicationScale = SeslCompatibilityInfoReflector.getField_applicationScale(SeslSpinningDatePickerSpinnerDelegate.this.mContext.getResources());
            Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            scaleRect(rect, field_applicationScale);
            obtain.setBoundsInParent(rect);
            obtain.setVisibleToUser(SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isVisibleToUserWrapper());
            int[] iArr = this.mTempArray;
            SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            scaleRect(rect, field_applicationScale);
            obtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != -1) {
                obtain.addAction(64);
            } else {
                obtain.addAction(128);
            }
            if (SeslSpinningDatePickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                if (SeslSpinningDatePickerSpinnerDelegate.this.getWrapSelectorWheel() || SeslSpinningDatePickerSpinnerDelegate.this.getValue().compareTo(SeslSpinningDatePickerSpinnerDelegate.this.getMaxValue()) < 0) {
                    obtain.addAction(4096);
                }
                if (SeslSpinningDatePickerSpinnerDelegate.this.getWrapSelectorWheel() || SeslSpinningDatePickerSpinnerDelegate.this.getValue().compareTo(SeslSpinningDatePickerSpinnerDelegate.this.getMinValue()) > 0) {
                    obtain.addAction(8192);
                }
            }
            return obtain;
        }

        private void scaleRect(Rect rect, float f) {
            if (f != 1.0f) {
                rect.left = (int) ((((float) rect.left) * f) + 0.5f);
                rect.top = (int) ((((float) rect.top) * f) + 0.5f);
                rect.right = (int) ((((float) rect.right) * f) + 0.5f);
                rect.bottom = (int) ((((float) rect.bottom) * f) + 0.5f);
            }
        }

        private boolean hasVirtualDecrementButton() {
            return SeslSpinningDatePickerSpinnerDelegate.this.getWrapSelectorWheel() || SeslSpinningDatePickerSpinnerDelegate.this.getValue().compareTo(SeslSpinningDatePickerSpinnerDelegate.this.getMinValue()) > 0;
        }

        private boolean hasVirtualIncrementButton() {
            return SeslSpinningDatePickerSpinnerDelegate.this.getWrapSelectorWheel() || SeslSpinningDatePickerSpinnerDelegate.this.getValue().compareTo(SeslSpinningDatePickerSpinnerDelegate.this.getMaxValue()) < 0;
        }

        private String getVirtualDecrementButtonText() {
            Calendar calendar = (Calendar) SeslSpinningDatePickerSpinnerDelegate.this.mValue.clone();
            calendar.add(5, -1);
            if (SeslSpinningDatePickerSpinnerDelegate.this.mWrapSelectorWheel) {
                calendar = SeslSpinningDatePickerSpinnerDelegate.this.getWrappedSelectorIndex(calendar);
            }
            if (calendar.compareTo(SeslSpinningDatePickerSpinnerDelegate.this.mMinValue) < 0) {
                return null;
            }
            if (SeslSpinningDatePickerSpinnerDelegate.this.mIsLunar) {
                return SeslSpinningDatePickerSpinnerDelegate.this.formatDateForLunar(calendar);
            }
            return SeslSpinningDatePickerSpinnerDelegate.this.formatDate(calendar) + ", " + SeslSpinningDatePickerSpinnerDelegate.this.mPickerContentDescription + ", ";
        }

        private String getVirtualIncrementButtonText() {
            Calendar calendar = (Calendar) SeslSpinningDatePickerSpinnerDelegate.this.mValue.clone();
            calendar.add(5, 1);
            if (SeslSpinningDatePickerSpinnerDelegate.this.mWrapSelectorWheel) {
                calendar = SeslSpinningDatePickerSpinnerDelegate.this.getWrappedSelectorIndex(calendar);
            }
            if (calendar.compareTo(SeslSpinningDatePickerSpinnerDelegate.this.mMaxValue) > 0) {
                return null;
            }
            if (SeslSpinningDatePickerSpinnerDelegate.this.mIsLunar) {
                return SeslSpinningDatePickerSpinnerDelegate.this.formatDateForLunar(calendar);
            }
            return SeslSpinningDatePickerSpinnerDelegate.this.formatDate(calendar) + ", " + SeslSpinningDatePickerSpinnerDelegate.this.mPickerContentDescription + ", ";
        }

        private String getVirtualCurrentButtonText() {
            Calendar calendar = (Calendar) SeslSpinningDatePickerSpinnerDelegate.this.mValue.clone();
            if (SeslSpinningDatePickerSpinnerDelegate.this.mWrapSelectorWheel) {
                calendar = SeslSpinningDatePickerSpinnerDelegate.this.getWrappedSelectorIndex(calendar);
            }
            if (calendar.compareTo(SeslSpinningDatePickerSpinnerDelegate.this.mMaxValue) > 0) {
                return null;
            }
            if (SeslSpinningDatePickerSpinnerDelegate.this.mIsLunar) {
                return SeslSpinningDatePickerSpinnerDelegate.this.formatDateForLunar(calendar);
            }
            return SeslSpinningDatePickerSpinnerDelegate.this.formatDate(calendar) + ", " + SeslSpinningDatePickerSpinnerDelegate.this.mPickerContentDescription + ", ";
        }
    }

    private void clearCalendar(Calendar calendar, Calendar calendar2) {
        calendar.set(1, calendar2.get(1));
        calendar.set(2, calendar2.get(2));
        calendar.set(5, calendar2.get(5));
    }

    private static String formatDateWithLocale(Calendar calendar) {
        return new SimpleDateFormat("EEE, MMM dd", Locale.getDefault()).format(calendar.getTime());
    }

    private static String formatWeekdayWithLocale(Calendar calendar) {
        return new SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.getTime());
    }

    private static String formatNumberWithLocale(int i) {
        return String.format(Locale.getDefault(), "%d", Integer.valueOf(i));
    }

    private boolean isCharacterNumberLanguage() {
        String language = Locale.getDefault().getLanguage();
        return "ar".equals(language) || "fa".equals(language) || "my".equals(language);
    }

    private boolean needCompareEqualMonthLanguage() {
        return "vi".equals(Locale.getDefault().getLanguage());
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public Calendar convertLunarToSolar(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        SeslSolarLunarConverterReflector.convertLunarToSolar(this.mPathClassLoader, this.mSolarLunarConverter, calendar.get(1), calendar.get(2), calendar.get(5), this.mIsLeapMonth);
        calendar2.set(SeslSolarLunarConverterReflector.getYear(this.mPathClassLoader, this.mSolarLunarConverter), SeslSolarLunarConverterReflector.getMonth(this.mPathClassLoader, this.mSolarLunarConverter), SeslSolarLunarConverterReflector.getDay(this.mPathClassLoader, this.mSolarLunarConverter));
        return calendar2;
    }

    @Override // androidx.picker.widget.SeslSpinningDatePickerSpinner.DatePickerDelegate
    public Calendar convertSolarToLunar(Calendar calendar, SeslSpinningDatePicker.LunarDate lunarDate) {
        Calendar calendar2 = (Calendar) calendar.clone();
        SeslSolarLunarConverterReflector.convertSolarToLunar(this.mPathClassLoader, this.mSolarLunarConverter, calendar.get(1), calendar.get(2), calendar.get(5));
        calendar2.set(SeslSolarLunarConverterReflector.getYear(this.mPathClassLoader, this.mSolarLunarConverter), SeslSolarLunarConverterReflector.getMonth(this.mPathClassLoader, this.mSolarLunarConverter), SeslSolarLunarConverterReflector.getDay(this.mPathClassLoader, this.mSolarLunarConverter));
        if (lunarDate != null) {
            lunarDate.day = SeslSolarLunarConverterReflector.getDay(this.mPathClassLoader, this.mSolarLunarConverter);
            lunarDate.month = SeslSolarLunarConverterReflector.getMonth(this.mPathClassLoader, this.mSolarLunarConverter);
            lunarDate.year = SeslSolarLunarConverterReflector.getYear(this.mPathClassLoader, this.mSolarLunarConverter);
            lunarDate.isLeapMonth = SeslSolarLunarConverterReflector.isLeapMonth(this.mPathClassLoader, this.mSolarLunarConverter);
        }
        return calendar2;
    }

    private Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        Calendar instance = Calendar.getInstance(locale);
        if (calendar != null) {
            instance.setTimeInMillis(calendar.getTimeInMillis());
        }
        instance.set(11, 12);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance;
    }

    private boolean isHighContrastFontEnabled() {
        return SeslViewReflector.isHighContrastTextEnabled(this.mInputText);
    }
}
