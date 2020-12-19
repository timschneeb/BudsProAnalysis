package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.util.Log;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HostManagerUtilsDBOperations {
    private static final String TAG = ("tUHM:" + HostManagerUtilsDBOperations.class.getSimpleName());

    public static String getLastDisconnectedDeviceIDFromDB(Context context) {
        String str = null;
        if (context == null) {
            return null;
        }
        RegistryDbManagerWithProvider registryDbManagerWithProvider = new RegistryDbManagerWithProvider();
        List<DeviceRegistryData> queryAllDeviceRegistryData = registryDbManagerWithProvider.queryAllDeviceRegistryData(context);
        if (!queryAllDeviceRegistryData.isEmpty()) {
            Collections.reverse(queryAllDeviceRegistryData);
            List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = registryDbManagerWithProvider.queryLastLaunchDeviceRegistryData(context);
            str = (queryLastLaunchDeviceRegistryData.size() > 0 ? queryLastLaunchDeviceRegistryData.get(0) : queryAllDeviceRegistryData.get(0)).deviceBtID;
        }
        String str2 = TAG;
        Log.d(str2, "getDeviceIDFromDB::connectedDeviceID" + str);
        return str;
    }

    public static String getLastLaunchedPackageName(Context context) {
        List<DeviceRegistryData> queryLastLaunchDeviceRegistryData;
        if (context == null || (queryLastLaunchDeviceRegistryData = new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(context)) == null || queryLastLaunchDeviceRegistryData.isEmpty()) {
            return null;
        }
        return queryLastLaunchDeviceRegistryData.get(0).packagename;
    }

    public static boolean isConnected(Context context, String str) {
        List<DeviceRegistryData> queryDevicebyDeviceIdRegistryData = new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(str, context);
        boolean z = false;
        if (queryDevicebyDeviceIdRegistryData != null && queryDevicebyDeviceIdRegistryData.size() > 0 && queryDevicebyDeviceIdRegistryData.get(0).isConnected == 2) {
            z = true;
        }
        String str2 = TAG;
        com.samsung.android.app.twatchmanager.log.Log.d(str2, "isConnected() btAdderess:" + str + " returns :" + z);
        return z;
    }

    public static boolean isDeviceAlreadyConnected(Context context, String str, String str2) {
        String str3 = TAG;
        com.samsung.android.app.twatchmanager.log.Log.d(str3, "checkSameTypeConnectedWearableDevice()::deviceAddress [" + str + "], simpleDeviceName [" + str2 + "]");
        boolean isMultiConnectionDevice = GearRulesManager.getInstance().isMultiConnectionDevice(str2);
        String str4 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("isMultiConnectedDevice = ");
        sb.append(isMultiConnectionDevice);
        com.samsung.android.app.twatchmanager.log.Log.d(str4, sb.toString());
        if (str2 == null || !isMultiConnectionDevice) {
            if (context == null) {
                context = TWatchManagerApplication.getAppContext();
            }
            if (context != null) {
                Iterator it = ((ArrayList) new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(str, context)).iterator();
                while (it.hasNext()) {
                    DeviceRegistryData deviceRegistryData = (DeviceRegistryData) it.next();
                    if (deviceRegistryData.isConnected == 2 && deviceRegistryData.deviceBtID.equals(str)) {
                        return true;
                    }
                }
            }
            return false;
        }
        com.samsung.android.app.twatchmanager.log.Log.d(TAG, "Don't care about GearCircle.");
        return false;
    }

    public static boolean isExistAddress(Context context, String str) {
        boolean z = false;
        if (context == null) {
            return false;
        }
        if (new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(str, context).size() > 0) {
            z = true;
        }
        String str2 = TAG;
        com.samsung.android.app.twatchmanager.log.Log.d(str2, "isExistAddress()::deviceId exist(" + z + ")");
        return z;
    }
}
