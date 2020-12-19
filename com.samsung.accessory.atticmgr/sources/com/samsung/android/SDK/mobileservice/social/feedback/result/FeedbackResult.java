package com.samsung.android.sdk.mobileservice.social.feedback.result;

import com.samsung.android.sdk.mobileservice.common.result.BaseResult;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;

public class FeedbackResult<T> implements BaseResult<T> {
    private T mResult;
    private CommonResultStatus mStatus;

    public FeedbackResult(CommonResultStatus commonResultStatus, T t) {
        this.mStatus = commonResultStatus;
        this.mResult = t;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.BaseResult
    public T getResult() {
        return this.mResult;
    }
}
