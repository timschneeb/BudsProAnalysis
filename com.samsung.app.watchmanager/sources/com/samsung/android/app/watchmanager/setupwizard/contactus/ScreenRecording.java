package com.samsung.android.app.watchmanager.setupwizard.contactus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

public class ScreenRecording {
    protected static int DISPLAY_HEIGHT = 1280;
    protected static int DISPLAY_WIDTH = 720;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final String TAG = "ScreenRecording";
    Activity activity;
    private final AskandErrorReportFragment askandErrorReportFragment;
    private String currFileName;
    private HandlerThread handlerThread;
    private boolean isImage;
    Handler mHandler;
    private ImageReader mImageReader;
    private MediaProjection mMediaProjection;
    private MediaProjectionCallback mMediaProjectionCallback;
    private MediaRecorder mMediaRecorder;
    private MediaProjectionManager mProjectionManager;
    private int mScreenDensity;
    private VirtualDisplay mVirtualDisplay;

    /* access modifiers changed from: private */
    @TargetApi(21)
    public class MediaProjectionCallback extends MediaProjection.Callback {
        private MediaProjectionCallback() {
        }

        public void onStop() {
            if (!ScreenRecording.this.isImage) {
                ScreenRecording.this.mMediaRecorder.stop();
                ScreenRecording.this.mMediaRecorder.reset();
                Log.v(ScreenRecording.TAG, "Recording Stopped");
            }
            ScreenRecording.this.mMediaProjection = null;
            ScreenRecording.this.stopScreenSharing();
        }
    }

    static {
        ORIENTATIONS.append(0, 90);
        ORIENTATIONS.append(1, 0);
        ORIENTATIONS.append(2, 270);
        ORIENTATIONS.append(3, 180);
    }

    public ScreenRecording(Activity activity2, AskandErrorReportFragment askandErrorReportFragment2) {
        Log.v(TAG, "Constructor");
        this.activity = activity2;
        this.askandErrorReportFragment = askandErrorReportFragment2;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.mScreenDensity = displayMetrics.densityDpi;
        DISPLAY_WIDTH = displayMetrics.widthPixels;
        DISPLAY_HEIGHT = displayMetrics.heightPixels;
    }

