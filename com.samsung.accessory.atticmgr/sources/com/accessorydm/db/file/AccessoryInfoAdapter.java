package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMAccessoryDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;

public class AccessoryInfoAdapter {
    private XDBAccessoryInfo accessoryInfo;

    public AccessoryInfoAdapter() {
        try {
            this.accessoryInfo = XDMAccessoryDbSqlQuery.xdmAccessoryDbSqlFetchRow();
        } catch (XDBUserDBException e) {
            e.printStackTrace();
        }
        if (this.accessoryInfo == null) {
            this.accessoryInfo = new XDBAccessoryInfo();
        }
    }

    public XDBAccessoryInfo getAccessoryInfo() {
        return this.accessoryInfo;
    }

    public void updateAccessoryDB(XDBAccessoryInfo xDBAccessoryInfo) {
        try {
            XDMAccessoryDbSqlQuery.xdmAccessoryDbSqlUpdateRow(1, xDBAccessoryInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public String getDeviceId() {
        return this.accessoryInfo.getDeviceId();
    }

    public String getSalesCode() {
        return this.accessoryInfo.getSalesCode();
    }

    public String getFirmwareVersion() {
        return this.accessoryInfo.getFirmwareVersion();
    }

    public String getModelNumber() {
        return this.accessoryInfo.getModelNumber();
    }

    public String getUniqueNumber() {
        return this.accessoryInfo.getUniqueNumber();
    }

    public String getSerialNumber() {
        return this.accessoryInfo.getSerialNumber();
    }

    public int getStatus() {
        return this.accessoryInfo.getStatus();
    }
}
