package com.samsung.android.sdk.mobileservice.auth;

public class TokenInfo {
    public static final String TOKEN_TYPE_ACCESS = "accessToken";
    public static final String TOKEN_TYPE_AUTH_CODE = "authCode";
    public static final String TOKEN_TYPE_REFRESH = "refreshToken";
    private String mApiServerUrl;
    private String mAuthServerUrl;
    private String mToken;
    private long mTokenIssuedTime;
    private String mTokenType;
    private long mTokenValidityPeriod;

    public String getToken() {
        return this.mToken;
    }

    public void setToken(String str) {
        this.mToken = str;
    }

    public String getTokenType() {
        return this.mTokenType;
    }

    public void setTokenType(String str) {
        this.mTokenType = str;
    }

    public String getApiServerUrl() {
        return this.mApiServerUrl;
    }

    public void setApiServerUrl(String str) {
        this.mApiServerUrl = str;
    }

    public String getAuthServerUrl() {
        return this.mAuthServerUrl;
    }

    public void setAuthServerUrl(String str) {
        this.mAuthServerUrl = str;
    }

    public long getTokenValidityPeriod() {
        return this.mTokenValidityPeriod;
    }

    public void setTokenValidityPeriod(long j) {
        this.mTokenValidityPeriod = j;
    }

    public long getTokenIssuedTime() {
        return this.mTokenIssuedTime;
    }

    public void setTokenIssuedTime(long j) {
        this.mTokenIssuedTime = j;
    }
}
