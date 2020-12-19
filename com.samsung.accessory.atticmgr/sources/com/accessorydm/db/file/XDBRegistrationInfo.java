package com.accessorydm.db.file;

import com.samsung.accessory.fotaprovider.AccessoryState;

public class XDBRegistrationInfo {
    private int consumerStatus = AccessoryState.NONE.getValue();
    private int deviceRegistrationStatus = 0;
    private int pushRegistrationStatus = 0;
    private int termStatus = 0;

    public int getTermStatus() {
        return this.termStatus;
    }

    public void setTermStatus(int i) {
        this.termStatus = i;
    }

    public int getDeviceRegistrationStatus() {
        return this.deviceRegistrationStatus;
    }

    public void setDeviceRegistrationStatus(int i) {
        this.deviceRegistrationStatus = i;
    }

    public int getPushRegistrationStatus() {
        return this.pushRegistrationStatus;
    }

    public void setPushRegistrationStatus(int i) {
        this.pushRegistrationStatus = i;
    }

    public int getConsumerStatus() {
        return this.consumerStatus;
    }

    public void setConsumerStatus(int i) {
        this.consumerStatus = i;
    }
}
