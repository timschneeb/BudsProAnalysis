package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.content.Intent;
import android.os.Build;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineQueue;
import com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager;
import com.samsung.accessory.hearablemgr.core.notification.NotificationConstants;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgLockTouchpad;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSetEqualizerType;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSetTouchpadOption;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlUtil;
import com.samsung.android.SDK.routine.AbsRoutineActionProvider;
import org.json.JSONException;
import org.json.JSONObject;
import seccompat.android.util.Log;

public class RoutineActionProvider extends AbsRoutineActionProvider {
    public static final int IS_SUPPORT_RESULT_FAIL_NOT_AVAILABLE = -2;
    public static final int IS_SUPPORT_RESULT_OK = 1;
    private static final String TAG = (Application.TAG_ + RoutineActionProvider.class.getSimpleName());

    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public int onAct(String str, String str2, boolean z) {
        return 0;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public String getCurrentParam(String str) {
        char c;
        Log.d(TAG, "getCurrentParam : " + str);
        switch (str.hashCode()) {
            case -1720051364:
                if (str.equals("attic_touchpad_option")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -740188306:
                if (str.equals("attic_bixby_voice_wake_up")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -572103441:
                if (str.equals("attic_gaming_mode")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -435311468:
                if (str.equals("attic_voice_detect")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -294349020:
                if (str.equals("attic_notifications")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 232242958:
                if (str.equals("attic_equalizer")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 463152068:
                if (str.equals("attic_lock_touchpad")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2020340607:
                if (str.equals("attic_noise_controls")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return String.valueOf(Application.getCoreService().getEarBudsInfo().noiseControls);
            case 1:
                return String.valueOf(Application.getCoreService().getEarBudsInfo().equalizerType);
            case 2:
                return String.valueOf(Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true));
            case 3:
                return String.valueOf(Application.getCoreService().getEarBudsInfo().touchpadLocked);
            case 4:
                return String.valueOf(Application.getCoreService().getEarBudsInfo().adjustSoundSync);
            case 5:
                return RoutineUtils.makeTouchpadOptionToJson(Application.getCoreService().getEarBudsInfo().touchpadOptionLeft, Application.getCoreService().getEarBudsInfo().touchpadOptionRight, Preferences.getString(PreferenceKey.LEFT_OTHER_OPTION_PACKAGE_NAME, ""), Preferences.getString(PreferenceKey.RIGHT_OTHER_OPTION_PACKAGE_NAME, "")).toString();
            case 6:
                return String.valueOf(Application.getCoreService().getEarBudsInfo().voiceWakeUp);
            case 7:
                return String.valueOf(Application.getCoreService().getEarBudsInfo().detectConversations);
            default:
                return null;
        }
    }

    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public int onAct(String str, String str2, boolean z, boolean z2) {
        String str3 = TAG;
        Log.d(str3, "onAct tag : " + str + ", param : " + str2 + ", isNegative : " + z + ", isRecovery : " + z2);
        if (str2 == null) {
            Log.d(TAG, "Action fail :: ACT_ERR_NONE_PARAM");
            return -102;
        } else if (Application.getCoreService().isExtendedStatusReady()) {
            return onAct(str, str2);
        } else {
            RoutineQueue.getInstance().offer(new RoutineQueue.Action(str, str2, z, z2));
            return 0;
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int onAct(String str, String str2) {
        char c;
        if (!Application.getCoreService().isExtendedStatusReady()) {
            Log.d(TAG, "Action fail :: ACT_ERR_SPP_CONNECTION_FAIL");
            return -101;
        } else if (!Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_DONE, false)) {
            Log.d(TAG, "Action fail :: ACT_ERR_NOT_OOBE_COMPLETED");
            return -100;
        } else {
            switch (str.hashCode()) {
                case -1720051364:
                    if (str.equals("attic_touchpad_option")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -740188306:
                    if (str.equals("attic_bixby_voice_wake_up")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -572103441:
                    if (str.equals("attic_gaming_mode")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -435311468:
                    if (str.equals("attic_voice_detect")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -294349020:
                    if (str.equals("attic_notifications")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 232242958:
                    if (str.equals("attic_equalizer")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 463152068:
                    if (str.equals("attic_lock_touchpad")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2020340607:
                    if (str.equals("attic_noise_controls")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    int intValue = Integer.valueOf(str2).intValue();
                    if (intValue == Application.getCoreService().getEarBudsInfo().noiseControls) {
                        Log.d(TAG, "Already set to noise controls : " + intValue);
                        break;
                    } else if (!NoiseControlUtil.setNoiseControl(intValue)) {
                        if (intValue == 1 && !NoiseControlUtil.isBothWearing()) {
                            Log.d(TAG, "Action fail :: ACT_ERR_NOT_BOTH_WEARING");
                            return -106;
                        } else if (intValue == 2 && !NoiseControlUtil.isWearing()) {
                            Log.d(TAG, "Action fail :: ACT_ERR_NOT_WEARING");
                            return -107;
                        }
                    }
                    break;
                case 1:
                    int intValue2 = Integer.valueOf(str2).intValue();
                    Application.getCoreService().getEarBudsInfo().equalizerType = intValue2;
                    SamsungAnalyticsUtil.setStatusString(SA.Status.EQUALIZER_STATUS, SamsungAnalyticsUtil.equalizerTypeToDetail(intValue2));
                    Application.getCoreService().sendSppMessage(new MsgSetEqualizerType((byte) intValue2));
                    Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_EQUALIZER_TYPE_UPDATED));
                    break;
                case 2:
                    if (NotificationUtil.isAccessibilityON()) {
                        if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_FIRST_ENTRY, true)) {
                            Log.d(TAG, "NOTIFICATION_FIRST_ENTRY set to false");
                            Preferences.putBoolean(PreferenceKey.NOTIFICATION_FIRST_ENTRY, false);
                        }
                        boolean booleanValue = Boolean.valueOf(str2).booleanValue();
                        NotificationUtil.setPreIncomingCallStatus(booleanValue);
                        Preferences.putBoolean(PreferenceKey.NOTIFICATION_ENABLE, Boolean.valueOf(booleanValue));
                        Util.sendPermissionBroadcast(Application.getContext(), new Intent(NotificationConstants.ACTION_NOTIFICATION_SETTING_UPDATE));
                        break;
                    } else {
                        Log.d(TAG, "Action fail :: ACT_ERR_PERMISSION_DENIED");
                        return -105;
                    }
                case 3:
                    boolean booleanValue2 = Boolean.valueOf(str2).booleanValue();
                    Application.getCoreService().getEarBudsInfo().touchpadLocked = booleanValue2;
                    Application.getCoreService().sendSppMessage(new MsgLockTouchpad(booleanValue2));
                    break;
                case 4:
                    if (GameModeManager.isSupportDevice()) {
                        boolean booleanValue3 = Boolean.valueOf(str2).booleanValue();
                        Application.getCoreService().getEarBudsInfo().adjustSoundSync = booleanValue3;
                        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.ADJUST_SOUND_SYNC, booleanValue3 ? (byte) 1 : 0));
                        break;
                    } else {
                        Log.d(TAG, "Action fail :: ACT_ERR_NOT_SUPPORTED_GAMING_MODE");
                        return -104;
                    }
                case 5:
                    try {
                        JSONObject jSONObject = new JSONObject(str2);
                        int i = jSONObject.getInt("touch_option_left");
                        int i2 = jSONObject.getInt("touch_option_right");
                        if (i == 5) {
                            Preferences.putString(PreferenceKey.LEFT_OTHER_OPTION_PACKAGE_NAME, jSONObject.getString("package_name_left"));
                        }
                        if (i2 == 6) {
                            Preferences.putString(PreferenceKey.RIGHT_OTHER_OPTION_PACKAGE_NAME, jSONObject.getString("package_name_right"));
                        }
                        Application.getCoreService().getEarBudsInfo().touchpadOptionLeft = i;
                        Application.getCoreService().getEarBudsInfo().touchpadOptionRight = i2;
                        Application.getCoreService().sendSppMessage(new MsgSetTouchpadOption((byte) i, (byte) i2));
                        break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }
                case 6:
                    boolean booleanValue4 = Boolean.valueOf(str2).booleanValue();
                    Application.getCoreService().getEarBudsInfo().voiceWakeUp = booleanValue4;
                    Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_VOICE_WAKE_UP, booleanValue4 ? (byte) 1 : 0));
                    break;
                case 7:
                    boolean booleanValue5 = Boolean.valueOf(str2).booleanValue();
                    Application.getCoreService().getEarBudsInfo().detectConversations = booleanValue5;
                    Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_DETECT_CONVERSATIONS, booleanValue5 ? (byte) 1 : 0));
                    SamsungAnalyticsUtil.setStatusInt(SA.Status.VOICE_DETECT_STATUS, booleanValue5 ? 1 : 0);
                    break;
            }
            return 1;
        }
    }

    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public int isValid(String str, String str2, boolean z) {
        int actionResult = RoutineUtils.getActionResult(str, str2);
        String str3 = TAG;
        Log.d(str3, "isValid tag : " + str + ", param : " + str2 + " isNegative : " + z + ", result : " + actionResult);
        return actionResult;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public String getLabelParam(String str, String str2, boolean z) {
        char c;
        String str3 = TAG;
        Log.d(str3, "getLabelParam tag : " + str + ", param : " + str2 + ", isNegative : " + z);
        switch (str.hashCode()) {
            case -1720051364:
                if (str.equals("attic_touchpad_option")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -740188306:
                if (str.equals("attic_bixby_voice_wake_up")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -572103441:
                if (str.equals("attic_gaming_mode")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -435311468:
                if (str.equals("attic_voice_detect")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -294349020:
                if (str.equals("attic_notifications")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 232242958:
                if (str.equals("attic_equalizer")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 463152068:
                if (str.equals("attic_lock_touchpad")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2020340607:
                if (str.equals("attic_noise_controls")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return RoutineUtils.makeOnOffLabel(str2);
            case 6:
                return RoutineUtils.makeEqualizerLabel(str2);
            case 7:
                return RoutineUtils.makeNoiseControlLabel(str2);
            default:
                return null;
        }
    }

    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public int isSupport(String str) {
        if (Build.VERSION.SDK_INT < 29) {
            return -2;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        int i = 1;
        if (hashCode != -740188306) {
            if (hashCode == -572103441 && str.equals("attic_gaming_mode")) {
                c = 1;
            }
        } else if (str.equals("attic_bixby_voice_wake_up")) {
            c = 0;
        }
        if (c == 0 ? !Application.getAomManager().checkEnabledBixby() : c == 1 && !GameModeManager.isSupportDevice()) {
            i = -2;
        }
        Log.d(TAG, "tag : " + str + ", isSupport : " + i);
        return i;
    }
}
