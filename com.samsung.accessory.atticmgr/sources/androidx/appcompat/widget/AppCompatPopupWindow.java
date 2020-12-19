package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import androidx.appcompat.R;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.core.widget.PopupWindowCompat;
import androidx.reflect.view.SeslViewReflector;

/* access modifiers changed from: package-private */
public class AppCompatPopupWindow extends PopupWindow {
    private static final boolean COMPAT_OVERLAP_ANCHOR = (Build.VERSION.SDK_INT < 21);
    private Context mContext;
    private boolean mHasNavigationBar;
    private int mNavigationBarHeight;
    private boolean mOverlapAnchor;
    private final Rect mTempRect = new Rect();

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i, 0);
    }

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context, attributeSet, i, i2);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.PopupWindow, i, i2);
        if (obtainStyledAttributes.hasValue(R.styleable.PopupWindow_overlapAnchor)) {
            setSupportOverlapAnchor(obtainStyledAttributes.getBoolean(R.styleable.PopupWindow_overlapAnchor, false));
        }
        this.mContext = context;
        if (Build.VERSION.SDK_INT >= 23) {
            Transition transition = getTransition(obtainStyledAttributes.getResourceId(R.styleable.PopupWindow_popupEnterTransition, 0));
            Transition transition2 = getTransition(obtainStyledAttributes.getResourceId(R.styleable.PopupWindow_popupExitTransition, 0));
            setEnterTransition(transition);
            setExitTransition(transition2);
        }
        setBackgroundDrawable(obtainStyledAttributes.getDrawable(R.styleable.PopupWindow_android_popupBackground));
        obtainStyledAttributes.recycle();
        this.mHasNavigationBar = ActionBarPolicy.get(context).hasNavigationBar();
        this.mNavigationBarHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_height);
    }

    private Transition getTransition(int i) {
        Transition inflateTransition;
        if (i == 0 || i == 17760256 || (inflateTransition = TransitionInflater.from(this.mContext).inflateTransition(i)) == null) {
            return null;
        }
        if (!((inflateTransition instanceof TransitionSet) && ((TransitionSet) inflateTransition).getTransitionCount() == 0)) {
            return inflateTransition;
        }
        return null;
    }

    public void showAsDropDown(View view, int i, int i2) {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2);
    }

    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2, i3);
    }

    @Override // android.widget.PopupWindow
    public void update(View view, int i, int i2, int i3, int i4) {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            i2 -= view.getHeight();
        }
        super.update(view, i, i2, i3, i4);
    }

    private void setSupportOverlapAnchor(boolean z) {
        if (COMPAT_OVERLAP_ANCHOR) {
            this.mOverlapAnchor = z;
        } else {
            PopupWindowCompat.setOverlapAnchor(this, z);
        }
    }

    public boolean getSupportOverlapAnchor() {
        return PopupWindowCompat.getOverlapAnchor(this);
    }

    public int getMaxAvailableHeight(View view, int i, boolean z) {
        int i2;
        Rect rect = new Rect();
        if (z) {
            SeslViewReflector.getWindowDisplayFrame(view, rect);
            if (this.mHasNavigationBar && this.mContext.getResources().getConfiguration().orientation != 2) {
                rect.bottom -= this.mNavigationBarHeight;
            }
        } else {
            view.getWindowVisibleDisplayFrame(rect);
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i3 = rect.bottom;
        if (getSupportOverlapAnchor()) {
            i2 = i3 - iArr[1];
        } else {
            i2 = i3 - (iArr[1] + view.getHeight());
        }
        int max = Math.max(i2 - i, (iArr[1] - rect.top) + i);
        if (getBackground() == null) {
            return max;
        }
        getBackground().getPadding(this.mTempRect);
        return max - (this.mTempRect.top + this.mTempRect.bottom);
    }
}
