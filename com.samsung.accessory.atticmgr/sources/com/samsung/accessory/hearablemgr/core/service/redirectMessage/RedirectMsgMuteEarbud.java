package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.content.Intent;
import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import java.nio.ByteBuffer;

public class RedirectMsgMuteEarbud extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgSetTouchpadOption";
    byte leftMute;
    byte rightMute;

    public RedirectMsgMuteEarbud(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.leftMute = recvDataByteBuffer.get();
        this.rightMute = recvDataByteBuffer.get();
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : leftMute : " + ((int) this.leftMute) + "rightMute :" + ((int) this.rightMute));
        boolean z = false;
        Application.getCoreService().getEarBudsInfo().leftMuteStatus = this.leftMute == 1;
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        if (this.rightMute == 1) {
            z = true;
        }
        earBudsInfo.rightMuteStatus = z;
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED));
    }
}
