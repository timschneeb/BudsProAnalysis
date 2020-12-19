package com.samsung.android.fotaagent.internalevent.event;

import com.samsung.android.fotaagent.ProcessFOTA;
import com.samsung.android.fotaagent.ProcessRegister;
import com.samsung.android.fotaprovider.log.Log;

public final class SoftwareUpdateEvent extends BaseEvent {
    private static SoftwareUpdateEvent instance = new SoftwareUpdateEvent();

    private SoftwareUpdateEvent() {
    }

    public static SoftwareUpdateEvent getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForNotRegisteredDevice() {
        Log.I("register device by menu");
        new ProcessRegister().registerDeviceOnForeground();
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForRegisteredDevice() {
        Log.I("process FOTA by menu");
        new ProcessFOTA().updateOnForeground();
    }
}
