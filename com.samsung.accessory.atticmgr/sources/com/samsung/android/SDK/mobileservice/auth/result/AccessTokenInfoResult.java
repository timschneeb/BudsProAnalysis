package com.samsung.android.sdk.mobileservice.auth.result;

import com.samsung.android.sdk.mobileservice.auth.TokenInfo;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class AccessTokenInfoResult implements Result {
    private TokenInfo mAccessToken;
    private CommonResultStatus mStatus;

    public AccessTokenInfoResult(CommonResultStatus commonResultStatus, TokenInfo tokenInfo) {
        this.mStatus = commonResultStatus;
        this.mAccessToken = tokenInfo;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public TokenInfo getAccessToken() {
        return this.mAccessToken;
    }
}
