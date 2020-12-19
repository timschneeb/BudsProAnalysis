package com.samsung.android.fotaagent.network.rest;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class RestRequest {
    private String body = null;
    private Map<String, String> headers = new HashMap();
    private String method;
    private String uri;

    public enum HttpMethod {
        POST,
        GET
    }

    public RestRequest(String str, String str2) {
        this.uri = str;
        this.method = str2;
    }

    public String getUri() {
        return this.uri;
    }

    /* access modifiers changed from: package-private */
    public String getMethod() {
        return this.method;
    }

    /* access modifiers changed from: package-private */
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    /* access modifiers changed from: package-private */
    public void setHeaders(Map<String, String> map) {
        this.headers.clear();
        this.headers.putAll(map);
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    /* access modifiers changed from: package-private */
    public boolean hasBody() {
        return !TextUtils.isEmpty(this.body);
    }
}
