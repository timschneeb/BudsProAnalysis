package com.samsung.android.sdk.mobileservice.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.samsung.android.sdk.mobileservice.SeMobileService;
import com.samsung.android.sdk.mobileservice.util.SdkLog;

public class CommonUtils {
    public static final int MAX_IMAGE_SIZE = 819200;
    public static final String SAMSUNG_ACCOUNT_PACKAGE_NAME = "com.osp.app.signin";
    private static final String TAG = "CommonUtils";

    public static String getMetaData(Context context, String str, String str2) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            SdkLog.s(e);
            applicationInfo = null;
        }
        Bundle bundle = applicationInfo != null ? applicationInfo.metaData : null;
        if (bundle != null) {
            return bundle.getString(str2);
        }
        return null;
    }

    public static boolean isAgentSupportMinVersion(int i, Context context) {
        return SeMobileService.getAgentVersion(context) >= i;
    }

    public static boolean isAgentNoMoreSupportedVersion(int i, Context context) {
        return SeMobileService.getAgentVersion(context) >= i;
    }

    public static boolean isSaAgentSupportMinVersion(int i, Context context) {
        return isStandAloneSamsungAccountSupported(context) && SeMobileService.getSaAgentVersion(context) >= i;
    }

    public static boolean isStandAloneSamsungAccountSupported(Context context) {
        return getSupportStandAloneFromMetaData(context);
    }

    private static boolean getSupportStandAloneFromMetaData(Context context) {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(SAMSUNG_ACCOUNT_PACKAGE_NAME, 128);
        } catch (PackageManager.NameNotFoundException e) {
            SdkLog.s(e);
            applicationInfo = null;
        }
        boolean z = false;
        if (!(applicationInfo == null || (bundle = applicationInfo.metaData) == null)) {
            z = bundle.getBoolean("SupportStandAlone", false);
        }
        SdkLog.d(TAG, "getSupportStandAloneFromMetaData : " + z);
        return z;
    }
}
