package com.sec.android.diagmonagent.log.ged.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.sec.android.diagmonagent.common.logger.AppLog;

public class PreferenceUtils {
    private static final String KEY_CURRENT_VERSION = "version";
    private static final String KEY_DEFAULT_MAX_FILE_COUNT = "maxFileCount";
    private static final String KEY_DEFAULT_MAX_FILE_SIZE = "maxFileSize";
    private static final String KEY_DEFAULT_UPLOAD_FILE = "uploadFile";
    private static final String KEY_JWT_TOKEN = "JWT_TOKEN";
    private static final String KEY_LAST_PD_UPDATED_TIME = "lastPDUpdatedTime";
    private static final String KEY_MAX_FILE_COUNT_VALUE = "maxFileCountValue";
    private static final String KEY_MAX_FILE_SIZE_ERROR_CODE = "maxFileSizeErrorCode";
    private static final String KEY_MAX_FILE_SIZE_SERVICE_VERSION = "maxFileSizeServiceVersion";
    private static final String KEY_MAX_FILE_SIZE_VALUE = "maxFileSizeValue";
    private static final String KEY_NEEDED_VERSION = "needed_version";
    private static final String KEY_POLLING_INTERVAL = "pollingInterval";
    private static final String KEY_REST_IDENTIFIER = "REST_IDENTIFIER";
    private static final String KEY_UPLOAD_FILE_ERROR_CODE = "uploadFileErrorCode";
    private static final String KEY_UPLOAD_FILE_SERVICE_VERSION = "uploadFileServiceVersion";
    private static final String KEY_UPLOAD_FILE_VALUE = "uploadFileValue";
    private static final String KEY_VERSION_INFO_URL = "version_info_url";
    private static final String PREFERENCE_NAME = "DIAGMON_PREFERENCE";

    public static String getRandomDeviceId(Context context) {
        return getDiagmonPreference(context, KEY_REST_IDENTIFIER, "");
    }

    public static void setRandomDeviceId(Context context, String str) {
        setDiagmonPreference(context, KEY_REST_IDENTIFIER, str);
    }

    public static void removeRandomDeviceId(Context context) {
        removeDiagmonPreference(context, KEY_REST_IDENTIFIER);
    }

    public static String getJwtToken(Context context) {
        return getDiagmonPreference(context, KEY_JWT_TOKEN, "");
    }

    public static void setJwtToken(Context context, String str) {
        setDiagmonPreference(context, KEY_JWT_TOKEN, str);
    }

    public static String getPolicyVersionInfoUrl(Context context, String str) {
        return getDiagmonPreference(context, KEY_VERSION_INFO_URL, str);
    }

    public static void setPolicyVersionInfoUrl(Context context, String str) {
        setDiagmonPreference(context, KEY_VERSION_INFO_URL, str);
    }

    public static String getCurrentPolicyVersion(Context context) {
        return getDiagmonPreference(context, "version", "0");
    }

    public static void setCurrentPolicyVersion(Context context, String str) {
        setDiagmonPreference(context, "version", str);
    }

    public static String getNeededPolicyVersion(Context context) {
        return getDiagmonPreference(context, KEY_NEEDED_VERSION, "0");
    }

    public static void setNeededPolicyVersion(Context context, String str) {
        setDiagmonPreference(context, KEY_NEEDED_VERSION, str);
    }

    public static String getPollingInterval(Context context) {
        return getDiagmonPreference(context, "pollingInterval", "1");
    }

    public static void setPollingInterval(Context context, String str) {
        setDiagmonPreference(context, "pollingInterval", str);
    }

    public static String getDefaultMaxFileCount(Context context) {
        return getDiagmonPreference(context, "maxFileCount", "10");
    }

    public static void setDefaultMaxFileCount(Context context, String str) {
        setDiagmonPreference(context, "maxFileCount", str);
    }

    public static String getDefaultMaxFileSize(Context context) {
        return getDiagmonPreference(context, "maxFileSize", "50");
    }

    public static void setDefaultMaxFileSize(Context context, String str) {
        setDiagmonPreference(context, "maxFileSize", str);
    }

