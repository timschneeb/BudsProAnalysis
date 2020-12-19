package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgReset extends Msg {
    public boolean result;

    public MsgReset(byte[] bArr) {
        super(bArr);
        this.result = bArr[getDataStartIndex()] == 0;
    }

    public MsgReset() {
        super((byte) MsgID.RESET);
    }
}
