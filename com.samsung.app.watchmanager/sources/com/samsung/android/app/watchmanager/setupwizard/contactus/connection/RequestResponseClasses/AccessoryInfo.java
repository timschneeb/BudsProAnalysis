package com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses;

public class AccessoryInfo {
    String buildNumber;
    String modelName;
    String network;
    String osVersion;

    public AccessoryInfo(String... strArr) {
        if (strArr.length > 0) {
            this.modelName = strArr[0];
        }
        if (strArr.length > 1) {
            this.osVersion = strArr[1];
        }
        if (strArr.length > 2) {
            this.buildNumber = strArr[2];
        }
        if (strArr.length > 3) {
            this.network = strArr[3];
        }
    }
}
