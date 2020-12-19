package com.samsung.android.app.twatchmanager.btutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.update.UpdateCheckTask;
import java.util.Locale;

public class OldFormatConverter {
    private static final String BUDS_NAME = "Buds";
    private static final String TAG = ("tUHM:" + OldFormatConverter.class.getSimpleName());
    private final String[] BLE_ONLY_DEVICES = {"Galaxy Fit"};
    private GearRulesManager mGearRulesManager;

    public OldFormatConverter(GearRulesManager gearRulesManager) {
        this.mGearRulesManager = gearRulesManager;
    }

    public static BluetoothDevice ConvertDeviceBRtoLE(BluetoothDevice bluetoothDevice) {
        String convertBREDRtoLE = convertBREDRtoLE(bluetoothDevice.getAddress());
        if (convertBREDRtoLE != null) {
            return getRemoteDevice(convertBREDRtoLE);
        }
        Log.d(TAG, "ConvertDeviceLEtoBR - converAddr is null");
        return null;
    }

    public static BluetoothDevice ConvertDeviceLEtoBR(BluetoothDevice bluetoothDevice) {
        String convertLEtoBREDR = convertLEtoBREDR(getBRFirstAddress(bluetoothDevice), bluetoothDevice.getAddress());
        if (convertLEtoBREDR != null) {
            return getRemoteDevice(convertLEtoBREDR);
        }
        Log.d(TAG, "ConvertDeviceLEtoBR - converAddr is null");
        return null;
    }

    private BluetoothDevice checkDeviceNeedToConvert(String str, String str2) {
        BluetoothDevice remoteDevice = getRemoteDevice(str);
        if (remoteDevice == null) {
            Log.w(TAG, "checkDeviceNeedToConvert() : device is null");
            return null;
        }
        if (str2 == null) {
            str2 = remoteDevice.getName();
        }
        String str3 = TAG;
        Log.d(str3, "checkDeviceNeedToConvert() : " + str2 + " / " + str);
        if (!isBLEOnlyDevice(str2) && isLEdevice(remoteDevice, true)) {
            Log.d(TAG, "checkDeviceNeedToConvert() : get BR device from LE device");
            BluetoothDevice ConvertDeviceLEtoBR = ConvertDeviceLEtoBR(remoteDevice);
            if (ConvertDeviceLEtoBR != null) {
                return ConvertDeviceLEtoBR;
            }
        }
        Log.d(TAG, "checkDeviceNeedToConvert() : device does not need to be converted");
        return remoteDevice;
    }

