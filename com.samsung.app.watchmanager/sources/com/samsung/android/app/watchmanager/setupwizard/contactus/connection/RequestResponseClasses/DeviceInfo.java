package com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses;

public class DeviceInfo {
    String buildNumber;
    String modelName;
    String network;
    String osVersion;

    public DeviceInfo(String str, String str2, String... strArr) {
        this.modelName = str;
        this.osVersion = str2;
        if (strArr.length > 0) {
            this.buildNumber = strArr[0];
        }
        if (strArr.length > 1) {
            this.network = strArr[1];
        }
    }

    public String getBuildNumber() {
        return this.buildNumber;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getNetwork() {
        return this.network;
    }

    public String getOsVersion() {
        return this.osVersion;
    }
}
