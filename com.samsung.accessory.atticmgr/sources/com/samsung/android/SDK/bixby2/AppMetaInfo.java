package com.samsung.android.sdk.bixby2;

public class AppMetaInfo {
    int appVersionCode;
    String capsuleId;

    public AppMetaInfo(String str, int i) {
        this.capsuleId = str;
        this.appVersionCode = i;
    }

    public String getCapsuleId() {
        return this.capsuleId;
    }

    public void setCapsuleId(String str) {
        this.capsuleId = str;
    }

    public int getAppVersionCode() {
        return this.appVersionCode;
    }

    public void setAppVersionCode(int i) {
        this.appVersionCode = i;
    }
}
