package com.samsung.android.sdk.mobileservice.common.result;

public class StringResult implements Result {
    private String mResult;
    private CommonResultStatus mStatus;

    public StringResult(CommonResultStatus commonResultStatus, String str) {
        this.mStatus = commonResultStatus;
        this.mResult = str;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public String getResult() {
        return this.mResult;
    }
}
