package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgLogRoleSwitch extends Msg {
    public boolean resCode;

    MsgLogRoleSwitch(byte[] bArr) {
        super(bArr);
        this.resCode = getRecvDataByteBuffer().get() == 0;
    }

    public MsgLogRoleSwitch() {
        super((byte) MsgID.LOG_TRACE_ROLE_SWITCH);
    }
}
