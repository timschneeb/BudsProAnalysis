package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;

public class XDBAgentAdp {
    private static XDBAgentInfo xdbGetAgentInfo() {
        try {
            return XDMDbSqlQuery.xdmDbSqlAgentInfoFetchRow(1);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    private static void xdbSetAgentInfo(XDBAgentInfo xDBAgentInfo) {
        try {
            XDMDbSqlQuery.xdmDbSqlAgentInfoUpdateRow(1, xDBAgentInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static void xdbSetDmAgentType(int i) {
        Log.I("AgentType=" + i);
        try {
            XDBAgentInfo xdbGetAgentInfo = xdbGetAgentInfo();
            xdbGetAgentInfo.m_nAgentType = i;
            xdbSetAgentInfo(xdbGetAgentInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetDmAgentType() {
        int i;
        try {
            i = xdbGetAgentInfo().m_nAgentType;
        } catch (Exception e) {
            Log.E(e.toString());
            i = 0;
        }
        Log.I("AgentType=" + i);
        return i;
    }
}
