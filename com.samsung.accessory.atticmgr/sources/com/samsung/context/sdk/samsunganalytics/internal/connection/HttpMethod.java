package com.samsung.context.sdk.samsunganalytics.internal.connection;

import com.accessorydm.tp.urlconnect.HttpNetworkInterface;

public enum HttpMethod {
    GET(HttpNetworkInterface.XTP_HTTP_METHOD_GET),
    POST(HttpNetworkInterface.XTP_HTTP_METHOD_POST);
    
    String method;

    private HttpMethod(String str) {
        this.method = str;
    }

    public String getMethod() {
        return this.method;
    }
}
