package com.samsung.android.app.watchmanager.sdllibrary;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import com.samsung.android.app.watchmanager.libinterface.BluetoothHeadsetInterface;

public class BluetoothHeadsetManager implements BluetoothHeadsetInterface {
    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothHeadsetInterface
    public boolean connect(BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice) {
        return ((Boolean) ReflectUtil.callMethod(bluetoothHeadset, "connect", new Class[]{BluetoothDevice.class}, bluetoothDevice)).booleanValue();
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothHeadsetInterface
    public boolean disconnect(BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice) {
        return ((Boolean) ReflectUtil.callMethod(bluetoothHeadset, "disconnect", new Class[]{BluetoothDevice.class}, bluetoothDevice)).booleanValue();
    }
}
