package com.samsung.accessory.fotaprovider.controller;

public abstract class ConnectionController {

    public interface ConnectionResultCallback {
        void onFailure();

        void onSuccess();
    }

    public abstract boolean isConnected();

    public abstract void makeConnection();

    public abstract void makeConnection(ConnectionResultCallback connectionResultCallback);

    public abstract void releaseConnection();
}
