package com.samsung.accessory.hearablemgr.module.noisecontrols;

import android.content.Context;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.SingleToast;
import com.samsung.accessory.hearablemgr.core.service.message.MsgNoiseControls;
import seccompat.android.util.Log;

public class NoiseControlUtil {
    public static final int NOISE_CONTROL_STATE_AMBIENT_SOUND = 2;
    public static final int NOISE_CONTROL_STATE_NOISE_REDUCTION = 1;
    public static final int NOISE_CONTROL_STATE_OFF = 0;
    private static final String TAG = (Application.TAG_ + NoiseControlUtil.class.getSimpleName());

    public static int indexToState(int i) {
        if (i != 0) {
            return i != 2 ? 0 : 2;
        }
        return 1;
    }

    public static int stateToIndex(int i) {
        if (i != 1) {
            return i != 2 ? 1 : 2;
        }
        return 0;
    }

    public static boolean setNoiseControl(int i) {
        if (i == 0) {
            return setOff();
        }
        if (i == 1) {
            return setNoiseReduction();
        }
        if (i != 2) {
            return false;
        }
        return setAmbientSound();
    }

    public static int setNextNoiseControl(Context context) {
        int i = (Application.getCoreService().getEarBudsInfo().noiseControls + 1) % 3;
        if (i != 0) {
            if (i != 1) {
                if (i != 2 || setAmbientSound()) {
                    return i;
                }
                SingleToast.show(context, context.getResources().getString(R.string.put_in_at_least_one_earbud), 0);
            } else if (setNoiseReduction()) {
                return i;
            } else {
                SingleToast.show(context, context.getResources().getString(R.string.settings_anc_toast_both_wearing), 0);
                if (setAmbientSound()) {
                    return 2;
                }
            }
            return 0;
        }
        setOff();
        return i;
    }

    private static boolean setOff() {
        sendSppNoiseControl(0);
        return true;
    }

    private static boolean setNoiseReduction() {
        if (isBothWearing()) {
            sendSppNoiseControl(1);
            return true;
        }
        Log.d(TAG, "setNoiseReduction() fail : not both wearing");
        return false;
    }

    private static boolean setAmbientSound() {
        if (isWearing()) {
            sendSppNoiseControl(2);
            return true;
        }
        Log.d(TAG, "setAmbientSound() fail : not wearing");
        return false;
    }

    public static boolean isBothWearing() {
        return Application.getCoreService().getEarBudsInfo().wearingL && Application.getCoreService().getEarBudsInfo().wearingR;
    }

    public static boolean isWearing() {
        return Application.getCoreService().getEarBudsInfo().wearingL || Application.getCoreService().getEarBudsInfo().wearingR;
    }

    public static void sendSppNoiseControl(int i) {
        Application.getCoreService().getEarBudsInfo().noiseControls = i;
        Application.getCoreService().sendSppMessage(new MsgNoiseControls((byte) i));
    }

    public static String stateToText(int i) {
        if (i == 0) {
            return Application.getContext().getString(R.string.noise_control_off);
        }
        if (i != 1) {
            return i != 2 ? "" : Application.getContext().getString(R.string.settings_ambient_sound);
        }
        return Application.getContext().getString(R.string.settings_noise_reduction_title);
    }

    public static String levelToText(int i, int i2) {
        if (i == 1) {
            if (i2 == 0) {
                return Application.getContext().getString(R.string.anc_level_low);
            }
            if (i2 != 1) {
                return "";
            }
            return Application.getContext().getString(R.string.anc_level_high);
        } else if (i != 2) {
            return "";
        } else {
            if (i2 == 0) {
                return Application.getContext().getString(R.string.sound_level_low);
            }
            if (i2 == 1) {
                return Application.getContext().getString(R.string.sound_level_medium);
            }
            if (i2 == 2) {
                return Application.getContext().getString(R.string.sound_level_high);
            }
            if (i2 != 3) {
                return "";
            }
            return Application.getContext().getString(R.string.sound_level_extra_high);
        }
    }
}
