package com.samsung.android.sdk.mobileservice.social.group.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.group.Group;

public class GroupResult implements Result {
    private Group mGroup;
    private CommonResultStatus mStatus;

    public GroupResult(CommonResultStatus commonResultStatus, Group group) {
        this.mStatus = commonResultStatus;
        this.mGroup = group;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public Group getResult() {
        return this.mGroup;
    }
}
