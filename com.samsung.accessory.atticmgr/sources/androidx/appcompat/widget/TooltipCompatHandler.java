package androidx.appcompat.widget;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.reflect.hardware.input.SeslInputManagerReflector;
import androidx.reflect.provider.SeslSettingsReflector;
import androidx.reflect.view.SeslPointerIconReflector;
import androidx.reflect.view.SeslViewReflector;

/* access modifiers changed from: package-private */
public class TooltipCompatHandler implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {
    private static final long HOVER_HIDE_TIMEOUT_MS = 15000;
    private static final long HOVER_HIDE_TIMEOUT_SHORT_MS = 3000;
    private static final long LONG_CLICK_HIDE_TIMEOUT_MS = 2500;
    private static final String TAG = "TooltipCompatHandler";
    private static TooltipCompatHandler sActiveHandler = null;
    private static boolean sIsCustomTooltipPosition = false;
    private static boolean sIsForceActionBarX = false;
    private static boolean sIsForceBelow = false;
    private static boolean sIsTooltipNull = false;
    private static int sLayoutDirection;
    private static TooltipCompatHandler sPendingHandler;
    private static int sPosX;
    private static int sPosY;
    private final View mAnchor;
    private int mAnchorX;
    private int mAnchorY;
    private boolean mFromTouch;
    private final Runnable mHideRunnable = new Runnable() {
        /* class androidx.appcompat.widget.TooltipCompatHandler.AnonymousClass2 */

        public void run() {
            TooltipCompatHandler.this.hide();
        }
    };
    private final int mHoverSlop;
    private boolean mInitialWindowFocus = false;
    private boolean mIsSPenPointChanged = false;
    private boolean mIsShowRunnablePostDelayed = false;
    private int mLastHoverEvent = -1;
    private TooltipPopup mPopup;
    private final Runnable mShowRunnable = new Runnable() {
        /* class androidx.appcompat.widget.TooltipCompatHandler.AnonymousClass1 */

        public void run() {
            TooltipCompatHandler.this.show(false);
        }
    };
    private final CharSequence mTooltipText;

    public void onViewAttachedToWindow(View view) {
    }

    public static void setTooltipText(View view, CharSequence charSequence) {
        if (view == null) {
            Log.i(TAG, "view is null");
            return;
        }
        TooltipCompatHandler tooltipCompatHandler = sPendingHandler;
        if (tooltipCompatHandler != null && tooltipCompatHandler.mAnchor == view) {
            setPendingHandler(null);
        }
        if (TextUtils.isEmpty(charSequence)) {
            TooltipCompatHandler tooltipCompatHandler2 = sActiveHandler;
            if (tooltipCompatHandler2 != null && tooltipCompatHandler2.mAnchor == view) {
                tooltipCompatHandler2.hide();
            }
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            view.setOnHoverListener(null);
            return;
        }
        TooltipCompatHandler tooltipCompatHandler3 = sActiveHandler;
        if (tooltipCompatHandler3 == null || tooltipCompatHandler3.mAnchor != view) {
            new TooltipCompatHandler(view, charSequence);
        } else {
            tooltipCompatHandler3.hide();
        }
    }

    private TooltipCompatHandler(View view, CharSequence charSequence) {
        this.mAnchor = view;
        this.mTooltipText = charSequence;
        this.mHoverSlop = ViewConfigurationCompat.getScaledHoverSlop(ViewConfiguration.get(this.mAnchor.getContext()));
        clearAnchorPos();
        this.mAnchor.setOnLongClickListener(this);
        this.mAnchor.setOnHoverListener(this);
    }

