package com.samsung.android.sdk.mobileservice.auth;

public class AuthInfo {
    private boolean mAccountDisclaimerAgreed;
    private String mAccountId;
    private String mCountryCode;
    private String mDevicePhysicalAddress;
    private boolean mEmailAddressAuthenticated;
    private String mGuid;
    private String mMobileCountryCode;
    private boolean mRealNameAuthenticated;

    public String getAccountId() {
        return this.mAccountId;
    }

    public void setAccountId(String str) {
        this.mAccountId = str;
    }

    public String getGuid() {
        return this.mGuid;
    }

    public void setGuid(String str) {
        this.mGuid = str;
    }

    public boolean getRealNameAuthenticated() {
        return this.mRealNameAuthenticated;
    }

    public void setRealNameAuthenticated(boolean z) {
        this.mRealNameAuthenticated = z;
    }

    public boolean getEmailAddressAuthenticated() {
        return this.mEmailAddressAuthenticated;
    }

    public void setEmailAddressAuthenticated(boolean z) {
        this.mEmailAddressAuthenticated = z;
    }

    public String getMobileCountryCode() {
        return this.mMobileCountryCode;
    }

    public void setMobileCountryCode(String str) {
        this.mMobileCountryCode = str;
    }

    public String getCountryCode() {
        return this.mCountryCode;
    }

    public void setCountryCode(String str) {
        this.mCountryCode = str;
    }

    public String getDevicePhysicalAddress() {
        return this.mDevicePhysicalAddress;
    }

    public void setDevicePhysicalAddress(String str) {
        this.mDevicePhysicalAddress = str;
    }

    public boolean getAccountDisclaimerAgreed() {
        return this.mAccountDisclaimerAgreed;
    }

    public void setAccountDisclaimerAgreed(boolean z) {
        this.mAccountDisclaimerAgreed = z;
    }
}
