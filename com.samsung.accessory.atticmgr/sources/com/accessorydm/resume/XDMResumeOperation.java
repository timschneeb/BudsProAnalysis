package com.accessorydm.resume;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.adapter.XDMInitAdapter;
import com.accessorydm.agent.XDMAgent;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.network.NetworkBlockedType;
import com.accessorydm.network.NetworkChecker;
import com.accessorydm.postpone.PostponeManager;
import com.accessorydm.ui.handler.XDMToastHandler;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;
import com.sec.android.fotaprovider.R;

public class XDMResumeOperation {
    private static final XDMResumeOperation INSTANCE = new XDMResumeOperation();

    /* access modifiers changed from: package-private */
    public enum ConfirmType {
        DOWNLOAD_CONFIRM,
        DOWNLOAD_RETRY_CONFIRM
    }

    public static XDMResumeOperation getInstance() {
        return INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public boolean doResume() {
        Log.I("");
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        switch (xdbGetFUMOStatus) {
            case 10:
            case 30:
            case 40:
            case 200:
                startDLProcessIfNecessary(xdbGetFUMOStatus);
                return true;
            case 20:
            case 65:
            case 80:
            case 100:
            case XFOTAInterface.XDL_STATE_DOWNLOAD_IN_CANCEL:
            case XFOTAInterface.XDL_STATE_USER_CANCEL_REPORTING:
            case 241:
                startReportProcessIfNecessary(xdbGetFUMOStatus);
                return true;
            case 50:
            case XFOTAInterface.XDL_STATE_POSTPONE_TO_UPDATE:
            case 251:
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
                return true;
            case 60:
                Log.I("UPDATE_IN_PROGRESS");
                return true;
            case 250:
                Log.I("Resume Copy Event Send");
                if (XFOTADl.xfotaCopyGetDrawingPercentage()) {
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_COPY_IN_PROGRESS);
                } else {
                    XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_COPY_RETRY_CONFIRM);
                }
                return true;
            default:
                return false;
        }
    }

    private void startReportProcessIfNecessary(int i) {
        Log.I("");
        if (XDMAgent.xdmAgentGetSyncMode() != 0) {
            XDMToastHandler.xdmShowToast(String.format("%s%n%s", XDMDmUtils.getContext().getString(R.string.STR_DM_CONNECTING_SERVER), XDMDmUtils.getContext().getString(R.string.STR_COMMON_PLEASE_WAIT)), 0);
            return;
        }
        if (i != 20) {
            if (i == 65 || i == 80 || i == 100) {
                XDMInitAdapter.xdmAccessoryUpdateResultReport();
                return;
            } else if (i != 230) {
                if (i == 240 || i == 241) {
                    XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
                    return;
                }
                return;
            }
        }
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
    }

    private void startDLProcessIfNecessary(int i) {
        Log.I("");
        NetworkBlockedType networkBlockType = NetworkChecker.get().getNetworkBlockType();
        if (networkBlockType.isBlocked()) {
            Log.I("network isn't available");
            networkBlockType.networkOperation(NetworkBlockedType.ShowUiType.GENERAL_NETWORK_UI_BLOCK);
        } else if (i == 10 || i == 30 || i == 40) {
            if (XFOTADl.xfotaDownloadGetDrawingPercentage()) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_PROGRESS);
            } else if (XDMAgent.xdmAgentGetSyncMode() != 0) {
                XDMToastHandler.xdmShowToast(String.format("%s%n%s", XDMDmUtils.getContext().getString(R.string.STR_DM_CONNECTING_SERVER), XDMDmUtils.getContext().getString(R.string.STR_COMMON_PLEASE_WAIT)), 0);
            } else if (shouldShowConfirm(ConfirmType.DOWNLOAD_RETRY_CONFIRM)) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_RETRY_CONFIRM);
            } else {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
            }
        } else if (i == 200) {
            PostponeManager.cancelPostpone();
            if (shouldShowConfirm(ConfirmType.DOWNLOAD_CONFIRM)) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_START_CONFIRM);
            } else {
                XFOTADl.xfotaDownloadMemoryCheck(1);
            }
        }
    }

    private boolean shouldShowConfirm(ConfirmType confirmType) {
        if (confirmType == ConfirmType.DOWNLOAD_CONFIRM && XDBFumoAdp.xdbGetFUMOOptionalUpdate()) {
            Log.I("Optional update: show confirm");
            return true;
        } else if (!NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext())) {
            Log.I("Wi-Fi is disconnected: show confirm");
            return true;
        } else if (XDB.xdbGetWifiAutoDownloadFlag()) {
            Log.I("Auto download over Wi-Fi: start download");
            return false;
        } else {
            Log.I("show confirm");
            return true;
        }
    }
}
