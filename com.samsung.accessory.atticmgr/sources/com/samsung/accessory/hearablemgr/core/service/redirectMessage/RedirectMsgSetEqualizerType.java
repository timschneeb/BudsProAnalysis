package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;

public class RedirectMsgSetEqualizerType extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgSetEqualizerType";
    byte setEqualizerType = getRecvDataByteBuffer().get();

    public RedirectMsgSetEqualizerType(byte[] bArr) {
        super(bArr);
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : setEqualizerType : " + ((int) this.setEqualizerType));
        Application.getCoreService().getEarBudsInfo().equalizerType = this.setEqualizerType;
    }
}
