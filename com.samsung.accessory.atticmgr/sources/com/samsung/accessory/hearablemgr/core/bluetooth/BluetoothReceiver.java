package com.samsung.accessory.hearablemgr.core.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import seccompat.android.util.Log;

public class BluetoothReceiver extends BroadcastReceiver {
    public static final String ACTION_ALIAS_CHANGED = "android.bluetooth.device.action.ALIAS_CHANGED";
    public static final String TAG = "Attic_BluetoothReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() : " + intent.getAction());
    }
}
