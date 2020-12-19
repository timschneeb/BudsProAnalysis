package com.samsung.android.sdk.mobileservice.social.group.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.group.Group;
import java.util.List;

public class GroupInvitationResult implements Result {
    private String mDisplayMessage;
    private List<ExcludedMember> mExcludedMembers;
    private Group mGroup;
    private CommonResultStatus mStatus;

    public GroupInvitationResult(CommonResultStatus commonResultStatus, Group group, List<ExcludedMember> list, String str) {
        this.mStatus = commonResultStatus;
        this.mGroup = group;
        this.mExcludedMembers = list;
        this.mDisplayMessage = str;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public String getDisplayMessage() {
        return this.mDisplayMessage;
    }

    public Group getGroup() {
        return this.mGroup;
    }

    public List<ExcludedMember> getExcludedMembers() {
        return this.mExcludedMembers;
    }

    public static class ExcludedMember {
        public static final String REASON_ALREADY_INVITED_USER = "reason_already_invited_user";
        public static final String REASON_ALREADY_JOINED = "reason_already_joined";
        public static final String REASON_ALREADY_JOINED_ANOTHER_UNIQUE_GROUP = "reason_already_joined_another_unique_group";
        public static final String REASON_UNREGISTERED_USER_FOR_SAMSUNG_ACCOUNT = "reason_unregistered_user_for_samsung_account";
        public static final String REASON_UNREGISTERED_USER_FOR_SAMSUNG_CLOUD = "reason_unregistered_user_for_samsung_cloud";
        public static final String REASON_USER_USING_OLD_APP_VERSION = "reason_user_using_old_app_version";
        private String mId;
        private String mOptionalId;
        private String mReason;

        public ExcludedMember(String str, String str2, String str3) {
            this.mId = str;
            this.mOptionalId = str2;
            this.mReason = str3;
        }

        public String getId() {
            return this.mId;
        }

        public String getOptionalId() {
            return this.mOptionalId;
        }

        public String getReason() {
            return this.mReason;
        }
    }
}
