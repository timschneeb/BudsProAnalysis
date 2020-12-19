package com.samsung.accessory.hearablemgr.core.service.message;

import java.nio.ByteBuffer;

public class MsgCheckTheFitOfEarbudsResult extends Msg {
    public int leftLevel;
    public boolean leftResult;
    public int rightLevel;
    public boolean rightResult;

    public MsgCheckTheFitOfEarbudsResult(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.leftLevel = recvDataByteBuffer.get();
        this.rightLevel = recvDataByteBuffer.get();
        boolean z = true;
        this.leftResult = recvDataByteBuffer.get() == 0;
        this.rightResult = recvDataByteBuffer.get() != 0 ? false : z;
    }
}
