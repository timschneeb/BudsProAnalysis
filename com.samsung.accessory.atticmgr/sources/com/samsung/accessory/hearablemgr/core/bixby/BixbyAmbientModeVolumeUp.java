package com.samsung.accessory.hearablemgr.core.bixby;

import com.google.gson.JsonObject;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.bixby.BixbyConstants;
import com.samsung.accessory.hearablemgr.core.service.message.MsgAmbientSoundLevel;
import com.samsung.android.sdk.bixby2.action.ResponseCallback;
import seccompat.android.util.Log;

public class BixbyAmbientModeVolumeUp {
    public static final String TAG = (Application.TAG_ + BixbyAmbientModeVolumeUp.class.getSimpleName());

    public void executeAction(ResponseCallback responseCallback) {
        JsonObject jsonObject = new JsonObject();
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        if (earBudsInfo.noiseControls == 2) {
            int i = 3;
            int i2 = earBudsInfo.ambientSoundLevel;
            if (i2 == 3) {
                jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_MAX);
            } else {
                int i3 = i2 + 1;
                if (i3 <= 3) {
                    i = i3;
                }
                earBudsInfo.ambientSoundLevel = i;
                Application.getCoreService().sendSppMessage(new MsgAmbientSoundLevel((byte) i));
                jsonObject.addProperty("result", "success");
                SamsungAnalyticsUtil.setStatusInt(SA.Status.AMBIENT_SOUND_VOLUME_STATUS, earBudsInfo.ambientSoundLevel);
            }
        } else {
            jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
            jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_OFF);
        }
        Log.d(TAG, jsonObject.toString());
        responseCallback.onComplete(jsonObject.toString());
    }
}
