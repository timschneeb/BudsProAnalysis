package com.samsung.context.sdk.samsunganalytics.internal.sender;

public class SimpleLog {
    private String _id;
    private String data;
    private long timestamp;
    private LogType type;

    public SimpleLog() {
    }

    public SimpleLog(long j, String str, LogType logType) {
        this("", j, str, logType);
    }

    public SimpleLog(String str, long j, String str2, LogType logType) {
        this._id = str;
        this.timestamp = j;
        this.data = str2;
        this.type = logType;
    }

    public String getId() {
        return this._id;
    }

    public void setId(String str) {
        this._id = str;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public LogType getType() {
        return this.type;
    }

    public void setType(LogType logType) {
        this.type = logType;
    }
}
