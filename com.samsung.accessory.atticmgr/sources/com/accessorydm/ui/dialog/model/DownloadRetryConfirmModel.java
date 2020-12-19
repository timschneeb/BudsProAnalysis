package com.accessorydm.ui.dialog.model;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.agent.XDMTask;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.agent.fota.XFOTADlAgentHandler;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.postpone.PostponeType;
import com.accessorydm.ui.dialog.model.XUIDialogModel;
import com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.samsung.android.fotaprovider.util.NetworkUtil;
import com.samsung.android.fotaprovider.util.OperatorUtil;
import com.samsung.android.fotaprovider.util.UiUtil;
import com.samsung.android.fotaprovider.util.type.HostDeviceTextType;
import com.sec.android.fotaprovider.R;

public final class DownloadRetryConfirmModel extends XUIDialogModel.Base {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DownloadRetryConfirmModel() {
        super(title(), message(), NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext()) ? ButtonStrategy.Neutral.NONE : new WifiSettingButtonStrategy(), new RetryCancelButtonStrategy(), new RetryOkButtonStrategy());
    }

    private static String title() {
        if (NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext())) {
            return getString(R.string.STR_ACCESSORY_DOWNLOAD_RETRY_CONFIRM_VIA_WIFI_TITLE);
        }
        return getString(R.string.STR_ACCESSORY_DOWNLOAD_COULDNT_COMPLETE_DOWNLOAD_TITLE);
    }

    private static String message() {
        if (NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext())) {
            return getString(HostDeviceTextType.get().getDownloadRetryConfirmViaWifiMessageId());
        }
        return getString(R.string.STR_ACCESSORY_DOWNLOAD_RETRY_CONFIRM);
    }

    @Override // com.accessorydm.ui.dialog.model.XUIDialogModel.Base, com.accessorydm.ui.dialog.model.XUIDialogModel
    public void preExecute() {
        XDMTask.xdmAgentFlagOffWhenDownloadFailed();
    }

    private static class WifiSettingButtonStrategy extends ButtonStrategy.Neutral {
        WifiSettingButtonStrategy() {
            super(OperatorUtil.replaceToWLAN(R.string.STR_BTN_WIFI_SETTINGS));
        }

        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
            UiUtil.showWiFiSetting();
        }
    }

    private static class RetryCancelButtonStrategy extends ButtonStrategy.Negative {
        RetryCancelButtonStrategy() {
            super(XUIDialogModel.Base.getString(R.string.STR_BTN_CANCEL));
        }

        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
            XUINotificationManager.getInstance().xuiRemoveNotification(NotificationType.XUI_INDICATOR_DOWNLOAD_RETRY_CONFIRM);
            XFOTADlAgentHandler.xfotaDlAgentHdlrStartOMADLAgent(XEventInterface.XEVENT.XEVENT_DL_USER_CANCEL_DOWNLOAD);
            XDMDmUtils.getInstance().xdmSetWaitWifiConnectMode(0);
            XDBPostPoneAdp.xdbSetPostponeType(PostponeType.NONE);
        }
    }

    private static class RetryOkButtonStrategy extends ButtonStrategy.Positive {
        RetryOkButtonStrategy() {
            super(XUIDialogModel.Base.getString(R.string.STR_BTN_OK));
        }

        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
            XUINotificationManager.getInstance().xuiRemoveNotification(NotificationType.XUI_INDICATOR_DOWNLOAD_RETRY_CONFIRM);
            XDMDmUtils.getInstance().xdmSetWaitWifiConnectMode(0);
            if (XDBFumoAdp.xdbGetFUMOStatus() == 200) {
                XFOTADl.xfotaDownloadMemoryCheck(1);
            } else {
                XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
            }
        }
    }
}
