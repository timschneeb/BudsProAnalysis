package com.samsung.android.sdk.mobileservice.social.feedback;

import java.util.List;

public class CommentList {
    private List<Comment> mComments;
    private String mNextCommentId;

    public CommentList(String str, List<Comment> list) {
        this.mNextCommentId = str;
        this.mComments = list;
    }

    public String getNextCommentId() {
        return this.mNextCommentId;
    }

    public List<Comment> getComments() {
        return this.mComments;
    }
}
