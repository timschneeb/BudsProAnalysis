package com.samsung.android.sdk.bixby2.util;

import android.os.Bundle;
import com.samsung.android.sdk.bixby2.LogUtil;

public class BixbyContextInfo {
    private static final String TAG = "BixbyContextInfo";
    private final String BIXBY_CLIENT_TASK_ID;
    private final String IS_MEDIA_CONTROL_ACTIVE;
    private final String IS_MUSIC_ACTIVE;
    private final String LOCALE;
    private Integer bixbyClientTaskId;
    private boolean isMediaControlActive;
    private boolean isMusicActive;
    private String locale;

    BixbyContextInfo(Bundle bundle) {
        this.LOCALE = "locale";
        this.IS_MUSIC_ACTIVE = "isMusicActive";
        this.IS_MEDIA_CONTROL_ACTIVE = "isMediaControlActive";
        this.BIXBY_CLIENT_TASK_ID = "bixbyClient_taskId";
        this.locale = bundle.getString("locale", "");
        this.isMusicActive = bundle.getBoolean("isMusicActive", false);
        this.isMediaControlActive = bundle.getBoolean("isMediaControlActive", false);
        if (bundle.containsKey("bixbyClient_taskId")) {
            this.bixbyClientTaskId = Integer.valueOf(bundle.getInt("bixbyClient_taskId"));
        }
        LogUtil.d(TAG, "BixbyContextInfo() :: locale - " + this.locale + ", isMusicActive - " + this.isMusicActive + ", isMediaControlActive - " + this.isMediaControlActive);
    }

    BixbyContextInfo() {
        this.LOCALE = "locale";
        this.IS_MUSIC_ACTIVE = "isMusicActive";
        this.IS_MEDIA_CONTROL_ACTIVE = "isMediaControlActive";
        this.BIXBY_CLIENT_TASK_ID = "bixbyClient_taskId";
        this.locale = "";
        this.isMusicActive = false;
        this.isMediaControlActive = false;
    }

    public String getLocale() {
        return this.locale;
    }

    public boolean isMusicActive() {
        return this.isMusicActive;
    }

    public boolean isMediaControlActive() {
        return this.isMediaControlActive;
    }

    public Integer getBixbyClientTaskId() {
        return this.bixbyClientTaskId;
    }
}
