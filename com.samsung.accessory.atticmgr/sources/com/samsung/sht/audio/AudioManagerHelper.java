package com.samsung.sht.audio;

import android.media.AudioManager;

public class AudioManagerHelper {
    private AudioManager mAudioManager = null;

    public AudioManagerHelper(AudioManager audioManager) {
        this.mAudioManager = audioManager;
    }

    public void setSpatialAudioOn() {
        setParameter("g_effect_headtracking_fx;enable=true");
    }

    public void setSpatialAudioOff() {
        setParameter("g_effect_headtracking_fx;enable=false");
    }

    public void setSpatialAudioSettingOn() {
        setParameter("g_effect_headtracking_fx;feature=true");
    }

    public void setSpatialAudioSettingOff() {
        setParameter("g_effect_headtracking_fx;feature=false");
    }

    public void updateAttitude(float f, float f2, float f3) {
        setParameter("g_effect_headtracking_fx;position=" + ((int) ((short) ((int) (f * 128.0f)))) + "," + ((int) ((short) ((int) (f2 * 128.0f)))) + "," + ((int) ((short) ((int) (f3 * 128.0f)))));
    }

    private void setParameter(String str) {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            audioManager.setParameters(str);
        }
    }
}
