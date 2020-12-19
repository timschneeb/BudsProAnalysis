package com.samsung.android.sdk.mobileservice.social.share.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.share.Space;

public class SpaceResult implements Result {
    private Space mResult;
    private CommonResultStatus mStatus;

    public SpaceResult(CommonResultStatus commonResultStatus, Space space) {
        this.mStatus = commonResultStatus;
        this.mResult = space;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public Space getResult() {
        return this.mResult;
    }
}
