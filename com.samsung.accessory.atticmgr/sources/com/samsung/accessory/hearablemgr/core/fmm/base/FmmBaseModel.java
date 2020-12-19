package com.samsung.accessory.hearablemgr.core.fmm.base;

import android.content.Context;
import android.content.Intent;
import com.google.gson.JsonObject;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmUtils;
import seccompat.android.util.Log;

public abstract class FmmBaseModel {
    private static final String TAG = (Application.TAG_ + FmmBaseModel.class.getSimpleName());
    private String mAction;
    private String mOperation;
    private String mResult;
    private int mResultCode;
    private String mType = "buds";
    private String mUid;

    /* access modifiers changed from: protected */
    public abstract JsonObject makeJsonData();

    public FmmBaseModel(String str, String str2, String str3) {
        this.mAction = str;
        this.mOperation = str2;
        this.mUid = str3;
        this.mResult = "success";
        this.mResultCode = 0;
    }

    public FmmBaseModel(String str, String str2) {
        this.mAction = FmmUtils.getActionByOperation(str);
        this.mOperation = str;
        this.mUid = str2;
        this.mResult = "success";
        this.mResultCode = 0;
    }

    public void setResultCode(int i) {
        this.mResult = FmmConstants.Result.FAIL;
        this.mResultCode = i;
    }

    public JsonObject makeJsonResult() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", this.mType);
        jsonObject.addProperty("operation", this.mOperation);
        jsonObject.addProperty("uid", this.mUid);
        jsonObject.addProperty("result", this.mResult);
        jsonObject.addProperty("result_code", Integer.valueOf(this.mResultCode));
        jsonObject.add("data", makeJsonData());
        return jsonObject;
    }

    public void send(Context context) {
        Log.d(TAG, "send()");
        String str = TAG;
        Log.d(str, "makeJsonResult().toString() : " + makeJsonResult().toString());
        Intent intent = new Intent(this.mAction);
        intent.putExtra("result", makeJsonResult().toString());
        intent.putExtra(FmmConstants.PLUGIN_PACKAGE, context.getPackageName());
        intent.putExtra(FmmConstants.PLUGIN_RECEIVER, FmmUtils.getRequestReceiverClassName());
        intent.setPackage(FmmConstants.FMM_PACKAGE);
        context.sendBroadcast(intent, FmmConstants.FMM_PERMISSION_SIGNATURE);
    }
}
