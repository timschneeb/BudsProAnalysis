package com.accessorydm.db.file;

import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.samsung.android.fotaagent.polling.PollingInfo;
import com.samsung.android.fotaprovider.log.Log;

public class XDBPollingAdp {
    /* JADX WARNING: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.samsung.android.fotaagent.polling.PollingInfo xdbGetPollingInfo() {
        /*
            r0 = 1
            com.samsung.android.fotaagent.polling.PollingInfo r0 = com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbFetchPollingRow(r0)     // Catch:{ XDBUserDBException -> 0x0010, Exception -> 0x0007 }
            goto L_0x001c
        L_0x0007:
            r0 = move-exception
            java.lang.String r0 = r0.toString()
            com.samsung.android.fotaprovider.log.Log.E(r0)
            goto L_0x001b
        L_0x0010:
            r0 = move-exception
            java.lang.String r1 = r0.toString()
            com.samsung.android.fotaprovider.log.Log.E(r1)
            r0.failHandling()
        L_0x001b:
            r0 = 0
        L_0x001c:
            if (r0 != 0) goto L_0x0028
            java.lang.String r0 = "pollingInfo is null, return default"
            com.samsung.android.fotaprovider.log.Log.W(r0)
            com.samsung.android.fotaagent.polling.PollingInfo r0 = new com.samsung.android.fotaagent.polling.PollingInfo
            r0.<init>()
        L_0x0028:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.file.XDBPollingAdp.xdbGetPollingInfo():com.samsung.android.fotaagent.polling.PollingInfo");
    }

    public static void xdbSetPollingInfo(PollingInfo pollingInfo) {
        try {
            XDMDbSqlQuery.xdmDbUpdatePollingRow(1, pollingInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static long xdbGetNextPollingTime() {
        try {
            return xdbGetPollingInfo().getNextPollingTime();
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void xdbSetNextPollingTime(long j) {
        try {
            PollingInfo xdbGetPollingInfo = xdbGetPollingInfo();
            xdbGetPollingInfo.setNextPollingTime(j);
            xdbSetPollingInfo(xdbGetPollingInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }
}
