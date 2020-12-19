package com.samsung.android.sdk.mobileservice.social.share.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.share.ShareApi;
import com.samsung.android.sdk.mobileservice.social.share.SharedItemWithUriList;
import java.util.List;

public class SharedItemListWithUriListResult implements Result {
    private List<ShareApi.SharedItemWithUriListRequest> mFailureList;
    private CommonResultStatus mStatus;
    private List<SharedItemWithUriList> mSuccessList;

    public SharedItemListWithUriListResult(CommonResultStatus commonResultStatus, List<SharedItemWithUriList> list, List<ShareApi.SharedItemWithUriListRequest> list2) {
        this.mStatus = commonResultStatus;
        this.mSuccessList = list;
        this.mFailureList = list2;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<SharedItemWithUriList> getSuccessList() {
        return this.mSuccessList;
    }

    public List<ShareApi.SharedItemWithUriListRequest> getFailureList() {
        return this.mFailureList;
    }
}
