package com.samsung.android.app.twatchmanager.update;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.samsung.android.app.twatchmanager.util.HandlerThreadUtils;

public class DownloadApkTimeOutCheckHandler {
    private static final int DOWNLOAD_FULL_TIME_OUT_CHECK_TIME = 1800;
    private static final int DOWNLOAD_TIME_INTERVAL = 15000;
    private static final int MESSAGE_DOWNLOAD_APK_PROGRESS = 3;
    private static final int PROGRESS_SEC = 15;
    private static final String TAG = ("tUHM:[Update]" + DownloadApkTimeOutCheckHandler.class.getSimpleName());
    private long mFileByteSize = 0;
    private int mFileSizeCheckProgressSec = 0;
    private Handler mHandler;
    private DownloadApkTimeOutCheckHandlerListener mListener = null;
    private int mProgressSec = -1;
    private HandlerThread mThread;

    public interface DownloadApkTimeOutCheckHandlerListener {
        void onFileSizeNoChanedTimeOut();

        void onFullTimeOut();

        void onRequestFileSize();
    }

    public void init() {
        this.mThread = new HandlerThread("DOWNLOAD_TIMEOUT_THREAD", 5);
        this.mThread.start();
        this.mHandler = new Handler(this.mThread.getLooper()) {
            /* class com.samsung.android.app.twatchmanager.update.DownloadApkTimeOutCheckHandler.AnonymousClass1 */

            public void handleMessage(Message message) {
                if (DownloadApkTimeOutCheckHandler.this.mListener != null && message.what == 3) {
                    DownloadApkTimeOutCheckHandler.this.mProgressSec += 15;
                    Log.d(DownloadApkTimeOutCheckHandler.TAG, "MESSAGE_DOWNLOAD_APK_PROGRESS progressSec = " + DownloadApkTimeOutCheckHandler.this.mProgressSec);
                    if (DownloadApkTimeOutCheckHandler.this.mProgressSec == DownloadApkTimeOutCheckHandler.DOWNLOAD_FULL_TIME_OUT_CHECK_TIME) {
                        DownloadApkTimeOutCheckHandler.this.mListener.onFullTimeOut();
                        DownloadApkTimeOutCheckHandler.this.stop();
                    } else {
                        sendEmptyMessageDelayed(3, 15000);
                    }
                }
                super.handleMessage(message);
            }
        };
    }

    public boolean isStarted() {
        return this.mProgressSec != -1;
    }

    public void setFileSize(long j) {
        String str = TAG;
        Log.d(str, "setFileSize byteSize = " + j + " before Size = " + this.mFileByteSize);
        if (j == this.mFileByteSize) {
            DownloadApkTimeOutCheckHandlerListener downloadApkTimeOutCheckHandlerListener = this.mListener;
            if (downloadApkTimeOutCheckHandlerListener != null) {
                downloadApkTimeOutCheckHandlerListener.onFileSizeNoChanedTimeOut();
            }
            stop();
            return;
        }
        this.mFileByteSize = j;
        this.mHandler.sendEmptyMessageDelayed(3, 15000);
    }

    public void start(DownloadApkTimeOutCheckHandlerListener downloadApkTimeOutCheckHandlerListener) {
        Log.d(TAG, "start");
        init();
        this.mListener = downloadApkTimeOutCheckHandlerListener;
        this.mFileSizeCheckProgressSec = 0;
        this.mProgressSec = 0;
        this.mFileByteSize = 0;
        this.mHandler.sendEmptyMessageDelayed(3, 15000);
    }

    public void stop() {
        Log.d(TAG, "stop");
        HandlerThreadUtils.close(this.mThread);
        this.mHandler.removeMessages(3);
        this.mFileSizeCheckProgressSec = 0;
        this.mProgressSec = -1;
        this.mFileByteSize = 0;
        this.mListener = null;
    }
}
