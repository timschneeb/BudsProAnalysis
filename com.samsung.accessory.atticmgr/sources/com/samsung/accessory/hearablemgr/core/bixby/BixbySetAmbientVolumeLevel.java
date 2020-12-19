package com.samsung.accessory.hearablemgr.core.bixby;

import android.os.Bundle;
import com.google.gson.JsonObject;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.bixby.BixbyConstants;
import com.samsung.accessory.hearablemgr.core.service.message.MsgAmbientSoundLevel;
import com.samsung.android.sdk.bixby2.action.ActionHandler;
import com.samsung.android.sdk.bixby2.action.ResponseCallback;
import java.util.HashMap;
import java.util.List;
import seccompat.android.util.Log;

public class BixbySetAmbientVolumeLevel {
    public static final String TAG = (Application.TAG_ + BixbySetAmbientVolumeLevel.class.getSimpleName());

    public void executeAction(Bundle bundle, ResponseCallback responseCallback) {
        JsonObject jsonObject = new JsonObject();
        HashMap hashMap = (HashMap) bundle.getSerializable(ActionHandler.PARAMS);
        String str = null;
        if (hashMap != null) {
            for (String str2 : hashMap.keySet()) {
                Log.d(TAG, "key : " + str2.toString() + " value : " + ((String) ((List) hashMap.get(str2)).get(0)));
                if (str2.equals(BixbyConstants.Response.AMBIENT_VOLUME_LEVEL)) {
                    str = (String) ((List) hashMap.get(str2)).get(0);
                }
            }
        } else {
            Log.e(TAG, "paramsMap == null");
        }
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        if (earBudsInfo.noiseControls != 2) {
            jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
            jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_OFF);
            Log.d(TAG, jsonObject.toString());
            responseCallback.onComplete(jsonObject.toString());
            return;
        }
        if (str != null) {
            try {
                if (str.equals("none")) {
                    jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                    jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.EMPTY_VALUE);
                    jsonObject.addProperty(BixbyConstants.Response.MAX_LEVEL, (Number) 4);
                } else {
                    int parseInt = Integer.parseInt(str) - 1;
                    if (parseInt < 0 || parseInt > 3) {
                        jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                        jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.INVALID_VALUE);
                        jsonObject.addProperty(BixbyConstants.Response.MAX_LEVEL, (Number) 4);
                    } else {
                        int i = earBudsInfo.ambientSoundLevel;
                        if (i == 0 && parseInt == 0) {
                            jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                            jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_MIN);
                        } else if (i == 3 && parseInt == 3) {
                            jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                            jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_MAX);
                        } else if (i == parseInt) {
                            jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                            jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_SET);
                        } else {
                            earBudsInfo.ambientSoundLevel = parseInt;
                            Application.getCoreService().sendSppMessage(new MsgAmbientSoundLevel((byte) parseInt));
                            jsonObject.addProperty("result", "success");
                            SamsungAnalyticsUtil.setStatusInt(SA.Status.AMBIENT_SOUND_VOLUME_STATUS, earBudsInfo.ambientSoundLevel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Log.d(TAG, jsonObject.toString());
        responseCallback.onComplete(jsonObject.toString());
    }
}
