package com.samsung.android.sdk.mobileservice.common;

public class CommonConstants {
    public static final String API_NAME_ACTIVITY = "ActivityApi";
    public static final String API_NAME_AUTH = "AuthApi";
    public static final String API_NAME_BUDDY = "BuddyApi";
    public static final String API_NAME_FEEDBACK = "FeedbackApi";
    public static final String API_NAME_GROUP = "GroupApi";
    public static final String API_NAME_PLACE = "PlaceApi";
    public static final String API_NAME_PROFILE = "ProfileApi";
    public static final String API_NAME_SHARE = "ShareApi";
    public static final String API_NAME_SOCIAL = "SocialApi";
    public static final int AUTHORIZED_STATUS_ACCOUNT_ONLY = 1;
    public static final int AUTHORIZED_STATUS_ALL = 3;
    public static final int AUTHORIZED_STATUS_NONE = 0;
    public static final String META_DATA_ACCOUNT_APP_ID = "account_app_id";
    public static final String META_DATA_APP_ID = "com.samsung.android.mobileservice.AppId";
    public static final int REQUEST_FAILURE = -1;
    public static final int REQUEST_FAILURE_NOT_CONNECTED_SERVICE = -8;
    public static final int REQUEST_FAILURE_NOT_SUPPORTED_AGENT_VERSION = -7;
    public static final int REQUEST_SUCCESS = 1;
    public static final int SERVICE_ID_UNKNOWN = -1;
    public static final String SERVICE_NAME_AUTH = "AuthService";
    public static final String SERVICE_NAME_COMMON = "Common";
    public static final String SERVICE_NAME_PLACE = "PlaceService";
    public static final String SERVICE_NAME_PROFILE = "ProfileService";
    public static final String SERVICE_NAME_SA_COMMON = "SaCommon";
    public static final String SERVICE_NAME_SOCIAL = "SocialService";

    public interface SupportedApiMinVersion {
        public static final int VERSION_10_0 = 1000000000;
        public static final int VERSION_10_1 = 1010000000;
        public static final int VERSION_10_5 = 1050000000;
        public static final int VERSION_10_6 = 1060000000;
        public static final int VERSION_11_0 = 1100000000;
        public static final int VERSION_11_1 = 1110000000;
        public static final int VERSION_11_2 = 1120000000;
        public static final int VERSION_4_1 = 410000000;
        public static final int VERSION_4_2 = 420000000;
    }
}
