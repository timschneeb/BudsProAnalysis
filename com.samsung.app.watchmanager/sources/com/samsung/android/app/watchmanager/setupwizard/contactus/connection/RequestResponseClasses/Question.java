package com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses;

public class Question {
    String body;
    String frequency;
    String title;

    public Question(String str, String... strArr) {
        this.body = str;
        if (strArr.length > 0) {
            this.title = strArr[0];
        }
        if (strArr.length > 1) {
            this.frequency = strArr[1];
        }
    }

    public String getBody() {
        return this.body;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public String getTitle() {
        return this.title;
    }
}