    public static String convertBREDRtoLE(String str) {
        byte[] bArr = new byte[6];
        byte[] bArr2 = new byte[6];
        for (int i = 0; i < 6; i++) {
            int i2 = (i * 2) + i;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        bArr2[0] = (byte) (bArr[0] | 192);
        for (int i3 = 1; i3 < 6; i3++) {
            bArr2[i3] = (byte) (((bArr[i3] & Byte.MAX_VALUE) << 1) | ((bArr[i3] & 128) >> 7));
        }
        StringBuilder sb = new StringBuilder();
        int i4 = bArr2[0] & 255;
        sb.append((i4 / 16 == 0 || i4 == 0) ? UpdateCheckTask.RESULT_CANT_FIND_APP + Integer.toHexString(i4) : Integer.toHexString(i4));
        sb.append(":");
        for (int i5 = 1; i5 < 6; i5++) {
            int i6 = bArr2[i5] & 255;
            sb.append((i6 / 16 == 0 || i6 == 0) ? UpdateCheckTask.RESULT_CANT_FIND_APP + Integer.toHexString(i6) : Integer.toHexString(i6));
            if (i5 != 5) {
                sb.append(":");
            }
        }
        String upperCase = sb.toString().toUpperCase(Locale.ENGLISH);
        Log.d(TAG, "convert addr - " + upperCase);
        return upperCase;
    }

    public static String convertLEtoBREDR(String str, String str2) {
        byte[] bArr = new byte[6];
        byte[] bArr2 = new byte[6];
        for (int i = 0; i < 6; i++) {
            int i2 = (i * 2) + i;
            bArr[i] = (byte) Integer.parseInt(str2.substring(i2, i2 + 2), 16);
        }
        bArr2[0] = bArr[0];
        for (int i3 = 1; i3 < 6; i3++) {
            bArr2[i3] = (byte) (((bArr[i3] & 254) >> 1) | ((bArr[i3] & 1) << 7));
        }
        StringBuilder sb = new StringBuilder();
        int i4 = bArr2[0] & 255;
        sb.append((i4 / 16 == 0 || i4 == 0) ? UpdateCheckTask.RESULT_CANT_FIND_APP + Integer.toHexString(i4) : Integer.toHexString(i4));
        sb.append(":");
        for (int i5 = 1; i5 < 6; i5++) {
            int i6 = bArr2[i5] & 255;
            sb.append((i6 / 16 == 0 || i6 == 0) ? UpdateCheckTask.RESULT_CANT_FIND_APP + Integer.toHexString(i6) : Integer.toHexString(i6));
            if (i5 != 5) {
                sb.append(":");
            }
        }
        String upperCase = sb.toString().toUpperCase(Locale.ENGLISH);
        String replaceFirst = upperCase.replaceFirst(upperCase.substring(0, 1), str);
        Log.d(TAG, "convert addr - " + replaceFirst);
        return replaceFirst;
    }

    private static String getBRFirstAddress(BluetoothDevice bluetoothDevice) {
        String str;
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            Log.d(TAG, "getBRFirstAddress - Bluetooth adapter is null");
            return UpdateCheckTask.RESULT_CANT_FIND_APP;
        }
        try {
            String name = bluetoothDevice.getName();
            if (name.contains(BUDS_NAME)) {
                byte[] manufacturerData = BluetoothDeviceFactory.get().getManufacturerData(bluetoothDevice);
                String str2 = TAG;
                Log.d(str2, "getBRFirstAddress - manufacturerData.length : " + manufacturerData.length);
                if (manufacturerData == null || manufacturerData.length < 11) {
                    str = "";
                } else {
                    str = String.format("%H", Byte.valueOf(manufacturerData[10]));
                }
            } else {
                Log.d(TAG, "getBRFirstAddress - Legacy Format");
                int indexOf = name.indexOf("(");
                str = name.substring(indexOf + 1, indexOf + 2);
            }
            String str3 = TAG;
            Log.d(str3, "getBRFirstAddress - firstAddr" + str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return UpdateCheckTask.RESULT_CANT_FIND_APP;
        }
    }

    public static String getDeviceNameFromBRaddr(String str) {
        BluetoothDevice ConvertDeviceBRtoLE = ConvertDeviceBRtoLE(getRemoteDevice(str));
        if (ConvertDeviceBRtoLE == null) {
            return null;
        }
        String name = ConvertDeviceBRtoLE.getName();
        if (name == null || name.length() <= 7) {
            return name;
        }
        String substring = name.substring(0, name.length() - 3);
        String str2 = TAG;
        Log.d(str2, "getDeviceNameFromBRaddr - strName" + substring);
        return substring;
    }

    public static BluetoothDevice getRemoteDevice(String str) {
        String str2;
        StringBuilder sb;
        String str3;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.d(TAG, "getRemoteDevice - Bluetooth adapter is null");
            return null;
        }
        try {
            return defaultAdapter.getRemoteDevice(str);
        } catch (IllegalArgumentException e) {
            str3 = TAG;
            sb = new StringBuilder();
            sb.append("getRemoteDevice - ");
            str2 = e.toString();
        } catch (Exception e2) {
            str3 = TAG;
            sb = new StringBuilder();
            sb.append("getRemoteDevice - ");
            str2 = e2.toString();
        }
        sb.append(str2);
        Log.e(str3, sb.toString());
        return null;
    }

    private boolean isBLEOnlyDevice(String str) {
        if (str != null) {
            for (String str2 : this.BLE_ONLY_DEVICES) {
                if (str.contains(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLEdevice(BluetoothDevice bluetoothDevice, boolean z) {
        if (bluetoothDevice == null) {
            return false;
        }
        return (z || this.mGearRulesManager.isPossibleSamsungWearable(bluetoothDevice.getName())) && bluetoothDevice.getType() == 2;
    }

    public BluetoothDevice getBRdevice(String str) {
        return checkDeviceNeedToConvert(str, null);
    }

    public BluetoothDevice getBrDeviceFromStub(String str, String str2) {
        return checkDeviceNeedToConvert(str, str2);
    }

    public boolean isLEdevice(BluetoothDevice bluetoothDevice) {
        return isLEdevice(bluetoothDevice, false);
    }
}
