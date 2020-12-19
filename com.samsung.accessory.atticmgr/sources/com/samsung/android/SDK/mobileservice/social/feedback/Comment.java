package com.samsung.android.sdk.mobileservice.social.feedback;

public class Comment {
    private String mComment;
    private String mCommentId;
    private long mCreatedTime;
    private EmotionList mEmotionList;
    private UserProfile mOwnerProfile;

    public Comment(String str, String str2, UserProfile userProfile, long j, EmotionList emotionList) {
        this.mCommentId = str;
        this.mComment = str2;
        this.mOwnerProfile = userProfile;
        this.mCreatedTime = j;
        this.mEmotionList = emotionList;
    }

    public String getCommentId() {
        return this.mCommentId;
    }

    public String getComment() {
        return this.mComment;
    }

    public UserProfile getOwnerProfile() {
        return this.mOwnerProfile;
    }

    public long getCreatedTime() {
        return this.mCreatedTime;
    }

    public EmotionList getEmotionList() {
        return this.mEmotionList;
    }
}
