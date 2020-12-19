package com.samsung.android.sdk.mobileservice.social.group;

import android.net.Uri;
import java.util.Map;

public class Group {
    public static final String GROUP_TYPE_FAMILY = "FMLY";
    public static final String GROUP_TYPE_GENERAL = "GNRL";
    private int mActiveMemberCount;
    private long mContentsUpdatedTime;
    private Uri mCoverThumbnailFileUri;
    private long mCreatedTime;
    private String mGroupId;
    private String mGroupName;
    private String mGroupType;
    private String mLeaderId;
    private int mMaxMemberCount;
    private Map mMetaData;
    private long mUpdatedTime;

    public Group(String str, String str2, String str3, String str4, Uri uri, long j, int i, int i2) {
        this.mGroupId = str;
        this.mGroupName = str2;
        this.mGroupType = str3;
        this.mLeaderId = str4;
        this.mCoverThumbnailFileUri = uri;
        this.mCreatedTime = j;
        this.mMaxMemberCount = i;
        this.mActiveMemberCount = i2;
    }

    public Group(String str, String str2, String str3, String str4, Uri uri, long j, int i, int i2, long j2, Map map, long j3) {
        this.mGroupId = str;
        this.mGroupName = str2;
        this.mGroupType = str3;
        this.mLeaderId = str4;
        this.mCoverThumbnailFileUri = uri;
        this.mCreatedTime = j;
        this.mMaxMemberCount = i;
        this.mActiveMemberCount = i2;
        this.mUpdatedTime = j2;
        this.mMetaData = map;
        this.mContentsUpdatedTime = j3;
    }

    public String getGroupId() {
        return this.mGroupId;
    }

    public String getGroupName() {
        return this.mGroupName;
    }

    public String getGroupType() {
        return this.mGroupType;
    }

    public String getLeaderId() {
        return this.mLeaderId;
    }

    public Uri getCoverThumbnailFileUri() {
        return this.mCoverThumbnailFileUri;
    }

    public long getCreatedTime() {
        return this.mCreatedTime;
    }

    public int getMaxMemberCount() {
        return this.mMaxMemberCount;
    }

    public int getActiveMemberCount() {
        return this.mActiveMemberCount;
    }

    public long getUpdatedTime() {
        return this.mUpdatedTime;
    }

    public Map getMetaData() {
        return this.mMetaData;
    }

    public long getContentsUpdatedTime() {
        return this.mContentsUpdatedTime;
    }
}
