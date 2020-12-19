package com.samsung.accessory.hearablemgr.core.gamemode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.util.HashSet;
import java.util.Set;
import seccompat.android.util.Log;

public class ScreenOnOffReceiver extends BroadcastReceiver {
    private static final String TAG = "Attic_ScreenOnOffReceiver";
    private Context mContext;
    private final BroadcastReceiver mDynamicReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.gamemode.ScreenOnOffReceiver.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            Log.d(ScreenOnOffReceiver.TAG, "mDynamicReceiver.onReceive() : " + intent.getAction());
            Log.d(ScreenOnOffReceiver.TAG, "isInUserUse() : " + Util.isInUserUse());
            for (BroadcastReceiver broadcastReceiver : ScreenOnOffReceiver.this.mListenReceivers) {
                broadcastReceiver.onReceive(context, intent);
            }
        }
    };
    private Set<BroadcastReceiver> mListenReceivers;

    public ScreenOnOffReceiver() {
    }

    public ScreenOnOffReceiver(Context context) {
        this.mContext = context;
        this.mListenReceivers = new HashSet();
        this.mContext.registerReceiver(this.mDynamicReceiver, getIntentFilter());
    }

    public void destroy() {
        this.mContext.unregisterReceiver(this.mDynamicReceiver);
        this.mListenReceivers.clear();
        this.mContext = null;
    }

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() : " + intent.getAction());
    }

    private static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        return intentFilter;
    }

    public void addReceiver(BroadcastReceiver broadcastReceiver) {
        this.mListenReceivers.add(broadcastReceiver);
    }

    public void removeReceiver(BroadcastReceiver broadcastReceiver) {
        this.mListenReceivers.remove(broadcastReceiver);
    }
}
