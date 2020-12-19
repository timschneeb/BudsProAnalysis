package com.samsung.android.sdk.mobileservice.social.share.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import java.util.List;

public class ItemListResult implements Result {
    private List<SharedItemListFailureResult> mFailureList;
    private CommonResultStatus mStatus;
    private List<SharedItemListSuccessResult> mSuccessList;

    public ItemListResult(CommonResultStatus commonResultStatus, List<SharedItemListSuccessResult> list, List<SharedItemListFailureResult> list2) {
        this.mStatus = commonResultStatus;
        this.mSuccessList = list;
        this.mFailureList = list2;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<SharedItemListSuccessResult> getSuccessList() {
        return this.mSuccessList;
    }

    public List<SharedItemListFailureResult> getFailureList() {
        return this.mFailureList;
    }

    public static class SharedItemListSuccessResult {
        private String itemId;
        private String spaceId;

        public SharedItemListSuccessResult(String str, String str2) {
            this.spaceId = str;
            this.itemId = str2;
        }

        public String getSpaceId() {
            return this.spaceId;
        }

        public String getItemId() {
            return this.itemId;
        }
    }

    public static class SharedItemListFailureResult {
        private Long errorCode;
        private String itemId;
        private String spaceId;

        public SharedItemListFailureResult(String str, String str2, Long l) {
            this.spaceId = str;
            this.itemId = str2;
            this.errorCode = l;
        }

        public String getSpaceId() {
            return this.spaceId;
        }

        public String getItemId() {
            return this.itemId;
        }

        public Long getErrorCode() {
            return this.errorCode;
        }
    }
}
