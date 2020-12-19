package com.samsung.android.sdk.mobileservice.social.share;

public class ShareSnapshot {
    private long mCurrentFileBytes;
    private long mCurrentFileBytesTransferred;
    private int mCurrentFileIndex;
    private long mTotalBytes;
    private long mTotalBytesTransferred;
    private int mTotalFileCount;
    private int mTotalFileCountTransferred;

    ShareSnapshot(long j, long j2, int i, int i2, long j3, long j4, int i3) {
        this.mTotalBytes = j;
        this.mTotalBytesTransferred = j2;
        this.mTotalFileCount = i;
        this.mTotalFileCountTransferred = i2;
        this.mCurrentFileBytes = j3;
        this.mCurrentFileBytesTransferred = j4;
        this.mCurrentFileIndex = i3;
    }

    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    public long getTotalBytesTransferred() {
        return this.mTotalBytesTransferred;
    }

    public int getTotalFileCount() {
        return this.mTotalFileCount;
    }

    public int getTotalFileCountTransferred() {
        return this.mTotalFileCountTransferred;
    }

    public long getCurrentFileBytes() {
        return this.mCurrentFileBytes;
    }

    public long getCurrentFileBytesTransferred() {
        return this.mCurrentFileBytesTransferred;
    }

    public int getCurrentFileIndex() {
        return this.mCurrentFileIndex;
    }
}
