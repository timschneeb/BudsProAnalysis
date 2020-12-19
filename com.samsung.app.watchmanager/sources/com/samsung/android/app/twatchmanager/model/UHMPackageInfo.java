package com.samsung.android.app.twatchmanager.model;

import com.samsung.android.app.twatchmanager.TWatchManagerApplication;

public class UHMPackageInfo {
    private static final long serialVersionUID = 4923464028630645933L;
    public boolean checkForUpdate;
    public String installerPackage;
    public boolean isOptional;
    public boolean onlyForNonSamsung;
    public String packageName;
    public boolean requiredForNonSamsung;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public UHMPackageInfo(String str, boolean z) {
        this(str, z, false, TWatchManagerApplication.getAppContext() != null ? TWatchManagerApplication.getAppContext().getPackageName() : "", false, false);
    }

    public UHMPackageInfo(String str, boolean z, boolean z2, String str2, boolean z3, boolean z4) {
        this.packageName = str;
        this.isOptional = z;
        this.requiredForNonSamsung = z2;
        this.installerPackage = str2;
        this.checkForUpdate = z3;
        this.onlyForNonSamsung = z4;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UHMPackageInfo)) {
            return false;
        }
        return ((UHMPackageInfo) obj).packageName.equalsIgnoreCase(this.packageName);
    }

    public int hashCode() {
        return this.packageName.hashCode();
    }

    public String toString() {
        return "UHMPackageInfo{packageName='" + this.packageName + '\'' + ", isOptional=" + this.isOptional + ", requiredForNonSamsung=" + this.requiredForNonSamsung + ", installerPackage='" + this.installerPackage + '\'' + ", checkForUpdate=" + this.checkForUpdate + ", onlyForNonSamsung=" + this.onlyForNonSamsung + '}';
    }
}
