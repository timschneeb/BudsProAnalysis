package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgMuteEarbudStatusUpdated extends Msg {
    public boolean leftStatus;
    public boolean rightStatus;

    public MsgMuteEarbudStatusUpdated(byte[] bArr) {
        super(bArr);
        boolean z = false;
        this.leftStatus = bArr[getDataStartIndex()] == 1;
        this.rightStatus = bArr[getDataStartIndex() + 1] == 1 ? true : z;
    }
}
