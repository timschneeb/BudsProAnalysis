package com.samsung.android.sdk.mobileservice.common.result;

public class BooleanResult implements Result {
    private boolean mResult;
    private CommonResultStatus mStatus;

    public BooleanResult(CommonResultStatus commonResultStatus, boolean z) {
        this.mStatus = commonResultStatus;
        this.mResult = z;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public boolean getResult() {
        return this.mResult;
    }
}
