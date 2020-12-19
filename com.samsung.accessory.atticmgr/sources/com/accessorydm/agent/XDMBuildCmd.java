package com.accessorydm.agent;

import android.text.TextUtils;
import com.accessorydm.db.file.XDBAgentAdp;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBProfileAdp;
import com.accessorydm.eng.core.XDMAuth;
import com.accessorydm.eng.core.XDMBase64;
import com.accessorydm.eng.core.XDMLinkedList;
import com.accessorydm.eng.core.XDMList;
import com.accessorydm.eng.core.XDMMem;
import com.accessorydm.eng.core.XDMOmLib;
import com.accessorydm.eng.core.XDMVnode;
import com.accessorydm.eng.core.XDMWorkspace;
import com.accessorydm.eng.parser.XDMParserAlert;
import com.accessorydm.eng.parser.XDMParserCred;
import com.accessorydm.eng.parser.XDMParserItem;
import com.accessorydm.eng.parser.XDMParserMeta;
import com.accessorydm.eng.parser.XDMParserPcdata;
import com.accessorydm.eng.parser.XDMParserReplace;
import com.accessorydm.eng.parser.XDMParserResults;
import com.accessorydm.eng.parser.XDMParserStatus;
import com.accessorydm.eng.parser.XDMParserSyncheader;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.GeneralUtil;
import java.nio.charset.Charset;

public class XDMBuildCmd extends XDMHandleCmd implements XFOTAInterface {
    private static int xdmAgentBuildCmdGetCmdID(XDMWorkspace xDMWorkspace) {
        int i = xDMWorkspace.cmdID;
        xDMWorkspace.cmdID = i + 1;
        return i;
    }

    public static XDMWorkspace xdmAgentBuildCmdSyncHeader(XDMWorkspace xDMWorkspace) {
        XDMParserSyncheader xDMParserSyncheader;
        if (xDMWorkspace.syncHeader == null) {
            xDMWorkspace.syncHeader = new XDMParserSyncheader();
            xDMParserSyncheader = xDMWorkspace.syncHeader;
            xDMWorkspace.m_szTargetURI = xDMWorkspace.m_szHostname;
            xDMParserSyncheader.m_szVerdtd = "1.2";
            xDMParserSyncheader.m_szVerproto = XDMInterface.VERPROTO_1_2;
            xDMParserSyncheader.m_szSessionId = xDMWorkspace.m_szSessionID;
            xDMParserSyncheader.msgid = xDMWorkspace.msgID;
            xDMParserSyncheader.m_szTarget = xDMWorkspace.m_szTargetURI;
            xDMParserSyncheader.m_szSource = xDMWorkspace.m_szSourceURI;
            xDMParserSyncheader.m_szLocname = xDMWorkspace.m_szUserName;
            XDMParserMeta xDMParserMeta = new XDMParserMeta();
            xDMParserMeta.maxmsgsize = xDMWorkspace.maxMsgSize;
            xDMParserMeta.maxobjsize = xDMWorkspace.maxObjSize;
            xDMParserSyncheader.meta = xDMParserMeta;
        } else {
            xDMParserSyncheader = xDMWorkspace.syncHeader;
            xDMParserSyncheader.msgid = xDMWorkspace.msgID;
            if (xDMWorkspace.m_szTargetURI.compareTo(xDMParserSyncheader.m_szTarget) != 0) {
                xDMParserSyncheader.m_szTarget = xDMWorkspace.m_szTargetURI;
                xdmAgentBuildCmdParseTargetURI(xDMWorkspace);
            }
        }
        XDMParserCred xDMParserCred = null;
        if (xDMParserSyncheader.cred != null) {
            xDMParserSyncheader.cred = null;
        }
        if (!((xDMWorkspace.authState == 1 || xDMWorkspace.authState == 0) && xDMWorkspace.serverAuthState == 1)) {
            if (!TextUtils.isEmpty(XDMAuth.xdmAuthCredType2String(xDMWorkspace.credType))) {
                if (!((xDMWorkspace.m_szUserName.length() == 0 && xDMWorkspace.m_szClientPW.length() == 0) || xDMWorkspace.credType == 2)) {
                    Log.H("1: " + GeneralUtil.maskDeviceId(xDMWorkspace.m_szUserName));
                    Log.H("2: " + xDMWorkspace.m_szClientPW);
                    XDMParserMeta xDMParserMeta2 = new XDMParserMeta();
                    xDMParserMeta2.m_szType = XDMAuth.xdmAuthCredType2String(xDMWorkspace.credType);
                    xDMParserMeta2.m_szFormat = "b64";
                    xDMParserCred = new XDMParserCred();
                    xDMParserCred.meta = xDMParserMeta2;
                    xDMParserCred.m_szData = XDMAuth.xdmAuthMakeDigest(xDMWorkspace.credType, xDMWorkspace.m_szUserName, xDMWorkspace.m_szClientPW, xDMWorkspace.nextNonce, xDMWorkspace.nextNonce.length, null, 0);
                    if (!TextUtils.isEmpty(xDMParserCred.m_szData)) {
                        Log.H("cred data = " + xDMParserCred.m_szData + "credType = " + xDMWorkspace.credType);
                    }
                }
                xDMParserSyncheader.cred = xDMParserCred;
            } else {
                xDMParserSyncheader.cred = null;
            }
        }
        return xDMWorkspace;
    }

