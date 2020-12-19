package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;

public class XDBAccXNodeAdp {
    public static void xdbCreateXNodeInfo(XDBAccXNodeInfo xDBAccXNodeInfo) {
        try {
            XDMDbSqlQuery.xdmDbInsertAccXListNodeRow(xDBAccXNodeInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static boolean xdbExistXNodeInfo(int i) {
        try {
            return XDMDbSqlQuery.xdmDbExistsAccXListNodeRow((long) i);
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }
}
