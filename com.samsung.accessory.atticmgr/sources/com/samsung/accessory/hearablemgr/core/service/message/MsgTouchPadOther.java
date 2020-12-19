package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgTouchPadOther extends Msg {
    public int touchpadOtherOptionValue;

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return null;
    }

    public MsgTouchPadOther(byte[] bArr) {
        super(bArr);
        this.touchpadOtherOptionValue = bArr[getDataStartIndex()];
    }

    public MsgTouchPadOther(int i, boolean z) {
        super((byte) MsgID.TOUCHPAD_OTHER_OPTION);
    }
}
