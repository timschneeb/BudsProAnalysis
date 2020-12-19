package com.samsung.android.sdk.mobileservice.social.feedback;

import com.samsung.android.sdk.mobileservice.social.feedback.ContentId;

public class Feedback<T extends ContentId> {
    private CommentList mCommentList;
    private T mContentId;
    private EmotionList mEmotionList;
    private int mTotalCommentCount;

    public Feedback(T t, EmotionList emotionList, int i, CommentList commentList) {
        this.mContentId = t;
        this.mEmotionList = emotionList;
        this.mTotalCommentCount = i;
        this.mCommentList = commentList;
    }

    public T getContentId() {
        return this.mContentId;
    }

    public EmotionList getEmotionList() {
        return this.mEmotionList;
    }

    public int getTotalCommentCount() {
        return this.mTotalCommentCount;
    }

    public CommentList getCommentList() {
        return this.mCommentList;
    }
}
