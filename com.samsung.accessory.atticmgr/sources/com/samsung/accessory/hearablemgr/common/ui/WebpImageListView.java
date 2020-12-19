package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.github.penfeizhou.animation.loader.AssetStreamLoader;
import com.github.penfeizhou.animation.webp.WebPDrawable;
import com.samsung.accessory.hearablemgr.common.util.WorkerHandler;
import java.util.Arrays;
import seccompat.android.util.Log;

public class WebpImageListView extends AppCompatImageView {
    private static final String TAG = "Attic_WebpImageListView";
    private int mCurImageIndex = 0;
    private String[] mFilenameList;
    private AnimationListener mListener;
    private WorkerHandler mWorkerHandler;

    public interface AnimationListener {
        void onAnimationStart(int i);
    }

    public WebpImageListView(Context context) {
        super(context);
        init(context, null);
    }

    public WebpImageListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mWorkerHandler = WorkerHandler.createWorkerHandler("webp_image_view@" + this);
        this.mFilenameList = ((String) getTag()).split(",");
        Log.d(TAG, "mFilenameList : " + Arrays.toString(this.mFilenameList));
        setWebpImageDrawable(new WebPDrawable(new AssetStreamLoader(getContext(), this.mFilenameList[0])));
    }

    /* access modifiers changed from: package-private */
    public void setNextImageDrawable() {
        this.mCurImageIndex = (this.mCurImageIndex + 1) % this.mFilenameList.length;
        Log.d(TAG, "setNextImageDrawable() : " + this.mCurImageIndex);
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.common.ui.WebpImageListView.AnonymousClass1 */

            public void run() {
                WebpImageListView webpImageListView = WebpImageListView.this;
                webpImageListView.setWebpImageDrawable(new WebPDrawable(new AssetStreamLoader(webpImageListView.getContext(), WebpImageListView.this.mFilenameList[WebpImageListView.this.mCurImageIndex])));
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void setWebpImageDrawable(WebPDrawable webPDrawable) {
        Log.d(TAG, "setWebpImageDrawable() : " + webPDrawable);
        webPDrawable.setLoopLimit(1);
        webPDrawable.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            /* class com.samsung.accessory.hearablemgr.common.ui.WebpImageListView.AnonymousClass2 */

            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public void onAnimationStart(Drawable drawable) {
                super.onAnimationStart(drawable);
                Log.d(WebpImageListView.TAG, "onAnimationStart()");
            }

            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                Log.d(WebpImageListView.TAG, "onAnimationEnd()");
                WebpImageListView.this.setNextImageDrawable();
            }
        });
        Log.d(TAG, "setImageDrawable()_before");
        setImageDrawable(webPDrawable);
        Log.d(TAG, "setImageDrawable()_end");
        final AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            final int i = this.mCurImageIndex;
            post(new Runnable() {
                /* class com.samsung.accessory.hearablemgr.common.ui.WebpImageListView.AnonymousClass3 */

                public void run() {
                    animationListener.onAnimationStart(i);
                }
            });
        }
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.mListener = animationListener;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mWorkerHandler.quit();
    }

    public void invalidate() {
        if (UiUtil.isOnUiThread()) {
            super.invalidate();
        } else {
            Log.w(TAG, "invalidate() skipped");
        }
    }

    public void requestLayout() {
        if (UiUtil.isOnUiThread()) {
            super.requestLayout();
        } else {
            Log.w(TAG, "requestLayout() skipped");
        }
    }
}
