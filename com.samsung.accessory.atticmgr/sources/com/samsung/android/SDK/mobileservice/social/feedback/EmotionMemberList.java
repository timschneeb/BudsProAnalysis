package com.samsung.android.sdk.mobileservice.social.feedback;

import com.samsung.android.sdk.mobileservice.social.feedback.ContentId;
import java.util.List;

public class EmotionMemberList<T extends ContentId> {
    private String mCommentId;
    private T mContentId;
    private List<EmotionMember> mEmotionMemberList;
    private String mNextMemberGuid;

    public EmotionMemberList(T t, String str, String str2, List<EmotionMember> list) {
        this.mContentId = t;
        this.mCommentId = str;
        this.mNextMemberGuid = str2;
        this.mEmotionMemberList = list;
    }

    public T getContentId() {
        return this.mContentId;
    }

    public String getNextMemberGuid() {
        return this.mNextMemberGuid;
    }

    public String getCommentId() {
        return this.mCommentId;
    }

    public List<EmotionMember> getEmotionMemberList() {
        return this.mEmotionMemberList;
    }

    public static class EmotionMember {
        private int mEmotionType;
        private long mUpdateTime;
        private UserProfile mUserProfile;

        public EmotionMember(UserProfile userProfile, long j, int i) {
            this.mUserProfile = userProfile;
            this.mUpdateTime = j;
            this.mEmotionType = i;
        }

        public UserProfile getUserProfile() {
            return this.mUserProfile;
        }

        public long getUpdateTime() {
            return this.mUpdateTime;
        }

        public int getEmotionType() {
            return this.mEmotionType;
        }
    }
}
