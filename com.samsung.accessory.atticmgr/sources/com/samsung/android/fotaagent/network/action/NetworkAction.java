package com.samsung.android.fotaagent.network.action;

import com.samsung.android.fotaagent.network.rest.RestRequest;
import com.samsung.android.fotaagent.network.rest.RestResponse;

public abstract class NetworkAction {
    public abstract String getBody();

    public abstract RestRequest.HttpMethod getMethod();

    public abstract NetworkResult getResult(RestResponse restResponse);

    public abstract String getURI();
}
