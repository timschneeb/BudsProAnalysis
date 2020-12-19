package com.samsung.android.sdk.mobileservice.social.group;

import android.net.Uri;

public class GroupMember {
    public static final int ID_TYPE_ACCOUNT_ID = 3;
    public static final int ID_TYPE_DUID = 2;
    public static final int ID_TYPE_GUID = 0;
    public static final int ID_TYPE_MSISDN = 1;
    private String mId;
    private boolean mIsLeader;
    private String mName;
    private String mOptionalId;
    private int mOptionalIdType;
    private int mStatus;
    private Uri mThumbnailFileUri;

    public GroupMember(int i, String str, String str2, int i2, String str3, boolean z, Uri uri) {
        this.mOptionalIdType = i;
        this.mId = str;
        this.mOptionalId = str2;
        this.mStatus = i2;
        this.mName = str3;
        this.mIsLeader = z;
        this.mThumbnailFileUri = uri;
    }

    public int getOptionalIdType() {
        return this.mOptionalIdType;
    }

    public String getId() {
        return this.mId;
    }

    public String getOptionalId() {
        return this.mOptionalId;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String getName() {
        return this.mName;
    }

    public boolean isLeader() {
        return this.mIsLeader;
    }

    public Uri getThumbnailFileUri() {
        return this.mThumbnailFileUri;
    }
}
