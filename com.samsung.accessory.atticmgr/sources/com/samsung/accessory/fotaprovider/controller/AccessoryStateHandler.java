package com.samsung.accessory.fotaprovider.controller;

import com.accessorydm.db.file.XDBFumoAdp;
import com.samsung.accessory.fotaprovider.AccessoryState;
import com.samsung.android.fotaprovider.log.Log;

public class AccessoryStateHandler {
    /* access modifiers changed from: protected */
    public void setCommonState(ConsumerInfo consumerInfo) {
        Log.D("set INIT");
        consumerInfo.setStatus(AccessoryState.INIT.getValue());
    }

    /* access modifiers changed from: protected */
    public void setDeviceInfoState(ConsumerInfo consumerInfo) {
        if (XDBFumoAdp.xdbGetFUMOStatus() == 251) {
            Log.D("set READY_TO_UPDATE");
            consumerInfo.setStatus(AccessoryState.READY_TO_UPDATE.getValue());
            return;
        }
        Log.D("set INIT");
        consumerInfo.setStatus(AccessoryState.INIT.getValue());
    }

    /* access modifiers changed from: protected */
    public void setInstallPackageState(ConsumerInfo consumerInfo) {
        Log.D("set UPDATE_IN_PROGRESS");
        consumerInfo.setStatus(AccessoryState.UPDATE_IN_PROGRESS.getValue());
    }

    public void setStateForReportUpdateResult(ConsumerInfo consumerInfo) {
        Log.D("set UPDATE_TO_REPORTING");
        consumerInfo.setStatus(AccessoryState.UPDATE_TO_REPORTING.getValue());
    }
}
