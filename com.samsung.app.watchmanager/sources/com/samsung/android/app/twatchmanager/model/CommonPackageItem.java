package com.samsung.android.app.twatchmanager.model;

public class CommonPackageItem {
    public boolean disablePackage = true;
    public String packageName;

    public String toString() {
        return "packageName: " + this.packageName + " disablePackage : " + this.disablePackage;
    }
}
