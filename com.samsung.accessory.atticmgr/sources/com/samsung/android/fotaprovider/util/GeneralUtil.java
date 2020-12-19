package com.samsung.android.fotaprovider.util;

import android.app.ActivityManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.PowerManager;
import com.accessorydm.interfaces.XCommonInterface;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import java.util.Calendar;
import java.util.List;

public class GeneralUtil {
    private static final Signature GSF_SIGNATURES = new Signature("308204433082032ba003020102020900c2e08746644a308d300d06092a864886f70d01010405003074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f6964301e170d3038303832313233313333345a170d3336303130373233313333345a3074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f696430820120300d06092a864886f70d01010105000382010d00308201080282010100ab562e00d83ba208ae0a966f124e29da11f2ab56d08f58e2cca91303e9b754d372f640a71b1dcb130967624e4656a7776a92193db2e5bfb724a91e77188b0e6a47a43b33d9609b77183145ccdf7b2e586674c9e1565b1f4c6a5955bff251a63dabf9c55c27222252e875e4f8154a645f897168c0b1bfc612eabf785769bb34aa7984dc7e2ea2764cae8307d8c17154d7ee5f64a51a44a602c249054157dc02cd5f5c0e55fbef8519fbe327f0b1511692c5a06f19d18385f5c4dbc2d6b93f68cc2979c70e18ab93866b3bd5db8999552a0e3b4c99df58fb918bedc182ba35e003c1b4b10dd244a8ee24fffd333872ab5221985edab0fc0d0b145b6aa192858e79020103a381d93081d6301d0603551d0e04160414c77d8cc2211756259a7fd382df6be398e4d786a53081a60603551d2304819e30819b8014c77d8cc2211756259a7fd382df6be398e4d786a5a178a4763074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f6964820900c2e08746644a308d300c0603551d13040530030101ff300d06092a864886f70d010104050003820101006dd252ceef85302c360aaace939bcff2cca904bb5d7a1661f8ae46b2994204d0ff4a68c7ed1a531ec4595a623ce60763b167297a7ae35712c407f208f0cb109429124d7b106219c084ca3eb3f9ad5fb871ef92269a8be28bf16d44c8d9a08e6cb2f005bb3fe2cb96447e868e731076ad45b33f6009ea19c161e62641aa99271dfd5228c5c587875ddb7f452758d661f6cc0cccb7352e424cc4365c523532f7325137593c4ae341f4db41edda0d0b1071a7c440f0fe9ea01cb627ca674369d084bd2fd911ff06cdbf2cfa10dc0f893ae35762919048c7efc64c7144178342f70581c9de573af55b390dd7fdb9418631895d5f759f30112687ff621410c069308a");

    public static String convertMillisToDateTime(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance.getTime().toString();
    }

    public static long convertTimeSetToMillis(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(11, i);
        instance.set(12, i2);
        instance.set(13, i3);
        return instance.getTimeInMillis();
    }

    public static Calendar convertMillisToCalendar(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance;
    }

    public static long bytesToMegabytes(long j) {
        return (long) Math.ceil((double) (j / 1048576));
    }

    public static String maskDeviceId(String str) {
        try {
            return str.replaceAll("(IMEI:)([\\w.]{8})", "I############").replaceAll("(TWID:)([\\w.]{8})", "T############");
        } catch (Exception unused) {
            return str;
        }
    }

    public static boolean isIdleScreen() {
        if (getTopPackageName((ActivityManager) FotaProviderInitializer.getContext().getSystemService("activity")).contains(FotaProviderInitializer.getContext().getPackageName())) {
            Log.I("idle or current package");
            return true;
        }
        Log.I("not idle");
        return false;
    }

    private static String getTopPackageName(ActivityManager activityManager) {
        if (activityManager == null) {
            return "activityManagerIsNull";
        }
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return "processInfos is null";
            }
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                int i = runningAppProcessInfo.importance;
                if (FotaProviderUtil.isSingleFotaProvider()) {
                    if ((i == 100 || i == 125 || i == 200 || i == 300 || i == 230 || i == 130) && runningAppProcessInfo.processName.equals(FotaProviderUtil.getSingleFotaProviderPackageName())) {
                        return runningAppProcessInfo.processName;
                    }
                } else if (i == 100) {
                    return runningAppProcessInfo.processName;
                }
            }
            return "cannot found on foreground";
        } catch (Exception e) {
            Log.E("Exception : " + e.toString());
            return "exception when find topPackage";
        }
    }

    public static boolean isGSFPackagedInstalled() {
        if (FotaProviderInitializer.getContext() == null) {
            Log.W("Context is null");
            return false;
        }
        PackageManager packageManager = FotaProviderInitializer.getContext().getPackageManager();
        if (packageManager == null) {
            Log.W("PackageManager is null");
            return false;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.google.android.gsf", 64);
            if (packageInfo == null) {
                Log.W("PackageInfo is null");
                return false;
            }
            for (Signature signature : packageInfo.signatures) {
                if (GSF_SIGNATURES.equals(signature)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static void turnScreenOn() {
        Log.I("");
        PowerManager powerManager = (PowerManager) FotaProviderInitializer.getContext().getSystemService("power");
        if (powerManager == null) {
            Log.W("PackageManager is null");
        } else if (!powerManager.isInteractive()) {
            Log.I("screen is off, so turn on");
            PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(268435466, ":FP_SHOW_ON_PHONE");
            newWakeLock.acquire(XCommonInterface.WAKE_LOCK_TIMEOUT);
            newWakeLock.release();
        }
    }

    public static void sleepInMilliseconds(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            Log.W("InterruptedException : " + e.toString());
        }
    }
}
