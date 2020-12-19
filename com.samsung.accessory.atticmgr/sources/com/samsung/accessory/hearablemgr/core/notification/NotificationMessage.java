package com.samsung.accessory.hearablemgr.core.notification;

import java.io.Serializable;
import seccompat.android.util.Log;

public class NotificationMessage implements Serializable {
    private static final String TAG = "Attic_NotificationMessage";
    public static final int TYPE_ALARM = 4864;
    public static final int TYPE_CALL = 4866;
    public static final int TYPE_MESSAGE = 4868;
    public static final int TYPE_MISSEDCALL = 4867;
    public static final int TYPE_NORMAL = 4869;
    public static final int TYPE_SCHEDULE = 4865;
    private String appName;
    private String mainText;
    private String pkgName;
    private String subText;
    private int type;
    private long when;

    public NotificationMessage(int i, String str, String str2, String str3, String str4, long j) {
        this.type = i;
        this.pkgName = str;
        this.appName = str2;
        this.mainText = str3;
        this.subText = str4;
        this.when = j;
    }

    public int getType() {
        return this.type;
    }

    public void setType(byte b) {
        this.type = b;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String str) {
        this.pkgName = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getMain() {
        return this.mainText;
    }

    public void setMain(String str) {
        this.mainText = str;
    }

    public String getBody() {
        return this.subText;
    }

    public void setBody(String str) {
        this.subText = str;
    }

    public long getWhen() {
        return this.when;
    }

    public void setWhen(long j) {
        this.when = j;
    }

    public void log() {
        Log.d(TAG, "type : 0x" + String.format("%x ", Integer.valueOf(this.type)) + ", pkgName : " + this.pkgName + ", appName : " + this.appName + ", when : " + this.when);
    }
}
