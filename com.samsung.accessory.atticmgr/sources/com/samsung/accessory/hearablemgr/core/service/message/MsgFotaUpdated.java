package com.samsung.accessory.hearablemgr.core.service.message;

import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgFotaUpdated extends Msg {
    public static final int EARBUD_STATUS_SUCCESS = 0;
    private static final String TAG = "Attic_MsgFotaUpdated";
    public static final int UPDATE_ID_PERCENT = 0;
    public static final int UPDATE_ID_STATE_CHANGED = 1;
    public int mErrorCode;
    public int mPercent;
    public int mState;
    public int mUpdateId;

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{1};
    }

    public MsgFotaUpdated(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.mUpdateId = recvDataByteBuffer.get();
        int i = this.mUpdateId;
        if (i == 0) {
            this.mPercent = recvDataByteBuffer.get();
        } else if (i == 1) {
            this.mState = recvDataByteBuffer.get();
        } else {
            Log.d(TAG, "UpdateId is : " + this.mUpdateId);
        }
        this.mErrorCode = recvDataByteBuffer.get();
    }

    public MsgFotaUpdated() {
        super(MsgID.FOTA_UPDATE, true);
    }
}
