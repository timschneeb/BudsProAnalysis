package com.samsung.accessory.hearablemgr.common.soagent;

import android.text.TextUtils;

public class AccessoryInfo {
    public static final String TYPE_ICON_X = "GEAR ICONX";
    private String mBTAddress = "";
    private String mHostDeviceMCC = "";
    private String mHostDeviceMNC = "";
    private String mMCC = "";
    private String mModelName = "";
    private String mSerialNumber = "";
    private String mToken = "";
    private String mType = "";

    public String getType() {
        return this.mType;
    }

    public void setType(String str) {
        this.mType = checkEmpty(str);
    }

    public String getSerialNumber() {
        return this.mSerialNumber;
    }

    public void setSerialNumber(String str) {
        this.mSerialNumber = checkEmpty(str);
    }

    public String getBTAddress() {
        return this.mBTAddress;
    }

    public void setBTAddress(String str) {
        this.mBTAddress = checkEmpty(str);
    }

    public String getModelName() {
        return this.mModelName;
    }

    public void setModelName(String str) {
        this.mModelName = checkEmpty(str);
    }

    public String getMCC() {
        return this.mMCC;
    }

    public void setMCC(String str) {
        this.mMCC = checkEmpty(str);
    }

    public String getHDMCC() {
        return this.mHostDeviceMCC;
    }

    public void setHDMCC(String str) {
        this.mHostDeviceMCC = checkEmpty(str);
    }

    public String getHDMNC() {
        return this.mHostDeviceMNC;
    }

    public void setHDMNC(String str) {
        this.mHostDeviceMNC = checkEmpty(str);
    }

    public String getToken() {
        return this.mToken;
    }

    public void setToken(String str) {
        this.mToken = checkEmpty(str);
    }

    private String checkEmpty(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }
}
