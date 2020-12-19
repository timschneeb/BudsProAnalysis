package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgLogSessionClose extends Msg {
    public boolean resCode;

    MsgLogSessionClose(byte[] bArr) {
        super(bArr);
        this.resCode = getRecvDataByteBuffer().get() == 0;
    }

    public MsgLogSessionClose() {
        super((byte) MsgID.LOG_SESSION_CLOSE);
    }
}
