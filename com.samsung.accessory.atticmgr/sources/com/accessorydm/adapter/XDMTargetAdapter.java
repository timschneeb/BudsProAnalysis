package com.accessorydm.adapter;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.db.file.XDBAdapter;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.fotaprovider.log.Log;

public class XDMTargetAdapter implements XDMDefInterface, XDMInterface {
    public static String AFOTA_DIR_PATH = "afota";
    public static String FOTA_DIR_PATH;

    public static String xdmGetTargetLanguage() {
        String str;
        String str2;
        Configuration configuration = XDMDmUtils.getContext().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT < 24) {
            str = configuration.locale.getLanguage();
            str2 = configuration.locale.getCountry();
        } else {
            str = configuration.getLocales().get(0).getLanguage();
            str2 = configuration.getLocales().get(0).getCountry();
        }
        String format = String.format("%s-%s", str, str2);
        Log.I("language : " + format);
        return format;
    }

    public static void xdmInitStorageState() {
        Log.I("");
        if (TextUtils.isEmpty(FOTA_DIR_PATH)) {
            FOTA_DIR_PATH = XDMDmUtils.getContext().getFilesDir().getAbsolutePath();
        }
        Log.H("FOTA_DIR_PATH [" + FOTA_DIR_PATH + "]");
    }

    public static boolean xdmGetStorageAvailable() {
        String xdmGetStoragePath = xdmGetStoragePath();
        if (TextUtils.isEmpty(xdmGetStoragePath)) {
            return false;
        }
        return XDBAdapter.xdbFolderExist(xdmGetStoragePath);
    }

    public static long xdmGetAvailableStorageSize() {
        String xdmGetStoragePath = xdmGetStoragePath();
        if (TextUtils.isEmpty(xdmGetStoragePath)) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(xdmGetStoragePath);
            return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        } catch (Exception e) {
            Log.printStackTrace(e);
            return 0;
        }
    }

    public static long xdmGetTotalStorageSize() {
        String xdmGetStoragePath = xdmGetStoragePath();
        if (TextUtils.isEmpty(xdmGetStoragePath)) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(xdmGetStoragePath);
            return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
        } catch (Exception e) {
            Log.printStackTrace(e);
            return 0;
        }
    }

    private static String xdmGetStoragePath() {
        String str = FOTA_DIR_PATH;
        if (TextUtils.isEmpty(str)) {
            str = XDMDmUtils.getContext().getFilesDir().getAbsolutePath();
            FOTA_DIR_PATH = str;
        }
        if (TextUtils.isEmpty(str)) {
            Log.E("getStoragePath is empty");
        }
        return str;
    }

    public static boolean xdmGetCheckWifiOnlyModel() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) XDMDmUtils.getContext().getSystemService("connectivity");
            PackageManager packageManager = XDMDmUtils.getContext().getPackageManager();
            ConnectivityManager connectivityManager2 = connectivityManager;
            if (connectivityManager == null) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        Log.E(e.toString());
                    }
                    Log.I("connectivity is null, retry...");
                    connectivityManager2 = (ConnectivityManager) XDMDmUtils.getContext().getSystemService("connectivity");
                    if (connectivityManager2 != null) {
                        break;
                    }
                }
            }
            boolean z = connectivityManager2 != null ? !packageManager.hasSystemFeature("android.hardware.telephony") : false;
            Log.I("isWifiOnly : " + z);
            return z;
        } catch (Exception e2) {
            Log.E(e2.toString());
            return false;
        }
    }
}
