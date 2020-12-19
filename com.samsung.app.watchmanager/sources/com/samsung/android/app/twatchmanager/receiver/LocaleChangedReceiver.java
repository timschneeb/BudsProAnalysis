package com.samsung.android.app.twatchmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.UpdateNotificationManager;

public class LocaleChangedReceiver extends BroadcastReceiver {
    private static final String TAG = ("tUHM:" + LocaleChangedReceiver.class.getSimpleName());

    public void onReceive(Context context, Intent intent) {
        String str = TAG;
        Log.d(str, "onReceive [" + intent + "]");
        UpdateNotificationManager.getInstance().cancel();
    }
}
