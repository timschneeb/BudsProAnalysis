package com.accessorydm.agent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.XDMSecReceiverApiCall;
import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.adapter.XDMInitAdapter;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.agent.fota.XFOTADlAgent;
import com.accessorydm.agent.fota.XFOTADlAgentHandler;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBAgentAdp;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBFumoInfo;
import com.accessorydm.db.file.XDBNoti;
import com.accessorydm.db.file.XDBNotiInfo;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.db.file.XDBProfileAdp;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.db.file.XDBSessionSaveInfo;
import com.accessorydm.dmstarter.XDMInitExecutor;
import com.accessorydm.eng.core.XDMAbortMsgParam;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.filetransfer.XDMFileTransferManager;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XNOTIInterface;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.interfaces.XUICInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.network.NetworkBlockedType;
import com.accessorydm.network.NetworkChecker;
import com.accessorydm.noti.XNOTI;
import com.accessorydm.noti.XNOTIAdapter;
import com.accessorydm.noti.XNOTIHandler;
import com.accessorydm.noti.XNOTIMessage;
import com.accessorydm.postpone.PostponeType;
import com.accessorydm.ui.XUIAdapter;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationId;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.samsung.android.fotaagent.ProcessExtra;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;

public class XDMTask implements Runnable, XDMDefInterface, XDMInterface, XEventInterface, XCommonInterface, XUICInterface, XFOTAInterface, XNOTIInterface, XTPInterface {
    public static Handler g_hDmTask;
    private boolean g_IsSyncTaskInit = false;
    private XFOTADlAgent m_DlAgent = null;
    private XDMAgent m_DmAgent = null;

    public XDMTask() {
        if (xdmNeedToTaskInitialize()) {
            new Thread(this, "THR:XDMTask").start();
            xdmAgentTaskInit();
        }
    }

    public void run() {
        Looper.prepare();
        g_hDmTask = new Handler(new Handler.Callback() {
            /* class com.accessorydm.agent.$$Lambda$XDMTask$2fszTlsPOd2Akmmqpg1V3S60sn0 */

            public final boolean handleMessage(Message message) {
                return XDMTask.this.lambda$run$0$XDMTask(message);
            }
        });
        Looper.loop();
    }

    public /* synthetic */ boolean lambda$run$0$XDMTask(Message message) {
        try {
            xdmAgentTaskHandler(message);
            return true;
        } catch (Exception e) {
            Log.E(e.toString());
            return true;
        }
    }

    public static boolean xdmAgentTaskDBInit() {
        Log.setDumpState(XDMDmUtils.getContext());
        if (!XDB.xdmVerifyCheckDBTable()) {
            Log.I("xdmVerifyCheckDBTable false !!");
            XDB.xdbSqlFailAbort();
            return false;
        } else if (XDB.xdmDbInit()) {
            return true;
        } else {
            Log.I("xdmDbInit false !!");
            XDB.xdbSqlFailAbort();
            return false;
        }
    }

    private void xdmAgentTaskInit() {
        if (xdmNeedToTaskInitialize()) {
            this.m_DmAgent = new XDMAgent();
            this.m_DmAgent.m_AgentHandler = new XDMAgentHandler();
            this.m_DlAgent = new XFOTADlAgent();
            this.m_DlAgent.m_DlAgentHandler = new XFOTADlAgentHandler();
            this.m_DmAgent.m_AgentHandler.m_HttpDMAdapter = this.m_DmAgent.m_HttpDMAdapter;
            this.g_IsSyncTaskInit = true;
        }
    }

    private static void xdmTaskRemoveMessage() {
        g_hDmTask.removeMessages(0, null);
    }

    private boolean xdmNeedToTaskInitialize() {
        return !this.g_IsSyncTaskInit;
    }

