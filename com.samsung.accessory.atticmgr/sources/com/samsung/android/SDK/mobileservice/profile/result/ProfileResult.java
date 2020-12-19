package com.samsung.android.sdk.mobileservice.profile.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.profile.Profile;

public class ProfileResult implements Result {
    private Profile mResult;
    private CommonResultStatus mStatus;

    public ProfileResult(CommonResultStatus commonResultStatus, Profile profile) {
        this.mStatus = commonResultStatus;
        this.mResult = profile;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public Profile getResult() {
        return this.mResult;
    }
}
