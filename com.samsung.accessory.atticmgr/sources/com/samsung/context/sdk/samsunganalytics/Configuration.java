package com.samsung.context.sdk.samsunganalytics;

public class Configuration {
    private int auidType = -1;
    private DBOpenHelper dbOpenHelper;
    private String deviceId;
    private boolean enableAutoDeviceId = false;
    private boolean enableFastReady = false;
    private boolean enableUseInAppLogging = false;
    private boolean isAlwaysRunningApp = false;
    private String trackingId;
    private UserAgreement userAgreement;
    private String version;

    public String getTrackingId() {
        return this.trackingId;
    }

    public Configuration setTrackingId(String str) {
        this.trackingId = str;
        return this;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public Configuration setDeviceId(String str) {
        this.deviceId = str;
        return this;
    }

    public Configuration enableAutoDeviceId() {
        this.enableAutoDeviceId = true;
        return this;
    }

    public Configuration enableUseInAppLogging(UserAgreement userAgreement2) {
        setUserAgreement(userAgreement2);
        this.enableUseInAppLogging = true;
        return this;
    }

    public boolean isEnableUseInAppLogging() {
        return this.enableUseInAppLogging;
    }

    public boolean isEnableAutoDeviceId() {
        return this.enableAutoDeviceId;
    }

    public UserAgreement getUserAgreement() {
        return this.userAgreement;
    }

    public Configuration setUserAgreement(UserAgreement userAgreement2) {
        this.userAgreement = userAgreement2;
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public Configuration setVersion(String str) {
        this.version = str;
        return this;
    }

    public boolean isAlwaysRunningApp() {
        return this.isAlwaysRunningApp;
    }

    public Configuration setAlwaysRunningApp(boolean z) {
        this.isAlwaysRunningApp = z;
        return this;
    }

    @Deprecated
    public boolean isEnableFastReady() {
        return this.enableFastReady;
    }

    @Deprecated
    public Configuration enableFastReady(boolean z) {
        this.enableFastReady = z;
        return this;
    }

    public Configuration setDbOpenHelper(DBOpenHelper dBOpenHelper) {
        this.dbOpenHelper = dBOpenHelper;
        return this;
    }

    public DBOpenHelper getDbOpenHelper() {
        return this.dbOpenHelper;
    }

    public int getAuidType() {
        return this.auidType;
    }

    public void setAuidType(int i) {
        this.auidType = i;
    }
}
