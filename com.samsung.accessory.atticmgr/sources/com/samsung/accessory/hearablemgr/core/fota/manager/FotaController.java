package com.samsung.accessory.hearablemgr.core.fota.manager;

import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

public class FotaController extends AccessoryController {
    private static final String TAG = (Application.TAG_ + FotaController.class.getSimpleName());

    public FotaController() {
        Log.d(TAG, "FotaController()");
        this.connectionController = new FotaConnectionController();
        this.requestController = new FotaRequestController();
        this.accessoryUtil = new FotaServerUtil();
    }
}
