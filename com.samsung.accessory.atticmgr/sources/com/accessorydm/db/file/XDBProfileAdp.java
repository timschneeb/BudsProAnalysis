package com.accessorydm.db.file;

import android.text.TextUtils;
import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.samsung.android.fotaprovider.log.Log;

public class XDBProfileAdp {
    public static XDBProfileInfo xdbGetProfileInfo() {
        try {
            return XDMDbSqlQuery.xdmDbFetchProfileRow((long) (XDBProfileListAdp.xdbGetProfileIndex() + 1));
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    public static XDBProfileInfo xdbGetProfileInfo(int i) {
        try {
            return XDMDbSqlQuery.xdmDbFetchProfileRow((long) (i + 1));
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return null;
    }

    public static void xdbSetProfileInfo(XDBProfileInfo xDBProfileInfo) {
        try {
            XDMDbSqlQuery.xdmDbUpdateProfileRow((long) (XDBProfileListAdp.xdbGetProfileIndex() + 1), xDBProfileInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static void xdbSetProfileInfo(int i, XDBProfileInfo xDBProfileInfo) {
        try {
            XDMDbSqlQuery.xdmDbUpdateProfileRow((long) (i + 1), xDBProfileInfo);
        } catch (XDBUserDBException e) {
            Log.E(e.toString());
            e.failHandling();
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
    }

    public static void xdbSetServerProtocol(String str) {
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.Protocol = str;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetServerProtocol() {
        try {
            return xdbGetProfileInfo().Protocol;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static String xdbGetServerUrl() {
        try {
            return xdbGetProfileInfo().ServerUrl;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetServerUrl(String str) {
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.ServerUrl = str;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetServerAddress() {
        try {
            return xdbGetProfileInfo().ServerIP;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetServerAddress(String str) {
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.ServerIP = str;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static int xdbGetServerPort() {
        try {
            return xdbGetProfileInfo().ServerPort;
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static void xdbSetServerPort(int i) {
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.ServerPort = i;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static void xdbSetChangedProtocol(boolean z) {
        Log.H("xdbSetChangedProtocol : " + z);
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.bChangedProtocol = z;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static boolean xdbGetChangedProtocol() {
        try {
            return xdbGetProfileInfo().bChangedProtocol;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public static void xdbSetBackUpServerUrl() {
        Log.I("");
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.ServerUrl = xdbGetProfileInfo.ServerUrl_Org;
            xdbGetProfileInfo.ServerIP = xdbGetProfileInfo.ServerIP_Org;
            xdbGetProfileInfo.ServerPort = xdbGetProfileInfo.ServerPort_Org;
            xdbGetProfileInfo.Protocol = xdbGetProfileInfo.Protocol_Org;
            xdbGetProfileInfo.bChangedProtocol = false;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static void xdbSetAuthType(int i) {
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.AuthType = i;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static void xdbSetServerAuthType(int i) {
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.nServerAuthType = i;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetDeviceId() {
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            if (xdbGetProfileInfo != null) {
                return xdbGetProfileInfo.UserName;
            }
            return "";
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static String xdbGetClientPassword() {
        try {
            return xdbGetProfileInfo().Password;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static String xdbGetServerID() {
        try {
            return xdbGetProfileInfo().ServerID;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static String xdbGetServerPassword() {
        try {
            return xdbGetProfileInfo().ServerPwd;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetClientNonce(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
                xdbGetProfileInfo.ClientNonce = str;
                xdbSetProfileInfo(xdbGetProfileInfo);
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
    }

    public static String xdbGetServerNonce() {
        try {
            return xdbGetProfileInfo().ServerNonce;
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static void xdbSetServerNonce(String str) {
        try {
            XDBProfileInfo xdbGetProfileInfo = xdbGetProfileInfo();
            xdbGetProfileInfo.ServerNonce = str;
            xdbSetProfileInfo(xdbGetProfileInfo);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static String xdbGetProfileName(int i) {
        try {
            return xdbGetProfileInfo(i).ProfileName;
        } catch (Exception e) {
            Log.E(e.toString());
            return "server1";
        }
    }
}
