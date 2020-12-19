package androidx.appcompat.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.view.ViewCompat;
import com.google.android.material.badge.BadgeDrawable;

/* access modifiers changed from: package-private */
public class TooltipPopup {
    private static final String TAG = "SESL_TooltipPopup";
    private final View mContentView;
    private final Context mContext;
    private boolean mIsForceActionBarX = false;
    private boolean mIsForceBelow = false;
    private final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
    private final TextView mMessageView;
    private int mNavigationBarHeight = 0;
    private final int[] mTmpAnchorPos = new int[2];
    private final int[] mTmpAppPos = new int[2];
    private final Rect mTmpDisplayFrame = new Rect();

    TooltipPopup(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843945, typedValue, false);
        if (typedValue.data != 0) {
            this.mContext = new ContextThemeWrapper(context, typedValue.data);
        } else {
            this.mContext = context;
        }
        this.mContentView = LayoutInflater.from(this.mContext).inflate(R.layout.sesl_tooltip, (ViewGroup) null);
        this.mMessageView = (TextView) this.mContentView.findViewById(R.id.message);
        this.mContentView.setOnTouchListener(new View.OnTouchListener() {
            /* class androidx.appcompat.widget.TooltipPopup.AnonymousClass1 */

            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    TooltipPopup.this.hide();
                    return true;
                } else if (action != 4) {
                    return false;
                } else {
                    TooltipPopup.this.hide();
                    return false;
                }
            }
        });
        this.mLayoutParams.setTitle(getClass().getSimpleName());
        this.mLayoutParams.packageName = this.mContext.getPackageName();
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        layoutParams.type = 1002;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R.style.Animation_AppCompat_Tooltip;
        this.mLayoutParams.flags = 262152;
    }

    /* access modifiers changed from: package-private */
    public void show(View view, int i, int i2, boolean z, CharSequence charSequence) {
        if (isShowing()) {
            hide();
        }
        this.mMessageView.setText(charSequence);
        computePosition(view, i, i2, z, this.mLayoutParams, false, false);
        ((WindowManager) this.mContext.getSystemService("window")).addView(this.mContentView, this.mLayoutParams);
    }

    /* access modifiers changed from: package-private */
    public void show(View view, int i, int i2, boolean z, CharSequence charSequence, boolean z2, boolean z3) {
        this.mIsForceBelow = z2;
        this.mIsForceActionBarX = z3;
        if (isShowing()) {
            hide();
        }
        this.mMessageView.setText(charSequence);
        computePosition(view, i, i2, z, this.mLayoutParams, this.mIsForceBelow, this.mIsForceActionBarX);
        ((WindowManager) this.mContext.getSystemService("window")).addView(this.mContentView, this.mLayoutParams);
    }

    public void showActionItemTooltip(int i, int i2, int i3, CharSequence charSequence) {
        if (isShowing()) {
            hide();
        }
        this.mMessageView.setText(charSequence);
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        layoutParams.x = i;
        layoutParams.y = i2;
        if (i3 == 0) {
            layoutParams.gravity = BadgeDrawable.TOP_END;
        } else {
            layoutParams.gravity = BadgeDrawable.TOP_START;
        }
        ((WindowManager) this.mContext.getSystemService("window")).addView(this.mContentView, this.mLayoutParams);
    }

    /* access modifiers changed from: package-private */
    public void hide() {
        this.mIsForceBelow = false;
        this.mIsForceActionBarX = false;
        if (isShowing()) {
            ((WindowManager) this.mContext.getSystemService("window")).removeView(this.mContentView);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isShowing() {
        return this.mContentView.getParent() != null;
    }

    /* access modifiers changed from: package-private */
    public void updateContent(CharSequence charSequence) {
        this.mMessageView.setText(charSequence);
    }

    private boolean checkNaviBarForLandscape() {
        Context context = this.mContext;
        Resources resources = context.getResources();
        Rect rect = this.mTmpDisplayFrame;
        Point point = new Point();
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        defaultDisplay.getRealSize(point);
        int rotation = defaultDisplay.getRotation();
        int dimension = (int) resources.getDimension(R.dimen.sesl_navigation_bar_height);
        if (rotation == 1 && rect.right + dimension >= point.x) {
            setNavigationBarHeight(point.x - rect.right);
            return true;
        } else if (rotation != 3 || rect.left > dimension) {
            return false;
        } else {
            setNavigationBarHeight(rect.left);
            return true;
        }
    }

    private void setNavigationBarHeight(int i) {
        this.mNavigationBarHeight = i;
    }

    private int getNavigationBarHeight() {
        return this.mNavigationBarHeight;
    }

    private void computePosition(View view, int i, int i2, boolean z, WindowManager.LayoutParams layoutParams, boolean z2, boolean z3) {
        layoutParams.token = view.getApplicationWindowToken();
        int width = view.getWidth() / 2;
        layoutParams.gravity = 49;
        View appRootView = getAppRootView(view);
        if (appRootView == null) {
            Log.e(TAG, "Cannot find app view");
            return;
        }
        appRootView.getWindowVisibleDisplayFrame(this.mTmpDisplayFrame);
        if (this.mTmpDisplayFrame.left < 0 && this.mTmpDisplayFrame.top < 0) {
            Resources resources = this.mContext.getResources();
            int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
            int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            this.mTmpDisplayFrame.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        int[] iArr = new int[2];
        appRootView.getLocationOnScreen(iArr);
        Rect rect = new Rect(iArr[0], iArr[1], iArr[0] + appRootView.getWidth(), iArr[1] + appRootView.getHeight());
        this.mTmpDisplayFrame.left = rect.left;
        this.mTmpDisplayFrame.right = rect.right;
        appRootView.getLocationOnScreen(this.mTmpAppPos);
        view.getLocationOnScreen(this.mTmpAnchorPos);
        Log.i(TAG, "computePosition - displayFrame left : " + this.mTmpDisplayFrame.left);
        Log.i(TAG, "computePosition - displayFrame right : " + this.mTmpDisplayFrame.right);
        Log.i(TAG, "computePosition - displayFrame top : " + this.mTmpDisplayFrame.top);
        Log.i(TAG, "computePosition - displayFrame bottom : " + this.mTmpDisplayFrame.bottom);
        Log.i(TAG, "computePosition - anchorView locationOnScreen x: " + this.mTmpAnchorPos[0]);
        Log.i(TAG, "computePosition - anchorView locationOnScreen y : " + this.mTmpAnchorPos[1]);
        Log.i(TAG, "computePosition - appView locationOnScreen x : " + this.mTmpAppPos[0]);
        Log.i(TAG, "computePosition - appView locationOnScreen y : " + this.mTmpAppPos[1]);
        int[] iArr2 = this.mTmpAnchorPos;
        int i3 = iArr2[0];
        int[] iArr3 = this.mTmpAppPos;
        iArr2[0] = i3 - iArr3[0];
        iArr2[1] = iArr2[1] - iArr3[1];
        layoutParams.x = (iArr2[0] + width) - (this.mTmpDisplayFrame.width() / 2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContentView.measure(makeMeasureSpec, makeMeasureSpec);
        int measuredHeight = this.mContentView.getMeasuredHeight();
        int measuredWidth = this.mContentView.getMeasuredWidth();
        int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sesl_hover_tooltip_popup_right_margin);
        int dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sesl_hover_tooltip_popup_area_margin);
        int[] iArr4 = this.mTmpAnchorPos;
        int i4 = iArr4[1] - measuredHeight;
        int height = iArr4[1] + view.getHeight();
        if (z) {
            if (ViewCompat.getLayoutDirection(view) == 0) {
                int i5 = measuredWidth / 2;
                layoutParams.x = (((this.mTmpAnchorPos[0] + view.getWidth()) - (this.mTmpDisplayFrame.width() / 2)) - i5) - dimensionPixelOffset;
                if (layoutParams.x < ((-this.mTmpDisplayFrame.width()) / 2) + i5) {
                    layoutParams.x = ((-this.mTmpDisplayFrame.width()) / 2) + i5 + dimensionPixelOffset;
                }
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            } else {
                layoutParams.x = ((this.mTmpAnchorPos[0] + width) - (this.mTmpDisplayFrame.width() / 2)) + (measuredWidth / 2) + dimensionPixelOffset;
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            }
            if (height + measuredHeight > this.mTmpDisplayFrame.height()) {
                layoutParams.y = i4;
            } else {
                layoutParams.y = height;
            }
        } else {
            layoutParams.x = (this.mTmpAnchorPos[0] + width) - (this.mTmpDisplayFrame.width() / 2);
            int i6 = measuredWidth / 2;
            if (layoutParams.x < ((-this.mTmpDisplayFrame.width()) / 2) + i6) {
                layoutParams.x = ((-this.mTmpDisplayFrame.width()) / 2) + i6 + dimensionPixelOffset2;
            }
            layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            layoutParams.y = i4 >= 0 ? i4 : height;
        }
        if (z2) {
            layoutParams.y = this.mTmpAnchorPos[1] + view.getHeight();
        }
        if (z3) {
            if (ViewCompat.getLayoutDirection(view) == 0) {
                int i7 = measuredWidth / 2;
                layoutParams.x = (((this.mTmpAnchorPos[0] + view.getWidth()) - (this.mTmpDisplayFrame.width() / 2)) - i7) - dimensionPixelOffset;
                if (layoutParams.x < ((-this.mTmpDisplayFrame.width()) / 2) + i7) {
                    layoutParams.x = ((-this.mTmpDisplayFrame.width()) / 2) + i7 + dimensionPixelOffset2;
                }
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            } else {
                layoutParams.x = (((this.mTmpAnchorPos[0] + width) - (this.mTmpDisplayFrame.width() / 2)) + (measuredWidth / 2)) - dimensionPixelOffset;
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            }
            if (measuredHeight + height > this.mTmpDisplayFrame.height()) {
                height = i4;
            }
            layoutParams.y = height;
        }
    }

    private int adjustTooltipPosition(int i, int i2, int i3) {
        int i4;
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if (checkNaviBarForLandscape()) {
            if (rotation == 1) {
                i4 = (((this.mTmpDisplayFrame.width() - i2) - getNavigationBarHeight()) / 2) - i3;
                if (i <= i4) {
                    return i;
                }
            } else if (rotation != 3) {
                return i;
            } else {
                if (i <= 0) {
                    int width = ((i2 - this.mTmpDisplayFrame.width()) / 2) + i3;
                    if (i <= width) {
                        return width + i3;
                    }
                    return i;
                }
                i4 = ((this.mTmpDisplayFrame.width() - i2) / 2) + i3;
                if (i <= i4) {
                    return i;
                }
            }
        } else if (rotation != 1 && rotation != 3) {
            return i;
        } else {
            if (i <= 0) {
                int width2 = ((i2 - this.mTmpDisplayFrame.width()) / 2) + i3;
                if (i < width2) {
                    return width2 + i3;
                }
                return i;
            }
            i4 = ((this.mTmpDisplayFrame.width() - i2) / 2) + i3;
            if (i <= i4) {
                return i;
            }
        }
        return i4 - i3;
    }

    private static View getAppRootView(View view) {
        View rootView = view.getRootView();
        ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
        if ((layoutParams instanceof WindowManager.LayoutParams) && ((WindowManager.LayoutParams) layoutParams).type == 2) {
            return rootView;
        }
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return ((Activity) context).getWindow().getDecorView();
            }
        }
        return rootView;
    }
}
