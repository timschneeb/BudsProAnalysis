package com.accessorydm.adapter;

import com.accessorydm.agent.XDMAgent;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBAgentAdp;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBLastUpdateAdp;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XNOTIInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.ui.XUIAdapter;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.FotaProviderUtil;

public class XDMInitAdapter implements XDMDefInterface, XDMInterface, XEventInterface, XCommonInterface, XFOTAInterface, XNOTIInterface {
    public static int xdmInitAdpCheckNetworkReady() {
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        if (xdbGetFUMOStatus == 50 || xdbGetFUMOStatus == 220 || xdbGetFUMOStatus == 251) {
            if (XDB.xdbAdpFileExists(null, XDB.xdbGetFileIdFirmwareData()) == -1) {
                Log.E("File Not Exist");
                XDBFumoAdp.xdbSetFUMOStatus(0);
            } else {
                Log.E("Already Download");
                return 3;
            }
        }
        if (XDMAgent.xdmAgentGetSyncMode() == 0) {
            return 0;
        }
        Log.I("XDM_NETWORK_STATE_SYNCML_USE");
        return 2;
    }

    public static void xdmAccessoryUpdateResultReport() {
        String xdbGetFUMOResultCode = XDBFumoAdp.xdbGetFUMOResultCode();
        XDBProfileListAdp.xdbSetNotiEvent(0);
        if (xdbGetFUMOResultCode.equals("200")) {
            Log.I("SUCCESSFUL_UPDATE");
            XDBFumoAdp.xdbSetFUMOStatus(100);
            XDBLastUpdateAdp.setSuccessfulLastUpdateInfo();
            FotaProviderUtil.sendLastUpdateInfo();
        } else {
            Log.I("UPDATE_FAILED");
            XDBFumoAdp.xdbSetFUMOStatus(80);
        }
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
        XDBAgentAdp.xdbSetDmAgentType(1);
        XUIAdapter.xuiAdpSetReportingToServer(true);
    }

    public static void xdmAccessoryUpdateResultSetAndReport(String str) {
        Log.I("Set Update Result : " + str);
        XDBFumoAdp.xdbSetFUMOResultCode(str);
        xdmAccessoryUpdateResultReport();
    }

    public static void xdmAccessoryUserCancelReport() {
        Log.I("");
        reportActionBy(XFOTAInterface.XDL_STATE_USER_CANCEL_REPORTING);
    }

    public static void xdmAccessoryDownloadFailedReport() {
        Log.I("");
        reportActionBy(241);
        XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_CONNECT_FAILED);
    }

    private static void reportActionBy(int i) {
        XDBFumoAdp.xdbSetFUMOStatus(i);
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
        XUIAdapter.xuiAdpSetReportingToServer(true);
    }
}
