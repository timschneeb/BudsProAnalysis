package com.samsung.accessory.fotaprovider;

import android.content.Context;
import android.content.Intent;
import com.samsung.android.fotaagent.internalevent.FotaInternalEventIntent;
import com.samsung.android.fotaprovider.log.Log;

public final class FotaProviderEventHandler {
    public static void setupWizardCompleted(Context context) {
        sendIntent(context, new Intent(FotaInternalEventIntent.INTERNAL_INTENT_SETUPWIZARD_COMPLETE));
    }

    public static void softwareUpdate(Context context) {
        sendIntent(context, new Intent(FotaInternalEventIntent.INTERNAL_INTENT_SOFTWARE_UPDATE));
    }

    public static void lastUpdate(Context context) {
        sendIntent(context, new Intent(FotaInternalEventIntent.INTERNAL_INTENT_LAST_UPDATE));
    }

    public static void deviceConnectionChanged(Context context, boolean z) {
        Intent intent = new Intent(FotaInternalEventIntent.INTERNAL_INTENT_DEVICE_CONNECTION);
        intent.putExtra("connected", z);
        sendIntent(context, intent);
    }

    private static void sendIntent(Context context, Intent intent) {
        Log.D(context.getClass().getSimpleName() + " sends " + intent.getAction());
        intent.setPackage(context.getApplicationContext().getPackageName());
        context.sendBroadcast(intent);
    }
}
