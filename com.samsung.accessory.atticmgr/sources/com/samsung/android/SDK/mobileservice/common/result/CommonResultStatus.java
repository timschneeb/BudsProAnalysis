package com.samsung.android.sdk.mobileservice.common.result;

public class CommonResultStatus {
    private String mAgentCode;
    private int mCode;
    private String mDisplayMessage;
    private String mMessage;

    public CommonResultStatus(int i) {
        this.mCode = i;
    }

    public CommonResultStatus(int i, String str, String str2) {
        this.mCode = i;
        this.mMessage = str;
        this.mAgentCode = str2;
    }

    public CommonResultStatus(int i, String str, String str2, String str3) {
        this.mCode = i;
        this.mMessage = str;
        this.mAgentCode = str2;
        this.mDisplayMessage = str3;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getDisplayMessage() {
        return this.mDisplayMessage;
    }

    public String getAgentCode() {
        return this.mAgentCode;
    }
}