    private void xdmAgentTaskHandler(Message message) throws InterruptedException {
        int i;
        Log.I("");
        if (message.obj != null) {
            XDMMsg.XDMMsgItem xDMMsgItem = (XDMMsg.XDMMsgItem) message.obj;
            XEventInterface.XEVENT valueOf = XEventInterface.XEVENT.valueOf(message.what);
            Log.I("[taskEventId : " + valueOf + "]");
            int i2 = 1;
            switch (valueOf) {
                case XEVENT_DM_CONNECT:
                    if (XDMInitExecutor.getInstance().isDmInitializedSuccessfully()) {
                        boolean xfotaDlAgentIsStatus = XFOTADlAgent.xfotaDlAgentIsStatus();
                        Log.I("xfotaDlAgentIsStatus :" + xfotaDlAgentIsStatus);
                        if (xfotaDlAgentIsStatus) {
                            if (XDBProfileAdp.xdbGetChangedProtocol()) {
                                Log.I("XEVENT_DM_CONNECT : Changed Protocol");
                            } else {
                                XDBProfileAdp.xdbSetBackUpServerUrl();
                            }
                            if (this.m_DmAgent.xdmAgentTpInit(0) != 0) {
                                Log.E("xdmAgentTpInit fail!!");
                                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                                return;
                            }
                            int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
                            XDBFumoAdp.xdbSetFUMOWifiOnlyDownload(false);
                            XDBFumoAdp.xdbSetFUMODeltaHash("");
                            if (xdbGetFUMOStatus == 0) {
                                XDBPostPoneAdp.xdbSetForceInstall(0);
                                XDBFumoInfo xdbGetFumoInfo = XDBFumoAdp.xdbGetFumoInfo();
                                if (xdbGetFumoInfo != null) {
                                    xdbGetFumoInfo.m_szStatusNotifyUrl = "";
                                    xdbGetFumoInfo.m_szObjectDownloadUrl = "";
                                    xdbGetFumoInfo.Correlator = "";
                                    xdbGetFumoInfo.ResultCode = "";
                                    xdbGetFumoInfo.szDescription = "";
                                    xdbGetFumoInfo.szDownloadResultCode = "";
                                    xdbGetFumoInfo.nObjectSize = 0;
                                    XDBFumoAdp.xdbSetFumoInfo(xdbGetFumoInfo);
                                }
                            }
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_TCPIP_OPEN, null, null);
                            return;
                        }
                        int xdbGetFUMOUpdateMechanism = XDBFumoAdp.xdbGetFUMOUpdateMechanism();
                        Log.I("nMechanism : " + xdbGetFUMOUpdateMechanism);
                        int xdbGetFUMOStatus2 = XDBFumoAdp.xdbGetFUMOStatus();
                        Log.I("nFumoStatus : " + xdbGetFUMOStatus2);
                        if (xdbGetFUMOUpdateMechanism == 2) {
                            if (xdbGetFUMOStatus2 == 50 || xdbGetFUMOStatus2 == 251) {
                                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
                                return;
                            } else if (xdbGetFUMOStatus2 == 200) {
                                if (XUIAdapter.xuiAdpIsReportingToServer() && XDBFumoAdp.xdbGetFUMOOptionalUpdate()) {
                                    XDBFumoAdp.xdbSetUiMode(1);
                                }
                                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_YES_NO);
                                return;
                            } else {
                                Log.I("XDM_FOTA_MECHANISM_ALTERNATIVE XEVENT_DL_CONNECT");
                                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                                return;
                            }
                        } else if (xdbGetFUMOUpdateMechanism == 3) {
                            if (xdbGetFUMOStatus2 == 50 || xdbGetFUMOStatus2 == 251) {
                                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
                                return;
                            } else if (xdbGetFUMOStatus2 != 40) {
                                Log.I("XDM_FOTA_MECHANISM_ALTERNATIVE_DOWNLOAD XEVENT_DL_CONNECT");
                                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                                return;
                            } else if (this.m_DmAgent.xdmAgentTpInit(0) != 0) {
                                Log.E("xdmAgentTpInit fail!");
                                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                                return;
                            } else {
                                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_TCPIP_OPEN, null, null);
                                return;
                            }
                        } else if (xdbGetFUMOUpdateMechanism == 1) {
                            if (XDBProfileAdp.xdbGetChangedProtocol()) {
                                Log.I("XDM_FOTA_MECHANISM_REPLACE : Changed Protocol");
                            } else {
                                XDBProfileAdp.xdbSetBackUpServerUrl();
                            }
                            if (this.m_DmAgent.xdmAgentTpInit(0) != 0) {
                                Log.E("xdmAgentTpInit fail!");
                                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                                return;
                            }
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_TCPIP_OPEN, null, null);
                            return;
                        } else if (xdbGetFUMOUpdateMechanism == 0) {
                            if (XDBProfileAdp.xdbGetChangedProtocol()) {
                                Log.I("XDM_FOTA_MECHANISM_NONE : Changed Protocol");
                            } else {
                                Log.I("XDM_FOTA_MECHANISM_NONE");
                                XDBProfileAdp.xdbSetBackUpServerUrl();
                            }
                            if (this.m_DmAgent.xdmAgentTpInit(0) != 0) {
                                Log.E("xdmAgentTpInit fail!");
                                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECTFAIL, null, null);
                                return;
                            }
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_TCPIP_OPEN, null, null);
                            return;
                        } else {
                            return;
                        }
                    } else {
                        Log.I("XUI_DM_NOT_INIT");
                        XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_NOT_INIT);
                        return;
                    }
                case XEVENT_DM_CONNECTFAIL:
                case XEVENT_DM_SENDFAIL:
                case XEVENT_DM_RECEIVEFAIL:
                    xdmAgentDmTpClose();
                    checkDMRetry();
                    return;
                case XEVENT_DM_START:
                    XDMAgent.xdmAgentSetSyncMode(1);
                    this.m_DmAgent.m_AgentHandler.xdmAgentHdlrContinueSession(XEventInterface.XEVENT.XEVENT_DM_START);
                    return;
                case XEVENT_DM_CONTINUE:
                    this.m_DmAgent.m_AgentHandler.xdmAgentHdlrContinueSession(XEventInterface.XEVENT.XEVENT_DM_CONTINUE);
                    return;
                case XEVENT_DM_ABORT:
                    if (xDMMsgItem.param == null) {
                        Log.E("param is null");
                        return;
                    }
                    XDMAbortMsgParam xDMAbortMsgParam = (XDMAbortMsgParam) xDMMsgItem.param.param;
                    Log.I("pAbortParam.abortCode:" + xDMAbortMsgParam.abortCode);
                    if (xDMAbortMsgParam.abortCode == 241) {
                        XDBAgentAdp.xdbSetDmAgentType(0);
                        XDBFumoAdp.xdbSetFUMOStatus(0);
                        XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                        XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                        XDMAgent.xdmAgentClose();
                        xdmAgentDmTpClose();
                    } else if (xDMAbortMsgParam.abortCode == 243) {
                        XDMAgentHandler.xdmAgentClose();
                        xdmAgentDmTpClose();
                    } else if (xDMAbortMsgParam.abortCode == 242) {
                        int xdbGetDmAgentType = XDBAgentAdp.xdbGetDmAgentType();
                        Log.I("XEVENT_ABORT_HTTP_ERROR, not implement...");
                        XDMAgentHandler.xdmAgentClose();
                        xdmAgentDmTpClose();
                        if (xdbGetDmAgentType == 1) {
                            XDBAgentAdp.xdbSetDmAgentType(0);
                            XDBFumoAdp.xdbSetFUMOStatus(0);
                            XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                        }
                    } else if (xDMAbortMsgParam.abortCode == 249) {
                        int xdbGetDmAgentType2 = XDBAgentAdp.xdbGetDmAgentType();
                        Log.I("XEVENT_ABORT_DM_CHANGEPROTOCOL_OVERCOUNT");
                        XDMAgentHandler.xdmAgentClose();
                        this.m_DmAgent.xdmAgentTpClose(0);
                        this.m_DmAgent.xdmAgentTpCloseNetwork(0);
                        if (xdbGetDmAgentType2 == 1) {
                            XDBAgentAdp.xdbSetDmAgentType(0);
                            XDBFumoAdp.xdbSetFUMOStatus(0);
                            XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                        }
                        XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                    } else if (xDMAbortMsgParam.abortCode == 250) {
                        Log.I("XEVENT_ABORT_DB_SQL_ERROR");
                        XDMAgentHandler.xdmAgentClose();
                        xdmAgentDmTpClose();
                        xdmAgentDlTpClose();
                        FotaProviderState.resetDataAndStopAlarms(XDMDmUtils.getContext());
                        xdmTaskRemoveMessage();
                        return;
                    } else if (xDMAbortMsgParam.abortCode == 251) {
                        XDBAgentAdp.xdbSetDmAgentType(0);
                        XDBFumoAdp.xdbSetFUMOStatus(0);
                        XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                        XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                        XDMAgentHandler.xdmAgentClose();
                        xdmAgentDmTpClose();
                        xdmAgentDlTpClose();
                        xdmTaskRemoveMessage();
                        return;
                    } else {
                        Log.I(" XEVENT_DM_ABORT : ELSE");
                        XDMAgentHandler.xdmAgentClose();
                        xdmAgentDmTpClose();
                    }
                    if (XDBProfileAdp.xdbGetChangedProtocol()) {
                        XDBProfileAdp.xdbSetBackUpServerUrl();
                    }
                    XUINotificationManager.getInstance().xuiRemoveAllNotification();
                    XDMAgent.xdmAgentTpSetRetryCount(0);
                    XDMAgent.xdmAgentResetChangeProtocolCount();
                    int i3 = xDMAbortMsgParam.abortCode;
                    if (i3 != 249) {
                        switch (i3) {
                            case 241:
                                XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_ABORT_BYUSER);
                                return;
                            case XEventInterface.XEVENT_ABORT_HTTP_ERROR /*{ENCODED_INT: 242}*/:
                                XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_HTTP_INTERNAL_ERROR);
                                return;
                            case XEventInterface.XEVENT_ABORT_SYNCDM_ERROR /*{ENCODED_INT: 243}*/:
                                break;
                            default:
                                return;
                        }
                    }
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_SYNC_ERROR);
                    return;
                case XEVENT_DM_FINISH:
                    xdmAgentDmTpClose();
                    XDMAgent.xdmAgentTpSetRetryCount(0);
                    this.m_DmAgent.m_AgentHandler.xdmAgentHdlrContinueSession(XEventInterface.XEVENT.XEVENT_DM_FINISH);
                    if (XDBFumoAdp.xdbGetFUMOStatus() == 0) {
                        XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                        return;
                    }
                    return;
                case XEVENT_DM_TCPIP_OPEN:
                    XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_SYNC_DM);
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_START, null, null);
                    return;
                case XEVENT_DL_CONNECT:
                    int xdbGetFUMOStatus3 = XDBFumoAdp.xdbGetFUMOStatus();
                    if (xdbGetFUMOStatus3 == 200 || xdbGetFUMOStatus3 == 30) {
                        XUIProgressModel.getInstance().initializeProgress();
                    }
                    XDMAgent.xdmAgentSetSyncMode(1);
                    int xfotaDlTpInit = this.m_DlAgent.xfotaDlTpInit(1);
                    if (xfotaDlTpInit == -7) {
                        if (xdbGetFUMOStatus3 == 10 || xdbGetFUMOStatus3 == 40 || xdbGetFUMOStatus3 == 20 || xdbGetFUMOStatus3 == 230) {
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_FINISH, null, null);
                            XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_BAD_URL);
                            XDMInitAdapter.xdmAccessoryDownloadFailedReport();
                            return;
                        }
                        return;
                    } else if (xfotaDlTpInit != 0) {
                        Log.E("xfotaDlTpInit fail!");
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECTFAIL, null, null);
                        return;
                    } else {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_TCPIP_OPEN, null, null);
                        return;
                    }
                case XEVENT_DL_CONNECTFAIL:
                case XEVENT_DL_SENDFAIL:
                case XEVENT_DL_RECEIVEFAIL:
                    xdmAgentDlTpClose();
                    xdmAgentFlagOffWhenDownloadFailed();
                    if (checkDLRetry()) {
                        Log.I("checkDLRetry, DL Retry Save Resume");
                        return;
                    } else {
                        forDefenseDLMaxRetryExceed();
                        return;
                    }
                case XEVENT_DL_ABORT:
                    if (xDMMsgItem.param != null) {
                        XDMAbortMsgParam xDMAbortMsgParam2 = (XDMAbortMsgParam) xDMMsgItem.param.param;
                        XUINotificationManager.getInstance().xuiRemoveNotification(NotificationId.XDM_NOTIFICATION_ID_PRIMARY);
                        xdmAgentFlagOffWhenDownloadFailed();
                        if (xDMAbortMsgParam2.abortCode == 241) {
                            if (NetworkChecker.get().getNetworkBlockType() == NetworkBlockedType.WIFI_DISCONNECTED) {
                                forDLRetryInitializeWithResumeSave();
                                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_FAILED_WIFI_DISCONNECTED);
                                return;
                            }
                            XDBAgentAdp.xdbSetDmAgentType(1);
                            XDBFumoAdp.xdbSetFUMOStatus(0);
                            XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
                            XDBFumoAdp.xdbSetUiMode(0);
                            XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                            XDMAgent.xdmAgentClose();
                            xdmAgentDlTpClose();
                            return;
                        } else if (xDMAbortMsgParam2.abortCode == 242) {
                            Log.I("XEVENT_ABORT_HTTP_ERROR, not implement...");
                            XDMAgentHandler.xdmAgentClose();
                            xdmAgentDlTpClose();
                            XDMAgent.xdmAgentSetSyncMode(0);
                            XFOTADlAgent.xfotaDlTpSetRetryCount(0);
                            if (XDBFumoAdp.xdbGetFUMOStatus() == 40) {
                                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_COMPLETE);
                                return;
                            }
                            Log.I("send generic alert for fail to download package");
                            XDBFumoAdp.xdbSetFUMOResultCode("500");
                            XDMInitAdapter.xdmAccessoryDownloadFailedReport();
                            return;
                        } else if (xDMAbortMsgParam2.abortCode == 248 || xDMAbortMsgParam2.abortCode == 247 || xDMAbortMsgParam2.abortCode == 246) {
                            Log.I("XEVENT_ABORT_DL_SERVICE_UNAVAILABLE");
                            XDMAgentHandler.xdmAgentClose();
                            this.m_DlAgent.xfotaDlTpClose(1);
                            this.m_DlAgent.xfotaDlTpCloseNetWork(1);
                            XDMAgent.xdmAgentSetSyncMode(0);
                            XFOTADlAgent.xfotaDlTpSetRetryCount(0);
                            if (XDBFumoAdp.xdbGetFUMOStatus() == 40) {
                                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_COMPLETE);
                                return;
                            }
                            Log.I("send generic alert for fail to download package");
                            if (xDMAbortMsgParam2.abortCode == 247) {
                                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_DL_SERVER_REDIRECT);
                            } else if (xDMAbortMsgParam2.abortCode == 246) {
                                XDBFumoAdp.xdbSetFUMOResultCode("510");
                            } else {
                                XDBFumoAdp.xdbSetFUMOResultCode(XFOTAInterface.XFOTA_GENERIC_DL_SERVICE_UNAVAILABLE);
                            }
                            XDMInitAdapter.xdmAccessoryDownloadFailedReport();
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                case XEVENT_DL_TCPIP_OPEN:
                    try {
                        XFOTADlAgent xFOTADlAgent = this.m_DlAgent;
                        i = XFOTADlAgent.g_HttpDLAdapter.xtpAdpOpen(1);
                    } catch (Exception e) {
                        Log.E(e.toString());
                        i = -2;
                    }
                    if (i == 0) {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_START, null, null);
                        return;
                    } else {
                        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECTFAIL, null, null);
                        return;
                    }
                case XEVENT_DL_START:
                    XFOTADlAgentHandler xFOTADlAgentHandler = this.m_DlAgent.m_DlAgentHandler;
                    XFOTADlAgentHandler.xfotaDlAgentHdlrStartOMADLAgent(XEventInterface.XEVENT.XEVENT_DL_START);
                    return;
                case XEVENT_DL_CONTINUE:
                    XFOTADlAgentHandler xFOTADlAgentHandler2 = this.m_DlAgent.m_DlAgentHandler;
                    XFOTADlAgentHandler.xfotaDlAgentHdlrStartOMADLAgent(XEventInterface.XEVENT.XEVENT_DL_CONTINUE);
                    return;
                case XEVENT_DL_FINISH:
                    xdmAgentDlTpClose();
                    XFOTADlAgent.xfotaDlTpSetRetryCount(0);
                    xdmAgentFlagOffWhenDownloadFailed();
                    XDMAgent.xdmAgentSetSyncMode(0);
                    if (XDBFumoAdp.xdbGetFUMOStatus() == 200) {
                        if (XUIAdapter.xuiAdpIsReportingToServer()) {
                            if (!XDBFumoAdp.xdbGetFUMOOptionalUpdate()) {
                                i2 = 2;
                            }
                            XDBFumoAdp.xdbSetUiMode(i2);
                        }
                        XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_YES_NO);
                        return;
                    }
                    return;
                case XEVENT_DL_USER_CANCEL_DOWNLOAD:
                    if (XDBFumoAdp.xdbGetFUMOStatus() == 30) {
                        XUIProgressModel.getInstance().initializeProgress();
                    }
                    XDBFumoAdp.xdbSetFUMODownloadResultCode(XFOTADlAgentHandler.xfotaDlAgentGetReportStatus(2));
                    XDBFumoAdp.xdbSetFUMOStatus(XFOTAInterface.XDL_STATE_DOWNLOAD_IN_CANCEL);
                    XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
                    return;
                case XEVENT_DL_DEVICE_FAIL_DOWNLOAD:
                    String str = (String) xDMMsgItem.param.param;
                    Log.I("XEVENT_DL_DEVICE_FAIL_DOWNLOAD [" + valueOf.ordinal() + "], FailReson = [" + str + "]");
                    this.m_DmAgent.xdmAgentTpClose(0);
                    this.m_DmAgent.xdmAgentTpCloseNetwork(0);
                    if (XFOTAInterface.XFOTA_GENERIC_BLOCKED_MDM_UPDATE_FAILED.equals(str)) {
                        XDBFumoAdp.xdbSetFUMODownloadResultCode(XFOTADlAgentHandler.xfotaDlAgentGetReportStatus(10));
                    } else if (XFOTAInterface.XFOTA_GENERIC_DOWNLOAD_FAILED_OUT_MEMORY.equals(str)) {
                        XDBFumoAdp.xdbSetFUMODownloadResultCode(XFOTADlAgentHandler.xfotaDlAgentGetReportStatus(1));
                    } else {
                        XDBFumoAdp.xdbSetFUMODownloadResultCode(XFOTADlAgentHandler.xfotaDlAgentGetReportStatus(7));
                    }
                    XDBFumoAdp.xdbSetFUMOResultCode(str);
                    XDBFumoAdp.xdbSetFUMOStatus(20);
                    XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                    return;
                case XEVENT_NOTI_RECEIVED:
                    XNOTIHandler xNOTIHandler = new XNOTIHandler();
                    XNOTIMessage xNOTIMessage = (XNOTIMessage) xDMMsgItem.param.param;
                    if (!XDMDevinfAdapter.xdmDevAdpVerifyDevID()) {
                        XNOTIAdapter.xnotiPushDataHandling();
                        return;
                    }
                    XNOTIMessage xnotiPushHdleMessageCopy = xNOTIHandler.xnotiPushHdleMessageCopy(xNOTIMessage);
                    if (xnotiPushHdleMessageCopy == null) {
                        Log.E("pPushMsg is NULL");
                        XNOTIAdapter.xnotiPushDataHandling();
                        return;
                    }
                    XNOTI xnotiPushHdleMsgHandler = xNOTIHandler.xnotiPushHdleMsgHandler(xnotiPushHdleMessageCopy);
                    if (xnotiPushHdleMsgHandler == null) {
                        XNOTIAdapter.xnotiPushDataHandling();
                        return;
                    } else if (xnotiPushHdleMsgHandler.appId != 0) {
                        Log.E("Not Support Application");
                        return;
                    } else if (xnotiPushHdleMsgHandler.triggerHeader == null) {
                        Log.E("triggerHeader is NULL.");
                        XNOTIAdapter.xnotiPushDataHandling();
                        return;
                    } else {
                        XDBNotiInfo xDBNotiInfo = new XDBNotiInfo();
                        xDBNotiInfo.appId = 0;
                        xDBNotiInfo.uiMode = xnotiPushHdleMsgHandler.triggerHeader.uiMode;
                        xDBNotiInfo.m_szServerId = xnotiPushHdleMsgHandler.triggerHeader.m_szServerID;
                        xDBNotiInfo.m_szSessionId = xnotiPushHdleMsgHandler.triggerHeader.m_szSessionID;
                        xDBNotiInfo.opMode = xnotiPushHdleMsgHandler.triggerBody.opmode;
                        xDBNotiInfo.jobId = xnotiPushHdleMsgHandler.triggerBody.pushjobId;
                        if (!XDB.xdbCheckActiveProfileIndexByServerID(xDBNotiInfo.m_szServerId)) {
                            Log.I("Not Active Profile Index By ServerID");
                            XNOTIAdapter.xnotiPushDataHandling();
                            return;
                        }
                        XDBSessionSaveInfo xdbGetSessionSaveStatus = XDBProfileListAdp.xdbGetSessionSaveStatus();
                        if (!((xdbGetSessionSaveStatus != null ? xdbGetSessionSaveStatus.nSessionSaveState : 0) == 0 && XDBFumoAdp.xdbGetFUMOInitiatedType() == 0 && XDBFumoAdp.xdbGetFUMOStatus() == 0 && XDBPostPoneAdp.xdbGetPostponeType() == PostponeType.NONE && !XDMSecReceiverApiCall.getInstance().isPushNotiSaved())) {
                            XDBNoti.xdbNotiInsertInfo(xDBNotiInfo);
                            Log.I("Noti was saved");
                            XDMSecReceiverApiCall.getInstance().setPushNotiSaved(false);
                            XNOTIAdapter.xnotiPushDataHandling();
                            i2 = 0;
                        }
                        if (i2 != 0) {
                            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_NOTI_EXECUTE, xDBNotiInfo, null);
                            return;
                        }
                        return;
                    }
                case XEVENT_NOTI_EXECUTE:
                    XDBNotiInfo xDBNotiInfo2 = (XDBNotiInfo) xDMMsgItem.param.param;
                    if (xDBNotiInfo2 == null) {
                        XNOTIAdapter.xnotiPushDataHandling();
                        return;
                    }
                    if (xDBNotiInfo2.appId != 0) {
                        Log.E("Not Support Application");
                    } else {
                        XNOTIAdapter.xnotiPushAdpClearSessionStatus();
                        XDMAgent.xdmAgentTpSetRetryCount(0);
                        XDB.xdbSetActiveProfileIndexByServerID(xDBNotiInfo2.m_szServerId);
                        XDBProfileListAdp.xdbSetNotiSessionID(xDBNotiInfo2.m_szSessionId);
                        XDBProfileListAdp.xdbSetNotiEvent(xDBNotiInfo2.uiMode);
                        XDBNoti.xdbNotiSetOPMode(xDBNotiInfo2.opMode);
                        XNOTIAdapter.xnotiPushAdpProcessNotiMessage(xDBNotiInfo2.uiMode);
                    }
                    XNOTIAdapter.xnotiPushDataHandling();
                    return;
                case XEVENT_NOTI_NOT_SPECIFIED:
                case XEVENT_NOTI_BACKGROUND:
                case XEVENT_NOTI_INFORMATIVE:
                case XEVENT_NOTI_INTERACTIVE:
                    Log.I(valueOf + " [" + valueOf.ordinal() + "]");
                    return;
                case XEVENT_ACCESSORY_COPY:
                    XDMFileTransferManager.copyDelta();
                    return;
                case XEVENT_ACCESSORY_DIFFERENT_DEVICE:
                    if (FotaProviderState.isInChangedDeviceProcess()) {
                        new ProcessExtra().changeDeviceInfo();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void xdmAgentDmTpClose() {
        Log.I("");
        XDMAgent xDMAgent = this.m_DmAgent;
        if (xDMAgent != null) {
            xDMAgent.xdmAgentTpClose(0);
            this.m_DmAgent.xdmAgentTpCloseNetwork(0);
        }
    }

    public void xdmAgentDlTpClose() {
        Log.I("");
        XFOTADlAgent xFOTADlAgent = this.m_DlAgent;
        if (xFOTADlAgent != null) {
            xFOTADlAgent.xfotaDlTpClose(1);
            this.m_DlAgent.xfotaDlTpCloseNetWork(1);
        }
    }

    public static void xdmAgentFlagOffWhenDownloadFailed() {
        XFOTADl.xfotaDownloadSetDrawingPercentage(false);
        XFOTADl.xfotaCopySetDrawingPercentage(false);
    }

    private void checkDMRetry() throws InterruptedException {
        Log.I("");
        if (this.m_DmAgent.xdmAgentTpCheckRetry()) {
            Thread.sleep(3500);
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
            return;
        }
        if (XDBProfileAdp.xdbGetChangedProtocol()) {
            XDBProfileAdp.xdbSetBackUpServerUrl();
        }
        XDMAgent.xdmAgentSetSyncMode(0);
        XDMAgent.xdmAgentTpSetRetryCount(0);
        XDMAgent.xdmAgentResetChangeProtocolCount();
        XUINotificationManager.getInstance().xuiRemoveNotification(NotificationId.XDM_NOTIFICATION_ID_PRIMARY);
        if (XDMDevinfAdapter.xdmBlocksDueToRoamingNetwork()) {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_ROAMING_WIFI_DISCONNECTED);
        } else {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_CONNECT_FAILED);
        }
        XDMDmUtils.getInstance().xdmSetResumeStatus(1);
    }

    private boolean checkDLRetry() throws InterruptedException {
        Log.I("");
        if (XDMDevinfAdapter.xdmBlocksDueToRoamingNetwork()) {
            forDLRetryInitializeWithResumeSave();
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_ROAMING_WIFI_DISCONNECTED);
            XUINotificationManager.getInstance().xuiRemoveNotification(NotificationId.XDM_NOTIFICATION_ID_PRIMARY);
            return true;
        } else if (this.m_DlAgent.xfotaDlTpCheckRetry()) {
            Thread.sleep(3500);
            int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
            NetworkBlockedType networkBlockType = NetworkChecker.get().getNetworkBlockType();
            if (xdbGetFUMOStatus != 230) {
                if (networkBlockType.isBlocked()) {
                    forDLRetryInitializeWithResumeSave();
                    networkBlockType.networkOperation(NetworkBlockedType.ShowUiType.DOWNLOAD_NETWORK_UI_BLOCK);
                } else if (!NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext())) {
                    Log.I("Download Fail Wi-Fi disconnected");
                    XDMAgent.xdmAgentSetSyncMode(0);
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_RETRY_CONFIRM);
                } else {
                    Log.I("else download retry");
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
                }
                return true;
            }
            if (networkBlockType.isBlocked()) {
                forDLRetryInitializeWithResumeSave();
            } else {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
            }
            return true;
        } else {
            forDLRetryInitializeWithResumeSave();
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_CONNECT_FAILED);
            XUINotificationManager.getInstance().xuiRemoveNotification(NotificationId.XDM_NOTIFICATION_ID_PRIMARY);
            return false;
        }
    }

    private void forDLRetryInitializeWithResumeSave() {
        XDMAgent.xdmAgentSetSyncMode(0);
        XFOTADlAgent.xfotaDlTpSetRetryCount(0);
        XDMDmUtils.getInstance().xdmSetResumeStatus(2);
    }

    private void forDefenseDLMaxRetryExceed() {
        Log.I("");
        int xfotaDlTpGetRetryFailCount = XFOTADlAgent.xfotaDlTpGetRetryFailCount();
        if (xfotaDlTpGetRetryFailCount < 1) {
            int i = xfotaDlTpGetRetryFailCount + 1;
            Log.E("DL server connect fail. nRetryFailCnt=" + i);
            XFOTADlAgent.xfotaDlTpSetRetryFailCount(i);
            return;
        }
        Log.E("DL server connect fail. nRetryFailCntMax OVER. Session reset");
        XFOTADlAgent.xfotaDlTpSetRetryFailCount(0);
        XDBFumoAdp.xdbSetFUMOResultCode("500");
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        Log.I("Fumo Status = " + xdbGetFUMOStatus);
        if (xdbGetFUMOStatus != 0) {
            Log.I("send generic alert for fail to download package");
            XUIProgressModel.getInstance().initializeProgress();
            XDMInitAdapter.xdmAccessoryDownloadFailedReport();
        }
    }
}
