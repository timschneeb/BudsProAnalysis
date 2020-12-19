package com.accessorydm.tp;

import android.text.TextUtils;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.adapter.XDMCommonUtils;
import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.agent.XDMDebug;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBAdapter;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBProfileAdp;
import com.accessorydm.db.file.XDBUrlInfo;
import com.accessorydm.eng.core.XDMHmacData;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.exception.file.ExceptionFileIO;
import com.accessorydm.exception.file.ExceptionFileNotFound;
import com.accessorydm.exception.file.ExceptionStorageNotUsed;
import com.accessorydm.exception.http.ExceptionHttpNetwork;
import com.accessorydm.exception.http.ExceptionHttpSend;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.network.NetworkBlockedType;
import com.accessorydm.network.NetworkChecker;
import com.accessorydm.tp.downloadInfo.UpdateDownloadInfo;
import com.accessorydm.tp.urlconnect.HttpNetworkInfo;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.accessorydm.tp.urlconnect.HttpURLConnectionAdapter;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class XTPAdapter implements XDMDefInterface, XDMInterface, XTPInterface, XEventInterface, XFOTAInterface, XDBInterface {
    public static XTPHttpObj[] g_HttpObj = null;
    private static boolean m_bIsConnected = false;
    private static int m_nHttpDebugCount;
    private XTPNetworkTimer networkTimer = null;
    private HttpURLConnection request = null;

    public XTPAdapter() {
        if (g_HttpObj == null) {
            g_HttpObj = new XTPHttpObj[2];
        }
    }

    public int xtpAdpInit(int i) {
        Log.I("xtpAdpInit");
        XTPHttpObj[] xTPHttpObjArr = g_HttpObj;
        if (xTPHttpObjArr[i] == null) {
            xTPHttpObjArr[i] = new XTPHttpObj();
        }
        int xtpAdpHttpInit = xtpAdpHttpInit(i);
        if (xtpAdpHttpInit != 0) {
            Log.E("xtpAdpHttpInit Fail!!");
        }
        return xtpAdpHttpInit;
    }

    public int xtpAdpOpen(int i) {
        int xtpAdpInit;
        if (g_HttpObj[i] != null || (xtpAdpInit = xtpAdpInit(i)) == 0) {
            XDMDmUtils.getInstance().xdmWakeLockAcquire("TP_OPEN_WAKE_LOCK");
            XDMDmUtils.getInstance().xdmWifiLockAcquire("TP_OPEN_WIFI_LOCK");
            try {
                NetworkBlockedType networkBlockType = NetworkChecker.get().getNetworkBlockType();
                if (networkBlockType.isBlocked()) {
                    Log.I("Can not go to DL Session - blockedType is " + networkBlockType);
                    return -2;
                }
                xtpAdpSetIsConnected(true);
                return 0;
            } catch (Exception e) {
                Log.E(e.toString());
                return -2;
            }
        } else {
            Log.E("xtpAdpInit Fail!!");
            return xtpAdpInit;
        }
    }

    public int xtpSendPackage(byte[] bArr, int i, int i2) {
        Log.I("");
        if (g_HttpObj[i2] == null) {
            return -2;
        }
        HttpURLConnectionAdapter httpURLConnectionAdapter = new HttpURLConnectionAdapter();
        try {
            this.request = httpURLConnectionAdapter.createHttpUrlConnection(i2);
            int sendMakeHeader = httpURLConnectionAdapter.sendMakeHeader(g_HttpObj[i2], this.request, i);
            if (sendMakeHeader != 0) {
                return -3;
            }
            httpURLConnectionAdapter.httpHeaderRequestLog(this.request);
            this.networkTimer = new XTPNetworkTimer(i2, XTPInterface.NetworkTimerMode.NETWORK_TIMER_MODE_SEND);
            if (i > 0) {
                httpURLConnectionAdapter.httpUrlSendData(this.request, bArr, this.networkTimer);
            } else {
                httpURLConnectionAdapter.httpUrlConnect(this.request, this.networkTimer);
            }
            xtpAdpWBXMLLog(i2, bArr);
            HttpNetworkInfo.getInstance().setURLConnect(this.request);
            return sendMakeHeader;
        } catch (ExceptionHttpNetwork | ExceptionHttpSend unused) {
            XTPNetworkTimer xTPNetworkTimer = this.networkTimer;
            if (xTPNetworkTimer != null) {
                xTPNetworkTimer.networkEndTimer();
                this.networkTimer = null;
            }
            httpURLConnectionAdapter.disconnectHttpUrlConnection(this.request);
            return -3;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00d2 A[SYNTHETIC, Splitter:B:54:0x00d2] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00d9 A[Catch:{ Exception -> 0x00e1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ed A[SYNTHETIC, Splitter:B:64:0x00ed] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00f4 A[Catch:{ Exception -> 0x00fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0108 A[SYNTHETIC, Splitter:B:76:0x0108] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x010f A[Catch:{ Exception -> 0x0117 }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0125 A[SYNTHETIC, Splitter:B:89:0x0125] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x012c A[Catch:{ Exception -> 0x0134 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int xtpReceivePackage(java.io.ByteArrayOutputStream r7, int r8) {
        /*
        // Method dump skipped, instructions count: 317
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.tp.XTPAdapter.xtpReceivePackage(java.io.ByteArrayOutputStream, int):int");
    }

    private int httpUrlReceiveDeltaFile(HttpURLConnectionAdapter httpURLConnectionAdapter, InputStream inputStream) {
        int i;
        RuntimeException e;
        Log.I("");
        long xdbGetObjectSizeFUMO = XDBFumoAdp.xdbGetObjectSizeFUMO();
        UpdateDownloadInfo updateDownloadInfo = new UpdateDownloadInfo();
        XUIProgressModel.getInstance().setTotalDeltaSize(xdbGetObjectSizeFUMO);
        if (!XDMCommonUtils.xdmGetTopActivityName().contains(XCommonInterface.PROGRESS_ACTIVITY_NAME)) {
            XDMMsg.xdmSendUIMessage(XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_PROGRESS, null, null);
        }
        try {
            updateDownloadInfo.openDeltaFileInputStream();
            updateDownloadInfo.setRemainDownloadSize(XDB.xdbGetFileSize(XDB.xdbGetFileIdFirmwareData()));
            XFOTADl.xfotaDownloadSetDrawingPercentage(true);
            while (xdbGetObjectSizeFUMO != updateDownloadInfo.getDownloadSize()) {
                byte[] httpUrlReceiveDeltaBodyData = httpURLConnectionAdapter.httpUrlReceiveDeltaBodyData(inputStream, HttpNetworkInterface.RECEIVE_DOWNLOAD_BUFFER_SIZE);
                if (httpUrlReceiveDeltaBodyData != null) {
                    if (httpUrlReceiveDeltaBodyData.length != 0) {
                        updateDownloadInfo.updateDownloadInfo(xdbGetObjectSizeFUMO, httpUrlReceiveDeltaBodyData);
                    }
                }
                Log.I("receiveDeltaFile is null");
                updateDownloadInfo.closeDeltaFileInputStream();
                updateDownloadInfo.resetFlexibleBufferWithDownloadInfo();
                return -4;
            }
            updateDownloadInfo.closeDeltaFileInputStream();
            updateDownloadInfo.resetFlexibleBufferWithDownloadInfo();
            return 0;
        } catch (ExceptionStorageNotUsed e2) {
            Log.E(e2.toString());
            i = -8;
            updateDownloadInfo.closeDeltaFileInputStream();
            updateDownloadInfo.resetFlexibleBufferWithDownloadInfo();
            return i;
        } catch (ExceptionFileNotFound e3) {
            e = e3;
            Log.E(e.toString());
            i = -9;
            updateDownloadInfo.closeDeltaFileInputStream();
            updateDownloadInfo.resetFlexibleBufferWithDownloadInfo();
            return i;
        } catch (ExceptionFileIO e4) {
            e = e4;
            Log.E(e.toString());
            i = -9;
            updateDownloadInfo.closeDeltaFileInputStream();
            updateDownloadInfo.resetFlexibleBufferWithDownloadInfo();
            return i;
        } catch (Exception e5) {
            Log.E(e5.toString());
        } catch (Throwable th) {
            updateDownloadInfo.closeDeltaFileInputStream();
            updateDownloadInfo.resetFlexibleBufferWithDownloadInfo();
            throw th;
        }
    }

    private void httpUrlReceiveData(ByteArrayOutputStream byteArrayOutputStream, int i, HttpURLConnectionAdapter httpURLConnectionAdapter, InputStream inputStream) {
        Log.I("");
        byte[] httpUrlReceiveBodyData = httpURLConnectionAdapter.httpUrlReceiveBodyData(inputStream, 5120);
        byteArrayOutputStream.reset();
        if (httpUrlReceiveBodyData.length > 0) {
            byteArrayOutputStream.write(httpUrlReceiveBodyData, 0, httpUrlReceiveBodyData.length);
            xtpAdpWBXMLLog(i, httpUrlReceiveBodyData);
        }
    }

    private int getResponseResultCode(int i) {
        if (g_HttpObj[i].nHttpReturnStatusValue >= 200 && g_HttpObj[i].nHttpReturnStatusValue < 300) {
            return 0;
        }
        if (i != 1) {
            return -6;
        }
        int i2 = g_HttpObj[i].nHttpReturnStatusValue;
        if (i2 == 403) {
            return -11;
        }
        if (i2 == 503) {
            return -13;
        }
        if (g_HttpObj[i].nHttpReturnStatusValue < 300 || g_HttpObj[i].nHttpReturnStatusValue >= 400) {
            return -6;
        }
        return -12;
    }

    private int xtpAdpHttpInit(int i) {
        XTPHttpObj[] xTPHttpObjArr = g_HttpObj;
        if (xTPHttpObjArr == null || xTPHttpObjArr[i] == null) {
            Log.E("g_HttpObj[appId] is null");
            return -1;
        }
        xTPHttpObjArr[i].appId = i;
        xTPHttpObjArr[i].protocol = 0;
        xTPHttpObjArr[i].nContentLength = -1;
        xTPHttpObjArr[i].nHeaderLength = -1;
        xTPHttpObjArr[i].nHttpReturnStatusValue = 0;
        xTPHttpObjArr[i].pHmacData = null;
        xTPHttpObjArr[i].m_szHttpOpenMode = HttpNetworkInterface.XTP_HTTP_METHOD_POST;
        xTPHttpObjArr[i].m_szHttpVersion = "HTTP/1.1";
        xTPHttpObjArr[i].m_szRequestUri = null;
        xTPHttpObjArr[i].m_szContentRange = null;
        xTPHttpObjArr[i].nTransferCoding = -1;
        xTPHttpObjArr[i].protocol = XDBAdapter.xdbGetConnectType(i);
        if (i == 0) {
            XTPHttpObj[] xTPHttpObjArr2 = g_HttpObj;
            xTPHttpObjArr2[i].nHttpConnection = 2;
            xTPHttpObjArr2[i].m_szHttpConnection = HttpNetworkInterface.XTP_HTTP_KEEPALIVE;
            xTPHttpObjArr2[i].m_szHttpMimeType = "application/vnd.syncml.dm+wbxml";
            xTPHttpObjArr2[i].m_szHttpAccept = "application/vnd.syncml.dm+wbxml";
        } else if (i == 1) {
            XTPHttpObj[] xTPHttpObjArr3 = g_HttpObj;
            xTPHttpObjArr3[i].nHttpConnection = 2;
            xTPHttpObjArr3[i].m_szHttpConnection = HttpNetworkInterface.XTP_HTTP_KEEPALIVE;
            xTPHttpObjArr3[i].m_szHttpMimeType = "application/vnd.oma.dd+xml";
            xTPHttpObjArr3[i].m_szHttpAccept = "application/vnd.oma.dd+xml";
        }
        g_HttpObj[i].m_szHttpUserAgent = XDMDevinfAdapter.xdmDevAdpGetHttpUserAgent();
        return xtpAdpGetHttpInfo(i);
    }

    public void xtpAdpClose(int i) {
        Log.I("");
        XDMDmUtils.getInstance().xdmWakeLockRelease();
        XDMDmUtils.getInstance().xdmWifiLockRelease();
        XTPHttpObj[] xTPHttpObjArr = g_HttpObj;
        if (xTPHttpObjArr != null && xTPHttpObjArr[i] != null) {
            try {
                HttpURLConnectionAdapter httpURLConnectionAdapter = new HttpURLConnectionAdapter();
                if (this.request != null) {
                    httpURLConnectionAdapter.disconnectHttpUrlConnection(this.request);
                    this.request = null;
                }
            } catch (Exception e) {
                Log.E(e.toString());
            }
            xtpAdpSetIsConnected(false);
        }
    }

    private int xtpAdpGetHttpInfo(int i) {
        XTPHttpObj[] xTPHttpObjArr = g_HttpObj;
        if (xTPHttpObjArr == null) {
            return -1;
        }
        xTPHttpObjArr[i].appId = i;
        String xdbGetServerUrl = XDB.xdbGetServerUrl(xTPHttpObjArr[i].appId);
        if (TextUtils.isEmpty(xdbGetServerUrl)) {
            Log.E("ServerURL is null!!");
            if (i == 0) {
                return -1;
            }
            return -7;
        }
        try {
            XDBUrlInfo xtpURLParser = XTPHttpUtil.xtpURLParser(xdbGetServerUrl);
            g_HttpObj[i].m_szServerURL = xtpURLParser.pURL;
            g_HttpObj[i].m_szServerAddr = xtpURLParser.pAddress;
            g_HttpObj[i].nServerPort = xtpURLParser.nPort;
            g_HttpObj[i].protocol = XTPHttpUtil.xtpHttpGetConnectType(xdbGetServerUrl);
            return 0;
        } catch (Exception e) {
            Log.E(e.toString());
            return -1;
        }
    }

    public int xtpAdpSetHttpObj(String str, String str2, String str3, String str4, int i) {
        if (g_HttpObj == null) {
            return -1;
        }
        if (!TextUtils.isEmpty(str)) {
            if (i == 0) {
                XDBUrlInfo xtpURLParser = XTPHttpUtil.xtpURLParser(str);
                int xdbGetConnectType = XDBAdapter.xdbGetConnectType(i);
                int xtpHttpExchangeProtocolType = XTPHttpUtil.xtpHttpExchangeProtocolType(xtpURLParser.pProtocol);
                String xdbGetServerAddress = XDB.xdbGetServerAddress(i);
                int xdbGetServerPort = XDB.xdbGetServerPort(i);
                if (TextUtils.isEmpty(xdbGetServerAddress)) {
                    Log.I("AddressOrg is null");
                    return -1;
                }
                if (str.length() > 0) {
                    XDBProfileAdp.xdbSetServerUrl(str);
                }
                if (!(xdbGetConnectType == xtpHttpExchangeProtocolType && xdbGetServerAddress.compareTo(xtpURLParser.pAddress) == 0 && xdbGetServerPort == xtpURLParser.nPort)) {
                    XDBProfileAdp.xdbSetServerAddress(xtpURLParser.pAddress);
                    XDBProfileAdp.xdbSetServerPort(xtpURLParser.nPort);
                    XDBProfileAdp.xdbSetServerProtocol(xtpURLParser.pProtocol);
                    return 2;
                }
            }
            g_HttpObj[i].m_szRequestUri = XTPHttpUtil.xtpHttpParsePath(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            g_HttpObj[i].pHmacData = str2;
        } else {
            g_HttpObj[i].pHmacData = null;
        }
        if (!TextUtils.isEmpty(str4)) {
            g_HttpObj[i].m_szHttpOpenMode = str4;
        } else {
            g_HttpObj[i].m_szHttpOpenMode = null;
        }
        if (!TextUtils.isEmpty(str3)) {
            g_HttpObj[i].m_szContentRange = str3;
        } else {
            g_HttpObj[i].m_szContentRange = null;
        }
        if (i != 1) {
            return 0;
        }
        XTPHttpObj[] xTPHttpObjArr = g_HttpObj;
        xTPHttpObjArr[i].m_szHttpMimeType = null;
        xTPHttpObjArr[i].m_szHttpAccept = null;
        xTPHttpObjArr[i].m_szHttpMimeType = XDBFumoAdp.xdbGetFUMODDContentType();
        g_HttpObj[i].m_szHttpAccept = XDBFumoAdp.xdbGetFUMODDAcceptType();
        if (TextUtils.isEmpty(g_HttpObj[i].m_szHttpMimeType)) {
            g_HttpObj[i].m_szHttpMimeType = "application/vnd.oma.dd+xml";
        }
        if (!TextUtils.isEmpty(g_HttpObj[i].m_szHttpAccept)) {
            return 0;
        }
        g_HttpObj[i].m_szHttpAccept = "application/vnd.oma.dd+xml";
        return 0;
    }

    public static boolean xtpAdpGetIsConnected() {
        Log.I("connect status is " + m_bIsConnected);
        return m_bIsConnected;
    }

    public static void xtpAdpSetIsConnected(boolean z) {
        m_bIsConnected = z;
    }

    public void xtpAdpCloseNetWork(int i) {
        g_HttpObj[i] = null;
    }

    private XDMHmacData xtpAdpHttpPsrParserHMAC(String str, long j) {
        XDMHmacData xDMHmacData = new XDMHmacData();
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String substring = str.substring(str.indexOf("algorithm="));
        xDMHmacData.m_szHmacAlgorithm = substring.substring(10, substring.indexOf(44));
        Log.H("algorithm:" + xDMHmacData.m_szHmacAlgorithm);
        String substring2 = str.substring(str.indexOf("username=\"") + 10);
        xDMHmacData.m_szHmacUserName = substring2.substring(0, substring2.indexOf(34));
        xDMHmacData.m_szHmacDigest = str.substring(str.indexOf("mac=")).substring(4);
        Log.H("startMac:" + xDMHmacData.m_szHmacDigest);
        xDMHmacData.httpContentLength = j;
        return xDMHmacData;
    }

    public XDMHmacData xtpAdpGetCurHMACData(int i) {
        try {
            return xtpAdpHttpPsrParserHMAC(g_HttpObj[i].pHmacData, (long) ((int) g_HttpObj[i].nContentLength));
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    private void xtpAdpWBXMLLog(int i, byte[] bArr) {
        if (XDMDebug.xdmIsDebugWbxmlFile() && i == 0) {
            try {
                XDMDebug.xdmWriteFile(XDMDmUtils.getInstance().xdmGetAccessorydmPath() + "httpdata" + m_nHttpDebugCount + ".wbxml", bArr);
                m_nHttpDebugCount = m_nHttpDebugCount + 1;
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
    }

    public static void xtpAdpResetWBXMLLog() {
        m_nHttpDebugCount = 0;
    }

    public static int xtpAdpCheckURL(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.E("Input Uri is NULL");
            return -5;
        }
        XDBUrlInfo xtpURLParser = XTPHttpUtil.xtpURLParser(str);
        XDBUrlInfo xtpURLParser2 = XTPHttpUtil.xtpURLParser(str2);
        if (xtpURLParser.pProtocol.equals(xtpURLParser2.pProtocol) && xtpURLParser.pAddress.equals(xtpURLParser2.pAddress) && xtpURLParser.nPort == xtpURLParser2.nPort) {
            return 0;
        }
        Log.I("different response url!!");
        return 2;
    }
}
