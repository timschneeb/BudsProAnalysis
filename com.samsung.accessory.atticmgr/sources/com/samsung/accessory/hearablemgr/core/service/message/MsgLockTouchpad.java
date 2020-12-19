package com.samsung.accessory.hearablemgr.core.service.message;

import android.content.Intent;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;

public class MsgLockTouchpad extends Msg {
    private boolean lockTouchpad;

    public MsgLockTouchpad(boolean z) {
        super((byte) -112);
        this.lockTouchpad = z;
        SamsungAnalyticsUtil.setStatusString(SA.Status.LOCK_TOUCHPAD, z ? "1" : "0");
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED));
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.lockTouchpad};
    }
}
