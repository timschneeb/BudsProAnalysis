package com.samsung.android.sdk.mobileservice.social.activity;

import android.net.Uri;

public class ActivityImage {
    private String mActivityId;
    private Uri mActivityImageContentUri;
    private String mGuid;
    private Uri mProfileImageContentUri;

    public ActivityImage(String str, String str2, Uri uri, Uri uri2) {
        this.mGuid = str;
        this.mActivityId = str2;
        this.mActivityImageContentUri = uri2;
        this.mProfileImageContentUri = uri;
    }

    public Uri getActivityImageUri() {
        return this.mActivityImageContentUri;
    }

    public Uri getProfileImageUri() {
        return this.mProfileImageContentUri;
    }

    public String getGuid() {
        return this.mGuid;
    }

    public String getActivityId() {
        return this.mActivityId;
    }
}
