package seccompat.android.bluetooth;

import seccompat.Reflection;
import seccompat.SecCompatUtil;

public class BluetoothDevice {
    public static String proxyGetAliasName(android.bluetooth.BluetoothDevice bluetoothDevice) {
        try {
            String proxyGetAlias = proxyGetAlias(bluetoothDevice);
            return proxyGetAlias == null ? bluetoothDevice.getName() : proxyGetAlias;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String proxyGetAlias(android.bluetooth.BluetoothDevice bluetoothDevice) {
        try {
            return (String) Reflection.callMethod(bluetoothDevice, "getAlias", new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean proxySetAlias(android.bluetooth.BluetoothDevice bluetoothDevice, String str) {
        try {
            Object callMethod = Reflection.callMethod(bluetoothDevice, "semSetAlias", str);
            if (callMethod != null) {
                return ((Boolean) callMethod).booleanValue();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] proxyGetManufacturerData(android.bluetooth.BluetoothDevice bluetoothDevice) {
        if (SecCompatUtil.isSEPDevice()) {
            try {
                return bluetoothDevice.semGetManufacturerData();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public static boolean proxySetManufacturerData(android.bluetooth.BluetoothDevice bluetoothDevice, byte[] bArr) {
        if (SecCompatUtil.isSEPDevice()) {
            try {
                return bluetoothDevice.semSetManufacturerData(bArr);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }
}
