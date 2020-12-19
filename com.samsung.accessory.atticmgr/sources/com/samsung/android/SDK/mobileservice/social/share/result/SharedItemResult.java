package com.samsung.android.sdk.mobileservice.social.share.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.share.SharedItem;

public class SharedItemResult implements Result {
    private SharedItem mResult;
    private CommonResultStatus mStatus;

    public SharedItemResult(CommonResultStatus commonResultStatus, SharedItem sharedItem) {
        this.mStatus = commonResultStatus;
        this.mResult = sharedItem;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public SharedItem getResult() {
        return this.mResult;
    }
}
