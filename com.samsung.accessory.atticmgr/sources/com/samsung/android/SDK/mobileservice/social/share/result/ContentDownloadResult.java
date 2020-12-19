package com.samsung.android.sdk.mobileservice.social.share.result;

import android.net.Uri;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import java.util.ArrayList;
import java.util.List;

public class ContentDownloadResult implements Result {
    private ArrayList<DownloadedContent> mFailureList;
    private CommonResultStatus mStatus;
    private ArrayList<DownloadedContent> mSuccessList;

    public ContentDownloadResult(CommonResultStatus commonResultStatus, ArrayList<DownloadedContent> arrayList, ArrayList<DownloadedContent> arrayList2) {
        this.mStatus = commonResultStatus;
        this.mSuccessList = arrayList;
        this.mFailureList = arrayList2;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<DownloadedContent> getSuccessList() {
        return this.mSuccessList;
    }

    public List<DownloadedContent> getFailedList() {
        return this.mFailureList;
    }

    public static class DownloadedContent {
        private long mFileSize;
        private Uri mFileUri;
        private String mHash;
        private String mItemId;
        private String mMimeType;
        private String mSpaceId;

        public DownloadedContent(String str, String str2, Uri uri, String str3, long j) {
            this.mSpaceId = str;
            this.mItemId = str2;
            this.mFileUri = uri;
            this.mMimeType = str3;
            this.mFileSize = j;
        }

        public DownloadedContent(String str, String str2, String str3, Uri uri, String str4, long j) {
            this.mSpaceId = str;
            this.mItemId = str2;
            this.mFileUri = uri;
            this.mMimeType = str4;
            this.mHash = str3;
            this.mFileSize = j;
        }

        public String getSpaceId() {
            return this.mSpaceId;
        }

        public String getItemId() {
            return this.mItemId;
        }

        public Uri getFileUri() {
            return this.mFileUri;
        }

        public String getMimeType() {
            return this.mMimeType;
        }

        public String getHash() {
            return this.mHash;
        }

        public long getFileSize() {
            return this.mFileSize;
        }
    }
}
