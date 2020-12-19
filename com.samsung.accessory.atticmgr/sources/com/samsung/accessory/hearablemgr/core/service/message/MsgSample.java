package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.BufferBuilder;
import java.nio.ByteBuffer;

public class MsgSample extends Msg {
    public boolean fieldBoolean;
    public int fieldInteger;

    public MsgSample(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.fieldInteger = recvDataByteBuffer.getInt();
        this.fieldBoolean = recvDataByteBuffer.get() != 1 ? false : true;
    }

    public MsgSample(int i, boolean z) {
        super((byte) -1);
        this.fieldInteger = i;
        this.fieldBoolean = z;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        BufferBuilder bufferBuilder = new BufferBuilder();
        bufferBuilder.putInt(this.fieldInteger);
        bufferBuilder.put(this.fieldBoolean ? (byte) 1 : 0);
        return bufferBuilder.array();
    }
}
