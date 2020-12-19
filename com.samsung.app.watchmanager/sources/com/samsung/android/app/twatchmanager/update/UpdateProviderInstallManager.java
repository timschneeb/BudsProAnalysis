package com.samsung.android.app.twatchmanager.update;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import com.samsung.android.app.twatchmanager.smartswitch.FileUtils;
import com.samsung.android.app.twatchmanager.update.UpdateInstaller;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UpdateProviderInstallManager {
    public static final String CONTAINER_APK_ASSETS_FOLDER = "GearModuleApks";
    public static final String PACKAGE_NAME_CALL_PROVIDER = "com.samsung.accessory.callprovider";
    public static final String TAG = ("tUHM:[Update][Conn]" + UpdateProviderInstallManager.class.getSimpleName());
    private IInstallManagerCallback mInstallManagerCallback = null;
    private UpdateInstaller mInstaller = null;
    private UpdateInstaller.IUpdateInstallerCallback mUpdateInstallerCallback = new UpdateInstaller.IUpdateInstallerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstaller.IUpdateInstallerCallback
        public void onEndOfInstall() {
            UpdateProviderInstallManager.this.mInstallManagerCallback.onEndOfInstall();
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstaller.IUpdateInstallerCallback
        public void onFailToInstall(int i, String str) {
            UpdateProviderInstallManager.this.mInstallManagerCallback.onFailToInstall(i, str);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstaller.IUpdateInstallerCallback
        public void onNoSilentInstallPermission(InstallPack installPack) {
            Log.d(UpdateProviderInstallManager.TAG, "onNoSilentInstallPermission() there is no install permission");
            UpdateProviderInstallManager.this.mInstallManagerCallback.onFailToInstall(0, installPack.packName);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstaller.IUpdateInstallerCallback
        public void onSinglePackageInstalled(String str) {
            String str2 = UpdateProviderInstallManager.TAG;
            Log.d(str2, "onSinglePackageInstalled() provider : " + str + " is installed.");
        }
    };

    public interface IInstallManagerCallback {
        void onEndOfInstall();

        void onFailToInstall(int i, String str);

        void onStartInstall();
    }

    public UpdateProviderInstallManager(IInstallManagerCallback iInstallManagerCallback) {
        this.mInstallManagerCallback = iInstallManagerCallback;
    }

    private boolean canInstallProvider(String str, PackageInfo packageInfo, PackageInfo packageInfo2) {
        boolean z;
        if (packageInfo2 != null) {
            String str2 = TAG;
            Log.d(str2, "canInstallProvider() providerName : " + packageInfo.packageName + " newVersionCode : " + packageInfo.versionCode + " currentVersionCode : " + packageInfo2.versionCode);
            if (packageInfo.versionCode <= packageInfo2.versionCode) {
                z = false;
                if (!PACKAGE_NAME_CALL_PROVIDER.equalsIgnoreCase(packageInfo.packageName) && !HostManagerUtilsRulesBTDevices.isHFPdevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str))) {
                    return false;
                }
            }
        }
        z = true;
        return !PACKAGE_NAME_CALL_PROVIDER.equalsIgnoreCase(packageInfo.packageName) ? z : z;
    }

    private boolean compareSharedUid(PackageInfo packageInfo, PackageInfo packageInfo2) {
        if (packageInfo == null || packageInfo2 == null) {
            return true;
        }
        return TextUtils.equals(packageInfo2.sharedUserId, packageInfo.sharedUserId);
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x009c A[SYNTHETIC, Splitter:B:38:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00b2 A[SYNTHETIC, Splitter:B:48:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bc A[SYNTHETIC, Splitter:B:53:0x00bc] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String copyProvidersFromPluginAsset(java.lang.String r12) {
        /*
        // Method dump skipped, instructions count: 198
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.copyProvidersFromPluginAsset(java.lang.String):java.lang.String");
    }

    public static String initPathToCopy() {
        String str;
        Context appContext = TWatchManagerApplication.getAppContext();
        if (appContext != null) {
            str = appContext.getFilesDir().getAbsolutePath() + File.separator + "Dump";
        } else {
            str = null;
        }
        if (!TextUtils.isEmpty(str)) {
            FileUtils.deleteDirectory(str);
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                InstallationUtils.changeFilePermission(str, InstallationUtils.PERMISSIONS_GLOBAL);
                Log.d(TAG, " copyProvidersFromPluginAsset() change file permission is done...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "initPathToCopy() return pathToCopy : " + str);
        return str;
    }

    private List<InstallPack> makeInstallPackList(String str, String str2, ArrayList<String> arrayList) {
        File file = new File(str2);
        ArrayList arrayList2 = new ArrayList();
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            try {
                PackageManager packageManager = TWatchManagerApplication.getAppContext().getPackageManager();
                for (File file2 : listFiles) {
                    PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(file2.getPath(), 0);
                    if (arrayList.contains(packageArchiveInfo.packageName)) {
                        PackageInfo packageInfo = null;
                        try {
                            packageInfo = packageManager.getPackageInfo(packageArchiveInfo.packageName, 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (canInstallProvider(str, packageArchiveInfo, packageInfo)) {
                            arrayList2.add(new InstallPack(file2.getName(), packageArchiveInfo.packageName, file2.getPath(), compareSharedUid(packageArchiveInfo, packageInfo)));
                        }
                    }
                }
            } catch (NullPointerException e2) {
                e2.printStackTrace();
            }
        }
        Log.d(TAG, "makeInstallPackList() ends .. makeInstallPackList : " + arrayList2);
        return arrayList2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startInstallProviders(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
        // Method dump skipped, instructions count: 132
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.startInstallProviders(java.lang.String, java.lang.String, java.lang.String):void");
    }
}
