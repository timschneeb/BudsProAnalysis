package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;

public class InstallationUtils {
    public static final int APP_STORE_PACKAGE_INSTALLED = 1;
    public static final int APP_STORE_PACKAGE_NOT_INSTALLED = 0;
    public static final String CONTAINER_APK_ASSETS_FOLDER = "GearModuleApks";
    public static final int CURRENT_OPERATION_DOWNLOADING = 602;
    public static final int CURRENT_OPERATION_ESSENTIAL_INSTALLATION = 603;
    public static final int CURRENT_OPERATION_INSTALLATION_NOT_REQUIRED = 605;
    public static final int CURRENT_OPERATION_SUPPORT_INSTALLATION = 604;
    public static final int CURRENT_OPERATION_UNKNOWN = 600;
    public static final int CURRENT_OPERATION_UPDATE_CHECKING = 601;
    public static String FAKE_SERVER_DOWNLOAD_FOLDER = "fake_server";
    public static final int HANDLE_PROVIDER_INSTALLATION = 205;
    public static final int INSTALLATION_COMPLETE = 202;
    public static final int INSTALLATION_PROGRESS = 201;
    public static final int INSTALLATION_TYPE_ESSENTIAL = 501;
    public static final int INSTALLATION_TYPE_SPECIAL = 502;
    public static final int MSG_FULL_INSTALLATION_COMPLETE = 315;
    public static final int MSG_INSTALLATION_FAILED = 317;
    public static final String MSG_INSTALLED_PACKAGE_INDEX = "package_index";
    public static final String MSG_INSTALLED_PACKAGE_NAME = "packageName";
    public static final int MSG_INSTALL_ESSENTIAL_DONE = 311;
    public static final int MSG_INSTALL_ESSENTIAL_PROGRESS = 310;
    public static final int MSG_INSTALL_ESSENTIAL_STARTED = 309;
    public static final int MSG_INSTALL_SPECIAL_FINISHED = 314;
    public static final int MSG_INSTALL_SPECIAL_PROGRESS = 313;
    public static final int MSG_INSTALL_SPECIAL_STARTED = 312;
    public static final int MSG_SUPPORT_INSTALLATION_COMPLETE = 316;
    public static final String PACKAGE_INSTALLER_INFO = "PackageInstallerInfo.xml";
    public static final String PARENT_PACKAGE_APP_STORE = "APP_STORE";
    public static final int PERMISSIONS_GLOBAL = 509;
    private static final String PREF_REINSTALL_PACKAGE_APK_MAP = "pref_reinstall_package_apk_map";
    private static final String PREF_REINSTALL_PARENT_PKG = "pref_reinstall_parent_package";
    public static final int START_INSTALLATION = 206;
    public static final int STATUS_BNR_PACKAGES_DONE = 802;
    public static final int STATUS_DISABLE_PACKAGES_ON_SWITCH_DONE = 803;
    public static final int STATUS_DOWNLOAD_FAILED = 307;
    public static final int STATUS_DOWNLOAD_NOT_REQUIRED = 714;
    public static final int STATUS_DOWNLOAD_PROGRESS = 710;
    public static final int STATUS_ESSENTIAL_DOWNLOAD_FAILED = 708;
    public static final int STATUS_ESSENTIAL_DOWNLOAD_START = 707;
    public static final int STATUS_ESSENTIAL_DOWNLOAD_SUCCESS = 709;
    public static final int STATUS_ESSENTIAL_DOWNLOAD_URL_NOT_AVAILABLE = 713;
    public static final int STATUS_INSTALL_FROM_PLAY_STORE = 717;
    public static final int STATUS_INSUFFICIENT_STORAGE = 716;
    public static final int STATUS_NO_NETWORK = 711;
    public static final int STATUS_NO_UPDATE = 308;
    public static final int STATUS_PACKAGES_VERIFICATION_DONE = 805;
    public static final int STATUS_REBOOT_PHONE = 718;
    public static final int STATUS_SET_PLUGIN_INFO = 719;
    public static final int STATUS_START_DOWNLOAD = 303;
    public static final int STATUS_UHM_TO_TUHM_CLEANUP_DONE = 801;
    public static final int STATUS_UNINSTALL_PACKAGES_DONE = 804;
    public static final int STATUS_UPDATE_CHECK = 301;
    public static final int STATUS_UPDATE_CHECK_COMPLETE = 302;
    public static final int STATUS_UPDATE_CHECK_FAILED = 702;
    public static final int STATUS_UPDATE_CHECK_START = 701;
    public static final int STATUS_UPDATE_DOWNLOADED = 306;
    public static final int STATUS_UPDATE_DOWNLOADING = 304;
    public static final int STATUS_UPDATE_DOWNLOAD_FAILED = 705;
    public static final int STATUS_UPDATE_DOWNLOAD_START = 704;
    public static final int STATUS_UPDATE_DOWNLOAD_SUCCESS = 706;
    public static final int STATUS_UPDATE_NOT_AVAILABLE = 703;
    private static final String[] SUPPORTED_APP_STORES = {GlobalConst.PACKAGE_NAME_PLAYSTORE, "com.tencent.android.qqdownloader", "com.baidu.appsearch"};
    public static final String TAG = ("tUHM:" + InstallationUtils.class.getSimpleName());
    public static final int UNINSTALLATION_COMPLETE = 204;
    public static final int UNINSTALLATION_PROGRESS = 203;

