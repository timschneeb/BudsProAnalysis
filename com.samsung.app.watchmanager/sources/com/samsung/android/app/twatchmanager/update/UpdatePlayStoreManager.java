package com.samsung.android.app.twatchmanager.update;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import b.j.a.b;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

public class UpdatePlayStoreManager {
    public static final String TAG = ("tUHM:[Update]" + UpdatePlayStoreManager.class.getSimpleName());
    private IPlayStoreUpdateCallback mCallback;
    private int mCurrent;
    private String mCurrentBTAddress;
    private String mCurrentPackageName;
    private int mCurrentVersionCode;
    private boolean mIsTUHMRemain;
    private BroadcastReceiver mPackageInstallReceiver;
    private Queue<String> mPackageQueue = new ArrayDeque();
    private int mTotal;

    public interface IPlayStoreUpdateCallback {
        void onFinishUpdate(boolean z, String str);
    }

    public UpdatePlayStoreManager(Set<String> set, String str, IPlayStoreUpdateCallback iPlayStoreUpdateCallback) {
        boolean z = false;
        this.mIsTUHMRemain = false;
        this.mCurrent = 0;
        this.mTotal = 0;
        this.mPackageInstallReceiver = new BroadcastReceiver() {
            /* class com.samsung.android.app.twatchmanager.update.UpdatePlayStoreManager.AnonymousClass1 */

            public void onReceive(Context context, Intent intent) {
                UpdatePlayStoreManager.this.checkAfterPlayStoreLaunched(context);
            }
        };
        if (set != null) {
            this.mTotal = set.size();
            this.mIsTUHMRemain = set.contains("com.samsung.android.app.watchmanager");
            set.remove("com.samsung.android.app.watchmanager");
            for (String str2 : set) {
                if (GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY.equals(str2)) {
                    z = true;
                } else {
                    this.mPackageQueue.offer(str2);
                }
            }
            if (z) {
                this.mPackageQueue.offer(GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY);
            }
        }
        this.mCallback = iPlayStoreUpdateCallback;
        this.mCurrentBTAddress = str;
    }

    private void registerResponseReciever(Context context) {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(UpdateInstallActivity.ACTION_CALL_PLAYSTORE);
            b.a(context).a(this.mPackageInstallReceiver, intentFilter);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void startPlayStoreInstallActivity(Context context) {
        UpdateInstallData.setNonSamsungInstallRequested(false);
        this.mCurrentVersionCode = HostManagerUtils.getVersionCode(context, this.mCurrentPackageName);
        String str = TAG;
        Log.d(str, "startPlayStoreInstallActivity() mCurrentPackageName:" + this.mCurrentPackageName + " mCurrentVersionCode : " + this.mCurrentVersionCode);
        Intent intent = new Intent(UpdateInstallActivity.ACTION_CALL_PLAYSTORE);
        intent.putExtra("package_name", this.mCurrentPackageName);
        intent.setClass(context, UpdateInstallActivity.class);
        intent.setFlags(268435456);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean tryToInstallTuhmViaPlayStore(Context context) {
        boolean z = false;
        if (this.mIsTUHMRemain) {
            this.mIsTUHMRemain = false;
            this.mCurrentPackageName = "com.samsung.android.app.watchmanager";
            startPlayStoreInstallActivity(context);
            z = true;
        }
        String str = TAG;
        Log.d(str, "tryToInstallTuhmViaPlayStore() isRequested : " + z);
        return z;
    }

    public void checkAfterPlayStoreLaunched(Context context) {
        int versionCode = HostManagerUtils.getVersionCode(context, this.mCurrentPackageName);
        String str = TAG;
        Log.d(str, "onReceive() mCurrentVersionCode : " + this.mCurrentVersionCode + " newVersionCode : " + versionCode);
        boolean z = versionCode > this.mCurrentVersionCode;
        if (z) {
            RegistryAppsDBManager.updateAppUpdateCancelCount(this.mCurrentPackageName, 0, context);
        }
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onReceive() mCurrentPackageName : ");
        sb.append(this.mCurrentPackageName);
        sb.append(" (");
        int i = this.mCurrent + 1;
        this.mCurrent = i;
        sb.append(i);
        sb.append("/");
        sb.append(this.mTotal);
        sb.append(")");
        Log.d(str2, sb.toString());
        if (this.mCurrent < this.mTotal) {
            this.mCurrentPackageName = this.mPackageQueue.poll();
            if (!TextUtils.isEmpty(this.mCurrentPackageName)) {
                startPlayStoreInstallActivity(context);
                return;
            } else if (!tryToInstallTuhmViaPlayStore(context)) {
                this.mCallback.onFinishUpdate(false, this.mCurrentBTAddress);
            } else {
                return;
            }
        } else {
            this.mCallback.onFinishUpdate(z, this.mCurrentBTAddress);
        }
        release(context);
    }

    public void release(Context context) {
        Log.d(TAG, "release() starts..");
        try {
            b.a(context).a(this.mPackageInstallReceiver);
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void startUpdateViaPlayStore(Context context) {
        Log.d(TAG, "startUpdateViaPlayStore() starts...");
        this.mCurrentPackageName = this.mPackageQueue.poll();
        this.mCurrent = 0;
        if (!TextUtils.isEmpty(this.mCurrentPackageName)) {
            registerResponseReciever(context);
            startPlayStoreInstallActivity(context);
            return;
        }
        registerResponseReciever(context);
        if (!tryToInstallTuhmViaPlayStore(context)) {
            Log.d(TAG, "startUpdateViaPlayStore() unexpected situation ... there is no package to install");
            this.mCallback.onFinishUpdate(false, this.mCurrentBTAddress);
            release(context);
        }
    }
}
