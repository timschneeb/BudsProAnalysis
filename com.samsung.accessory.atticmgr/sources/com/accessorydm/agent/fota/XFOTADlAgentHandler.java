package com.accessorydm.agent.fota;

import android.text.TextUtils;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.adapter.XDMCommonUtils;
import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.adapter.XDMInitAdapter;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBAdapter;
import com.accessorydm.db.file.XDBChecksum;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XNOTIInterface;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.tp.XTPAdapter;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.accessorydm.ui.dialog.XUIDialog;
import com.accessorydm.ui.handler.XDMServiceHandler;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

public class XFOTADlAgentHandler extends XFOTADlAgent implements XDMDefInterface, XNOTIInterface, XDBInterface {
    public static int xfotaDlAgentHdlrCheckDeltaPkgSize() {
        long xdbGetObjectSizeFUMO = XDBFumoAdp.xdbGetObjectSizeFUMO();
        Log.I("FirmwareObjectSize:" + xdbGetObjectSizeFUMO);
        if (XFOTADl.xfotaGetDeltaDownState() != 1 || XDBAdapter.xdbFileFreeSizeCheck(xdbGetObjectSizeFUMO * 2) == 0) {
            return 0;
        }
        Log.E("FFS Free Space NOT ENOUGH");
        XDBFumoAdp.xdbSetFUMODownloadResultCode(xfotaDlAgentGetReportStatus(1));
        XDBFumoAdp.xdbSetFUMOStatus(20);
        if (XTPAdapter.xtpAdpCheckURL(XDBFumoAdp.xdbGetDownloadAddrFUMO(), XDBFumoAdp.xdbGetStatusAddrFUMO()) == 2) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
        }
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
        return 2;
    }

    private static void xfotaDlAgentHdlrDownloadProgress() {
        int i;
        try {
            i = g_HttpDLAdapter.xtpReceivePackage(null, 1);
        } catch (Exception e) {
            Log.E(e.toString());
            i = -4;
        }
        if (i != 0) {
            switch (i) {
                case XTPInterface.XTP_RET_DL_SERVICE_UNAVAILABLE /*{ENCODED_INT: -13}*/:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_SERVICE_UNAVAILABLE);
                    return;
                case XTPInterface.XTP_RET_DL_REDIRECT /*{ENCODED_INT: -12}*/:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_REDIRECT);
                    return;
                case -11:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_FORBIDDEN);
                    return;
                case XTPInterface.XTP_RET_CONTENT_TYPE_FAIL /*{ENCODED_INT: -10}*/:
                    xfotaDlClearAndSetErrorForDownloadProgress(8, XUIEventInterface.DM_UIEVENT.XUI_DM_RECV_FAILED);
                    return;
                case -9:
                    xfotaDlClearAndSetErrorForDownloadProgress(1, XUIEventInterface.DM_UIEVENT.XUI_DM_RECV_FAILED);
                    return;
                case -8:
                    xfotaDlClearAndSetErrorForDownloadProgress(1, XUIEventInterface.DL_UIEVENT.XUI_DL_MEMORY_FULL);
                    return;
                case -7:
                default:
                    int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
                    if (xdbGetFUMOStatus != 230 && xdbGetFUMOStatus != 20) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_RECEIVEFAIL, null, null);
                        return;
                    }
                    return;
                case -6:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_HTTP_ERROR);
                    return;
            }
        } else {
            boolean booleanValue = XDBFumoAdp.xdbGetFUMODownloadMode().booleanValue();
            Log.I("nDownloadMode : " + booleanValue);
            int xfotaDlAgentGetHttpConStatus = xfotaDlAgentGetHttpConStatus();
            xfotaDlAgentHdlrDownloadProgressFumo(xfotaDlAgentGetHttpConStatus, xfotaDlAgentGetHttpConStatus != 0 ? xfotaDlAgentGetHttpContentRange(booleanValue) : "");
        }
    }

    private static void xfotaDlClearAndSetErrorForDownloadProgress(int i, Object obj) {
        XDB.xdbDeleteFile(XDB.xdbGetFileIdFirmwareData());
        XDBFumoAdp.xdbSetFUMODownloadResultCode(xfotaDlAgentGetReportStatus(i));
        XDBFumoAdp.xdbSetFUMOStatus(20);
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
        XDMEvent.XDMSetEvent(null, obj);
    }

    private static void xfotaDlAgentHdlrDownloadProgressFumo(int i, String str) {
        Log.I("");
        if (i == 0) {
            int xdbGetFileIdFirmwareData = XDB.xdbGetFileIdFirmwareData();
            if (!xfotaDlDeltaVerifyChecksum(XDB.xdbFileGetNameFromCallerID(xdbGetFileIdFirmwareData))) {
                XDB.xdbDeleteFile(xdbGetFileIdFirmwareData);
                XDBFumoAdp.xdbSetFUMODownloadResultCode(xfotaDlAgentGetReportStatus(4));
                XDBFumoAdp.xdbSetFUMOStatus(20);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                if (XDBFumoAdp.xdbGetUiMode() == 1) {
                    XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DL_INVALID_DELTA);
                    return;
                }
                return;
            }
            XDBFumoAdp.xdbSetFUMOStatus(40);
            if (TextUtils.isEmpty(XDBFumoAdp.xdbGetStatusAddrFUMO())) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                int xdbGetFUMOUpdateMechanism = XDBFumoAdp.xdbGetFUMOUpdateMechanism();
                if (xdbGetFUMOUpdateMechanism == 2) {
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_COMPLETE);
                } else if (xdbGetFUMOUpdateMechanism == 3) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
                } else {
                    Log.E("ERROR");
                }
            } else if (XTPAdapter.xtpAdpCheckURL(XDBFumoAdp.xdbGetDownloadAddrFUMO(), XDBFumoAdp.xdbGetStatusAddrFUMO()) == -5) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECTFAIL, null, null);
            } else {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
            }
        } else {
            int i2 = -3;
            if (i == 1) {
                try {
                    g_HttpDLAdapter.xtpAdpSetHttpObj(XDBFumoAdp.xdbGetDownloadAddrFUMO(), "", str, HttpNetworkInterface.XTP_HTTP_METHOD_GET, 1);
                    try {
                        i2 = g_HttpDLAdapter.xtpSendPackage(null, 0, 1);
                    } catch (Exception e) {
                        Log.E(e.toString());
                    }
                    if (i2 == 0) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONTINUE, null, null);
                    } else if (i2 == -2) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECTFAIL, null, null);
                    } else {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
                    }
                } catch (NullPointerException e2) {
                    Log.E(e2.toString());
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
                }
            } else {
                Log.E("delta download failed");
                XDBFumoAdp.xdbSetFUMOStatus(20);
                XDBFumoAdp.xdbSetFUMODownloadResultCode(xfotaDlAgentGetReportStatus(4));
                String xdbGetStatusAddrFUMO = XDBFumoAdp.xdbGetStatusAddrFUMO();
                String xfotaDlAgentGetReportStatus = xfotaDlAgentGetReportStatus(4);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                if (XDBFumoAdp.xdbGetUiMode() == 1) {
                    XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DL_INVALID_DELTA);
                }
                int xtpAdpCheckURL = XTPAdapter.xtpAdpCheckURL(XDBFumoAdp.xdbGetDownloadAddrFUMO(), xdbGetStatusAddrFUMO);
                if (xtpAdpCheckURL == 2) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                } else if (xtpAdpCheckURL == -5) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_RECEIVEFAIL, null, null);
                } else {
                    try {
                        g_HttpDLAdapter.xtpAdpSetHttpObj(xdbGetStatusAddrFUMO, "", str, HttpNetworkInterface.XTP_HTTP_METHOD_POST, 1);
                        try {
                            i2 = g_HttpDLAdapter.xtpSendPackage(xfotaDlAgentGetReportStatus.getBytes(Charset.defaultCharset()), xfotaDlAgentGetReportStatus.length(), 1);
                        } catch (Exception e3) {
                            Log.E(e3.toString());
                        }
                        if (i2 == 0) {
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONTINUE, null, null);
                        } else if (i2 == -2) {
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECTFAIL, null, null);
                        } else {
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
                        }
                    } catch (NullPointerException e4) {
                        Log.E(e4.toString());
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
                    }
                }
            }
        }
    }

    private static void xfotaDlAgentHdlrDownloadStart() {
        String str = "";
        Log.I(str);
        boolean booleanValue = XDBFumoAdp.xdbGetFUMODownloadMode().booleanValue();
        int xfotaDlAgentGetHttpConStatus = xfotaDlAgentGetHttpConStatus();
        if (xfotaDlAgentGetHttpConStatus != 0) {
            str = xfotaDlAgentGetHttpContentRange(booleanValue);
        }
        xfotaDlAgentHdlrDownloadStartFumo(xfotaDlAgentGetHttpConStatus, str);
    }

    private static void xfotaDlAgentHdlrDownloadStartFumo(int i, String str) {
        Log.I("");
        int i2 = -3;
        if (i == 0) {
            XDBFumoAdp.xdbSetFUMOStatus(40);
            String xdbGetStatusAddrFUMO = XDBFumoAdp.xdbGetStatusAddrFUMO();
            if (TextUtils.isEmpty(xdbGetStatusAddrFUMO)) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                int xdbGetFUMOUpdateMechanism = XDBFumoAdp.xdbGetFUMOUpdateMechanism();
                if (xdbGetFUMOUpdateMechanism == 2) {
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_COMPLETE);
                } else if (xdbGetFUMOUpdateMechanism == 3) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
                } else {
                    Log.E("ERROR.");
                }
            } else if (XTPAdapter.xtpAdpCheckURL(XDBFumoAdp.xdbGetDownloadAddrFUMO(), XDBFumoAdp.xdbGetStatusAddrFUMO()) == 2) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
            } else {
                try {
                    g_HttpDLAdapter.xtpAdpSetHttpObj(xdbGetStatusAddrFUMO, "", str, HttpNetworkInterface.XTP_HTTP_METHOD_POST, 1);
                    String xfotaDlAgentGetReportStatus = xfotaDlAgentGetReportStatus(0);
                    try {
                        i2 = g_HttpDLAdapter.xtpSendPackage(xfotaDlAgentGetReportStatus.getBytes(Charset.defaultCharset()), xfotaDlAgentGetReportStatus.length(), 1);
                    } catch (NullPointerException e) {
                        Log.E(e.toString());
                    }
                    xfotaDlAgentHdlrSendMessageByHttpSendOrReceiveDataResult(i2);
                } catch (NullPointerException e2) {
                    Log.E(e2.toString());
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
                }
            }
        } else if (i == 1) {
            try {
                g_HttpDLAdapter.xtpAdpSetHttpObj(XDBFumoAdp.xdbGetDownloadAddrFUMO(), "", str, HttpNetworkInterface.XTP_HTTP_METHOD_GET, 1);
                try {
                    i2 = g_HttpDLAdapter.xtpSendPackage(null, 0, 1);
                } catch (Exception e3) {
                    Log.E(e3.toString());
                }
                xfotaDlAgentHdlrSendMessageByHttpSendOrReceiveDataResult(i2);
                XDBFumoAdp.xdbSetFUMOStatus(30);
            } catch (NullPointerException e4) {
                Log.E(e4.toString());
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
            }
        } else {
            Log.E("What Problem");
            XDBFumoAdp.xdbSetFUMOStatus(20);
            XDBFumoAdp.xdbSetFUMODownloadResultCode(xfotaDlAgentGetReportStatus(4));
            String xdbGetStatusAddrFUMO2 = XDBFumoAdp.xdbGetStatusAddrFUMO();
            XDBFumoAdp.xdbSetFUMOInitiatedType(0);
            if (XDBFumoAdp.xdbGetUiMode() == 1) {
                XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DL_INVALID_DELTA);
            }
            if (XTPAdapter.xtpAdpCheckURL(XDBFumoAdp.xdbGetDownloadAddrFUMO(), xdbGetStatusAddrFUMO2) == 2) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                return;
            }
            String xfotaDlAgentGetReportStatus2 = xfotaDlAgentGetReportStatus(4);
            try {
                g_HttpDLAdapter.xtpAdpSetHttpObj(xdbGetStatusAddrFUMO2, "", str, HttpNetworkInterface.XTP_HTTP_METHOD_POST, 1);
                try {
                    i2 = g_HttpDLAdapter.xtpSendPackage(xfotaDlAgentGetReportStatus2.getBytes(Charset.defaultCharset()), xfotaDlAgentGetReportStatus2.length(), 1);
                } catch (Exception e5) {
                    Log.E(e5.toString());
                }
                xfotaDlAgentHdlrSendMessageByHttpSendOrReceiveDataResult(i2);
            } catch (NullPointerException e6) {
                Log.E(e6.toString());
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
            }
        }
    }

    private static void xfotaDlAgentHdlrDD() {
        int i;
        Log.I("");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            i = g_HttpDLAdapter.xtpReceivePackage(byteArrayOutputStream, 1);
        } catch (Exception e) {
            Log.E(e.toString());
            i = -4;
        }
        if (i == -6) {
            xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_HTTP_ERROR);
        } else if (i != 0) {
            switch (i) {
                case XTPInterface.XTP_RET_DL_SERVICE_UNAVAILABLE /*{ENCODED_INT: -13}*/:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_SERVICE_UNAVAILABLE);
                    return;
                case XTPInterface.XTP_RET_DL_REDIRECT /*{ENCODED_INT: -12}*/:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_REDIRECT);
                    return;
                case -11:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_FORBIDDEN);
                    return;
                default:
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_RECEIVEFAIL, null, null);
                    return;
            }
        } else {
            Log.I("DD check finish. nRet = [" + i + "]");
            if (xfotaDlAgentParserDescriptor(byteArrayOutputStream.toByteArray()) == 11) {
                XDBFumoAdp.xdbSetFUMOStatus(200);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                return;
            }
            XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_BAD_URL);
            XDMInitAdapter.xdmAccessoryDownloadFailedReport();
        }
    }

    private static void xfotaDlAgentHdlrDownloadTakeOver() {
        boolean booleanValue = XDBFumoAdp.xdbGetFUMODownloadMode().booleanValue();
        Log.I("bDownloadMode = " + booleanValue);
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        Log.I("fumo org status = " + xdbGetFUMOStatus);
        int xfotaDlAgentGetHttpConStatus = xfotaDlAgentGetHttpConStatus();
        xfotaDlAgentHdlrDownloadTakeOverFumo(xfotaDlAgentGetHttpConStatus, xfotaDlAgentGetHttpConStatus != 0 ? xfotaDlAgentGetHttpContentRange(booleanValue) : null);
    }

    private static void xfotaDlAgentHdlrDownloadTakeOverFumo(int i, String str) {
        Log.I("");
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        Log.I("fumo org status = " + xdbGetFUMOStatus);
        int i2 = -3;
        if (i == 0) {
            XDBFumoAdp.xdbSetFUMOStatus(40);
            String xdbGetStatusAddrFUMO = XDBFumoAdp.xdbGetStatusAddrFUMO();
            if (TextUtils.isEmpty(xdbGetStatusAddrFUMO)) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                int xdbGetFUMOUpdateMechanism = XDBFumoAdp.xdbGetFUMOUpdateMechanism();
                if (xdbGetFUMOUpdateMechanism == 2) {
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_COMPLETE);
                } else if (xdbGetFUMOUpdateMechanism == 3) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
                } else {
                    Log.E("ERROR.");
                }
            } else {
                if ((xdbGetFUMOStatus == 30 ? XTPAdapter.xtpAdpCheckURL(XDBFumoAdp.xdbGetDownloadAddrFUMO(), xdbGetStatusAddrFUMO) : 0) == 2) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                    return;
                }
                String xfotaDlAgentGetReportStatus = xfotaDlAgentGetReportStatus(0);
                try {
                    g_HttpDLAdapter.xtpAdpSetHttpObj(xdbGetStatusAddrFUMO, "", str, HttpNetworkInterface.XTP_HTTP_METHOD_POST, 1);
                    try {
                        i2 = g_HttpDLAdapter.xtpSendPackage(xfotaDlAgentGetReportStatus.getBytes(Charset.defaultCharset()), xfotaDlAgentGetReportStatus.length(), 1);
                    } catch (Exception e) {
                        Log.E(e.toString());
                    }
                    if (i2 == -6) {
                        xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_HTTP_ERROR);
                    } else if (i2 == -2) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECTFAIL, null, null);
                    } else if (i2 != 0) {
                        switch (i2) {
                            case XTPInterface.XTP_RET_DL_SERVICE_UNAVAILABLE /*{ENCODED_INT: -13}*/:
                                xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_SERVICE_UNAVAILABLE);
                                return;
                            case XTPInterface.XTP_RET_DL_REDIRECT /*{ENCODED_INT: -12}*/:
                                xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_REDIRECT);
                                return;
                            case -11:
                                xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_FORBIDDEN);
                                return;
                            default:
                                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
                                return;
                        }
                    } else {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONTINUE, null, null);
                    }
                } catch (NullPointerException e2) {
                    Log.E(e2.toString());
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
                }
            }
        } else if (i == 1) {
            try {
                g_HttpDLAdapter.xtpAdpSetHttpObj(XDBFumoAdp.xdbGetDownloadAddrFUMO(), "", str, HttpNetworkInterface.XTP_HTTP_METHOD_GET, 1);
                try {
                    i2 = g_HttpDLAdapter.xtpSendPackage(null, 0, 1);
                } catch (Exception e3) {
                    Log.E(e3.toString());
                }
                xfotaDlAgentHdlrSendMessageByHttpSendOrReceiveDataResult(i2);
                XDBFumoAdp.xdbSetFUMOStatus(30);
            } catch (NullPointerException e4) {
                Log.E(e4.toString());
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
            }
        } else {
            Log.I("XDL_STATE_DOWNLOAD_FAILED");
            XDBFumoAdp.xdbSetFUMOStatus(20);
            XDBFumoAdp.xdbSetFUMODownloadResultCode(xfotaDlAgentGetReportStatus(4));
            String xdbGetStatusAddrFUMO2 = XDBFumoAdp.xdbGetStatusAddrFUMO();
            XDBFumoAdp.xdbSetFUMOInitiatedType(0);
            if (XDBFumoAdp.xdbGetUiMode() == 1) {
                XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DL_INVALID_DELTA);
            }
            if (XTPAdapter.xtpAdpCheckURL(XDBFumoAdp.xdbGetDownloadAddrFUMO(), XDBFumoAdp.xdbGetStatusAddrFUMO()) == 2) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                return;
            }
            String xfotaDlAgentGetReportStatus2 = xfotaDlAgentGetReportStatus(4);
            try {
                g_HttpDLAdapter.xtpAdpSetHttpObj(xdbGetStatusAddrFUMO2, "", str, HttpNetworkInterface.XTP_HTTP_METHOD_POST, 1);
                try {
                    i2 = g_HttpDLAdapter.xtpSendPackage(xfotaDlAgentGetReportStatus2.getBytes(Charset.defaultCharset()), xfotaDlAgentGetReportStatus2.length(), 1);
                } catch (Exception e5) {
                    Log.E(e5.toString());
                }
                xfotaDlAgentHdlrSendMessageByHttpSendOrReceiveDataResult(i2);
            } catch (NullPointerException e6) {
                Log.E(e6.toString());
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
            }
        }
    }

    private static void xfotaDlAgentHdlrDownloadComplete() {
        int i;
        Log.I("");
        try {
            i = g_HttpDLAdapter.xtpReceivePackage(new ByteArrayOutputStream(), 1);
        } catch (Exception e) {
            Log.E(e.toString());
            i = -4;
        }
        if (i == -6) {
            xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_HTTP_ERROR);
        } else if (i != 0) {
            switch (i) {
                case XTPInterface.XTP_RET_DL_SERVICE_UNAVAILABLE /*{ENCODED_INT: -13}*/:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_SERVICE_UNAVAILABLE);
                    return;
                case XTPInterface.XTP_RET_DL_REDIRECT /*{ENCODED_INT: -12}*/:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_REDIRECT);
                    return;
                case -11:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_DL_FORBIDDEN);
                    return;
                default:
                    xfotaDlAbortMessage(XEventInterface.XEVENT_ABORT_HTTP_ERROR);
                    return;
            }
        } else {
            xfotaDlAgentHdlrDownloadCompleteFumo();
        }
    }

    private static void xfotaDlAgentHdlrDownloadCompleteFumo() {
        int i;
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        Log.I("1)nAgentStatus [" + xdbGetFUMOStatus + "]");
        if (xdbGetFUMOStatus == 230) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
            XDMInitAdapter.xdmAccessoryUserCancelReport();
        } else if (xdbGetFUMOStatus == 20) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
            XDBFumoAdp.xdbSetFUMOStatus(241);
            if (XTPAdapter.g_HttpObj[1].nHttpConnection == 1) {
                try {
                    i = g_HttpDLAdapter.xtpAdpOpen(1);
                } catch (Exception e) {
                    Log.E(e.toString());
                    i = -2;
                }
                if (i != 0) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                } else {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
                }
            } else {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
            }
        } else {
            int xdbGetFUMOUpdateMechanism = XDBFumoAdp.xdbGetFUMOUpdateMechanism();
            XDBFumoAdp.xdbSetFUMODownloadMode(true);
            if (xdbGetFUMOUpdateMechanism == 2) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_COMPLETE);
            } else if (xdbGetFUMOUpdateMechanism == 3) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                XDBFumoAdp.xdbSetFUMOStatus(40);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
            }
        }
    }

    public static void xfotaDlAgentHdlrStartOMADLAgent(XEventInterface.XEVENT xevent) {
        int i;
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        Log.I("xfotaDlAgentHdlrStartOMADLAgent [" + xevent + "] nAgentStatus[" + xdbGetFUMOStatus + "]");
        int i2 = AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT[xevent.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                Log.I("XEVENT_DL_CONTINUE");
                if (xdbGetFUMOStatus == 10) {
                    Log.I("XDL_STATE_IDLE_START");
                    xfotaDlAgentHdlrDD();
                } else if (xdbGetFUMOStatus == 20) {
                    Log.I("XDL_STATE_DOWNLOAD_IN_FAIL");
                    xfotaDlAgentHdlrDownloadComplete();
                } else if (xdbGetFUMOStatus == 30) {
                    xfotaDlAgentHdlrDownloadProgress();
                } else if (xdbGetFUMOStatus == 40) {
                    Log.I("XDL_STATE_DOWNLOAD_COMPLETE");
                    xfotaDlAgentHdlrDownloadComplete();
                } else if (xdbGetFUMOStatus == 230) {
                    Log.I("XDL_STATE_DOWNLOAD_IN_CANCEL");
                    xfotaDlAgentHdlrDownloadComplete();
                }
            } else if (i2 == 3) {
                Log.I("XEVENT_DL_USER_CANCEL_DOWNLOAD");
                g_HttpDLAdapter.xtpAdpClose(1);
                g_HttpDLAdapter.xtpAdpCloseNetWork(1);
                XDBFumoAdp.xdbSetFUMODownloadResultCode(xfotaDlAgentGetReportStatus(2));
                XDBFumoAdp.xdbSetFUMOStatus(XFOTAInterface.XDL_STATE_DOWNLOAD_IN_CANCEL);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
            } else if (i2 == 4) {
                Log.I("XEVENT_DL_DELTA_SIZE_ERROR_DOWNLOAD");
                g_HttpDLAdapter.xtpAdpClose(1);
                g_HttpDLAdapter.xtpAdpCloseNetWork(1);
                XDBFumoAdp.xdbSetFUMODownloadResultCode(xfotaDlAgentGetReportStatus(4));
                XDBFumoAdp.xdbSetFUMOStatus(20);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
            }
        } else if (xdbGetFUMOStatus != 10) {
            if (xdbGetFUMOStatus != 20) {
                if (xdbGetFUMOStatus == 30 || xdbGetFUMOStatus == 40) {
                    xfotaDlAgentHdlrDownloadTakeOver();
                    return;
                }
                if (xdbGetFUMOStatus != 50) {
                    if (xdbGetFUMOStatus == 200) {
                        XDBFumoAdp.xdbSetFUMODownloadConnType(XDMCommonUtils.xdmGetUsingBearer());
                        xfotaDlAgentHdlrDownloadStart();
                        return;
                    } else if (xdbGetFUMOStatus != 230) {
                        if (xdbGetFUMOStatus != 251) {
                            return;
                        }
                    }
                }
                g_HttpDLAdapter.xtpAdpClose(1);
                g_HttpDLAdapter.xtpAdpCloseNetWork(1);
                if (XDMDevinfAdapter.xdmDevAdpBatteryLifeCheck()) {
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
                    return;
                }
                return;
            }
            xfotaDlAgentDownloadFailed();
        } else {
            try {
                g_HttpDLAdapter.xtpAdpSetHttpObj(XDB.xdbGetServerUrl(1), "", "", HttpNetworkInterface.XTP_HTTP_METHOD_GET, 1);
                try {
                    i = g_HttpDLAdapter.xtpSendPackage(null, 0, 1);
                } catch (Exception e) {
                    Log.E(e.toString());
                    i = -3;
                }
                xfotaDlAgentHdlrSendMessageByHttpSendOrReceiveDataResult(i);
            } catch (NullPointerException e2) {
                Log.E(e2.toString());
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.accessorydm.agent.fota.XFOTADlAgentHandler$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT = new int[XEventInterface.XEVENT.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.accessorydm.interfaces.XEventInterface$XEVENT[] r0 = com.accessorydm.interfaces.XEventInterface.XEVENT.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.agent.fota.XFOTADlAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT = r0
                int[] r0 = com.accessorydm.agent.fota.XFOTADlAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.interfaces.XEventInterface$XEVENT r1 = com.accessorydm.interfaces.XEventInterface.XEVENT.XEVENT_DL_START     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.agent.fota.XFOTADlAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.interfaces.XEventInterface$XEVENT r1 = com.accessorydm.interfaces.XEventInterface.XEVENT.XEVENT_DL_CONTINUE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = com.accessorydm.agent.fota.XFOTADlAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.interfaces.XEventInterface$XEVENT r1 = com.accessorydm.interfaces.XEventInterface.XEVENT.XEVENT_DL_USER_CANCEL_DOWNLOAD     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = com.accessorydm.agent.fota.XFOTADlAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.accessorydm.interfaces.XEventInterface$XEVENT r1 = com.accessorydm.interfaces.XEventInterface.XEVENT.XEVENT_DL_DELTA_SIZE_ERROR_DOWNLOAD     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.fota.XFOTADlAgentHandler.AnonymousClass1.<clinit>():void");
        }
    }

    private static boolean xfotaDlDeltaVerifyChecksum(String str) {
        boolean z = true;
        if (XDMDmUtils.getInstance().XDM_VALIDATION_CHECK) {
            String checksum = XDBChecksum.getChecksum(str);
            if (TextUtils.isEmpty(checksum)) {
                Log.I("Checksum is Empty");
            } else {
                String xdbGetFUMODeltaHash = XDBFumoAdp.xdbGetFUMODeltaHash();
                if (TextUtils.isEmpty(xdbGetFUMODeltaHash) || !checksum.equals(xdbGetFUMODeltaHash)) {
                    Log.I("Hash not matches");
                } else {
                    Log.I("Hash matches");
                }
            }
            z = false;
        } else {
            Log.I("Skip Checksum check");
        }
        Log.I("Checksum Check : " + z);
        return z;
    }

    private static void xfotaDlAbortMessage(int i) {
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_ABORT, XDMMsg.xdmCreateAbortMessage(i, false), null);
    }

    private static void xfotaDlAgentHdlrSendMessageByHttpSendOrReceiveDataResult(int i) {
        if (i == -2) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECTFAIL, null, null);
        } else if (i != 0) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_SENDFAIL, null, null);
        } else {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONTINUE, null, null);
        }
    }
}
