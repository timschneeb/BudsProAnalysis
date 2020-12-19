package com.samsung.android.sdk.mobileservice.social.share.result;

import android.net.Uri;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class SpaceImageDownloadResult implements Result {
    private DownloadedImage mImage;
    private CommonResultStatus mStatus;

    public SpaceImageDownloadResult(CommonResultStatus commonResultStatus, DownloadedImage downloadedImage) {
        this.mStatus = commonResultStatus;
        this.mImage = downloadedImage;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public DownloadedImage getResult() {
        return this.mImage;
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
