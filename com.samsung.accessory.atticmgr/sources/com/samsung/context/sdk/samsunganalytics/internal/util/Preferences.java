package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String APP_PREF_NAMES = "AppPrefs";
    public static final String PREFS_KEY_APP_VERSION = "appVersion";
    public static final String PREFS_KEY_DID = "deviceId";
    public static final String PREFS_KEY_DID_TYPE = "auidType";
    public static final String PREFS_KEY_ENABLE_DEVICE = "enable_device";
    public static final String PREFS_KEY_SEND_COMMON_SUCCESS = "sendCommonSuccess";
    public static final String PREFS_KEY_SEND_COMMON_TIME = "sendCommonTime";
    public static final String PROPERTY_PREFS_NAME = "SAProperties";
    public static final String PROPERTY_SENT_DATE = "property_sent_date";
    public static final String SHARED_PREFS_NAME = "SamsungAnalyticsPrefs";
    public static final String STATUS_SENT_DATE = "status_sent_date";
    public static final String TERMS_PREF_NAME = "SATerms";

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, 0);
    }

    public static SharedPreferences getPropertyPreferences(Context context) {
        return context.getSharedPreferences(PROPERTY_PREFS_NAME, 0);
    }
}
