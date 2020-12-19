package com.accessorydm.db.file;

import android.text.TextUtils;
import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.accessorydm.eng.core.XDMUic;
import com.accessorydm.eng.core.XDMUicResult;
import com.samsung.android.fotaprovider.log.Log;

public class XDBProfileListAdp {
    public static XDBProfileListInfo xdbGetProfileList() {
        try {
            return XDMDbSqlQuery.xdmDbFetchProfileListRow(1);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    public static void xdbSetProfileList(XDBProfileListInfo xDBProfileListInfo) {
        try {
            XDMDbSqlQuery.xdmDbUpdateProfileListRow(1, xDBProfileListInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static int xdbGetProfileIndex() {
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            if (xdbGetProfileList != null) {
                return xdbGetProfileList.Profileindex;
            }
            return 0;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void xdbSetProfileIndex(int i) {
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.Profileindex = i;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            Log.E(String.format("unable to write index.%s", e.toString()));
        }
        Log.H("Set Profile Index : " + i);
    }

    public static String[] xdbGetProfileName() {
        try {
            return xdbGetProfileList().ProfileName;
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    public static String xdbGetProfileName(int i) {
        try {
            return xdbGetProfileList().ProfileName[i];
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetProfileName(int i, String str) {
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.ProfileName[i] = str;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetNotiSessionID() {
        try {
            return xdbGetProfileList().m_szSessionID;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetNotiSessionID(String str) {
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.m_szSessionID = str;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetNotiEvent() {
        try {
            return xdbGetProfileList().nNotiEvent;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void xdbSetNotiEvent(int i) {
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.nNotiEvent = i;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetNotiReSyncMode() {
        try {
            Integer valueOf = Integer.valueOf(xdbGetProfileList().nNotiReSyncMode);
            if (valueOf != null) {
                return valueOf.intValue();
            }
            return 0;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static Boolean xdbSetNotiReSyncMode(int i) {
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.nNotiReSyncMode = i;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            Log.E(e.toString());
        }
        return true;
    }

    public static String xdbGetDDFParserNodeIndex() {
        try {
            return String.valueOf(xdbGetProfileList().nDDFParserNodeIndex);
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetDDFParserNodeIndex(int i) {
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.nDDFParserNodeIndex = i;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static XDBSessionSaveInfo xdbGetSessionSaveInfo() {
        XDBSessionSaveInfo xDBSessionSaveInfo = new XDBSessionSaveInfo();
        try {
            return xdbGetProfileList().NotiResumeState;
        } catch (Exception e) {
            Log.E(e.toString());
            return xDBSessionSaveInfo;
        }
    }

    public static XDBSessionSaveInfo xdbGetSessionSaveStatus() {
        XDBSessionSaveInfo xDBSessionSaveInfo = new XDBSessionSaveInfo();
        try {
            xDBSessionSaveInfo = xdbGetProfileList().NotiResumeState;
        } catch (Exception e) {
            Log.E(e.toString());
        }
        if (xDBSessionSaveInfo == null) {
            Log.E("Read Error");
            return null;
        }
        Log.I("nSessionSaveState [" + xDBSessionSaveInfo.nSessionSaveState + "], nNotiUiEvent [" + xDBSessionSaveInfo.nNotiUiEvent + "]");
        return xDBSessionSaveInfo;
    }

    public static boolean xdbSetSessionSaveStatus(int i, int i2, int i3) {
        boolean z;
        XDBSessionSaveInfo xDBSessionSaveInfo = new XDBSessionSaveInfo();
        Log.I("");
        Log.I("nSessionSaveState [" + i + "], nNotiUiEvent [" + i2 + "], nNotiRetrycount [" + i3 + "]");
        xDBSessionSaveInfo.nSessionSaveState = i;
        xDBSessionSaveInfo.nNotiUiEvent = i2;
        xDBSessionSaveInfo.nNotiRetryCount = i3;
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.NotiResumeState = xDBSessionSaveInfo;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            try {
                Log.E(e.toString());
            } catch (Exception e2) {
                Log.E(e2.toString());
                z = false;
            }
        }
        z = true;
        Log.I("return value :" + z);
        return z;
    }

    public static void xdbClearUicResultKeepFlag() {
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.tUicResultKeep.eStatus = 0;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static XDMUicResult xdbUicGetResultKeep() {
        XDMUicResult xdmUicCreateResult = XDMUic.xdmUicCreateResult();
        if (xdmUicCreateResult == null) {
            return null;
        }
        XDBUICResultKeepInfo xDBUICResultKeepInfo = new XDBUICResultKeepInfo();
        try {
            xDBUICResultKeepInfo = xdbGetProfileList().tUicResultKeep;
        } catch (Exception e) {
            Log.E(e.toString());
            XDB.xdbSqlFailAbort();
        }
        xdmUicCreateResult.appId = xDBUICResultKeepInfo.appId;
        xdmUicCreateResult.UICType = xDBUICResultKeepInfo.UICType;
        xdmUicCreateResult.result = xDBUICResultKeepInfo.result;
        xdmUicCreateResult.MenuNumbers = xDBUICResultKeepInfo.number;
        if (xDBUICResultKeepInfo.m_szText.length() > 0) {
            xdmUicCreateResult.text.len = xDBUICResultKeepInfo.nLen;
            xdmUicCreateResult.text.size = xDBUICResultKeepInfo.nSize;
            xdmUicCreateResult.text.text = xDBUICResultKeepInfo.m_szText;
        }
        return xdmUicCreateResult;
    }

    public static XDMUicResult xdbUicSetResultKeep(XDMUicResult xDMUicResult, int i) {
        Log.I("xdbUicSetResultKeep pUicResultKeepFlag [" + i + "]");
        if (xDMUicResult == null) {
            Log.E("ptUicResult is NULL!");
            return xDMUicResult;
        }
        XDBUICResultKeepInfo xDBUICResultKeepInfo = new XDBUICResultKeepInfo();
        xDBUICResultKeepInfo.eStatus = i;
        xDBUICResultKeepInfo.appId = xDMUicResult.appId;
        xDBUICResultKeepInfo.UICType = xDMUicResult.UICType;
        xDBUICResultKeepInfo.result = xDMUicResult.result;
        xDBUICResultKeepInfo.number = xDMUicResult.MenuNumbers;
        if (xDMUicResult.text != null) {
            xDBUICResultKeepInfo.nLen = xDMUicResult.text.len;
            xDBUICResultKeepInfo.nSize = xDMUicResult.text.size;
            if (!TextUtils.isEmpty(xDMUicResult.text.text)) {
                xDBUICResultKeepInfo.m_szText = xDMUicResult.text.text;
            }
        }
        try {
            XDBProfileListInfo xdbGetProfileList = xdbGetProfileList();
            xdbGetProfileList.tUicResultKeep = xDBUICResultKeepInfo;
            xdbSetProfileList(xdbGetProfileList);
        } catch (Exception e) {
            Log.E(e.toString());
            XDB.xdbSqlFailAbort();
        }
        return xDMUicResult;
    }
}
