package com.samsung.android.app.twatchmanager.factory;

import com.samsung.android.app.twatchmanager.util.FeatureUtil;
import com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface;
import com.samsung.android.app.watchmanager.sdllibrary.BluetoothDeviceManager;

public class BluetoothDeviceFactory {
    private static BluetoothDeviceInterface mInterface;

    public static BluetoothDeviceInterface get() {
        if (mInterface == null) {
            mInterface = !FeatureUtil.supportSem() ? new BluetoothDeviceManager() : new com.samsung.android.app.watchmanager.selibrary.BluetoothDeviceManager();
        }
        return mInterface;
    }
}
