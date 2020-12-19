package com.sec.android.diagmonagent.common.logger;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class AppLog {
    public static String SERVICE_ID_TAG = "";
    private static final String TAG = "DIAGMON_SDK";
    private static Context mContext = null;
    private static String mServiceId = "";
    private static IAppLogData sInstance;

    public static int v(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            return Log.v(TAG, str);
        }
        return sInstance.v(SERVICE_ID_TAG, str);
    }

    public static int d(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            return Log.d(TAG, str);
        }
        return sInstance.d(SERVICE_ID_TAG, str);
    }

    public static int i(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            return Log.i(TAG, str);
        }
        return sInstance.i(SERVICE_ID_TAG, str);
    }

    public static int w(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            return Log.w(TAG, str);
        }
        return sInstance.w(SERVICE_ID_TAG, str);
    }

    public static int e(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            return Log.e(TAG, str);
        }
        return sInstance.e(SERVICE_ID_TAG, str);
    }

    public static void initLogger(Context context, String str) {
        try {
            mContext = context;
            mServiceId = str;
            if (sInstance == null) {
                sInstance = new AppLogData(mContext);
                if (!TextUtils.isEmpty(mServiceId)) {
                    SERVICE_ID_TAG = mServiceId;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
