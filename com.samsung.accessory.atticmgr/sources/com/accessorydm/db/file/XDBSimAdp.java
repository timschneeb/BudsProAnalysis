package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;

public class XDBSimAdp {
    public static XDBSimInfo xdbGetSimInfo() {
        try {
            return XDMDbSqlQuery.xdmDbFetchSimInfoRow(1);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    public static void xdbSetSimInfo(XDBSimInfo xDBSimInfo) {
        try {
            XDMDbSqlQuery.xdmDbUpdateSimInfoRow(1, xDBSimInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static String xdbGetSimIMSI() {
        try {
            return xdbGetSimInfo().m_szIMSI;
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    public static void xdbSetSimIMSI(String str) {
        if (str == null) {
            Log.E("imsi is NULL");
            return;
        }
        try {
            XDBSimInfo xdbGetSimInfo = xdbGetSimInfo();
            xdbGetSimInfo.m_szIMSI = str;
            xdbSetSimInfo(xdbGetSimInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }
}
