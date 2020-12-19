package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.BNRHelper;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import com.samsung.android.app.twatchmanager.model.InstallationError;
import com.samsung.android.app.twatchmanager.packagecontroller.PackageControllerFactory;
import com.samsung.android.app.twatchmanager.receiver.PackageControlReceiver;
import com.samsung.android.app.watchmanager.libinterface.IInstaller;
import com.samsung.android.app.watchmanager.libinterface.OnstatusReturned;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InstallationAsyncTask extends AsyncTask<String, String, Integer> implements OnstatusReturned {
    private String TAG = ("tUHM:" + InstallationAsyncTask.class.getSimpleName());
    private volatile InstallPack currentInstallPack;
    private volatile int curretApkSize;
    private volatile boolean isInstallFinished;
    private volatile boolean isUnInstallFinished;
    private List<InstallPack> mApplicationList;
    private Context mContext;
    private WeakReference mHandlerWeakRef;
    private IInstaller mPackageController;
    private String parentPackage;

    public InstallationAsyncTask(Context context) {
    }

    public InstallationAsyncTask(Context context, List<InstallPack> list, String str) {
        this.mContext = context.getApplicationContext();
        this.mApplicationList = list;
        this.parentPackage = str;
        Log.i(this.TAG, "Creating AsyncTask");
        try {
            this.mPackageController = PackageControllerFactory.getInstaller(this.mContext);
            this.mPackageController.SetOnStatusReturned(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public Integer doInBackground(String... strArr) {
        IllegalArgumentException e;
        IllegalAccessException e2;
        InvocationTargetException e3;
        Exception e4;
        Collections.sort(this.mApplicationList, new Comparator<InstallPack>() {
            /* class com.samsung.android.app.twatchmanager.util.InstallationAsyncTask.AnonymousClass1 */

            public int compare(InstallPack installPack, InstallPack installPack2) {
                boolean z = installPack.reinstall;
                if (z == installPack2.reinstall) {
                    return 0;
                }
                return z ? 1 : -1;
            }
        });
        String str = this.TAG;
        Log.d(str, "doInBackground()  sorted mApplicationList:" + this.mApplicationList);
        int size = this.mApplicationList.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            InstallPack installPack = this.mApplicationList.get(i);
            try {
                this.isInstallFinished = false;
                InstallationUtils.changeFilePermission(installPack.path, InstallationUtils.PERMISSIONS_GLOBAL);
                this.curretApkSize = ((int) new File(installPack.path).length()) / 1048576;
                this.currentInstallPack = installPack;
                if (installPack.reinstall) {
                    if (!z) {
                        try {
                            ArrayList<String> arrayList = new ArrayList<>(size - i);
                            for (int i2 = i; i2 < size; i2++) {
                                arrayList.add(this.mApplicationList.get(i2).packName);
                            }
                            String str2 = this.TAG;
                            Log.d(str2, "doInBackground() bnrPackageList:" + arrayList);
                            new BNRHelper().requestBackup(arrayList);
                            z = true;
                        } catch (IllegalArgumentException e5) {
                            e = e5;
                            z = true;
                            e.printStackTrace();
                        } catch (IllegalAccessException e6) {
                            e2 = e6;
                            z = true;
                            e2.printStackTrace();
                        } catch (InvocationTargetException e7) {
                            e3 = e7;
                            z = true;
                            e3.printStackTrace();
                        } catch (Exception e8) {
                            e4 = e8;
                            z = true;
                            e4.printStackTrace();
                        }
                    }
                    this.isUnInstallFinished = false;
                    String str3 = this.TAG;
                    Log.d(str3, "Reinstall :" + installPack.packName);
                    PackageControlReceiver.addBlockedPackage(installPack.packName);
                    this.mPackageController.uninstallPackage(installPack.packName);
                    synchronized (this) {
                        while (!this.isUnInstallFinished && !isCancelled()) {
                            try {
                                Log.d(this.TAG, "waiting for uninstall response from PM");
                                wait();
                            } catch (InterruptedException e9) {
                                e9.printStackTrace();
                            }
                        }
                    }
                }
                if (GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY.equals(installPack.packName) && HostManagerUtils.isExistPackage(this.mContext, GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY) && HostManagerUtils.conditionForSAPReboot() && InstallationUtils.getVersionCode(GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY) == 321) {
                    HostManagerUtils.saveRebootRequiredForGearFit2(true);
                }
                this.mPackageController.installPackage(installPack.path, installPack.installerPackage, installPack.packName);
                String str4 = this.TAG;
                Log.d(str4, "doInBackground :" + installPack.path);
                synchronized (this) {
                    while (!this.isInstallFinished && !isCancelled()) {
                        try {
                            Log.d(this.TAG, "waiting for install response from PM");
                            wait();
                        } catch (InterruptedException e10) {
                            e10.printStackTrace();
                        }
                    }
                }
                String str5 = this.TAG;
                Log.d(str5, "doInBackground() isCancelled():" + isCancelled());
                if (isCancelled()) {
                    return 0;
                }
                int indexOf = this.mApplicationList.indexOf(installPack);
                Log.d(this.TAG, "Publishing progress");
                publishProgress(String.valueOf(indexOf), installPack.packName);
                if (!installPack.keepApk) {
                    InstallationUtils.cleardumpStorage(this.mContext, installPack.path);
                    Log.d(this.TAG, "Clear dump storage");
                } else {
                    String str6 = this.TAG;
                    Log.d(str6, "Keep Apk :" + installPack.path);
                }
            } catch (IllegalArgumentException e11) {
                e = e11;
                e.printStackTrace();
            } catch (IllegalAccessException e12) {
                e2 = e12;
                e2.printStackTrace();
            } catch (InvocationTargetException e13) {
                e3 = e13;
                e3.printStackTrace();
            } catch (Exception e14) {
                e4 = e14;
                e4.printStackTrace();
            }
        }
        return 0;
    }

    public void exit() {
        Log.d(this.TAG, "exit()");
        cancel(true);
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
        Handler handler;
        WeakReference weakReference = this.mHandlerWeakRef;
        if (weakReference != null && (handler = (Handler) weakReference.get()) != null) {
            handler.sendEmptyMessage(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Integer num) {
        Handler handler;
        WeakReference weakReference = this.mHandlerWeakRef;
        if (!(weakReference == null || (handler = (Handler) weakReference.get()) == null)) {
            handler.sendEmptyMessage(InstallationUtils.INSTALLATION_COMPLETE);
        }
        Log.d(this.TAG, "Installation is done for all packages");
        IInstaller iInstaller = this.mPackageController;
        if (iInstaller != null) {
            iInstaller.SetOnStatusReturned(null);
        }
        this.mPackageController = null;
        this.mHandlerWeakRef = null;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(String... strArr) {
        Log.d(this.TAG, "onProgressUpdate: ");
        WeakReference weakReference = this.mHandlerWeakRef;
        if (weakReference != null) {
            Handler handler = (Handler) weakReference.get();
            if (handler != null) {
                Message obtainMessage = handler.obtainMessage();
                obtainMessage.what = InstallationUtils.INSTALLATION_PROGRESS;
                Bundle bundle = new Bundle();
                bundle.putInt(InstallationUtils.MSG_INSTALLED_PACKAGE_INDEX, Integer.parseInt(strArr[0]));
                bundle.putString("packageName", strArr[1]);
                obtainMessage.setData(bundle);
                handler.sendMessage(obtainMessage);
                String str = this.TAG;
                Log.d(str, "Installation is done package: " + strArr[1]);
            }
        } else {
            Log.e(this.TAG, "mHandlerWeakRef is null");
        }
        super.onProgressUpdate((Object[]) strArr);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.OnstatusReturned
    public void packageInstalled(String str, int i) {
        Handler handler;
        synchronized (this) {
            if (i == 1) {
                String str2 = this.TAG;
                Log.d(str2, "Installation successful for package: " + str);
                this.isInstallFinished = true;
                InstallationUtils.setParentPackageForReinstall(this.mContext, str, this.parentPackage);
                InstallationUtils.setAppNameForReinstallToast(this.mContext, str);
                InstallationUtils.setPackageApkNameForReinstall(this.mContext, str, this.currentInstallPack.apkName);
            } else {
                String str3 = this.TAG;
                Log.d(str3, "Installation FAILED for package: " + str);
                if (!(this.mHandlerWeakRef == null || (handler = (Handler) this.mHandlerWeakRef.get()) == null)) {
                    handler.sendMessage(handler.obtainMessage(InstallationUtils.MSG_INSTALLATION_FAILED, new InstallationError(str, i, this.curretApkSize)));
                }
                cancel(true);
                this.isInstallFinished = true;
            }
            Log.d(this.TAG, " Notify all ");
            notifyAll();
        }
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.OnstatusReturned
    public void packageUninstalled(String str, int i) {
        synchronized (this) {
            String str2 = this.TAG;
            Log.d(str2, " packageUninstalled() packagename: " + str + " returnCode: " + i);
            this.isUnInstallFinished = true;
            notifyAll();
        }
    }

    public void setHandler(Handler handler) {
        this.mHandlerWeakRef = new WeakReference(handler);
    }
}
