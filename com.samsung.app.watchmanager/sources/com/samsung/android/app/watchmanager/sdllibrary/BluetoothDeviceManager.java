package com.samsung.android.app.watchmanager.sdllibrary;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface;

public class BluetoothDeviceManager implements BluetoothDeviceInterface {
    public static String TAG = "BluetoothDeviceManager";

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface
    public boolean createBond(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.createBond();
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface
    public String getAliasName(BluetoothDevice bluetoothDevice) {
        String str = (String) ReflectUtil.callMethod(bluetoothDevice, "getAlias", new Object[0]);
        return TextUtils.isEmpty(str) ? bluetoothDevice.getName() : str;
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface
    public byte[] getManufacturerData(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "[SDL_BT]Unused API");
        return null;
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.BluetoothDeviceInterface
    public boolean removeBond(BluetoothDevice bluetoothDevice) {
        return ((Boolean) ReflectUtil.callMethod(bluetoothDevice, "removeBond", new Object[0])).booleanValue();
    }
}
