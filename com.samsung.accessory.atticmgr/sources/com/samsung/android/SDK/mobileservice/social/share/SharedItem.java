package com.samsung.android.sdk.mobileservice.social.share;

import android.net.Uri;
import java.util.Map;

public class SharedItem {
    private long mCreatedTime;
    private boolean mIsOwnedByMe;
    private String mItemId;
    private String mLeaderId;
    private String mMemo;
    private Map mMetaData;
    private String mMimeType;
    private long mModifiedTime;
    private String mOriginalContentPath;
    private long mSize;
    private String mSourceCid;
    private String mSpaceId;
    private String mStreamingUrl;
    private Uri mThumbnailFileUri;
    private String mTitle;

    public SharedItem(String str, String str2, String str3) {
        setItemId(str);
        setSpaceId(str2);
        setLeaderId(str3);
    }

    public String getItemId() {
        return this.mItemId;
    }

    public void setItemId(String str) {
        this.mItemId = str;
    }

    public String getSpaceId() {
        return this.mSpaceId;
    }

    public void setSpaceId(String str) {
        this.mSpaceId = str;
    }

    public String getLeaderId() {
        return this.mLeaderId;
    }

    public void setLeaderId(String str) {
        this.mLeaderId = str;
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

    public String getMimeType() {
        return this.mMimeType;
    }

    public void setMimeType(String str) {
        this.mMimeType = str;
    }

    public Uri getThumbnailFileUri() {
        return this.mThumbnailFileUri;
    }

    public void setThumbnailFileUri(Uri uri) {
        this.mThumbnailFileUri = uri;
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

    public String getOriginalContentPath() {
        return this.mOriginalContentPath;
    }

    public void setOriginalContentPath(String str) {
        this.mOriginalContentPath = str;
    }

    public String getStreamingUrl() {
        return this.mStreamingUrl;
    }

    public void setStreamingUrl(String str) {
        this.mStreamingUrl = str;
    }

    public String getSourceCid() {
        return this.mSourceCid;
    }

    public void setSourceCid(String str) {
        this.mSourceCid = str;
    }
}
