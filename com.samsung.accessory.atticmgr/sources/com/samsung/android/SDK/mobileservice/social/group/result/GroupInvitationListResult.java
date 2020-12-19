package com.samsung.android.sdk.mobileservice.social.group.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import java.util.List;

public class GroupInvitationListResult implements Result {
    private List<GroupInvitation> mInvitations;
    private CommonResultStatus mStatus;

    public GroupInvitationListResult(CommonResultStatus commonResultStatus, List<GroupInvitation> list) {
        this.mStatus = commonResultStatus;
        this.mInvitations = list;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<GroupInvitation> getInvitationList() {
        return this.mInvitations;
    }

    public static class GroupInvitation {
        private long mExpiredTime;
        private String mGroupCoverThumbnailUrl;
        private String mGroupId;
        private String mGroupName;
        private String mMessage;
        private long mRequestedTime;
        private String mRequesterId;
        private String mRequesterImageUrl;
        private String mRequesterName;

        public GroupInvitation(String str, String str2, String str3, String str4, String str5, String str6, String str7, long j, long j2) {
            this.mGroupId = str;
            this.mGroupName = str2;
            this.mGroupCoverThumbnailUrl = str3;
            this.mMessage = str4;
            this.mRequesterId = str5;
            this.mRequesterName = str6;
            this.mRequesterImageUrl = str7;
            this.mRequestedTime = j;
            this.mExpiredTime = j2;
        }

        public String getGroupId() {
            return this.mGroupId;
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public String getGroupCoverThumbnailUrl() {
            return this.mGroupCoverThumbnailUrl;
        }

        public String getMessage() {
            return this.mMessage;
        }

        public String getRequesterId() {
            return this.mRequesterId;
        }

        public String getRequesterName() {
            return this.mRequesterName;
        }

        public String getRequesterImageUrl() {
            return this.mRequesterImageUrl;
        }

        public long getRequestedTime() {
            return this.mRequestedTime;
        }

        public long getExpiredTime() {
            return this.mExpiredTime;
        }
    }
}
