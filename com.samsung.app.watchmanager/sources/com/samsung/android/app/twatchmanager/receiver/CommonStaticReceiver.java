package com.samsung.android.app.twatchmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;

public class CommonStaticReceiver extends BroadcastReceiver {
    private static final String SAMSUNG_MEMBERS_ACCESSORY_LOG_REQUEST = "com.samsung.android.gearlog_sm_request";
    private static final String TAG = ("tUHM:" + CommonStaticReceiver.class.getSimpleName());

    private void handleSamsungMembersAccessoryRequest(Context context, Intent intent) {
        for (DeviceRegistryData deviceRegistryData : new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(context)) {
            if (deviceRegistryData.isConnected == 2) {
                String str = TAG;
                Log.d(str, "connected device package = " + deviceRegistryData.packagename);
                Intent intent2 = new Intent(SAMSUNG_MEMBERS_ACCESSORY_LOG_REQUEST);
                intent2.setPackage(deviceRegistryData.packagename);
                intent2.putExtra("isFromTUHM", true);
                context.sendBroadcast(intent2);
            }
        }
    }

    public void onReceive(Context context, Intent intent) {
        String str = TAG;
        Log.d(str, "onReceive [" + intent + "]");
        String action = intent.getAction();
        if (action != null) {
            String str2 = TAG;
            Log.d(str2, "intent getAction=" + action);
            if (SAMSUNG_MEMBERS_ACCESSORY_LOG_REQUEST.equalsIgnoreCase(action) && !intent.hasExtra("isFromTUHM")) {
                handleSamsungMembersAccessoryRequest(context, intent);
            }
        }
    }
}
