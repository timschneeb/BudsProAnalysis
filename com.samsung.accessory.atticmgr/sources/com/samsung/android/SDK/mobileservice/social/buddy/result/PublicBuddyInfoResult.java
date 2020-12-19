package com.samsung.android.sdk.mobileservice.social.buddy.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.buddy.PublicBuddyInfo;

public class PublicBuddyInfoResult implements Result {
    private PublicBuddyInfo mResult;
    private CommonResultStatus mStatus;

    public PublicBuddyInfoResult(CommonResultStatus commonResultStatus, PublicBuddyInfo publicBuddyInfo) {
        this.mStatus = commonResultStatus;
        this.mResult = publicBuddyInfo;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public PublicBuddyInfo getResult() {
        return this.mResult;
    }
}
