package com.accessorydm.db;

import android.database.sqlite.SQLiteDatabase;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.agent.XDMTask;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.sql.XDMDbHelper;
import com.samsung.android.fotaprovider.log.Log;

public final class XDMDbManager {
    private static boolean mDBInit = false;

    public static boolean initializeDatabase() {
        new XDMDbHelper(XDMDmUtils.getContext());
        XDMDmUtils.getInstance().xdmTaskInit();
        if (mDBInit) {
            return true;
        }
        XDB.xdbInit();
        mDBInit = XDMTask.xdmAgentTaskDBInit();
        if (mDBInit) {
            return true;
        }
        Log.W("DB Init false");
        return false;
    }

    public static SQLiteDatabase xdmDbGetReadableDatabase() {
        try {
            return new XDMDbHelper(XDMDmUtils.getContext()).getReadableDatabase();
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    public static SQLiteDatabase xdmDbGetWritableDatabase() {
        try {
            return new XDMDbHelper(XDMDmUtils.getContext()).getWritableDatabase();
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    public static void xdmDbCloseSQLiteDatabase(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase != null) {
            try {
                sQLiteDatabase.close();
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
    }
}
