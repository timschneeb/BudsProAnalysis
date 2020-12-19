package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.content.Intent;
import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.service.CoreService;

public class RedirectMsgSetNoiseReduction extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgSetNoiseReduction";
    boolean noise_reduction_config = false;

    public RedirectMsgSetNoiseReduction(byte[] bArr) {
        super(bArr);
        boolean z = false;
        this.noise_reduction_config = getRecvDataByteBuffer().get() == 1 ? true : z;
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : noise_reduction_config : " + this.noise_reduction_config);
        Application.getCoreService().getEarBudsInfo().noiseReduction = this.noise_reduction_config;
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_NOISE_REDUCTION_UPDATED));
    }
}
