package com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses;

public class FeedBackType {
    long categoryId;
    String categoryType;
    String mainType;
    String subType;

    public FeedBackType(String str, String str2, long j) {
        this.mainType = str;
        this.subType = str2;
        this.categoryId = j;
    }

    public long getCategoryId() {
        return this.categoryId;
    }

    public String getCategoryType() {
        return this.categoryType;
    }

    public String getMainType() {
        return this.mainType;
    }

    public String getSubType() {
        return this.subType;
    }
}
