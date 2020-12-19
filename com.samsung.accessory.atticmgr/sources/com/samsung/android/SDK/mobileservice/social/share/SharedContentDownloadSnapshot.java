package com.samsung.android.sdk.mobileservice.social.share;

public class SharedContentDownloadSnapshot {
    private long mCurrentFileBytes;
    private long mCurrentFileBytesReceived;
    private int mCurrentFileIndex;
    private long mTotalBytes;
    private long mTotalBytesReceived;
    private int mTotalFileCount;
    private int mTotalFileCountReceived;

    SharedContentDownloadSnapshot(long j, long j2, int i, int i2, long j3, long j4, int i3) {
        this.mTotalBytes = j;
        this.mTotalBytesReceived = j2;
        this.mTotalFileCount = i;
        this.mTotalFileCountReceived = i2;
        this.mCurrentFileBytes = j3;
        this.mCurrentFileBytesReceived = j4;
        this.mCurrentFileIndex = i3;
    }

    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    public long getTotalBytesReceived() {
        return this.mTotalBytesReceived;
    }

    public int getTotalFileCount() {
        return this.mTotalFileCount;
    }

    public int getTotalFileCountReceived() {
        return this.mTotalFileCountReceived;
    }

    public long getCurrentFileBytes() {
        return this.mCurrentFileBytes;
    }

    public long getCurrentFileBytesReceived() {
        return this.mCurrentFileBytesReceived;
    }

    public int getCurrentFileIndex() {
        return this.mCurrentFileIndex;
    }
}
