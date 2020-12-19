package com.sec.android.diagmonagent.log.provider.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.samsung.context.sdk.samsunganalytics.BuildConfig;
import com.sec.android.diagmonagent.common.logger.AppLog;

public class DiagMonUtil {
    public static final String AGREE_TYPE_DIAGNOSTIC = "D";
    public static final String AGREE_TYPE_SERVICE = "S";
    public static final String AGREE_TYPE_SERVICE_GED = "G";
    public static final String AGREE_TYPE_SERVICE_LEGACY = "Y";
    private static final String DMA_PKG_NAME = "com.sec.android.diagmonagent";
    private static final int DMA_SUPPORT_VERSION = 600000000;
    public static final int GED_DMA = 3;
    public static final int LEGACY_DMA = 1;
    private static final String MANUFACTURER_SAMSUNG = "samsung";
    private static final long MAX_USABLE_SIZE = 1073741824;
    private static final long MIN_USABLE_SIZE = 314572800;
    public static final int NEW_DMA = 2;
    public static final int NO_DMA = 0;
    public static final String SDK_TYPE_DEFAULT = "S";
    public static final String SDK_TYPE_GED = "G";
    private static final int SEP_VER_12_1 = 120100;
    public static final String TAG = ("DIAGMON_SDK[" + getSdkVersion() + "]");
    public static final Uri URI = Uri.parse("content://com.sec.android.log.diagmonagent/");

    public static String getSdkVersion() {
        try {
            return String.valueOf((int) BuildConfig.VERSION_CODE);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getSdkType(Context context) {
        return checkDMA(context) == 3 ? "G" : "S";
    }

    public static int checkDMA(Context context) {
        if (!"samsung".equalsIgnoreCase(Build.MANUFACTURER)) {
            return 3;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0).versionCode < DMA_SUPPORT_VERSION ? 1 : 2;
        } catch (PackageManager.NameNotFoundException e) {
            AppLog.e("DiagMonAgent isn't found: " + e.getMessage());
            return 0;
        }
    }

    public static String getPackageVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                return packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
            }
            return "";
        } catch (PackageManager.NameNotFoundException unused) {
            String str = TAG;
            Log.e(str, context.getPackageName() + " is not found");
            return "";
        }
    }

    public static void printResultfromDMA(Bundle bundle) {
        try {
            String string = bundle.getString("result");
            String string2 = bundle.getString("cause");
            if (string2 == null) {
                AppLog.i("Results : " + string);
                return;
            }
            AppLog.i("Results : " + string + ", Cause : " + string2);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static boolean insufficientStorage() {
        long totalSpace = (Environment.getDataDirectory().getTotalSpace() * 5) / 100;
        if (totalSpace > MAX_USABLE_SIZE) {
            totalSpace = 1073741824;
        } else if (totalSpace < MIN_USABLE_SIZE) {
            totalSpace = 314572800;
        }
        AppLog.d("Storage size threshold : " + totalSpace + " bytes");
        long usableSpace = Environment.getDataDirectory().getUsableSpace();
        if (usableSpace >= totalSpace) {
            return false;
        }
        AppLog.w("insufficient storage");
        AppLog.w("usableSpace: " + usableSpace + ", threshold: " + totalSpace);
        return true;
    }

    public static String getLegacyAuthority(String str) {
        return "com.sec.android.log." + str;
    }

    public static boolean isGED() {
        return !"samsung".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean underPalette() {
        return Build.VERSION.SEM_PLATFORM_INT < SEP_VER_12_1;
    }
}
