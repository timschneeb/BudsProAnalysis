package com.samsung.android.fotaagent.internalevent;

import com.samsung.android.fotaagent.SafeBroadcastReceiver;
import com.samsung.android.fotaagent.internalevent.event.DeviceConnectedEvent;
import com.samsung.android.fotaagent.internalevent.event.DeviceDisconnectedEvent;
import com.samsung.android.fotaagent.internalevent.event.LastUpdateEvent;
import com.samsung.android.fotaagent.internalevent.event.SetupWizardCompletedEvent;
import com.samsung.android.fotaagent.internalevent.event.SoftwareUpdateEvent;
import com.samsung.android.fotaprovider.log.Log;

public class InternalEventReceiver extends SafeBroadcastReceiver {
    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* access modifiers changed from: protected */
    @Override // com.samsung.android.fotaagent.SafeBroadcastReceiver
    public void handle() {
        char c;
        Log.D("Receive broadcast message:" + this.action);
        String valueOf = String.valueOf(this.action);
        switch (valueOf.hashCode()) {
            case -1418934781:
                if (valueOf.equals(FotaInternalEventIntent.INTERNAL_INTENT_SETUPWIZARD_COMPLETE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -642869746:
                if (valueOf.equals(FotaInternalEventIntent.INTERNAL_INTENT_SOFTWARE_UPDATE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 273621183:
                if (valueOf.equals(FotaInternalEventIntent.INTERNAL_INTENT_LAST_UPDATE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 672357588:
                if (valueOf.equals(FotaInternalEventIntent.INTERNAL_INTENT_DEVICE_CONNECTION)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            SetupWizardCompletedEvent.getInstance().handle();
        } else if (c == 1) {
            SoftwareUpdateEvent.getInstance().handle();
        } else if (c == 2) {
            LastUpdateEvent.getInstance().handle();
        } else if (c != 3) {
            Log.W("wrong intent");
        } else if (this.intent.getBooleanExtra("connected", false)) {
            DeviceConnectedEvent.getInstance().handle();
        } else {
            DeviceDisconnectedEvent.getInstance().handle();
        }
    }
}
