package com.accessorydm.agent.fota;

import android.text.TextUtils;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBAdapter;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBFumoInfo;
import com.accessorydm.db.file.XDBUrlInfo;
import com.accessorydm.eng.core.XDMDDXmlDataSet;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.eng.parser.XDMXmlParser;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.tp.XTPAdapter;
import com.accessorydm.tp.XTPHttpUtil;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.nio.charset.Charset;

public class XFOTADlAgent implements XDMInterface, XEventInterface, XFOTAInterface, XTPInterface {
    private static final int XDL_MAX_DOWNLOAD_SIZE = 5888;
    private static final int XDM_HTTP_HEADER_MAX_SIZE = 768;
    public static XTPAdapter g_HttpDLAdapter;
    private static int m_nDLConnectRetryCount;
    private static int m_nDLConnectRetryFailCount;
    private static String[] m_szReportStatus;
    public XFOTADlAgentHandler m_DlAgentHandler = null;

    public XFOTADlAgent() {
        m_szReportStatus = new String[12];
        if (g_HttpDLAdapter == null) {
            g_HttpDLAdapter = new XTPAdapter();
        }
        String[] strArr = m_szReportStatus;
        strArr[0] = "900 Success";
        strArr[1] = "901 Insufficient memory";
        strArr[2] = "902 User Cancelled";
        strArr[3] = "903 Loss of Service";
        strArr[4] = "905 Attribute mismatch";
        strArr[5] = "906 Invalid descriptor";
        strArr[6] = "951 Invalid DDVersion";
        strArr[7] = "952 Device Aborted";
        strArr[8] = "953 Non-Acceptable Content";
        strArr[9] = "954 Loader Error";
        strArr[10] = "462 Blocked by MDM Policy";
        strArr[11] = "";
    }

    public static String xfotaDlAgentGetReportStatus(int i) {
        return m_szReportStatus[i];
    }

    public static boolean xfotaDlAgentIsStatus() {
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        Log.I("nAgentStatus = [" + xdbGetFUMOStatus + "]");
        if (xdbGetFUMOStatus == 0) {
            return true;
        }
        if (xdbGetFUMOStatus == 10 || xdbGetFUMOStatus == 20 || xdbGetFUMOStatus == 30 || xdbGetFUMOStatus == 40) {
            return false;
        }
        if (xdbGetFUMOStatus == 65 || xdbGetFUMOStatus == 80 || xdbGetFUMOStatus == 100) {
            return true;
        }
        if (xdbGetFUMOStatus == 200 || xdbGetFUMOStatus == 230) {
            return false;
        }
        return xdbGetFUMOStatus == 240 || xdbGetFUMOStatus == 241;
    }

    public static int xfotaDlAgentGetHttpConStatus() {
        Log.I("");
        int xdbGetFileIdFirmwareData = XDB.xdbGetFileIdFirmwareData();
        long xdbGetFileSize = XDB.xdbGetFileSize(xdbGetFileIdFirmwareData);
        long xdbGetObjectSizeFUMO = XDBFumoAdp.xdbGetObjectSizeFUMO();
        int i = (xdbGetFileSize > xdbGetObjectSizeFUMO ? 1 : (xdbGetFileSize == xdbGetObjectSizeFUMO ? 0 : -1));
        if (i == 0) {
            Log.I("offset = " + xdbGetFileSize + "  TotalSize = " + xdbGetObjectSizeFUMO);
            return 0;
        } else if (i <= 0) {
            return 1;
        } else {
            XDB.xdbDeleteFile(xdbGetFileIdFirmwareData);
            Log.E("received oversize delta");
            Log.E("offset =" + xdbGetFileSize + "  TotalSize = " + xdbGetObjectSizeFUMO);
            return -2;
        }
    }

    public static String xfotaDlAgentGetHttpContentRange(boolean z) {
        long xdbGetFileSize = XDB.xdbGetFileSize(XDB.xdbGetFileIdFirmwareData());
        long xdbGetObjectSizeFUMO = XDBFumoAdp.xdbGetObjectSizeFUMO();
        Log.I("nOffset = " + xdbGetFileSize + " nTotalObjectSize = " + xdbGetObjectSizeFUMO);
        if (!z) {
            long j = 5888 < xdbGetObjectSizeFUMO - xdbGetFileSize ? (5888 + xdbGetFileSize) - 1 : xdbGetObjectSizeFUMO - 1;
            String valueOf = String.valueOf(xdbGetFileSize);
            Log.I("offset = " + xdbGetFileSize + " , downloadsize = " + j + ", TotalSize = " + xdbGetObjectSizeFUMO);
            return valueOf;
        }
        String valueOf2 = String.valueOf(xdbGetFileSize);
        Log.I("offset = " + xdbGetFileSize + ", TotalSize = " + xdbGetObjectSizeFUMO);
        return valueOf2;
    }

