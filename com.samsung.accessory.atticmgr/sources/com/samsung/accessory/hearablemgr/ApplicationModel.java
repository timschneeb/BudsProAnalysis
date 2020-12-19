package com.samsung.accessory.hearablemgr;

import android.app.Application;
import android.content.Context;
import com.samsung.accessory.hearablemgr.core.aom.AomManager;
import com.samsung.accessory.hearablemgr.core.fota.manager.FotaProviderApplication;

public class ApplicationModel extends Application {
    static final String DEVICE_NAME = "Galaxy Buds Pro";
    static final String DEVICE_NAME_COMPAT = "Galaxy Buds Pro";
    static final String MODEL_NAME = "SM-R190";
    static final String PERMISSION_SIGNATURE = "com.samsung.accessory.atticmgr.permission.SIGNATURE";
    static final String S_MODEL_NAME = "R190";
    static final String TAG_ = "Attic_";

    static AomManager getAomManager(Context context) {
        return new AomManager(context);
    }

    static void initFotaProviderApplication(Application application) {
        FotaProviderApplication.init(application);
    }

    static void terminateFotaProviderApplication(Application application) {
        FotaProviderApplication.terminate(application);
    }
}
