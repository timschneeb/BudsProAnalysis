package com.samsung.android.sdk.mobileservice.social.share.result;

import android.net.Uri;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class DownloadImageResult implements Result {
    private DownloadedImage mImage;
    private CommonResultStatus mStatus;

    public DownloadImageResult(CommonResultStatus commonResultStatus, DownloadedImage downloadedImage) {
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
        private String mHash;
        private Uri mImageUri;
        private String mItemId;

        public DownloadedImage(String str, String str2, Uri uri) {
            this.mItemId = str;
            this.mHash = str2;
            this.mImageUri = uri;
        }

        public String getItemId() {
            return this.mItemId;
        }

        public String getHash() {
            return this.mHash;
        }

        public Uri getImageUri() {
            return this.mImageUri;
        }
    }
}
