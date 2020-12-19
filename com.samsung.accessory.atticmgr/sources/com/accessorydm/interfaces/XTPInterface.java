package com.accessorydm.interfaces;

public interface XTPInterface {
    public static final int XTP_CHUNKED = 1;
    public static final int XTP_DL_RETRY_COUNT_MAX = 15;
    public static final int XTP_DL_RETRY_FAIL_COUNT_MAX = 1;
    public static final int XTP_DM_CHANGE_PROTOCOL_COUNT_MAX = 5;
    public static final int XTP_HTTP_CONNECTION_CLOSE = 1;
    public static final int XTP_HTTP_CONNECTION_KEEP_ALIVE = 2;
    public static final int XTP_HTTP_CONNECTION_NONE = 0;
    public static final int XTP_NOT_CHUNKED = 0;
    public static final int XTP_RETRY_COUNT_MAX = 3;
    public static final int XTP_RETRY_COUNT_NONE = 0;
    public static final int XTP_RET_CONNECTION_FAIL = -2;
    public static final int XTP_RET_CONTENT_TYPE_FAIL = -10;
    public static final int XTP_RET_DL_FORBIDDEN = -11;
    public static final int XTP_RET_DL_REDIRECT = -12;
    public static final int XTP_RET_DL_SERVICE_UNAVAILABLE = -13;
    public static final int XTP_RET_DOWNLOAD_URL_READ_FAIL = -7;
    public static final int XTP_RET_FILE_ERROR = -9;
    public static final int XTP_RET_HTTP_RES_FAIL = -6;
    public static final int XTP_RET_INIT_FAIL = -1;
    public static final int XTP_RET_INVALID_PARAM = -5;
    public static final int XTP_RET_MEMROY_ERROR = -8;
    public static final int XTP_RET_OK = 0;
    public static final int XTP_RET_RECEIVE_FAIL = -4;
    public static final int XTP_RET_SEND_FAIL = -3;
    public static final int XTP_TYPE_HTTP = 2;
    public static final int XTP_TYPE_HTTPS = 1;
    public static final int XTP_TYPE_NONE = 0;

    public enum NetworkTimerMode {
        NETWORK_TIMER_MODE_RECEIVE("Receive"),
        NETWORK_TIMER_MODE_SEND("Send");
        
        private final String szValue;

        private NetworkTimerMode(String str) {
            this.szValue = str;
        }

        public String getTimerMode() {
            return this.szValue;
        }
    }
}