    public static int xfotaDlAgentParserDescriptor(byte[] bArr) {
        XDMDDXmlDataSet xDMDDXmlDataSet;
        int i;
        try {
            xDMDDXmlDataSet = new XDMXmlParser().xdmXmlParserDownloadDescriptor(bArr);
            i = 11;
        } catch (Exception e) {
            Log.E(e.toString());
            xDMDDXmlDataSet = null;
            i = 5;
        }
        return xDMDDXmlDataSet != null ? xfotaDlAgentSetFUMOObjectFromDD(xDMDDXmlDataSet) : i;
    }

    private static int xfotaDlAgentSetFUMOObjectFromDD(XDMDDXmlDataSet xDMDDXmlDataSet) {
        if (TextUtils.isEmpty(xDMDDXmlDataSet.m_szType) || TextUtils.isEmpty(xDMDDXmlDataSet.m_szSize) || TextUtils.isEmpty(xDMDDXmlDataSet.m_szObjectURI)) {
            return 5;
        }
        if (!XDMInterface.SYNCML_MIME_TYPE_DOWNLOAD_TYPE.equals(xDMDDXmlDataSet.m_szType)) {
            return 8;
        }
        XDBFumoInfo xdbGetFumoInfo = XDBFumoAdp.xdbGetFumoInfo();
        if (xdbGetFumoInfo == null) {
            return 11;
        }
        xdbGetFumoInfo.m_szContentType = xDMDDXmlDataSet.m_szType;
        xdbGetFumoInfo.m_szAcceptType = xDMDDXmlDataSet.m_szType;
        if (!TextUtils.isEmpty(xDMDDXmlDataSet.m_szObjectURI)) {
            String xdbCheckOMADDURL = XDBAdapter.xdbCheckOMADDURL(xDMDDXmlDataSet.m_szObjectURI);
            XDBUrlInfo xtpURLParser = XTPHttpUtil.xtpURLParser(xdbCheckOMADDURL);
            if (TextUtils.isEmpty(xtpURLParser.pAddress)) {
                Log.E("Wrong Address");
                Log.H("m_szObjectURI szTempURL: " + xdbCheckOMADDURL);
            } else {
                int i = xtpURLParser.nPort;
                xdbGetFumoInfo.m_szObjectDownloadUrl = xtpURLParser.pURL;
                xdbGetFumoInfo.m_szObjectDownloadIP = xtpURLParser.pAddress;
                if (i == 0 || i > 65535) {
                    i = xdbGetFumoInfo.ServerPort;
                }
                xdbGetFumoInfo.nObjectDownloadPort = i;
            }
        }
        if (!TextUtils.isEmpty(xDMDDXmlDataSet.m_szInstallNotifyURI)) {
            String xdbCheckOMADDURL2 = XDBAdapter.xdbCheckOMADDURL(xDMDDXmlDataSet.m_szInstallNotifyURI);
            XDBUrlInfo xtpURLParser2 = XTPHttpUtil.xtpURLParser(xdbCheckOMADDURL2);
            if (TextUtils.isEmpty(xtpURLParser2.pAddress)) {
                Log.E("Wrong Address");
                Log.H("m_szInstallNotifyURI szTempURL: " + xdbCheckOMADDURL2);
            } else {
                xdbGetFumoInfo.m_szStatusNotifyUrl = xtpURLParser2.pURL;
                xdbGetFumoInfo.m_szStatusNotifyIP = xtpURLParser2.pAddress;
                xdbGetFumoInfo.m_szStatusNotifyProtocol = xtpURLParser2.pProtocol;
                int i2 = xtpURLParser2.nPort;
                if (i2 == 0 || i2 > 65535) {
                    i2 = xdbGetFumoInfo.ServerPort;
                }
                xdbGetFumoInfo.nStatusNotifyPort = i2;
            }
        }
        if (!TextUtils.isEmpty(xDMDDXmlDataSet.m_szInstallParam)) {
            xdbGetFumoInfo.szObjectHash = xDMDDXmlDataSet.m_szInstallParam.replaceAll("MD5=", "");
            String[] split = xDMDDXmlDataSet.m_szInstallParam.split(";");
            for (int i3 = 0; i3 < split.length; i3++) {
                if (split[i3].contains("MD5=")) {
                    xdbGetFumoInfo.szObjectHash = split[i3].substring(split[i3].indexOf(61) + 1);
                } else if (split[i3].contains("updateFwV=")) {
                    xdbGetFumoInfo.szUpdateFWVer = split[i3].substring(split[i3].indexOf(61) + 1);
                    Log.I("szUpdateFWVer : " + xdbGetFumoInfo.szUpdateFWVer);
                }
            }
        }
        if (!TextUtils.isEmpty(xDMDDXmlDataSet.m_szSize)) {
            xdbGetFumoInfo.nObjectSize = Long.parseLong(xDMDDXmlDataSet.m_szSize);
        }
        if (!TextUtils.isEmpty(xDMDDXmlDataSet.m_szDescription)) {
            xdbGetFumoInfo.szDescription = xDMDDXmlDataSet.m_szDescription;
        }
        if (!(TextUtils.isEmpty(xDMDDXmlDataSet.m_szDDVersion) || "1".equals(xDMDDXmlDataSet.m_szDDVersion.substring(0, xDMDDXmlDataSet.m_szDDVersion.indexOf(XDMInterface.XDM_BASE_PATH))))) {
            return 6;
        }
        XDBFumoAdp.xdbSetFumoInfo(xdbGetFumoInfo);
        return 11;
    }

