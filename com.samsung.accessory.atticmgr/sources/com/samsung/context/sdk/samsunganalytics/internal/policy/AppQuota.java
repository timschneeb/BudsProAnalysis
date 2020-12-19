package com.samsung.context.sdk.samsunganalytics.internal.policy;

public class AppQuota {
    private int dataQuota;
    private int dataUsed;
    private int loadedSize;
    private int wifiQuota;
    private int wifiUsed;

    public int getDataQuota() {
        return this.dataQuota;
    }

    public void setDataQuota(int i) {
        this.dataQuota = i;
    }

    public int getWifiQuota() {
        return this.wifiQuota;
    }

    public void setWifiQuota(int i) {
        this.wifiQuota = i;
    }

    public int getDataUsed() {
        return this.dataUsed;
    }

    public void setDataUsed(int i) {
        this.dataUsed = i;
    }

    public int getWifiUsed() {
        return this.wifiUsed;
    }

    public void setWifiUsed(int i) {
        this.wifiUsed = i;
    }

    public int getLoadedSize() {
        return this.loadedSize;
    }

    public void setLoadedSize(int i) {
        this.loadedSize = i;
    }
}
