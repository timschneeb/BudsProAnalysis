package com.accessorydm.db.sql;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.accessorydm.db.XDMDbManager;
import com.accessorydm.db.file.XDBLastUpdateInfo;
import com.accessorydm.db.file.XDBUserDBException;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.android.fotaprovider.log.Log;

public class XDMLastUpdateDbSqlQuery implements XDBInterface {
    public static void createDBLastUpdate(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            sQLiteDatabase.execSQL(XDBInterface.DATABASE_LAST_UPDATE_INFO_CREATE);
        } catch (SQLException e) {
            Log.E(e.toString());
        }
    }

    public static void deleteDBLastUpdate(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS LastUpdate");
        } catch (SQLException e) {
            Log.E(e.toString());
        }
    }

    public static long insertLastUpdateInfo(XDBLastUpdateInfo xDBLastUpdateInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_DATE, Long.valueOf(xDBLastUpdateInfo.getLastUpdateDate()));
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_VERSION, xDBLastUpdateInfo.getLastUpdateVersion());
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_DESCRIPTION, xDBLastUpdateInfo.getLastUpdateDescription());
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_RESULTCODE, xDBLastUpdateInfo.getLastUpdateResultCode());
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_DELTASIZE, Long.valueOf(xDBLastUpdateInfo.getLastUpdateDeltaSize()));
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_LAST_UPDATE_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    public static void updateLastUpdateInfo(long j, XDBLastUpdateInfo xDBLastUpdateInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_DATE, Long.valueOf(xDBLastUpdateInfo.getLastUpdateDate()));
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_VERSION, xDBLastUpdateInfo.getLastUpdateVersion());
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_DESCRIPTION, xDBLastUpdateInfo.getLastUpdateDescription());
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_RESULTCODE, xDBLastUpdateInfo.getLastUpdateResultCode());
            contentValues.put(XDBInterface.XDM_SQL_LAST_UPDATE_DELTASIZE, Long.valueOf(xDBLastUpdateInfo.getLastUpdateDeltaSize()));
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_LAST_UPDATE_TABLE, contentValues, "rowId=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0098, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0099, code lost:
        if (r2 != null) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a0, code lost:
        r1.addSuppressed(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a3, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBLastUpdateInfo getQueryLastUpdateInfo() throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 185
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMLastUpdateDbSqlQuery.getQueryLastUpdateInfo():com.accessorydm.db.file.XDBLastUpdateInfo");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0063, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0065, code lost:
        if (r3 != null) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006c, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0070, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean existLastUpdateInfo(long r17) {
        /*
        // Method dump skipped, instructions count: 132
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMLastUpdateDbSqlQuery.existLastUpdateInfo(long):boolean");
    }
}
