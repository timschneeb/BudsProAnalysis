package com.samsung.android.fotaagent.internalevent.event;

import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.controller.ConnectionController;
import com.samsung.android.fotaagent.ProcessExtra;
import com.samsung.android.fotaagent.ProcessFOTA;
import com.samsung.android.fotaagent.ProcessRegister;
import com.samsung.android.fotaprovider.log.Log;

public final class DeviceConnectedEvent extends BaseEvent {
    private static DeviceConnectedEvent instance = new DeviceConnectedEvent();

    private DeviceConnectedEvent() {
    }

    public static DeviceConnectedEvent getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForNotRegisteredDevice() {
        Log.I("BT is connected but device is not registered, so register first");
        AccessoryController.getInstance().getConnectionController().makeConnection(new ConnectionController.ConnectionResultCallback() {
            /* class com.samsung.android.fotaagent.internalevent.event.DeviceConnectedEvent.AnonymousClass1 */

            @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController.ConnectionResultCallback
            public void onSuccess() {
                Log.I("Device is not registered, so register first");
                new ProcessRegister().registerDeviceOnBackground();
                new ProcessExtra().notifyConnectionToAccessorydm();
            }

            @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController.ConnectionResultCallback
            public void onFailure() {
                Log.W("Device is not ready yet");
            }
        });
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForRegisteredDevice() {
        if (AccessoryController.getInstance().getConnectionController().isConnected()) {
            Log.I("BT is already connected, so do nothing");
            return;
        }
        Log.I("BT is connected and device is registered, so start everything");
        AccessoryController.getInstance().getConnectionController().makeConnection(new ConnectionController.ConnectionResultCallback() {
            /* class com.samsung.android.fotaagent.internalevent.event.DeviceConnectedEvent.AnonymousClass2 */

            @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController.ConnectionResultCallback
            public void onSuccess() {
                Log.I("Device is registered, so start everything");
                new ProcessExtra().startPollingAlarm();
                new ProcessRegister().registerPushIfNeeded();
                new ProcessFOTA().updateByCurrentStatus();
                new ProcessExtra().notifyConnectionToAccessorydm();
            }

            @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController.ConnectionResultCallback
            public void onFailure() {
                Log.W("Device is not ready yet");
            }
        });
    }
}
