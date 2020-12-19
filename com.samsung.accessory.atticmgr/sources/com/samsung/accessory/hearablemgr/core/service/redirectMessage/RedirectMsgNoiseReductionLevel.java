package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;

public class RedirectMsgNoiseReductionLevel extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgNoiseReductionLevel";
    byte noise_reduction_level = getRecvDataByteBuffer().get();

    public RedirectMsgNoiseReductionLevel(byte[] bArr) {
        super(bArr);
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : noise_reduction_level : " + ((int) this.noise_reduction_level));
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        byte b = this.noise_reduction_level;
        earBudsInfo.noiseReductionLevel = b;
        SamsungAnalyticsUtil.setStatusInt(SA.Status.ACTIVE_NOISE_CANCELLING_LEVEL_STATUS, b);
    }
}
