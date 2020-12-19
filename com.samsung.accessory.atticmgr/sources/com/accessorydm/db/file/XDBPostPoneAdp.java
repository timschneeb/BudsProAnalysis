package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.accessorydm.postpone.PostponeType;
import com.samsung.android.fotaprovider.log.Log;

public class XDBPostPoneAdp {
    public static XDBPostPoneInfo xdbGetPostPoneInfo() {
        try {
            return XDMDbSqlQuery.xdmDbFetchPostPoneRow(1);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    public static void xdbSetPostPoneInfo(XDBPostPoneInfo xDBPostPoneInfo) {
        try {
            XDMDbSqlQuery.xdmDbUpdatePostPoneRow(1, xDBPostPoneInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static PostponeType xdbGetPostponeType() {
        PostponeType postponeType = PostponeType.NONE;
        try {
            postponeType = PostponeType.valueOf(xdbGetPostPoneInfo().getPostponeStatus());
        } catch (Exception e) {
            Log.E(e.toString());
        }
        Log.I("get postponeType: " + postponeType + "(" + postponeType.getStatus() + ")");
        return postponeType;
    }

    public static void xdbSetPostponeType(PostponeType postponeType) {
        Log.I("set postponeType: " + postponeType + "(" + postponeType.getStatus() + ")");
        try {
            XDBPostPoneInfo xdbGetPostPoneInfo = xdbGetPostPoneInfo();
            xdbGetPostPoneInfo.setPostponeStatus(postponeType.getStatus());
            xdbSetPostPoneInfo(xdbGetPostPoneInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static long xdbGetPostponeTime() {
        long j;
        try {
            j = xdbGetPostPoneInfo().getPostponeTime();
        } catch (Exception e) {
            Log.E(e.toString());
            j = 0;
        }
        Log.I("get postponeTime: " + j);
        return j;
    }

    public static void xdbSetPostponeTime(long j) {
        try {
            XDBPostPoneInfo xdbGetPostPoneInfo = xdbGetPostPoneInfo();
            xdbGetPostPoneInfo.setPostponeTime(j);
            xdbSetPostPoneInfo(xdbGetPostPoneInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
        Log.I("set postponeTime: " + j);
    }

    public static int xdbGetForceInstall() {
        int i;
        try {
            i = xdbGetPostPoneInfo().getForceInstall();
        } catch (Exception e) {
            Log.E(e.toString());
            i = 0;
        }
        Log.I("get forceInstall: " + i);
        return i;
    }

    public static void xdbSetForceInstall(int i) {
        Log.I("set forceInstall: " + i);
        try {
            XDBPostPoneInfo xdbGetPostPoneInfo = xdbGetPostPoneInfo();
            xdbGetPostPoneInfo.setForceInstall(i);
            xdbSetPostPoneInfo(xdbGetPostPoneInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetWifiPostponeRetryCount() {
        int i;
        try {
            i = xdbGetPostPoneInfo().getWifiPostponeRetryCount();
        } catch (Exception e) {
            Log.E(e.toString());
            i = 0;
        }
        Log.I("get wifiPostponeRetryCount: " + i);
        return i;
    }
}
