package com.samsung.android.fotaagent.network.rest;

import android.text.TextUtils;
import com.samsung.android.fotaagent.network.Xpath;
import com.samsung.android.fotaprovider.log.Log;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestResponse {
    private static final String ERROR_CODE_DEVICE_NOT_REGISTERED = "FUD_3000";
    private static final String ERROR_CODE_INVALID_PARAMETER = "FUD_1";
    private static final String ERROR_CODE_INVALID_TIMESTAMP = "SSO_8005";
    private static final String ERROR_CODE_MODEL_CC_NOT_EXIST = "FUD_3007";
    public static final int ERROR_TYPE_BAD_REQUEST = 430;
    public static final int ERROR_TYPE_EMPTY = 0;
    public static final int ERROR_TYPE_HTTP_NOT_FOUND = 510;
    public static final int ERROR_TYPE_INVALID_PARAMETER = 100;
    public static final int ERROR_TYPE_NOT_EXIST = 440;
    public static final int ERROR_TYPE_NO_AVAILABLE_NETWORK = 400;
    public static final int ERROR_TYPE_PROCESS = 500;
    public static final int ERROR_TYPE_UNKNOWN = 600;
    public static final int ERROR_TYPE_WIFIONLY_IN_ROAMING = 410;
    private String body;
    private String errorCode = "";
    private int errorType = 0;
    private Map<String, List<String>> headers = new HashMap();
    private int httpCode;
    private String responseURI = "";

    public RestResponse(int i, String str, Map<String, List<String>> map) {
        this.httpCode = i;
        this.body = str;
        this.headers.putAll(map);
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public String getBody() {
        return this.body;
    }

    /* access modifiers changed from: package-private */
    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    public String getResponseURI() {
        return this.responseURI;
    }

    public int getErrorType() {
        return this.errorType;
    }

    public boolean isSuccess() {
        return 200 == this.httpCode;
    }

    /* access modifiers changed from: package-private */
    public void setError() {
        Log.E("Error Steam:" + this.body);
        if (!TextUtils.isEmpty(this.body)) {
            try {
                this.errorCode = Xpath.getResult(this.body, "error/code");
                this.responseURI = Xpath.getResult(this.body, "error/responseURI");
            } catch (Exception e) {
                Log.printStackTrace(e);
            }
            this.errorType = initErrorType();
        }
    }

    private int initErrorType() {
        if (this.httpCode == 0 && this.body == null) {
            return 0;
        }
        if (!TextUtils.isEmpty(this.errorCode)) {
            if (this.errorCode.contains(ERROR_CODE_INVALID_PARAMETER)) {
                return 100;
            }
            if (ERROR_CODE_DEVICE_NOT_REGISTERED.equals(this.errorCode) || ERROR_CODE_MODEL_CC_NOT_EXIST.equals(this.errorCode)) {
                return ERROR_TYPE_NOT_EXIST;
            }
            if (ERROR_CODE_INVALID_TIMESTAMP.equals(this.errorCode)) {
                return 500;
            }
        }
        int i = this.httpCode;
        if (i == 400) {
            return ERROR_TYPE_BAD_REQUEST;
        }
        if (i != 401) {
            if (i == 404) {
                return ERROR_TYPE_HTTP_NOT_FOUND;
            }
            if (i == 500 || i == 501) {
                return 500;
            }
            return ERROR_TYPE_UNKNOWN;
        }
        return 500;
    }

    /* access modifiers changed from: package-private */
    public boolean isTimeStampError() {
        return ERROR_CODE_INVALID_TIMESTAMP.equals(this.errorCode);
    }
}
