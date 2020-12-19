package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;

public class XDBResyncModeAdp {
    public static boolean xdbGetNonceResync() {
        boolean z = false;
        try {
            z = XDMDbSqlQuery.xdmDbFetchResyncModeRow(1).nNoceResyncMode;
            Log.I("ResyncMode is" + z);
            return z;
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
            return z;
        } catch (Exception e2) {
            Log.E(e2.toString());
            return z;
        }
    }

    public static void xdbSetNonceResync(boolean z) {
        XDBResyncModeInfo xDBResyncModeInfo = new XDBResyncModeInfo();
        try {
            xDBResyncModeInfo.nNoceResyncMode = z;
            XDMDbSqlQuery.xdmDbUpdateResyncModeRow(1, xDBResyncModeInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }
}
