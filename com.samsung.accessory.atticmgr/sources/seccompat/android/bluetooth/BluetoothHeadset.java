package seccompat.android.bluetooth;

import android.bluetooth.BluetoothDevice;
import seccompat.Reflection;
import seccompat.SecCompatUtil;

public class BluetoothHeadset {
    public static int proxyGetFeatureIdSupportIBR() {
        if (SecCompatUtil.isSEPDevice()) {
            return 300;
        }
        try {
            return ((Integer) Reflection.getStaticField("android.bluetooth.BluetoothHeadset.FEATURE_ID_SUPPORTED_INBAND")).intValue();
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    public static int proxyGetSettingIdApplyedIBR() {
        if (SecCompatUtil.isSEPDevice()) {
            return 200;
        }
        try {
            return ((Integer) Reflection.getStaticField("android.bluetooth.BluetoothHeadset.SETTING_ID_HEADSET_APPLYED_INBAND")).intValue();
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    public static boolean proxySetHeadsetSettings(android.bluetooth.BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice, int i, int i2) {
        if (SecCompatUtil.isSEPDevice()) {
            return bluetoothHeadset.semSetHeadsetSetting(bluetoothDevice, i, i2);
        }
        try {
            return ((Boolean) Reflection.callMethod(bluetoothHeadset, "setHeadsetSettings", bluetoothDevice, Integer.valueOf(i), Integer.valueOf(i2))).booleanValue();
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static int proxyGetHeadsetSettings(android.bluetooth.BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice, int i) {
        if (SecCompatUtil.isSEPDevice()) {
            return bluetoothHeadset.semGetHeadsetSetting(bluetoothDevice, i);
        }
        try {
            return ((Integer) Reflection.callMethod(bluetoothHeadset, "getHeadsetSettings", bluetoothDevice, Integer.valueOf(i))).intValue();
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    public static int proxyGetFeatureSettings(android.bluetooth.BluetoothHeadset bluetoothHeadset, int i) {
        if (SecCompatUtil.isSEPDevice()) {
            return bluetoothHeadset.semGetFeatureSetting(i);
        }
        try {
            return ((Integer) Reflection.callMethod(bluetoothHeadset, "getFeatureSettings", Integer.valueOf(i))).intValue();
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    public static boolean proxyConnect(android.bluetooth.BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice) {
        Boolean bool = (Boolean) Reflection.callMethod(bluetoothHeadset, "connect", bluetoothDevice);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static boolean proxyDisconnect(android.bluetooth.BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice) {
        Boolean bool = (Boolean) Reflection.callMethod(bluetoothHeadset, "disconnect", bluetoothDevice);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }
}
