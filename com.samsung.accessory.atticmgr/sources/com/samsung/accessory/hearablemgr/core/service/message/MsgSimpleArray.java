package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgSimpleArray extends Msg {
    public byte[] data;

    public MsgSimpleArray(byte b, byte[] bArr) {
        super(b);
        this.data = bArr;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return this.data;
    }
}
