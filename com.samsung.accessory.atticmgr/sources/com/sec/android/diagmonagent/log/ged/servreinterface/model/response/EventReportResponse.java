package com.sec.android.diagmonagent.log.ged.servreinterface.model.response;

public class EventReportResponse {
    private String eventId;
    private String preSignedURL;

    public String getPreSignedURL() {
        return this.preSignedURL;
    }

    public void setPreSignedURL(String str) {
        this.preSignedURL = str;
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String str) {
        this.eventId = str;
    }
}
