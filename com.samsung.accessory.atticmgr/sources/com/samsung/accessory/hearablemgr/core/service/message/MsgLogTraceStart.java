package com.samsung.accessory.hearablemgr.core.service.message;

import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgLogTraceStart extends Msg {
    private static final String TAG = "Attic_MsgLogTraceStart";
    public boolean coupled;
    public int dataSize;
    public int deviceType;
    public int partialDataMaxSize;
    public int traceCount;
    public int traceReadOffset;
    public byte value;

    MsgLogTraceStart(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.dataSize = recvDataByteBuffer.getInt();
        this.partialDataMaxSize = recvDataByteBuffer.getShort();
        this.deviceType = recvDataByteBuffer.get();
        this.coupled = recvDataByteBuffer.get() != 1 ? false : true;
        this.traceCount = (int) Math.ceil(((double) this.dataSize) / ((double) this.partialDataMaxSize));
        Log.d(TAG, "MSG_ID_LOG_TRACE_START: dataSize:  " + this.dataSize + ", partialDataMaxSize : " + this.partialDataMaxSize + ", traceCount :" + this.traceCount);
    }

    public MsgLogTraceStart(byte b) {
        super(MsgID.LOG_TRACE_START, false);
        this.value = b;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.value};
    }
}