    public static XDMParserAlert xdmAgentBuildCmdAlert(XDMWorkspace xDMWorkspace, String str) {
        XDMParserAlert xDMParserAlert = new XDMParserAlert();
        xDMParserAlert.cmdid = xdmAgentBuildCmdGetCmdID(xDMWorkspace);
        xDMParserAlert.m_szData = str;
        return xDMParserAlert;
    }

    public static XDMParserAlert xdmAgentBuildCmdGenericAlert(XDMWorkspace xDMWorkspace, String str) {
        XDMParserAlert xDMParserAlert = new XDMParserAlert();
        xDMParserAlert.cmdid = xdmAgentBuildCmdGetCmdID(xDMWorkspace);
        xDMParserAlert.m_szData = str;
        XDMParserItem xDMParserItem = new XDMParserItem();
        xDMParserItem.meta = new XDMParserMeta();
        xDMParserItem.data = new XDMParserPcdata();
        xDMParserItem.m_szSource = XDBFumoAdp.xdbGetFUMOUpdateReportURI();
        int xdbGetFUMOInitiatedType = XDBFumoAdp.xdbGetFUMOInitiatedType();
        if (xdbGetFUMOInitiatedType == 1) {
            xDMParserItem.meta.m_szFormat = "chr";
            xDMParserItem.meta.m_szType = XDMInterface.ALERT_TYPE_USER_INIT;
            Log.I("ALERT_TYPE_USER_INIT");
        } else if (xdbGetFUMOInitiatedType == 2) {
            xDMParserItem.meta.m_szFormat = "chr";
            xDMParserItem.meta.m_szType = XDMInterface.ALERT_TYPE_DEV_INIT;
            Log.I("ALERT_TYPE_DEV_INIT");
        } else {
            Log.I("Init no flag");
        }
        xDMParserItem.data.data = "0".toCharArray();
        xDMParserItem.data.type = 0;
        xDMParserAlert.itemlist = XDMList.xdmListAppend(null, null, xDMParserItem);
        return xDMParserAlert;
    }

