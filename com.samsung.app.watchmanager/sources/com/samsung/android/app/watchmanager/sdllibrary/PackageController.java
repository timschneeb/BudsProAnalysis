package com.samsung.android.app.watchmanager.sdllibrary;

import android.content.Context;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import com.samsung.android.app.watchmanager.libinterface.AbstractPackageInstaller;
import java.io.File;
import java.lang.reflect.Method;

public class PackageController extends AbstractPackageInstaller {
    public static final int INSTALL_REPLACE_EXISTING = 2;
    private static final String TAG = ("tUHM:" + PackageController.class.getSimpleName());
    private static final String WATCH_MANAGER_PKG_NAME = "com.samsung.android.app.watchmanager";
    private PackageDeleteObserver deleteObserver = new PackageDeleteObserver();
    private PackageInstallObserver installObserver = new PackageInstallObserver();
    private Method method;
    private PackageManager pm;
    private Method uninstallmethod;

    class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
        PackageDeleteObserver() {
        }

        public void packageDeleted(String str, int i) {
            String str2 = PackageController.TAG;
            Log.d(str2, "packageDeleted returnCode = " + i);
            PackageController.this.notifyPackageUninstalled(str, i);
        }
    }

    /* access modifiers changed from: package-private */
    public class PackageInstallObserver extends IPackageInstallObserver.Stub {
        PackageInstallObserver() {
        }

        public void packageInstalled(String str, int i) {
            String str2 = PackageController.TAG;
            Log.d(str2, "packageInstalled returnCode = " + i);
            PackageController.this.notifyPackageInstalled(str, i);
        }
    }

    public PackageController(Context context) {
        super(context);
        this.pm = context.getPackageManager();
        String str = TAG;
        Log.d(str, "PackageController Context : " + context);
        Class<?> cls = Integer.TYPE;
        this.method = this.pm.getClass().getMethod("installPackage", Uri.class, IPackageInstallObserver.class, cls, String.class);
        this.uninstallmethod = this.pm.getClass().getMethod("deletePackage", String.class, IPackageDeleteObserver.class, cls);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public int getStorageErrorCode() {
        return -4;
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void installPackage(Uri uri, String str) {
        this.method.invoke(this.pm, uri, this.installObserver, 2, "com.samsung.android.app.watchmanager");
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void installPackage(File file, String str) {
        if (file.exists()) {
            installPackage(Uri.fromFile(file), str);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void installPackage(String str, String str2) {
        installPackage(new File(str), str2);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void installPackage(String str, String str2, String str3) {
        File file = new File(str);
        if (file.exists()) {
            Uri fromFile = Uri.fromFile(file);
            if (str2 == null) {
                str2 = this.mContext.getPackageName();
            }
            this.method.invoke(this.pm, fromFile, this.installObserver, 2, str2);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void uninstallPackage(String str) {
        this.uninstallmethod.invoke(this.pm, str, this.deleteObserver, 0);
    }
}
