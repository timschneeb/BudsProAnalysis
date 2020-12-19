package com.samsung.android.sdk.mobileservice;

/* access modifiers changed from: package-private */
public enum SessionErrorCode {
    NO_PROBLEM(0, 100),
    CAUSE_AGENT_NOT_INSTALLED(1, 0),
    CAUSE_AGENT_NOT_AVAILABLE(2, 1),
    CAUSE_AGENT_OLD_VERSION(3, 2),
    CAUSE_SDK_OLD_VERSION(4, 3),
    CAUSE_CONNECT_CANCELED(5, 4),
    CAUSE_CONNECT_TIMEOUT(6, 5),
    CAUSE_AGENT_FORCE_UPDATE_REQUIRED(7, 5),
    CAUSE_UNDEFINED(99, 0);
    
    private int mPriority;
    private int mValue;

    private SessionErrorCode(int i, int i2) {
        this.mValue = i;
        this.mPriority = i2;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public static SessionErrorCode getErrorCode(int i) {
        SessionErrorCode[] values = values();
        for (SessionErrorCode sessionErrorCode : values) {
            if (sessionErrorCode.getValue() == i) {
                return sessionErrorCode;
            }
        }
        return null;
    }

    public int comparePriority(SessionErrorCode sessionErrorCode) {
        if (this.mPriority < sessionErrorCode.getPriority()) {
            return -1;
        }
        return this.mPriority == sessionErrorCode.getPriority() ? 0 : 1;
    }
}
