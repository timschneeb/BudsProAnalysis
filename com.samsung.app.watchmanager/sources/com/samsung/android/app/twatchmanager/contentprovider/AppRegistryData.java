package com.samsung.android.app.twatchmanager.contentprovider;

public class AppRegistryData {
    public String appName;
    public String deviceId;
    public String packagename;
    public int updateCancelCount;
    public String version;

    public AppRegistryData(String str, String str2, String str3, int i, String str4) {
        this.packagename = str;
        this.appName = str2;
        this.updateCancelCount = i;
        this.deviceId = str4;
        this.version = str3;
    }

    public String toString() {
        return (((("\nDB::[-- AppRegistryData --]" + "\nDB::packagename : " + this.packagename) + "\nDB::appName : " + this.appName) + "\nDB::deviceId : " + this.deviceId) + "\nDB::updateCancelCount : " + this.updateCancelCount) + "\nDB::version : " + this.version;
    }
}
