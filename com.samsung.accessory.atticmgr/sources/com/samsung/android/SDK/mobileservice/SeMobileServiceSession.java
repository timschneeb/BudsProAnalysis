package com.samsung.android.sdk.mobileservice;

import java.util.HashMap;

public interface SeMobileServiceSession {
    public static final int API_STATUS_BLOCKED_BY_POLICY = -4;
    public static final int API_STATUS_FORCE_UPDATE_REQUIRED = -2;
    public static final int API_STATUS_MIN = -4;
    public static final int API_STATUS_NOT_SUPPORTED = -1;
    public static final int API_STATUS_NOT_SUPPORT_SDK_VERSION = -3;
    public static final int API_STATUS_UNDEFINED = -99;
    public static final long INVALID_PACKAGE_VERSION = 0;
    public static final int MIN_AGENT_SUPPORT_VERSION = 0;
    public static final int SERVICE_STATUS_BLOCKED_BY_POLICY = 4;
    public static final int SERVICE_STATUS_FORCE_UPDATE_REQUIRED = 2;
    public static final int SERVICE_STATUS_MAX = 4;
    public static final int SERVICE_STATUS_NOT_SUPPORTED = 1;
    public static final int SERVICE_STATUS_NOT_SUPPORT_SDK_VERSION = 3;
    public static final int SERVICE_STATUS_OK = 0;
    public static final int SERVICE_STATUS_UNDEFINED = 99;

    public interface ConnectionResultCallback {
        public static final int CAUSE_AGENT_FORCE_UPDATE_REQUIRED = SessionErrorCode.CAUSE_AGENT_FORCE_UPDATE_REQUIRED.getValue();
        public static final int CAUSE_AGENT_NOT_AVAILABLE = SessionErrorCode.CAUSE_AGENT_NOT_AVAILABLE.getValue();
        public static final int CAUSE_AGENT_NOT_INSTALLED = SessionErrorCode.CAUSE_AGENT_NOT_INSTALLED.getValue();
        public static final int CAUSE_AGENT_OLD_VERSION = SessionErrorCode.CAUSE_AGENT_OLD_VERSION.getValue();
        public static final int CAUSE_AGENT_UNDEFINED_ERROR = SessionErrorCode.CAUSE_UNDEFINED.getValue();
        public static final int CAUSE_CONNECT_CANCELED = SessionErrorCode.CAUSE_CONNECT_CANCELED.getValue();
        public static final int CAUSE_CONNECT_TIMEOUT = SessionErrorCode.CAUSE_CONNECT_TIMEOUT.getValue();
        public static final int CAUSE_SDK_OLD_VERSION = SessionErrorCode.CAUSE_SDK_OLD_VERSION.getValue();

        void onFailure(int i);

        void onSuccess(HashMap<String, Integer> hashMap, boolean z);
    }

    public interface OnAgentUpdatedListener {
        void onAgentUpdated();
    }

    public interface ServiceConnectionListener {
        public static final int STATUS_SERVICE_CONNECTED = 1;
        public static final int STATUS_SERVICE_DISCONNECTED = -1;

        void onChanged(int i, String str);
    }

    void connect();

    void disconnect();

    long getLatestAgentVersionInGalaxyApps();

    long getLatestSaAgentVersionInGalaxyApps();

    long getSamsungAccountAgentVersion();

    long getSamsungExperienceServiceAgentVersion();

    int getServiceStatus(String str);

    boolean isAddedService(String str);

    boolean isServiceConnected(String str);

    boolean isSessionConnected();

    boolean isSupportedApi(String str);

    void reconnect();

    void setOnAgentUpdatedListener(OnAgentUpdatedListener onAgentUpdatedListener);

    void setSessionListener(ServiceConnectionListener serviceConnectionListener);
}
