package com.samsung.android.sdk.mobileservice.auth;

import java.util.HashMap;

public class DisclaimerAgreementInfo {
    public static final String ACCOUNT = "agreement_samsung_account";
    public static final String SOCIAL = "agreement_social_service";
    private HashMap<String, Boolean> mInfo = new HashMap<>();

    public boolean getValue(String str, boolean z) {
        return this.mInfo.containsKey(str) ? this.mInfo.get(str).booleanValue() : z;
    }

    public void add(String str, boolean z) {
        this.mInfo.put(str, Boolean.valueOf(z));
    }

    public boolean contain(String str) {
        return this.mInfo.containsKey(str);
    }
}