    @TargetApi(21)
    private VirtualDisplay createVirtualDisplay() {
        MediaProjection mediaProjection;
        int i;
        int i2;
        int i3;
        int i4;
        Surface surface;
        if (!this.isImage) {
            mediaProjection = this.mMediaProjection;
            i = DISPLAY_WIDTH;
            i2 = DISPLAY_HEIGHT;
            i3 = this.mScreenDensity;
            i4 = 16;
            surface = this.mMediaRecorder.getSurface();
        } else {
            mediaProjection = this.mMediaProjection;
            i = DISPLAY_WIDTH;
            i2 = DISPLAY_HEIGHT;
            i3 = this.mScreenDensity;
            i4 = 16;
            surface = this.mImageReader.getSurface();
        }
        return mediaProjection.createVirtualDisplay("MainActivity", i, i2, i3, i4, surface, null, null);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    @TargetApi(19)
    private void stopScreenSharing() {
        VirtualDisplay virtualDisplay = this.mVirtualDisplay;
        if (virtualDisplay != null) {
            virtualDisplay.release();
            destroyMediaProjection();
        }
    }

    /* access modifiers changed from: protected */
    public void closeRecording() {
        try {
            this.mMediaRecorder.stop();
            this.mMediaRecorder.reset();
        } catch (RuntimeException e) {
            Log.d(TAG, "video not recorded properly.so deleting it");
            File file = new File(this.currFileName);
            if (file.exists()) {
                file.delete();
            }
            e.printStackTrace();
        }
        Log.v(TAG, "closing Recording");
        stopScreenSharing();
    }

    /* access modifiers changed from: protected */
    @TargetApi(21)
    public void destroyMediaProjection() {
        MediaProjection mediaProjection = this.mMediaProjection;
        if (mediaProjection != null) {
            mediaProjection.unregisterCallback(this.mMediaProjectionCallback);
            this.mMediaProjection.stop();
            this.mMediaProjection = null;
        }
        Log.i(TAG, "MediaProjection Stopped");
    }

    public ImageReader getmImageReader() {
        return this.mImageReader;
    }

    @TargetApi(19)
    public void initImageReader() {
        this.handlerThread = new HandlerThread(ScreenRecording.class.getSimpleName(), 10);
        this.handlerThread.start();
        this.mHandler = new Handler(this.handlerThread.getLooper());
        this.mImageReader = ImageReader.newInstance(DISPLAY_WIDTH, DISPLAY_HEIGHT, 1, 2);
        this.mProjectionManager = (MediaProjectionManager) this.activity.getSystemService("media_projection");
        this.mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.ScreenRecording.AnonymousClass1 */

            /* JADX WARNING: Removed duplicated region for block: B:41:0x0107 A[Catch:{ all -> 0x012c }] */
            /* JADX WARNING: Removed duplicated region for block: B:44:0x0119 A[SYNTHETIC, Splitter:B:44:0x0119] */
            /* JADX WARNING: Removed duplicated region for block: B:49:0x0123  */
            /* JADX WARNING: Removed duplicated region for block: B:54:0x012f A[SYNTHETIC, Splitter:B:54:0x012f] */
            /* JADX WARNING: Removed duplicated region for block: B:59:0x0139  */
            /* JADX WARNING: Removed duplicated region for block: B:61:0x013e  */
            /* JADX WARNING: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onImageAvailable(android.media.ImageReader r14) {
                /*
                // Method dump skipped, instructions count: 328
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.ScreenRecording.AnonymousClass1.onImageAvailable(android.media.ImageReader):void");
            }
        }, this.mHandler);
    }

    /* access modifiers changed from: protected */
    public void initRecorder() {
        this.mMediaRecorder = new MediaRecorder();
        this.mProjectionManager = (MediaProjectionManager) this.activity.getSystemService("media_projection");
        try {
            this.mMediaRecorder.setVideoSource(2);
            this.mMediaRecorder.setOutputFormat(1);
            this.currFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/video/" + System.currentTimeMillis() + ".mp4";
            this.mMediaRecorder.setOutputFile(this.currFileName);
            this.mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
            this.mMediaRecorder.setVideoEncoder(2);
            this.mMediaRecorder.setVideoEncodingBitRate(4000000);
            this.mMediaRecorder.setVideoFrameRate(30);
            this.mMediaRecorder.setOrientationHint(ORIENTATIONS.get(this.activity.getWindowManager().getDefaultDisplay().getRotation() + 90));
            this.mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isImage() {
        return this.isImage;
    }

    public void onActivityResult(int i, Intent intent) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        if (i != -1) {
            Toast.makeText(this.activity, "Screen Cast Permission Denied", 0).show();
            return;
        }
        this.mMediaProjectionCallback = new MediaProjectionCallback();
        this.mMediaProjection = this.mProjectionManager.getMediaProjection(i, intent);
        this.mMediaProjection.registerCallback(this.mMediaProjectionCallback, null);
        this.mVirtualDisplay = createVirtualDisplay();
        if (!this.isImage) {
            this.mMediaRecorder.start();
        }
    }

    public void setImage(boolean z) {
        this.isImage = z;
    }

    /* access modifiers changed from: protected */
    @TargetApi(21)
    public void shareScreen() {
        if (this.mMediaProjection == null) {
            this.askandErrorReportFragment.startActivityForResult(this.mProjectionManager.createScreenCaptureIntent(), 2345);
            return;
        }
        this.mVirtualDisplay = createVirtualDisplay();
        if (!this.isImage) {
            this.mMediaRecorder.start();
        }
    }
}
