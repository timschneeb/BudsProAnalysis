package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgTouchUpdated extends Msg {
    public byte status;

    MsgTouchUpdated(byte[] bArr) {
        super(bArr);
        this.status = bArr[getDataStartIndex()];
    }
}
