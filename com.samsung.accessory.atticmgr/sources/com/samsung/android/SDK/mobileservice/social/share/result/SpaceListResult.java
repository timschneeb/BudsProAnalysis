package com.samsung.android.sdk.mobileservice.social.share.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.share.Space;
import java.util.List;

public class SpaceListResult implements Result {
    private List<Space> mResult;
    private CommonResultStatus mStatus;

    public SpaceListResult(CommonResultStatus commonResultStatus, List<Space> list) {
        this.mStatus = commonResultStatus;
        this.mResult = list;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<Space> getResult() {
        return this.mResult;
    }
}
