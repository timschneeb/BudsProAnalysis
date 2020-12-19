package com.samsung.android.fotaagent.network.action;

import com.samsung.android.fotaagent.network.rest.RestRequest;
import com.samsung.android.fotaagent.network.rest.RestResponse;
import com.samsung.android.fotaprovider.util.OperatorUtil;

public class PushRegistrationAction extends NetworkAction {
    private static final String URL_FUMO_PUSH = "/device/fumo/ippushregister";
    private static final String URL_OSP_SERVER = ".ospserver.net";
    private String fcmId;
    private String sppId;

    public PushRegistrationAction(String str, String str2) {
        this.fcmId = str;
        this.sppId = str2;
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkAction
    public RestRequest.HttpMethod getMethod() {
        return RestRequest.HttpMethod.POST;
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkAction
    public String getURI() {
        return "https://" + OperatorUtil.getUrlPrefix() + URL_OSP_SERVER + URL_FUMO_PUSH;
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkAction
    public String getBody() {
        return PushRegistrationBody.get(this.fcmId, this.sppId);
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkAction
    public NetworkResult getResult(RestResponse restResponse) {
        return new PushRegistrationResult(restResponse);
    }
}
