package com.samsung.android.fotaprovider.util;

import com.samsung.android.fotaprovider.log.Log;

public class NativeUtil {
    public static native String getDataLogKey();

    public static native String getOspServerValue();

    public static native String mealyMachine(int i, int i2);

    static {
        try {
            System.loadLibrary("FotaServiceInfo");
        } catch (Throwable th) {
            Log.printStackTrace(th);
        }
    }
}
