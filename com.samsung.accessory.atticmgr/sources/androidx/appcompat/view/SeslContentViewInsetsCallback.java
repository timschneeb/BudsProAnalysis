package androidx.appcompat.view;

import android.graphics.Insets;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import java.util.List;

public class SeslContentViewInsetsCallback extends WindowInsetsAnimation.Callback implements View.OnApplyWindowInsetsListener {
    private static final String TAG = "SeslCVInsetsCallback";
    private final Handler handler = new Handler();
    private int mDeferInsetTypes;
    private final Runnable mHandleInsetsAnimationCanceled = new Runnable() {
        /* class androidx.appcompat.view.SeslContentViewInsetsCallback.AnonymousClass1 */

        public void run() {
            Log.i(SeslContentViewInsetsCallback.TAG, "WindowInsetsAnimation could have been cancelled");
            if (!SeslContentViewInsetsCallback.this.mWindowInsetsAnimationStarted && SeslContentViewInsetsCallback.this.mIsDeferInsets) {
                Log.i(SeslContentViewInsetsCallback.TAG, "Start to restore insets state");
                SeslContentViewInsetsCallback.this.mIsDeferInsets = false;
                if (SeslContentViewInsetsCallback.this.mView != null && SeslContentViewInsetsCallback.this.mLastWindowInsets != null) {
                    SeslContentViewInsetsCallback.this.mView.dispatchApplyWindowInsets(SeslContentViewInsetsCallback.this.mLastWindowInsets);
                }
            }
        }
    };
    private boolean mIsDeferInsets = false;
    private WindowInsets mLastWindowInsets;
    private int mPersistentInsetTypes;
    private View mView;
    private boolean mWindowInsetsAnimationStarted = false;

    public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
        return windowInsets;
    }

    public SeslContentViewInsetsCallback(int i, int i2) {
        super(1);
        this.mPersistentInsetTypes = i;
        this.mDeferInsetTypes = i2;
    }

    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        int i;
        this.mView = view;
        this.mLastWindowInsets = windowInsets;
        if (this.mIsDeferInsets) {
            i = this.mPersistentInsetTypes;
        } else {
            i = this.mPersistentInsetTypes | this.mDeferInsetTypes;
        }
        Insets insets = windowInsets.getInsets(i);
        Log.i(TAG, "onApplyWindowInsets, typeInsets = " + insets + ", mIsDeferInsets = " + this.mIsDeferInsets);
        this.mView.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return WindowInsets.CONSUMED;
    }

    public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        if ((windowInsetsAnimation.getTypeMask() & this.mDeferInsetTypes) != 0) {
            Log.i(TAG, "onPrepare");
            this.mIsDeferInsets = true;
            this.handler.postDelayed(this.mHandleInsetsAnimationCanceled, 100);
        }
    }

    public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        if ((windowInsetsAnimation.getTypeMask() & this.mDeferInsetTypes) != 0) {
            Log.i(TAG, "onStart");
            this.handler.removeCallbacks(this.mHandleInsetsAnimationCanceled);
            this.mWindowInsetsAnimationStarted = true;
        }
        return bounds;
    }

    public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
        WindowInsets windowInsets;
        if ((windowInsetsAnimation.getTypeMask() & this.mDeferInsetTypes) != 0) {
            Log.i(TAG, "onEnd");
            this.mIsDeferInsets = false;
            this.mWindowInsetsAnimationStarted = false;
            View view = this.mView;
            if (view != null && (windowInsets = this.mLastWindowInsets) != null) {
                view.dispatchApplyWindowInsets(windowInsets);
            }
        }
    }
}
