package com.accessorydm.db.file;

import com.accessorydm.interfaces.XDMAccessoryInterface;
import com.samsung.accessory.fotaprovider.AccessoryState;
import java.io.Serializable;

public class XDBAccessoryInfo implements Serializable, XDMAccessoryInterface {
    private String country = XDMAccessoryInterface.XDM_ACCESSORY_INFO_COUNTRY_DEFAULT;
    private String deviceId = "";
    private String firmwareVersion = "";
    private String hardwareVersion = "";
    private String mcc = XDMAccessoryInterface.XDM_ACCESSORY_INFO_MCC_DEFAULT;
    private String modelNumber = "";
    private String pushType = "FCM";
    private String salesCode = "";
    private String serialNumber = "";
    private int status = AccessoryState.NONE.getValue();
    private String uniqueNumber = "";

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getModelNumber() {
        return this.modelNumber;
    }

    public void setModelNumber(String str) {
        this.modelNumber = str;
    }

    public String getSalesCode() {
        return this.salesCode;
    }

    public void setSalesCode(String str) {
        this.salesCode = str;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(String str) {
        this.firmwareVersion = str;
    }

    public String getHardwareVersion() {
        return this.hardwareVersion;
    }

    public void setHardwareVersion(String str) {
        this.hardwareVersion = str;
    }

    public String getUniqueNumber() {
        return this.uniqueNumber;
    }

    public void setUniqueNumber(String str) {
        this.uniqueNumber = str;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String str) {
        this.serialNumber = str;
    }

    public String getMCC() {
        return this.mcc;
    }

    public void setMCC(String str) {
        this.mcc = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }
}
