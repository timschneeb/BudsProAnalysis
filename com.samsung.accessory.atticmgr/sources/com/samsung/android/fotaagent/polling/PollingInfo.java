package com.samsung.android.fotaagent.polling;

public class PollingInfo {
    private static final int DEFAULT_HEARTBEAT_PERIOD = 0;
    private static final String DEFAULT_HEARTBEAT_URL = "/device/fumo/deviceheartbeat";
    private static final int DEFAULT_NEXT_HEARTBEAT_TIME = 0;
    private static final int DEFAULT_NEXT_POLLING_TIME = 0;
    private static final String DEFAULT_ORIGIN_PRE_URL = "http://org-fota-dn.ospserver.net/firmware/";
    private static final int DEFAULT_PERIOD = 7;
    private static final String DEFAULT_PERIOD_UNIT = "day";
    private static final String DEFAULT_PRE_URL = "http://fota-cloud-dn.ospserver.net/firmware/";
    private static final int DEFAULT_RANGE = 3;
    private static final int DEFAULT_TIME = 15;
    private static final String DEFAULT_VERSION_XML = "version.xml";
    private int heartBeatPeriod = 0;
    private String heartBeatUrl = DEFAULT_HEARTBEAT_URL;
    private long nextHeartBeatTime = 0;
    private long nextPollingTime = 0;
    private String originPreUrl = DEFAULT_ORIGIN_PRE_URL;
    private int period = 7;
    private String periodUnit = DEFAULT_PERIOD_UNIT;
    private String preUrl = DEFAULT_PRE_URL;
    private int range = 3;
    private int time = 15;
    private String versionFileName = DEFAULT_VERSION_XML;

    public String getOriginPreUrl() {
        return this.originPreUrl;
    }

    public void setOriginPreUrl(String str) {
        this.originPreUrl = str;
    }

    public String getPreUrl() {
        return this.preUrl;
    }

    public void setPreUrl(String str) {
        this.preUrl = str;
    }

    public String getPeriodUnit() {
        return this.periodUnit;
    }

    public void setPeriodUnit(String str) {
        this.periodUnit = str;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int i) {
        this.period = i;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int i) {
        this.time = i;
    }

    public int getRange() {
        return this.range;
    }

    public void setRange(int i) {
        this.range = i;
    }

    public long getNextPollingTime() {
        return this.nextPollingTime;
    }

    public void setNextPollingTime(long j) {
        this.nextPollingTime = j;
    }

    public String getVersionFileName() {
        return this.versionFileName;
    }

    public void setVersionFileName(String str) {
        this.versionFileName = str;
    }

    public String getHeartBeatUrl() {
        return this.heartBeatUrl;
    }

    public void setHeartBeatUrl(String str) {
        this.heartBeatUrl = str;
    }

    public int getHeartBeatPeriod() {
        return this.heartBeatPeriod;
    }

    public void setHeartBeatPeriod(int i) {
        this.heartBeatPeriod = i;
    }

    public long getNextHeartBeatTime() {
        return this.nextHeartBeatTime;
    }

    public void setNextHeartBeatTime(long j) {
        this.nextHeartBeatTime = j;
    }
}
