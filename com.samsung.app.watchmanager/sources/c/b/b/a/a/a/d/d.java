package c.b.b.a.a.a.d;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.UserManager;
import android.text.TextUtils;
import c.b.b.a.a.a.i.a;
import c.b.b.a.a.a.i.e;
import c.b.b.a.a.c;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class d {

    /* renamed from: a  reason: collision with root package name */
    public static String f1738a = "RSSAV1wsc2s314SAamk";

    public static String a(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes("UTF-8"));
            return String.format(Locale.US, "%064x", new BigInteger(1, instance.digest()));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            a.a(d.class, e);
            return null;
        }
    }

    public static Map<String, String> a(Map<String, String> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (TextUtils.isEmpty(key)) {
                a.c("cd key is empty");
            } else {
                if (key.length() > 40) {
                    a.c("cd key length over:" + key);
                    key = key.substring(0, 40);
                }
                if (value != null && value.length() > 1024) {
                    a.c("cd value length over:" + value);
                    value = value.substring(0, 1024);
                }
                hashMap.put(key, value);
            }
        }
        return hashMap;
    }

    public static boolean a(Application application, c cVar) {
        UserManager userManager;
        if (application == null) {
            e.b("context cannot be null");
            return false;
        } else if (cVar == null) {
            e.b("Configuration cannot be null");
            return false;
        } else if (TextUtils.isEmpty(cVar.e())) {
            e.b("TrackingId is empty, set TrackingId");
            return false;
        } else if (!TextUtils.isEmpty(cVar.d()) || cVar.i()) {
            b.a(application, cVar);
            if (cVar.k()) {
                if (cVar.f() == null) {
                    e.b("If you want to use In App Logging, you should implement UserAgreement interface");
                    return false;
                } else if (b.b() == 2 && !a(application, "com.sec.spp.permission.TOKEN", false)) {
                    e.b("SamsungAnalytics2 need to define 'com.sec.spp.permission.TOKEN_XXXX' permission in AndroidManifest");
                    return false;
                }
            } else if (b.b() == 2 && !a(application, "com.sec.spp.permission.TOKEN", false)) {
                e.b("If you want to use DLC Logger, define 'com.sec.spp.permission.TOKEN_XXXX' permission in AndroidManifest");
                return false;
            } else if (!TextUtils.isEmpty(cVar.d())) {
                e.b("This mode is not allowed to set device Id");
                return false;
            }
            if (TextUtils.isEmpty(cVar.g())) {
                e.b("you should set the UI version");
                return false;
            } else if (Build.VERSION.SDK_INT < 24 || (userManager = (UserManager) application.getSystemService("user")) == null || userManager.isUserUnlocked()) {
                return true;
            } else {
                a.b("The user has not unlocked the device.");
                c cVar2 = new c(application, cVar);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                intentFilter.addAction("android.intent.action.USER_UNLOCKED");
                application.registerReceiver(cVar2, intentFilter);
                return false;
            }
        } else {
            e.b("Device Id is empty, set Device Id or enable auto device id");
            return false;
        }
    }

    public static boolean a(Context context) {
        String str;
        String str2;
        SharedPreferences.Editor editor;
        Cursor query;
        SharedPreferences a2 = c.b.b.a.a.a.i.c.a(context);
        boolean z = false;
        int i = a2.getInt("enable_device", 0);
        if (i != 0) {
            return i == 1;
        }
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
                    a.a("Floating feature is not supported (non-samsung device)");
                    a.a(d.class, e);
                    return false;
                }
            } catch (Exception unused) {
                a.a("DMA is not supported");
                a.a(d.class, e);
            }
        } catch (Throwable th) {
            if (query != null) {
                query.close();
            }
            throw th;
        }
        if (!z) {
            a.a("feature is not supported");
            editor = a2.edit().putInt("enable_device", 2);
        } else {
            a.a("cf feature is supported");
            editor = a2.edit().putInt("enable_device", 1);
        }
        editor.apply();
        return z;
    }

    public static boolean a(Context context, String str, boolean z) {
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
            a.a(d.class, e);
        }
        return false;
    }
}
