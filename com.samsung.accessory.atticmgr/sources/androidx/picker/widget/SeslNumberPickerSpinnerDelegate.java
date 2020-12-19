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
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.SparseArray;
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
import android.widget.NumberPicker;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.util.SeslMisc;
import androidx.core.view.ViewCompat;
import androidx.picker.R;
import androidx.picker.util.SeslAnimationListener;
import androidx.picker.widget.SeslNumberPicker;
import androidx.reflect.content.res.SeslCompatibilityInfoReflector;
import androidx.reflect.content.res.SeslConfigurationReflector;
import androidx.reflect.graphics.SeslPaintReflector;
import androidx.reflect.media.SeslAudioManagerReflector;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslHoverPopupWindowReflector;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* access modifiers changed from: package-private */
public class SeslNumberPickerSpinnerDelegate extends SeslNumberPicker.AbsNumberPickerDelegate {
    private static final int DECREASE_BUTTON = 1;
    private static final int DEFAULT_WHEEL_INTERVAL = 1;
    private static final char[] DIGIT_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 1632, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1776, 1777, 1778, 1779, 1780, 1781, 1782, 1783, 1784, 1785, 2406, 2407, 2408, 2409, 2410, 2411, 2412, 2413, 2414, 2415, 2534, 2535, 2536, 2537, 2538, 2539, 2540, 2541, 2542, 2543, 3302, 3303, 3304, 3305, 3306, 3307, 3308, 3309, 3310, 3311, 4160, 4161, 4162, 4163, 4164, 4165, 4166, 4167, 4168, 4169};
    private static final int HCF_UNFOCUSED_TEXT_SIZE_DIFF = 2;
    private static final int INCREASE_BUTTON = 3;
    private static final int INPUT = 2;
    private static final String INPUT_TYPE_MONTH = "inputType=month_edittext";
    private static final String INPUT_TYPE_YEAR_DATE_TIME = "inputType=YearDateTime_edittext";
    private static final int PICKER_VIBRATE_INDEX = 32;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 300;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 4;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 2;
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 5;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 500;
    private static final int START_ANIMATION_SCROLL_DURATION = 857;
    private static final int START_ANIMATION_SCROLL_DURATION_2016B = 557;
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
    private BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    private int mBottomSelectionDividerBottom;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private final Scroller mCustomScroller;
    private boolean mCustomTypefaceSet = false;
    private boolean mCustomWheelIntervalMode = false;
    private boolean mDecrementVirtualButtonPressed;
    private final Typeface mDefaultTypeface;
    private String[] mDisplayedValues;
    private ValueAnimator mFadeInAnimator;
    private ValueAnimator mFadeOutAnimator;
    private Scroller mFlingScroller;
    private SeslNumberPicker.Formatter mFormatter;
    private HapticPreDrawListener mHapticPreDrawListener;
    private Typeface mHcfFocusedTypefaceBold;
    private final int mHcfUnfocusedTextSizeDiff;
    private final float mHeightRatio;
    private float mIdleAlpha = 0.1f;
    private boolean mIgnoreMoveEvents;
    private boolean mIgnoreUpEvent;
    private boolean mIncrementVirtualButtonPressed;
    private int mInitialScrollOffset = Integer.MIN_VALUE;
    private final EditText mInputText;
    private boolean mIsAmPm;
    private boolean mIsBoldTextEnabled;
    private boolean mIsEditTextMode;
    private boolean mIsEditTextModeEnabled = true;
    private boolean mIsHcfEnabled;
    private boolean mIsLongClicked = false;
    private boolean mIsLongPressed = false;
    private boolean mIsPressedBackKey = false;
    private boolean mIsStartingAnimation = false;
    private boolean mIsValueChanged = false;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastFocusedChildVirtualViewId;
    private int mLastHoveredChildVirtualViewId;
    private final Typeface mLegacyTypeface;
    private final Scroller mLinearScroller;
    private final int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private final int mMinHeight;
    private int mMinValue;
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private int mModifiedTxtHeight;
    private SeslNumberPicker.OnEditTextModeChangedListener mOnEditTextModeChangedListener;
    private SeslNumberPicker.OnScrollListener mOnScrollListener;
    private SeslNumberPicker.OnValueChangeListener mOnValueChangeListener;
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
    private final SparseArray<String> mSelectorIndexToStringCache = new SparseArray<>();
    private final int[] mSelectorIndices = new int[5];
    private int mSelectorTextGapHeight;
    private Paint mSelectorWheelPaint;
    private SwitchIntervalOnLongPressCommand mSwitchIntervalOnLongPressCommand;
    private final int mTextColor;
    private int mTextSize;
    private Toast mToast;
    private String mToastText;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass6 */

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            SeslNumberPickerSpinnerDelegate.this.mAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate();
        }
    };
    private int mValue;
    private int mValueChangeOffset;
    private VelocityTracker mVelocityTracker;
    private final Drawable mVirtualButtonFocusedDrawable;
    private int mWheelInterval = 1;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelPreferred = true;

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int getMaxHeight() {
        return 0;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int getMaxWidth() {
        return 0;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int getMinHeight() {
        return 0;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int getMinWidth() {
        return 0;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onWindowVisibilityChanged(int i) {
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setSubTextSize(float f) {
    }

    public SeslNumberPickerSpinnerDelegate(SeslNumberPicker seslNumberPicker, Context context, AttributeSet attributeSet, int i, int i2) {
        super(seslNumberPicker, context);
        int i3;
        int i4;
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
        int i5 = this.mMinHeight;
        if (i5 == -1 || (i4 = this.mMaxHeight) == -1 || i5 <= i4) {
            int i6 = this.mMinWidth;
            if (i6 == -1 || (i3 = this.mMaxWidth) == -1 || i6 <= i3) {
                this.mSelectionDividerHeight = (int) TypedValue.applyDimension(1, 2.0f, resources.getDisplayMetrics());
                this.mComputeMaxWidth = this.mMaxWidth == -1;
                if (!SeslMisc.isLightTheme(this.mContext)) {
                    this.mIdleAlpha = 0.2f;
                    this.mAlpha = 0.2f;
                }
                this.mPressedStateHelper = new PressedStateHelper();
                this.mDelegator.setWillNotDraw(false);
                ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.sesl_number_picker_spinner, (ViewGroup) this.mDelegator, true);
                this.mInputText = (EditText) this.mDelegator.findViewById(R.id.numberpicker_input);
                this.mInputText.setLongClickable(false);
                this.mInputText.setIncludeFontPadding(false);
                this.mInputText.setAccessibilityDelegate(new View.AccessibilityDelegate() {
                    /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass1 */

                    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                        if (i == 16) {
                            SeslNumberPickerSpinnerDelegate.this.mInputText.selectAll();
                            SeslNumberPickerSpinnerDelegate.this.showSoftInput();
                        }
                        return super.performAccessibilityAction(view, i, bundle);
                    }
                });
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
                    this.mInputText.setIncludeFontPadding(true);
                    this.mPickerTypeface = this.mDefaultTypeface;
                }
                this.mIsHcfEnabled = isHighContrastFontEnabled();
                this.mHcfFocusedTypefaceBold = Typeface.create(this.mPickerTypeface, 1);
                this.mHcfUnfocusedTextSizeDiff = (int) TypedValue.applyDimension(1, 2.0f, this.mContext.getResources().getDisplayMetrics());
                setInputTextTypeface();
                this.mTextColor = this.mInputText.getTextColors().getColorForState(this.mDelegator.getEnableStateSet(), -1);
                int i7 = (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK) | 855638016;
                this.mVirtualButtonFocusedDrawable = new ColorDrawable(i7);
                this.mInputText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass2 */

                    public void onFocusChange(View view, boolean z) {
                        if (z) {
                            SeslNumberPickerSpinnerDelegate.this.setEditTextMode(true);
                            SeslNumberPickerSpinnerDelegate.this.mInputText.selectAll();
                            return;
                        }
                        SeslNumberPickerSpinnerDelegate.this.mInputText.setSelection(0, 0);
                        SeslNumberPickerSpinnerDelegate.this.validateInputTextView(view);
                    }
                });
                this.mInputText.setOnTouchListener(new View.OnTouchListener() {
                    /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass3 */

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (!(view instanceof EditText) || motionEvent.getActionMasked() != 0) {
                            return false;
                        }
                        ((EditText) view).selectAll();
                        SeslNumberPickerSpinnerDelegate.this.showSoftInput();
                        return true;
                    }
                });
                this.mInputText.setFilters(new InputFilter[]{new InputTextFilter()});
                this.mInputText.setRawInputType(2);
                this.mInputText.setImeOptions(33554438);
                this.mInputText.setCursorVisible(false);
                this.mInputText.setHighlightColor(i7);
                SeslViewReflector.semSetHoverPopupType(this.mInputText, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
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
                if (updateBoldTextEnabledInSettings()) {
                    this.mSelectorWheelPaint.setFakeBoldText(true);
                }
                this.mCustomScroller = new Scroller(this.mContext, this.SIZE_PATH_INTERPOLATOR, true);
                this.mLinearScroller = new Scroller(this.mContext, null, true);
                this.mFlingScroller = this.mLinearScroller;
                this.mAdjustScroller = new Scroller(this.mContext, new PathInterpolator(0.4f, 0.0f, 0.3f, 1.0f));
                setFormatter(SeslNumberPicker.getTwoDigitFormatter());
                updateInputTextView();
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
                this.mToastText = resources.getString(R.string.sesl_number_picker_invalid_value_entered);
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setPickerContentDescription(String str) {
        this.mPickerContentDescription = str;
        ((SeslNumberPicker.CustomEditText) this.mInputText).setPickerContentDescription(str);
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setImeOptions(int i) {
        this.mInputText.setImeOptions(i);
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setAmPm() {
        this.mIsAmPm = true;
        this.mTextSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_time_picker_spinner_am_pm_text_size);
        this.mSelectorWheelPaint.setTextSize((float) this.mTextSize);
        this.mInputText.setTextSize(0, (float) this.mTextSize);
        setEditTextModeEnabled(false);
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setEditTextModeEnabled(boolean z) {
        if (this.mIsEditTextModeEnabled != z && !z) {
            if (this.mIsEditTextMode) {
                setEditTextMode(false);
            }
            this.mInputText.setAccessibilityDelegate(null);
            this.mIsEditTextModeEnabled = z;
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean isEditTextModeEnabled() {
        return this.mIsEditTextModeEnabled;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setEditTextMode(boolean z) {
        AccessibilityNodeProviderImpl accessibilityNodeProviderImpl;
        if (this.mIsEditTextModeEnabled && this.mIsEditTextMode != z) {
            this.mIsEditTextMode = z;
            if (this.mIsEditTextMode) {
                tryComputeMaxWidth();
                removeAllCallbacks();
                if (!this.mIsStartingAnimation) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                    this.mFlingScroller.abortAnimation();
                    onScrollStateChange(0);
                }
                this.mDelegator.setDescendantFocusability(262144);
                updateInputTextView();
                this.mInputText.setVisibility(0);
                if (this.mAccessibilityManager.isEnabled() && (accessibilityNodeProviderImpl = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider()) != null) {
                    accessibilityNodeProviderImpl.performAction(2, 128, null);
                }
            } else {
                int i = this.mWheelInterval;
                if (!(i == 1 || !this.mCustomWheelIntervalMode || this.mValue % i == 0)) {
                    applyWheelCustomInterval(false);
                }
                if (this.mFadeOutAnimator.isRunning()) {
                    this.mFadeOutAnimator.cancel();
                }
                if (this.mFadeInAnimator.isRunning()) {
                    this.mFadeInAnimator.cancel();
                }
                this.mAlpha = this.mIdleAlpha;
                this.mInputText.setVisibility(4);
                this.mDelegator.setDescendantFocusability(131072);
            }
            this.mLastFocusedChildVirtualViewId = -1;
            this.mDelegator.invalidate();
            SeslNumberPicker.OnEditTextModeChangedListener onEditTextModeChangedListener = this.mOnEditTextModeChangedListener;
            if (onEditTextModeChangedListener != null) {
                onEditTextModeChangedListener.onEditTextModeChanged(this.mDelegator, this.mIsEditTextMode);
            }
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setCustomIntervalValue(int i) {
        this.mWheelInterval = i;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void applyWheelCustomInterval(boolean z) {
        int i = this.mWheelInterval;
        if (i != 1) {
            this.mCustomWheelIntervalMode = z;
            if (z) {
                ensureValueAdjusted(i);
            }
            initializeSelectorWheelIndices();
            this.mDelegator.invalidate();
        }
    }

    private void ensureValueAdjusted(int i) {
        int i2 = this.mValue;
        int i3 = i2 % i;
        if (i3 != 0) {
            int i4 = i2 - i3;
            if (i3 > i / 2) {
                i4 += i;
            }
            setValueInternal(i4, true);
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean isChangedDefaultInterval() {
        return this.mWheelInterval != 1 && !this.mCustomWheelIntervalMode;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean isEditTextMode() {
        return this.mIsEditTextMode;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onMeasure(int i, int i2) {
        this.mDelegator.superOnMeasure(makeMeasureSpec(i, this.mMaxWidth), makeMeasureSpec(i2, this.mMaxHeight));
        this.mDelegator.setMeasuredDimensionWrapper(resolveSizeAndStateRespectingMinSize(this.mMinWidth, this.mDelegator.getMeasuredWidth(), i), resolveSizeAndStateRespectingMinSize(this.mMinHeight, this.mDelegator.getMeasuredHeight(), i2));
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if (!this.mIsEditTextModeEnabled) {
            return false;
        }
        if ((this.mInputText.hasFocus() || (!this.mIsEditTextModeEnabled && this.mDelegator.hasFocus())) && keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) {
            this.mIsPressedBackKey = true;
            hideSoftInput();
            setEditTextMode(false);
            return true;
        }
        this.mIsPressedBackKey = false;
        return false;
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onWindowFocusChanged(boolean z) {
        InputMethodManager inputMethodManager;
        if (z && this.mIsEditTextMode && this.mInputText.isFocused()) {
            showSoftInputForWindowFocused();
        } else if (z && this.mIsEditTextMode && !this.mInputText.isFocused() && (inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method")) != null && inputMethodManager.isActive(this.mInputText)) {
            inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
        }
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mDelegator.isEnabled() || this.mIsEditTextMode || this.mIsStartingAnimation || motionEvent.getActionMasked() != 0) {
            return false;
        }
        removeAllCallbacks();
        this.mInputText.setVisibility(4);
        float y = motionEvent.getY();
        this.mLastDownEventY = y;
        this.mLastDownOrMoveEventY = y;
        this.mLastDownEventTime = motionEvent.getEventTime();
        this.mIgnoreMoveEvents = false;
        this.mIgnoreUpEvent = false;
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
                if (this.mWheelInterval != 1) {
                    postSwitchIntervalOnLongPress();
                }
            } else if (f2 <= ((float) this.mBottomSelectionDividerBottom)) {
                this.mPerformClickOnTap = true;
                if (this.mWheelInterval != 1) {
                    postSwitchIntervalOnLongPress();
                } else {
                    postBeginSoftInputOnLongPressCommand();
                }
            } else if (this.mWheelInterval != 1) {
                postSwitchIntervalOnLongPress();
            }
        }
        return true;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mDelegator.isEnabled() || this.mIsEditTextMode || this.mIsStartingAnimation) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            removeBeginSoftInputCommand();
            removeSwitchIntervalOnLongPress();
            if (!this.mIgnoreUpEvent) {
                this.mPressedStateHelper.cancel();
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                int yVelocity = (int) velocityTracker.getYVelocity();
                int y = (int) motionEvent.getY();
                int abs = (int) Math.abs(((float) y) - this.mLastDownEventY);
                if (!this.mIsEditTextModeEnabled && this.mIgnoreMoveEvents) {
                    ensureScrollWheelAdjusted();
                    startFadeAnimation(true);
                    onScrollStateChange(0);
                } else if (Math.abs(yVelocity) <= this.mMinimumFlingVelocity) {
                    motionEvent.getEventTime();
                    long j = this.mLastDownEventTime;
                    if (abs > this.mTouchSlop) {
                        if (this.mIsLongClicked) {
                            showSoftInput();
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
            }
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1 && actionMasked != 3) {
            return false;
        }
        removeAllCallbacks();
        return false;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (this.mDelegator.isEnabled() && !this.mIsEditTextMode && !this.mIsStartingAnimation && (motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onConfigurationChanged(Configuration configuration) {
        boolean z = this.mIsBoldTextEnabled;
        updateBoldTextEnabledInSettings();
        boolean z2 = this.mIsBoldTextEnabled;
        if (z != z2) {
            this.mSelectorWheelPaint.setFakeBoldText(z2);
        }
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onFocusChanged(boolean z, int i, Rect rect) {
        AccessibilityNodeProviderImpl accessibilityNodeProviderImpl;
        AccessibilityNodeProviderImpl accessibilityNodeProviderImpl2;
        if (!z) {
            if (this.mAccessibilityManager.isEnabled() && (accessibilityNodeProviderImpl2 = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider()) != null) {
                if (this.mIsEditTextMode) {
                    this.mLastFocusedChildVirtualViewId = 2;
                }
                accessibilityNodeProviderImpl2.performAction(this.mLastFocusedChildVirtualViewId, 128, null);
            }
            this.mLastFocusedChildVirtualViewId = -1;
            this.mLastHoveredChildVirtualViewId = Integer.MIN_VALUE;
        } else {
            if (this.mIsEditTextMode) {
                this.mLastFocusedChildVirtualViewId = -1;
                if (this.mInputText.getVisibility() == 0) {
                    this.mInputText.requestFocus();
                }
            } else {
                this.mLastFocusedChildVirtualViewId = 1;
                if (!this.mWrapSelectorWheel && getValue() == getMinValue()) {
                    this.mLastFocusedChildVirtualViewId = 2;
                }
            }
            if (this.mAccessibilityManager.isEnabled() && (accessibilityNodeProviderImpl = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider()) != null) {
                if (this.mIsEditTextMode) {
                    this.mLastFocusedChildVirtualViewId = 2;
                }
                accessibilityNodeProviderImpl.performAction(this.mLastFocusedChildVirtualViewId, 64, null);
            }
        }
        this.mDelegator.invalidate();
    }

    /* JADX WARNING: Removed duplicated region for block: B:89:0x0122 A[ADDED_TO_REGION, RETURN] */
    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dispatchKeyEvent(android.view.KeyEvent r8) {
        /*
        // Method dump skipped, instructions count: 306
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.picker.widget.SeslNumberPickerSpinnerDelegate.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void dispatchTrackballEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (!this.mAccessibilityManager.isEnabled()) {
            return false;
        }
        int y = (int) motionEvent.getY();
        int i = 2;
        if (!this.mIsEditTextMode) {
            if (y <= this.mTopSelectionDividerTop) {
                i = 1;
            } else if (this.mBottomSelectionDividerBottom <= y) {
                i = 3;
            }
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

    private void playSoundAndHapticFeedback() {
        this.mAudioManager.playSoundEffect(this.mPickerSoundIndex);
        if (!this.mHapticPreDrawListener.mSkipHapticCalls) {
            this.mDelegator.performHapticFeedback(this.mPickerVibrateIndex);
            this.mHapticPreDrawListener.mSkipHapticCalls = true;
        }
    }

    /* access modifiers changed from: private */
    public static class HapticPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public boolean mSkipHapticCalls;

        private HapticPreDrawListener() {
            this.mSkipHapticCalls = false;
        }

        public boolean onPreDraw() {
            this.mSkipHapticCalls = false;
            return true;
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setEnabled(boolean z) {
        this.mInputText.setEnabled(z);
        if (!z && this.mScrollState != 0) {
            stopScrollAnimation();
            onScrollStateChange(0);
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void scrollBy(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr = this.mSelectorIndices;
        if (i2 != 0 && this.mSelectorElementHeight > 0) {
            if (!this.mWrapSelectorWheel && (i5 = this.mCurrentScrollOffset) + i2 > (i6 = this.mInitialScrollOffset) && iArr[2] <= this.mMinValue) {
                i2 = i6 - i5;
                if (this.mIsAmPm && this.mLastDownOrMoveEventY > ((float) this.mDelegator.getBottom())) {
                    this.mIgnoreMoveEvents = true;
                    return;
                }
            }
            if (!this.mWrapSelectorWheel && (i3 = this.mCurrentScrollOffset) + i2 < (i4 = this.mInitialScrollOffset) && iArr[2] >= this.mMaxValue) {
                i2 = i4 - i3;
                if (this.mIsAmPm && this.mLastDownOrMoveEventY < ((float) this.mDelegator.getTop())) {
                    this.mIgnoreMoveEvents = true;
                    return;
                }
            }
            this.mCurrentScrollOffset += i2;
            while (true) {
                int i7 = this.mCurrentScrollOffset;
                if (i7 - this.mInitialScrollOffset < this.mValueChangeOffset) {
                    break;
                }
                this.mCurrentScrollOffset = i7 - this.mSelectorElementHeight;
                decrementSelectorIndices(iArr);
                if (!this.mIsStartingAnimation) {
                    setValueInternal(iArr[2], true);
                    this.mIsValueChanged = true;
                    playSoundAndHapticFeedback();
                } else if (this.mWheelInterval != 1 && this.mCustomWheelIntervalMode) {
                    initializeSelectorWheelIndices();
                }
                if (!this.mWrapSelectorWheel && iArr[2] <= this.mMinValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            while (true) {
                int i8 = this.mCurrentScrollOffset;
                if (i8 - this.mInitialScrollOffset <= (-this.mValueChangeOffset)) {
                    this.mCurrentScrollOffset = i8 + this.mSelectorElementHeight;
                    incrementSelectorIndices(iArr);
                    if (!this.mIsStartingAnimation) {
                        setValueInternal(iArr[2], true);
                        this.mIsValueChanged = true;
                        playSoundAndHapticFeedback();
                    } else if (this.mWheelInterval != 1 && this.mCustomWheelIntervalMode) {
                        initializeSelectorWheelIndices();
                    }
                    if (!this.mWrapSelectorWheel && iArr[2] >= this.mMaxValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                    }
                } else {
                    return;
                }
            }
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int computeVerticalScrollOffset() {
        return this.mCurrentScrollOffset;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int computeVerticalScrollRange() {
        return ((this.mMaxValue - this.mMinValue) + 1) * this.mSelectorElementHeight;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int computeVerticalScrollExtent() {
        return this.mDelegator.getHeight();
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setOnValueChangedListener(SeslNumberPicker.OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setOnScrollListener(SeslNumberPicker.OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setOnEditTextModeChangedListener(SeslNumberPicker.OnEditTextModeChangedListener onEditTextModeChangedListener) {
        this.mOnEditTextModeChangedListener = onEditTextModeChangedListener;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setFormatter(SeslNumberPicker.Formatter formatter) {
        if (formatter != this.mFormatter) {
            this.mFormatter = formatter;
            initializeSelectorWheelIndices();
            updateInputTextView();
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setValue(int i) {
        if (!this.mFlingScroller.isFinished()) {
            stopScrollAnimation();
        }
        setValueInternal(i, false);
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean isEditTextModeNotAmPm() {
        return this.mIsEditTextMode && !this.mIsAmPm;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void performClick() {
        if (this.mIsEditTextModeEnabled) {
            showSoftInput();
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void performClick(boolean z) {
        if (this.mIsAmPm) {
            z = this.mValue != this.mMaxValue;
        }
        changeValueByOne(z);
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void performLongClick() {
        this.mIgnoreMoveEvents = true;
        if (this.mIsEditTextModeEnabled) {
            this.mIsLongClicked = true;
        }
    }

    private void showSoftInputForWindowFocused() {
        this.mDelegator.postDelayed(new Runnable() {
            /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass4 */

            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) SeslNumberPickerSpinnerDelegate.this.mContext.getSystemService("input_method");
                if (inputMethodManager != null && SeslNumberPickerSpinnerDelegate.this.mIsEditTextMode && SeslNumberPickerSpinnerDelegate.this.mInputText.isFocused() && !inputMethodManager.showSoftInput(SeslNumberPickerSpinnerDelegate.this.mInputText, 0)) {
                    SeslNumberPickerSpinnerDelegate.this.mDelegator.postDelayed(new Runnable() {
                        /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass4.AnonymousClass1 */

                        public void run() {
                            InputMethodManager inputMethodManager = (InputMethodManager) SeslNumberPickerSpinnerDelegate.this.mContext.getSystemService("input_method");
                            if (inputMethodManager != null && SeslNumberPickerSpinnerDelegate.this.mIsEditTextMode && SeslNumberPickerSpinnerDelegate.this.mInputText.isFocused()) {
                                inputMethodManager.showSoftInput(SeslNumberPickerSpinnerDelegate.this.mInputText, 0);
                            }
                        }
                    }, 20);
                }
            }
        }, 20);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager != null) {
            this.mInputText.setVisibility(0);
            this.mInputText.requestFocus();
            inputMethodManager.viewClicked(this.mInputText);
            inputMethodManager.showSoftInput(this.mInputText, 0);
        }
    }

    private void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager != null && inputMethodManager.isActive(this.mInputText)) {
            inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            this.mInputText.setVisibility(4);
        }
    }

    private void tryComputeMaxWidth() {
        int i;
        if (this.mComputeMaxWidth) {
            String[] strArr = this.mDisplayedValues;
            int i2 = 0;
            if (strArr == null) {
                float f = 0.0f;
                for (int i3 = 0; i3 <= 9; i3++) {
                    float measureText = this.mSelectorWheelPaint.measureText(formatNumberWithLocale(i3));
                    if (measureText > f) {
                        f = measureText;
                    }
                }
                for (int i4 = this.mMaxValue; i4 > 0; i4 /= 10) {
                    i2++;
                }
                i = (int) (((float) i2) * f);
            } else {
                int length = strArr.length;
                int i5 = 0;
                int i6 = 0;
                while (i2 < length) {
                    float measureText2 = this.mSelectorWheelPaint.measureText(this.mDisplayedValues[i2]);
                    if (measureText2 > ((float) i5)) {
                        i5 = (int) measureText2;
                        i6 = this.mDisplayedValues[i2].length();
                    }
                    i2++;
                }
                i = i5;
                i2 = i6;
            }
            int paddingLeft = i + this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight();
            if (isHighContrastFontEnabled()) {
                paddingLeft += ((int) Math.ceil((double) (SeslPaintReflector.getHCTStrokeWidth(this.mSelectorWheelPaint) / 2.0f))) * (i2 + 2);
            }
            if (this.mMaxWidth != paddingLeft) {
                int i7 = this.mMinWidth;
                if (paddingLeft > i7) {
                    this.mMaxWidth = paddingLeft;
                } else {
                    this.mMaxWidth = i7;
                }
                this.mDelegator.invalidate();
            }
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setWrapSelectorWheel(boolean z) {
        this.mWrapSelectorWheelPreferred = z;
        updateWrapSelectorWheel();
    }

    private void updateWrapSelectorWheel() {
        boolean z = true;
        if (!(this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length) || !this.mWrapSelectorWheelPreferred) {
            z = false;
        }
        if (this.mWrapSelectorWheel != z) {
            this.mWrapSelectorWheel = z;
            initializeSelectorWheelIndices();
            this.mDelegator.invalidate();
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int getValue() {
        return this.mValue;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int getMinValue() {
        return this.mMinValue;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setMinValue(int i) {
        if (this.mMinValue != i) {
            if (i >= 0) {
                this.mMinValue = i;
                int i2 = this.mMinValue;
                if (i2 > this.mValue) {
                    this.mValue = i2;
                }
                updateWrapSelectorWheel();
                initializeSelectorWheelIndices();
                updateInputTextView();
                tryComputeMaxWidth();
                this.mDelegator.invalidate();
                return;
            }
            throw new IllegalArgumentException("minValue must be >= 0");
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int getMaxValue() {
        return this.mMaxValue;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setMaxValue(int i) {
        if (this.mMaxValue != i) {
            if (i >= 0) {
                this.mMaxValue = i;
                int i2 = this.mMaxValue;
                if (i2 < this.mValue) {
                    this.mValue = i2;
                }
                updateWrapSelectorWheel();
                initializeSelectorWheelIndices();
                updateInputTextView();
                tryComputeMaxWidth();
                this.mDelegator.invalidate();
                return;
            }
            throw new IllegalArgumentException("maxValue must be >= 0");
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setDisplayedValues(String[] strArr) {
        if (this.mDisplayedValues != strArr) {
            this.mDisplayedValues = strArr;
            if (this.mDisplayedValues != null) {
                this.mInputText.setRawInputType(524289);
            } else {
                this.mInputText.setRawInputType(2);
            }
            updateInputTextView();
            initializeSelectorWheelIndices();
            tryComputeMaxWidth();
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setErrorToastMessage(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mToastText = str;
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setTextSize(float f) {
        this.mTextSize = (int) TypedValue.applyDimension(1, f, this.mContext.getResources().getDisplayMetrics());
        this.mSelectorWheelPaint.setTextSize((float) this.mTextSize);
        this.mInputText.setTextSize(0, (float) this.mTextSize);
        tryComputeMaxWidth();
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
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

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public int getPaintFlags() {
        return this.mSelectorWheelPaint.getFlags();
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setPaintFlags(int i) {
        if (this.mSelectorWheelPaint.getFlags() != i) {
            this.mSelectorWheelPaint.setFlags(i);
            this.mInputText.setPaintFlags(i);
            tryComputeMaxWidth();
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void startAnimation(final int i, SeslAnimationListener seslAnimationListener) {
        this.mAnimationListener = seslAnimationListener;
        if (!this.mIsEditTextMode) {
            if (this.mFadeOutAnimator.isStarted()) {
                this.mFadeOutAnimator.cancel();
            }
            if (this.mFadeInAnimator.isStarted()) {
                this.mFadeInAnimator.cancel();
            }
            this.mAlpha = this.mActivatedAlpha;
            this.mDelegator.post(new Runnable() {
                /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass5 */

                public void run() {
                    if (SeslNumberPickerSpinnerDelegate.this.mSelectorElementHeight == 0) {
                        SeslNumberPickerSpinnerDelegate.this.mReservedStartAnimation = true;
                        return;
                    }
                    SeslNumberPickerSpinnerDelegate.this.mIsStartingAnimation = true;
                    SeslNumberPickerSpinnerDelegate seslNumberPickerSpinnerDelegate = SeslNumberPickerSpinnerDelegate.this;
                    seslNumberPickerSpinnerDelegate.mFlingScroller = seslNumberPickerSpinnerDelegate.mCustomScroller;
                    final int i = SeslNumberPickerSpinnerDelegate.this.getValue() != SeslNumberPickerSpinnerDelegate.this.getMinValue() ? SeslNumberPickerSpinnerDelegate.this.mSelectorElementHeight : -SeslNumberPickerSpinnerDelegate.this.mSelectorElementHeight;
                    int i2 = SeslNumberPickerSpinnerDelegate.this.mIsAmPm ? i : SeslNumberPickerSpinnerDelegate.this.mSelectorElementHeight * 5;
                    if (!SeslNumberPickerSpinnerDelegate.this.mIsAmPm) {
                        i = (int) (((double) SeslNumberPickerSpinnerDelegate.this.mSelectorElementHeight) * 5.4d);
                    }
                    SeslNumberPickerSpinnerDelegate.this.scrollBy(0, i2);
                    SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate();
                    new Handler().postDelayed(new Runnable() {
                        /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass5.AnonymousClass1 */

                        public void run() {
                            new Handler().postDelayed(new Runnable() {
                                /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass5.AnonymousClass1.AnonymousClass1 */

                                public void run() {
                                    if (!SeslNumberPickerSpinnerDelegate.this.moveToFinalScrollerPosition(SeslNumberPickerSpinnerDelegate.this.mFlingScroller)) {
                                        SeslNumberPickerSpinnerDelegate.this.moveToFinalScrollerPosition(SeslNumberPickerSpinnerDelegate.this.mAdjustScroller);
                                    }
                                    SeslNumberPickerSpinnerDelegate.this.mPreviousScrollerY = 0;
                                    SeslNumberPickerSpinnerDelegate.this.mFlingScroller.startScroll(0, 0, 0, -i, SeslNumberPickerSpinnerDelegate.this.mIsAmPm ? SeslNumberPickerSpinnerDelegate.START_ANIMATION_SCROLL_DURATION : SeslNumberPickerSpinnerDelegate.START_ANIMATION_SCROLL_DURATION_2016B);
                                    SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate();
                                    new Handler().postDelayed(new Runnable() {
                                        /* class androidx.picker.widget.SeslNumberPickerSpinnerDelegate.AnonymousClass5.AnonymousClass1.AnonymousClass1.AnonymousClass1 */

                                        public void run() {
                                            SeslNumberPickerSpinnerDelegate.this.moveToFinalScrollerPosition(SeslNumberPickerSpinnerDelegate.this.mFlingScroller);
                                            SeslNumberPickerSpinnerDelegate.this.mFlingScroller.abortAnimation();
                                            SeslNumberPickerSpinnerDelegate.this.mAdjustScroller.abortAnimation();
                                            SeslNumberPickerSpinnerDelegate.this.ensureScrollWheelAdjusted();
                                            SeslNumberPickerSpinnerDelegate.this.mFlingScroller = SeslNumberPickerSpinnerDelegate.this.mLinearScroller;
                                            SeslNumberPickerSpinnerDelegate.this.mIsStartingAnimation = false;
                                            SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate();
                                            SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(true);
                                            if (SeslNumberPickerSpinnerDelegate.this.mAnimationListener != null) {
                                                SeslNumberPickerSpinnerDelegate.this.mAnimationListener.onAnimationEnd();
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
        int i = 0;
        if (z) {
            ValueAnimator valueAnimator = this.mFadeOutAnimator;
            if (!this.mFlingScroller.isFinished()) {
                i = this.mFlingScroller.getDuration();
            }
            valueAnimator.setStartDelay((long) (i + 500));
            this.mFadeOutAnimator.start();
            return;
        }
        this.mFadeInAnimator.setFloatValues(this.mAlpha, this.mActivatedAlpha);
        this.mFadeOutAnimator.cancel();
        this.mFadeInAnimator.start();
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onDetachedFromWindow() {
        removeAllCallbacks();
        this.mDelegator.getViewTreeObserver().removeOnPreDrawListener(this.mHapticPreDrawListener);
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onAttachedToWindow() {
        this.mDelegator.getViewTreeObserver().addOnPreDrawListener(this.mHapticPreDrawListener);
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onDraw(Canvas canvas) {
        int[] iArr;
        int right = this.mDelegator.getRight();
        int left = this.mDelegator.getLeft();
        int bottom = this.mDelegator.getBottom();
        float f = ((float) (right - left)) / 2.0f;
        float f2 = (float) (this.mCurrentScrollOffset - this.mSelectorElementHeight);
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
        float f3 = f2;
        for (int i2 : this.mSelectorIndices) {
            String str = this.mSelectorIndexToStringCache.get(i2);
            float f4 = this.mAlpha;
            float f5 = this.mIdleAlpha;
            if (f4 < f5) {
                f4 = f5;
            }
            int descent = (int) ((((this.mSelectorWheelPaint.descent() - this.mSelectorWheelPaint.ascent()) / 2.0f) + f3) - this.mSelectorWheelPaint.descent());
            int i3 = this.mTopSelectionDividerTop;
            int i4 = this.mInitialScrollOffset;
            if (f3 >= ((float) (i3 - i4))) {
                int i5 = this.mBottomSelectionDividerBottom;
                if (f3 <= ((float) (i4 + i5))) {
                    if (f3 <= ((float) (i3 + i5)) / 2.0f) {
                        canvas.save();
                        canvas.clipRect(0, this.mTopSelectionDividerTop, right, this.mBottomSelectionDividerBottom);
                        this.mSelectorWheelPaint.setColor(this.mTextColor);
                        setHcfTextAppearance(true);
                        float f6 = (float) descent;
                        canvas.drawText(str, f, f6, this.mSelectorWheelPaint);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0, 0, right, this.mTopSelectionDividerTop);
                        setHcfTextAppearance(false);
                        this.mSelectorWheelPaint.setAlpha((int) (f4 * 255.0f));
                        canvas.drawText(str, f, f6, this.mSelectorWheelPaint);
                        canvas.restore();
                    } else {
                        canvas.save();
                        canvas.clipRect(0, this.mTopSelectionDividerTop, right, this.mBottomSelectionDividerBottom);
                        setHcfTextAppearance(true);
                        this.mSelectorWheelPaint.setColor(this.mTextColor);
                        float f7 = (float) descent;
                        canvas.drawText(str, f, f7, this.mSelectorWheelPaint);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0, this.mBottomSelectionDividerBottom, right, bottom);
                        this.mSelectorWheelPaint.setAlpha((int) (f4 * 255.0f));
                        setHcfTextAppearance(false);
                        canvas.drawText(str, f, f7, this.mSelectorWheelPaint);
                        canvas.restore();
                    }
                    f3 += (float) this.mSelectorElementHeight;
                }
            }
            canvas.save();
            this.mSelectorWheelPaint.setAlpha((int) (f4 * 255.0f));
            setHcfTextAppearance(false);
            canvas.drawText(str, f, (float) descent, this.mSelectorWheelPaint);
            canvas.restore();
            f3 += (float) this.mSelectorElementHeight;
        }
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(NumberPicker.class.getName());
        accessibilityEvent.setScrollable(true);
        accessibilityEvent.setScrollY((this.mMinValue + this.mValue) * this.mSelectorElementHeight);
        accessibilityEvent.setMaxScrollY((this.mMaxValue - this.mMinValue) * this.mSelectorElementHeight);
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (this.mAccessibilityNodeProvider == null) {
            this.mAccessibilityNodeProvider = new AccessibilityNodeProviderImpl();
        }
        return this.mAccessibilityNodeProvider;
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
        int i;
        this.mSelectorIndexToStringCache.clear();
        int[] iArr = this.mSelectorIndices;
        if (this.mIsStartingAnimation) {
            i = iArr[2];
        } else {
            i = getValue();
        }
        for (int i2 = 0; i2 < this.mSelectorIndices.length; i2++) {
            int i3 = ((i2 - 2) * (this.mCustomWheelIntervalMode ? this.mWheelInterval : 1)) + i;
            if (this.mWrapSelectorWheel) {
                i3 = getWrappedSelectorIndex(i3);
            }
            iArr[i2] = i3;
            ensureCachedScrollSelectorValue(iArr[i2]);
        }
    }

    private void setValueInternal(int i, boolean z) {
        int i2;
        if (this.mValue != i) {
            if (this.mWrapSelectorWheel) {
                i2 = getWrappedSelectorIndex(i);
            } else {
                i2 = Math.min(Math.max(i, this.mMinValue), this.mMaxValue);
            }
            int i3 = this.mValue;
            this.mValue = i2;
            updateInputTextView();
            if (z) {
                notifyChange(i3, i2);
            }
            initializeSelectorWheelIndices();
            this.mDelegator.invalidate();
        } else if (isCharacterNumberLanguage()) {
            updateInputTextView();
            this.mDelegator.invalidate();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void changeValueByOne(boolean z) {
        this.mInputText.setVisibility(4);
        if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        this.mPreviousScrollerY = 0;
        if (z) {
            this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, 500);
        } else {
            this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, 500);
        }
        this.mDelegator.invalidate();
    }

    private void initializeSelectorWheel() {
        if (this.mIsStartingAnimation) {
            if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
                moveToFinalScrollerPosition(this.mAdjustScroller);
            }
            stopScrollAnimation();
        } else {
            initializeSelectorWheelIndices();
        }
        this.mSelectorTextGapHeight = (int) ((((float) ((this.mDelegator.getBottom() - this.mDelegator.getTop()) - (this.mTextSize * 3))) / ((float) 3)) + 0.5f);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        int i = this.mModifiedTxtHeight;
        if (i > this.mSelectorElementHeight || this.mIsAmPm) {
            i = this.mDelegator.getHeight() / 3;
        }
        this.mValueChangeOffset = i;
        this.mInitialScrollOffset = (this.mInputText.getTop() + (this.mModifiedTxtHeight / 2)) - this.mSelectorElementHeight;
        this.mCurrentScrollOffset = this.mInitialScrollOffset;
        ((SeslNumberPicker.CustomEditText) this.mInputText).setEditTextPosition(((int) (((this.mSelectorWheelPaint.descent() - this.mSelectorWheelPaint.ascent()) / 2.0f) - this.mSelectorWheelPaint.descent())) - (this.mInputText.getBaseline() - (this.mModifiedTxtHeight / 2)));
        if (this.mReservedStartAnimation) {
            startAnimation(0, this.mAnimationListener);
            this.mReservedStartAnimation = false;
        }
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            if (!ensureScrollWheelAdjusted()) {
                updateInputTextView();
            }
            onScrollStateChange(0);
        } else if (this.mScrollState != 1) {
            updateInputTextView();
        }
    }

    private void onScrollStateChange(int i) {
        if (this.mScrollState != i) {
            this.mScrollState = i;
            SeslNumberPicker.OnScrollListener onScrollListener = this.mOnScrollListener;
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
    private int getWrappedSelectorIndex(int i) {
        int i2 = this.mMaxValue;
        if (i > i2) {
            int i3 = this.mMinValue;
            return i3 + ((i - i3) % ((i2 - i3) + 1));
        }
        int i4 = this.mMinValue;
        return i < i4 ? i2 - ((i2 - i) % ((i2 - i4) + 1)) : i;
    }

    private void incrementSelectorIndices(int[] iArr) {
        System.arraycopy(iArr, 1, iArr, 0, iArr.length - 1);
        int i = iArr[iArr.length - 2] + 1;
        if (this.mWrapSelectorWheel && i > this.mMaxValue) {
            i = this.mMinValue;
        }
        iArr[iArr.length - 1] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void decrementSelectorIndices(int[] iArr) {
        System.arraycopy(iArr, 0, iArr, 1, iArr.length - 1);
        int i = iArr[1] - 1;
        if (this.mWrapSelectorWheel && i < this.mMinValue) {
            i = this.mMaxValue;
        }
        iArr[0] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void ensureCachedScrollSelectorValue(int i) {
        String str;
        SparseArray<String> sparseArray = this.mSelectorIndexToStringCache;
        if (sparseArray.get(i) == null) {
            int i2 = this.mMinValue;
            if (i < i2 || i > this.mMaxValue) {
                str = "";
            } else {
                String[] strArr = this.mDisplayedValues;
                str = strArr != null ? strArr[i - i2] : formatNumber(i);
            }
            sparseArray.put(i, str);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String formatNumber(int i) {
        SeslNumberPicker.Formatter formatter = this.mFormatter;
        return formatter != null ? formatter.format(i) : formatNumberWithLocale(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void validateInputTextView(View view) {
        String valueOf = String.valueOf(((TextView) view).getText());
        int selectedPos = getSelectedPos(valueOf);
        boolean z = false;
        if (TextUtils.isEmpty(valueOf) || this.mValue == selectedPos) {
            int i = this.mWheelInterval;
            if (i == 1 || !this.mCustomWheelIntervalMode || !this.mIsPressedBackKey) {
                updateInputTextView();
                return;
            }
            if (selectedPos % i == 0) {
                z = true;
            }
            applyWheelCustomInterval(z);
            return;
        }
        int i2 = this.mWheelInterval;
        if (i2 != 1 && this.mCustomWheelIntervalMode) {
            if (selectedPos % i2 == 0) {
                z = true;
            }
            applyWheelCustomInterval(z);
        }
        setValueInternal(selectedPos, true);
    }

    private boolean updateInputTextView() {
        String[] strArr = this.mDisplayedValues;
        String formatNumber = strArr == null ? formatNumber(this.mValue) : strArr[this.mValue - this.mMinValue];
        if (TextUtils.isEmpty(formatNumber) || formatNumber.equals(this.mInputText.getText().toString())) {
            return false;
        }
        this.mInputText.setText(formatNumber);
        Selection.setSelection(this.mInputText.getText(), this.mInputText.getText().length());
        return true;
    }

    private void notifyChange(int i, int i2) {
        String str;
        AccessibilityNodeProviderImpl accessibilityNodeProviderImpl;
        if (this.mAccessibilityManager.isEnabled() && !this.mIsStartingAnimation) {
            int wrappedSelectorIndex = getWrappedSelectorIndex(this.mValue);
            if (wrappedSelectorIndex <= this.mMaxValue) {
                String[] strArr = this.mDisplayedValues;
                str = strArr == null ? formatNumber(wrappedSelectorIndex) : strArr[wrappedSelectorIndex - this.mMinValue];
            } else {
                str = null;
            }
            this.mDelegator.announceForAccessibility(str);
            if (!this.mIsEditTextModeEnabled && (accessibilityNodeProviderImpl = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider()) != null) {
                accessibilityNodeProviderImpl.performAction(2, 64, null);
            }
        }
        SeslNumberPicker.OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this.mDelegator, i, this.mValue);
        }
    }

    private void postSwitchIntervalOnLongPress() {
        if (this.mSwitchIntervalOnLongPressCommand == null) {
            this.mSwitchIntervalOnLongPressCommand = new SwitchIntervalOnLongPressCommand();
        } else {
            this.mDelegator.removeCallbacks(this.mSwitchIntervalOnLongPressCommand);
        }
        this.mDelegator.postDelayed(this.mSwitchIntervalOnLongPressCommand, (long) ViewConfiguration.getLongPressTimeout());
    }

    private void removeSwitchIntervalOnLongPress() {
        if (this.mSwitchIntervalOnLongPressCommand != null) {
            this.mDelegator.removeCallbacks(this.mSwitchIntervalOnLongPressCommand);
        }
    }

    private void postBeginSoftInputOnLongPressCommand() {
        if (this.mBeginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new BeginSoftInputOnLongPressCommand();
        } else {
            this.mDelegator.removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        this.mDelegator.postDelayed(this.mBeginSoftInputOnLongPressCommand, (long) ViewConfiguration.getLongPressTimeout());
    }

    private void removeBeginSoftInputCommand() {
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            this.mDelegator.removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
    }

    private void removeAllCallbacks() {
        if (this.mSwitchIntervalOnLongPressCommand != null) {
            this.mDelegator.removeCallbacks(this.mSwitchIntervalOnLongPressCommand);
        }
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            this.mDelegator.removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getSelectedPos(String str) {
        if (this.mDisplayedValues == null) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                return this.mMinValue;
            }
        } else {
            for (int i = 0; i < this.mDisplayedValues.length; i++) {
                str = str.toLowerCase();
                if (this.mDisplayedValues[i].toLowerCase().startsWith(str)) {
                    return this.mMinValue + i;
                }
            }
            return Integer.parseInt(str);
        }
    }

    class InputTextFilter extends NumberKeyListener {
        public int getInputType() {
            return 1;
        }

        InputTextFilter() {
        }

        /* access modifiers changed from: protected */
        public char[] getAcceptedChars() {
            return SeslNumberPickerSpinnerDelegate.DIGIT_CHARACTERS;
        }

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (SeslNumberPickerSpinnerDelegate.this.mDisplayedValues == null) {
                CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
                if (filter == null) {
                    filter = charSequence.subSequence(i, i2);
                }
                String str = String.valueOf(spanned.subSequence(0, i3)) + ((Object) filter) + ((Object) spanned.subSequence(i4, spanned.length()));
                if ("".equals(str)) {
                    return str;
                }
                if (SeslNumberPickerSpinnerDelegate.this.getSelectedPos(str) <= SeslNumberPickerSpinnerDelegate.this.mMaxValue) {
                    int length = str.length();
                    SeslNumberPickerSpinnerDelegate seslNumberPickerSpinnerDelegate = SeslNumberPickerSpinnerDelegate.this;
                    if (length <= seslNumberPickerSpinnerDelegate.formatNumber(seslNumberPickerSpinnerDelegate.mMaxValue).length()) {
                        return filter;
                    }
                }
                if (SeslNumberPickerSpinnerDelegate.this.mIsEditTextMode) {
                    if (SeslNumberPickerSpinnerDelegate.this.mToast == null) {
                        SeslNumberPickerSpinnerDelegate seslNumberPickerSpinnerDelegate2 = SeslNumberPickerSpinnerDelegate.this;
                        seslNumberPickerSpinnerDelegate2.mToast = Toast.makeText(seslNumberPickerSpinnerDelegate2.mContext, SeslNumberPickerSpinnerDelegate.this.mToastText, 0);
                    }
                    SeslNumberPickerSpinnerDelegate.this.mToast.show();
                }
                return "";
            }
            String valueOf = String.valueOf(charSequence.subSequence(i, i2));
            String lowerCase = String.valueOf(String.valueOf(spanned.subSequence(0, i3)) + ((Object) valueOf) + ((Object) spanned.subSequence(i4, spanned.length()))).toLowerCase();
            String[] strArr = SeslNumberPickerSpinnerDelegate.this.mDisplayedValues;
            int length2 = strArr.length;
            for (int i5 = 0; i5 < length2; i5++) {
                String lowerCase2 = strArr[i5].toLowerCase();
                if (SeslNumberPickerSpinnerDelegate.this.needCompareEqualMonthLanguage()) {
                    if (lowerCase2.equals(lowerCase)) {
                        return valueOf;
                    }
                } else if (lowerCase2.startsWith(lowerCase)) {
                    return valueOf;
                }
            }
            if (SeslNumberPickerSpinnerDelegate.this.mIsEditTextMode && !TextUtils.isEmpty(lowerCase)) {
                if (SeslNumberPickerSpinnerDelegate.this.mToast == null) {
                    SeslNumberPickerSpinnerDelegate seslNumberPickerSpinnerDelegate3 = SeslNumberPickerSpinnerDelegate.this;
                    seslNumberPickerSpinnerDelegate3.mToast = Toast.makeText(seslNumberPickerSpinnerDelegate3.mContext, SeslNumberPickerSpinnerDelegate.this.mToastText, 0);
                }
                SeslNumberPickerSpinnerDelegate.this.mToast.show();
            }
            return "";
        }
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
            androidx.picker.widget.SeslNumberPicker r9 = r8.mDelegator
            r9.invalidate()
            r8.mIsValueChanged = r1
            r9 = 1
            return r9
        L_0x0042:
            r8.mIsValueChanged = r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.picker.widget.SeslNumberPickerSpinnerDelegate.ensureScrollWheelAdjusted(int):boolean");
    }

    /* access modifiers changed from: package-private */
    public class PressedStateHelper implements Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS = 1;
        private final int MODE_TAPPED = 2;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
        }

        public void cancel() {
            int right = SeslNumberPickerSpinnerDelegate.this.mDelegator.getRight();
            int bottom = SeslNumberPickerSpinnerDelegate.this.mDelegator.getBottom();
            this.mMode = 0;
            this.mManagedButton = 0;
            SeslNumberPickerSpinnerDelegate.this.mDelegator.removeCallbacks(this);
            if (SeslNumberPickerSpinnerDelegate.this.mIncrementVirtualButtonPressed) {
                SeslNumberPickerSpinnerDelegate.this.mIncrementVirtualButtonPressed = false;
                SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
            }
            if (SeslNumberPickerSpinnerDelegate.this.mDecrementVirtualButtonPressed) {
                SeslNumberPickerSpinnerDelegate.this.mDecrementVirtualButtonPressed = false;
                SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop);
            }
        }

        public void buttonPressDelayed(int i) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = i;
            SeslNumberPickerSpinnerDelegate.this.mDelegator.postDelayed(this, (long) ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int i) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = i;
            SeslNumberPickerSpinnerDelegate.this.mDelegator.post(this);
        }

        public void run() {
            int right = SeslNumberPickerSpinnerDelegate.this.mDelegator.getRight();
            int bottom = SeslNumberPickerSpinnerDelegate.this.mDelegator.getBottom();
            int i = this.mMode;
            if (i == 1) {
                int i2 = this.mManagedButton;
                if (i2 == 1) {
                    SeslNumberPickerSpinnerDelegate.this.mIncrementVirtualButtonPressed = true;
                    SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
                } else if (i2 == 2) {
                    SeslNumberPickerSpinnerDelegate.this.mDecrementVirtualButtonPressed = true;
                    SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop);
                }
            } else if (i == 2) {
                int i3 = this.mManagedButton;
                if (i3 == 1) {
                    if (!SeslNumberPickerSpinnerDelegate.this.mIncrementVirtualButtonPressed) {
                        SeslNumberPickerSpinnerDelegate.this.mDelegator.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                    }
                    SeslNumberPickerSpinnerDelegate seslNumberPickerSpinnerDelegate = SeslNumberPickerSpinnerDelegate.this;
                    seslNumberPickerSpinnerDelegate.mIncrementVirtualButtonPressed = !seslNumberPickerSpinnerDelegate.mIncrementVirtualButtonPressed;
                    SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
                } else if (i3 == 2) {
                    if (!SeslNumberPickerSpinnerDelegate.this.mDecrementVirtualButtonPressed) {
                        SeslNumberPickerSpinnerDelegate.this.mDelegator.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                    }
                    SeslNumberPickerSpinnerDelegate seslNumberPickerSpinnerDelegate2 = SeslNumberPickerSpinnerDelegate.this;
                    seslNumberPickerSpinnerDelegate2.mDecrementVirtualButtonPressed = !seslNumberPickerSpinnerDelegate2.mDecrementVirtualButtonPressed;
                    SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public class SwitchIntervalOnLongPressCommand implements Runnable {
        SwitchIntervalOnLongPressCommand() {
        }

        public void run() {
            SeslNumberPickerSpinnerDelegate.this.mIgnoreMoveEvents = true;
            SeslNumberPickerSpinnerDelegate.this.mIgnoreUpEvent = true;
            SeslNumberPickerSpinnerDelegate seslNumberPickerSpinnerDelegate = SeslNumberPickerSpinnerDelegate.this;
            seslNumberPickerSpinnerDelegate.applyWheelCustomInterval(true ^ seslNumberPickerSpinnerDelegate.mCustomWheelIntervalMode);
        }
    }

    /* access modifiers changed from: package-private */
    public class BeginSoftInputOnLongPressCommand implements Runnable {
        BeginSoftInputOnLongPressCommand() {
        }

        public void run() {
            SeslNumberPickerSpinnerDelegate.this.performLongClick();
        }
    }

    /* access modifiers changed from: package-private */
    public class AccessibilityNodeProviderImpl extends AccessibilityNodeProvider {
        private static final int UNDEFINED = Integer.MIN_VALUE;
        private static final int VIRTUAL_VIEW_ID_DECREMENT = 1;
        private static final int VIRTUAL_VIEW_ID_INCREMENT = 3;
        private static final int VIRTUAL_VIEW_ID_INPUT = 2;
        private int mAccessibilityFocusedView = Integer.MIN_VALUE;
        private final int[] mTempArray = new int[2];
        private final Rect mTempRect = new Rect();

        AccessibilityNodeProviderImpl() {
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            int left = SeslNumberPickerSpinnerDelegate.this.mDelegator.getLeft();
            int right = SeslNumberPickerSpinnerDelegate.this.mDelegator.getRight();
            int top = SeslNumberPickerSpinnerDelegate.this.mDelegator.getTop();
            int bottom = SeslNumberPickerSpinnerDelegate.this.mDelegator.getBottom();
            int scrollX = SeslNumberPickerSpinnerDelegate.this.mDelegator.getScrollX();
            int scrollY = SeslNumberPickerSpinnerDelegate.this.mDelegator.getScrollY();
            if (!(SeslNumberPickerSpinnerDelegate.this.mLastFocusedChildVirtualViewId == -1 && SeslNumberPickerSpinnerDelegate.this.mLastHoveredChildVirtualViewId == Integer.MIN_VALUE)) {
                if (i == -1) {
                    return createAccessibilityNodeInfoForNumberPicker(scrollX, scrollY, (right - left) + scrollX, (bottom - top) + scrollY);
                }
                if (i == 1) {
                    return createAccessibilityNodeInfoForVirtualButton(1, getVirtualDecrementButtonText(), scrollX, scrollY, scrollX + (right - left), SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop + SeslNumberPickerSpinnerDelegate.this.mSelectionDividerHeight);
                }
                if (i == 2) {
                    return createAccessibiltyNodeInfoForInputText(scrollX, SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop + SeslNumberPickerSpinnerDelegate.this.mSelectionDividerHeight, (right - left) + scrollX, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom - SeslNumberPickerSpinnerDelegate.this.mSelectionDividerHeight);
                }
                if (i == 3) {
                    return createAccessibilityNodeInfoForVirtualButton(3, getVirtualIncrementButtonText(), scrollX, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom - SeslNumberPickerSpinnerDelegate.this.mSelectionDividerHeight, scrollX + (right - left), scrollY + (bottom - top));
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
            if (SeslNumberPickerSpinnerDelegate.this.mIsStartingAnimation) {
                return false;
            }
            int right = SeslNumberPickerSpinnerDelegate.this.mDelegator.getRight();
            int bottom = SeslNumberPickerSpinnerDelegate.this.mDelegator.getBottom();
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
                                    SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
                                    return true;
                                } else if (this.mAccessibilityFocusedView == i) {
                                    return false;
                                } else {
                                    this.mAccessibilityFocusedView = i;
                                    sendAccessibilityEventForVirtualView(i, 32768);
                                    SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom, right, bottom);
                                    return true;
                                }
                            } else if (!SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                                return false;
                            } else {
                                SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(false);
                                SeslNumberPickerSpinnerDelegate.this.changeValueByOne(true);
                                sendAccessibilityEventForVirtualView(i, 1);
                                SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(true);
                                return true;
                            }
                        }
                    } else if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 != 16) {
                                if (i2 != 32) {
                                    if (i2 != 64) {
                                        if (i2 != 128) {
                                            return SeslNumberPickerSpinnerDelegate.this.mInputText.performAccessibilityAction(i2, bundle);
                                        }
                                        if (this.mAccessibilityFocusedView != i) {
                                            return false;
                                        }
                                        this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                                        sendAccessibilityEventForVirtualView(i, 65536);
                                        SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop, right, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom);
                                        return true;
                                    } else if (this.mAccessibilityFocusedView == i) {
                                        return false;
                                    } else {
                                        this.mAccessibilityFocusedView = i;
                                        sendAccessibilityEventForVirtualView(i, 32768);
                                        SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop, right, SeslNumberPickerSpinnerDelegate.this.mBottomSelectionDividerBottom);
                                        return true;
                                    }
                                } else if (!SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                                    return false;
                                } else {
                                    SeslNumberPickerSpinnerDelegate.this.performLongClick();
                                    return true;
                                }
                            } else if (!SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                                return false;
                            } else {
                                SeslNumberPickerSpinnerDelegate.this.performClick();
                                return true;
                            }
                        } else if (!SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled() || !SeslNumberPickerSpinnerDelegate.this.mInputText.isFocused()) {
                            return false;
                        } else {
                            SeslNumberPickerSpinnerDelegate.this.mInputText.clearFocus();
                            return true;
                        }
                    } else if (!SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled() || SeslNumberPickerSpinnerDelegate.this.mInputText.isFocused()) {
                        return false;
                    } else {
                        return SeslNumberPickerSpinnerDelegate.this.mInputText.requestFocus();
                    }
                } else if (i2 != 16) {
                    if (i2 != 64) {
                        if (i2 != 128 || this.mAccessibilityFocusedView != i) {
                            return false;
                        }
                        this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                        sendAccessibilityEventForVirtualView(i, 65536);
                        SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop);
                        return true;
                    } else if (this.mAccessibilityFocusedView == i) {
                        return false;
                    } else {
                        this.mAccessibilityFocusedView = i;
                        sendAccessibilityEventForVirtualView(i, 32768);
                        SeslNumberPickerSpinnerDelegate.this.mDelegator.invalidate(0, 0, right, SeslNumberPickerSpinnerDelegate.this.mTopSelectionDividerTop);
                        return true;
                    }
                } else if (!SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                    return false;
                } else {
                    SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(false);
                    SeslNumberPickerSpinnerDelegate.this.changeValueByOne(false);
                    sendAccessibilityEventForVirtualView(i, 1);
                    SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(true);
                    return true;
                }
            } else if (i2 != 64) {
                if (i2 != 128) {
                    if (i2 != 4096) {
                        if (i2 == 8192) {
                            if (!SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled() || (!SeslNumberPickerSpinnerDelegate.this.getWrapSelectorWheel() && SeslNumberPickerSpinnerDelegate.this.getValue() <= SeslNumberPickerSpinnerDelegate.this.getMinValue())) {
                                return false;
                            }
                            SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(false);
                            SeslNumberPickerSpinnerDelegate.this.changeValueByOne(false);
                            SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(true);
                            return true;
                        }
                    } else if (!SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled() || (!SeslNumberPickerSpinnerDelegate.this.getWrapSelectorWheel() && SeslNumberPickerSpinnerDelegate.this.getValue() >= SeslNumberPickerSpinnerDelegate.this.getMaxValue())) {
                        return false;
                    } else {
                        SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(false);
                        SeslNumberPickerSpinnerDelegate.this.changeValueByOne(true);
                        SeslNumberPickerSpinnerDelegate.this.startFadeAnimation(true);
                        return true;
                    }
                } else if (this.mAccessibilityFocusedView != i) {
                    return false;
                } else {
                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                    SeslViewReflector.clearAccessibilityFocus(SeslNumberPickerSpinnerDelegate.this.mDelegator);
                    return true;
                }
            } else if (this.mAccessibilityFocusedView == i) {
                return false;
            } else {
                this.mAccessibilityFocusedView = i;
                SeslViewReflector.requestAccessibilityFocus(SeslNumberPickerSpinnerDelegate.this.mDelegator);
                return true;
            }
            return super.performAction(i, i2, bundle);
        }

        public void sendAccessibilityEventForVirtualView(int i, int i2) {
            if (i != 1) {
                if (i == 2) {
                    sendAccessibilityEventForVirtualText(i2);
                } else if (i == 3 && hasVirtualIncrementButton()) {
                    sendAccessibilityEventForVirtualButton(i, i2, getVirtualIncrementButtonText());
                }
            } else if (hasVirtualDecrementButton()) {
                sendAccessibilityEventForVirtualButton(i, i2, getVirtualDecrementButtonText());
            }
        }

        private void sendAccessibilityEventForVirtualText(int i) {
            if (SeslNumberPickerSpinnerDelegate.this.mAccessibilityManager.isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
                SeslNumberPickerSpinnerDelegate.this.mInputText.onInitializeAccessibilityEvent(obtain);
                SeslNumberPickerSpinnerDelegate.this.mInputText.onPopulateAccessibilityEvent(obtain);
                obtain.setSource(SeslNumberPickerSpinnerDelegate.this.mDelegator, 2);
                SeslNumberPickerSpinnerDelegate.this.mDelegator.requestSendAccessibilityEvent(SeslNumberPickerSpinnerDelegate.this.mDelegator, obtain);
            }
        }

        private void sendAccessibilityEventForVirtualButton(int i, int i2, String str) {
            if (SeslNumberPickerSpinnerDelegate.this.mAccessibilityManager.isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
                obtain.setClassName(Button.class.getName());
                obtain.setPackageName(SeslNumberPickerSpinnerDelegate.this.mContext.getPackageName());
                obtain.getText().add(str);
                obtain.setEnabled(SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled());
                obtain.setSource(SeslNumberPickerSpinnerDelegate.this.mDelegator, i);
                SeslNumberPickerSpinnerDelegate.this.mDelegator.requestSendAccessibilityEvent(SeslNumberPickerSpinnerDelegate.this.mDelegator, obtain);
            }
        }

        private void findAccessibilityNodeInfosByTextInChild(String str, int i, List<AccessibilityNodeInfo> list) {
            if (i == 1) {
                String virtualDecrementButtonText = getVirtualDecrementButtonText();
                if (!TextUtils.isEmpty(virtualDecrementButtonText) && virtualDecrementButtonText.toLowerCase().contains(str)) {
                    list.add(createAccessibilityNodeInfo(1));
                }
            } else if (i == 2) {
                Editable text = SeslNumberPickerSpinnerDelegate.this.mInputText.getText();
                if (!TextUtils.isEmpty(text) && text.toString().toLowerCase().contains(str)) {
                    list.add(createAccessibilityNodeInfo(2));
                }
            } else if (i == 3) {
                String virtualIncrementButtonText = getVirtualIncrementButtonText();
                if (!TextUtils.isEmpty(virtualIncrementButtonText) && virtualIncrementButtonText.toLowerCase().contains(str)) {
                    list.add(createAccessibilityNodeInfo(3));
                }
            }
        }

        private AccessibilityNodeInfo createAccessibiltyNodeInfoForInputText(int i, int i2, int i3, int i4) {
            AccessibilityNodeInfo createAccessibilityNodeInfo = SeslNumberPickerSpinnerDelegate.this.mInputText.createAccessibilityNodeInfo();
            createAccessibilityNodeInfo.setSource(SeslNumberPickerSpinnerDelegate.this.mDelegator, 2);
            if (this.mAccessibilityFocusedView != 2) {
                createAccessibilityNodeInfo.setAccessibilityFocused(false);
                createAccessibilityNodeInfo.addAction(64);
            } else {
                createAccessibilityNodeInfo.setAccessibilityFocused(true);
                createAccessibilityNodeInfo.addAction(128);
            }
            if (!SeslNumberPickerSpinnerDelegate.this.mIsEditTextModeEnabled) {
                createAccessibilityNodeInfo.setClassName(TextView.class.getName());
                createAccessibilityNodeInfo.setText(getVirtualCurrentButtonText());
                createAccessibilityNodeInfo.setSelected(true);
                createAccessibilityNodeInfo.setAccessibilityFocused(false);
            }
            Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            createAccessibilityNodeInfo.setVisibleToUser(SeslNumberPickerSpinnerDelegate.this.mDelegator.isVisibleToUserWrapper(rect));
            createAccessibilityNodeInfo.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            SeslNumberPickerSpinnerDelegate.this.mDelegator.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            createAccessibilityNodeInfo.setBoundsInScreen(rect);
            return createAccessibilityNodeInfo;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForVirtualButton(int i, String str, int i2, int i3, int i4, int i5) {
            AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
            obtain.setClassName(Button.class.getName());
            obtain.setPackageName(SeslNumberPickerSpinnerDelegate.this.mContext.getPackageName());
            obtain.setSource(SeslNumberPickerSpinnerDelegate.this.mDelegator, i);
            obtain.setParent(SeslNumberPickerSpinnerDelegate.this.mDelegator);
            obtain.setText(str);
            obtain.setClickable(true);
            obtain.setLongClickable(true);
            obtain.setEnabled(SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled());
            Rect rect = this.mTempRect;
            rect.set(i2, i3, i4, i5);
            obtain.setVisibleToUser(SeslNumberPickerSpinnerDelegate.this.mDelegator.isVisibleToUserWrapper(rect));
            obtain.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            SeslNumberPickerSpinnerDelegate.this.mDelegator.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            obtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != i) {
                obtain.addAction(64);
            } else {
                obtain.addAction(128);
            }
            if (SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                obtain.addAction(16);
            }
            return obtain;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForNumberPicker(int i, int i2, int i3, int i4) {
            AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
            obtain.setClassName(NumberPicker.class.getName());
            obtain.setPackageName(SeslNumberPickerSpinnerDelegate.this.mContext.getPackageName());
            obtain.setSource(SeslNumberPickerSpinnerDelegate.this.mDelegator);
            if (hasVirtualDecrementButton()) {
                obtain.addChild(SeslNumberPickerSpinnerDelegate.this.mDelegator, 1);
            }
            obtain.addChild(SeslNumberPickerSpinnerDelegate.this.mDelegator, 2);
            if (hasVirtualIncrementButton()) {
                obtain.addChild(SeslNumberPickerSpinnerDelegate.this.mDelegator, 3);
            }
            obtain.setParent((View) SeslNumberPickerSpinnerDelegate.this.mDelegator.getParentForAccessibility());
            obtain.setEnabled(SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled());
            obtain.setScrollable(true);
            float field_applicationScale = SeslCompatibilityInfoReflector.getField_applicationScale(SeslNumberPickerSpinnerDelegate.this.mContext.getResources());
            Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            scaleRect(rect, field_applicationScale);
            obtain.setBoundsInParent(rect);
            obtain.setVisibleToUser(SeslNumberPickerSpinnerDelegate.this.mDelegator.isVisibleToUserWrapper());
            int[] iArr = this.mTempArray;
            SeslNumberPickerSpinnerDelegate.this.mDelegator.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            scaleRect(rect, field_applicationScale);
            obtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != -1) {
                obtain.addAction(64);
            } else {
                obtain.addAction(128);
            }
            if (SeslNumberPickerSpinnerDelegate.this.mDelegator.isEnabled()) {
                if (SeslNumberPickerSpinnerDelegate.this.getWrapSelectorWheel() || SeslNumberPickerSpinnerDelegate.this.getValue() < SeslNumberPickerSpinnerDelegate.this.getMaxValue()) {
                    obtain.addAction(4096);
                }
                if (SeslNumberPickerSpinnerDelegate.this.getWrapSelectorWheel() || SeslNumberPickerSpinnerDelegate.this.getValue() > SeslNumberPickerSpinnerDelegate.this.getMinValue()) {
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
            return SeslNumberPickerSpinnerDelegate.this.getWrapSelectorWheel() || SeslNumberPickerSpinnerDelegate.this.getValue() > SeslNumberPickerSpinnerDelegate.this.getMinValue();
        }

        private boolean hasVirtualIncrementButton() {
            return SeslNumberPickerSpinnerDelegate.this.getWrapSelectorWheel() || SeslNumberPickerSpinnerDelegate.this.getValue() < SeslNumberPickerSpinnerDelegate.this.getMaxValue();
        }

        private String getVirtualDecrementButtonText() {
            String str;
            int i = SeslNumberPickerSpinnerDelegate.this.mValue - SeslNumberPickerSpinnerDelegate.this.mWheelInterval;
            if (SeslNumberPickerSpinnerDelegate.this.mWrapSelectorWheel) {
                i = SeslNumberPickerSpinnerDelegate.this.getWrappedSelectorIndex(i);
            }
            if (i < SeslNumberPickerSpinnerDelegate.this.mMinValue) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            if (SeslNumberPickerSpinnerDelegate.this.mDisplayedValues == null) {
                str = SeslNumberPickerSpinnerDelegate.this.formatNumber(i);
            } else {
                str = SeslNumberPickerSpinnerDelegate.this.mDisplayedValues[i - SeslNumberPickerSpinnerDelegate.this.mMinValue];
            }
            sb.append(str);
            sb.append(", ");
            sb.append(SeslNumberPickerSpinnerDelegate.this.mPickerContentDescription);
            sb.append(", ");
            return sb.toString();
        }

        private String getVirtualIncrementButtonText() {
            String str;
            int i = SeslNumberPickerSpinnerDelegate.this.mValue + SeslNumberPickerSpinnerDelegate.this.mWheelInterval;
            if (SeslNumberPickerSpinnerDelegate.this.mWrapSelectorWheel) {
                i = SeslNumberPickerSpinnerDelegate.this.getWrappedSelectorIndex(i);
            }
            if (i > SeslNumberPickerSpinnerDelegate.this.mMaxValue) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            if (SeslNumberPickerSpinnerDelegate.this.mDisplayedValues == null) {
                str = SeslNumberPickerSpinnerDelegate.this.formatNumber(i);
            } else {
                str = SeslNumberPickerSpinnerDelegate.this.mDisplayedValues[i - SeslNumberPickerSpinnerDelegate.this.mMinValue];
            }
            sb.append(str);
            sb.append(", ");
            sb.append(SeslNumberPickerSpinnerDelegate.this.mPickerContentDescription);
            sb.append(", ");
            return sb.toString();
        }

        private String getVirtualCurrentButtonText() {
            String str;
            int i = SeslNumberPickerSpinnerDelegate.this.mValue;
            if (SeslNumberPickerSpinnerDelegate.this.mWrapSelectorWheel) {
                i = SeslNumberPickerSpinnerDelegate.this.getWrappedSelectorIndex(i);
            }
            if (i > SeslNumberPickerSpinnerDelegate.this.mMaxValue) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            if (SeslNumberPickerSpinnerDelegate.this.mDisplayedValues == null) {
                str = SeslNumberPickerSpinnerDelegate.this.formatNumber(i);
            } else {
                str = SeslNumberPickerSpinnerDelegate.this.mDisplayedValues[i - SeslNumberPickerSpinnerDelegate.this.mMinValue];
            }
            sb.append(str);
            sb.append(", ");
            sb.append(SeslNumberPickerSpinnerDelegate.this.mPickerContentDescription);
            sb.append(" ");
            return sb.toString();
        }
    }

    private static String formatNumberWithLocale(int i) {
        return String.format(Locale.getDefault(), "%d", Integer.valueOf(i));
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setMaxInputLength(int i) {
        InputFilter inputFilter = this.mInputText.getFilters()[0];
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(i);
        this.mInputText.setFilters(new InputFilter[]{inputFilter, lengthFilter});
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public EditText getEditText() {
        return this.mInputText;
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setMonthInputMode() {
        this.mInputText.setImeOptions(33554432);
        this.mInputText.setPrivateImeOptions(INPUT_TYPE_MONTH);
        this.mInputText.setText("");
    }

    @Override // androidx.picker.widget.SeslNumberPicker.NumberPickerDelegate
    public void setYearDateTimeInputMode() {
        this.mInputText.setImeOptions(33554432);
        this.mInputText.setPrivateImeOptions(INPUT_TYPE_YEAR_DATE_TIME);
        this.mInputText.setText("");
    }

    private boolean isCharacterNumberLanguage() {
        String language = Locale.getDefault().getLanguage();
        return "ar".equals(language) || "fa".equals(language) || "my".equals(language);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean needCompareEqualMonthLanguage() {
        return "vi".equals(Locale.getDefault().getLanguage());
    }

    private boolean isHighContrastFontEnabled() {
        return SeslViewReflector.isHighContrastTextEnabled(this.mInputText);
    }

    private boolean updateBoldTextEnabledInSettings() {
        boolean z = false;
        if (Build.VERSION.SDK_INT < 28) {
            return false;
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "bold_text", 0) != 0) {
            z = true;
        }
        this.mIsBoldTextEnabled = z;
        return this.mIsBoldTextEnabled;
    }
}
