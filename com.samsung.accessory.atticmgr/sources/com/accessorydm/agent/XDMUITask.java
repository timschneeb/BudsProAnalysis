package com.accessorydm.agent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.adapter.XDMCommonUtils;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.filetransfer.XDMFileTransferManager;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.interfaces.XDMAccessoryInterface;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XNOTIInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.noti.XNOTIAdapter;
import com.accessorydm.ui.UIManager;
import com.accessorydm.ui.XUIAdapter;
import com.accessorydm.ui.dialog.XUIDialog;
import com.accessorydm.ui.handler.XDMServiceHandler;
import com.accessorydm.ui.handler.XDMToastHandler;
import com.accessorydm.ui.installconfirm.InstallCountdown;
import com.accessorydm.ui.installconfirm.XUIInstallConfirmModel;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationId;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.accessorydm.ui.progress.listener.XUIProgressRangeCheckListener;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.controller.NotificationController;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;
import com.samsung.android.fotaprovider.util.type.DeviceType;

public class XDMUITask implements Runnable, XDMDefInterface, XDMInterface, XEventInterface, XNOTIInterface, XFOTAInterface, XCommonInterface, XDMAccessoryInterface {
    private static boolean g_IsSyncUiTaskInit = false;
    public static Handler g_hDmUiTask;

    public XDMUITask() {
        if (!g_IsSyncUiTaskInit) {
            XUIProgressModel.getInstance().addProgressListener(new XUIProgressRangeCheckListener());
            new Thread(this, "THR:XDMUITask").start();
            g_IsSyncUiTaskInit = true;
        }
    }

    public void run() {
        Looper.prepare();
        g_hDmUiTask = new Handler(new Handler.Callback() {
            /* class com.accessorydm.agent.$$Lambda$XDMUITask$PjmUMLeB1mhaQGqGDDvCahPQCGQ */

            public final boolean handleMessage(Message message) {
                return XDMUITask.this.lambda$run$0$XDMUITask(message);
            }
        });
        Looper.loop();
    }

    public /* synthetic */ boolean lambda$run$0$XDMUITask(Message message) {
        try {
            xdmUIEvent(message);
            return true;
        } catch (Exception e) {
            Log.E(e.toString());
            return true;
        }
    }

    private void xdmUIEvent(Message message) {
        if (message.obj != null) {
            XDMMsg.XDMMsgItem xDMMsgItem = (XDMMsg.XDMMsgItem) message.obj;
            if (xDMMsgItem.type instanceof XUIEventInterface.DM_UIEVENT) {
                xdmDmUiEvent(((XUIEventInterface.DM_UIEVENT) xDMMsgItem.type).ordinal());
            } else if (xDMMsgItem.type instanceof XUIEventInterface.DL_UIEVENT) {
                xdmDlUiEvent(((XUIEventInterface.DL_UIEVENT) xDMMsgItem.type).ordinal());
            } else if (xDMMsgItem.type instanceof XUIEventInterface.ACCESSORY_UIEVENT) {
                xdmAccUiEvent(((XUIEventInterface.ACCESSORY_UIEVENT) xDMMsgItem.type).ordinal());
            }
        }
    }

    private void xdmDoUiTaskEvent(NotificationType notificationType, Object obj) {
        if (notificationType != NotificationType.XUI_INDICATOR_NONE) {
            XUINotificationManager.getInstance().xuiSetIndicator(notificationType);
            AccessoryController.getInstance().getNotificationController().sendAccessoryNotification(new NotificationController.NotificationCallback(obj) {
                /* class com.accessorydm.agent.$$Lambda$XDMUITask$WpCIVrOC8oqRZPCc58MUTK3zvdI */
                private final /* synthetic */ Object f$0;

                {
                    this.f$0 = r1;
                }

                @Override // com.samsung.accessory.fotaprovider.controller.NotificationController.NotificationCallback
                public final void onResponse() {
                    XDMUITask.lambda$xdmDoUiTaskEvent$1(this.f$0);
                }
            }, notificationType);
        } else if (XDBFumoAdp.xdbGetUiMode() == 1) {
            XDMServiceHandler.xdmSendMessageDmHandler(obj);
        }
    }

