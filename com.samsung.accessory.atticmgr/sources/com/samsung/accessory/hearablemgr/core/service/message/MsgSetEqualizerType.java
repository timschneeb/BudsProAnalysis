package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;

public class MsgSetEqualizerType extends Msg {
    public byte equalizerType;

    public MsgSetEqualizerType(byte b) {
        super((byte) MsgID.EQUALIZER);
        this.equalizerType = b;
        Preferences.putInt(PreferenceKey.EQUALIZER_TYPE, Integer.valueOf(b));
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.equalizerType};
    }
}