    public static XDMParserAlert xdmAgentBuildCmdGenericAlertReport(XDMWorkspace xDMWorkspace, String str) {
        XDMParserAlert xDMParserAlert = new XDMParserAlert();
        xDMParserAlert.cmdid = xdmAgentBuildCmdGetCmdID(xDMWorkspace);
        xDMParserAlert.m_szData = str;
        String xdbGetFUMOCorrelator = XDBFumoAdp.xdbGetFUMOCorrelator();
        if (!TextUtils.isEmpty(xdbGetFUMOCorrelator) && xdbGetFUMOCorrelator.length() != 0) {
            xDMParserAlert.m_szCorrelator = xdbGetFUMOCorrelator;
        }
        XDMParserItem xDMParserItem = new XDMParserItem();
        xDMParserItem.data = new XDMParserPcdata();
        xDMParserItem.data.type = 0;
        int xdbGetDmAgentType = XDBAgentAdp.xdbGetDmAgentType();
        if (xdbGetDmAgentType == 1) {
            xDMParserItem.m_szSource = XDBFumoAdp.xdbGetFUMOUpdateReportURI();
            xDMParserItem.meta = new XDMParserMeta();
            if (TextUtils.isEmpty(xDMParserItem.m_szSource)) {
                Log.E("Item.source is null");
            } else if (xDMParserItem.m_szSource.contains("/Update")) {
                xDMParserItem.meta.m_szType = XDMInterface.ALERT_TYPE_UPDATE_REPORT;
            } else if (xDMParserItem.m_szSource.contains("/DownloadAndUpdate")) {
                xDMParserItem.meta.m_szType = XDMInterface.ALERT_TYPE_DOWNLOAD_AND_UPDATE_REPORT;
            } else if (xDMParserItem.m_szSource.contains("/Download")) {
                xDMParserItem.meta.m_szType = XDMInterface.ALERT_TYPE_DOWNLOAD_REPORT;
            }
            String xdbGetFUMOResultCode = XDBFumoAdp.xdbGetFUMOResultCode();
            if (TextUtils.isEmpty(xdbGetFUMOResultCode)) {
                xDMParserItem.data.data = XFOTAInterface.XFOTA_GENERIC_CLIENT_ERROR.toCharArray();
            } else if (xdbGetFUMOResultCode.equals("0")) {
                xDMParserItem.data.data = XFOTAInterface.XFOTA_GENERIC_CLIENT_ERROR.toCharArray();
            } else {
                xDMParserItem.data.data = xdbGetFUMOResultCode.toCharArray();
            }
        } else {
            Log.E("nAgentType : " + xdbGetDmAgentType);
        }
        xDMParserAlert.itemlist = XDMList.xdmListAppend(null, null, xDMParserItem);
        return xDMParserAlert;
    }

    public static XDMParserReplace xdmAgentBuildCmdReplace(XDMWorkspace xDMWorkspace, XDMLinkedList xDMLinkedList) {
        XDMParserReplace xDMParserReplace = new XDMParserReplace();
        xDMParserReplace.cmdid = xdmAgentBuildCmdGetCmdID(xDMWorkspace);
        XDMLinkedList.xdmListSetCurrentObj(xDMLinkedList, 0);
        XDMParserItem xDMParserItem = (XDMParserItem) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
        XDMList xDMList = null;
        while (xDMParserItem != null) {
            XDMParserItem xDMParserItem2 = new XDMParserItem();
            xdmAgentDataStDuplItem(xDMParserItem2, xDMParserItem);
            if (xDMList == null) {
                xDMList = XDMList.xdmListAppend(xDMList, null, xDMParserItem2);
            } else {
                XDMList.xdmListAppend(xDMList, null, xDMParserItem2);
            }
            xDMParserItem = (XDMParserItem) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
        }
        xDMParserReplace.itemlist = xDMList;
        return xDMParserReplace;
    }

