package com.accessorydm.db.sql;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.accessorydm.db.XDMDbManager;
import com.accessorydm.db.file.XDBRegistrationInfo;
import com.accessorydm.db.file.XDBUserDBException;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.android.fotaprovider.log.Log;

public class XDMRegistrationDbSqlQuery implements XDBInterface {
    static void createDBRegistration(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            sQLiteDatabase.execSQL(XDBInterface.DATABASE_REGISTER_INFO_CREATE);
        } catch (SQLException e) {
            Log.E(e.toString());
        }
    }

    static void deleteDBRegistration(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS register");
        } catch (SQLException e) {
            Log.E(e.toString());
        }
    }

    public static long insertRegistrationInfo(XDBRegistrationInfo xDBRegistrationInfo) {
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(XDBInterface.XDM_SQL_REGISTER_TERMS_STATE, Integer.valueOf(xDBRegistrationInfo.getTermStatus()));
            contentValues.put("register", Integer.valueOf(xDBRegistrationInfo.getDeviceRegistrationStatus()));
            contentValues.put(XDBInterface.XDM_SQL_REGISTER_PUSH_STATE, Integer.valueOf(xDBRegistrationInfo.getPushRegistrationStatus()));
            contentValues.put(XDBInterface.XDM_SQL_REGISTER_CONSUMER_STATE, Integer.valueOf(xDBRegistrationInfo.getConsumerStatus()));
            j = xdmDbGetWritableDatabase.insert("register", null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    public static void updateRegistrationInfo(long j, XDBRegistrationInfo xDBRegistrationInfo) throws XDBUserDBException {
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(XDBInterface.XDM_SQL_REGISTER_TERMS_STATE, Integer.valueOf(xDBRegistrationInfo.getTermStatus()));
            contentValues.put("register", Integer.valueOf(xDBRegistrationInfo.getDeviceRegistrationStatus()));
            contentValues.put(XDBInterface.XDM_SQL_REGISTER_PUSH_STATE, Integer.valueOf(xDBRegistrationInfo.getPushRegistrationStatus()));
            contentValues.put(XDBInterface.XDM_SQL_REGISTER_CONSUMER_STATE, Integer.valueOf(xDBRegistrationInfo.getConsumerStatus()));
            xdmDbGetWritableDatabase.update("register", contentValues, "rowId=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0075, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0076, code lost:
        if (r0 != null) goto L_0x0078;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007d, code lost:
        r1.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0080, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBRegistrationInfo getQueryRegistrationInfo() throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 149
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMRegistrationDbSqlQuery.getQueryRegistrationInfo():com.accessorydm.db.file.XDBRegistrationInfo");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005e, code lost:
        if (r12 != null) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r12.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0064, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0065, code lost:
        r13.addSuppressed(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0068, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean existRegistrationInfo(long r12) {
        /*
        // Method dump skipped, instructions count: 124
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMRegistrationDbSqlQuery.existRegistrationInfo(long):boolean");
    }
}
