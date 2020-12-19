package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgExtendedStatusUpdated extends Msg {
    public static final int DEVICE_COLOR_BLACK = 2;
    public static final int DEVICE_COLOR_PINK = 4;
    public static final int DEVICE_COLOR_WHITE = 0;
    public static final int DEVICE_COLOR_YELLOW = 3;
    public static final int NOISE_CONTROLS_OPTION_AMBIENT_SOUND = 2;
    public static final int NOISE_CONTROLS_OPTION_NOISE_REDUCTION = 4;
    public static final int NOISE_CONTROLS_OPTION_OFF = 1;
    public static final byte PRIMARY_EARBUD_LEFT = 1;
    public static final byte PRIMARY_EARBUD_RIGHT = 0;
    public static final int REVISION_DEVICE_LOG_SM_REQUEST_SUPPORT = 0;
    public static final int REVISION_SEAMLESS_CONNECTION_SUPPORT = 0;
    private static final String TAG = "Attic_MsgExtendedStatusUpdated";
    public static final int TYPE_KERNEL = 0;
    public static final int TYPE_OPEN = 1;
    public boolean adjustSoundSync;
    public int ambientSoundLevel;
    public boolean autoSwitchAudioOutput;
    public int batteryCase;
    public int batteryLeft;
    public int batteryRight;
    public int colorL;
    public int colorR;
    public boolean coupled;
    public boolean detectConversations;
    public int detectConversationsDuration;
    public int deviceColor;
    public byte earType;
    public byte equalizer;
    public int equalizerType;
    public boolean extraHighAmbient;
    public int fmmRevision;
    public int hearingEnhancements;
    public int noiseControls;
    public boolean noiseControlsAmbient;
    public boolean noiseControlsAnc;
    public boolean noiseControlsOff;
    public boolean noiseReduction;
    public int noiseReductionLevel;
    public boolean outsideDoubleTap;
    public int placementL;
    public int placementR;
    public byte primaryEarbud;
    public byte revision;
    public boolean seamlessConnection;
    public boolean spatialAudio;
    public boolean speakSeamlessly;
    public boolean touchpadConfig;
    public int touchpadOptionLeft;
    public int touchpadOptionRight;
    public boolean voiceWakeUp;
    public int voiceWakeUpLanguage;
    public boolean wearingL;
    public boolean wearingR;

    MsgExtendedStatusUpdated(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.revision = recvDataByteBuffer.get();
        this.earType = recvDataByteBuffer.get();
        this.batteryLeft = recvDataByteBuffer.get();
        this.batteryRight = recvDataByteBuffer.get();
        boolean z = false;
        this.coupled = recvDataByteBuffer.get() == 1;
        this.primaryEarbud = recvDataByteBuffer.get();
        byte b = recvDataByteBuffer.get();
        this.placementL = ByteUtil.valueOfLeft(b);
        this.placementR = ByteUtil.valueOfRight(b);
        this.wearingL = this.placementL == 1;
        this.wearingR = this.placementR == 1;
        this.batteryCase = recvDataByteBuffer.get();
        this.adjustSoundSync = recvDataByteBuffer.get() == 1;
        this.equalizerType = recvDataByteBuffer.get();
        this.touchpadConfig = recvDataByteBuffer.get() == 1;
        byte b2 = recvDataByteBuffer.get();
        this.touchpadOptionLeft = ByteUtil.valueOfLeft(b2);
        this.touchpadOptionRight = ByteUtil.valueOfRight(b2);
        this.noiseControls = recvDataByteBuffer.get();
        this.voiceWakeUp = recvDataByteBuffer.get() == 1;
        short s = recvDataByteBuffer.getShort();
        short s2 = recvDataByteBuffer.getShort();
        if (this.coupled) {
            this.deviceColor = s2 != 0 ? s2 : s;
        } else {
            this.deviceColor = this.primaryEarbud == 0 ? s2 : s;
        }
        this.voiceWakeUpLanguage = recvDataByteBuffer.get();
        this.seamlessConnection = recvDataByteBuffer.get() == 0;
        this.fmmRevision = recvDataByteBuffer.get();
        byte b3 = recvDataByteBuffer.get();
        this.noiseControlsOff = ByteUtil.valueOfBinaryDigit(b3, 0) == 1;
        this.noiseControlsAmbient = ByteUtil.valueOfBinaryDigit(b3, 1) == 2;
        this.noiseControlsAnc = ByteUtil.valueOfBinaryDigit(b3, 2) == 4;
        if (this.revision < 3) {
            this.extraHighAmbient = recvDataByteBuffer.get() == 1;
        } else {
            this.speakSeamlessly = recvDataByteBuffer.get() == 1;
        }
        this.ambientSoundLevel = recvDataByteBuffer.get();
        this.noiseReductionLevel = recvDataByteBuffer.get();
        this.autoSwitchAudioOutput = recvDataByteBuffer.get() == 1;
        this.detectConversations = recvDataByteBuffer.get() == 1;
        this.detectConversationsDuration = recvDataByteBuffer.get();
        if (this.detectConversationsDuration > 2) {
            this.detectConversationsDuration = 1;
        }
        if (this.revision > 1) {
            this.spatialAudio = recvDataByteBuffer.get() == 1 ? true : z;
        }
        if (this.revision >= 5) {
            this.hearingEnhancements = recvDataByteBuffer.get();
        }
        Log.d(TAG, "revision=" + ((int) this.revision) + ", batteryLeft=" + this.batteryLeft + ", batteryRight=" + this.batteryRight + ", batteryCase=" + this.batteryCase + ", adjustSoundSync=" + this.adjustSoundSync);
        StringBuilder sb = new StringBuilder();
        sb.append("noiseControls= ");
        sb.append(this.noiseControls);
        sb.append(", noiseControlsOff=");
        sb.append(this.noiseControlsOff);
        sb.append(", noiseControlsAnc=");
        sb.append(this.noiseControlsAnc);
        sb.append(", noiseControlsAmbient=");
        sb.append(this.noiseControlsAmbient);
        Log.d(TAG, sb.toString());
    }

    public void applyTo(EarBudsInfo earBudsInfo) {
        earBudsInfo.batteryL = this.batteryLeft;
        earBudsInfo.batteryR = this.batteryRight;
        earBudsInfo.batteryCase = this.batteryCase;
        earBudsInfo.coupled = this.coupled;
        earBudsInfo.wearingL = this.wearingL;
        earBudsInfo.wearingR = this.wearingR;
        earBudsInfo.placementL = this.placementL;
        earBudsInfo.placementR = this.placementR;
        earBudsInfo.adjustSoundSync = this.adjustSoundSync;
        earBudsInfo.equalizerType = this.equalizerType;
        earBudsInfo.touchpadLocked = this.touchpadConfig;
        earBudsInfo.touchpadOptionLeft = this.touchpadOptionLeft;
        earBudsInfo.touchpadOptionRight = this.touchpadOptionRight;
        earBudsInfo.colorL = this.colorL;
        earBudsInfo.colorR = this.colorR;
        earBudsInfo.extendedRevision = this.revision;
        earBudsInfo.deviceColor = this.deviceColor;
        earBudsInfo.noiseControls = this.noiseControls;
        boolean z = this.extraHighAmbient;
        earBudsInfo.extraHighAmbient = z;
        earBudsInfo.voiceWakeUp = this.voiceWakeUp;
        earBudsInfo.voiceWakeUpLanguage = this.voiceWakeUpLanguage;
        earBudsInfo.seamlessConnection = this.seamlessConnection;
        earBudsInfo.fmmRevision = this.fmmRevision;
        earBudsInfo.noiseControlsOff = this.noiseControlsOff;
        earBudsInfo.noiseControlsAnc = this.noiseControlsAnc;
        earBudsInfo.noiseControlsAmbient = this.noiseControlsAmbient;
        earBudsInfo.extraHighAmbient = z;
        earBudsInfo.noiseReductionLevel = this.noiseReductionLevel;
        earBudsInfo.ambientSoundLevel = this.ambientSoundLevel;
        earBudsInfo.autoSwitchAudio = this.autoSwitchAudioOutput;
        earBudsInfo.detectConversations = this.detectConversations;
        earBudsInfo.detectConversationsDuration = this.detectConversationsDuration;
        earBudsInfo.spatialAudio = this.spatialAudio;
        earBudsInfo.speakSeamlessly = this.speakSeamlessly;
        earBudsInfo.hearingEnhancements = this.hearingEnhancements;
        earBudsInfo.calcBatteryIntegrated();
    }
}
