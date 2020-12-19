package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public final class FeatureUtil {
    private static final String TAG = "FeatureUtil";
    private static boolean semAvailable = false;

    private static void checkSupportSemAPI(Context context) {
        Log.d(TAG, "checkSupportSemAPI");
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.hasSystemFeature("com.samsung.feature.samsung_experience_mobile") || packageManager.hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite")) {
                semAvailable = true;
                Log.d(TAG, "semAvailable set as true ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            semAvailable = false;
            Log.d(TAG, "semAvailable set as false ");
        }
    }

    public static void init(Context context) {
        checkSupportSemAPI(context);
    }

    public static boolean supportSem() {
        String str = TAG;
        Log.d(str, "semAvailable = " + semAvailable);
        return semAvailable;
    }
}
