package com.accessorydm.db.file;

import java.io.Serializable;

public class XDBFumoInfo implements Serializable {
    private static final long serialVersionUID = 1;
    public int AuthType;
    public String Correlator = "";
    public int ObexType;
    public String ResultCode = "";
    public String ServerIP = "";
    public int ServerPort;
    public String ServerUrl = "";
    public boolean bUpdateWait;
    public boolean m_bBigDeltaDownload = false;
    public boolean m_bCheckRooting = false;
    public boolean m_bOptionalCancel = false;
    public boolean m_bOptionalUpdate = false;
    public boolean m_bWifiOnlyDownload = false;
    public String m_szAcceptType = "";
    public String m_szContentType = "";
    public String m_szObjectDownloadIP = "";
    public String m_szObjectDownloadProtocol = "";
    public String m_szObjectDownloadUrl = "";
    public String m_szProtocol = "";
    public String m_szReportURI = "";
    public String m_szStatusNodeName = "";
    public String m_szStatusNotifyIP = "";
    public String m_szStatusNotifyProtocol = "";
    public String m_szStatusNotifyUrl = "";
    public int nCopyRetryCount;
    public int nCurrentDownloadMode;
    public boolean nDownloadMode = false;
    public int nFFSWriteSize;
    public int nInitiatedType;
    public int nLowBatteryRetryCount;
    public int nObjectDownloadPort;
    public long nObjectSize;
    public int nStatus;
    public int nStatusNotifyPort;
    public int nUiMode;
    public int nUpdateMechanism;
    public String szDescription;
    public String szDownloadResultCode = "";
    public String szNetworkConnType = "";
    public String szObjectHash = "";
    public String szUpdateFWVer = "";
}
