package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgLogTraceComplete extends Msg {
    MsgLogTraceComplete(byte[] bArr) {
        super(bArr);
    }

    public MsgLogTraceComplete() {
        super((byte) MsgID.LOG_TRACE_COMPLETE);
    }
}
