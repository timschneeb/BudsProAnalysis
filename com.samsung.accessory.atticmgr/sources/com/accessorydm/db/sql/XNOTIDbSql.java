package com.accessorydm.db.sql;

public interface XNOTIDbSql {
    public static final String XNOTI_DB_SQL_APPID = "appId";
    public static final String XNOTI_DB_SQL_INFO_TABLE = "NOTIFICATION";
    public static final String XNOTI_DB_SQL_INFO_TABLE_CREATE = "create table if not exists NOTIFICATION (rowid integer primary key autoincrement, appId integer, uiMode integer, sessinoId text, serverId text, opmode integer, jobId integer);";
    public static final String XNOTI_DB_SQL_JOBID = "jobId";
    public static final String XNOTI_DB_SQL_OPMODE = "opmode";
    public static final String XNOTI_DB_SQL_SERVERID = "serverId";
    public static final String XNOTI_DB_SQL_SESSIONID = "sessinoId";
    public static final String XNOTI_DB_SQL_UIMODE = "uiMode";
}
