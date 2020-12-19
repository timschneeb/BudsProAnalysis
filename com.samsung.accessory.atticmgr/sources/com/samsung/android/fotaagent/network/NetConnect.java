package com.samsung.android.fotaagent.network;

import android.content.Context;
import com.samsung.android.fotaagent.network.action.NetworkAction;
import com.samsung.android.fotaagent.network.action.NetworkResult;
import com.samsung.android.fotaagent.network.rest.OSPRestClient;
import com.samsung.android.fotaagent.network.rest.RestRequest;
import com.samsung.android.fotaagent.network.rest.RestResponse;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;

public class NetConnect {
    private static final int MAX_RETRY_COUNT = 3;

    public NetworkResult execute(Context context, NetworkAction networkAction) {
        Log.I("Request Network Connection");
        if (!NetworkUtil.isWiFiNetworkConnected(context)) {
            if (NetworkUtil.isRoamingNetworkConnected(context)) {
                Log.I("roaming network is connected");
                NetworkResult result = networkAction.getResult(null);
                result.setErrorType(RestResponse.ERROR_TYPE_WIFIONLY_IN_ROAMING);
                return result;
            } else if (!NetworkUtil.isMobileNetworkConnected(context)) {
                Log.I("network is not available");
                NetworkResult result2 = networkAction.getResult(null);
                result2.setErrorType(400);
                return result2;
            }
        }
        OSPRestClient oSPRestClient = new OSPRestClient(context);
        NetworkResult networkResult = null;
        for (int i = 0; i < 3; i++) {
            try {
                RestRequest restRequest = new RestRequest(networkAction.getURI(), networkAction.getMethod().toString());
                restRequest.setBody(networkAction.getBody());
                networkResult = networkAction.getResult(oSPRestClient.run(restRequest));
                if (networkResult.isSuccess() || !networkResult.needToRetry()) {
                    return networkResult;
                }
            } catch (Exception e) {
                Log.printStackTrace(e);
            }
        }
        if (networkResult != null) {
            return networkResult;
        }
        Log.W("networkResult is null, return ERROR_TYPE_UNKNOWN");
        NetworkResult result3 = networkAction.getResult(null);
        result3.setErrorType(RestResponse.ERROR_TYPE_UNKNOWN);
        return result3;
    }
}
