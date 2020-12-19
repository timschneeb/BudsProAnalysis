package com.samsung.accessory.hearablemgr.core.fota.manager;

import com.samsung.accessory.fotaprovider.controller.ConnectionController;
import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

public final class FotaConnectionController extends ConnectionController {
    public static boolean FotaConnected = false;
    private static final String TAG = (Application.TAG_ + FotaConnectionController.class.getSimpleName());

    @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController
    public void makeConnection() {
        new FotaConnection().doMyConnection();
        Log.d(TAG, "makeConnection");
    }

    @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController
    public void makeConnection(ConnectionController.ConnectionResultCallback connectionResultCallback) {
        new FotaConnection().doMyConnection(connectionResultCallback);
        Log.d(TAG, "makeConnection(ConnectionController.ConnectionResultCallback connectionResultCallback)");
    }

    @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController
    public void releaseConnection() {
        new FotaConnection().disconnectMyConnection();
        Log.d(TAG, "releaseConnection()");
    }

    @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController
    public boolean isConnected() {
        Log.d(TAG, "isConnected()");
        return new FotaConnection().isConnected();
    }
}
