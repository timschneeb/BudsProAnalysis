package com.samsung.android.app.watchmanager.setupwizard;

import android.content.Context;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.util.BNRAsyncTask;
import com.samsung.android.app.twatchmanager.util.CleanupAsyncTask;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.UninstallManager;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PluginChangeManager {
    public static final String TAG = ("tUHM:[Conn]" + PluginChangeManager.class.getSimpleName());
    private IPluginChangeCallback mCallback;
    private GearInfo mGearInfoToConnect;
    private Set<String> mLastAllPackageSet;
    private List<String> mPackagesToUninstall;

    public interface IPluginChangeCallback {
        void onFinished();
    }

    public PluginChangeManager(IPluginChangeCallback iPluginChangeCallback) {
        this.mCallback = iPluginChangeCallback;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private List<String> getUninstallPackageList() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"com.samsung.accessory.goproviders", "com.samsung.accessory.saproviders", "com.sec.android.fotaprovider"};
        for (String str : strArr) {
            if (this.mLastAllPackageSet.contains(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isUnInstallCheckRequired() {
        HashSet hashSet = new HashSet();
        hashSet.add(GlobalConst.CONTAINER_PACKAGE_NAME_GEAR2S);
        hashSet.add(GlobalConst.CONTAINER_PACKAGE_NAME_GEAR1);
        return (this.mLastAllPackageSet.contains(GlobalConst.CONTAINER_PACKAGE_NAME_GEAR2S) || this.mLastAllPackageSet.contains(GlobalConst.CONTAINER_PACKAGE_NAME_GEAR1)) && !hashSet.contains(this.mGearInfoToConnect.getContainerPackageName());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void uninstallPackages(final UninstallManager.UninstallationListener uninstallationListener) {
        new BNRAsyncTask(this.mPackagesToUninstall, new BNRAsyncTask.IBNRTaskCallback() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginChangeManager.AnonymousClass2 */

            @Override // com.samsung.android.app.twatchmanager.util.BNRAsyncTask.IBNRTaskCallback
            public void onFinished() {
                new UninstallManager(PluginChangeManager.this.mPackagesToUninstall, uninstallationListener).start();
            }
        }).execute(new Void[0]);
    }

    public void enableCurrentAllRelatedPackages() {
        String containerPackageName = this.mGearInfoToConnect.getContainerPackageName();
        String str = this.mGearInfoToConnect.deviceName;
        String str2 = TAG;
        Log.d(str2, " enableAllRelatedPackages() containerPackage: " + containerPackageName + " deviceName:" + str);
        ArrayList<String> packageInstallerInfo = InstallationUtils.getPackageInstallerInfo(TWatchManagerApplication.getAppContext(), containerPackageName, str);
        String str3 = this.mGearInfoToConnect.pluginPackage;
        if (packageInstallerInfo != null) {
            Iterator<String> it = packageInstallerInfo.iterator();
            while (it.hasNext()) {
                String next = it.next();
                String str4 = TAG;
                Log.d(str4, "enableAllRelatedPackages() provider : " + next);
                if (!next.equals(str3)) {
                    HostManagerUtils.enableApplication(TWatchManagerApplication.getAppContext(), next);
                }
            }
        }
        if (!TextUtils.isEmpty(str3)) {
            HostManagerUtils.enableApplication(TWatchManagerApplication.getAppContext(), str3);
        }
        if (!TextUtils.isEmpty(containerPackageName) && !TextUtils.equals(containerPackageName, str3)) {
            HostManagerUtils.enableApplication(TWatchManagerApplication.getAppContext(), containerPackageName);
        }
    }

    public void init(GearInfo gearInfo) {
        GearInfo gearInfo2;
        this.mGearInfoToConnect = gearInfo;
        this.mLastAllPackageSet = new HashSet();
        this.mPackagesToUninstall = new ArrayList();
        for (DeviceRegistryData deviceRegistryData : new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(TWatchManagerApplication.getAppContext())) {
            String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(deviceRegistryData.deviceFixedName);
            if (!TextUtils.equals(this.mGearInfoToConnect.deviceName, simpleBTNameByName) && (gearInfo2 = GearRulesManager.getInstance().getGearInfo(simpleBTNameByName)) != null && !gearInfo2.supportMultiConnection) {
                String containerPackageName = gearInfo2.getContainerPackageName();
                ArrayList<String> packageInstallerInfo = InstallationUtils.getPackageInstallerInfo(TWatchManagerApplication.getAppContext(), containerPackageName, simpleBTNameByName);
                if (packageInstallerInfo != null) {
                    this.mLastAllPackageSet.addAll(packageInstallerInfo);
                }
                this.mLastAllPackageSet.add(containerPackageName);
            }
        }
        String str = TAG;
        Log.d(str, "initGearInfoAfterRulesSync() mLastAllPackageSet : " + this.mLastAllPackageSet);
    }

    public void startPluginChangeProcess() {
        UpdateUtil.sendBackupLogIntent();
        Context appContext = TWatchManagerApplication.getAppContext();
        AnonymousClass1 r2 = new CleanupAsyncTask.ICleanupTaskCallback() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginChangeManager.AnonymousClass1 */

            @Override // com.samsung.android.app.twatchmanager.util.CleanupAsyncTask.ICleanupTaskCallback
            public void onFinished() {
                if (PluginChangeManager.this.isUnInstallCheckRequired()) {
                    PluginChangeManager pluginChangeManager = PluginChangeManager.this;
                    pluginChangeManager.mPackagesToUninstall = pluginChangeManager.getUninstallPackageList();
                }
                String str = PluginChangeManager.TAG;
                Log.d(str, "IPluginChangeTaskCallback.onFinished() starts... mPackagesToUninstall : " + PluginChangeManager.this.mPackagesToUninstall);
                if (!PluginChangeManager.this.mPackagesToUninstall.isEmpty()) {
                    PluginChangeManager.this.uninstallPackages(new UninstallManager.UninstallationListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.PluginChangeManager.AnonymousClass1.AnonymousClass1 */

                        @Override // com.samsung.android.app.twatchmanager.util.UninstallManager.UninstallationListener
                        public void onFinished() {
                            PluginChangeManager.this.enableCurrentAllRelatedPackages();
                            PluginChangeManager.this.mCallback.onFinished();
                            Log.d(PluginChangeManager.TAG, "UninstallationListener.onFinished() ends... plugin disable/uninstall/enable work is done...");
                        }
                    });
                    return;
                }
                PluginChangeManager.this.enableCurrentAllRelatedPackages();
                PluginChangeManager.this.mCallback.onFinished();
                Log.d(PluginChangeManager.TAG, "IPluginChangeTaskCallback.onFinished() starts... plugin disable/enable work is done...");
            }
        };
        GearInfo gearInfo = this.mGearInfoToConnect;
        new CleanupAsyncTask(appContext, r2, gearInfo.deviceName, gearInfo.getContainerPackageName()).execute(new Object[0]);
    }
}
