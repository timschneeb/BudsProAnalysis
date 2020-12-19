package com.samsung.android.sdk.mobileservice.social.group.result;

import android.net.Uri;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class GroupImageDownloadResult implements Result {
    private DownloadedImage mDownloadedImage;
    private CommonResultStatus mStatus;

    public GroupImageDownloadResult(CommonResultStatus commonResultStatus, DownloadedImage downloadedImage) {
        this.mStatus = commonResultStatus;
        this.mDownloadedImage = downloadedImage;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public DownloadedImage getResult() {
        return this.mDownloadedImage;
    }

    public static class DownloadedImage {
        private Uri mFileUri;
        private String mId;

        public DownloadedImage(String str, Uri uri) {
            this.mId = str;
            this.mFileUri = uri;
        }

        public String getId() {
            return this.mId;
        }

        public Uri getFileUri() {
            return this.mFileUri;
        }
    }
}
