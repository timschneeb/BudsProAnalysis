package com.samsung.android.app.watchmanager.setupwizard.contactus;

public class FeedBackListItem {
    boolean answered = false;
    String category;
    String date;
    String feedbacktype;
    String title;
    boolean validation;

    public FeedBackListItem(String str, String str2, String str3, String str4, boolean z, boolean z2) {
        this.feedbacktype = str;
        this.title = str2;
        this.category = str3;
        this.date = str4;
        this.answered = z;
        this.validation = z2;
    }
}
