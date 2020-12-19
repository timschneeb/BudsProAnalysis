package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgNoiseReductionModeUpdated extends Msg {
    public boolean noiseReduction;

    public MsgNoiseReductionModeUpdated(byte[] bArr) {
        super(bArr);
        this.noiseReduction = bArr[getDataStartIndex()] != 1 ? false : true;
    }
}
