package com.samsung.android.fotaagent.internalevent.event;

import com.samsung.android.fotaagent.ProcessFOTA;
import com.samsung.android.fotaagent.ProcessRegister;
import com.samsung.android.fotaprovider.log.Log;

public final class SetupWizardCompletedEvent extends BaseEvent {
    private static SetupWizardCompletedEvent instance = new SetupWizardCompletedEvent();

    private SetupWizardCompletedEvent() {
    }

    public static SetupWizardCompletedEvent getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForNotRegisteredDevice() {
        Log.I("register device after setup wizard completed");
        new ProcessRegister().registerDeviceOnBackgroundWithDelay();
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForRegisteredDevice() {
        Log.I("process FOTA that Gear Plugin is updated or SamsungPay requests it");
        new ProcessFOTA().updateOnBackgroundByRequest();
    }
}
