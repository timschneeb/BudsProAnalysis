package com.samsung.android.sdk.mobileservice.auth.result;

import com.samsung.android.sdk.mobileservice.auth.TokenInfo;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class ResultTokenInfo implements Result {
    private TokenInfo mResult;
    private CommonResultStatus mStatus;

    public ResultTokenInfo(CommonResultStatus commonResultStatus, TokenInfo tokenInfo) {
        this.mStatus = commonResultStatus;
        this.mResult = tokenInfo;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public TokenInfo getResult() {
        return this.mResult;
    }
}
