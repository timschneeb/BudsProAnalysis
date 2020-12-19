package com.samsung.accessory.fotaprovider.controller;

import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.accessorydm.db.file.XDBAccessoryInfo;

public class ConsumerInfo {
    XDBAccessoryInfo accessoryInfo;
    boolean isRunBgUpdate;

    public ConsumerInfo(String str, String str2, String str3, String str4, String str5, String str6) {
        this.accessoryInfo = new XDBAccessoryInfo();
        this.accessoryInfo.setDeviceId(str == null ? "" : str);
        this.accessoryInfo.setModelNumber(str2 == null ? "" : str2);
        this.accessoryInfo.setSalesCode(str3 == null ? "" : str3);
        this.accessoryInfo.setFirmwareVersion(str4 == null ? "" : str4);
        this.accessoryInfo.setUniqueNumber(str5 == null ? "" : str5);
        this.accessoryInfo.setSerialNumber(str6 == null ? "" : str6);
    }

    ConsumerInfo() {
        this.accessoryInfo = new AccessoryInfoAdapter().getAccessoryInfo();
    }

    public XDBAccessoryInfo getAccessoryInfo() {
        return this.accessoryInfo;
    }

    public String getDeviceId() {
        return this.accessoryInfo.getDeviceId();
    }

    public String getSalesCode() {
        return this.accessoryInfo.getSalesCode();
    }

    public String getModelNumber() {
        return this.accessoryInfo.getModelNumber();
    }

    public String getFirmwareVersion() {
        return this.accessoryInfo.getFirmwareVersion();
    }

    public int getStatus() {
        return this.accessoryInfo.getStatus();
    }

    public boolean isRunBgUpdate() {
        return this.isRunBgUpdate;
    }

    public void setStatus(int i) {
        this.accessoryInfo.setStatus(i);
    }
}
