package com.accessorydm.interfaces;

public interface XCommonInterface {
    public static final String PROGRESS_ACTIVITY_NAME = "XUIProgressActivity";
    public static final long WAKE_LOCK_TIMEOUT = 5000;
    public static final String XCOMMON_INTENT_POSTPONE_ACTION = "com.sec.android.fotaprovider.POSTPONED";
    public static final String XCOMMON_INTENT_STORAGE_SETTING = "android.settings.INTERNAL_STORAGE_SETTINGS";
    public static final String XCOMMON_INTENT_STORAGE_SMART_MANAGER = "com.samsung.android.sm.ACTION_STORAGE";
    public static final String XCOMMON_INTENT_WIFIONLY_FROM_FC = "sec.fotaprovider.intent.WIFIONLY";
    public static final String XCOMMON_INTENT_WIFI_SETTING = "android.settings.WIFI_SETTINGS";
    public static final int XDM_NOTIFY_OBSERVER = 1;
    public static final int XUI_MAX_POSTPONE_DEFAULT_COUNT = 3;

    public enum INIT_TYPE {
        INIT_TYPE_NONE,
        INIT_TYPE_PULL,
        INIT_TYPE_POLLING,
        INIT_TYPE_PUSH;
        
        private static final INIT_TYPE[] values = values();

        public static INIT_TYPE valueOf(int i) {
            try {
                return values[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
