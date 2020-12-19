package com.samsung.android.sdk.mobileservice.social.activity;

import android.net.Uri;

public class ContentInfo {
    private Uri mContentUri;
    private String mHash;
    private String mMime;
    private String mName;
    private long mSize;

    public ContentInfo(String str, String str2, long j, String str3, Uri uri) {
        this.mName = str;
        this.mHash = str2;
        this.mSize = j;
        this.mMime = str3;
        this.mContentUri = uri;
    }

    public String getName() {
        return this.mName;
    }

    public String getHash() {
        return this.mHash;
    }

    public long getSize() {
        return this.mSize;
    }

    public String getMime() {
        return this.mMime;
    }

    public Uri getContentUri() {
        return this.mContentUri;
    }
}
