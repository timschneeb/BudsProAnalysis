package com.samsung.android.sdk.mobileservice.auth.result;

import com.samsung.android.sdk.mobileservice.auth.DeviceAuthInfo;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.common.result.Result;

public class DeviceAuthInfoResult implements Result {
    private DeviceAuthInfo mResult;
    private CommonResultStatus mStatus;

    public DeviceAuthInfoResult(CommonResultStatus commonResultStatus, DeviceAuthInfo deviceAuthInfo) {
        this.mStatus = commonResultStatus;
        this.mResult = deviceAuthInfo;
    }

    @Override // com.samsung.android.sdk.mobileservice.common.result.Result
    public CommonResultStatus getStatus() {
        return this.mStatus;
    }

    public DeviceAuthInfo getResult() {
        return this.mResult;
    }
}
