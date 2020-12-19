package com.accessorydm.db.file;

public class XDBUserDBException extends Exception {
    public static final int SQL_ERROR = 1;
    private static final long serialVersionUID = 1;
    private int m_ErrorCode;

    public XDBUserDBException(int i) {
        this.m_ErrorCode = i;
    }

    public void failHandling() {
        if (this.m_ErrorCode == 1) {
            XDB.xdbSqlFailAbort();
        }
    }

    public String getMessage() {
        return "Exception occurs from : " + this.m_ErrorCode;
    }
}
