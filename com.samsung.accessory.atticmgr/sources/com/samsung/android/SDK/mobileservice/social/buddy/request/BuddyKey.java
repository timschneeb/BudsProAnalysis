package com.samsung.android.sdk.mobileservice.social.buddy.request;

import android.text.TextUtils;

public abstract class BuddyKey {
    private String mKeyword;

    public abstract int getType();

    BuddyKey(String str) {
        this.mKeyword = str;
    }

    /* access modifiers changed from: package-private */
    public String getKeyword() {
        return TextUtils.isEmpty(this.mKeyword) ? "" : this.mKeyword;
    }
}
