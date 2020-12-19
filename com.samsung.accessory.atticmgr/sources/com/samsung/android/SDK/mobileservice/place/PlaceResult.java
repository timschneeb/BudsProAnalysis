package com.samsung.android.sdk.mobileservice.place;

import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class PlaceResult<T> implements Result {
    private T mResult;
    private CommonResultStatus mStatus;

    PlaceResult(CommonResultStatus commonResultStatus, T t) {
        this.mStatus = commonResultStatus;
        this.mResult = t;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public T getResult() {
        return this.mResult;
    }
}
