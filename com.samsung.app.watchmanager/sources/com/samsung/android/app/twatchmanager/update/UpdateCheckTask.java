package com.samsung.android.app.twatchmanager.update;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.BaseUpdateTask;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsDBOperations;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UpdateCheckTask extends BaseUpdateTask {
    public static final String RESULT_CANT_FIND_APP = "0";
    public static final String RESULT_CURRENT_IS_LATEST = "1";
    public static final String RESULT_REQUEST_BLOCKED_FROM_SERVER = "-1";
    public static final String RESULT_UPDATE_IS_AVAILABLE = "2";
    public static final String TAG = ("tUHM:" + UpdateCheckTask.class.getSimpleName());
    private static final int TUHM_FIRST_VERSION_CODE = 1;
    private static final String TUHM_FIRST_VERSION_NAME = "1.0.0";
    private Set<String> mLocalUpdatePackageSet = new HashSet();

    public UpdateCheckTask(Set<String> set, BaseUpdateTask.IUpdateTaskCallback iUpdateTaskCallback) {
        super(set, iUpdateTaskCallback);
        initLocalUpdatePackageSet();
    }

    private int getContentSizeInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getPackageVersionCode(String str) {
        PackageManager.NameNotFoundException e;
        PackageManager packageManager = TWatchManagerApplication.getAppContext().getPackageManager();
        int i = -1;
        String str2 = null;
        if (packageManager != null) {
            try {
                if (packageManager.getApplicationInfo(str, 0) != null) {
                    String str3 = TAG;
                    Log.d(str3, "getPackageVersionCode() " + str);
                    PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                    if (packageInfo != null) {
                        int i2 = packageInfo.versionCode;
                        try {
                            str2 = packageInfo.versionName;
                            i = i2;
                        } catch (PackageManager.NameNotFoundException e2) {
                            e = e2;
                            i = i2;
                            e.printStackTrace();
                            String str4 = TAG;
                            Log.d(str4, "getPackageVersionCode() return info... *" + i + "*(" + str2 + ")");
                            return String.valueOf(i);
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e3) {
                e = e3;
                e.printStackTrace();
                String str42 = TAG;
                Log.d(str42, "getPackageVersionCode() return info... *" + i + "*(" + str2 + ")");
                return String.valueOf(i);
            }
        }
        String str422 = TAG;
        Log.d(str422, "getPackageVersionCode() return info... *" + i + "*(" + str2 + ")");
        return String.valueOf(i);
    }

    private void handleUpdateCheckResult(ArrayList<StubAPIHelper.XMLResult> arrayList) {
        boolean z;
        this.mResultMap.clear();
        this.mTotalContentSize = 0;
        Iterator<StubAPIHelper.XMLResult> it = arrayList.iterator();
        while (it.hasNext()) {
            StubAPIHelper.XMLResult next = it.next();
            Log.d(TAG, "handleUpdateCheckResult() result..\n" + next.printAllData());
            String str = next.get(StubAPIHelper.XMLResultKey.APP_ID);
            String str2 = next.get(StubAPIHelper.XMLResultKey.RESULT_CODE);
            String str3 = next.get(StubAPIHelper.XMLResultKey.VERSION_NAME);
            UpdateHistoryManager.getInstance().setUpdateHistory(next);
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                if (str.equalsIgnoreCase(UpdateUtil.changeContainerPackageForGear12S(HostManagerUtilsDBOperations.getLastLaunchedPackageName(TWatchManagerApplication.getAppContext()))) || str.equalsIgnoreCase("com.samsung.android.app.watchmanager") || str.equalsIgnoreCase(GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY)) {
                    if (this.mLocalUpdatePackageSet.contains(str)) {
                        z = true;
                        next.put(StubAPIHelper.XMLResultKey.RESULT_CODE.toString(), "2");
                    } else {
                        z = false;
                    }
                    if ("2".equals(str2) || z) {
                        this.mTotalContentSize += getContentSizeInt(next.get(StubAPIHelper.XMLResultKey.CONTENT_SIZE));
                        this.mResultMap.put(str, next);
                    } else if ("1".equals(str2)) {
                        RegistryAppsDBManager.updateAppUpdateCancelCount(str, 0, TWatchManagerApplication.getAppContext());
                    }
                }
            }
        }
        if (UpdateUtil.isTUHMUpdated(TWatchManagerApplication.getAppContext())) {
            UpdateUtil.updateTUHMVersionToDB(TWatchManagerApplication.getAppContext());
        }
    }

    private void initLocalUpdatePackageSet() {
        if (UpdateUtil.isLocalUpdateTestModeEnabled()) {
            File[] listFiles = new File(UpdateUtil.getLocalUpdateTestPath()).listFiles();
            for (File file : listFiles) {
                if (file != null && file.isFile()) {
                    this.mLocalUpdatePackageSet.add(InstallationUtils.getPackageName(TWatchManagerApplication.getAppContext(), file.getAbsolutePath()));
                }
            }
            Log.d(TAG, "initLocalUpdatePackageSet() mLocalUpdatePackageSet : " + this.mLocalUpdatePackageSet);
        }
    }

    /* access modifiers changed from: protected */
    public Void doInBackground(Void... voidArr) {
        Log.d(TAG, "run() start update check thread...");
        if (!initialCheck()) {
            return null;
        }
        ArrayList<StubAPIHelper.XMLResult> arrayList = new ArrayList<>();
        for (String str : this.mPackageNameSet) {
            if (HostManagerUtils.isExistPackage(TWatchManagerApplication.getAppContext(), str)) {
                ArrayList<StubAPIHelper.XMLResult> stubUpdateCheck = this.mHelper.stubUpdateCheck(str, getPackageVersionCode(str));
                String str2 = TAG;
                Log.d(str2, "run() stubUpdateCheck result... : " + stubUpdateCheck);
                if (stubUpdateCheck != null && !stubUpdateCheck.isEmpty()) {
                    arrayList.addAll(stubUpdateCheck);
                }
            } else {
                String str3 = TAG;
                Log.d(str3, "run() package : " + str + " doesn't exist, ** can't check update");
            }
        }
        handleUpdateCheckResult(arrayList);
        return null;
    }
}
