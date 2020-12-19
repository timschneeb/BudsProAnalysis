package com.samsung.accessory.hearablemgr.core.aom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.module.mainmenu.BixbyVoiceWakeUpActivity;
import seccompat.android.util.Log;

public class AomManagerModel {
    private static final String ACTION_AOM_WAKEUP_COMMAND = "samsung.intent.action.WAKEUP_COMMAND";
    private static final String CLASSNAME_BIXBY_WAKEUP = "com.samsung.android.bixby.wakeup.ExternalWakeupService";
    private static final String EXTRA_AOM_CHANNEL = "samsung.android.voicewakeup.extra.AOM_CHANNEL";
    private static final String EXTRA_AOM_DEVICE = "samsung.android.voicewakeup.extra.AOM_DEVICE";
    private static final String EXTRA_AOM_SEAMLESS = "samsung.android.voicewakeup.extra.AOM_SEAMLESS";
    private static final String PACKAGENAME_BIXBY_WAKEUP = "com.samsung.android.bixby.wakeup";
    private static final String TAG = "Attic_AomManager";

    public static void startBixby(Context context, CoreService coreService) {
        try {
            Intent intent = new Intent(ACTION_AOM_WAKEUP_COMMAND);
            intent.putExtra("android.bluetooth.device.extra.DEVICE", coreService.getConnectedDevice());
            intent.putExtra(EXTRA_AOM_DEVICE, "SM-R190");
            intent.putExtra(EXTRA_AOM_SEAMLESS, coreService.getEarBudsInfo().speakSeamlessly);
            intent.putExtra(EXTRA_AOM_CHANNEL, "spp");
            intent.setComponent(new ComponentName(PACKAGENAME_BIXBY_WAKEUP, CLASSNAME_BIXBY_WAKEUP));
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "bixby is disable!");
        }
    }

    public static Class<?> getLaunchClass() {
        return BixbyVoiceWakeUpActivity.class;
    }
}
