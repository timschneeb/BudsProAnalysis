package com.samsung.android.app.twatchmanager.update;

import android.content.SharedPreferences;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateInstallData {
    public static final String INSTALL_DATA_BACKUP_PREF = "install_data_backup_pref";
    public static final String PREF_KEY_INSTALL_REQUESTED = "PREF_KEY_INSTALL_REQUESTED";
    public static final String TAG = ("tUHM:[Update]" + UpdateInstallData.class.getSimpleName());
    private Map<String, InstallPack> mData;
    private List<String> mInstallOrder;

    /* access modifiers changed from: private */
    public static class LazyHolder {
        private static final UpdateInstallData INSTANCE = new UpdateInstallData();

        private LazyHolder() {
        }
    }

    private UpdateInstallData() {
        this.mData = new HashMap();
        this.mInstallOrder = new ArrayList();
    }

    public static UpdateInstallData getInstance() {
        Log.d(TAG, "getInstance()");
        return LazyHolder.INSTANCE;
    }

    public static boolean isNonSamsungInstallRequested() {
        boolean z = TWatchManagerApplication.getAppContext().getSharedPreferences(INSTALL_DATA_BACKUP_PREF, 0).getBoolean(PREF_KEY_INSTALL_REQUESTED, false);
        String str = TAG;
        Log.d(str, "isNonSamsungInstallRequested() requested : " + z);
        return z;
    }

    public static void setNonSamsungInstallRequested(boolean z) {
        String str = TAG;
        Log.d(str, "setNonSamsungInstallRequested() change it to : " + z);
        SharedPreferences.Editor edit = TWatchManagerApplication.getAppContext().getSharedPreferences(INSTALL_DATA_BACKUP_PREF, 0).edit();
        edit.putBoolean(PREF_KEY_INSTALL_REQUESTED, z);
        edit.apply();
    }

    public InstallPack getCurrentInstallPack() {
        InstallPack installPack;
        if (!this.mInstallOrder.isEmpty()) {
            installPack = this.mData.get(this.mInstallOrder.get(0));
        } else {
            installPack = null;
        }
        String str = TAG;
        Log.d(str, "getCurrentInstallPack() will return : " + installPack);
        return installPack;
    }

    public boolean hasPackageToInstall() {
        return !this.mInstallOrder.isEmpty();
    }

    public void initData(List<InstallPack> list) {
        this.mInstallOrder.clear();
        this.mData.clear();
        if (list != null) {
            boolean z = false;
            for (InstallPack installPack : list) {
                if (GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY.equals(installPack.packName)) {
                    z = true;
                } else {
                    this.mInstallOrder.add(installPack.packName);
                }
                this.mData.put(installPack.packName, installPack);
            }
            if (z) {
                this.mInstallOrder.add(GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY);
            }
        }
    }

    public InstallPack popInstallPack() {
        InstallPack currentInstallPack = getCurrentInstallPack();
        if (currentInstallPack != null) {
            this.mData.remove(currentInstallPack.packName);
            this.mInstallOrder.remove(currentInstallPack.packName);
        }
        String str = TAG;
        Log.d(str, "popInstallPack() to pop : " + currentInstallPack + " isRemoved : " + false);
        return currentInstallPack;
    }
}
