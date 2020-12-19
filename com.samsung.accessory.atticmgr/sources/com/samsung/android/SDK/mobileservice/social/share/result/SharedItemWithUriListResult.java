package com.samsung.android.sdk.mobileservice.social.share.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.share.SharedItemWithUriList;

public class SharedItemWithUriListResult implements Result {
    private SharedItemWithUriList mResult;
    private CommonResultStatus mStatus;

    public SharedItemWithUriListResult(CommonResultStatus commonResultStatus, SharedItemWithUriList sharedItemWithUriList) {
        this.mStatus = commonResultStatus;
        this.mResult = sharedItemWithUriList;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public SharedItemWithUriList getResult() {
        return this.mResult;
    }
}
