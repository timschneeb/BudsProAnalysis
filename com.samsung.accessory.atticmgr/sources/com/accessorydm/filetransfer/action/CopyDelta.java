package com.accessorydm.filetransfer.action;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.adapter.XDMInitAdapter;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XDMAccessoryInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.postpone.PostponeManager;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;
import com.samsung.accessory.fotaprovider.controller.RequestController;
import com.samsung.accessory.fotaprovider.controller.RequestError;
import com.samsung.android.fotaprovider.log.Log;
import java.io.File;
import java.util.Locale;

public final class CopyDelta extends FileTransferAction {
    /* access modifiers changed from: package-private */
    @Override // com.accessorydm.filetransfer.action.FileTransferAction
    public boolean checkPrecondition() {
        XDMDmUtils.getInstance().xdmSetWaitWifiConnectMode(0);
        PostponeManager.cancelPostpone();
        XFOTADl.xfotaCopySetDrawingPercentage(true);
        XDBFumoAdp.xdbSetFUMOStatus(250);
        XDMMsg.xdmSendUIMessage(XUIEventInterface.DL_UIEVENT.XUI_DL_COPY_IN_PROGRESS, Long.valueOf(XDBFumoAdp.xdbGetObjectSizeFUMO()), null);
        if (!AccessoryController.getInstance().getConnectionController().isConnected()) {
            Log.W("Device connection is not ready");
            failedCopyProcess();
            return false;
        } else if (!AccessoryController.getInstance().getRequestController().isInProgress()) {
            return true;
        } else {
            Log.W("Accessory is in progress");
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.accessorydm.filetransfer.action.FileTransferAction
    public void controlAccessory() {
        Log.I("");
        File file = new File(XDB.xdbFileGetNameFromCallerID(XDB.xdbGetFileIdFirmwareData()));
        Log.I(file.getName());
        if (!file.exists()) {
            Log.W("file is not existed");
            failedCopyProcess();
            return;
        }
        AccessoryController.getInstance().getRequestController().sendPackage(file.getPath(), new RequestController.RequestCallback.Result() {
            /* class com.accessorydm.filetransfer.action.CopyDelta.AnonymousClass1 */

            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
            public void onSuccessAction(ConsumerInfo consumerInfo) {
                Log.I("copyDelta succeeded");
                CopyDelta.this.completeCopyProcess();
            }

            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
            public void onFailure(RequestError requestError) {
                Log.W("copyDelta failed");
                if (requestError != null) {
                    Log.W("error: " + requestError);
                    int i = AnonymousClass3.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError[requestError.ordinal()];
                    if (i == 1) {
                        FileTransferFailure.handleLowMemory(XDMAccessoryInterface.XDMAccessoryCheckState.XDM_ACCESSORY_CHECK_COPY);
                        return;
                    } else if (i == 2) {
                        CopyDelta.this.failedCopyProcess(RequestError.ERROR_FILE_TRANSFER_ACCESSORY_UNCOUPLED);
                        return;
                    }
                }
                CopyDelta.this.failedCopyProcess();
            }
        }, new RequestController.RequestCallback.FileTransfer() {
            /* class com.accessorydm.filetransfer.action.CopyDelta.AnonymousClass2 */

            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.FileTransfer
            public void onFileTransferStart() {
                Log.I("Copying Progress Reset");
                CopyDelta.this.updateCopyProgress(0);
            }

            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.FileTransfer
            public void onFileProgress(int i) {
                Log.I(String.format(Locale.US, "Copying Percentage:%d%%", Integer.valueOf(i)));
                CopyDelta.this.updateCopyProgress(i);
            }
        });
        Log.I("Start to transfer delta package!");
    }

    /* renamed from: com.accessorydm.filetransfer.action.CopyDelta$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError = new int[RequestError.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.samsung.accessory.fotaprovider.controller.RequestError[] r0 = com.samsung.accessory.fotaprovider.controller.RequestError.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.filetransfer.action.CopyDelta.AnonymousClass3.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError = r0
                int[] r0 = com.accessorydm.filetransfer.action.CopyDelta.AnonymousClass3.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.samsung.accessory.fotaprovider.controller.RequestError r1 = com.samsung.accessory.fotaprovider.controller.RequestError.ERROR_LOW_MEMORY     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.filetransfer.action.CopyDelta.AnonymousClass3.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError     // Catch:{ NoSuchFieldError -> 0x001f }
                com.samsung.accessory.fotaprovider.controller.RequestError r1 = com.samsung.accessory.fotaprovider.controller.RequestError.ERROR_FILE_TRANSFER_ACCESSORY_UNCOUPLED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.filetransfer.action.CopyDelta.AnonymousClass3.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateCopyProgress(int i) {
        XUIProgressModel.getInstance().updateProgressInfoForCopy(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void completeCopyProcess() {
        XFOTADl.xfotaCopySetDrawingPercentage(false);
        XDBFumoAdp.xdbSetFUMOCopyRetryCount(0);
        XDB.xdbAdpDeltaAllClear();
        XDBFumoAdp.xdbSetFUMOStatus(251);
        XUIProgressModel.getInstance().initializeProgress();
        XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void failedCopyProcess() {
        failedCopyProcess(RequestError.ERROR_FILE_TRANSFER);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void failedCopyProcess(RequestError requestError) {
        Log.I("");
        int xdbGetFUMOCopyRetryCount = XDBFumoAdp.xdbGetFUMOCopyRetryCount() + 1;
        XFOTADl.xfotaCopySetDrawingPercentage(false);
        if (xdbGetFUMOCopyRetryCount >= 3) {
            XUIProgressModel.getInstance().initializeProgress();
            XDBFumoAdp.xdbSetFUMOCopyRetryCount(0);
            XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(XFOTAInterface.XFOTA_GENERIC_SAP_COPY_FAILED);
            XDB.xdbAdpDeltaAllClear();
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_COPY_FAILED);
            return;
        }
        XUIProgressModel.getInstance().initializeProgress();
        XDBFumoAdp.xdbSetFUMOCopyRetryCount(xdbGetFUMOCopyRetryCount);
        if (requestError == RequestError.ERROR_FILE_TRANSFER_ACCESSORY_UNCOUPLED) {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_COPY_RETRY_LATER_ACCESSORY_UNCOUPLED);
        } else {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_COPY_RETRY_LATER);
        }
    }
}
