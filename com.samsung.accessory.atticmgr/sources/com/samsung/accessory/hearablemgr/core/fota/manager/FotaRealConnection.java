package com.samsung.accessory.hearablemgr.core.fota.manager;

import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

public class FotaRealConnection {
    private static final String TAG = (Application.TAG_ + FotaRealConnection.class.getSimpleName());

    public interface RealConnectionCallback {
        void onConnected();

        void onError();
    }

    public interface TransferCallback {
        void completed();
    }

    public void connectRealDevice(RealConnectionCallback realConnectionCallback) {
        Log.d(TAG, "connectRealDevice ");
        if (isConnected()) {
            Log.d(TAG, "onConnected ");
            realConnectionCallback.onConnected();
            return;
        }
        Log.d(TAG, "onError ");
        realConnectionCallback.onError();
    }

    public void transferRealDevice(TransferCallback transferCallback) {
        Log.d(TAG, "transferRealDevice ");
        if (isTransferred()) {
            transferCallback.completed();
        }
    }

    public void disconnect() {
        Log.d(TAG, "disconnect ");
        FotaConnectionController.FotaConnected = false;
    }

    private boolean isConnected() {
        String str = TAG;
        Log.d(str, "isConnected : " + Application.getCoreService().isConnected());
        return Application.getCoreService().isConnected();
    }

    private boolean isTransferred() {
        Log.d(TAG, "isTransferred ");
        return true;
    }
}
