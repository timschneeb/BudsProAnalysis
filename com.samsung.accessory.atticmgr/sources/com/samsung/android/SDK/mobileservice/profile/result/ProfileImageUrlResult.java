package com.samsung.android.sdk.mobileservice.profile.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class ProfileImageUrlResult implements Result {
    private String mProfileImageUrl;
    private CommonResultStatus mStatus;

    public ProfileImageUrlResult(CommonResultStatus commonResultStatus, String str) {
        this.mStatus = commonResultStatus;
        this.mProfileImageUrl = str;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public String getResult() {
        return this.mProfileImageUrl;
    }
}
