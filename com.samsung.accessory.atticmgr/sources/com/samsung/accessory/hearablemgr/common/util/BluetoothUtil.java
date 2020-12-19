package com.samsung.accessory.hearablemgr.common.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import java.util.Iterator;
import java.util.Set;
import seccompat.Reflection;
import seccompat.SecCompatUtil;
import seccompat.android.util.Log;

public class BluetoothUtil {
    public static final String ACTION_ALIAS_CHANGED = "android.bluetooth.device.action.ALIAS_CHANGED";
    private static final String TAG = "Attic_BluetoothUtil";

    public static boolean isConnecting(int i) {
        return i == 1 || i == 2;
    }

    public static boolean isDisconnecting(int i) {
        return i == 3 || i == 0;
    }

    public static BluetoothAdapter getAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    public static boolean isAdapterOn() {
        boolean z = getAdapter() != null && getAdapter().isEnabled();
        Log.d(TAG, "isAdapterOn() : " + z);
        return z;
    }

    public static BluetoothDevice getBondedDevice(String str) {
        BluetoothDevice bluetoothDevice;
        Set<BluetoothDevice> bondedDevices;
        if (!TextUtils.isEmpty(str) && getAdapter() != null && (bondedDevices = getAdapter().getBondedDevices()) != null) {
            Iterator<BluetoothDevice> it = bondedDevices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                bluetoothDevice = it.next();
                if (str.equalsIgnoreCase(bluetoothDevice.getAddress())) {
                    break;
                }
            }
        }
        bluetoothDevice = null;
        Log.d(TAG, "getBondedDevice() : " + deviceToString(bluetoothDevice));
        return bluetoothDevice;
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "DISCONNECTED";
        }
        if (i == 1) {
            return "CONNECTING...";
        }
        if (i == 2) {
            return "CONNECTED";
        }
        if (i == 3) {
            return "DISCONNECTING...";
        }
        switch (i) {
            case 10:
                return "BOND_NONE";
            case 11:
                return "BOND_BONDING...";
            case 12:
                return "BOND_BONDED";
            default:
                return String.valueOf(i);
        }
    }

    public static String adapterStateToString(int i) {
        switch (i) {
            case 10:
                return "OFF";
            case 11:
                return "TURNING_ON...";
            case 12:
                return "ON";
            case 13:
                return "TURNING_OFF...";
            default:
                return String.valueOf(i);
        }
    }

    public static String profileToString(int i) {
        if (i != 1) {
            return i != 2 ? String.valueOf(i) : "A2DP";
        }
        return "HEADSET";
    }

    public static String deviceToString(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return "null";
        }
        return privateAddress(bluetoothDevice.getAddress()) + bluetoothDevice.getName();
    }

    public static String privateAddress(String str) {
        if (Application.DEBUG_MODE || TextUtils.isEmpty(str)) {
            return "<< " + str + " >>";
        }
        String[] split = str.split(":");
        if (split.length < 1) {
            return "<< SPLIT ERROR! >>";
        }
        return "<< " + split[0] + ":__:__:__:__:" + split[split.length - 1] + " >>";
    }

    public static String privateAddress(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice == null ? "null" : privateAddress(bluetoothDevice.getAddress());
    }

    public static String autoPrivateAddress(String str) {
        return (!TextUtils.isEmpty(str) && str.split(":").length == 6) ? privateAddress(str) : str;
    }

    public static String autoPrivateAddress(Object obj) {
        return obj != null ? autoPrivateAddress(obj.toString()) : "null";
    }

    public static String getAliasName(String str) {
        BluetoothDevice bondedDevice = getBondedDevice(str);
        String str2 = null;
        if (bondedDevice == null) {
            return Preferences.getString(PreferenceKey.LAST_BT_DEVICE_ALIAS_NAME, null, str);
        }
        try {
            str2 = seccompat.android.bluetooth.BluetoothDevice.proxyGetAliasName(bondedDevice);
        } catch (Error | Exception e) {
            Log.e(TAG, "getAliasName() : " + e);
        }
        if (str2 != null) {
            Preferences.putString(PreferenceKey.LAST_BT_DEVICE_ALIAS_NAME, str2, str);
        }
        return str2;
    }

    public static String getAlias(String str) {
        BluetoothDevice bondedDevice = getBondedDevice(str);
        String str2 = null;
        if (bondedDevice == null) {
            return Preferences.getString(PreferenceKey.LAST_BT_DEVICE_ALIAS_NAME, null, str);
        }
        try {
            str2 = seccompat.android.bluetooth.BluetoothDevice.proxyGetAlias(bondedDevice);
        } catch (Error | Exception e) {
            Log.e(TAG, "getAlias() : " + e);
        }
        if (str2 != null) {
            Preferences.putString(PreferenceKey.LAST_BT_DEVICE_ALIAS_NAME, str2, str);
        }
        return str2;
    }

    public static String getDeviceName(String str) {
        BluetoothDevice bondedDevice = getBondedDevice(str);
        if (bondedDevice == null) {
            return Preferences.getString(PreferenceKey.LAST_BT_DEVICE_NAME, null, str);
        }
        String name = bondedDevice.getName();
        if (name != null) {
            Preferences.putString(PreferenceKey.LAST_BT_DEVICE_NAME, name, str);
        }
        return name;
    }

    public static boolean equalsDevice(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2) {
        return (bluetoothDevice == null || bluetoothDevice2 == null || !TextUtils.equals(bluetoothDevice.getAddress(), bluetoothDevice2.getAddress())) ? false : true;
    }

    public static void setAliasName(String str, String str2) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
            if (bondedDevices != null) {
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (bluetoothDevice.getAddress().equals(str)) {
                        try {
                            if (SecCompatUtil.isSEPDevice()) {
                                seccompat.android.bluetooth.BluetoothDevice.proxySetAlias(bluetoothDevice, str2);
                            } else {
                                Reflection.callMethod(bluetoothDevice, "setAlias", str2);
                            }
                        } catch (Error | Exception e) {
                            Log.e(TAG, "setAliasName() : " + e);
                        }
                    }
                }
                return;
            }
            Log.i(TAG, "pairedDevices is null");
        }
    }
}