    public static int changeFilePermission(String str, int i) {
        String str2;
        StringBuilder sb;
        String str3 = TAG;
        Log.d(str3, "changeFilePermission(" + str + ", " + i + ")");
        int i2 = -1000;
        try {
            i2 = ((Integer) Class.forName("android.os.FileUtils").getMethod("setPermissions", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE).invoke(null, str, Integer.valueOf(i), -1, -1)).intValue();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (ExceptionInInitializerError e2) {
            e2.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (NoSuchMethodError e3) {
            e3.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (IllegalAccessException e5) {
            e5.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (IllegalAccessError e6) {
            e6.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (IllegalArgumentException e7) {
            e7.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (LinkageError e8) {
            e8.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (InvocationTargetException e9) {
            e9.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (RuntimeException e10) {
            e10.printStackTrace();
            str2 = TAG;
            sb = new StringBuilder();
        } catch (Throwable th) {
            String str4 = TAG;
            Log.d(str4, "changeFilePermission()-->result = " + -1000);
            throw th;
        }
        sb.append("changeFilePermission()-->result = ");
        sb.append(i2);
        Log.d(str2, sb.toString());
        return i2;
    }

    public static void cleardumpStorage(Context context, String str) {
        String str2;
        String str3;
        StringBuilder sb;
        File file = new File(str);
        if (file.exists()) {
            if (file.delete()) {
                str3 = TAG;
                sb = new StringBuilder();
                sb.append(str);
                str2 = " deleted!!";
            } else {
                str3 = TAG;
                sb = new StringBuilder();
                sb.append(str);
                str2 = " delete failed!!";
            }
            sb.append(str2);
            Log.d(str3, sb.toString());
        }
    }

    public static void copyFileInternally(InputStream inputStream, OutputStream outputStream) {
        if (outputStream != null && inputStream != null) {
            int available = inputStream.available();
            if (available > 1000000) {
                available = 1000000;
            }
            byte[] bArr = new byte[available];
            while (true) {
                int read = inputStream.read(bArr);
                if (read > 0) {
                    outputStream.write(bArr, 0, read);
                } else {
                    outputStream.flush();
                    return;
                }
            }
        }
    }

    public static void deleteAllContent(File file) {
        File[] listFiles;
        Log.d(TAG, "deleteAllContent():" + file);
        if (file != null) {
            if (file.isDirectory() && (listFiles = file.listFiles()) != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    deleteAllContent(file2);
                }
            }
            Log.d(TAG, "deleteAllContent() path: " + file + " Result: " + file.delete());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00ae A[SYNTHETIC, Splitter:B:47:0x00ae] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d2 A[SYNTHETIC, Splitter:B:64:0x00d2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.HashMap<java.lang.String, java.util.ArrayList<java.lang.String>> getAllPackageInstallerInfo(android.content.Context r10, java.lang.String r11) {
        /*
        // Method dump skipped, instructions count: 221
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.InstallationUtils.getAllPackageInstallerInfo(android.content.Context, java.lang.String):java.util.HashMap");
    }

    public static String getApkNameFromPreference(Context context, String str) {
        return context.getSharedPreferences(PREF_REINSTALL_PACKAGE_APK_MAP, 0).getString(str, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getAppName(android.content.Context r3, java.lang.String r4, java.lang.String r5) {
        /*
            if (r3 == 0) goto L_0x001b
            if (r5 == 0) goto L_0x001b
            android.content.res.Resources r0 = r3.getResources()
            java.lang.String r1 = "."
            java.lang.String r2 = "_"
            java.lang.String r1 = r5.replace(r1, r2)
            int r3 = com.samsung.android.app.twatchmanager.util.ResourceLoader.getStringId(r3, r1)
            if (r3 == 0) goto L_0x001b
            java.lang.String r3 = r0.getString(r3)
            goto L_0x001c
        L_0x001b:
            r3 = 0
        L_0x001c:
            if (r3 != 0) goto L_0x002c
            if (r4 == 0) goto L_0x002c
            com.samsung.android.app.twatchmanager.manager.GearRulesManager r0 = com.samsung.android.app.twatchmanager.manager.GearRulesManager.getInstance()
            com.samsung.android.app.twatchmanager.model.GearInfo r0 = r0.getGearInfo(r4)
            if (r0 == 0) goto L_0x002c
            java.lang.String r3 = r0.pluginAppName
        L_0x002c:
            if (r3 != 0) goto L_0x002f
            r3 = r5
        L_0x002f:
            java.lang.String r0 = com.samsung.android.app.twatchmanager.util.InstallationUtils.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "#getAppName() device_name:"
            r1.append(r2)
            r1.append(r4)
            java.lang.String r4 = ", packageName :"
            r1.append(r4)
            r1.append(r5)
            java.lang.String r4 = ", return :"
            r1.append(r4)
            r1.append(r3)
            java.lang.String r4 = r1.toString()
            com.samsung.android.app.twatchmanager.log.Log.d(r0, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.InstallationUtils.getAppName(android.content.Context, java.lang.String, java.lang.String):java.lang.String");
    }

    public static String getAppNameFromPreference(Context context, String str) {
        return context.getSharedPreferences(GlobalConst.REINSTALL_PREFERENCE_APP_NAME, 0).getString(str, null);
    }

    public static String getContainerPackage(String str) {
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(str);
        String containerPackage = GearRulesManager.getInstance().getContainerPackage(simpleBTNameByName);
        String str2 = TAG;
        Log.d(str2, "getContainerPackage() deviceFixedName: " + str + ", simpleDeviceName :" + simpleBTNameByName + ", cPackageName :" + containerPackage);
        return containerPackage;
    }

    public static String getEssentialFolderName() {
        return "GearModuleApks";
    }

    public static InstallPack getLocalInstallPack(Context context, String str) {
        String[] list;
        Log.d(TAG, " getLocalInstallPack()");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FAKE_SERVER_DOWNLOAD_FOLDER);
        Log.d(TAG, " getLocalInstallPack() fakeServer.exists(): " + file.exists());
        if (!file.exists() || (list = file.list()) == null || list.length <= 0) {
            return null;
        }
        Log.d(TAG, " getLocalInstallPack() pkgName:" + str);
        for (String str2 : list) {
            if (str2.endsWith(".apk")) {
                String str3 = file.getAbsolutePath() + File.separator + str2;
                String packageName = getPackageName(context, str3);
                Log.d(TAG, " getLocalInstallPack() apkPath:" + str3 + " packageName:" + packageName);
                if (packageName != null && packageName.equals(str)) {
                    InstallPack installPack = new InstallPack(str2, str, str3, true);
                    Log.d(TAG, "getLocalInstallPack()  InstallPack:" + installPack);
                    return installPack;
                }
            }
        }
        return null;
    }

    public static ArrayList<String> getPackageInstallerInfo(Context context, String str, String str2) {
        return getPackageInstallerInfo(context, str, str2, !HostManagerUtils.isSamsungDevice());
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ff A[SYNTHETIC, Splitter:B:64:0x00ff] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0121 A[SYNTHETIC, Splitter:B:81:0x0121] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<java.lang.String> getPackageInstallerInfo(android.content.Context r10, java.lang.String r11, java.lang.String r12, boolean r13) {
        /*
        // Method dump skipped, instructions count: 300
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.InstallationUtils.getPackageInstallerInfo(android.content.Context, java.lang.String, java.lang.String, boolean):java.util.ArrayList");
    }

    public static String getPackageName(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageArchiveInfo = packageManager != null ? packageManager.getPackageArchiveInfo(str, 0) : null;
        return packageArchiveInfo != null ? packageArchiveInfo.packageName : "";
    }

    public static String getParentPackageFromPreference(Context context, String str) {
        return context.getSharedPreferences(PREF_REINSTALL_PARENT_PKG, 0).getString(str, null);
    }

    public static String getPathForExtStorage() {
        return Environment.getExternalStorageDirectory().getPath() + File.separator + "Gear";
    }

    public static String getProviderDumpPath(Context context) {
        Log.d(TAG, "getProviderDumpPath()");
        if (context == null) {
            return null;
        }
        File file = new File(context.getFilesDir().getAbsolutePath() + File.separator + "Dump");
        if (!file.exists()) {
            file.mkdirs();
        }
        String absolutePath = file.getAbsolutePath();
        try {
            changeFilePermission(absolutePath, PERMISSIONS_GLOBAL);
            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
            return absolutePath;
        }
    }

    public static String getTargetAppStorePkg() {
        Context appContext = TWatchManagerApplication.getAppContext();
        String[] strArr = SUPPORTED_APP_STORES;
        for (String str : strArr) {
            if (HostManagerUtils.isExistPackage(appContext, str)) {
                Log.e(TAG, "getTargetAppStorePkg() targetAppStore:" + str);
                if (!GlobalConst.PACKAGE_NAME_PLAYSTORE.equalsIgnoreCase(str) || isInstalledFromPlaystore(appContext)) {
                    return str;
                }
            }
        }
        Log.d(TAG, "getTargetAppStorePkg() targetAppStore:null");
        return null;
    }

    public static int getVersionCode(String str) {
        PackageManager packageManager = TWatchManagerApplication.getAppContext().getPackageManager();
        int i = 0;
        if (packageManager != null) {
            try {
                i = packageManager.getPackageInfo(str, 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "pm is null");
        }
        String str2 = TAG;
        Log.d(str2, "getVersionCode() packageName:" + str + " returns " + i);
        return i;
    }

    public static boolean hasApksInAsset(Context context, String str) {
        Object e;
        StringBuilder sb;
        String str2;
        boolean z = false;
        try {
            String[] list = context.getPackageManager().getResourcesForApplication(str).getAssets().list(getEssentialFolderName());
            if (list != null && list.length > 0) {
                z = true;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e = e2;
            str2 = TAG;
            sb = new StringBuilder();
            sb.append("hasApksInAsset() ");
            sb.append(e);
            Log.d(str2, sb.toString());
            String str3 = TAG;
            Log.d(str3, "hasApksInAsset() package:" + str + " returns: " + z);
            return z;
        } catch (IOException e3) {
            e = e3;
            str2 = TAG;
            sb = new StringBuilder();
            sb.append("hasApksInAsset() ");
            sb.append(e);
            Log.d(str2, sb.toString());
            String str32 = TAG;
            Log.d(str32, "hasApksInAsset() package:" + str + " returns: " + z);
            return z;
        }
        String str322 = TAG;
        Log.d(str322, "hasApksInAsset() package:" + str + " returns: " + z);
        return z;
    }

    public static boolean hasInstallPermission(Context context) {
        boolean z = false;
        if (context != null) {
            if (context.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0) {
                z = true;
            }
            String str = TAG;
            Log.d(str, "hasInstallPermission() return:" + z);
        }
        return z;
    }

    public static boolean isChinaLocalStoreExist(Context context) {
        int i = 1;
        while (true) {
            String[] strArr = SUPPORTED_APP_STORES;
            if (i >= strArr.length) {
                return false;
            }
            if (HostManagerUtils.isExistPackage(context, strArr[i])) {
                String str = TAG;
                Log.d(str, "isChinaLocalStoreExist()  store = " + SUPPORTED_APP_STORES[i]);
                return true;
            }
            i++;
        }
    }

    public static boolean isDeviceSupportsIntStorage() {
        return true;
    }

    public static boolean isInstallFromPlaystore(Context context) {
        boolean z = !hasInstallPermission(context) && HostManagerUtils.isExistPackage(context, GlobalConst.PACKAGE_NAME_PLAYSTORE);
        String str = TAG;
        Log.d(str, "isInstallFromPlaystore()  return :" + z);
        return z;
    }

    public static boolean isInstallRequiredExceptionalCase(Context context, String str) {
        boolean z = (TextUtils.equals(GlobalConst.CONTAINER_PACKAGE_NAME_GEAR2S, str) || TextUtils.equals(GlobalConst.CONTAINER_PACKAGE_NAME_GEAR1, str)) && (!HostManagerUtils.isExistPackage(context, "com.samsung.accessory.goproviders") || !HostManagerUtils.isExistPackage(context, "com.samsung.accessory.saproviders") || !HostManagerUtils.isExistPackage(context, "com.sec.android.fotaprovider"));
        String str2 = TAG;
        Log.d(str2, "isInstallRequiredExceptionalCase() supportPackage:" + str + " returns :" + z);
        return z;
    }

    public static boolean isInstalledByTuhm(Context context, String str) {
        PackageManager packageManager;
        return (context == null || str == null || (packageManager = context.getPackageManager()) == null || !context.getPackageName().equals(packageManager.getInstallerPackageName(str))) ? false : true;
    }

    public static boolean isInstalledFromPlaystore(Context context) {
        String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        String str = TAG;
        Log.d(str, "isInstalledFromPlaystore()  installer :" + installerPackageName);
        return GlobalConst.PACKAGE_NAME_PLAYSTORE.equalsIgnoreCase(installerPackageName);
    }

    public static boolean isLocalInstallation() {
        boolean z;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FAKE_SERVER_DOWNLOAD_FOLDER);
        if (file.exists()) {
            String str = TAG;
            Log.d(str, FAKE_SERVER_DOWNLOAD_FOLDER + " exists ! isDirectory:" + file.isDirectory());
            String[] list = file.list();
            String str2 = TAG;
            Log.d(str2, "isLocalInstallation() apkList:" + Arrays.toString(list));
            if (list != null && list.length > 0) {
                z = true;
                String str3 = TAG;
                Log.d(str3, "isLocalInstallation()  returns :" + z);
                return z;
            }
        }
        z = false;
        String str32 = TAG;
        Log.d(str32, "isLocalInstallation()  returns :" + z);
        return z;
    }

    public static boolean isSupportPackageInstalled(Context context, String str) {
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(str);
        String str2 = TAG;
        Log.d(str2, "isSupportPackageInstalled() SimpleDeviceName: " + simpleBTNameByName);
        String supportPackage = GearRulesManager.getInstance().getSupportPackage(simpleBTNameByName);
        boolean isExistPackage = HostManagerUtils.isExistPackage(context, supportPackage);
        String str3 = TAG;
        Log.d(str3, " isSupportPackageInstalled() deviceName : " + simpleBTNameByName + " cPackageName :" + supportPackage + " isExist :" + isExistPackage);
        return isExistPackage;
    }

    public static ArrayList<InstallPack> loadPackageInfo(Context context, String str) {
        ArrayList<InstallPack> arrayList = new ArrayList<>();
        File file = new File(str);
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                String packageName = getPackageName(context, listFiles[i].getAbsolutePath());
                arrayList.add(new InstallPack(listFiles[i].getName(), packageName, listFiles[i].getAbsolutePath(), getVersionCode(packageName)));
            }
        } else {
            String str2 = TAG;
            Log.d(str2, "dumpFolder :" + file.getAbsolutePath() + " is not a directory");
        }
        return arrayList;
    }

    public static void setAppNameForReinstallToast(Context context, String str) {
        if (context != null) {
            SharedPreferences.Editor edit = context.getSharedPreferences(GlobalConst.REINSTALL_PREFERENCE_APP_NAME, 0).edit();
            if (str != null) {
                String applicationLabel = HostManagerUtils.getApplicationLabel(context, str);
                String str2 = TAG;
                Log.d(str2, " AppName : " + applicationLabel);
                edit.putString(str, applicationLabel);
            }
            edit.apply();
        }
    }

    public static void setPackageApkNameForReinstall(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_REINSTALL_PACKAGE_APK_MAP, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static void setParentPackageForReinstall(Context context, String str, String str2) {
        if (context != null) {
            SharedPreferences.Editor edit = context.getSharedPreferences(PREF_REINSTALL_PARENT_PKG, 0).edit();
            edit.putString(str, str2);
            edit.apply();
        }
    }

    private static void skip(XmlPullParser xmlPullParser) {
        if (xmlPullParser.getEventType() == 2) {
            int i = 1;
            while (i != 0) {
                int next = xmlPullParser.next();
                if (next == 2) {
                    i++;
                } else if (next == 3) {
                    i--;
                }
            }
            return;
        }
        throw new IllegalStateException();
    }
}
