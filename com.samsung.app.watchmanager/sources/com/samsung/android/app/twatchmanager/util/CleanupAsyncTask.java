package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CleanupAsyncTask extends AsyncTask {
    private static final String TAG = ("tUHM:[Conn]" + CleanupAsyncTask.class.getSimpleName());
    private ICleanupTaskCallback mCallback;
    private String mContainerPackageToConnect;
    private Context mContext;
    private String mDeviceNameToConnect;

    public interface ICleanupTaskCallback {
        void onFinished();
    }

    public CleanupAsyncTask(Context context, ICleanupTaskCallback iCleanupTaskCallback, String str, String str2) {
        this.mContext = context;
        this.mCallback = iCleanupTaskCallback;
        this.mDeviceNameToConnect = str;
        this.mContainerPackageToConnect = str2;
    }

    private Set<String> getNonMultiConnectionPackageSet() {
        HashSet hashSet = new HashSet();
        Map<String, GearInfo> allGearInfo = GearRulesManager.getInstance().getAllGearInfo();
        if (allGearInfo != null) {
            for (String str : allGearInfo.keySet()) {
                GearInfo gearInfo = allGearInfo.get(str);
                if (!isSupportMultiConnection(gearInfo)) {
                    String containerPackageName = gearInfo.getContainerPackageName();
                    if (!TextUtils.equals(this.mContainerPackageToConnect, containerPackageName)) {
                        if (HostManagerUtils.isExistPackage(this.mContext, containerPackageName)) {
                            hashSet.addAll(getProvidersToDisable(containerPackageName));
                        }
                        String str2 = gearInfo.pluginPackage;
                        if (HostManagerUtils.isExistPackage(this.mContext, str2)) {
                            hashSet.add(str2);
                        }
                    }
                }
            }
        }
        return hashSet;
    }

    private Set<String> getProvidersToDisable(String str) {
        HashSet hashSet = new HashSet();
        HashMap<String, ArrayList<String>> allPackageInstallerInfo = InstallationUtils.getAllPackageInstallerInfo(this.mContext, str);
        if (allPackageInstallerInfo != null) {
            for (String str2 : allPackageInstallerInfo.keySet()) {
                hashSet.addAll(allPackageInstallerInfo.get(str2));
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (!HostManagerUtils.isExistPackage(this.mContext, str3) || !GearRulesManager.getInstance().isPackageCanDisable(str3)) {
                it.remove();
            }
        }
        String str4 = TAG;
        Log.d(str4, "getProvidersToDisable() containerPackage : " + str + " providers to disable : " + hashSet);
        return hashSet;
    }

    private boolean isSupportMultiConnection(GearInfo gearInfo) {
        return gearInfo != null && gearInfo.supportMultiConnection;
    }

    /* access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Object doInBackground(Object[] objArr) {
        GearRulesManager.getInstance().getGearInfo(this.mDeviceNameToConnect);
        Set<String> nonMultiConnectionPackageSet = getNonMultiConnectionPackageSet();
        String str = TAG;
        Log.d(str, "doInBackground() ** plugin/providers packages to disable :" + nonMultiConnectionPackageSet);
        for (String str2 : nonMultiConnectionPackageSet) {
            HostManagerUtils.disableApplication(this.mContext, str2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Object obj) {
        super.onPostExecute(obj);
        this.mCallback.onFinished();
        this.mContext = null;
        this.mCallback = null;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }
}
