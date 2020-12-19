package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.content.Intent;
import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import java.nio.ByteBuffer;

public class RedirectMsgSetTouchpadOption extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgSetTouchpadOption";
    byte leftOption;
    byte rightOption;

    public RedirectMsgSetTouchpadOption(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.leftOption = recvDataByteBuffer.get();
        this.rightOption = recvDataByteBuffer.get();
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : leftOption : " + ((int) this.leftOption) + "_rightOption :" + ((int) this.rightOption));
        Application.getCoreService().getEarBudsInfo().touchpadOptionLeft = this.leftOption;
        Application.getCoreService().getEarBudsInfo().touchpadOptionRight = this.rightOption;
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED));
    }
}