    public static XDMParserStatus xdmAgentBuildCmdStatus(XDMWorkspace xDMWorkspace, int i, String str, String str2, String str3, String str4) {
        XDMParserStatus xDMParserStatus = new XDMParserStatus();
        xDMParserStatus.cmdid = xdmAgentBuildCmdGetCmdID(xDMWorkspace);
        xDMParserStatus.m_szMsgRef = xDMWorkspace.m_szMsgRef;
        xDMParserStatus.m_szCmdRef = String.valueOf(i);
        xDMParserStatus.m_szCmd = str;
        xDMParserStatus.m_szData = str4;
        if (!TextUtils.isEmpty(str3)) {
            xDMParserStatus.targetref = XDMList.xdmListAppend(null, null, str3);
        } else if (xDMWorkspace.targetRefList != null) {
            XDMLinkedList.xdmListSetCurrentObj(xDMWorkspace.targetRefList, 0);
            XDMParserItem xDMParserItem = (XDMParserItem) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.targetRefList);
            XDMList xDMList = null;
            while (xDMParserItem != null) {
                XDMParserItem xDMParserItem2 = new XDMParserItem();
                XDMHandleCmd.xdmAgentDataStDuplItem(xDMParserItem2, xDMParserItem);
                if (xDMList == null) {
                    xDMList = XDMList.xdmListAppend(xDMList, null, xDMParserItem2);
                } else {
                    XDMList.xdmListAppend(xDMList, null, xDMParserItem2);
                }
                xDMParserItem = (XDMParserItem) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.targetRefList);
            }
            xDMParserStatus.targetref = xDMList;
        }
        if (!TextUtils.isEmpty(str2)) {
            xDMParserStatus.sourceref = XDMList.xdmListAppend(null, null, str2);
        } else if (xDMWorkspace.sourceRefList != null) {
            XDMLinkedList.xdmListSetCurrentObj(xDMWorkspace.sourceRefList, 0);
            XDMParserItem xDMParserItem3 = (XDMParserItem) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.sourceRefList);
            XDMList xDMList2 = null;
            while (xDMParserItem3 != null) {
                if (xDMList2 == null) {
                    xDMList2 = XDMList.xdmListAppend(xDMList2, null, xDMParserItem3);
                } else {
                    XDMList.xdmListAppend(xDMList2, null, xDMParserItem3);
                }
                xDMParserItem3 = (XDMParserItem) XDMLinkedList.xdmListGetNextObj(xDMWorkspace.sourceRefList);
            }
            xDMParserStatus.sourceref = xDMList2;
        }
        if (("407".compareTo(str4) == 0 || "401".compareTo(str4) == 0) && i == 0) {
            XDMParserMeta xDMParserMeta = new XDMParserMeta();
            xDMParserMeta.m_szFormat = "b64";
            if (xDMWorkspace.serverCredType == 0) {
                xDMParserMeta.m_szType = XDMInterface.CRED_TYPE_BASIC;
                Log.I("XDM_CRED_TYPE_BASIC");
            } else if (xDMWorkspace.serverCredType == 1) {
                xDMParserMeta.m_szType = XDMInterface.CRED_TYPE_MD5;
                xDMParserMeta.m_szNextNonce = XDMBase64.xdmBase64Encode(xDMWorkspace.serverNextNonce);
                Log.H("CRED_TYPE_MD5 serverNextNonce: " + new String(xDMWorkspace.serverNextNonce, Charset.defaultCharset()) + "Encoded server nonce " + xDMParserMeta.m_szNextNonce);
            } else if (xDMWorkspace.serverCredType == 2) {
                xDMParserMeta.m_szType = XDMInterface.CRED_TYPE_HMAC;
                xDMParserMeta.m_szNextNonce = XDMBase64.xdmBase64Encode(xDMWorkspace.serverNextNonce);
            }
            xDMParserStatus.chal = xDMParserMeta;
        }
        if (!xDMWorkspace.sendChal && XDMInterface.STATUS_AUTHENTICATIONACCEPTED.compareTo(str4) == 0 && i == 0 && xDMWorkspace.serverCredType == 1) {
            if (xDMParserStatus.chal != null) {
                XDMHandleCmd.xdmAgentDataStDeleteMeta(xDMParserStatus.chal);
                xDMParserStatus.chal = null;
            }
            XDMParserMeta xDMParserMeta2 = new XDMParserMeta();
            xDMParserMeta2.m_szFormat = "b64";
            xDMParserMeta2.m_szType = XDMInterface.CRED_TYPE_MD5;
            xDMParserMeta2.m_szNextNonce = XDMBase64.xdmBase64Encode(xDMWorkspace.serverNextNonce);
            XDBProfileAdp.xdbSetServerNonce(xDMParserMeta2.m_szNextNonce);
            Log.H("CRED_TYPE_MD5 serverNextNonce: " + new String(xDMWorkspace.serverNextNonce, Charset.defaultCharset()) + "Encoded server nonce " + xDMParserMeta2.m_szNextNonce);
            xDMWorkspace.sendChal = true;
            xDMParserStatus.chal = xDMParserMeta2;
        }
        if (xDMWorkspace.serverCredType == 2 && "200".compareTo(str4) == 0 && i == 0) {
            XDMParserMeta xDMParserMeta3 = new XDMParserMeta();
            xDMParserMeta3.m_szFormat = "b64";
            xDMParserMeta3.m_szType = XDMInterface.CRED_TYPE_HMAC;
            xDMParserMeta3.m_szNextNonce = XDMBase64.xdmBase64Encode(xDMWorkspace.serverNextNonce);
            xDMParserStatus.chal = xDMParserMeta3;
        }
        return xDMParserStatus;
    }

    public static XDMParserResults xdmAgentBuildCmdDetailResults(XDMWorkspace xDMWorkspace, int i, String str, String str2, String str3, int i2, char[] cArr) {
        char[] cArr2 = new char[128];
        if (xDMWorkspace == null || TextUtils.isEmpty(str)) {
            Log.I("ws or source is null");
            return null;
        }
        XDMMem.xdmLibStrsplit(str.toCharArray(), '?', cArr2);
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMWorkspace.om, XDMMem.xdmLibCharToString(cArr2));
        if (xdmOmGetNodeProp == null) {
            Log.I("Result node is null");
            return null;
        }
        XDMParserResults xDMParserResults = new XDMParserResults();
        XDMParserItem xDMParserItem = new XDMParserItem();
        xDMParserResults.cmdid = xdmAgentBuildCmdGetCmdID(xDMWorkspace);
        xDMParserResults.m_szMsgRef = xDMWorkspace.m_szMsgRef;
        xDMParserResults.m_szCmdRef = String.valueOf(i);
        if (!TextUtils.isEmpty(str)) {
            xDMParserItem.m_szSource = str;
        }
        if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3) || i2 > 0) {
            XDMParserMeta xDMParserMeta = new XDMParserMeta();
            if (!TextUtils.isEmpty(str3)) {
                xDMParserMeta.m_szType = str3;
            }
            if (!TextUtils.isEmpty(str2)) {
                xDMParserMeta.m_szFormat = str2;
            }
            if (i2 > 0) {
                xDMParserMeta.size = i2;
            }
            xDMParserItem.meta = xDMParserMeta;
        }
        if (cArr == null) {
            xDMParserItem.data = null;
        } else if (xdmOmGetNodeProp.format == 2) {
            xDMParserItem.data = new XDMParserPcdata();
            xDMParserItem.data.type = 1;
            xDMParserItem.data.data = new char[i2];
            System.arraycopy(cArr, 0, xDMParserItem.data.data, 0, i2);
            xDMParserItem.data.size = i2;
        } else if (TextUtils.isEmpty(str2) || "bin".compareTo(str2) != 0) {
            xDMParserItem.data = xdmAgentDataStString2Pcdata(cArr);
        } else {
            xDMParserItem.data = new XDMParserPcdata();
            xDMParserItem.data.data = new char[i2];
            System.arraycopy(cArr, 0, xDMParserItem.data.data, 0, i2);
            xDMParserItem.data.size = i2;
        }
        xDMParserResults.itemlist = XDMList.xdmListAppend(null, null, xDMParserItem);
        return xDMParserResults;
    }

    private static void xdmAgentBuildCmdParseTargetURI(XDMWorkspace xDMWorkspace) {
        String str = xDMWorkspace.m_szTargetURI;
        try {
            if (str.substring(0, 5).compareTo(HttpNetworkInterface.XTP_NETWORK_TYPE_HTTPS) == 0) {
                String substring = str.substring(8);
                Log.H("target.substring " + substring);
                int indexOf = substring.indexOf(58);
                int indexOf2 = substring.indexOf(63);
                if (indexOf2 > 0) {
                    xDMWorkspace.m_szHostname = "https://" + substring.substring(0, indexOf2);
                } else {
                    xDMWorkspace.m_szHostname = "https://" + substring;
                }
                Log.H("ws.hostname=> " + xDMWorkspace.m_szHostname);
                try {
                    xDMWorkspace.port = Integer.parseInt(substring.substring(indexOf + 1, substring.indexOf(47)));
                } catch (NumberFormatException unused) {
                    xDMWorkspace.port = 443;
                }
                Log.H("ws.port" + xDMWorkspace.port);
                return;
            }
            String substring2 = str.substring(7);
            Log.H("target.substring " + substring2);
            int indexOf3 = substring2.indexOf(58);
            int indexOf4 = substring2.indexOf(63);
            if (indexOf4 > 0) {
                xDMWorkspace.m_szHostname = "http://" + substring2.substring(0, indexOf4);
            } else {
                xDMWorkspace.m_szHostname = "http://" + substring2;
            }
            Log.H("ws.hostname => " + xDMWorkspace.m_szHostname);
            try {
                xDMWorkspace.port = Integer.parseInt(substring2.substring(indexOf3 + 1, substring2.indexOf(47)));
            } catch (NumberFormatException unused2) {
                xDMWorkspace.port = 80;
            }
            Log.H("ws.port => " + xDMWorkspace.port);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }
}
