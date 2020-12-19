package com.samsung.android.fotaprovider.receiver;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import com.samsung.android.fotaprovider.log.Log;

public class BroadcastRegister {
    private static final BroadcastRegister instance = new BroadcastRegister();
    private SystemEventReceiver systemEventReceiver = null;

    private BroadcastRegister() {
    }

    public static BroadcastRegister getInstance() {
        return instance;
    }

    public void registerSystemEventReceiverForUserUnlockedFilter(Context context) {
        Log.I("");
        if (context == null) {
            Log.W("context is null");
            return;
        }
        createSystemEventReceiver();
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                context.registerReceiver(this.systemEventReceiver, createIntentFilter("android.intent.action.USER_UNLOCKED"));
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
    }

    public void unregisterSystemEventReceiverWithAllFilters(Context context) {
        Log.I("");
        if (context == null) {
            Log.W("context is null");
            return;
        }
        SystemEventReceiver systemEventReceiver2 = this.systemEventReceiver;
        if (systemEventReceiver2 != null) {
            try {
                context.unregisterReceiver(systemEventReceiver2);
                this.systemEventReceiver = null;
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
    }

    private void createSystemEventReceiver() {
        if (this.systemEventReceiver == null) {
            this.systemEventReceiver = new SystemEventReceiver();
        }
    }

    private IntentFilter createIntentFilter(String... strArr) {
        IntentFilter intentFilter = new IntentFilter();
        for (String str : strArr) {
            intentFilter.addAction(str);
        }
        return intentFilter;
    }
}
