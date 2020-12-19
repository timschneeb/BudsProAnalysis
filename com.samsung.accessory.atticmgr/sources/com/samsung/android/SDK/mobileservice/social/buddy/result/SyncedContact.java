package com.samsung.android.sdk.mobileservice.social.buddy.result;

public class SyncedContact {
    private final String mRawContactId;

    public SyncedContact(String str) {
        this.mRawContactId = str;
    }

    public String getRawContactId() {
        return this.mRawContactId;
    }
}