    public static String getDefaultUploadFile(Context context) {
        return getDiagmonPreference(context, "uploadFile", "1");
    }

    public static void setDefaultUploadFile(Context context, String str) {
        setDiagmonPreference(context, "uploadFile", str);
    }

    public static String getMaxFileSizeValue(Context context) {
        return getDiagmonPreference(context, KEY_MAX_FILE_SIZE_VALUE, "");
    }

    public static void setMaxFileSizeValue(Context context, String str) {
        setDiagmonPreference(context, KEY_MAX_FILE_SIZE_VALUE, str);
    }

    public static String getMaxFileSizeServiceVersion(Context context) {
        return getDiagmonPreference(context, KEY_MAX_FILE_SIZE_SERVICE_VERSION, "");
    }

    public static void setMaxFileSizeServiceVersion(Context context, String str) {
        setDiagmonPreference(context, KEY_MAX_FILE_SIZE_SERVICE_VERSION, str);
    }

    public static String getMaxFileSizeErrorCode(Context context) {
        return getDiagmonPreference(context, KEY_MAX_FILE_SIZE_ERROR_CODE, "");
    }

    public static void setMaxFileSizeErrorCode(Context context, String str) {
        setDiagmonPreference(context, KEY_MAX_FILE_SIZE_ERROR_CODE, str);
    }

    public static String getUploadFileValue(Context context) {
        return getDiagmonPreference(context, KEY_UPLOAD_FILE_VALUE, "");
    }

    public static void setUploadFileValue(Context context, String str) {
        setDiagmonPreference(context, KEY_UPLOAD_FILE_VALUE, str);
    }

    public static String getUploadFileServiceVersion(Context context) {
        return getDiagmonPreference(context, KEY_UPLOAD_FILE_SERVICE_VERSION, "");
    }

    public static void setUploadFileServiceVersion(Context context, String str) {
        setDiagmonPreference(context, KEY_UPLOAD_FILE_SERVICE_VERSION, str);
    }

    public static String getUploadFileErrorCode(Context context) {
        return getDiagmonPreference(context, KEY_UPLOAD_FILE_ERROR_CODE, "");
    }

    public static void setUploadFileErrorCode(Context context, String str) {
        setDiagmonPreference(context, KEY_UPLOAD_FILE_ERROR_CODE, str);
    }

    public static String getMaxFileCountValue(Context context) {
        return getDiagmonPreference(context, KEY_MAX_FILE_COUNT_VALUE, "");
    }

    public static void setMaxFileCountValue(Context context, String str) {
        setDiagmonPreference(context, KEY_MAX_FILE_COUNT_VALUE, str);
    }

    public static long getLastPDUpdatedTime(Context context) {
        return getDiagmonPreference(context, KEY_LAST_PD_UPDATED_TIME, 0);
    }

    public static void setLastPDUpdatedTime(Context context, long j) {
        setDiagmonPreference(context, KEY_LAST_PD_UPDATED_TIME, j);
    }

    public static void initPolicyPreference(Context context) {
        setUploadFileValue(context, "");
        setUploadFileServiceVersion(context, "");
        setUploadFileErrorCode(context, "");
        setMaxFileSizeValue(context, "");
        setMaxFileSizeServiceVersion(context, "");
        setMaxFileSizeErrorCode(context, "");
        setMaxFileCountValue(context, "");
    }

    private static String getDiagmonPreference(Context context, String str, String str2) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(str, str2);
    }

    private static void setDiagmonPreference(Context context, String str, String str2) {
        if (str2 == null) {
            AppLog.d(str + " - value is null");
            return;
        }
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    private static long getDiagmonPreference(Context context, String str, long j) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getLong(str, j);
    }

    private static void setDiagmonPreference(Context context, String str, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        edit.putLong(str, j);
        edit.apply();
    }

    private static void removeDiagmonPreference(Context context, String str) {
        context.getSharedPreferences(PREFERENCE_NAME, 0).edit().remove(str).apply();
    }
}
