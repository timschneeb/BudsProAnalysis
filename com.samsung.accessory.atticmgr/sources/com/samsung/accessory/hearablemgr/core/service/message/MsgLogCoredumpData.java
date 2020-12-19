package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.BufferBuilder;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgLogCoredumpData extends Msg {
    private static final String TAG = "Attic_MsgLogCoredumpData";
    public int dataOffset;
    public int dataSize;
    public int partialDataOffset;
    public int partialDataSize;
    public byte[] rawData;

    MsgLogCoredumpData(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.partialDataOffset = recvDataByteBuffer.getInt();
        this.partialDataSize = recvDataByteBuffer.getShort();
        this.rawData = new byte[this.partialDataSize];
        recvDataByteBuffer.get(this.rawData);
        Log.d(TAG, "MSG_ID_LOG_COREDUMP_DATA: partialDataOffset:  " + this.partialDataOffset + ", partialDataSize : " + this.partialDataSize);
    }

    public MsgLogCoredumpData(int i, int i2) {
        super(MsgID.LOG_COREDUMP_DATA, false);
        this.dataOffset = i;
        this.dataSize = i2;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        BufferBuilder bufferBuilder = new BufferBuilder();
        bufferBuilder.putInt(this.dataOffset);
        bufferBuilder.putInt(this.dataSize);
        return bufferBuilder.array();
    }
}
