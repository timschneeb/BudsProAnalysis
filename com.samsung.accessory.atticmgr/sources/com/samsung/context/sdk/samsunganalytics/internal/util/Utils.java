package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import com.samsung.context.sdk.samsunganalytics.AnalyticsException;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.property.PropertyLogBuildClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingLogBuildClient;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static final int DMA_DATA_PROVIDE_VERSION = 710000000;
    private static final int DMA_NO_EXIST = 0;
    public static final String DMA_PKG_NAME = "com.sec.android.diagmonagent";
    private static int DMA_VERSION = -1;
    public static final String LOG_DELIMITER = "\u000e";
    private static final int NOT_SET = -1;
    private static BroadcastReceiver br;

    public static boolean isEngBin() {
        return Build.TYPE.equals("eng");
    }

    public static void throwException(String str) {
        if (!isEngBin()) {
            Debug.LogE(str);
            return;
        }
        throw new AnalyticsException(str);
    }

    public static long getDaysAgo(int i) {
        return Long.valueOf(System.currentTimeMillis()).longValue() - (((long) i) * 86400000);
    }

    public static boolean compareDays(int i, Long l) {
        return Long.valueOf(System.currentTimeMillis()).longValue() > l.longValue() + (((long) i) * 86400000);
    }

    public static boolean compareHours(int i, Long l) {
        return Long.valueOf(System.currentTimeMillis()).longValue() > l.longValue() + (((long) i) * 3600000);
    }

    public static boolean isSendProperty(Context context) {
        if (!compareDays(1, Long.valueOf(Preferences.getPreferences(context).getLong(Preferences.PROPERTY_SENT_DATE, 0)))) {
            Debug.LogD("do not send property < 1day");
            return false;
        }
        Preferences.getPreferences(context).edit().putLong(Preferences.PROPERTY_SENT_DATE, System.currentTimeMillis()).apply();
        return true;
    }

    public static LogType getTypeForServer(String str) {
        return "dl".equals(str) ? LogType.DEVICE : LogType.UIX;
    }

    public static boolean isDiagnosticAgree(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "samsung_errorlog_agree", 0) == 1;
    }

    public static int getDMAversion(Context context) {
        if (DMA_VERSION == -1) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(DMA_PKG_NAME, 0);
                DMA_VERSION = packageInfo.versionCode;
                Debug.LogD("Utils", "dma pkg:" + packageInfo.versionCode);
            } catch (PackageManager.NameNotFoundException unused) {
                Debug.LogD("Utils", "DMA Client is not exist");
                DMA_VERSION = 0;
            }
        }
        return DMA_VERSION;
    }

    public static boolean isDMADataProvideVersion(Context context) {
        return DMA_DATA_PROVIDE_VERSION <= getDMAversion(context);
    }

    public static void sendSettings(Context context, Configuration configuration) {
        SingleThreadExecutor.getInstance().execute(new SettingLogBuildClient(context, configuration));
    }

    public static void sendProperties(Context context, Configuration configuration) {
        SingleThreadExecutor.getInstance().execute(new PropertyLogBuildClient(context, configuration));
    }

    public static void registerReceiver(Context context, final Configuration configuration) {
        Debug.LogENG("register BR");
        if (br == null) {
            br = new BroadcastReceiver() {
                /* class com.samsung.context.sdk.samsunganalytics.internal.util.Utils.AnonymousClass1 */

                public void onReceive(Context context, Intent intent) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("receive BR ");
                    sb.append(intent != null ? intent.getAction() : "null");
                    Debug.LogENG(sb.toString());
                    if (intent != null && "android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
                        Utils.sendSettings(context, configuration);
                        Utils.sendProperties(context, configuration);
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            context.registerReceiver(br, intentFilter);
            return;
        }
        Debug.LogENG("BR is already registered");
    }

    public static String makeDelimiterString(Map<String, String> map, Depth depth) {
        String str = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            str = ((str == null ? entry.getKey().toString() : (str + depth.getCollectionDLM()) + ((Object) entry.getKey())) + depth.getKeyValueDLM()) + ((Object) entry.getValue());
        }
        return str;
    }

    public static Map<String, String> parseDelimiterString(String str, Depth depth) {
        HashMap hashMap = new HashMap();
        for (String str2 : str.split(depth.getCollectionDLM())) {
            String[] split = str2.split(depth.getKeyValueDLM());
            if (split.length > 1) {
                hashMap.put(split[0], split[1]);
            }
        }
        return hashMap;
    }

    public enum Depth {
        ONE_DEPTH("\u0002", "\u0003"),
        TWO_DEPTH("\u0004", "\u0005"),
        THREE_DEPTH("\u0006", "\u0007");
        
        private String collDlm;
        private String keyvalueDlm;

        private Depth(String str, String str2) {
            this.collDlm = str;
            this.keyvalueDlm = str2;
        }

        public String getCollectionDLM() {
            return this.collDlm;
        }

        public String getKeyValueDLM() {
            return this.keyvalueDlm;
        }
    }
}
