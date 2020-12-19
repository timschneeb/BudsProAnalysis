package com.sec.android.diagmonagent.log.ged.db.model;

public class ServiceInfo {
    private String deviceId = "";
    private String documentId = "";
    private String sdkType = "";
    private String sdkVersion = "";
    private String serviceAgreeType = "";
    private String serviceId = "";
    private String serviceVersion = "";
    private int status = 0;
    private long timestamp = 0;
    private String trackingId = "";

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String str) {
        this.serviceId = str;
    }

    public String getTrackingId() {
        return this.trackingId;
    }

    public void setTrackingId(String str) {
        this.trackingId = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String str) {
        this.documentId = str;
    }

    public String getServiceVersion() {
        return this.serviceVersion;
    }

    public void setServiceVersion(String str) {
        this.serviceVersion = str;
    }

    public String getServiceAgreeType() {
        return this.serviceAgreeType;
    }

    public void setServiceAgreeType(String str) {
        this.serviceAgreeType = str;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public void setSdkVersion(String str) {
        this.sdkVersion = str;
    }

    public String getSdkType() {
        return this.sdkType;
    }

    public void setSdkType(String str) {
        this.sdkType = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }
}
