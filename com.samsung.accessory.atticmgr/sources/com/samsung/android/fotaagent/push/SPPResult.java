package com.samsung.android.fotaagent.push;

import com.samsung.android.fotaprovider.log.Log;

public class SPPResult {
    public static final String REGISTER_ERROR = "PUSH_REGISTRATION_FAIL";
    public static final String UNREGISTER_ERROR = "PUSH_DEREGISTRATION_FAIL";
    protected boolean bSuccess = false;
    protected int mError = 0;
    protected String mPushID = "";

    public SPPResult(boolean z) {
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

    public void setError(int i) {
        this.mError = i;
        Log.I("SPP Register error: " + i);
    }

    public int getError() {
        return this.mError;
    }
}
