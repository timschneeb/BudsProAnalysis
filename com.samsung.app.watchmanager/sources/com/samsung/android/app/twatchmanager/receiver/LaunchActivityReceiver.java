package com.samsung.android.app.twatchmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.ApplicationLaunchManager;

public class LaunchActivityReceiver extends BroadcastReceiver {
    private static final String ACTION_WATCH_MANAGER_ACTIVITY = "com.samsung.connection.ACTION_LAUNCH_APP";
    public static final String EXTRA_DEVICE_ADDRESS_FROM_STUB = "BT_ADD_FROM_STUB";
    private static final String EXTRA_DEVICE_TYPE = "DEVICE_TYPE";
    public static final String EXTRA_MODEL_NAME = "MODEL_NAME";
    private static final String EXTRA_PLUGIN_NAME = "PLUGIN_NAME";
    private static final String EXTRA_REQUESTED_APP = "request_app_package_name";
    private static String TAG = ("tUHM:" + LaunchActivityReceiver.class.getSimpleName());

    private void startGearManagerForStub(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("btAddress");
        String stringExtra2 = intent.getStringExtra("deviceName");
        String stringExtra3 = intent.getStringExtra("requestAppName");
        int intExtra = intent.getIntExtra("deviceType", -1);
        String stringExtra4 = intent.getStringExtra("paramPackageName");
        Intent intent2 = new Intent("android.intent.action.MAIN");
        if (stringExtra != null) {
            intent2.putExtra("BT_ADD_FROM_STUB", stringExtra);
            intent2.putExtra("MODEL_NAME", stringExtra2);
            intent2.putExtra(EXTRA_REQUESTED_APP, stringExtra3);
            intent2.putExtra("DEVICE_TYPE", intExtra);
            intent2.putExtra(EXTRA_PLUGIN_NAME, stringExtra4);
            String str = TAG;
            Log.d(str, "startGearManagerForStub with intent - " + stringExtra + " / " + stringExtra2 + " / " + stringExtra3);
            ApplicationLaunchManager.getInstance().startGearManagerFromStub(context, intent2);
            return;
        }
        Log.d(TAG, "Mac address is null, return");
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String str = TAG;
            Log.d(str, "onReceive, intent: " + intent);
            if (ACTION_WATCH_MANAGER_ACTIVITY.equals(intent.getAction())) {
                startGearManagerForStub(context, intent);
            }
        }
    }
}
