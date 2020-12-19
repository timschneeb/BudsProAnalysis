package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import com.samsung.accessory.hearablemgr.R;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.io.IOException;
import seccompat.android.util.Log;

public class VIView extends FrameLayout implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener {
    private static final boolean DEBUG_MODE = false;
    private static final String TAG = "Attic_VIView";
    private boolean mLooping = true;
    private MediaPlayer mMediaPlayer;
    private boolean mPreparing = true;
    private int mResId = 0;
    private View mScreenView;
    private boolean mStopped = false;
    private TextureView mTextureView;

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public VIView(Context context) {
        super(context);
        init(context, null);
    }

    public VIView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public VIView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VIView);
            try {
                this.mResId = obtainStyledAttributes.getResourceId(2, 0);
                boolean z = true;
                this.mLooping = obtainStyledAttributes.getBoolean(1, true);
                if (obtainStyledAttributes.getBoolean(0, true)) {
                    z = false;
                }
                this.mStopped = z;
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        this.mTextureView = new TextureView(context);
        this.mTextureView.setSurfaceTextureListener(this);
        addView(this.mTextureView);
        this.mScreenView = new View(context);
        Drawable background = getBackground();
        if (background != null) {
            this.mScreenView.setBackground(background.getConstantState().newDrawable());
        }
        addView(this.mScreenView);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        Log.d(TAG, "onSurfaceTextureAvailable() : " + Util.toSimpleString(surfaceTexture) + ", width=" + i + ", height=" + i2);
        prepareMediaPlayer(new Surface(surfaceTexture));
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        Log.d(TAG, "onSurfaceTextureSizeChanged() : " + Util.toSimpleString(surfaceTexture) + ", width=" + i + ", height=" + i2);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d(TAG, "onSurfaceTextureDestroyed() : " + Util.toSimpleString(surfaceTexture));
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            return true;
        }
        mediaPlayer.release();
        this.mMediaPlayer = null;
        return true;
    }

    private void prepareMediaPlayer(Surface surface) {
        Log.d(TAG, "prepareMediaPlayer() : " + Util.toSimpleString(surface));
        if (this.mResId != 0) {
            try {
                this.mMediaPlayer = new MediaPlayer();
                setResourceToMediaPlayer(this.mMediaPlayer, getResources(), this.mResId);
                this.mMediaPlayer.setLooping(this.mLooping);
                this.mMediaPlayer.setSurface(surface);
                this.mMediaPlayer.setOnPreparedListener(this);
                this.mMediaPlayer.setOnCompletionListener(this);
                this.mMediaPlayer.setOnSeekCompleteListener(this);
                this.mMediaPlayer.setOnInfoListener(this);
                this.mPreparing = true;
                this.mMediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "prepareMediaPlayer() : Exception : " + e);
            }
        } else {
            Log.e(TAG, "prepareMediaPlayer() : mResId == null");
        }
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared() : " + Util.toSimpleString(mediaPlayer));
        this.mPreparing = false;
        MediaPlayer mediaPlayer2 = this.mMediaPlayer;
        if (mediaPlayer2 == null) {
            Log.w(TAG, "onPrepared() : mMediaPlayer == null");
        } else if (this.mStopped) {
            mediaPlayer2.seekTo(1);
        } else {
            mediaPlayer2.start();
        }
    }

    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        if (i != 3) {
            return false;
        }
        Log.d(TAG, "onInfo() : MEDIA_INFO_VIDEO_RENDERING_START");
        this.mScreenView.animate().alpha(0.0f).setDuration(400).setInterpolator(new LinearInterpolator()).start();
        return false;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onCompletion() : " + Util.toSimpleString(mediaPlayer));
    }

    public void onSeekComplete(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onSeekComplete() : " + Util.toSimpleString(mediaPlayer));
    }

    static void setResourceToMediaPlayer(MediaPlayer mediaPlayer, Resources resources, int i) throws IOException {
        AssetFileDescriptor openRawResourceFd = resources.openRawResourceFd(i);
        try {
            mediaPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
        } finally {
            Util.safeClose(openRawResourceFd);
        }
    }

    public void start() {
        MediaPlayer mediaPlayer;
        Log.d(TAG, "start() : " + this.mPreparing + ", mMediaPlayer=" + Util.toSimpleString(this.mMediaPlayer));
        this.mStopped = false;
        if (!this.mPreparing && (mediaPlayer = this.mMediaPlayer) != null) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        MediaPlayer mediaPlayer;
        Log.d(TAG, "stop() : " + this.mPreparing + ", mMediaPlayer=" + Util.toSimpleString(this.mMediaPlayer));
        this.mStopped = true;
        if (!this.mPreparing && (mediaPlayer = this.mMediaPlayer) != null) {
            mediaPlayer.pause();
        }
    }

    public void reset() {
        MediaPlayer mediaPlayer;
        Log.d(TAG, "reset() : " + this.mPreparing + ", mMediaPlayer=" + Util.toSimpleString(this.mMediaPlayer));
        this.mStopped = true;
        if (!this.mPreparing && (mediaPlayer = this.mMediaPlayer) != null) {
            mediaPlayer.pause();
            this.mMediaPlayer.seekTo(1);
        }
    }
}
