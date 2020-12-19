package com.samsung.accessory.hearablemgr.core.service.message;

import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgLogCoredumpDataSize extends Msg {
    private static final String TAG = "Attic_MsgLogCoreDataSize";
    public int partialDataCnt;
    public int partialDataMaxSize;
    public int totalDataSize;

    MsgLogCoredumpDataSize(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.totalDataSize = recvDataByteBuffer.getInt();
        this.partialDataMaxSize = recvDataByteBuffer.getShort();
        this.partialDataCnt = (int) Math.ceil(((double) this.totalDataSize) / ((double) this.partialDataMaxSize));
        Log.d(TAG, "MSG_ID_LOG_COREDUMP_DATA_SIZE.: partialDataMaxSize:  " + this.partialDataMaxSize + ", totalDataSize : " + this.totalDataSize + ", partialDataCnt: " + this.partialDataCnt);
    }

    public MsgLogCoredumpDataSize() {
        super((byte) MsgID.LOG_COREDUMP_DATA_SIZE);
    }
}
