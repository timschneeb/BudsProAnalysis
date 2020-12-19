package com.samsung.android.sdk.mobileservice.auth.result;

import com.samsung.android.sdk.mobileservice.auth.AuthInfo;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class AuthInfoResult implements Result {
    private AuthInfo mResult;
    private CommonResultStatus mStatus;

    public AuthInfoResult(CommonResultStatus commonResultStatus, AuthInfo authInfo) {
        this.mStatus = commonResultStatus;
        this.mResult = authInfo;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public AuthInfo getResult() {
        return this.mResult;
    }
}
