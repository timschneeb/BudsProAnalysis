package com.github.penfeizhou.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.github.penfeizhou.animation.decode.FrameSeqDecoder;
import com.github.penfeizhou.animation.loader.Loader;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

public abstract class FrameAnimationDrawable<Decoder extends FrameSeqDecoder> extends Drawable implements Animatable2Compat, FrameSeqDecoder.RenderListener {
    private static final int MSG_ANIMATION_END = 2;
    private static final int MSG_ANIMATION_START = 1;
    private static final String TAG = FrameAnimationDrawable.class.getSimpleName();
    private Set<Animatable2Compat.AnimationCallback> animationCallbacks = new HashSet();
    private boolean autoPlay = true;
    private Bitmap bitmap;
    private DrawFilter drawFilter = new PaintFlagsDrawFilter(0, 3);
    private final Decoder frameSeqDecoder;
    private Runnable invalidateRunnable = new Runnable() {
        /* class com.github.penfeizhou.animation.FrameAnimationDrawable.AnonymousClass2 */

        public void run() {
            FrameAnimationDrawable.this.invalidateSelf();
        }
    };
    private Matrix matrix = new Matrix();
    private final Paint paint = new Paint();
    private Handler uiHandler = new Handler(Looper.getMainLooper()) {
        /* class com.github.penfeizhou.animation.FrameAnimationDrawable.AnonymousClass1 */

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                for (Animatable2Compat.AnimationCallback animationCallback : FrameAnimationDrawable.this.animationCallbacks) {
                    animationCallback.onAnimationStart(FrameAnimationDrawable.this);
                }
            } else if (i == 2) {
                for (Animatable2Compat.AnimationCallback animationCallback2 : FrameAnimationDrawable.this.animationCallbacks) {
                    animationCallback2.onAnimationEnd(FrameAnimationDrawable.this);
                }
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract Decoder createFrameSeqDecoder(Loader loader, FrameSeqDecoder.RenderListener renderListener);

    public int getOpacity() {
        return -3;
    }

    public FrameAnimationDrawable(Decoder decoder) {
        this.paint.setAntiAlias(true);
        this.frameSeqDecoder = decoder;
    }

    public FrameAnimationDrawable(Loader loader) {
        this.paint.setAntiAlias(true);
        this.frameSeqDecoder = createFrameSeqDecoder(loader, this);
    }

    public void setAutoPlay(boolean z) {
        this.autoPlay = z;
    }

    public void setLoopLimit(int i) {
        this.frameSeqDecoder.setLoopLimit(i);
    }

    public void reset() {
        this.frameSeqDecoder.reset();
    }

    public void pause() {
        this.frameSeqDecoder.pause();
    }

    public void resume() {
        this.frameSeqDecoder.resume();
    }

    public boolean isPaused() {
        return this.frameSeqDecoder.isPaused();
    }

    public void start() {
        this.frameSeqDecoder.addRenderListener(this);
        if (this.autoPlay) {
            this.frameSeqDecoder.start();
        } else if (!this.frameSeqDecoder.isRunning()) {
            this.frameSeqDecoder.start();
        }
    }

    public void stop() {
        this.frameSeqDecoder.removeRenderListener(this);
        if (this.autoPlay) {
            this.frameSeqDecoder.stop();
        } else {
            this.frameSeqDecoder.stopIfNeeded();
        }
    }

    public boolean isRunning() {
        return this.frameSeqDecoder.isRunning();
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            canvas.setDrawFilter(this.drawFilter);
            canvas.drawBitmap(this.bitmap, this.matrix, this.paint);
        }
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        boolean desiredSize = this.frameSeqDecoder.setDesiredSize(getBounds().width(), getBounds().height());
        this.matrix.setScale(((((float) getBounds().width()) * 1.0f) * ((float) this.frameSeqDecoder.getSampleSize())) / ((float) this.frameSeqDecoder.getBounds().width()), ((((float) getBounds().height()) * 1.0f) * ((float) this.frameSeqDecoder.getSampleSize())) / ((float) this.frameSeqDecoder.getBounds().height()));
        if (desiredSize) {
            this.bitmap = Bitmap.createBitmap(this.frameSeqDecoder.getBounds().width() / this.frameSeqDecoder.getSampleSize(), this.frameSeqDecoder.getBounds().height() / this.frameSeqDecoder.getSampleSize(), Bitmap.Config.ARGB_8888);
        }
    }

    public void setAlpha(int i) {
        this.paint.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    @Override // com.github.penfeizhou.animation.decode.FrameSeqDecoder.RenderListener
    public void onStart() {
        Message.obtain(this.uiHandler, 1).sendToTarget();
    }

    @Override // com.github.penfeizhou.animation.decode.FrameSeqDecoder.RenderListener
    public void onRender(ByteBuffer byteBuffer) {
        if (isRunning()) {
            Bitmap bitmap2 = this.bitmap;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                this.bitmap = Bitmap.createBitmap(this.frameSeqDecoder.getBounds().width() / this.frameSeqDecoder.getSampleSize(), this.frameSeqDecoder.getBounds().height() / this.frameSeqDecoder.getSampleSize(), Bitmap.Config.ARGB_8888);
            }
            byteBuffer.rewind();
            if (byteBuffer.remaining() < this.bitmap.getByteCount()) {
                Log.e(TAG, "onRender:Buffer not large enough for pixels");
                return;
            }
            this.bitmap.copyPixelsFromBuffer(byteBuffer);
            this.uiHandler.post(this.invalidateRunnable);
        }
    }

    @Override // com.github.penfeizhou.animation.decode.FrameSeqDecoder.RenderListener
    public void onEnd() {
        Message.obtain(this.uiHandler, 2).sendToTarget();
    }

    public boolean setVisible(boolean z, boolean z2) {
        if (this.autoPlay) {
            if (z) {
                if (!isRunning()) {
                    start();
                }
            } else if (isRunning()) {
                stop();
            }
        }
        return super.setVisible(z, z2);
    }

    public int getIntrinsicWidth() {
        try {
            return this.frameSeqDecoder.getBounds().width();
        } catch (Exception unused) {
            return 0;
        }
    }

    public int getIntrinsicHeight() {
        try {
            return this.frameSeqDecoder.getBounds().height();
        } catch (Exception unused) {
            return 0;
        }
    }

    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public void registerAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        this.animationCallbacks.add(animationCallback);
    }

    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public boolean unregisterAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        return this.animationCallbacks.remove(animationCallback);
    }

    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public void clearAnimationCallbacks() {
        this.animationCallbacks.clear();
    }
}
