package com.samsung.accessory.hearablemgr.core.bixby;

import android.os.Bundle;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.bixby.BixbyConstants;
import com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgNoiseControls;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.android.sdk.bixby2.action.ActionHandler;
import com.samsung.android.sdk.bixby2.action.ResponseCallback;
import java.util.HashMap;
import java.util.List;
import seccompat.android.util.Log;

public class BixbyTurnOffFeature {
    public static final String TAG = (Application.TAG_ + BixbyTurnOffFeature.class.getSimpleName());

    public void executeAction(Bundle bundle, ResponseCallback responseCallback) {
        ResponseCallback responseCallback2;
        JsonObject jsonObject = new JsonObject();
        HashMap hashMap = (HashMap) bundle.getSerializable(ActionHandler.PARAMS);
        String str = null;
        if (hashMap != null) {
            for (String str2 : hashMap.keySet()) {
                Log.d(TAG, "key : " + str2.toString() + " value : " + ((String) ((List) hashMap.get(str2)).get(0)));
                if (str2.equals(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME)) {
                    str = (String) ((List) hashMap.get(str2)).get(0);
                }
            }
        } else {
            Log.e(TAG, "paramsMap == null");
        }
        if (str != null) {
            EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
            char c = 65535;
            switch (str.hashCode()) {
                case -1658843502:
                    if (str.equals(BixbyConstants.Response.GAMING_MODE)) {
                        c = 2;
                        break;
                    }
                    break;
                case 65974:
                    if (str.equals(BixbyConstants.Response.ANC)) {
                        c = 0;
                        break;
                    }
                    break;
                case 66015:
                    if (str.equals(BixbyConstants.Response.AOM)) {
                        c = 3;
                        break;
                    }
                    break;
                case 252952276:
                    if (str.equals(BixbyConstants.Response.SPEAK_EASY)) {
                        c = 4;
                        break;
                    }
                    break;
                case 791026039:
                    if (str.equals(BixbyConstants.Response.AMBIENT_SOUND)) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0 || c == 1) {
                responseCallback2 = responseCallback;
                if (earBudsInfo.noiseControls == 0) {
                    jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                    jsonObject.addProperty(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME, str);
                    jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_OFF);
                } else {
                    Application.getCoreService().sendSppMessage(new MsgNoiseControls((byte) 0));
                    earBudsInfo.noiseControls = 0;
                    jsonObject.addProperty("result", "success");
                    jsonObject.addProperty(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME, BixbyConstants.Response.OFF);
                }
            } else if (c == 2) {
                responseCallback2 = responseCallback;
                if (!GameModeManager.isSupportDevice()) {
                    jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                    jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.NOT_SUPPORTED);
                    JsonArray jsonArray = new JsonArray();
                    jsonArray.add(BixbyConstants.Response.AMBIENT_SOUND);
                    jsonArray.add(BixbyConstants.Response.ANC);
                    if (Application.getAomManager().isSupportAOM()) {
                        jsonArray.add(BixbyConstants.Response.AOM);
                    }
                    jsonArray.add(BixbyConstants.Response.SPEAK_EASY);
                    jsonObject.add(BixbyConstants.Response.AVAILABLE_FEATURE, jsonArray);
                    Log.d(TAG, jsonObject.toString());
                    responseCallback2.onComplete(jsonObject.toString());
                    return;
                } else if (!earBudsInfo.adjustSoundSync) {
                    jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                    jsonObject.addProperty(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME, str);
                    jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_OFF);
                } else {
                    Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.ADJUST_SOUND_SYNC, (byte) 0));
                    earBudsInfo.adjustSoundSync = false;
                    SamsungAnalyticsUtil.setStatusString(SA.Status.GAME_MODE, earBudsInfo.adjustSoundSync ? "1" : "0");
                    jsonObject.addProperty("result", "success");
                    jsonObject.addProperty(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME, str);
                }
            } else if (c != 3) {
                if (c != 4) {
                    jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                    jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.NOT_SUPPORTED);
                    JsonArray jsonArray2 = new JsonArray();
                    jsonArray2.add(BixbyConstants.Response.AMBIENT_SOUND);
                    jsonArray2.add(BixbyConstants.Response.ANC);
                    if (Application.getAomManager().isSupportAOM()) {
                        jsonArray2.add(BixbyConstants.Response.AOM);
                    }
                    if (GameModeManager.isSupportDevice()) {
                        jsonArray2.add(BixbyConstants.Response.GAMING_MODE);
                    }
                    jsonArray2.add(BixbyConstants.Response.SPEAK_EASY);
                    jsonObject.add(BixbyConstants.Response.AVAILABLE_FEATURE, jsonArray2);
                } else if (!earBudsInfo.detectConversations) {
                    jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                    jsonObject.addProperty(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME, str);
                    jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_OFF);
                } else {
                    Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_DETECT_CONVERSATIONS, (byte) 0));
                    earBudsInfo.detectConversations = false;
                    jsonObject.addProperty("result", "success");
                    jsonObject.addProperty(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME, str);
                    SamsungAnalyticsUtil.setStatusInt(SA.Status.VOICE_DETECT_STATUS, 0);
                }
                responseCallback2 = responseCallback;
            } else if (!Application.getAomManager().isSupportAOM()) {
                jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.NOT_SUPPORTED);
                JsonArray jsonArray3 = new JsonArray();
                jsonArray3.add(BixbyConstants.Response.AMBIENT_SOUND);
                jsonArray3.add(BixbyConstants.Response.ANC);
                if (GameModeManager.isSupportDevice()) {
                    jsonArray3.add(BixbyConstants.Response.GAMING_MODE);
                }
                jsonArray3.add(BixbyConstants.Response.SPEAK_EASY);
                jsonObject.add(BixbyConstants.Response.AVAILABLE_FEATURE, jsonArray3);
                Log.d(TAG, jsonObject.toString());
                responseCallback.onComplete(jsonObject.toString());
                return;
            } else {
                responseCallback2 = responseCallback;
                if (!earBudsInfo.voiceWakeUp) {
                    jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                    jsonObject.addProperty(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME, str);
                    jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_OFF);
                } else {
                    Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_VOICE_WAKE_UP, (byte) 0));
                    earBudsInfo.voiceWakeUp = false;
                    SamsungAnalyticsUtil.setStatusString(SA.Status.VOICE_WAKE_UP, earBudsInfo.voiceWakeUp ? "1" : "0");
                    Application.getAomManager().setBixbyMic(false);
                    jsonObject.addProperty("result", "success");
                    jsonObject.addProperty(BixbyConstants.Response.GALAXY_BUDS_FEATURE_NAME, str);
                }
            }
            Log.d(TAG, jsonObject.toString());
            responseCallback2.onComplete(jsonObject.toString());
        }
    }
}
