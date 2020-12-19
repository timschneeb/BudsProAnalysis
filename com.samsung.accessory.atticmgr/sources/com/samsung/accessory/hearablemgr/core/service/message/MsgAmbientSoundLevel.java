package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgAmbientSoundLevel extends Msg {
    public byte ambientSoundLevel;

    public MsgAmbientSoundLevel(byte b) {
        super((byte) MsgID.AMBIENT_SOUND_LEVEL);
        this.ambientSoundLevel = b;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.ambientSoundLevel};
    }
}
