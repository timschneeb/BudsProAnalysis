package com.samsung.context.sdk.samsunganalytics.internal.sender;

public enum LogType {
    DEVICE("dvc"),
    UIX("uix");
    
    String abbrev;

    private LogType(String str) {
        this.abbrev = str;
    }

    public String getAbbrev() {
        return this.abbrev;
    }
}
