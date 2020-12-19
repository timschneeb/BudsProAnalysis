package com.samsung.accessory.hearablemgr.core.fota.manager;

import com.samsung.accessory.hearablemgr.Application;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import seccompat.android.util.Log;

public class FotaProviderApplication {
    private static final String TAG = (Application.TAG_ + FotaProviderApplication.class.getSimpleName());

    public static void init(Application application) {
        Log.d(TAG, "init()");
        FotaProviderInitializer.initializeFotaProviderWithAccessoryController(application, new FotaController());
    }

    public static void terminate(Application application) {
        Log.d(TAG, "terminate()");
        FotaProviderInitializer.terminateFotaProvider(application);
    }
}
