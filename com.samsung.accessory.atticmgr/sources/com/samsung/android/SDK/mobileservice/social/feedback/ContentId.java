package com.samsung.android.sdk.mobileservice.social.feedback;

import android.os.Bundle;

public abstract class ContentId {
    public static final int TYPE_ACTIVITY = 1;
    private int mContentIdType = 1;

    /* access modifiers changed from: package-private */
    public abstract Bundle toBundle();

    public ContentId(int i) {
        this.mContentIdType = i;
    }

    public int getContentIdType() {
        return this.mContentIdType;
    }
}
