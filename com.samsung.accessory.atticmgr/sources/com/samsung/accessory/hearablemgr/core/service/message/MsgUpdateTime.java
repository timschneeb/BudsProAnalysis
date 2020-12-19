package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.BufferBuilder;

public class MsgUpdateTime extends Msg {
    private static final String TAG = "Attic_MsgUpdateTime";
    public long currentTime;
    public int timeZone;

    public MsgUpdateTime(long j, int i) {
        super((byte) MsgID.UPDATE_TIME);
        this.currentTime = j;
        this.timeZone = i;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        BufferBuilder bufferBuilder = new BufferBuilder();
        bufferBuilder.putLong(this.currentTime);
        bufferBuilder.putInt(this.timeZone);
        return bufferBuilder.array();
    }
}
