package com.accessorydm.interfaces;

public interface XEventInterface {
    public static final int XEVENT_ABORT_BLOCKED_MDM_UPDATE = 251;
    public static final int XEVENT_ABORT_DB_SQL_ERROR = 250;
    public static final int XEVENT_ABORT_DL_FORBIDDEN = 246;
    public static final int XEVENT_ABORT_DL_REDIRECT = 247;
    public static final int XEVENT_ABORT_DL_SERVICE_UNAVAILABLE = 248;
    public static final int XEVENT_ABORT_DM_CHANGEPROTOCOL_MAXCOUNT = 249;
    public static final int XEVENT_ABORT_HTTP_ERROR = 242;
    public static final int XEVENT_ABORT_SYNCDM_ERROR = 243;
    public static final int XEVENT_ABORT_USER_REQ = 241;

    public enum XEVENT {
        XEVENT_NONE,
        XEVENT_DM_CONNECT,
        XEVENT_DM_CONNECTFAIL,
        XEVENT_DM_START,
        XEVENT_DM_CONTINUE,
        XEVENT_DM_ABORT,
        XEVENT_DM_FINISH,
        XEVENT_DM_SENDFAIL,
        XEVENT_DM_RECEIVEFAIL,
        XEVENT_DM_TCPIP_OPEN,
        XEVENT_NOTI_RECEIVED,
        XEVENT_NOTI_EXECUTE,
        XEVENT_NOTI_NOT_SPECIFIED,
        XEVENT_NOTI_BACKGROUND,
        XEVENT_NOTI_INFORMATIVE,
        XEVENT_NOTI_INTERACTIVE,
        XEVENT_DL_CONNECT,
        XEVENT_DL_CONNECTFAIL,
        XEVENT_DL_START,
        XEVENT_DL_CONTINUE,
        XEVENT_DL_ABORT,
        XEVENT_DL_FINISH,
        XEVENT_DL_SENDFAIL,
        XEVENT_DL_RECEIVEFAIL,
        XEVENT_DL_TCPIP_OPEN,
        XEVENT_DL_USER_CANCEL_DOWNLOAD,
        XEVENT_DL_DELTA_SIZE_ERROR_DOWNLOAD,
        XEVENT_DL_DEVICE_FAIL_DOWNLOAD,
        XEVENT_DL_BLOCKED_MDM_UPDATE_FAILED,
        XEVENT_ACCESSORY_COPY,
        XEVENT_ACCESSORY_DIFFERENT_DEVICE;
        
        private static final XEVENT[] values = values();

        public static XEVENT valueOf(int i) {
            try {
                return values[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
