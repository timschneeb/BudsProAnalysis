package com.samsung.android.fotaprovider.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.interfaces.XCommonInterface;
import com.samsung.android.fotaprovider.deviceinfo.ProviderInfo;

public final class UiUtil {
    public static void setOrientationFor(Activity activity) {
        if (new ProviderInfo().isTablet()) {
            activity.setRequestedOrientation(2);
        } else {
            activity.setRequestedOrientation(1);
        }
    }

    public static void showWiFiSetting() {
        Intent intent = new Intent(XCommonInterface.XCOMMON_INTENT_WIFI_SETTING);
        intent.setFlags(335544320);
        XDMDmUtils.getContext().startActivity(intent);
    }

    public static void showStorageSetting() {
        try {
            Intent intent = new Intent(XCommonInterface.XCOMMON_INTENT_STORAGE_SMART_MANAGER);
            intent.setFlags(268468224);
            XDMDmUtils.getContext().startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Intent intent2 = new Intent(XCommonInterface.XCOMMON_INTENT_STORAGE_SETTING);
            intent2.setFlags(268468224);
            XDMDmUtils.getContext().startActivity(intent2);
        }
    }
}
