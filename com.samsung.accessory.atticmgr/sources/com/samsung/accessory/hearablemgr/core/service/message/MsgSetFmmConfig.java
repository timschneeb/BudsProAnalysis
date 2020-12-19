package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.BufferBuilder;
import seccompat.android.util.Log;

public class MsgSetFmmConfig extends Msg {
    private static final String TAG = "Attic_MsgSetFmmConfig";
    public BufferBuilder fmmConfigData;
    public boolean result;

    MsgSetFmmConfig(byte[] bArr) {
        super(bArr);
        this.result = getRecvDataByteBuffer().get() == 0;
        Log.d(TAG, "MSG_ID_SET_FMM_CONFIG: result:  " + this.result);
    }

    public MsgSetFmmConfig(BufferBuilder bufferBuilder) {
        super(MsgID.SET_FMM_CONFIG, false);
        this.fmmConfigData = bufferBuilder;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return this.fmmConfigData.array();
    }
}