    public static void xfotaDlAgentDownloadFailed() {
        int i;
        Log.I("");
        try {
            g_HttpDLAdapter.xtpAdpSetHttpObj(XDBFumoAdp.xdbGetStatusAddrFUMO(), "", "", HttpNetworkInterface.XTP_HTTP_METHOD_POST, 1);
            String xdbGetFUMODownloadResultCode = XDBFumoAdp.xdbGetFUMODownloadResultCode();
            Log.I("szDownloadResultCode : " + xdbGetFUMODownloadResultCode);
            if (TextUtils.isEmpty(xdbGetFUMODownloadResultCode)) {
                xdbGetFUMODownloadResultCode = xfotaDlAgentGetReportStatus(7);
                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_UNDEFINED_ERROR);
            } else if (xfotaDlAgentGetReportStatus(1).equals(xdbGetFUMODownloadResultCode)) {
                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_DOWNLOAD_FAILED_OUT_MEMORY);
            } else if (xfotaDlAgentGetReportStatus(2).equals(xdbGetFUMODownloadResultCode)) {
                XDBFumoAdp.xdbSetFUMOResultCode("401");
            } else if (xfotaDlAgentGetReportStatus(4).equals(xdbGetFUMODownloadResultCode)) {
                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_DOWNLOAD_FAILED_NETWORK);
            } else if (xfotaDlAgentGetReportStatus(5).equals(xdbGetFUMODownloadResultCode)) {
                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_UNDEFINED_ERROR);
            } else if (xfotaDlAgentGetReportStatus(6).equals(xdbGetFUMODownloadResultCode)) {
                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_UNDEFINED_ERROR);
            } else if (xfotaDlAgentGetReportStatus(8).equals(xdbGetFUMODownloadResultCode)) {
                XDBFumoAdp.xdbSetFUMOResultCode("405");
            } else if (xfotaDlAgentGetReportStatus(9).equals(xdbGetFUMODownloadResultCode)) {
                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_BAD_URL);
            } else if (xfotaDlAgentGetReportStatus(10).equals(xdbGetFUMODownloadResultCode)) {
                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_BLOCKED_MDM_UPDATE_FAILED);
            }
            try {
                i = g_HttpDLAdapter.xtpSendPackage(xdbGetFUMODownloadResultCode.getBytes(Charset.defaultCharset()), xdbGetFUMODownloadResultCode.length(), 1);
            } catch (Exception e) {
                Log.E(e.toString());
                i = -3;
            }
            if (i == 0) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONTINUE, null, null);
            } else if (i == -2) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECTFAIL, null, null);
            } else {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
            }
        } catch (NullPointerException e2) {
            Log.E(e2.toString());
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
        }
    }

    public void xfotaDlTpClose(int i) {
        g_HttpDLAdapter.xtpAdpClose(i);
    }

    public int xfotaDlTpInit(int i) {
        return g_HttpDLAdapter.xtpAdpInit(i);
    }

    public void xfotaDlTpCloseNetWork(int i) {
        g_HttpDLAdapter.xtpAdpCloseNetWork(i);
    }

    public boolean xfotaDlTpCheckRetry() {
        Log.I("DL ConnectRetryCount " + m_nDLConnectRetryCount);
        int i = m_nDLConnectRetryCount;
        if (i >= 15) {
            m_nDLConnectRetryCount = 0;
            return false;
        }
        m_nDLConnectRetryCount = i + 1;
        return true;
    }

    public static void xfotaDlTpSetRetryCount(int i) {
        m_nDLConnectRetryCount = i;
    }

    public static int xfotaDlTpGetRetryCount() {
        return m_nDLConnectRetryCount;
    }

    public static int xfotaDlTpGetRetryFailCount() {
        Log.I("xfotaDlTpGetRetryFailCount : " + m_nDLConnectRetryFailCount);
        return m_nDLConnectRetryFailCount;
    }

    public static void xfotaDlTpSetRetryFailCount(int i) {
        Log.I("xfotaDlTpGetRetryFailCount : " + i);
        m_nDLConnectRetryFailCount = i;
    }
}
