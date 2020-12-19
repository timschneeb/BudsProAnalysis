package com.accessorydm.db.file;

import android.text.TextUtils;
import com.accessorydm.agent.XDMAgent;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.tp.XTPHttpUtil;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;

public class XDBFumoAdp implements XFOTAInterface, XDMInterface {
    public static XDBFumoInfo xdbGetFumoInfo() {
        try {
            return XDMDbSqlQuery.xdmDbFetchFUMORow(1);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    public static void xdbSetFumoInfo(XDBFumoInfo xDBFumoInfo) {
        try {
            XDMDbSqlQuery.xdmDbUpdateFUMORow(1, xDBFumoInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static String xdbGetFUMOProtocol() {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            if (!(xdbGetFumoInfo.nStatus == 40 || xdbGetFumoInfo.nStatus == 20)) {
                if (xdbGetFumoInfo.nStatus != 230) {
                    if (xdbGetFumoInfo.nStatus == 10) {
                        return xdbGetFumoInfo.m_szProtocol;
                    }
                    return xdbGetFumoInfo.m_szObjectDownloadProtocol;
                }
            }
            return xdbGetFumoInfo.m_szStatusNotifyProtocol;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static boolean xdbSetFUMOServerUrl(String str) {
        XDBUrlInfo xtpURLParser = XTPHttpUtil.xtpURLParser(str);
        if (TextUtils.isEmpty(xtpURLParser.pAddress)) {
            Log.E("Wrong Address");
            Log.H("xdbSetFUMOServerUrl szURL: " + str);
            return false;
        }
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.m_szProtocol = xtpURLParser.pProtocol;
            xdbGetFumoInfo.ServerUrl = xtpURLParser.pURL;
            xdbGetFumoInfo.ServerIP = xtpURLParser.pAddress;
            xdbGetFumoInfo.ServerPort = xtpURLParser.nPort;
            xdbSetFumoInfo(xdbGetFumoInfo);
            return true;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public static String xdbGetDownloadAddrFUMO() {
        String str;
        try {
            str = xdbGetFumoInfo().m_szObjectDownloadUrl;
        } catch (Exception e) {
            Log.E(e.toString());
            str = "";
        }
        Log.H("Download URI : " + str);
        return str;
    }

    public static String xdbGetStatusAddrFUMO() {
        try {
            return xdbGetFumoInfo().m_szStatusNotifyUrl;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static String xdbGetFUMOUpdateReportURI() {
        try {
            String str = xdbGetFumoInfo().m_szReportURI;
            if (TextUtils.isEmpty(str)) {
                return XDMAgent.xdmAgentGetDefaultLocuri();
            }
            return str;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetFUMOUpdateReportURI(String str) {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.m_szReportURI = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static long xdbGetObjectSizeFUMO() {
        try {
            return xdbGetFumoInfo().nObjectSize;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static int xdbGetFUMOStatus() {
        int i;
        try {
            i = xdbGetFumoInfo().nStatus;
        } catch (Exception e) {
            Log.E(e.toString());
            i = 0;
        }
        if (!XFOTADl.xfotaDownloadGetDrawingPercentage()) {
            Log.I("get FUMO_Status : " + i);
        }
        return i;
    }

    public static void xdbSetFUMOStatus(int i) {
        Log.I("set FUMO_Status : " + i);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.nStatus = i;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
            Log.E("xdbSetFUMOStatus db write was failed");
        }
        FotaProviderState.setFotaBadgeState(i);
    }

    public static void xdbSetFUMOStatusNode(String str) {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.m_szStatusNodeName = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetFUMOStatusNode() {
        String str;
        try {
            str = xdbGetFumoInfo().m_szStatusNodeName;
        } catch (Exception e) {
            Log.E(e.toString());
            str = "";
        }
        Log.I("pNodeName = " + str);
        return str;
    }

    public static String xdbGetFUMOResultCode() {
        String str;
        try {
            str = xdbGetFumoInfo().ResultCode;
        } catch (Exception e) {
            Log.E(e.toString());
            str = "";
        }
        Log.I("get ResultCode:" + str);
        return str;
    }

    public static void xdbSetFUMOResultCode(String str) {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.ResultCode = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
            Log.I("set ResultCode: " + str);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetFUMOUpdateMechanism() {
        try {
            int i = xdbGetFumoInfo().nUpdateMechanism;
            if (i >= 5) {
                return 0;
            }
            return i;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void xdbSetFUMOUpdateMechanism(int i) {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.nUpdateMechanism = i;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static Boolean xdbGetFUMODownloadMode() {
        boolean z;
        try {
            z = xdbGetFumoInfo().nDownloadMode;
        } catch (Exception e) {
            Log.E(e.toString());
            z = false;
        }
        return Boolean.valueOf(z);
    }

    public static void xdbSetFUMODownloadMode(Boolean bool) {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.nDownloadMode = bool.booleanValue();
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetFUMOCorrelator() {
        String str;
        try {
            str = xdbGetFumoInfo().Correlator;
        } catch (Exception e) {
            Log.E(e.toString());
            str = "";
        }
        Log.H("szCorrelator = " + str);
        return str;
    }

    public static void xdbSetFUMOCorrelator(String str) {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.Correlator = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
        Log.H("szCorrelator = " + str);
    }

    public static String xdbGetFUMODDContentType() {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            if (xdbGetFumoInfo.nStatus == 200 || xdbGetFumoInfo.nStatus == 30) {
                return xdbGetFumoInfo.m_szContentType;
            }
            return "";
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static String xdbGetFUMODDAcceptType() {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            if (xdbGetFumoInfo.nStatus == 200 || xdbGetFumoInfo.nStatus == 30) {
                return xdbGetFumoInfo.m_szAcceptType;
            }
            return "";
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static String xdbGetFUMODescription() {
        try {
            return xdbGetFumoInfo().szDescription;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetFUMODescription(String str) {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.szDescription = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static boolean xdbGetFUMOOptionalUpdate() {
        boolean z;
        try {
            z = xdbGetFumoInfo().m_bOptionalUpdate;
        } catch (Exception e) {
            Log.E(e.toString());
            z = false;
        }
        Log.I("OptionalUpdate Type: " + z);
        return z;
    }

    public static void xdbSetFUMOOptionalUpdate(boolean z) {
        Log.I("OptionalUpdate Type: " + z);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.m_bOptionalUpdate = z;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static boolean xdbGetFUMOWifiOnlyDownload() {
        boolean z;
        try {
            z = xdbGetFumoInfo().m_bWifiOnlyDownload;
        } catch (Exception e) {
            Log.E(e.toString());
            z = false;
        }
        Log.I("WifiOnlyDownload Type: " + z);
        return z;
    }

    public static void xdbSetFUMOWifiOnlyDownload(boolean z) {
        Log.I("WifiOnlyDownload Type: " + z);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.m_bWifiOnlyDownload = z;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static boolean xdbGetFUMOCheckRooting() {
        boolean z;
        try {
            z = xdbGetFumoInfo().m_bCheckRooting;
        } catch (Exception e) {
            Log.E(e.toString());
            z = false;
        }
        Log.I("CheckRooting Type: " + z);
        return z;
    }

    public static void xdbSetFUMOCheckRooting(boolean z) {
        Log.I("CheckRooting Type: " + z);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.m_bCheckRooting = z;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetFUMOInitiatedType() {
        int i;
        try {
            i = xdbGetFumoInfo().nInitiatedType;
        } catch (Exception e) {
            Log.E(e.toString());
            i = 0;
        }
        Log.I("Initiated Type: " + i);
        return i;
    }

    public static void xdbSetFUMOInitiatedType(int i) {
        Log.I("Initiated Type: " + i);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.nInitiatedType = i;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetFUMODownloadResultCode() {
        try {
            return xdbGetFumoInfo().szDownloadResultCode;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetFUMODownloadResultCode(String str) {
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.szDownloadResultCode = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetFUMOCopyRetryCount() {
        try {
            return xdbGetFumoInfo().nCopyRetryCount;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void xdbSetFUMOCopyRetryCount(int i) {
        Log.I("Copy Fail Count : " + i);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.nCopyRetryCount = i;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetFUMODownloadConnType() {
        String str;
        try {
            str = xdbGetFumoInfo().szNetworkConnType;
        } catch (Exception e) {
            Log.E(e.toString());
            str = "";
        }
        Log.I("DownloadConnType : " + str);
        return str;
    }

    public static void xdbSetFUMODownloadConnType(String str) {
        try {
            Log.I("DownloadConnType : " + str);
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.szNetworkConnType = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetFUMOLowBatteryRetryCount() {
        try {
            return xdbGetFumoInfo().nLowBatteryRetryCount;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void xdbSetFUMOLowBatteryRetryCount(int i) {
        Log.I("Low Battery Count : " + i);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.nLowBatteryRetryCount = i;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetFUMODeltaHash() {
        String str;
        try {
            str = xdbGetFumoInfo().szObjectHash;
        } catch (Exception e) {
            Log.E(e.toString());
            str = "";
        }
        Log.I("DeltaHash : " + str);
        return str;
    }

    public static void xdbSetFUMODeltaHash(String str) {
        try {
            Log.I("DeltaHash : " + str);
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.szObjectHash = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetUpdateFWVer() {
        try {
            return xdbGetFumoInfo().szUpdateFWVer;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetUpdateFWVer(String str) {
        Log.I("Set UpdateFWVer : " + str);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.szUpdateFWVer = str;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetUiMode() {
        int i;
        try {
            i = xdbGetFumoInfo().nUiMode;
        } catch (Exception e) {
            Log.E(e.toString());
            i = 0;
        }
        Log.I("Get UiMode : " + i);
        return i;
    }

    public static void xdbSetUiMode(int i) {
        Log.I("Set UiMode : " + i);
        try {
            XDBFumoInfo xdbGetFumoInfo = xdbGetFumoInfo();
            xdbGetFumoInfo.nUiMode = i;
            xdbSetFumoInfo(xdbGetFumoInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }
}
