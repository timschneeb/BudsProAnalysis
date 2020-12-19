package com.accessorydm.db.file;

import com.accessorydm.postpone.PostponeType;

public class XDBPostPoneInfo {
    private int forceInstall = 0;
    private int postponeStatus = PostponeType.NONE.getStatus();
    private long postponeTime = 0;
    private int wifiPostponeRetryCount = 0;

    public int getPostponeStatus() {
        return this.postponeStatus;
    }

    public void setPostponeStatus(int i) {
        this.postponeStatus = i;
    }

    public long getPostponeTime() {
        return this.postponeTime;
    }

    public void setPostponeTime(long j) {
        this.postponeTime = j;
    }

    public int getForceInstall() {
        return this.forceInstall;
    }

    public void setForceInstall(int i) {
        this.forceInstall = i;
    }

    public int getWifiPostponeRetryCount() {
        return this.wifiPostponeRetryCount;
    }

    public void setWifiPostponeRetryCount(int i) {
        this.wifiPostponeRetryCount = i;
    }
}
