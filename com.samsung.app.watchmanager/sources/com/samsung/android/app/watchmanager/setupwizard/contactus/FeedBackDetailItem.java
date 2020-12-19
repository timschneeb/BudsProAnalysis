package com.samsung.android.app.watchmanager.setupwizard.contactus;

import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses.AnswerList;

public class FeedBackDetailItem {
    AnswerList answerList;
    String categoryText;
    String dateText;
    long feedbackId;
    String questionText;

    public FeedBackDetailItem(String str, String str2, String str3, AnswerList answerList2, long j) {
        this.categoryText = str;
        this.dateText = str2;
        this.questionText = str3;
        this.answerList = answerList2;
        this.feedbackId = j;
    }
}
