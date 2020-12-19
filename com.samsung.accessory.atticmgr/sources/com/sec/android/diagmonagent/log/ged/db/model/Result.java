package com.sec.android.diagmonagent.log.ged.db.model;

public class Result {
    private static final int UNDEFINED_ID = -1;
    private int clientStatusCode;
    private String eventId;
    private int id = -1;
    private String serviceId;
    private long timestamp;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String str) {
        this.eventId = str;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String str) {
        this.serviceId = str;
    }

    public int getClientStatusCode() {
        return this.clientStatusCode;
    }

    public void setClientStatusCode(int i) {
        this.clientStatusCode = i;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }
}
