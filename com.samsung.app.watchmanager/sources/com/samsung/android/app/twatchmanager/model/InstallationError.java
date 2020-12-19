package com.samsung.android.app.twatchmanager.model;

public class InstallationError {
    public int apkSize;
    public int errorCode;
    public String packageName;

    public InstallationError(String str, int i, int i2) {
        this.packageName = str;
        this.errorCode = i;
        this.apkSize = i2;
    }
}
