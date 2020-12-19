package com.accessorydm.filetransfer.action;

import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;
import com.samsung.accessory.fotaprovider.controller.RequestController;
import com.samsung.accessory.fotaprovider.controller.RequestError;
import com.samsung.android.fotaprovider.log.Log;

public final class ResetDevice extends FileTransferAction {
    /* access modifiers changed from: package-private */
    @Override // com.accessorydm.filetransfer.action.FileTransferAction
    public boolean checkPrecondition() {
        if (!AccessoryController.getInstance().getConnectionController().isConnected()) {
            Log.W("Device connection is not ready");
            FileTransferFailure.handleAccessoryConnectionFailure(null);
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
        AccessoryController.getInstance().getRequestController().resetStatus(new RequestController.RequestCallback.Result() {
            /* class com.accessorydm.filetransfer.action.ResetDevice.AnonymousClass1 */

            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
            public void onSuccessAction(ConsumerInfo consumerInfo) {
                Log.I("resetDevice succeeded");
            }

            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
            public void onFailure(RequestError requestError) {
                Log.W("resetDevice failed");
                FileTransferFailure.handleAccessoryConnectionFailure(requestError);
            }
        });
    }
}
