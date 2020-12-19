package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.content.Intent;
import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;

public class RedirectMsgLockTouchpad extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgLockTouchpad";
    boolean touchpad_config = false;

    public RedirectMsgLockTouchpad(byte[] bArr) {
        super(bArr);
        boolean z = false;
        this.touchpad_config = getRecvDataByteBuffer().get() == 1 ? true : z;
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : touchpad_config :" + this.touchpad_config);
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        boolean z = this.touchpad_config;
        earBudsInfo.touchpadLocked = z;
        SamsungAnalyticsUtil.setStatusString(SA.Status.LOCK_TOUCHPAD, z ? "1" : "0");
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED));
    }
}
