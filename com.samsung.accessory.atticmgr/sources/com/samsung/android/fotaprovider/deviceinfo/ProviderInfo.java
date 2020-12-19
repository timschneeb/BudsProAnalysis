package com.samsung.android.fotaprovider.deviceinfo;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ProviderInfo {
    private static final String BUILD_MANUFACTURER = Build.MANUFACTURER;
    private static final String DEFAULT_MCC = "901";
    private static final String DEFAULT_MNC = "00";
    private static final String EXCEPTION_MCC_LIST = "001,002,999,@65";
    private static final String EXCEPTION_MNC_LIST = "@5";
    private static final String SAMSUNG = "SAMSUNG";
    private static String deviceType = "";
    private static String salesCode = "";

    public String getDeviceId() {
        return "";
    }

    public boolean isSamsungDevice() {
        return BUILD_MANUFACTURER.equalsIgnoreCase(SAMSUNG);
    }

    public String getSalesCode() {
        if (TextUtils.isEmpty(salesCode)) {
            try {
                salesCode = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/system/bin/getprop", "ro.csc.sales_code"}).getInputStream(), StandardCharsets.UTF_8)).readLine();
            } catch (Exception unused) {
                salesCode = "";
            }
        }
        Log.D("SalesCode: " + salesCode);
        return salesCode;
    }

    private String getDeviceType() {
        if (TextUtils.isEmpty(deviceType)) {
            try {
                deviceType = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/system/bin/getprop", "ro.build.characteristics"}).getInputStream(), StandardCharsets.UTF_8)).readLine();
            } catch (Exception unused) {
                deviceType = XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE;
            }
        }
        Log.D("DeviceType: " + deviceType);
        return deviceType;
    }

    public boolean isTablet() {
        return getDeviceType().contains("tablet");
    }

    public String getAppVersion() {
        try {
            PackageInfo packageInfo = FotaProviderInitializer.getContext().getPackageManager().getPackageInfo(FotaProviderInitializer.getContext().getPackageName(), 0);
            if (!TextUtils.isEmpty(packageInfo.versionName)) {
                return packageInfo.versionName;
            }
            return "";
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public String getSimMCC() {
        try {
            String substring = getSimOperator().substring(0, 3);
            if (!EXCEPTION_MCC_LIST.contains(substring)) {
                return substring;
            }
            Log.I("Change Except MCC to Default");
            return DEFAULT_MCC;
        } catch (Exception unused) {
            return "";
        }
    }

    public String getSimMNC() {
        try {
            String substring = getSimOperator().substring(3);
            if (!EXCEPTION_MNC_LIST.equals(substring)) {
                return substring;
            }
            Log.I("Change Except MNC to Default");
            return DEFAULT_MNC;
        } catch (Exception unused) {
            return "";
        }
    }

    public String getNetworkMCC() {
        try {
            return getNetworkOperator().substring(0, 3);
        } catch (Exception unused) {
            return "";
        }
    }

    public String getNetworkMNC() {
        try {
            return getNetworkOperator().substring(3);
        } catch (Exception unused) {
            return "";
        }
    }

    private String getSimOperator() {
        TelephonyManager telephonyManager = (TelephonyManager) FotaProviderInitializer.getContext().getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE);
        return (telephonyManager == null || telephonyManager.getSimState() != 5) ? "" : telephonyManager.getSimOperator();
    }

    private String getNetworkOperator() {
        TelephonyManager telephonyManager = (TelephonyManager) FotaProviderInitializer.getContext().getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE);
        return telephonyManager != null ? telephonyManager.getNetworkOperator() : "";
    }
}
