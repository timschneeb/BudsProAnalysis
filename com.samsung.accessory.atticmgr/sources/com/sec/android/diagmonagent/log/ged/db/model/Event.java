package com.sec.android.diagmonagent.log.ged.db.model;

public class Event {
    private static final int UNDEFINED_ID = -1;
    private String description = "";
    private String deviceId = "";
    private String errorCode = "";
    private String eventId = "";
    private long expirationTime = 0;
    private String extension = "";
    private int id = -1;
    private String logPath = "";
    private String memory = "";
    private boolean networkMode = true;
    private String relayClientType = "";
    private String relayClientVersion = "";
    private int retryCount = 0;
    private String s3Path = "";
    private String sdkType = "";
    private String sdkVersion = "";
    private String serviceAgreeType = "";
    private String serviceDefinedKey = "";
    private String serviceId = "";
    private String serviceVersion = "";
    private int status = 100;
    private String storage = "";
    private long timestamp;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String str) {
        this.serviceId = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
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

    public String getServiceDefinedKey() {
        return this.serviceDefinedKey;
    }

    public void setServiceDefinedKey(String str) {
        this.serviceDefinedKey = str;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String str) {
        this.errorCode = str;
    }

    public String getLogPath() {
        return this.logPath;
    }

    public void setLogPath(String str) {
        this.logPath = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getRelayClientVersion() {
        return this.relayClientVersion;
    }

    public void setRelayClientVersion(String str) {
        this.relayClientVersion = str;
    }

    public String getRelayClientType() {
        return this.relayClientType;
    }

    public void setRelayClientType(String str) {
        this.relayClientType = str;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String str) {
        this.extension = str;
    }

    public boolean isNetworkMode() {
        return this.networkMode;
    }

    public void setNetworkMode(boolean z) {
        this.networkMode = z;
    }

    public String getMemory() {
        return this.memory;
    }

    public void setMemory(String str) {
        this.memory = str;
    }

    public String getStorage() {
        return this.storage;
    }

    public void setStorage(String str) {
        this.storage = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int i) {
        this.retryCount = i;
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String str) {
        this.eventId = str;
    }

    public String getS3Path() {
        return this.s3Path;
    }

    public void setS3Path(String str) {
        this.s3Path = str;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public long getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(long j) {
        this.expirationTime = j;
    }
}
