package com.samsung.context.sdk.samsunganalytics.internal.connection;

import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;

/* JADX INFO: Failed to restore enum class, 'enum' modifier removed */
public final class Domain extends Enum<Domain> {
    private static final /* synthetic */ Domain[] $VALUES = {REGISTRATION, POLICY, DLS};
    public static final Domain DLS = new Domain("DLS", 2, "");
    public static final Domain POLICY;
    public static final Domain REGISTRATION;
    String domain;

    public static Domain valueOf(String str) {
        return (Domain) Enum.valueOf(Domain.class, str);
    }

    public static Domain[] values() {
        return (Domain[]) $VALUES.clone();
    }

    static {
        String str = "https://stg-api.di.atlas.samsung.com";
        REGISTRATION = new Domain("REGISTRATION", 0, Utils.isEngBin() ? str : "https://regi.di.atlas.samsung.com");
        if (!Utils.isEngBin()) {
            str = "https://dc.di.atlas.samsung.com";
        }
        POLICY = new Domain("POLICY", 1, str);
    }

    private Domain(String str, int i, String str2) {
        this.domain = str2;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String str) {
        this.domain = str;
    }
}
