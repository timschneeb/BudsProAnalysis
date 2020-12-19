package com.samsung.android.app.twatchmanager.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import b.j.a.b;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import com.samsung.android.app.twatchmanager.receiver.BackupCompleteReceiver;
import com.samsung.android.app.twatchmanager.update.UpdateInstaller;
import com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager;
import com.samsung.android.app.twatchmanager.util.CertificateChecker;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateInstallManager {
    public static final String TAG = ("tUHM:[Update][Conn]" + UpdateInstallManager.class.getSimpleName());
    private String mBtAddress;
    private String mContainerPackageName;
    private String mDeviceName;
    private Handler mDisconnectCompleteListener = new Handler() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateInstallManager.AnonymousClass3 */

        public void handleMessage(Message message) {
            String str = UpdateInstallManager.TAG;
            Log.d(str, "mDisconnectCompleteListener.handleMessage() starts.. msg : " + message.what);
            BackupCompleteReceiver.unregisterReceiver(TWatchManagerApplication.getAppContext());
            UpdateInstallManager.this.mInstallManagerCallback.onStartInstall();
            UpdateInstallManager.this.installPluginPackages();
        }
    };
    private Map<String, String> mDownloadedPluginSignatureMap;
    private IInstallManagerCallback mInstallManagerCallback;
    private String mInstallPath;
    private UpdateInstaller mInstaller;
    private IntentInstallBroadcastReceiver mIntentInstallReceiver = null;
    private boolean mPluginUpdateRemain;
    private UpdateProviderInstallManager mProviderInstallManager;
    private UpdateProviderInstallManager.IInstallManagerCallback mProviderInstallManagerCallback = new UpdateProviderInstallManager.IInstallManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateInstallManager.AnonymousClass2 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.IInstallManagerCallback
        public void onEndOfInstall() {
            UpdateInstallManager updateInstallManager = UpdateInstallManager.this;
            DeviceRegistryData targetDeviceRegistryData = updateInstallManager.getTargetDeviceRegistryData(updateInstallManager.mBtAddress);
            if (targetDeviceRegistryData != null) {
                UpdateHistoryManager.getInstance().updateStatus(targetDeviceRegistryData.packagename, 0);
            }
            if (UpdateInstallManager.this.tUHMInstallNeeded()) {
                UpdateInstallManager.this.updateCurrentUHMVersionToDB();
                UpdateInstallManager.this.mInstallManagerCallback.onInstallUHM();
                return;
            }
            UpdateInstallManager.this.mInstallManagerCallback.onEndOfInstall();
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.IInstallManagerCallback
        public void onFailToInstall(int i, String str) {
            UpdateInstallManager updateInstallManager = UpdateInstallManager.this;
            DeviceRegistryData targetDeviceRegistryData = updateInstallManager.getTargetDeviceRegistryData(updateInstallManager.mBtAddress);
            if (targetDeviceRegistryData != null) {
                UpdateHistoryManager.getInstance().updateStatus(targetDeviceRegistryData.packagename, 2);
            }
            UpdateInstallManager.this.mInstallManagerCallback.onFailToInstall(i, str);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.IInstallManagerCallback
        public void onStartInstall() {
        }
    };
    private boolean mTUHMInstallNeeded;
    private String mTUHMSignature;
    private UpdateInstaller.IUpdateInstallerCallback mUpdateInstallerCallback = new UpdateInstaller.IUpdateInstallerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateInstallManager.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstaller.IUpdateInstallerCallback
        public void onEndOfInstall() {
            UpdateInstallManager.this.startProviderInstall();
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstaller.IUpdateInstallerCallback
        public void onFailToInstall(int i, String str) {
            String str2 = UpdateInstallManager.TAG;
            Log.d(str2, "onFailToInstall() reason : " + i + " packageName : " + str);
            if ("com.samsung.android.app.watchmanager".equals(str) || !UpdateInstallManager.this.tUHMInstallNeeded()) {
                UpdateInstallManager.this.mInstallManagerCallback.onFailToInstall(i, str);
                return;
            }
            UpdateInstallManager.this.updateCurrentUHMVersionToDB();
            UpdateInstallManager.this.mInstallManagerCallback.onInstallUHM();
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstaller.IUpdateInstallerCallback
        public void onNoSilentInstallPermission(InstallPack installPack) {
            Log.d(UpdateInstallManager.TAG, "onNoSilentInstallPermission() We able to install via Intent only");
            UpdateInstallData.setNonSamsungInstallRequested(false);
            InstallationUtils.changeFilePermission(installPack.path, InstallationUtils.PERMISSIONS_GLOBAL);
            try {
                Context appContext = TWatchManagerApplication.getAppContext();
                Intent intent = new Intent(UpdateInstallActivity.ACTION_CALL_PACKAGE_INSTALLER);
                intent.putExtra(UpdateInstallActivity.INTENT_KEY_FILE_PATH, installPack.path);
                intent.setClass(appContext, UpdateInstallActivity.class);
                intent.setFlags(268435456);
                appContext.startActivity(intent);
                UpdateInstallManager.this.mIntentInstallReceiver = new IntentInstallBroadcastReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(UpdateInstallActivity.ACTION_CALL_PACKAGE_INSTALLER);
                b.a(appContext).a(UpdateInstallManager.this.mIntentInstallReceiver, intentFilter);
            } catch (RuntimeException e) {
                e.printStackTrace();
                Log.d(UpdateInstallManager.TAG, "mUpdateInstallerCallback.onNoSilentInstallPermission() can't start activity...");
                UpdateInstallManager.this.mInstallManagerCallback.onFailToInstall(0, installPack.packName);
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstaller.IUpdateInstallerCallback
        public void onSinglePackageInstalled(String str) {
            String str2 = UpdateInstallManager.TAG;
            Log.d(str2, "mUpdateInstallerCallback.onSinglePackageInstalled() " + str + " is installed...");
        }
    };

    public interface IInstallManagerCallback {
        void onDisconnectBeforePluginInstall(String str);

        void onEndOfInstall();

        void onFailToInstall(int i, String str);

        void onInstallUHM();

        void onStartInstall();
    }

    /* access modifiers changed from: private */
    public class IntentInstallBroadcastReceiver extends BroadcastReceiver {
        private IntentInstallBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("resultCode", -1);
            String str = UpdateInstallManager.TAG;
            Log.d(str, "onReceive() resultCode : " + intExtra);
            UpdateInstallManager.this.checkAfterPackageInstallerLaunched();
        }
    }

    public UpdateInstallManager(IInstallManagerCallback iInstallManagerCallback, String str, String str2, Map<String, String> map, boolean z) {
        init(iInstallManagerCallback, str, str2, map, z);
    }

    public UpdateInstallManager(IInstallManagerCallback iInstallManagerCallback, String str, Map<String, String> map, boolean z) {
        String str2 = null;
        DeviceRegistryData targetDeviceRegistryData = getTargetDeviceRegistryData(str);
        String str3 = TAG;
        Log.d(str3, "UpdateInstallManager() plugin db info to install : " + targetDeviceRegistryData);
        init(iInstallManagerCallback, str, targetDeviceRegistryData != null ? HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(targetDeviceRegistryData.deviceFixedName) : str2, map, z);
    }

    private boolean checkSignature(String str, Map<String, String> map, String str2) {
        boolean z;
        if (this.mPluginUpdateRemain) {
            z = CertificateChecker.get().matchSignatureLegacy(str, str2);
        } else {
            z = CertificateChecker.get().matchSignature(str, map.get(str2));
        }
        String str3 = TAG;
        Log.d(str3, "installPackage() *** " + str2 + " ***  matchSignature ? : " + z);
        return z;
    }

    private File[] getDownloadedFileList() {
        String str = TAG;
        Log.d(str, "getDownloadedFileList() starts... mInstallPath : " + this.mInstallPath);
        if (!TextUtils.isEmpty(this.mInstallPath)) {
            File file = new File(this.mInstallPath);
            if (file.exists()) {
                return file.listFiles();
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private DeviceRegistryData getTargetDeviceRegistryData(String str) {
        if (!TextUtils.isEmpty(str)) {
            List<DeviceRegistryData> queryDevicebyDeviceIdRegistryData = new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(str, TWatchManagerApplication.getAppContext());
            if (!queryDevicebyDeviceIdRegistryData.isEmpty()) {
                return queryDevicebyDeviceIdRegistryData.get(0);
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x005a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void init(com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback r1, java.lang.String r2, java.lang.String r3, java.util.Map<java.lang.String, java.lang.String> r4, boolean r5) {
        /*
        // Method dump skipped, instructions count: 143
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.UpdateInstallManager.init(com.samsung.android.app.twatchmanager.update.UpdateInstallManager$IInstallManagerCallback, java.lang.String, java.lang.String, java.util.Map, boolean):void");
    }

    private boolean isTargetWatchDeviceConnected() {
        boolean z;
        DeviceRegistryData targetDeviceRegistryData = getTargetDeviceRegistryData(this.mBtAddress);
        if (targetDeviceRegistryData != null) {
            boolean isContainerPackage = GearRulesManager.getInstance().isContainerPackage(UpdateUtil.changeContainerPackageForGear12S(targetDeviceRegistryData.packagename));
            String str = TAG;
            Log.d(str, "isTargetWatchDeviceConnected() isWatchPackage : " + isContainerPackage);
            if (isContainerPackage && targetDeviceRegistryData.isConnected == 2) {
                z = true;
                String str2 = TAG;
                Log.d(str2, "isLastWatchDeviceConnected() result : " + z);
                return z;
            }
        }
        z = false;
        String str22 = TAG;
        Log.d(str22, "isLastWatchDeviceConnected() result : " + z);
        return z;
    }

    private List<InstallPack> makeInstallPackList(Map<String, String> map) {
        ArrayList arrayList = new ArrayList();
        File[] downloadedFileList = getDownloadedFileList();
        if (downloadedFileList != null) {
            for (File file : downloadedFileList) {
                if (file != null && file.isFile()) {
                    String name = file.getName();
                    String absolutePath = file.getAbsolutePath();
                    String packageName = InstallationUtils.getPackageName(TWatchManagerApplication.getAppContext(), absolutePath);
                    if (map.containsKey(packageName) && checkSignature(absolutePath, map, packageName)) {
                        arrayList.add(new InstallPack(name, packageName, absolutePath, InstallationUtils.getVersionCode(packageName)));
                    }
                }
            }
        }
        InstallationUtils.changeFilePermission(this.mInstallPath, InstallationUtils.PERMISSIONS_GLOBAL);
        InstallationUtils.changeFilePermission(new File(this.mInstallPath).getParent(), InstallationUtils.PERMISSIONS_GLOBAL);
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startProviderInstall() {
        Log.d(TAG, "startProviderInstall() starts... ");
        this.mProviderInstallManager = new UpdateProviderInstallManager(this.mProviderInstallManagerCallback);
        this.mProviderInstallManager.startInstallProviders(this.mBtAddress, this.mDeviceName, this.mContainerPackageName);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateCurrentUHMVersionToDB() {
        Context appContext = TWatchManagerApplication.getAppContext();
        int versionCode = HostManagerUtils.getVersionCode(appContext, "com.samsung.android.app.watchmanager");
        RegistryAppsDBManager.updateAppVersion("com.samsung.android.app.watchmanager", versionCode, appContext);
        String str = TAG;
        Log.d(str, "updateCurrentUHMVersionToDB() ** current tUHM versionCode : " + versionCode);
    }

    public void checkAfterPackageInstallerLaunched() {
        InstallPack currentInstallPack = UpdateInstallData.getInstance().getCurrentInstallPack();
        Log.d(TAG, "onReceive() installPack : " + currentInstallPack);
        if (currentInstallPack != null) {
            int versionCode = InstallationUtils.getVersionCode(currentInstallPack.packName);
            if (versionCode > currentInstallPack.mVersionCode) {
                RegistryAppsDBManager.updateAppUpdateCancelCount(currentInstallPack.packName, 0, TWatchManagerApplication.getAppContext());
            }
            this.mInstaller.handleAfterSinglePkgInstalled();
            boolean z = !UpdateInstallData.getInstance().hasPackageToInstall();
            if (z) {
                this.mUpdateInstallerCallback.onEndOfInstall();
            } else {
                this.mInstaller.startUpdateInstallation();
            }
            Log.d(TAG, "onReceive() mVersionCode [" + currentInstallPack.mVersionCode + "], newVersionCode [" + versionCode + "] endUpdate : " + z);
            try {
                b.a(TWatchManagerApplication.getAppContext()).a(this.mIntentInstallReceiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void installPluginPackages() {
        boolean z;
        List<InstallPack> makeInstallPackList = makeInstallPackList(this.mDownloadedPluginSignatureMap);
        String str = TAG;
        Log.d(str, "installPluginPackages() starts... downloaded packages : " + this.mDownloadedPluginSignatureMap.keySet() + " installPackList size : " + makeInstallPackList.size());
        if (!makeInstallPackList.isEmpty()) {
            this.mInstaller = new UpdateInstaller();
            this.mInstaller.init(makeInstallPackList, this.mUpdateInstallerCallback);
            z = this.mInstaller.startUpdateInstallation();
        } else {
            z = false;
        }
        String str2 = TAG;
        Log.d(str2, "installPluginPackages() can start update installation? " + z);
        if (!z) {
            this.mUpdateInstallerCallback.onFailToInstall(-1, null);
        }
    }

    public void installTUHMPackage() {
        boolean z;
        HashMap hashMap = new HashMap();
        hashMap.put("com.samsung.android.app.watchmanager", this.mTUHMSignature);
        List<InstallPack> makeInstallPackList = makeInstallPackList(hashMap);
        int i = 0;
        if (!makeInstallPackList.isEmpty()) {
            this.mInstaller = new UpdateInstaller();
            this.mInstaller.init(makeInstallPackList, this.mUpdateInstallerCallback);
            z = this.mInstaller.startUpdateInstallation();
        } else {
            z = false;
        }
        String str = TAG;
        Log.d(str, "installTUHMPackage() can start update installation? " + z);
        if (!z) {
            this.mUpdateInstallerCallback.onFailToInstall(-1, "com.samsung.android.app.watchmanager");
        }
        if (!z) {
            i = 2;
        }
        UpdateHistoryManager.getInstance().updateStatus("com.samsung.android.app.watchmanager", i);
    }

    public void pluginInstallProcess() {
        String str = TAG;
        Log.d(str, "pluginInstallProcess() starts... mBtAddress : " + this.mBtAddress + " mDeviceName : " + this.mDeviceName);
        if (!this.mDownloadedPluginSignatureMap.isEmpty()) {
            UpdateUtil.sendBackupLogIntent();
            if (isTargetWatchDeviceConnected()) {
                this.mInstallManagerCallback.onDisconnectBeforePluginInstall(this.mDeviceName);
                BackupCompleteReceiver.registerReceiver(TWatchManagerApplication.getAppContext(), this.mDisconnectCompleteListener, this.mBtAddress, this.mDeviceName, 3);
                return;
            }
            this.mInstallManagerCallback.onStartInstall();
            installPluginPackages();
            return;
        }
        this.mUpdateInstallerCallback.onFailToInstall(-1, null);
    }

    public boolean tUHMInstallNeeded() {
        return this.mTUHMInstallNeeded;
    }
}
