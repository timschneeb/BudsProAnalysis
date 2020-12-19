package com.samsung.accessory.hearablemgr.core.aom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.module.LaunchActivity;

public class AomReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_DONE, false)) {
            try {
                if (!intent.getExtras().getString("packageName").equals(context.getPackageName())) {
                    return;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (Util.isInstalledPackage(UhmFwUtil.getUhmPackageName()) && UhmFwUtil.getLastLaunchDeviceId() != null) {
                if (Application.getCoreService().isConnected()) {
                    Class<?> launchClass = AomManagerModel.getLaunchClass();
                    if (launchClass != null) {
                        Intent intent2 = new Intent(context, launchClass);
                        intent2.addFlags(335577088);
                        context.startActivity(intent2);
                        return;
                    }
                    return;
                }
                Intent intent3 = new Intent(context, LaunchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(UhmFwUtil.EXTRA_LAUNCH_DATA_LAUNCH_MODE, 1009);
                bundle.putString("deviceid", UhmFwUtil.getLastLaunchDeviceId());
                intent3.putExtras(bundle);
                intent3.addFlags(268435456);
                context.startActivity(intent3);
            }
        }
    }
}
