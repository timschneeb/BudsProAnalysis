package com.samsung.android.sdk.mobileservice.social.feedback;

import com.samsung.android.sdk.mobileservice.social.feedback.ContentId;

public class Notification<T extends ContentId> {
    public static int FEEDBACK_TYPE_COMMENT = 1;
    public static int FEEDBACK_TYPE_EMOTION = 2;
    private String mComment;
    private String mCommentId;
    private T mContentId;
    private int mEmotionType;
    private int mFeedbackType;
    private String mNotificationId;
    private UserProfile mSenderProfile;
    private long mTimestamp;

    public Notification(T t, String str, int i, UserProfile userProfile, String str2, String str3, int i2, long j) {
        this.mContentId = t;
        this.mNotificationId = str;
        this.mFeedbackType = i;
        this.mSenderProfile = userProfile;
        this.mCommentId = str2;
        this.mComment = str3;
        this.mEmotionType = i2;
        this.mTimestamp = j;
    }

    public T getContentId() {
        return this.mContentId;
    }

    public String getNotificationId() {
        return this.mNotificationId;
    }

    public int getFeedbackType() {
        return this.mFeedbackType;
    }

    public UserProfile getSenderProfile() {
        return this.mSenderProfile;
    }

    public String getCommentId() {
        return this.mCommentId;
    }

    public String getComment() {
        return this.mComment;
    }

    public int getEmotionType() {
        return this.mEmotionType;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }
}
