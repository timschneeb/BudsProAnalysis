package com.samsung.android.app.watchmanager.libinterface;

import android.net.Uri;
import java.io.File;

public interface IInstaller {
    public static final String ACTION_PACKAGE_INSTALLED = "com.samsung.android.watchmanager.ACTION_PACKAGE_INSTALLED";
    public static final String ACTION_PACKAGE_INSTALLED_EXTRA_PACKAGENAME = "packagename";
    public static final int INSTALL_SUCCEEDED = 1;
    public static final String PERM_ACCESS_UNIFIED_HOST_MANAGER = "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER";
    public static final String SAMSUNG_APPS_PKG_NAME = "com.sec.android.app.samsungapps";
    public static final int UNINSTALL_SUCCEEDED = 1;

    void SetOnStatusReturned(OnstatusReturned onstatusReturned);

    int getStorageErrorCode();

    void installPackage(Uri uri, String str);

    void installPackage(File file, String str);

    void installPackage(String str, String str2);

    void installPackage(String str, String str2, String str3);

    void uninstallPackage(String str);
}
