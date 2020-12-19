package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
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
import com.samsung.accessory.hearablemgr.common.util.WorkerHandler;
import java.util.Arrays;
import seccompat.android.util.Log;

public class VIListView extends FrameLayout implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener {
    private static final boolean DEBUG_MODE = false;
    private static final String TAG = "Attic_VIListView";
    private int mCurIndex = 0;
    private MediaPlayer mMediaPlayer;
    private MediaStartListener mMediaStartListener;
    private boolean mPreparing = true;
    private String[] mRawNameList;
    private View mScreenView;
    private boolean mScreenViewShowing;
    private boolean mStopped = false;
    private TextureView mTextureView;
    private WorkerHandler mWorkerHandler;

    public interface MediaStartListener {
        void onMediaStart(VIListView vIListView, int i);
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public VIListView(Context context) {
        super(context);
        init(context, null);
    }

    public VIListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public VIListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mWorkerHandler = WorkerHandler.createWorkerHandler("vi_list_view_worker@" + this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VIListView);
            try {
                Log.d(TAG, "init() : " + obtainStyledAttributes.getString(1));
                String string = obtainStyledAttributes.getString(1);
                if (string != null) {
                    this.mRawNameList = string.split(",");
                }
                Log.d(TAG, "mRawFileList : " + Arrays.toString(this.mRawNameList));
                boolean z = false;
                if (!obtainStyledAttributes.getBoolean(0, true)) {
                    z = true;
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
        this.mScreenViewShowing = true;
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
        if (this.mRawNameList != null) {
            try {
                this.mMediaPlayer = new MediaPlayer();
                this.mMediaPlayer.setLooping(false);
                this.mMediaPlayer.setSurface(surface);
                this.mMediaPlayer.setOnPreparedListener(this);
                this.mMediaPlayer.setOnCompletionListener(this);
                this.mMediaPlayer.setOnSeekCompleteListener(this);
                setMediaPlayerDataSource(this.mRawNameList[this.mCurIndex]);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "prepareMediaPlayer() : Exception : " + e);
            }
        } else {
            Log.e(TAG, "prepareMediaPlayer() : mResId == null");
        }
    }

    private void setMediaPlayerDataSource(final String str) {
        Log.d(TAG, "setMediaPlayerDataSource() : " + str);
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.common.ui.VIListView.AnonymousClass1 */

            public void run() {
                try {
                    if (VIListView.this.mMediaPlayer != null) {
                        VIListView.this.mMediaPlayer.reset();
                        VIView.setResourceToMediaPlayer(VIListView.this.mMediaPlayer, VIListView.this.getResources(), VIListView.this.getResources().getIdentifier(str, "raw", VIListView.this.getContext().getPackageName()));
                        VIListView.this.mPreparing = true;
                        VIListView.this.mMediaPlayer.prepareAsync();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(VIListView.TAG, "setMediaPlayerDataSource() : Exception : " + e);
                }
            }
        });
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared() : " + Util.toSimpleString(mediaPlayer));
        this.mPreparing = false;
        MediaPlayer mediaPlayer2 = this.mMediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.setOnInfoListener(this);
            if (this.mStopped) {
                this.mMediaPlayer.seekTo(1);
            } else {
                this.mMediaPlayer.start();
            }
        } else {
            Log.w(TAG, "onPrepared() : mMediaPlayer == null");
        }
    }

    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        if (i == 3) {
            Log.d(TAG, "onInfo() : MEDIA_INFO_VIDEO_RENDERING_START");
            MediaStartListener mediaStartListener = this.mMediaStartListener;
            if (mediaStartListener != null) {
                mediaStartListener.onMediaStart(this, this.mCurIndex);
            }
            if (this.mScreenViewShowing) {
                this.mScreenView.animate().alpha(0.0f).setDuration(400).setInterpolator(new LinearInterpolator()).start();
                this.mScreenViewShowing = false;
            }
        }
        return false;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onCompletion() : " + Util.toSimpleString(mediaPlayer));
        String[] strArr = this.mRawNameList;
        this.mCurIndex = (this.mCurIndex + 1) % strArr.length;
        setMediaPlayerDataSource(strArr[this.mCurIndex]);
    }

    public void onSeekComplete(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onSeekComplete() : " + Util.toSimpleString(mediaPlayer));
    }

    public void setMediaStartListener(MediaStartListener mediaStartListener) {
        this.mMediaStartListener = mediaStartListener;
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
        Log.d(TAG, "reset() : " + this.mPreparing + ", mMediaPlayer=" + Util.toSimpleString(this.mMediaPlayer));
        this.mStopped = true;
        String[] strArr = this.mRawNameList;
        this.mCurIndex = 0;
        setMediaPlayerDataSource(strArr[0]);
    }
}
