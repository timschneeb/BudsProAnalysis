package androidx.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import androidx.core.view.ViewCompat;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;

public class SeslEdgeEffect extends EdgeEffect {
    private static final double ANGLE = 0.5235987755982988d;
    private static final int APPEAR_TIME = 250;
    private static final int[] ATTRS = {16843982};
    private static final float COS = ((float) Math.cos(ANGLE));
    private static final float EDGE_CONTROL_POINT_HEIGHT_WITHOUT_TAB_IN_DIP = 29.0f;
    private static final float EDGE_CONTROL_POINT_HEIGHT_WITH_TAB_IN_DIP = 19.0f;
    private static final float EDGE_PADDING_WITHOUT_TAB_IN_DIP = 5.0f;
    private static final float EDGE_PADDING_WITH_TAB_IN_DIP = 3.0f;
    private static final float EPSILON = 0.001f;
    private static final int KEEP_TIME = 0;
    private static final float MAX_ALPHA = 0.15f;
    private static final float MAX_GLOW_SCALE = 2.0f;
    private static final int MAX_VELOCITY = 10000;
    private static final int MIN_VELOCITY = 100;
    private static final int MSG_CALL_ONRELEASE = 1;
    private static final float PULL_GLOW_BEGIN = 0.0f;
    private static final int PULL_TIME = 167;
    private static final float RADIUS_FACTOR = 0.75f;
    private static final int RECEDE_TIME = 450;
    private static final float SIN = ((float) Math.sin(ANGLE));
    private static final int STATE_ABSORB = 2;
    private static final int STATE_APPEAR = 5;
    private static final int STATE_IDLE = 0;
    private static final int STATE_KEEP = 6;
    private static final int STATE_PULL = 1;
    private static final int STATE_PULL_DECAY = 4;
    private static final int STATE_RECEDE = 3;
    private static final float TAB_HEIGHT_BUFFER_IN_DIP = 5.0f;
    private static final float TAB_HEIGHT_IN_DIP = 85.0f;
    private static final String TAG = "SeslEdgeEffect";
    private float MAX_SCALE = 1.0f;
    private final Rect mBounds = new Rect();
    private float mDisplacement = 0.5f;
    private final DisplayMetrics mDisplayMetrics;
    private float mDuration;
    private float mEdgeControlPointHeight;
    private float mEdgePadding;
    private Runnable mForceCallOnRelease = new Runnable() {
        /* class androidx.core.widget.SeslEdgeEffect.AnonymousClass2 */

        public void run() {
            SeslEdgeEffect.this.mOnReleaseCalled = true;
            SeslEdgeEffect seslEdgeEffect = SeslEdgeEffect.this;
            seslEdgeEffect.onPull(seslEdgeEffect.mTempDeltaDistance, SeslEdgeEffect.this.mTempDisplacement);
            SeslEdgeEffect.this.mHandler.sendEmptyMessageDelayed(1, 700);
        }
    };
    private float mGlowAlpha;
    private float mGlowAlphaFinish;
    private float mGlowAlphaStart;
    private float mGlowScaleY;
    private float mGlowScaleYFinish;
    private float mGlowScaleYStart;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        /* class androidx.core.widget.SeslEdgeEffect.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (message.what == 1) {
                SeslEdgeEffect.this.onRelease();
            }
        }
    };
    private View mHostView;
    private final Interpolator mInterpolator;
    private boolean mOnReleaseCalled = false;
    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();
    private float mPullDistance;
    private long mStartTime;
    private int mState = 0;
    private final float mTabHeight;
    private final float mTabHeightBuffer;
    private float mTargetDisplacement = 0.5f;
    private float mTempDeltaDistance;
    private float mTempDisplacement;

    public SeslEdgeEffect(Context context) {
        super(context);
        this.mPaint.setAntiAlias(true);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(ATTRS);
        int color = obtainStyledAttributes.getColor(0, -10066330);
        obtainStyledAttributes.recycle();
        this.mPaint.setColor((color & ViewCompat.MEASURED_SIZE_MASK) | 855638016);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        this.mInterpolator = new DecelerateInterpolator();
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mTabHeight = dipToPixels(TAB_HEIGHT_IN_DIP);
        this.mTabHeightBuffer = dipToPixels(5.0f);
    }

    private float dipToPixels(float f) {
        return TypedValue.applyDimension(1, f, this.mDisplayMetrics);
    }

    public void setHostView(View view) {
        this.mHostView = view;
    }

    public void setSize(int i, int i2) {
        float f = (float) i;
        float f2 = (RADIUS_FACTOR * f) / SIN;
        float f3 = f2 - (COS * f2);
        float f4 = (float) i2;
        if (f <= this.mTabHeight + this.mTabHeightBuffer) {
            this.mEdgePadding = dipToPixels(EDGE_PADDING_WITH_TAB_IN_DIP);
            this.mEdgeControlPointHeight = dipToPixels(EDGE_CONTROL_POINT_HEIGHT_WITH_TAB_IN_DIP);
        } else {
            this.mEdgePadding = dipToPixels(5.0f);
            this.mEdgeControlPointHeight = dipToPixels(EDGE_CONTROL_POINT_HEIGHT_WITHOUT_TAB_IN_DIP);
        }
        Rect rect = this.mBounds;
        rect.set(rect.left, this.mBounds.top, i, (int) Math.min(f4, f3));
    }

    public boolean isFinished() {
        return this.mState == 0;
    }

    public void finish() {
        this.mState = 0;
    }

    public void onPull(float f) {
        onPull(f, 0.5f);
    }

    private boolean isEdgeEffectRunning() {
        int i = this.mState;
        return i == 5 || i == 6 || i == 3 || i == 2;
    }

    public void onPullCallOnRelease(float f, float f2, int i) {
        this.mTempDeltaDistance = f;
        this.mTempDisplacement = f2;
        if (i == 0) {
            this.mOnReleaseCalled = true;
            onPull(this.mTempDeltaDistance, this.mTempDisplacement);
            this.mHandler.sendEmptyMessageDelayed(1, 700);
            return;
        }
        this.mHandler.postDelayed(this.mForceCallOnRelease, (long) i);
    }

    public void onPull(float f, float f2) {
        int semGetVibrationIndex;
        if (this.mPullDistance == 0.0f) {
            this.mOnReleaseCalled = false;
            if (isEdgeEffectRunning()) {
                this.mPullDistance += f;
            }
        }
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this.mTargetDisplacement = f2;
        if (this.mState != 4 || ((float) (currentAnimationTimeMillis - this.mStartTime)) >= this.mDuration) {
            if (this.mState != 1) {
                this.mGlowScaleY = Math.max(0.0f, this.mGlowScaleY);
            }
            if (isEdgeEffectRunning()) {
                return;
            }
            if (this.mPullDistance == 0.0f || this.mOnReleaseCalled) {
                if (!(this.mHostView == null || (semGetVibrationIndex = SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(28)) == -1)) {
                    this.mHostView.performHapticFeedback(semGetVibrationIndex);
                }
                this.mState = 1;
                this.mStartTime = currentAnimationTimeMillis;
                this.mDuration = 167.0f;
                this.mPullDistance += f;
            }
        }
    }

    public void onRelease() {
        this.mPullDistance = 0.0f;
        this.mOnReleaseCalled = true;
        int i = this.mState;
        if (i == 1 || i == 4) {
            this.mState = 3;
            this.mGlowAlphaStart = this.mGlowAlpha;
            this.mGlowScaleYStart = this.mGlowScaleY;
            this.mGlowAlphaFinish = 0.0f;
            this.mGlowScaleYFinish = 0.0f;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 450.0f;
        }
    }

    public void onAbsorb(int i) {
        if (!isEdgeEffectRunning()) {
            View view = this.mHostView;
            if (view != null) {
                view.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(28));
            }
            this.mOnReleaseCalled = true;
            this.mState = 2;
            Math.min(Math.max(100, Math.abs(i)), (int) MAX_VELOCITY);
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 250.0f;
            this.mGlowAlphaStart = 0.0f;
            this.mGlowScaleYStart = 0.0f;
            this.mGlowScaleYFinish = this.MAX_SCALE;
            this.mGlowAlphaFinish = MAX_ALPHA;
            this.mTargetDisplacement = 0.5f;
            this.mHandler.sendEmptyMessageDelayed(1, 700);
        }
    }

    public void setColor(int i) {
        this.mPaint.setColor(i);
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public boolean draw(Canvas canvas) {
        boolean z;
        update();
        int save = canvas.save();
        float centerX = (float) this.mBounds.centerX();
        canvas.scale(1.0f, Math.min(this.mGlowScaleY, 1.0f), centerX, 0.0f);
        Math.max(0.0f, Math.min(this.mDisplacement, 1.0f));
        float f = this.mEdgeControlPointHeight + this.mEdgePadding;
        float width = ((float) this.mBounds.width()) * 0.2f;
        this.mPath.reset();
        this.mPath.moveTo(0.0f, 0.0f);
        this.mPath.lineTo(0.0f, this.mEdgePadding);
        this.mPath.cubicTo(centerX - width, f, centerX + width, f, (float) this.mBounds.width(), this.mEdgePadding);
        this.mPath.lineTo((float) this.mBounds.width(), 0.0f);
        this.mPath.close();
        this.mPaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restoreToCount(save);
        if (this.mState == 3 && this.mGlowScaleY == 0.0f) {
            this.mState = 0;
            z = true;
        } else {
            z = false;
        }
        return this.mState != 0 || z;
    }

    public int getMaxHeight() {
        return (int) ((((float) this.mBounds.height()) * MAX_GLOW_SCALE) + 0.5f);
    }

    private void update() {
        float min = Math.min(((float) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime)) / this.mDuration, 1.0f);
        float interpolation = this.mInterpolator.getInterpolation(min);
        float f = this.mGlowAlphaStart;
        this.mGlowAlpha = f + ((this.mGlowAlphaFinish - f) * interpolation);
        float f2 = this.mGlowScaleYStart;
        this.mGlowScaleY = f2 + ((this.mGlowScaleYFinish - f2) * interpolation);
        this.mDisplacement = (this.mDisplacement + this.mTargetDisplacement) / MAX_GLOW_SCALE;
        if (min >= 0.999f || this.mState == 1) {
            switch (this.mState) {
                case 1:
                    this.mState = 5;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 250.0f;
                    this.mGlowAlphaStart = 0.0f;
                    this.mGlowScaleYStart = 0.0f;
                    this.mGlowAlphaFinish = MAX_ALPHA;
                    this.mGlowScaleYFinish = this.MAX_SCALE;
                    this.mGlowScaleY = 0.0f;
                    this.mOnReleaseCalled = false;
                    return;
                case 2:
                    this.mState = 6;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 0.0f;
                    this.mGlowAlphaStart = MAX_ALPHA;
                    this.mGlowAlphaFinish = MAX_ALPHA;
                    float f3 = this.MAX_SCALE;
                    this.mGlowScaleYStart = f3;
                    this.mGlowScaleYFinish = f3;
                    return;
                case 3:
                    this.mState = 0;
                    return;
                case 4:
                    this.mState = 3;
                    return;
                case 5:
                    this.mState = 6;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 0.0f;
                    this.mGlowAlphaStart = MAX_ALPHA;
                    this.mGlowAlphaFinish = MAX_ALPHA;
                    float f4 = this.MAX_SCALE;
                    this.mGlowScaleYStart = f4;
                    this.mGlowScaleYFinish = f4;
                    return;
                case 6:
                    this.mState = 3;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 450.0f;
                    this.mGlowAlphaStart = this.mGlowAlpha;
                    this.mGlowScaleYStart = this.mGlowScaleY;
                    this.mGlowAlphaFinish = 0.0f;
                    this.mGlowScaleYFinish = 0.0f;
                    return;
                default:
                    return;
            }
        }
    }
}
