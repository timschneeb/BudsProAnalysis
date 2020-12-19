package com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses;

public class AnswerList {
    String answer;
    long answerDateTime;
    AnswerFileList[] answerFileList;
    long answerId;

    public String getAnswer() {
        return this.answer;
    }

    public long getAnswerDateTime() {
        return this.answerDateTime;
    }

    public AnswerFileList[] getAnswerFileList() {
        return this.answerFileList;
    }

    public long getAnswerId() {
        return this.answerId;
    }
}
