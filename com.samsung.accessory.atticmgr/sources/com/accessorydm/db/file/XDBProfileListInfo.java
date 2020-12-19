package com.accessorydm.db.file;

import com.accessorydm.interfaces.XDMInterface;
import java.io.Serializable;

public class XDBProfileListInfo implements Serializable, XDMInterface {
    private static final long serialVersionUID = 1;
    public int MagicNumber;
    public XDBSessionSaveInfo NotiResumeState = new XDBSessionSaveInfo();
    public String[] ProfileName = new String[2];
    public int Profileindex = 0;
    public boolean bAutoUpdate = false;
    public boolean bPushMessage = false;
    public boolean bSkipDevDiscovery = false;
    public boolean bWifiOnly = false;
    public String m_szNetworkConnName = "";
    public String m_szSessionID;
    public int nDDFParserNodeIndex = 0;
    public long nDestoryNotiTime;
    public int nDeviceRegister = 0;
    public int nNotiEvent;
    public int nNotiJobId;
    public int nNotiOpMode;
    public int nNotiReSyncMode;
    public int nProxyIndex = 0;
    public int nSaveDeltaFileIndex = 0;
    public XDBUICResultKeepInfo tUicResultKeep = new XDBUICResultKeepInfo();
}
