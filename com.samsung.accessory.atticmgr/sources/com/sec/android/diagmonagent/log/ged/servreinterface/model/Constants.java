package com.sec.android.diagmonagent.log.ged.servreinterface.model;

public class Constants {
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_MESSAGE = "errorMessage";

    public static class EventResponseConstants {
        public static final String EVENT_ID = "eventId";
        public static final String PRE_SIGNED_URL = "preSignedURL";
    }

    public static class PolicyResponseConstants {
        public static final String DEFAULT_POLICY_SET = "defaultPolicySet";
        public static final String ERROR_CODE = "errorCode";
        public static final String LATEST_DEFAULT = "latestDefault";
        public static final String MAX_FILE_COUNT = "maxFileCount";
        public static final String MAX_FILE_SIZE = "maxFileSize";
        public static final String POLICY_ID = "policyId";
        public static final String POLICY_SET = "policySet";
        public static final String POLLING_INTERVAL = "pollingInterval";
        public static final String SERVICES = "services";
        public static final String SERVICE_ID = "serviceId";
        public static final String SERVICE_VERSION = "serviceVersion";
        public static final String UPLOAD_FILE = "uploadFile";
        public static final String URL = "url";
        public static final String VALUE = "value";
        public static final String VERSION = "version";
    }

    public static class ServiceResponseConstants {
        public static final String DOCUMENT_ID = "documentId";
        public static final String ERROR_CODE = "errorCode";
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String SERVICE = "service";
        public static final String SERVICE_ID = "id";
        public static final String STATUS_CODE = "statusCode";
    }

    public static class TokenResponseConstants {
        public static final String TOKEN = "token";
    }
}
