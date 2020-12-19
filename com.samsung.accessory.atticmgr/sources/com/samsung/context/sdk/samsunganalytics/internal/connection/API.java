package com.samsung.context.sdk.samsunganalytics.internal.connection;

public enum API {
    DATA_DELETE(Domain.REGISTRATION, Directory.DATA_DELETE, HttpMethod.POST),
    GET_POLICY(Domain.POLICY, Directory.DEVICE_CONTROLLER_DIR, HttpMethod.GET),
    SEND_LOG(Domain.DLS, Directory.DLS_DIR, HttpMethod.POST),
    SEND_BUFFERED_LOG(Domain.DLS, Directory.DLS_DIR_BAT, HttpMethod.POST);
    
    Directory directory;
    Domain domain;
    HttpMethod method;

    private API(Domain domain2, Directory directory2, HttpMethod httpMethod) {
        this.domain = domain2;
        this.directory = directory2;
        this.method = httpMethod;
    }

    public String getUrl() {
        return this.domain.getDomain() + this.directory.getDirectory();
    }

    public String getMethod() {
        return this.method.getMethod();
    }
}
