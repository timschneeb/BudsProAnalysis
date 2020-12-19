package com.samsung.android.app.twatchmanager.factory;

import com.samsung.android.app.twatchmanager.util.FeatureUtil;
import com.samsung.android.app.watchmanager.libinterface.BluetoothHeadsetInterface;
import com.samsung.android.app.watchmanager.selibrary.BluetoothHeadsetManager;

public class BluetoothHeadsetFactory {
    private static BluetoothHeadsetInterface mInterface;

    public static BluetoothHeadsetInterface get() {
        if (mInterface == null) {
            mInterface = FeatureUtil.supportSem() ? new BluetoothHeadsetManager() : new com.samsung.android.app.watchmanager.sdllibrary.BluetoothHeadsetManager();
        }
        return mInterface;
    }
}
