package com.sec.android.diagmonagent.log.ged.db.dao;

public class Contracts {
    static final String COLUMN_PRIMARY_KEY_ID = "id";
    static final String COMMA = ", ";
    static final String CREATE_TABLE = "CREATE TABLE ";
    static final String NOT_NULL = " NOT NULL";
    static final String PRIMARY_KEY_AUTOINCREMENT = " PRIMARY KEY AUTOINCREMENT";
    static final String TYPE_INTEGER = " INTEGER";
    static final String TYPE_TEXT = " TEXT";

    public static class EventContract {
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DEVICE_ID = "deviceId";
        public static final String COLUMN_ERROR_CODE = "errorCode";
        public static final String COLUMN_EVENT_ID = "eventId";
        public static final String COLUMN_EXPIRATION_TIME = "expirationTime";
        public static final String COLUMN_EXTENSION = "extension";
        public static final String COLUMN_LOG_PATH = "logPath";
        public static final String COLUMN_MEMORY = "memory";
        public static final String COLUMN_NETWORK_MODE = "networkMode";
        public static final String COLUMN_RELAY_CLIENT_TYPE = "relayClientType";
        public static final String COLUMN_RELAY_CLIENT_VERSION = "relayClientVersion";
        public static final String COLUMN_RETRY_COUNT = "retryCount";
        public static final String COLUMN_S3_PATH = "s3Path";
        public static final String COLUMN_SDK_TYPE = "sdkType";
        public static final String COLUMN_SDK_VERSION = "sdkVersion";
        public static final String COLUMN_SERVICE_AGREE_TYPE = "serviceAgreeType";
        public static final String COLUMN_SERVICE_DEFINED_KEY = "serviceDefinedKey";
        public static final String COLUMN_SERVICE_ID = "serviceId";
        public static final String COLUMN_SERVICE_VERSION = "serviceVersion";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_STORAGE = "storage";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String TABLE_EVENT = "Event";
    }

    public static class PolicyContract {
        public static final String COLUMN_ERROR_CODE = "errorCode";
        public static final String COLUMN_POLICY_ID = "policyId";
        public static final String COLUMN_SERVICE_ID = "serviceId";
        public static final String COLUMN_SERVICE_VERSION = "serviceVersion";
        public static final String COLUMN_VALUE = "value";
    }

    public static class ResultContract {
        public static final String COLUMN_CLIENT_STATUS_CODE = "clientStatusCode";
        public static final String COLUMN_EVENT_ID = "eventId";
        public static final String COLUMN_SERVICE_ID = "serviceId";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String TABLE_RESULT = "Result";
    }

    public static class ServiceContract {
        public static final String COLUMN_DEVICE_ID = "deviceId";
        public static final String COLUMN_DOCUMENT_ID = "documentId";
        public static final String COLUMN_SDK_TYPE = "sdkType";
        public static final String COLUMN_SDK_VERSION = "sdkVersion";
        public static final String COLUMN_SERVICE_AGREE_TYPE = "serviceAgreeType";
        public static final String COLUMN_SERVICE_ID = "serviceId";
        public static final String COLUMN_SERVICE_VERSION = "serviceVersion";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_TRACKING_ID = "trackingId";
    }
}
