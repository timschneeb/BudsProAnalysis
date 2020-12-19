package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgAmbientSoundLevel;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgLockTouchpad;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgMuteEarbud;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgNoiseControls;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgNoiseReductionLevel;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgSetAmbientMode;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgSetEqualizerType;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgSetNoiseReduction;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgSetTouchpadOption;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgSimple;
import com.samsung.accessory.hearablemgr.core.service.redirectMessage.RedirectMsgTouchAndHoldNoiseControls;
import seccompat.android.util.Log;

public class MsgUniversalAcknowledgement extends Msg {
    public static final String TAG = "Attic_MsgUniversalAcknowledgement";
    public int reDirectID = getRecvDataByteBuffer().get();

    public MsgUniversalAcknowledgement(byte[] bArr) {
        super(bArr);
        Log.d(TAG, "replyID : " + String.format("%02X ", Integer.valueOf(this.reDirectID)));
    }

    public void convertMessage(byte[] bArr) {
        int i = this.reDirectID;
        if (i == -128) {
            Log.d(TAG, "SET_AMBIENT_MODE");
            new RedirectMsgSetAmbientMode(bArr).applyTo();
        } else if (i == -112) {
            Log.d(TAG, "LOCK_TOUCHPAD");
            new RedirectMsgLockTouchpad(bArr).applyTo();
        } else if (i == -110) {
            Log.d(TAG, "SET_TOUCHPAD_OPTION");
            new RedirectMsgSetTouchpadOption(bArr).applyTo();
        } else if (i == -81) {
            Log.d(TAG, "SET_SEAMLESS_CONNECTION");
            new RedirectMsgSimple(bArr).applyTo();
        } else if (i != 115) {
            switch (i) {
                case -125:
                    Log.d(TAG, "NOISE_REDUCTION_LEVEL");
                    new RedirectMsgNoiseReductionLevel(bArr).applyTo();
                    return;
                case -124:
                    Log.d(TAG, "AMBIENT_SOUND_LEVEL");
                    new RedirectMsgAmbientSoundLevel(bArr).applyTo();
                    return;
                case -123:
                    Log.d(TAG, "ADJUST_SOUND_SYNC");
                    new RedirectMsgSimple(bArr).applyTo();
                    return;
                case -122:
                    Log.d(TAG, "EQUALIZER");
                    new RedirectMsgSetEqualizerType(bArr).applyTo();
                    return;
                default:
                    switch (i) {
                        case -106:
                            Log.d(TAG, "EXTRA_HIGH_AMBIENT");
                            new RedirectMsgSimple(bArr).applyTo();
                            return;
                        case -105:
                            Log.d(TAG, "SET_VOICE_WAKE_UP");
                            new RedirectMsgSimple(bArr).applyTo();
                            return;
                        case -104:
                            Log.d(TAG, "SET_NOISE_REDUCTION");
                            new RedirectMsgSetNoiseReduction(bArr).applyTo();
                            return;
                        default:
                            switch (i) {
                                case -97:
                                    Log.d(TAG, "PASS_THROUGH");
                                    new RedirectMsgSimple(bArr).applyTo();
                                    return;
                                case -96:
                                    Log.d(TAG, "FIND_MY_EARBUDS_START");
                                    new RedirectMsgSimple(bArr).applyTo();
                                    return;
                                case -95:
                                    Log.d(TAG, "FIND_MY_EARBUDS_STOP");
                                    new RedirectMsgSimple(bArr).applyTo();
                                    return;
                                case -94:
                                    Log.d(TAG, "MUTE_EARBUD");
                                    new RedirectMsgMuteEarbud(bArr).applyTo();
                                    return;
                                default:
                                    switch (i) {
                                        case 120:
                                            Log.d(TAG, "NOISE_CONTROLS");
                                            new RedirectMsgNoiseControls(bArr).applyTo();
                                            return;
                                        case 121:
                                            Log.d(TAG, "SET_TOUCH_AND_HOLD_NOISE_CONTROLS");
                                            new RedirectMsgTouchAndHoldNoiseControls(bArr).applyTo();
                                            return;
                                        case 122:
                                            Log.d(TAG, "SET_DETECT_CONVERSATIONS");
                                            new RedirectMsgSimple(bArr).applyTo();
                                            return;
                                        case 123:
                                            Log.d(TAG, "SET_DETECT_CONVERSATIONS_DURATION");
                                            return;
                                        case 124:
                                            Log.d(TAG, "SET_SPATIAL_AUDIO");
                                            new RedirectMsgSimple(bArr).applyTo();
                                            return;
                                        default:
                                            return;
                                    }
                            }
                    }
            }
        } else {
            Log.d(TAG, "AUTO_SWITCH_AUDIO_OUPUT");
        }
    }
}
