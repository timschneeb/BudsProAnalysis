package com.google.android.material.internal;

import android.os.Build;
import com.samsung.accessory.hearablemgr.BuildConfig;

public class ManufacturerUtils {
    private ManufacturerUtils() {
    }

    public static boolean isSamsungDevice() {
        return Build.MANUFACTURER.equalsIgnoreCase(BuildConfig.FLAVOR_version);
    }
}
