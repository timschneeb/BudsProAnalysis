package com.samsung.android.app.twatchmanager.update;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.update.UpdateDownloadManager;
import com.samsung.android.app.twatchmanager.update.UpdateInstallManager;
import com.samsung.android.app.twatchmanager.update.UpdateManager;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UpdateCheckingReceiver extends BroadcastReceiver {
    private static final String TAG = ("tUHM:[Update]" + UpdateCheckingReceiver.class.getSimpleName());
    private String mBtAddress;
    private Context mContext;
    private UpdateDownloadManager mDownloadManager;
    private UpdateDownloadManager.IDownloadManagerCallback mDownloadManagerCallback = new UpdateDownloadManager.IDownloadManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateCheckingReceiver.AnonymousClass2 */
        private double mTotalSizeInMB = 0.0d;

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onDownloadAvailable(HashMap<String, StubAPIHelper.XMLResult> hashMap, int i) {
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onDownloading(int i, double d2) {
            String format = String.format("(%.1f MB / %.1f MB)", Double.valueOf(d2), Double.valueOf(this.mTotalSizeInMB));
            if (i % 20 == 0) {
                String str = UpdateCheckingReceiver.TAG;
                Log.d(str, String.format("onDownloading() percent : %d " + format, Integer.valueOf(i)));
            }
            UpdateNotificationManager.getInstance().showBackgroundInstallNotification("downloading...", i, false, true);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onFailToDownload(UpdateDownloadManager.ErrorCode errorCode) {
            Log.d(UpdateCheckingReceiver.TAG, "onFailToDownload() starts...");
            UpdateCheckingReceiver.this.mDownloadManager.clearResources();
            UpdateCheckingReceiver.this.endOfBackgroundUpdate(UpdateTimer.ACTION_BACKGROUND_UPDATE);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onFinishDownload(Map<String, String> map) {
            Log.d(UpdateCheckingReceiver.TAG, "onEndDownload() start to install...");
            UpdateNotificationManager.getInstance().showBackgroundInstallNotification("Installing...", 100, true, false);
            UpdateCheckingReceiver updateCheckingReceiver = UpdateCheckingReceiver.this;
            updateCheckingReceiver.mInstallManager = new UpdateInstallManager(updateCheckingReceiver.mInstallManagerCallback, UpdateCheckingReceiver.this.mBtAddress, map, false);
            UpdateCheckingReceiver.this.mInstallManager.pluginInstallProcess();
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onStartDownload(double d2) {
            this.mTotalSizeInMB = d2;
        }
    };
    private UpdateInstallManager mInstallManager;
    private UpdateInstallManager.IInstallManagerCallback mInstallManagerCallback = new UpdateInstallManager.IInstallManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateCheckingReceiver.AnonymousClass3 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onDisconnectBeforePluginInstall(String str) {
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onEndOfInstall() {
            String str = UpdateCheckingReceiver.TAG;
            Log.d(str, "onEndOfInstall() starts.. mInstallManager : " + UpdateCheckingReceiver.this.mInstallManager);
            UpdateNotificationManager.getInstance().showBackgroundInstallNotification("Succeeded to install...", -1, false, false);
            UpdateCheckingReceiver.this.endOfBackgroundUpdate(UpdateTimer.ACTION_BACKGROUND_UPDATE);
            UpdateUtil.sendUpdateCompleteBroadcast(UpdateCheckingReceiver.this.mBtAddress);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onFailToInstall(int i, String str) {
            String str2 = UpdateCheckingReceiver.TAG;
            Log.d(str2, "onFailToInstall() starts.. reason : " + i + " packageName : " + str);
            UpdateCheckingReceiver.this.endOfBackgroundUpdate(UpdateTimer.ACTION_BACKGROUND_UPDATE);
            UpdateUtil.sendUpdateCompleteBroadcast(UpdateCheckingReceiver.this.mBtAddress);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onInstallUHM() {
            UpdateCheckingReceiver.this.endOfBackgroundUpdate(UpdateTimer.ACTION_AFTER_TUHM_INSTALL_REQUEST);
            UpdateUtil.sendUpdateCompleteBroadcast(UpdateCheckingReceiver.this.mBtAddress);
            if (UpdateCheckingReceiver.this.mInstallManager != null) {
                UpdateCheckingReceiver.this.mInstallManager.installTUHMPackage();
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onStartInstall() {
        }
    };
    private UpdateManager mUpdateManager;
    private UpdateManager.IUpdateManagerCallback mUpdateManagerCallback = new UpdateManager.IUpdateManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateCheckingReceiver.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateManager.IUpdateManagerCallback
        public void onUpdateAvailable(int i, String str) {
            String str2 = UpdateCheckingReceiver.TAG;
            Log.d(str2, "UpdateCheckingReceiver.onUpdateAvailable() totalUpdateAppSize: " + i + " btAddress : " + str);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateManager.IUpdateManagerCallback
        public void onUpdateCheckTimeOut(String str) {
            UpdateCheckingReceiver.this.endOfBackgroundUpdate(UpdateTimer.ACTION_BACKGROUND_UPDATE);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateManager.IUpdateManagerCallback
        public void onUpdateUnAvailable() {
            UpdateCheckingReceiver.this.endOfBackgroundUpdate(UpdateTimer.ACTION_BACKGROUND_UPDATE);
        }
    };

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void endOfBackgroundUpdate(String str) {
        String str2 = TAG;
        Log.d(str2, "endOfBackgroundUpdate() starts... action : " + str);
        Log.saveLog();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0085 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isPossibleToUpdateCheck(java.lang.String r7) {
        /*
        // Method dump skipped, instructions count: 137
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.UpdateCheckingReceiver.isPossibleToUpdateCheck(java.lang.String):boolean");
    }

    private boolean isTopLaunched(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
        boolean z = false;
        if (runningTasks != null && runningTasks.size() > 0) {
            ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
            String str = TAG;
            Log.d(str, "isTopLaunched topActivity [" + runningTaskInfo.topActivity.getClassName() + "]");
            z = runningTaskInfo.topActivity.getPackageName().equals(context.getPackageName());
        }
        String str2 = TAG;
        Log.d(str2, "isTopLaunched [" + z + "]");
        return z;
    }

    private void notifyPlugin() {
        Context appContext = TWatchManagerApplication.getAppContext();
        Set<String> appsUpdateList = UpdateUtil.getAppsUpdateList(appContext);
        String[] strArr = appsUpdateList.isEmpty() ? new String[0] : (String[]) appsUpdateList.toArray(new String[appsUpdateList.size()]);
        Intent intent = new Intent(GlobalConst.ACTION_UPDATE_AVAILABLE);
        intent.putExtra(GlobalConst.EXTRA_UPDATE_AVAILABLE, strArr);
        appContext.sendBroadcast(intent, "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER");
        Log.d(TAG, "notifyPlugin done");
    }

    private void updateFound() {
        UpdateNotificationManager.getInstance().showNotification();
        UpdateUtil.setUpdateNotified(TWatchManagerApplication.getAppContext(), true);
        notifyPlugin();
    }

    public void onReceive(Context context, Intent intent) {
    }
}
