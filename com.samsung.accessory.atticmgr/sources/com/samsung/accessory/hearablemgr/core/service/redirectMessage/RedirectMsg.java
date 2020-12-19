package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class RedirectMsg extends Msg {
    private static final int REPLY_MESSAGE_LENGTH = 1;

    public RedirectMsg(byte[] bArr) {
        super(bArr);
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getRecvData() {
        if (this.bufRecvFrame == null) {
            return null;
        }
        return Arrays.copyOfRange(this.bufRecvFrame, getDataStartIndex() + 1, (this.bufRecvFrame.length - 2) - 1);
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public ByteBuffer getRecvDataByteBuffer() {
        if (this.bufRecvFrame == null) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(this.bufRecvFrame, getDataStartIndex() + 1, ((this.bufRecvFrame.length - 2) - 1) - getDataStartIndex());
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap;
    }
}
