package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMRegistrationDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;

public class XDBRegistrationAdp {
    private static XDBRegistrationInfo getRegistrationInfo() {
        try {
            return XDMRegistrationDbSqlQuery.getQueryRegistrationInfo();
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    private static void setRegistrationInfo(XDBRegistrationInfo xDBRegistrationInfo) {
        try {
            XDMRegistrationDbSqlQuery.updateRegistrationInfo(1, xDBRegistrationInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static int getDeviceRegistrationStatus() {
        try {
            int deviceRegistrationStatus = getRegistrationInfo().getDeviceRegistrationStatus();
            Log.I("getDeviceRegistrationStatus : " + deviceRegistrationStatus);
            return deviceRegistrationStatus;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void setDeviceRegistrationStatus(int i) {
        try {
            XDBRegistrationInfo registrationInfo = getRegistrationInfo();
            registrationInfo.setDeviceRegistrationStatus(i);
            setRegistrationInfo(registrationInfo);
            Log.I("setDeviceRegistrationStatus : " + i);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int getPushRegistrationStatus() {
        try {
            int pushRegistrationStatus = getRegistrationInfo().getPushRegistrationStatus();
            Log.I("getPushRegistrationStatus : " + pushRegistrationStatus);
            return pushRegistrationStatus;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void setPushRegistrationStatus(int i) {
        try {
            XDBRegistrationInfo registrationInfo = getRegistrationInfo();
            registrationInfo.setPushRegistrationStatus(i);
            setRegistrationInfo(registrationInfo);
            Log.I("setPushRegistrationStatus : " + i);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }
}
