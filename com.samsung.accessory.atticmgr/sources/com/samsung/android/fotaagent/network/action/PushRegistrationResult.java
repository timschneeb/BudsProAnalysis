package com.samsung.android.fotaagent.network.action;

import com.samsung.android.fotaagent.network.rest.RestResponse;
import com.samsung.android.fotaprovider.log.Log;
import org.w3c.dom.Element;

class PushRegistrationResult extends NetworkResult {
    PushRegistrationResult(RestResponse restResponse) {
        super(restResponse);
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkResult
    public boolean needToRetry() {
        return getErrorType() == 500;
    }

    /* access modifiers changed from: package-private */
    @Override // com.samsung.android.fotaagent.network.action.NetworkResult
    public void parse(Element element) {
        Log.I("do nothing");
    }
}
