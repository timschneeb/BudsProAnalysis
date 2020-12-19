package com.samsung.android.app.twatchmanager.manager;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.log.LoggerUtil;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import com.samsung.android.app.twatchmanager.model.UHMPackageInfo;
import com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager;
import com.samsung.android.app.twatchmanager.util.CertificateChecker;
import com.samsung.android.app.twatchmanager.util.HandlerThreadUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.InstallationAsyncTask;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.Toaster;
import java.util.ArrayList;
import java.util.Iterator;

public class InstallationManager {
    private static final String TAG = ("tUHM:" + InstallationManager.class.getSimpleName());
    private InstallationAsyncTask installationAsyncTask;
    private HandlerThread installationThread = new HandlerThread("INSTALLATION_THREAD", 5);
    String lastProcessedpackageName;
    private String mBtAddress;
    private Activity mContext;
    private String mDeviceName;
    private Handler mHandler;
    private String mPackageName;
    private Handler mlocalInstallHandler;
    private int onGoingInstallationType;
    private ArrayList<InstallPack> pendingInstallList;

    private final class LocalHandler extends Handler {
        public LocalHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            String str = InstallationManager.TAG;
            Log.d(str, "mlocalInstallHandler  : " + message.what);
            int i = message.what;
            if (i == 201) {
                Bundle data = message.getData();
                InstallationManager.this.lastProcessedpackageName = data.getString("packageName");
                int i2 = data.getInt(InstallationUtils.MSG_INSTALLED_PACKAGE_INDEX);
                if (InstallationManager.this.onGoingInstallationType == 603) {
                    Log.d(InstallationManager.TAG, "INSTALLATION_TYPE_ESSENTIAL");
                    Message obtainMessage = InstallationManager.this.mHandler.obtainMessage();
                    obtainMessage.what = InstallationUtils.MSG_INSTALL_ESSENTIAL_PROGRESS;
                    obtainMessage.arg1 = i2;
                    InstallationManager.this.mHandler.sendMessage(obtainMessage);
                }
            } else if (i == 202) {
                if (InstallationManager.this.onGoingInstallationType == 604) {
                    InstallationManager.this.mHandler.sendEmptyMessage(InstallationUtils.MSG_SUPPORT_INSTALLATION_COMPLETE);
                    if ((InstallationManager.this.mPackageName != null && InstallationManager.this.mPackageName.equalsIgnoreCase(InstallationManager.this.lastProcessedpackageName)) || (InstallationManager.this.pendingInstallList != null && InstallationManager.this.pendingInstallList.size() > 0)) {
                        InstallationManager installationManager = InstallationManager.this;
                        installationManager.handleProviderInstallation(installationManager.lastProcessedpackageName);
                        return;
                    }
                } else if (InstallationManager.this.onGoingInstallationType != 603) {
                    if (InstallationManager.this.onGoingInstallationType == 605) {
                        Log.d(InstallationManager.TAG, "onGoingInstallationType == INSTALLATION_NOT_REQUIRED");
                        InstallationManager.this.mHandler.sendEmptyMessage(InstallationUtils.MSG_FULL_INSTALLATION_COMPLETE);
                        return;
                    }
                    return;
                }
                InstallationManager.this.mHandler.sendEmptyMessageDelayed(InstallationUtils.MSG_FULL_INSTALLATION_COMPLETE, 3500);
            } else if (i == 205) {
                InstallationManager.this.handleProviderInstallation((InstallationManager) message);
            } else if (i == 206) {
                InstallationManager.this.handleStartInstallation(message);
            } else if (i == 317) {
                InstallationManager.this.mHandler.sendMessage(InstallationManager.this.mHandler.obtainMessage(InstallationUtils.MSG_INSTALLATION_FAILED, message.obj));
            }
        }
    }

    public InstallationManager(Activity activity, Handler handler, String str, String str2, String str3) {
        this.mContext = activity;
        this.mHandler = handler;
        this.mDeviceName = str;
        this.mPackageName = str2;
        this.mBtAddress = str3;
        this.installationThread.start();
        this.mlocalInstallHandler = new LocalHandler(this.installationThread.getLooper());
    }

    private void deleteExtractedFile(InstallPack installPack) {
        String str = TAG;
        Log.d(str, " deleteExtractedFile() InstallPack:" + installPack);
        try {
            InstallationUtils.changeFilePermission(installPack.path, InstallationUtils.PERMISSIONS_GLOBAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        InstallationUtils.cleardumpStorage(this.mContext, installPack.path);
    }

    private ArrayList<InstallPack> filterApps(ArrayList<InstallPack> arrayList) {
        String str = TAG;
        Log.d(str, " filterApps() before filter allProviders:" + arrayList);
        ArrayList<InstallPack> arrayList2 = new ArrayList<>();
        arrayList2.addAll(arrayList);
        Iterator<InstallPack> it = arrayList.iterator();
        while (it.hasNext()) {
            InstallPack next = it.next();
            if (HostManagerUtils.isExistPackage(this.mContext, next.packName)) {
                HostManagerUtils.enableApplication(this.mContext, next.packName);
                PackageManager packageManager = this.mContext.getPackageManager();
                PackageInfo packageInfo = null;
                if (packageManager != null) {
                    packageInfo = packageManager.getPackageArchiveInfo(next.path, 0);
                } else {
                    Log.e(TAG, "pm is null");
                }
                String str2 = TAG;
                Log.d(str2, " extractedPInfo :" + packageInfo);
                if (packageInfo != null) {
                    try {
                        PackageInfo packageInfo2 = packageManager.getPackageInfo(next.packName, 0);
                        String str3 = TAG;
                        Log.d(str3, next.packName + " extractedPInfo.versionCode :" + packageInfo.versionCode + " installedPackageInfo.versionCode " + packageInfo2.versionCode);
                        String str4 = TAG;
                        Log.d(str4, " package :" + next.packName + " extractedPInfo.sharedUserId :" + packageInfo.sharedUserId + " installedPackageInfo.sharedUserId:" + packageInfo2.sharedUserId);
                        if (TextUtils.isEmpty(packageInfo2.sharedUserId) && !TextUtils.isEmpty(packageInfo.sharedUserId)) {
                            next.reinstall = true;
                            if (packageInfo.versionCode == packageInfo2.versionCode) {
                                String str5 = TAG;
                                Log.d(str5, next.packName + " same versionCode but different sharedUserId ");
                                Activity activity = this.mContext;
                                Toaster.show(activity, next.packName + "\nsame versionCode but different sharedUserId ");
                            }
                        }
                        if (packageInfo.versionCode <= packageInfo2.versionCode) {
                            deleteExtractedFile(next);
                            arrayList2.remove(next);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    deleteExtractedFile(next);
                }
            } else if (next.packName.equalsIgnoreCase(UpdateProviderInstallManager.PACKAGE_NAME_CALL_PROVIDER)) {
                BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(this.mBtAddress);
                String str6 = TAG;
                Log.d(str6, " Call provider filtering!! bluetoothDevice:" + remoteDevice);
                if (!HostManagerUtilsRulesBTDevices.isHFPdevice(remoteDevice)) {
                    Log.d(TAG, " SKIP call provider installation");
                }
            }
            arrayList2.remove(next);
        }
        String str7 = TAG;
        Log.d(str7, " filterApps() After Filter :" + arrayList2);
        return arrayList2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x0205  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0214 A[SYNTHETIC, Splitter:B:108:0x0214] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x021f A[SYNTHETIC, Splitter:B:113:0x021f] */
    /* JADX WARNING: Removed duplicated region for block: B:126:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:127:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:129:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01cc A[SYNTHETIC, Splitter:B:76:0x01cc] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01d7 A[SYNTHETIC, Splitter:B:81:0x01d7] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01e3 A[SYNTHETIC, Splitter:B:87:0x01e3] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01ee  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01fa A[SYNTHETIC, Splitter:B:97:0x01fa] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.ArrayList<com.samsung.android.app.twatchmanager.model.InstallPack> getInstallPackForEssentialProviders(java.lang.String r17, java.util.ArrayList<java.lang.String> r18, java.lang.String r19) {
        /*
        // Method dump skipped, instructions count: 555
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.manager.InstallationManager.getInstallPackForEssentialProviders(java.lang.String, java.util.ArrayList, java.lang.String):java.util.ArrayList");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleProviderInstallation(Message message) {
        ArrayList<InstallPack> arrayList;
        ArrayList<InstallPack> installPackForEssentialProviders;
        String str = (String) message.obj;
        Log.d(TAG, "handleProviderInstallation getAllPackageInstallerInfo: " + InstallationUtils.getAllPackageInstallerInfo(this.mContext, str));
        ArrayList<String> packageInstallerInfo = InstallationUtils.getPackageInstallerInfo(this.mContext, str, this.mDeviceName);
        Log.d(TAG, "orderPackageListMap :" + packageInstallerInfo);
        if (packageInstallerInfo == null || packageInstallerInfo.size() <= 0 || (installPackForEssentialProviders = getInstallPackForEssentialProviders(str, packageInstallerInfo, InstallationUtils.getEssentialFolderName())) == null) {
            arrayList = null;
        } else {
            this.onGoingInstallationType = InstallationUtils.CURRENT_OPERATION_ESSENTIAL_INSTALLATION;
            Log.d(TAG, "handleProviderInstallation() providerPackList.size():" + installPackForEssentialProviders.size());
            arrayList = filterApps(installPackForEssentialProviders);
        }
        ArrayList<InstallPack> arrayList2 = this.pendingInstallList;
        if (arrayList2 != null && arrayList2.size() > 0) {
            if (arrayList == null) {
                arrayList = this.pendingInstallList;
            } else {
                Iterator<InstallPack> it = this.pendingInstallList.iterator();
                while (it.hasNext()) {
                    InstallPack next = it.next();
                    Log.d(TAG, "handleProviderInstallation() adding pending InstallPack to install list: " + next);
                    arrayList.add(next);
                }
            }
            this.pendingInstallList = null;
        }
        Log.d(TAG, "Filtered packagelist :" + arrayList);
        if (arrayList != null && arrayList.size() > 0) {
            Message obtainMessage = this.mHandler.obtainMessage(InstallationUtils.MSG_INSTALL_ESSENTIAL_STARTED);
            obtainMessage.arg1 = arrayList.size();
            this.mHandler.sendMessage(obtainMessage);
            installPackages(arrayList, str);
        } else if (this.mlocalInstallHandler != null) {
            Log.d(TAG, "setting onGoingInstallationType to INSTALLATION_NOT_REQUIRED");
            this.onGoingInstallationType = InstallationUtils.CURRENT_OPERATION_INSTALLATION_NOT_REQUIRED;
            this.mlocalInstallHandler.sendEmptyMessage(InstallationUtils.INSTALLATION_COMPLETE);
        } else {
            Log.e(TAG, "mlocalInstallHandler is null");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleStartInstallation(Message message) {
        ArrayList<InstallPack> arrayList = (ArrayList) message.obj;
        CertificateChecker certificateChecker = CertificateChecker.get();
        Iterator<InstallPack> it = arrayList.iterator();
        boolean z = true;
        while (it.hasNext()) {
            InstallPack next = it.next();
            if (certificateChecker != null) {
                z &= InstallationUtils.isLocalInstallation() ? certificateChecker.matchSignatureWithDifferentInstalledPackage(next.path, this.mContext.getPackageName()) : certificateChecker.verifySignature(next.path, next.signature);
            } else {
                z = false;
            }
        }
        String str = TAG;
        Log.d(str, "checker.verifySignature() returns signatureMatch:" + z);
        if (!z) {
            Iterator<InstallPack> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                InstallationUtils.cleardumpStorage(this.mContext, it2.next().path);
            }
            this.mHandler.sendEmptyMessage(InstallationUtils.MSG_INSTALLATION_FAILED);
            return;
        }
        InstallPack installPack = null;
        ArrayList<UHMPackageInfo> additionalPackageList = GearRulesManager.getInstance().getAdditionalPackageList(this.mDeviceName);
        Iterator<InstallPack> it3 = arrayList.iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            InstallPack next2 = it3.next();
            if (next2.packName.equalsIgnoreCase(this.mPackageName)) {
                boolean remove = arrayList.remove(next2);
                String str2 = TAG;
                Log.d(str2, "removed supported module from installPacklist :" + remove);
                installPack = next2;
                break;
            }
        }
        if (additionalPackageList != null && additionalPackageList.size() > 0) {
            Iterator<InstallPack> it4 = arrayList.iterator();
            while (it4.hasNext()) {
                InstallPack next3 = it4.next();
                Iterator<UHMPackageInfo> it5 = additionalPackageList.iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        break;
                    }
                    UHMPackageInfo next4 = it5.next();
                    if (next4.packageName.equals(next3.packName)) {
                        next3.installerPackage = next4.installerPackage;
                        break;
                    }
                }
            }
        }
        if (arrayList.size() > 0) {
            setPendingInstallList(arrayList);
        }
        if (installPack != null) {
            installSupportedModule(installPack);
        } else {
            handleProviderInstallation(this.mPackageName);
        }
    }

    public void handleProviderInstallation(String str) {
        String str2 = TAG;
        Log.d(str2, " handleProviderInstallation :" + str);
        Handler handler = this.mlocalInstallHandler;
        if (handler != null) {
            this.mlocalInstallHandler.sendMessage(handler.obtainMessage(InstallationUtils.HANDLE_PROVIDER_INSTALLATION, str));
            return;
        }
        Log.e(TAG, "mlocalInstallHandler is null");
    }

    public void installPackages(ArrayList<InstallPack> arrayList, String str) {
        String str2 = TAG;
        Log.d(str2, " installPackages() :" + arrayList);
        if (InstallationUtils.hasInstallPermission(this.mContext)) {
            this.installationAsyncTask = new InstallationAsyncTask(this.mContext, arrayList, str);
            this.installationAsyncTask.setHandler(this.mlocalInstallHandler);
            this.installationAsyncTask.execute(new String[0]);
            return;
        }
        Log.e(TAG, "Doesnt have silent install permission ");
        InstallThroughIntent installThroughIntent = new InstallThroughIntent(this.mContext, arrayList, false);
        installThroughIntent.setHandler(this.mlocalInstallHandler);
        installThroughIntent.start();
    }

    public void installSupportedModule(InstallPack installPack) {
        Log.d(TAG, " installSupportedModule() ");
        LoggerUtil.insertLog(this.mContext, "G021", "Install SW", null);
        ArrayList<InstallPack> arrayList = new ArrayList<>();
        arrayList.add(installPack);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(InstallationUtils.MSG_INSTALL_ESSENTIAL_STARTED));
        this.onGoingInstallationType = InstallationUtils.CURRENT_OPERATION_SUPPORT_INSTALLATION;
        installPackages(arrayList, InstallationUtils.PARENT_PACKAGE_APP_STORE);
    }

    public void setPendingInstallList(ArrayList<InstallPack> arrayList) {
        this.pendingInstallList = arrayList;
    }

    public void startInstallationProcess(ArrayList<InstallPack> arrayList) {
        String str = TAG;
        Log.d(str, " startInstallationProcess :" + arrayList);
        Handler handler = this.mlocalInstallHandler;
        if (handler != null) {
            this.mlocalInstallHandler.sendMessage(handler.obtainMessage(InstallationUtils.START_INSTALLATION, arrayList));
            return;
        }
        Log.e(TAG, "mlocalInstallHandler is null");
    }

    public void stopInstallation() {
        Log.d(TAG, " stopInstallation()");
        Handler handler = this.mHandler;
        if (handler != null && handler.hasMessages(InstallationUtils.MSG_FULL_INSTALLATION_COMPLETE)) {
            Log.d(TAG, " remove MSG_FULL_INSTALLATION_COMPLETE message");
            this.mHandler.removeMessages(InstallationUtils.MSG_FULL_INSTALLATION_COMPLETE);
        }
        InstallationAsyncTask installationAsyncTask2 = this.installationAsyncTask;
        if (installationAsyncTask2 != null) {
            installationAsyncTask2.exit();
            this.installationAsyncTask.cancel(true);
        }
        Handler handler2 = this.mlocalInstallHandler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
            this.mlocalInstallHandler = null;
        }
        HandlerThreadUtils.close(this.installationThread);
        this.installationThread = null;
    }
}
