package com.samsung.android.sdk.mobileservice.social;

import android.content.Intent;

public class SocialServiceState {
    public static final int SERVICE_END = 5;
    public static final int SERVICE_NORMAL = 1;
    public static final int SERVICE_NOTICE = 2;
    public static final int SERVICE_PAUSE = 4;
    public static final int SERVICE_REAGREE = 3;
    private Intent mIntent = null;
    private int mState = -1;

    public SocialServiceState(int i) {
        this.mState = i;
    }

    public SocialServiceState(int i, Intent intent) {
        this.mState = i;
        this.mIntent = intent;
    }

    public int getState() {
        return this.mState;
    }

    public Intent getIntent() {
        return this.mIntent;
    }
}
