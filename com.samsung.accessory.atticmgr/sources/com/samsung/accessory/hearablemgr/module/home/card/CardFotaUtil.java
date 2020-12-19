package com.samsung.accessory.hearablemgr.module.home.card;

import android.app.Activity;
import com.samsung.accessory.fotaprovider.FotaProviderEventHandler;
import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

public class CardFotaUtil {
    private static final String TAG = "Attic_CardFotaUtil";

    static void actionOk(Activity activity) {
        FotaProviderEventHandler.softwareUpdate(Application.getContext());
        Log.d(TAG, "FotaProviderEventHandler : SOFTWARE_UPDATE");
    }
}
