package com.samsung.accessory.hearablemgr.core.bixby;

import com.google.gson.JsonObject;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.bixby.BixbyConstants;
import com.samsung.accessory.hearablemgr.core.service.message.MsgLockTouchpad;
import com.samsung.android.sdk.bixby2.action.ResponseCallback;
import seccompat.android.util.Log;

public class BixbyUnLockTouchpad {
    public static final String TAG = (Application.TAG_ + BixbyUnLockTouchpad.class.getSimpleName());

    public void executeAction(ResponseCallback responseCallback) {
        JsonObject jsonObject = new JsonObject();
        if (!Application.getCoreService().getEarBudsInfo().touchpadLocked) {
            jsonObject.addProperty("result", BixbyConstants.Response.FAILURE);
            jsonObject.addProperty(BixbyConstants.Response.MORE_INFO, BixbyConstants.Response.ALREADY_UNLOCKED);
        } else {
            Application.getCoreService().getEarBudsInfo().touchpadLocked = false;
            Application.getCoreService().sendSppMessage(new MsgLockTouchpad(false));
            jsonObject.addProperty("result", "success");
        }
        Log.d(TAG, jsonObject.toString());
        responseCallback.onComplete(jsonObject.toString());
    }
}
