package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgSetTouchAndHoldNoiseControls extends Msg {
    private boolean activeNoiseCanceling;
    private boolean ambientSound;
    private boolean off;

    public MsgSetTouchAndHoldNoiseControls(boolean z, boolean z2, boolean z3) {
        super((byte) MsgID.SET_TOUCH_AND_HOLD_NOISE_CONTROLS);
        this.activeNoiseCanceling = z;
        this.ambientSound = z2;
        this.off = z3;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.activeNoiseCanceling, this.ambientSound, this.off};
    }
}
