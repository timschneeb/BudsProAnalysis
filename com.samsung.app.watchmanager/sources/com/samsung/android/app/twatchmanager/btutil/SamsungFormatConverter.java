package com.samsung.android.app.twatchmanager.btutil;

import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.util.Log;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;

public class SamsungFormatConverter {
    private static final int BD_ADDR_LEN = 6;
    private static final int BD_UUID_LEN = 16;
    private static final String TAG = ("tUHM;" + SamsungFormatConverter.class.getSimpleName());

    public static String convertLEtoBR(String str, byte[] bArr) {
        byte[] bytesFromAddress = getBytesFromAddress(str);
        String addressStringFromByte = getAddressStringFromByte(bArr);
        byte[] bArr2 = new byte[6];
        for (int i = 0; i < 6; i++) {
            bArr2[i] = (byte) (bArr[(6 - i) - 1] ^ bytesFromAddress[i]);
        }
        String addressStringFromByte2 = getAddressStringFromByte(bArr2);
        if (isEngBuild()) {
            String str2 = TAG;
            Log.d(str2, "ble addr - " + str);
            String str3 = TAG;
            Log.d(str3, "key addr - " + addressStringFromByte);
            String str4 = TAG;
            Log.d(str4, "br addr - " + addressStringFromByte2);
        }
        return addressStringFromByte2;
    }

    public static String getAddressStringFromByte(byte[] bArr) {
        if (bArr == null || bArr.length != 6) {
            return null;
        }
        return String.format("%02X:%02X:%02X:%02X:%02X:%02X", Byte.valueOf(bArr[0]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[3]), Byte.valueOf(bArr[4]), Byte.valueOf(bArr[5]));
    }

    public static byte[] getBytesFromAddress(String str) {
        byte[] bArr = new byte[6];
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            if (str.charAt(i) != ':') {
                bArr[i2] = (byte) Integer.parseInt(str.substring(i, i + 2), 16);
                i2++;
                i++;
            }
            i++;
        }
        return bArr;
    }

    public static ManufacturerData getManufacturerData(BluetoothDevice bluetoothDevice) {
        try {
            byte[] manufacturerData = BluetoothDeviceFactory.get().getManufacturerData(bluetoothDevice);
            if (isEngBuild()) {
                printRawdataByte(manufacturerData);
            }
            byte[] singleManufacturerData = getSingleManufacturerData(manufacturerData);
            if (isEngBuild()) {
                printRawdataByte(singleManufacturerData);
            }
            return new ManufacturerData(singleManufacturerData);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getSingleManufacturerData(byte[] bArr) {
        if (bArr == null || bArr.length - 1 <= bArr[0]) {
            Log.d(TAG, "getSingleManufactuerData - single data");
            return bArr;
        }
        Log.d(TAG, "getSingleManufactuerData - multi data");
        byte[] bArr2 = new byte[(bArr.length - 4)];
        int i = 0;
        while (i < bArr[0]) {
            bArr2[i] = bArr[i];
            i++;
        }
        for (int i2 = i + 4; i2 < bArr.length; i2++) {
            bArr2[i] = bArr[i2];
            i++;
        }
        return bArr2;
    }

    public static boolean isEngBuild() {
        return "eng".equals(Build.TYPE);
    }

    private static void printRawdataByte(byte[] bArr) {
        String str = "";
        for (int i = 0; bArr.length > i; i++) {
            str = str + String.format("%02X", Byte.valueOf(bArr[i])) + " ";
        }
        Log.d(TAG, "printRawdataByte - length : " + bArr.length + "  raw : " + str);
    }
}
