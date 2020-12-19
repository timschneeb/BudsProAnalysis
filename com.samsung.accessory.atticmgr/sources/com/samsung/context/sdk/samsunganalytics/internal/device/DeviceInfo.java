package com.samsung.context.sdk.samsunganalytics.internal.device;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DeviceInfo {
    private String androidVersion = "";
    private String appVersionName = "";
    private String brand = "";
    private String country = "";
    private String deviceModel = "";
    private String firmwareVersion = "";
    private String language = "";
    private String mcc = "";
    private String mnc = "";
    private String timeZoneOffset = "";

    public DeviceInfo(Context context) {
        String simOperator;
        Locale locale = context.getResources().getConfiguration().locale;
        this.country = locale.getDisplayCountry();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE);
        if (!(telephonyManager == null || (simOperator = telephonyManager.getSimOperator()) == null || simOperator.length() < 3)) {
            this.mcc = simOperator.substring(0, 3);
            this.mnc = simOperator.substring(3);
        }
        this.language = locale.getLanguage();
        this.androidVersion = Build.VERSION.RELEASE;
        this.brand = Build.BRAND;
        this.deviceModel = Build.MODEL;
        this.firmwareVersion = Build.VERSION.INCREMENTAL;
        this.timeZoneOffset = String.valueOf(TimeUnit.MILLISECONDS.toMinutes((long) TimeZone.getDefault().getRawOffset()));
        try {
            this.appVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Debug.LogException(getClass(), e);
        }
    }

    public String getMcc() {
        return this.mcc;
    }

    public String getMnc() {
        return this.mnc;
    }

    public String getCountry() {
        return this.country;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getAndroidVersion() {
        return this.androidVersion;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public String getAppVersionName() {
        return this.appVersionName;
    }

    public String getTimeZoneOffset() {
        return this.timeZoneOffset;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }
}
