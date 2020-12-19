package com.accessorydm.interfaces;

public interface XDMInterface {
    public static final String ALERT_CLIENT_EVENT = "1224";
    public static final String ALERT_CLIENT_INITIATED_MGMT = "1201";
    public static final String ALERT_CONTINUE_OR_ABORT = "1101";
    public static final String ALERT_DISPLAY = "1100";
    public static final String ALERT_GENERIC = "1226";
    public static final String ALERT_MULTIPLE_CHOICE = "1104";
    public static final String ALERT_NEXT_MESSAGE = "1222";
    public static final String ALERT_NO_END_OF_DATA = "1225";
    public static final String ALERT_SERVER_INITIATED_MGMT = "1200";
    public static final String ALERT_SESSION_ABORT = "1223";
    public static final String ALERT_SINGLE_CHOICE = "1103";
    public static final String ALERT_TEXT_INPUT = "1102";
    public static final String ALERT_TYPE_DEV_INIT = "org.openmobilealliance.dm.firmwareupdate.devicerequest";
    public static final String ALERT_TYPE_DOWNLOAD_AND_UPDATE_REPORT = "org.openmobilealliance.dm.firmwareupdate.downloadandupdate";
    public static final String ALERT_TYPE_DOWNLOAD_REPORT = "org.openmobilealliance.dm.firmwareupdate.download";
    public static final String ALERT_TYPE_UPDATE_REPORT = "org.openmobilealliance.dm.firmwareupdate.update";
    public static final String ALERT_TYPE_USER_INIT = "org.openmobilealliance.dm.firmwareupdate.userrequest";
    public static final String AUTH_TYPE_BASIC = "BASIC";
    public static final String AUTH_TYPE_DIGEST = "DIGEST";
    public static final String AUTH_TYPE_HMAC = "HMAC";
    public static final String AUTH_TYPE_NONE = "NONE";
    public static final String CMD_ADD = "Add";
    public static final String CMD_ALERT = "Alert";
    public static final String CMD_ATOMIC = "Atomic";
    public static final String CMD_COPY = "Copy";
    public static final String CMD_DELETE = "Delete";
    public static final String CMD_EXEC = "Exec";
    public static final String CMD_GET = "Get";
    public static final String CMD_MAP = "Map";
    public static final String CMD_PUT = "Put";
    public static final String CMD_REPLACE = "Replace";
    public static final String CMD_RESULTS = "Results";
    public static final String CMD_SEQUENCE = "Sequence";
    public static final String CMD_STATUS = "Status";
    public static final String CMD_SYNC = "Sync";
    public static final String CMD_SYNCHDR = "SyncHdr";
    public static final String CRED_TYPE_BASIC = "syncml:auth-basic";
    public static final String CRED_TYPE_HMAC = "syncml:auth-MAC";
    public static final String CRED_TYPE_MD5 = "syncml:auth-md5";
    public static final String DEFAULT_DISPLAY_UIC_OPTION = "MINDT=30";
    public static final String DOWNLOAD_VERSION = "1.0";
    public static final String FUMO_X_NODE_COMMON = "/COMMONPkg";
    public static final int FUMO_X_NODE_COUNT_COMMON = 1;
    public static final String MIMETYPE_TEXT_PLAIN = "text/plain";
    public static final String STATUS_ACCEPTED_AND_BUFFERED = "213";
    public static final String STATUS_ACCEPTED_FOR_PROCESSING = "202";
    public static final String STATUS_ALREADY_EXISTS = "418";
    public static final String STATUS_ATOMIC_FAILED = "507";
    public static final String STATUS_ATOMIC_RESPONSE_TOO_LARGE_TO_FIT = "517";
    public static final String STATUS_ATOMIC_ROLL_BACK_FAILED = "516";
    public static final String STATUS_ATOMIC_ROLL_BACK_OK = "216";
    public static final String STATUS_AUTHENTICATIONACCEPTED = "212";
    public static final String STATUS_AUTHENTICATION_REQUIRED = "407";
    public static final String STATUS_COMMAND_FAILED = "500";
    public static final String STATUS_COMMAND_NOT_ALLOWED = "405";
    public static final String STATUS_DATA_STORE_FAILURE = "510";
    public static final String STATUS_DEVICE_FULL = "420";
    public static final String STATUS_FORBIDDEN = "403";
    public static final String STATUS_INCOMPLETE_COMMAND = "412";
    public static final String STATUS_NOT_EXECUTED = "215";
    public static final String STATUS_NOT_FOUND = "404";
    public static final String STATUS_NOT_MODIFIED = "304";
    public static final String STATUS_OK = "200";
    public static final String STATUS_OPERATION_CANCELLED = "214";
    public static final String STATUS_OPTIONAL_FEATURE_NOT_SUPPORTED = "406";
    public static final String STATUS_PERMISSION_DENIED = "425";
    public static final String STATUS_REQUESTED_RANGE_NOT_SATISFIABLE = "416";
    public static final String STATUS_REQUEST_ENTITY_TOO_LARGE = "413";
    public static final String STATUS_REQUEST_TIMEOUT = "408";
    public static final String STATUS_UNAUTHORIZED = "401";
    public static final String STATUS_UNSUPPORTED_MEDEA_TYPE_OR_FORMAT = "415";
    public static final String STATUS_URI_TOO_LONG = "414";
    public static final int SYNCMLDL = 1;
    public static final int SYNCMLDM = 0;
    public static final int SYNCMLMAX = 2;
    public static final String SYNCML_MIME_TYPE_DOWNLOAD_TYPE = "application/octet-stream";
    public static final String SYNCML_MIME_TYPE_TNDS_WBXML = "application/vnd.syncml.dmtnds+wbxml";
    public static final String SYNCML_MIME_TYPE_TNDS_XML = "application/vnd.syncml.dmtnds+xml";
    public static final String VERDTD_1_2 = "1.2";
    public static final String VERPROTO_1_2 = "DM/1.2";
    public static final String WBXML_STRING_TABLE_1_2 = "-//SYNCML//DTD SyncML 1.2//EN";
    public static final int XDM_ABORT_AGENT_FAILURE = 4;
    public static final int XDM_ABORT_ALERT_FROM_SERVER = 10;
    public static final int XDM_ABORT_AUTHENTICATION_FAILURE = 3;
    public static final int XDM_ABORT_CONNECT_CLOSE = 8;
    public static final int XDM_ABORT_PARSING_FAILURE = 9;
    public static final int XDM_ABORT_RECV_FAILURE = 6;
    public static final int XDM_ABORT_RECV_TIMEOUT = 7;
    public static final int XDM_ABORT_SEND_FAILURE = 5;
    public static final int XDM_ABORT_TIMEOUT = 2;
    public static final int XDM_ABORT_USER_REQUEST = 1;
    public static final String XDM_ACCOUNT_PATH = "./DMAcc";
    public static final String XDM_ACC_AAUTHPREF_PATH = "/AAuthPref";
    public static final String XDM_ACC_ADDRTYPE_PATH = "/AddrType";
    public static final String XDM_ACC_ADDR_PATH = "/Addr";
    public static final String XDM_ACC_APPADDR_PATH = "/AppAddr";
    public static final String XDM_ACC_APPAUTH_PATH = "/AppAuth";
    public static final String XDM_ACC_APPID_PATH = "/AppID";
    public static final String XDM_ACC_AUTHPREF_PATH = "/AuthPref";
    public static final String XDM_ACC_CLIENTNONCE_PATH = "/ClientNonce";
    public static final String XDM_ACC_CLIENTPW_PATH = "/ClientPW";
    public static final String XDM_ACC_CONREF_PATH = "/ConRef";
    public static final String XDM_ACC_EXT_PATH = "/Ext";
    public static final String XDM_ACC_NAME_PATH = "/Name";
    public static final String XDM_ACC_PORTNBR_PATH = "/PortNbr";
    public static final String XDM_ACC_PREFCONREF_PATH = "/PrefConRef";
    public static final String XDM_ACC_SERVERID_PATH = "/ServerID";
    public static final String XDM_ACC_SERVERID_PATH_1_1 = "/ServerId";
    public static final String XDM_ACC_SERVERNONCE_PATH = "/ServerNonce";
    public static final String XDM_ACC_SERVERPW_PATH = "/ServerPW";
    public static final String XDM_ACC_TOCONREF_PATH = "/ToConRef";
    public static final String XDM_ACC_USERNAME_PATH = "/UserName";
    public static final int XDM_AGENT_DM = 0;
    public static final int XDM_AGENT_FUMO = 1;
    public static final String XDM_APPADDR_ADDRTYPE_PATH = "/AddrType";
    public static final String XDM_APPADDR_ADDR_PATH = "/Addr";
    public static final String XDM_APPADDR_PORT_PATH = "/Port";
    public static final String XDM_APPADDR_PORT_PORTNUMBER_PATH = "/PortNbr";
    public static final String XDM_APPAUTH_AAUTHDATA_PATH = "/AAuthData";
    public static final String XDM_APPAUTH_AAUTHLEVEL_PATH = "/AAuthLevel";
    public static final String XDM_APPAUTH_AAUTHNAME_PATH = "/AAuthName";
    public static final String XDM_APPAUTH_AAUTHSECRET_PATH = "/AAuthSecret";
    public static final String XDM_APPAUTH_AAUTHTYPE_PATH = "/AAuthType";
    public static final int XDM_AUTH_STATE_FAIL = -1;
    public static final int XDM_AUTH_STATE_INVALID_CRED = -2;
    public static final int XDM_AUTH_STATE_MISSING_CRED = -3;
    public static final int XDM_AUTH_STATE_NONE = -8;
    public static final int XDM_AUTH_STATE_OK = 1;
    public static final int XDM_AUTH_STATE_OK_PENDING = 0;
    public static final int XDM_AUTH_STATE_REQUIRED = -9;
    public static final int XDM_AUTH_STATE_RETRY = -7;
    public static final String XDM_BASE_PATH = ".";
    public static final String XDM_CONNECTION_NAME = "DM Profile";
    public static final String XDM_CON_EXT_PATH = "/Ext";
    public static final String XDM_CON_EXT_SERVICE_PATH = "/Ext/Service";
    public static final String XDM_CON_NAP_ADDRTYPE_PATH = "/NAP/AddrType";
    public static final String XDM_CON_NAP_ADDR_PATH = "/NAP/Addr";
    public static final String XDM_CON_NAP_AUTH_CHAP_ID_PATH = "/NAP/Auth/CHAP/Id";
    public static final String XDM_CON_NAP_AUTH_CHAP_PATH = "/NAP/Auth/CHAP";
    public static final String XDM_CON_NAP_AUTH_CHAP_SEC_PATH = "/NAP/Auth/CHAP/Secret";
    public static final String XDM_CON_NAP_AUTH_PAP_ID_PATH = "/NAP/Auth/PAP/Id";
    public static final String XDM_CON_NAP_AUTH_PAP_PATH = "/NAP/Auth/PAP";
    public static final String XDM_CON_NAP_AUTH_PAP_SEC_PATH = "/NAP/Auth/PAP/Secret";
    public static final String XDM_CON_NAP_AUTH_PATH = "/NAP/Auth";
    public static final String XDM_CON_NAP_BEARER_PATH = "/NAP/Bearer";
    public static final String XDM_CON_NAP_PATH = "/NAP";
    public static final String XDM_CON_PX_ADDRTYPE_PATH = "/PX/AddrType";
    public static final String XDM_CON_PX_ADDR_PATH = "/PX/Addr";
    public static final String XDM_CON_PX_AUTH_PATH = "/PX/Auth";
    public static final String XDM_CON_PX_PATH = "/PX";
    public static final String XDM_CON_PX_PORTNBR_PATH = "/PX/PortNbr";
    public static final int XDM_CRED_TYPE_BASIC = 0;
    public static final int XDM_CRED_TYPE_HMAC = 2;
    public static final int XDM_CRED_TYPE_MD5 = 1;
    public static final int XDM_CRED_TYPE_MD5_NOT_BASE64 = 3;
    public static final int XDM_CRED_TYPE_NONE = -1;
    public static final int XDM_CRED_TYPE_SHA1 = 4;
    public static final long XDM_DEFAULT_BIG_BUFFER_SIZE = 1024;
    public static final long XDM_DEFAULT_BIG_BUFFER_SIZE_2 = 2048;
    public static final int XDM_DEFAULT_BUFFER_SIZE = 32;
    public static final int XDM_DEFAULT_BUFFER_SIZE_2 = 64;
    public static final int XDM_DEFAULT_BUFFER_SIZE_3 = 128;
    public static final int XDM_DEFAULT_BUFFER_SIZE_4 = 256;
    public static final long XDM_DEFAULT_BUFFER_SIZE_5 = 512;
    public static final int XDM_DEFAULT_BUFFER_SIZE_HALF = 16;
    public static final String XDM_DEFAULT_CONREF = "dataProxy";
    public static final String XDM_DEVDETAIL_DEFAULT_DEVTYPE = "phone";
    public static final String XDM_DEVDETAIL_DEFAULT_LRGOBJ_SUPPORT = "false";
    public static final String XDM_DEVDETAIL_DEFAULT_URI_SUBNODE_VALUE = "0";
    public static final String XDM_DEVDETAIL_EXT_PATH = "./DevDetail/Ext";
    public static final String XDM_DEVDETAIL_FWV_PATH = "./DevDetail/FwV";
    public static final String XDM_DEVDETAIL_LRGOBJ_PATH = "./DevDetail/LrgObj";
    public static final String XDM_DEVDETAIL_PATH = "./DevDetail";
    public static final int XDM_DEVICE_INIT = 2;
    public static final String XDM_DEVINFO_BEARER_PATH = "./DevInfo/Bearer";
    public static final String XDM_DEVINFO_DEFAULT_DMV1_1 = "1.1";
    public static final String XDM_DEVINFO_DEFAULT_DMV1_2 = "1.2";
    public static final String XDM_DEVINFO_DEFAULT_LANG = "en-us";
    public static final String XDM_DEVINFO_DEVID_PATH = "./DevInfo/DevId";
    public static final String XDM_DEVINFO_DMV_PATH = "./DevInfo/DmV";
    public static final String XDM_DEVINFO_EXT_DMCLIENTVER_PATH = "./DevInfo/Ext/DMClientVer";
    public static final String XDM_DEVINFO_EXT_FOTACLIENTVER_PATH = "./DevInfo/Ext/FotaClientVer";
    public static final String XDM_DEVINFO_EXT_NETWORKCONNTYPE_PATH = "./DevInfo/Ext/DevNetworkConnType";
    public static final String XDM_DEVINFO_EXT_PATH = "./DevInfo/Ext";
    public static final String XDM_DEVINFO_EXT_SIMCARDMCC_PATH = "./DevInfo/Ext/SIMCardMcc";
    public static final String XDM_DEVINFO_EXT_SIMCARDMNC_PATH = "./DevInfo/Ext/SIMCardMnc";
    public static final String XDM_DEVINFO_EXT_TELEPHONYMCC_PATH = "./DevInfo/Ext/TelephonyMcc";
    public static final String XDM_DEVINFO_EXT_TELEPHONYMNC_PATH = "./DevInfo/Ext/TelephonyMnc";
    public static final String XDM_DEVINFO_LANG_PATH = "./DevInfo/Lang";
    public static final String XDM_DEVINFO_MAN_PATH = "./DevInfo/Man";
    public static final String XDM_DEVINFO_MOD_PATH = "./DevInfo/Mod";
    public static final String XDM_DEVINFO_PATH = "./DevInfo";
    public static final int XDM_DL_DELTA_DOWNLOAD_BEFORE = 1;
    public static final int XDM_FORMAT_B64 = 1;
    public static final int XDM_FORMAT_BIN = 2;
    public static final int XDM_FORMAT_BOOL = 3;
    public static final int XDM_FORMAT_CHR = 4;
    public static final int XDM_FORMAT_DATE = 10;
    public static final int XDM_FORMAT_FLOAT = 9;
    public static final int XDM_FORMAT_INT = 5;
    public static final int XDM_FORMAT_NODE = 6;
    public static final int XDM_FORMAT_NONE = 12;
    public static final int XDM_FORMAT_NULL = 7;
    public static final int XDM_FORMAT_TIME = 11;
    public static final int XDM_FORMAT_XML = 8;
    public static final int XDM_FOTA_MECHANISM_ALTERNATIVE = 2;
    public static final int XDM_FOTA_MECHANISM_ALTERNATIVE_DOWNLOAD = 3;
    public static final int XDM_FOTA_MECHANISM_NONE = 0;
    public static final int XDM_FOTA_MECHANISM_REPLACE = 1;
    public static final int XDM_FOTA_MECHANISM_UPDATE = 4;
    public static final int XDM_HTTP_RETRY_COUNT = 3;
    public static final int XDM_MAX_ACL_NUM = 10;
    public static final int XDM_MAX_AUTH_COUNT = 3;
    public static final int XDM_MAX_CHILD_NUM = 100;
    public static final long XDM_MAX_NODENAME_SIZE = 256;
    public static final long XDM_MAX_NODE_NUM = 1024;
    public static final long XDM_MAX_SPACE_SIZE = 40960;
    public static final int XDM_MAX_TYPE_NUM = 10;
    public static final int XDM_MECHANISM_END = 5;
    public static final double XDM_MEGA_BYTE_SIZE = 1048576.0d;
    public static final int XDM_NETWORK_STATE_ALREADY_DOWNLOAD = 3;
    public static final int XDM_NETWORK_STATE_NOT_READY = 1;
    public static final int XDM_NETWORK_STATE_NOT_USE = 0;
    public static final int XDM_NETWORK_STATE_USING = 2;
    public static final int XDM_NONE_INIT = 0;
    public static final int XDM_NOTI_RESYNC_MODE_FALSE = 0;
    public static final int XDM_NOTI_RESYNC_MODE_TRUE = 1;
    public static final int XDM_OMACL_ADD = 1;
    public static final int XDM_OMACL_DELETE = 2;
    public static final int XDM_OMACL_EXEC = 4;
    public static final int XDM_OMACL_GET = 8;
    public static final int XDM_OMACL_NONE = 0;
    public static final int XDM_OMACL_REPLACE = 16;
    public static final String XDM_OMA_ALTERNATIVE = "/DownloadAndUpdate/PkgURL";
    public static final String XDM_OMA_ALTERNATIVE_2 = "/Download/PkgURL";
    public static final String XDM_OMA_EXEC_ALTERNATIVE = "/DownloadAndUpdate";
    public static final String XDM_OMA_EXEC_ALTERNATIVE_2 = "/Download";
    public static final String XDM_OMA_EXEC_REPLACE = "/Update";
    public static final String XDM_OMA_MECHANISM = "/Mechanism";
    public static final String XDM_OMA_REPLACE = "/Update/PkgData";
    public static final int XDM_OMVFSPACK_ACL = 70;
    public static final int XDM_OMVFSPACK_ENDNODE = 68;
    public static final int XDM_OMVFSPACK_STARTNODE = 66;
    public static final int XDM_OMVFS_ERR_BUFFER_NOT_ENOUGH = -3;
    public static final int XDM_OMVFS_ERR_FAILED = -4;
    public static final int XDM_OMVFS_ERR_INVALIDPARAMETER = -1;
    public static final int XDM_OMVFS_ERR_NOEFFECT = -2;
    public static final int XDM_OMVFS_ERR_NOSPACE = -5;
    public static final int XDM_OMVFS_ERR_OK = 0;
    public static final int XDM_PROCESS_STEP_ATOMIC = 2;
    public static final int XDM_PROCESS_STEP_FINISH = 3;
    public static final int XDM_PROCESS_STEP_NORMAL = 0;
    public static final int XDM_PROCESS_STEP_SEQUENCE = 1;
    public static final int XDM_PROFILE_NUM = 2;
    public static final int XDM_RESUME_STATUS_DL_FAIL = 2;
    public static final int XDM_RESUME_STATUS_DM_FAIL = 1;
    public static final int XDM_RESUME_STATUS_NONE = 0;
    public static final int XDM_RET_ALERT_SESSION_ABORT = 1;
    public static final int XDM_RET_AUTH_MAX_ERROR = -5;
    public static final int XDM_RET_BUFFER_SIZE_EXCEEDED = -3;
    public static final int XDM_RET_CHANGED_PROFILE = 2;
    public static final int XDM_RET_EXEC_ALTERNATIVE = 5;
    public static final int XDM_RET_EXEC_ALTERNATIVE_DOWNLOAD = 6;
    public static final int XDM_RET_EXEC_ALTERNATIVE_UPDATE = 7;
    public static final int XDM_RET_EXEC_DOWNLOAD_COMPLETE = 8;
    public static final int XDM_RET_EXEC_REPLACE = 4;
    public static final int XDM_RET_FAILED = -1;
    public static final int XDM_RET_FINISH = 3;
    public static final int XDM_RET_OK = 0;
    public static final int XDM_RET_PARSE_ERROR = -2;
    public static final int XDM_RET_PAUSED_BECAUSE_UIC_COMMAND = -4;
    public static final int XDM_RET_UNKNOWN_ERROR = -6;
    public static final int XDM_SCOPE_DYNAMIC = 2;
    public static final int XDM_SCOPE_NONE = 0;
    public static final int XDM_SCOPE_PERMANENT = 1;
    public static final String XDM_SYNCML_CON_PATH = "./SyncML/Con";
    public static final String XDM_SYNCML_PATH = "./SyncML";
    public static final int XDM_SYNC_BOOTSTARP = 3;
    public static final int XDM_SYNC_COMPLETE = 2;
    public static final int XDM_SYNC_NONE = 0;
    public static final int XDM_SYNC_RUN = 1;
    public static final int XDM_SYSTEM_SYSSCOPE_NOT_OK = 2;
    public static final int XDM_SYSTEM_SYSSCOPE_NOT_SCAN = 0;
    public static final int XDM_SYSTEM_SYSSCOPE_OK = 1;
    public static final int XDM_TASK_ACTIVE = 1;
    public static final int XDM_TASK_NONE = 0;
    public static final int XDM_TASK_RETRY = 2;
    public static final int XDM_TNDS_PROPERTY_ACL = 1;
    public static final int XDM_TNDS_PROPERTY_FORMAT = 2;
    public static final int XDM_TNDS_PROPERTY_TYPE = 4;
    public static final int XDM_TNDS_PROPERTY_VALUE = 8;
    public static final String XDM_TOCONREF_CONREF_PATH = "/ConRef";
    public static final int XDM_TYPE_EXTENSION = 2;
    public static final int XDM_TYPE_OPAQUE = 1;
    public static final int XDM_TYPE_STRING = 0;
    public static final int XDM_UI_MODE_BACKGROUND = 2;
    public static final int XDM_UI_MODE_FOREGROUND = 1;
    public static final int XDM_UI_MODE_NONE = 0;
    public static final int XDM_USER_INIT = 1;
    public static final int XDM_WAIT_WIFI_MODE_NONE = 0;
    public static final int XDM_WAIT_WIFI_MODE_OVERSIZE = 1;
    public static final int XDM_WAIT_WIFI_MODE_RETRY = 2;
    public static final int XDM_WBXML_ENCODING_BUF_SIZE = 7168;
    public static final int XDM_WBXML_MAX_MESSAGE_SIZE = 5120;
    public static final int XDM_WBXML_MAX_OBJECT_SIZE = 1048576;

    public enum XDMAtomicStep {
        XDM_ATOMIC_NONE,
        XDM_ATOMIC_STEP_ROLLBACK,
        XDM_ATOMIC_STEP_NOT_EXEC
    }

    public enum XDMProcessingState {
        XDM_PROC_NONE,
        XDM_PROC_SYNCHDR,
        XDM_PROC_ALERT,
        XDM_PROC_STATUS,
        XDM_PROC_RESULTS,
        XDM_PROC_GET,
        XDM_PROC_EXEC,
        XDM_PROC_ADD,
        XDM_PROC_REPLACE,
        XDM_PROC_DELETE,
        XDM_PROC_COPY
    }

    public enum XDMSyncMLState {
        XDM_STATE_NONE,
        XDM_STATE_INIT,
        XDM_STATE_CLIENT_INIT_MGMT,
        XDM_STATE_PROCESSING,
        XDM_STATE_GENERIC_ALERT,
        XDM_STATE_GENERIC_ALERT_REPORT,
        XDM_STATE_ABORT_ALERT,
        XDM_STATE_FINISH
    }
}
