package com.samsung.android.app.twatchmanager.model;

public class InstallPack {
    public String apkName;
    public String appName;
    public String installerPackage;
    public boolean keepApk;
    public int mVersionCode;
    public String packName;
    public String path;
    public boolean reinstall;
    public String signature;

    public InstallPack(String str, String str2, String str3, int i) {
        this(str, str2, str3, false);
        this.mVersionCode = i;
    }

    public InstallPack(String str, String str2, String str3, boolean z) {
        this(str, str2, str3, z, "com.samsung.android.app.watchmanager");
    }

    public InstallPack(String str, String str2, String str3, boolean z, String str4) {
        this.mVersionCode = 0;
        this.path = null;
        this.keepApk = false;
        this.signature = null;
        this.reinstall = false;
        this.apkName = str;
        this.packName = str2;
        this.path = str3;
        this.keepApk = z;
        this.installerPackage = str4;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InstallPack)) {
            return false;
        }
        return ((InstallPack) obj).packName.equalsIgnoreCase(this.packName);
    }

    public int hashCode() {
        return this.packName.hashCode();
    }

    public String toString() {
        return "[ packName : " + this.packName + ", mVersionCode : " + this.mVersionCode + " apkName : " + this.apkName + " , path : " + this.path + ", installerPackage:" + this.installerPackage + " ]";
    }
}
