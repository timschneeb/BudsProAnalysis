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

public class BixbyAmbientModeVolumeDown {
    public static final String TAG = (Application.TAG_ + BixbyAmbientModeVolumeDown.class.getSimpleName());

    public void executeAction(ResponseCallback responseCallback) {
        JsonObject jsonObject = new JsonObject();
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        if (earBudsInfo.noiseControls == 2) {
            int i = earBudsInfo.ambientSoundLevel;
            if (i == 0) {
                jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
                jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_MIN);
            } else {
                int i2 = i - 1;
                if (i2 < 0) {
                    i2 = 0;
                }
                earBudsInfo.ambientSoundLevel = i2;
                Application.getCoreService().sendSppMessage(new MsgAmbientSoundLevel((byte) i2));
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
