package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.samsung.context.sdk.samsunganalytics.DBOpenHelper;

public class DefaultDBOpenHelper extends SQLiteOpenHelper implements DBOpenHelper {
    public static final String DATABASE_NAME = "SamsungAnalytics.db";
    public static final int DATABASE_VERSION = 1;

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public DefaultDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(TableInfo.SQL_CREATE_TABLE);
    }

    @Override // com.samsung.context.sdk.samsunganalytics.DBOpenHelper
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override // com.samsung.context.sdk.samsunganalytics.DBOpenHelper
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}
