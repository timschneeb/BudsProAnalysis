package com.samsung.android.app.twatchmanager.update;

import android.os.AsyncTask;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.DownloadApkTimeOutCheckHandler;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.update.UpdateDownloadManager;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UpdateDownloadFileTask extends AsyncTask<Void, Integer, Boolean> {
    private static final String TAG = ("tUHM:[Update]" + UpdateDownloadFileTask.class.getSimpleName());
    private boolean mDataNetworkAvailable;
    private DownloadApkTimeOutCheckHandler mDownloadApkTimeOutCheckHandler;
    private IUpdateDownloadFileCallback mDownloadFileCallback;
    private String mDownloadPath;
    private HashMap<String, String> mDownloadedPkgMap = new HashMap<>();
    FileOutputStream mFout;
    private boolean mHasEnoughStorage;
    InputStream mIn;
    private boolean mPackageSetValid;
    private DownloadApkTimeOutCheckHandler.DownloadApkTimeOutCheckHandlerListener mTimeoutListener = new DownloadApkTimeOutCheckHandler.DownloadApkTimeOutCheckHandlerListener() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateDownloadFileTask.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.update.DownloadApkTimeOutCheckHandler.DownloadApkTimeOutCheckHandlerListener
        public void onFileSizeNoChanedTimeOut() {
        }

        @Override // com.samsung.android.app.twatchmanager.update.DownloadApkTimeOutCheckHandler.DownloadApkTimeOutCheckHandlerListener
        public void onFullTimeOut() {
            Log.d(UpdateDownloadFileTask.TAG, "onFullTimeOut() starts...");
            UpdateDownloadFileTask.this.releaseAfterDownload();
        }

        @Override // com.samsung.android.app.twatchmanager.update.DownloadApkTimeOutCheckHandler.DownloadApkTimeOutCheckHandlerListener
        public void onRequestFileSize() {
        }
    };
    private HashMap<String, StubAPIHelper.XMLResult> mToDownloadPkgMap = new HashMap<>();
    private int mTotalContentSize;
    HttpURLConnection mUrlConnection;
    private long sizeDownloaded = 0;
    private int tempFileCount = 1;

    public interface IUpdateDownloadFileCallback {
        void onFailToUpdateDownload(UpdateDownloadManager.ErrorCode errorCode);

        void onStartUpdateDownload(int i);

        void onSuccessToUpdateDownload(Map<String, String> map);

        void onUpdateDownloading(int i, int i2);
    }

    public UpdateDownloadFileTask(IUpdateDownloadFileCallback iUpdateDownloadFileCallback, HashMap<String, StubAPIHelper.XMLResult> hashMap, int i) {
        this.mDownloadFileCallback = iUpdateDownloadFileCallback;
        this.mDownloadApkTimeOutCheckHandler = new DownloadApkTimeOutCheckHandler();
        this.mToDownloadPkgMap = hashMap;
        this.mTotalContentSize = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x010e A[Catch:{ all -> 0x0104 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean downloadFile(java.lang.String r13, java.lang.String r14) {
        /*
        // Method dump skipped, instructions count: 305
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.UpdateDownloadFileTask.downloadFile(java.lang.String, java.lang.String):boolean");
    }

    private String getFileName(String str) {
        String str2 = TAG;
        Log.d(str2, "getFileName(" + str + ")");
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf >= 0) {
            return str.substring(lastIndexOf + 1) + ".apk";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("NewApk_");
        int i = this.tempFileCount;
        this.tempFileCount = i + 1;
        sb.append(i);
        sb.append(".apk");
        return sb.toString();
    }

    private boolean isDownloadPathValid() {
        File file = new File(this.mDownloadPath);
        if (file.exists() || file.mkdirs()) {
            return true;
        }
        String str = TAG;
        Log.d(str, "Unable to create directory: " + file.getAbsolutePath());
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void releaseAfterDownload() {
        try {
            this.mFout.flush();
            this.mFout.close();
            this.mFout = null;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.mIn.close();
            this.mIn = null;
        } catch (IOException | NullPointerException e2) {
            e2.printStackTrace();
        }
        HttpURLConnection httpURLConnection = this.mUrlConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            this.mUrlConnection = null;
        }
    }

    private boolean startDownload() {
        this.mDownloadedPkgMap.clear();
        this.sizeDownloaded = 0;
        boolean z = false;
        if (this.mToDownloadPkgMap.size() > 0 && isDownloadPathValid()) {
            this.mDownloadApkTimeOutCheckHandler.start(this.mTimeoutListener);
            Iterator<String> it = this.mToDownloadPkgMap.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                String next = it.next();
                String str = TAG;
                Log.d(str, "startDownload() calling download file for package " + next);
                StubAPIHelper.XMLResult xMLResult = this.mToDownloadPkgMap.get(next);
                if (!downloadFile(next, xMLResult.get(StubAPIHelper.XMLResultKey.DOWNLOAD_URI))) {
                    String str2 = TAG;
                    Log.d(str2, "startDownload() failed to download package " + next);
                    break;
                }
                this.mDownloadedPkgMap.put(next, xMLResult.get(StubAPIHelper.XMLResultKey.SIGNATURE));
            }
            this.mDownloadApkTimeOutCheckHandler.stop();
        }
        String str3 = TAG;
        Log.d(str3, "startDownload() return.." + z);
        return z;
    }

    /* access modifiers changed from: protected */
    public Boolean doInBackground(Void... voidArr) {
        this.mDownloadPath = UpdateUtil.getPathToDownload(TWatchManagerApplication.getAppContext());
        this.mDataNetworkAvailable = HostManagerUtilsNetwork.isDataNetworkConnected(TWatchManagerApplication.getAppContext());
        HashMap<String, StubAPIHelper.XMLResult> hashMap = this.mToDownloadPkgMap;
        boolean z = false;
        this.mPackageSetValid = hashMap != null && !hashMap.isEmpty();
        this.mHasEnoughStorage = HostManagerUtils.hasEnoughStorage(TWatchManagerApplication.getAppContext(), (long) this.mTotalContentSize);
        String str = TAG;
        Log.d(str, "initialCheck() starts.. mDataNetworkAvailable : " + this.mDataNetworkAvailable + " mPackageSetValid : " + this.mPackageSetValid + " mHasEnoughStorage : " + this.mHasEnoughStorage);
        if (this.mDataNetworkAvailable && this.mPackageSetValid && this.mHasEnoughStorage) {
            z = startDownload();
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Boolean bool) {
        if (bool.booleanValue()) {
            this.mDownloadFileCallback.onSuccessToUpdateDownload(this.mDownloadedPkgMap);
            return;
        }
        UpdateDownloadManager.ErrorCode errorCode = UpdateDownloadManager.ErrorCode.DOWNLOAD_FAILED_BY_TIMEOUT;
        if (!this.mDataNetworkAvailable) {
            errorCode = UpdateDownloadManager.ErrorCode.DOWNLOAD_FAILED_BY_NO_NETWORK;
        } else if (!this.mPackageSetValid) {
            errorCode = UpdateDownloadManager.ErrorCode.DOWNLOAD_URL_INVALID;
        } else if (!this.mHasEnoughStorage) {
            errorCode = UpdateDownloadManager.ErrorCode.DOWNLOAD_FAILED_BY_LACK_STORAGE;
        }
        this.mDownloadFileCallback.onFailToUpdateDownload(errorCode);
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        this.mDownloadFileCallback.onStartUpdateDownload(this.mTotalContentSize);
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(Integer... numArr) {
        this.mDownloadFileCallback.onUpdateDownloading(numArr[0].intValue(), numArr[1].intValue());
    }
}
