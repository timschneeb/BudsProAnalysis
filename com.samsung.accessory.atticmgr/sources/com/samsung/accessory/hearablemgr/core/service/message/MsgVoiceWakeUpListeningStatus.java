package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgVoiceWakeUpListeningStatus extends Msg {
    public boolean voiceWakeUpListeningStatus;

    public MsgVoiceWakeUpListeningStatus(byte[] bArr) {
        super(bArr);
        this.voiceWakeUpListeningStatus = bArr[getDataStartIndex()] != 1 ? false : true;
    }
}
