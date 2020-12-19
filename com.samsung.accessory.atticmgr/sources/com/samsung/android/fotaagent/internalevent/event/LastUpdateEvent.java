package com.samsung.android.fotaagent.internalevent.event;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.ui.lastupdate.XUILastUpdateActivity;
import com.samsung.android.fotaprovider.log.Log;

public final class LastUpdateEvent extends BaseEvent {
    private static LastUpdateEvent instance = new LastUpdateEvent();

    private LastUpdateEvent() {
    }

    public static LastUpdateEvent getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForNotRegisteredDevice() {
        Log.W("wrong behavior");
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.internalevent.event.BaseEvent
    public void executeForRegisteredDevice() {
        Log.I("show last update info");
        XDMDmUtils.getInstance().callActivity(XUILastUpdateActivity.class);
    }
}
