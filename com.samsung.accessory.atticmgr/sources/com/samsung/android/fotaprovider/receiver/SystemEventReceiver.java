package com.samsung.android.fotaprovider.receiver;

import com.samsung.android.fotaagent.SafeBroadcastReceiver;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.FotaProviderFileEncryptionUtil;

public class SystemEventReceiver extends SafeBroadcastReceiver {
    /* access modifiers changed from: protected */
    @Override // com.samsung.android.fotaagent.SafeBroadcastReceiver
    public void handle() {
        Log.D("Receive broadcast message:" + this.action);
        String valueOf = String.valueOf(this.action);
        if (((valueOf.hashCode() == 833559602 && valueOf.equals("android.intent.action.USER_UNLOCKED")) ? (char) 0 : 65535) != 0) {
            Log.W("not acceptable intent, do nothing");
        } else if (FotaProviderFileEncryptionUtil.isUserUnlocked(this.context)) {
            Log.I("FBE unlocked by user, so initialize app");
            FotaProviderInitializer.initializeApplication();
        } else {
            Log.W("received user unlocked, but device is still locked");
        }
    }
}
