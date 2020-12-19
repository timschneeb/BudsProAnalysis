package com.sec.android.diagmonagent.log.provider.a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import c.b.b.a.a.b;
import com.samsung.android.app.twatchmanager.update.UpdateCheckTask;
import com.sec.android.diagmonagent.log.provider.c;
import com.sec.android.diagmonagent.log.provider.d;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class a {

    /* renamed from: a  reason: collision with root package name */
    public static final String f1964a = ("DIAGMON_SDK[" + c.f() + "]");

    /* renamed from: b  reason: collision with root package name */
    static boolean f1965b = false;

    public static int a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0).versionCode < 600000000 ? 1 : 2;
        } catch (PackageManager.NameNotFoundException e) {
            String str = f1964a;
            Log.w(str, "DiagMonAgent isn't found: " + e.getMessage());
            return 0;
        }
    }

    public static Bundle a(Context context, com.sec.android.diagmonagent.log.provider.a aVar, d dVar) {
        Bundle bundle = new Bundle();
        try {
            bundle.putParcelable("fileDescriptor", a(context, dVar));
            bundle.putString("serviceId", aVar.g());
            bundle.putString("serviceVersion", aVar.h());
            bundle.putString("serviceDefinedKey", dVar.k());
            bundle.putString("errorCode", dVar.c());
            bundle.putBoolean("wifiOnly", dVar.h());
            bundle.putString("errorDesc", dVar.b());
            bundle.putString("relayClientVersion", dVar.j());
            bundle.putString("relayClientType", dVar.i());
            bundle.putString("extension", dVar.d());
            bundle.putString("deviceId", aVar.e());
            bundle.putString("serviceAgreeType", aVar.b());
            bundle.putString("sdkVersion", c.f());
            bundle.putString("sdkType", c.g());
            bundle.putString("memory", dVar.g().toString());
            bundle.putString("storage", dVar.e().toString());
            Log.d(f1964a, "Generated EventObject");
            return bundle;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bundle a(com.sec.android.diagmonagent.log.provider.a aVar) {
        Bundle bundle = new Bundle();
        bundle.putString("serviceId", aVar.g());
        bundle.putString("serviceVersion", b(aVar.c()));
        bundle.putString("serviceAgreeType", aVar.b());
        bundle.putString("deviceId", aVar.e());
        bundle.putString("trackingId", aVar.i());
        bundle.putString("sdkVersion", c.f());
        bundle.putString("sdkType", c.g());
        Log.i(f1964a, "generated SR object");
        return bundle;
    }

    public static ParcelFileDescriptor a(Context context, d dVar) {
        ParcelFileDescriptor parcelFileDescriptor;
        if (dVar.f() == null || TextUtils.isEmpty(dVar.f())) {
            Log.w(f1964a, "No Log Path, You have to set LogPath to report logs");
            throw new IOException("Not found");
        }
        try {
            String valueOf = String.valueOf(System.currentTimeMillis());
            File file = new File(context.getFilesDir().getAbsolutePath() + "/zip");
            file.mkdir();
            String absolutePath = file.getAbsolutePath();
            String str = absolutePath + "/" + valueOf + ".zip";
            c.a(dVar.f(), str);
            parcelFileDescriptor = null;
            try {
                parcelFileDescriptor = ParcelFileDescriptor.open(new File(str), 268435456);
                dVar.d(str);
                Log.d(f1964a, "Zipping logs is completed");
                Log.d(f1964a, "Zipped file size : " + String.valueOf(parcelFileDescriptor.getStatSize()));
                return parcelFileDescriptor;
            } catch (IOException e) {
                Log.w(f1964a, e.getMessage());
            } catch (Throwable unused) {
            }
        } catch (Exception e2) {
            Log.w(f1964a, "Zipping failure");
            Log.w(f1964a, "Exception : " + e2.getMessage());
            throw e2;
        }
        return parcelFileDescriptor;
    }

    public static void a(Bundle bundle) {
        String str;
        String str2;
        try {
            String string = bundle.getString("serviceId");
            String string2 = bundle.getString("result");
            String string3 = bundle.getString("cause");
            if (string3 == null) {
                String str3 = f1964a;
                Log.i(str3, "Service ID : " + string + ", results : " + string2);
                return;
            }
            String str4 = f1964a;
            Log.i(str4, "Service ID : " + string + ", Results : " + string2 + ", Cause : " + string3);
        } catch (NullPointerException e) {
            str2 = f1964a;
            str = e.getMessage();
            Log.w(str2, str);
        } catch (Exception e2) {
            str2 = f1964a;
            str = e2.getMessage();
            Log.w(str2, str);
        }
    }

    public static void a(String str) {
        String str2;
        StringBuilder sb;
        String str3;
        File file = new File(str);
        if (!file.exists()) {
            str3 = f1964a;
            sb = new StringBuilder();
            str2 = "File is not found : ";
        } else if (file.delete()) {
            String str4 = f1964a;
            Log.d(str4, "Removed zipFile : " + str);
            return;
        } else {
            str3 = f1964a;
            sb = new StringBuilder();
            str2 = "Coudn't removed zipFile : ";
        }
        sb.append(str2);
        sb.append(str);
        Log.w(str3, sb.toString());
    }

    public static Intent b(Context context, com.sec.android.diagmonagent.log.provider.a aVar, d dVar) {
        JSONObject jSONObject = new JSONObject();
        Intent intent = c(context) == 1000 ? new Intent("com.sec.android.diagmonagent.intent.REPORT_ERROR_V2") : new Intent("com.sec.android.diagmonagent.intent.REPORT_ERROR_APP");
        Bundle bundle = new Bundle();
        intent.addFlags(32);
        bundle.putBundle("DiagMon", new Bundle());
        bundle.getBundle("DiagMon").putBundle("CFailLogUpload", new Bundle());
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putString("ServiceID", aVar.g());
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putBundle("Ext", new Bundle());
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ClientV", b(context));
        if (!TextUtils.isEmpty(dVar.i())) {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("RelayClient", dVar.i());
        }
        if (!TextUtils.isEmpty(dVar.j())) {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("RelayClientV", dVar.j());
        }
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("UiMode", UpdateCheckTask.RESULT_CANT_FIND_APP);
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ResultCode", dVar.c());
        if (!TextUtils.isEmpty(dVar.k())) {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("EventID", dVar.k());
        }
        try {
            jSONObject.put("SasdkV", b.f1812b);
            jSONObject.put("SdkV", c.f());
            jSONObject.put("TrackingID", aVar.i());
            jSONObject.put("Description", dVar.b());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("Description", jSONObject.toString());
        if (dVar.h()) {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("WifiOnlyFeature", "1");
        } else {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("WifiOnlyFeature", UpdateCheckTask.RESULT_CANT_FIND_APP);
        }
        intent.putExtra("uploadMO", bundle);
        intent.setFlags(32);
        Log.i(f1964a, "EventObject is generated");
        return intent;
    }

    public static String b(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager != null ? packageManager.getPackageInfo(context.getPackageName(), 0).versionName : "";
        } catch (PackageManager.NameNotFoundException unused) {
            String str = f1964a;
            Log.e(str, context.getPackageName() + " is not found");
            return "";
        }
    }

    public static int c(Context context) {
        return context.getApplicationInfo().uid;
    }
}
