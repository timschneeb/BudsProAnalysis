package com.accessorydm.agent;

import android.text.TextUtils;
import com.accessorydm.adapter.XDMCommonUtils;
import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBAgentAdp;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.db.file.XDBProfileAdp;
import com.accessorydm.db.file.XDBProfileInfo;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.eng.core.XDMAccXNode;
import com.accessorydm.eng.core.XDMAuth;
import com.accessorydm.eng.core.XDMBase64;
import com.accessorydm.eng.core.XDMDDFXmlHandler;
import com.accessorydm.eng.core.XDMEncoder;
import com.accessorydm.eng.core.XDMHmacData;
import com.accessorydm.eng.core.XDMLinkedList;
import com.accessorydm.eng.core.XDMList;
import com.accessorydm.eng.core.XDMMem;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.eng.core.XDMOmAcl;
import com.accessorydm.eng.core.XDMOmLib;
import com.accessorydm.eng.core.XDMOmList;
import com.accessorydm.eng.core.XDMOmTree;
import com.accessorydm.eng.core.XDMOmTreeException;
import com.accessorydm.eng.core.XDMOmVfs;
import com.accessorydm.eng.core.XDMUic;
import com.accessorydm.eng.core.XDMVnode;
import com.accessorydm.eng.core.XDMWbxmlEncoder;
import com.accessorydm.eng.core.XDMWorkspace;
import com.accessorydm.eng.parser.XDMParser;
import com.accessorydm.eng.parser.XDMParserAdd;
import com.accessorydm.eng.parser.XDMParserAlert;
import com.accessorydm.eng.parser.XDMParserAtomic;
import com.accessorydm.eng.parser.XDMParserCopy;
import com.accessorydm.eng.parser.XDMParserCred;
import com.accessorydm.eng.parser.XDMParserDelete;
import com.accessorydm.eng.parser.XDMParserExec;
import com.accessorydm.eng.parser.XDMParserGet;
import com.accessorydm.eng.parser.XDMParserItem;
import com.accessorydm.eng.parser.XDMParserPcdata;
import com.accessorydm.eng.parser.XDMParserReplace;
import com.accessorydm.eng.parser.XDMParserResults;
import com.accessorydm.eng.parser.XDMParserSequence;
import com.accessorydm.eng.parser.XDMParserStatus;
import com.accessorydm.eng.parser.XDMParserSyncheader;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.interfaces.XUICInterface;
import com.accessorydm.tp.XTPAdapter;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManagerEnabler;
import com.samsung.android.fotaprovider.log.Log;
import java.nio.charset.Charset;
import java.util.Calendar;

public class XDMAgent implements XDMDefInterface, XDMInterface, XEventInterface, XFOTAInterface, XTPInterface, XUICInterface {
    private static final String DEFAULT_NONCE = "SamSungNextNonce=";
    private static final int PACKAGE_SIZE_GAP = 128;
    private static XDMWorkspace g_DmWs = null;
    private static XDMAccXNode m_DmAccXNodeInfo = null;
    private static XDMAccXNode m_DmAccXNodeTndsInfo = null;
    private static boolean m_bPendingStatus = false;
    private static int m_nChangedProtocolCount = 0;
    private static int m_nConnectRetryCount = 0;
    private static int m_nDMSync = 0;
    private static String szSvcState = "";
    public XDMParserAdd m_AddCmd;
    public XDMAgentHandler m_AgentHandler;
    public XDMParserAlert m_Alert;
    public XDMParserAtomic m_Atomic;
    public XDMParserCopy m_CopyCmd;
    public XDMParserDelete m_DeleteCmd;
    public XDMParserExec m_Exec;
    public XDMParserGet m_Get;
    public XDMParserSyncheader m_Header;
    public XTPAdapter m_HttpDMAdapter;
    public XDMParserReplace m_ReplaceCmd;
    public XDMParserSequence m_Sequence;
    public XDMParserStatus m_Status;
    public boolean m_bInProgresscmd;
    public String m_szCmd;

    public XDMAgent() {
        if (this.m_HttpDMAdapter == null) {
            this.m_HttpDMAdapter = new XTPAdapter();
        }
    }

    private static void xdmAgentInitParser(XDMWorkspace xDMWorkspace, XDMParser xDMParser) {
        xDMParser.xdmParParseInit(xDMParser, xDMWorkspace);
    }

    public XDMWorkspace xdmAgentGetWorkSpace() {
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (xDMWorkspace != null) {
            return xDMWorkspace;
        }
        Log.E("dm_ws is NULL");
        return null;
    }

    public static int xdmAgentGetSyncMode() {
        if (m_nDMSync != 0) {
            Log.I("nSync = " + m_nDMSync);
        }
        return m_nDMSync;
    }

    public static void xdmAgentSetSyncMode(int i) {
        Log.I("nSync = " + i);
        m_nDMSync = i;
    }

    private boolean xdmAgentIsAccessibleNode(String str) {
        if (!TextUtils.isEmpty(XDMMem.xdmLibStrstr(str, XDMInterface.XDM_APPAUTH_AAUTHSECRET_PATH)) || !TextUtils.isEmpty(XDMMem.xdmLibStrstr(str, XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH))) {
            return false;
        }
        String xdmDDFGetMOPath = XDMDDFXmlHandler.xdmDDFGetMOPath(10);
        if (!TextUtils.isEmpty(xdmDDFGetMOPath) && XDMMem.xdmLibStrncmp(str, xdmDDFGetMOPath, xdmDDFGetMOPath.length()) == 0) {
            return false;
        }
        return true;
    }

