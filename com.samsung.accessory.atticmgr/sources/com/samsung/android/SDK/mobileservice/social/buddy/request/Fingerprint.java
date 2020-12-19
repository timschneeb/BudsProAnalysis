package com.samsung.android.sdk.mobileservice.social.buddy.request;

public class Fingerprint extends BuddyKey {
    private static final int FINGERPRINT = 1;

    @Override // com.samsung.android.sdk.mobileservice.social.buddy.request.BuddyKey
    public int getType() {
        return 1;
    }

    public Fingerprint(String str) {
        super(str);
    }
}
