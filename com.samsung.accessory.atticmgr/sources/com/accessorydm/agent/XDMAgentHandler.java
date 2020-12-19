package com.accessorydm.agent;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBAgentAdp;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBProfileAdp;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.eng.core.XDMWorkspace;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.FotaProviderUtil;
import java.io.ByteArrayOutputStream;

public class XDMAgentHandler extends XDMAgent {
    private void xdmAgnetHdlrContinueSessionDmStart() {
        int i;
        Log.I("");
        if (xdmAgentGetWorkSpace() != null && XDBProfileAdp.xdbGetChangedProtocol()) {
            Log.I("xdbGetChangedProtocol, do not create new package");
        } else if (xdmAgentStartSession() != 0) {
            xdmAgentHdlrAbortSession(9);
            return;
        } else if (xdmAgentCreatePackage() != 0) {
            xdmAgentHdlrAbortSession(9);
            return;
        }
        try {
            i = this.m_HttpDMAdapter.xtpAdpOpen(0);
        } catch (Exception e) {
            Log.E(e.toString());
            i = -2;
        }
        if (i != 0) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
        } else if (xdmAgentSendPackage() != 2) {
        } else {
            if (XDMAgent.xdmAgentCheckChangeProtocolCount()) {
                XDBProfileAdp.xdbSetChangedProtocol(true);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_FINISH, null, null);
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
                return;
            }
            xdmAgentHdlrAbortChangeProtocol();
        }
    }

    private void xdmAgnetHdlrContinueSessionFumoStart() {
        int i;
        Log.I("");
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        Log.I("nStatus [" + xdbGetFUMOStatus + "]");
        int i2 = -2;
        if (xdbGetFUMOStatus == 100 || xdbGetFUMOStatus == 80 || xdbGetFUMOStatus == 65 || xdbGetFUMOStatus == 240 || xdbGetFUMOStatus == 241) {
            XDB.xdbDeleteFile(XDB.xdbGetFileIdFirmwareData());
            i = xdmAgentStartGeneralAlert();
            if (i == -1) {
                xdmAgentHdlrAbortSession(9);
                return;
            } else if (i == -2) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                return;
            }
        } else if (XDBFumoAdp.xdbGetFUMOUpdateMechanism() == 3) {
            i = xdmAgentStartGeneralAlert();
            if (i == -1) {
                xdmAgentHdlrAbortSession(9);
                return;
            } else if (i == -2) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                return;
            }
        } else if (xdmAgentStartSession() != 0) {
            xdmAgentHdlrAbortSession(9);
            return;
        } else if (xdmAgentCreatePackage() != 0) {
            xdmAgentHdlrAbortSession(9);
            return;
        } else {
            try {
                i2 = this.m_HttpDMAdapter.xtpAdpOpen(0);
            } catch (Exception e) {
                Log.E(e.toString());
            }
            if (i2 != 0) {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                return;
            }
            i = xdmAgentSendPackage();
        }
        if (i != 2) {
            return;
        }
        if (XDMAgent.xdmAgentCheckChangeProtocolCount()) {
            XDBProfileAdp.xdbSetChangedProtocol(true);
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_FINISH, null, null);
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
            return;
        }
        xdmAgentHdlrAbortChangeProtocol();
    }

    public void xdmAgentHdlrContinueSession(XEventInterface.XEVENT xevent) {
        int i;
        Log.I("");
        XDMWorkspace xdmAgentGetWorkSpace = xdmAgentGetWorkSpace();
        if (xdmAgentGetWorkSpace == null) {
            Log.I("!ws WARNING");
        }
        int i2 = AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT[xevent.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                Log.I("XEVENT_DM_CONTINUE");
                if (xdmAgentGetWorkSpace == null) {
                    Log.I("ws XEVENT_DM_CONTINUE WARNING");
                    return;
                }
                xdmAgentGetWorkSpace.procState = XDMInterface.XDMProcessingState.XDM_PROC_NONE;
                try {
                    if (xdmAgentGetWorkSpace.buf == null) {
                        xdmAgentGetWorkSpace.buf = new ByteArrayOutputStream();
                    }
                    i = this.m_HttpDMAdapter.xtpReceivePackage(xdmAgentGetWorkSpace.buf, 0);
                } catch (Exception e) {
                    Log.E(e.toString());
                    i = -4;
                }
                if (xdmAgentGetWorkSpace.buf == null || xdmAgentGetWorkSpace.buf.size() == 0) {
                    xdmAgentHdlrAbortSession(9);
                    return;
                }
                xdmAgentGetWorkSpace.recvHmacData = this.m_HttpDMAdapter.xtpAdpGetCurHMACData(xdmAgentGetWorkSpace.appId);
                if (i == -6) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_ABORT, XDMMsg.xdmCreateAbortMessage(XEventInterface.XEVENT_ABORT_HTTP_ERROR, false), null);
                } else if (i != 0) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_RECEIVEFAIL, null, null);
                } else {
                    int xdmAgentStartMgmtSession = xdmAgentStartMgmtSession();
                    if (xdmAgentStartMgmtSession == -1) {
                        Log.I("XDM_RET_FAILED");
                        xdmAgentHdlrAbortSession(9);
                    } else if (xdmAgentStartMgmtSession == -5) {
                        Log.I("XDM_RET_AUTH_MAX_ERROR");
                        xdmAgentHdlrAbortSession(3);
                    } else if (xdmAgentStartMgmtSession == 1) {
                        Log.I("XDM_RET_ALERT_SESSION_ABORT");
                        xdmAgentHdlrDestroySession();
                    } else if (xdmAgentStartMgmtSession == 3) {
                        Log.I("XDM_RET_FINISH");
                        Log.I("no action command finish session");
                        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
                        if (xdbGetFUMOStatus == 100 || xdbGetFUMOStatus == 80 || xdbGetFUMOStatus == 65 || xdbGetFUMOStatus == 240 || xdbGetFUMOStatus == 241) {
                            Log.I("FUMO nStatus : " + xdbGetFUMOStatus);
                            XDBAgentAdp.xdbSetDmAgentType(0);
                            XDBFumoAdp.xdbSetFUMOStatus(0);
                            XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                        }
                        xdmAgentHdlrDestroySession();
                    } else if (xdmAgentStartMgmtSession == 5) {
                        Log.I("XDM_RET_EXEC_ALTERNATIVE");
                        Log.I("Connect to the Contents Server");
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_FINISH, null, null);
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                    } else if (xdmAgentStartMgmtSession == 8) {
                        xdmAgentHdlrDestroySession();
                        XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_COMPLETE);
                    } else if (xdmAgentStartMgmtSession == 6) {
                        Log.I("XDM_RET_EXEC_ALTERNATIVE_DOWNLOAD");
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_FINISH, null, null);
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                    } else if (xdmAgentStartMgmtSession == 7) {
                        Log.I("XDM_RET_EXEC_ALTERNATIVE_UPDATE");
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_FINISH, null, null);
                        XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_COMPLETE);
                    } else if (xdmAgentStartMgmtSession == 2) {
                        Log.I("XDM_RET_CHANGED_PROFILE");
                        if (XDMAgent.xdmAgentCheckChangeProtocolCount()) {
                            XDBProfileAdp.xdbSetChangedProtocol(true);
                            XDMDmUtils.getInstance().g_Task.xdmAgentDmTpClose();
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
                            return;
                        }
                        xdmAgentHdlrAbortChangeProtocol();
                    } else if (xdmAgentStartMgmtSession == -2) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                    }
                }
            } else if (i2 == 3) {
                int xdbGetFUMOStatus2 = XDBFumoAdp.xdbGetFUMOStatus();
                Log.I("nStatus [" + xdbGetFUMOStatus2 + "]");
                if (xdbGetFUMOStatus2 == 0) {
                    if (xdmAgentGetPendingStatus()) {
                        Log.I("XDM_TASK_RETRY");
                        xdmAgentClose();
                        return;
                    }
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_FINISH);
                    if (XDBProfileAdp.xdbGetChangedProtocol()) {
                        XDBProfileAdp.xdbSetBackUpServerUrl();
                    }
                    XDMAgent.xdmAgentResetChangeProtocolCount();
                    XDBProfileListAdp.xdbSetNotiEvent(0);
                    FotaProviderUtil.sendLastCheckedDate();
                } else if (xdbGetFUMOStatus2 == 10) {
                    FotaProviderUtil.sendLastCheckedDate();
                    Log.I("Case XEVENT_DM_FINISH & XDL_STATE_IDLE_START");
                } else {
                    Log.I("Case XEVENT_DM_FINISH BUT not FINISH STATUS");
                }
                xdmAgentClose();
                if (xdbGetFUMOStatus2 == 0) {
                    XDMDebug.xdmSetSessionRuning(false);
                }
            }
        } else if (XDBAgentAdp.xdbGetDmAgentType() == 1) {
            xdmAgnetHdlrContinueSessionFumoStart();
        } else {
            xdmAgnetHdlrContinueSessionDmStart();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.accessorydm.agent.XDMAgentHandler$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT = new int[XEventInterface.XEVENT.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.accessorydm.interfaces.XEventInterface$XEVENT[] r0 = com.accessorydm.interfaces.XEventInterface.XEVENT.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.agent.XDMAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT = r0
                int[] r0 = com.accessorydm.agent.XDMAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.interfaces.XEventInterface$XEVENT r1 = com.accessorydm.interfaces.XEventInterface.XEVENT.XEVENT_DM_START     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.agent.XDMAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.interfaces.XEventInterface$XEVENT r1 = com.accessorydm.interfaces.XEventInterface.XEVENT.XEVENT_DM_CONTINUE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = com.accessorydm.agent.XDMAgentHandler.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XEventInterface$XEVENT     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.interfaces.XEventInterface$XEVENT r1 = com.accessorydm.interfaces.XEventInterface.XEVENT.XEVENT_DM_FINISH     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.agent.XDMAgentHandler.AnonymousClass1.<clinit>():void");
        }
    }

    private void xdmAgentHdlrDestroySession() {
        xdmAgentSetSyncMode(2);
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_FINISH, null, null);
    }

    private void xdmAgentHdlrAbortSession(int i) {
        Log.I("AbortReason=[" + i + "]");
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_ABORT, XDMMsg.xdmCreateAbortMessage(XEventInterface.XEVENT_ABORT_SYNCDM_ERROR, false), null);
    }

    private void xdmAgentHdlrAbortChangeProtocol() {
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_ABORT, XDMMsg.xdmCreateAbortMessage(XEventInterface.XEVENT_ABORT_DM_CHANGEPROTOCOL_MAXCOUNT, false), null);
    }
}
