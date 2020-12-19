package com.accessorydm.filetransfer.action;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.XDMSecReceiverApiCall;
import com.accessorydm.ui.handler.XDMToastHandler;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;
import com.samsung.accessory.fotaprovider.controller.RequestController;
import com.samsung.accessory.fotaprovider.controller.RequestError;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.type.DeviceType;

public final class CheckDeviceInfo extends FileTransferAction {
    /* access modifiers changed from: package-private */
    @Override // com.accessorydm.filetransfer.action.FileTransferAction
    public boolean checkPrecondition() {
        if (!AccessoryController.isAvailable()) {
            Log.E("AccessoryController is null. Do nothing.");
            return false;
        } else if (!AccessoryController.getInstance().getConnectionController().isConnected()) {
            Log.W("Device connection is not ready");
            FileTransferFailure.handleAccessoryConnectionFailure(null);
            XDMSecReceiverApiCall.getInstance().xdmAccessoryCheckDeviceCallback(0);
            return false;
        } else if (!AccessoryController.getInstance().getRequestController().isInProgress()) {
            return true;
        } else {
            Log.W("Accessory is in progress");
            XDMToastHandler.xdmShowToast(XDMDmUtils.getContext().getString(DeviceType.get().getTextType().getConnectingMessageId()), 0);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.accessorydm.filetransfer.action.FileTransferAction
    public void controlAccessory() {
        Log.I("");
        AccessoryController.getInstance().getRequestController().checkDeviceInfo(new RequestController.DeviceInfoRequestCallback.Result() {
            /* class com.accessorydm.filetransfer.action.CheckDeviceInfo.AnonymousClass1 */

            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
            public void onSuccessAction(ConsumerInfo consumerInfo) {
                Log.I("checkDeviceInfo succeeded");
                CheckDeviceInfoActionSelector.nextAction(consumerInfo);
            }

            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
            public void onFailure(RequestError requestError) {
                Log.W("checkDeviceInfo failed");
                CheckDeviceInfoActionSelector.nextAction(requestError);
            }
        });
    }
}
