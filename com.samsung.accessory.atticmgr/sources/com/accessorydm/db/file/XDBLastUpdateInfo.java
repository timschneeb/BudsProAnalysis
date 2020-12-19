package com.accessorydm.db.file;

public class XDBLastUpdateInfo {
    private long date = 0;
    private long deltaSize = 0;
    private String description = "";
    private String lastUpdateFwVer = "";
    private String resultCode = "";

    public long getLastUpdateDate() {
        return this.date;
    }

    public void setLastUpdateDate(long j) {
        this.date = j;
    }

    public String getLastUpdateVersion() {
        return this.lastUpdateFwVer;
    }

    public void setLastUpdateVersion(String str) {
        this.lastUpdateFwVer = str;
    }

    public String getLastUpdateDescription() {
        return this.description;
    }

    public void setLastUpdateDescription(String str) {
        this.description = str;
    }

    public String getLastUpdateResultCode() {
        return this.resultCode;
    }

    public void setLastUpdateResultCode(String str) {
        this.resultCode = str;
    }

    public long getLastUpdateDeltaSize() {
        return this.deltaSize;
    }

    public void setLastUpdateDeltaSize(long j) {
        this.deltaSize = j;
    }
}
