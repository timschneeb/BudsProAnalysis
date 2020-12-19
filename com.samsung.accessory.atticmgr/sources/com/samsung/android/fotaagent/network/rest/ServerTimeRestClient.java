package com.samsung.android.fotaagent.network.rest;

import android.content.Context;
import com.samsung.android.fotaagent.network.ServerTimePreference;
import com.samsung.android.fotaagent.network.Xpath;
import com.samsung.android.fotaagent.network.rest.RestRequest;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.OperatorUtil;

/* access modifiers changed from: package-private */
public class ServerTimeRestClient extends RestClient {
    private static final String CHINA_SERVER_TIME_URL = "https://chn.ospserver.net/security/sso/initialize/time";
    private static final String SERVER_TIME_APPID = "j5p7ll8g33";
    private static final String SERVER_TIME_APPSECRET = "5763D0052DC1462E13751F753384E9A9";
    private static final String SERVER_TIME_URL = "https://fota-apis.samsungdm.com/auth/time";
    private Context context;

    ServerTimeRestClient(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: package-private */
    public void run() {
        RestRequest restRequest = new RestRequest(getTimeZoneServerUrl(), RestRequest.HttpMethod.GET.toString());
        restRequest.setHeaders(RestHeader.instance.getHeaders(RestHeader.instance.generateAuth(SERVER_TIME_APPID, SERVER_TIME_APPSECRET, restRequest.getMethod(), restRequest.getUri(), restRequest.getBody(), System.currentTimeMillis() / 1000)));
        setServerTime(execute(restRequest));
    }

    private String getTimeZoneServerUrl() {
        return OperatorUtil.isChinaConsumer() ? CHINA_SERVER_TIME_URL : SERVER_TIME_URL;
    }

    private void setServerTime(RestResponse restResponse) {
        if (restResponse == null || !restResponse.isSuccess()) {
            Log.W("fail to receive time");
            return;
        }
        try {
            Log.I("success to get time");
            long longValue = Long.valueOf(Xpath.getResult(restResponse.getBody(), "initializeResult/currentServerTime")).longValue();
            Log.H("<initializeResult><currentServerTime>" + longValue + "</currentServerTime></initializeResult>");
            ServerTimePreference.instance.setServerTime(this.context, longValue);
        } catch (Exception e) {
            Log.printStackTrace(e);
        }
    }
}
