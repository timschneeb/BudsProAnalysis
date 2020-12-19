package com.sec.android.diagmonagent.log.provider.a;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.sec.android.diagmonagent.log.provider.a;
import com.sec.android.diagmonagent.log.provider.d;
import java.io.File;

public class b {
    public static boolean a(Context context, Bundle bundle) {
        if (bundle.getString("serviceId") == null || bundle.getString("serviceId").isEmpty()) {
            Log.w(a.f1964a, "Service ID has to be set");
            return true;
        } else if (bundle.getString("serviceAgreeType") == null || bundle.getString("serviceAgreeType").isEmpty()) {
            Log.w(a.f1964a, "You have to agree to terms and conditions");
            return true;
        } else {
            String string = bundle.getString("serviceAgreeType");
            if (string.equals("S")) {
                String str = a.f1964a;
                Log.d(str, "Agreement value : " + string);
            } else if (string.equals("D")) {
                String str2 = a.f1964a;
                Log.d(str2, "Agreement value : " + string);
                if (bundle.getString("deviceId") != null && !bundle.getString("deviceId").isEmpty()) {
                    Log.w(a.f1964a, "You can't use setDeviceId API if you used setAgree as Diagnostic agreement");
                    return true;
                }
            } else {
                String str3 = a.f1964a;
                Log.w(str3, "Undefined agreement : " + string);
                return true;
            }
            if (bundle.getString("serviceVersion") == null || bundle.getString("serviceVersion").isEmpty()) {
                Log.w(a.f1964a, "No service version");
                return true;
            } else if (bundle.getString("sdkVersion").isEmpty()) {
                Log.w(a.f1964a, "No SDK version");
                return true;
            } else if (!bundle.getString("sdkType").isEmpty()) {
                return false;
            } else {
                Log.w(a.f1964a, "No SDK type");
                return true;
            }
        }
    }

    public static boolean a(Context context, Bundle bundle, Bundle bundle2) {
        String str;
        StringBuilder sb;
        if (bundle.getString("serviceId") == null || bundle.getString("serviceId").isEmpty()) {
            Log.w(a.f1964a, "Service ID has to be set");
            return true;
        } else if (bundle.getString("serviceVersion") == null || bundle.getString("serviceVersion").isEmpty()) {
            Log.w(a.f1964a, "No Service version");
            return true;
        } else if (bundle.getString("sdkVersion").isEmpty()) {
            Log.w(a.f1964a, "No SDK version");
            return true;
        } else if (bundle.getString("sdkType").isEmpty()) {
            Log.w(a.f1964a, "No SDK type");
            return true;
        } else if (bundle.getString("serviceAgreeType") == null || bundle.getString("serviceAgreeType").isEmpty()) {
            Log.w(a.f1964a, "You have to agree to terms and conditions");
            return true;
        } else {
            String string = bundle.getString("serviceAgreeType");
            if (string.equals("S")) {
                str = a.f1964a;
                sb = new StringBuilder();
            } else if (string.equals("D")) {
                str = a.f1964a;
                sb = new StringBuilder();
            } else {
                String str2 = a.f1964a;
                Log.w(str2, "Undefined agreement : " + string);
                return true;
            }
            sb.append("Agreement : ");
            sb.append(string);
            Log.d(str, sb.toString());
            return false;
        }
    }

    public static boolean a(a aVar) {
        String str;
        String str2;
        if (aVar == null) {
            str = a.f1964a;
            str2 = "DiagMonConfiguration has to be set";
        } else if (aVar.g() == null || aVar.g().isEmpty()) {
            str = a.f1964a;
            str2 = "Service ID has to be set";
        } else if (aVar.a()) {
            return false;
        } else {
            str = a.f1964a;
            str2 = "You have to agree to terms and conditions";
        }
        Log.w(str, str2);
        return true;
    }

    public static boolean a(d dVar) {
        if (!TextUtils.isEmpty(dVar.c())) {
            return false;
        }
        Log.w(a.f1964a, "No Result code - you have to set");
        return true;
    }

    public static boolean a(String str) {
        File file = new File(str);
        return !file.isDirectory() || file.listFiles().length < 1;
    }
}
