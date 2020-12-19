package com.samsung.android.fotaprovider.util;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Build;
import android.os.UserManager;
import com.samsung.android.fotaprovider.deviceinfo.ProviderInfo;
import com.samsung.android.fotaprovider.log.Log;

public final class FotaProviderFileEncryptionUtil {
    public static boolean isUserUnlocked(Context context) {
        boolean z = true;
        if (isSupportFBE(context)) {
            UserManager userManager = (UserManager) context.getSystemService("user");
            if (userManager != null) {
                z = userManager.isUserUnlocked();
            }
            if (z) {
                Log.D("isUserUnlocked is true : unlocked.");
            } else {
                Log.D("isUserUnlocked is false : on direct boot mode.");
            }
        }
        return z;
    }

    private static boolean isSupportFBE(Context context) {
        return Build.VERSION.SDK_INT >= 24 && new ProviderInfo().isSamsungDevice() && isFBEDevice(context);
    }

    private static boolean isFBEDevice(Context context) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            return false;
        }
        try {
            if (5 == devicePolicyManager.getStorageEncryptionStatus()) {
                return true;
            }
            return false;
        } catch (IllegalStateException unused) {
            Log.D("isFBEDevice - java.lang.IllegalStateException: Unmatching package name");
            return false;
        }
    }
}
