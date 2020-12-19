package com.samsung.accessory.hearablemgr.core.notification;

public class NotificationAppData {
    private int count;
    private boolean isDual;
    private boolean isEnable;
    private String mAppName;
    private String mPackageName;
    private long timeStamp;
    private int uId;

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long j) {
        this.timeStamp = j;
    }

    public int getuId() {
        return this.uId;
    }

    public void setuId(int i) {
        this.uId = i;
    }

    public NotificationAppData(boolean z, String str, String str2, boolean z2) {
        this.isEnable = z;
        this.mAppName = str;
        this.mPackageName = str2;
        this.isDual = z2;
    }

    public boolean isEnable() {
        return this.isEnable;
    }

    public void setEnable(boolean z) {
        this.isEnable = z;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public void setAppName(String str) {
        this.mAppName = str;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    public boolean isDual() {
        return this.isDual;
    }
}
