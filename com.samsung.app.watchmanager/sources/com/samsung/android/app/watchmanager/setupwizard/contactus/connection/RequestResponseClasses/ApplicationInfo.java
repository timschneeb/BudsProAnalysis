package com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses;

public class ApplicationInfo {
    String applicationId;
    String applicationPackage;
    String applicationVersion;

    public ApplicationInfo(String str, String str2, String... strArr) {
        this.applicationId = str;
        this.applicationVersion = str2;
        if (strArr.length > 0) {
            this.applicationPackage = strArr[0];
        }
    }
}
