package com.accessorydm.db.sql;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.accessorydm.db.XDMDbManager;
import com.accessorydm.db.file.XDBNotiInfo;
import com.accessorydm.db.file.XDBUserDBException;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.android.fotaprovider.log.Log;

public class XNOTIDbSqlQuery implements XDBInterface, XNOTIDbSql {
    public static void xnotiDbSqlCreate(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            sQLiteDatabase.execSQL(XNOTIDbSql.XNOTI_DB_SQL_INFO_TABLE_CREATE);
        } catch (SQLException e) {
            Log.E(e.toString());
        }
    }

    public static void xnotiDbSqlDrop(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS NOTIFICATION");
        } catch (SQLException e) {
            Log.E(e.toString());
        }
    }

    public static void xnotiDbSqlInfoInsertRow(XDBNotiInfo xDBNotiInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put("appId", Integer.valueOf(xDBNotiInfo.appId));
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_UIMODE, Integer.valueOf(xDBNotiInfo.uiMode));
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_SESSIONID, xDBNotiInfo.m_szSessionId);
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_SERVERID, xDBNotiInfo.m_szServerId);
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_OPMODE, Integer.valueOf(xDBNotiInfo.opMode));
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_JOBID, Integer.valueOf(xDBNotiInfo.jobId));
            xdmDbGetWritableDatabase.insert(XNOTIDbSql.XNOTI_DB_SQL_INFO_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
    }

    public static void xnotiDbSqlInfoDeleteRow(String str, String str2) {
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            xdmDbGetWritableDatabase.delete(XNOTIDbSql.XNOTI_DB_SQL_INFO_TABLE, str + "='" + str2 + "'", null);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
    }

    public static void xnotiDbSqlInfoUpdateRow(long j, XDBNotiInfo xDBNotiInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put("appId", Integer.valueOf(xDBNotiInfo.appId));
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_UIMODE, Integer.valueOf(xDBNotiInfo.uiMode));
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_SESSIONID, xDBNotiInfo.m_szSessionId);
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_SERVERID, xDBNotiInfo.m_szServerId);
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_OPMODE, Integer.valueOf(xDBNotiInfo.opMode));
            contentValues.put(XNOTIDbSql.XNOTI_DB_SQL_JOBID, Integer.valueOf(xDBNotiInfo.jobId));
            xdmDbGetWritableDatabase.update(XNOTIDbSql.XNOTI_DB_SQL_INFO_TABLE, contentValues, "rowId=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0078, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0079, code lost:
        if (r3 != null) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007f, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0080, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0083, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBNotiInfo xnotiDbSqlInfoFetchRow() throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 152
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XNOTIDbSqlQuery.xnotiDbSqlInfoFetchRow():com.accessorydm.db.file.XDBNotiInfo");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004e, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004f, code lost:
        if (r2 != null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0055, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0056, code lost:
        r3.addSuppressed(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0059, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xnotiDbSqlInfoExistsRow() {
        /*
        // Method dump skipped, instructions count: 108
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XNOTIDbSqlQuery.xnotiDbSqlInfoExistsRow():boolean");
    }
}
