package com.samsung.android.app.twatchmanager.log;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.samsung.android.app.twatchmanager.factory.FloatingFeatureFactory;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;

public class LoggerUtil {
    private static final String ACTION_LOGGING = "com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY";
    private static final String EXTRA_DATA = "data";
    private static final String LOGGER_PACKAGE = "com.samsung.android.providers.context";
    private static final String TAG = "LoggerUtil";

    public static class Builder {
        private static final int OFF = 0;
        private static final int ON = 1000;
        private static final boolean SUPPORT_LOGGING = (HostManagerUtils.isSamsungDevice() && FloatingFeatureFactory.get().getEnableStatus("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE"));
        private Context mContext;
        private String mExtra = null;
        private String mFeature;
        private long mValue = -1;

        public Builder(Context context, String str) {
            this.mContext = context;
            this.mFeature = str;
        }

        public Intent build() {
            Intent intent = new Intent(LoggerUtil.ACTION_LOGGING);
            intent.setPackage(LoggerUtil.LOGGER_PACKAGE);
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", "com.samsung.android.app.watchmanager");
            contentValues.put("feature", this.mFeature);
            String str = this.mExtra;
            if (str != null) {
                contentValues.put("extra", str);
            }
            long j = this.mValue;
            if (j >= 0) {
                contentValues.put("value", Long.valueOf(j));
            }
            intent.putExtra(LoggerUtil.EXTRA_DATA, contentValues);
            return intent;
        }

        public void buildAndSend() {
            if (SUPPORT_LOGGING) {
                this.mContext.sendBroadcast(build());
            }
        }

        public Builder setExtra(String str) {
            this.mExtra = str;
            return this;
        }

        public Builder setOnOffValue(boolean z) {
            this.mValue = z ? 1000 : 0;
            return this;
        }

        public Builder setValue(long j) {
            this.mValue = j;
            return this;
        }
    }

    public static int getVersionOfContextProviders(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            if (packageManager != null) {
                packageInfo = packageManager.getPackageInfo(LOGGER_PACKAGE, 128);
            }
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
            return -1;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d(TAG, "Could not find ContextProvider");
            return -1;
        }
    }

    public static void insertLog(Context context, String str) {
        insertLog(context, str, null, null);
    }

    public static void insertLog(Context context, String str, String str2) {
        insertLog(context, str, str2, null);
    }

    public static void insertLog(Context context, String str, String str2, String str3) {
        if (context != null && getVersionOfContextProviders(context) >= 2) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", "com.samsung.android.app.watchmanager");
                contentValues.put("feature", str);
                if (str2 != null) {
                    contentValues.put("extra", str2);
                }
                if (str3 != null) {
                    contentValues.put("value", str3);
                }
                Intent intent = new Intent(ACTION_LOGGING);
                intent.setPackage(LOGGER_PACKAGE);
                intent.putExtra(EXTRA_DATA, contentValues);
                context.sendBroadcast(intent);
            } catch (Exception e) {
                Log.e(TAG, "Error while using insertLog");
                Log.e(TAG, e.toString());
            }
        }
    }
}
