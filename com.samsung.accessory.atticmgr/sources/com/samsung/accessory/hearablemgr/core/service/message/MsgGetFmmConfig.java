package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgGetFmmConfig extends Msg {
    private static final String TAG = "Attic_MsgGetFmmConfig";
    public byte[] getFmmConfig;

    public MsgGetFmmConfig(byte[] bArr) {
        super(bArr);
        this.getFmmConfig = getRecvData();
    }

    public MsgGetFmmConfig() {
        super((byte) MsgID.GET_FMM_CONFIG);
    }
}
