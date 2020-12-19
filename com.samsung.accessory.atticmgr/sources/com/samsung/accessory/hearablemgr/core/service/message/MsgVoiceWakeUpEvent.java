package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgVoiceWakeUpEvent extends Msg {
    public boolean status;

    MsgVoiceWakeUpEvent(byte[] bArr) {
        super(bArr);
        this.status = bArr[getDataStartIndex()] != 1 ? false : true;
    }
}
