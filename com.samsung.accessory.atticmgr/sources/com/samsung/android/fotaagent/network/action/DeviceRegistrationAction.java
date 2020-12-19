package com.samsung.android.fotaagent.network.action;

import com.samsung.android.fotaagent.network.rest.RestRequest;
import com.samsung.android.fotaagent.network.rest.RestResponse;
import com.samsung.android.fotaprovider.util.OperatorUtil;

public class DeviceRegistrationAction extends NetworkAction {
    private static final String URL_FUMO_DEVICE = "/device/fumo/device/";
    private static final String URL_OSP_SERVER = ".ospserver.net";

    @Override // com.samsung.android.fotaagent.network.action.NetworkAction
    public RestRequest.HttpMethod getMethod() {
        return RestRequest.HttpMethod.POST;
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkAction
    public String getURI() {
        return "https://" + OperatorUtil.getUrlPrefix() + URL_OSP_SERVER + URL_FUMO_DEVICE;
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkAction
    public String getBody() {
        return DeviceRegistrationBody.get();
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkAction
    public NetworkResult getResult(RestResponse restResponse) {
        return new DeviceRegistrationResult(restResponse);
    }
}
