package com.samsung.android.app.twatchmanager.update;

import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import com.samsung.android.app.twatchmanager.packagecontroller.PackageControllerFactory;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.libinterface.IInstaller;
import com.samsung.android.app.watchmanager.libinterface.OnstatusReturned;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UpdateInstaller implements OnstatusReturned {
    private static final String TAG = ("tUHM:" + UpdateInstaller.class.getSimpleName());
    private IUpdateInstallerCallback mUpdateInstallerCallback;

    public interface IUpdateInstallerCallback {
        void onEndOfInstall();

        void onFailToInstall(int i, String str);

        void onNoSilentInstallPermission(InstallPack installPack);

        void onSinglePackageInstalled(String str);
    }

    private boolean clearResourceIfUHMInstall(String str) {
        if (!"com.samsung.android.app.watchmanager".equals(str) && !GlobalConst.PACKAGE_NAME_OLD_UNIFIED_HOST_MANAGER.equals(str)) {
            return false;
        }
        Log.d(TAG, "clearResourceIfUHMInstall() clear resources and update long life log before tUHM update installation ");
        RegistryAppsDBManager.updateAppUpdateCancelCount(str, 0, TWatchManagerApplication.getAppContext());
        UpdateUtil.clearUpdateCheckPref(TWatchManagerApplication.getAppContext());
        Log.saveLog();
        return true;
    }

    private void installPackage(InstallPack installPack) {
        if (InstallationUtils.hasInstallPermission(TWatchManagerApplication.getAppContext())) {
            Log.d(TAG, "We do have permissions for Silent installation");
            InstallationUtils.changeFilePermission(installPack.path, InstallationUtils.PERMISSIONS_GLOBAL);
            try {
                IInstaller installer = PackageControllerFactory.getInstaller(TWatchManagerApplication.getAppContext());
                installer.SetOnStatusReturned(this);
                installer.installPackage(installPack.path, installPack.installerPackage, installPack.packName);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            this.mUpdateInstallerCallback.onNoSilentInstallPermission(installPack);
        }
    }

    private void removeInstalledPackageFile(String str) {
        if (UpdateUtil.isLocalUpdateTestModeEnabled() || InstallationUtils.isLocalInstallation()) {
            Log.d(TAG, "handleAfterSinglePkgInstalled() now local update testing... don't remove the installed apk file");
            return;
        }
        File file = new File(str);
        boolean z = false;
        if (file.exists()) {
            z = file.delete();
        }
        String str2 = TAG;
        Log.d(str2, "handleAfterSinglePkgInstalled() toRemoveFile : " + file + " removeResult : " + z);
    }

    public void handleAfterSinglePkgInstalled() {
        try {
            removeInstalledPackageFile(UpdateInstallData.getInstance().popInstallPack().path);
        } catch (IndexOutOfBoundsException | NullPointerException | UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    public void init(List<InstallPack> list, IUpdateInstallerCallback iUpdateInstallerCallback) {
        UpdateInstallData.getInstance().initData(list);
        this.mUpdateInstallerCallback = iUpdateInstallerCallback;
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.OnstatusReturned
    public void packageInstalled(String str, int i) {
        Log.d(TAG, "packageInstalled(" + str + ", " + i + ")");
        if (i == 1) {
            Log.d(TAG, "packageInstalled() enable application after installed");
            this.mUpdateInstallerCallback.onSinglePackageInstalled(str);
            handleAfterSinglePkgInstalled();
            RegistryAppsDBManager.updateAppUpdateCancelCount(str, 0, TWatchManagerApplication.getAppContext());
            boolean z = !UpdateInstallData.getInstance().hasPackageToInstall();
            Log.d(TAG, "packageInstalled() endUpdate : " + z);
            if (z) {
                this.mUpdateInstallerCallback.onEndOfInstall();
            } else {
                startUpdateInstallation();
            }
        } else {
            this.mUpdateInstallerCallback.onFailToInstall(i, str);
        }
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.OnstatusReturned
    public void packageUninstalled(String str, int i) {
        Log.d(TAG, "packageUninstalled(), This is not used for package update.");
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x005d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean startUpdateInstallation() {
        /*
            r6 = this;
            java.lang.String r0 = com.samsung.android.app.twatchmanager.update.UpdateInstaller.TAG
            java.lang.String r1 = "startUpdateInstallation() starts..."
            com.samsung.android.app.twatchmanager.log.Log.d(r0, r1)
            com.samsung.android.app.twatchmanager.update.UpdateInstallData r0 = com.samsung.android.app.twatchmanager.update.UpdateInstallData.getInstance()
            com.samsung.android.app.twatchmanager.model.InstallPack r0 = r0.getCurrentInstallPack()
            r1 = 0
            if (r0 == 0) goto L_0x0044
            java.io.File r2 = new java.io.File
            java.lang.String r3 = r0.path
            r2.<init>(r3)
            java.lang.String r2 = r0.packName
            java.lang.String r3 = com.samsung.android.app.twatchmanager.update.UpdateInstaller.TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Invoking install request for package("
            r4.append(r5)
            r4.append(r2)
            java.lang.String r5 = ")"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.samsung.android.app.twatchmanager.log.Log.d(r3, r4)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x0044
            r6.clearResourceIfUHMInstall(r2)
            r6.installPackage(r0)
            r0 = 1
            goto L_0x0045
        L_0x0044:
            r0 = 0
        L_0x0045:
            java.lang.String r2 = com.samsung.android.app.twatchmanager.update.UpdateInstaller.TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "startUpdateInstallation() isInstallRequestSucceeded : "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            com.samsung.android.app.twatchmanager.log.Log.d(r2, r3)
            if (r0 != 0) goto L_0x0063
            com.samsung.android.app.twatchmanager.update.UpdateInstaller$IUpdateInstallerCallback r2 = r6.mUpdateInstallerCallback
            r3 = 0
            r2.onFailToInstall(r1, r3)
        L_0x0063:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.UpdateInstaller.startUpdateInstallation():boolean");
    }
}
