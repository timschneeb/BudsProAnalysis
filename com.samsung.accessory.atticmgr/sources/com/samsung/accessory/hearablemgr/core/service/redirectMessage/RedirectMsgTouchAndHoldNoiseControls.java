package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import java.nio.ByteBuffer;

public class RedirectMsgTouchAndHoldNoiseControls extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgTouchAndHoldNoiseControls";
    private boolean activeNoiseCanceling;
    private boolean ambientSound;
    private boolean off;

    public RedirectMsgTouchAndHoldNoiseControls(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        boolean z = false;
        this.activeNoiseCanceling = recvDataByteBuffer.get() == 1;
        this.ambientSound = recvDataByteBuffer.get() == 1;
        this.off = recvDataByteBuffer.get() == 1 ? true : z;
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : activeNoiseCanceling : " + this.activeNoiseCanceling + "_ambientSound : " + this.ambientSound + "_off : " + this.off);
        Application.getCoreService().getEarBudsInfo().noiseControlsAnc = this.activeNoiseCanceling;
        Application.getCoreService().getEarBudsInfo().noiseControlsAmbient = this.ambientSound;
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        boolean z = this.off;
        earBudsInfo.noiseControlsOff = z;
        SamsungAnalyticsUtil.setStatusString(SA.Status.SET_NOISE_CONTROLS, SamsungAnalyticsUtil.makeSetNoiseControlsDetail(this.activeNoiseCanceling, this.ambientSound, z));
    }
}
