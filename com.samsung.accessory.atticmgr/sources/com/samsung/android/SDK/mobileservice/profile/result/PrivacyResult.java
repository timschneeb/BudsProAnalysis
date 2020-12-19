package com.samsung.android.sdk.mobileservice.profile.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.profile.Privacy;

public class PrivacyResult implements Result {
    private Privacy mResult;
    private CommonResultStatus mStatus;

    public PrivacyResult(CommonResultStatus commonResultStatus, Privacy privacy) {
        this.mStatus = commonResultStatus;
        this.mResult = privacy;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public Privacy getResult() {
        return this.mResult;
    }
}
