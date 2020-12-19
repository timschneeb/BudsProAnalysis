package com.samsung.android.sdk.mobileservice.social.share.result;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;
import com.samsung.android.sdk.mobileservice.social.share.SharedItemWithUriList;
import java.util.List;

public class SharedItemListWithContentListResult implements Result {
    private List<SharedItemWithUriList> mItemList;
    private CommonResultStatus mStatus;

    public SharedItemListWithContentListResult(CommonResultStatus commonResultStatus, List<SharedItemWithUriList> list) {
        this.mStatus = commonResultStatus;
        this.mItemList = list;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public List<SharedItemWithUriList> getItemsList() {
        return this.mItemList;
    }
}
