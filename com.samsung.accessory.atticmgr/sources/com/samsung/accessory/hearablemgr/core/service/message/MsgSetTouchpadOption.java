package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;

public class MsgSetTouchpadOption extends Msg {
    public static final byte OPTION_AMBIENT_SOUND = 2;
    public static final byte OPTION_BIXBY_WAKE_UP = 1;
    public static final byte OPTION_VOLUME_UP_DOWN = 3;
    public byte leftOption;
    public byte rightOption;

    public MsgSetTouchpadOption(byte b, byte b2) {
        super((byte) MsgID.SET_TOUCHPAD_OPTION);
        this.leftOption = b;
        this.rightOption = b2;
        SamsungAnalyticsUtil.setStatusString(SA.Status.TOUCH_AND_HOLD_LEFT, SamsungAnalyticsUtil.touchPadOptionToDetail(this.leftOption));
        SamsungAnalyticsUtil.setStatusString(SA.Status.TOUCH_AND_HOLD_RIGHT, SamsungAnalyticsUtil.touchPadOptionToDetail(this.rightOption));
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.leftOption, this.rightOption};
    }
}
