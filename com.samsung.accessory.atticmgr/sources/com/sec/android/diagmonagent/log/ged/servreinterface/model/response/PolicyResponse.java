package com.sec.android.diagmonagent.log.ged.servreinterface.model.response;

public class PolicyResponse {
    private String defaultMaxFileCount;
    private String defaultMaxFileSize;
    private String defaultUploadFile;
    private String maxFileCountValue;
    private String maxFileSizeErrorCode;
    private String maxFileSizeServiceVersion;
    private String maxFileSizeValue;
    private String pollingInterval;
    private String uploadFileErrorCode;
    private String uploadFileServiceVersion;
    private String uploadFileValue;
    private String version;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getPollingInterval() {
        return this.pollingInterval;
    }

    public void setPollingInterval(String str) {
        this.pollingInterval = str;
    }

    public String getDefaultUploadFile() {
        return this.defaultUploadFile;
    }

    public void setDefaultUploadFile(String str) {
        this.defaultUploadFile = str;
    }

    public String getDefaultMaxFileSize() {
        return this.defaultMaxFileSize;
    }

    public void setDefaultMaxFileSize(String str) {
        this.defaultMaxFileSize = str;
    }

    public String getDefaultMaxFileCount() {
        return this.defaultMaxFileCount;
    }

    public void setDefaultMaxFileCount(String str) {
        this.defaultMaxFileCount = str;
    }

    public String getUploadFileValue() {
        return this.uploadFileValue;
    }

    public void setUploadFileValue(String str) {
        this.uploadFileValue = str;
    }

    public String getUploadFileServiceVersion() {
        return this.uploadFileServiceVersion;
    }

    public void setUploadFileServiceVersion(String str) {
        this.uploadFileServiceVersion = str;
    }

    public String getUploadFileErrorCode() {
        return this.uploadFileErrorCode;
    }

    public void setUploadFileErrorCode(String str) {
        this.uploadFileErrorCode = str;
    }

    public String getMaxFileSizeValue() {
        return this.maxFileSizeValue;
    }

    public void setMaxFileSizeValue(String str) {
        this.maxFileSizeValue = str;
    }

    public String getMaxFileSizeServiceVersion() {
        return this.maxFileSizeServiceVersion;
    }

    public void setMaxFileSizeServiceVersion(String str) {
        this.maxFileSizeServiceVersion = str;
    }

    public String getMaxFileSizeErrorCode() {
        return this.maxFileSizeErrorCode;
    }

    public void setMaxFileSizeErrorCode(String str) {
        this.maxFileSizeErrorCode = str;
    }

    public String getMaxFileCountValue() {
        return this.maxFileCountValue;
    }

    public void setMaxFileCountValue(String str) {
        this.maxFileCountValue = str;
    }
}
