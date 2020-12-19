package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgLogSessionOpen extends Msg {
    public boolean resCode;

    MsgLogSessionOpen(byte[] bArr) {
        super(bArr);
        this.resCode = getRecvDataByteBuffer().get() == 0;
    }

    public MsgLogSessionOpen() {
        super((byte) MsgID.LOG_SESSION_OPEN);
    }
}
