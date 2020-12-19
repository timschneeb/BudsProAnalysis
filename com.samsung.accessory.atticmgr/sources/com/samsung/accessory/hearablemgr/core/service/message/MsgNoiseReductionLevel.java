package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgNoiseReductionLevel extends Msg {
    public byte noiseReductionLevel;

    public MsgNoiseReductionLevel(byte b) {
        super((byte) MsgID.NOISE_REDUCTION_LEVEL);
        this.noiseReductionLevel = b;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.noiseReductionLevel};
    }
}
