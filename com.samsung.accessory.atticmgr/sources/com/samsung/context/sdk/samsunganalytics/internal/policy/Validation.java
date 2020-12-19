package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.UserManager;
import android.text.TextUtils;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Validation {
    public static String SALT = "RSSAV1wsc2s314SAamk";

    public static Map<String, String> checkSizeLimit(Map<String, String> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (TextUtils.isEmpty(key)) {
                Debug.LogENG("cd key is empty");
            } else {
                if (key.length() > 40) {
                    Debug.LogENG("cd key length over:" + key);
                    key = key.substring(0, 40);
                }
                if (value != null && value.length() > 1024) {
                    Debug.LogENG("cd value length over:" + value);
                    value = value.substring(0, 1024);
                }
                hashMap.put(key, value);
            }
        }
        return hashMap;
    }

    public static boolean isValidConfig(final Application application, final Configuration configuration) {
        UserManager userManager;
        if (application == null) {
            Utils.throwException("context cannot be null");
            return false;
        } else if (configuration == null) {
            Utils.throwException("Configuration cannot be null");
            return false;
        } else if (TextUtils.isEmpty(configuration.getTrackingId())) {
            Utils.throwException("TrackingId is empty, set TrackingId");
            return false;
        } else if (TextUtils.isEmpty(configuration.getDeviceId()) && !configuration.isEnableAutoDeviceId()) {
            Utils.throwException("Device Id is empty, set Device Id or enable auto device id");
            return false;
        } else if (-1 == PolicyUtils.setSenderType(application, configuration)) {
            Debug.LogD("SenderType is None");
            return false;
        } else if (PolicyUtils.getSenderType() != 2 || hasPermission(application, "com.sec.spp.permission.TOKEN", false)) {
            if (configuration.isEnableUseInAppLogging()) {
                if (configuration.getUserAgreement() == null) {
                    Utils.throwException("If you want to use In App Logging, you should implement UserAgreement interface");
                    return false;
                }
            } else if (!TextUtils.isEmpty(configuration.getDeviceId())) {
                Utils.throwException("This mode is not allowed to set device Id");
                return false;
            }
            if (TextUtils.isEmpty(configuration.getVersion())) {
                Utils.throwException("you should set the UI version");
                return false;
            } else if (Build.VERSION.SDK_INT < 24 || (userManager = (UserManager) application.getSystemService("user")) == null || userManager.isUserUnlocked()) {
                return true;
            } else {
                Debug.LogE("The user has not unlocked the device.");
                AnonymousClass1 r1 = new BroadcastReceiver() {
                    /* class com.samsung.context.sdk.samsunganalytics.internal.policy.Validation.AnonymousClass1 */

                    public void onReceive(Context context, Intent intent) {
                        Debug.LogD("receive " + intent.getAction());
                        SamsungAnalytics.setConfiguration(application, configuration);
                    }
                };
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                intentFilter.addAction("android.intent.action.USER_UNLOCKED");
                application.registerReceiver(r1, intentFilter);
                return false;
            }
        } else {
            Utils.throwException("SamsungAnalytics2 need to define 'com.sec.spp.permission.TOKEN_XXXX' permission in AndroidManifest");
            return false;
        }
    }

    public static boolean isLoggingEnableDevice(Context context) {
        String str;
        String str2;
        Cursor query;
        SharedPreferences preferences = Preferences.getPreferences(context);
        boolean z = false;
        int i = preferences.getInt(Preferences.PREFS_KEY_ENABLE_DEVICE, 0);
        if (i == 0) {
            if (Build.VERSION.SDK_INT > 23) {
                str2 = "com.samsung.android.feature.SemFloatingFeature";
                str = "getBoolean";
            } else {
                str2 = "com.samsung.android.feature.FloatingFeature";
                str = "getEnableStatus";
            }
            try {
                Class<?> cls = Class.forName(str2);
                z = ((Boolean) cls.getMethod(str, String.class).invoke(cls.getMethod("getInstance", null).invoke(null, new Object[0]), "SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")).booleanValue();
            } catch (Exception e) {
                try {
                    if (Build.VERSION.SDK_INT >= 29) {
                        query = context.getContentResolver().query(Uri.parse("content://com.sec.android.log.diagmonagent.sa/check/diagnostic"), null, null, null);
                        if (query != null) {
                            query.moveToNext();
                            if (1 == query.getInt(0)) {
                                z = true;
                            }
                        }
                        if (query != null) {
                            query.close();
                        }
                    } else {
                        Debug.LogD("Floating feature is not supported (non-samsung device)");
                        Debug.LogException(Validation.class, e);
                        return false;
                    }
                } catch (Exception unused) {
                    Debug.LogD("DMA is not supported");
                    Debug.LogException(Validation.class, e);
                }
            } catch (Throwable th) {
                if (query != null) {
                    query.close();
                }
                throw th;
            }
            if (!z) {
                Debug.LogD("feature is not supported");
                preferences.edit().putInt(Preferences.PREFS_KEY_ENABLE_DEVICE, 2).apply();
                return z;
            }
            Debug.LogD("cf feature is supported");
            preferences.edit().putInt(Preferences.PREFS_KEY_ENABLE_DEVICE, 1).apply();
            return z;
        } else if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasPermission(Context context, String str, boolean z) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
            if (packageInfo.requestedPermissions != null) {
                String[] strArr = packageInfo.requestedPermissions;
                for (String str2 : strArr) {
                    if (z) {
                        if (str2.equalsIgnoreCase(str)) {
                            return true;
                        }
                    } else if (str2.startsWith(str)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Debug.LogException(Validation.class, e);
        }
        return false;
    }

    public static String sha256(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes(HttpNetworkInterface.XTP_HTTP_UTF8));
            return String.format(Locale.US, "%064x", new BigInteger(1, instance.digest()));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Debug.LogException(Validation.class, e);
            return null;
        }
    }
}
