package com.samsung.context.sdk.samsunganalytics;

import android.database.sqlite.SQLiteDatabase;

public interface DBOpenHelper {
    void close();

    SQLiteDatabase getReadableDatabase();

    SQLiteDatabase getWritableDatabase();
}
