package com.samsung.accessory.hearablemgr.core.gamemode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import seccompat.android.util.Log;

public class GameModeReceiver extends BroadcastReceiver {
    private static final String TAG = "Attic_GameModeReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() : " + intent.getAction());
    }
}
