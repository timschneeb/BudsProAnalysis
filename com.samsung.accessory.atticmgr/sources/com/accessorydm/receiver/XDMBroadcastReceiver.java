package com.accessorydm.receiver;

import android.os.Build;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.samsung.android.fotaagent.SafeBroadcastReceiver;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;

public class XDMBroadcastReceiver extends SafeBroadcastReceiver {
    public static final String INTENT_LOCALE_CHANGED = "android.intent.action.LOCALE_CHANGED";

    @Override // com.samsung.android.fotaagent.SafeBroadcastReceiver
    public void handle() {
        Log.I("Receive broadcast message:" + this.action);
        if (!FotaProviderState.isDeviceRegisteredDB()) {
            Log.I("Device not registered");
        } else if (INTENT_LOCALE_CHANGED.equals(this.action)) {
            if (Build.VERSION.SDK_INT >= 26) {
                XUINotificationManager.getInstance().xuiUpdateNotificationChannel();
            }
            XUINotificationManager.getInstance().xuiReNotifyAllNotification();
        }
    }
}
