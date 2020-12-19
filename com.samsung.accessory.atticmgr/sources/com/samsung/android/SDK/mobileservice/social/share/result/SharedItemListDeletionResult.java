package com.samsung.android.sdk.mobileservice.social.share.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import java.util.List;

public class SharedItemListDeletionResult implements Result {
    private List<SharedItemDeletionResult> mResultList;
    private CommonResultStatus mStatus;

    public SharedItemListDeletionResult(CommonResultStatus commonResultStatus, List<SharedItemDeletionResult> list) {
        this.mStatus = commonResultStatus;
        this.mResultList = list;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<SharedItemDeletionResult> getResultList() {
        return this.mResultList;
    }

    public static class SharedItemDeletionResult {
        private String itemId;
        private boolean result;
        private String spaceId;

        public SharedItemDeletionResult(String str, String str2, boolean z) {
            this.spaceId = str;
            this.itemId = str2;
            this.result = z;
        }

        public String getSpaceId() {
            return this.spaceId;
        }

        public String getItemId() {
            return this.itemId;
        }

        public boolean getResult() {
            return this.result;
        }
    }
}