    static /* synthetic */ void lambda$xdmDoUiTaskEvent$1(Object obj) {
        if (XDBFumoAdp.xdbGetUiMode() == 1) {
            XDMServiceHandler.xdmSendMessageDmHandler(obj);
        }
    }

    private void xdmDmUiEvent(int i) {
        Log.I("");
        XUIEventInterface.DM_UIEVENT valueOf = XUIEventInterface.DM_UIEVENT.valueOf(i);
        Log.I("[dmUiEvent : " + valueOf + "]");
        switch (valueOf) {
            case XUI_DM_NOT_INIT:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_CONNECTION_FAILED);
                return;
            case XUI_DM_CHECKING_FOR_UPDATE:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIEventInterface.DM_UIEVENT.XUI_DM_CHECKING_FOR_UPDATE);
                return;
            case XUI_DM_FINISH:
                XUINotificationManager.getInstance().xuiRemoveNotification(NotificationId.XDM_NOTIFICATION_ID_PRIMARY);
                if (FotaProviderState.isInChangedDeviceProcess()) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_ACCESSORY_DIFFERENT_DEVICE, null, null);
                    return;
                }
                if (XDBFumoAdp.xdbGetUiMode() == 1 && AccessoryController.getInstance().getConnectionController().isConnected()) {
                    if ("261".equals(XDMAgent.xdmAgentGetSvcState())) {
                        XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DM_SERVER_PARTIALLY_OPEN);
                    } else if (!XUIAdapter.xuiAdpIsReportingToServer()) {
                        XDMServiceHandler.xdmSendMessageDmHandler(XUIEventInterface.DM_UIEVENT.XUI_DM_NO_UPDATABLE_VERSION);
                    }
                }
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                XDBFumoAdp.xdbSetUiMode(0);
                XNOTIAdapter.xnotiPushAdpResetSessionSaveState();
                XNOTIAdapter.xnotiPushAdpHandleNotiQueue();
                return;
            case XUI_DM_ABORT_BYUSER:
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                XDBFumoAdp.xdbSetUiMode(0);
                XNOTIAdapter.xnotiPushAdpResetSessionSaveState();
                XNOTIAdapter.xnotiPushAdpHandleNotiQueue();
                return;
            case XUI_DM_SYNC_ERROR:
            case XUI_DM_HTTP_INTERNAL_ERROR:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_CONNECTION_FAILED);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                XDBFumoAdp.xdbSetUiMode(0);
                XNOTIAdapter.xnotiPushAdpClearSessionStatus();
                XNOTIAdapter.xnotiPushAdpHandleNotiQueue();
                return;
            case XUI_DM_CONNECT_FAILED:
            case XUI_DM_SEND_FAILED:
            case XUI_DM_RECV_FAILED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_CONNECTION_FAILED);
                return;
            case XUI_DM_NOTI_NOT_SPECIFIED:
            case XUI_DM_NOTI_BACKGROUND:
            case XUI_DM_NOTI_INFORMATIVE:
            case XUI_DM_NOTI_INTERACTIVE:
                Log.I(valueOf + "[" + valueOf.ordinal() + "]");
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                XDBFumoAdp.xdbSetUiMode(0);
                XUIAdapter.xuiAdpRequestNoti(valueOf);
                return;
            case XUI_DM_ROAMING_WIFI_DISCONNECTED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ROAMING_WIFI_DISCONNECTED);
                XNOTIAdapter.xnotiPushAdpResetSessionSaveState();
                XDBFumoAdp.xdbSetUiMode(0);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                return;
            default:
                return;
        }
    }

    private void xdmDlUiEvent(int i) {
        XUIEventInterface.DL_UIEVENT valueOf = XUIEventInterface.DL_UIEVENT.valueOf(i);
        Log.I("[dlUiEvent : " + valueOf + "]");
        switch (valueOf) {
            case XUI_DL_DOWNLOAD_START_CONFIRM:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_DOWNLOAD_START_CONFIRM, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_START_CONFIRM);
                if (XDBFumoAdp.xdbGetUiMode() == 2) {
                    XDMToastHandler.xdmShowToast(XDMDmUtils.getContext().getString(DeviceType.get().getTextType().getTitleId()), 1);
                    return;
                }
                return;
            case XUI_DL_DOWNLOAD_YES_NO:
                if (XDBFumoAdp.xdbGetFUMOOptionalUpdate()) {
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_START_CONFIRM);
                    return;
                } else if (!NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext()) || !XDB.xdbGetWifiAutoDownloadFlag()) {
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_START_CONFIRM);
                    return;
                } else {
                    XFOTADl.xfotaDownloadMemoryCheck(1);
                    return;
                }
            case XUI_DL_CONNECT_FAILED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DL_CONNECTION_FAILED);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                return;
            case XUI_DL_DOWNLOAD_RETRY_CONFIRM:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_DOWNLOAD_RETRY_CONFIRM, XUIDialog.DL_DOWNLOAD_RETRY_CONFIRM);
                if (!NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext())) {
                    XDMDmUtils.getInstance().xdmSetWaitWifiConnectMode(2);
                    return;
                }
                return;
            case XUI_DL_DOWNLOAD_FAILED_WIFI_DISCONNECTED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_DOWNLOAD_FAILED_WIFI_DISCONNECTED, XUIDialog.DL_DOWNLOAD_FAILED_WIFI_DISCONNECTED);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                return;
            case XUI_DL_DOWNLOAD_FAILED_NETWORK_DISCONNECTED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_DOWNLOAD_FAILED_NETWORK_DISCONNECTED, XUIDialog.DL_DOWNLOAD_FAILED_NETWORK_DISCONNECTED);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                return;
            case XUI_DL_DOWNLOAD_IN_PROGRESS:
                XUIProgressModel.getInstance().setTotalDeltaSize(XDBFumoAdp.xdbGetObjectSizeFUMO());
                if (XDMCommonUtils.xdmCheckIdleScreen()) {
                    xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_PROGRESS);
                    return;
                }
                return;
            case XUI_DL_COPY_IN_PROGRESS:
                XUIProgressModel.getInstance().setTotalDeltaSize(XDBFumoAdp.xdbGetObjectSizeFUMO());
                if (XDMCommonUtils.xdmCheckIdleScreen()) {
                    xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIEventInterface.DL_UIEVENT.XUI_DL_COPY_IN_PROGRESS);
                    return;
                }
                return;
            case XUI_DL_DOWNLOAD_IN_COMPLETE:
                XDBFumoAdp.xdbSetFUMOCopyRetryCount(0);
                XUIProgressModel.getInstance().initializeProgress();
                XDMFileTransferManager.checkDeviceInfo();
                return;
            case XUI_DL_MEMORY_FULL:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DL_MEMORY_FULL);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                XDBFumoAdp.xdbSetUiMode(0);
                return;
            case XUI_DL_UPDATE_CONFIRM:
                if (DeviceType.get().shouldShowUpdateConfirmUI()) {
                    NotificationType notificationType = XDBPostPoneAdp.xdbGetPostponeType().getNotificationType();
                    if (notificationType == null) {
                        notificationType = NotificationType.XUI_INDICATOR_FOTA_UPDATE;
                    }
                    xdmDoUiTaskEvent(notificationType, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
                    if (XDBFumoAdp.xdbGetUiMode() == 2) {
                        XDMToastHandler.xdmShowToast(XDMDmUtils.getContext().getString(DeviceType.get().getTextType().getTitleId()), 1);
                        if (XUIInstallConfirmModel.getInstance().isNeededInstallCountdown()) {
                            InstallCountdown.getInstance().startOrKeepGoing(null, XUIInstallConfirmModel.getInstance());
                            return;
                        }
                        return;
                    }
                    return;
                }
                UIManager.getInstance().finishAllActivities();
                XDMFileTransferManager.checkDeviceInfo();
                return;
            default:
                return;
        }
    }

    private void xdmAccUiEvent(int i) {
        XUIEventInterface.ACCESSORY_UIEVENT valueOf = XUIEventInterface.ACCESSORY_UIEVENT.valueOf(i);
        Log.I("[accEvent : " + valueOf + "]");
        switch (valueOf) {
            case XUI_DM_ACCESSORY_COPY_RETRY_CONFIRM:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_COPY_FAILED, XUIDialog.DM_ACCESSORY_COPY_RETRY_CONFIRM);
                return;
            case XUI_DM_ACCESSORY_COPY_FAILED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ACCESSORY_COPY_FAILED);
                return;
            case XUI_DM_ACCESSORY_CONNECTION_FAILED:
                int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
                if (xdbGetFUMOStatus != 0) {
                    if (xdbGetFUMOStatus == 40) {
                        XDBFumoAdp.xdbSetFUMOStatus(250);
                        xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_COPY_FAILED, XUIDialog.DM_ACCESSORY_CONNECTION_FAILED);
                        return;
                    } else if (!(xdbGetFUMOStatus == 50 || xdbGetFUMOStatus == 200 || xdbGetFUMOStatus == 220 || xdbGetFUMOStatus == 230 || xdbGetFUMOStatus == 250 || xdbGetFUMOStatus == 251)) {
                        return;
                    }
                }
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ACCESSORY_CONNECTION_FAILED);
                return;
            case XUI_DM_ACCESSORY_COPY_RETRY_LATER:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_COPY_FAILED, XUIDialog.DM_ACCESSORY_COPY_RETRY_LATER);
                return;
            case XUI_DM_ACCESSORY_COPY_RETRY_LATER_ACCESSORY_UNCOUPLED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_COPY_FAILED, XUIDialog.DM_ACCESSORY_COPY_RETRY_LATER_ACCESSORY_UNCOUPLED);
                return;
            case XUI_DM_ACCESSORY_LOW_MEMORY_DOWNLOAD_WATCH:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ACCESSORY_LOW_MEMORY_DOWNLOAD_WATCH);
                return;
            case XUI_DM_ACCESSORY_LOW_MEMORY_COPY_WATCH:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ACCESSORY_LOW_MEMORY_COPY_WATCH);
                return;
            case XUI_DM_ACCESSORY_LOW_MEMORY_INSTALL_WATCH:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_FOTA_UPDATE, XUIDialog.DM_ACCESSORY_LOW_MEMORY_INSTALL_WATCH);
                return;
            case XUI_DM_ACCESSORY_LOW_BATTERY_WATCH:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ACCESSORY_LOW_BATTERY_WATCH);
                return;
            case XUI_DM_ACCESSORY_INSTALL_FAILED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ACCESSORY_INSTALL_FAILED);
                return;
            case XUI_DM_ACCESSORY_SYSSCOPE_SCANNING:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIEventInterface.DM_UIEVENT.XUI_DM_CHECKING_FOR_UPDATE);
                return;
            case XUI_DM_ACCESSORY_SYSSCOPE_MODIFIED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ACCESSORY_SYSSCOPE_MODIFIED);
                if (XDBFumoAdp.xdbGetFUMOStatus() == 0) {
                    XDBFumoAdp.xdbSetFUMOInitiatedType(0);
                    return;
                }
                return;
            case XUI_DM_ACCESSORY_BLOCKED_BY_POLICY_FAILED:
                xdmDoUiTaskEvent(NotificationType.XUI_INDICATOR_NONE, XUIDialog.DM_ACCESSORY_BLOCKED_BY_POLICY_FAILED);
                return;
            default:
                return;
        }
    }
}
