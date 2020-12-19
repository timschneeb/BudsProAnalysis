package com.samsung.android.sdk.mobileservice.social.activity;

import android.net.Uri;
import java.util.List;

public class Activity {
    public static final int ACTIVITY_TYPE_POSTING = 4;
    public static final int ACTIVITY_TYPE_PROFILE_CHANGE = 3;
    public static final int ACTIVITY_TYPE_PROFILE_IMAGE = 2;
    public static final int ACTIVITY_TYPE_STATUS_MESSAGE = 1;
    public static final int READ_TYPE_READ = 1;
    public static final int READ_TYPE_UNREAD = 2;
    private String mActivityId;
    private Uri mActivityImageContentUri;
    private List<ContentInfo> mContentInfoList;
    private long mCreatedTime;
    private String mMemo;
    private String mMeta;
    private long mModifiedTime;
    private String mOwnerGuid;
    private String mOwnerName;
    private Uri mProfileImageContentUri;
    private String mStatusMessage;
    private int mType;

    public Activity(String str, String str2, int i, long j, String str3, String str4, Uri uri, Uri uri2) {
        this.mActivityId = str;
        this.mStatusMessage = str2;
        this.mType = i;
        this.mCreatedTime = j;
        this.mOwnerGuid = str3;
        this.mOwnerName = str4;
        this.mActivityImageContentUri = uri;
        this.mProfileImageContentUri = uri2;
    }

    public Activity(String str, String str2, int i, long j, long j2, String str3, String str4, Uri uri, Uri uri2, String str5, List<ContentInfo> list) {
        this.mActivityId = str;
        this.mMemo = str2;
        this.mType = i;
        this.mCreatedTime = j;
        this.mModifiedTime = j2;
        this.mOwnerGuid = str3;
        this.mOwnerName = str4;
        this.mActivityImageContentUri = uri;
        this.mProfileImageContentUri = uri2;
        this.mMeta = str5;
        this.mContentInfoList = list;
    }

    public String getActivityId() {
        return this.mActivityId;
    }

    public String getStatusMessage() {
        return this.mStatusMessage;
    }

    public String getMemo() {
        return this.mMemo;
    }

    public int getType() {
        return this.mType;
    }

    public long getCreatedTime() {
        return this.mCreatedTime;
    }

    public long getModifiedTime() {
        return this.mModifiedTime;
    }

    public String getOwnerGuid() {
        return this.mOwnerGuid;
    }

    public String getOwnerName() {
        return this.mOwnerName;
    }

    public Uri getActivityImageContentUri() {
        return this.mActivityImageContentUri;
    }

    public Uri getProfileImageContentUri() {
        return this.mProfileImageContentUri;
    }

    public String getMeta() {
        return this.mMeta;
    }

    public List<ContentInfo> getContentInfoList() {
        return this.mContentInfoList;
    }
}
