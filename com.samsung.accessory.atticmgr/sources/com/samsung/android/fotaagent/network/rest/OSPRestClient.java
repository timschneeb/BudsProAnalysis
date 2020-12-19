package com.samsung.android.fotaagent.network.rest;

import android.content.Context;
import com.samsung.android.fotaagent.network.ServerTimePreference;

public class OSPRestClient extends RestClient {
    private static final String APPID = "dz7680f4t7";
    private static final String APPSECRET = "4BE4F2C346C6F8831A480E14FD4DE276";
    private Context context;

    public OSPRestClient(Context context2) {
        this.context = context2;
    }

    public RestResponse run(RestRequest restRequest) {
        if (ServerTimePreference.instance.getServerTime(this.context) == 0) {
            new ServerTimeRestClient(this.context).run();
        }
        RestResponse executeAuthorizedAPI = executeAuthorizedAPI(restRequest);
        if (!executeAuthorizedAPI.isSuccess() && executeAuthorizedAPI.isTimeStampError()) {
            ServerTimePreference.instance.resetServerTime(this.context);
        }
        return executeAuthorizedAPI;
    }

    private RestResponse executeAuthorizedAPI(RestRequest restRequest) {
        restRequest.setHeaders(RestHeader.instance.getHeaders(RestHeader.instance.generateAuth(APPID, APPSECRET, restRequest.getMethod(), restRequest.getUri(), restRequest.getBody(), ServerTimePreference.instance.getServerTime(this.context))));
        return execute(restRequest);
    }
}
