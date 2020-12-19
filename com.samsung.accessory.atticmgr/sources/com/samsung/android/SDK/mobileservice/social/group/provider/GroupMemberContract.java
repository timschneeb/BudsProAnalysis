package com.samsung.android.sdk.mobileservice.social.group.provider;

import android.net.Uri;

public class GroupMemberContract {
    public static final String AUTHORITY = "com.samsung.android.mobileservice.social.group.member";
    private static final String AUTHORITY_URI = "content://com.samsung.android.mobileservice.social.group.member";
    public static final int ID_TYPE_ACCOUNT_ID = 3;
    public static final int ID_TYPE_DUID = 2;
    public static final int ID_TYPE_GUID = 0;
    public static final int ID_TYPE_MSISDN = 1;

    private GroupMemberContract() {
    }

    public static class GroupMember {
        public static final Uri CONTENT_URI = Uri.parse("content://com.samsung.android.mobileservice.social.group.member/");
        public static final String EXPIRED_TIME = "expiredTime";
        public static final String GROUP_ID = "groupId";
        public static final String GUID = "guid";
        public static final String ID = "id";
        public static final String IMAGE_URL = "imageUrl";
        public static final String INVITATION_TYPE = "invitationType";
        public static final String INVITE_TYPE = "inviteType";
        public static final String MSISDN = "msisdn";
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String THUMBNAIL_LOCAL_PATH = "thumbnailLocalPath";
        public static final String UPDATED_TIME = "updatedTime";

        private GroupMember() {
        }

        public static class Status {
            public static final int NORMAL = 2;
            public static final int PENDING = 1;

            private Status() {
            }
        }
    }
}
