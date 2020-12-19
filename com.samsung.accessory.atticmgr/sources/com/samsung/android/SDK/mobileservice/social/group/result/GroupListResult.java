package com.samsung.android.sdk.mobileservice.social.group.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.group.Group;
import java.util.List;

public class GroupListResult implements Result {
    private List<Group> mGroups;
    private CommonResultStatus mStatus;

    public GroupListResult(CommonResultStatus commonResultStatus, List<Group> list) {
        this.mStatus = commonResultStatus;
        this.mGroups = list;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<Group> getResult() {
        return this.mGroups;
    }
}
