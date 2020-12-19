package com.samsung.accessory.hearablemgr.library;

import android.content.Context;

public interface SpatialSensorInterface {

    public interface SupportApi {
        Context getContext();

        boolean getSetting();

        boolean isSppConnected();

        boolean sendSppMessage(byte b, byte[] bArr);
    }

    void onCreate(SupportApi supportApi, boolean z);

    void onDestroy();

    void onSettingUpdated(boolean z);

    void onSppConnected(String str);

    void onSppDisconnected(String str);

    void onSppMessageReceived(byte b, byte[] bArr);
}
