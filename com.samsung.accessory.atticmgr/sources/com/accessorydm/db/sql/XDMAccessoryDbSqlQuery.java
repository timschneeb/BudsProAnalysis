package com.accessorydm.db.sql;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.accessorydm.db.XDMDbManager;
import com.accessorydm.db.file.XDBAccessoryInfo;
import com.accessorydm.db.file.XDBUserDBException;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.android.fotaprovider.log.Log;

public class XDMAccessoryDbSqlQuery implements XDBInterface {
    static void xdmAccessoryDbSqlCreate(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            sQLiteDatabase.execSQL(XDBInterface.DATABASE_ACCESSORY_INFO_CREATE);
        } catch (SQLException e) {
            Log.E(e.toString());
        }
    }

    static void xdmAccessoryDbSqlDrop(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS accessory");
        } catch (SQLException e) {
            Log.E(e.toString());
        }
    }

    public static long xdmAccessoryDbSqlInsertRow(XDBAccessoryInfo xDBAccessoryInfo) {
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("deviceid", xDBAccessoryInfo.getDeviceId());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_MODEL, xDBAccessoryInfo.getModelNumber());
            contentValues.put("cc", xDBAccessoryInfo.getSalesCode());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_FWVERSION, xDBAccessoryInfo.getFirmwareVersion());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_HWVERSION, xDBAccessoryInfo.getHardwareVersion());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_UNIQUE, xDBAccessoryInfo.getUniqueNumber());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_SERIAL, xDBAccessoryInfo.getSerialNumber());
            contentValues.put("status", Integer.valueOf(xDBAccessoryInfo.getStatus()));
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_MCC, xDBAccessoryInfo.getMCC());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_OPERATOR, xDBAccessoryInfo.getCountry());
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_ACCESSORY_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    public static void xdmAccessoryDbSqlUpdateRow(long j, XDBAccessoryInfo xDBAccessoryInfo) throws XDBUserDBException {
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("deviceid", xDBAccessoryInfo.getDeviceId());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_MODEL, xDBAccessoryInfo.getModelNumber());
            contentValues.put("cc", xDBAccessoryInfo.getSalesCode());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_FWVERSION, xDBAccessoryInfo.getFirmwareVersion());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_HWVERSION, xDBAccessoryInfo.getHardwareVersion());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_UNIQUE, xDBAccessoryInfo.getUniqueNumber());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_SERIAL, xDBAccessoryInfo.getSerialNumber());
            contentValues.put("status", Integer.valueOf(xDBAccessoryInfo.getStatus()));
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_MCC, xDBAccessoryInfo.getMCC());
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_PUSHTYPE, "");
            contentValues.put(XDBInterface.XDM_SQL_ACCESSORY_OPERATOR, xDBAccessoryInfo.getCountry());
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_ACCESSORY_TABLE, contentValues, "rowId=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00bf, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c1, code lost:
        if (r3 != null) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c8, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00cc, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBAccessoryInfo xdmAccessoryDbSqlFetchRow() throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 225
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMAccessoryDbSqlQuery.xdmAccessoryDbSqlFetchRow():com.accessorydm.db.file.XDBAccessoryInfo");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0075, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0077, code lost:
        if (r3 != null) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007e, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0082, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmAccessoryDbSqlExistsRow(long r24) {
        /*
        // Method dump skipped, instructions count: 150
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMAccessoryDbSqlQuery.xdmAccessoryDbSqlExistsRow(long):boolean");
    }
}
