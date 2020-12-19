package com.samsung.android.app.twatchmanager.packagecontroller;

import android.content.Context;
import android.os.Build;
import com.samsung.android.app.watchmanager.libinterface.IInstaller;
import com.samsung.android.app.watchmanager.sdllibrary.PackageController;
import com.samsung.android.app.watchmanager.selibrary.PackageInstaller2;

public class PackageControllerFactory {
    private static IInstaller instance;

    public static IInstaller getInstaller(Context context) {
        if (instance == null) {
            instance = makeInstance(context);
        }
        return instance;
    }

    public static int getStorageErrorCode(Context context) {
        try {
            return makeInstance(context).getStorageErrorCode();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static IInstaller makeInstance(Context context) {
        return Build.VERSION.SDK_INT >= 23 ? new PackageInstaller2(context.getApplicationContext()) : new PackageController(context.getApplicationContext());
    }
}
