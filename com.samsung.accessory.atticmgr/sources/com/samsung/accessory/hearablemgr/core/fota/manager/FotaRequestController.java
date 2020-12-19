package com.samsung.accessory.hearablemgr.core.fota.manager;

import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;
import com.samsung.accessory.fotaprovider.controller.RequestController;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import seccompat.android.util.Log;

public final class FotaRequestController extends RequestController {
    private static final String TAG = (Application.TAG_ + FotaRequestController.class.getSimpleName());
    public static boolean isInProgress = false;
    public static RequestController.RequestCallback.FileTransfer mRequestFileTransferCallback;
    public static RequestController.RequestCallback.Result mRequestResultCallback;
    EarBudsInfo info;

    @Override // com.samsung.accessory.fotaprovider.controller.RequestController
    public void initializeDeviceInfo(RequestController.RequestCallback.Result result) {
        Log.d(TAG, "initializeDeviceInfo");
        mRequestResultCallback = result;
        Application.getCoreService().getEarBudsFotaInfo().printFota();
        mRequestResultCallback.onSuccess(new ConsumerInfo(Application.getCoreService().getEarBudsFotaInfo().deviceId, Application.getCoreService().getEarBudsFotaInfo().modelNumber, Application.getCoreService().getEarBudsFotaInfo().salesCode, Application.getCoreService().getEarBudsFotaInfo().firmwareVersion, Application.getCoreService().getEarBudsFotaInfo().uniqueNumber, Application.getCoreService().getEarBudsFotaInfo().serialNumber));
    }

    @Override // com.samsung.accessory.fotaprovider.controller.RequestController
    public void checkDeviceInfo(RequestController.DeviceInfoRequestCallback.Result result) {
        Log.d(TAG, "checkDeviceInfo");
        mRequestResultCallback = result;
        Application.getCoreService().getEarBudsFotaInfo().printFota();
        mRequestResultCallback.onSuccess(new ConsumerInfo(Application.getCoreService().getEarBudsFotaInfo().deviceId, Application.getCoreService().getEarBudsFotaInfo().modelNumber, Application.getCoreService().getEarBudsFotaInfo().salesCode, Application.getCoreService().getEarBudsFotaInfo().firmwareVersion, Application.getCoreService().getEarBudsFotaInfo().uniqueNumber, Application.getCoreService().getEarBudsFotaInfo().serialNumber));
    }

    @Override // com.samsung.accessory.fotaprovider.controller.RequestController
    public void sendPackage(String str, RequestController.RequestCallback.Result result, RequestController.RequestCallback.FileTransfer fileTransfer) {
        String str2 = TAG;
        Log.d(str2, "sendPackage : " + str);
        this.info = Application.getCoreService().getEarBudsInfo();
        mRequestFileTransferCallback = fileTransfer;
        mRequestResultCallback = result;
        Application.getCoreService().getEarBudsFotaInfo().printFota();
        Log.d(TAG, "onFileTransferStart ");
        mRequestFileTransferCallback.onFileTransferStart();
        Application.getCoreService().startFotaInstall(str);
        Log.d(TAG, "FotaRequestController.isInProgress true");
        isInProgress = true;
    }

    @Override // com.samsung.accessory.fotaprovider.controller.RequestController
    public void installPackage(RequestController.InstallPackageRequestCallback.Result result) {
        Log.d(TAG, "installPackage ");
        mRequestResultCallback = result;
        Application.getCoreService().getEarBudsFotaInfo().printFota();
        mRequestResultCallback.onSuccess(new ConsumerInfo(Application.getCoreService().getEarBudsFotaInfo().deviceId, Application.getCoreService().getEarBudsFotaInfo().modelNumber, Application.getCoreService().getEarBudsFotaInfo().salesCode, Application.getCoreService().getEarBudsFotaInfo().firmwareVersion, Application.getCoreService().getEarBudsFotaInfo().uniqueNumber, Application.getCoreService().getEarBudsFotaInfo().serialNumber));
    }

    @Override // com.samsung.accessory.fotaprovider.controller.RequestController
    public void resetStatus(RequestController.RequestCallback.Result result) {
        Log.d(TAG, "resetStatus ");
        mRequestResultCallback = result;
        Application.getCoreService().getEarBudsFotaInfo().printFota();
        mRequestResultCallback.onSuccess(new ConsumerInfo(Application.getCoreService().getEarBudsFotaInfo().deviceId, Application.getCoreService().getEarBudsFotaInfo().modelNumber, Application.getCoreService().getEarBudsFotaInfo().salesCode, Application.getCoreService().getEarBudsFotaInfo().firmwareVersion, Application.getCoreService().getEarBudsFotaInfo().uniqueNumber, Application.getCoreService().getEarBudsFotaInfo().serialNumber));
    }

    @Override // com.samsung.accessory.fotaprovider.controller.RequestController
    public boolean isInProgress() {
        String str = TAG;
        Log.d(str, "isInProgress  : " + isInProgress);
        return isInProgress;
    }
}
