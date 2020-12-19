package com.samsung.accessory.hearablemgr.core.bixby;

import android.content.Context;
import android.os.Bundle;
import com.google.gson.JsonObject;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.bixby.BixbyConstants;
import com.samsung.android.sdk.bixby2.Sbixby;
import com.samsung.android.sdk.bixby2.action.ActionHandler;
import com.samsung.android.sdk.bixby2.action.ResponseCallback;
import seccompat.android.util.Log;

public class BixbyActionHandler extends ActionHandler {
    public static final String TAG = (Application.TAG_ + BixbyActionHandler.class.getSimpleName());

    public static class Actions {
        static final String ACTION_AMBIENT_MODE_VOLUME_DOWN = "AmbientVolumeDown";
        static final String ACTION_AMBIENT_MODE_VOLUME_UP = "AmbientVolumeUp";
        static final String ACTION_CHANGE_EQUALIZER_MODE = "ChangeEqualizerMode";
        static final String ACTION_CHANGE_NOISECONTROL = "ChangeNoiseControl";
        static final String ACTION_CHECK_AMBIENT_VOLUME_LEVEL = "CheckAmbientVolumeLevel";
        static final String ACTION_LOCK_TOUCHPAD = "LockTouchPad";
        static final String ACTION_SET_AMBIENT_VOLUME_LEVEL = "SetAmbientVolumeLevel";
        static final String ACTION_SHOW_BATTERY_STATUS = "ShowBatteryStatus";
        static final String ACTION_SHOW_EQ_STATUS = "ShowEQStatus";
        static final String ACTION_TURN_OFF_FEATURE = "TurnOffFeature";
        static final String ACTION_TURN_ON_FEATURE = "TurnOnFeature";
        static final String ACTION_UNLOCK_TOUCHPAD = "UnLockTouchPad";
    }

    public static void initialize(Context context) {
        Log.d(TAG, "initialize()");
        Sbixby.initialize(context);
        Sbixby instance = Sbixby.getInstance();
        instance.addActionHandler("ShowBatteryStatus", new BixbyActionHandler());
        instance.addActionHandler("TurnOffFeature", new BixbyActionHandler());
        instance.addActionHandler("TurnOnFeature", new BixbyActionHandler());
        instance.addActionHandler("ShowEQStatus", new BixbyActionHandler());
        instance.addActionHandler("ChangeEqualizerMode", new BixbyActionHandler());
        instance.addActionHandler("LockTouchPad", new BixbyActionHandler());
        instance.addActionHandler("UnLockTouchPad", new BixbyActionHandler());
        instance.addActionHandler("ChangeNoiseControl", new BixbyActionHandler());
        instance.addActionHandler("CheckAmbientVolumeLevel", new BixbyActionHandler());
        instance.addActionHandler("AmbientVolumeUp", new BixbyActionHandler());
        instance.addActionHandler("AmbientVolumeDown", new BixbyActionHandler());
        instance.addActionHandler("SetAmbientVolumeLevel", new BixbyActionHandler());
    }

    @Override // com.samsung.android.sdk.bixby2.action.ActionHandler
    public void executeAction(Context context, String str, Bundle bundle, ResponseCallback responseCallback) {
        String str2 = TAG;
        Log.d(str2, "Action name : " + str);
        JsonObject jsonObject = new JsonObject();
        if (!Application.getCoreService().isConnected()) {
            jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
            jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.NOT_CONNECTED);
            responseCallback.onComplete(jsonObject.toString());
            return;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -2004243708:
                if (str.equals("TurnOffFeature")) {
                    c = 1;
                    break;
                }
                break;
            case -1977937036:
                if (str.equals("AmbientVolumeDown")) {
                    c = '\n';
                    break;
                }
                break;
            case -1852268333:
                if (str.equals("ChangeNoiseControl")) {
                    c = 7;
                    break;
                }
                break;
            case -1764131720:
                if (str.equals("UnLockTouchPad")) {
                    c = 6;
                    break;
                }
                break;
            case -1722841094:
                if (str.equals("TurnOnFeature")) {
                    c = 2;
                    break;
                }
                break;
            case -1512373182:
                if (str.equals("ShowBatteryStatus")) {
                    c = 0;
                    break;
                }
                break;
            case -881224283:
                if (str.equals("ChangeEqualizerMode")) {
                    c = 4;
                    break;
                }
                break;
            case -346191379:
                if (str.equals("AmbientVolumeUp")) {
                    c = '\t';
                    break;
                }
                break;
            case 468238143:
                if (str.equals("LockTouchPad")) {
                    c = 5;
                    break;
                }
                break;
            case 602240475:
                if (str.equals("ShowEQStatus")) {
                    c = 3;
                    break;
                }
                break;
            case 1294471642:
                if (str.equals("CheckAmbientVolumeLevel")) {
                    c = '\b';
                    break;
                }
                break;
            case 1666776756:
                if (str.equals("SetAmbientVolumeLevel")) {
                    c = 11;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (responseCallback != null) {
                    new BixbyBatteryStatus().executeAction(bundle, responseCallback);
                    return;
                }
                return;
            case 1:
                if (responseCallback != null) {
                    new BixbyTurnOffFeature().executeAction(bundle, responseCallback);
                    return;
                }
                return;
            case 2:
                if (responseCallback != null) {
                    new BixbyTurnOnFeature().executeAction(bundle, responseCallback);
                    return;
                }
                return;
            case 3:
                if (responseCallback != null) {
                    new BixbyShowEQStatus().executeAction(responseCallback);
                    return;
                }
                return;
            case 4:
                if (responseCallback != null) {
                    new BixbyChangeEqualizerMode().executeAction(bundle, responseCallback);
                    return;
                }
                return;
            case 5:
                if (responseCallback != null) {
                    new BixbyLockTouchpad().executeAction(responseCallback);
                    return;
                }
                return;
            case 6:
                if (responseCallback != null) {
                    new BixbyUnLockTouchpad().executeAction(responseCallback);
                    return;
                }
                return;
            case 7:
                if (responseCallback != null) {
                    new BixbyChangeNoiseControl().executeAction(bundle, responseCallback);
                    return;
                }
                return;
            case '\b':
                if (responseCallback != null) {
                    new BixbyCheckAmbientVolumeLevel().executeAction(responseCallback);
                    return;
                }
                return;
            case '\t':
                if (responseCallback != null) {
                    new BixbyAmbientModeVolumeUp().executeAction(responseCallback);
                    return;
                }
                return;
            case '\n':
                if (responseCallback != null) {
                    new BixbyAmbientModeVolumeDown().executeAction(responseCallback);
                    return;
                }
                return;
            case 11:
                if (responseCallback != null) {
                    new BixbySetAmbientVolumeLevel().executeAction(bundle, responseCallback);
                    return;
                }
                return;
            default:
                Log.d(TAG, "not support feature");
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("result", BixbyConstants.Response.FAILURE);
                jsonObject2.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.NOT_SUPPORTED);
                responseCallback.onComplete(jsonObject2.toString());
                return;
        }
    }
}
