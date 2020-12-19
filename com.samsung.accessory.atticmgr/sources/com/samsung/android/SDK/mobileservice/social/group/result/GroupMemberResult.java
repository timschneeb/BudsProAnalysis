package com.samsung.android.sdk.mobileservice.social.group.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.group.GroupMember;
import java.util.List;

public class GroupMemberResult implements Result {
    private List<GroupMember> mGroupMembers;
    private CommonResultStatus mStatus;
    private int mTotalCountWithInvitation;

    public GroupMemberResult(CommonResultStatus commonResultStatus, List<GroupMember> list) {
        this.mStatus = commonResultStatus;
        this.mGroupMembers = list;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<GroupMember> getGroupMembers() {
        return this.mGroupMembers;
    }

    public int getTotalCountWithInvitation() {
        return this.mTotalCountWithInvitation;
    }

    public void setTotalCountWithInvitation(int i) {
        this.mTotalCountWithInvitation = i;
    }
}
