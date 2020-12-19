package com.samsung.android.app.twatchmanager.update;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.update.BaseUpdateTask;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import d.a.a.b;
import d.a.a.d.a;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UpdateManager {
    private static final String TAG = ("tUHM:[Update]" + UpdateManager.class.getSimpleName());
    public static final int UPDATE_CHECK_TIMEOUT_BACKGROUND = 60000;
    public static final int UPDATE_CHECK_TIMEOUT_FOREGRUND = 10000;
    public static final int UPDATE_CHECK_TIMEOUT_PER_REQUESET = 2000;
    private String mBtAddress = null;
    private Set<String> mCheckablePackageSet;
    private boolean mIsBackground;
    private String mPluginPackageName = null;
    private String mSimpleDeviceName = null;
    private Handler mTimeoutHandler = new Handler();
    private BaseUpdateTask.IUpdateTaskCallback mUpdateCheckCallback = new BaseUpdateTask.IUpdateTaskCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateManager.AnonymousClass3 */

        @Override // com.samsung.android.app.twatchmanager.update.BaseUpdateTask.IUpdateTaskCallback
        public void onResult(HashMap<String, StubAPIHelper.XMLResult> hashMap, int i) {
            Context appContext = TWatchManagerApplication.getAppContext();
            UpdateManager.this.releaseTimeoutHandler();
            UpdateManager.this.mCheckablePackageSet.removeAll(hashMap.keySet());
            int size = hashMap.size();
            String str = UpdateManager.TAG;
            Log.d(str, "mUpdateCheckCallback.onResult() Update Available for packages " + size);
            if (size > 0) {
                SharedPreferences.Editor edit = appContext.getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0).edit();
                edit.putStringSet(GlobalConst.PACKAGE_LIST, hashMap.keySet());
                edit.commit();
                UpdateManager updateManager = UpdateManager.this;
                updateManager.mUpdateManagerCallback.onUpdateAvailable(i, updateManager.mBtAddress);
                return;
            }
            UpdateUtil.clearUpdateCheckPref(appContext);
            UpdateManager.this.mUpdateManagerCallback.onUpdateUnAvailable();
        }
    };
    private BaseUpdateTask mUpdateCheckerThread;
    protected IUpdateManagerCallback mUpdateManagerCallback;

    public interface IUpdateManagerCallback {
        void onUpdateAvailable(int i, String str);

        void onUpdateCheckTimeOut(String str);

        void onUpdateUnAvailable();
    }

    public UpdateManager(IUpdateManagerCallback iUpdateManagerCallback, boolean z) {
        this.mUpdateManagerCallback = iUpdateManagerCallback;
        this.mIsBackground = z;
        List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(TWatchManagerApplication.getAppContext());
        if (!queryLastLaunchDeviceRegistryData.isEmpty()) {
            this.mBtAddress = queryLastLaunchDeviceRegistryData.get(0).deviceBtID;
            this.mPluginPackageName = queryLastLaunchDeviceRegistryData.get(0).packagename;
            this.mSimpleDeviceName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(queryLastLaunchDeviceRegistryData.get(0).deviceFixedName);
        }
    }

    public static boolean checkPluginUpdateRemain() {
        return TWatchManagerApplication.getAppContext().getSharedPreferences(GlobalConst.XML_MULTIPLE_UPDATE, 0).getBoolean("update_plugin_remain", false);
    }

    private static boolean checkTimeToUpdate() {
        boolean z;
        Context appContext = TWatchManagerApplication.getAppContext();
        Log.d(TAG, "checkTimeToUpdate() starts... check update time finally ... ");
        b previousUpdateCheckTime = UpdateUtil.getPreviousUpdateCheckTime(appContext);
        boolean z2 = true;
        if (previousUpdateCheckTime != null) {
            int thresholdTime = UpdateUtil.getThresholdTime();
            d.a.a.d.b a2 = a.a("yyyy-MM-dd HH:mm:ss");
            b b2 = previousUpdateCheckTime.b(thresholdTime);
            z = b2.c();
            String str = TAG;
            Log.d(str, "isUpdateCheckTime() previouslyUpdatedAt : " + previousUpdateCheckTime + ", nextUpdateCheckTime : " + b2.a(a2));
        } else {
            Log.d(TAG, "isUpdateCheckTime() previouslyUpdatedAt is null, initial case ... will return true");
            z = true;
        }
        if (!UpdateUtil.isTUHMUpdated(appContext)) {
            z2 = z;
        }
        if (z2) {
            UpdateUtil.updatePreviousTime(appContext);
        }
        String str2 = TAG;
        Log.d(str2, "checkTimeToUpdate() isUpdateCheckTime : " + z2);
        return z2;
    }

    private static boolean checkUpdateNeeded(boolean z, Intent intent) {
        boolean z2 = false;
        boolean booleanExtra = intent.getBooleanExtra(UpdateNotificationManager.EXTRA_UPDATE_NOTIFICATION, false);
        boolean isUpdateNotified = UpdateUtil.isUpdateNotified(TWatchManagerApplication.getAppContext());
        if (isUpdateNotified) {
            UpdateUtil.setUpdateNotified(TWatchManagerApplication.getAppContext(), false);
        }
        String str = TAG;
        Log.d(str, "checkUpdateNeeded() isFromUpdateNotification = " + booleanExtra + " isUpdateNotified : " + isUpdateNotified + " isBackgroundCheck : " + z);
        if (!InstallationUtils.isLocalInstallation()) {
            if (z || booleanExtra || isUpdateNotified || UpdateUtil.isForceUpdateNeeded()) {
                z2 = true;
            }
            if (!z2) {
                z2 = checkTimeToUpdate();
            }
        }
        String str2 = TAG;
        Log.d(str2, "checkUpdateNeeded() updateNeeded ? : " + z2);
        return z2;
    }

    public static void clearPluginUpdateRemainPref() {
        SharedPreferences.Editor edit = TWatchManagerApplication.getAppContext().getSharedPreferences(GlobalConst.XML_MULTIPLE_UPDATE, 0).edit();
        edit.remove("update_plugin_remain");
        edit.remove("plugin_downloaded_set");
        edit.apply();
    }

    public static Set<String> getPluginUpdateRemainSet() {
        return TWatchManagerApplication.getAppContext().getSharedPreferences(GlobalConst.XML_MULTIPLE_UPDATE, 0).getStringSet("plugin_downloaded_set", new HashSet());
    }

    public static boolean isUpdateCheckAvailable(boolean z, Intent intent) {
        boolean isDataNetworkConnected = HostManagerUtilsNetwork.isDataNetworkConnected(TWatchManagerApplication.getAppContext());
        List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(TWatchManagerApplication.getAppContext());
        String str = TAG;
        Log.d(str, "isUpdateCheckAvailable() isNetworkAvailable : " + isDataNetworkConnected + " lastLaunchedPlugin size : " + queryLastLaunchDeviceRegistryData.size());
        if (isDataNetworkConnected) {
            return (!queryLastLaunchDeviceRegistryData.isEmpty() || !z) && checkUpdateNeeded(z, intent);
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void releaseTimeoutHandler() {
        Handler handler = this.mTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void runUpdateCheckThreadAfterRulesSync() {
        GearRulesManager.getInstance().syncGearInfo(new GearRulesManager.ISyncCallback() {
            /* class com.samsung.android.app.twatchmanager.update.UpdateManager.AnonymousClass2 */

            @Override // com.samsung.android.app.twatchmanager.manager.GearRulesManager.ISyncCallback
            public void onSyncComplete(boolean z) {
                String str = UpdateManager.TAG;
                Log.d(str, "onSyncComplete() isSuccess : " + z);
                if (z) {
                    Log.d(UpdateManager.TAG, "Rule file read successful.");
                    UpdateManager.this.mCheckablePackageSet = new HashSet();
                    UpdateManager.this.mCheckablePackageSet.add("com.samsung.android.app.watchmanager");
                    for (DeviceRegistryData deviceRegistryData : new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(TWatchManagerApplication.getAppContext())) {
                        GearInfo gearInfo = GearRulesManager.getInstance().getGearInfo(HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(deviceRegistryData.deviceFixedName));
                        if (gearInfo != null) {
                            UpdateManager.this.mCheckablePackageSet.addAll(new UpdatePackageSet(gearInfo).get());
                        }
                    }
                    String str2 = UpdateManager.TAG;
                    Log.d(str2, "onSyncComplete() mCheckablePackageSet : " + UpdateManager.this.mCheckablePackageSet);
                    if (!UpdateManager.this.mCheckablePackageSet.isEmpty()) {
                        UpdateManager updateManager = UpdateManager.this;
                        updateManager.startUpdateCheckTimer(updateManager.mCheckablePackageSet.size());
                        UpdateManager updateManager2 = UpdateManager.this;
                        updateManager2.mUpdateCheckerThread = new UpdateCheckTask(updateManager2.mCheckablePackageSet, UpdateManager.this.mUpdateCheckCallback);
                        UpdateManager.this.mUpdateCheckerThread.execute(new Void[0]);
                        return;
                    }
                    return;
                }
                UpdateManager.this.mUpdateManagerCallback.onUpdateUnAvailable();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startUpdateCheckTimer(int i) {
        int i2 = this.mIsBackground ? 60000 : 10000;
        int i3 = i * UPDATE_CHECK_TIMEOUT_PER_REQUESET;
        String str = TAG;
        Log.d(str, "startUpdateCheckTimer() starts...baseWaitTime : " + i2 + " maxWaitTime : " + i3);
        if (i2 >= i3) {
            i3 = i2;
        }
        this.mTimeoutHandler.postDelayed(new Runnable() {
            /* class com.samsung.android.app.twatchmanager.update.UpdateManager.AnonymousClass1 */

            public void run() {
                if (UpdateManager.this.mUpdateCheckerThread != null) {
                    UpdateManager.this.mUpdateCheckerThread.cancel(true);
                }
                if (UpdateManager.this.mUpdateManagerCallback != null) {
                    Log.d(UpdateManager.TAG, "mTimeoutHandler.run() time-out...");
                    UpdateManager updateManager = UpdateManager.this;
                    updateManager.mUpdateManagerCallback.onUpdateCheckTimeOut(updateManager.mBtAddress);
                }
            }
        }, (long) i3);
    }

    public void startUpdateChecking() {
        Log.d(TAG, "startUpdateChecking() starts..");
        runUpdateCheckThreadAfterRulesSync();
    }
}
