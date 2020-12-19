package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.packagecontroller.PackageControllerFactory;
import com.samsung.android.app.twatchmanager.receiver.PackageControlReceiver;
import com.samsung.android.app.watchmanager.libinterface.IInstaller;
import com.samsung.android.app.watchmanager.libinterface.OnstatusReturned;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UninstallAsyncTask extends AsyncTask<String, String, Integer> implements OnstatusReturned {
    private String TAG = ("tUHM:" + UninstallAsyncTask.class.getSimpleName());
    private volatile boolean isUnInstallFinished;
    private List<String> mApplicationList;
    private Handler mHandler;
    private IInstaller mPackageController;

    public UninstallAsyncTask(Context context, List<String> list, Handler handler) {
        this.mApplicationList = list;
        this.mHandler = handler;
        Log.i(this.TAG, "Creating UninstallAsyncTask");
        try {
            this.mPackageController = PackageControllerFactory.getInstaller(context);
            this.mPackageController.SetOnStatusReturned(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public Integer doInBackground(String... strArr) {
        for (String str : this.mApplicationList) {
            try {
                this.isUnInstallFinished = false;
                String str2 = this.TAG;
                Log.d(str2, "Uninstall :" + str);
                this.mPackageController.uninstallPackage(str);
                PackageControlReceiver.addBlockedPackage(str);
                synchronized (this) {
                    while (!this.isUnInstallFinished && !isCancelled()) {
                        try {
                            Log.d(this.TAG, "waiting for uninstall response from PM");
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (isCancelled()) {
                    return 0;
                }
                int indexOf = this.mApplicationList.indexOf(str);
                Log.d(this.TAG, "Publishing progress");
                publishProgress(String.valueOf(indexOf), str);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
        this.mHandler.sendEmptyMessage(0);
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Integer num) {
        this.mHandler.sendEmptyMessage(InstallationUtils.UNINSTALLATION_COMPLETE);
        Log.d(this.TAG, "Uninstallation is done for all packages");
        IInstaller iInstaller = this.mPackageController;
        if (iInstaller != null) {
            iInstaller.SetOnStatusReturned(null);
        }
        this.mPackageController = null;
        this.mHandler = null;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(String... strArr) {
        Log.d(this.TAG, "onProgressUpdate: ");
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = InstallationUtils.UNINSTALLATION_PROGRESS;
        Bundle bundle = new Bundle();
        bundle.putInt(InstallationUtils.MSG_INSTALLED_PACKAGE_INDEX, Integer.parseInt(strArr[0]));
        bundle.putString("packageName", strArr[1]);
        obtainMessage.setData(bundle);
        this.mHandler.sendMessage(obtainMessage);
        String str = this.TAG;
        Log.d(str, "Uninstallation is done package: " + strArr[1]);
        super.onProgressUpdate((Object[]) strArr);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.OnstatusReturned
    public void packageInstalled(String str, int i) {
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
}
