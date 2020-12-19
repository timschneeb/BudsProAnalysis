package com.samsung.android.app.watchmanager.selibrary;

import android.bluetooth.BluetoothDevice;
import com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface;

public class BluetoothDeviceManager implements BluetoothDeviceInterface {
    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface
    public boolean createBond(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semCreateBond((byte) 1);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface
    public String getAliasName(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semGetAliasName();
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface
    public byte[] getManufacturerData(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semGetManufacturerData();
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface
    public boolean removeBond(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semRemoveBond();
    }
}
