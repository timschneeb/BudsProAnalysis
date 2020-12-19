package com.samsung.android.sdk.mobileservice.auth;

public class DeviceAuthInfo {
    private String mImsi;
    private String mMsisdn;

    public String getImsi() {
        return this.mImsi;
    }

    public void setImsi(String str) {
        this.mImsi = str;
    }

    public String getMsisdn() {
        return this.mMsisdn;
    }

    public void setMsisdn(String str) {
        this.mMsisdn = str;
    }
}
