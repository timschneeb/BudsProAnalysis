package com.samsung.android.fotaprovider.util;

import android.content.Context;
import android.content.Intent;
import com.accessorydm.db.file.XDBLastUpdateAdp;
import com.accessorydm.db.file.XDBLastUpdateInfo;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.android.fotaagent.FotaNoticeIntent;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.galaxywearable.BroadcastHelper;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import com.sec.android.fotaprovider.BuildConfig;
import java.io.File;

public class FotaProviderUtil {
    private static String getBandPackageName() {
        return "com.samsung.android.neatplugin";
    }

    private static String getEarbudsPackageNamePostfix() {
        return "mgr";
    }

    private static String getEarbudsPackageNamePrefix() {
        return "com.samsung.accessory.";
    }

    private static String getGearFit2PackageName() {
        return "com.samsung.android.gearfit2plugin";
    }

    private static String getGearModulesPackageNamePrefix() {
        return "com.samsung.android.gear";
    }

    public static String getSingleFotaProviderPackageName() {
        return BuildConfig.LIBRARY_PACKAGE_NAME;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00fe, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0103, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0104, code lost:
        r3.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0107, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x010a, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x010f, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0110, code lost:
        r3.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0113, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyLogToSdcard(android.content.Context r12, java.lang.String r13) {
        /*
        // Method dump skipped, instructions count: 305
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaprovider.util.FotaProviderUtil.copyLogToSdcard(android.content.Context, java.lang.String):void");
    }

    public static void setFilePermissions(File file) {
        if (file.exists()) {
            if (!file.setReadable(true, false)) {
                Log.W("setPermissions() : setReadable FAIL");
            }
            if (!file.setWritable(true, false)) {
                Log.W("setPermissions() : setWritable FAIL");
            }
            if (!file.setExecutable(true, false)) {
                Log.W("setPermissions() : setWritable FAIL");
                return;
            }
            return;
        }
        Log.W("setPermissions() : file not exist");
    }

    public static DeviceType getDeviceType() {
        String packageName = FotaProviderInitializer.getContext().getPackageName();
        if (packageName.equals(getBandPackageName())) {
            return DeviceType.BAND;
        }
        if (packageName.startsWith(getEarbudsPackageNamePrefix()) && packageName.contains(getEarbudsPackageNamePostfix())) {
            return DeviceType.EARBUDS;
        }
        if (packageName.equals(getGearFit2PackageName())) {
            return DeviceType.GEARFIT2;
        }
        if (packageName.startsWith(getGearModulesPackageNamePrefix()) || isSingleFotaProvider()) {
            return DeviceType.WATCH;
        }
        Log.W("Unknown package. Set as default to watch");
        return DeviceType.WATCH;
    }

    public static boolean isSingleFotaProvider() {
        return FotaProviderInitializer.getContext().getPackageName().equals(getSingleFotaProviderPackageName());
    }

    public static String generateLogTagByPackageName(Context context, String str) {
        String str2;
        if (!isSingleFotaProvider()) {
            try {
                String[] split = context.getPackageName().split("\\.");
                str2 = split[split.length - 1];
            } catch (IndexOutOfBoundsException unused) {
                str2 = "UNKNOWN";
            }
        } else {
            str2 = "TEST";
        }
        return str + "_" + str2.toUpperCase();
    }

    public static void sendLastCheckedDate() {
        long currentTimeMillis = System.currentTimeMillis();
        Log.I("Current time : " + currentTimeMillis);
        Intent intent = new Intent(FotaNoticeIntent.INTENT_LAST_CHECKED_DATE);
        intent.putExtra(XDBInterface.XDM_SQL_DB_POLLING_TIME, currentTimeMillis);
        intent.setPackage(FotaProviderInitializer.getContext().getPackageName());
        BroadcastHelper.sendBroadcast(intent);
    }

    public static void sendIntentUpdateInProgress() {
        Log.D("");
        Intent intent = new Intent(FotaNoticeIntent.INTENT_UPDATE_IN_PROGRESS);
        intent.setPackage(FotaProviderInitializer.getContext().getPackageName());
        BroadcastHelper.sendBroadcast(intent);
    }

    public static void sendLastUpdateInfo() {
        XDBLastUpdateInfo lastUpdateInfo = XDBLastUpdateAdp.getLastUpdateInfo();
        long lastUpdateDate = lastUpdateInfo != null ? lastUpdateInfo.getLastUpdateDate() : 0;
        Log.I("send last update time: " + lastUpdateDate);
        Intent intent = new Intent(FotaNoticeIntent.INTENT_LAST_UPDATE_INFO);
        intent.putExtra(XDBInterface.XDM_SQL_DB_POLLING_TIME, lastUpdateDate);
        intent.setPackage(FotaProviderInitializer.getContext().getPackageName());
        BroadcastHelper.sendBroadcast(intent);
    }
}
