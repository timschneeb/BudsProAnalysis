package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database;

import android.provider.BaseColumns;

public class TableInfo implements BaseColumns {
    public static final String COLUMN_NAME_DATA = "data";
    public static final String COLUMN_NAME_LOG_TYPE = "logtype";
    public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS logs_v2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp INTEGER, logtype TEXT, data TEXT)";
    public static final String TABLE_NAME = "logs_v2";
}
