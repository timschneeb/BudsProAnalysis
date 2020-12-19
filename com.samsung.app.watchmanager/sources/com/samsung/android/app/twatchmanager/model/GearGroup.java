package com.samsung.android.app.twatchmanager.model;

import com.samsung.android.app.twatchmanager.util.StringResourceManagerUtil;

public class GearGroup extends GroupBase {
    public String downloadInstallLayout = "pop_download_install";
    public int maxAPILevel = 99;
    public boolean requestDisconnectAlways = false;
    public boolean resetOption = true;
    public String switchGearTitleIcon = "gm_ab_logo_wearable";
    public String wearableType = StringResourceManagerUtil.WATCH_TYPE;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof GroupBase)) {
            return this.name.equals(((GroupBase) obj).name);
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return "GearGroup{name='" + this.name + '\'' + ", downloadInstallLayout='" + this.downloadInstallLayout + '\'' + ", wearableType='" + this.wearableType + '\'' + ", maxAPILevel='" + this.maxAPILevel + '\'' + ", resetOption='" + this.resetOption + '\'' + ", requestDisconnectAlways=" + this.requestDisconnectAlways + '\'' + ", switchGearTitleIcon='" + this.switchGearTitleIcon + '\'' + '}';
    }
}
