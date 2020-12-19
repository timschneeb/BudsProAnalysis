package com.samsung.android.fotaagent.internalevent.event;

import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.android.fotaagent.ProcessExtra;
import com.samsung.android.fotaagent.ProcessFOTA;
import com.samsung.android.fotaagent.register.InitDeviceWork;
import com.samsung.android.fotaprovider.log.Log;

public final class DeviceDisconnectedEvent extends BaseEvent {
    private static DeviceDisconnectedEvent instance = new DeviceDisconnectedEvent();

    private DeviceDisconnectedEvent() {
    }

    public static DeviceDisconnectedEvent getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForNotRegisteredDevice() {
        Log.I("BT is disconnected but device is not registered, so cancel initializing device for registration");
        InitDeviceWork.getInstance().cancel();
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForRegisteredDevice() {
        Log.I("BT is disconnected and device is registered, so stop everything");
        AccessoryController.getInstance().getConnectionController().releaseConnection();
        ProcessExtra processExtra = new ProcessExtra();
        processExtra.stopInstallConfirmPostponeAlarm();
        processExtra.stopPollingAlarm();
        processExtra.removeUI();
        new ProcessFOTA().stopHoldingUpdateResult();
    }
}
