package com.samsung.accessory.hearablemgr.core.fota.manager;

import com.samsung.accessory.fotaprovider.AccessoryEventHandler;
import com.samsung.accessory.fotaprovider.controller.ConnectionController;
import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.fota.manager.FotaRealConnection;
import seccompat.android.util.Log;

public final class FotaConnection {
    private static final String TAG = (Application.TAG_ + FotaConnection.class.getSimpleName());
    private ConnectionController.ConnectionResultCallback connectionResultCallback;
    private FotaRealConnection.RealConnectionCallback secondDeviceConnectionCallback = new FotaRealConnection.RealConnectionCallback() {
        /* class com.samsung.accessory.hearablemgr.core.fota.manager.FotaConnection.AnonymousClass1 */

        @Override // com.samsung.accessory.hearablemgr.core.fota.manager.FotaRealConnection.RealConnectionCallback
        public void onConnected() {
            String str = FotaConnection.TAG;
            Log.d(str, "onConnected()" + FotaConnection.this.connectionResultCallback);
            if (FotaConnection.this.connectionResultCallback != null) {
                FotaConnection.this.connectionResultCallback.onSuccess();
                Log.d(FotaConnection.TAG, "Fota Connected : true");
                FotaConnectionController.FotaConnected = true;
            }
        }

        @Override // com.samsung.accessory.hearablemgr.core.fota.manager.FotaRealConnection.RealConnectionCallback
        public void onError() {
            Log.d(FotaConnection.TAG, "onError()");
            if (FotaConnection.this.connectionResultCallback != null) {
                FotaConnection.this.connectionResultCallback.onFailure();
                FotaConnectionController.FotaConnected = false;
            }
        }
    };
    private FotaRealConnection.TransferCallback transferCallback = new FotaRealConnection.TransferCallback() {
        /* class com.samsung.accessory.hearablemgr.core.fota.manager.FotaConnection.AnonymousClass2 */

        @Override // com.samsung.accessory.hearablemgr.core.fota.manager.FotaRealConnection.TransferCallback
        public void completed() {
            Log.d(FotaConnection.TAG, "TransferCallback : completed()");
            Application.getCoreService().getEarBudsFotaInfo().printFota();
            AccessoryEventHandler.getInstance().reportUpdateResult(new ConsumerInfo(Application.getCoreService().getEarBudsFotaInfo().deviceId, Application.getCoreService().getEarBudsFotaInfo().modelNumber, Application.getCoreService().getEarBudsFotaInfo().salesCode, Application.getCoreService().getEarBudsFotaInfo().firmwareVersion, Application.getCoreService().getEarBudsFotaInfo().uniqueNumber, Application.getCoreService().getEarBudsFotaInfo().serialNumber), true);
        }
    };

    public void doMyConnection() {
        Log.d(TAG, "doMyConnection()");
        new FotaRealConnection().connectRealDevice(this.secondDeviceConnectionCallback);
    }

    public void doMyConnection(ConnectionController.ConnectionResultCallback connectionResultCallback2) {
        Log.d(TAG, "doMyConnection(ConnectionController.ConnectionResultCallback callback)");
        this.connectionResultCallback = connectionResultCallback2;
        new FotaRealConnection().connectRealDevice(this.secondDeviceConnectionCallback);
    }

    public void disconnectMyConnection() {
        Log.d(TAG, "disconnectMyConnection()");
        new FotaRealConnection().disconnect();
        FotaConnectionController.FotaConnected = false;
    }

    public boolean isConnected() {
        String str = TAG;
        Log.d(str, "FotaProviderConnected() : " + FotaConnectionController.FotaConnected);
        return FotaConnectionController.FotaConnected;
    }
}
