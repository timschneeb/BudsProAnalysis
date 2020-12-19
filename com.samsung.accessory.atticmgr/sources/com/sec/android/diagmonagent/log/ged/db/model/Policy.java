package com.sec.android.diagmonagent.log.ged.db.model;

public class Policy {
    private String errorCode;
    private String policyId;
    private String serviceId;
    private String serviceVersion;
    private String value;

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String str) {
        this.serviceId = str;
    }

    public String getPolicyId() {
        return this.policyId;
    }

    public void setPolicyId(String str) {
        this.policyId = str;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String str) {
        this.errorCode = str;
    }

    public String getServiceVersion() {
        return this.serviceVersion;
    }

    public void setServiceVersion(String str) {
        this.serviceVersion = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
