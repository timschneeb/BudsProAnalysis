package com.samsung.accessory.hearablemgr.core.service.message;

import java.nio.ByteBuffer;

public class MsgFotaResult extends Msg {
    public static final int EARBUD_STATUS_SUCCESS = 0;
    private static final String TAG = "Attic_MsgFotaResult";
    public int mErrorCode;
    public int mResult;

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{1};
    }

    public MsgFotaResult(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.mResult = recvDataByteBuffer.get();
        this.mErrorCode = recvDataByteBuffer.get();
    }

    public MsgFotaResult() {
        super(MsgID.FOTA_RESULT, true);
    }
}
