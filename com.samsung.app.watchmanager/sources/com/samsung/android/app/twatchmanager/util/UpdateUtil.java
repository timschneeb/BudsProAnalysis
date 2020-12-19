package com.samsung.android.app.twatchmanager.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.AppRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.update.UpdateCheckTask;
import com.samsung.android.app.twatchmanager.update.UpdateHistoryManager;
import com.samsung.android.app.twatchmanager.update.UpdateNotificationManager;
import com.samsung.android.app.watchmanager.R;
import d.a.a.b;
import d.a.a.d.a;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UpdateUtil {
    public static final String DOWNLOAD_PATH = "Download";
    private static final String TAG = ("tUHM:[Util]" + UpdateUtil.class.getSimpleName());
    private static final String TEST_FILE_NAME_FOR_BACKGROUND_UPDATE_TEST = "background_update.test";
    private static final String TEST_FILE_NAME_FOR_FAKE_SERVER = "fake_server";
    private static final String TEST_FILE_NAME_FOR_FREQUENT_UPDATE = "frequent_update_check.test";
    private static final String TEST_FILE_NAME_FOR_LOCAL_UPDATE_TEST = "local_update.test";
    private static final String TEST_FILE_NAME_FOR_ONLY_GEAR_UPDATE = "go_to_gearworld";
    private static final String TEST_FILE_NAME_FOR_PLAYSTORE_UPDATE_TEST = "playstore_update.test";
    private static final String TEST_FILE_NAME_FOR_SOMETIMES_UPDATE = "sometimes_update_check.test";
    private static final String TEST_FILE_NAME_FOR_UPDATE = "go_to_andromeda.test";
    public static final String UPDATE_FOLDER = "Update";
    private static volatile String sDownloadString = null;

    public static String changeContainerPackageForGear12S(String str) {
        String str2 = GlobalConst.PACKAGE_NAME_GEAR1.equals(str) ? GlobalConst.CONTAINER_PACKAGE_NAME_GEAR1 : GlobalConst.PACKAGE_NAME_GEAR2S.equals(str) ? GlobalConst.CONTAINER_PACKAGE_NAME_GEAR2S : str;
        String str3 = TAG;
        Log.d(str3, "changeContainerPackageForGear12S() packageName : " + str + " changeTo : " + str2);
        return str2;
    }

    public static void checkForceUpdateWithPackageName(String str) {
        List<AppRegistryData> queryAppRegistryDataByPackageName = RegistryAppsDBManager.queryAppRegistryDataByPackageName(str, TWatchManagerApplication.getAppContext());
        int i = 0;
        if (!queryAppRegistryDataByPackageName.isEmpty()) {
            int i2 = queryAppRegistryDataByPackageName.get(0).updateCancelCount;
            String str2 = TAG;
            Log.d(str2, "checkForceUpdateWithPackageName() current cancel count : " + i2);
            if (i2 <= 3) {
                i = i2 + 1;
            } else {
                return;
            }
        }
        RegistryAppsDBManager.updateAppUpdateCancelCount(str, i, TWatchManagerApplication.getAppContext());
    }

    public static void checkForceUpdateWithUpdateList(Set<String> set) {
        String str = TAG;
        Log.d(str, "checkForceUpdateWithUpdateList() starts... updateList : " + set);
        for (String str2 : set) {
            checkForceUpdateWithPackageName(str2);
        }
    }

    public static void clearUpdateCheckPref(Context context) {
        Log.d(TAG, "clearUpdateCheckPref() starts...");
        if (context != null) {
            SharedPreferences.Editor edit = context.getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0).edit();
            edit.remove(GlobalConst.PACKAGE_LIST);
            edit.apply();
        }
    }

    public static String getAbiType() {
        return Build.VERSION.SDK_INT < 21 ? "no" : Build.SUPPORTED_64_BIT_ABIS.length > 0 ? "64" : Build.SUPPORTED_32_BIT_ABIS.length > 0 ? "32" : "ex";
    }

    public static Set<String> getAppsUpdateList(Context context) {
        String str;
        String str2;
        Set<String> hashSet = new HashSet<>();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0);
            hashSet = sharedPreferences.getStringSet(GlobalConst.PACKAGE_LIST, hashSet);
            if (hashSet == null || hashSet.isEmpty()) {
                str = TAG;
                str2 = "getAppsUpdateList() packageList is null or empty";
            } else {
                HashSet<String> hashSet2 = new HashSet();
                for (String str3 : hashSet) {
                    String string = sharedPreferences.getString(str3 + "_" + StubAPIHelper.XMLResultKey.VERSION_NAME, null);
                    String versionName = HostManagerUtils.getVersionName(context, str3);
                    String str4 = TAG;
                    Log.d(str4, "getAppsUpdateList() installedAppVersion : " + versionName);
                    if (!TextUtils.isEmpty(versionName)) {
                        String str5 = TAG;
                        Log.d(str5, "getAppsUpdateList() app [" + str3 + "], savedAppVersion [" + string + "]");
                        if (string != null) {
                            if (versionName.compareTo(string) < 0) {
                            }
                        }
                    }
                    hashSet2.add(str3);
                }
                hashSet.removeAll(hashSet2);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                if (hashSet.isEmpty()) {
                    edit.remove(GlobalConst.PACKAGE_LIST);
                    UpdateNotificationManager.getInstance().cancel();
                } else {
                    edit.putStringSet(GlobalConst.PACKAGE_LIST, hashSet);
                }
                for (String str6 : hashSet2) {
                    Iterator<String> it = StubAPIHelper.RESULT_KEYSET.iterator();
                    while (it.hasNext()) {
                        edit.remove(str6 + "_" + it.next());
                    }
                }
                edit.apply();
                String str7 = TAG;
                Log.d(str7, "getAppsUpdateList() after verify.... result [" + hashSet + "]");
                return hashSet;
            }
        } else {
            str = TAG;
            str2 = "getAppsUpdateList() context is null";
        }
        Log.e(str, str2);
        String str72 = TAG;
        Log.d(str72, "getAppsUpdateList() after verify.... result [" + hashSet + "]");
        return hashSet;
    }

    public static String getFakeServerInstallPath() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + TEST_FILE_NAME_FOR_FAKE_SERVER;
        Log.d(TAG, "getLocalUpdateTestPath() path : " + str);
        return str;
    }

    public static String getLocalUpdateTestPath() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + TEST_FILE_NAME_FOR_LOCAL_UPDATE_TEST;
        Log.d(TAG, "getLocalUpdateTestPath() path : " + str);
        return str;
    }

    public static String getPD() {
        Log.d(TAG, "getPD()");
        return isTestMode4Update() ? "1" : UpdateCheckTask.RESULT_CANT_FIND_APP;
    }

    public static String getPathToDownload(Context context) {
        File filesDir;
        if (!(context == null || !TextUtils.isEmpty(sDownloadString) || (filesDir = context.getFilesDir()) == null)) {
            sDownloadString = filesDir.getAbsolutePath() + File.separator + DOWNLOAD_PATH + File.separator + UPDATE_FOLDER;
        }
        String str = TAG;
        Log.d(str, "getPathToDownload ends... path : " + sDownloadString + "");
        return sDownloadString;
    }

    public static b getPreviousUpdateCheckTime(Context context) {
        b bVar = null;
        if (context != null) {
            try {
                bVar = a.a("yyyy-MM-dd E HH:mm:ss").a(UpdateHistoryManager.getInstance().getLastCheckedTime());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            String str = TAG;
            Log.d(str, "getPreviousUpdateCheckTime() tUHMPreviouslyUpdatedAt : " + bVar);
        }
        return bVar;
    }

    public static int getThresholdTime() {
        return isSometimesUpdateCheckNeeded() ? GlobalConst.TIME_OUT_FOR_SOMETIMES_TESTING : isfrequentUpdateCheckNeeded() ? 10000 : GlobalConst.TIME_OUT;
    }

    public static boolean isBackgroundTestMode() {
        return HostManagerUtils.folderExistsInRootPath(TEST_FILE_NAME_FOR_BACKGROUND_UPDATE_TEST);
    }

    public static boolean isForceUpdateNeeded() {
        Log.d(TAG, "isForceUpdateNeeded() starts...");
        List<AppRegistryData> queryAppRegistryDataByPackageName = RegistryAppsDBManager.queryAppRegistryDataByPackageName("com.samsung.android.app.watchmanager", TWatchManagerApplication.getAppContext());
        if (!queryAppRegistryDataByPackageName.isEmpty()) {
            String str = TAG;
            Log.d(str, "isForceUpdateNeeded() tUHM case : update cancel count : " + queryAppRegistryDataByPackageName.get(0).updateCancelCount);
            if (queryAppRegistryDataByPackageName.get(0).updateCancelCount > 3) {
                return true;
            }
        }
        List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(TWatchManagerApplication.getAppContext());
        if (!queryLastLaunchDeviceRegistryData.isEmpty()) {
            List<AppRegistryData> queryAppRegistryDataByPackageName2 = RegistryAppsDBManager.queryAppRegistryDataByPackageName(queryLastLaunchDeviceRegistryData.get(0).packagename, TWatchManagerApplication.getAppContext());
            if (!queryAppRegistryDataByPackageName2.isEmpty()) {
                String str2 = TAG;
                Log.d(str2, "isForceUpdateNeeded() last launched plugin case : update cancel count : " + queryAppRegistryDataByPackageName2.get(0).updateCancelCount);
                if (queryAppRegistryDataByPackageName2.get(0).updateCancelCount > 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isForceUpdateNeeded(String str) {
        List<AppRegistryData> queryAppRegistryDataByPackageName = RegistryAppsDBManager.queryAppRegistryDataByPackageName(str, TWatchManagerApplication.getAppContext());
        if (queryAppRegistryDataByPackageName.isEmpty()) {
            return false;
        }
        int i = queryAppRegistryDataByPackageName.get(0).updateCancelCount;
        String str2 = TAG;
        Log.d(str2, "isForceUpdateNeeded() current cancel count : " + i);
        return i >= 3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0035  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isForceUpdateNeeded(java.util.Set<java.lang.String> r3) {
        /*
            java.lang.String r0 = com.samsung.android.app.twatchmanager.util.UpdateUtil.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isForceUpdateNeeded() starts... with updateList: "
            r1.append(r2)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.samsung.android.app.twatchmanager.log.Log.d(r0, r1)
            r0 = 0
            if (r3 == 0) goto L_0x0041
            java.lang.String r1 = "com.samsung.android.app.watchmanager"
            boolean r2 = r3.contains(r1)
            if (r2 == 0) goto L_0x002b
            r0 = 1
            r3 = 4
            android.content.Context r2 = com.samsung.android.app.twatchmanager.TWatchManagerApplication.getAppContext()
            com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager.updateAppUpdateCancelCount(r1, r3, r2)
            goto L_0x0041
        L_0x002b:
            java.util.Iterator r3 = r3.iterator()
        L_0x002f:
            boolean r1 = r3.hasNext()
            if (r1 == 0) goto L_0x0041
            java.lang.Object r0 = r3.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = isForceUpdateNeeded(r0)
            if (r0 == 0) goto L_0x002f
        L_0x0041:
            java.lang.String r3 = com.samsung.android.app.twatchmanager.util.UpdateUtil.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isForceUpdateNeeded() forceUpdateNeeded : "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            com.samsung.android.app.twatchmanager.log.Log.d(r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.UpdateUtil.isForceUpdateNeeded(java.util.Set):boolean");
    }

    public static boolean isLocalUpdateTestModeEnabled() {
        boolean folderExistsInRootPath = HostManagerUtils.folderExistsInRootPath(TEST_FILE_NAME_FOR_LOCAL_UPDATE_TEST);
        String str = TAG;
        Log.d(str, "isLocalUpdateTestModeEnabled() testMode : " + folderExistsInRootPath);
        return folderExistsInRootPath;
    }

    public static boolean isPlayStoreTestMode() {
        boolean folderExistsInRootPath = HostManagerUtils.folderExistsInRootPath(TEST_FILE_NAME_FOR_PLAYSTORE_UPDATE_TEST);
        String str = TAG;
        Log.d(str, "isPlayStoreTestMode() testMode : " + folderExistsInRootPath);
        return folderExistsInRootPath;
    }

    public static boolean isQAStoreConfirmed() {
        String str;
        String str2;
        boolean z = true;
        if (isTestMode4Update()) {
            Context appContext = TWatchManagerApplication.getAppContext();
            if (appContext == null) {
                z = false;
            } else if (HostManagerUtils.isSamsungDevice() || !HostManagerUtils.isPlayStoreAvailable(appContext)) {
                z = appContext.getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0).getBoolean(GlobalConst.QASTORE_CONFIRM, false);
            } else {
                str2 = TAG;
                str = "checkQAStoreUseConfirm() non samsung device and play store case, always return true";
            }
            String str3 = TAG;
            Log.d(str3, "checkQAStoreUseConfirm() end ... result : " + z);
            return z;
        }
        str2 = TAG;
        str = "checkQAStoreUseConfirm() it is not QA store mode, always return true";
        Log.d(str2, str);
        String str32 = TAG;
        Log.d(str32, "checkQAStoreUseConfirm() end ... result : " + z);
        return z;
    }

    public static boolean isSometimesUpdateCheckNeeded() {
        boolean folderExistsInRootPath = HostManagerUtils.folderExistsInRootPath(TEST_FILE_NAME_FOR_SOMETIMES_UPDATE);
        String str = TAG;
        Log.d(str, "isSometimesUpdateCheckNeeded() isSometimesUpdate : " + folderExistsInRootPath);
        return folderExistsInRootPath;
    }

    public static boolean isTUHMUpdated(Context context) {
        List<AppRegistryData> queryAppRegistryDataByPackageName = RegistryAppsDBManager.queryAppRegistryDataByPackageName("com.samsung.android.app.watchmanager", context);
        boolean z = false;
        if (!queryAppRegistryDataByPackageName.isEmpty()) {
            int i = -1;
            try {
                i = Integer.parseInt(queryAppRegistryDataByPackageName.get(0).version);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            int versionCode = HostManagerUtils.getVersionCode(context, "com.samsung.android.app.watchmanager");
            String str = TAG;
            Log.d(str, "isTUHMUpdated() prevVersionCode : " + i + " curVersionCode : " + versionCode);
            if (versionCode > i) {
                z = true;
            }
        }
        String str2 = TAG;
        Log.d(str2, "isTUHMUpdated() result : " + z);
        return z;
    }

    public static boolean isTestMode4Update() {
        boolean folderExistsInRootPath = HostManagerUtils.folderExistsInRootPath(TEST_FILE_NAME_FOR_UPDATE, TEST_FILE_NAME_FOR_ONLY_GEAR_UPDATE);
        String str = TAG;
        Log.d(str, "isTestMode4Update(): isTestMode : " + folderExistsInRootPath);
        return folderExistsInRootPath;
    }

    public static boolean isUpdateNotified(Context context) {
        if (context != null) {
            return context.getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0).getBoolean("IsUpdateNotified", false);
        }
        return false;
    }

    public static boolean isfrequentUpdateCheckNeeded() {
        boolean folderExistsInRootPath = HostManagerUtils.folderExistsInRootPath(TEST_FILE_NAME_FOR_FREQUENT_UPDATE);
        String str = TAG;
        Log.d(str, "isfrequentUpdateCheckNeeded() isFrequentUpdate : " + folderExistsInRootPath);
        return folderExistsInRootPath;
    }

    public static void sendBackupLogIntent() {
        Log.d(TAG, "sendBackupLogIntent() send broadcast for long-life logging");
        TWatchManagerApplication.getAppContext().sendBroadcast(new Intent(GlobalConst.ACTION_BACKUP_LOG));
    }

    public static void sendUpdateCompleteBroadcast(String str) {
        String str2 = TAG;
        Log.d(str2, "sendUpdateCompleteBroadcast() mBtAddress : " + str);
        Context appContext = TWatchManagerApplication.getAppContext();
        if (!TextUtils.isEmpty(str) && appContext != null) {
            List<DeviceRegistryData> queryDevicebyDeviceIdRegistryData = new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(str, appContext);
            if (!queryDevicebyDeviceIdRegistryData.isEmpty()) {
                String str3 = queryDevicebyDeviceIdRegistryData.get(0).packagename;
                String str4 = TAG;
                Log.d(str4, "sendUpdateCompleteBroadcast() send broadcast ... set packageName : " + str3);
                Intent intent = new Intent(GlobalConst.ACTION_PLUGIN_UPDATE_FINISH_IN_TUHM);
                intent.setPackage(str3);
                intent.putExtra(GlobalConst.DEVICE_ID, str);
                TWatchManagerApplication.getAppContext().sendBroadcast(intent);
            }
        }
    }

    public static void setQAStoreConfirm(boolean z) {
        String str = TAG;
        Log.d(str, "setQAStoreConfirm() starts... toValue : " + z);
        Context appContext = TWatchManagerApplication.getAppContext();
        if (appContext != null) {
            SharedPreferences.Editor edit = appContext.getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0).edit();
            edit.putBoolean(GlobalConst.QASTORE_CONFIRM, z);
            edit.apply();
        }
    }

    public static void setUpdateNotified(Context context, boolean z) {
        String str = TAG;
        Log.d(str, "setUpdateNotified() starts... toValue : " + z + " context : " + context);
        if (context != null) {
            SharedPreferences.Editor edit = context.getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0).edit();
            edit.putBoolean("IsUpdateNotified", z);
            edit.apply();
        }
    }

    public static void showUpdateCancelPopup(Activity activity, final View.OnClickListener onClickListener) {
        Log.d(TAG, "showUpdateCancelPopup() starts... ");
        if (activity != null) {
            try {
                final CommonDialog commonDialog = new CommonDialog(activity, 1, 0, 3);
                commonDialog.createDialog();
                commonDialog.setTextToOkBtn(activity.getString(R.string.uhm_update_cancel_popup_button_ok));
                commonDialog.setTextCapsForOkBtn(false);
                commonDialog.setTextCapsForCancelBtn(false);
                commonDialog.setTitle(activity.getResources().getString(R.string.uhm_update_cancel_popup_title));
                commonDialog.setMessage(activity.getResources().getString(R.string.uhm_update_cancel_popup_desc));
                commonDialog.setOkBtnListener(new View.OnClickListener() {
                    /* class com.samsung.android.app.twatchmanager.util.UpdateUtil.AnonymousClass1 */

                    public void onClick(View view) {
                        commonDialog.dismiss();
                        onClickListener.onClick(view);
                    }
                });
                commonDialog.setCancelBtnListener(new View.OnClickListener() {
                    /* class com.samsung.android.app.twatchmanager.util.UpdateUtil.AnonymousClass2 */

                    public void onClick(View view) {
                        Log.d(UpdateUtil.TAG, "onClick() cancel starts...");
                        commonDialog.dismiss();
                    }
                });
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updatePreviousTime(Context context) {
        String str = TAG;
        Log.d(str, "updatePreviousTime() starts..." + context);
        if (context != null) {
            d.a.a.d.b a2 = a.a("yyyy-MM-dd E HH:mm:ss");
            new b();
            String a3 = b.h().a(a2);
            String str2 = TAG;
            Log.d(str2, "updatePreviousTime() update tUHM previous update time : " + a3);
            UpdateHistoryManager.getInstance().setLastCheckedTime(a3);
        }
    }

    public static void updateTUHMVersionToDB(Context context) {
        RegistryAppsDBManager.updateAppVersion("com.samsung.android.app.watchmanager", HostManagerUtils.getVersionCode(context, "com.samsung.android.app.watchmanager"), context);
    }
}
