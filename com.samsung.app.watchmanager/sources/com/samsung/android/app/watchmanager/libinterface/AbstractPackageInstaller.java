package com.samsung.android.app.watchmanager.libinterface;

import android.content.Context;
import android.content.Intent;

public abstract class AbstractPackageInstaller implements IInstaller {
    protected Context mContext;
    private OnstatusReturned mOnstatusReturned;

    public AbstractPackageInstaller(Context context) {
        this.mContext = context;
    }

    private void sendDataToWatchManagerStub(String str, int i) {
        if (i == 1) {
            Intent intent = new Intent(IInstaller.ACTION_PACKAGE_INSTALLED);
            intent.putExtra(IInstaller.ACTION_PACKAGE_INSTALLED_EXTRA_PACKAGENAME, str);
            intent.addFlags(268435456);
            this.mContext.sendBroadcast(intent, "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER");
        }
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void SetOnStatusReturned(OnstatusReturned onstatusReturned) {
        this.mOnstatusReturned = onstatusReturned;
    }

    /* access modifiers changed from: protected */
    public void notifyPackageInstalled(String str, int i) {
        OnstatusReturned onstatusReturned = this.mOnstatusReturned;
        if (onstatusReturned != null) {
            onstatusReturned.packageInstalled(str, i);
        }
        sendDataToWatchManagerStub(str, i);
    }

    /* access modifiers changed from: protected */
    public void notifyPackageUninstalled(String str, int i) {
        OnstatusReturned onstatusReturned = this.mOnstatusReturned;
        if (onstatusReturned != null) {
            onstatusReturned.packageUninstalled(str, i);
        }
    }
}
