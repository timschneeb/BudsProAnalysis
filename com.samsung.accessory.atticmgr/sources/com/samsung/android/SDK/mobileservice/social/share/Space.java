package com.samsung.android.sdk.mobileservice.social.share;

import android.net.Uri;
import java.util.Map;

public class Space {
    private long mContentUpdatedTime;
    private Uri mCoverThumbnailFileUri;
    private long mCreatedTime;
    private String mGroupId;
    private boolean mIsOwnedByMe;
    private int mItemCount;
    private String mLeaderId;
    private String mMemo;
    private Map mMetaData;
    private long mModifiedTime;
    private long mSize;
    private String mSourceCid;
    private String mSpaceId;
    private String mTitle;
    private int mUnreadCount;

    public Space(String str, String str2, String str3) {
        this.mGroupId = str;
        this.mSpaceId = str2;
        this.mLeaderId = str3;
    }

    public String getSpaceId() {
        return this.mSpaceId;
    }

    public void setSpaceId(String str) {
        this.mSpaceId = str;
    }

    public String getGroupId() {
        return this.mGroupId;
    }

    public void setGroupId(String str) {
        this.mGroupId = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public String getMemo() {
        return this.mMemo;
    }

    public void setMemo(String str) {
        this.mMemo = str;
    }

    public Uri getCoverThumbnailFileUri() {
        return this.mCoverThumbnailFileUri;
    }

    public void setCoverThumbnailFileUri(Uri uri) {
        this.mCoverThumbnailFileUri = uri;
    }

    public long getCreatedTime() {
        return this.mCreatedTime;
    }

    public void setCreatedTime(long j) {
        this.mCreatedTime = j;
    }

    public long getModifiedTime() {
        return this.mModifiedTime;
    }

    public void setModifiedTime(long j) {
        this.mModifiedTime = j;
    }

    public String getLeaderId() {
        return this.mLeaderId;
    }

    public void setLeaderId(String str) {
        this.mLeaderId = str;
    }

    public String getSourceCid() {
        return this.mSourceCid;
    }

    public void setSourceCid(String str) {
        this.mSourceCid = str;
    }

    public int getUnreadCount() {
        return this.mUnreadCount;
    }

    public void setUnreadCount(int i) {
        this.mUnreadCount = i;
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    public void setItemCount(int i) {
        this.mItemCount = i;
    }

    public boolean isOwnedByMe() {
        return this.mIsOwnedByMe;
    }

    public void setOwnedByMe(boolean z) {
        this.mIsOwnedByMe = z;
    }

    public Map getMetaData() {
        return this.mMetaData;
    }

    public void setMetaData(Map map) {
        this.mMetaData = map;
    }

    public long getSize() {
        return this.mSize;
    }

    public void setSize(long j) {
        this.mSize = j;
    }

    public long getContentUpdatedTime() {
        return this.mContentUpdatedTime;
    }

    public void setContentUpdatedTime(long j) {
        this.mContentUpdatedTime = j;
    }
}
