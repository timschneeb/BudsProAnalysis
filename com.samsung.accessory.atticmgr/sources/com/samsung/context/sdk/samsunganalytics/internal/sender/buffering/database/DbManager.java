package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.samsung.android.SDK.routine.Constants;
import com.samsung.context.sdk.samsunganalytics.DBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class DbManager {
    private DBOpenHelper dbOpenHelper;
    private Queue<SimpleLog> list;

    public DbManager(Context context) {
        this(new DefaultDBOpenHelper(context));
    }

    public DbManager(DBOpenHelper dBOpenHelper) {
        this.list = new LinkedBlockingQueue();
        if (dBOpenHelper != null) {
            this.dbOpenHelper = dBOpenHelper;
            dBOpenHelper.getWritableDatabase().execSQL(TableInfo.SQL_CREATE_TABLE);
        }
        delete(5);
    }

    private Queue<SimpleLog> select(String str) {
        this.list.clear();
        Cursor rawQuery = this.dbOpenHelper.getReadableDatabase().rawQuery(str, null);
        while (rawQuery.moveToNext()) {
            SimpleLog simpleLog = new SimpleLog();
            simpleLog.setId(rawQuery.getString(rawQuery.getColumnIndex(Constants.EXTRA_ID)));
            simpleLog.setData(rawQuery.getString(rawQuery.getColumnIndex("data")));
            simpleLog.setTimestamp(rawQuery.getLong(rawQuery.getColumnIndex("timestamp")));
            simpleLog.setType(rawQuery.getString(rawQuery.getColumnIndex(TableInfo.COLUMN_NAME_LOG_TYPE)).equals(LogType.DEVICE.getAbbrev()) ? LogType.DEVICE : LogType.UIX);
            this.list.add(simpleLog);
        }
        rawQuery.close();
        return this.list;
    }

    public Queue<SimpleLog> selectSome(int i) {
        return select("select * from logs_v2 LIMIT " + i);
    }

    public Queue<SimpleLog> selectAll() {
        return select("select * from logs_v2");
    }

    public void insert(SimpleLog simpleLog) {
        SQLiteDatabase writableDatabase = this.dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timestamp", Long.valueOf(simpleLog.getTimestamp()));
        contentValues.put("data", simpleLog.getData());
        contentValues.put(TableInfo.COLUMN_NAME_LOG_TYPE, simpleLog.getType().getAbbrev());
        writableDatabase.insert(TableInfo.TABLE_NAME, null, contentValues);
    }

    public void delete(String str) {
        SQLiteDatabase writableDatabase = this.dbOpenHelper.getWritableDatabase();
        writableDatabase.delete(TableInfo.TABLE_NAME, "_id = " + str, null);
    }

    public void delete(long j) {
        SQLiteDatabase writableDatabase = this.dbOpenHelper.getWritableDatabase();
        writableDatabase.delete(TableInfo.TABLE_NAME, "timestamp <= " + j, null);
    }

    public void delete(List<String> list2) {
        SQLiteDatabase writableDatabase = this.dbOpenHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            int size = list2.size();
            int i = 0;
            while (size > 0) {
                int i2 = 900;
                if (size < 900) {
                    i2 = size;
                }
                int i3 = i + i2;
                List<String> subList = list2.subList(i, i3);
                writableDatabase.delete(TableInfo.TABLE_NAME, ("_id IN(" + new String(new char[(subList.size() - 1)]).replaceAll("\u0000", "?,")) + "?)", (String[]) subList.toArray(new String[0]));
                size -= i2;
                i = i3;
            }
            list2.clear();
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
        writableDatabase.endTransaction();
    }

    public void close() {
        DBOpenHelper dBOpenHelper = this.dbOpenHelper;
        if (dBOpenHelper != null) {
            dBOpenHelper.close();
        }
    }

    public boolean isEmpty() {
        return getDataSize() <= 0;
    }

    public long getDataSize() {
        return DatabaseUtils.queryNumEntries(this.dbOpenHelper.getReadableDatabase(), TableInfo.TABLE_NAME);
    }
}
