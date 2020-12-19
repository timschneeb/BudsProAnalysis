package com.samsung.android.app.watchmanager.libinterface;

import android.bluetooth.BluetoothDevice;

public interface BluetoothDeviceInterface {
    boolean createBond(BluetoothDevice bluetoothDevice);

    String getAliasName(BluetoothDevice bluetoothDevice);

    byte[] getManufacturerData(BluetoothDevice bluetoothDevice);

    boolean removeBond(BluetoothDevice bluetoothDevice);
}
