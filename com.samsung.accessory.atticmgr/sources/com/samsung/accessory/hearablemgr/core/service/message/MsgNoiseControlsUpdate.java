package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgNoiseControlsUpdate extends Msg {
    public int noiseControlsUpdate;

    public MsgNoiseControlsUpdate(byte[] bArr) {
        super(bArr);
        this.noiseControlsUpdate = bArr[getDataStartIndex()];
    }
}
