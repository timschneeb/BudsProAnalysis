package com.samsung.android.fotaagent.network.action;

import com.samsung.android.fotaagent.network.rest.RestResponse;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public abstract class NetworkResult {
    private String body = null;
    private int errorType = 0;
    private boolean isConnectionSuccessful = false;
    private boolean isParsingSuccessful = true;

    public boolean isUpdateAvailable() {
        return false;
    }

    public abstract boolean needToRetry();

    /* access modifiers changed from: package-private */
    public abstract void parse(Element element);

    NetworkResult(RestResponse restResponse) {
        if (restResponse == null) {
            Log.W("response is null");
            return;
        }
        this.body = restResponse.getBody();
        Log.D("response http code: " + restResponse.getHttpCode());
        Log.D("response body: " + this.body);
        Log.H("response URI: " + restResponse.getResponseURI());
        if (restResponse.isSuccess()) {
            Log.D("URL connection is success");
            this.isConnectionSuccessful = true;
            return;
        }
        this.errorType = restResponse.getErrorType();
        Log.D("URL connection is failed, errorType: " + this.errorType);
    }

    public int getErrorType() {
        return this.errorType;
    }

    public void setErrorType(int i) {
        this.errorType = i;
    }

    public boolean isSuccess() {
        return this.isConnectionSuccessful && this.isParsingSuccessful;
    }

    /* access modifiers changed from: package-private */
    public void parseResponseIfPossible() {
        if (this.isConnectionSuccessful && this.body != null) {
            try {
                parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(this.body.getBytes("utf-8")))).getDocumentElement());
            } catch (Exception e) {
                this.isParsingSuccessful = false;
                Log.E(e.toString());
            }
        }
    }
}
