package com.samsung.android.app.watchmanager.libinterface;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;

public interface BluetoothHeadsetInterface {
    boolean connect(BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice);

    boolean disconnect(BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice);
}
