package com.samsung.android.fotaagent;

import com.samsung.android.fotaagent.polling.PollingIntent;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;

public class PollingReceiver extends SafeBroadcastReceiver {
    /* access modifiers changed from: protected */
    @Override // com.samsung.android.fotaagent.SafeBroadcastReceiver
    public void handle() {
        Log.D("Receive broadcast message:" + this.action);
        if (!FotaProviderState.isDeviceRegisteredDB()) {
            Log.W("device is not registered, do nothing");
        } else if (PollingIntent.INTENT_POLLING_TIME.equals(this.action)) {
            new ProcessFOTA().updateOnBackgroundByPollingAlarm();
        }
    }
}
