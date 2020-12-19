package com.sec.android.diagmonagent.log.ged.servreinterface.model.response;

public class PolicyVersionResponse {
    private String latestDefault;
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getLatestDefault() {
        return this.latestDefault;
    }

    public void setLatestDefault(String str) {
        this.latestDefault = str;
    }
}
