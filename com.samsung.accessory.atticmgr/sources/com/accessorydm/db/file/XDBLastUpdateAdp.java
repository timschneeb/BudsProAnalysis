package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMLastUpdateDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.GeneralUtil;

public class XDBLastUpdateAdp {
    public static XDBLastUpdateInfo getLastUpdateInfo() {
        try {
            return XDMLastUpdateDbSqlQuery.getQueryLastUpdateInfo();
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    private static void setLastUpdateInfo(XDBLastUpdateInfo xDBLastUpdateInfo) {
        try {
            XDMLastUpdateDbSqlQuery.updateLastUpdateInfo(1, xDBLastUpdateInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static void setSuccessfulLastUpdateInfo() {
        XDBLastUpdateInfo xDBLastUpdateInfo = new XDBLastUpdateInfo();
        xDBLastUpdateInfo.setLastUpdateDate(System.currentTimeMillis());
        xDBLastUpdateInfo.setLastUpdateVersion(XDBFumoAdp.xdbGetUpdateFWVer());
        xDBLastUpdateInfo.setLastUpdateDescription(XDBFumoAdp.xdbGetFUMODescription());
        xDBLastUpdateInfo.setLastUpdateResultCode(XDBFumoAdp.xdbGetFUMOResultCode());
        xDBLastUpdateInfo.setLastUpdateDeltaSize(XDBFumoAdp.xdbGetObjectSizeFUMO());
        setLastUpdateInfo(xDBLastUpdateInfo);
        Log.I("Last Update Info \nDate : " + GeneralUtil.convertMillisToDateTime(xDBLastUpdateInfo.getLastUpdateDate()) + ", LastUpdateFwVer : " + xDBLastUpdateInfo.getLastUpdateVersion() + ", ResultCode : " + xDBLastUpdateInfo.getLastUpdateResultCode() + ", DeltaSize : " + xDBLastUpdateInfo.getLastUpdateDeltaSize());
    }
}
