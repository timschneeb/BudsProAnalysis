package com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses;

public class FeedBackList {
    AccessoryInfo accessory;
    AnswerList[] answerList;
    int betaProjectId;
    DeviceInfo device;
    long feedbackId;
    long groupId;
    long parentId;
    Question question;
    Rating rating;
    String status;
    FeedBackType type;
    boolean validation;
    long writeDateTime;

    public AccessoryInfo getAccessory() {
        return this.accessory;
    }

    public AnswerList[] getAnswerList() {
        return this.answerList;
    }

    public int getBetaProjectId() {
        return this.betaProjectId;
    }

    public DeviceInfo getDevice() {
        return this.device;
    }

    public long getFeedbackId() {
        return this.feedbackId;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public long getParentId() {
        return this.parentId;
    }

    public Question getQuestion() {
        return this.question;
    }

    public Rating getRating() {
        return this.rating;
    }

    public String getStatus() {
        return this.status;
    }

    public FeedBackType getType() {
        return this.type;
    }

    public long getWriteDateTime() {
        return this.writeDateTime;
    }

    public boolean isValidation() {
        return this.validation;
    }
}
