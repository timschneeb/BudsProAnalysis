package com.sec.android.diagmonagent.log.ged.servreinterface.model.response;

public class ServiceRegistrationResponse {
    private String documentId;
    private String errorCode;
    private String errorMessage;
    private String id;
    private String statusCode;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String str) {
        this.documentId = str;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String str) {
        this.statusCode = str;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String str) {
        this.errorCode = str;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String str) {
        this.errorMessage = str;
    }
}
