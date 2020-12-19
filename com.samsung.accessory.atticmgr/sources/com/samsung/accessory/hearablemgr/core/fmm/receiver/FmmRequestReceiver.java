package com.samsung.accessory.hearablemgr.core.fmm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.fmm.FmmManager;
import seccompat.android.util.Log;

public class FmmRequestReceiver extends BroadcastReceiver {
    private static final String TAG = (Application.TAG_ + FmmRequestReceiver.class.getSimpleName());

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            String stringExtra = intent.getStringExtra("operation");
            String stringExtra2 = intent.getStringExtra("uid");
            String str = TAG;
            Log.i(str, "onReceive(): " + intent.getAction() + ", operation = " + stringExtra + ", uid: " + stringExtra2);
            FmmManager.handleResponse(context, intent);
        }
    }
}
