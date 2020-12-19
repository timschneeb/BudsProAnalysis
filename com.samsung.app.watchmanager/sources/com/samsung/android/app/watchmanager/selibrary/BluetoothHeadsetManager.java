package com.samsung.android.app.watchmanager.selibrary;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import com.samsung.android.app.watchmanager.libinterface.BluetoothHeadsetInterface;

public class BluetoothHeadsetManager implements BluetoothHeadsetInterface {
    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothHeadsetInterface
    public boolean connect(BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice) {
        return bluetoothHeadset.semConnect(bluetoothDevice);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothHeadsetInterface
    public boolean disconnect(BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice) {
        return bluetoothHeadset.semDisconnect(bluetoothDevice);
    }
}
