package com.samsung.accessory.hearablemgr.core.fmm.utils;

public class FmmConstants {
    public static final String FMM_PACKAGE = "com.samsung.android.fmm";
    public static final String FMM_PERMISSION_SIGNATURE = "com.sec.android.permission.DSMLAWMO";
    public static final String MODEL_NAME = "Galaxy Buds Pro";
    public static final String MODEL_NUMBER = "SM-R190";
    public static final String NOT_SUPPORT = "N";
    public static final String PLUGIN_PACKAGE = "plugin_package";
    public static final String PLUGIN_RECEIVER = "plugin_receiver";
    public static final String RESULT = "result";
    public static final String SUPPORT = "Y";

    public static class Action {
        public static final String ACTION_OPERATION_RESPONSE = "com.samsung.android.fmm.OPERATION_RESPONSE";
        public static final String ACTION_WD_CHANGED = "com.samsung.android.fmm.WD_CHANGED";
    }

    public static class Operation {
        public static final String CONNECTION = "CONNECTION";
        public static final String CONNECTION_CHECK = "CONNECTIONCHECK";
        public static final String GET_DEVICE_INFO = "GET_DEVICE_INFO";
        public static final String MUTE_L = "MUTE-L";
        public static final String MUTE_R = "MUTE-R";
        public static final String REMOVE = "REMOVE";
        public static final String RING = "RING";
        public static final String RING_STATUS = "RINGSTATUS";
        public static final String SET_DEVICE_INFO = "SET_DEVICE_INFO";
        public static final String STATUS_CHANGE = "STATUS_CHANGE";
        public static final String UPDATE = "UPDATE";
    }

    public static class RequestKey {
        public static final String OPERATION = "operation";
        public static final String STATUS = "status";
        public static final String UID = "uid";
    }

    public static class Result {
        public static final String FAIL = "fail";
        public static final String SUCCESS = "success";
    }

    public static class ResultCode {
        public static final int ALREADY_MUTE = 12;
        public static final int ALREADY_RING = 7;
        public static final int ALREADY_STOP = 8;
        public static final int ALREADY_UNMUTE = 13;
        public static final int DEVICE_BOTH_WEARING = 9;
        public static final int DEVICE_CALLING = 10;
        public static final int DEVICE_DISCONNECTED = 1;
        public static final int DEVICE_IS_WEARING = 14;
        public static final int DISABLE_REMOTE_CONTROL = 6;
        public static final int DUPLICATE_REQEUST = 3;
        public static final int INCORRECT_UID = 5;
        public static final int NOT_FINDING = 11;
        public static final int SUCCESS = 0;
        public static final int TIMEOUT = 2;
        public static final int UNKNOWN = 4;
    }

    public static class ResultKey {
        public static final String DATA = "data";
        public static final String OPERATION = "operation";
        public static final String RESULT = "result";
        public static final String RESULT_CODE = "result_code";
        public static final String TYPE = "type";
        public static final String UID = "uid";
    }

    public static class SupportOption {
        public static final String RING = "RING";
        public static final String SET_DEVICE_INFO = "SET_DEVICE_INFO";
    }

    public static class fmmConfig {
        public static final String e2e = "e2e";
        public static final String findingSupport = "findingSupport";
        public static final String fmmToken = "fmmToken";
        public static final String iv = "iv";
        public static final String maxN = "maxN";
        public static final String region = "region";
        public static final String secretKey = "secretKey";
        public static final String sn = "sn";
    }
}
