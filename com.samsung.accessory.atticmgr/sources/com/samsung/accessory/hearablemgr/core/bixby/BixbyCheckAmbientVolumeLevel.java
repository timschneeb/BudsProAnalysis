package com.samsung.accessory.hearablemgr.core.bixby;

import com.google.gson.JsonObject;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bixby.BixbyConstants;
import com.samsung.android.sdk.bixby2.action.ResponseCallback;
import seccompat.android.util.Log;

public class BixbyCheckAmbientVolumeLevel {
    public static final String TAG = (Application.TAG_ + BixbyCheckAmbientVolumeLevel.class.getSimpleName());

    public void executeAction(ResponseCallback responseCallback) {
        JsonObject jsonObject = new JsonObject();
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        if (earBudsInfo.noiseControls == 2) {
            jsonObject.addProperty("result", "success");
            jsonObject.addProperty(BixbyConstants.Response.AMBIENT_LEVEL, Integer.toString(earBudsInfo.ambientSoundLevel + 1));
        } else {
            jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
            jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_OFF);
        }
        Log.d(TAG, jsonObject.toString());
        responseCallback.onComplete(jsonObject.toString());
    }
}