    private boolean xdmAgentIsPermanentNode(XDMOmTree xDMOmTree, String str) {
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str);
        return xdmOmGetNodeProp != null && xdmOmGetNodeProp.scope == 1;
    }

    private static int xdmAgentInit() {
        g_DmWs = new XDMWorkspace();
        if (g_DmWs == null) {
            return -1;
        }
        m_DmAccXNodeInfo = new XDMAccXNode();
        return 0;
    }

    public static void xdmAgentClose() {
        XDMWorkspace xDMWorkspace = g_DmWs;
        Log.I("inDMSync = " + m_nDMSync);
        if (m_nDMSync > 0) {
            if (xDMWorkspace != null) {
                if (m_bPendingStatus) {
                    Log.I("Pending Status don't save");
                    XDMOmLib.xdmOmVfsEnd(xDMWorkspace.om.vfs);
                } else {
                    Log.I("workspace save");
                    XDMOmLib.xdmOmEnd(xDMWorkspace.om);
                }
                xDMWorkspace.xdmFreeWorkSpace();
                g_DmWs = null;
            }
            xdmAgentSetSyncMode(0);
        }
    }

    private static int xdmAgentParsingWbxml(byte[] bArr) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        xDMWorkspace.nextMsg = false;
        xDMWorkspace.endOfMsg = false;
        XDMParser xDMParser = new XDMParser(bArr);
        xdmAgentInitParser(xDMWorkspace, xDMParser);
        if (xDMParser.xdmParParse() != 0) {
            return -2;
        }
        return 0;
    }

    private int xdmAgentVerifyServerAuth(XDMParserSyncheader xDMParserSyncheader) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMParserCred xDMParserCred = xDMParserSyncheader.cred;
        XDMHmacData xDMHmacData = xDMWorkspace.recvHmacData;
        Log.I("");
        if (TextUtils.isEmpty(xDMWorkspace.m_szServerID)) {
            Log.H("ServerID is null");
            return (xDMWorkspace.serverAuthState == -7 || xDMWorkspace.serverAuthState == -9) ? -1 : -9;
        }
        if (xDMWorkspace.serverCredType == 2) {
            if (xDMHmacData == null) {
                Log.H("HMAC is null");
                return -9;
            }
            Log.H("algorithm : " + xDMHmacData.m_szHmacAlgorithm);
            Log.H("digest : " + xDMHmacData.m_szHmacDigest);
            if (TextUtils.isEmpty(xDMHmacData.m_szHmacAlgorithm) || TextUtils.isEmpty(xDMHmacData.m_szHmacUserName) || TextUtils.isEmpty(xDMHmacData.m_szHmacDigest)) {
                Log.H("Any of MAC data is empty");
                return -9;
            } else if ("MD5".compareTo(xDMHmacData.m_szHmacAlgorithm) != 0) {
                Log.H("State No Credential");
                return -9;
            } else {
                Log.H("credtype:" + xDMWorkspace.serverCredType + ", nextNonce:" + new String(xDMWorkspace.serverNextNonce, Charset.defaultCharset()));
                StringBuilder sb = new StringBuilder();
                sb.append("httpContentLength:");
                sb.append(xDMHmacData.httpContentLength);
                Log.H(sb.toString());
                String xdmAuthMakeDigest = XDMAuth.xdmAuthMakeDigest(xDMWorkspace.serverCredType, xDMWorkspace.m_szServerID, xDMWorkspace.m_szServerPW, xDMWorkspace.serverNextNonce, xDMWorkspace.serverNextNonce.length, xDMWorkspace.buf.toByteArray(), xDMHmacData.httpContentLength);
                if (TextUtils.isEmpty(xdmAuthMakeDigest)) {
                    Log.H("key is null");
                    return -1;
                } else if (xdmAuthMakeDigest.compareTo(xDMHmacData.m_szHmacDigest) != 0) {
                    Log.H("key and pHMAC.m_szHmacDigest not equal");
                    return -1;
                }
            }
        } else if (xDMParserCred.meta == null) {
            return -9;
        } else {
            if (XDMAuth.xdmAuthCredString2Type(xDMParserCred.meta.m_szType) != xDMWorkspace.serverCredType) {
                Log.H("server auth type is mismatch");
                return -1;
            } else if (XDMInterface.CRED_TYPE_MD5.compareTo(xDMParserCred.meta.m_szType) == 0) {
                Log.H("CRED_TYPE_MD5 ws.serverCredType : " + xDMWorkspace.serverCredType);
                String xdmAuthMakeDigest2 = XDMAuth.xdmAuthMakeDigest(xDMWorkspace.serverCredType, xDMWorkspace.m_szServerID, xDMWorkspace.m_szServerPW, xDMWorkspace.serverNextNonce, xDMWorkspace.serverNextNonce.length, null, 0);
                if (TextUtils.isEmpty(xdmAuthMakeDigest2)) {
                    Log.H("key is null");
                    return -1;
                }
                Log.I("CRED_TYPE_MD5 key.compareTo(cred.data) != 0 key= " + xdmAuthMakeDigest2 + " cred.data= " + xDMParserCred.m_szData);
                if (xdmAuthMakeDigest2.compareTo(xDMParserCred.m_szData) != 0) {
                    Log.H("key.compareTo(cred.data) != 0 key= " + xdmAuthMakeDigest2 + " cred.data= " + xDMParserCred.m_szData);
                    Log.I("key and cred.data not equal");
                    return -1;
                }
            } else if (XDMInterface.CRED_TYPE_BASIC.compareTo(xDMParserCred.meta.m_szType) == 0) {
                String xdmAuthMakeDigest3 = XDMAuth.xdmAuthMakeDigest(xDMWorkspace.serverCredType, xDMWorkspace.m_szServerID, xDMWorkspace.m_szServerPW, "".getBytes(Charset.defaultCharset()), 0, null, 0);
                if (TextUtils.isEmpty(xdmAuthMakeDigest3)) {
                    Log.H("key is null");
                    return -1;
                } else if (xdmAuthMakeDigest3.compareTo(xDMParserCred.m_szData) != 0) {
                    Log.H("key.compareTo(cred.data) != 0 key= " + xdmAuthMakeDigest3 + " cred.data= " + xDMParserCred.m_szData);
                    Log.I("key and cred.data not equal");
                    return -1;
                }
            }
        }
        return 1;
    }

    public int xdmAgentSendPackage() {
        int i;
        XDMWorkspace xDMWorkspace = g_DmWs;
        int i2 = -3;
        if (xDMWorkspace.credType == 0 || xDMWorkspace.credType == 1) {
            try {
                i = this.m_HttpDMAdapter.xtpAdpSetHttpObj(xDMWorkspace.m_szTargetURI, "", "", HttpNetworkInterface.XTP_HTTP_METHOD_POST, 0);
                if (i == 2) {
                    m_bPendingStatus = true;
                } else {
                    if (m_bPendingStatus) {
                        m_bPendingStatus = false;
                    }
                    try {
                        i2 = this.m_HttpDMAdapter.xtpSendPackage(xDMWorkspace.buf.toByteArray(), xDMWorkspace.buf.size(), 0);
                    } catch (Exception e) {
                        Log.E(e.toString());
                    }
                    i = i2;
                    if (i == 0) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONTINUE, null, null);
                    } else if (i == -2) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                    } else {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_SENDFAIL, null, null);
                    }
                }
            } catch (NullPointerException e2) {
                Log.E(e2.toString());
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_SENDFAIL, null, null);
                return -3;
            }
        } else {
            Log.H("1: " + xDMWorkspace.m_szUserName);
            Log.H("1: " + xDMWorkspace.m_szClientPW);
            String xdmAuthMakeDigest = XDMAuth.xdmAuthMakeDigest(xDMWorkspace.credType, xDMWorkspace.m_szUserName, xDMWorkspace.m_szClientPW, xDMWorkspace.nextNonce, xDMWorkspace.nextNonce.length, xDMWorkspace.buf.toByteArray(), (long) XDMWbxmlEncoder.xdmWbxEncGetBufferSize());
            try {
                i = this.m_HttpDMAdapter.xtpAdpSetHttpObj(xDMWorkspace.m_szTargetURI, "algorithm=MD5, username=\"" + xDMWorkspace.m_szUserName + "\", mac=" + xdmAuthMakeDigest, "", HttpNetworkInterface.XTP_HTTP_METHOD_POST, 0);
                if (i == 2) {
                    m_bPendingStatus = true;
                } else {
                    if (m_bPendingStatus) {
                        m_bPendingStatus = false;
                    }
                    try {
                        i2 = this.m_HttpDMAdapter.xtpSendPackage(xDMWorkspace.buf.toByteArray(), XDMEncoder.xdmEncGetBufferSize(xDMWorkspace.e), 0);
                    } catch (Exception e3) {
                        Log.E(e3.toString());
                    }
                    i = i2;
                    if (i == 0) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONTINUE, null, null);
                    } else if (i == -2) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                    } else {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_SENDFAIL, null, null);
                    }
                }
            } catch (NullPointerException e4) {
                Log.E(e4.toString());
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_SENDFAIL, null, null);
                return -3;
            }
        }
        return i;
    }

    private String xdmAgentLibMakeSessionID() {
        Calendar instance = Calendar.getInstance();
        String format = String.format("%x%x", Integer.valueOf(String.valueOf(instance.get(12))), Integer.valueOf(instance.get(13)));
        Log.H("Make sessionid =" + format);
        return format;
    }

    public int xdmAgentStartSession() {
        Log.I("");
        if (xdmAgentInit() != 0) {
            return -1;
        }
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (XDBProfileListAdp.xdbGetNotiEvent() <= 0 || m_bPendingStatus) {
            xDMWorkspace.m_szSessionID = xdmAgentLibMakeSessionID();
        } else {
            xDMWorkspace.m_szSessionID = XDBProfileListAdp.xdbGetNotiSessionID();
        }
        try {
            if (xdmAgentMakeNode() != 0) {
                return -1;
            }
            return 0;
        } catch (XDMOmTreeException e) {
            Log.E(e.toString());
            Log.E("OmTree Delete");
            XDMOmLib.xdmOmVfsEnd(xDMWorkspace.om.vfs);
            XDMOmLib.xdmOmVfsDeleteStdobj(xDMWorkspace.om.vfs);
            XDMOmVfs.xdmOmVfsDeleteOmFile();
            return -1;
        } catch (Exception e2) {
            Log.E(e2.toString());
            return -1;
        }
    }

    private int xdmAgentMakeNode() throws XDMOmTreeException {
        XDMOmTree xDMOmTree = g_DmWs.om;
        if (XDMOmLib.xdmOmInit(xDMOmTree) != 0) {
            return -1;
        }
        XDMOmLib.xdmOmSetServerId(xDMOmTree, "*");
        xdmAgentMakeSyncMLNode();
        xdmAgentMakeDevInfoNode();
        xdmAgentMakeDevDetailNode();
        xdmAgentMakeFwUpdateNode();
        return 0;
    }

    public int xdmAgentCreatePackage() {
        int i;
        int i2;
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMEncoder xDMEncoder = xDMWorkspace.e;
        if (xDMWorkspace.dmState == XDMInterface.XDMSyncMLState.XDM_STATE_INIT) {
            i = xdmAgentLoadWorkSpace();
            if (XDBFumoAdp.xdbGetFUMOInitiatedType() != 0) {
                xDMWorkspace.dmState = XDMInterface.XDMSyncMLState.XDM_STATE_GENERIC_ALERT;
            } else {
                xDMWorkspace.dmState = XDMInterface.XDMSyncMLState.XDM_STATE_CLIENT_INIT_MGMT;
            }
        } else {
            i = (xDMWorkspace.dmState == XDMInterface.XDMSyncMLState.XDM_STATE_GENERIC_ALERT_REPORT || xDMWorkspace.dmState == XDMInterface.XDMSyncMLState.XDM_STATE_ABORT_ALERT) ? xdmAgentLoadWorkSpace() : 0;
        }
        if (i != 0) {
            Log.E("xdmAgentCreatePackage failed");
            return -1;
        }
        xDMWorkspace.buf.reset();
        xDMEncoder.xdmEncInit(xDMWorkspace.buf);
        xDMEncoder.xdmEncStartSyncml(0, 106, XDMInterface.WBXML_STRING_TABLE_1_2, 29);
        XDMWorkspace xdmAgentBuildCmdSyncHeader = XDMBuildCmd.xdmAgentBuildCmdSyncHeader(xDMWorkspace);
        xDMEncoder.xdmEncAddSyncHeader(xdmAgentBuildCmdSyncHeader.syncHeader);
        xDMEncoder.xdmEncStartSyncbody();
        int i3 = AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMInterface$XDMSyncMLState[xdmAgentBuildCmdSyncHeader.dmState.ordinal()];
        if (i3 == 1) {
            Log.I("XDM_STATE_CLIENT_INIT_MGMT");
            xdmAgentClientInitPackage(xDMEncoder);
        } else if (i3 == 2) {
            Log.I("XDM_STATE_PROCESSING");
            xdmAgentMgmtPackage(xDMEncoder);
        } else if (i3 == 3) {
            Log.I("XDM_STATE_GENERIC_ALERT");
            int xdmAgentClientInitPackage = xdmAgentClientInitPackage(xDMEncoder);
            if (xdmAgentClientInitPackage != 0) {
                if (xdmAgentClientInitPackage == -3) {
                    xdmAgentBuildCmdSyncHeader.endOfMsg = false;
                } else {
                    Log.E("failed(%d)" + xdmAgentClientInitPackage);
                }
                return -1;
            }
            i = xdmAgentCreatePackageGenericAlert(xDMEncoder, XDMInterface.ALERT_GENERIC);
            if (i != 0) {
                if (i == -3) {
                    xdmAgentBuildCmdSyncHeader.endOfMsg = false;
                } else {
                    Log.E("failed(%d)" + i);
                }
                return -1;
            }
            xdmAgentBuildCmdSyncHeader.endOfMsg = true;
        } else if (i3 == 4) {
            Log.I("XDM_STATE_GENERIC_ALERT_REPORT");
            int xdmAgentClientInitPackage2 = xdmAgentClientInitPackage(xDMEncoder);
            if (xdmAgentClientInitPackage2 != 0) {
                if (xdmAgentClientInitPackage2 == -3) {
                    xdmAgentBuildCmdSyncHeader.endOfMsg = false;
                } else {
                    Log.E("failed(%d)" + xdmAgentClientInitPackage2);
                }
                return -1;
            }
            i = xdmAgentCreatePackageReportGenericAlert(xDMEncoder, XDMInterface.ALERT_GENERIC);
            if (i != 0) {
                if (i == -3) {
                    xdmAgentBuildCmdSyncHeader.endOfMsg = false;
                } else {
                    Log.E("failed(%d)" + i);
                }
                return -1;
            }
            xdmAgentBuildCmdSyncHeader.endOfMsg = true;
        } else if (i3 == 5) {
            Log.I("XDM_STATE_ABORT_ALERT");
            if (XDBProfileListAdp.xdbGetNotiEvent() > 0) {
                i2 = xdmAgentCreatePackageAlert(xDMEncoder, XDMInterface.ALERT_SERVER_INITIATED_MGMT);
            } else {
                i2 = xdmAgentCreatePackageAlert(xDMEncoder, XDMInterface.ALERT_CLIENT_INITIATED_MGMT);
            }
            if (i2 != 0) {
                if (i2 == -3) {
                    xdmAgentBuildCmdSyncHeader.endOfMsg = false;
                } else {
                    Log.E("failed(%d)" + i2);
                }
                return -1;
            }
            i = xdmAgentCreatePackageAlert(xDMEncoder, XDMInterface.ALERT_SESSION_ABORT);
            if (i != 0) {
                if (i == -3) {
                    xdmAgentBuildCmdSyncHeader.endOfMsg = false;
                } else {
                    Log.E("failed(%d)" + i);
                }
                return -1;
            }
            xdmAgentBuildCmdSyncHeader.endOfMsg = true;
        }
        if (xdmAgentBuildCmdSyncHeader.dataBuffered || xdmAgentBuildCmdSyncHeader.sendRemain) {
            xdmAgentBuildCmdSyncHeader.endOfMsg = false;
        }
        xDMEncoder.xdmEncEndSyncbody(xdmAgentBuildCmdSyncHeader.endOfMsg);
        xDMEncoder.xdmEncEndSyncml();
        return i;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.accessorydm.agent.XDMAgent$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$interfaces$XDMInterface$XDMSyncMLState = new int[XDMInterface.XDMSyncMLState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.accessorydm.interfaces.XDMInterface$XDMSyncMLState[] r0 = com.accessorydm.interfaces.XDMInterface.XDMSyncMLState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.agent.XDMAgent.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMInterface$XDMSyncMLState = r0
                int[] r0 = com.accessorydm.agent.XDMAgent.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMInterface$XDMSyncMLState     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.interfaces.XDMInterface$XDMSyncMLState r1 = com.accessorydm.interfaces.XDMInterface.XDMSyncMLState.XDM_STATE_CLIENT_INIT_MGMT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.agent.XDMAgent.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMInterface$XDMSyncMLState     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.interfaces.XDMInterface$XDMSyncMLState r1 = com.accessorydm.interfaces.XDMInterface.XDMSyncMLState.XDM_STATE_PROCESSING     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = com.accessorydm.agent.XDMAgent.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMInterface$XDMSyncMLState     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.interfaces.XDMInterface$XDMSyncMLState r1 = com.accessorydm.interfaces.XDMInterface.XDMSyncMLState.XDM_STATE_GENERIC_ALERT     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = com.accessorydm.agent.XDMAgent.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMInterface$XDMSyncMLState     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.accessorydm.interfaces.XDMInterface$XDMSyncMLState r1 = com.accessorydm.interfaces.XDMInterface.XDMSyncMLState.XDM_STATE_GENERIC_ALERT_REPORT     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = com.accessorydm.agent.XDMAgent.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMInterface$XDMSyncMLState     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.accessorydm.interfaces.XDMInterface$XDMSyncMLState r1 = com.accessorydm.interfaces.XDMInterface.XDMSyncMLState.XDM_STATE_ABORT_ALERT     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMAgent.AnonymousClass1.<clinit>():void");
        }
    }

    private int xdmAgentLoadWorkSpace() {
        XDMOmTree xDMOmTree;
        String concat;
        XDMVnode xdmOmGetNodeProp;
        char[] cArr;
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (xDMWorkspace == null || (xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp((xDMOmTree = xDMWorkspace.om), (concat = m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHNAME_PATH)))) == null) {
            return -1;
        }
        char[] cArr2 = new char[xdmOmGetNodeProp.size];
        XDMOmLib.xdmOmRead(xDMOmTree, concat, 0, cArr2, xdmOmGetNodeProp.size);
        xDMWorkspace.m_szUserName = String.valueOf(cArr2);
        String concat2 = m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHSECRET_PATH);
        XDMVnode xdmOmGetNodeProp2 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat2);
        if (xdmOmGetNodeProp2 == null) {
            return -1;
        }
        char[] cArr3 = new char[xdmOmGetNodeProp2.size];
        XDMOmLib.xdmOmRead(xDMOmTree, concat2, 0, cArr3, xdmOmGetNodeProp2.size);
        xDMWorkspace.m_szClientPW = String.valueOf(cArr3);
        String concat3 = m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHTYPE_PATH);
        XDMVnode xdmOmGetNodeProp3 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat3);
        if (xdmOmGetNodeProp3 == null) {
            return -1;
        }
        char[] cArr4 = new char[xdmOmGetNodeProp3.size];
        XDMOmLib.xdmOmRead(xDMOmTree, concat3, 0, cArr4, xdmOmGetNodeProp3.size);
        int xdbGetNotiReSyncMode = XDBProfileListAdp.xdbGetNotiReSyncMode();
        if (xdbGetNotiReSyncMode == 1) {
            xDMWorkspace.credType = XDMAuth.xdmAuthAAuthtring2Type(XDMInterface.AUTH_TYPE_DIGEST);
        } else {
            xDMWorkspace.credType = XDMAuth.xdmAuthAAuthtring2Type(String.valueOf(cArr4));
        }
        String concat4 = m_DmAccXNodeInfo.m_szServerAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHTYPE_PATH);
        XDMVnode xdmOmGetNodeProp4 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat4);
        if (xdmOmGetNodeProp4 == null) {
            return -1;
        }
        char[] cArr5 = new char[xdmOmGetNodeProp4.size];
        XDMOmLib.xdmOmRead(xDMOmTree, concat4, 0, cArr5, xdmOmGetNodeProp4.size);
        if (xdbGetNotiReSyncMode == 1) {
            xDMWorkspace.serverCredType = XDMAuth.xdmAuthAAuthtring2Type(XDMInterface.AUTH_TYPE_DIGEST);
        } else {
            xDMWorkspace.serverCredType = XDMAuth.xdmAuthAAuthtring2Type(String.valueOf(cArr5));
            if (xDMWorkspace.serverCredType == -1) {
                xDMWorkspace.serverCredType = xDMWorkspace.credType;
            }
        }
        String concat5 = m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_SERVERID_PATH);
        XDMVnode xdmOmGetNodeProp5 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat5);
        if (xdmOmGetNodeProp5 == null) {
            return -1;
        }
        char[] cArr6 = new char[xdmOmGetNodeProp5.size];
        XDMOmLib.xdmOmRead(xDMOmTree, concat5, 0, cArr6, xdmOmGetNodeProp5.size);
        xDMWorkspace.m_szServerID = String.valueOf(cArr6);
        String concat6 = m_DmAccXNodeInfo.m_szServerAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHSECRET_PATH);
        XDMVnode xdmOmGetNodeProp6 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat6);
        if (xdmOmGetNodeProp6 == null) {
            return -1;
        }
        char[] cArr7 = new char[xdmOmGetNodeProp6.size];
        XDMOmLib.xdmOmRead(xDMOmTree, concat6, 0, cArr7, xdmOmGetNodeProp6.size);
        xDMWorkspace.m_szServerPW = String.valueOf(cArr7);
        char[] cArr8 = null;
        if (xdbGetNotiReSyncMode == 1) {
            xDMWorkspace.nextNonce[0] = 0;
            xDMWorkspace.nextNonce[1] = 0;
            xDMWorkspace.nextNonce[2] = 0;
            xDMWorkspace.nextNonce[3] = 0;
        } else {
            String concat7 = m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH);
            XDMVnode xdmOmGetNodeProp7 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat7);
            if (xdmOmGetNodeProp7 != null) {
                if (xdmOmGetNodeProp7.size > 0) {
                    cArr = new char[xdmOmGetNodeProp7.size];
                    XDMOmLib.xdmOmRead(xDMOmTree, concat7, 0, cArr, xdmOmGetNodeProp7.size);
                } else {
                    cArr = null;
                }
                if (cArr != null) {
                    if (xdmOmGetNodeProp7.format != 1) {
                        for (int i = 0; i < xdmOmGetNodeProp7.size; i++) {
                            xDMWorkspace.nextNonce[i] = (byte) cArr[i];
                        }
                        Log.I("node->size = " + xdmOmGetNodeProp7.size);
                    } else {
                        byte[] xdmBase64Decode = XDMBase64.xdmBase64Decode(new String(cArr));
                        xDMWorkspace.nextNonce = new byte[xdmBase64Decode.length];
                        System.arraycopy(xdmBase64Decode, 0, xDMWorkspace.nextNonce, 0, xdmBase64Decode.length);
                    }
                }
            }
            return -1;
        }
        String concat8 = m_DmAccXNodeInfo.m_szServerAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH);
        Log.H("Server szAccBuf) :" + concat8);
        XDMVnode xdmOmGetNodeProp8 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat8);
        if (xdmOmGetNodeProp8 != null) {
            if (xdmOmGetNodeProp8.size > 0) {
                cArr8 = new char[xdmOmGetNodeProp8.size];
                XDMOmLib.xdmOmRead(xDMOmTree, concat8, 0, cArr8, xdmOmGetNodeProp8.size);
            }
            if (cArr8 != null) {
                if (xdmOmGetNodeProp8.format != 1) {
                    for (int i2 = 0; i2 < xdmOmGetNodeProp8.size; i2++) {
                        xDMWorkspace.serverNextNonce[i2] = (byte) cArr8[i2];
                    }
                } else {
                    Log.H("Server Next Noncenew String(buf) :" + new String(cArr8));
                    byte[] xdmBase64Decode2 = XDMBase64.xdmBase64Decode(new String(cArr8));
                    xDMWorkspace.serverNextNonce = new byte[xdmBase64Decode2.length];
                    System.arraycopy(xdmBase64Decode2, 0, xDMWorkspace.serverNextNonce, 0, xdmBase64Decode2.length);
                }
            }
            String concat9 = m_DmAccXNodeInfo.m_szAppAddr.concat("/Addr");
            XDMVnode xdmOmGetNodeProp9 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat9);
            if (xdmOmGetNodeProp9 == null) {
                return -1;
            }
            char[] cArr9 = new char[xdmOmGetNodeProp9.size];
            XDMOmLib.xdmOmRead(xDMOmTree, concat9, 0, cArr9, xdmOmGetNodeProp9.size);
            xDMWorkspace.m_szHostname = String.valueOf(cArr9);
            String concat10 = m_DmAccXNodeInfo.m_szAppAddrPort.concat("/PortNbr");
            XDMVnode xdmOmGetNodeProp10 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat10);
            if (xdmOmGetNodeProp10 == null) {
                return -1;
            }
            char[] cArr10 = new char[xdmOmGetNodeProp10.size];
            XDMOmLib.xdmOmRead(xDMOmTree, concat10, 0, cArr10, xdmOmGetNodeProp10.size);
            try {
                xDMWorkspace.port = Integer.valueOf(String.valueOf(cArr10)).intValue();
            } catch (NumberFormatException e) {
                Log.E(e.toString());
            }
            XDMVnode xdmOmGetNodeProp11 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_DEVID_PATH);
            if (xdmOmGetNodeProp11 == null) {
                return -1;
            }
            char[] cArr11 = new char[xdmOmGetNodeProp11.size];
            XDMOmLib.xdmOmRead(xDMOmTree, XDMInterface.XDM_DEVINFO_DEVID_PATH, 0, cArr11, xdmOmGetNodeProp11.size);
            xDMWorkspace.m_szSourceURI = String.valueOf(cArr11);
            return 0;
        }
        return -1;
    }

    private void xdmAgentMgmtPackage(XDMEncoder xDMEncoder) {
        int xdmAgentCreatePackageAlert;
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (!xDMWorkspace.dataBuffered || (xdmAgentCreatePackageAlert = xdmAgentCreatePackageAlert(xDMEncoder, XDMInterface.ALERT_NEXT_MESSAGE)) == 0) {
            int xdmAgentCreatePackageStatus = xdmAgentCreatePackageStatus(xDMEncoder);
            if (xdmAgentCreatePackageStatus == 0) {
                int xdmAgentCreatePackageResults = xdmAgentCreatePackageResults(xDMEncoder);
                if (xdmAgentCreatePackageResults == 0) {
                    xDMWorkspace.endOfMsg = true;
                } else if (xdmAgentCreatePackageResults == -3) {
                    xDMWorkspace.endOfMsg = false;
                } else {
                    Log.E("failed = " + xdmAgentCreatePackageResults);
                }
            } else if (xdmAgentCreatePackageStatus == -3) {
                xDMWorkspace.endOfMsg = false;
            } else {
                Log.E("failed = " + xdmAgentCreatePackageStatus);
            }
        } else if (xdmAgentCreatePackageAlert == -3) {
            xDMWorkspace.endOfMsg = false;
        } else {
            Log.E("failed = " + xdmAgentCreatePackageAlert);
        }
    }

    private void xdmAgentMakeSyncMLNode() throws XDMOmTreeException {
        XDMOmTree xDMOmTree = g_DmWs.om;
        try {
            XDBProfileInfo xdbGetProfileInfo = XDBProfileAdp.xdbGetProfileInfo();
            xdmAgentMakeDefaultAcl(xDMOmTree, XDMInterface.XDM_BASE_PATH, 9, 1);
            xdm_SET_OM_PATH(xDMOmTree, XDMInterface.XDM_SYNCML_PATH, 27, 1);
            xdm_SET_OM_PATH(xDMOmTree, XDMInterface.XDM_BASE_PATH, 27, 1);
            xdm_SET_OM_PATH(xDMOmTree, XDMInterface.XDM_ACCOUNT_PATH, 27, 1);
            xdm_SET_OM_PATH(xDMOmTree, XDMInterface.XDM_SYNCML_CON_PATH, 27, 1);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szAccount, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPID_PATH), "w7", 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_SERVERID_PATH), xdbGetProfileInfo.ServerID, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_NAME_PATH), xdbGetProfileInfo.ProfileName, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_PREFCONREF_PATH), xdbGetProfileInfo.PrefConRef, 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_TOCONREF_PATH), 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szToConRef, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szToConRef.concat("/ConRef"), XDMInterface.XDM_DEFAULT_CONREF, 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPADDR_PATH), 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szAppAddr, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAppAddr.concat("/Addr"), xdbGetProfileInfo.ServerUrl, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAppAddr.concat("/AddrType"), "URI", 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szAppAddr.concat(XDMInterface.XDM_APPADDR_PORT_PATH), 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szAppAddrPort, 27, 2);
            String valueOf = String.valueOf(xdbGetProfileInfo.ServerPort);
            Log.H("ServerPort = " + valueOf);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAppAddrPort.concat("/PortNbr"), valueOf, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_AAUTHPREF_PATH), XDMAuth.xdmAuthCredType2String(xdbGetProfileInfo.AuthType), 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPAUTH_PATH), 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szClientAppAuth, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHLEVEL_PATH), xdbGetProfileInfo.AuthLevel, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHTYPE_PATH), XDMAuth.xdmAuthAAuthType2String(xdbGetProfileInfo.AuthType), 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHNAME_PATH), xdbGetProfileInfo.UserName, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHSECRET_PATH), xdbGetProfileInfo.Password, 27, 2);
            xdmAgentSetOMAccB64(xDMOmTree, m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH), xdmAgentCheckNonce(xdbGetProfileInfo.ClientNonce), 27, 2);
            xdm_SET_OM_PATH(xDMOmTree, m_DmAccXNodeInfo.m_szServerAppAuth, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szServerAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHLEVEL_PATH), xdbGetProfileInfo.ServerAuthLevel, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szServerAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHTYPE_PATH), XDMAuth.xdmAuthAAuthType2String(xdbGetProfileInfo.nServerAuthType), 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szServerAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHNAME_PATH), xdbGetProfileInfo.ServerID, 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szServerAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHSECRET_PATH), xdbGetProfileInfo.ServerPwd, 27, 2);
            xdmAgentSetOMAccB64(xDMOmTree, m_DmAccXNodeInfo.m_szServerAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH), xdmAgentCheckNonce(xdbGetProfileInfo.ServerNonce), 27, 2);
            xdmAgentSetOMAccStr(xDMOmTree, m_DmAccXNodeInfo.m_szAccount.concat("/Ext"), " ", 27, 2);
            String xdmDDFGetMOPath = XDMDDFXmlHandler.xdmDDFGetMOPath(10);
            if (!TextUtils.isEmpty(xdmDDFGetMOPath)) {
                xdm_SET_OM_PATH(xDMOmTree, xdmDDFGetMOPath, 27, 1);
            }
        } catch (Exception e) {
            Log.E("XDMOmTreeException : " + e.toString());
            throw new XDMOmTreeException();
        }
    }

    private String xdmAgentCheckNonce(String str) {
        return TextUtils.isEmpty(str) ? XDMBase64.xdmBase64Encode(DEFAULT_NONCE) : str;
    }

    private static void xdmAgentReMakeFwUpdateNode(XDMOmTree xDMOmTree, String str) throws XDMOmTreeException {
        char[] cArr = new char[str.length()];
        String str2 = str;
        while (true) {
            XDMOmLib.xdmOmMakeParentPath(str2, cArr);
            str2 = XDMMem.xdmLibCharToString(cArr);
            if (TextUtils.isEmpty(str2)) {
                Log.I("szTmpbuf null!");
                break;
            }
            Log.I(str2);
            if (!str2.contains("/Update") && !str2.contains("/DownloadAndUpdate") && !str2.contains("/Download") && !str2.contains("/Ext")) {
                if (XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str2) == null) {
                    XDMOmLib.xdmOmProcessCmdImplicitAdd(xDMOmTree, str2, 24, 1);
                }
            }
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        xdmAgentSetOMAccStr(xDMOmTree, str2 + XFOTAInterface.XFUMO_PKGNAME_PATH, " ", 25, 2);
        xdmAgentSetOMAccStr(xDMOmTree, str2 + XFOTAInterface.XFUMO_PKGVERSION_PATH, " ", 25, 2);
        String str3 = str2 + "/Download";
        xdm_SET_OM_PATH(xDMOmTree, str3, 13, 2);
        xdmAgentSetOMAccStr(xDMOmTree, str3.concat(XFOTAInterface.XFUMO_PKGURL_PATH), " ", 25, 2);
        String str4 = str2 + "/Update";
        xdm_SET_OM_PATH(xDMOmTree, str4, 29, 2);
        xdmAgentSetOMAccBin(xDMOmTree, str4.concat(XFOTAInterface.XFUMO_PKGDATA_PATH), "", 0, 16, 2);
        String str5 = str2 + "/DownloadAndUpdate";
        xdm_SET_OM_PATH(xDMOmTree, str5, 29, 2);
        xdmAgentSetOMAccStr(xDMOmTree, str5.concat(XFOTAInterface.XFUMO_PKGURL_PATH), " ", 25, 2);
        xdmAgentSetOMAccStr(xDMOmTree, str2 + XFOTAInterface.XFUMO_STATE_PATH, String.valueOf(XDBFumoAdp.xdbGetFUMOStatus()), 8, 2);
        String str6 = str2 + "/Ext";
        xdm_SET_OM_PATH(xDMOmTree, str6, 25, 2);
        xdmAgentSetOMAccStr(xDMOmTree, str6.concat(XFOTAInterface.XFUMO_SVCSTATE), " ", 25, 2);
        xdmAgentSetOMAccStr(xDMOmTree, str6.concat(XFOTAInterface.XFUMO_DOWNLOADCONNTYPE_PATH), " ", 25, 2);
        xdmAgentSetOMAccStr(xDMOmTree, str6.concat(XFOTAInterface.XFUMO_ROOTINGCHECK_PATH), " ", 25, 2);
        String concat = str6.concat(XFOTAInterface.XFUMO_POSTPONE_PATH);
        xdmAgentSetOMAccStr(xDMOmTree, concat, " ", 25, 2);
        String concat2 = concat.concat(XFOTAInterface.XFUMO_FORCE_PATH);
        xdmAgentSetOMAccStr(xDMOmTree, concat2, " ", 25, 2);
        Log.I("pFUMONode:" + concat2);
    }

    private void xdmAgentMakeDevInfoNode() throws XDMOmTreeException {
        XDMOmTree xDMOmTree = g_DmWs.om;
        xdm_SET_OM_PATH(xDMOmTree, XDMInterface.XDM_DEVINFO_PATH, 11, 1);
        xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_DEVID_PATH, XDMDevinfAdapter.xdmDevAdpGetFullDeviceID(), 8, 1);
        xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_MAN_PATH, XDMDevinfAdapter.xdmDevAdpGetManufacturer(), 11, 1);
        xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_MOD_PATH, XDMDevinfAdapter.xdmDevAdpGetModel(), 11, 1);
        xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_DMV_PATH, "1.2", 11, 1);
        xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_LANG_PATH, XDMDevinfAdapter.xdmDevAdpGetLanguage(), 11, 1);
        xdm_SET_OM_PATH(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_PATH, 10, 1);
        String xdmDevAdpGetTelephonyMcc = XDMDevinfAdapter.xdmDevAdpGetTelephonyMcc();
        if (!TextUtils.isEmpty(xdmDevAdpGetTelephonyMcc)) {
            xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_TELEPHONYMCC_PATH, xdmDevAdpGetTelephonyMcc, 11, 1);
        }
        String xdmDevAdpGetTelephonyMnc = XDMDevinfAdapter.xdmDevAdpGetTelephonyMnc();
        if (!TextUtils.isEmpty(xdmDevAdpGetTelephonyMnc)) {
            xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_TELEPHONYMNC_PATH, xdmDevAdpGetTelephonyMnc, 11, 1);
        }
        String xdmDevAdpGetAppVersion = XDMDevinfAdapter.xdmDevAdpGetAppVersion();
        if (!TextUtils.isEmpty(xdmDevAdpGetAppVersion)) {
            xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_FOTACLIENTVER_PATH, xdmDevAdpGetAppVersion, 11, 1);
            xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_DMCLIENTVER_PATH, xdmDevAdpGetAppVersion, 11, 1);
        }
        xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_NETWORKCONNTYPE_PATH, xdmAgentGetDevNetworkConnType(), 11, 1);
    }

    private void xdmAgentMakeDevDetailNode() throws XDMOmTreeException {
        XDMOmTree xDMOmTree = g_DmWs.om;
        xdm_SET_OM_PATH(xDMOmTree, XDMInterface.XDM_DEVDETAIL_PATH, 8, 1);
        xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVDETAIL_FWV_PATH, XDMDevinfAdapter.xdmDevAdpGetFirmwareVersion(), 8, 1);
        xdmAgentSetOMAccStr(xDMOmTree, XDMInterface.XDM_DEVDETAIL_LRGOBJ_PATH, XDMInterface.XDM_DEVDETAIL_DEFAULT_LRGOBJ_SUPPORT, 8, 1);
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVDETAIL_LRGOBJ_PATH);
        if (xdmOmGetNodeProp != null) {
            xdmOmGetNodeProp.format = 3;
            xdmOmGetNodeProp.type = null;
        }
        xdm_SET_OM_PATH(xDMOmTree, XDMInterface.XDM_DEVDETAIL_EXT_PATH, 10, 1);
    }

    private void xdmAgentMakeFwUpdateNode() throws XDMOmTreeException {
        XDMOmTree xDMOmTree = g_DmWs.om;
        Log.I("xdmAgentMakeFwUpdateNode Initialize");
        xdm_SET_OM_PATH(xDMOmTree, XFOTAInterface.XFUMO_PATH, 25, 1);
        for (int i = 0; i < 1; i++) {
            String concat = XFOTAInterface.XFUMO_PATH.concat(XDMInterface.FUMO_X_NODE_COMMON);
            Log.I("pFUMOPackageNode :".concat(concat));
            xdm_SET_OM_PATH(xDMOmTree, concat, 25, 1);
            xdmAgentSetOMAccStr(xDMOmTree, concat.concat(XFOTAInterface.XFUMO_PKGNAME_PATH), XFOTAInterface.XDL_DEFAULT_PKGNAME, 25, 2);
            xdmAgentSetOMAccStr(xDMOmTree, concat.concat(XFOTAInterface.XFUMO_PKGVERSION_PATH), "1.0", 25, 2);
            String concat2 = concat.concat("/Download");
            xdm_SET_OM_PATH(xDMOmTree, concat2, 29, 2);
            xdmAgentSetOMAccStr(xDMOmTree, concat2.concat(XFOTAInterface.XFUMO_PKGURL_PATH), " ", 25, 2);
            String concat3 = concat.concat("/Update");
            xdm_SET_OM_PATH(xDMOmTree, concat3, 29, 2);
            xdmAgentSetOMAccBin(xDMOmTree, concat3.concat(XFOTAInterface.XFUMO_PKGDATA_PATH), "", 0, 16, 2);
            String concat4 = concat.concat("/DownloadAndUpdate");
            xdm_SET_OM_PATH(xDMOmTree, concat4, 29, 2);
            xdmAgentSetOMAccStr(xDMOmTree, concat4.concat(XFOTAInterface.XFUMO_PKGURL_PATH), " ", 25, 2);
            String concat5 = concat.concat(XFOTAInterface.XFUMO_STATE_PATH);
            String valueOf = String.valueOf(XDBFumoAdp.xdbGetFUMOStatus());
            Log.I(valueOf);
            xdmAgentSetOMAccStr(xDMOmTree, concat5, valueOf, 8, 2);
            String concat6 = concat.concat("/Ext");
            xdm_SET_OM_PATH(xDMOmTree, concat6, 25, 2);
            xdmAgentSetOMAccStr(xDMOmTree, concat6.concat(XFOTAInterface.XFUMO_SVCSTATE), " ", 25, 2);
            xdmAgentSetOMAccStr(xDMOmTree, concat6.concat(XFOTAInterface.XFUMO_DOWNLOADCONNTYPE_PATH), " ", 25, 2);
            xdmAgentSetOMAccStr(xDMOmTree, concat6.concat(XFOTAInterface.XFUMO_ROOTINGCHECK_PATH), " ", 25, 2);
            String concat7 = concat6.concat(XFOTAInterface.XFUMO_POSTPONE_PATH);
            xdmAgentSetOMAccStr(xDMOmTree, concat7, " ", 25, 2);
            xdmAgentSetOMAccStr(xDMOmTree, concat7.concat(XFOTAInterface.XFUMO_FORCE_PATH), " ", 25, 2);
        }
    }

    private static void xdm_SET_OM_PATH(XDMOmTree xDMOmTree, String str, int i, int i2) throws XDMOmTreeException {
        try {
            if (XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str) == null) {
                XDMOmLib.xdmOmWrite(xDMOmTree, str, 0, 0, "", 0);
                xdmAgentMakeDefaultAcl(xDMOmTree, str, i, i2);
            }
        } catch (Exception e) {
            Log.E("XDMOmTreeException : " + e.toString());
            throw new XDMOmTreeException();
        }
    }

    public static void xdmAgentMakeDefaultAcl(XDMOmTree xDMOmTree, String str, int i, int i2) {
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str);
        if (xdmOmGetNodeProp != null) {
            if (i != 0) {
                ((XDMOmAcl) xdmOmGetNodeProp.acl.data).ac = i;
            } else {
                Log.I("ACL is XDM_OMACL_NONE");
            }
            xdmOmGetNodeProp.scope = i2;
            return;
        }
        Log.I("Not Exist");
    }

    private static void xdmAgentSetOMAccStr(Object obj, String str, String str2, int i, int i2) throws XDMOmTreeException {
        XDMOmTree xDMOmTree = (XDMOmTree) obj;
        try {
            XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str);
            if (TextUtils.isEmpty(str2)) {
                str2 = "";
            }
            if (xdmOmGetNodeProp == null) {
                xdmAgentSetOM(str, str2);
                xdmAgentMakeDefaultAcl(xDMOmTree, str, i, i2);
                return;
            }
            char[] cArr = new char[xdmOmGetNodeProp.size];
            XDMOmLib.xdmOmRead(xDMOmTree, str, 0, cArr, xdmOmGetNodeProp.size);
            String valueOf = String.valueOf(cArr);
            if (xdmOmGetNodeProp.size != str2.length()) {
                xdmAgentSetOM(str, str2);
            } else if (valueOf.compareTo(str2) != 0) {
                xdmAgentSetOM(str, str2);
            }
        } catch (Exception e) {
            Log.E("XDMOmTreeException : " + e.toString());
            throw new XDMOmTreeException();
        }
    }

    private void xdmAgentSetOMAccB64(Object obj, String str, String str2, int i, int i2) {
        XDMOmTree xDMOmTree = (XDMOmTree) obj;
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str);
        if (xdmOmGetNodeProp == null) {
            xdmAgentSetOMB64(str, str2);
            xdmAgentMakeDefaultAcl(xDMOmTree, str, i, i2);
            return;
        }
        char[] cArr = new char[xdmOmGetNodeProp.size];
        XDMOmLib.xdmOmRead(xDMOmTree, str, 0, cArr, xdmOmGetNodeProp.size);
        String valueOf = String.valueOf(cArr);
        if (str2.length() != xdmOmGetNodeProp.size) {
            xdmAgentSetOMB64(str, str2);
        } else if (valueOf.compareTo(str2) != 0) {
            xdmAgentSetOMB64(str, str2);
        }
    }

    private static void xdmAgentSetOMAccBin(Object obj, String str, String str2, int i, int i2, int i3) {
        XDMOmTree xDMOmTree = (XDMOmTree) obj;
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str);
        if (xdmOmGetNodeProp == null) {
            xdmAgentSetOMBin(str, str2, i);
            xdmAgentMakeDefaultAcl(xDMOmTree, str, i2, i3);
            return;
        }
        char[] cArr = new char[xdmOmGetNodeProp.size];
        XDMOmLib.xdmOmRead(xDMOmTree, str, 0, cArr, xdmOmGetNodeProp.size);
        String valueOf = String.valueOf(cArr);
        if (i != xdmOmGetNodeProp.size) {
            xdmAgentSetOMBin(str, str2, i);
        } else if (valueOf.compareTo(str2) != 0) {
            xdmAgentSetOMBin(str, str2, i);
        }
    }

    private static void xdmAgentSetOMBin(String str, String str2, int i) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMOmLib.xdmOmWrite(xDMWorkspace.om, str, i, 0, str2, i);
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMWorkspace.om, str);
        if (xdmOmGetNodeProp != null) {
            if (xdmOmGetNodeProp.type != null) {
                XDMOmLib.xdmOmVfsDeleteMimeList(xdmOmGetNodeProp.type);
            }
            xdmOmGetNodeProp.type = null;
            xdmOmGetNodeProp.format = 2;
        }
    }

    private void xdmAgentSetOMB64(String str, Object obj) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (obj == null) {
            Log.E("data is NULL");
            return;
        }
        int length = String.valueOf(obj).getBytes(Charset.defaultCharset()).length;
        if (length <= 0) {
            XDMOmLib.xdmOmDeleteImplicit(xDMWorkspace.om, str, true);
            Log.I("The [" + str + "] node is 0 length");
        }
        XDMOmLib.xdmOmWrite(xDMWorkspace.om, str, length, 0, obj, length);
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMWorkspace.om, str);
        if (xdmOmGetNodeProp != null) {
            if (xdmOmGetNodeProp.type != null) {
                XDMOmLib.xdmOmVfsDeleteMimeList(xdmOmGetNodeProp.type);
            }
            XDMOmList xDMOmList = new XDMOmList();
            xDMOmList.data = XDMInterface.MIMETYPE_TEXT_PLAIN;
            xDMOmList.next = null;
            xdmOmGetNodeProp.type = xDMOmList;
            xdmOmGetNodeProp.format = 1;
        }
    }

    private static void xdmAgentSetOM(String str, Object obj) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (obj != null) {
            int length = String.valueOf(obj).getBytes(Charset.defaultCharset()).length;
            if (length <= 0) {
                XDMOmLib.xdmOmDeleteImplicit(xDMWorkspace.om, str, true);
            }
            XDMOmLib.xdmOmWrite(xDMWorkspace.om, str, length, 0, obj, length);
            XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMWorkspace.om, str);
            if (xdmOmGetNodeProp != null) {
                if (xdmOmGetNodeProp.type != null) {
                    XDMOmLib.xdmOmVfsDeleteMimeList(xdmOmGetNodeProp.type);
                }
                XDMOmList xDMOmList = new XDMOmList();
                xDMOmList.data = XDMInterface.MIMETYPE_TEXT_PLAIN;
                xDMOmList.next = null;
                xdmOmGetNodeProp.type = xDMOmList;
                xdmOmGetNodeProp.format = 4;
            }
        }
    }

    private int xdmAgentClientInitPackage(XDMEncoder xDMEncoder) {
        int i;
        XDMWorkspace xDMWorkspace = g_DmWs;
        Log.I("");
        int xdmAgentCreatePackageStatus = xdmAgentCreatePackageStatus(xDMEncoder);
        if (xdmAgentCreatePackageStatus != 0) {
            if (xdmAgentCreatePackageStatus == -3) {
                xDMWorkspace.endOfMsg = false;
            } else {
                Log.E("failed(" + xdmAgentCreatePackageStatus + ")");
            }
            return -1;
        }
        int xdmAgentCreatePackageResults = xdmAgentCreatePackageResults(xDMEncoder);
        if (xdmAgentCreatePackageResults != 0) {
            if (xdmAgentCreatePackageResults == -3) {
                xDMWorkspace.endOfMsg = false;
            } else {
                Log.E("failed(" + xdmAgentCreatePackageResults + ")");
            }
            return -1;
        }
        if (XDBProfileListAdp.xdbGetNotiEvent() > 0) {
            i = xdmAgentCreatePackageAlert(xDMEncoder, XDMInterface.ALERT_SERVER_INITIATED_MGMT);
        } else {
            i = xdmAgentCreatePackageAlert(xDMEncoder, XDMInterface.ALERT_CLIENT_INITIATED_MGMT);
        }
        if (i != 0) {
            if (i == -3) {
                xDMWorkspace.endOfMsg = false;
            } else {
                Log.E("failed(" + i + ")");
            }
            return -1;
        }
        int xdmAgentCreatePackageDevInfo = xdmAgentCreatePackageDevInfo(xDMEncoder);
        if (xdmAgentCreatePackageDevInfo != 0) {
            if (xdmAgentCreatePackageDevInfo == -3) {
                xDMWorkspace.endOfMsg = false;
            } else {
                Log.E("failed(" + xdmAgentCreatePackageDevInfo + ")");
            }
            return -1;
        }
        xDMWorkspace.endOfMsg = true;
        return 0;
    }

    private int xdmAgentCreatePackageStatus(XDMEncoder xDMEncoder) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMLinkedList.xdmListSetCurrentObj(xDMWorkspace.statusList, 0);
        for (XDMParserStatus xDMParserStatus = (XDMParserStatus) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.statusList); xDMParserStatus != null; xDMParserStatus = (XDMParserStatus) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.statusList)) {
            xDMEncoder.xdmEncAddStatus(xDMParserStatus);
            XDMLinkedList.xdmListRemoveObjAtFirst(xDMWorkspace.statusList);
            XDMHandleCmd.xdmAgentDataStDeleteStatus(xDMParserStatus);
        }
        XDMLinkedList.xdmListClearLinkedList(xDMWorkspace.statusList);
        return 0;
    }

    private int xdmAgentCreatePackageResults(XDMEncoder xDMEncoder) {
        XDMParserResults xDMParserResults;
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMOmTree xDMOmTree = xDMWorkspace.om;
        XDMLinkedList.xdmListSetCurrentObj(xDMWorkspace.resultsList, 0);
        XDMParserResults xDMParserResults2 = (XDMParserResults) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.resultsList);
        if (xDMParserResults2 != null || xDMWorkspace.dmState == XDMInterface.XDMSyncMLState.XDM_STATE_CLIENT_INIT_MGMT || !xDMWorkspace.nextMsg) {
            while (xDMParserResults2 != null) {
                XDMParserItem xDMParserItem = (XDMParserItem) xDMParserResults2.itemlist.item;
                int intValue = (xDMParserItem.meta == null || xDMParserItem.meta.size <= 0) ? 0 : Integer.valueOf(xDMParserItem.meta.size).intValue();
                XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, xDMParserItem.m_szSource);
                int i = intValue + 128;
                int xdmEncGetBufferSize = xDMWorkspace.maxMsgSize - XDMEncoder.xdmEncGetBufferSize(xDMEncoder);
                if (xdmEncGetBufferSize < 128) {
                    return -3;
                }
                if (intValue <= 0 || xdmEncGetBufferSize >= i) {
                    intValue = 0;
                }
                if (intValue > 0) {
                    char[] cArr = new char[intValue];
                    if (xDMParserItem.meta == null || TextUtils.isEmpty(xDMParserItem.meta.m_szType) || XDMInterface.SYNCML_MIME_TYPE_TNDS_XML.compareTo(xDMParserItem.meta.m_szType) != 0) {
                        XDMOmLib.xdmOmRead(xDMOmTree, xDMParserItem.m_szSource, 0, cArr, intValue);
                        if (xdmOmGetNodeProp == null || xdmOmGetNodeProp.format != 2) {
                            xDMParserItem.data = XDMHandleCmd.xdmAgentDataStString2Pcdata(cArr);
                        } else {
                            xDMParserItem.data = new XDMParserPcdata();
                            xDMParserItem.data.type = 1;
                            xDMParserItem.data.data = new char[intValue];
                            System.arraycopy(cArr, 0, xDMParserItem.data.data, 0, intValue);
                            xDMParserItem.data.size = intValue;
                        }
                    } else {
                        byte[] bArr = new byte[intValue];
                        XDB.xdbReadFile(XDB.xdbGetFileIdTNDS(), 0, intValue, bArr);
                        xDMParserItem.data = XDMHandleCmd.xdmAgentDataStString2Pcdata(new String(bArr, Charset.defaultCharset()).toCharArray());
                        xDMWorkspace.sendPos = 0;
                        xDMEncoder.xdmEncAddResults(xDMParserResults2);
                        xDMParserResults = (XDMParserResults) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.resultsList);
                        XDMLinkedList.xdmListRemoveObjAtFirst(xDMWorkspace.resultsList);
                        XDMHandleCmd.xdmAgentDataStDeleteResults(xDMParserResults2);
                        xDMParserResults2 = xDMParserResults;
                    }
                }
                xDMWorkspace.sendPos = 0;
                xDMEncoder.xdmEncAddResults(xDMParserResults2);
                xDMParserResults = (XDMParserResults) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.resultsList);
                XDMLinkedList.xdmListRemoveObjAtFirst(xDMWorkspace.resultsList);
                XDMHandleCmd.xdmAgentDataStDeleteResults(xDMParserResults2);
                xDMParserResults2 = xDMParserResults;
            }
            XDMLinkedList.xdmListClearLinkedList(xDMWorkspace.resultsList);
            return 0;
        }
        XDMLinkedList.xdmListClearLinkedList(xDMWorkspace.resultsList);
        return 0;
    }

    private int xdmAgentCreatePackageAlert(XDMEncoder xDMEncoder, String str) {
        XDMParserAlert xdmAgentBuildCmdAlert = XDMBuildCmd.xdmAgentBuildCmdAlert(g_DmWs, str);
        xDMEncoder.xdmEncAddAlert(xdmAgentBuildCmdAlert);
        XDMHandleCmd.xdmAgentDataStDeleteAlert(xdmAgentBuildCmdAlert);
        return 0;
    }

    private int xdmAgentCreatePackageDevInfo(XDMEncoder xDMEncoder) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMOmTree xDMOmTree = xDMWorkspace.om;
        XDMLinkedList xdmListCreateLinkedList = XDMLinkedList.xdmListCreateLinkedList();
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_LANG_PATH);
        if (xdmOmGetNodeProp != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_LANG_PATH, xdmOmGetNodeProp.size);
        }
        XDMVnode xdmOmGetNodeProp2 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_DMV_PATH);
        if (xdmOmGetNodeProp2 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_DMV_PATH, xdmOmGetNodeProp2.size);
        }
        XDMVnode xdmOmGetNodeProp3 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_MOD_PATH);
        if (xdmOmGetNodeProp3 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_MOD_PATH, xdmOmGetNodeProp3.size);
        }
        XDMVnode xdmOmGetNodeProp4 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_MAN_PATH);
        if (xdmOmGetNodeProp4 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_MAN_PATH, xdmOmGetNodeProp4.size);
        }
        XDMVnode xdmOmGetNodeProp5 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_DEVID_PATH);
        if (xdmOmGetNodeProp5 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_DEVID_PATH, xdmOmGetNodeProp5.size);
        }
        XDMVnode xdmOmGetNodeProp6 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_TELEPHONYMCC_PATH);
        if (xdmOmGetNodeProp6 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_EXT_TELEPHONYMCC_PATH, xdmOmGetNodeProp6.size);
        }
        XDMVnode xdmOmGetNodeProp7 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_TELEPHONYMNC_PATH);
        if (xdmOmGetNodeProp7 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_EXT_TELEPHONYMNC_PATH, xdmOmGetNodeProp7.size);
        }
        XDMVnode xdmOmGetNodeProp8 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_FOTACLIENTVER_PATH);
        if (xdmOmGetNodeProp8 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_EXT_FOTACLIENTVER_PATH, xdmOmGetNodeProp8.size);
        }
        XDMVnode xdmOmGetNodeProp9 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_DMCLIENTVER_PATH);
        if (xdmOmGetNodeProp9 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_EXT_DMCLIENTVER_PATH, xdmOmGetNodeProp9.size);
        }
        XDMVnode xdmOmGetNodeProp10 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMInterface.XDM_DEVINFO_EXT_NETWORKCONNTYPE_PATH);
        if (xdmOmGetNodeProp10 != null) {
            xdmAgent_MAKE_REP_ITEM(xDMOmTree, xdmListCreateLinkedList, XDMInterface.XDM_DEVINFO_EXT_NETWORKCONNTYPE_PATH, xdmOmGetNodeProp10.size);
        }
        XDMParserReplace xdmAgentBuildCmdReplace = XDMBuildCmd.xdmAgentBuildCmdReplace(xDMWorkspace, xdmListCreateLinkedList);
        XDMLinkedList.xdmListFreeLinkedList(xdmListCreateLinkedList);
        xDMEncoder.xdmEncAddReplace(xdmAgentBuildCmdReplace);
        XDMHandleCmd.xdmAgentDataStDeleteReplace(xdmAgentBuildCmdReplace);
        return 0;
    }

    private void xdmAgent_MAKE_REP_ITEM(XDMOmTree xDMOmTree, XDMLinkedList xDMLinkedList, String str, int i) {
        char[] cArr = new char[i];
        if (XDMOmLib.xdmOmRead(xDMOmTree, str, 0, cArr, i) < 0) {
            Log.I("xdmOmRead failed");
        }
        XDMParserItem xDMParserItem = new XDMParserItem();
        xDMParserItem.m_szSource = str;
        xDMParserItem.data = XDMHandleCmd.xdmAgentDataStString2Pcdata(cArr);
        XDMLinkedList.xdmListAddObjAtLast(xDMLinkedList, xDMParserItem);
    }

    public static void xdmAgentSaveBootstrapDateToFFS(XDBProfileInfo xDBProfileInfo) {
        Log.H("ServerID[" + xDBProfileInfo.ServerID + "]");
        int xdbSetActiveProfileIndexByServerID = XDB.xdbSetActiveProfileIndexByServerID(xDBProfileInfo.ServerID);
        XDBProfileAdp.xdbSetProfileInfo(xDBProfileInfo);
        XDBProfileListAdp.xdbSetProfileName(xdbSetActiveProfileIndexByServerID, xDBProfileInfo.ProfileName);
    }

    private int xdmAgentCreatePackageGenericAlert(XDMEncoder xDMEncoder, String str) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        Log.I("");
        XDMParserAlert xdmAgentBuildCmdGenericAlert = XDMBuildCmd.xdmAgentBuildCmdGenericAlert(xDMWorkspace, str);
        xDMEncoder.xdmEncAddAlert(xdmAgentBuildCmdGenericAlert);
        XDMHandleCmd.xdmAgentDataStDeleteAlert(xdmAgentBuildCmdGenericAlert);
        return 0;
    }

    private int xdmAgentCreatePackageReportGenericAlert(XDMEncoder xDMEncoder, String str) {
        XDMParserAlert xdmAgentBuildCmdGenericAlertReport = XDMBuildCmd.xdmAgentBuildCmdGenericAlertReport(g_DmWs, str);
        xDMEncoder.xdmEncAddAlert(xdmAgentBuildCmdGenericAlertReport);
        XDMHandleCmd.xdmAgentDataStDeleteAlert(xdmAgentBuildCmdGenericAlertReport);
        return 0;
    }

    private boolean xdmAgentVefifyAtomicCmd(XDMAgent xDMAgent) {
        if ("Atomic_Start".compareTo(xDMAgent.m_szCmd) == 0 || HttpNetworkInterface.XTP_HTTP_METHOD_GET.compareTo(xDMAgent.m_szCmd) == 0) {
            return false;
        }
        Log.I("");
        return true;
    }

    private int xdmAgentCmdAtomicBlock(XDMParserAtomic xDMParserAtomic, XDMLinkedList xDMLinkedList) throws XDMOmTreeException {
        XDMParserStatus xDMParserStatus;
        XDMWorkspace xDMWorkspace = g_DmWs;
        xDMWorkspace.tmpItem = null;
        XDMLinkedList.xdmListSetCurrentObj(xDMLinkedList, 0);
        XDMAgent xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
        boolean z = true;
        while (xDMAgent != null) {
            if (!xdmAgentVefifyAtomicCmd(xDMAgent)) {
                z = false;
            }
            xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
        }
        if (z) {
            xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserAtomic.cmdid, XDMInterface.CMD_ATOMIC, null, null, "200");
            xDMWorkspace.atomicStep = XDMInterface.XDMAtomicStep.XDM_ATOMIC_NONE;
        } else {
            xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserAtomic.cmdid, XDMInterface.CMD_ATOMIC, null, null, XDMInterface.STATUS_ATOMIC_FAILED);
            xDMWorkspace.atomicStep = XDMInterface.XDMAtomicStep.XDM_ATOMIC_STEP_ROLLBACK;
        }
        XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
        XDMLinkedList.xdmListSetCurrentObj(xDMLinkedList, 0);
        XDMAgent xDMAgent2 = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
        int i = 1;
        while (xDMAgent2 != null) {
            if (!xDMWorkspace.atomicFlag) {
                if (XDMInterface.CMD_GET.compareTo(xDMAgent2.m_szCmd) == 0) {
                    if (xdmAgentCmdGet(xDMAgent2.m_Get, true) != 0) {
                        Log.E("get failed");
                        return -1;
                    }
                } else if (XDMInterface.CMD_EXEC.compareTo(xDMAgent2.m_szCmd) == 0) {
                    int xdmAgentCmdExec = xdmAgentCmdExec(xDMAgent2.m_Exec);
                    if (XDMInterface.STATUS_ATOMIC_FAILED.compareTo(xDMParserStatus.m_szData) == 0) {
                        xDMWorkspace.atomicFlag = true;
                    }
                    if (xdmAgentCmdExec != 0) {
                        Log.E("exec failed");
                        return -1;
                    }
                } else if (XDMInterface.CMD_ADD.compareTo(xDMAgent2.m_szCmd) == 0) {
                    int xdmAgentCmdAdd = xdmAgentCmdAdd(xDMAgent2.m_AddCmd, true, xDMParserStatus);
                    if (XDMInterface.STATUS_ATOMIC_FAILED.compareTo(xDMParserStatus.m_szData) == 0) {
                        xDMWorkspace.atomicFlag = true;
                    }
                    if (xdmAgentCmdAdd != 0) {
                        Log.E("Add failed");
                        return -1;
                    }
                } else if (XDMInterface.CMD_DELETE.compareTo(xDMAgent2.m_szCmd) == 0) {
                    int xdmAgentCmdDelete = xdmAgentCmdDelete(xDMAgent2.m_DeleteCmd, true, xDMParserStatus);
                    if (XDMInterface.STATUS_ATOMIC_FAILED.compareTo(xDMParserStatus.m_szData) == 0) {
                        xDMWorkspace.atomicFlag = true;
                    }
                    if (xdmAgentCmdDelete != 0) {
                        Log.E("Delete failed");
                        return -1;
                    }
                } else if (XDMInterface.CMD_REPLACE.compareTo(xDMAgent2.m_szCmd) == 0) {
                    int xdmAgentCmdReplace = xdmAgentCmdReplace(xDMAgent2.m_ReplaceCmd, true, xDMParserStatus);
                    if (XDMInterface.STATUS_ATOMIC_FAILED.compareTo(xDMParserStatus.m_szData) == 0) {
                        xDMWorkspace.atomicFlag = true;
                    }
                    if (xdmAgentCmdReplace != 0) {
                        Log.E("Replace failed");
                        return -1;
                    }
                } else if (XDMInterface.CMD_COPY.compareTo(xDMAgent2.m_szCmd) == 0) {
                    int xdmAgentCmdCopy = xdmAgentCmdCopy(xDMAgent2.m_CopyCmd, true, xDMParserStatus);
                    if (XDMInterface.STATUS_ATOMIC_FAILED.compareTo(xDMParserStatus.m_szData) == 0) {
                        xDMWorkspace.atomicFlag = true;
                    }
                    if (xdmAgentCmdCopy != 0) {
                        Log.E("Copy failed");
                        return -1;
                    }
                } else if ("Atomic_Start".compareTo(xDMAgent2.m_szCmd) == 0) {
                    xDMWorkspace.atomicFlag = true;
                    XDMParserAtomic xDMParserAtomic2 = xDMAgent2.m_Atomic;
                    xdmAgentCmdAtomicBlock(xDMParserAtomic2, xDMParserAtomic2.itemlist);
                } else {
                    Log.E("unknown command");
                }
                i++;
            } else if (XDMInterface.CMD_GET.compareTo(xDMAgent2.m_szCmd) == 0) {
                XDMParserItem xDMParserItem = (XDMParserItem) xDMAgent2.m_Get.itemlist.item;
                if (!TextUtils.isEmpty(xDMParserItem.m_szTarget)) {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_Get.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_Get.cmdid, XDMInterface.CMD_GET, null, null, "404");
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
            } else if (XDMInterface.CMD_EXEC.compareTo(xDMAgent2.m_szCmd) == 0) {
                XDMParserItem xDMParserItem2 = (XDMParserItem) xDMAgent2.m_Exec.itemlist.item;
                if (!TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_Exec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem2.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_Exec.cmdid, XDMInterface.CMD_EXEC, null, null, "404");
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
            } else if (XDMInterface.CMD_ADD.compareTo(xDMAgent2.m_szCmd) == 0) {
                XDMParserItem xDMParserItem3 = (XDMParserItem) xDMAgent2.m_AddCmd.itemlist.item;
                if (!TextUtils.isEmpty(xDMParserItem3.m_szTarget)) {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_AddCmd.cmdid, XDMInterface.CMD_ADD, null, xDMParserItem3.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_AddCmd.cmdid, XDMInterface.CMD_ADD, null, null, "404");
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
            } else if (XDMInterface.CMD_DELETE.compareTo(xDMAgent2.m_szCmd) == 0) {
                XDMParserItem xDMParserItem4 = (XDMParserItem) xDMAgent2.m_DeleteCmd.itemlist.item;
                if (!TextUtils.isEmpty(xDMParserItem4.m_szTarget)) {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_DeleteCmd.cmdid, XDMInterface.CMD_DELETE, null, xDMParserItem4.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_DeleteCmd.cmdid, XDMInterface.CMD_DELETE, null, null, "404");
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
            } else if (XDMInterface.CMD_REPLACE.compareTo(xDMAgent2.m_szCmd) == 0) {
                XDMParserItem xDMParserItem5 = (XDMParserItem) xDMAgent2.m_ReplaceCmd.itemlist.item;
                if (!TextUtils.isEmpty(xDMParserItem5.m_szTarget)) {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_ReplaceCmd.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem5.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_ReplaceCmd.cmdid, XDMInterface.CMD_REPLACE, null, null, "404");
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
            } else if (XDMInterface.CMD_COPY.compareTo(xDMAgent2.m_szCmd) == 0) {
                XDMParserItem xDMParserItem6 = (XDMParserItem) xDMAgent2.m_CopyCmd.itemlist.item;
                if (!TextUtils.isEmpty(xDMParserItem6.m_szTarget)) {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_CopyCmd.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem6.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMAgent2.m_CopyCmd.cmdid, XDMInterface.CMD_COPY, null, null, "404");
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
            } else {
                Log.I("unknown command");
            }
            xDMAgent2 = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
        }
        XDMLinkedList.xdmListClearLinkedList(xDMParserAtomic.itemlist);
        return i;
    }

    private int xdmAgentCmdGet(XDMParserGet xDMParserGet, boolean z) {
        String str;
        XDMParserStatus xDMParserStatus;
        XDMParserStatus xDMParserStatus2;
        XDMParserStatus xDMParserStatus3;
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMOmTree xDMOmTree = xDMWorkspace.om;
        int i = 100;
        String[] strArr = new String[100];
        boolean xdmAgentCmdUicAlert = xdmAgentCmdUicAlert();
        XDMList xDMList = xDMParserGet.itemlist;
        while (xDMList != null) {
            XDMParserItem xDMParserItem = (XDMParserItem) xDMList.item;
            if (xDMWorkspace.serverAuthState != 1) {
                if (!TextUtils.isEmpty(xDMParserItem.m_szTarget)) {
                    xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, xDMWorkspace.m_szStatusReturnCode);
                } else {
                    xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, null, xDMWorkspace.m_szStatusReturnCode);
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus3);
                xDMList = xDMList.next;
            } else if (TextUtils.isEmpty(xDMParserItem.m_szTarget)) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, null, "404"));
                xDMList = xDMList.next;
            } else if (!xdmAgentCmdUicAlert) {
                if (!TextUtils.isEmpty(xDMParserItem.m_szTarget)) {
                    xDMParserStatus2 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus2 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, null, XDMInterface.STATUS_NOT_EXECUTED);
                }
                if (xDMParserStatus2 != null) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus2);
                }
                xDMList = xDMList.next;
            } else if (z) {
                if (!TextUtils.isEmpty(xDMParserItem.m_szTarget)) {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, xDMParserItem.m_szTarget, null, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, null, XDMInterface.STATUS_NOT_EXECUTED);
                }
                if (xDMParserStatus != null) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
                }
                xDMList = xDMList.next;
            } else if (!TextUtils.isEmpty(XDMMem.xdmLibStrstr(xDMParserItem.m_szTarget, "?"))) {
                xdmAgentCmdProp(XDMInterface.CMD_GET, xDMParserItem, xDMParserGet);
                xDMList = xDMList.next;
            } else {
                XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, xDMParserItem.m_szTarget);
                if (xdmOmGetNodeProp == null) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "404"));
                    xDMList = xDMList.next;
                } else if (!xdmAgentIsAccessibleNode(xDMParserItem.m_szTarget)) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "405"));
                    xDMList = xDMList.next;
                } else if (!XDMOmLib.xdmOmCheckAcl(xDMOmTree, xdmOmGetNodeProp, 8)) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_PERMISSION_DENIED));
                    xDMList = xDMList.next;
                } else {
                    if (xdmOmGetNodeProp.vaddr < 0 && xdmOmGetNodeProp.size <= 0) {
                        XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
                        int xdmOmGetChild = XDMOmLib.xdmOmGetChild(xDMOmTree, xDMParserItem.m_szTarget, strArr, i);
                        String xdmOmGetFormatString = XDMOmList.xdmOmGetFormatString(xdmOmGetNodeProp.format);
                        String valueOf = (xdmOmGetNodeProp.type == null || xdmOmGetNodeProp.type.data == null) ? null : String.valueOf(xdmOmGetNodeProp.type.data);
                        if (xdmOmGetChild > 0) {
                            str = strArr[0];
                            for (int i2 = 1; i2 < xdmOmGetChild; i2++) {
                                str = str.concat("/").concat(strArr[i2]);
                            }
                        } else {
                            str = "";
                        }
                        XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, xdmOmGetFormatString, valueOf, 0, str.toCharArray()));
                    } else if (xdmOmGetNodeProp.size > xDMWorkspace.serverMaxObjSize) {
                        XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "413"));
                        xDMList = xDMList.next;
                    } else {
                        int i3 = xdmOmGetNodeProp.size;
                        String xdmOmGetFormatString2 = XDMOmList.xdmOmGetFormatString(xdmOmGetNodeProp.format);
                        char[] cArr = new char[xdmOmGetNodeProp.size];
                        if (cArr.length == 0) {
                            XDMParserStatus xdmAgentBuildCmdStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                            if (xdmAgentBuildCmdStatus != null) {
                                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xdmAgentBuildCmdStatus);
                            }
                            xDMList = xDMList.next;
                        } else {
                            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
                            String valueOf2 = (xdmOmGetNodeProp.type == null || xdmOmGetNodeProp.type.data == null) ? null : String.valueOf(xdmOmGetNodeProp.type.data);
                            XDMOmLib.xdmOmRead(xDMOmTree, xDMParserItem.m_szTarget, 0, cArr, xdmOmGetNodeProp.size);
                            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, xdmOmGetFormatString2, valueOf2, i3, cArr));
                            Log.H("item.target = " + xDMParserItem.m_szTarget);
                            Log.H("item.data = " + new String(cArr));
                        }
                    }
                    xDMList = xDMList.next;
                    i = 100;
                }
            }
        }
        return 0;
    }

    private boolean xdmAgentCmdUicAlert() {
        XDMParserStatus xDMParserStatus;
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (xDMWorkspace.uicAlert != null) {
            if (xDMWorkspace.uicFlag == XUICInterface.XUICFlag.UIC_TRUE || xDMWorkspace.uicFlag == XUICInterface.XUICFlag.UIC_NONE) {
                xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMWorkspace.uicAlert.cmdid, XDMInterface.CMD_ALERT, null, null, "200");
                if (xDMWorkspace.uicData != null) {
                    xDMParserStatus.itemlist = xDMWorkspace.uicData;
                }
            } else if (xDMWorkspace.uicFlag == XUICInterface.XUICFlag.UIC_FALSE) {
                xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMWorkspace.uicAlert.cmdid, XDMInterface.CMD_ALERT, null, null, XDMInterface.STATUS_NOT_MODIFIED);
                if (xDMWorkspace.uicData != null) {
                    xDMParserStatus.itemlist = xDMWorkspace.uicData;
                }
            } else if (xDMWorkspace.uicFlag == XUICInterface.XUICFlag.UIC_CANCELED) {
                xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMWorkspace.uicAlert.cmdid, XDMInterface.CMD_ALERT, null, null, XDMInterface.STATUS_OPERATION_CANCELLED);
                if (xDMWorkspace.uicData != null) {
                    xDMParserStatus.itemlist = xDMWorkspace.uicData;
                }
            } else {
                xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMWorkspace.uicAlert.cmdid, XDMInterface.CMD_ALERT, null, null, XDMInterface.STATUS_NOT_EXECUTED);
                if (xDMWorkspace.uicData != null) {
                    xDMParserStatus.itemlist = xDMWorkspace.uicData;
                }
            }
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
            XDMHandleCmd.xdmAgentDataStDeleteAlert(xDMWorkspace.uicAlert);
            xDMWorkspace.uicData = null;
            xDMWorkspace.uicAlert = null;
        }
        return xDMWorkspace.uicFlag == XUICInterface.XUICFlag.UIC_TRUE || xDMWorkspace.uicFlag == XUICInterface.XUICFlag.UIC_NONE;
    }

    private int xdmAgentCmdProp(String str, XDMParserItem xDMParserItem, Object obj) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        String str2 = xDMParserItem.m_szTarget;
        if (XDMInterface.CMD_GET.compareTo(str) == 0) {
            XDMParserGet xDMParserGet = (XDMParserGet) obj;
            if (TextUtils.isEmpty(str2)) {
                Log.I("ptr is null");
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, str, null, xDMParserItem.m_szTarget, "405"));
                return 0;
            }
            Log.I("ptr = " + str2);
            char[] cArr = new char[str2.length()];
            String xdmLibStrsplit = XDMMem.xdmLibStrsplit(str2.toCharArray(), '?', cArr);
            if (TextUtils.isEmpty(xdmLibStrsplit)) {
                Log.I("ptr is null");
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, str, null, xDMParserItem.m_szTarget, "405"));
                return 0;
            }
            Log.I("ptr = " + xdmLibStrsplit);
            char[] cArr2 = new char[xdmLibStrsplit.length()];
            String xdmLibStrsplit2 = XDMMem.xdmLibStrsplit(xdmLibStrsplit.toCharArray(), '=', cArr2);
            if (TextUtils.isEmpty(xdmLibStrsplit2)) {
                Log.I("ptr is null");
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, str, null, xDMParserItem.m_szTarget, "405"));
                return 0;
            }
            Log.I("ptr = " + xdmLibStrsplit2);
            return xdmAgentCmdPropGet(xDMWorkspace, xDMParserItem, xdmLibStrsplit2, cArr2, cArr, obj);
        } else if (XDMInterface.CMD_REPLACE.compareTo(str) == 0) {
            XDMParserReplace xDMParserReplace = (XDMParserReplace) obj;
            if (TextUtils.isEmpty(str2)) {
                Log.I("ptr is null");
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, str, null, xDMParserItem.m_szTarget, "405"));
                return 0;
            }
            Log.I("ptr = " + str2);
            char[] cArr3 = new char[str2.length()];
            String xdmLibStrsplit3 = XDMMem.xdmLibStrsplit(str2.toCharArray(), '?', cArr3);
            if (TextUtils.isEmpty(xdmLibStrsplit3)) {
                Log.I("ptr is null");
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, str, null, xDMParserItem.m_szTarget, "405"));
                return 0;
            }
            Log.I("ptr = " + xdmLibStrsplit3);
            char[] cArr4 = new char[xdmLibStrsplit3.length()];
            String xdmLibStrsplit4 = XDMMem.xdmLibStrsplit(xdmLibStrsplit3.toCharArray(), '=', cArr4);
            Log.I(String.valueOf(cArr4) + ":" + String.valueOf(xdmLibStrsplit4));
            if (TextUtils.isEmpty(xdmLibStrsplit4)) {
                Log.I("ptr is null");
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, str, null, xDMParserItem.m_szTarget, "405"));
                return 0;
            }
            Log.I("ptr = " + xdmLibStrsplit4);
            return xdmAgentCmdPropReplace(xDMWorkspace, xDMParserItem, xdmLibStrsplit4, cArr3, obj);
        } else {
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, ((XDMParserGet) obj).cmdid, str, null, xDMParserItem.m_szTarget, "405"));
            return 0;
        }
    }

    private int xdmAgentCmdPropGet(XDMWorkspace xDMWorkspace, XDMParserItem xDMParserItem, String str, char[] cArr, char[] cArr2, Object obj) {
        XDMOmTree xDMOmTree = xDMWorkspace.om;
        XDMParserGet xDMParserGet = (XDMParserGet) obj;
        int xdbGetFileIdTNDS = XDB.xdbGetFileIdTNDS();
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMMem.xdmLibCharToString(cArr2));
        if (xdmOmGetNodeProp == null) {
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "404"));
            return 0;
        }
        String xdmLibCharToString = XDMMem.xdmLibCharToString(cArr);
        if (xdmLibCharToString == null || "list".compareTo(xdmLibCharToString) != 0) {
            if (!XDMOmLib.xdmOmCheckAcl(xDMOmTree, xdmOmGetNodeProp, 8)) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_PERMISSION_DENIED));
                return 0;
            }
            char[] cArr3 = null;
            if ("ACL".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
                String xdmAgentGetAclStr = xdmAgentGetAclStr(xdmOmGetNodeProp.acl, xDMParserItem);
                if (!TextUtils.isEmpty(xdmAgentGetAclStr)) {
                    cArr3 = xdmAgentGetAclStr.toCharArray();
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, "chr", XDMInterface.MIMETYPE_TEXT_PLAIN, 0, cArr3));
                return 0;
            }
            if ("Format".compareTo(str) == 0) {
                String xdmOmGetFormatString = XDMOmList.xdmOmGetFormatString(xdmOmGetNodeProp.format);
                if (!TextUtils.isEmpty(xdmOmGetFormatString)) {
                    cArr3 = xdmOmGetFormatString.toCharArray();
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, "chr", XDMInterface.MIMETYPE_TEXT_PLAIN, 0, cArr3));
            } else if ("Type".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
                if (xdmOmGetNodeProp.type == null || xdmOmGetNodeProp.type.data == null) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, "", null, 0, null));
                } else {
                    String valueOf = String.valueOf(xdmOmGetNodeProp.type.data);
                    if (!TextUtils.isEmpty(valueOf)) {
                        cArr3 = valueOf.toCharArray();
                    }
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, "chr", valueOf, 0, cArr3));
                }
            } else if ("Size".compareTo(str) == 0) {
                if (xdmOmGetNodeProp.vaddr < 0 || xdmOmGetNodeProp.size <= 0) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "406"));
                } else {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
                    String valueOf2 = String.valueOf(xdmOmGetNodeProp.size - 1);
                    if (!TextUtils.isEmpty(valueOf2)) {
                        cArr3 = valueOf2.toCharArray();
                    }
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, "chr", XDMInterface.MIMETYPE_TEXT_PLAIN, 0, cArr3));
                }
            } else if ("Name".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
                String str2 = xdmOmGetNodeProp.m_szName;
                if (!TextUtils.isEmpty(str2)) {
                    cArr3 = str2.toCharArray();
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, "chr", XDMInterface.MIMETYPE_TEXT_PLAIN, 0, cArr3));
            } else if ("Title".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
                String str3 = xdmOmGetNodeProp.title;
                if (!TextUtils.isEmpty(str3)) {
                    cArr3 = str3.toCharArray();
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xDMParserItem.m_szTarget, "chr", XDMInterface.MIMETYPE_TEXT_PLAIN, 0, cArr3));
            } else {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "405"));
                return 0;
            }
            return 0;
        } else if (!xdmAgentIsAccessibleNode(xDMParserItem.m_szTarget)) {
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "405"));
            return 0;
        } else if (!XDMOmLib.xdmOmCheckAcl(xDMOmTree, xdmOmGetNodeProp, 8)) {
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_PERMISSION_DENIED));
            return 0;
        } else {
            if ("Struct".compareTo(str) == 0) {
                xdmAgentCmdPropGetStruct(xDMParserGet, xdmOmGetNodeProp, false);
            } else if ("StructData".compareTo(str) == 0) {
                xdmAgentCmdPropGetStruct(xDMParserGet, xdmOmGetNodeProp, true);
            } else if (str.contains("TNDS") && !xdmAgentCmdPropGetTnds(xDMParserGet, xDMOmTree, xdmOmGetNodeProp, str)) {
                XDB.xdbDeleteFile(xdbGetFileIdTNDS);
                XDMLinkedList.xdmListAddObjAtLast(g_DmWs.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(g_DmWs, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "404"));
                return 0;
            }
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserGet.cmdid, XDMInterface.CMD_GET, null, xDMParserItem.m_szTarget, "200"));
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005c A[LOOP:0: B:20:0x005a->B:21:0x005c, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void xdmAgentCmdPropGetStruct(com.accessorydm.eng.parser.XDMParserGet r10, com.accessorydm.eng.core.XDMVnode r11, boolean r12) {
        /*
            r9 = this;
            com.accessorydm.eng.core.XDMWorkspace r7 = com.accessorydm.agent.XDMAgent.g_DmWs
            com.accessorydm.eng.core.XDMOmTree r0 = r7.om
            if (r11 != 0) goto L_0x0007
            return
        L_0x0007:
            com.accessorydm.eng.core.XDMVnode r8 = r11.childlist
            com.accessorydm.eng.core.XDMOmTree r1 = r7.om
            java.lang.String r2 = r9.xdmAgentGetPathFromNode(r1, r11)
            r1 = 0
            r3 = 0
            if (r12 == 0) goto L_0x003b
            int r4 = r11.vaddr
            if (r4 < 0) goto L_0x0034
            int r4 = r11.size
            if (r4 <= 0) goto L_0x0034
            int r3 = r11.format
            if (r3 <= 0) goto L_0x0026
            int r3 = r11.format
            java.lang.String r3 = com.accessorydm.eng.core.XDMOmList.xdmOmGetFormatString(r3)
            goto L_0x0028
        L_0x0026:
            java.lang.String r3 = ""
        L_0x0028:
            int r4 = r11.size
            char[] r5 = new char[r4]
            com.accessorydm.eng.core.XDMOmLib.xdmOmRead(r0, r2, r1, r5, r4)
            int r0 = r11.size
            r6 = r5
            r5 = r0
            goto L_0x0044
        L_0x0034:
            int r0 = r11.format
            java.lang.String r0 = com.accessorydm.eng.core.XDMOmList.xdmOmGetFormatString(r0)
            goto L_0x0041
        L_0x003b:
            int r0 = r11.format
            java.lang.String r0 = com.accessorydm.eng.core.XDMOmList.xdmOmGetFormatString(r0)
        L_0x0041:
            r5 = r1
            r6 = r3
            r3 = r0
        L_0x0044:
            java.lang.String r11 = r11.m_szName
            boolean r11 = r9.xdmAgentIsAccessibleNode(r11)
            if (r11 == 0) goto L_0x005a
            int r1 = r10.cmdid
            java.lang.String r4 = ""
            r0 = r7
            com.accessorydm.eng.parser.XDMParserResults r11 = com.accessorydm.agent.XDMBuildCmd.xdmAgentBuildCmdDetailResults(r0, r1, r2, r3, r4, r5, r6)
            com.accessorydm.eng.core.XDMLinkedList r0 = r7.resultsList
            com.accessorydm.eng.core.XDMLinkedList.xdmListAddObjAtLast(r0, r11)
        L_0x005a:
            if (r8 == 0) goto L_0x0062
            r9.xdmAgentCmdPropGetStruct(r10, r8, r12)
            com.accessorydm.eng.core.XDMVnode r8 = r8.next
            goto L_0x005a
        L_0x0062:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMAgent.xdmAgentCmdPropGetStruct(com.accessorydm.eng.parser.XDMParserGet, com.accessorydm.eng.core.XDMVnode, boolean):void");
    }

    private String xdmAgentGetPathFromNode(XDMOmTree xDMOmTree, XDMVnode xDMVnode) {
        String[] strArr = new String[10];
        XDMVnode xdmOmVfsGetParent = XDMOmLib.xdmOmVfsGetParent(xDMOmTree.vfs, xDMOmTree.vfs.root, xDMVnode);
        int i = 0;
        while (xdmOmVfsGetParent != null && xdmOmVfsGetParent != xDMOmTree.vfs.root) {
            strArr[i] = xdmOmVfsGetParent.m_szName;
            i++;
            xdmOmVfsGetParent = XDMOmLib.xdmOmVfsGetParent(xDMOmTree.vfs, xDMOmTree.vfs.root, xdmOmVfsGetParent);
        }
        if (xdmOmVfsGetParent == null) {
            return XDMInterface.XDM_BASE_PATH;
        }
        String str = "./";
        for (int i2 = i - 1; i2 >= 0; i2--) {
            str = str.concat(strArr[i2]).concat("/");
        }
        return (str.compareTo("/") == 0 && str.compareTo("./") == 0) ? str : str.concat(xDMVnode.m_szName);
    }

    private int xdmAgentCmdPropReplace(XDMWorkspace xDMWorkspace, XDMParserItem xDMParserItem, String str, char[] cArr, Object obj) {
        XDMOmTree xDMOmTree = xDMWorkspace.om;
        XDMParserReplace xDMParserReplace = (XDMParserReplace) obj;
        String xdmLibCharToString = XDMMem.xdmLibCharToString(cArr);
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, xdmLibCharToString);
        if (xdmOmGetNodeProp == null) {
            Log.I("!node");
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "404"));
            return 0;
        } else if (xdmAgentIsPermanentNode(xDMOmTree, xdmLibCharToString)) {
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "405"));
            return 0;
        } else if (!XDMOmLib.xdmOmCheckAcl(xDMOmTree, xdmOmGetNodeProp, 16)) {
            Log.I("!XDM_OMACL_REPLACE");
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_PERMISSION_DENIED));
            return 0;
        } else {
            if ("ACL".compareTo(str) == 0) {
                Log.I("ACL");
                if (xdmOmGetNodeProp.format == 6 || XDMOmLib.xdmOmCheckAcl(xDMOmTree, xdmOmGetNodeProp.ptParentNode, 16)) {
                    String xdmAgentDataStGetString = XDMHandleCmd.xdmAgentDataStGetString(xDMParserItem.data);
                    if (TextUtils.isEmpty(xdmAgentDataStGetString)) {
                        xdmAgentDataStGetString = String.valueOf(xDMParserItem.data.data);
                    }
                    XDMOmList xdmAgentMakeAcl = xdmAgentMakeAcl(null, xdmAgentDataStGetString);
                    XDMOmList.xdmOmDeleteAclList(xdmOmGetNodeProp.acl);
                    xdmOmGetNodeProp.acl = xdmAgentMakeAcl;
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "200"));
                } else {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_PERMISSION_DENIED));
                    Log.I("STATUS_COMMAND_NOT_ALLOWED=" + String.valueOf(cArr));
                    return 0;
                }
            } else if ("Format".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "405"));
            } else if ("Type".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "405"));
            } else if ("Size".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "405"));
            } else if ("Name".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "405"));
            } else if ("Title".compareTo(str) == 0) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "200"));
                xdmOmGetNodeProp.title = null;
                xdmOmGetNodeProp.title = XDMHandleCmd.xdmAgentDataStGetString(xDMParserItem.data);
                if (!(!TextUtils.isEmpty(xdmOmGetNodeProp.title) || xDMParserItem.data == null || xDMParserItem.data.data == null)) {
                    xdmOmGetNodeProp.title = String.valueOf(xDMParserItem.data.data);
                }
            } else {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserReplace.cmdid, XDMInterface.CMD_REPLACE, null, xDMParserItem.m_szTarget, "405"));
            }
            return 0;
        }
    }

    private void xdmAgentMakeTndsSubTree(XDMOmTree xDMOmTree, XDMVnode xDMVnode, int i, String str) {
        XDMOmAcl xDMOmAcl;
        int i2;
        String str2;
        boolean z;
        new XDMOmList();
        if (xDMVnode != null) {
            int xdbGetFileIdTNDS = XDB.xdbGetFileIdTNDS();
            String str3 = XDMDDFXmlHandler.g_szDmXmlTagString[4];
            if (!TextUtils.isEmpty(str)) {
                String str4 = XDMDDFXmlHandler.g_szDmXmlTagString[8];
                Log.I("szTag : " + str4);
                String concat = str3.concat(str4);
                Log.I("szPath : " + str);
                String concat2 = concat.concat(str);
                String str5 = XDMDDFXmlHandler.g_szDmXmlTagString[9];
                Log.I("szTag" + str5);
                str3 = concat2.concat(str5);
            }
            if (!TextUtils.isEmpty(xDMVnode.m_szName)) {
                String concat3 = str3.concat(XDMDDFXmlHandler.g_szDmXmlTagString[6]).concat(xDMVnode.m_szName);
                Log.I("node.name : " + xDMVnode.m_szName);
                str3 = concat3.concat(XDMDDFXmlHandler.g_szDmXmlTagString[7]);
            }
            String xdmOmGetFormatString = XDMOmList.xdmOmGetFormatString(xDMVnode.format);
            if (!(TextUtils.isEmpty(xdmOmGetFormatString) && xDMVnode.acl == null && xDMVnode.type == null)) {
                String concat4 = str3.concat(XDMDDFXmlHandler.g_szDmXmlTagString[12]);
                if ((i & 2) == 2 && !TextUtils.isEmpty(xdmOmGetFormatString)) {
                    concat4 = concat4.concat(XDMDDFXmlHandler.g_szDmXmlTagString[16]).concat("<").concat(xdmOmGetFormatString).concat("/>").concat(XDMDDFXmlHandler.g_szDmXmlTagString[17]);
                }
                if ((i & 4) == 4 && xDMVnode.type != null) {
                    String str6 = (String) xDMVnode.type.data;
                    if (!TextUtils.isEmpty(str6)) {
                        concat4 = concat4.concat(XDMDDFXmlHandler.g_szDmXmlTagString[18]).concat("<MIME>").concat(str6).concat("</MIME>").concat(XDMDDFXmlHandler.g_szDmXmlTagString[19]);
                    }
                }
                if (!((i & 1) != 1 || xDMVnode.acl == null || (xDMOmAcl = (XDMOmAcl) xDMVnode.acl.data) == null || (i2 = xDMOmAcl.ac) == 0)) {
                    String concat5 = concat4.concat(XDMDDFXmlHandler.g_szDmXmlTagString[14]);
                    if ((i2 & 1) == 1) {
                        str2 = concat5.concat("Add=*");
                        z = true;
                    } else {
                        str2 = concat5;
                        z = false;
                    }
                    if ((i2 & 2) == 2) {
                        if (z) {
                            str2 = str2.concat("&amp;Delete=*");
                        } else {
                            str2 = str2.concat("Delete=*");
                            z = true;
                        }
                    }
                    if ((i2 & 4) == 4) {
                        if (z) {
                            str2 = str2.concat("&amp;Exec=*");
                        } else {
                            str2 = str2.concat("Exec=*");
                            z = true;
                        }
                    }
                    if ((i2 & 8) == 8) {
                        if (z) {
                            str2 = str2.concat("&amp;Get=*");
                        } else {
                            str2 = str2.concat("Get=*");
                            z = true;
                        }
                    }
                    if ((i2 & 16) == 16) {
                        if (z) {
                            str2 = str2.concat("&amp;Replace=*");
                        } else {
                            str2 = str2.concat("Replace=*");
                        }
                    }
                    concat4 = str2.concat(XDMDDFXmlHandler.g_szDmXmlTagString[15]);
                }
                str3 = concat4.concat(XDMDDFXmlHandler.g_szDmXmlTagString[13]);
            }
            if ((i & 8) != 8) {
                XDB.xdbAppendFile(xdbGetFileIdTNDS, str3.getBytes(Charset.defaultCharset()));
            } else if (xDMVnode.size > 0) {
                XDB.xdbAppendFile(xdbGetFileIdTNDS, str3.concat(XDMDDFXmlHandler.g_szDmXmlTagString[10]).getBytes(Charset.defaultCharset()));
                String xdmAgentGetPathFromNode = xdmAgentGetPathFromNode(xDMOmTree, xDMVnode);
                char[] cArr = new char[xDMVnode.size];
                int xdmOmRead = XDMOmLib.xdmOmRead(xDMOmTree, xdmAgentGetPathFromNode, 0, cArr, xDMVnode.size);
                String valueOf = String.valueOf(cArr);
                if (xdmOmRead > 0) {
                    XDB.xdbAppendFile(xdbGetFileIdTNDS, valueOf.getBytes(Charset.defaultCharset()));
                }
                XDB.xdbAppendFile(xdbGetFileIdTNDS, XDMDDFXmlHandler.g_szDmXmlTagString[11].getBytes(Charset.defaultCharset()));
            } else {
                XDB.xdbAppendFile(xdbGetFileIdTNDS, str3.getBytes(Charset.defaultCharset()));
            }
            for (XDMVnode xDMVnode2 = xDMVnode.childlist; xDMVnode2 != null; xDMVnode2 = xDMVnode2.next) {
                xdmAgentMakeTndsSubTree(xDMOmTree, xDMVnode2, i, null);
            }
            XDB.xdbAppendFile(xdbGetFileIdTNDS, XDMDDFXmlHandler.g_szDmXmlTagString[5].getBytes(Charset.defaultCharset()));
        }
    }

    private boolean xdmAgentCmdPropGetTnds(XDMParserGet xDMParserGet, XDMOmTree xDMOmTree, XDMVnode xDMVnode, String str) {
        int i;
        XDMParserResults xDMParserResults;
        int i2;
        XDMWorkspace xDMWorkspace = g_DmWs;
        Log.I("");
        if (xDMVnode == null || xDMVnode.childlist == null) {
            return false;
        }
        int xdbGetFileIdTNDS = XDB.xdbGetFileIdTNDS();
        String[] split = str.split("\\+");
        String str2 = split[0];
        Log.I("token : " + str2);
        if (split.length > 1) {
            String str3 = str2;
            i = 0;
            for (int i3 = 1; i3 < split.length; i3++) {
                if ("ACL".compareTo(str3) == 0) {
                    i2 = i | 1;
                } else if ("Format".compareTo(str3) == 0) {
                    i2 = i | 2;
                } else if ("Type".compareTo(str3) == 0) {
                    i2 = i | 4;
                } else if ("Value".compareTo(str3) == 0) {
                    i2 = i | 8;
                } else {
                    str3 = split[i3];
                }
                i = i2;
                str3 = split[i3];
            }
        } else {
            String[] split2 = str.split("-");
            if (split2 != null) {
                String str4 = split2[0];
                Log.I("token : " + str4);
                if ("ACL".compareTo(str4) == 0) {
                    i = 14;
                } else if ("Format".compareTo(str4) == 0) {
                    i = 13;
                } else if ("Type".compareTo(str4) == 0) {
                    i = 11;
                } else if ("Value".compareTo(str4) == 0) {
                    i = 7;
                }
            }
            i = 15;
        }
        XDB.xdbDeleteFile(xdbGetFileIdTNDS);
        XDB.xdbAppendFile(xdbGetFileIdTNDS, "".concat(XDMDDFXmlHandler.g_szDmXmlTagString[34]).concat(XDMDDFXmlHandler.g_szDmXmlTagString[0]).concat(XDMDDFXmlHandler.g_szDmXmlTagString[2]).concat("1.2").concat(XDMDDFXmlHandler.g_szDmXmlTagString[3]).getBytes(Charset.defaultCharset()));
        String xdmAgentGetPathFromNode = xdmAgentGetPathFromNode(xDMOmTree, xDMVnode);
        char[] cArr = new char[xdmAgentGetPathFromNode.length()];
        XDMOmLib.xdmOmMakeParentPath(xdmAgentGetPathFromNode, cArr);
        Log.I("tempPath : " + XDMMem.xdmLibCharToString(cArr));
        xdmAgentMakeTndsSubTree(xDMOmTree, xDMVnode, i, XDMMem.xdmLibCharToString(cArr));
        XDB.xdbAppendFile(xdbGetFileIdTNDS, XDMDDFXmlHandler.g_szDmXmlTagString[1].getBytes(Charset.defaultCharset()));
        XDB.xdbAppendFile(xdbGetFileIdTNDS, XDMDDFXmlHandler.g_szDmXmlTagString[35].getBytes(Charset.defaultCharset()));
        int xdbGetFileSize = (int) XDB.xdbGetFileSize(xdbGetFileIdTNDS);
        String str5 = new String((byte[]) XDB.xdbReadFile(xdbGetFileIdTNDS, 0, xdbGetFileSize), Charset.defaultCharset());
        String xdmAgentGetPathFromNode2 = xdmAgentGetPathFromNode(xDMOmTree, xDMVnode);
        String xdmOmGetFormatString = XDMOmList.xdmOmGetFormatString(8);
        Log.H("name : " + xdmAgentGetPathFromNode2);
        if (TextUtils.isEmpty(str5)) {
            Log.E("_____ TNDSResults File Read Error!");
            xDMParserResults = XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xdmAgentGetPathFromNode2, xdmOmGetFormatString, XDMInterface.SYNCML_MIME_TYPE_TNDS_XML, xdbGetFileSize, null);
        } else {
            xDMParserResults = XDMBuildCmd.xdmAgentBuildCmdDetailResults(xDMWorkspace, xDMParserGet.cmdid, xdmAgentGetPathFromNode2, xdmOmGetFormatString, XDMInterface.SYNCML_MIME_TYPE_TNDS_XML, xdbGetFileSize, str5.toCharArray());
        }
        XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.resultsList, xDMParserResults);
        return true;
    }

    private String xdmAgentGetAclStr(XDMOmList xDMOmList, XDMParserItem xDMParserItem) {
        String str = "\u0000";
        String[] strArr = {str, str, str, str, str};
        if (xDMOmList == null) {
            return null;
        }
        if (xDMParserItem.meta == null) {
            xDMParserItem.meta = null;
        } else if (TextUtils.isEmpty(xDMParserItem.meta.m_szFormat)) {
            xDMParserItem.meta.m_szFormat = null;
        } else {
            Log.I("item->meta !NULL");
        }
        while (xDMOmList != null) {
            XDMOmAcl xDMOmAcl = (XDMOmAcl) xDMOmList.data;
            if ((xDMOmAcl.ac & 1) > 0) {
                if (strArr[0].charAt(0) == 0) {
                    strArr[0] = xDMOmAcl.m_szServerid;
                } else {
                    strArr[0] = strArr[0].concat(xDMOmAcl.m_szServerid);
                }
            }
            if ((xDMOmAcl.ac & 2) > 0) {
                if (strArr[1].charAt(0) == 0) {
                    strArr[1] = xDMOmAcl.m_szServerid;
                } else {
                    strArr[1] = strArr[1].concat(xDMOmAcl.m_szServerid);
                }
            }
            if ((xDMOmAcl.ac & 4) > 0) {
                if (strArr[2].charAt(0) == 0) {
                    strArr[2] = xDMOmAcl.m_szServerid;
                } else {
                    strArr[2] = strArr[2].concat(xDMOmAcl.m_szServerid);
                }
            }
            if ((xDMOmAcl.ac & 8) > 0) {
                if (strArr[3].charAt(0) == 0) {
                    strArr[3] = xDMOmAcl.m_szServerid;
                } else {
                    strArr[3] = strArr[3].concat(xDMOmAcl.m_szServerid);
                }
            }
            if ((xDMOmAcl.ac & 16) > 0) {
                if (strArr[4].charAt(0) == 0) {
                    strArr[4] = xDMOmAcl.m_szServerid;
                } else {
                    strArr[4] = strArr[4].concat(xDMOmAcl.m_szServerid);
                }
            }
            xDMOmList = xDMOmList.next;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 0 && strArr[i].charAt(0) != 0) {
                if (str.charAt(0) != 0) {
                    if (xDMParserItem.meta == null) {
                        str = str.concat("&");
                    } else if (TextUtils.isEmpty(xDMParserItem.meta.m_szFormat)) {
                        str = str.concat("&");
                    } else if ("xml".compareTo(xDMParserItem.meta.m_szFormat) == 0) {
                        str = str.concat("&amp;");
                    } else {
                        str = str.concat("&");
                    }
                }
                if (str.charAt(0) != 0) {
                    str = str.concat("Add=");
                } else {
                    str = "Add=";
                }
            }
            if (i == 1 && strArr[i].charAt(0) != 0) {
                if (str.charAt(0) != 0) {
                    if (xDMParserItem.meta == null) {
                        str = str.concat("&");
                    } else if (TextUtils.isEmpty(xDMParserItem.meta.m_szFormat)) {
                        str = str.concat("&");
                    } else if ("xml".compareTo(xDMParserItem.meta.m_szFormat) == 0) {
                        str = str.concat("&amp;");
                    } else {
                        str = str.concat("&");
                    }
                }
                if (str.charAt(0) != 0) {
                    str = str.concat("Delete=");
                } else {
                    str = "Delete=";
                }
            }
            if (i == 2 && strArr[i].charAt(0) != 0) {
                if (str.charAt(0) != 0) {
                    if (xDMParserItem.meta == null) {
                        str = str.concat("&");
                    } else if (TextUtils.isEmpty(xDMParserItem.meta.m_szFormat)) {
                        str = str.concat("&");
                    } else if ("xml".compareTo(xDMParserItem.meta.m_szFormat) == 0) {
                        str = str.concat("&amp;");
                    } else {
                        str = str.concat("&");
                    }
                }
                if (str.charAt(0) != 0) {
                    str = str.concat("Exec=");
                } else {
                    str = "Exec=";
                }
            }
            if (i == 3 && strArr[i].charAt(0) != 0) {
                if (str.charAt(0) != 0) {
                    if (xDMParserItem.meta == null) {
                        str = str.concat("&");
                    } else if (TextUtils.isEmpty(xDMParserItem.meta.m_szFormat)) {
                        str = str.concat("&");
                    } else if ("xml".compareTo(xDMParserItem.meta.m_szFormat) == 0) {
                        str = str.concat("&amp;");
                    } else {
                        str = str.concat("&");
                    }
                }
                if (str.charAt(0) != 0) {
                    str = str.concat("Get=");
                } else {
                    str = "Get=";
                }
            }
            if (i == 4 && strArr[i].charAt(0) != 0) {
                if (str.charAt(0) != 0) {
                    if (xDMParserItem.meta == null) {
                        str = str.concat("&");
                    } else if (TextUtils.isEmpty(xDMParserItem.meta.m_szFormat)) {
                        str = str.concat("&");
                    } else if ("xml".compareTo(xDMParserItem.meta.m_szFormat) == 0) {
                        str = str.concat("&amp;");
                    } else {
                        str = str.concat("&");
                    }
                }
                if (str.charAt(0) != 0) {
                    str = str.concat("Replace=");
                } else {
                    str = "Replace=";
                }
            }
            if (strArr[i].charAt(0) != 0) {
                str = str.concat(strArr[i]);
            }
        }
        return str;
    }

    private XDMOmList xdmAgentMakeAcl(XDMOmList xDMOmList, String str) {
        char[] cArr = new char[str.length()];
        String xdmLibStrsplit = XDMMem.xdmLibStrsplit(str.toCharArray(), '&', cArr);
        String str2 = null;
        while (true) {
            if (TextUtils.isEmpty(xdmLibStrsplit)) {
                cArr[cArr.length - 1] = 0;
            }
            String xdmLibCharToString = XDMMem.xdmLibCharToString(cArr);
            if (xdmLibCharToString != null) {
                char[] cArr2 = new char[xdmLibCharToString.length()];
                xdmLibCharToString = XDMMem.xdmLibStrsplit(xdmLibCharToString.toCharArray(), '=', cArr2);
                str2 = XDMMem.xdmLibCharToString(cArr2);
            }
            if (!TextUtils.isEmpty(xdmLibCharToString)) {
                if (XDMInterface.CMD_ADD.compareTo(str2) == 0) {
                    xDMOmList = xdmAgentAppendAclItem(xDMOmList, xdmLibCharToString, 1);
                } else if (XDMInterface.CMD_DELETE.compareTo(str2) == 0) {
                    xDMOmList = xdmAgentAppendAclItem(xDMOmList, xdmLibCharToString, 2);
                } else if (XDMInterface.CMD_REPLACE.compareTo(str2) == 0) {
                    xDMOmList = xdmAgentAppendAclItem(xDMOmList, xdmLibCharToString, 16);
                } else if (XDMInterface.CMD_GET.compareTo(str2) == 0) {
                    xDMOmList = xdmAgentAppendAclItem(xDMOmList, xdmLibCharToString, 8);
                } else if (XDMInterface.CMD_EXEC.compareTo(str2) == 0) {
                    xDMOmList = xdmAgentAppendAclItem(xDMOmList, xdmLibCharToString, 4);
                }
            }
            if (TextUtils.isEmpty(xdmLibStrsplit)) {
                return xDMOmList;
            }
            if (xdmLibStrsplit.charAt(0) == 'a' && xdmLibStrsplit.charAt(1) == 'm' && xdmLibStrsplit.charAt(2) == 'p' && xdmLibStrsplit.charAt(3) == ';') {
                xdmLibStrsplit = xdmLibStrsplit.substring(4);
            }
            cArr = new char[(xdmLibStrsplit.length() + 1)];
            xdmLibStrsplit = XDMMem.xdmLibStrsplit(xdmLibStrsplit.toCharArray(), '&', cArr);
        }
    }

    private XDMOmList xdmAgentAppendAclItem(XDMOmList xDMOmList, String str, int i) {
        char[] cArr = new char[(str.length() + 1)];
        if (!str.contains("*")) {
            str = XDMMem.xdmLibStrsplit(str.toCharArray(), '+', cArr);
        } else {
            cArr[0] = '*';
            cArr[1] = 0;
        }
        char[] cArr2 = cArr;
        boolean z = false;
        while (!TextUtils.isEmpty(str)) {
            boolean z2 = z;
            XDMOmList xDMOmList2 = xDMOmList;
            while (xDMOmList2 != null && xDMOmList2.data != null) {
                XDMOmAcl xDMOmAcl = (XDMOmAcl) xDMOmList2.data;
                if (xDMOmAcl.m_szServerid.compareTo(String.valueOf(cArr2)) == 0) {
                    xDMOmAcl.ac |= i;
                    z2 = true;
                }
                xDMOmList2 = xDMOmList2.next;
            }
            if (!z2) {
                char[] cArr3 = new char[40];
                XDMOmAcl xDMOmAcl2 = new XDMOmAcl();
                if (!String.valueOf(cArr2).contains("*")) {
                    String.valueOf(cArr2).getChars(0, 39, cArr3, 0);
                } else {
                    cArr3[0] = '*';
                    cArr3[1] = 0;
                }
                xDMOmAcl2.m_szServerid = XDMMem.xdmLibCharToString(cArr3);
                xDMOmAcl2.ac |= i;
                XDMOmList xDMOmList3 = new XDMOmList();
                xDMOmList3.data = xDMOmAcl2;
                xDMOmList3.next = null;
                xDMOmList = XDMOmLib.xdmOmVfsAppendList(xDMOmList, xDMOmList3);
            }
            cArr2 = new char[str.length()];
            str = XDMMem.xdmLibStrsplit(str.toCharArray(), '+', cArr2);
            z = z2;
        }
        return xDMOmList;
    }

    public int xdmAgentStartMgmtSession() {
        int i;
        int i2;
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (xDMWorkspace == null) {
            Log.E("Parsing package failed Abort session " + 0);
            return -1;
        }
        if (xDMWorkspace.procState == XDMInterface.XDMProcessingState.XDM_PROC_NONE) {
            xDMWorkspace.numAction = 0;
            int xdmAgentParsingWbxml = xdmAgentParsingWbxml(xDMWorkspace.buf.toByteArray());
            if (xdmAgentParsingWbxml != 0) {
                Log.E("Parsing package failed Abort session" + xdmAgentParsingWbxml);
                return -1;
            }
        }
        try {
            int xdmAgentHandleCmd = xdmAgentHandleCmd();
            xDMWorkspace.uicFlag = XUICInterface.XUICFlag.UIC_TRUE;
            if (xdmAgentHandleCmd == -4) {
                Log.I("Handling Paused  Processing UIC Command");
                return xdmAgentHandleCmd;
            } else if (xdmAgentHandleCmd == 0) {
                if (xDMWorkspace.dmState != XDMInterface.XDMSyncMLState.XDM_STATE_FINISH) {
                    xDMWorkspace.msgID++;
                }
                if (xDMWorkspace.authState == 1 && xDMWorkspace.serverAuthState == 1) {
                    xDMWorkspace.dmState = XDMInterface.XDMSyncMLState.XDM_STATE_PROCESSING;
                    xDMWorkspace.authCount = 0;
                    Log.I("total action commands = " + xDMWorkspace.numAction);
                    if (xDMWorkspace.numAction != 0 || !xDMWorkspace.isFinal) {
                        int xdmAgentCreatePackage = xdmAgentCreatePackage();
                        if (xdmAgentCreatePackage < 0) {
                            Log.E("xdmAgentCreatePackage failed " + xdmAgentCreatePackage);
                            return -1;
                        }
                        if (XTPAdapter.g_HttpObj[0].nHttpConnection == 1) {
                            try {
                                i2 = this.m_HttpDMAdapter.xtpAdpOpen(0);
                            } catch (Exception e) {
                                Log.E(e.toString());
                                i2 = -2;
                            }
                            if (i2 != 0) {
                                Log.E("XTP_RET_CONNECTION_FAIL");
                                return -2;
                            }
                        }
                        return xdmAgentSendPackage();
                    }
                    XDBProfileListAdp.xdbClearUicResultKeepFlag();
                    int xdbGetFUMOUpdateMechanism = XDBFumoAdp.xdbGetFUMOUpdateMechanism();
                    int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
                    Log.I("nStatus :" + xdbGetFUMOStatus);
                    if (xdbGetFUMOUpdateMechanism == 2 && xdbGetFUMOStatus == 10) {
                        Log.I("Now Download Start");
                        return 5;
                    } else if (xdbGetFUMOUpdateMechanism == 1 && xdbGetFUMOStatus == 40) {
                        Log.I("OMA-DM Download Completed");
                        XDBFumoAdp.xdbSetFUMOStatus(50);
                        return 8;
                    } else if (xdbGetFUMOUpdateMechanism == 3 && xdbGetFUMOStatus == 10) {
                        Log.I("XDM_RET_EXEC_ALTERNATIVE_DOWNLOAD Start");
                        return 6;
                    } else if (xdbGetFUMOUpdateMechanism == 3 && (xdbGetFUMOStatus == 50 || xdbGetFUMOStatus == 251)) {
                        Log.I("Now Update Start");
                        return 7;
                    } else {
                        XDBProfileListAdp.xdbSetNotiReSyncMode(0);
                        XDBProfileListAdp.xdbClearUicResultKeepFlag();
                        return 3;
                    }
                } else {
                    xDMWorkspace.authCount++;
                    if (xDMWorkspace.authCount >= 3) {
                        xDMWorkspace.authCount = 0;
                        xDMWorkspace.serverAuthState = -8;
                        Log.E("Authentication Failed Abort");
                        XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                        return -5;
                    }
                    if (xDMWorkspace.authState == 0) {
                        xDMWorkspace.dmState = XDMInterface.XDMSyncMLState.XDM_STATE_PROCESSING;
                    } else {
                        Log.H("Authentication Retry...ws->dmState = " + xDMWorkspace.dmState);
                        if (!(xDMWorkspace.dmState == XDMInterface.XDMSyncMLState.XDM_STATE_CLIENT_INIT_MGMT || xDMWorkspace.dmState == XDMInterface.XDMSyncMLState.XDM_STATE_GENERIC_ALERT || xDMWorkspace.dmState == XDMInterface.XDMSyncMLState.XDM_STATE_GENERIC_ALERT_REPORT)) {
                            xDMWorkspace.dmState = XDMInterface.XDMSyncMLState.XDM_STATE_PROCESSING;
                        }
                    }
                    if (xdmAgentCreatePackage() != 0) {
                        Log.E(BluetoothManagerEnabler.REASON_FAILED);
                        return -1;
                    }
                    if (XTPAdapter.g_HttpObj[0].nHttpConnection == 1) {
                        try {
                            i = this.m_HttpDMAdapter.xtpAdpOpen(0);
                        } catch (Exception e2) {
                            Log.E(e2.toString());
                            i = -2;
                        }
                        if (i != 0) {
                            return -2;
                        }
                    }
                    return xdmAgentSendPackage();
                }
            } else if (xdmAgentHandleCmd != 1) {
                Log.E("Handling Commands failed Abort session " + xdmAgentHandleCmd);
                return -1;
            } else {
                Log.I("XDM_RET_ALERT_SESSION_ABORT");
                XDBFumoAdp.xdbSetFUMOStatus(0);
                XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                return xdmAgentHandleCmd;
            }
        } catch (XDMOmTreeException e3) {
            Log.E(e3.toString());
            Log.E("OmTree Delete");
            XDMOmLib.xdmOmVfsEnd(xDMWorkspace.om.vfs);
            XDMOmVfs.xdmOmVfsDeleteOmFile();
            return -1;
        } catch (Exception e4) {
            Log.E(e4.toString());
            return -1;
        }
    }

    public int xdmAgentStartGeneralAlert() {
        int i;
        XDMWorkspace xDMWorkspace = g_DmWs;
        Log.I("");
        if (xdmAgentInit() != 0) {
            return -1;
        }
        if (XDBProfileListAdp.xdbGetNotiEvent() > 0) {
            g_DmWs.m_szSessionID = XDBProfileListAdp.xdbGetNotiSessionID();
        } else {
            g_DmWs.m_szSessionID = xdmAgentLibMakeSessionID();
        }
        try {
            if (xdmAgentMakeNode() != 0) {
                return -1;
            }
        } catch (XDMOmTreeException e) {
            Log.E(e.toString());
            Log.E("OmTree Delete");
            XDMOmLib.xdmOmVfsEnd(xDMWorkspace.om.vfs);
            XDMOmLib.xdmOmVfsDeleteStdobj(xDMWorkspace.om.vfs);
            XDMOmVfs.xdmOmVfsDeleteOmFile();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        int xdbGetDmAgentType = XDBAgentAdp.xdbGetDmAgentType();
        Log.I("nAgentType : " + xdbGetDmAgentType);
        if (xdbGetDmAgentType == 1) {
            XDMOmTree xDMOmTree = g_DmWs.om;
            String xdbGetFUMOStatusNode = XDBFumoAdp.xdbGetFUMOStatusNode();
            if (!TextUtils.isEmpty(xdbGetFUMOStatusNode)) {
                String concat = XFOTAInterface.XFUMO_PATH.concat("/").concat(xdbGetFUMOStatusNode).concat(XFOTAInterface.XFUMO_STATE_PATH);
                String valueOf = String.valueOf(XDBFumoAdp.xdbGetFUMOStatus());
                Log.I("node[" + concat + "] value[" + valueOf + "]");
                try {
                    xdmAgentSetOMAccStr(xDMOmTree, concat, valueOf, 8, 2);
                } catch (XDMOmTreeException e3) {
                    Log.E(e3.toString());
                    Log.E("OmTree Delete");
                    XDMOmLib.xdmOmVfsEnd(xDMWorkspace.om.vfs);
                    XDMOmLib.xdmOmVfsDeleteStdobj(xDMWorkspace.om.vfs);
                    XDMOmVfs.xdmOmVfsDeleteOmFile();
                } catch (Exception e4) {
                    Log.E(e4.toString());
                }
            }
        }
        g_DmWs.dmState = XDMInterface.XDMSyncMLState.XDM_STATE_GENERIC_ALERT_REPORT;
        if (xdmAgentCreatePackage() != 0) {
            return -1;
        }
        try {
            i = this.m_HttpDMAdapter.xtpAdpOpen(0);
        } catch (Exception e5) {
            Log.E(e5.toString());
            i = -2;
        }
        if (i != 0) {
            return -2;
        }
        XTPAdapter.xtpAdpSetIsConnected(true);
        return xdmAgentSendPackage();
    }

    private int xdmAgentHandleCmd() throws XDMOmTreeException {
        XDMAgent xDMAgent;
        XDMLinkedList xDMLinkedList;
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (xDMWorkspace.procState == XDMInterface.XDMProcessingState.XDM_PROC_NONE) {
            xDMWorkspace.procStep = 0;
            xDMWorkspace.cmdID = 1;
        }
        boolean z = false;
        while (xDMWorkspace.procStep != 3) {
            if (!xDMWorkspace.IsSequenceProcessing) {
                xDMLinkedList = xDMWorkspace.list;
                XDMLinkedList.xdmListSetCurrentObj(xDMLinkedList, 0);
                xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
            } else {
                xDMLinkedList = xDMWorkspace.sequenceList;
                xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetObj(xDMLinkedList, 0);
                if (xDMAgent != null) {
                    xdmAgentCmdSequenceBlock(xDMWorkspace.sequenceList);
                } else {
                    xDMWorkspace.IsSequenceProcessing = false;
                }
                if (!xDMWorkspace.IsSequenceProcessing) {
                    xDMLinkedList = xDMWorkspace.list;
                    XDMLinkedList.xdmListSetCurrentObj(xDMLinkedList, 0);
                    XDMAgent xDMAgent2 = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
                    xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
                    XDMLinkedList.xdmListRemoveObjAtFirst(xDMLinkedList);
                }
            }
            if (xDMAgent == null) {
                if (xDMWorkspace.uicAlert == null) {
                    break;
                }
                xdmAgentCmdUicAlert();
            }
            while (xDMAgent != null) {
                if (XDMInterface.CMD_GET.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_EXEC.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_ALERT.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_ADD.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_REPLACE.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_COPY.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_DELETE.compareTo(xDMAgent.m_szCmd) == 0 || "Atomic_Start".compareTo(xDMAgent.m_szCmd) == 0 || "Sequence_Start".compareTo(xDMAgent.m_szCmd) == 0) {
                    xDMWorkspace.numAction++;
                }
                Log.I(xDMAgent.m_szCmd);
                if (xDMAgent.m_Atomic != null) {
                    xDMWorkspace.inAtomicCmd = true;
                    z = true;
                } else if (xDMAgent.m_Sequence != null) {
                    xDMWorkspace.inSequenceCmd = true;
                }
                int xdmAgentVerifyCmd = xdmAgentVerifyCmd(xDMAgent, z, null);
                if (!xDMWorkspace.IsSequenceProcessing) {
                    xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
                    XDMLinkedList.xdmListRemoveObjAtFirst(xDMLinkedList);
                }
                if (xdmAgentVerifyCmd == -4) {
                    Log.I("XDM_RET_PAUSED_BECAUSE_UIC_COMMAND");
                    return -4;
                }
                xDMWorkspace.atomicFlag = false;
                xDMWorkspace.inAtomicCmd = false;
                xDMWorkspace.inSequenceCmd = false;
                if (xdmAgentVerifyCmd == 5 || xdmAgentVerifyCmd == 4) {
                    xDMWorkspace.procStep = 3;
                    xDMWorkspace.numAction = 0;
                    return xdmAgentVerifyCmd;
                } else if (xdmAgentVerifyCmd == 1) {
                    return xdmAgentVerifyCmd;
                } else {
                    if (xdmAgentVerifyCmd != 0) {
                        Log.E("Processing failed");
                        return -1;
                    }
                }
            }
            XDMLinkedList.xdmListClearLinkedList(xDMLinkedList);
        }
        XDMLinkedList.xdmListClearLinkedList(xDMWorkspace.list);
        return 0;
    }

    private int xdmAgentVerifyCmd(XDMAgent xDMAgent, boolean z, XDMParserStatus xDMParserStatus) throws XDMOmTreeException {
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (XDMInterface.CMD_SYNCHDR.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdSyncHeader(xDMAgent.m_Header);
        }
        if (XDMInterface.CMD_STATUS.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdStatus(xDMAgent.m_Status);
        }
        if (XDMInterface.CMD_GET.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdGet(xDMAgent.m_Get, z);
        }
        if (XDMInterface.CMD_EXEC.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdExec(xDMAgent.m_Exec);
        }
        if (XDMInterface.CMD_ALERT.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdAlert(xDMAgent.m_Alert, z);
        }
        if (XDMInterface.CMD_ADD.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdAdd(xDMAgent.m_AddCmd, z, xDMParserStatus);
        }
        if (XDMInterface.CMD_REPLACE.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdReplace(xDMAgent.m_ReplaceCmd, z, xDMParserStatus);
        }
        if (XDMInterface.CMD_COPY.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdCopy(xDMAgent.m_CopyCmd, z, xDMParserStatus);
        }
        if (XDMInterface.CMD_DELETE.compareTo(xDMAgent.m_szCmd) == 0) {
            return xdmAgentCmdDelete(xDMAgent.m_DeleteCmd, z, xDMParserStatus);
        }
        if ("Atomic_Start".compareTo(xDMAgent.m_szCmd) == 0) {
            xDMWorkspace.inAtomicCmd = true;
            xDMWorkspace.atomicFlag = false;
            try {
                XDMOmLib.xdmOmVfsSaveFs(xDMWorkspace.om.vfs);
            } catch (Exception e) {
                Log.E(e.toString());
            }
            int xdmAgentCmdAtomic = xdmAgentCmdAtomic(xDMAgent.m_Atomic);
            if (xDMWorkspace.atomicFlag) {
                xDMWorkspace.om = null;
                xDMWorkspace.om = new XDMOmTree();
                XDMOmLib.xdmOmVfsInit(xDMWorkspace.om.vfs);
            }
            xDMWorkspace.inAtomicCmd = false;
            return xdmAgentCmdAtomic;
        } else if ("Sequence_Start".compareTo(xDMAgent.m_szCmd) == 0) {
            xDMWorkspace.inSequenceCmd = true;
            int xdmAgentCmdSequence = xdmAgentCmdSequence(xDMAgent.m_Sequence);
            if (xdmAgentCmdSequence == -4) {
                return xdmAgentCmdSequence;
            }
            xDMWorkspace.inSequenceCmd = false;
            return xdmAgentCmdSequence;
        } else {
            Log.E("Unknown Command" + xDMAgent.m_szCmd);
            return -6;
        }
    }

    private int xdmAgentCmdSequenceBlock(XDMLinkedList xDMLinkedList) throws XDMOmTreeException {
        int i;
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMLinkedList.xdmListSetCurrentObj(xDMLinkedList, 0);
        XDMAgent xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
        if (xDMAgent != null) {
            xDMWorkspace.IsSequenceProcessing = true;
        }
        int i2 = 0;
        while (xDMAgent != null) {
            if (XDMInterface.CMD_GET.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_EXEC.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_ALERT.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_ADD.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_REPLACE.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_COPY.compareTo(xDMAgent.m_szCmd) == 0 || XDMInterface.CMD_DELETE.compareTo(xDMAgent.m_szCmd) == 0) {
                xDMWorkspace.numAction++;
            }
            if ("Atomic_Start".compareTo(xDMAgent.m_szCmd) == 0) {
                Log.I("Atomic_Start");
                if (xDMWorkspace.inAtomicCmd) {
                    i = xdmAgentCmdAtomic(xDMAgent.m_Atomic);
                } else {
                    i = xdmAgentVerifyCmd(xDMAgent, true, null);
                }
            } else if ("Sequence_Start".compareTo(xDMAgent.m_szCmd) == 0) {
                Log.I("Sequence_Start");
                if (xDMWorkspace.inSequenceCmd) {
                    i = xdmAgentCmdSequence(xDMAgent.m_Sequence);
                } else {
                    i = xdmAgentVerifyCmd(xDMAgent, false, null);
                }
            } else {
                i = xdmAgentVerifyCmd(xDMAgent, false, null);
            }
            i2 = i;
            xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
            XDMLinkedList.xdmListRemoveObjAtFirst(xDMLinkedList);
            if (i2 == -4) {
                xDMWorkspace.sequenceList = xDMLinkedList;
                return -4;
            } else if (i2 == 1) {
                return i2;
            } else {
                if (i2 != 0) {
                    Log.E("Processing failed");
                    return -1;
                }
            }
        }
        XDMLinkedList.xdmListClearLinkedList(xDMLinkedList);
        xDMWorkspace.IsSequenceProcessing = false;
        return i2;
    }

    private int xdmAgentCmdSyncHeader(XDMParserSyncheader xDMParserSyncheader) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        xDMWorkspace.m_szMsgRef = String.valueOf(xDMParserSyncheader.msgid);
        if (!TextUtils.isEmpty(xDMParserSyncheader.m_szRespUri)) {
            xDMWorkspace.m_szTargetURI = xDMParserSyncheader.m_szRespUri;
        }
        if (xDMParserSyncheader.meta != null) {
            if (xDMParserSyncheader.meta.maxobjsize > 0) {
                xDMWorkspace.serverMaxObjSize = xDMParserSyncheader.meta.maxobjsize;
                if (xDMWorkspace.serverMaxObjSize <= 0) {
                    xDMWorkspace.serverMaxObjSize = 1048576;
                } else if (xDMWorkspace.serverMaxObjSize > 1048576) {
                    xDMWorkspace.serverMaxObjSize = 1048576;
                }
            } else {
                xDMWorkspace.serverMaxObjSize = 1048576;
            }
            if (xDMParserSyncheader.meta.maxmsgsize > 0) {
                xDMWorkspace.serverMaxMsgSize = xDMParserSyncheader.meta.maxmsgsize;
            } else {
                xDMWorkspace.serverMaxMsgSize = 5120;
            }
        } else {
            xDMWorkspace.serverMaxObjSize = 1048576;
            xDMWorkspace.serverMaxMsgSize = 5120;
        }
        if (xDMWorkspace.serverAuthState != 1) {
            if (xDMWorkspace.serverCredType == 2) {
                xDMWorkspace.serverAuthState = xdmAgentVerifyServerAuth(xDMParserSyncheader);
            } else if (xDMParserSyncheader.cred != null) {
                xDMWorkspace.serverAuthState = xdmAgentVerifyServerAuth(xDMParserSyncheader);
            } else {
                Log.H("Not Used Server Authentication");
                if (xDMWorkspace.serverAuthState == -7 || xDMWorkspace.serverAuthState == -9) {
                    xDMWorkspace.serverAuthState = -1;
                } else {
                    xDMWorkspace.serverAuthState = -9;
                }
            }
        }
        if (xDMWorkspace.serverAuthState == 1) {
            if (xDMWorkspace.serverCredType == 2) {
                xDMWorkspace.m_szStatusReturnCode = "200";
            } else {
                xDMWorkspace.m_szStatusReturnCode = XDMInterface.STATUS_AUTHENTICATIONACCEPTED;
            }
            XDBProfileAdp.xdbSetServerAuthType(xDMWorkspace.serverCredType);
        } else if (xDMWorkspace.serverAuthState == -9) {
            xDMWorkspace.m_szStatusReturnCode = "407";
        } else {
            xDMWorkspace.m_szStatusReturnCode = "401";
        }
        XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, 0, XDMInterface.CMD_SYNCHDR, xDMWorkspace.m_szHostname, xDMWorkspace.m_szSourceURI, xDMWorkspace.m_szStatusReturnCode));
        return 0;
    }

    private int xdmAgentCmdStatus(XDMParserStatus xDMParserStatus) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        if ("401".compareTo(xDMParserStatus.m_szData) == 0) {
            xDMWorkspace.authState = -7;
            Log.E("Client invalid credential(401)");
            if (XDMInterface.CMD_SYNCHDR.compareTo(xDMParserStatus.m_szCmd) == 0 && xDMParserStatus.chal == null) {
                Log.E("SyncHdr Status 401. and No Chal");
                xDMWorkspace.authCount = 3;
            }
        } else if (XDMInterface.STATUS_AUTHENTICATIONACCEPTED.compareTo(xDMParserStatus.m_szData) == 0) {
            xDMWorkspace.authState = 1;
        } else if ("200".compareTo(xDMParserStatus.m_szData) == 0 && XDMInterface.CMD_SYNCHDR.compareTo(xDMParserStatus.m_szCmd) == 0) {
            if (xDMWorkspace.credType != 2) {
                xDMWorkspace.authState = 1;
            } else if (xDMParserStatus.chal != null) {
                xDMWorkspace.authState = 1;
            }
            Log.I("Client Authorization Accepted (Catch 200)");
        } else if (XDMInterface.STATUS_ACCEPTED_AND_BUFFERED.compareTo(xDMParserStatus.m_szData) == 0) {
            Log.I("received Status 'buffered' cmd " + xDMParserStatus.m_szCmdRef);
            if (xDMWorkspace.tempResults != null) {
                XDMParserResults xDMParserResults = new XDMParserResults();
                XDMHandleCmd.xdmAgentDataStDuplResults(xDMParserResults, xDMWorkspace.tempResults);
                XDMLinkedList.xdmListAddObjAtFirst(xDMWorkspace.resultsList, xDMParserResults);
            } else {
                Log.E("can't find cached results can't send multi-messaged");
            }
        }
        if (xDMParserStatus.chal != null) {
            if (XDMInterface.CRED_TYPE_MD5.compareTo(xDMParserStatus.chal.m_szType) == 0) {
                xDMWorkspace.credType = 1;
                XDBProfileAdp.xdbSetAuthType(xDMWorkspace.credType);
                if ("b64".compareTo(xDMParserStatus.chal.m_szFormat) == 0) {
                    xdmAgentSetOMB64(m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH), xDMParserStatus.chal.m_szNextNonce);
                    byte[] xdmBase64Decode = XDMBase64.xdmBase64Decode(xDMParserStatus.chal.m_szNextNonce);
                    xDMWorkspace.nextNonce = new byte[xdmBase64Decode.length];
                    System.arraycopy(xdmBase64Decode, 0, xDMWorkspace.nextNonce, 0, xdmBase64Decode.length);
                    String str = new String(xDMWorkspace.nextNonce, Charset.defaultCharset());
                    Log.H("receive nextNonce:" + xDMParserStatus.chal.m_szNextNonce + "B64 decode String(ws.nextNonce):" + str);
                } else {
                    Log.I("!B64");
                    xdmAgentSetOM(m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH), xDMParserStatus.chal.m_szNextNonce);
                    xDMWorkspace.nextNonce = xDMParserStatus.chal.m_szNextNonce.getBytes(Charset.defaultCharset());
                }
                XDBProfileAdp.xdbSetClientNonce(xDMParserStatus.chal.m_szNextNonce);
                xdmAgentSetOM(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_AAUTHPREF_PATH), xDMParserStatus.chal.m_szType);
            } else if (XDMInterface.CRED_TYPE_HMAC.compareTo(xDMParserStatus.chal.m_szType) == 0) {
                xDMWorkspace.credType = 2;
                XDBProfileAdp.xdbSetAuthType(xDMWorkspace.credType);
                if ("b64".compareTo(xDMParserStatus.chal.m_szFormat) == 0) {
                    xdmAgentSetOMB64(m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH), xDMParserStatus.chal.m_szNextNonce);
                    byte[] xdmBase64Decode2 = XDMBase64.xdmBase64Decode(xDMParserStatus.chal.m_szNextNonce);
                    xDMWorkspace.nextNonce = new byte[xdmBase64Decode2.length];
                    System.arraycopy(xdmBase64Decode2, 0, xDMWorkspace.nextNonce, 0, xdmBase64Decode2.length);
                    String str2 = new String(xDMWorkspace.nextNonce, Charset.defaultCharset());
                    Log.H("B64 decode nextNonce" + str2);
                } else {
                    xdmAgentSetOM(m_DmAccXNodeInfo.m_szClientAppAuth.concat(XDMInterface.XDM_APPAUTH_AAUTHDATA_PATH), xDMParserStatus.chal.m_szNextNonce);
                    xDMWorkspace.nextNonce = xDMParserStatus.chal.m_szNextNonce.getBytes(Charset.defaultCharset());
                }
                XDBProfileAdp.xdbSetClientNonce(xDMParserStatus.chal.m_szNextNonce);
                xdmAgentSetOM(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_AAUTHPREF_PATH), xDMParserStatus.chal.m_szType);
            } else if (XDMInterface.CRED_TYPE_BASIC.compareTo(xDMParserStatus.chal.m_szType) == 0) {
                xDMWorkspace.credType = 0;
                XDBProfileAdp.xdbSetAuthType(xDMWorkspace.credType);
                xdmAgentSetOM(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_AAUTHPREF_PATH), xDMParserStatus.chal.m_szType);
            }
        }
        return 0;
    }

    private void xdmAgentCmdExecFumo(XDMWorkspace xDMWorkspace, XDMParserExec xDMParserExec, XDMParserItem xDMParserItem) {
        char c = xDMWorkspace.nUpdateMechanism;
        if (c == 0) {
            XDBFumoAdp.xdbSetFUMOStatus(0);
            XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
            this.m_Status = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_ACCEPTED_FOR_PROCESSING);
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, this.m_Status);
            Log.I("Mechanism is XDM_FOTA_MECHANISM_NONE");
        } else if (c == 1) {
            XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
            XDBFumoAdp.xdbSetFUMOStatus(0);
            this.m_Status = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, "406");
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, this.m_Status);
        } else if (c == 2) {
            XDBFumoAdp.xdbSetFUMOStatus(10);
            XDBFumoAdp.xdbSetFUMOUpdateReportURI(xDMParserItem.m_szTarget);
            Log.I("Mechanism is XDM_FOTA_MECHANISM_ALTERNATIVE");
            if (xDMParserExec != null) {
                this.m_Status = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_ACCEPTED_FOR_PROCESSING);
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, this.m_Status);
            }
        } else if (c != 3) {
            XDBFumoAdp.xdbSetFUMOStatus(0);
            XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
            this.m_Status = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, "500");
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, this.m_Status);
            Log.I("Mechanism is");
        } else {
            XDBFumoAdp.xdbSetFUMOUpdateReportURI(xDMParserItem.m_szTarget);
            int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
            Log.I("nStatus" + xdbGetFUMOStatus);
            if (xdbGetFUMOStatus == 251) {
                XDBFumoAdp.xdbSetFUMOStatus(50);
            } else {
                XDBFumoAdp.xdbSetFUMOStatus(10);
            }
            Log.I("Mechanism is XDM_FOTA_MECHANISM_ALTERNATIVE_DOWNLOAD");
            this.m_Status = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_ACCEPTED_FOR_PROCESSING);
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, this.m_Status);
        }
    }

    private int xdmAgentCmdExec(XDMParserExec xDMParserExec) {
        XDMParserStatus xDMParserStatus;
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMOmTree xDMOmTree = xDMWorkspace.om;
        XDMList xDMList = xDMParserExec.itemlist;
        while (xDMList != null) {
            XDMParserItem xDMParserItem = (XDMParserItem) xDMList.item;
            if (xDMWorkspace.serverAuthState != 1) {
                XDBFumoAdp.xdbSetFUMOStatus(0);
                XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                if (!TextUtils.isEmpty(xDMParserItem.m_szTarget)) {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, xDMWorkspace.m_szStatusReturnCode);
                } else {
                    xDMParserStatus = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, null, xDMWorkspace.m_szStatusReturnCode);
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus);
                xDMList = xDMList.next;
            } else if (TextUtils.isEmpty(xDMParserItem.m_szTarget)) {
                XDBFumoAdp.xdbSetFUMOStatus(0);
                XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, null, "404"));
                xDMList = xDMList.next;
            } else {
                XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, xDMParserItem.m_szTarget);
                if (xdmOmGetNodeProp == null) {
                    XDBFumoAdp.xdbSetFUMOStatus(0);
                    XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, "404"));
                    xDMList = xDMList.next;
                } else if (!xdmAgentIsAccessibleNode(xDMParserItem.m_szTarget)) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, "405"));
                    xDMList = xDMList.next;
                } else if (xdmAgentIsPermanentNode(xDMOmTree, xDMParserItem.m_szTarget)) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, "405"));
                    xDMList = xDMList.next;
                } else if (!XDMOmLib.xdmOmCheckAcl(xDMOmTree, xdmOmGetNodeProp, 4)) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_PERMISSION_DENIED));
                    xDMList = xDMList.next;
                } else if (!TextUtils.isEmpty(xDMParserItem.m_szTarget)) {
                    Log.I(xDMParserItem.m_szTarget);
                    if (!XDMOmLib.xdmOmCheckAclCurrentNode(xDMOmTree, xDMParserItem.m_szTarget, 4)) {
                        XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, XDMInterface.STATUS_PERMISSION_DENIED));
                        xDMList = xDMList.next;
                    } else if (xDMParserItem.m_szTarget.contains("/DownloadAndUpdate") || xDMParserItem.m_szTarget.contains("/Download") || xDMParserItem.m_szTarget.contains("/Update")) {
                        if (xDMParserItem.m_szTarget.contains("/Update") || xDMParserItem.m_szTarget.contains("/DownloadAndUpdate") || xDMParserItem.m_szTarget.contains("/Download")) {
                            XDBAgentAdp.xdbSetDmAgentType(1);
                        } else {
                            XDBAgentAdp.xdbSetDmAgentType(0);
                        }
                        if (!TextUtils.isEmpty(xDMParserExec.m_szCorrelator)) {
                            XDBFumoAdp.xdbSetFUMOCorrelator(xDMParserExec.m_szCorrelator);
                        }
                        if (xDMParserItem.data == null || xDMParserItem.data.data == null) {
                            XDBFumoAdp.xdbSetFUMOOptionalUpdate(false);
                        } else if ("O".equals(String.valueOf(xDMParserItem.data.data)) || "o".equals(String.valueOf(xDMParserItem.data.data))) {
                            XDBFumoAdp.xdbSetFUMOOptionalUpdate(true);
                        } else {
                            XDBFumoAdp.xdbSetFUMOOptionalUpdate(false);
                        }
                        int xdbGetDmAgentType = XDBAgentAdp.xdbGetDmAgentType();
                        if (xdbGetDmAgentType == 1) {
                            XDBFumoAdp.xdbSetUpdateFWVer("");
                            xdmAgentCmdExecFumo(xDMWorkspace, xDMParserExec, xDMParserItem);
                        } else {
                            Log.I(String.valueOf(xdbGetDmAgentType));
                        }
                        xDMList = xDMList.next;
                    } else {
                        Log.I("Node is not existed");
                        XDBFumoAdp.xdbSetFUMOStatus(0);
                        XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                        XDMLinkedList.xdmListAddObjAtLast(g_DmWs.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, xDMParserItem.m_szTarget, "406"));
                        xDMList = xDMList.next;
                    }
                } else {
                    Log.I("Error item->target->pLocURI is NULL");
                    XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                    XDBFumoAdp.xdbSetFUMOStatus(0);
                    XDMLinkedList.xdmListAddObjAtLast(g_DmWs.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserExec.cmdid, XDMInterface.CMD_EXEC, null, null, "403"));
                    xDMList = xDMList.next;
                }
            }
        }
        return 0;
    }

    private int xdmAgentCmdAlert(XDMParserAlert xDMParserAlert, boolean z) {
        XDMWorkspace xDMWorkspace = g_DmWs;
        xDMWorkspace.procState = XDMInterface.XDMProcessingState.XDM_PROC_ALERT;
        if (!TextUtils.isEmpty(xDMParserAlert.m_szData)) {
            Log.I("Code " + xDMParserAlert.m_szData);
            int i = 0;
            if (xDMWorkspace.serverAuthState != 1) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserAlert.cmdid, XDMInterface.CMD_ALERT, null, null, xDMWorkspace.m_szStatusReturnCode));
            } else if (z) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserAlert.cmdid, XDMInterface.CMD_ALERT, null, null, XDMInterface.STATUS_NOT_EXECUTED));
            } else if (XDMInterface.ALERT_NEXT_MESSAGE.compareTo(xDMParserAlert.m_szData) == 0) {
                xDMWorkspace.nextMsg = true;
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserAlert.cmdid, XDMInterface.CMD_ALERT, null, null, "200"));
            } else if ("1100".compareTo(xDMParserAlert.m_szData) == 0 || "1101".compareTo(xDMParserAlert.m_szData) == 0 || XDMInterface.ALERT_TEXT_INPUT.compareTo(xDMParserAlert.m_szData) == 0 || XDMInterface.ALERT_SINGLE_CHOICE.compareTo(xDMParserAlert.m_szData) == 0 || XDMInterface.ALERT_MULTIPLE_CHOICE.compareTo(xDMParserAlert.m_szData) == 0) {
                String str = null;
                if (xDMWorkspace.uicOption != null) {
                    XDMUic.xdmUicFreeUicOption(xDMWorkspace.uicOption);
                    xDMWorkspace.uicOption = null;
                }
                if (xDMWorkspace.uicOption == null) {
                    xDMWorkspace.uicOption = XDMUic.xdmUicCreateUicOption();
                }
                xDMWorkspace.uicOption.UICType = XDMUic.xdmUicGetUicType(xDMParserAlert.m_szData);
                XDMList xDMList = xDMParserAlert.itemlist;
                XDMParserItem xDMParserItem = (XDMParserItem) xDMList.item;
                if (xDMParserItem.data != null) {
                    str = XDMHandleCmd.xdmAgentDataStGetString(xDMParserItem.data);
                    Log.H("str = " + str);
                } else {
                    Log.I("str = NULL");
                }
                if (TextUtils.isEmpty(str)) {
                    if ("1100".compareTo(xDMParserAlert.m_szData) == 0 || "1101".compareTo(xDMParserAlert.m_szData) == 0 || XDMInterface.ALERT_TEXT_INPUT.compareTo(xDMParserAlert.m_szData) == 0 || XDMInterface.ALERT_SINGLE_CHOICE.compareTo(xDMParserAlert.m_szData) == 0 || XDMInterface.ALERT_MULTIPLE_CHOICE.compareTo(xDMParserAlert.m_szData) == 0) {
                        str = XDMInterface.DEFAULT_DISPLAY_UIC_OPTION;
                    } else if (!(xDMParserItem.data == null || xDMParserItem.data.data == null)) {
                        str = String.valueOf(xDMParserItem.data.data);
                    }
                }
                String xdmUicOptionProcess = XDMUic.xdmUicOptionProcess(str, xDMWorkspace.uicOption);
                XDMList xDMList2 = xDMList.next;
                if (xDMList2 != null) {
                    xDMParserItem = (XDMParserItem) xDMList2.item;
                }
                if (xDMParserItem.data != null) {
                    xdmUicOptionProcess = XDMHandleCmd.xdmAgentDataStGetString(xDMParserItem.data);
                }
                if (!(!TextUtils.isEmpty(xdmUicOptionProcess) || xDMParserItem.data == null || xDMParserItem.data.data == null)) {
                    xdmUicOptionProcess = String.valueOf(xDMParserItem.data.data);
                }
                if (!TextUtils.isEmpty(xdmUicOptionProcess)) {
                    xDMWorkspace.uicOption.text = XDMList.xdmListAppendStrText(xDMWorkspace.uicOption.text, xdmUicOptionProcess);
                }
                if (XDMInterface.ALERT_SINGLE_CHOICE.compareTo(xDMParserAlert.m_szData) == 0 || XDMInterface.ALERT_MULTIPLE_CHOICE.compareTo(xDMParserAlert.m_szData) == 0) {
                    if (xDMList2 != null) {
                        for (XDMList xDMList3 = xDMList2.next; xDMList3 != null; xDMList3 = xDMList3.next) {
                            XDMParserItem xDMParserItem2 = (XDMParserItem) xDMList3.item;
                            if (xDMParserItem2.data != null) {
                                xdmUicOptionProcess = XDMHandleCmd.xdmAgentDataStGetString(xDMParserItem2.data);
                            }
                            if (!(!TextUtils.isEmpty(xdmUicOptionProcess) || xDMParserItem2.data == null || xDMParserItem2.data.data == null)) {
                                xdmUicOptionProcess = String.valueOf(xDMParserItem2.data.data);
                            }
                            if (!TextUtils.isEmpty(xdmUicOptionProcess)) {
                                xDMWorkspace.uicOption.uicMenuList[i] = xdmUicOptionProcess;
                                i++;
                            }
                        }
                    }
                    xDMWorkspace.uicOption.uicMenuNumbers = i;
                }
                xDMWorkspace.uicOption.appId = xDMWorkspace.appId;
                if (xDMWorkspace.uicAlert != null) {
                    XDMHandleCmd.xdmAgentDataStDeleteAlert(xDMWorkspace.uicAlert);
                }
                xDMWorkspace.uicAlert = new XDMParserAlert();
                XDMHandleCmd.xdmAgentDataStDuplAlert(xDMWorkspace.uicAlert, xDMParserAlert);
                return -4;
            } else if (XDMInterface.ALERT_SESSION_ABORT.compareTo(xDMParserAlert.m_szData) == 0) {
                xDMWorkspace.sessionAbort = 1;
                return 1;
            }
            xDMWorkspace.procState = XDMInterface.XDMProcessingState.XDM_PROC_NONE;
            return 0;
        }
        Log.I("alert->data is NULL");
        return -1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:138:0x0338  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0342  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0366  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x036b  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0376  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0385  */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x03f2  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x0458  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int xdmAgentCmdAdd(com.accessorydm.eng.parser.XDMParserAdd r27, boolean r28, com.accessorydm.eng.parser.XDMParserStatus r29) throws com.accessorydm.eng.core.XDMOmTreeException {
        /*
        // Method dump skipped, instructions count: 1955
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMAgent.xdmAgentCmdAdd(com.accessorydm.eng.parser.XDMParserAdd, boolean, com.accessorydm.eng.parser.XDMParserStatus):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01e4, code lost:
        if (com.accessorydm.interfaces.XDMInterface.AUTH_TYPE_DIGEST.compareTo(java.lang.String.valueOf(r8)) == 0) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x02ad, code lost:
        if (com.accessorydm.interfaces.XDMInterface.AUTH_TYPE_DIGEST.compareTo(java.lang.String.valueOf(r5)) == 0) goto L_0x028b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x02c0  */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02c3  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x02dd  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x02e0  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x02fa  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02ff  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01f9  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01fc  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0218  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x021b  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0239  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x023e  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x025b  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x025e  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0278  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x027b  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x028d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int xdmAgentGetAccountFromOM(com.accessorydm.eng.core.XDMOmTree r17) throws com.accessorydm.eng.core.XDMOmTreeException {
        /*
        // Method dump skipped, instructions count: 812
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMAgent.xdmAgentGetAccountFromOM(com.accessorydm.eng.core.XDMOmTree):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:157:0x035e  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x03ef  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x04e0  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x04e7  */
    /* JADX WARNING: Removed duplicated region for block: B:204:0x04ef  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x051e  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x052e  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x0530  */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x053a  */
    /* JADX WARNING: Removed duplicated region for block: B:217:0x0553  */
    /* JADX WARNING: Removed duplicated region for block: B:241:0x05cd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int xdmAgentCmdReplace(com.accessorydm.eng.parser.XDMParserReplace r26, boolean r27, com.accessorydm.eng.parser.XDMParserStatus r28) {
        /*
        // Method dump skipped, instructions count: 1506
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMAgent.xdmAgentCmdReplace(com.accessorydm.eng.parser.XDMParserReplace, boolean, com.accessorydm.eng.parser.XDMParserStatus):int");
    }

    private int xdmAgentVerifyUpdateMechanism(XDMWorkspace xDMWorkspace, String str, String str2) {
        if (str.contains(XDMInterface.XDM_OMA_REPLACE)) {
            xDMWorkspace.nUpdateMechanism = 1;
            XDBFumoAdp.xdbSetFUMOStatus(10);
            XDBFumoAdp.xdbSetFUMOUpdateMechanism(xDMWorkspace.nUpdateMechanism);
        } else if (str.contains(XDMInterface.XDM_OMA_ALTERNATIVE)) {
            if (TextUtils.isEmpty(str2) || str2.length() > 256) {
                Log.I("D/L Mechanism  Object URL MISMATCH");
                return 0;
            } else if (!XDBFumoAdp.xdbSetFUMOServerUrl(str2)) {
                Log.I("wrong URL");
                return 0;
            } else {
                xDMWorkspace.nUpdateMechanism = 2;
                xDMWorkspace.m_szDownloadURI = str2;
                XDBFumoAdp.xdbSetFUMOUpdateMechanism(xDMWorkspace.nUpdateMechanism);
            }
        } else if (str.contains(XDMInterface.XDM_OMA_ALTERNATIVE_2)) {
            if (TextUtils.isEmpty(str2) || str2.length() > 256) {
                Log.I("D/L Mechanism  Object URL MISMATCH");
                return 0;
            } else if (!XDBFumoAdp.xdbSetFUMOServerUrl(str2)) {
                Log.I("wrong URL");
                return 0;
            } else {
                xDMWorkspace.nUpdateMechanism = 3;
                xDMWorkspace.m_szDownloadURI = str2;
                XDBFumoAdp.xdbSetFUMOUpdateMechanism(xDMWorkspace.nUpdateMechanism);
            }
        }
        return 1;
    }

    private int xdmAgentVerifyFotaOption(XDMWorkspace xDMWorkspace, String str, String str2) {
        Log.I("szPath : " + str + ", szData : " + str2);
        if (str.endsWith(XFOTAInterface.XFUMO_SVCSTATE)) {
            if (TextUtils.isEmpty(str2)) {
                return -1;
            }
            xDMWorkspace.m_szSvcState = str2;
            xdmAgentSetSvcState(str2);
            Log.I("SVCSTATE : " + xDMWorkspace.m_szSvcState);
        } else if (str.endsWith(XFOTAInterface.XFUMO_DOWNLOADCONNTYPE_PATH)) {
            if (TextUtils.isEmpty(str2)) {
                XDBFumoAdp.xdbSetFUMOWifiOnlyDownload(false);
                return -1;
            } else if (str2.endsWith(XFOTAInterface.XFUMO_DOWNLOAD_TYPE_WIFI)) {
                XDBFumoAdp.xdbSetFUMOWifiOnlyDownload(true);
            } else {
                XDBFumoAdp.xdbSetFUMOWifiOnlyDownload(false);
            }
        } else if (str.endsWith(XFOTAInterface.XFUMO_ROOTINGCHECK_PATH)) {
            Log.I("RootingCheckPath doesn't support");
        } else if (str.endsWith(XFOTAInterface.XFUMO_POSTPONE_PATH)) {
            Log.I("PostponeCountPath doesn't support");
        } else if (str.endsWith(XFOTAInterface.XFUMO_FORCE_PATH)) {
            try {
                XDBPostPoneAdp.xdbSetForceInstall(Integer.valueOf(str2).intValue());
            } catch (Exception e) {
                Log.E(e.toString());
                Log.E("Postpone Force - Set default value");
                XDBPostPoneAdp.xdbSetForceInstall(0);
                return -1;
            }
        }
        return 1;
    }

    private int xdmAgentCmdCopy(XDMParserCopy xDMParserCopy, boolean z, XDMParserStatus xDMParserStatus) {
        XDMParserStatus xDMParserStatus2;
        int i;
        char[] cArr;
        XDMParserStatus xDMParserStatus3;
        XDMParserItem xDMParserItem;
        XDMParserStatus xDMParserStatus4;
        XDMParserStatus xDMParserStatus5;
        XDMWorkspace xDMWorkspace = g_DmWs;
        XDMOmTree xDMOmTree = xDMWorkspace.om;
        char[] cArr2 = new char[80];
        boolean xdmAgentCmdUicAlert = xdmAgentCmdUicAlert();
        XDMList xDMList = xDMParserCopy.itemlist;
        while (xDMList != null) {
            XDMParserItem xDMParserItem2 = (XDMParserItem) xDMList.item;
            if (xDMWorkspace.serverAuthState != 1) {
                if (!TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                    xDMParserStatus5 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, xDMWorkspace.m_szStatusReturnCode);
                } else {
                    xDMParserStatus5 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, null, xDMWorkspace.m_szStatusReturnCode);
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus5);
                xDMList = xDMList.next;
            } else if (!xdmAgentCmdUicAlert) {
                if (xDMParserItem2.moredata > 0) {
                    xDMWorkspace.dataBuffered = true;
                }
                if (!TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                    xDMParserStatus4 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                } else {
                    xDMParserStatus4 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, null, XDMInterface.STATUS_NOT_EXECUTED);
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus4);
                xDMList = xDMList.next;
            } else if (z && xDMWorkspace.atomicStep != XDMInterface.XDMAtomicStep.XDM_ATOMIC_NONE) {
                if (xDMWorkspace.tmpItem != null) {
                    if (xDMWorkspace.tmpItem.equals(xDMParserItem2)) {
                        if (!TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                            int i2 = xDMParserCopy.cmdid;
                            String str = xDMParserItem2.m_szTarget;
                            xDMParserItem = null;
                            xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, i2, XDMInterface.CMD_COPY, null, str, XDMInterface.STATUS_ALREADY_EXISTS);
                        } else {
                            xDMParserItem = null;
                            xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, null, XDMInterface.STATUS_ALREADY_EXISTS);
                        }
                        xDMWorkspace.atomicStep = XDMInterface.XDMAtomicStep.XDM_ATOMIC_STEP_NOT_EXEC;
                        xDMWorkspace.tmpItem = xDMParserItem;
                    } else if (!TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                        xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, XDMInterface.STATUS_ATOMIC_ROLL_BACK_OK);
                    } else {
                        xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, null, XDMInterface.STATUS_ATOMIC_ROLL_BACK_OK);
                    }
                } else if (xDMWorkspace.atomicStep == XDMInterface.XDMAtomicStep.XDM_ATOMIC_STEP_NOT_EXEC) {
                    if (!TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                        xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, XDMInterface.STATUS_NOT_EXECUTED);
                    } else {
                        xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, null, XDMInterface.STATUS_NOT_EXECUTED);
                    }
                } else if (!TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                    xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, XDMInterface.STATUS_ATOMIC_ROLL_BACK_OK);
                } else {
                    xDMParserStatus3 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, null, XDMInterface.STATUS_ATOMIC_ROLL_BACK_OK);
                }
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus3);
                xDMList = xDMList.next;
            } else if (TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, null, "404"));
                xDMList = xDMList.next;
            } else if (TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, null, "404"));
                xDMList = xDMList.next;
            } else {
                XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, xDMParserItem2.m_szSource);
                if (xdmOmGetNodeProp == null) {
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szSource, XDMInterface.STATUS_NOT_EXECUTED));
                    xDMList = xDMList.next;
                    if (z && xDMParserStatus != null) {
                        xDMParserStatus.m_szData = XDMInterface.STATUS_ATOMIC_FAILED;
                    }
                } else {
                    int i3 = xdmOmGetNodeProp.size;
                    char[] cArr3 = new char[i3];
                    XDMOmVfs.xdmOmVfsGetData(xDMOmTree.vfs, xdmOmGetNodeProp, cArr3);
                    int i4 = xdmOmGetNodeProp.format;
                    if (!(xdmOmGetNodeProp.type == null || xdmOmGetNodeProp.type.data == null)) {
                        String.valueOf(xdmOmGetNodeProp.type.data);
                    }
                    int i5 = xdmOmGetNodeProp.format;
                    XDMVnode xdmOmGetNodeProp2 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, xDMParserItem2.m_szTarget);
                    if (xdmOmGetNodeProp2 == null || xDMParserItem2.moredata != 0 || xDMWorkspace.dataBuffered) {
                        XDMOmLib.xdmOmMakeParentPath(xDMParserItem2.m_szTarget, cArr2);
                        XDMVnode xdmOmGetNodeProp3 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, XDMMem.xdmLibCharToString(cArr2));
                        if (xdmOmGetNodeProp3 == null) {
                            if (xDMParserItem2.moredata > 0) {
                                xDMWorkspace.dataBuffered = true;
                            }
                            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, "500"));
                            xDMList = xDMList.next;
                            if (z && xDMParserStatus != null) {
                                xDMParserStatus.m_szData = XDMInterface.STATUS_ATOMIC_FAILED;
                            }
                        } else if (!XDMOmLib.xdmOmCheckAcl(xDMOmTree, xdmOmGetNodeProp3, 1)) {
                            if (xDMParserItem2.moredata > 0) {
                                xDMWorkspace.dataBuffered = true;
                            }
                            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, XDMInterface.STATUS_PERMISSION_DENIED));
                            xDMList = xDMList.next;
                        } else {
                            xDMWorkspace.dataTotalSize = i3;
                            if (XDMOmLib.xdmOmWrite(xDMOmTree, xDMParserItem2.m_szTarget, xDMWorkspace.dataTotalSize, 0, String.valueOf(cArr3), i3) < 0) {
                                if (xDMParserItem2.moredata > 0) {
                                    xDMWorkspace.dataBuffered = true;
                                }
                                XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, "500"));
                                xDMWorkspace.dataBuffered = false;
                                xDMList = xDMList.next;
                                if (z && xDMParserStatus != null) {
                                    xDMParserStatus.m_szData = XDMInterface.STATUS_ATOMIC_FAILED;
                                }
                            } else {
                                XDMVnode xdmOmGetNodeProp4 = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, xDMParserItem2.m_szTarget);
                                if (xdmOmGetNodeProp4 != null) {
                                    if (!TextUtils.isEmpty(null)) {
                                        if (xdmOmGetNodeProp4.type != null) {
                                            XDMOmLib.xdmOmVfsDeleteMimeList(xdmOmGetNodeProp4.type);
                                        }
                                        xdmOmGetNodeProp4.type = new XDMOmList();
                                        xdmOmGetNodeProp4.type.data = null;
                                        xdmOmGetNodeProp4.type.next = null;
                                    }
                                    xdmOmGetNodeProp4.format = i5;
                                }
                                if (xDMParserItem2.moredata == 0) {
                                    xDMWorkspace.prevBufPos = 0;
                                    xDMWorkspace.dataBuffered = false;
                                    xDMWorkspace.dataTotalSize = 0;
                                    xDMParserStatus2 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, "200");
                                } else {
                                    xDMWorkspace.prevBufPos += i3;
                                    xDMWorkspace.dataBuffered = true;
                                    xDMParserStatus2 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, XDMInterface.STATUS_ACCEPTED_AND_BUFFERED);
                                }
                            }
                        }
                    } else {
                        int i6 = xdmOmGetNodeProp2.size;
                        if (i6 < i3) {
                            cArr = new char[i3];
                            i = i3;
                        } else {
                            i = i6;
                            cArr = new char[i6];
                        }
                        if (xdmOmGetNodeProp2.format == 6) {
                            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szSource, XDMInterface.STATUS_NOT_EXECUTED));
                            xDMList = xDMList.next;
                            if (z && xDMParserStatus != null) {
                                xDMParserStatus.m_szData = XDMInterface.STATUS_ATOMIC_FAILED;
                            }
                        } else {
                            System.arraycopy(cArr3, 0, cArr, 0, i3);
                            XDMOmVfs.xdmOmVfsSetData(xDMOmTree.vfs, xdmOmGetNodeProp2, new String(cArr), i3);
                            if (xDMParserItem2.moredata == 0) {
                                xDMWorkspace.prevBufPos = 0;
                                xDMWorkspace.dataBuffered = false;
                                xDMWorkspace.dataTotalSize = 0;
                                xDMParserStatus2 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, "200");
                            } else {
                                xDMWorkspace.prevBufPos += i;
                                xDMWorkspace.dataBuffered = true;
                                xDMParserStatus2 = XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserCopy.cmdid, XDMInterface.CMD_COPY, null, xDMParserItem2.m_szTarget, XDMInterface.STATUS_ACCEPTED_AND_BUFFERED);
                            }
                        }
                    }
                    XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, xDMParserStatus2);
                    xDMList = xDMList.next;
                }
            }
        }
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:82:0x01f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int xdmAgentCmdDelete(com.accessorydm.eng.parser.XDMParserDelete r17, boolean r18, com.accessorydm.eng.parser.XDMParserStatus r19) {
        /*
        // Method dump skipped, instructions count: 517
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMAgent.xdmAgentCmdDelete(com.accessorydm.eng.parser.XDMParserDelete, boolean, com.accessorydm.eng.parser.XDMParserStatus):int");
    }

    private int xdmAgentCmdAtomic(XDMParserAtomic xDMParserAtomic) throws XDMOmTreeException {
        int xdmAgentCmdAtomicBlock;
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (xDMParserAtomic == null || xDMParserAtomic.itemlist == null || (xdmAgentCmdAtomicBlock = xdmAgentCmdAtomicBlock(xDMParserAtomic, xDMParserAtomic.itemlist)) < 0) {
            return -1;
        }
        xDMWorkspace.numAction += xdmAgentCmdAtomicBlock;
        return 0;
    }

    private int xdmAgentCmdSequence(XDMParserSequence xDMParserSequence) throws XDMOmTreeException {
        XDMWorkspace xDMWorkspace = g_DmWs;
        if (xDMParserSequence == null) {
            return -1;
        }
        if (!xDMWorkspace.IsSequenceProcessing) {
            XDMLinkedList.xdmListAddObjAtLast(xDMWorkspace.statusList, XDMBuildCmd.xdmAgentBuildCmdStatus(xDMWorkspace, xDMParserSequence.cmdid, XDMInterface.CMD_SEQUENCE, null, null, "200"));
        }
        if (xDMParserSequence.itemlist == null) {
            return -1;
        }
        int xdmAgentCmdSequenceBlock = xdmAgentCmdSequenceBlock(xDMParserSequence.itemlist);
        if (xdmAgentCmdSequenceBlock == -4) {
            return -4;
        }
        if (xdmAgentCmdSequenceBlock < 0) {
            return -1;
        }
        return xdmAgentCmdSequenceBlock;
    }

    public static void xdmAgentSetXNodePath(String str, String str2, boolean z) {
        Log.I("target[" + str2 + "]parent[" + str + "]");
        if (xdmAgentGetSyncMode() != 3) {
            m_DmAccXNodeTndsInfo = new XDMAccXNode();
            if (XDMInterface.XDM_BASE_PATH.compareTo(str) == 0 || XDMInterface.XDM_ACCOUNT_PATH.compareTo(str) == 0) {
                XDMAccXNode xDMAccXNode = m_DmAccXNodeTndsInfo;
                xDMAccXNode.m_szAccount = str;
                xDMAccXNode.m_szAccount = xDMAccXNode.m_szAccount.concat("/");
                XDMAccXNode xDMAccXNode2 = m_DmAccXNodeTndsInfo;
                xDMAccXNode2.m_szAccount = xDMAccXNode2.m_szAccount.concat(str2);
            }
            if (str.compareTo(m_DmAccXNodeTndsInfo.m_szAccount.concat(XDMInterface.XDM_ACC_TOCONREF_PATH)) == 0) {
                XDMAccXNode xDMAccXNode3 = m_DmAccXNodeTndsInfo;
                xDMAccXNode3.m_szToConRef = str;
                xDMAccXNode3.m_szToConRef = xDMAccXNode3.m_szToConRef.concat("/");
                XDMAccXNode xDMAccXNode4 = m_DmAccXNodeTndsInfo;
                xDMAccXNode4.m_szToConRef = xDMAccXNode4.m_szToConRef.concat(str2);
            }
            if (str.compareTo(m_DmAccXNodeTndsInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPADDR_PATH)) == 0) {
                XDMAccXNode xDMAccXNode5 = m_DmAccXNodeTndsInfo;
                xDMAccXNode5.m_szAppAddr = str;
                xDMAccXNode5.m_szAppAddr = xDMAccXNode5.m_szAppAddr.concat("/");
                XDMAccXNode xDMAccXNode6 = m_DmAccXNodeTndsInfo;
                xDMAccXNode6.m_szAppAddr = xDMAccXNode6.m_szAppAddr.concat(str2);
            }
            if (str.compareTo(m_DmAccXNodeTndsInfo.m_szAppAddr.concat(XDMInterface.XDM_APPADDR_PORT_PATH)) == 0) {
                XDMAccXNode xDMAccXNode7 = m_DmAccXNodeTndsInfo;
                xDMAccXNode7.m_szAppAddrPort = str;
                xDMAccXNode7.m_szAppAddrPort = xDMAccXNode7.m_szAppAddrPort.concat("/");
                XDMAccXNode xDMAccXNode8 = m_DmAccXNodeTndsInfo;
                xDMAccXNode8.m_szAppAddrPort = xDMAccXNode8.m_szAppAddrPort.concat(str2);
            }
            if (str.compareTo(m_DmAccXNodeTndsInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPAUTH_PATH)) != 0) {
                return;
            }
            if (str2.compareTo("ClientSide") == 0) {
                XDMAccXNode xDMAccXNode9 = m_DmAccXNodeTndsInfo;
                xDMAccXNode9.m_szClientAppAuth = str;
                xDMAccXNode9.m_szClientAppAuth = xDMAccXNode9.m_szClientAppAuth.concat("/");
                XDMAccXNode xDMAccXNode10 = m_DmAccXNodeTndsInfo;
                xDMAccXNode10.m_szClientAppAuth = xDMAccXNode10.m_szClientAppAuth.concat(str2);
                return;
            }
            XDMAccXNode xDMAccXNode11 = m_DmAccXNodeTndsInfo;
            xDMAccXNode11.m_szServerAppAuth = str;
            xDMAccXNode11.m_szServerAppAuth = xDMAccXNode11.m_szServerAppAuth.concat("/");
            XDMAccXNode xDMAccXNode12 = m_DmAccXNodeTndsInfo;
            xDMAccXNode12.m_szServerAppAuth = xDMAccXNode12.m_szServerAppAuth.concat(str2);
        } else if (z) {
            if (XDMInterface.XDM_BASE_PATH.compareTo(str) == 0 || XDMInterface.XDM_ACCOUNT_PATH.compareTo(str) == 0) {
                XDMAccXNode xDMAccXNode13 = m_DmAccXNodeInfo;
                xDMAccXNode13.m_szAccount = str;
                xDMAccXNode13.m_szAccount = xDMAccXNode13.m_szAccount.concat("/");
                XDMAccXNode xDMAccXNode14 = m_DmAccXNodeInfo;
                xDMAccXNode14.m_szAccount = xDMAccXNode14.m_szAccount.concat(str2);
            }
            if (str.compareTo(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_TOCONREF_PATH)) == 0) {
                XDMAccXNode xDMAccXNode15 = m_DmAccXNodeInfo;
                xDMAccXNode15.m_szToConRef = str;
                xDMAccXNode15.m_szToConRef = xDMAccXNode15.m_szToConRef.concat("/");
                XDMAccXNode xDMAccXNode16 = m_DmAccXNodeInfo;
                xDMAccXNode16.m_szToConRef = xDMAccXNode16.m_szToConRef.concat(str2);
            }
            if (str.compareTo(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPADDR_PATH)) == 0) {
                XDMAccXNode xDMAccXNode17 = m_DmAccXNodeInfo;
                xDMAccXNode17.m_szAppAddr = str;
                xDMAccXNode17.m_szAppAddr = xDMAccXNode17.m_szAppAddr.concat("/");
                XDMAccXNode xDMAccXNode18 = m_DmAccXNodeInfo;
                xDMAccXNode18.m_szAppAddr = xDMAccXNode18.m_szAppAddr.concat(str2);
            }
            if (str.compareTo(m_DmAccXNodeInfo.m_szAppAddr.concat(XDMInterface.XDM_APPADDR_PORT_PATH)) == 0) {
                XDMAccXNode xDMAccXNode19 = m_DmAccXNodeInfo;
                xDMAccXNode19.m_szAppAddrPort = str;
                xDMAccXNode19.m_szAppAddrPort = xDMAccXNode19.m_szAppAddrPort.concat("/");
                XDMAccXNode xDMAccXNode20 = m_DmAccXNodeInfo;
                xDMAccXNode20.m_szAppAddrPort = xDMAccXNode20.m_szAppAddrPort.concat(str2);
            }
            if (str.compareTo(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPAUTH_PATH)) != 0) {
                return;
            }
            if ("ClientSide".compareTo(str2) == 0) {
                XDMAccXNode xDMAccXNode21 = m_DmAccXNodeInfo;
                xDMAccXNode21.m_szClientAppAuth = str;
                xDMAccXNode21.m_szClientAppAuth = xDMAccXNode21.m_szClientAppAuth.concat("/");
                XDMAccXNode xDMAccXNode22 = m_DmAccXNodeInfo;
                xDMAccXNode22.m_szClientAppAuth = xDMAccXNode22.m_szClientAppAuth.concat(str2);
                return;
            }
            XDMAccXNode xDMAccXNode23 = m_DmAccXNodeInfo;
            xDMAccXNode23.m_szServerAppAuth = str;
            xDMAccXNode23.m_szServerAppAuth = xDMAccXNode23.m_szServerAppAuth.concat("/");
            XDMAccXNode xDMAccXNode24 = m_DmAccXNodeInfo;
            xDMAccXNode24.m_szServerAppAuth = xDMAccXNode24.m_szServerAppAuth.concat(str2);
        } else {
            if (XDMInterface.XDM_BASE_PATH.compareTo(str) == 0 || XDMInterface.XDM_ACCOUNT_PATH.compareTo(str) == 0) {
                m_DmAccXNodeInfo.m_szAccount = str2;
            }
            if (str.compareTo(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_TOCONREF_PATH)) == 0) {
                m_DmAccXNodeInfo.m_szToConRef = str2;
            }
            if (str.compareTo(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPADDR_PATH)) == 0) {
                m_DmAccXNodeInfo.m_szAppAddr = str2;
            }
            if (str.compareTo(m_DmAccXNodeInfo.m_szAppAddr.concat(XDMInterface.XDM_APPADDR_PORT_PATH)) == 0) {
                m_DmAccXNodeInfo.m_szAppAddrPort = str2;
            }
            if (str.compareTo(m_DmAccXNodeInfo.m_szAccount.concat(XDMInterface.XDM_ACC_APPAUTH_PATH)) != 0) {
                return;
            }
            if ("ClientSide".compareTo(str2) == 0) {
                m_DmAccXNodeInfo.m_szClientAppAuth = str2;
            } else {
                m_DmAccXNodeInfo.m_szServerAppAuth = str2;
            }
        }
    }

    private void xdmAgentSetAclDynamicFUMONode(XDMOmTree xDMOmTree, String str) {
        Log.I("target path[" + str + "]");
        if (str.contains(XFOTAInterface.XFUMO_PKGNAME_PATH)) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 24, 2);
        } else if (str.contains(XFOTAInterface.XFUMO_PKGVERSION_PATH)) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 24, 2);
        } else if (str.contains(XFOTAInterface.XFUMO_PKGURL_PATH)) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 25, 2);
        } else if (str.contains("/DownloadAndUpdate")) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 13, 2);
        } else if (str.contains("/Update")) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 12, 2);
        } else if (str.contains(XFOTAInterface.XFUMO_PKGDATA_PATH)) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 16, 2);
        } else if (str.contains("/Download")) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 13, 2);
        } else if (str.contains(XFOTAInterface.XFUMO_STATE_PATH)) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 8, 2);
        } else if (str.contains("/Ext")) {
            xdmAgentMakeDefaultAcl(xDMOmTree, str, 8, 2);
        }
    }

    public void xdmAgentTpClose(int i) {
        this.m_HttpDMAdapter.xtpAdpClose(i);
    }

    public int xdmAgentTpInit(int i) {
        return this.m_HttpDMAdapter.xtpAdpInit(i);
    }

    public void xdmAgentTpCloseNetwork(int i) {
        this.m_HttpDMAdapter.xtpAdpCloseNetWork(i);
    }

    public boolean xdmAgentTpCheckRetry() {
        Log.I("DM ConnectRetryCount " + m_nConnectRetryCount);
        if (XDMDevinfAdapter.xdmBlocksDueToRoamingNetwork()) {
            m_nConnectRetryCount = 3;
        }
        int i = m_nConnectRetryCount;
        if (i >= 3) {
            m_nConnectRetryCount = 0;
            return false;
        }
        m_nConnectRetryCount = i + 1;
        return true;
    }

    public static boolean xdmAgentCheckChangeProtocolCount() {
        m_nChangedProtocolCount++;
        Log.I("ChangeProtocolCount " + m_nChangedProtocolCount);
        if (m_nChangedProtocolCount < 5) {
            return true;
        }
        m_nChangedProtocolCount = 0;
        return false;
    }

    public static void xdmAgentResetChangeProtocolCount() {
        m_nChangedProtocolCount = 0;
    }

    public static void xdmAgentTpSetRetryCount(int i) {
        m_nConnectRetryCount = i;
    }

    public static boolean xdmAgentGetPendingStatus() {
        return m_bPendingStatus;
    }

    public static String xdmAgentGetDefaultLocuri() {
        return XFOTAInterface.XFUMO_PATH.concat("/DownloadAndUpdate");
    }

    private static String xdmAgentGetDevNetworkConnType() {
        String xdmGetUsingBearer = XDMCommonUtils.xdmGetUsingBearer();
        String xdbGetFUMODownloadConnType = XDBFumoAdp.xdbGetFUMODownloadConnType();
        int xdbGetDmAgentType = XDBAgentAdp.xdbGetDmAgentType();
        Log.I("nAgentType = " + xdbGetDmAgentType);
        return xdbGetDmAgentType == 0 ? xdmGetUsingBearer : xdbGetFUMODownloadConnType;
    }

    public static String xdmAgentGetSvcState() {
        return szSvcState;
    }

    public static void xdmAgentSetSvcState(String str) {
        Log.I("SvcState : " + str);
        szSvcState = str;
    }
}
