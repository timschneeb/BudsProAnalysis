package com.samsung.android.sdk.bixby2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.samsung.android.sdk.bixby2.LogUtil;

public class ApplicationTriggerReceiver extends BroadcastReceiver {
    private static final String TAG = "ApplicationTriggerReceiver";

    public void onReceive(Context context, Intent intent) {
        LogUtil.i(TAG, "onReceived()");
        if (context != null) {
            context.unregisterReceiver(this);
            LogUtil.i(TAG, "ApplicationTriggerReceiver unRegistered");
        }
    }
}
