package seccompat.android.bluetooth;

import android.bluetooth.BluetoothDevice;
import seccompat.Reflection;
import seccompat.SecCompatUtil;

public class BluetoothA2dp {
    public static final String ACTION_DUAL_PLAY_MODE_ENABLED = "com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED";
    public static final int SEM_LATENCY_DEFAULT = 0;
    public static final int SEM_LATENCY_LOW = 1;

    public static BluetoothDevice proxySemGetActiveStreamDevice(android.bluetooth.BluetoothA2dp bluetoothA2dp) {
        if (SecCompatUtil.isSEPDevice()) {
            return bluetoothA2dp.semGetActiveStreamDevice();
        }
        return null;
    }

    public static boolean proxyConnect(android.bluetooth.BluetoothA2dp bluetoothA2dp, BluetoothDevice bluetoothDevice) {
        Boolean bool = (Boolean) Reflection.callMethod(bluetoothA2dp, "connect", bluetoothDevice);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static boolean proxyDisconnect(android.bluetooth.BluetoothA2dp bluetoothA2dp, BluetoothDevice bluetoothDevice) {
        Boolean bool = (Boolean) Reflection.callMethod(bluetoothA2dp, "disconnect", bluetoothDevice);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static boolean proxySemIsDualPlayMode(android.bluetooth.BluetoothA2dp bluetoothA2dp) {
        try {
            if (SecCompatUtil.isSEPDevice()) {
                return bluetoothA2dp.semIsDualPlayMode();
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static boolean proxySemSetLatency(android.bluetooth.BluetoothA2dp bluetoothA2dp, BluetoothDevice bluetoothDevice, int i) {
        try {
            Boolean bool = (Boolean) Reflection.callMethod(bluetoothA2dp, "semSetLatency", bluetoothDevice, Integer.valueOf(i));
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }
}
