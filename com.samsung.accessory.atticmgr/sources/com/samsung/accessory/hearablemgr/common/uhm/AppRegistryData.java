package com.samsung.accessory.hearablemgr.common.uhm;

public class AppRegistryData {
    public String appName;
    public String deviceId;
    public String packageName;
    public String version;

    public AppRegistryData(String str, String str2, String str3, String str4) {
        this.packageName = str;
        this.appName = str2;
        this.version = str3;
        this.deviceId = str4;
    }
}
