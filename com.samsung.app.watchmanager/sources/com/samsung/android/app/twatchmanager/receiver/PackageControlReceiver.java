package com.samsung.android.app.twatchmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.packagecontroller.PackageControllerFactory;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.watchmanager.libinterface.IInstaller;
import com.samsung.android.app.watchmanager.libinterface.OnstatusReturned;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PackageControlReceiver extends BroadcastReceiver {
    private static final String LIST_PACKAGES = "list";
    private static final String TAG = "tUHM:PackageControlReceiver";
    private static final String XML_BLOCKED_PACKAGES = "blocked_packages";
    private BackgroundInstall mBI = null;
    Context mContext;

    /* access modifiers changed from: package-private */
    public class BackgroundInstall extends AsyncTask<String, String, String> {
        volatile boolean isInstallFinish = false;
        boolean keepApk;
        Context mContext;
        private IInstaller mPackageController;
        String packName;
        String path;

        public BackgroundInstall(Context context, String str, String str2, boolean z) {
            this.mContext = context;
            this.path = str;
            this.packName = str2;
            this.keepApk = z;
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... strArr) {
            String str;
            String str2;
            if (this.path == null) {
                return null;
            }
            try {
                Log.d(PackageControlReceiver.TAG, "PackageController.installPackage Start");
                this.mPackageController.installPackage(this.path, this.packName);
                synchronized (this) {
                    while (!this.isInstallFinish) {
                        try {
                            Log.i(PackageControlReceiver.TAG, "wait");
                            wait();
                        } catch (InterruptedException unused) {
                            Log.i(PackageControlReceiver.TAG, "interrupted");
                        }
                    }
                }
                File file = new File(this.path);
                if (this.keepApk) {
                    str = PackageControlReceiver.TAG;
                    str2 = "Keep apk :" + file.getAbsolutePath();
                } else if (file.delete()) {
                    str = PackageControlReceiver.TAG;
                    str2 = "Deleted :" + file.getAbsolutePath();
                } else {
                    str = PackageControlReceiver.TAG;
                    str2 = "Cannot delete :" + file.getAbsolutePath();
                }
                Log.d(str, str2);
                Log.d(PackageControlReceiver.TAG, "Install finish");
                Thread.sleep(20);
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            if (this.isInstallFinish) {
                PackageControlReceiver.this.deleteInternalFile(PackageControlReceiver.this.getInternalPathToPMTempDir());
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            String str = this.path;
            if (str != null) {
                try {
                    InstallationUtils.changeFilePermission(str, InstallationUtils.PERMISSIONS_GLOBAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    this.mPackageController = PackageControllerFactory.getInstaller(this.mContext);
                    this.mPackageController.SetOnStatusReturned(new OnstatusReturned() {
                        /* class com.samsung.android.app.twatchmanager.receiver.PackageControlReceiver.BackgroundInstall.AnonymousClass1 */

                        @Override // com.samsung.android.app.watchmanager.libinterface.OnstatusReturned
                        public void packageInstalled(String str, int i) {
                            String str2;
                            String str3;
                            Log.d(PackageControlReceiver.TAG, "Installation response packageName=" + str);
                            if (i == 1) {
                                str2 = PackageControlReceiver.TAG;
                                str3 = "Re-Install Success";
                            } else {
                                str2 = PackageControlReceiver.TAG;
                                str3 = "Re-Install UnSuccessful";
                            }
                            Log.d(str2, str3);
                            BackgroundInstall backgroundInstall = BackgroundInstall.this;
                            backgroundInstall.isInstallFinish = true;
                            synchronized (backgroundInstall) {
                                BackgroundInstall.this.notify();
                            }
                        }

                        @Override // com.samsung.android.app.watchmanager.libinterface.OnstatusReturned
                        public void packageUninstalled(String str, int i) {
                            if (i == 1) {
                                Log.d(PackageControlReceiver.TAG, "packageName=" + str);
                                Log.d(PackageControlReceiver.TAG, "Unistall Success");
                            }
                        }
                    });
                } catch (SecurityException e2) {
                    e2.printStackTrace();
                } catch (NoSuchMethodException e3) {
                    e3.printStackTrace();
                }
                super.onPreExecute();
            }
        }
    }

    public static void addBlockedPackage(String str) {
        Log.d(TAG, " addBlockedPackage [" + str + "]");
        SharedPreferences sharedPreferences = TWatchManagerApplication.getAppContext().getSharedPreferences(XML_BLOCKED_PACKAGES, 4);
        Set<String> stringSet = sharedPreferences.getStringSet(LIST_PACKAGES, null);
        Log.d(TAG, " get Set from preferences [" + stringSet + "]");
        HashSet hashSet = new HashSet();
        if (stringSet != null) {
            hashSet.addAll(stringSet);
        }
        hashSet.add(str);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(LIST_PACKAGES);
        if (hashSet.size() > 0) {
            edit.putStringSet(LIST_PACKAGES, hashSet);
        }
        edit.apply();
        Log.d(TAG, " successfully added [" + str + "] into [" + hashSet + "]");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void modifyDB(String str) {
        Context context = this.mContext;
        RegistryDbManagerWithProvider registryDbManagerWithProvider = new RegistryDbManagerWithProvider();
        RegistryAppsDBManager.deleteAppRegistryData(str, this.mContext);
        List<DeviceRegistryData> queryAllDeviceRegistryData = registryDbManagerWithProvider.queryAllDeviceRegistryData(context);
        if (queryAllDeviceRegistryData == null || queryAllDeviceRegistryData.size() <= 0) {
            Log.d(TAG, "nothing to set as last launched");
            return;
        }
        for (DeviceRegistryData deviceRegistryData : queryAllDeviceRegistryData) {
            if (deviceRegistryData.isConnected == 2) {
                Log.d(TAG, deviceRegistryData.deviceName + "(" + deviceRegistryData.deviceBtID + ") will be lastLaunchedDevice, because it's connected");
                registryDbManagerWithProvider.updateDeviceLastLaunchRegistryData(deviceRegistryData.deviceBtID, context);
                return;
            }
        }
        DeviceRegistryData deviceRegistryData2 = queryAllDeviceRegistryData.get(0);
        Log.d(TAG, deviceRegistryData2.deviceName + "(" + deviceRegistryData2.deviceBtID + ") will be lastLaunchedDevice, because it's first in the list");
        registryDbManagerWithProvider.updateDeviceLastLaunchRegistryData(deviceRegistryData2.deviceBtID, context);
    }

    private static boolean removeBlockedPackage(String str) {
        Log.d(TAG, " removeBlockedPackage [" + str + "]");
        SharedPreferences sharedPreferences = TWatchManagerApplication.getAppContext().getSharedPreferences(XML_BLOCKED_PACKAGES, 4);
        Set<String> stringSet = sharedPreferences.getStringSet(LIST_PACKAGES, null);
        Log.d(TAG, " current list of blocked apps [" + stringSet + "]");
        if (stringSet == null) {
            return false;
        }
        HashSet hashSet = new HashSet();
        hashSet.addAll(stringSet);
        boolean remove = hashSet.remove(str);
        Log.d(TAG, " Removed from shared preferences [" + remove + "] and remain packages are [" + hashSet + "]");
        if (!remove) {
            return remove;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(LIST_PACKAGES);
        if (hashSet.size() > 0) {
            edit.putStringSet(LIST_PACKAGES, hashSet);
        }
        edit.apply();
        return remove;
    }

    private void startReinstallInternal(String str, String str2) {
        String apkNameFromPreference = InstallationUtils.getApkNameFromPreference(this.mContext, str2);
        Log.d(TAG, "startReinstallInternal() parentPkg: " + str + " uninstalledPackage: " + str2 + " apkName :" + apkNameFromPreference);
        String str3 = null;
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager != null) {
                str3 = copyFileToInternalDirTemp(packageManager.getResourcesForApplication(str).getAssets(), "GearModuleApks", apkNameFromPreference);
            } else {
                Log.e(TAG, "pm is null");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "startReinstallInternal() path : " + str3);
        if (str3 != null) {
            String appNameFromPreference = InstallationUtils.getAppNameFromPreference(this.mContext, str2);
            Log.i(TAG, "Package removed, reinstalling again package:" + str2 + " appName :" + appNameFromPreference);
            this.mBI = new BackgroundInstall(this.mContext, str3, str2, false);
            this.mBI.execute("");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0077 A[SYNTHETIC, Splitter:B:33:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x007f A[Catch:{ Exception -> 0x007b }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x008c A[SYNTHETIC, Splitter:B:44:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0094 A[Catch:{ Exception -> 0x0090 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String copyFileToInternalDirTemp(android.content.res.AssetManager r5, java.lang.String r6, java.lang.String r7) {
        /*
        // Method dump skipped, instructions count: 158
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.receiver.PackageControlReceiver.copyFileToInternalDirTemp(android.content.res.AssetManager, java.lang.String, java.lang.String):java.lang.String");
    }

    public void deleteInternalFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            Log.d(TAG, file.delete() ? "Deleted file created during re-installation" : "Unable to delete file created during re-installation!!");
        }
    }

    public String getInternalPathToPMTempDir() {
        Log.d(TAG, "getInternalPathToPMTempDir()");
        Context context = this.mContext;
        if (context != null) {
            String absolutePath = context.getDir("Providers_UHM", 0).getAbsolutePath();
            try {
                InstallationUtils.changeFilePermission(absolutePath, InstallationUtils.PERMISSIONS_GLOBAL);
                return absolutePath;
            } catch (Exception e) {
                e.printStackTrace();
                return absolutePath;
            }
        } else {
            Log.d(TAG, "context is null, can't get internal path.");
            return null;
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            this.mContext = context;
            String action = intent.getAction();
            if (action != null) {
                Log.i(TAG, "intent getAction=" + action);
                if ("android.intent.action.PACKAGE_FULLY_REMOVED".equalsIgnoreCase(action)) {
                    Uri data = intent.getData();
                    final String str = null;
                    if (data != null) {
                        str = data.getSchemeSpecificPart();
                    }
                    Log.d(TAG, "Uninstalled Package :" + str);
                    if (str != null) {
                        if (!InstallationUtils.hasInstallPermission(context)) {
                            removeBlockedPackage(str);
                        } else if (removeBlockedPackage(str)) {
                            Log.d(TAG, " Blocked app in accordance with queue : " + str);
                        } else {
                            String parentPackageFromPreference = InstallationUtils.getParentPackageFromPreference(context, str);
                            Log.d(TAG, "Parent package :" + parentPackageFromPreference);
                            if (parentPackageFromPreference != null && !parentPackageFromPreference.equalsIgnoreCase(InstallationUtils.PARENT_PACKAGE_APP_STORE)) {
                                startReinstallInternal(parentPackageFromPreference, str);
                            } else {
                                GearRulesManager.getInstance().syncGearInfo(new GearRulesManager.ISyncCallback() {
                                    /* class com.samsung.android.app.twatchmanager.receiver.PackageControlReceiver.AnonymousClass1 */

                                    @Override // com.samsung.android.app.twatchmanager.manager.GearRulesManager.ISyncCallback
                                    public void onSyncComplete(boolean z) {
                                        Log.d(PackageControlReceiver.TAG, "onSyncComplete() isSuccess : " + z);
                                        Set<String> supportPackageSet = GearRulesManager.getInstance().getSupportPackageSet();
                                        if (supportPackageSet != null) {
                                            for (String str : supportPackageSet) {
                                                if (str.equals(str)) {
                                                    Log.d(PackageControlReceiver.TAG, "uninstalledPackage package is Gear supported Package name");
                                                    PackageControlReceiver.this.modifyDB(str);
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}
