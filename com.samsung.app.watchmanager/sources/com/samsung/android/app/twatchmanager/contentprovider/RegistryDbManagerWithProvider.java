package com.samsung.android.app.twatchmanager.contentprovider;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegistryDbManagerWithProvider {
    private static final String TAG = "tUHM:RegistryDbManagerWithProvider";

    /* JADX WARNING: Removed duplicated region for block: B:7:0x006e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData> getRegistryData(android.database.Cursor r12) {
        /*
        // Method dump skipped, instructions count: 114
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider.getRegistryData(android.database.Cursor):java.util.List");
    }

    public static int updateDeviceRegistryConnectionState(Context context, String str, int i) {
        Log.d(TAG, "updateDeviceRegistryConnectionState:" + str + ", connectionState = " + i);
        return new RegistryDbManagerWithProvider().updateDeviceConnectionState(str, i, context);
    }

    public Uri addDeviceRegistryData(DeviceRegistryData deviceRegistryData, Context context) {
        String str;
        Log.d(TAG, "addDeviceRegistryData: " + deviceRegistryData);
        Uri uri = null;
        if (deviceRegistryData.deviceName == null || deviceRegistryData.deviceBtID.isEmpty()) {
            str = "addDeviceRegistryData: device not valid";
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseContentProvider.DEVICE_NAME, deviceRegistryData.deviceName);
            String str2 = deviceRegistryData.deviceFixedName;
            if (str2 != null) {
                contentValues.put(BaseContentProvider.DEVICE_FIXED_NAME, str2);
            }
            contentValues.put(BaseContentProvider.DEVICE_BT_ID, deviceRegistryData.deviceBtID);
            contentValues.put("package_name", deviceRegistryData.packagename);
            contentValues.put(BaseContentProvider.LAST_LAUNCH, Integer.valueOf(deviceRegistryData.lastLaunch));
            contentValues.put(BaseContentProvider.DEVICE_FEATURE_NECKLETAUTOCONNECTION, deviceRegistryData.neckletAutoConnection);
            contentValues.put(BaseContentProvider.DEVICE_CONNECTED, Integer.valueOf(deviceRegistryData.isConnected));
            contentValues.put(BaseContentProvider.DEVICE_SUPPORTS_PAIRING, Integer.valueOf(deviceRegistryData.supportsPairing));
            try {
                uri = context.getContentResolver().insert(BaseContentProvider.DEVICE_CONTENT_URI, contentValues);
            } catch (RuntimeException unused) {
                Log.e(TAG, "error addDeviceRegistryData");
            }
            updateDeviceLastLaunchRegistryData(BaseContentProvider.DEVICE_BT_ID, context);
            printAllDeviceData("addDeviceRegistryData", context);
            str = "addDeviceRegistryData, result = " + uri;
        }
        Log.d(TAG, str);
        return uri;
    }

    public int deleteDeviceRegistryDataDeviceID(String str, Context context) {
        Log.d(TAG, "deleteDeviceRegistryDataDeviceID() deviceID=" + str);
        int i = 0;
        if (str != null) {
            String[] strArr = {""};
            strArr[0] = str;
            try {
                i = context.getContentResolver().delete(BaseContentProvider.DEVICE_CONTENT_URI, "bt_id=?", strArr);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            printAllDeviceData("deleteDeviceRegistryData", context);
            Log.d(TAG, "deleteDeviceRegistryData, rowsDeleted:" + i);
        }
        return i;
    }

    public void printAllDeviceData(String str, Context context) {
        List<DeviceRegistryData> queryAllDeviceRegistryData = queryAllDeviceRegistryData(context);
        if (queryAllDeviceRegistryData != null) {
            Log.d(TAG, "DB:: called from " + str);
            Log.d(TAG, "DB:: Device list!!");
            Iterator<DeviceRegistryData> it = queryAllDeviceRegistryData.iterator();
            while (it.hasNext()) {
                Log.d(TAG, "DB:: " + it.next());
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0089  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData> queryAllDeviceRegistryData(android.content.Context r13) {
        /*
        // Method dump skipped, instructions count: 165
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider.queryAllDeviceRegistryData(android.content.Context):java.util.List");
    }

    public List<DeviceRegistryData> queryConnectedDevice(Context context) {
        Cursor cursor;
        Log.d(TAG, "quaryConnectedDevice");
        try {
            cursor = context.getContentResolver().query(BaseContentProvider.DEVICE_CONTENT_URI, null, "connected = ?", new String[]{String.valueOf(2)}, null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            cursor = null;
        }
        List<DeviceRegistryData> registryData = getRegistryData(cursor);
        Log.d(TAG, "queryAllDeviceRegistryData, size = " + registryData.size());
        return registryData;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x00b2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData> queryDevicebyDeviceIdRegistryData(java.lang.String r12, android.content.Context r13) {
        /*
        // Method dump skipped, instructions count: 206
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider.queryDevicebyDeviceIdRegistryData(java.lang.String, android.content.Context):java.util.List");
    }

    @TargetApi(16)
    public List<DeviceRegistryData> queryLastLaunchDeviceRegistryData(Context context) {
        Cursor cursor;
        Log.d(TAG, "queryLastLaunchDeviceRegistryData");
        try {
            cursor = context.getContentResolver().query(BaseContentProvider.DEVICE_CONTENT_URI, null, "last_launch = 1", null, null, null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            cursor = null;
        }
        List<DeviceRegistryData> registryData = getRegistryData(cursor);
        Log.d(TAG, "queryLastLaunchDeviceRegistryData, size = " + registryData.size());
        return registryData;
    }

    public int updateDeviceConnectionState(String str, int i, Context context) {
        Log.d(TAG, "updateDeviceConnectionState:" + str + "  connected:" + i);
        ArrayList arrayList = (ArrayList) queryDevicebyDeviceIdRegistryData(str, context);
        int i2 = 0;
        if (arrayList.size() == 0) {
            Log.e(TAG, "updateDeviceConnectionState() : getDeviceRegistryDataByDeviceId has 0 value");
            return 0;
        }
        DeviceRegistryData deviceRegistryData = (DeviceRegistryData) arrayList.get(0);
        if (deviceRegistryData == null || deviceRegistryData.isConnected != i) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseContentProvider.DEVICE_CONNECTED, Integer.valueOf(i));
            try {
                i2 = context.getContentResolver().update(BaseContentProvider.DEVICE_CONTENT_URI, contentValues, "bt_id = ?", new String[]{str});
            } catch (RuntimeException unused) {
                Log.e(TAG, "error updateDeviceConnectionState()");
            }
            Log.d(TAG, "updateDeviceConnectionState: count is " + i2);
            if (i2 > 0) {
                Log.d(TAG, "updateDeviceConnectionState: updated. SendBroadcast!");
                Intent intent = new Intent(GlobalConst.ACTION_UHM_DB_CONNECTION_UPDATED);
                Bundle bundle = new Bundle();
                bundle.putString("deviceId", str);
                bundle.putInt("conntected", i);
                intent.putExtras(bundle);
                context.sendBroadcast(intent);
            }
            printAllDeviceData("updateDeviceConnectionState", context);
            return i2;
        }
        Log.e(TAG, "updateDeviceConnectionState() : connection state is same.");
        return 0;
    }

    public int updateDeviceLastLaunchRegistryData(String str, Context context) {
        int i;
        Log.d(TAG, "updateDeviceLastLaunchRegistryData:" + str);
        ContentValues contentValues = new ContentValues();
        int i2 = 0;
        contentValues.put(BaseContentProvider.LAST_LAUNCH, (Integer) 0);
        try {
            i = context.getContentResolver().update(BaseContentProvider.DEVICE_CONTENT_URI, contentValues, "last_launch = 1", null);
        } catch (RuntimeException unused) {
            Log.e(TAG, "updateDeviceLastLaunchRegistryData() error");
            i = 0;
        }
        Log.d(TAG, "updateDeviceLastLaunchRegistryData() last_launch 1->0, count:" + i);
        contentValues.clear();
        contentValues.put(BaseContentProvider.LAST_LAUNCH, (Integer) 1);
        try {
            i2 = context.getContentResolver().update(BaseContentProvider.DEVICE_CONTENT_URI, contentValues, "bt_id = ?", new String[]{str});
        } catch (RuntimeException unused2) {
            Log.e(TAG, "updateDeviceLastLaunchRegistryData() error");
        }
        Log.d(TAG, "updateDeviceLastLaunchRegistryData()  last_launch 0->1, result ... " + i2);
        printAllDeviceData("updateDeviceLastLaunchRegistryData", context);
        return i2;
    }

    public int updateDevicePackageNameRegistryData(String str, String str2, Context context) {
        Log.d(TAG, "updateDevicePackageNameRegistryData() packageName : " + str2);
        int i = 0;
        if (TextUtils.isEmpty(str2)) {
            return 0;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("package_name", str2);
        try {
            i = context.getContentResolver().update(BaseContentProvider.DEVICE_CONTENT_URI, contentValues, "bt_id = ?", new String[]{str});
        } catch (RuntimeException unused) {
            Log.e(TAG, "error updateDevicePackageNameRegistryData");
        }
        printAllDeviceData("updateDevicePackageNameRegistryData", context);
        Log.d(TAG, "updateDevicePackageNameRegistryData, count:" + i);
        return i;
    }
}
