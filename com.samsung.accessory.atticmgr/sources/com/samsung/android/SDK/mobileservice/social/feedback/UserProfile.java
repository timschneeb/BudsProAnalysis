package com.samsung.android.sdk.mobileservice.social.feedback;

import android.net.Uri;

public class UserProfile {
    private String mGuid;
    private Uri mImageContentUri;
    private String mName;

    public UserProfile(String str, String str2, Uri uri) {
        this.mGuid = str;
        this.mName = str2;
        this.mImageContentUri = uri;
    }

    public UserProfile(String str, Uri uri) {
        this.mGuid = str;
        this.mImageContentUri = uri;
    }

    public String getGuid() {
        return this.mGuid;
    }

    public String getName() {
        return this.mName;
    }

    public Uri getImageContentUri() {
        return this.mImageContentUri;
    }
}
