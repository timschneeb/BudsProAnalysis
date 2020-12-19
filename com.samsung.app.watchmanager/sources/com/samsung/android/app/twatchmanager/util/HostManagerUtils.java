package com.samsung.android.app.twatchmanager.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.factory.ActivityManagerFactory;
import com.samsung.android.app.twatchmanager.factory.SystemPropertyFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.PluginExecutor;
import com.samsung.android.app.watchmanager.setupwizard.ContactUs;
import com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment;
import com.samsung.android.app.watchmanager.setupwizard.PermissionFragment;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.GlobalConst;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class HostManagerUtils {
    private static final String AUTO_SWITCH_BROADCAST = "com.samsung.android.watchmanager.ACTION_HM_REQUEST_CONNECT";
    private static final long MB_IN_BYTES = 1048576;
    private static final String PREF_REBOOT_REQUIRED = "rebood_required_pref";
    public static final int REQUEST_REMOVE_ALL_TASKS = 2;
    public static final int SAMSUNG_FLAG_ENABLE_STATUSBAR_OPEN_BY_NOTIFICATION = 2;
    private static final String TAG = ("tUHM:" + HostManagerUtils.class.getSimpleName());
    private static String mDeviceType;
    private static double sDeviceTotalMemoryInMB = -1.0d;

    public static final boolean DEBUGGABLE() {
        return false;
    }

    public static boolean changeComponentState(Context context, String str, int i) {
        String str2 = TAG;
        Log.d(str2, "changeComponentState() change state for [" + str + "] to  " + i);
        try {
            PackageManager packageManager = context.getPackageManager();
            String str3 = TAG;
            Log.d(str3, "changeComponentState() pm : " + packageManager);
            if (packageManager != null) {
                packageManager.setApplicationEnabledSetting(str, i, 0);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String str4 = null;
            Toaster.show(context, ((String) null) + " " + str);
            if (i == 1) {
                str4 = "Cannot enable ";
            } else if (i == 2) {
                str4 = "Cannot disable";
            }
            String str5 = TAG;
            Log.d(str5, "changeComponentState() " + str4 + " " + str);
        }
        return false;
    }

    public static boolean conditionForSAPReboot() {
        boolean z = isSystemApp(GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY) && Build.VERSION.SDK_INT >= 23;
        String str = TAG;
        Log.d(str, "conditionForSAPReboot, result [" + z + "]");
        return z;
    }

    public static void disableApplication(Context context, String str) {
        String str2 = TAG;
        Log.d(str2, "disableApplication() context : " + context);
        if (context != null) {
            boolean changeComponentState = changeComponentState(context, str, 2);
            String str3 = TAG;
            Log.d(str3, "disableApplication() " + str + " is disabled? : " + changeComponentState);
        }
    }

    public static void enableApplication(Context context, String str) {
        if (context == null) {
            Log.d(TAG, "enalbeApplication() context is null");
        } else if (!isApplicationEnabled(context, str)) {
            changeComponentState(context, str, 1);
        }
    }

    public static boolean folderExistsInPath(String str, String... strArr) {
        File[] listFiles = new File(str).listFiles();
        HashSet hashSet = new HashSet(Arrays.asList(strArr));
        boolean z = false;
        if (listFiles != null) {
            int length = listFiles.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String lowerCase = listFiles[i].getName().toLowerCase();
                if (hashSet.contains(lowerCase)) {
                    String str2 = TAG;
                    Log.d(str2, "folderExistsInPath(): found one : " + lowerCase);
                    z = true;
                    break;
                }
                i++;
            }
        }
        String str3 = TAG;
        Log.d(str3, "folderExistsInPath(): result : " + z);
        return z;
    }

    public static boolean folderExistsInRootPath(String... strArr) {
        return folderExistsInPath(Environment.getExternalStorageDirectory().getAbsolutePath(), strArr);
    }

    public static String getApplicationLabel(Context context, String str) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        ApplicationInfo applicationInfo = null;
        if (packageManager != null) {
            try {
                applicationInfo = packageManager.getApplicationInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                String str2 = TAG;
                Log.d(str2, " Name not found :" + str);
            }
        } else {
            Log.e(TAG, "pm is null");
        }
        String str3 = (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "(unknown)");
        String str4 = TAG;
        Log.d(str4, " AppName : " + str3);
        return str3;
    }

    public static double getDeviceMemorySize(Context context) {
        if (sDeviceTotalMemoryInMB < 0.0d) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
            sDeviceTotalMemoryInMB = (double) (((float) memoryInfo.totalMem) / 1048576.0f);
        }
        return sDeviceTotalMemoryInMB;
    }

    public static String getInstallerPackage(Context context, String str) {
        String str2;
        try {
            str2 = context.getPackageManager().getInstallerPackageName(str);
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            str2 = null;
        }
        String str3 = TAG;
        Log.d(str3, "isInstalledFromPlaystore() " + str + "'s  installer :" + str2);
        return str2;
    }

    public static Intent getPluginIntent(String str) {
        Intent intent = new Intent(GlobalConst.ACTION_STEALTH_MODE);
        intent.setPackage(str);
        return intent;
    }

    public static boolean getRebootRequiredForGearFit2FromPref() {
        boolean z = TWatchManagerApplication.getAppContext().getSharedPreferences(PREF_REBOOT_REQUIRED, 0).getBoolean(PREF_REBOOT_REQUIRED, false);
        String str = TAG;
        Log.d(str, "getRebootRequiredForGearFit2FromPref() returns :" + z);
        return z;
    }

    public static String getResolution(Context context) {
        if (context == null) {
            Log.e(TAG, "getResolution() has null context");
            return "unknown";
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        String str = displayMetrics.heightPixels + "x" + i;
        if (!isSamsungDevice()) {
            Log.d(TAG, "getResolution()::Resolution is fixed as 1920x1080 for nonSamsung device.");
            str = "1920x1080";
        }
        Log.d(TAG, "getResolution = " + str);
        return str;
    }

    public static long getStorageBytesUntilLow(Context context) {
        Log.d(TAG, "getStorageBytesUntilLow()");
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        File dataDirectory = Environment.getDataDirectory();
        try {
            Long l = (Long) StorageManager.class.getMethod("getStorageBytesUntilLow", File.class).invoke(storageManager, dataDirectory);
            if (l != null) {
                return l.longValue();
            }
            return 524288000;
        } catch (Exception e) {
            e.printStackTrace();
            return 524288000;
        }
    }

    public static int getVersionCode(Context context, String str) {
        PackageInfo packageInfo = null;
        if (context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    packageInfo = packageManager.getPackageInfo(str, 128);
                } else {
                    Log.e(TAG, "package manager is null");
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getVersionCode:");
        sb.append(str);
        sb.append(" return: ");
        sb.append(packageInfo != null ? packageInfo.versionCode : -1);
        Log.d(str2, sb.toString());
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return -1;
    }

    public static String getVersionName(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return "";
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return "";
            }
            String str2 = TAG;
            Log.e(str2, "getVersionName() packageName : " + str + " versionName : " + packageInfo.versionName);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void handleTaskInternal() {
        Log.d(TAG, "handleTaskInternal() starts...");
        ActivityManager activityManager = (ActivityManager) TWatchManagerApplication.getAppContext().getSystemService("activity");
        if (activityManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 21) {
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
            if (runningTasks != null) {
                for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                    String packageName = runningTaskInfo.baseActivity.getPackageName();
                    if (packageName != null && packageName.equals(TWatchManagerApplication.getAppContext().getPackageName())) {
                        if (TWatchManagerApplication.getAppContext().checkCallingOrSelfPermission("android.permission.REMOVE_TASKS") == 0) {
                            ActivityManagerFactory.get().removeTask(activityManager, runningTaskInfo.id);
                            Log.d(TAG, "handleTaskInternal() request to remove task");
                        } else {
                            Log.d(TAG, "No permission to remove task");
                        }
                    }
                }
                return;
            }
            return;
        }
        List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();
        if (appTasks != null) {
            for (ActivityManager.AppTask appTask : appTasks) {
                try {
                    appTask.finishAndRemoveTask();
                    Log.d(TAG, "handleTaskInternal() request to remove task");
                } catch (IllegalArgumentException e) {
                    Log.w(TAG, "task.finishAndRemoveTask() ", e);
                }
            }
        }
    }

    public static Locale handleUnsupportableLocaleUpdate(Locale locale) {
        String str = TAG;
        Log.d(str, "handleUnsupportableLocaleUpdate : " + locale);
        if (Build.VERSION.SDK_INT < 24) {
            return locale;
        }
        Object build = new Locale.Builder().setLanguage("zh").setScript("Hans").setRegion("SG").build();
        Object build2 = new Locale.Builder().setLanguage("zh").setScript("Hans").setRegion("MO").build();
        Object build3 = new Locale.Builder().setLanguage("zh").setScript("Hans").setRegion("CN").build();
        Object build4 = new Locale.Builder().setLanguage("zh").setRegion("CN").build();
        Object build5 = new Locale.Builder().setLanguage("zh").setScript("Hant").setRegion("MO").build();
        Object build6 = new Locale.Builder().setLanguage("zh").setScript("Hans").setRegion("HK").build();
        Object build7 = new Locale.Builder().setLanguage("zh").setScript("Hant").setRegion("HK").build();
        Object build8 = new Locale.Builder().setLanguage("zh").setRegion("HK").build();
        Object build9 = new Locale.Builder().setLanguage("zh").setScript("Hant").setRegion("TW").build();
        Object build10 = new Locale.Builder().setLanguage("zh").setRegion("TW").build();
        Object[][] objArr = {new Locale[]{build, build4}, new Locale[]{build2, build4}, new Locale[]{build3, build4}, new Locale[]{build5, build8}, new Locale[]{build6, build8}, new Locale[]{build7, build8}, new Locale[]{build9, build10}, new Locale[]{new Locale.Builder().setLanguage("es").setRegion("MX").build(), new Locale.Builder().setLanguage("es").setRegion("US").build()}};
        for (int i = 0; i < objArr.length; i++) {
            if (locale.equals(objArr[i][0])) {
                return objArr[i][1];
            }
        }
        return locale;
    }

    public static boolean hasEnoughStorage(Context context, long j) {
        if (context == null) {
            return true;
        }
        long storageBytesUntilLow = getStorageBytesUntilLow(context);
        String str = TAG;
        Log.d(str, "hasEnoughStorage() requiredStorage:" + j + " availableStorage:" + storageBytesUntilLow);
        return storageBytesUntilLow - j > 0;
    }

    public static boolean hasPermissionToDisable(String str) {
        Context appContext = TWatchManagerApplication.getAppContext();
        PackageManager packageManager = appContext.getPackageManager();
        boolean z = false;
        if (packageManager != null) {
            try {
                z = packageManager.getPackageInfo(appContext.getPackageName(), 0).sharedUserId.equals(packageManager.getPackageInfo(str, 0).sharedUserId);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "hasPermissionToDisable(), package manager is null");
        }
        String str2 = TAG;
        Log.d(str2, "hasPermissionToDisable() packageName:" + str + " return :" + z);
        return z;
    }

    public static boolean isApplicationEnabled(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                int applicationEnabledSetting = packageManager.getApplicationEnabledSetting(str);
                String str2 = TAG;
                Log.d(str2, "isEnabled [" + str + "] :: value = " + applicationEnabledSetting);
                return applicationEnabledSetting != 2;
            }
            Log.e(TAG, "package manager is null");
            return true;
        } catch (IllegalArgumentException unused) {
            Log.d(TAG, "isEnabled :: IllegalArgumentException");
            return true;
        }
    }

    public static boolean isChinaPhone(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if ("CN".equals(telephonyManager.getNetworkCountryIso())) {
            return true;
        }
        return telephonyManager.getSimState() == 5 && "CN".equals(telephonyManager.getSimCountryIso());
    }

    private static boolean isEngBinary() {
        return "eng".equals(Build.TYPE);
    }

    public static boolean isEngBuild() {
        String str = TAG;
        Log.d(str, "Build.Type : " + Build.TYPE);
        return "eng".equals(Build.TYPE);
    }

    public static boolean isExistPackage(Context context, String str) {
        PackageInfo packageInfo = null;
        if (context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    packageInfo = packageManager.getPackageInfo(str, 128);
                } else {
                    Log.e(TAG, "package manager is null");
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("isExistPackage:");
        sb.append(str);
        sb.append(" return: ");
        sb.append(packageInfo != null);
        Log.d(str2, sb.toString());
        return packageInfo != null;
    }

    public static boolean isGS3Model() {
        String str = Build.MODEL;
        Log.d(TAG, "isGS3Model()::MODEL = " + str);
        for (String str2 : GlobalConst.CONNECTION_AVAILABLE_LIST_OF_1G_PHONE) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isJapanModel() {
        String systemProperty = SystemPropertyFactory.getAndroidSystemProperty().getSystemProperty("ro.csc.sales_code");
        String str = TAG;
        Log.d(str, "Sales code = " + systemProperty);
        return "DCM".equals(systemProperty) || "KDI".equals(systemProperty) || "XJP".equals(systemProperty) || "SBM".equals(systemProperty) || "PNG".equals(systemProperty) || "VFJ".equals(systemProperty);
    }

    public static boolean isMaximumPowerSavingMode() {
        boolean z = true;
        if (1 != Settings.System.getInt(TWatchManagerApplication.getAppContext().getContentResolver(), SystemPropertyFactory.getAndroidSystemProperty().ULTRA_POWERSAVING_MODE(), 0)) {
            z = false;
        }
        String str = TAG;
        Log.d(str, "isMaximumPowerSavingMode, res [" + z + "]");
        return z;
    }

    public static boolean isNewGearUpdate(Context context) {
        Log.d(TAG, "isNewGearUpdate()");
        Set<String> appsUpdateList = UpdateUtil.getAppsUpdateList(context);
        boolean contains = appsUpdateList.contains("com.samsung.android.app.watchmanager");
        String str = TAG;
        Log.d(str, "isNewGearUpdate(), packageList [" + appsUpdateList + "] result [" + contains + "]");
        return contains;
    }

    public static boolean isPlayStoreAvailable(Context context) {
        if (context == null) {
            context = TWatchManagerApplication.getAppContext();
        }
        boolean isExistPackage = isExistPackage(context, GlobalConst.PACKAGE_NAME_PLAYSTORE);
        boolean equals = GlobalConst.PACKAGE_NAME_PLAYSTORE.equals(getInstallerPackage(context, "com.samsung.android.app.watchmanager"));
        boolean isPlayStoreTestMode = UpdateUtil.isPlayStoreTestMode();
        String str = TAG;
        Log.d(str, "isPlayStoreAvailable() testMode : " + isPlayStoreTestMode + " exists : " + isExistPackage + " installerCheck : " + equals);
        return isPlayStoreTestMode || (isExistPackage && equals);
    }

    public static boolean isRebootRequired(Context context, String str) {
        boolean z;
        boolean rebootRequiredForGearFit2FromPref = getRebootRequiredForGearFit2FromPref();
        boolean isRequiredRebootBySAPteam = isRequiredRebootBySAPteam(context);
        String str2 = TAG;
        Log.d(str2, "isRebootRequired() rebootRequired:" + rebootRequiredForGearFit2FromPref);
        String str3 = TAG;
        Log.d(str3, "isRebootRequired() rebootRequiredBySAPteam:" + isRequiredRebootBySAPteam);
        if (str == null || !str.toLowerCase(Locale.ENGLISH).contains("gear fit2") || (!rebootRequiredForGearFit2FromPref && !isRequiredRebootBySAPteam)) {
            z = false;
        } else {
            z = true;
            Log.d(TAG, "isRebootRequired() issued SAP detected");
        }
        String str4 = TAG;
        Log.d(str4, "isRebootRequired() gearName:" + str + " returns :" + z);
        return z;
    }

    private static boolean isRequiredRebootBySAPteam(Context context) {
        Log.d(TAG, "isRequiredRebootBySAPteam()");
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY, 128);
            String str = TAG;
            Log.d(str, "isRequiredRebootBySAPteam(), current SAP [" + packageInfo.versionCode + "]");
            if ((packageInfo.versionCode != 325 && packageInfo.versionCode != 326) || !conditionForSAPReboot()) {
                return false;
            }
            long currentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            String str2 = TAG;
            Log.i(str2, "isRequiredRebootBySAPteam(), bootTime : " + currentTimeMillis);
            if (currentTimeMillis >= packageInfo.lastUpdateTime) {
                return false;
            }
            Log.i(TAG, "isRequiredRebootbySAPteam(), No reboot after updating application");
            return true;
        } catch (PackageManager.NameNotFoundException | NullPointerException unused) {
            Log.e(TAG, "isRequiredRebootBySAPteam(), NameNotFoundException");
            return false;
        }
    }

    public static boolean isSamsungChinaModel() {
        String str;
        boolean z = false;
        if (isSamsungDevice()) {
            str = SystemPropertyFactory.getAndroidSystemProperty().getSystemProperty("ro.csc.sales_code");
            if ("CHN".equals(str) || "CHM".equals(str) || "CHC".equals(str) || "CHU".equals(str) || "CTC".equals(str)) {
                z = true;
            }
        } else {
            str = null;
        }
        String str2 = TAG;
        Log.d(str2, "isSamsungChinaModel, res [" + z + "], salesCode [" + str + "]");
        return z;
    }

    public static boolean isSamsungDevice() {
        if (isSamsungGEDModel()) {
            Log.d(TAG, " isSamsungDevice() return false ");
            return false;
        } else if (!InstallationUtils.hasInstallPermission(TWatchManagerApplication.getAppContext())) {
            return false;
        } else {
            String str = Build.MANUFACTURER;
            boolean equalsIgnoreCase = str.equalsIgnoreCase("SAMSUNG");
            String str2 = TAG;
            Log.d(str2, " isSamsungDevice() MANUFACTURER :" + str + " return :" + equalsIgnoreCase);
            return equalsIgnoreCase;
        }
    }

    public static boolean isSamsungDeviceWithCustomBinary() {
        boolean z = !InstallationUtils.hasInstallPermission(TWatchManagerApplication.getAppContext()) && "samsung".equalsIgnoreCase(Build.MANUFACTURER);
        String str = TAG;
        Log.d(str, "isSamsungDeviceWithCustomBinary, res [" + z + "]");
        return z;
    }

    public static boolean isSamsungGEDModel() {
        String str = Build.MODEL;
        if (str == null) {
            return false;
        }
        if (!str.contains("SM-G900FG") && !str.contains("GT-I9505G")) {
            return false;
        }
        String str2 = TAG;
        Log.d(str2, "SamsungGEDModel : " + str);
        return true;
    }

    public static boolean isSupportButtonShapes() {
        boolean z = isSamsungDevice() && Build.VERSION.SDK_INT >= 23;
        String str = TAG;
        Log.d(str, "isSupportButtonShapes() return:" + z);
        return z;
    }

    public static boolean isSystemApp(String str) {
        PackageManager packageManager = TWatchManagerApplication.getAppContext().getPackageManager();
        ApplicationInfo applicationInfo = null;
        if (packageManager != null) {
            try {
                applicationInfo = packageManager.getApplicationInfo(str, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        boolean z = true;
        if (applicationInfo == null || (applicationInfo.flags & 1) == 0) {
            z = false;
        }
        String str2 = TAG;
        Log.d(str2, "isSystemApp() packageName:" + str + " return :" + z);
        return z;
    }

    public static boolean isTablet() {
        String str = mDeviceType;
        if (str != null && str.length() > 0) {
            return mDeviceType.contains("tablet");
        }
        mDeviceType = SystemPropertyFactory.getAndroidSystemProperty().getSystemProperty("ro.build.characteristics");
        String str2 = mDeviceType;
        return str2 != null && str2.contains("tablet");
    }

    public static boolean isTopActivity(Context context, String str) {
        String str2 = TAG;
        Log.d(str2, "isTopActivity() - " + str);
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(2);
        if (runningTasks != null) {
            for (int i = 0; i < runningTasks.size(); i++) {
                String className = runningTasks.get(i).topActivity.getClassName();
                String str3 = TAG;
                Log.d(str3, "topActivity = " + className);
                if (className != null && className.contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean less1_5GbMemory(Context context) {
        double deviceMemorySize = getDeviceMemorySize(context);
        boolean z = deviceMemorySize < 1024.0d;
        String str = TAG;
        Log.d(str, "less1_5GbMemory, memory [" + deviceMemorySize + "], result [" + z + "]");
        return z;
    }

    public static void openBrowser(Context context, String str) {
        String str2 = TAG;
        Log.d(str2, "openBrowser [" + str + "]");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException | SecurityException e) {
            Log.e(TAG, "unable to open the link");
            e.printStackTrace();
        }
    }

    public static void removeAllTasks(final long j) {
        Log.d(TAG, "removeAllTasks() without activity");
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.android.app.twatchmanager.util.HostManagerUtils.AnonymousClass2 */

            public void run() {
                String str = HostManagerUtils.TAG;
                Log.d(str, "removeAllTasks() started with delay : " + j);
                HostManagerUtils.handleTaskInternal();
            }
        }, j);
    }

    public static void removeAllTasks(final Activity activity, final long j) {
        Log.d(TAG, "removeAllTasks() with activity");
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.android.app.twatchmanager.util.HostManagerUtils.AnonymousClass1 */

            public void run() {
                String str = HostManagerUtils.TAG;
                Log.d(str, "removeAllTasks() started with delay : " + j);
                activity.finish();
                HostManagerUtils.handleTaskInternal();
            }
        }, j);
    }

    public static void resetBootRequiredFlag() {
        saveRebootRequiredForGearFit2(false);
    }

    public static void saveRebootRequiredForGearFit2(boolean z) {
        Log.d(TAG, "saveRebootRequiredForGearFit2()");
        SharedPreferences.Editor edit = TWatchManagerApplication.getAppContext().getSharedPreferences(PREF_REBOOT_REQUIRED, 0).edit();
        edit.putBoolean(PREF_REBOOT_REQUIRED, z);
        edit.apply();
    }

    public static void sendAutoSwitchBroadCast(Context context, String str, String str2, String str3) {
        String str4 = TAG;
        Log.d(str4, "sendAutoSwitchBroadCast() pluginPackage:" + str + " deviceAddress:" + str2 + " deviceName:" + str3);
        Intent intent = new Intent(AUTO_SWITCH_BROADCAST);
        intent.putExtra(HMConnectFragment.EXTRA_DEVICE_ADDRESS, str2);
        intent.putExtra(SetupWizardWelcomeActivity.EXTRA_IS_AUTO_SWITCH, "true");
        intent.setPackage(str);
        if (isExistPackage(context, str)) {
            RegistryDbManagerWithProvider registryDbManagerWithProvider = new RegistryDbManagerWithProvider();
            List<DeviceRegistryData> queryDevicebyDeviceIdRegistryData = registryDbManagerWithProvider.queryDevicebyDeviceIdRegistryData(str2, context);
            if (str2 == null || queryDevicebyDeviceIdRegistryData.size() == 0) {
                String str5 = TAG;
                Log.d(str5, "device [" + str2 + "] does not exist in DB, lets add");
                registryDbManagerWithProvider.addDeviceRegistryData(new DeviceRegistryData(str, str3, str2, 1, 0, HostManagerUtilsRulesBTDevices.supportsPairing(str3)), context);
            } else {
                String str6 = TAG;
                Log.d(str6, "device [" + str2 + "] already in DB");
            }
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            try {
                e.printStackTrace();
            } catch (RuntimeException e2) {
                e2.printStackTrace();
                Log.d(TAG, "sendAutoSwitchBroadCast() return false");
                Toaster.show(context, "No plug-in");
            }
        }
        context.sendBroadcast(intent);
        Log.d(TAG, "sendAutoSwitchBroadCast() return true");
    }

    public static void setStatusBarOpenByNotification(Activity activity) {
        String str = TAG;
        Log.d(str, "setStatusBarOpenByNotification(" + activity + ")");
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        try {
            Field declaredField = attributes.getClass().getDeclaredField("samsungFlags");
            declaredField.setInt(attributes, declaredField.getInt(attributes) | 2);
            window.setAttributes(attributes);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    public static void startContactUsProcess(final Activity activity) {
        Log.d(TAG, "OnClick Contact us");
        PermissionFragment.verifyPermissions(activity, new PermissionFragment.IGrantedTask() {
            /* class com.samsung.android.app.twatchmanager.util.HostManagerUtils.AnonymousClass3 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.PermissionFragment.IGrantedTask
            public void doTask() {
                if (HostManagerUtils.isSamsungDevice()) {
                    HostManagerUtils.startSamsungMembers(activity);
                    return;
                }
                Activity activity = activity;
                HostManagerUtils.openBrowser(activity, new ContactUs(activity).getApplicationURI());
            }
        }, PermissionUtils.INITIAL_PERMISSION_FOR_CONTACT_US);
    }

    public static void startPluginActivity(Activity activity, String str, String str2, String str3, String str4, int i, boolean z) {
        PluginExecutor.requestStartPlugin(activity, str, str2, str3, str4, i, z, null);
    }

    public static void startSamsungMembers(Context context) {
        if (isExistPackage(context, GlobalConst.PACKAGE_NAME_SAMSUNG_MEMBERS) && InstallationUtils.getVersionCode(GlobalConst.PACKAGE_NAME_SAMSUNG_MEMBERS) >= 170001000) {
            startSamsungMembersApp(context);
        } else {
            openBrowser(context, new ContactUs(context).getBrowserURI());
        }
    }

    public static void startSamsungMembersApp(Context context) {
        String packageName = context.getPackageName();
        String applicationURI = new ContactUs(context).getApplicationURI();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/contactUs"));
        intent.putExtra("packageName", packageName);
        intent.putExtra("appId", GlobalConst.SCS_CLIENT_ID_OF_SM);
        intent.putExtra("appName", "Samsung Gear");
        intent.putExtra("faqUrl", applicationURI);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyPluginActivity(Context context, String str) {
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(getPluginIntent(str), 65536);
        boolean z = queryIntentActivities != null && queryIntentActivities.size() > 0;
        String str2 = TAG;
        Log.d(str2, "verifyPluginActivity [" + z + "]");
        return z;
    }
}
