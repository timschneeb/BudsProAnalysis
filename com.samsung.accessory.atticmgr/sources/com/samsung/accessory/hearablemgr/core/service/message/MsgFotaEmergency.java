package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgFotaEmergency extends Msg {
    public byte byteData;
    public int mReason;

    public MsgFotaEmergency() {
        super((byte) MsgID.FOTA_EMERGENCY);
    }

    public MsgFotaEmergency(byte[] bArr) {
        super(bArr);
        this.mReason = getRecvDataByteBuffer().get();
    }

    public MsgFotaEmergency(byte b, boolean z, byte b2) {
        super(b, z);
        this.byteData = b2;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.byteData};
    }
}
