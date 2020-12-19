package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.github.penfeizhou.animation.loader.AssetStreamLoader;
import com.github.penfeizhou.animation.webp.WebPDrawable;
import com.samsung.accessory.hearablemgr.common.util.WorkerHandler;
import seccompat.android.util.Log;

public class WebpImageView extends AppCompatImageView {
    private static final String TAG = "Attic_WebpImageView";
    private String mFilename;
    private Handler mHandler;
    private Integer mLoopLimit;
    private WorkerHandler mWorkerHandler;

    public WebpImageView(Context context) {
        super(context);
        init(context, null);
    }

    public WebpImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mHandler = new Handler();
        this.mWorkerHandler = WorkerHandler.createWorkerHandler("webp_image_view@" + this);
        this.mFilename = (String) getTag();
        String str = this.mFilename;
        if (str != null) {
            setImageDrawable(new WebPDrawable(new AssetStreamLoader(context, str)));
        }
    }

    public void setLoopLimit(int i) {
        this.mLoopLimit = Integer.valueOf(i);
    }

    public void setFilename(String str) {
        this.mFilename = str;
    }

    public void initWebpImageDrawable(final Runnable runnable) {
        Log.d(TAG, "initWebpImageDrawable()");
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.common.ui.WebpImageView.AnonymousClass1 */

            public void run() {
                if (WebpImageView.this.mFilename != null) {
                    WebpImageView webpImageView = WebpImageView.this;
                    webpImageView.setWebpImageDrawable(new WebPDrawable(new AssetStreamLoader(webpImageView.getContext(), WebpImageView.this.mFilename)), runnable);
                    return;
                }
                Log.e(WebpImageView.TAG, "mFilename == null");
            }
        });
    }

    public WebPDrawable getWebPDrawable() {
        Drawable drawable = getDrawable();
        if (drawable instanceof WebPDrawable) {
            return (WebPDrawable) drawable;
        }
        return null;
    }

    public void stop() {
        Drawable drawable = getDrawable();
        if (drawable instanceof WebPDrawable) {
            ((WebPDrawable) drawable).stop();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setWebpImageDrawable(WebPDrawable webPDrawable, Runnable runnable) {
        Log.d(TAG, "setWebpImageDrawable() : " + webPDrawable);
        Integer num = this.mLoopLimit;
        if (num != null) {
            webPDrawable.setLoopLimit(num.intValue());
        }
        Log.d(TAG, "setImageDrawable()_before");
        setImageDrawable(webPDrawable);
        webPDrawable.pause();
        Log.d(TAG, "setImageDrawable()_end");
        if (runnable != null) {
            this.mHandler.post(runnable);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mWorkerHandler.quit();
        this.mHandler.removeCallbacksAndMessages(null);
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
