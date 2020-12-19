package com.samsung.android.app.twatchmanager.model;

import java.util.ArrayList;
import java.util.List;

public class ModuleInfo {
    public String containerPackage;
    protected List<UHMPackageInfo> mAdditionalPackageList;
    protected List<DeviceItem> mDeviceList;
    protected List<GearGroup> mGearGroupList;
    public String pluginAppName;
    public String pluginPackage;
    public String viClass;

    public void addAdditionalPackage(UHMPackageInfo uHMPackageInfo) {
        if (this.mAdditionalPackageList == null) {
            this.mAdditionalPackageList = new ArrayList();
        }
        this.mAdditionalPackageList.add(uHMPackageInfo);
    }

    public void addDevice(DeviceItem deviceItem) {
        if (this.mDeviceList == null) {
            this.mDeviceList = new ArrayList();
        }
        this.mDeviceList.add(deviceItem);
    }

    public void addGroup(GearGroup gearGroup) {
        if (this.mGearGroupList == null) {
            this.mGearGroupList = new ArrayList();
        }
        this.mGearGroupList.add(gearGroup);
    }

    public List getAdditionalPackageList() {
        return this.mAdditionalPackageList;
    }

    public List getDeviceList() {
        return this.mDeviceList;
    }

    public List<GearGroup> getmGearGroupList() {
        return this.mGearGroupList;
    }
}
