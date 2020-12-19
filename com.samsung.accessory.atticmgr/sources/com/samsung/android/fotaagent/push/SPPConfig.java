package com.samsung.android.fotaagent.push;

public interface SPPConfig {
    public static final String APP_ID = "40a985eacedf50ec";
    public static final String EXTRA_APPID = "appId";
    public static final String EXTRA_ERROR = "Error";
    public static final String EXTRA_MSG = "msg";
    public static final String EXTRA_REGID = "RegistrationID";
    public static final String EXTRA_REQTYPE = "reqType";
    public static final String EXTRA_STATUS = "com.sec.spp.Status";
    public static final String EXTRA_USERDATA = "userdata";
    public static final String NOTIFICATION_ACK_RESULT_ACTION = "com.sec.spp.NotificationAckResultAction";
    public static final String PUSH_REGISTRATION_CHANGED_ACTION = "com.sec.spp.RegistrationChangedAction";
    public static final int PUSH_REQ_TYPE_DEFAULT = 0;
    public static final int PUSH_REQ_TYPE_DEREGISTRATION = 2;
    public static final int PUSH_REQ_TYPE_MAX = 3;
    public static final int PUSH_REQ_TYPE_REGISTRATION = 1;
    public static final String PUSH_SERVICE_REQUEST = "com.sec.spp.action.SPP_REQUEST";
    public static final String SERVICE_ABNORMALLY_STOPPED_ACTION = "com.sec.spp.ServiceAbnormallyStoppedAction";
    public static final String SERVICE_DATA_DELETED_ACTION = "com.sec.spp.ServiceDataDeletedAction";
    public static final String SPP_PACKAGENAME = "com.sec.spp.push";
    public static final int STATE_DEREGISTRATION_FAIL = 3;
    public static final int STATE_DEREGISTRATION_SUCCESS = 2;
    public static final int STATE_REGISTRATION_FAIL = 1;
    public static final int STATE_REGISTRATION_SUCCESS = 0;
}
