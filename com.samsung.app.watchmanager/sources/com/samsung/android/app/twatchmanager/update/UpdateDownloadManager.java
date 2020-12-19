package com.samsung.android.app.twatchmanager.update;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.smartswitch.FileUtils;
import com.samsung.android.app.twatchmanager.update.BaseUpdateTask;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.update.UpdateDownloadFileTask;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UpdateDownloadManager {
    public static final int DOWNLOAD_URL_TIMEOUT_BACKGROUND = 60000;
    public static final int DOWNLOAD_URL_TIMEOUT_FOREGROUND = 20000;
    public static final String TAG = ("tUHM:[Update][Conn]" + UpdateDownloadManager.class.getSimpleName());
    private IDownloadManagerCallback mDownloadManagerCallback;
    private String mDownloadPath = null;
    private boolean mIsBackground = false;
    private Handler mTimeoutHandler = new Handler();
    UpdateDownloadFileTask.IUpdateDownloadFileCallback mUpdateDownloadFileCallback = new UpdateDownloadFileTask.IUpdateDownloadFileCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.AnonymousClass3 */
        private int mPrevProgress = 0;
        private long mTotalSize = 0;
        private double mTotalSizeInMB = 0.0d;

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadFileTask.IUpdateDownloadFileCallback
        public void onFailToUpdateDownload(ErrorCode errorCode) {
            String str = UpdateDownloadManager.TAG;
            Log.d(str, "onFailToUpdateDownload() starts... errorCode : " + errorCode);
            UpdateDownloadManager.this.mDownloadManagerCallback.onFailToDownload(errorCode);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadFileTask.IUpdateDownloadFileCallback
        public void onStartUpdateDownload(int i) {
            String str = UpdateDownloadManager.TAG;
            Log.d(str, "onStartUpdateDownload() starts... totalDownloadSize : " + i);
            this.mPrevProgress = 0;
            this.mTotalSize = (long) i;
            double d2 = (double) this.mTotalSize;
            Double.isNaN(d2);
            this.mTotalSizeInMB = d2 / 1048576.0d;
            this.mPrevProgress = 0;
            UpdateDownloadManager.this.mDownloadManagerCallback.onStartDownload(this.mTotalSizeInMB);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadFileTask.IUpdateDownloadFileCallback
        public void onSuccessToUpdateDownload(Map<String, String> map) {
            String str = UpdateDownloadManager.TAG;
            Log.d(str, "onSuccessToUpdateDownload() starts... downloaded package size : " + map.size());
            UpdateDownloadManager.this.mDownloadManagerCallback.onFinishDownload(map);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadFileTask.IUpdateDownloadFileCallback
        public void onUpdateDownloading(int i, int i2) {
            double d2 = (double) i2;
            Double.isNaN(d2);
            double d3 = d2 / 1048576.0d;
            int i3 = (int) ((d3 / this.mTotalSizeInMB) * 100.0d);
            if (i3 % 20 == 0) {
                Log.d(UpdateDownloadManager.TAG, String.format("percent : %d downloadedSizeInMB : %.1f totalSizeInMB :  %.1f", Integer.valueOf(i3), Double.valueOf(d3), Double.valueOf(this.mTotalSizeInMB)));
            }
            if (i3 >= this.mPrevProgress) {
                this.mPrevProgress = i3;
                UpdateDownloadManager.this.mDownloadManagerCallback.onDownloading(this.mPrevProgress, d3);
            }
        }
    };
    private UpdateDownloadFileTask mUpdateDownloadFileTask;
    BaseUpdateTask.IUpdateTaskCallback mUpdateDownloadURLCallback = new BaseUpdateTask.IUpdateTaskCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.AnonymousClass2 */

        @Override // com.samsung.android.app.twatchmanager.update.BaseUpdateTask.IUpdateTaskCallback
        public void onResult(HashMap<String, StubAPIHelper.XMLResult> hashMap, int i) {
            UpdateDownloadManager.this.releaseTimeoutHandler();
            UpdateDownloadManager updateDownloadManager = UpdateDownloadManager.this;
            updateDownloadManager.mUpdateDownloadFileTask = new UpdateDownloadFileTask(updateDownloadManager.mUpdateDownloadFileCallback, hashMap, i);
            UpdateDownloadManager.this.mUpdateDownloadFileTask.execute(new Void[0]);
            UpdateDownloadManager.this.mDownloadManagerCallback.onDownloadAvailable(hashMap, i);
            UpdateDownloadManager.this.sendIntentToModuleBeforeDownload(TWatchManagerApplication.getAppContext());
        }
    };
    private BaseUpdateTask mUpdateDownloadURLTask;
    private Set<String> mUpdateList = null;

    public enum ErrorCode {
        DOWNLOAD_URL_INVALID,
        DOWNLOAD_FAILED_BY_TIMEOUT,
        DOWNLOAD_FAILED_BY_NO_NETWORK,
        DOWNLOAD_FAILED_BY_LACK_STORAGE,
        DOWNLOAD_NOT_REQUIRED
    }

    public interface IDownloadManagerCallback {
        void onDownloadAvailable(HashMap<String, StubAPIHelper.XMLResult> hashMap, int i);

        void onDownloading(int i, double d2);

        void onFailToDownload(ErrorCode errorCode);

        void onFinishDownload(Map<String, String> map);

        void onStartDownload(double d2);
    }

    public UpdateDownloadManager(IDownloadManagerCallback iDownloadManagerCallback, Set<String> set, boolean z) {
        this.mDownloadManagerCallback = iDownloadManagerCallback;
        this.mUpdateList = set;
        this.mDownloadPath = UpdateUtil.getPathToDownload(TWatchManagerApplication.getAppContext());
        this.mIsBackground = z;
    }

    private boolean isUHMIncluded() {
        boolean contains = this.mUpdateList.contains("com.samsung.android.app.watchmanager");
        String str = TAG;
        Log.d(str, "isUHMIncluded [" + contains + "]");
        return contains;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void releaseTimeoutHandler() {
        Handler handler = this.mTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendIntentToModuleBeforeDownload(Context context) {
        String str = TAG;
        Log.d(str, "sendIntentToModuleBeforeDownload starts [" + context + "]");
        boolean isUHMIncluded = isUHMIncluded();
        if (context == null || isUHMIncluded) {
            Log.e(TAG, "sendIntentToModuleBeforeDownload, will not send intent");
        } else {
            context.sendBroadcast(new Intent(GlobalConst.ACTION_HM_UPDATE_DOWNLOAD_STARTED));
        }
        Log.d(TAG, "sendIntentToModuleBeforeDownload ends");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startDownloadCheckTimer(final ErrorCode errorCode, int i) {
        Handler handler = this.mTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mTimeoutHandler.postDelayed(new Runnable() {
                /* class com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.AnonymousClass4 */

                public void run() {
                    if (UpdateDownloadManager.this.mUpdateDownloadURLTask != null) {
                        UpdateDownloadManager.this.mUpdateDownloadURLTask.cancel(true);
                    }
                    if (UpdateDownloadManager.this.mDownloadManagerCallback != null) {
                        Log.d(UpdateDownloadManager.TAG, "mTimeoutHandler.run() download timeout...");
                        UpdateDownloadManager.this.mDownloadManagerCallback.onFailToDownload(errorCode);
                    }
                }
            }, (long) i);
        }
    }

    public void clearResources() {
        if (new File(this.mDownloadPath).exists()) {
            FileUtils.deleteDirectory(this.mDownloadPath);
        }
        UpdateUtil.clearUpdateCheckPref(TWatchManagerApplication.getAppContext());
        BaseUpdateTask baseUpdateTask = this.mUpdateDownloadURLTask;
        if (baseUpdateTask != null) {
            baseUpdateTask.cancel(true);
        }
        UpdateDownloadFileTask updateDownloadFileTask = this.mUpdateDownloadFileTask;
        if (updateDownloadFileTask != null) {
            updateDownloadFileTask.cancel(true);
        }
        releaseTimeoutHandler();
    }

    public void startUpdateDownloadManager(final Context context) {
        Log.d(TAG, "startUpdateDownloadManager() starts...");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            /* class com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.AnonymousClass1 */

            public void run() {
                String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
                String str = UpdateDownloadManager.TAG;
                Log.d(str, "startUpdateDownloadManager() extuk : " + string);
                if (HostManagerUtilsNetwork.isDataNetworkConnected(context)) {
                    UpdateDownloadManager updateDownloadManager = UpdateDownloadManager.this;
                    updateDownloadManager.startDownloadCheckTimer(ErrorCode.DOWNLOAD_FAILED_BY_TIMEOUT, updateDownloadManager.mIsBackground ? 60000 : UpdateDownloadManager.DOWNLOAD_URL_TIMEOUT_FOREGROUND);
                    UpdateDownloadManager updateDownloadManager2 = UpdateDownloadManager.this;
                    updateDownloadManager2.mUpdateDownloadURLTask = new UpdateDownLoadURLTask(updateDownloadManager2.mUpdateList, UpdateDownloadManager.this.mUpdateDownloadURLCallback, string);
                    UpdateDownloadManager.this.mUpdateDownloadURLTask.execute(new Void[0]);
                    return;
                }
                UpdateDownloadManager.this.mDownloadManagerCallback.onFailToDownload(ErrorCode.DOWNLOAD_FAILED_BY_NO_NETWORK);
            }
        });
    }
}