    public boolean onLongClick(View view) {
        this.mAnchorX = view.getWidth() / 2;
        this.mAnchorY = view.getHeight() / 2;
        show(true);
        return true;
    }

    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.mPopup != null && this.mFromTouch) {
            return false;
        }
        if (this.mAnchor == null) {
            Log.i(TAG, "TooltipCompat Anchor view is null");
            return false;
        }
        Context context = view.getContext();
        if (!motionEvent.isFromSource(InputDeviceCompat.SOURCE_STYLUS) || isSPenHoveringSettingsEnabled()) {
            AccessibilityManager accessibilityManager = (AccessibilityManager) this.mAnchor.getContext().getSystemService("accessibility");
            if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
                return false;
            }
            int action = motionEvent.getAction();
            this.mLastHoverEvent = action;
            if (action != 7) {
                if (action == 9) {
                    this.mInitialWindowFocus = this.mAnchor.hasWindowFocus();
                    if (Build.VERSION.SDK_INT >= 24 && this.mAnchor.isEnabled() && this.mPopup == null && context != null) {
                        SeslViewReflector.semSetPointerIcon(view, 2, PointerIcon.getSystemIcon(context, SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_MORE()));
                    }
                } else if (action == 10) {
                    Log.i(TAG, "MotionEvent.ACTION_HOVER_EXIT : hide SeslTooltipPopup");
                    if (Build.VERSION.SDK_INT < 24) {
                        showPenPointEffect(motionEvent, false);
                    } else if (!(!this.mAnchor.isEnabled() || this.mPopup == null || context == null)) {
                        SeslViewReflector.semSetPointerIcon(view, 2, PointerIcon.getSystemIcon(context, SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT()));
                    }
                    hide();
                }
            } else if (this.mAnchor.isEnabled() && this.mPopup == null) {
                this.mAnchorX = (int) motionEvent.getX();
                this.mAnchorY = (int) motionEvent.getY();
                if (Build.VERSION.SDK_INT < 24) {
                    showPenPointEffect(motionEvent, true);
                }
                if (!this.mIsShowRunnablePostDelayed) {
                    setPendingHandler(this);
                    this.mIsShowRunnablePostDelayed = true;
                }
            }
            return false;
        }
        if (Build.VERSION.SDK_INT >= 24 && this.mAnchor.isEnabled() && this.mPopup != null && context != null) {
            SeslViewReflector.semSetPointerIcon(view, 2, PointerIcon.getSystemIcon(context, SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT()));
        }
        return false;
    }

    public void onViewDetachedFromWindow(View view) {
        hide();
    }

    /* access modifiers changed from: package-private */
    public void show(boolean z) {
        long j;
        int i;
        long j2;
        if (ViewCompat.isAttachedToWindow(this.mAnchor)) {
            setPendingHandler(null);
            TooltipCompatHandler tooltipCompatHandler = sActiveHandler;
            if (tooltipCompatHandler != null) {
                tooltipCompatHandler.hide();
            }
            sActiveHandler = this;
            this.mFromTouch = z;
            this.mPopup = new TooltipPopup(this.mAnchor.getContext());
            if (sIsCustomTooltipPosition) {
                sIsForceBelow = false;
                sIsForceActionBarX = false;
                if (!sIsTooltipNull || z) {
                    this.mPopup.showActionItemTooltip(sPosX, sPosY, sLayoutDirection, this.mTooltipText);
                    sIsCustomTooltipPosition = false;
                } else {
                    return;
                }
            } else if (!sIsTooltipNull) {
                if (sIsForceBelow || sIsForceActionBarX) {
                    this.mPopup.show(this.mAnchor, this.mAnchorX, this.mAnchorY, this.mFromTouch, this.mTooltipText, sIsForceBelow, sIsForceActionBarX);
                } else {
                    sIsForceBelow = false;
                    sIsForceActionBarX = false;
                    this.mPopup.show(this.mAnchor, this.mAnchorX, this.mAnchorY, this.mFromTouch, this.mTooltipText);
                }
            } else {
                return;
            }
            this.mAnchor.addOnAttachStateChangeListener(this);
            if (this.mFromTouch) {
                j = LONG_CLICK_HIDE_TIMEOUT_MS;
            } else {
                if ((ViewCompat.getWindowSystemUiVisibility(this.mAnchor) & 1) == 1) {
                    j2 = 3000;
                    i = ViewConfiguration.getLongPressTimeout();
                } else {
                    j2 = HOVER_HIDE_TIMEOUT_MS;
                    i = ViewConfiguration.getLongPressTimeout();
                }
                j = j2 - ((long) i);
            }
            this.mAnchor.removeCallbacks(this.mHideRunnable);
            this.mAnchor.postDelayed(this.mHideRunnable, j);
            if (this.mLastHoverEvent == 7 && !this.mAnchor.hasWindowFocus() && this.mInitialWindowFocus != this.mAnchor.hasWindowFocus()) {
                hide();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void hide() {
        if (sActiveHandler == this) {
            sActiveHandler = null;
            TooltipPopup tooltipPopup = this.mPopup;
            if (tooltipPopup != null) {
                tooltipPopup.hide();
                this.mPopup = null;
                clearAnchorPos();
                this.mAnchor.removeOnAttachStateChangeListener(this);
            } else {
                Log.e(TAG, "sActiveHandler.mPopup == null");
            }
        }
        this.mIsShowRunnablePostDelayed = false;
        if (sPendingHandler == this) {
            setPendingHandler(null);
        }
        this.mAnchor.removeCallbacks(this.mHideRunnable);
        sPosX = 0;
        sPosY = 0;
        sIsTooltipNull = false;
        sIsCustomTooltipPosition = false;
    }

    private static void setPendingHandler(TooltipCompatHandler tooltipCompatHandler) {
        TooltipCompatHandler tooltipCompatHandler2 = sPendingHandler;
        if (tooltipCompatHandler2 != null) {
            tooltipCompatHandler2.cancelPendingShow();
        }
        sPendingHandler = tooltipCompatHandler;
        TooltipCompatHandler tooltipCompatHandler3 = sPendingHandler;
        if (tooltipCompatHandler3 != null) {
            tooltipCompatHandler3.scheduleShow();
        }
    }

    private void scheduleShow() {
        this.mAnchor.postDelayed(this.mShowRunnable, (long) ViewConfiguration.getLongPressTimeout());
    }

    private void cancelPendingShow() {
        this.mAnchor.removeCallbacks(this.mShowRunnable);
    }

    private boolean updateAnchorPos(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (Math.abs(x - this.mAnchorX) <= this.mHoverSlop && Math.abs(y - this.mAnchorY) <= this.mHoverSlop) {
            return false;
        }
        this.mAnchorX = x;
        this.mAnchorY = y;
        return true;
    }

    private void clearAnchorPos() {
        this.mAnchorX = Integer.MAX_VALUE;
        this.mAnchorY = Integer.MAX_VALUE;
    }

    private void update(CharSequence charSequence) {
        this.mPopup.updateContent(charSequence);
    }

    public static void seslSetTooltipPosition(int i, int i2, int i3) {
        sLayoutDirection = i3;
        sPosX = i;
        sPosY = i2;
        sIsCustomTooltipPosition = true;
    }

    public static void seslSetTooltipNull(boolean z) {
        sIsTooltipNull = z;
    }

    public static void seslSetTooltipForceBelow(boolean z) {
        sIsForceBelow = z;
    }

    public static void seslSetTooltipForceActionBarPosX(boolean z) {
        sIsForceActionBarX = z;
    }

    private void showPenPointEffect(MotionEvent motionEvent, boolean z) {
        if (motionEvent.getToolType(0) != 2) {
            return;
        }
        if (z) {
            SeslInputManagerReflector.setPointerIconType(SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_MORE());
            this.mIsSPenPointChanged = true;
        } else if (this.mIsSPenPointChanged) {
            SeslInputManagerReflector.setPointerIconType(SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT());
            this.mIsSPenPointChanged = false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isSPenHoveringSettingsEnabled() {
        return Settings.System.getInt(this.mAnchor.getContext().getContentResolver(), SeslSettingsReflector.SeslSystemReflector.getField_SEM_PEN_HOVERING(), 0) == 1;
    }
}
