package com.accessorydm.agent.fota;

import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.filetransfer.XDMFileTransferManager;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.tp.XTPAdapter;
import com.samsung.android.fotaprovider.log.Log;

public class XFOTADl implements XDMDefInterface, XDMInterface, XEventInterface, XFOTAInterface, XTPInterface {
    private static boolean m_bDrawingCopyPercentageStates = false;
    private static boolean m_bDrawingPercentageStates = false;
    private static int m_nDeltaDownState;

    private static boolean xfotaDownUserInteractAction() {
        int xfotaDlAgentHdlrCheckDeltaPkgSize = XFOTADlAgentHandler.xfotaDlAgentHdlrCheckDeltaPkgSize();
        if (xfotaDlAgentHdlrCheckDeltaPkgSize == 2) {
            Log.I("FFS memory Insufficient");
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_MEMORY_FULL);
            return false;
        } else if (xfotaDlAgentHdlrCheckDeltaPkgSize != -2) {
            return true;
        } else {
            return false;
        }
    }

    public static int xfotaDownloadMemoryCheck(int i) {
        Log.I("");
        xfotaSetDeltaDownState(i);
        if (!xfotaDownUserInteractAction()) {
            return -1;
        }
        if (i != 1) {
            return 0;
        }
        if (!XDB.xdbAdpDeltaFolderCreate()) {
            Log.E("xdbAdpDeltaFolderCreate fail!!");
            xfotaMemoryFullReport();
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
            return -1;
        }
        XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_PROGRESS);
        XDMFileTransferManager.checkDeviceInfo();
        return 0;
    }

    public static void xfotaDownloadSetDrawingPercentage(boolean z) {
        Log.D("set to m_bDrawingPercentageStates: " + z);
        m_bDrawingPercentageStates = z;
    }

    public static boolean xfotaDownloadGetDrawingPercentage() {
        Log.D("m_bDrawingPercentageStates = " + m_bDrawingPercentageStates);
        return m_bDrawingPercentageStates;
    }

    public static void xfotaCopySetDrawingPercentage(boolean z) {
        Log.D("set to m_bDrawingCopyPercentageStates: " + z);
        m_bDrawingCopyPercentageStates = z;
    }

    public static boolean xfotaCopyGetDrawingPercentage() {
        Log.D("m_bDrawingCopyPercentageStates = " + m_bDrawingPercentageStates);
        return m_bDrawingCopyPercentageStates;
    }

    public static void xfotaSetDeltaDownState(int i) {
        m_nDeltaDownState = i;
    }

    public static int xfotaGetDeltaDownState() {
        return m_nDeltaDownState;
    }

    private static void xfotaMemoryFullReport() {
        XDBFumoAdp.xdbSetFUMODownloadResultCode(XFOTADlAgentHandler.xfotaDlAgentGetReportStatus(1));
        XDBFumoAdp.xdbSetFUMOStatus(20);
        if (XTPAdapter.xtpAdpCheckURL(XDBFumoAdp.xdbGetDownloadAddrFUMO(), XDBFumoAdp.xdbGetStatusAddrFUMO()) == 2) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
        }
        XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_MEMORY_FULL);
    }
}
