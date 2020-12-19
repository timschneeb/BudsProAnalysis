package com.samsung.android.app.twatchmanager.manager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import java.io.File;
import java.util.List;

public class InstallThroughIntent implements InstallationCallback {
    private static final String AUTHORITY = "com.samsung.android.app.watchmanager.fileprovider";
    private static final String TAG = ("tUHM:" + InstallThroughIntent.class.getSimpleName());
    private static boolean installInProgress = false;
    private List<InstallPack> list;
    private int mCompleted;
    private Activity mContext;
    private int mCounter;
    private Handler mHandler;
    private int mVersionCode = 0;
    private boolean update = false;

    public InstallThroughIntent(Activity activity, List<InstallPack> list2, boolean z) {
        this.mCounter = list2.size();
        this.list = list2;
        this.mContext = activity;
        Activity activity2 = this.mContext;
        if (activity2 instanceof SetupWizardWelcomeActivity) {
            ((SetupWizardWelcomeActivity) activity2).setInstallationCallback(this);
        }
        this.update = z;
    }

    public static boolean isInstallationInProgress() {
        return installInProgress;
    }

    public void makeAndThrowIntent(InstallPack installPack) {
        String str = installPack.path;
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "Path is NULL, Something fishy!!!");
        }
        InstallationUtils.changeFilePermission(str, InstallationUtils.PERMISSIONS_GLOBAL);
        if (this.update) {
            this.mVersionCode = InstallationUtils.getVersionCode(installPack.packName);
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT < 24) {
            intent.setDataAndType(Uri.fromFile(new File(str)), "application/vnd.android.package-archive");
        } else {
            Log.d(TAG, "Android N install via file provider");
            intent.setDataAndType(FileProvider.a(this.mContext, AUTHORITY, new File(str)), "application/vnd.android.package-archive");
            intent.setFlags(1);
        }
        this.mContext.startActivityForResult(intent, 17);
        installInProgress = true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x003f, code lost:
        if (r6.mVersionCode != r1) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x004b, code lost:
        if (com.samsung.android.app.twatchmanager.util.HostManagerUtils.isExistPackage(r6.mContext, r0.packName) != false) goto L_0x0041;
     */
    @Override // com.samsung.android.app.twatchmanager.manager.InstallationCallback
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onInstallationResult(int r7) {
        /*
        // Method dump skipped, instructions count: 267
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.manager.InstallThroughIntent.onInstallationResult(int):void");
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public void start() {
        this.mCompleted = 0;
        if (this.mCounter > 0) {
            String str = TAG;
            Log.d(str, "start NS installation , path" + this.list.get(this.mCompleted).path);
            makeAndThrowIntent(this.list.get(this.mCompleted));
            return;
        }
        this.mHandler.sendEmptyMessage(InstallationUtils.INSTALLATION_COMPLETE);
    }
}
