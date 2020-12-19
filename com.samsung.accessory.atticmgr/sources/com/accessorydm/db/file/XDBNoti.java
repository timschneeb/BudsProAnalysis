package com.accessorydm.db.file;

import com.accessorydm.db.sql.XNOTIDbSql;
import com.accessorydm.db.sql.XNOTIDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;
import java.util.Locale;

public class XDBNoti implements XNOTIDbSql {
    public static boolean xdbNotiExistInfo() {
        boolean z;
        try {
            z = XNOTIDbSqlQuery.xnotiDbSqlInfoExistsRow();
        } catch (Exception e) {
            Log.E(e.toString());
            z = false;
        }
        Log.I(Boolean.toString(z));
        return z;
    }

    public static void xdbNotiInsertInfo(XDBNotiInfo xDBNotiInfo) {
        Log.H(xDBNotiInfo.appId + ", " + xDBNotiInfo.uiMode + ", " + xDBNotiInfo.m_szSessionId);
        try {
            XNOTIDbSqlQuery.xnotiDbSqlInfoInsertRow(xDBNotiInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static void xdbNotiDeleteSessionId(String str) {
        try {
            Log.H(str);
            XNOTIDbSqlQuery.xnotiDbSqlInfoDeleteRow(XNOTIDbSql.XNOTI_DB_SQL_SESSIONID, str);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static XDBNotiInfo xdbNotiGetInfo() {
        XDBNotiInfo xDBNotiInfo = null;
        try {
            xDBNotiInfo = XNOTIDbSqlQuery.xnotiDbSqlInfoFetchRow();
            if (xDBNotiInfo != null) {
                Log.H(String.format(Locale.US, "%d, %d, %s", Integer.valueOf(xDBNotiInfo.appId), Integer.valueOf(xDBNotiInfo.uiMode), xDBNotiInfo.m_szSessionId));
            }
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return xDBNotiInfo;
    }

    public static void xdbNotiSetInfo(XDBNotiInfo xDBNotiInfo) {
        try {
            XNOTIDbSqlQuery.xnotiDbSqlInfoUpdateRow(1, xDBNotiInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static int xdbNotiGetOPMode() {
        try {
            return xdbNotiGetInfo().opMode;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void xdbNotiSetOPMode(int i) {
        try {
            XDBNotiInfo xdbNotiGetInfo = xdbNotiGetInfo();
            xdbNotiGetInfo.opMode = i;
            xdbNotiSetInfo(xdbNotiGetInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }
}
