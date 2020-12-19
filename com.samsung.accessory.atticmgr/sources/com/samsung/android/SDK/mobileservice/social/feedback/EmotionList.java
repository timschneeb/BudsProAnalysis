package com.samsung.android.sdk.mobileservice.social.feedback;

import java.util.List;

public class EmotionList {
    private List<Emotion> mEmotions;
    private int mMyEmotionType;

    public EmotionList(int i, List<Emotion> list) {
        this.mMyEmotionType = i;
        this.mEmotions = list;
    }

    public int getMyEmotionType() {
        return this.mMyEmotionType;
    }

    public List<Emotion> getEmotions() {
        return this.mEmotions;
    }
}
