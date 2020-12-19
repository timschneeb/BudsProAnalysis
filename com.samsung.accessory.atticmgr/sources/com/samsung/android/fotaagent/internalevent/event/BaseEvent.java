package com.samsung.android.fotaagent.internalevent.event;

import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;

/* access modifiers changed from: package-private */
public abstract class BaseEvent {
    /* access modifiers changed from: package-private */
    public abstract void executeForNotRegisteredDevice();

    /* access modifiers changed from: package-private */
    public abstract void executeForRegisteredDevice();

    BaseEvent() {
    }

    public final void handle() {
        Log.I(getClass().getName());
        try {
            if (!FotaProviderState.isDeviceRegisteredDB()) {
                Log.I("Device is not registered...");
                executeForNotRegisteredDevice();
                return;
            }
            Log.I("Device is registered...");
            executeForRegisteredDevice();
        } catch (IllegalArgumentException | IllegalStateException e) {
            Log.E(getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
