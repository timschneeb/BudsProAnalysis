package com.samsung.android.fotaagent.push;

import com.samsung.android.fotaprovider.log.Log;

public class FCMResult {
    protected boolean bSuccess = false;
    protected String mErrorMsg = "";
    protected String mPushID = "";

    public void setResult(boolean z) {
        this.bSuccess = z;
    }

    public boolean isSuccess() {
        return this.bSuccess;
    }

    public void setPushID(String str) {
        this.mPushID = str;
    }

    public String getPushID() {
        return this.mPushID;
    }

    public void setErrorMsg(String str) {
        this.mErrorMsg = str;
        Log.D("FCM Register error: " + str);
    }

    public String getErrorMsg() {
        return this.mErrorMsg;
    }

    public void setNextRetry() {
        if (FCM.PLAY_SERVICE_ERROR.equals(this.mErrorMsg) || FCM.FCM_UNKNOWN_ERROR.equals(this.mErrorMsg)) {
            Log.W("need to register FCM next time");
        }
    }
}
