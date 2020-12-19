package com.samsung.accessory.fotaprovider.controller;

import com.samsung.accessory.fotaprovider.AccessoryController;

public abstract class RequestController {
    public abstract void checkDeviceInfo(DeviceInfoRequestCallback.Result result);

    public abstract void initializeDeviceInfo(RequestCallback.Result result);

    public abstract void installPackage(InstallPackageRequestCallback.Result result);

    public abstract boolean isInProgress();

    public abstract void resetStatus(RequestCallback.Result result);

    public abstract void sendPackage(String str, RequestCallback.Result result, RequestCallback.FileTransfer fileTransfer);

    public interface RequestCallback {

        public interface FileTransfer {
            void onFileProgress(int i);

            void onFileTransferStart();
        }

        public static abstract class Result {
            public abstract void onFailure(RequestError requestError);

            public abstract void onSuccessAction(ConsumerInfo consumerInfo);

            public void onSuccess(ConsumerInfo consumerInfo) {
                AccessoryController.getInstance().getAccessoryStateHandler().setCommonState(consumerInfo);
                onSuccessAction(consumerInfo);
            }
        }
    }

    public interface DeviceInfoRequestCallback {

        public static abstract class Result extends RequestCallback.Result {
            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
            public final void onSuccess(ConsumerInfo consumerInfo) {
                if (AccessoryUtil.isDifferentDevice(consumerInfo)) {
                    onFailure(RequestError.ERROR_DIFFERENT_DEVICE);
                    return;
                }
                AccessoryController.getInstance().getAccessoryStateHandler().setDeviceInfoState(consumerInfo);
                onSuccessAction(consumerInfo);
            }
        }
    }

    public interface InstallPackageRequestCallback {

        public static abstract class Result extends RequestCallback.Result {
            @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
            public final void onSuccess(ConsumerInfo consumerInfo) {
                AccessoryController.getInstance().getAccessoryStateHandler().setInstallPackageState(consumerInfo);
                onSuccessAction(consumerInfo);
            }
        }
    }
}
