package com.samsung.android.sdk.mobileservice.social.feedback;

public class Emotion {
    public static final int EMOTION_TYPE_LIKE = 0;
    public static final int EMOTION_TYPE_NONE = -1;
    private int mCount;
    private int mEmotionType;

    public Emotion(int i, int i2) {
        this.mEmotionType = i;
        this.mCount = i2;
    }

    public int getEmotionType() {
        return this.mEmotionType;
    }

    public int getCount() {
        return this.mCount;
    }
}
