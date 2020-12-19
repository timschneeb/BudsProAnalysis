package com.sec.android.diagmonagent.log.ged.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sec.android.diagmonagent.log.ged.db.dao.EventDao;
import com.sec.android.diagmonagent.log.ged.db.dao.ResultDao;
import com.sec.android.diagmonagent.log.ged.db.dao.ServiceDao;

public class GEDDatabase {
    private static volatile GEDDatabase gedDatabase;
    private Context context;
    private SQLiteDatabase db;

    public static GEDDatabase get(Context context2) {
        if (gedDatabase == null) {
            synchronized (DataController.class) {
                if (gedDatabase == null) {
                    gedDatabase = new GEDDatabase(context2);
                }
            }
        }
        return gedDatabase;
    }

    private GEDDatabase(Context context2) {
        this.db = new SQLiteHelper(context2).getWritableDatabase();
        this.context = context2;
    }

    public ServiceDao getServiceDao() {
        return new ServiceDao(this.context);
    }

    public EventDao getEventDao() {
        return new EventDao(this.db);
    }

    public ResultDao getResultDao() {
        return new ResultDao(this.db);
    }

    private class SQLiteHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DIAGMON_GED_DB = "diagmon_ged.db";

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }

        private SQLiteHelper(Context context) {
            super(context, DIAGMON_GED_DB, (SQLiteDatabase.CursorFactory) null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(EventDao.CREATE_TABLE_EVENT);
            sQLiteDatabase.execSQL(ResultDao.CREATE_TABLE_RESULT);
        }
    }
}
